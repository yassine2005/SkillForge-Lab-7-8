import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class QuizTaking extends JDialog {

    private final Student student;
    private final Course course;
    private final Lesson lesson;

    public QuizTaking(Frame owner, Student student, Course course, Lesson lesson) {
        super(owner, "Quiz - " + lesson.getTitle(), true);
        this.student = student;
        this.course = course;
        this.lesson = lesson;

        // check if already completed -> if yes, show message and close
        if (isLessonAlreadyCompleted()) {
            JOptionPane.showMessageDialog(owner, "You have already completed this lesson.", "Already Completed", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        setLayout(new BorderLayout());

        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setText("Quiz placeholder for lesson:\n\n" + lesson.getTitle() + "\n\n" +
                "This is a simple quiz dialog. Press 'Finish Quiz' to mark this lesson as completed.");
        add(new JScrollPane(ta), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton finish = new JButton("Finish Quiz");
        JButton cancel = new JButton("Cancel");
        bottom.add(finish);
        bottom.add(cancel);
        add(bottom, BorderLayout.SOUTH);

        finish.addActionListener((ActionEvent e) -> {
            try {
                markLessonCompleted();
                JOptionPane.showMessageDialog(this, "Quiz finished. Lesson marked completed.");
                dispose();
                checkAndShowCertificateIfComplete(owner);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error when finishing quiz: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancel.addActionListener((ActionEvent e) -> dispose());

        setSize(480, 320);
        setLocationRelativeTo(owner);
    }

    private boolean isLessonAlreadyCompleted() {
        for (Progress p : student.getProgressTrackers()) {
            if (p.getCourseId().equals(course.getID())) {
                for (Tracker t : p.getTrackers()) {
                    Lesson l = t.getLesson();
                    if (l != null && l.getLessonId().equals(lesson.getLessonId())) {
                        return t.getState();
                    }
                }
            }
        }
        return false;
    }

    private void markLessonCompleted() {
        Progress target = null;
        for (Progress p : student.getProgressTrackers()) {
            if (p.getCourseId().equals(course.getID())) {
                target = p;
                break;
            }
        }
        if (target == null) {
            Progress newP = new Progress(course.getID(), course.getLessons(), student.getID());
            student.getProgressTrackers().add(newP);
            target = newP;
        }

        // find tracker by lesson id (do not rely on object identity)
        Tracker found = null;
        for (Tracker t : target.getTrackers()) {
            Lesson l = t.getLesson();
            if (l != null && l.getLessonId().equals(lesson.getLessonId())) {
                found = t;
                break;
            }
        }

        if (found == null) {
            // If tracker not found, rebuild trackers to match current course lessons then find again
            target.updateTrackers(course.getLessons());
            for (Tracker t : target.getTrackers()) {
                Lesson l = t.getLesson();
                if (l != null && l.getLessonId().equals(lesson.getLessonId())) {
                    found = t;
                    break;
                }
            }
        }

        if (found == null) {
            throw new IllegalStateException("Could not find tracker for lesson " + lesson.getLessonId());
        }

        found.setState(true);

        UserDatabaseManager udb = new UserDatabaseManager("users.json");
        udb.updateRecord(student);
        udb.saveToFile();
    }

    private void checkAndShowCertificateIfComplete(Frame owner) {
        int total = course.getLessons().size();
        if (total == 0) return;

        int completed = 0;
        for (Progress p : student.getProgressTrackers()) {
            if (p.getCourseId().equals(course.getID())) {
                for (Tracker t : p.getTrackers()) {
                    if (t.getState()) completed++;
                }
                break;
            }
        }

        int percent = (int) ((completed * 100.0) / total);
        if (percent >= 100) {
            Certificate cert = new Certificate(owner, student, course);
            cert.setLocationRelativeTo(owner);
            cert.setVisible(true);
        }
    }
}
