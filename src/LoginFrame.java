import javax.swing.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JPanel login;
    private JPasswordField passWord;
    private JTextField userName;
    private JButton loginButton;
    private JButton backButton;

    private final AuthenticateManager auth;

    public LoginFrame() {
        UserDatabaseManager database = new UserDatabaseManager("users.json");
        this.auth = new AuthenticateManager(database);

        setTitle("Login");
        setContentPane(login);
        setSize(280, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        // ENTER in password triggers login
        passWord.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) handleLogin();
            }
        });

        loginButton.addActionListener(e -> handleLogin());

        backButton.addActionListener(e -> {
            dispose();
            new EntryFrame();
        });

        userName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    passWord.requestFocus();
                }
            }
        });
    }

    private void handleLogin() {
        String userName = this.userName.getText().trim();
        String password = new String(passWord.getPassword());

        if (userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both username and password!",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User user = auth.login(userName, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this,
                        "Login successful!\nWelcome, " + user.getUsername(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                dispose();

                switch (user.getRole().toLowerCase()) {
                    case "student":
                        new StudentDashboard((Student) user);
                        break;
                    case "instructor":
                        new InstructorDashboard((Instructor) user, new CourseDatabaseManager("courses.json"));
                        break;
                    case "admin":
                        new AdminDashboard((Admin) user);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
                passWord.setText("");
                passWord.requestFocus();
            }

        } catch (Exception e) {
            e.printStackTrace(); // debug
            JOptionPane.showMessageDialog(this,
                    "An error occurred during login.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
