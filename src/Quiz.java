import java.util.List;

public class Quiz {
    private String title;
    private final String quizId;
    private List<Questions> questions;
  public Quiz(String title, String quizId) {
        this.title = title;
      this.quizId = quizId;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
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

}
