import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class StudentCoursePerformanceAnalytics {
    private final String studentId;
    private final String courseId;
    private final Map<String, List<QuizResult>> lessonResultsHistory; // lessonId -> QuizResults
    private int completedLessonsCount;
    private double averageLessonsScore; // average best score across all completed lessons

    // Constructor
    public StudentCoursePerformanceAnalytics(String studentId, String courseId, Map<String, List<QuizResult>> resultsHistory) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.lessonResultsHistory = resultsHistory != null ? resultsHistory : new HashMap<>();

        calculateCompletedLessons();
        calculateAverageLessonsScore();
    }

    private void calculateCompletedLessons() {
        this.completedLessonsCount = 0;
        for (List<QuizResult> quizResults : this.lessonResultsHistory.values()) {
            for (QuizResult result : quizResults) {
                if (result.getState()) {
                    this.completedLessonsCount++;
                    break;
                }
            }
        }
    }

    private void calculateAverageLessonsScore() {
        int totalLessons = this.lessonResultsHistory.size();
        double totalScorePercentage = 0.0;
        for (Map.Entry<String, List<QuizResult>> entry : this.lessonResultsHistory.entrySet()) {
            if (entry.getValue() != null) {
                String lessonId = entry.getKey();
                List<QuizResult> quizResults = entry.getValue();

                // Get best score for the lesson
                double bestScorePercentage = 0.0;
                for (QuizResult result : quizResults) {
                    if (result.getScorePercentage() > bestScorePercentage)
                        bestScorePercentage = result.getScorePercentage();
                }
                totalScorePercentage += bestScorePercentage;
            }
        }
        this.averageLessonsScore = totalLessons > 0 ? totalScorePercentage / totalLessons / 100 : 0.0;
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public Map<String, List<QuizResult>> getLessonsResultsHistory() {
        return lessonResultsHistory;
    }

    public int getCompletedLessonsCount() { return completedLessonsCount; }

    public double getAverageScore() { return averageLessonsScore; }
    public double getCompletionRate() {
        int totalLessons = this.lessonResultsHistory.size();
        return totalLessons > 0 ? (double) this.completedLessonsCount / totalLessons : 0.0;
    }
    public double getQuizAttemptRate() {
        int totalLessons = this.lessonResultsHistory.size();
        int quizAttempts = 0;
        for (List<QuizResult> quizResults : this.lessonResultsHistory.values()) {
            quizAttempts += quizResults.size();
        }
        return totalLessons > 0 ? (double) quizAttempts / totalLessons : 0.0;
    }

    public boolean isLessonCompleted(String lessonId) {
        List<QuizResult> quizResults = this.lessonResultsHistory.get(lessonId);
        if (quizResults != null) {
            for (QuizResult result : quizResults) {
                if (result.getState()) {
                    return true;
                }
            }
        }
        return false;
    }
    public int getLessonAttemptCount(String lessonId) {
        List<QuizResult> quizResults = this.lessonResultsHistory.get(lessonId);
        return quizResults != null ? quizResults.size() : 0;
    }
    public double getLessonBestScorePercentage(String lessonId) {
        List<QuizResult> quizResults = this.lessonResultsHistory.get(lessonId);
        double bestScorePercentage = 0.0;
        if (quizResults != null) {
            for (QuizResult result : quizResults) {
                if (result.getScorePercentage() > bestScorePercentage)
                    bestScorePercentage = result.getScorePercentage();
            }
        }
        return bestScorePercentage;
    }
}