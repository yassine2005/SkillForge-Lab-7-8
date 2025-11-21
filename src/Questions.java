import java.util.List;

public class Questions {
    private final String questionId;
    private List<String> options;
    private int correctAnswers;
    private String questionText;

 public Questions(String questionId) {
        this.questionId = questionId;
     }
     public String getQuestionId() {
     return questionId;
     }
 public List<String> getOptions() {
     return options;
 }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOptions(List<String> options) {
     this.options = options;
   }
 public int getCorrectAnswers() {
     return correctAnswers;
 }
 public void setCorrectAnswers(int correctAnswers) {
     this.correctAnswers = correctAnswers;
 }

}
