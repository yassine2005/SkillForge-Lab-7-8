import javax.swing.*;

public class Enrol {

    public static void enroll(Student student, Course course) {

        if (student == null || course == null) {
            JOptionPane.showMessageDialog(null, "Error: Student or Course is null.");
            return;
        }

        // IMPORTANT — always check using student.getCourses()
        if (student.getCourses().contains(course.getID())) {
            JOptionPane.showMessageDialog(null, "You are already enrolled in this course!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Enroll in: " + course.getTitle() + "?",
                "Confirm Enrollment",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Add course to student memory object
        student.addCourse(course);

        // Add student to course memory object
        course.enrollStudent(student);

        // Save to DB
        CourseDatabaseManager courseDB = new CourseDatabaseManager("courses.json");
        UserDatabaseManager userDB = new UserDatabaseManager("users.json");

        courseDB.updateRecord(course);
        courseDB.saveToFile();

        userDB.updateRecord(student);
        userDB.saveToFile();

        // *** CRITICAL PART ***
        // Reload student so UI gets updated data
        Student refreshed = (Student) userDB.getRecordByID(student.getID());

        // Copy new data into original student object
        if (refreshed != null) {
            student.setCourses(refreshed.getCourses());
        }

        JOptionPane.showMessageDialog(null, "Enrolled successfully!");
    }
}
