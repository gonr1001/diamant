/**
 * Created on Feb 22, 2007
 * 
 * 
 * Title: DxEditEventDlg.java 
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
 * 
 */
package dInterface.dAssignementDlgs;

/**
 * * Created on Mar 25, 2005
 * 
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxEditEventDlg is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgIdentification;
import dInterface.dAffectation.SelectInstructors;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.Section;
import dInternal.dData.dActivities.Type;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSite;
import dInternal.dOptimization.DxEvent;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.dialog.InformationDlg;

public class DxEditEventDlg extends JDialog implements ActionListener,
		ChangeListener, DlgIdentification {

	private final String ACTION_CB_SITE = "aCBSITE";

	private final String ACTION_CB_TYPE = "aCBTYPE";

	private final String ACTION_CB_ROOM = "aCBROOM";

	private DModel _dModel;

	private JTabbedPane _tabbedPane;

	private JScrollPane _jScrollPane;

	private ButtonsPanel _applyPanel;

	private Vector _events; // contains event resource

	private JList[] _instructorsLists;

	public DxEditEventDlg(JDialog parent) {
		super(parent, DConst.T_AFFEC_DLG);
	}

	/**
	 * Constructor for EditActivityDlg in the case of one or more events
	 * 
	 * @param dialog
	 *            The parent dialog of this dialog
	 * @param dApplic
	 *            The application
	 * @param currentActivity
	 *            The activity choiced in the activityDialog
	 * @param canBeModified
	 */

	public DxEditEventDlg(JDialog dialog, DApplication dApplic,
			String currentActivity, boolean canBeModified) {
		// One activity or n events
		super(dialog, DConst.T_AFFEC_DLG /*+ "DxDXDX!!!!!!!!"*/);
		setLocationRelativeTo(dialog);
		_dModel = dApplic.getCurrentDModel();
		_events = getEventsVector(currentActivity);

		buildDlg(canBeModified);
		displayDlg();
	} // end DxEditActivityDlg

	public DxEditEventDlg(JFrame parent) {
		super(parent, DConst.T_AFFEC_DLG);
	}

	/**
	 * action performed
	 * 
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals(ACTION_CB_SITE)) {
			_applyPanel.setFirstDisable();
			//JPanel tpane = (JPanel) _tabbedPane.getSelectedComponent();
			//String siteName = getSelectedSite(tpane);
			_applyPanel.setFirstEnable();
		}

		if (command.equals(ACTION_CB_TYPE)) {
			_applyPanel.setFirstDisable();
			JPanel tpane = (JPanel) _tabbedPane.getSelectedComponent();
			String sSiteName = getSelectedSite(tpane);
			String sTypeName = getSelectedType(tpane);
			DxSite currentSite = _dModel.getDxSetOfSites().getSite(sSiteName);

			JComboBox cbTypes = getTypesJCB(tpane);
			cbTypes.removeActionListener(this);
			cbTypes.setSelectedItem(sTypeName);
			DefaultComboBoxModel dcbmRooms;

			if (sTypeName.equalsIgnoreCase("------")) {
				dcbmRooms = new DefaultComboBoxModel(currentSite
						.getAllRoomsNameSorted());
			} else {
				DxCategory currentCat = _dModel.getDxSetOfSites().getCat(
						sSiteName, sTypeName);
				dcbmRooms = new DefaultComboBoxModel(currentCat
						.getSetOfDxRooms().getRoomsNameSorted());
			}

			JComboBox cbRooms = getRoomsJCB(tpane);
			cbRooms.removeActionListener(this);
			cbRooms.setModel(dcbmRooms);
			cbRooms.setSelectedItem("------");
			int capacity = 0;
			JLabel jl = getCapacityLabel(tpane);
			jl.setText(new Integer(capacity).toString());
			cbRooms.addActionListener(this);
			cbTypes.addActionListener(this);
			this.repaint();
			_applyPanel.setFirstEnable();
		}
		DefaultComboBoxModel dcbmRooms;
		if (command.equals(ACTION_CB_ROOM)) {
			_applyPanel.setFirstDisable();
			JPanel tpane = (JPanel) _tabbedPane.getSelectedComponent();
			String sSiteName = getSelectedSite(tpane);
			String roomName = getSelectedRoom(tpane);
			DxSite currentSite = _dModel.getDxSetOfSites().getSite(sSiteName);

			if (roomName.equalsIgnoreCase("------")) {

				dcbmRooms = new DefaultComboBoxModel(currentSite
						.getAllRoomsNameSorted());

				JComboBox cbRooms = getRoomsJCB(tpane);
				cbRooms.removeActionListener(this);
				cbRooms.setModel(dcbmRooms);
				cbRooms.setSelectedItem("------");
				JComboBox cbTypes = getTypesJCB(tpane);
				cbTypes.removeActionListener(this);
				cbTypes.setSelectedItem("------");
				int capacity = 0;
				JLabel jl = getCapacityLabel(tpane);
				jl.setText(new Integer(capacity).toString());
				cbRooms.addActionListener(this);
				cbTypes.addActionListener(this);

			} else {
				String currentCatName = currentSite.getCatNameOfRoom(roomName);
				DxCategory currentCat = currentSite.getSetOfCat().getCat(
						currentCatName);
				DxRoom currentRoom = currentSite.getRoom(currentCat.getName(),
						roomName);
				dcbmRooms = new DefaultComboBoxModel(currentCat
						.getSetOfDxRooms().getRoomsNameSorted());
				JComboBox cbRooms = getRoomsJCB(tpane);
				cbRooms.removeActionListener(this);
				cbRooms.setModel(dcbmRooms);
				cbRooms.setSelectedItem(roomName);

				JComboBox cbTypes = getTypesJCB(tpane);
				cbTypes.removeActionListener(this);
				cbTypes.setSelectedItem(currentCatName);

				int capacity = currentRoom.getCapacity();
				JLabel jl = getCapacityLabel(tpane);
				jl.setText(new Integer(capacity).toString());

				cbRooms.addActionListener(this);
				cbTypes.addActionListener(this);
			}
			this.repaint();
			_applyPanel.setFirstEnable();
		}
		if (command.equals(DConst.STATE_AC)) {
			_applyPanel.setFirstEnable();
		}

		if (command.equals(DConst.BUT_CLOSE)) {
			dispose();
		} else if (command.equals(DConst.BUT_APPLY)) {
			boolean apply = false;
			// for (int i = 0; i < this._events.size(); i++) {
			apply = applyChanges();
			if (!apply) {
				new DxExceptionDlg(this, "Valeur erronée");
				// break;
				// }
				_applyPanel.setFirstDisable();
			} // end for
			if (apply) {
				_dModel.changeInDModelByEditActivityDlg(this);
			}

		} else if (command.equals("comboBoxChanged")
				|| command.equals(DConst.BUT_PLACE)
				|| command.equals(DConst.BUT_FIGE)) {// a comboBox has
			// changed
			_applyPanel.setFirstEnable();

		} else if (command.equals(DConst.BUT_CHANGE)) {// change instructors
			new SelectInstructors(
					this,
					makeVector(_instructorsLists[_tabbedPane.getSelectedIndex()]),
					buildInstructorList());
		}

	}

	/**
	 * apply change in a event
	 */
	private boolean applyChanges() {
		Cycle cycle = _dModel.getTTStructure().getCurrentCycle();
		DxEvent event = (DxEvent) ((DResource) _events.get(_tabbedPane
				.getSelectedIndex())).getAttach();
		_dModel.getConditionsTest().removeEventInTTs(_dModel.getTTStructure(),
				(DResource) _events.get(_tabbedPane.getSelectedIndex()), false);

		JPanel tpane = (JPanel) _tabbedPane.getSelectedComponent();
		String duration = getSelectedDuration(tpane);
		if ((!DXToolsMethods.isIntValue(duration))
				|| (Integer.parseInt(duration) < 0))
			return false;
		String day = getSelectedDay(tpane);
		String hour = getSelectedHour(tpane);

		JList instructor = getInstructorsList(tpane);
		ListModel lm = instructor.getModel();
		String intructorKeys = getInstructorKeys(lm);

		String room = getSelectedRoom(tpane);
	
		boolean assignBut = isAssignedButtonSelected(tpane);

		boolean fixedBut = isFixedButtonSelected(tpane); // =

		int[] daytime = {
				Integer.parseInt(DXToolsMethods.getToken(day, ".", 0)),
				Integer.parseInt(DXToolsMethods.getToken(hour, ":", 0)),
				Integer.parseInt(DXToolsMethods.getToken(hour, ":", 1)) };
		String periodKey = cycle.getPeriod(daytime);
		event.setDuration(Integer.parseInt(duration)
				* _dModel.getTTStructure().getPeriodLenght());
		event.setPeriodKey(periodKey);
		event.setInstructorKey(intructorKeys);
//		if (DxFlags.newRooms) {
			event.setRoomKey(Long.toString(_dModel.getDxSetOfRooms()
					.getRoomKeyByName(room)));
			event.setRoomName(room);
			System.out
					.println("DxE event1.1 room name: " + event.getRoomName());
//		} else {
//			event.setKey(2, Long.toString(getResourceKey(_dModel
//					.getSetOfRooms(), room)));
//		}
		event.setAssigned(assignBut);
		event.setPermanentState(fixedBut);

		Vector<Object> vect = new Vector<Object>();
		vect.add(_events.get(_tabbedPane.getSelectedIndex()));

		_dModel.getSetOfEvents().updateActivities(_dModel.getSetOfActivities(),
				vect);
		_dModel.getConditionsTest().addEventInTTs(_dModel.getTTStructure(),
				(DResource) _events.get(_tabbedPane.getSelectedIndex()), false);

		return true;
	}

	/**
	 * build the instructor list
	 * 
	 * @return
	 */
	private Vector buildCurrentInstructorList(DxEvent event) {
		Vector<String> v = new Vector<String>();

		DxSetOfInstructors soi = _dModel.getDxSetOfInstructors();
		long keys[] = event.getInstructorKey();
		for (int i = 0; i < keys.length; i++) {
			String sName = soi.getInstructorName(keys[i]);
			if (sName != null)
				v.add(sName);
		}
		return v;
	}

	private Vector<String> buildDayList() {
		Vector<String> list = new Vector<String>();
		Cycle cycle = _dModel.getTTStructure().getCurrentCycle();
		for (int i = 0; i < cycle.getSetOfDays().size(); i++)
			list.add(cycle.getSetOfDays().getResourceAt(i).getKey() + "."
					+ cycle.getSetOfDays().getResourceAt(i).getID());
		return list;
	}

	private JPanel buildDayPanel(DxEvent event) {
		JPanel dayPanel = new JPanel();
		Vector<String> vect = buildDayList();
		JComboBox dayCB = new JComboBox(vect);

		dayPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_DAY_NAME));
		dayPanel.add(dayCB);
		Cycle cycle = _dModel.getTTStructure().getCurrentCycle();
		long dayKey = Long.parseLong(DXToolsMethods.getToken(event
				.getPeriodKey(), ".", 0));
		DResource day = cycle.getSetOfDays().getResource(dayKey);

		dayCB.setSelectedItem(day.getKey() + "." + day.getID());
		dayCB.addActionListener(this);

		return dayPanel;
	} // end buildDayPanel

	/**
	 * Initialize the dialog
	 */
	private void buildDlg(boolean canBeModified) {

		_tabbedPane = buildTabbedPane(canBeModified);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(_tabbedPane, BorderLayout.NORTH);
		_tabbedPane.addChangeListener(this);
		_tabbedPane.setSelectedIndex(0);

		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_applyPanel = new TwoButtonsPanel(this, a);
		getContentPane().add(_applyPanel);
		_applyPanel.setFirstDisable();

	} // end buildDlg

	/**
	 * build duration
	 * 
	 * @return
	 */
	private String buildDuration(DxEvent event) {
		int duration = event.getDuration()
				/ _dModel.getTTStructure().getPeriodLenght();
		return String.valueOf(duration);
	}

	private JPanel buildDurationPanel(DxEvent event, boolean canBeModified) {
		Vector thePeriods = buildThePeriods(_dModel.getTTStructure()
				.getCurrentCycle().getMaxNumberOfPeriodsInASequence());
		JPanel durationPanel = new JPanel();
		JComboBox periodsCB = new JComboBox(thePeriods);

		durationPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_TIME_LENGTH));
		durationPanel.setName(DConst.R_TIME_LENGTH);
		durationPanel.add(periodsCB);
		periodsCB.setSelectedItem(buildDuration(event));

		if (canBeModified) {
			periodsCB.addActionListener(this);
		} else {
			periodsCB.setEnabled(false);
		}
		return durationPanel;
	} // end buildDurationPanel

	/**
	 * Builds a panel to be placed in a tab of the tabbedPane
	 * 
	 * @return the JPanel to be placed in a tab of the tabbedPane
	 */
	private JPanel buildEventPanel(DxEvent event, int index,
			boolean canBeModified) {
		JPanel localPanel = new JPanel();
		localPanel.setLayout(new BorderLayout());
		JPanel timePanel = buildTimePanel(event, canBeModified);
		JPanel instructorPanel = buildInstructorPanel(event, index);
		JPanel roomPanel = buildRoomPanel(event);// , jtp);
		JPanel fixingPanel = buildFixingPanel(event);

		localPanel.add(timePanel, BorderLayout.NORTH);
		localPanel.add(instructorPanel, BorderLayout.CENTER);
		localPanel.add(roomPanel, BorderLayout.EAST);
		localPanel.add(fixingPanel, BorderLayout.SOUTH);

		return localPanel;
	} // end buildEventPanel

	private JPanel buildFixingPanel(DxEvent event) {
		JPanel myPanel = new JPanel();
		JPanel fixingPanel = new JPanel();
		fixingPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_ASSIGN));
		JToggleButton assigned = new JToggleButton(DConst.BUT_PLACE);
		assigned.setSelected(event.isAssigned());
		assigned.addActionListener(this);
		JToggleButton fixed = new JToggleButton(DConst.BUT_FIGE);

		fixed.setSelected(event.getPermanentState());
		fixed.addActionListener(this);

		fixingPanel.add(assigned);
		fixingPanel.add(fixed);
		myPanel.add(fixingPanel);
		return myPanel;

	} // end buildFixingPanel

	private JPanel buildHourPanel(DxEvent event) {
		JPanel hourPanel = new JPanel();
		Vector<String> vect = buildHourList(event);
		JComboBox hourCB = new JComboBox(vect);

		hourPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_ACTIVITY_BEGIN_HOUR));
		hourPanel.add(hourCB);
		Period period = findPeriod(event);
		hourCB.setSelectedItem(period.getBeginHour()[0] + ":"
				+ period.getBeginHour()[1]);
		hourCB.addActionListener(this);

		return hourPanel;
	} // end buildHourPanel

	private Period findPeriod(DxEvent event) {
		Cycle cycle = _dModel.getTTStructure().getCurrentCycle();
		long dayKey = Long.parseLong(DXToolsMethods.getToken(event
				.getPeriodKey(), ".", 0));
		long seqKey = Long.parseLong(DXToolsMethods.getToken(event
				.getPeriodKey(), ".", 1));
		long perKey = Long.parseLong(DXToolsMethods.getToken(event
				.getPeriodKey(), ".", 2));
		Period period = cycle.getPeriodByKey(dayKey, seqKey, perKey);
		return period;
	}


	private Vector<String> buildHourList(DxEvent event) {
		Vector<String> list = new Vector<String>();
		Cycle cycle = _dModel.getTTStructure().getCurrentCycle();
		Period period = findPeriod(event);
		long dayKey = Long.parseLong(DXToolsMethods.getToken(event
				.getPeriodKey(), ".", 0));
		Day day = (Day) cycle.getSetOfDays().getResource(dayKey).getAttach();
		int[] avoidPriority = {};
		int duration = event.getDuration()
				/ _dModel.getTTStructure().getPeriodLenght();
		for (int i = 0; i < day.getSetOfSequences().size(); i++) {
			Sequence seq = (Sequence) day.getSetOfSequences().getResourceAt(i)
					.getAttach();
			for (int j = 0; j < seq.getSetOfPeriods().size(); j++) {
				period = (Period) seq.getSetOfPeriods().getResourceAt(j)
						.getAttach();
				if (cycle.isPeriodContiguous(dayKey, day.getSetOfSequences()
						.getResourceAt(i).getKey(), seq.getSetOfPeriods()
						.getResourceAt(j).getKey(), duration, avoidPriority,
						true)) {
					list.add(period.getBeginHour()[0] + ":"
							+ period.getBeginHour()[1]);
				}// end if (cycle.isPeriodContiguous(p
			}// end for (int j=0; j< seq.getSetOfPeriods().size(); j++)
		}// end for (int i=0; i< day.getSetOfSequences().size(); i++)
		return list;
	}

	private Vector buildInstructorList() {
		Vector<String> v = new Vector<String>();
		DxSetOfInstructors dxsoi = _dModel.getDxSetOfInstructors();
		v = dxsoi.getNamesVector();
		v.add(DConst.NO_ROOM_INTERNAL);
		return v;
	}

	private JPanel buildInstructorPanel(DxEvent event, int index) {
		int WIDTH_INSTRUCTOR_PANEL = 170;
		int HEIGTH_INSTRUCTOR_PANEL = 120;
		JPanel localPanel = new JPanel();
		JPanel instructorsPanel = new JPanel();
		Vector vect = buildCurrentInstructorList(event);
		_instructorsLists[index] = new JList(vect.toArray());
		_jScrollPane = new JScrollPane(_instructorsLists[index]);
		_jScrollPane.setPreferredSize(new Dimension(WIDTH_INSTRUCTOR_PANEL,
				HEIGTH_INSTRUCTOR_PANEL));

		instructorsPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_INSTRUCTOR_NAME));

		JButton jButtonChange = new JButton(DConst.BUT_CHANGE);
		jButtonChange.addActionListener(this);

		instructorsPanel.add(_jScrollPane);
		instructorsPanel.add(jButtonChange); // to be used when adding
		// instructors
		localPanel.add(instructorsPanel);
		return localPanel;
	} // end buildInstructorPanel

	private JPanel buildRoomPanel(DxEvent event) {// , JTabbedPane jtp) {
		JPanel myPanel = new JPanel(new BorderLayout());
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_ROOM_NAME));
		roomPanel.setLayout(new GridLayout(2, 2));

		// This is for the Sites
		DxSetOfSites dxsosSites = _dModel.getDxSetOfSites();
		DefaultComboBoxModel dcbmSites = new DefaultComboBoxModel(dxsosSites
				.getSitesNamesSorted());
		JComboBox cbSites = new JComboBox(dcbmSites);
		cbSites.setSelectedItem(_dModel.getCurrentSiteName());

		if (dxsosSites.size() == 1) { // if 1 only one site
			cbSites.setEnabled(false);
		} else {
			cbSites.setActionCommand(ACTION_CB_SITE);
			cbSites.addActionListener(this);
			cbSites.setEnabled(true);
		}
		// End is for the Sites

		DxSite currentSite = dxsosSites.getSite(_dModel.getCurrentSiteName());

		int capacity = 0;
		DxCategory currentCat;
		String name = event.getRoomName();
		DxRoom currentRoom;
		DefaultComboBoxModel dcbmCategories = new DefaultComboBoxModel(
				currentSite.getSetOfCat().getCatsNamesSorted());
		JComboBox cbCategories = new JComboBox(dcbmCategories);
		DefaultComboBoxModel dcbmRooms;
		JComboBox cbRooms = new JComboBox();
		//System.out.println("room " + name);

		if (name.equalsIgnoreCase("------")) {
			System.out.println("------" + name);
			currentRoom = null;
			currentCat = null;

			dcbmRooms = new DefaultComboBoxModel(currentSite
					.getAllRoomsNameSorted());
			cbRooms.setModel(dcbmRooms);

			cbRooms.setSelectedItem("------");
			cbCategories.setSelectedItem("------");
			capacity = 0;
		} else {
			// if it is a room?
			if (currentSite.getAllRoomsNameSorted().contains(name)) {
				String currentCatName = currentSite.getCatNameOfRoom(name);
				currentCat = currentSite.getSetOfCat().getCat(currentCatName);
				currentRoom = currentSite.getRoom(currentCat.getName(), name);
//				System.out.println("cat" + currentCat.getName() + " name "
//						+ name);
				dcbmRooms = new DefaultComboBoxModel(currentCat
						.getSetOfDxRooms().getRoomsNameSorted());
				cbRooms.setModel(dcbmRooms);

				cbRooms.setSelectedItem(name);
				cbCategories.setSelectedItem(currentCatName);

				capacity = currentRoom.getCapacity();
			} else { // it is a cat.
				currentCat = currentSite.getSetOfCat().getCat(name);
				currentRoom = null;
//				System.out.println("cat" + currentCat.getName() + " noroom "
//						+ name);
				dcbmRooms = new DefaultComboBoxModel(currentCat
						.getSetOfDxRooms().getRoomsNameSorted());
				cbRooms.setModel(dcbmRooms);

				cbRooms.setSelectedItem("------");
				cbCategories.setSelectedItem(name);

				capacity = 0;
			}

		}

//		System.out.println("Cat type " + event.getCatName(currentSite));
		cbCategories.setActionCommand(ACTION_CB_TYPE);
		cbCategories.addActionListener(this);

		cbRooms.setActionCommand(ACTION_CB_ROOM);
		cbRooms.addActionListener(this);

		JLabel jl = new JLabel(new Integer(capacity).toString());
		JPanel roomState = new JPanel();
		roomState.setBorder(new TitledBorder(new EtchedBorder(), "Sites: "));
		roomState.add(cbSites);

		// construction du contour de la combobox de locaux
		JPanel roomName = new JPanel();
		roomName.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.NAME_LABEL));
		roomName.add(cbRooms);

		// construction du contour du label de capacité
		JPanel roomCapacity = new JPanel();
		roomCapacity.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.CAPACITY_LABEL));
		roomCapacity.add(jl);

		// construction du contour de la combobox de fonction de locaux
		JPanel functionRoom = new JPanel();
		functionRoom.setBorder(new TitledBorder(new EtchedBorder(), "Types: "));
		functionRoom.add(cbCategories);

		// construction du panel complet
		roomPanel.add(roomState);
		roomPanel.add(functionRoom);
		roomPanel.add(roomName);
		roomPanel.add(roomCapacity);

		myPanel.add(roomPanel);
		return myPanel;
	} // end buildRoomPanel

	private JTabbedPane buildTabbedPane(boolean canBeModified) {
		JTabbedPane jtp = new JTabbedPane();
		_instructorsLists = new JList[_events.size()];
		for (int i = 0; i < _events.size(); i++) {
			if (_events.get(i) != null) {
				DxEvent event = (DxEvent) ((DResource) _events.get(i))
						.getAttach();
				jtp.addTab(((DResource) _events.get(i)).getID(),
						buildEventPanel(event, i, canBeModified));
			}
		}// end for
		return jtp;
	}// end buildTabbedPane

	// dans ttable
	private Vector buildThePeriods(int size) {
		Vector<String> v = new Vector<String>();
		for (int i = 0; i <= size; i++) {
			v.addElement(Integer.toString(i));
		}
		return v;
	}

	private JPanel buildTimePanel(DxEvent event, boolean canBeModified) {
		JPanel localPanel = new JPanel();
		localPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_TIMETABLE_NAME));
		JPanel durationPanel = buildDurationPanel(event, canBeModified);
		JPanel dayPanel = buildDayPanel(event);
		JPanel hourPanel = buildHourPanel(event);

		JLabel jlb = new JLabel(DConst.LIMIT + event.getCapacityLimit());

		localPanel.add(durationPanel);
		localPanel.add(dayPanel);
		localPanel.add(hourPanel);
		localPanel.add(jlb);

		return localPanel;
	} // buildTimePanel

	private void displayDlg() {
		int FACTOR = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// the constants are to adjust the dialog size
		this.setBounds(screenSize.width / 6, screenSize.height / 4,
				screenSize.width / 3, screenSize.height / 2 + FACTOR);
		this.pack();
		this.setResizable(true);
		this.setVisible(true);
	} // end init

	/**
	 * 
	 * @param jPanel
	 * @return
	 */
	private JLabel getCapacityLabel(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(2);
		JPanel localJPanel = (JPanel) externalPanel.getComponent(0);
		JPanel roomJPanel = (JPanel) localJPanel.getComponent(3);
		return ((JLabel) roomJPanel.getComponent(0));
	} // end getSelectedRoom

	/**
	 * Return vector of resources, each resource represent an event
	 */
	private Vector getEventsVector(String activityName) {

		Vector<DResource> events = new Vector<DResource>();
		String actID = DXToolsMethods.getToken4Activitiy(activityName, ".", 0);
		String typID = DXToolsMethods.getToken4Activitiy(activityName, ".", 1);

		if (typID.length() != 0) {
			String secID = DXToolsMethods.getToken4Activitiy(activityName, ".",
					2);
			if (secID.length() != 0) {
				String unitID = DXToolsMethods.getToken4Activitiy(activityName,
						".", 3);
				if (unitID.length() != 0) {
					events.add(_dModel.getSetOfEvents().getResource(
							activityName));
				} else {// else unitID.length()!=0
					Section sect = _dModel.getSetOfActivities().getSection(
							actID, typID, secID);
					for (int i = 0; i < sect.getSetOfUnities().size(); i++) {
						events.add(_dModel.getSetOfEvents().getResource(
								actID
										+ "."
										+ typID
										+ "."
										+ secID
										+ "."
										+ sect.getSetOfUnities().getResourceAt(
												i).getID() + "."));
					}// end for (int i=0; i<sect.getSetOfUnities().size();
				}// end else unitID.length()!=0
			} else {// else if(secID.length()!=0)
				Type type = _dModel.getSetOfActivities().getType(actID, typID);
				for (int i = 0; i < type.getSetOfSections().size(); i++) {
					Section sect = _dModel.getSetOfActivities().getSection(
							actID, typID,
							type.getSetOfSections().getResourceAt(i).getID());
					for (int j = 0; j < sect.getSetOfUnities().size(); j++) {
						events.add(_dModel.getSetOfEvents().getResource(
								actID
										+ "."
										+ typID
										+ "."
										+ type.getSetOfSections()
												.getResourceAt(i).getID()
										+ "."
										+ sect.getSetOfUnities().getResourceAt(
												j).getID() + "."));
					}// end for (int i=0; i<sect.getSetOfUnities().size();
					// i++)
				}// end for(int i=0; i< type.getSetOfSections().size(); i++)
			}// end else if(secID.length()!=0)
		} else {// else if(typID.length()!=0)
			Activity activity = (Activity) _dModel.getSetOfActivities()
					.getResource(actID).getAttach();
			for (int a = 0; a < activity.getSetOfTypes().size(); a++) {
				typID = activity.getSetOfTypes().getResourceAt(a).getID();
				Type type = _dModel.getSetOfActivities().getType(actID, typID);
				for (int i = 0; i < type.getSetOfSections().size(); i++) {
					Section sect = _dModel.getSetOfActivities().getSection(
							actID, typID,
							type.getSetOfSections().getResourceAt(i).getID());
					for (int j = 0; j < sect.getSetOfUnities().size(); j++) {
						events.add(_dModel.getSetOfEvents().getResource(
								actID
										+ "."
										+ typID
										+ "."
										+ type.getSetOfSections()
												.getResourceAt(i).getID()
										+ "."
										+ sect.getSetOfUnities().getResourceAt(
												j).getID() + "."));
					}// end for (int i=0; i<sect.getSetOfUnities().size();
				}// end for(int i=0; i< type.getSetOfSections().size(); i++)
			}
		}// end else if(typID.length()!=0)
		return events;
	}

	private String getInstructorKeys(ListModel lm) {
		// TODO: DConst.newInstructors
		String a = "";
		for (int i = 0; i < lm.getSize(); i++) {
			long key = _dModel.getDxSetOfInstructors().getInstructorKey(
					(String) lm.getElementAt(i));
			a += key + ":";
		}
		return a;
	}

	private JList getInstructorsList(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(1);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
		JScrollPane jsp = (JScrollPane) myJPanel.getComponent(0);
		return (JList) (jsp.getViewport()).getComponent(0);
	} // getInstructorsList

//	/**
//	 * get a resource key
//	 * 
//	 * @param soresc
//	 * @param elt
//	 * @return the resource key or -1 if key does not found
//	 */
//	private long getResourceKey(SetOfRooms sor, String elt) {
//		if (!elt.equalsIgnoreCase(DConst.NO_ROOM_INTERNAL)) {
//			return sor.getResource(elt).getKey();
//		}
//		return -1;
//	}

	/**
	 * 
	 * @param jPanel
	 * @return
	 */
	private JComboBox getRoomsJCB(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(2);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
		JPanel roomJPanel = (JPanel) myJPanel.getComponent(2);
		return ((JComboBox) roomJPanel.getComponent(0));
	} // end getRoomJCB

	/**
	 * 
	 * @param jPanel
	 * @return
	 */
	private JComboBox getTypesJCB(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(2);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
		JPanel roomJPanel = (JPanel) myJPanel.getComponent(1);
		return ((JComboBox) roomJPanel.getComponent(0));
	} // end getTypeJCB

	private String getSelectedDay(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(0);
		JPanel dP = (JPanel) externalPanel.getComponent(1);
		return ((JComboBox) dP.getComponent(0)).getSelectedItem().toString();
	} // end getSelectedDay

	private String getSelectedDuration(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(0);
		JPanel dP = (JPanel) externalPanel.getComponent(0);
		return ((JComboBox) dP.getComponent(0)).getSelectedItem().toString();
	} // end getSelectedDuration

	private String getSelectedHour(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(0);
		JPanel hP = (JPanel) externalPanel.getComponent(2);
		return ((JComboBox) hP.getComponent(0)).getSelectedItem().toString();
	} // end getSelectedHour

	/**
	 * 
	 * @param jPanel
	 * @return
	 */
	private String getSelectedRoom(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(2);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
		JPanel roomJPanel = (JPanel) myJPanel.getComponent(2);
		return ((JComboBox) roomJPanel.getComponent(0)).getSelectedItem()
				.toString();
	} // end getSelectedRoom

	/**
	 * 
	 * @param jPanel
	 * @return
	 */
	private String getSelectedSite(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(2);
		JPanel localJPanel = (JPanel) externalPanel.getComponent(0);
		JPanel siteJPanel = (JPanel) localJPanel.getComponent(0);
		return ((JComboBox) siteJPanel.getComponent(0)).getSelectedItem()
				.toString();
	} // end getSelectedRoom

	/**
	 * 
	 * @param jPanel
	 * @return
	 */
	private String getSelectedType(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(2);
		JPanel localJPanel = (JPanel) externalPanel.getComponent(0);
		JPanel siteJPanel = (JPanel) localJPanel.getComponent(1);
		return ((JComboBox) siteJPanel.getComponent(0)).getSelectedItem()
				.toString();
	} // end getSelectedType

	public String idDlgToString() {
		return this.getClass().toString();
	}

	private boolean isAssignedButtonSelected(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(3);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);

		return ((JToggleButton) (myJPanel.getComponent(0))).isSelected();

	} // end isAssignedButtonSelected

	private boolean isFixedButtonSelected(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(3);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);

		return ((JToggleButton) (myJPanel.getComponent(1))).isSelected();
	} // end isFixedButtonSelected

	private Vector makeVector(JList jList) {
		Vector<Object> v = new Vector<Object>();
		if (jList != null) {
			for (int i = 0; i < jList.getModel().getSize(); i++)
				v.add(jList.getModel().getElementAt(i));
		}
		return v;
	}

	public void updateInstructorList(Vector v) {
		_instructorsLists[_tabbedPane.getSelectedIndex()].setListData(v);
		_tabbedPane.repaint();
		_applyPanel.setFirstEnable();
	}

	/**
	 * item state changed
	 * 
	 * @param e
	 */
	public void stateChanged(ChangeEvent ce) {
		// pane changed (detected by isFirstEnable)
		// it it is necessary to apply or close
		if (_applyPanel.isFirstEnable()) {
			_tabbedPane.removeChangeListener(this);
			int i = _tabbedPane.getSelectedIndex();
			_tabbedPane.setSelectedIndex(i);
			new InformationDlg(this, "Appliquer ou fermer pour continuer",
					"Operation interdite");
			_tabbedPane.addChangeListener(this);
		} else {
			_tabbedPane.setSelectedIndex(((JTabbedPane) ce.getSource())
					.getSelectedIndex());
			int j = ((JTabbedPane) ce.getSource()).getSelectedIndex();
			_tabbedPane.setSelectedIndex(j);

		}
	}// end state change
}// end class
