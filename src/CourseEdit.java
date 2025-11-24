import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseEdit extends JPanel {
    private JTextField courseTitle;
    private JButton editSpecificLessonButton;
    private JButton updateButton;
    private JTextField description;
    private JTextField courseID;
    private JPanel edit;
    private JButton deleteLesson;
    private JButton addLesson;
    private final CourseDatabaseManager databaseManager;

    public CourseEdit(CourseDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;

        setLayout(new BorderLayout());
        add(edit, BorderLayout.CENTER);

        editSpecificLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = courseID.getText().trim();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(CourseEdit.this, "Enter Course ID");
                    return;
                }
                Course c = databaseManager.getRecordByID(id);
                if (c == null) {
                    JOptionPane.showMessageDialog(CourseEdit.this, "Course not found: " + id);
                    return;
                }
                LessonEdit ld = new LessonEdit(databaseManager, id);
                CourseEdit.this.removeAll();
                CourseEdit.this.setLayout(new BorderLayout());
                CourseEdit.this.add(ld, BorderLayout.CENTER);
                CourseEdit.this.revalidate();
                CourseEdit.this.repaint();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = courseID.getText().trim();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(CourseEdit.this, "Enter Course ID");
                    return;
                }
                Course c = databaseManager.getRecordByID(id);
                if (c == null) {
                    JOptionPane.showMessageDialog(CourseEdit.this, "Course not found: " + id);
                    return;
                }
                c.setTitle(courseTitle.getText());
                c.setDescription(description.getText());
                databaseManager.updateRecord(c);
                databaseManager.saveToFile();
                JOptionPane.showMessageDialog(CourseEdit.this, "Course edited successfully: " + c.getID());
            }
        });

        deleteLesson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = courseID.getText().trim();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(CourseEdit.this, "Enter Course ID");
                    return;
                }
                Course c = databaseManager.getRecordByID(id);
                if (c == null) {
                    JOptionPane.showMessageDialog(CourseEdit.this, "Course not found: " + id);
                    return;
                }
                LessonDelete ld = new LessonDelete(databaseManager, id);
                CourseEdit.this.removeAll();
                CourseEdit.this.setLayout(new BorderLayout());
                CourseEdit.this.add(ld, BorderLayout.CENTER);
                CourseEdit.this.revalidate();
                CourseEdit.this.repaint();
            }
        });
    }
}
