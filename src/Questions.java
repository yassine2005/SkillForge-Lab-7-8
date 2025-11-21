import java.util.List;

public class Questions {
    private final String questionId;
    private List<String> options;
    private int correct;
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
 public int getCorrect() {
     return correct;
 }
 public void setCorrect(int correct) {
     this.correct = correct;
 }

}
