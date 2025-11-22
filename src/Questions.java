import java.util.List;

public class Questions {
    private List<String> options;
     private String correct;
    private String questionText;

 public Questions(String questionText,String correct)
 {
        this.questionText = questionText;
          this.correct = correct;
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

   public String getCorrect() {
     return correct;
   }

   public void setCorrect(String correct) {
     this.correct = correct;
   }


    public void addOption(String option) {
     options.add(option);
  }
}
