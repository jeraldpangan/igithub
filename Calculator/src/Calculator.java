import javax.swing.*;

import frontend.Home;

import java.awt.*;

/** Main entry point to the app. */
public class Calculator extends JFrame {
    /**
     * Constructor to build and run the app.
     */
    public Calculator() {
        add(new Home());

        // Set the windowing options
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(400, 500));
        setVisible(true);
    }

    public static void main(String[] args) {
        // Start the app.
        new Calculator();
    }
}