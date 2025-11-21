import javax.swing.*;
import java.awt.*;

public class ViewUsersPanel extends JPanel {

    public ViewUsersPanel() {
        setLayout(new BorderLayout());

        UserDatabaseManager db = new UserDatabaseManager("users.json");

        DefaultListModel<User> model = new DefaultListModel<>();
        for (User u : db.getRecords()) model.addElement(u);

        JList<User> list = new JList<>(model);

        list.setCellRenderer((jl, user, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(
                    user.getID() + " | " +
                            user.getUsername() + " | " +
                            user.getEmail() + " | " +
                            user.getRole()
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
