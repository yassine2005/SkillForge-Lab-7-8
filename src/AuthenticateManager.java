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
        switch (role.toLowerCase()) {
            case "student":
                newUser = new Student(userId, role, username, email, hashedPassword);
                break;
            case "instructor":
                newUser = new Instructor(userId, role, username, email, hashedPassword);
                break;
            case "admin":
                newUser = new Admin(userId, role, username, email, hashedPassword);
                break;
            default:
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
        User raw = database.getRecordByUsername(username);
        if (raw == null) {
            return null;
        }
        String hashedPassword = PasswordHashing.hashPassword(password);
        if (!hashedPassword.equals(raw.getHashedPassword())) {
            return null;
        }
        return raw;
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
