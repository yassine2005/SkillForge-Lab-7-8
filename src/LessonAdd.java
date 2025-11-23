import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LessonAdd extends JPanel{

    private JButton ADDButton;
    private JTextField content;
    private JPanel add;
    private JTextField title;
    private JLabel titleLabel;
    private JLabel contentLabel;
    private JTextField resource;
    private JLabel resourcesLabel;
    private final String courseId;
    private final CourseDatabaseManager databaseManager;

    public LessonAdd(CourseDatabaseManager databaseManager, String courseId) {
        this.databaseManager = databaseManager;
        this.courseId = courseId;

        setLayout(new BorderLayout());
        add(add, BorderLayout.CENTER);

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddLesson();  // ← kept the same because YOU SAID no logic changes
            }
        });
    }

    private void handleAddLesson() {
        String lessonTitle = title.getText().trim();
        String lessonContent = content.getText().trim();
        String s =resource.getText();
        ArrayList<String> resources = new ArrayList<>();
          Course course=databaseManager.getRecordByID(courseId);
        if (!s.isEmpty()) {
            String[] parts = s.split(",");
            for (String x : parts) {
                resources.add(x.trim());    }
            }
        if (lessonTitle.isEmpty() || lessonContent.isEmpty()||resources.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid course name!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
        }

        try {
            String lessonID = generateLessonID();
            Lesson newLesson = new Lesson(lessonID,lessonTitle , lessonContent);
            newLesson.setResources(resources);
            changeToQuizAdd(newLesson);
            course.addLesson(newLesson);
            databaseManager.saveToFile();

            JOptionPane.showMessageDialog(this, "lesson added successfully!" + lessonID, "Success", JOptionPane.INFORMATION_MESSAGE);

            title.setText("");
            content.setText("");
            resource.setText("");
            title.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding lesson!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void changeToQuizAdd(Lesson newLesson) {
        QuizAdd Add = new QuizAdd(databaseManager, courseId,newLesson);
        removeAll();
        setLayout(new BorderLayout());
        add(Add, BorderLayout.CENTER);
        revalidate();
        repaint();

<<<<<<< HEAD

=======
>>>>>>> 250e5ed (added add quiz)
    }


    private String generateLessonID() {
        int highest = getHighestID();
        return String.format("L%04d", highest + 1);
    }

    private int getHighestID() {
        Course c=databaseManager.getRecordByID(courseId);
        int highest = 0;
        for (Lesson lesson : c.getLessons()) {
            String lessonId = lesson.getID();
            try {
                String numberPart = lessonId.substring(1);
                int idNumber = Integer.parseInt(numberPart);
                if (idNumber > highest) {
                    highest = idNumber;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return highest;
    }
}
