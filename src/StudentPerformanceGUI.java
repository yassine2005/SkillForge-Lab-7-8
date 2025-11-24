import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
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
            JLabel quizRate = new JLabel("Quiz rate: " + analytics.getQuizAttemptRate());
            add(quizRate, BorderLayout.NORTH);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            double averageScores = analytics.getAverageScore();
            double completionRate = analytics.getCompletionRate();

            dataset.addValue(averageScores, "AverageScore", "Average Score");
            dataset.addValue(completionRate, "CompletionRate", "Completion Rate");

            // Create chart
            JFreeChart chart = ChartFactory.createBarChart(
                    "Lessons Analytics",   // chart title
                    "Lessons",             // x-axis label
                    "Value",               // y-axis label
                    dataset,               // dataset
                    PlotOrientation.VERTICAL,
                    true,                  // include legend
                    true,                  // tooltips
                    false                  // URLs
            );


            // Put chart into a panel
            ChartPanel chartPanel = new ChartPanel(chart);
            add(chartPanel, BorderLayout.CENTER);

        }catch (RuntimeException ex){
            JOptionPane.showMessageDialog(null, "Error retrieving student performance data");
        }
    }
}
