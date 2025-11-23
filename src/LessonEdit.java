import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LessonEdit extends JPanel {
    private JTextField Title;
    private JButton updateButton;
    private JTextField content;
    private JTextField ID;
    private JPanel edit;
    private JLabel lessonId;
    private JTextField resource;
    private final CourseDatabaseManager databaseManager;
    private String courseID;

    public LessonEdit(CourseDatabaseManager databaseManager, String CourseId) {
        this.courseID = CourseId;
        this.databaseManager = databaseManager;

        setLayout(new BorderLayout());
        add(edit, BorderLayout.CENTER);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = ID.getText().trim();

                Course course = databaseManager.getRecordByID(courseID);
                if (course == null) {
                    JOptionPane.showMessageDialog(LessonEdit.this,
                            "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Lesson lesson = course.getLessonById(id);
                if (lesson == null) {
                    JOptionPane.showMessageDialog(LessonEdit.this,
                            "Invalid lesson ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<String> resources = new ArrayList<>();
                String s = resource.getText();
                if (!s.isEmpty()) {
                    String[] parts = s.split(",");
                    for (String x : parts) {
                        resources.add(x.trim());
                    }
                }

                lesson.setResources(resources);
                lesson.setContent(content.getText());
                lesson.setTitle(Title.getText());

                databaseManager.updateRecord(course);
                databaseManager.saveToFile();

                JOptionPane.showMessageDialog(LessonEdit.this,
                        "Lesson edited successfully.");

                clearFields();
            }
        });
    }

    private void clearFields() {
        Title.setText("");
        content.setText("");
        ID.setText("");
        resource.setText("");
    }
}
