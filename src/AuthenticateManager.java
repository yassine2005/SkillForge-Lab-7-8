public class AuthenticateManager {
    private final UserDatabaseManager database;

    public AuthenticateManager(UserDatabaseManager database) {
        this.database = database;
    }

    public User signup(String username, String email, String password, String role) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("All fields are required!");
        }

        if (database.getRecordByEmail(email) != null) {
            throw new IllegalArgumentException("Email already registered!");
        }

        String hashedPassword = PasswordHashing.hashPassword(password);
        String userId = generatedID(role);

        User newUser;

        if (role.equalsIgnoreCase("student")) {
            newUser = new Student(userId, role, username, email, hashedPassword);
        }
        else if (role.equalsIgnoreCase("instructor")) {
            newUser = new Instructor(userId, role, username, email, hashedPassword);
        }
        else if (role.equalsIgnoreCase("admin")) {
            newUser = new Admin(userId, role, username, email, hashedPassword);
        }
        else {
            throw new IllegalArgumentException("Invalid role selected");
        }

        database.addRecord(newUser);
        database.saveToFile();
        return newUser;
    }

    public User login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }

        User user = database.getRecordByUsername(username);
        if (user == null) {
            return null;
        }

        String hashedPassword = PasswordHashing.hashPassword(password);
        if (!hashedPassword.equals(user.getHashedPassword())) {
            return null;
        }

        if (user.getRole().equalsIgnoreCase("student")) {
            return new Student(user.getID(), user.getRole(), user.getUsername(), user.getEmail(), user.getHashedPassword());
        }
        else if (user.getRole().equalsIgnoreCase("instructor")) {
            return new Instructor(user.getID(), user.getRole(), user.getUsername(), user.getEmail(), user.getHashedPassword());
        }
        else if (user.getRole().equalsIgnoreCase("admin")) {
            return new Admin(user.getID(), user.getRole(), user.getUsername(), user.getEmail(), user.getHashedPassword());
        }

        return null;
    }

    private String generatedID(String role) {
        String prefix;

        if (role.equalsIgnoreCase("student")) prefix = "S";
        else if (role.equalsIgnoreCase("instructor")) prefix = "I";
        else prefix = "A";

        int highest = getHighestID(role);
        return String.format("%s%04d", prefix, highest + 1);
    }

    private int getHighestID(String role) {
        int highest = 0;
        for (User user : database.getRecords()) {
            if (user.getRole().equalsIgnoreCase(role)) {
                try {
                    int id = Integer.parseInt(user.getID().substring(1));
                    if (id > highest) highest = id;
                } catch (Exception ignored) {}
            }
        }
        return highest;
    }
}
