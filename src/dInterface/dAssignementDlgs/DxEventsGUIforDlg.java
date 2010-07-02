package dInterface.dAssignementDlgs;

import java.awt.Frame;

import java.awt.event.MouseEvent;

import javax.swing.JDialog;

import dInterface.dUtil.ButtonsPanel;
import dInternal.DModel;
import dInternal.dData.dActivities.SetOfActivities;

@SuppressWarnings("serial")
public class DxEventsGUIforDlg extends JDialog {

	// constants for JLists in dialog  
	protected final int WIDTH_DLG = 700;

	protected final int HEIGHT_DLG = 420;

	protected final int WIDTH_PANE = 150;

	protected final int HEIGHT_PANE = 280;

	protected ButtonsPanel _buttonsPanel;

	protected SetOfActivities _activities;

	protected DModel _dModel;

	public DxEventsGUIforDlg(Frame f, String str, boolean b) {
		super(f, str, b);
	}

	public String idDlgToString() {
		return this.getClass().toString();
	}

	protected void saySomething(String str, MouseEvent e) {
		System.out.println(str + e.toString());
	}
}