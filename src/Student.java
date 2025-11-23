import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private final ArrayList<Progress> progressTrackers = new ArrayList<>();

    public Student(String uID, String role, String username, String email, String hashedPassword) {
        super(uID, role, username, email, hashedPassword);
    }


    @Override
    public void addCourse(Course course) {
        if (course == null) return;
        addCourseId(course.getID());
        progressTrackers.add(new Progress(course.getID(), course.getLessons(), userID));
    }

    @Override
    public void removeCourse(Course course) {
        if (course == null) return;
        removeCourseId(course.getID());
        for (int i = 0; i < progressTrackers.size(); i++) {
            Progress prog = progressTrackers.get(i);
            if (prog.getCourseId().equals(course.getID())) {
                progressTrackers.remove(i);
                break;
            }
        }
    }

    public void addQuizResult(String courseId, Lesson lesson, QuizResult result) {
        for (Progress progress : progressTrackers) {
            if (progress.getCourseId().equals(courseId)) {
                for (Tracker tracker : progress.getTrackers()) {
                    if (tracker.getLesson().equals(lesson)) {
                        tracker.addQuizResult(result);
                        return;
                    }
                }
            }
        }
    }

    public ArrayList<String> getEnrolledCourseIds() {
        return getCourses();
    }

    public boolean isEnrolledInCourse(String courseId) {
        return getCourses().contains(courseId);
    }

    public ArrayList<Progress> getProgressTrackers() {
        return progressTrackers;
    }

    public Map<String, List<QuizResult>> getCourseQuizesResults(String courseId) {
        for (Progress progress : progressTrackers) {
            if (progress.getCourseId().equals(courseId)) {
                return progress.getQuizesResults();
            }
        }
        return new HashMap<>();
    }
}
