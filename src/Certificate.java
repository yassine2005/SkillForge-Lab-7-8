import javax.swing.*;
import java.awt.*;

public class Certificate extends JDialog {

    public Certificate(Frame owner, Student student, Course course) {
        super(owner, "Certificate of Completion", true);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Certificate of Completion", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel line = new JLabel("______________________________", SwingConstants.CENTER);
        line.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel("This certifies that: " + student.getUsername());
        name.setFont(new Font("Serif", Font.PLAIN, 20));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel courseLabel = new JLabel("Has successfully completed the course:");
        courseLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel courseName = new JLabel(course.getTitle());
        courseName.setFont(new Font("Serif", Font.BOLD, 22));
        courseName.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(line);
        panel.add(Box.createVerticalStrut(20));
        panel.add(name);
        panel.add(Box.createVerticalStrut(15));
        panel.add(courseLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(courseName);

        add(panel, BorderLayout.CENTER);

        setSize(500, 350);
        setLocationRelativeTo(owner);
        setResizable(false);
    }
}
