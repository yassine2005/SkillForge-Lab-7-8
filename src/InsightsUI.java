import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InsightsUI extends JPanel {
    private JPanel inputPanel;

    public InsightsUI(Instructor instructor) {
        // Set vertical layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton viewAnalyticsButton = new JButton("View Course Analytics");
        JButton viewStudentPerformance = new JButton("View Student Performance");

        buttonPanel.add(viewAnalyticsButton);
        buttonPanel.add(viewStudentPerformance);

        add(buttonPanel);

        // Create input panel (initially empty)
        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        add(inputPanel);

        viewAnalyticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear previous input panel
                inputPanel.removeAll();

                JLabel label = new JLabel("Course ID:");
                JTextField text = new JTextField(15);
                JButton button = new JButton("View Analytics");

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(text.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter a valid Course ID");
                            return;
                        }
                        new CourseAnalyticsGUI(text.getText());
                    }
                });

                inputPanel.add(label);
                inputPanel.add(text);
                inputPanel.add(button);
                inputPanel.revalidate();
                inputPanel.repaint();
            }
        });

        viewStudentPerformance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear previous input panel
                inputPanel.removeAll();

                JLabel courseLabel = new JLabel("Course ID:");
                JTextField courseText = new JTextField(15);
                JLabel studentLabel = new JLabel("Student ID:");
                JTextField studentText = new JTextField(15);
                JButton button = new JButton("View Analytics");

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(courseText.getText().isEmpty() || studentText.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter a valid Course ID");
                            return;
                        }
                        new StudentPerformanceGUI(courseText.getText(), studentText.getText());
                    }
                });

                inputPanel.add(courseLabel);
                inputPanel.add(courseText);
                inputPanel.add(studentLabel);
                inputPanel.add(studentText);
                inputPanel.add(button);
                inputPanel.revalidate();
                inputPanel.repaint();
            }
        });
    }
}
