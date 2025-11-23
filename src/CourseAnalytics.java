import java.util.*;

public class CourseAnalytics {
    private final String courseId;
    private final List<StudentCoursePerformanceAnalytics> studentPerformances;
    private double courseCompletionRate;
    private Map<String, Double> lessonAverageScoresPercentages = new HashMap<>(); // lessonId -> average score percentage
    private Map<String, Double> lessonCompletionRate = new HashMap<>(); // lessonId -> completion rate
    private Map<String, Double> lessonQuizAttemptRate = new HashMap<>(); // lessonId -> quiz attempt rate

    // Constructor - takes only final fields, other fields will be calculated
    public CourseAnalytics(String courseId, List<StudentCoursePerformanceAnalytics> studentPerformances) {
        this.courseId = courseId;
        this.studentPerformances = studentPerformances;

        calculateCourseCompletionRate();
        calculateLessonCompletionRate();
        calculateLessonQuizAttemptRate();
        calculateLessonAverageScoresPercentages();
    }

    private void calculateCourseCompletionRate() {
        //check if the student has completed all lessons

        int totalStudents = studentPerformances.size();
        if (totalStudents == 0) {
            this.courseCompletionRate = 0.0;
            return;
        }

        int studentsCompletedCourse = 0;
        int totalLessons = studentPerformances.get(0).getLessonsResultsHistory().size();
        for (StudentCoursePerformanceAnalytics performance : studentPerformances) {
            studentsCompletedCourse = performance.getCompletedLessonsCount();
            if(studentsCompletedCourse == totalLessons) {
                studentsCompletedCourse++;
            }
        }
        this.courseCompletionRate = (double) studentsCompletedCourse / totalStudents * 100;
    }

    private void calculateLessonCompletionRate() {
        int totalStudents = studentPerformances.size();
        if (totalStudents == 0) {
            this.lessonCompletionRate = new HashMap<>();
            return;
        }

        Set<String> lessonIds= this.studentPerformances.get(0).getLessonsResultsHistory().keySet();
        for (String lessonId : lessonIds) {
            int studentsCompletedCurrentLesson = 0;
            for (StudentCoursePerformanceAnalytics performance : studentPerformances) {
                if(performance.isLessonCompleted(lessonId))
                    studentsCompletedCurrentLesson++;
            }
            double completionRate = (double) studentsCompletedCurrentLesson / totalStudents * 100;
            this.lessonCompletionRate.put(lessonId, completionRate);
        }
    }

    private void calculateLessonQuizAttemptRate() {
        int totalStudents = studentPerformances.size();
        if (totalStudents == 0) {
            this.lessonQuizAttemptRate = new HashMap<>();
            return;
        }

        Set<String> lessonIds= this.studentPerformances.get(0).getLessonsResultsHistory().keySet();
        for (String lessonId : lessonIds) {
            int totalAttemptsPerLesson = 0;
            for (StudentCoursePerformanceAnalytics performance : studentPerformances) {
                int attemptsCount = performance.getLessonAttemptCount(lessonId);
                totalAttemptsPerLesson += attemptsCount;
            }
            double attemptRate = (double) totalAttemptsPerLesson / totalStudents * 100;
            this.lessonQuizAttemptRate.put(lessonId, attemptRate);
        }
    }

    private void calculateLessonAverageScoresPercentages() {
        int totalStudents = studentPerformances.size();
        if (totalStudents == 0) {
            this.lessonQuizAttemptRate = new HashMap<>();
            return;
        }

        Set<String> lessonIds= this.studentPerformances.get(0).getLessonsResultsHistory().keySet();
        for (String lessonId : lessonIds) {
            double lessonTotalScoresPercentages = 0.0;
            for (StudentCoursePerformanceAnalytics performance : studentPerformances) {
                double lessonScoresPercentages = performance.getLessonBestScorePercentage(lessonId);
                lessonTotalScoresPercentages += lessonScoresPercentages;
            }
            double lessonScorePercentageRate = (double) lessonTotalScoresPercentages / totalStudents * 100;
            this.lessonQuizAttemptRate.put(lessonId, lessonScorePercentageRate);
        }
    }

    // Getters
    public String getCourseId() {
        return courseId;
    }

    public List<StudentCoursePerformanceAnalytics> getStudentPerformances() {
        return studentPerformances;
    }

    public List<String> getLessonIds() {
        if (studentPerformances.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<String>(lessonAverageScoresPercentages.keySet());
    }

    public double getCourseCompletionRate() {
        return courseCompletionRate;
    }

    public Map<String, Double> getLessonAverageScoresPercentages() {
        return lessonAverageScoresPercentages;
    }

    public Map<String, Double> getLessonCompletionRate() {
        return lessonCompletionRate;
    }

    public Map<String, Double> getLessonQuizAttemptRate() {
        return lessonQuizAttemptRate;
    }
}
