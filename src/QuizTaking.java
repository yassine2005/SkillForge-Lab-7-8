import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class QuizTaking extends JDialog {

    private final Student student;
    private final Course course;
    private final Lesson lesson;

    public QuizTaking(Frame owner, Student student, Course course, Lesson lesson) {
        super(owner, "Quiz - " + lesson.getTitle(), true);
        this.student = student;
        this.course = course;
        this.lesson = lesson;

        if (lesson.getQuiz() == null || lesson.getQuiz().getQuestions() == null || lesson.getQuiz().getQuestions().isEmpty()) {
            JOptionPane.showMessageDialog(owner, "No quiz available for this lesson.", "No Quiz", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        if (hasStudentTakenQuiz()) {
            JOptionPane.showMessageDialog(owner, "You have already taken this quiz.", "Quiz Taken", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        buildUI(owner);
        setSize(700, 520);
        setLocationRelativeTo(owner);
    }

    private void buildUI(Frame owner) {
        setLayout(new BorderLayout());

        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Quiz quiz = lesson.getQuiz();
        List<Questions> questions = quiz.getQuestions();
        List<ButtonGroup> groups = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            Questions q = questions.get(i);

            JPanel qPanel = new JPanel();
            qPanel.setLayout(new BorderLayout());
            qPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Question " + (i + 1)),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6)
            ));

            JTextArea qText = new JTextArea();
            qText.setText(q.getQuestionText());
            qText.setEditable(false);
            qText.setLineWrap(true);
            qText.setWrapStyleWord(true);
            qText.setBackground(getBackground());
            qText.setBorder(null);
            qText.setFocusable(false);
            qPanel.add(qText, BorderLayout.NORTH);

            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new GridLayout(0, 1, 4, 4));
            ButtonGroup bg = new ButtonGroup();
            groups.add(bg);

            List<String> opts = q.getOptions();
            for (int optIndex = 0; optIndex < 4; optIndex++) {
                String optText = optIndex < opts.size() ? opts.get(optIndex) : "";
                JRadioButton rb = new JRadioButton(optText);
                rb.setActionCommand(optText);
                bg.add(rb);
                optionsPanel.add(rb);
            }

            qPanel.add(optionsPanel, BorderLayout.CENTER);
            questionsPanel.add(qPanel);
            questionsPanel.add(Box.createVerticalStrut(8));
        }

        JScrollPane scroll = new JScrollPane(questionsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");
        bottom.add(cancel);
        bottom.add(submit);
        add(bottom, BorderLayout.SOUTH);

        cancel.addActionListener((ActionEvent e) -> dispose());

        submit.addActionListener((ActionEvent e) -> {
            try {
                for (ButtonGroup g : groups) {
                    if (g.getSelection() == null) {
                        JOptionPane.showMessageDialog(this, "Please answer all questions.", "Incomplete", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                int correct = 0;
                int total = questions.size();

                for (int qi = 0; qi < questions.size(); qi++) {
                    ButtonGroup g = groups.get(qi);
                    String answer = g.getSelection().getActionCommand();
                    String correctAns = questions.get(qi).getCorrect();

                    if (correctAns != null && answer.equalsIgnoreCase(correctAns)) {
                        correct++;
                    }
                }

                double percent = total > 0 ? (correct * 100.0) / total : 0.0;
                long timestamp = System.currentTimeMillis();
                int attemptNumber = computeAttemptNumber() + 1;
                boolean state = true;

                QuizResult result = new QuizResult(
                        student.getID(),
                        course.getID(),
                        lesson.getLessonId(),
                        percent,
                        total,
                        timestamp,
                        attemptNumber,
                        state
                );

                student.addQuizResult(course.getID(), lesson, result);
                markLessonCompletedInProgress();

                UserDatabaseManager udb = new UserDatabaseManager("users.json");
                udb.updateRecord(student);
                udb.saveToFile();

                JOptionPane.showMessageDialog(this, "Quiz submitted. Score: " + String.format("%.2f", percent) + "%");
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error submitting quiz: " + ex.getMessage());
            }
        });
    }

    private boolean hasStudentTakenQuiz() {
        for (Progress p : student.getProgressTrackers()) {
            if (p.getCourseId().equals(course.getID())) {
                for (Tracker t : p.getTrackers()) {
                    for (QuizResult r : t.getQuizHistory()) {
                        if (r != null && lesson.getLessonId().equals(r.getLessonId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private int computeAttemptNumber() {
        int attempts = 0;
        for (Progress p : student.getProgressTrackers()) {
            if (p.getCourseId().equals(course.getID())) {
                for (Tracker t : p.getTrackers()) {
                    for (QuizResult r : t.getQuizHistory()) {
                        if (lesson.getLessonId().equals(r.getLessonId())) {
                            attempts++;
                        }
                    }
                }
                break;
            }
        }
        return attempts;
    }

    private void markLessonCompletedInProgress() {
        Progress target = null;
        for (Progress p : student.getProgressTrackers()) {
            if (p.getCourseId().equals(course.getID())) {
                target = p;
                break;
            }
        }

        if (target == null) {
            target = new Progress(course.getID(), course.getLessons(), student.getID());
            student.getProgressTrackers().add(target);
        }

        Tracker found = null;
        for (Tracker t : target.getTrackers()) {
            if (lesson.getLessonId().equals(t.getLesson().getLessonId())) {
                found = t;
                break;
            }
        }

        if (found == null) {
            target.updateTrackers(course.getLessons());
            for (Tracker t : target.getTrackers()) {
                if (lesson.getLessonId().equals(t.getLesson().getLessonId())) {
                    found = t;
                    break;
                }
            }
        }

        if (found != null) {
            found.setState(true);
        }
    }
}
