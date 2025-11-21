import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends DashBoard {

    private final Admin admin;

    public AdminDashboard(Admin admin) {
        super(admin);
        this.admin = admin;

        navButtons.setLayout(new GridLayout(1, 3, 10, 10));

        JButton usersButton = new JButton("Users");
        usersButton.setBackground(Color.LIGHT_GRAY);
        usersButton.addActionListener(e -> changeContentPanel(new ViewUsersPanel()));
        navButtons.add(usersButton);

        JButton coursesButton = new JButton("Courses");
        coursesButton.setBackground(Color.LIGHT_GRAY);
        coursesButton.addActionListener(e -> changeContentPanel(new ViewCoursesPanel()));
        navButtons.add(coursesButton);

        JButton statsButton = new JButton("Stats");
        statsButton.setBackground(Color.LIGHT_GRAY);
        statsButton.addActionListener(e -> changeContentPanel(new AdminStatsPanel()));
        navButtons.add(statsButton);
    }
}
