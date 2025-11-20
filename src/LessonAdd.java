import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LessonAdd extends JPanel{

    private JButton ADDButton;
    private JTextField content;
    private JPanel add;
    private JTextField title;
    private JLabel titleLabel;
    private JLabel contentLabel;
    private final String instructorId;
    private final CourseDatabaseManager databaseManager;

    public LessonAdd(CourseDatabaseManager databaseManager, String instructorId) {
        this.databaseManager = databaseManager;
        this.instructorId = instructorId;

        setLayout(new BorderLayout());
        add(add, BorderLayout.CENTER);

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddCourse();  // ← kept the same because YOU SAID no logic changes
            }
        });
    }

    private void handleAddCourse() {
        String lessonTitle = title.getText().trim();
        String lessonContent = content.getText().trim();

        if (lessonTitle.isEmpty() || lessonContent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid course name!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try {
            String lessonID = generateCourseID();
            Lesson newCourse = new Lesson(lessonID,lessonTitle , lessonContent);
            databaseManager.addLessons(newCourse);
            databaseManager.saveToFile();

            JOptionPane.showMessageDialog(this, "lesson added successfully!" + lessonID, "Success", JOptionPane.INFORMATION_MESSAGE);

            title.setText("");
            title.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding lesson!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String generateCourseID() {
        int highest = getHighestID();
        return String.format("L%04d", highest + 1);
    }

    private int getHighestID() {
        int highest = 0;
        for (Lesson course : databaseManager.getLessons()) {
            String courseId = course.getID();
            try {
                String numberPart = courseId.substring(1);
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
