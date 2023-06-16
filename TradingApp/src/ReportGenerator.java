

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import Model.DatabaseHandler;
import Model.PLPoint;
import Model.User;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



public class ReportGenerator  {


    public void generateReport() throws Exception {
        LineChartGenerator();
        // Load the FXML file
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml")) ;
        Parent root = loader.load();
        
        // Create the scene
        Scene scene = new Scene(root);

        // // Set up the stage
        // primaryStage.setScene(scene);
        // primaryStage.show();

        // Export the scene as an image
        exportSceneToImage(scene, "exported_image.png");
        PDF();
    }

    private void exportSceneToImage(Scene scene, String filePath) {
        WritableImage snapshot = scene.snapshot(null);

        File outputFile = new File(filePath);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", outputFile);
            System.out.println("Scene exported as an image successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting scene as an image: " + e.getMessage());
        }
    }

    

    public static void LineChartGenerator() {
         DatabaseHandler dbh=new DatabaseHandler();
        User user=dbh.getUser();
        ArrayList<PLPoint> list = new ArrayList<>();
        list = DatabaseHandler.loadPLPoint(user.getUsername());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (PLPoint point : list) {
            dataset.addValue(point.getPLPoint(), "P&L Points", point.getDate());
        }

        // Create the chart
        JFreeChart chart = ChartFactory.createLineChart(
                "P&L Points",  // Chart title
                "Date",      // X-axis label
                "P&L points",      // Y-axis label
                dataset,       // Dataset
                PlotOrientation.VERTICAL,
                true,          // Include legend
                true,          // Include tooltips
                false          // Include URLs
        );

        // Customize the chart
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 13));
        chart.getTitle().setPaint(Color.WHITE);
        chart.setBackgroundPaint(Color.decode("#152238"));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.decode("#152238"));
        plot.setOutlinePaint(Color.WHITE);
        plot.setRangeGridlinesVisible(true);  // Remove the range gridlines
        plot.setDomainGridlinesVisible(false); // Remove the domain gridlines

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(0, Color.decode("#FFDE59"));
        renderer.setBaseShapesVisible(true); // Show the shapes/nodes on data points
        renderer.setSeriesItemLabelFont(0, new Font("Arial", Font.PLAIN, 9)); // Set the series label font
        renderer.setSeriesItemLabelPaint(0, Color.WHITE); // Set the series label font color
       
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));
        domainAxis.setLabelFont(new Font("Arial", Font.BOLD, 11));
        domainAxis.setTickLabelPaint(Color.WHITE); 
        domainAxis.setTickMarkStroke(new BasicStroke(1.0f));
        domainAxis.setLabelPaint(Color.WHITE);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));
        rangeAxis.setLabelFont(new Font("Arial", Font.BOLD, 11));
        rangeAxis.setTickLabelPaint(Color.WHITE);
        rangeAxis.setTickMarkStroke(new BasicStroke(1.0f));
        rangeAxis.setLabelPaint(Color.WHITE);

        // Save the chart as a PNG file
        int width = 400;
        int height = 250;
        File outputFile = new File("line_chart.png");
        try {
            ChartUtilities.saveChartAsPNG(outputFile, chart, width, height);
            System.out.println("Line chart saved as: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void PDF() {
        
        // Path to the input image file
        String imagePath = "exported_image.png";
        
        // Path to the output PDF file
        String pdfPath = "PerformanceReport.pdf";

        // Create a new Document
        Document document = new Document(PageSize.A4);

        try {
            // Create a PdfWriter instance to write the document to a file
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            
            // Open the document
            document.open();
            
            // Create an Image instance from the input image file
            Image image = Image.getInstance(imagePath);
            
            // Set the image position and scale as per your requirements
            image.setAbsolutePosition(0, 0);
            image.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());

            // Add the image to the document
            document.add(image);
            
            // Close the document
            document.close();
            
            System.out.println("PDF created successfully.");
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}

