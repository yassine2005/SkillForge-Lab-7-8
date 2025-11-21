import javax.swing.*;

public class Enrol {

    public static void enroll(Student student, Course course) {

        if (student == null || course == null) {
            JOptionPane.showMessageDialog(null, "Error: Student or Course is null.");
            return;
        }

        if (student.getEnrolledCourseIds().contains(course.getID())) {
            JOptionPane.showMessageDialog(null, "You are already enrolled in this course.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Do you want to enroll in: " + course.getTitle() + "?",
                "Confirm Enrollment",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Enrollment cancelled.");
            return;
        }


        student.addCourse(course);


        course.enrollStudent(student);

        CourseDatabaseManager courseDB = new CourseDatabaseManager("courses.json");
        UserDatabaseManager userDB = new UserDatabaseManager("users.json");

        courseDB.updateRecord(course);
        courseDB.saveToFile();

        userDB.updateRecord(student);
        userDB.saveToFile();

        JOptionPane.showMessageDialog(null, "Enrolled Successfully!");
    }
}
