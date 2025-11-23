import javax.swing.*;
import java.awt.*;

public class LessonViewer extends JPanel {

    private final Student student;
    private final Course course;
    private final Lesson lesson;

    public LessonViewer(Student student, Course course, Lesson lesson) {
        this.student = student;
        this.course = course;
        this.lesson = lesson;

        setLayout(new BorderLayout());

        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setText(lesson.getContent());

        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        JButton quizButton = new JButton("Take Quiz");
        quizButton.addActionListener(e -> {
            boolean completed = isLessonCompleted();
            if (completed) {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "You have already completed this lesson (quiz taken).",
                        "Quiz Taken",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            Window win = SwingUtilities.getWindowAncestor(this);
            Frame owner = (win instanceof Frame) ? (Frame) win : null;

            QuizTaking dialog = new QuizTaking(owner, student, course, lesson);
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);



            revalidate();
            repaint();
        });

        add(quizButton, BorderLayout.SOUTH);
    }

    private boolean isLessonCompleted() {
        for (Progress p : student.getProgressTrackers()) {
            if (p.getCourseId().equals(course.getID())) {
                for (Tracker t : p.getTrackers()) {
                    if (t.getLesson() != null && t.getLesson().getLessonId().equals(lesson.getLessonId())) {
                        return t.getState();
                    }
                }
            }
        }
        return false;
    }
}
