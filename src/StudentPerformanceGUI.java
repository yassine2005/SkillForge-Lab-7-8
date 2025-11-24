import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StudentPerformanceGUI extends JFrame {
    private AnalyticsService analyticsService = new AnalyticsService();

    public StudentPerformanceGUI(String courseID, String studentID) {
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        try {
            StudentCoursePerformanceAnalytics analytics =
                    analyticsService.getStudentCoursePerformance(courseID, studentID);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            double averageScores = analytics.getAverageScore();
            double completionRate = analytics.getCompletionRate();

            dataset.addValue(averageScores, "AverageScore", "Average Score");
            dataset.addValue(completionRate, "CompletionRate", "Completion Rate");
            dataset.addValue(analytics.getQuizAttemptRate(), "QuizAttemptRate", "Quiz Attempt Rate");

            // Create chart
            JFreeChart chart = ChartFactory.createBarChart(
                    "Student Analytics",   // chart title
                    "Performance",                  // x-axis label
                    "",                         // y-axis label
                    dataset
            );

            // Put chart into a panel
            ChartPanel chartPanel = new ChartPanel(chart);
            add(chartPanel, BorderLayout.CENTER);

        }catch (RuntimeException ex){
            JOptionPane.showMessageDialog(null, "Error retrieving student performance data");
        }
    }
}
