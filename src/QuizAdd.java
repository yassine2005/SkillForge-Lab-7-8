import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizAdd extends JPanel{

    private JButton saveButton;
    private JTextField first;
    private JPanel add;
    private JTextField question;
    private JLabel titleLabel;
    private JLabel contentLabel;
    private JTextField second;
    private JTextField third;
    private JTextField fourth;
    private JTextField answer;
    private JButton Add;
    private final String courseId;
    private final CourseDatabaseManager databaseManager;
    private final Lesson lesson;
    List<Questions> questions=new ArrayList<Questions>();

    public QuizAdd(CourseDatabaseManager databaseManager, String courseId,Lesson lesson) {
        this.databaseManager = databaseManager;
        this.courseId = courseId;
        this.lesson = lesson;
        setLayout(new BorderLayout());
        add(add, BorderLayout.CENTER);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddQuiz();
                changeToLessonAdd(courseId);
            }
        });
        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleQuestion();

            }
        });

    }

       private void handleQuestion(){
           String ques = question.getText().trim();
           String firs = first.getText().trim().toLowerCase();
           String sec = second.getText().trim().toLowerCase();
           String thir= third.getText().trim().toLowerCase();
           String four= fourth.getText().trim().toLowerCase();
           String correct= answer.getText().trim().toLowerCase();
           if (ques.isEmpty() || firs.isEmpty()||sec.isEmpty()||thir.isEmpty()||four.isEmpty()||correct.isEmpty()) {
               JOptionPane.showMessageDialog(this, "Please enter a valid quiz !", "Error", JOptionPane.ERROR_MESSAGE);
           return;
           }
           Questions quest=new Questions(ques,correct);
            List<String> options=new ArrayList<>();
            options.add(firs);
            options.add(sec);
            options.add(thir);
            options.add(four);
           quest.setOptions(options);
           questions.add(quest);
           JOptionPane.showMessageDialog(this, "Question added successfully!" , "Success", JOptionPane.INFORMATION_MESSAGE);
           question.setText("");
           first.setText("");
           second.setText("");
           third.setText("");
           fourth.setText("");
           answer.setText("");
           question.requestFocus();
       }

    private void handleAddQuiz() {

      if(questions.isEmpty()){
          JOptionPane.showMessageDialog(this, "Please enter a valid quiz !", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
        try {
            Course course=databaseManager.getRecordByID(courseId);
            String idd = generateQuizID();
            Quiz newQuiz = new Quiz(idd,questions);
            this.lesson.setQuiz(newQuiz);
            databaseManager.updateRecord(course);
            databaseManager.saveToFile();

            JOptionPane.showMessageDialog(this, "Quiz added successfully!" + idd, "Success", JOptionPane.INFORMATION_MESSAGE);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding Quiz!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String generateQuizID() {
        int highest = getHighestID();
        return String.format("Q%04d", highest + 1);
    }
    private int getHighestID() {
        Course c = databaseManager.getRecordByID(courseId);
        int highest = 0;

        if (c != null) {
            for (Lesson lesson : c.getLessons()) {
                Quiz quiz = lesson.getQuiz();
                if (quiz != null) {
                    String quizId = quiz.getQuizId();try {
                        String numberPart = quizId.substring(1);
                        int idNumber = Integer.parseInt(numberPart);
                        if (idNumber > highest) {
                            highest = idNumber;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }  }
        }
        return highest;
    }

    private void changeToLessonAdd(String courseId) {
        LessonAdd lessonAdd = new LessonAdd(databaseManager, courseId);
        removeAll();
        setLayout(new BorderLayout());
        add(lessonAdd, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
