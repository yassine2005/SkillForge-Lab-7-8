import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LessonDelete extends JPanel {
    private JTextField DeleteID;
    private JButton confirmDeleteButton;
    private JPanel delete;
    private final CourseDatabaseManager databaseManager;
    private String courseID;

    public LessonDelete(CourseDatabaseManager databaseManager, String CourseId) {
        this.databaseManager = databaseManager;
        this.courseID = CourseId;

        setLayout(new BorderLayout());
        add(delete, BorderLayout.CENTER);

        confirmDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = DeleteID.getText().trim();
                Course course=databaseManager.getRecordByID(CourseId);
                Lesson lesson=course.getLessonById(id);
                if(lesson!=null)  {
                    course.deleteLesson(lesson);
                    databaseManager.updateRecord(course);
                    databaseManager.saveToFile();
                    JOptionPane.showMessageDialog(LessonDelete.this,"Lesson deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(LessonDelete.this,"Invalid ID");
                }
            }
        });
    }
}
