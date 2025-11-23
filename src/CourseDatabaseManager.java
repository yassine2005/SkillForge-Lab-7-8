import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class CourseDatabaseManager extends JsonDatabaseManager<Course> {
    Type listType = null;

    public CourseDatabaseManager(String filename) {
        super(filename);
    }

    public List<Course> getPendingCourses() {
        List<Course> pending = new ArrayList<>();
        for (Course course : records) {
            if (course.isPending()) {
                pending.add(course);
            }
        }
        return pending;
    }

    public List<Course> getApprovedCourses() {
        List<Course> approved = new ArrayList<>();
        for (Course course : records) {
            if (course.isApproved()) {
                approved.add(course);
            }
        }
        return approved;
    }

    public void approveCourses(String id) {
        Course course = getRecordByID(id);
        if (course == null) return;
        course.setApprovalStatus("Approved");
        updateRecord(course);
        saveToFile();
    }

    public void rejectCourses(String id) {
        Course course = getRecordByID(id);
        if (course == null) return;
        course.setApprovalStatus("Rejected");
        updateRecord(course);
        saveToFile();
    }

    public boolean deleteCourseAndCleanup(String courseId) {
        if (courseId == null) return false;
        Course toRemove = getRecordByID(courseId);
        if (toRemove == null) return false;

        UserDatabaseManager userDB = new UserDatabaseManager("users.json");

        // Remove course id from all users and remove progress for students
        ArrayList<User> allUsers = userDB.getRecords();
        for (int i = 0; i < allUsers.size(); i++) {
            User u = allUsers.get(i);
            if (u == null) continue;

            if (u.getCourses() != null && u.getCourses().contains(courseId)) {
                u.removeCourseId(courseId);
            }

            if (u instanceof Student) {
                Student s = (Student) u;
                for (int p = s.getProgressTrackers().size() - 1; p >= 0; p--) {
                    Progress prog = s.getProgressTrackers().get(p);
                    if (prog.getCourseId().equals(courseId)) {
                        s.getProgressTrackers().remove(p);
                    }
                }
            }
            userDB.updateRecord(u);
        }

        userDB.saveToFile();

        // Remove course from this database records
        deleteCourse(courseId);
        saveToFile();

        return true;
    }

    @Override
    public void readFromFile() {
        records.clear();
        File file = new File(filename);

        if (!file.exists() || file.length() == 0) {
            return;
        }

        if (listType == null) {
            listType = new TypeToken<ArrayList<Course>>() {
            }.getType();
        }

        try (FileReader reader = new FileReader(filename)) {
            ArrayList<Course> list = gson.fromJson(reader, listType);
            if (list != null) {
                records = list;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
