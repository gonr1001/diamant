package dInterface.dAssignementDlgs;


import java.awt.Frame;

import java.awt.event.MouseEvent;

import javax.swing.JDialog;



public class DxEventsGUIforDlg extends JDialog {

	protected final int WIDTH_DLG = 700;
	protected final int HEIGHT_DLG = 420;
	protected final int WIDTH_PANE = 150;
	protected final int HEIGHT_PANE = 280;

	public DxEventsGUIforDlg(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
	}

	public String idDlgToString() {
		return this.getClass().toString();
	}

	protected void saySomething(String str, MouseEvent e) {
		System.out.println(str + e.toString());
	}
}