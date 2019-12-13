package view.algoInfos;

import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class AbstractAlgoInfos extends JTextArea {

	public AbstractAlgoInfos() {
		super();
		setPreferredSize(new Dimension(240, 100));
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}
}
