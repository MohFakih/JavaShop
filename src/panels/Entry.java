package panels;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Entry extends JPanel {
	public JLabel		Label;
	public JTextField 	Field;
	public Entry(String labelText, int cols, boolean isPassword) {
		Label = new JLabel(labelText);
		if(isPassword) {
			Field = new JPasswordField(cols);
		}else {
			Field = new JTextField(cols);
		}
		add(Label);
		add(Field);
	}
	public Entry(String labelText) {
		this(labelText, 10);
	}
	public Entry(String labelText, int cols) {
		this(labelText, cols, false);
	}
}
