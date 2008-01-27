/**
 *
 * Title: SelectInstructors 
 *
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */

/**
 * Description: SelectInstructors is a class used to
 *
 */
package dInterface.dAffectation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dAssignementDlgs.DxEditEventDlg;
import dInterface.dAssignementDlgs.EditEventDlg;
//import dInterface.dAssignementDlgs.EditEventDlg;

import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInterface.dUtil.TwoButtonsPanel;
import developer.DxFlags;


public class SelectInstructors extends JDialog implements ActionListener {

	/**
	 * the lists containing the instructors ID
	 */
	/**
	 * the vectors containing the instructors ID
	 */

	// private JButton _toRight, _toLeft;
	private JLabel _lVisible, _lNoVisible;

	private JList _rightList, _leftList;

	private JPanel _centerPanel, _arrowsPanel;

	private ButtonsPanel _validatePanel;

	private DxEditEventDlg _dxEEventDlg;

//	private EditEventDlg _eEventDlg;

	private Vector<String> _rightVec;
	private Vector<String> _leftVec;

	private EditEventDlg _eEventDlg;

	// private JList _leftVec;

	// /*
	// * Constructeur
	// * il est utilisé par la classe EditActivityDlg pour manipuler
	// * les enseignants
	// * @param DApplication le noeud pere de l'application
	// * @param EditActivityDlg ead est le dialogue père
	// * @param Vector leftVec est le vecteur contenant les enseignants de la
	// liste gauche
	// * @param Vector rightVec est le vecteur contenant les enseignants de la
	// liste droite
	// */
	// public SelectInstructors(DApplication dApplic, DxEditActivityDlg dxEad,
	// Vector leftVec, Vector rightVec) {
	// super(dApplic.getJFrame(), DConst.LISTS_INSTRUCTOR_TD, true); //true
	// gives a modal Dlg
	// //_dApplic = dApplic;
	// _dxEad = dxEad;
	// _leftVec = leftVec;
	// //_leftVec.
	// _rightVec = rightVec;
	// for (int i = 0; i < _leftVec.size(); i++)
	// _rightVec.remove(_leftVec.get(i).toString());
	// initialize();
	// setLocationRelativeTo(dApplic.getJFrame());
	// setVisible(true);
	// }

	// /*
	// * Constructeur
	// * il est utilisé par la classe EditEventDlg pour manipuler
	// * les enseignants
	// * @param DApplication le noeud pere de l'application
	// * @param EditEventDlg eEventd est le dialogue père
	// * @param Vector leftVec est le vecteur contenant les enseignants de la
	// liste gauche
	// * @param Vector rightVec est le vecteur contenant les enseignants de la
	// liste droite
	// */
	// public SelectInstructors(DApplication dApplic, DxEditEventDlg
	// dxEEventDlg,
	// Vector leftVec, Vector rightVec) {
	//		
	// super(dApplic.getJFrame(), DConst.LISTS_INSTRUCTOR_TD, true); //true
	// gives a modal Dlg
	// // int FACTOR = 50;
	// _dxEEventDlg = dxEEventDlg;
	// _leftVec = leftVec;
	// _rightVec = rightVec;
	// for (int i = 0; i < _leftVec.size(); i++)
	// _rightVec.remove(_leftVec.get(i).toString());
	//		
	// // this.setBounds(screenSize.width / 4, screenSize.height / 4,
	// // screenSize.width / 3, screenSize.height / 2 + FACTOR);initialize();
	// setLocationRelativeTo(dApplic.getJFrame());
	// // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//		
	// setVisible(true);
	// }

	public SelectInstructors(DxEditEventDlg dxEEventDlg,
			Vector<String> leftVec, Vector<String> rightVec) {

		super(dxEEventDlg, DConst.LISTS_INSTRUCTOR_TD, true); // true gives a
		// modal Dlg
		int FACTOR = 50;
		_dxEEventDlg = dxEEventDlg;
		_leftVec = leftVec;
		_rightVec = rightVec;
		for (int i = 0; i < _leftVec.size(); i++)
			_rightVec.remove(_leftVec.get(i).toString());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(screenSize.width / 6, screenSize.height / 4,
				screenSize.width / 3, screenSize.height / 2 + FACTOR);
		initialize();
		setLocationRelativeTo(dxEEventDlg);

		setVisible(true);
	}

	public SelectInstructors(EditEventDlg eEventDlg, Vector<String> leftVec,
			Vector<String> rightVec) {

		super(eEventDlg, DConst.LISTS_INSTRUCTOR_TD, true); // true gives a
		// modal Dlg
		int FACTOR = 50;
		_eEventDlg = eEventDlg;
		_leftVec = leftVec;
		_rightVec = rightVec;
		for (int i = 0; i < _leftVec.size(); i++)
			_rightVec.remove(_leftVec.get(i).toString());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(screenSize.width / 6, screenSize.height / 4,
				screenSize.width / 3, screenSize.height / 2 + FACTOR);
		initialize();
		setLocationRelativeTo(eEventDlg);

		setVisible(true);
	}

	// /*
	// * Constructeur
	// * il est utilisé par la classe EditEventDlg pour manipuler
	// * les enseignants
	// * @param DApplication le noeud pere de l'application
	// * @param EditEventDlg eEventd est le dialogue père
	// * @param Vector leftVec est le vecteur contenant les enseignants de la
	// liste gauche
	// * @param Vector rightVec est le vecteur contenant les enseignants de la
	// liste droite
	// */
//	public SelectInstructors(DApplication dApplic, Vector<String> leftVec,
//			Vector<String> rightVec) {
//		super(dApplic.getJFrame(), DConst.LISTS_INSTRUCTOR_TD, true); // true
//		// gives
//		// a
//		// modal
//		// Dlg
//		// _dApplic = dApplic;
//		// _eEventDlg = eEventd;
//		_leftVec = leftVec;
//		_rightVec = rightVec;
//		for (int i = 0; i < _leftVec.size(); i++)
//			_rightVec.remove(_leftVec.get(i).toString());
//		initialize();
//		setLocationRelativeTo(dApplic.getJFrame());
//		setVisible(true);
//	}

	// public SelectInstructors(DApplication applic, EditEventDlg dlg, Vector
	// vector, Vector vector2) {
	// // TODO Auto-generated constructor stub
	// }

	/**
	 * Initialize the dialog
	 */
	protected void initialize() {
		// _chooser = new JComboBox(_soi.getNameSortedRessources());
		_rightList = new JList(_rightVec);
		_rightList.addMouseListener(mouseListenerLists);
		JPanel listPanel = DxTools.listPanel(_rightList, 150, 300);
		_lNoVisible = new JLabel(_rightVec.size() + " " + DConst.NOT_INCLUDED);
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(_lNoVisible, BorderLayout.NORTH);
		rightPanel.add(listPanel, BorderLayout.SOUTH);
		// left panel
		_leftList = new JList(_leftVec);
		_leftList.addMouseListener(mouseListenerLists);
		_lVisible = new JLabel(_leftVec.size() + " " + DConst.INCLUDED);
		listPanel = DxTools.listPanel(_leftList, 150, 300);
		JPanel leftPanel = new JPanel();
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(_lVisible, BorderLayout.NORTH);
		leftPanel.add(listPanel, BorderLayout.SOUTH);
		// arrows panel private
		String[] _arrows = { DConst.TO_RIGHT, DConst.TO_LEFT };
		_arrowsPanel = DxTools.arrowsPanel(this, _arrows, true);
		// placing the panels and buttons into the _listsPanel
		_centerPanel = new JPanel();
		_centerPanel.add(leftPanel, BorderLayout.EAST);
		_centerPanel.add(_arrowsPanel, BorderLayout.CENTER);
		_centerPanel.add(rightPanel, BorderLayout.WEST);
		// _applyPanel
		String[] a = { DConst.BUT_VALIDATE, DConst.BUT_CLOSE };
		_validatePanel = new TwoButtonsPanel(this, a);
		// Setting the button VALIDATE disable
		_validatePanel.setFirstDisable();
		// placing the elements into the JDialog
		setSize(400, 390);
		setResizable(false);
		getContentPane().add(_centerPanel, BorderLayout.CENTER);
		getContentPane().add(_validatePanel, BorderLayout.SOUTH);
	}// end method

	/**
	 * Define the mouse adapter and actions for the JLists the actions are
	 * select and deselect items in the JLists
	 */
	private MouseListener mouseListenerLists = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (((JList) e.getSource()).getModel().getSize() == 0) {
				return;
			}
			if (e.getSource().equals(_leftList)) {
				_rightList.clearSelection();
			} else {
				_leftList.clearSelection();
			}
		}// end public void mouseClicked
	};// end definition of MouseListener mouseListener = new MouseAdapter(){

	/**
	 * 
	 * @param e
	 *            an event
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(DConst.BUT_CLOSE))
			dispose();
		if (command.equals(DConst.BUT_VALIDATE)) {
			if (DxFlags.newDxEditEventDlg) {
				_dxEEventDlg.updateInstructorList(_leftVec);
		} else {
				_eEventDlg.updateInstructorList(_leftVec);
		}
			_validatePanel.setFirstDisable();
			dispose();

		}
		if (command.equals(DConst.TO_RIGHT) || command.equals(DConst.TO_LEFT)) {
			if (command.equals(DConst.TO_LEFT)) {
				DxTools.listTransfers(_rightList, _leftList, _rightVec,
						_leftVec, 1);
			} else {
				DxTools.listTransfers(_leftList, _rightList, _leftVec,
						_rightVec, 1);
			}
			_lNoVisible.setText(_rightVec.size() + " " + DConst.NOT_INCLUDED);
			_lVisible.setText(_leftVec.size() + " " + DConst.INCLUDED);
			_validatePanel.setFirstEnable();
		}// end if (command.equals(_arrowsNames[0]) ||
		// command.equals(_arrowsNames[1]))
	}// end method

}// end class
