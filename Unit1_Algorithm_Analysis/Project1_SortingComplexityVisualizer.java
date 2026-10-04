import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Project1_SortingComplexityVisualizer {

    public static void main(String[] args) {

        int[] nValues = {10, 100, 1000};

        double[] mergeSort = {33.22, 664.39, 9965.78};
        double[] quickSort = {33.22, 664.39, 9965.78};

        // Display values in terminal
        System.out.println("Sorting Complexity Comparison");
        System.out.println("----------------------------------------");
        System.out.println("n\tMerge Sort\tQuick Sort");

        for (int i = 0; i < nValues.length; i++) {
            System.out.printf(
                "%d\t%.2f\t\t%.2f%n",
                nValues[i],
                mergeSort[i],
                quickSort[i]
            );
        }

        System.out.println("\nTime Complexity:");
        System.out.println("Merge Sort : O(n log n)");
        System.out.println("Quick Sort : O(n log n) average case");

        // Create image
        int width = 1200;
        int height = 800;

        BufferedImage image = new BufferedImage(
            width,
            height,
            BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = image.createGraphics();

        // Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Title
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));

        g.drawString(
            "Sorting Complexity: Merge Sort vs Quick Sort",
            250,
            50
        );

        // Graph area
        int left = 100;
        int right = 1100;
        int top = 100;
        int bottom = 650;

        // Axes
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);

        g.drawLine(left, bottom, right, bottom);
        g.drawLine(left, top, left, bottom);

        // X-axis label
        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString(
            "Input Size (n)",
            500,
            720
        );

        // Y-axis label
        g.rotate(-Math.PI / 2);

        g.drawString(
            "Estimated Operations",
            -450,
            40
        );

        g.rotate(Math.PI / 2);

        // Grid lines and Y-axis labels
        g.setFont(new Font("Arial", Font.PLAIN, 14));

        for (int i = 0; i <= 5; i++) {

            int y = bottom - i * 100;

            g.setColor(new Color(220, 220, 220));
            g.drawLine(left, y, right, y);

            g.setColor(Color.BLACK);

            g.drawString(
                String.valueOf(i * 2000),
                45,
                y + 5
            );
        }

        // X-axis labels
        int[] xPositions = {180, 550, 950};

        for (int i = 0; i < nValues.length; i++) {

            g.setColor(Color.BLACK);

            g.drawString(
                String.valueOf(nValues[i]),
                xPositions[i] - 10,
                bottom + 30
            );
        }

        // Scale values for graph
        double maxValue = 10000;

        int[] mergeY = new int[3];
        int[] quickY = new int[3];

        for (int i = 0; i < 3; i++) {

            mergeY[i] =
                bottom -
                (int) ((mergeSort[i] / maxValue) * 500);

            quickY[i] =
                bottom -
                (int) ((quickSort[i] / maxValue) * 500);
        }

        // Merge Sort line
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(4));

        for (int i = 0; i < 2; i++) {

            g.drawLine(
                xPositions[i],
                mergeY[i],
                xPositions[i + 1],
                mergeY[i + 1]
            );
        }

        // Merge Sort points
        for (int i = 0; i < 3; i++) {

            g.fillOval(
                xPositions[i] - 7,
                mergeY[i] - 7,
                14,
                14
            );
        }

        // Quick Sort line
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(3));

        for (int i = 0; i < 2; i++) {

            g.drawLine(
                xPositions[i],
                quickY[i],
                xPositions[i + 1],
                quickY[i + 1]
            );
        }

        // Quick Sort points
        for (int i = 0; i < 3; i++) {

            g.fillRect(
                xPositions[i] - 6,
                quickY[i] - 6,
                12,
                12
            );
        }

        // Legend
        g.setFont(new Font("Arial", Font.BOLD, 16));

        g.setColor(Color.BLUE);

        g.drawLine(
            750,
            120,
            790,
            120
        );

        g.drawString(
            "Merge Sort - O(n log n)",
            800,
            126
        );

        g.setColor(Color.RED);

        g.drawLine(
            750,
            155,
            790,
            155
        );

        g.drawString(
            "Quick Sort - Average Case O(n log n)",
            800,
            161
        );

        // Note
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.ITALIC, 15));

        g.drawString(
            "Quick Sort shown for average-case O(n log n).",
            400,
            760
        );

        g.dispose();

        // Save and automatically open image
        try {

            File outputFile = new File("Visualization.png");

            ImageIO.write(
                image,
                "png",
                outputFile
            );

            System.out.println(
                "\nVisualization.png created successfully!"
            );

            // Automatically open the image
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }

        } catch (Exception e) {

            System.out.println(
                "Error creating or opening Visualization.png"
            );

            e.printStackTrace();
        }
    }
}