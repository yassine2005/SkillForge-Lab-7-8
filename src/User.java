import java.util.ArrayList;

public class User implements Record {
    protected String username;
    protected String role;
    protected String userID;
    protected String email;
    protected String hashedPassword;


    protected ArrayList<String> courses = new ArrayList<>();

    public User(String uID, String role, String username, String email, String hashedPassword) {
        this.username = username;
        this.role = role;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.userID = uID;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userID + '\'' +
                ",role='" + role + '\'' +
                ",username='" + username + '\'' +
                ",email='" + email + '\'' +
                '}';
    }

    @Override
    public String getID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        if (course == null) return;
        String id = course.getID();
        if (!courses.contains(id)) courses.add(id);
    }

    public void addCourseId(String id) {
        if (id == null) return;
        if (!courses.contains(id)) courses.add(id);
    }

    public void removeCourse(Course course){
        if (course == null) return;
        courses.remove(course.getID());
    }

    public void removeCourseId(String id) {
        courses.remove(id);
    }
}
