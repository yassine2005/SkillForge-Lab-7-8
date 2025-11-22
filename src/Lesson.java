import java.util.ArrayList;
import java.util.List;

public class Lesson  {

    private final String lessonId;
    private String  title ,content ;
    private List<String> resources = new ArrayList<>();
    private Quiz quiz;
    public Lesson(String lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }
     public void setTitle(String title) {
        this.title = title;
     }
    public String getID() {
        return lessonId;
    }
    public String getContent() {
        return content;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
    public Quiz getQuiz() {
      return quiz;
    }
   public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
   }
}
