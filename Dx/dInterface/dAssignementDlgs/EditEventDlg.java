/**
 *
 * Title: EditActivityDlg $Revision: 1.6 $  $Date: 2008-01-28 01:51:56 $
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
 * @version $Revision: 1.6 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */

/**
 * Description: EditActivityDlg is a class used to
 *
 */
package dInterface.dAssignementDlgs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import dInterface.dAffectation.EventsDlgInterface;
import dInterface.dAffectation.SelectInstructors;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxJComboBox;
import dInterface.dUtil.TwoButtonsPanel;

import dInternal.DModel;
import dInternal.DResource;
import dInternal.dData.DxResource;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dRooms.DxSetOfRooms; //import dInternal.dData.dRooms.RoomAttach;
import dInternal.dData.dActivities.Section;
import dInternal.dData.dInstructors.DxSetOfInstructors;

//import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dActivities.Type;

import dInternal.dOptimization.DxEvent;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.dialog.InformationDlg;

public class EditEventDlg extends JDialog implements ActionListener,
		ChangeListener {

	private DApplication _dApplic;
	private DModel _dm;
	private EventsDlgInterface _evDlgInt = null;
	private int _currentActivityIndex = 0;

	private JTabbedPane _tabbedPane;
	private JScrollPane _jScrollPane;
	private ButtonsPanel _applyPanel;
	private DxJComboBox _roomCB;
	private JLabel[] _capacity;
	private boolean _canBeModified = false;
	private Vector _unities = new Vector(); // contains event resource
	private JList[] _instructorsLists;

	/**
	 * Constructor for EditEventDlg in the case of one or more
	 *        events
	 * @param activityDialog The parent dialog of this dialog
	 * @param dApplic The application
	 * @param currentActivity The activity choiced in the activityDialog
	 * @param canBeModified
	 */

	public EditEventDlg(JDialog dialog, DModel dModel, String currentActivity,
			boolean canBeModified) {
		super(dialog, DConst.T_AFFEC_DLG);//"Affectation d'évenement(s)");
		continueContructor(dialog, dModel, currentActivity, canBeModified);

	} // end EditActivityDlg

//	/**
//	 * Constructor for EditEventDlg, in the case of one event
//	 * @param dialog The parent dialog of this dialog
//	 * @param dApplic The application
//	 * @param currentActivity The activity choiced in the dialog
//	 * @param evDlg,
//	 * @param isModified
//	 */
//	public EditEventDlg(JDialog dialog, DModel dModel, String currentActivity,
//			EventsDlgInterface evDlg, boolean canBeModified) {
//		super(dialog, DConst.EVENTS_DLG_TITLE);
//		_evDlgInt = evDlg;
//		continueContructor(dialog, dModel, currentActivity, canBeModified);
//
//	}

	private void continueContructor(JDialog dialog, DModel dModel,
			String currentActivity, boolean canBeModified) {
		setLocationRelativeTo(dialog);
		_dm = dModel;
		_canBeModified = canBeModified;
		_unities = buildUnitiesVector(currentActivity);
		_instructorsLists = new JList[_unities.size()];
		_capacity = new JLabel[_unities.size()];
		initialize();
	}

	/**
	 * Initialize the dialog
	 */
	private void initialize() {
		int FACTOR = 50;

		_tabbedPane = buildTabbedPane();
		//myJPanel.add(_tabbedPane, BorderLayout.CENTER);
		this.getContentPane().add(_tabbedPane, BorderLayout.CENTER);
		_tabbedPane.addChangeListener(this);
		_currentActivityIndex = 0;
		_tabbedPane.setSelectedIndex(_currentActivityIndex);

		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_applyPanel = new TwoButtonsPanel(this, a);
		getContentPane().add(_applyPanel, BorderLayout.SOUTH);
		_applyPanel.setFirstDisable();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// XXXX Pascal: magic numbers sans commentaire
		this.setBounds(screenSize.width / 6, screenSize.height / 4,
				screenSize.width / 3, screenSize.height / 2 + FACTOR);
		this.pack();
		this.setResizable(true);
		this.setVisible(true);
	} // end init

	/**
	 * action performed
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(DConst.CATEGORY_AC)) {
			_roomCB.disableActionListeners();
			Vector vectR = buildRoomList(); // XXXX Pascal: Vector est 'deprecated' depuis plusieurs annees
			_roomCB = new DxJComboBox(vectR);
			_roomCB.enableActionListeners();
			_applyPanel.setFirstEnable();
		}
		if (command.equals(DConst.NAME_AC)) {
			JPanel tpane = ((JPanel) _tabbedPane
					.getComponentAt(_currentActivityIndex));
			String roomName = getSelectedRoom(tpane);
			_capacity[_currentActivityIndex].setText(getCapacity(roomName));
			_applyPanel.setFirstEnable();
		}
		if (command.equals(DConst.BUT_CLOSE)) { // fermer
			dispose();
		} else if (command.equals(DConst.BUT_APPLY)) { // apply
			boolean apply = false;
			for (int i = 0; i < this._unities.size(); i++) {
				_currentActivityIndex = i;
				apply = applyChanges();
				if (!apply) {
					new FatalProblemDlg(this, "Valeur erronée");
					break;
				}
				_applyPanel.setFirstDisable();
			} // end for
			if (apply) {
				_dm.changeInDModelByEditActivityDlg(this);
				if (_evDlgInt != null)
					_evDlgInt.initializePanel();
			}

		} else if (command.equals("comboBoxChanged")
				|| command.equals(DConst.BUT_PLACE)
				|| command.equals(DConst.BUT_FIGE)) {// a comboBox has changed
			_applyPanel.setFirstEnable();

		} else if (command.equals(DConst.BUT_CHANGE)) {// change instrcutors
			new SelectInstructors(this,
					makeVector(_instructorsLists[_currentActivityIndex]),
					buildInstructorList());
		}

	}

	private JTabbedPane buildTabbedPane() {
		JTabbedPane jtp = new JTabbedPane();
		_instructorsLists = new JList[_unities.size()];
		for (int i = 0; i < _unities.size(); i++) {
			if (_unities.get(i) != null) {
				_currentActivityIndex = i;
				jtp.addTab(((DResource) _unities.get(i)).getID(),
						buildUnityPanel(i));
			} // end if
		}// end for
		return jtp;
	}// end buildTabbedPane

	/**
	 * Builds a panel to be placed in a tab of the tabbedPane
	 * @return the JPanel to be placed in a tab of the tabbedPane
	 */
	private JPanel buildUnityPanel(int index) {
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new GridLayout(4, 1));
		JPanel timePanel = buildTimePanel();
		JPanel instructorPanel = buildInstructorPanel(index);
		JPanel roomPanel = buildRoomPanel(index);
		JPanel fixingPanel = buildFixingPanel(index);

		myPanel.add(timePanel);
		myPanel.add(instructorPanel);
		myPanel.add(roomPanel);
		myPanel.add(fixingPanel);

		return myPanel;
	} // end buildUnityPanel

	private JPanel buildTimePanel() {
		JPanel myPanel = new JPanel();

		JPanel durationPanel = buildDurationPanel();
		JPanel dayPanel = buildDayPanel();
		JPanel hourPanel = buildHourPanel();

		myPanel.add(durationPanel);
		myPanel.add(dayPanel);
		myPanel.add(hourPanel);

		return myPanel;
	} // buildTimePanel

	private JPanel buildInstructorPanel(int index) {
		JPanel myPanel = new JPanel();
		JPanel instructorsPanel = new JPanel();
		Vector vect = buildCurrentInstructorList(index);
		_instructorsLists[index] = new JList(vect.toArray());

		_jScrollPane = new JScrollPane(_instructorsLists[index]);
		_jScrollPane.setPreferredSize(new Dimension(170, 40));

		instructorsPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_INSTRUCTOR_NAME));

		JButton jButtonChange = new JButton(DConst.BUT_CHANGE);
		jButtonChange.addActionListener(this);

		instructorsPanel.add(_jScrollPane);
		instructorsPanel.add(jButtonChange); //to be used when adding instructors

		myPanel.add(instructorsPanel);
		return myPanel;
	} // end buildInstructorPanel

	private JList getInstructorsList(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(1);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
		JScrollPane jsp = (JScrollPane) myJPanel.getComponent(0);

		return (JList) (jsp.getViewport()).getComponent(0);
	} // getInstructorsList

	private JPanel buildRoomPanel(int index) {
		JPanel myPanel = new JPanel();
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_ROOM_NAME));

		// for development mode
		Vector[] vectC = buildCategoryRoomList();
		JComboBox categoryRoomCB = new JComboBox(vectC[1]);
		categoryRoomCB.setSelectedItem(vectC[0].get(0).toString());
		categoryRoomCB.setActionCommand(DConst.CATEGORY_AC);
		categoryRoomCB.addActionListener(this);

		Vector vectR = buildRoomList();//vectC[0].get(0).toString());
		_roomCB = new DxJComboBox(vectR);
		_roomCB.setActionCommand(DConst.NAME_AC);
		_roomCB.setSelectedItem(vectR.get(0).toString());
		_roomCB.addActionListener(this);

		//Vector[] vectCapacity  = buildCapacityList();
		String capacity = getCapacity(vectR.get(0).toString());
		_capacity[index] = new JLabel(capacity);

		JPanel categoryRoom = new JPanel();
		categoryRoom.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.CATEGORY_LABEL));
		categoryRoom.add(categoryRoomCB);
		JPanel roomName = new JPanel();
		roomName.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.NAME_LABEL));

		roomName.add(_roomCB);
		roomName.add(_capacity[index]);

		roomPanel.add(categoryRoom);
		roomPanel.add(roomName);

		myPanel.add(roomPanel);

		return myPanel;

	} //end  buildRoomPanel

	private String getSelectedRoom(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(2);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
		JPanel roomJPanel = (JPanel) myJPanel.getComponent(1);
		return ((JComboBox) roomJPanel.getComponent(0)).getSelectedItem()
				.toString();
	} // end getSelectedRoom

	/* private String getSelectedCategory(JPanel jPanel) {
	 JPanel externalPanel = (JPanel) jPanel.getComponent(2);
	 JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
	 JPanel catJPanel = (JPanel) myJPanel.getComponent(0);
	 return ((JComboBox)catJPanel.getComponent(0)).getSelectedItem().toString();
	  } // end getSelectedCategory
	 */

	private JPanel buildFixingPanel(int index) {
		DxEvent event = (DxEvent) ((DResource) _unities.get(index)).getAttach();
		JPanel myPanel = new JPanel();
		JPanel fixingPanel = new JPanel();
		fixingPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_ASSIGN));
		JToggleButton assigned = new JToggleButton(DConst.BUT_PLACE);
		//    assigned.setSelected(event.getAssignState());
		assigned.setSelected(event.getPermanentState());
		assigned.addActionListener(this);
		JToggleButton fixed = new JToggleButton(DConst.BUT_FIGE);

		fixed.setSelected(event.getPermanentState());
		fixed.addActionListener(this);

		fixingPanel.add(assigned);
		fixingPanel.add(fixed);
		myPanel.add(fixingPanel);
		return myPanel;

	} // end buildFixingPanel

	private boolean isAssignedButtonSelected(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(3);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);

		return ((JToggleButton) (myJPanel.getComponent(0))).isSelected();

	} // end isAssignedButtonSelected

	private boolean isFixedButtonSelected(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(3);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);

		return ((JToggleButton) (myJPanel.getComponent(1))).isSelected();
	} //end isFixedButtonSelected

	private JPanel buildDurationPanel() {
		Vector thePeriods = buildThePeriods(_dm.getTTStructure()
				.getCurrentCycle().getMaxNumberOfPeriodsInASequence());
		JPanel durationPanel = new JPanel();
		JComboBox periodsCB = new JComboBox(thePeriods);

		durationPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_TIME_LENGTH));
		durationPanel.setName(DConst.R_TIME_LENGTH);
		durationPanel.add(periodsCB);

		if (_canBeModified) {
			periodsCB.setSelectedItem(buildDuration());
			periodsCB.addActionListener(this);
		} else {
			periodsCB.setSelectedItem(buildDuration());
			periodsCB.setEnabled(false);
		}

		return durationPanel;
	} // end buildDurationPanel

	private String getSelectedDuration(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(0);
		JPanel dP = (JPanel) externalPanel.getComponent(0);
		return ((JComboBox) dP.getComponent(0)).getSelectedItem().toString();
	} // end getSelectedDuration

	private JPanel buildDayPanel() {
		JPanel dayPanel = new JPanel();
		Vector[] vect = buildDayList();
		JComboBox dayCB = new JComboBox(vect[1]);

		dayPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_DAY_NAME));
		dayPanel.add(dayCB);
		dayCB.setSelectedItem(vect[0].get(0).toString());
		dayCB.addActionListener(this);

		return dayPanel;
	} // end buildDayPanel

	private String getSelectedDay(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(0);
		JPanel dP = (JPanel) externalPanel.getComponent(1);
		return ((JComboBox) dP.getComponent(0)).getSelectedItem().toString();
	} //end getSelectedDay

	private JPanel buildHourPanel() {
		JPanel hourPanel = new JPanel();
		Vector[] vect = buildHourList();
		JComboBox hourCB = new JComboBox(vect[1]);

		hourPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.R_ACTIVITY_BEGIN_HOUR));
		hourPanel.add(hourCB);
		hourCB.setSelectedItem(vect[0].get(0).toString());
		hourCB.addActionListener(this);

		return hourPanel;
	} // end buildHourPanel

	private String getSelectedHour(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(0);
		JPanel hP = (JPanel) externalPanel.getComponent(2);
		return ((JComboBox) hP.getComponent(0)).getSelectedItem().toString();
	} // end getSelectedHour

	private Vector makeVector(JList jList) {
		Vector v = new Vector();
		if (jList != null) {
			for (int i = 0; i < jList.getModel().getSize(); i++)
				v.add(jList.getModel().getElementAt(i));

		}

		return v;
	}

	public void updateInstructorList(Vector v) {
		_instructorsLists[_currentActivityIndex].setListData(v);
		_jScrollPane.repaint();

		//instructorsLists[_currentActivityIndex] = new JList(v.toArray());
		_applyPanel.setFirstEnable();
	}

	/**
	 * item state changed
	 * @param e
	 */
	public void stateChanged(ChangeEvent ce) {

		if (!_applyPanel.isFirstEnable()) {
			_currentActivityIndex = ((JTabbedPane) ce.getSource())
					.getSelectedIndex();
			_tabbedPane.setSelectedIndex(_currentActivityIndex);

		} else {
			_tabbedPane.removeChangeListener(this);
			_tabbedPane.setSelectedIndex(_currentActivityIndex);
			new InformationDlg(this, "Appliquer ou fermer pour continuer",
					"Operation interdite");
			_tabbedPane.addChangeListener(this);
		}
	}// end state change

	/**
	 * Return vector of resources. each resource represent an event
	 */
	private Vector oldBuildUnitiesVector(String activityName) {
		//int nbTokens= DXToolsMethods.countTokens(activityName, ".");
		Vector unities = new Vector(1);
		//System.out.println("CounTokens: "+nbTokens);// debug
		String actID = DXToolsMethods.getToken(activityName, ".", 0);
		String typID = DXToolsMethods.getToken(activityName, ".", 1);

		if (typID.length() != 0) {
			String secID = DXToolsMethods.getToken(activityName, ".", 2);
			if (secID.length() != 0) {
				String unitID = DXToolsMethods.getToken(activityName, ".", 3);
				if (unitID.length() != 0) {
					//          unities.add(_dApplic.getCurrentDModel().getSetOfEvents().
					//                      getResource(activityName));
					unities.add(_dApplic.getCurrentDModel().getSetOfEvents()
							.getResource(activityName));

				} else {// else unitID.length()!=0
					Section sect = _dApplic.getCurrentDModel()
							.getSetOfActivities().getSection(actID, typID,
									secID);
					for (int i = 0; i < sect.getSetOfUnities().size(); i++) {
						unities.add(_dApplic.getCurrentDModel()
								.getSetOfEvents().getResource(
										actID
												+ "."
												+ typID
												+ "."
												+ secID
												+ "."
												+ sect.getSetOfUnities()
														.getResourceAt(i)
														.getID() + "."));
					}// end for (int i=0; i<sect.getSetOfUnities().size(); i++)
				}// end else unitID.length()!=0
			} else {// else if(secID.length()!=0)
				Type type = _dApplic.getCurrentDModel().getSetOfActivities()
						.getType(actID, typID);
				for (int i = 0; i < type.getSetOfSections().size(); i++) {
					Section sect = _dApplic.getCurrentDModel()
							.getSetOfActivities().getSection(
									actID,
									typID,
									type.getSetOfSections().getResourceAt(i)
											.getID());
					for (int j = 0; j < sect.getSetOfUnities().size(); j++) {
						unities.add(_dApplic.getCurrentDModel()
								.getSetOfEvents().getResource(
										actID
												+ "."
												+ typID
												+ "."
												+ type.getSetOfSections()
														.getResourceAt(i)
														.getID()
												+ "."
												+ sect.getSetOfUnities()
														.getResourceAt(j)
														.getID() + "."));
					}// end for (int i=0; i<sect.getSetOfUnities().size(); i++)
				}// end for(int i=0; i< type.getSetOfSections().size(); i++)
			}// end else if(secID.length()!=0)
		} else {// else if(typID.length()!=0)
			Activity activity = (Activity) _dApplic.getCurrentDModel()
					.getSetOfActivities().getResource(actID).getAttach();
			for (int a = 0; a < activity.getSetOfTypes().size(); a++) {
				typID = activity.getSetOfTypes().getResourceAt(a).getID();
				Type type = _dApplic.getCurrentDModel().getSetOfActivities()
						.getType(actID, typID);
				for (int i = 0; i < type.getSetOfSections().size(); i++) {
					Section sect = _dApplic.getCurrentDModel()
							.getSetOfActivities().getSection(
									actID,
									typID,
									type.getSetOfSections().getResourceAt(i)
											.getID());
					for (int j = 0; j < sect.getSetOfUnities().size(); j++) {
						unities.add(_dApplic.getCurrentDModel()
								.getSetOfEvents().getResource(
										actID
												+ "."
												+ typID
												+ "."
												+ type.getSetOfSections()
														.getResourceAt(i)
														.getID()
												+ "."
												+ sect.getSetOfUnities()
														.getResourceAt(j)
														.getID() + "."));
					}// end for (int i=0; i<sect.getSetOfUnities().size(); i++)
				}// end for(int i=0; i< type.getSetOfSections().size(); i++)
			}
		}// end else if(typID.length()!=0)
		return unities;
	}

	/**
	 * Return vector of resources, each resource represent an event
	 */
	private Vector<DResource> buildUnitiesVector(String activityName) {

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
					events.add(_dm.getSetOfEvents().getResource(activityName));
				} else {// else unitID.length()!=0
					Section sect = _dm.getSetOfActivities().getSection(actID,
							typID, secID);
					for (int i = 0; i < sect.getSetOfUnities().size(); i++) {
						events.add(_dm.getSetOfEvents().getResource(
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
				Type type = _dm.getSetOfActivities().getType(actID, typID);
				for (int i = 0; i < type.getSetOfSections().size(); i++) {
					Section sect = _dm.getSetOfActivities().getSection(actID,
							typID,
							type.getSetOfSections().getResourceAt(i).getID());
					for (int j = 0; j < sect.getSetOfUnities().size(); j++) {
						events.add(_dm.getSetOfEvents().getResource(
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
			Activity activity = (Activity) _dm.getSetOfActivities()
					.getResource(actID).getAttach();
			for (int a = 0; a < activity.getSetOfTypes().size(); a++) {
				typID = activity.getSetOfTypes().getResourceAt(a).getID();
				Type type = _dm.getSetOfActivities().getType(actID, typID);
				for (int i = 0; i < type.getSetOfSections().size(); i++) {
					Section sect = _dm.getSetOfActivities().getSection(actID,
							typID,
							type.getSetOfSections().getResourceAt(i).getID());
					for (int j = 0; j < sect.getSetOfUnities().size(); j++) {
						events.add(_dm.getSetOfEvents().getResource(
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

	/**
	 *build duration
	 * @return
	 */
	private String buildDuration() {
		DxEvent event = (DxEvent) ((DResource) _unities
				.get(_currentActivityIndex)).getAttach();
		int duration = event.getDuration()
				/ _dm.getTTStructure().getPeriodLenght();
		return String.valueOf(duration);
	}

	/**
	 * build the hour list
	 * @return Vector[] of two elements the first is a Vector containing
	 *
	 *                  the second contains
	 */
	private Vector[] buildHourList() {
		Vector list[] = { new Vector(), new Vector() };
		DxEvent event = (DxEvent) ((DResource) _unities
				.get(_currentActivityIndex)).getAttach();
		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		long dayKey = Long.parseLong(DXToolsMethods.getToken(event
				.getPeriodKey(), ".", 0));
		long seqKey = Long.parseLong(DXToolsMethods.getToken(event
				.getPeriodKey(), ".", 1));
		long perKey = Long.parseLong(DXToolsMethods.getToken(event
				.getPeriodKey(), ".", 2));
		Period period = cycle.getPeriodByKey(dayKey, seqKey, perKey);
		list[0].add(period.getBeginHour()[0] + ":" + period.getBeginHour()[1]);
		Day day = (Day) cycle.getSetOfDays().getResource(dayKey).getAttach();
		int[] avoidPriority = {};
		int duration = event.getDuration()
				/ _dm.getTTStructure().getPeriodLenght();
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
					list[1].add(period.getBeginHour()[0] + ":"
							+ period.getBeginHour()[1]);
				}// end if (cycle.isPeriodContiguous(p
			}// end for (int j=0; j< seq.getSetOfPeriods().size(); j++)
		}// end for (int i=0; i< day.getSetOfSequences().size(); i++)
		return list;
	}

	/**
	 *build the day list
	 * @return
	 */
	private Vector[] buildDayList() {
		Vector list[] = { new Vector(1), new Vector(1) };
		DxEvent event = (DxEvent) ((DResource) _unities
				.get(_currentActivityIndex)).getAttach();
		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		long dayKey = Long.parseLong(DXToolsMethods.getToken(event
				.getPeriodKey(), ".", 0));
		DResource day = cycle.getSetOfDays().getResource(dayKey);
		list[0].add(day.getKey() + "." + day.getID());
		for (int i = 0; i < cycle.getSetOfDays().size(); i++)
			list[1].add(cycle.getSetOfDays().getResourceAt(i).getKey() + "."
					+ cycle.getSetOfDays().getResourceAt(i).getID());
		return list;
	}

	/**
	 *build the instructor list
	 * @return
	 */
	private Vector buildCurrentInstructorList(int index) {
		Vector v = new Vector();//, new Vector(1)};
		DxEvent event = (DxEvent) ((DResource) _unities.get(index)).getAttach();
		DxSetOfInstructors soi = _dm.getDxSetOfInstructors();
		//long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
		long keys[] = event.getInstructorKey();
		for (int i = 0; i < keys.length; i++) {
			//     DResource instructor = soi.getResource(keys[i]);
			DxResource instructor = soi.getInstructor(i);
			if (instructor != null)
				v.add(instructor.getName());
		}
		return v;
	}

	private Vector buildInstructorList() {//int index){
		Vector v = new Vector();//, new Vector(1)};
		//EventAttach event= (EventAttach)((DResource)_unities.get(index)).getAttach();
		DxSetOfInstructors soi = _dm.getDxSetOfInstructors();
		//long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
		//long keys [] = event.getInstructorKey();
		for (int i = 0; i < soi.size(); i++)
			v.add(soi.getInstructorName(i));
		v.add(DConst.NO_ROOM_INTERNAL);
		return v;
	}

	/**
	 *build the room list
	 * @return
	 */
	private Vector[] oldBuildRoomList() {//String category){
		Vector list[] = { new Vector(1), new Vector(1) };
		DxEvent event = (DxEvent) ((DResource) _unities
				.get(_currentActivityIndex)).getAttach();
		DxSetOfRooms sor = _dm.getDxSetOfRooms();
		//long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
		DxResource room = sor.getResource(event.getRoomKey());
		if (room != null)
			list[0].add(room.getName());
		else
			list[0].add(DConst.NO_ROOM_INTERNAL);
		for (int i = 0; i < sor.size(); i++) {
			//      list[1].add(sor.getResourceAt(i).getID());
		}
		list[1].add(DConst.NO_ROOM_INTERNAL);
		return list;
	}

	/**
	 *build the room list
	 * @return
	 */
	private Vector buildRoomList() {//String category){
		Vector list = new Vector();//, new Vector(1)};
		DxEvent event = (DxEvent) ((DResource) _unities
				.get(_currentActivityIndex)).getAttach();
		DxSetOfRooms sor = _dm.getDxSetOfRooms();
		//long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
		//    DxResource room = sor.getResource(event.getRoomKey());
		//    if(room!=null)
		//      list[0].add(room.getName());
		//    else
		//      list[0].add(DConst.NO_ROOM_INTERNAL);
		//    for(int i=0; i< sor.size(); i++) {
		//      list[1].add(sor.getResourceAt(i).getID());
		//    }
		//    list[1].add(DConst.NO_ROOM_INTERNAL);
		return sor.getNamesVector();
	}

	private String getCapacity(String str) {

		//    DxSetOfRooms sor= _dApplic.getCurrentDModel().getSetOfRooms();
		//    DResource res = 	sor.getResource(str);
		//    if (res == null) {
		//      return "000";
		//    } 
		//	RoomAttach ra = (RoomAttach) res.getAttach();
		//	return String.valueOf(ra.getCapacity());  
		return "000";
	}

	/**
	 * @return
	 */
	/*  private Vector[] buildCapacityList() {
	 Vector list[] = {new Vector(1), new Vector(1)};
	 EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
	 SetOfRooms sor= _dm.getSetOfRooms();
	 //long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
	 Resource room = sor.getResource(event.getRoomKey());
	 //if(room!=null)
	 // list[0].add(room.getID());
	 //else
	 // list[0].add(DConst.NO_ROOM_INTERNAL);
	 for(int i=0; i< sor.size(); i++) {
	 RoomAttach ra = (RoomAttach) sor.getResourceAt(i).getAttach();
	 list[1].add(String.valueOf(ra.getCapacity()));
	 }
	 list[1].add(DConst.NO_ROOM_INTERNAL);
	 return list;
	 }*/
	private Vector[] buildCategoryRoomList() {
		Vector list[] = { new Vector(1), new Vector(1) };
		//EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
		//SetOfCategories soc= _dm.getSetOfCategories();
		//long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
		//Resource room = sor.getResource(event.getRoomKey());
		//if(room!=null)
		//list[0].add(room.getID());
		//else
		list[0].add(DConst.NO_ROOM_INTERNAL);
		//for(int i=0; i< soc.size(); i++)
		//list[1].add(soc.getResourceAt(i).getID());
		list[1].add(DConst.NO_ROOM_INTERNAL);
		return list;
	}

	/**
	 * apply change in a event
	 */
	private boolean applyChanges() {
		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		DxEvent event = (DxEvent) ((DResource) _unities
				.get(_currentActivityIndex)).getAttach();
		//remove event
		_dm.getConditionsToTest().removeEventInTTs(_dm.getTTStructure(),
				(DResource) _unities.get(_currentActivityIndex), false);

		JPanel tpane = ((JPanel) _tabbedPane
				.getComponentAt(_currentActivityIndex));
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

		boolean fixedBut = isFixedButtonSelected(tpane); //= ((JToggleButton)((JPanel)tpane.getComponent(3)).getComponent(1)).isSelected();
		int[] daytime = {
				Integer.parseInt(DXToolsMethods.getToken(day, ".", 0)),
				Integer.parseInt(DXToolsMethods.getToken(hour, ":", 0)),
				Integer.parseInt(DXToolsMethods.getToken(hour, ":", 1)) };
		String periodKey = cycle.getPeriod(daytime);
		event.setDuration(Integer.parseInt(duration)
				* _dm.getTTStructure().getPeriodLenght());
		event.setKey(4, periodKey);
		event.setKey(1, intructorKeys);
		event.setKey(2, Long.toString(getResourceKey(_dm.getDxSetOfRooms(),
				room)));
		event.setPermanentState(assignBut);
		event.setPermanentState(fixedBut);
		Vector vect = new Vector();
		vect.add(_unities.get(_currentActivityIndex));
		_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(), vect);
		//add event
		_dm.getConditionsToTest().addEventInTTs(_dm.getTTStructure(),
				(DResource) _unities.get(_currentActivityIndex), false);
		return true;
	}

	private String getInstructorKeys(ListModel lm) {
		String a = "";
		for (int i = 0; i < lm.getSize(); i++) {
			long key = _dm.getDxSetOfInstructors().getResource(
					(String) lm.getElementAt(i)).getKey();
			a += key + ":";
		}
		return a;
	}

	/**
	 * get a resource key
	 * @param soresc
	 * @param elt
	 * @return the resource key or -1 if key does not found
	 */
	private long getResourceKey(DxSetOfRooms sor, String elt) {
		if (!elt.equalsIgnoreCase(DConst.NO_ROOM_INTERNAL)) {
			return sor.getResource(elt).getKey();
		}
		return -1;
	}

	private Vector buildThePeriods(int size) {
		Vector v = new Vector();
		for (int i = 0; i <= size; i++) {
			v.addElement(Integer.toString(i));
		}

		return v;

	}
}// end class
