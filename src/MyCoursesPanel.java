import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyCoursesPanel extends JPanel {
    private final Student student;

    public MyCoursesPanel(Student student) {
        this.student = student;
        setLayout(new BorderLayout());

        DefaultListModel<Course> model = new DefaultListModel<>();
        CourseDatabaseManager cdb = new CourseDatabaseManager("courses.json");
        for (String cid : student.getEnrolledCourseIds()) {
            Course c = cdb.getRecordByID(cid);
            if (c != null) model.addElement(c);
        }

        JList<Course> list = new JList<>(model);
        list.setCellRenderer((jl, course, index, isSelected, cellHasFocus) -> {
            int completed = 0;
            int total = course.getLessons().size();
            for (Progress p : student.getProgressTrackers()) {
                if (p.getCourseId().equals(course.getID())) {
                    for (Tracker t : p.getTrackers()) if (t.getState()) completed++;
                    break;
                }
            }
            int percent = total == 0 ? 0 : (completed * 100) / total;
            JLabel label = new JLabel(course.getID() + " | " + course.getTitle() + " | " + percent + "%");
            if (isSelected) {
                label.setOpaque(true);
                label.setBackground(Color.LIGHT_GRAY);
            }
            return label;
        });

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Course selected = list.getSelectedValue();
                    if (selected != null) {
                        JFrame f = new JFrame(selected.getTitle());
                        f.setSize(500,400);
                        f.setLocationRelativeTo(null);
                        f.setContentPane(new CourseLessonsPanel(student, selected));
                        f.setVisible(true);
                    }
                }
            }
        });

        add(new JScrollPane(list), BorderLayout.CENTER);
    }
}
