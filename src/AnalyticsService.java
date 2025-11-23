import java.util.*;

public class AnalyticsService {
    private UserDatabaseManager uDb = new UserDatabaseManager("users.json");
    private CourseDatabaseManager cDb = new CourseDatabaseManager("courses.json");


    public StudentCoursePerformanceAnalytics getStudentCoursePerformance(String courseId, String studentId) {
        Course course = cDb.getRecordByID(courseId);
        Student student = (Student) uDb.getRecordByID(studentId);
        if(course == null || student == null) {
            throw new RuntimeException();
        }

        return new StudentCoursePerformanceAnalytics(student.getID(), course.getID(), student.getCourseQuizesResults(courseId));
    }

    public CourseAnalytics getCourseAnalytics(String courseId) {
        Course course = cDb.getRecordByID(courseId);
        List<Student> students = uDb.getAllStudents();
        if(course == null) {
            throw new RuntimeException();
        }
        List<StudentCoursePerformanceAnalytics> studentsPerformances = new ArrayList<>();
        for (Student student : students) {
            if(student.isEnrolledInCourse(courseId)) {
                studentsPerformances.add(getStudentCoursePerformance(courseId, student.getID()));
            }
        }

        return new CourseAnalytics(courseId, studentsPerformances);
    }

}
