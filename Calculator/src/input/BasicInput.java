/**
 * Contains the input panel with all the buttons and operands.
 */

package input;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import backend.Parser;
import backend.Utils;
import ui.CustomButton;
import ui.Theme;

/**
 * Input panel with all the buttons.
 */
public class BasicInput extends JPanel {
	/**
	 * List of all the buttons.
	 */
	static final private String buttons[] = {
			"C", "Ans", "%", "÷",
			"7", "8", "9", "x",
			"4", "5", "6", "-",
			"1", "2", "3", "+",
			"0", ".", "="
	};

	/** Reference to the global textfield */
	private JTextField textField = Parser.textField;

	/** Flag to indicate whether the scientific calculator buttons should be shown */
	public static boolean showScientific = true;

	/**
	 * Array of all the buttons.
	 */
	private CustomButton button_arr[] = new CustomButton[buttons.length];

	public BasicInput() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		setLayout(new GridBagLayout());

		// Create the scientific layout
		if (showScientific) {
			new ScientificInput(this);
		}

		createButtons();

		// Change the clear button action
		button_arr[0].removeActionListener(button_arr[0].getActionListeners()[0]);
		button_arr[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Parser.textField.setText("");
			}
		});

		// Equal operator function.
		button_arr[18].removeActionListener(button_arr[18].getActionListeners()[0]);
		button_arr[18].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Parser.calculate();
			}
		});

		// Set the button colors
		button_arr[3].setBackground(Theme.PRIMARY_COLOR);
		button_arr[7].setBackground(Theme.PRIMARY_COLOR);
		button_arr[11].setBackground(Theme.PRIMARY_COLOR);
		button_arr[15].setBackground(Theme.PRIMARY_COLOR);
		button_arr[18].setBackground(Theme.PRIMARY_COLOR);

		button_arr[4].setBackground(Theme.SECONDARY_COLOR);
		button_arr[5].setBackground(Theme.SECONDARY_COLOR);
		button_arr[6].setBackground(Theme.SECONDARY_COLOR);
		button_arr[8].setBackground(Theme.SECONDARY_COLOR);
		button_arr[9].setBackground(Theme.SECONDARY_COLOR);
		button_arr[10].setBackground(Theme.SECONDARY_COLOR);
		button_arr[12].setBackground(Theme.SECONDARY_COLOR);
		button_arr[13].setBackground(Theme.SECONDARY_COLOR);
		button_arr[14].setBackground(Theme.SECONDARY_COLOR);
		button_arr[16].setBackground(Theme.SECONDARY_COLOR);
		button_arr[17].setBackground(Theme.SECONDARY_COLOR);

		// button_arr[1].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));

		/** Offset the calculator buttons if the scientific calculator is shown */
		int space = showScientific ? 5 : 0;
		for (int i = 0; i < buttons.length - 3; i++) {
			gbc.gridx = space + (i % 4);
			gbc.gridy = i / 4;

			add(button_arr[i], gbc);
		}

		gbc.gridy++;
		gbc.gridx = space;
		gbc.gridwidth = 2;
		add(button_arr[16], gbc);
		gbc.gridx += 2;
		gbc.gridwidth = 1;
		add(button_arr[17], gbc);
		gbc.gridx++;
		add(button_arr[18], gbc);

		setBackground(Theme.BG_COLOR);
	}

	/**
	 * Creates and initializes the basic buttons in the grid.
	 */
	private void createButtons() {
		for (int i = 0; i < buttons.length; i++) {
			button_arr[i] = new CustomButton(buttons[i]);
			// Set the action when the button is clicked
			button_arr[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Parser.clearOutput();

					String text = ((JButton) e.getSource()).getText();
					textField.setText(textField.getText() + text);
				}
			});
			// Set the button to allow key input when the textfield isn't in focus
			button_arr[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if (!Utils.valInArray((Character) e.getKeyChar(), Utils.ALLOWED_KEYS)) {
						e.consume();
						return;
					}

					Parser.clearOutput();

					if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && textField.getText().length() > 0) {
						String text = textField.getText();
						textField.setText(text.substring(0, text.length() - 1));
					} else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
						Parser.calculate();
					} else {
						// Substitute certain characters
						switch (e.getKeyChar()) {
							case '*':
								e.setKeyChar('x');
								break;
							case '/':
								e.setKeyChar('÷');
								break;
							default:
								break;
						}
						textField.setText(textField.getText() + e.getKeyChar());
					}
				}
			});
		}
	}
}
