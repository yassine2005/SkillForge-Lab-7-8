import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
                            course.getDescription() + " | Instructor: " +
                            course.getInstructorId() + " | Status: " +
                            course.getApprovalStatus()
            );

            if (isSelected) {
                label.setOpaque(true);
                label.setBackground(Color.LIGHT_GRAY);
            }
            return label;
        });

        JPopupMenu menu = new JPopupMenu();
        JMenuItem approve = new JMenuItem("Approve");
        JMenuItem reject = new JMenuItem("Reject");

        menu.add(approve);
        menu.add(reject);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = list.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        list.setSelectedIndex(index);
                        menu.show(list, e.getX(), e.getY());
                    }
                }
            }
        });

        approve.addActionListener(e -> {
            Course c = list.getSelectedValue();
            if (c != null) {
                c.setApprovalStatus("APPROVED");
                db.updateRecord(c);
                db.saveToFile();
                list.repaint();
            }
        });

        reject.addActionListener(e -> {
            Course c = list.getSelectedValue();
            if (c != null) {
                c.setApprovalStatus("REJECTED");
                db.updateRecord(c);
                db.saveToFile();
                list.repaint();
            }
        });

        add(new JScrollPane(list), BorderLayout.CENTER);
    }
}
