import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Progress {
    private final String courseId;
    private final String studentID;
    private Date completionDate;
    private final ArrayList<Tracker> trackers = new ArrayList<>();


    public Progress(String courseId, List<Lesson> lessons, String studentID) {
        this.courseId = courseId;
        this.studentID = studentID;
        for (Lesson lesson : lessons) {
            trackers.add(new Tracker(lesson));
        }
    }

    public String getCourseId() {
        return courseId;
    }

    private Tracker findTracker(Lesson lesson) {
        for (Tracker tracker : trackers) {
            if (tracker.getLesson() == lesson) {
                return tracker;
            }
        }
        return null;
    }

    public void completeLesson(Lesson lesson) {
        Tracker tracker = findTracker(lesson);
        if (tracker != null) {
            tracker.setState(true);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void unCompleteLesson(Lesson lesson) {
        Tracker tracker = findTracker(lesson);
        if (tracker != null) {
            tracker.setState(false);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public ArrayList<Tracker> getTrackers() {
        return trackers;
    }

    public void updateTrackers(List<Lesson> lessons) {
        ArrayList<Lesson> completed = new ArrayList<>();
        for (Tracker tracker : trackers) {
            if (tracker.getState()) {
                completed.add(tracker.getLesson());
            }
        }

        trackers.clear();
        for (Lesson lesson : lessons) {
            if (completed.contains(lesson)) {
                trackers.add(new Tracker(lesson, true));
            } else {
                trackers.add(new Tracker(lesson));
            }
        }
    }

    public String getStudentID() {
        return studentID;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }
}
