public class QuizResult {
        private final String studentId;
        private final String courseId;
        private final String lessonId;
        private final double scorePercentage;
        private final int totalQuestions;
        private final long timestamp;
        private final int attemptNumber;
        private final boolean state;

        // Parameterized Constructor
        public QuizResult(String studentId, String courseId, String lessonId, double scorePercentage, int totalQuestions, long timestamp, int attemptNumber, boolean state) {
                this.studentId = studentId;
                this.courseId = courseId;
                this.lessonId = lessonId;
                this.scorePercentage = scorePercentage;
                this.totalQuestions = totalQuestions;
                this.timestamp = timestamp;
                this.attemptNumber = attemptNumber;
                this.state = state;
        }

        // Getters
        public String getStudentId() {
                return studentId;
        }

        public String getCourseId() {
                return courseId;
        }

        public String getLessonId() {
                return lessonId;
        }

        public double getScorePercentage() {
                return scorePercentage;
        }

        public int getTotalQuestions() {
                return totalQuestions;
        }

        public long getTimestamp() {
                return timestamp;
        }

        public int getAttemptNumber() {
                return attemptNumber;
        }

        public boolean getState() {
                return state;
        }
}
