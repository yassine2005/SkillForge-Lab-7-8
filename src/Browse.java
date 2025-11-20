import javax.swing.*;
import java.awt.*;

public class Browse extends JPanel {

    public Browse() {

        CourseDatabaseManager db = new CourseDatabaseManager("courses.json");

        setLayout(new BorderLayout());

        JLabel title = new JLabel("All Courses");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();


        for (int i = 0; i < db.getRecords().size(); i++) {
            Course c = db.getRecords().get(i);
            model.addElement(c.getID() + " | " + c.getDescription());
        }

        JList<String> list = new JList<>(model);
        JScrollPane scroll = new JScrollPane(list);

        add(scroll, BorderLayout.CENTER);
    }
}
