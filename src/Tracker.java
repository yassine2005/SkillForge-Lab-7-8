import java.util.ArrayList;
import java.util.List;

public class Tracker {
    private final Lesson lesson;
    private boolean state;
    private List<QuizResult> quizHistory;

    public Tracker(Lesson lesson) {
        this.lesson = lesson;
        this.state = false;
        this.quizHistory = new ArrayList<>();
    }

    public Tracker(Lesson lesson, boolean state) {
        this.lesson = lesson;
        this.state = state;
        this.quizHistory = new ArrayList<>();
    }

    public Tracker(Lesson lesson, boolean state, List<QuizResult> quizHistory) {
        this.lesson = lesson;
        this.state = state;
        this.quizHistory = quizHistory;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<QuizResult> getQuizHistory() {
        return quizHistory;
    }

    QuizResult getBestQuizResult() {
        if (quizHistory.isEmpty())
            return null;

        QuizResult bestResult = quizHistory.get(0);
        for (QuizResult result : quizHistory) {
            if (result.getScorePercentage() > bestResult.getScorePercentage()) {
                bestResult = result;
            }
        }
        return bestResult;
    }

    public void addQuizResult(QuizResult result) {
        quizHistory.add(result);
    }
}
