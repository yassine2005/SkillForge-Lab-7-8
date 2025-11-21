import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends DashBoard {

    private final Student student;
    private Browse browsePanel;

    public StudentDashboard(Student student) {
        super(student);
        this.student = student;

        navButtons.setLayout(new GridLayout(1, 2, 10, 10));

        JButton viewButton = new JButton("View");
        viewButton.setBackground(Color.LIGHT_GRAY);
        viewButton.addActionListener(e -> {
            browsePanel = new Browse();
            changeContentPanel(browsePanel);
        });
        navButtons.add(viewButton);

        JButton enrollButton = new JButton("Enroll");
        enrollButton.setBackground(Color.LIGHT_GRAY);
        enrollButton.addActionListener(e -> handleEnroll());
        navButtons.add(enrollButton);
    }

    private void handleEnroll() {
        if (browsePanel == null) {
            JOptionPane.showMessageDialog(this, "Open View first!");
            return;
        }

        Course selected = browsePanel.getSelectedCourse();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a course first!");
            return;
        }

        Enrol.enroll(student, selected);
    }
}
