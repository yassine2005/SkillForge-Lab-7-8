import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseDelete extends JPanel {
    private JTextField DeleteID;
    private JButton confirmDeleteButton;
    private JPanel delete;
    private final CourseDatabaseManager databaseManager;
    private final JFrame parentFrame;

    public CourseDelete(CourseDatabaseManager databaseManager, JFrame parentFrame) {
        this.databaseManager = databaseManager;
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout());
        add(delete, BorderLayout.CENTER);

        confirmDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = DeleteID.getText().trim();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "Please enter a Course ID", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Course c = databaseManager.getRecordByID(id);
                if (c == null) {
                    JOptionPane.showMessageDialog(parentFrame, "Course not found", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(parentFrame,
                        "Delete course \"" + c.getTitle() + "\" (" + id + ")? This will remove it from all users.",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                boolean ok = databaseManager.deleteCourseAndCleanup(id);
                if (ok) {
                    JOptionPane.showMessageDialog(parentFrame, "Course deleted and cleaned up successfully.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Failed to delete course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
