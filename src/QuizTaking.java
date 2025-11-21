import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class QuizTaking extends JDialog {

    private final Student student;
    private final Course course;
    private final Lesson lesson;

    public QuizTaking(Frame owner, Student student, Course course, Lesson lesson) {
        super(owner, "Quiz - " + lesson.getTitle(), true);
        this.student = student;
        this.course = course;
        this.lesson = lesson;

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
            markLessonCompleted();
            JOptionPane.showMessageDialog(this, "Quiz finished. Lesson marked completed.");
            dispose();
        });

        cancel.addActionListener((ActionEvent e) -> dispose());

        setSize(480, 320);
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
        target.completeLesson(lesson);

        UserDatabaseManager udb = new UserDatabaseManager("users.json");
        udb.updateRecord(student);
        udb.saveToFile();
    }
}
