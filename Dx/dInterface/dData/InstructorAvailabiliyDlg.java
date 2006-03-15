/**
 *
 * Title: InstructorAvailabiliyDlg 
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
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */
package dInterface.dData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.dData.AvailabilityAttach;
import dInternal.dDlgModel.InstructorAvailabilityDlgModel;

/**
 * Dialog used to set disponibilities for an instructor. The user must select
 * the corresponding instructor in the combo list and click on a period to
 * select/deselect it. A selected period (pressed) means that the teacher is
 * available at that period.
 * 
 * User must be warned that changes made in this dialog box is only used by
 * DIAMANT and so does not represent what data is actually in the central
 * database. In fact, as long as a difference exists between both sets of data,
 * user must be warned of this difference in the import/export report...
 * 
 * The grid for each instructor is constructed to follow the standard model
 * proposed by the STI
 * 
 * 
 */
public class InstructorAvailabiliyDlg extends JDialog implements
		ActionListener, ItemListener {

	private DApplication _dApplic;

	private int _nbOfDays;

	private InstructorAvailabilityDlgModel _iaDlgModel;

	private ButtonsPanel _applyPanel;

	private JPanel _centerPanel;

	private JComboBox _chooser;

	/**
	 * @associates JToggleButton
	 */
	private Vector _posVect;

	private AvailabilityAttach _currentInstr;

	private int[][] _currentAvailbility;

	/**
	 * Constructor.
	 * 
	 * @param dApplication
	 *            The component on which the dialog will be displayed.
	 *
	 */
	public InstructorAvailabiliyDlg(DApplication dApplication) {
		super(dApplication.getJFrame(), DConst.INST_ASSIGN_TD, false);
		_dApplic = dApplication;
		if (_dApplic.getCurrentDModel() == null)
			return;
		_iaDlgModel = _dApplic.getCurrentDModel().getIADlgModel();

		_nbOfDays = _dApplic.getCurrentDModel().getTTStructure()
				.getNumberOfActiveDays();

		//try {
			initialize();
			pack();
			setLocationRelativeTo(_dApplic.getJFrame());
			setVisible(true);
		//} catch (Exception e) {
			// TODO process exceptions
		//	e.printStackTrace();
		//}
	} // end InstructorAvailabiliyDlg

	/**
	 * Component's initialisation and placement.
	 */
	private void initialize(){ // throws Exception {
		JPanel chooserPanel = new JPanel();
		// creates the JComboBox with the list of all instructors
		_chooser = new JComboBox(_iaDlgModel.getInstructorsNames());
		_chooser.addItemListener(this);
		chooserPanel.add(_chooser, null);
		this.getContentPane().add(chooserPanel, BorderLayout.NORTH);

		// gridPanel
		String sel = (String) _chooser.getSelectedItem();
		//TODO change
		_currentInstr = (AvailabilityAttach) _dApplic.getCurrentDModel()
				.getSetOfInstructors().getResource(sel).getAttach();
		_centerPanel = makeGridPanel();
		this.getContentPane().add(_centerPanel, BorderLayout.CENTER);

		// _applyPanel
		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE }; 
		_applyPanel = new TwoButtonsPanel(this, a);
		// Setting the button APPLY disable
		_applyPanel.setFirstDisable();
		this.getContentPane().add(_applyPanel, BorderLayout.SOUTH);
	} // end initialize()

	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals(DConst.BUT_CLOSE)) { // close
			dispose();
		} else if (command.equals(DConst.BUT_APPLY)) { // apply

			_applyPanel.setFirstDisable();
			//TODO change
			//_currentInstr.setAvailability(_currentAvailbility);
			_dApplic.getCurrentDModel().changeInDModelByInstructorsDlg(this);
			// if a button of the grid has been pressed
		} else if (_posVect.indexOf(event.getSource()) > -1) {
			int index = _posVect.indexOf(event.getSource());
			int day = index / _iaDlgModel.getMaxNbOfPeriods();
			int per = index % _iaDlgModel.getMaxNbOfPeriods();
			if (((JToggleButton) _posVect.get(index)).isSelected()) {
				_currentAvailbility[day][per] = 1;
			} else {
				_currentAvailbility[day][per] = 5;
			}
			_applyPanel.setFirstEnable();
		}
	}

	/**
	 * combobox item selected
	 */
	public void itemStateChanged(ItemEvent event) {
		_applyPanel.setFirstDisable();
		if (event.getStateChange() == ItemEvent.SELECTED) {
			Object source = event.getSource();
			if (source.equals(_chooser)) {
				getContentPane().remove(_centerPanel);
				String sel = (String) _chooser.getSelectedItem();
				_currentInstr = (AvailabilityAttach) _dApplic
						.getCurrentDModel().getSetOfInstructors().getResource(
								sel).getAttach();
				_centerPanel = makeGridPanel();// _currentInstr);
				getContentPane().add(_centerPanel, BorderLayout.CENTER);
				pack();
			}
		}
	}// end itemStateChangeed

	/**
	 * Creates the grid of button. The button is pressed if the instructor is
	 * free at that period, depressed if not.
	 * 
	 * @param instr
	 *            the instructor for which the grid is constructed.
	 */
	private JPanel makeGridPanel(/* InstructorAttach instr */) {
		JPanel gridPanel = new JPanel();
		int maxNbOfPeriods = _iaDlgModel.getMaxNbOfPeriods();
		gridPanel.setLayout(new GridLayout(maxNbOfPeriods + 1, _nbOfDays + 1));
		gridPanel.setBorder(BorderFactory
				.createTitledBorder(DConst.AVAILABILITIES));
		_posVect = new Vector();
		_posVect.setSize((maxNbOfPeriods + 1) * (_nbOfDays + 1));
		gridPanel.add(new JLabel("")); // top left corner
		String[] days = _iaDlgModel.getDays();
		for (int i = 0; i < days.length; i++)
			// first line : name of days
			gridPanel.add(new JLabel(days[i], SwingConstants.CENTER));

		_currentAvailbility = _currentInstr.getMatrixAvailability();
		String[] hours = _iaDlgModel.getHours();
		for (int j = 0; j < maxNbOfPeriods; j++) {
			// first column : the time of the period

			gridPanel.add(new JLabel(hours[j], SwingConstants.RIGHT));
			// create a button for each day for the period
			for (int i = 0; i < _nbOfDays; i++) {
				JToggleButton tBut = new JToggleButton();
				if (_currentAvailbility[i][j] == 1) {
					Vector assignedSites = _currentInstr.isAssignedInPeriod(i,
							j, _dApplic.getCurrentDModel().getOtherSites());
					if (assignedSites.size() != 0) {
						Color col = this.getGridColor((String) assignedSites
								.get(0));
						if (col == Color.RED || col == Color.BLUE
								|| col == Color.GREEN) {
							tBut.setToolTipText(DConst.NOT_DISPO);
						}
						tBut.setBackground(col);
						tBut.setEnabled(false);
					} else
						tBut.setSelected(_currentAvailbility[i][j] == 1);
				}
				tBut.addActionListener(this);
				tBut.setPreferredSize(new Dimension(50, 12));
				gridPanel.add(tBut);// , null);
				_posVect.setElementAt(tBut, (i * maxNbOfPeriods) + j);
			}
		}
		return gridPanel;
	}

	private Color getGridColor(String site) {
		if (site.equalsIgnoreCase(DConst.USEDSHE)) {
			return Color.RED;
		} else if (site.equalsIgnoreCase(DConst.USEDLON)) {
			return Color.BLUE;
		} else if (site.equalsIgnoreCase(DConst.USEDCOW)) {
			return Color.GREEN;
		}
		return Color.GRAY;
	}

} /* end InstructorAvailabilityDlg */