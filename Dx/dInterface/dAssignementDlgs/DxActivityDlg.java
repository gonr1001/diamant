package dInterface.dAssignementDlgs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DlgIdentification;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.dData.DxResource;
import dInternal.dData.dActivities.DxActivity;
import dInternal.dData.dActivities.DxSetOfActivities;
import developer.DxFlags;

public class DxActivityDlg extends JDialog implements ActionListener,
		DlgIdentification {
	// private DApplication _dApplic;

	private JDialog _jd;

	private JLabel _lVisible;

	private JLabel _lNoVisible;

	private JList _rightList;

	private JList _leftList;

	private ButtonsPanel _buttonsPanel;

	private Object[] _currentActivities = new Object[0];

	private DxSetOfActivities _dxsoaAct;

	private String[] _arrowsNames = { DConst.TO_RIGHT, DConst.TO_LEFT };

	/**
	 * the vectors containing the activities ID
	 */
	private Vector<DxActivity> _dxavRight;

	private Vector<DxActivity> _dxavLeft;

	private DModel _dModel;

	/**
	 * Default constructor
	 * 
	 * @param dApplic
	 *            The application object (for extracting the JFrame)
	 */

	// public DxActivityDlg(DApplication dApplic) {
	// super(dApplic.getJFrame(), DConst.ACT_LIST, true);
	// _dApplic = dApplic;
	// _jd = this; // to pass this dialog to the EditActivityDlg
	// if (dApplic.getCurrentDxDoc() == null)
	// return;
	// _dmodel = dApplic.getCurrentDxDoc().getCurrentDModel();
	// _dxsoaAct = _dmodel.getDxSetOfActivities();
	//
	// initialize();
	// int x = _dApplic.getJFrame().getX();
	// int y = _dApplic.getJFrame().getY();
	// this.setLocation(x + DConst.X_OFFSET, y + DConst.Y_OFFSET);
	// // this.setMinimumSize(new Dimension(300, 300));
	// // this.setPreferredSize(new Dimension(400, 400)); //the real
	// // this.setMaximumSize(new Dimension(500, 500)); // XXXX Pascal: lien
	// // inutile avec JDK 1.5
	// this.pack();
	// this.setResizable(false);
	// this.setVisible(true);
	// }
	public DxActivityDlg(JFrame jFrame, DModel dModel) {
		super(jFrame, DConst.ACT_LIST, true);
		// _dApplic = dApplic;
		_jd = this; // to pass this dialog to the EditActivityDlg
		// if (dApplic.getCurrentDxDoc() == null)
		// return;
		_dModel = dModel;
		_dxsoaAct = _dModel.getDxSetOfActivities();

		initialize();
		int x = jFrame.getX();
		int y = jFrame.getY();
		this.setLocation(x + DConst.X_OFFSET, y + DConst.Y_OFFSET);
		// this.setMinimumSize(new Dimension(300, 300));
		// this.setPreferredSize(new Dimension(400, 400)); //the real
		// this.setMaximumSize(new Dimension(500, 500)); // XXXX Pascal: lien
		// inutile avec JDK 1.5
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}

	public Dimension getMinimumSize() {
		return new Dimension(300, 300);
	}

	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	public Dimension getMaximumSize() {
		return new Dimension(500, 500);
	}

	/**
	 * Initialize the dialog
	 */
	protected void initialize() {

		_dxavRight = new Vector<DxActivity>();
		_dxavLeft = new Vector<DxActivity>();

		DxActivity dxaCurrentAct;
		Iterator<DxResource> itActivities = _dxsoaAct.iterator();
		while (itActivities.hasNext()) {
			dxaCurrentAct = (DxActivity) itActivities.next();
			if (dxaCurrentAct.getVisibility()) {
				_dxavLeft.add(dxaCurrentAct);
			} else {
				_dxavRight.add(dxaCurrentAct);
			}

		}

		// right panel
		_rightList = new JList(_dxavRight);
		_rightList.addMouseListener(mouseListenerLists);

		JPanel listPanel = DxTools.listPanel(_rightList);
		_lNoVisible = new JLabel(_dxavRight.size() + " " + DConst.NOT_INCLUDED);
		JPanel rightPanel = new JPanel(new BorderLayout());
		listPanel.setMinimumSize(new Dimension(100, 100));
		listPanel.setPreferredSize(new Dimension(100, 300));
		listPanel.setMaximumSize(new Dimension(100, 400));
		rightPanel.add(_lNoVisible, BorderLayout.NORTH);
		rightPanel.add(listPanel, BorderLayout.SOUTH);

		// left panel
		_leftList = new JList(_dxavLeft);
		_leftList.addMouseListener(mouseListenerLists);
		_lVisible = new JLabel(_dxavLeft.size() + " " + DConst.INCLUDED);
		JPanel listPanelLeft = DxTools.listPanel(_leftList);
		JPanel leftPanel = new JPanel();
		listPanelLeft.setMinimumSize(new Dimension(100, 100));
		listPanelLeft.setPreferredSize(new Dimension(100, 300));
		listPanelLeft.setMaximumSize(new Dimension(100, 400));
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(_lVisible, BorderLayout.NORTH);
		leftPanel.add(listPanelLeft, BorderLayout.CENTER);
		// arrows panel
		JPanel arrowsPanel = DxTools.arrowsPanel(this, _arrowsNames, true);
		// placing the panels and buttons into the _listsPanel
		JPanel centerPanel = new JPanel();
		centerPanel.add(leftPanel, BorderLayout.EAST);
		centerPanel.add(arrowsPanel, BorderLayout.CENTER);
		centerPanel.add(rightPanel, BorderLayout.WEST);
		// _applyPanel
		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_buttonsPanel = new TwoButtonsPanel(this, a);
		// Setting the button APPLY disable
		_buttonsPanel.setFirstDisable();
		// placing the elements into the JDialog
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
	}// end initialize

	/**
	 * Define the mouse adapter and actions for the JList is
	 */
	private MouseListener mouseListenerLists = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (((JList) e.getSource()).getModel().getSize() == 0)
				return;
			if (e.getSource().equals(_leftList))
				_rightList.clearSelection();
			else
				_leftList.clearSelection();
			_currentActivities = ((JList) e.getSource()).getSelectedValues();

			if (e.getClickCount() == 2) {
				new DxEditEventDlg(_jd, _dModel, /* _dApplic, */
				(String) _currentActivities[0], false);
			}// end if
		}// end public void mouseClicked
	};// end definition of MouseListener mouseListener = new MouseAdapter(){

	/**
	 * 
	 * @param e
	 *            an event
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		// if buttons CLOSE
		if (command.equals(DConst.BUT_CLOSE))
			dispose();
		// if button APPLY
		if (command.equals(DConst.BUT_APPLY)) {
			setActivitesVisibility();

			_dModel.changeInDModel(this.idDlgToString());
			_buttonsPanel.setFirstDisable();
		}
		// if arrows
		if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])) {
			_lNoVisible.setText(_dxavRight.size() + " " + DConst.NOT_INCLUDED);
			_lVisible.setText(_dxavLeft.size() + " " + DConst.INCLUDED);
			_buttonsPanel.setFirstEnable();
		}// end if (command.equals(_arrowsNames[0]) ||
		// command.equals(_arrowsNames[1]))
	}// end method

	/**
	 * Sets the field "Visible" of the activities, according with their position
	 * in the JLists. If an activity is in the _rightList, Visible = false.
	 */
	private void setActivitesVisibility() {
		Iterator<DxActivity> itAct = _dxavRight.iterator();
		while (itAct.hasNext()) {
			itAct.next().setVisibility(false);
		}

		itAct = _dxavLeft.iterator();
		while (itAct.hasNext()) {
			itAct.next().setVisibility(true);
		}
	}

	public String idDlgToString() {
		return this.getClass().toString();
	}
} // end DxActivityDlg
