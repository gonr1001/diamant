package dInterface.dAssignementDlgs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
public class DxInstructorAvailabilityDlg extends JDialog implements
		ActionListener, ItemListener, DlgIdentification {
	
	private final int AVAILABLE= 1;
	private final int NOT_AVAILABLE= 5;
	
	private final int EXTRA= 3;
	private final int CORNERS= 2;
	
	private final int BUTTON_WIDTH= 52;
	private final int BUTTON_HEIGHT= 12;

	private ButtonsPanel _applyPanel;
	
	private JPanel _topPanel;

	private JPanel _chooserPanel;

	private JPanel _centerPanel;

	private JComboBox _chooser;

	private Vector<JToggleButton> _availabilityVector;
	
	private Vector<JButton> _topVector;
	
	private Vector<JButton> _bottomVector;
	
	private Vector<JButton> _leftVector;
	
	private Vector<JButton> _rightVector;
	
	private Vector<JButton> _topCorners;
	
	private Vector<JButton> _bottomCorners;

	private DModel _dmodel;

	private DxSetOfInstructors _soi;

	private int[][] _currentAvailability;

	private DxInstructor _currentInst;
	
	 JToggleButton _available;
	 JToggleButton _notAvailable;

	// private boolean _isMultiSite;

	/**
	 * 
	 * @param dApplic
	 *            The component on which the dialog will be displayed.
	 * @param soi
	 *            The setOfResources to be displayed.
	 * TODO assert that parameters are not null
	 *            
	 */
	public DxInstructorAvailabilityDlg(DApplication dApplic,
			DxSetOfInstructors soi) {
		super(dApplic.getJFrame(), DConst.INST_ASSIGN_TD, false);

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
		_topPanel = new JPanel(new BorderLayout());
		_chooserPanel = new JPanel();
		// creates the JComboBox with the list of all instructors
		_chooser = new JComboBox(_soi.getNameSortedRessources());
		_currentInst = (DxInstructor) _chooser.getSelectedItem();
		_chooser.addItemListener(this);
		_chooserPanel.add(_chooser, null);
		_topPanel.add(_chooserPanel,BorderLayout.NORTH);
		
		boolean set = true;
		 _available = new JToggleButton("disponible", set);
		 _available.setActionCommand("AVAILABLE");
		 _available.addActionListener(this);
		 _available.setPreferredSize(new Dimension(BUTTON_WIDTH *2, BUTTON_HEIGHT));
		 _notAvailable = new JToggleButton("not disponible");
		 _notAvailable.setActionCommand("NOT_AVAILABLE");
		 _notAvailable.addActionListener(this);
		 _notAvailable.setPreferredSize(new Dimension(BUTTON_WIDTH*2, BUTTON_HEIGHT));
		
		
		 _topPanel.add(_available, BorderLayout.WEST);
		 _topPanel.add(_notAvailable, BorderLayout.EAST);
		
		
		
		this.getContentPane().add(_topPanel, BorderLayout.NORTH);

		// gridPanel which contains augmented grid + legend
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
		} 			
		if (command.equals(DConst.BUT_APPLY)) {
			inButtonApply();
			// if a button of the grid has been pressed
		} else if (_availabilityVector.indexOf(event.getSource()) > -1) {
			inAvailability(event);
		} else if (_topVector.indexOf(event.getSource()) > -1) {
			inTopVector(event);			
		} else if (_bottomVector.indexOf(event.getSource()) > -1) {
			inBottomVector(event);
		} else if (_leftVector.indexOf(event.getSource()) > -1) {
			inLeftVector(event);			
		} else if (_rightVector.indexOf(event.getSource()) > -1) {
			inRightVector(event);
		} else if (_topCorners.indexOf(event.getSource()) > -1) {
			inTopCorners(event);
		}else if (_bottomCorners.indexOf(event.getSource()) > -1) {
			inButtomCorners();
		} 
		if (command.equals("AVAILABLE")) {
			changeTo(_available, true);
			System.out.println("here Ava");
		}
		if (command.equals("NOT_AVAILABLE")) {
			changeTo(_notAvailable, false);
			System.out.println("here not Ava");
			
		}
	}


	private void changeTo(JToggleButton tb, boolean b) {
		tb.setSelected(b);
		tb.repaint();	
	}

	private void inTopVector(ActionEvent event) {
		int index = _topVector.indexOf(event.getSource());
		changeColumn(index, AVAILABLE);
		reDisplayAvailability();
		_applyPanel.setFirstEnable();
	}

	private void inBottomVector(ActionEvent event) {
		int index = _bottomVector.indexOf(event.getSource());
		changeColumn(index, NOT_AVAILABLE);
		reDisplayAvailability();
		_applyPanel.setFirstEnable();
	}
	
	private void inLeftVector(ActionEvent event) {
		int index = _leftVector.indexOf(event.getSource());
		changeLine(index, AVAILABLE);
		reDisplayAvailability();
		_applyPanel.setFirstEnable();
	}

	private void inRightVector(ActionEvent event) {
		int index = _rightVector.indexOf(event.getSource());
		changeLine(index, NOT_AVAILABLE);
		reDisplayAvailability();
		_applyPanel.setFirstEnable();
	}
	
	private void changeColumn(int index, int value) {
		for (int j = 0; j < _currentAvailability[index].length; j++) {
			_currentAvailability[index][j] = value;
		}
	}
	
	private void changeLine(int indexJ, int value) {
		for (int i = 0; i < _currentAvailability.length; i++) {
			_currentAvailability[i][indexJ] = value;
		}	
	}
	
	private void inAvailability(ActionEvent event) {				
		int index = _availabilityVector.indexOf(event.getSource());
		int nbOfPeriods = _dmodel.getTTStructure().getCurrentCycle()
				.getMaxNumberOfPeriodsADay();
		int day = index / nbOfPeriods;
		int per = index % nbOfPeriods;
		if (_availabilityVector.get(index).isSelected()) {
			_currentAvailability[day][per] = AVAILABLE;
		} else {
			_currentAvailability[day][per] = NOT_AVAILABLE;
		}
		reDisplayAvailability();//nbOfPeriods, nbOfDays);
		_applyPanel.setFirstEnable();		
	}

	private void inButtonApply() {
		_applyPanel.setFirstDisable();
		_currentInst = ((DxInstructor) _chooser.getSelectedItem());
		_currentInst.setAvailability(_currentAvailability);
		_dmodel.changeInDModel(this.idDlgToString());
	}
	
	
	private void inTopCorners(ActionEvent event) {
		int index = _topCorners.indexOf(event.getSource());
		if (index == 0) {
			changeAll(AVAILABLE);
		}else {
			changeAll(NOT_AVAILABLE);
		}
		reDisplayAvailability();
		_applyPanel.setFirstEnable();
	}

	
	private void inButtomCorners() {
		invertAvailbility();
		reDisplayAvailability();
		_applyPanel.setFirstEnable();
	}

	
	private void changeAll(int value) {
		for (int i = 0; i < _currentAvailability.length; i++) {
			for (int j = 0; j < _currentAvailability[i].length; j++) {
				_currentAvailability[i][j] = value;
			}
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
		String[] days = _dmodel.getTTStructure().getDayNames();
		
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(nbOfPeriods + EXTRA, nbOfDays + EXTRA));
		gridPanel.setBorder(BorderFactory
				.createTitledBorder(DConst.AVAILABILITIES));
		_availabilityVector = new Vector<JToggleButton>();
		_availabilityVector.setSize((nbOfPeriods) * (nbOfDays));
		displayDays(days, gridPanel);
		displayGrid(time, nbOfPeriods, nbOfDays, gridPanel);
		return gridPanel;
	}



	private void displayDays(String[] days, JPanel gP) {
		gP.add(new JLabel("")); // top left corner
		gP.add(new JLabel("")); // next top left corner
		for (int i = 0; i < days.length; i++){
			// first line : name of days
			gP.add(new JLabel(days[i], SwingConstants.CENTER));
		}
		gP.add(new JLabel("")); // top right corner
	}

	
	private void displayGrid(String[] time, int nbOfPeriods, int nbOfDays,
			JPanel gridPanel) {
		displayTopButtons(nbOfDays, gridPanel);		
		displayCentralGrid(time, nbOfPeriods, nbOfDays, gridPanel);	
		displayBottomButtons(nbOfDays, gridPanel);	
	}


	private void displayTopButtons(int nbOfDays, JPanel gridPanel) {
		_topCorners	= new Vector <JButton>();
		_topCorners.setSize(CORNERS);
		_topVector = new Vector<JButton>();
		_topVector.setSize(nbOfDays);
		gridPanel.add(new JLabel("")); 
		JButton tBut = new JButton("t d");
		tBut.addActionListener(this);
		tBut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		gridPanel.add(tBut);
		_topCorners.set(0, tBut);
		for(int i= 0; i < nbOfDays; i++) {
			tBut = new JButton("d");
			tBut.addActionListener(this);
			tBut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			gridPanel.add(tBut);
			_topVector.setElementAt(tBut,i);			
		}
		tBut = new JButton("tnd");
		tBut.addActionListener(this);
		tBut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		gridPanel.add(tBut);
		_topCorners.set(1, tBut);
	}
	
	
	
	private void displayBottomButtons(int nbOfDays, JPanel gridPanel) {
		_bottomCorners	= new Vector <JButton>();
		_bottomCorners.setSize(CORNERS);
		_bottomVector = new Vector<JButton>();
		_bottomVector.setSize(nbOfDays);
		gridPanel.add(new JLabel("")); 
		JButton tBut = new JButton("inv");
		tBut.addActionListener(this);
		tBut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		gridPanel.add(tBut);
		_bottomCorners.set(0,tBut);
		for(int i= 0; i < nbOfDays; i++) {
			tBut = new JButton("nd");
			tBut.addActionListener(this);
			tBut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			gridPanel.add(tBut);
			_bottomVector.setElementAt(tBut,i);			
		}
		tBut = new JButton("inv");
		tBut.addActionListener(this);
		tBut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		gridPanel.add(tBut);
		_bottomCorners.set(1,tBut);
	}
	
	

	private void displayCentralGrid(String[] time, int nbOfPeriods,
			int nbOfDays, JPanel gridPanel) {
		_leftVector = new Vector<JButton>();
		_leftVector.setSize(nbOfPeriods);
		_rightVector = new Vector<JButton>();
		_rightVector.setSize(nbOfPeriods);
		for (int j = 2; j < nbOfPeriods  + 2; j++) {
			displayLine(time[j-2], nbOfPeriods,
					nbOfDays, gridPanel, j);		
		}
		
	}
	
	
	private void displayLine(String string, int nbOfPeriods, int nbOfDays,
			JPanel gridPanel, int j) {
		// first column : the time of the period
		gridPanel.add(new JLabel(string, SwingConstants.RIGHT));
		
		// first bottom in column : line Available
		JButton leftBut = new JButton("d");
		leftBut.addActionListener(this);
		leftBut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		gridPanel.add(leftBut);
		_leftVector.setElementAt(leftBut, j-2);
		
		middleOfLine(nbOfPeriods, nbOfDays, gridPanel, j-2);
		
		// last column in column : the time of the period
		JButton rightBut = new JButton("nd");
		rightBut.addActionListener(this);
		rightBut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		gridPanel.add(rightBut);	
		_rightVector.setElementAt(rightBut, j-2);
	}

	private void middleOfLine(int nbOfPeriods, int nbOfDays, JPanel gridPanel, int j) {
		_currentAvailability = _currentInst.getAvailability()
		.getMatrixAvailability();
		for (int i = 0; i < nbOfDays; i++) {
			JToggleButton tBut = new JToggleButton();
					tBut.setSelected(_currentAvailability[i][j] == 1);
			tBut.addActionListener(this);
			tBut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			gridPanel.add(tBut);
			_availabilityVector.set( (i * nbOfPeriods) + j, tBut);
		}
	}

	
	private void reDisplayAvailability(){

		for (int j = 0; j < _currentAvailability[0].length; j++) {
			for (int i = 0; i < _currentAvailability.length; i++) {
				JToggleButton tBut = _availabilityVector
						.elementAt((i * _currentAvailability[0].length) + j);
				if (_currentAvailability[i][j] == AVAILABLE) {
					tBut.setSelected(true);
					tBut.repaint();
				} else {
					tBut.setSelected(false);
					tBut.repaint();
				}
			}
		}
	}
	

	
	private void invertAvailbility() {
		for(int i = 0; i < _currentAvailability.length; i++) {
			for(int j = 0; j < _currentAvailability[0].length; j++) {
				if (_currentAvailability[i][j] == AVAILABLE){
					_currentAvailability[i][j] = NOT_AVAILABLE;
				}  else {
					_currentAvailability[i][j] = AVAILABLE;
				}
			}			
		}
	}
	
	
	// TODO this was old methods used i multisite timetable
	// keep as inspiration source when multisite come again
//	private void processLine(int nbOfPeriods, int nbOfDays, JPanel gridPanel, int j) {
//		for (int i = 0; i < nbOfDays; i++) {
//			JToggleButton tBut = new JToggleButton();
//			if (_currentAvailability[i][j] == 1) {
//				Vector<String> assignedSites = _currentInst.getAvailability()
//						.isAssignedInPeriod(i, j, _dmodel.getOtherSites());
//				if (assignedSites.size() != 0) {
//					Color col = this.getGridColor(assignedSites
//							.get(0));
//					if (col == Color.RED || col == Color.BLUE
//							|| col == Color.GREEN) {
//						tBut.setToolTipText(DConst.NOT_DISPO);
//					}
//					tBut.setBackground(col);
//					tBut.setEnabled(false);
//				} else
//					tBut.setSelected(_currentAvailability[i][j] == 1);
//			}
//			tBut.addActionListener(this);
//			tBut.setPreferredSize(new Dimension(50, 12));
//			gridPanel.add(tBut);
//			_availabilityVector.setElementAt(tBut, (i * nbOfPeriods) + j);
//		}
//	}
//
//	private Color getGridColor(String site) {
//		if (site.equalsIgnoreCase(DConst.USEDSHE)) {
//			return Color.RED;
//		} else if (site.equalsIgnoreCase(DConst.USEDLON)) {
//			return Color.BLUE;
//		} else if (site.equalsIgnoreCase(DConst.USEDCOW)) {
//			return Color.GREEN;
//		}
//		return Color.GRAY;
//	}

	public String idDlgToString() {
		return this.getClass().toString();
	}
} /* end DxOldAvailabilityInstructorDlg */
