import javax.swing.*;
import java.awt.*;

public class Browse extends JPanel {

    private JList<Course> list;

    public Browse() {

        CourseDatabaseManager db = new CourseDatabaseManager("courses.json");

        setLayout(new BorderLayout());

        JLabel title = new JLabel("All Courses");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        DefaultListModel<Course> model = new DefaultListModel<>();

        for (int i = 0; i < db.getRecords().size(); i++) {
            model.addElement(db.getRecords().get(i));
        }

        list = new JList<>(model);
        list.setCellRenderer((jl, c, idx, sel, focus) -> {
            JLabel l = new JLabel(c.getID() + " | " + c.getTitle() + " | " + c.getDescription());
            if (sel) {
                l.setOpaque(true);
                l.setBackground(Color.LIGHT_GRAY);
            }
            return l;
        });

        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public Course getSelectedCourse() {
        return list.getSelectedValue();
    }
}
