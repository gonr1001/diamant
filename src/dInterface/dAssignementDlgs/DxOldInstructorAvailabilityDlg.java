package dInterface.dAssignementDlgs;

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
import dInterface.DlgIdentification;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.dData.dInstructors.DxInstructor;
import dInternal.dData.dInstructors.DxSetOfInstructors;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: AvailabilityInstructorDlg is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
@SuppressWarnings("serial")
public class DxOldInstructorAvailabilityDlg extends JDialog implements
		ActionListener, ItemListener, DlgIdentification {

	private ButtonsPanel _applyPanel;

	private JPanel _chooserPanel;

	private JPanel _centerPanel;

	private JComboBox _chooser;

	private Vector<JToggleButton> _posVect;

	private DModel _dmodel;

	private DxSetOfInstructors _soi;

	private int[][] _currentAvailbility;

	private DxInstructor _currentInst;

	// private boolean _isMultiSite;

	/**
	 * 
	 * @param dApplic
	 *            The component on which the dialog will be displayed.
	 * @param soi
	 *            The setOfResources to be displayed.
	 */
	public DxOldInstructorAvailabilityDlg(DApplication dApplic,
			DxSetOfInstructors soi) {
		super(dApplic.getJFrame(), DConst.INST_ASSIGN_TD, false);

		if (dApplic.getCurrentDxDoc() == null)
			return;
		_dmodel = dApplic.getCurrentDModel();
		_soi = soi;

		try {
			initialize();
			pack();
			setLocationRelativeTo(dApplic.getJFrame());
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end DxInstructorAvailabilityDlg

	/**
	 * Component's initialization and placement.
	 */
	private void initialize() {
		_chooserPanel = new JPanel();
		// creates the JComboBox with the list of all instructors
		_chooser = new JComboBox(_soi.getNameSortedRessources());
		_currentInst = (DxInstructor) _chooser.getSelectedItem();
		_chooser.addItemListener(this);
		_chooserPanel.add(_chooser, null);
		this.getContentPane().add(_chooserPanel, BorderLayout.NORTH);

		// gridPanel
		_centerPanel = makeGridPanel();
		this.getContentPane().add(_centerPanel, BorderLayout.CENTER);

		// _applyPanel
		String[] butnames = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_applyPanel = new TwoButtonsPanel(this, butnames);
		// Setting the button APPLY disable
		_applyPanel.setFirstDisable();
		this.getContentPane().add(_applyPanel, BorderLayout.SOUTH);
	} // end initialize()

	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals(DConst.BUT_CLOSE)) { 
			dispose();
		} else if (command.equals(DConst.BUT_APPLY)) {
			_applyPanel.setFirstDisable();
			_currentInst = ((DxInstructor) _chooser.getSelectedItem());
			_currentInst.setAvailability(_currentAvailbility);
			_dmodel.changeInDModel(this.idDlgToString());
			// if a button of the grid has been pressed
		} else if (_posVect.indexOf(event.getSource()) > -1) {
			int index = _posVect.indexOf(event.getSource());
			int nbOfPeriods = _dmodel.getTTStructure().getCurrentCycle()
					.getMaxNumberOfPeriodsADay();
			int day = index / nbOfPeriods;
			int per = index % nbOfPeriods;
			if (_posVect.get(index).isSelected()) {
				_currentAvailbility[day][per] = 1;
			} else {
				_currentAvailbility[day][per] = 5;
			}
			_applyPanel.setFirstEnable();
		}
	}

	/**
	 * JComboBox item selected
	 */
	public void itemStateChanged(ItemEvent event) {
		_applyPanel.setFirstDisable();
		if (event.getStateChange() == ItemEvent.SELECTED) {
			Object source = event.getSource();
			if (source.equals(_chooser)) {
				getContentPane().remove(_centerPanel);
				_currentInst = ((DxInstructor) _chooser.getSelectedItem());
				_centerPanel = makeGridPanel();
				getContentPane().add(_centerPanel, BorderLayout.CENTER);
				pack();
			}
		}
	}// end itemStateChanged

	/**
	 * Creates the grid of button. The button is pressed if the instructor is
	 * free at that period, depressed if not.
	 * 
	 * @param instr
	 *            the instructor for which the grid is constructed.
	 */
	private JPanel makeGridPanel() {
		String[] time = _dmodel.getTTStructure().getCurrentCycle()
				.getHourOfPeriodsADay();
		int nbOfPeriods = _dmodel.getTTStructure().getCurrentCycle()
				.getMaxNumberOfPeriodsADay();
		int nbOfDays = _dmodel.getTTStructure().getNumberOfActiveDays();
		String[] _days = _dmodel.getTTStructure().getDayNames();
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(nbOfPeriods + 1, nbOfDays + 1));
		gridPanel.setBorder(BorderFactory
				.createTitledBorder(DConst.AVAILABILITIES));
		_posVect = new Vector<JToggleButton>();
		_posVect.setSize((nbOfPeriods + 1) * (nbOfDays + 1));
		gridPanel.add(new JLabel("")); // top left corner
		for (int i = 0; i < _days.length; i++){
			// first line : name of days
			gridPanel.add(new JLabel(_days[i], SwingConstants.CENTER));
		}
		_currentAvailbility = _currentInst.getAvailability()
				.getMatrixAvailability();

		formatGrid(time, nbOfPeriods, nbOfDays, gridPanel);
		return gridPanel;
	}

	/**
	 * @param time
	 * @param nbOfPeriods
	 * @param nbOfDays
	 * @param gridPanel
	 */
	private void formatGrid(String[] time, int nbOfPeriods, int nbOfDays,
			JPanel gridPanel) {
		for (int j = 0; j < nbOfPeriods; j++) {
			// first column : the time of the period
			gridPanel.add(new JLabel(time[j], SwingConstants.RIGHT));
			// create a button for each day for the period
			inFor(nbOfPeriods, nbOfDays, gridPanel, j);
		}
	}

	/**
	 * @param nbOfPeriods
	 * @param nbOfDays
	 * @param gridPanel
	 * @param j
	 */
	private void inFor(int nbOfPeriods, int nbOfDays, JPanel gridPanel, int j) {
		for (int i = 0; i < nbOfDays; i++) {
			JToggleButton tBut = new JToggleButton();
			if (_currentAvailbility[i][j] == 1) {
				Vector<String> assignedSites = _currentInst.getAvailability()
						.isAssignedInPeriod(i, j, _dmodel.getOtherSites());
				if (assignedSites.size() != 0) {
					Color col = this.getGridColor(assignedSites
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
			gridPanel.add(tBut);
			_posVect.setElementAt(tBut, (i * nbOfPeriods) + j);
		}
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

	public String idDlgToString() {
		return this.getClass().toString();
	}
} /* end DxOldAvailabilityInstructorDlg */
