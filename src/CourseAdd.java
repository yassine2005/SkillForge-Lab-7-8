import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseAdd extends JPanel {
    private JButton ADDButton;
    private JTextField description;
    private JPanel add;
    private JTextField CourseName;
    private final String instructorId;
    private final CourseDatabaseManager databaseManager;

    public CourseAdd(CourseDatabaseManager databaseManager, String instructorId) {
        this.databaseManager = databaseManager;
        this.instructorId = instructorId;

        setLayout(new BorderLayout());
        add(add, BorderLayout.CENTER);

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddCourse();
            }
        });
    }

    private void handleAddCourse() {
        String courseName = CourseName.getText().trim();
        String courseDescription = description.getText().trim();

        if (courseName.isEmpty() || courseDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid course name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String courseID = generateCourseID();
            Course newCourse = new Course(courseID, courseName, courseDescription, instructorId);
            databaseManager.addRecord(newCourse);
            databaseManager.saveToFile();


            UserDatabaseManager userDB = new UserDatabaseManager("users.json");
            User u = userDB.getRecordByID(instructorId);
            if (u instanceof Instructor) {
                Instructor instr = (Instructor) u;
                instr.addCourse(newCourse); // keep existing API: add Course object
                userDB.updateRecord(instr);
                userDB.saveToFile();
            }

            int choice = JOptionPane.showConfirmDialog(this,
                    "Course added successfully: " + courseID + ".\nDo you want to add lessons to this course now?",
                    "Course Added",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                // open LessonAdd for this course
                changeToLessonAdd(newCourse.getID());
                databaseManager.saveToFile();
            } else {
                JOptionPane.showMessageDialog(this, "Course created: " + courseID, "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            CourseName.setText("");
            CourseName.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding course!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void changeToLessonAdd(String courseId) {
        // Try to find parent dashboard and change its content panel
        // Walk up: this panel is used inside InstructorDashboard via changeContentPanel
        // Simpler approach: replace current panel contents with LessonAdd
        LessonAdd lessonAdd = new LessonAdd(databaseManager, courseId);
        removeAll();
        setLayout(new BorderLayout());
        add(lessonAdd, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private String generateCourseID() {
        int highest = getHighestID();
        return String.format("C%04d", highest + 1);
    }

    private int getHighestID() {
        int highest = 0;
        for (Course course : databaseManager.getRecords()) {
            String courseId = course.getID();
            try {
                String numberPart = courseId.substring(1);
                int idNumber = Integer.parseInt(numberPart);
                if (idNumber > highest) {
                    highest = idNumber;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return highest;
    }
}
