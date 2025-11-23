import org.apache.pdfbox.pdmodel.common.PDRectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Certificate extends JDialog {

    private final JPanel contentPanel;

    public Certificate(Frame owner, Student student, Course course) {
        super(owner, "Certificate of Completion", true);

        setLayout(new BorderLayout());

        contentPanel = buildCertificatePanel(student, course);
        add(contentPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton download = new JButton("Download (PDF)");
        JButton close = new JButton("Close");
        bottom.add(download);
        bottom.add(close);
        add(bottom, BorderLayout.SOUTH);

        download.addActionListener((ActionEvent e) -> {
            try {
                saveCertificateAsPdfOrPng(student, course);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while saving certificate: " + ex.getMessage());
            }
        });

        close.addActionListener((ActionEvent e) -> dispose());

        setSize(520, 420);
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private JPanel buildCertificatePanel(Student student, Course course) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("Certificate of Completion", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel spacer = new JLabel(" ");
        spacer.setPreferredSize(new Dimension(0, 8));
        spacer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel("This certifies that: " + student.getUsername());
        name.setFont(new Font("Serif", Font.PLAIN, 20));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel courseLabel = new JLabel("Has successfully completed the course:");
        courseLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel courseName = new JLabel(course.getTitle());
        courseName.setFont(new Font("Serif", Font.BOLD, 22));
        courseName.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(12));
        panel.add(new JSeparator(SwingConstants.HORIZONTAL));
        panel.add(Box.createVerticalStrut(20));
        panel.add(name);
        panel.add(Box.createVerticalStrut(12));
        panel.add(courseLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(courseName);

        return panel;
    }

    private void saveCertificateAsPdfOrPng(Student student, Course course) throws Exception {

        int w = contentPanel.getWidth();
        int h = contentPanel.getHeight();

        if (w <= 0 || h <= 0) {

            contentPanel.setSize(500, 300);
            w = contentPanel.getWidth();
            h = contentPanel.getHeight();
        }

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, w, h);
        contentPanel.printAll(g2d);
        g2d.dispose();

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("certificate_" + student.getID() + "_" + course.getID() + ".pdf"));
        int choice = chooser.showSaveDialog(this);
        if (choice != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        String path = file.getAbsolutePath();


        try {

            Class<?> pdDocClass = Class.forName("org.apache.pdfbox.pdmodel.PDDocument");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            baos.close();


            org.apache.pdfbox.pdmodel.PDDocument document = new org.apache.pdfbox.pdmodel.PDDocument();
            org.apache.pdfbox.pdmodel.common.PDRectangle rect = PDRectangle.LETTER;

            org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage(new org.apache.pdfbox.pdmodel.common.PDRectangle(w, h));
            document.addPage(page);
            org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject pdImage =
                    org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory.createFromImage(document, img);
            org.apache.pdfbox.pdmodel.PDPageContentStream contentStream =
                    new org.apache.pdfbox.pdmodel.PDPageContentStream(document, page);
            contentStream.drawImage(pdImage, 0, 0, w, h);
            contentStream.close();

            String outPath = path;
            if (!outPath.toLowerCase().endsWith(".pdf")) outPath += ".pdf";
            document.save(outPath);
            document.close();

            JOptionPane.showMessageDialog(this, "Saved PDF: " + outPath);
            return;
        } catch (ClassNotFoundException cnf) {

        } catch (Exception ex) {

            ex.printStackTrace();
        }


        String outPath = path;
        if (!outPath.toLowerCase().endsWith(".png")) outPath += ".png";
        ImageIO.write(img, "png", new File(outPath));
        JOptionPane.showMessageDialog(this, "Saved image: " + outPath + "\n(Install Apache PDFBox to enable direct PDF export.)");
    }
}
