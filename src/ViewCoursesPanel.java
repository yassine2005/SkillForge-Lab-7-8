import javax.swing.*;
import java.awt.*;

public class ViewCoursesPanel extends JPanel {

    public ViewCoursesPanel() {
        setLayout(new BorderLayout());

        CourseDatabaseManager db = new CourseDatabaseManager("courses.json");

        DefaultListModel<Course> model = new DefaultListModel<>();
        for (Course c : db.getRecords()) model.addElement(c);

        JList<Course> list = new JList<>(model);

        list.setCellRenderer((jList, course, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(
                    course.getID() + " | " +
                            course.getTitle() + " | " +
                            course.getInstructorId()
            );
            if (isSelected) {
                label.setOpaque(true);
                label.setBackground(Color.LIGHT_GRAY);
            }
            return label;
        });

        add(new JScrollPane(list), BorderLayout.CENTER);
    }
}
