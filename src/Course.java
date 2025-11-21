import java.util.ArrayList;
import java.util.List;

public class Course implements Record {
    private final String courseId;
    private final String instructorId;
    private String title, description;
    private List<Lesson> lessons = new ArrayList<>();


    private ArrayList<String> studentIds = new ArrayList<>();

    public Course(String courseId, String title, String description, String instructorId) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructorId='" + instructorId + '\'' +
                ", lessons=" + lessons +
                ", studentIds=" + studentIds +
                '}';
    }

    @Override
    public String getID() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public ArrayList<String> getStudentIds() {
        return studentIds;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void setStudentIds(ArrayList<String> studentIds) {
        this.studentIds = studentIds;
    }

    public void enrollStudent(Student student) {
        if (student == null) return;
        String sid = student.getID();
        if (!studentIds.contains(sid)) studentIds.add(sid);
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void deleteLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    public Lesson getLessonById(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        return null;
    }
}
