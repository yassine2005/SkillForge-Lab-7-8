import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseAnalyticsGUI extends JFrame {
    private AnalyticsService analyticsService = new AnalyticsService();

    public CourseAnalyticsGUI(String courseID) {
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        try {
            CourseAnalytics analytics = analyticsService.getCourseAnalytics(courseID);
            JPanel coursePanel = new JPanel();
            coursePanel.add(new JLabel("Course ID: " + analytics.getCourseId()));
            coursePanel.add(new JLabel("Course completion ratio: " + analytics.getCourseCompletionRate()));
            add(coursePanel, BorderLayout.NORTH);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            Map<String, Double> averageScores = analytics.getLessonAverageScoresPercentages();
            Map<String, Double> completionRate = analytics.getLessonCompletionRate();
            Map<String, Double> quizAttemptRate = analytics.getLessonQuizAttemptRate();
            for (String lessonId : analytics.getLessonIds()) {
                dataset.addValue(averageScores.get(lessonId), "AverageScore", lessonId);
                dataset.addValue(completionRate.get(lessonId), "CompletionRate", lessonId);
                dataset.addValue(quizAttemptRate.get(lessonId), "QuizAttemptRate", lessonId);
            }

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
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving course analytics");
        }
    }
}
