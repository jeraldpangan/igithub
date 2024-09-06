/**
 * Used to show the output of the calculator.
 */

package output;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import backend.History;
import backend.Parser;
import backend.Utils;
import ui.Theme;

/**
 * Shows the calulator output.
 */
public class Output extends JPanel {
	/**
	 * Shows the output in the UI
	 */
	private JTextField textField = Parser.textField;
	private JScrollBar scrollBar;

	static int i = 0;
	static int current_index = 15;

	public Output() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Theme.BG_COLOR);

		// Set the scroll bar to use the system look and feel
		LookAndFeel prevLF = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
			UIManager.setLookAndFeel(prevLF);
		} catch (Exception e) {
			scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		}

		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 38));
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setForeground(Theme.FG_COLOR);
		textField.setBackground(Theme.BG_COLOR);
		textField.setCaretColor(Theme.FG_COLOR);

		// Used to filter keyboard input to allowed key characters
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP && i > 2 && ((current_index < 15 && current_index > 0)
						|| (textField.getText().isEmpty() && current_index == 15)) && History.length > 0) {
					i = 0;
					if (current_index >= 15) {
						current_index = History.length - 1;
					} else {
						current_index--;
					}
					textField.setText(History.storeCalcs.get(current_index));
				}

				if (e.getKeyChar() == KeyEvent.VK_ENTER && i > 2) {
					i = 0;
					Parser.clearOutput();
					Parser.calculate();
				}

				if (e.getKeyCode() == KeyEvent.VK_DOWN && i > 2 && current_index < 15 && current_index >= 0) {
					current_index++;
					if (current_index < History.length) {
						textField.setText(History.storeCalcs.get(current_index));
					} else {
						textField.setText("");
						current_index = 15;
					}
					i = 0;
				}

				i++;
				super.keyPressed(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (!Utils.valInArray((Character) e.getKeyChar(), Utils.ALLOWED_KEYS)) {
					e.consume(); // Ignore the key
					return;
				}

				if (e.getKeyChar() != KeyEvent.VK_ENTER) {
					Parser.clearOutput();
				}

				switch (e.getKeyChar()) {
					case '*':
						e.setKeyChar('x');
						break;
					default:
						break;
				}

				current_index = 15;

				super.keyTyped(e);
			}
		});

		// Add a scrollbar to the textfield for long results.
		BoundedRangeModel brm = textField.getHorizontalVisibility();
		scrollBar.setModel(brm);
		scrollBar.setBackground(Theme.BG_COLOR);

		add(textField);
		add(scrollBar);
	}
}
