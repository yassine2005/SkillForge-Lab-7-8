import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CourseLessonsPanel extends JPanel {

    private final Student student;
    private final Course course;
    private JList<Lesson> list;

    public CourseLessonsPanel(Student student, Course course) {
        this.student = student;
        this.course = course;

        setLayout(new BorderLayout());

        DefaultListModel<Lesson> model = new DefaultListModel<>();
        for (Lesson l : course.getLessons()) {
            model.addElement(l);
        }

        list = new JList<>(model);

        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus
            ) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                Lesson lesson = (Lesson) value;

                boolean completed = isLessonCompleted(lesson);

                String status = completed ? " ✔" : " ✖";
                label.setText(lesson.getTitle() + status);

                if (completed) {
                    label.setForeground(new Color(0, 135, 0));  // green
                }

                return label;
            }
        });


        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    Lesson lesson = list.getSelectedValue();
                    if (lesson == null) return;

                    JFrame f = new JFrame(lesson.getTitle());
                    f.setSize(520, 380);
                    f.setLocationRelativeTo(null);

                    f.setContentPane(new LessonViewer(student, course, lesson));

                    f.setVisible(true);

                    // when coming back, refresh display
                    refreshList();
                }
            }
        });

        add(new JScrollPane(list), BorderLayout.CENTER);
    }


    private boolean isLessonCompleted(Lesson lesson) {
        for (Progress p : student.getProgressTrackers()) {
            if (p.getCourseId().equals(course.getID())) {
                for (Tracker t : p.getTrackers()) {
                    if (t.getLesson().getLessonId().equals(lesson.getLessonId())) {
                        return t.getState();
                    }
                }
            }
        }
        return false;
    }


    public void refreshList() {
        list.repaint();
    }
}
