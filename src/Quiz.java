import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private final String quizId;
    private List<Questions> questions;
    private int score;
    private int minScore;
    private int maxScore;

  public Quiz( String quizId, List<Questions> questions) {
      this.quizId = quizId;
      this.questions = questions == null ? new ArrayList<>() : questions;
      this.minScore= (int) (0.5* this.questions.size());
      this.maxScore= this.questions.size();
      this.score = 0;
  }

   public String getQuizId() {
      return quizId;
   }

    public List<Questions> getQuestions() {
      return questions;
 }
    public void setQuestions(List<Questions> questions) {
      this.questions = questions;
 }
    public void addQuestion(Questions question) {
      questions.add(question);
      this.minScore= (int) (0.5* this.questions.size());
      this.maxScore= this.questions.size();
  }

    public void removeQuestion(Questions question) {
        questions.remove(question);
        this.minScore= (int) (0.5* this.questions.size());
        this.maxScore= this.questions.size();
    }

    public int calculateScore(ArrayList<String> userAnswers) {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrect().equalsIgnoreCase(userAnswers.get(i))) {
                score++;
            }
        }

        if (score > this.score) {
            this.score = score;
        }

        return score;
    }


    public double getPercentage() {
        return maxScore > 0 ? (double) score / maxScore * 100 : 0;
    }

}
