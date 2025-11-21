import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int minScore;
    private final String quizId;
    private List<Questions> questions;
    private int score;
    private int maxScore;

  public Quiz( String quizId) {
      this.minScore= (int) (0.5* questions.size());
      this.quizId = quizId;
      this.maxScore= (int) (1* questions.size());
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
  }

    public void removeQuestion(Questions question) {
        questions.remove(question);
    }
    public int calculateScore(ArrayList<Integer> userAnswers) {
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers.get(i) == questions.get(i).getCorrect()) {
                score++;
            }
        }
        return score;
    }












    public double getPercentage() {
        return maxScore > 0 ? (double) score / maxScore * 100 : 0;
    }

}
