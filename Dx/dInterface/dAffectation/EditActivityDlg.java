/**
 *
 * Title: EditActivityDlg $Revision: 1.79 $  $Date: 2006-09-13 13:08:21 $
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
 * @version $Revision: 1.79 $
 * @author  $Author: hara2602 $
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
package dInterface.dAffectation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
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
import dDeveloper.DxFlags;
import dInterface.DApplication;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxJComboBox;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DResource;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.Section;
import dInternal.dData.dActivities.Type;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.RoomAttach;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dOptimization.EventAttach;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.dialog.InformationDlg;

public class EditActivityDlg extends JDialog implements ActionListener,
        ChangeListener {

    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private DApplication _dApplic;

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
     * Constructor for EditActivityDlg in the case of one or more events
     * 
     * @param activityDialog
     *            The parent dialog of this dialog
     * @param dApplic
     *            The application
     * @param currentActivity
     *            The activity choiced in the activityDialog
     * @param canBeModified
     */

    public EditActivityDlg(JDialog dialog, DApplication dApplic,
            String currentActivity, boolean canBeModified) {
        super(dialog, DConst.T_AFFEC_DLG + "RGRMultiEvent");// "Affectation
        // d'évenement(s)");
        continueContructor(dialog, dApplic, currentActivity, canBeModified);

    } // end EditActivityDlg

    /**
     * Constructor for EditActivityDlg, in the case of one event
     * 
     * @param dialog
     *            The parent dialog of this dialog
     * @param dApplic
     *            The application
     * @param currentActivity
     *            The activity choiced in the dialog
     * @param evDlg,
     * @param isModified
     */
    public EditActivityDlg(JDialog dialog, DApplication dApplic,
            String currentActivity, EventsDlgInterface evDlg,
            boolean canBeModified) {
        super(dialog, DConst.EVENTS_DLG_TITLE + "RGROneEvent");
        _evDlgInt = evDlg;
        continueContructor(dialog, dApplic, currentActivity, canBeModified);

    }

    private void continueContructor(JDialog dialog, DApplication dApplic,
            String currentActivity, boolean canBeModified) {
        setLocationRelativeTo(dialog);
        _dApplic = dApplic;
        // _dm = dApplic.getDMediator().getCurrentDoc().getDM();
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
        // myJPanel.add(_tabbedPane, BorderLayout.CENTER);
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
     * 
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(DConst.CATEGORY_AC)) {
            _roomCB.disableActionListeners();
            Vector[] vectR = buildRoomList(); // XXXX Pascal: Vector est
            // 'deprecated' depuis plusieurs
            // annees
            _roomCB = new DxJComboBox(vectR[1]);
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
                    new DxExceptionDlg(this, "Valeur erronée");
                    break;
                }
                _applyPanel.setFirstDisable();
            } // end for
            if (apply) {
                _dApplic.getCurrentDModel().changeInDModelByEditActivityDlg(
                        this);
                if (_evDlgInt != null)
                    _evDlgInt.initializePanel();
            }

        } else if (command.equals("comboBoxChanged")
                || command.equals(DConst.BUT_PLACE)
                || command.equals(DConst.BUT_FIGE)) {// a comboBox has
            // changed
            _applyPanel.setFirstEnable();

        } else if (command.equals(DConst.BUT_CHANGE)) {// change instrcutors
            new SelectInstructors(_dApplic, this,
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
     * 
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
        instructorsPanel.add(jButtonChange); // to be used when adding
        // instructors

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

        Vector[] vectR = buildRoomList();// vectC[0].get(0).toString());
        _roomCB = new DxJComboBox(vectR[1]);
        _roomCB.setActionCommand(DConst.NAME_AC);
        _roomCB.setSelectedItem(vectR[0].get(0).toString());
        _roomCB.addActionListener(this);

        // Vector[] vectCapacity = buildCapacityList();
        String capacity = getCapacity(vectR[0].get(0).toString());
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

    } // end buildRoomPanel

    private String getSelectedRoom(JPanel jPanel) {
        JPanel externalPanel = (JPanel) jPanel.getComponent(2);
        JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
        JPanel roomJPanel = (JPanel) myJPanel.getComponent(1);
        return ((JComboBox) roomJPanel.getComponent(0)).getSelectedItem()
                .toString();
    } // end getSelectedRoom

    /*
     * private String getSelectedCategory(JPanel jPanel) { JPanel externalPanel =
     * (JPanel) jPanel.getComponent(2); JPanel myJPanel = (JPanel)
     * externalPanel.getComponent(0); JPanel catJPanel = (JPanel)
     * myJPanel.getComponent(0); return
     * ((JComboBox)catJPanel.getComponent(0)).getSelectedItem().toString(); } //
     * end getSelectedCategory
     */

    private JPanel buildFixingPanel(int index) {
        EventAttach event = (EventAttach) ((DResource) _unities.get(index))
                .getAttach();
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

    private JPanel buildDurationPanel() {
        Vector thePeriods = buildThePeriods(_dApplic.getCurrentDModel()
                .getTTStructure().getCurrentCycle()
                .getMaxNumberOfPeriodsInASequence());
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
    } // end getSelectedDay

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

        // instructorsLists[_currentActivityIndex] = new JList(v.toArray());
        _applyPanel.setFirstEnable();
    }

    /**
     * item state changed
     * 
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
    private Vector buildUnitiesVector(String activityName) {
        // int nbTokens= DXToolsMethods.countTokens(activityName, ".");
        Vector unities = new Vector(1);
        // System.out.println("CounTokens: "+nbTokens);// debug
        String actID = DXToolsMethods.getToken(activityName, ".", 0);
        String typID = DXToolsMethods.getToken(activityName, ".", 1);

        if (typID.length() != 0) {
            String secID = DXToolsMethods.getToken(activityName, ".", 2);
            if (secID.length() != 0) {
                String unitID = DXToolsMethods.getToken(activityName, ".", 3);
                if (unitID.length() != 0) {
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
                    }// end for (int i=0; i<sect.getSetOfUnities().size();
                    // i++)
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
                    }// end for (int i=0; i<sect.getSetOfUnities().size();
                    // i++)
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
                    }// end for (int i=0; i<sect.getSetOfUnities().size();
                    // i++)
                }// end for(int i=0; i< type.getSetOfSections().size(); i++)
            }
        }// end else if(typID.length()!=0)
        return unities;
    }

    /**
     * build duration
     * 
     * @return
     */
    private String buildDuration() {
        EventAttach event = (EventAttach) ((DResource) _unities
                .get(_currentActivityIndex)).getAttach();
        int duration = event.getDuration()
                / _dApplic.getCurrentDModel().getTTStructure()
                        .getPeriodLenght();
        return String.valueOf(duration);
    }

    /**
     * build the hour list
     * 
     * @return Vector[] of two elements the first is a Vector containing
     * 
     * the second contains
     */
    private Vector[] buildHourList() {
        Vector list[] = { new Vector(), new Vector() };
        EventAttach event = (EventAttach) ((DResource) _unities
                .get(_currentActivityIndex)).getAttach();
        Cycle cycle = _dApplic.getCurrentDModel().getTTStructure()
                .getCurrentCycle();
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
                / _dApplic.getCurrentDModel().getTTStructure()
                        .getPeriodLenght();
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
     * build the day list
     * 
     * @return
     */
    private Vector[] buildDayList() {
        Vector list[] = { new Vector(1), new Vector(1) };
        EventAttach event = (EventAttach) ((DResource) _unities
                .get(_currentActivityIndex)).getAttach();
        Cycle cycle = _dApplic.getCurrentDModel().getTTStructure()
                .getCurrentCycle();
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
     * build the instructor list
     * 
     * @return
     */
    private Vector buildCurrentInstructorList(int index) {
        Vector<String> v = new Vector<String>();

        EventAttach event = (EventAttach) ((DResource) _unities.get(index))
                .getAttach();
        DxSetOfInstructors soi = _dApplic.getCurrentDModel()
                .getDxSetOfInstructors();
        long keys[] = event.getInstructorKey();
        for (int i = 0; i < keys.length; i++) {
            String sName = soi.getInstructorName(keys[i]);
            if (sName != null)
                v.add(sName);
        }

        return v;
    }

    private Vector buildInstructorList() {
        Vector<String> v = new Vector<String>();

        DxSetOfInstructors dxsoi = _dApplic.getCurrentDModel()
                .getDxSetOfInstructors();
        v = dxsoi.getNamesVector();
        v.add(DConst.NO_ROOM_INTERNAL);

        return v;
    }

    /**
     * build the room list
     * 
     * @return
     */
    private Vector[] buildRoomList() {// String category){
        Vector list[] = { new Vector<String>(), new Vector<String>() };
        EventAttach event = (EventAttach) ((DResource) _unities
                .get(_currentActivityIndex)).getAttach();
        if (DxFlags.newRooms) {
            DxSetOfRooms dxsor = _dApplic.getCurrentDModel().getDxSetOfRooms();
            DxRoom dxr = dxsor.getRoom(event.getRoomKey());
            if (dxr != null)
                list[0].add(dxr.getName());
            else
                list[0].add(DConst.NO_ROOM_INTERNAL);

            Iterator itDxSor = dxsor.iterator();
            while (itDxSor.hasNext()) {
                list[1].add(((DxRoom) itDxSor.next()).getName());
            }
            list[1].add(DConst.NO_ROOM_INTERNAL);

        } else {
            SetOfRooms sor = _dApplic.getCurrentDModel().getSetOfRooms();
            DResource room = sor.getResource(event.getRoomKey());
            if (room != null)
                list[0].add(room.getID());
            else
                list[0].add(DConst.NO_ROOM_INTERNAL);
            for (int i = 0; i < sor.size(); i++) {
                list[1].add(sor.getResourceAt(i).getID());
            }
            list[1].add(DConst.NO_ROOM_INTERNAL);
        }

        return list;
    }

    private String getCapacity(String str) {

        if (DxFlags.newRooms) {
            DxSetOfRooms dxsor = _dApplic.getCurrentDModel().getDxSetOfRooms();
            DxRoom dxr = dxsor.getRoom(str);
            if(dxr==null)
            {
                return "000";
            }
            return String.valueOf(dxr.getCapacity());
        }
        
        //Old Rooms
        SetOfRooms sor = _dApplic.getCurrentDModel().getSetOfRooms();
        DResource res = sor.getResource(str);
        if (res == null) {
            return "000";
        }
        RoomAttach ra = (RoomAttach) res.getAttach();
        return String.valueOf(ra.getCapacity());
    }

    /**
     * @return
     */
    /*
     * private Vector[] buildCapacityList() { Vector list[] = {new Vector(1),
     * new Vector(1)}; EventAttach event=
     * (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
     * SetOfRooms sor= _dm.getSetOfRooms(); //long dayKey=
     * Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
     * Resource room = sor.getResource(event.getRoomKey()); //if(room!=null) //
     * list[0].add(room.getID()); //else //
     * list[0].add(DConst.NO_ROOM_INTERNAL); for(int i=0; i< sor.size(); i++) {
     * RoomAttach ra = (RoomAttach) sor.getResourceAt(i).getAttach();
     * list[1].add(String.valueOf(ra.getCapacity())); }
     * list[1].add(DConst.NO_ROOM_INTERNAL); return list; }
     */
    private Vector[] buildCategoryRoomList() {
        Vector list[] = { new Vector(1), new Vector(1) };
        // EventAttach event=
        // (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
        // SetOfCategories soc= _dm.getSetOfCategories();
        // long dayKey=
        // Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
        // Resource room = sor.getResource(event.getRoomKey());
        // if(room!=null)
        // list[0].add(room.getID());
        // else
        list[0].add(DConst.NO_ROOM_INTERNAL);
        // for(int i=0; i< soc.size(); i++)
        // list[1].add(soc.getResourceAt(i).getID());
        list[1].add(DConst.NO_ROOM_INTERNAL);
        return list;
    }

    /**
     * apply change in a event
     */
    private boolean applyChanges() {
        Cycle cycle = _dApplic.getCurrentDModel().getTTStructure()
                .getCurrentCycle();
        EventAttach event = (EventAttach) ((DResource) _unities
                .get(_currentActivityIndex)).getAttach();
        // remove event
        _dApplic.getCurrentDModel().getConditionsTest().removeEventInTTs(
                _dApplic.getCurrentDModel().getTTStructure(),
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

        boolean fixedBut = isFixedButtonSelected(tpane); // =
        // ((JToggleButton)((JPanel)tpane.getComponent(3)).getComponent(1)).isSelected();
        int[] daytime = {
                Integer.parseInt(DXToolsMethods.getToken(day, ".", 0)),
                Integer.parseInt(DXToolsMethods.getToken(hour, ":", 0)),
                Integer.parseInt(DXToolsMethods.getToken(hour, ":", 1)) };
        String periodKey = cycle.getPeriod(daytime);
        event.setDuration(Integer.parseInt(duration)
                * _dApplic.getCurrentDModel().getTTStructure()
                        .getPeriodLenght());
        event.setKey(4, periodKey);
        event.setKey(1, intructorKeys);
        if (DxFlags.newRooms) {
            event.setKey(2, Long.toString(_dApplic.getCurrentDModel()
                    .getDxSetOfRooms().getRoomKeyByName(room)));
        } else {
            event.setKey(2, Long.toString(getResourceKey(_dApplic
                    .getCurrentDModel().getSetOfRooms(), room)));

        }

        event.setAssigned(assignBut);
        event.setPermanentState(fixedBut);
        Vector vect = new Vector();
        vect.add(_unities.get(_currentActivityIndex));
        _dApplic.getCurrentDModel().getSetOfEvents().updateActivities(
                _dApplic.getCurrentDModel().getSetOfActivities(), vect);
        // add event
        _dApplic.getCurrentDModel().getConditionsTest().addEventInTTs(
                _dApplic.getCurrentDModel().getTTStructure(),
                (DResource) _unities.get(_currentActivityIndex), false);
        return true;
    }

    private String getInstructorKeys(ListModel lm) {
        // TODO: DConst.newInstructors
        String a = "";
        for (int i = 0; i < lm.getSize(); i++) {
            long key = _dApplic.getCurrentDModel().getDxSetOfInstructors()
                    .getInstructorKey((String) lm.getElementAt(i));
            a += key + ":";
        }
        return a;
    }

    /**
     * get a resource key
     * 
     * @param soresc
     * @param elt
     * @return the resource key or -1 if key does not found
     */
    private long getResourceKey(SetOfRooms sor, String elt) {
        if (!elt.equalsIgnoreCase(DConst.NO_ROOM_INTERNAL)) {
            return sor.getResource(elt).getKey();
        }
        return -1;
    }

    private Vector buildThePeriods(int size) {
        Vector<String> v = new Vector<String>();
        for (int i = 0; i <= size; i++) {
            v.addElement(Integer.toString(i));
        }

        return v;

    }
}// end class
