import javax.swing.*;
import java.awt.*;

public class AdminStatsPanel extends JPanel {

    public AdminStatsPanel() {
        setLayout(new GridLayout(3,1));

        UserDatabaseManager udb = new UserDatabaseManager("users.json");
        CourseDatabaseManager cdb = new CourseDatabaseManager("courses.json");

        int users = udb.getRecords().size();
        int courses = cdb.getRecords().size();

        add(new JLabel("Total Users: " + users));
        add(new JLabel("Total Courses: " + courses));
        add(new JLabel("Admin Panel Ready"));
    }
}
