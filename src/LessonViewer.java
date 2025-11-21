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
            QuizTaking dialog = new QuizTaking(null, student, course, lesson);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        add(quizButton, BorderLayout.SOUTH);
    }
}
