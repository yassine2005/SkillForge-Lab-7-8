import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryFrame extends JFrame {
    private JPanel Entry = new JPanel();
    private JButton signUpButton = new JButton();
    private JButton loginButton = new JButton();
    private JLabel WelcomeMessage = new JLabel();

    public EntryFrame() {
        setTitle("Skill Forge - Welcome");
        setContentPane(Entry);
        setSize(180, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new LoginFrame();
               dispose();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUp();
                dispose();
            }
        });
    }

}
