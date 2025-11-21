import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame {
    private JPanel SignUp;
    private JPasswordField ConfirmedPass;
    private JTextField userName;
    private JTextField Email;
    private JPasswordField Password;
    private JComboBox<String> role;
    private JButton backButton;
    private JButton signUpButton;

    private final AuthenticateManager authManager;

    public SignUp() {
        UserDatabaseManager database = new UserDatabaseManager("users.json");
        this.authManager = new AuthenticateManager(database);
        setTitle("SignUp");
        setContentPane(SignUp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,250);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        role.removeAllItems();
        role.addItem("");             // EMPTY first option
        role.addItem("Student");
        role.addItem("Instructor");
        role.addItem("Admin");

        userName.addActionListener(e -> {});
        Email.addActionListener(e -> {});
        role.addActionListener(e -> {});
        ConfirmedPass.addActionListener(e -> {});
        Password.addActionListener(e -> {});

        backButton.addActionListener(e -> {
            dispose();
            new EntryFrame();
        });

        signUpButton.addActionListener(e -> {
            handleSignUp();
        });
    }

    private void handleSignUp() {
        String username = userName.getText();
        String email = Email.getText();
        String password = new String(Password.getPassword());
        String confirmedPass = new String(ConfirmedPass.getPassword());
        String userRole = (String) role.getSelectedItem();

        if (userRole == null || userRole.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please choose a role!", "Invalid Role", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!ValidationResult.isValidUsername(username)) {
            JOptionPane.showMessageDialog(this, ValidationResult.getUsernameError(), "Invalid Username", JOptionPane.ERROR_MESSAGE);
            userName.selectAll();
            userName.requestFocus();
            return;
        }

        if (!ValidationResult.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, ValidationResult.getEmailError(), "Invalid Email", JOptionPane.ERROR_MESSAGE);
            Email.selectAll();
            Email.requestFocus();
            return;
        }

        if (!ValidationResult.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, ValidationResult.getPasswordError(), "Invalid Password", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmedPass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            ConfirmedPass.setText("");
            Password.setText("");
            Password.requestFocus();
            return;
        }

        try {
            User newUser = authManager.signup(username, email, password, userRole);
            JOptionPane.showMessageDialog(this, "Account created successfully!\nWelcome, " + newUser.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginFrame();
        } catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Signup Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
