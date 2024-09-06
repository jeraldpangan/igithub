package ui;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/** Custom button with themeing */
public class CustomButton extends JButton {
    public CustomButton(String text) {
        super(text);
        /** Set the theme of the button */
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 28));
        setForeground(Theme.FG_COLOR);
        setBackground(Theme.TERTAIRY_COLOR);
        setBorder(BorderFactory.createLineBorder(Theme.BG_COLOR));
        setFocusPainted(false);
    }
}
