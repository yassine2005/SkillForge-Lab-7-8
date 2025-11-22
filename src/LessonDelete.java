import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LessonDelete extends JPanel {
    private JTextField DeleteID;
    private JButton confirmDeleteButton;
    private JPanel delete;
    private final CourseDatabaseManager databaseManager;

    public LessonDelete(CourseDatabaseManager databaseManager, InstructorDashboard dashboard) {
        this.databaseManager = databaseManager;

        setLayout(new BorderLayout());
        add(delete, BorderLayout.CENTER);

        confirmDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = DeleteID.getText().trim();
                if(databaseManager.findRecord(id))  {
                    databaseManager.deleteCourse(id);
                    databaseManager.saveToFile();

                } else {
                    JOptionPane.showMessageDialog(dashboard,"Invalid ID");
                }
            }
        });
    }
}
