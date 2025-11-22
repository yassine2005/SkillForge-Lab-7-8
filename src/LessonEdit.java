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
    public LessonEdit(CourseDatabaseManager databaseManager,String CourseId){
        this.courseID = CourseId;
        this.databaseManager = databaseManager;
        setLayout(new BorderLayout());
        add(edit, BorderLayout.CENTER);


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = ID.getText().trim();
                Course course=databaseManager.getRecordByID(CourseId);
                Lesson lesson=course.getLessonById(id);
                String s =resource.getText();
                ArrayList<String> resources = new ArrayList<>();
                if (!s.isEmpty()) {
                    String[] parts = s.split(",");
                    for (String x : parts) {
                        resources.add(x.trim());    }
                }
                if(lesson!=null)  {
                    lesson.setResources(resources);
                    lesson.setContent(content.getText());
                    lesson.setTitle(Title.getText());
                    databaseManager.updateRecord(course);
                    databaseManager.saveToFile();
                    JOptionPane.showMessageDialog(LessonEdit.this,"Lesson edited successfully");
                } else {
                    JOptionPane.showMessageDialog(LessonEdit.this,"Invalid ID");
                }

            }
        });
    }


}
