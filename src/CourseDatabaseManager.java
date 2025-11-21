import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class CourseDatabaseManager extends JsonDatabaseManager<Course> {
    Type listType = null;
    protected ArrayList<Lesson> less = new ArrayList<>();
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
          course.setApprovalStatus("Approved");
          updateRecord(course);
      }

    public void rejectCourses(String id) {
        Course course = getRecordByID(id);
        course.setApprovalStatus("Rejected") ;
        updateRecord(course);
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

