/**
 *
 * Title: EditActivityDlg $Revision: 1.33 $  $Date: 2004-05-18 17:28:13 $
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
 * @version $Revision: 1.33 $
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
 */package dInterface.dAffectation;



import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Toolkit;

import java.awt.event.*;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.JList;

import dResources.DConst;
import dInterface.DApplication;
import dInternal.dConditionsTest.EventAttach;
import dInternal.dTimeTable.*;
import dInternal.dData.*;
import dInternal.dUtil.DXToolsMethods;

import com.iLib.gDialog.FatalProblemDlg;
import com.iLib.gDialog.InformationDlg;

import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;



public class EditActivityDlg 
		extends JDialog 
		implements ActionListener, ChangeListener{

  private DApplication _dApplic;
  private EventsDlgInterface _evDlgInt = null;
  private int _currentActivityIndex = 0;
  private boolean _isModified = false;
  private Vector _unities = new Vector();           // contains event resource
  private JList [] _instructorsLists;
  private JScrollPane _jScrollPane;
  private JTabbedPane _tabbedPane;
  private ButtonsPanel _applyPanel;
 
  /**
   * Constructor for activities
   * @param activityDialog The parent dialog of this dialog
   * @param dApplic The application
   * @param currentActivity The ativiti choiced in the activityDialog
   */
  
  
  public EditActivityDlg(JDialog dialog, 
  							DApplication dApplic, 
  							String currentActivity, 
  							boolean isModified) {
    super(dialog, DConst.T_AFFEC_DLG);//"Affectation d'évenement(s)");
    setLocationRelativeTo(dialog);
    _dApplic = dApplic;
    _isModified = isModified;
    _unities = buildUnitiesVector(currentActivity);
	initialize();
  } // end EditActivityDlg

  /**
   * Constructor for one event
   * @param dialog The parent dialog of this dialog
   * @param dApplic The application
   * @param currentActivity The activity choiced in the dialog
   * @param evDlg, 
   * @param isModified
   */
  public EditActivityDlg(JDialog dialog, 
  							DApplication dApplic, 
  							String currentActivity, 
  							EventsDlgInterface evDlg, 
  							boolean isModified) {
    super(dialog, DConst.EVENTS_DLG_TITLE);
	_evDlgInt= evDlg;
    setLocationRelativeTo(dialog);	
    _dApplic = dApplic;    
    _isModified= isModified;
    _unities = buildUnitiesVector(currentActivity);
    initialize();

  }
  /**
   * Initialize the dialog
   */
  private void initialize(){
    String [] a ={DConst.BUT_APPLY, DConst.BUT_CLOSE};
    _applyPanel = new TwoButtonsPanel(this, a);
    getContentPane().add(_applyPanel, BorderLayout.SOUTH);
    _tabbedPane = new JTabbedPane();
    //_tabbedPane.
    // Panels for tabbed Pane
	_instructorsLists =  new JList[_unities.size()] ;
    for (int i=0; i< _unities.size(); i++){
    	
      if(_unities.get(i)!=null){
        _currentActivityIndex=i;
        _tabbedPane.addTab(((Resource)_unities.get(i)).getID(), createUnityPanel(i, true, null));
      }
    }// end for
    _currentActivityIndex=0;
    getContentPane().add(_tabbedPane, BorderLayout.CENTER);
    _tabbedPane.addChangeListener(this);
    _tabbedPane.setSelectedIndex(_currentActivityIndex);
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension unitySize = new Dimension(350, 270);
    setBounds((screenSize.width - unitySize.width) / 2, (screenSize.height -
        unitySize.height) / 2, unitySize.width, unitySize.height);
    setResizable(true);
    setVisible(true);
  } // end init

  /**
   * action performed
   * @param e
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    if (command.equals(DConst.BUT_CLOSE)) {  // fermer
      dispose();
    } else if (command.equals(DConst.BUT_APPLY )) {  // apply
      boolean apply = false;
      for(int i=0; i< this._unities.size(); i++){
        _currentActivityIndex=i;
        apply = applyChanges();
        if(!apply){
          new FatalProblemDlg(this,"Valeur eronnée");
          break;
        } else
		_applyPanel.setFirstDisable();
      } // end for
      if(apply){
        _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().sendEvent();
        if(_evDlgInt!=null)
          _evDlgInt.initializePanel();
        //dispose();
      }

    } else if(command.equals("comboBoxChanged") || command.equals(DConst.BUT_PLACE)
              || command.equals(DConst.BUT_FIGE)){// comboBox has changed
      //System.out.println("Enable appliquer ... ");
	  _applyPanel.setFirstEnable();
    } else if(command.equals(DConst.BUT_CHANGE)){// change instrcutors
     //if (instructorsLists[_currentActivityIndex]= null)
      new SelectInstructors(_dApplic, this, 
      						makeVector(_instructorsLists[_currentActivityIndex]), 
      						buildInstructorList(_currentActivityIndex));
    }

  }
  
  private Vector makeVector(JList jList) {
  	Vector v = new Vector();
  	if (jList!= null) {
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
   public void stateChanged( ChangeEvent ce) {
   	
	if(!_applyPanel.isFirstEnable()){
		 _currentActivityIndex = ((JTabbedPane)ce.getSource()).getSelectedIndex();
  		_tabbedPane.setSelectedIndex(_currentActivityIndex);

	} else { 
		_tabbedPane.removeChangeListener(this);
		_tabbedPane.setSelectedIndex(_currentActivityIndex);
		new InformationDlg(this, "Appliquer ou fermer pour continuer", "Operation interdite");
		_tabbedPane.addChangeListener(this);
	}
   }// end state change


  /**
   * Builds a panel to be placed in a tab of the tabbedPane
   * @return a JPanel to be placed in a tab of the tabbedPane
   */
  private JPanel createUnityPanel(int index, boolean first, Vector newInstructors){
    EventAttach event= (EventAttach)((Resource)_unities.get(index)).getAttach();
    JLabel duration, day, hour, room, instructor;
    JComboBox  cbDuration, cbDay, cbHour, cbRoom;
    //JList instructorsList;
    JScrollPane cbInstructor;
    JToggleButton place, fix;
    duration = new JLabel(DConst.R_TIME_LENGTH);
    day = new JLabel(DConst.R_DAY_NAME);
    hour = new JLabel(DConst.R_ACTIVITY_BEGIN_HOUR);
    room = new JLabel(DConst.R_ROOM_NAME);
    instructor = new JLabel(DConst.R_INSTRUCTOR_NAME);

    JTextField resDuration= new JTextField(2);
    resDuration.setText(buildDuration());
    resDuration.setEnabled(_isModified);

    Vector[] vect = buildDayList();
    cbDay = new JComboBox(vect[1]);
    cbDay.addActionListener(this);
    cbDay.setSelectedItem(vect[0].get(0).toString());
    vect = buildHourList();
    cbHour = new JComboBox(vect[1]);
    cbHour.addActionListener(this);
    cbHour.setSelectedItem(vect[0].get(0).toString());
    vect= buildRoomList();
    cbRoom = new JComboBox(vect[1]);
    cbRoom.addActionListener(this);
    cbRoom.setSelectedItem(vect[0].get(0).toString());
    
	vect[0] = buildCurrentInstructorList(index);
	_instructorsLists[index] = new JList(vect[0].toArray());
   // if (first) {
   //   vect = buildInstructorList();
   //   instructorsList = new JList(vect[0].toArray());
   // } else {
   //   instructorsList = new JList(newInstructors);
   // }
    _jScrollPane = new JScrollPane(_instructorsLists[index]);
	_jScrollPane.setPreferredSize(new Dimension(170, 40));
    place = new JToggleButton(DConst.BUT_PLACE);
    place.setSelected(event.getAssignState());
    place.addActionListener(this);
    fix = new JToggleButton(DConst.BUT_FIGE);
    fix.addActionListener(this);
    fix.setSelected(event.getPermanentState());
    JPanel centerPanel = new JPanel(new GridLayout(4,1));
    JPanel panel = new JPanel(); //new GridLayout(1,6));
    // duration
    panel.add(duration);
    panel.add(resDuration);
    centerPanel.add(panel);
    // day and hour
    //panel = new JPanel();
    panel.add(day);
    panel.add(cbDay);
    panel.add(hour);
    panel.add(cbHour);
    centerPanel.add(panel);
    // room
    panel = new JPanel(); //new GridLayout(1,2));
    panel.add(room);
    panel.add(cbRoom);
    centerPanel.add(panel);
    // instructor
    panel = new JPanel(); //new GridLayout(1,3));
    panel.add(instructor);
    panel.add(_jScrollPane);
    JButton jButtonChange = new JButton( DConst.BUT_CHANGE );
    //jButtonChange.setPreferredSize(new Dimension(75, 22));
    jButtonChange.addActionListener(this);
    panel.add(jButtonChange); //to be used when adding instructors
    centerPanel.add(panel);
    // bottom
    JPanel buttomPanel = new JPanel();
    buttomPanel.add(place);
    buttomPanel.add(fix);
    centerPanel.add(panel);
    centerPanel.add(buttomPanel);
	_applyPanel.setFirstDisable();
    return centerPanel;
  }

  /**
   * Return vector of resources. each resource represent an event
   */
  private Vector buildUnitiesVector(String activityName){
    int nbTokens= DXToolsMethods.countTokens(activityName, ".");
    Vector unities= new Vector(1);
    //System.out.println("CounTokens: "+nbTokens);// debug
    String actID= DXToolsMethods.getToken(activityName,".",0);
    String typID= DXToolsMethods.getToken(activityName,".",1);
    //if (nbTokens==1)
    //typID= "1";
    if(typID.length()!=0){
      String secID= DXToolsMethods.getToken(activityName,".",2);
      if(secID.length()!=0){
        String unitID= DXToolsMethods.getToken(activityName,".",3);
        if(unitID.length()!=0){
          unities.add(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().
                       getResource(activityName));

        }else{// else unitID.length()!=0
          Section sect= _dApplic.getDMediator().getCurrentDoc().getDM().
                        getSetOfActivities().getSection(actID,typID,secID);
          for (int i=0; i<sect.getSetOfUnities().size(); i++){
            unities.add(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().
            getResource(actID+"."+typID+"."+secID+"."+sect.getSetOfUnities()
            .getResourceAt(i).getID()+"."));
          }// end for (int i=0; i<sect.getSetOfUnities().size(); i++)
        }// end else unitID.length()!=0
      }else{// else if(secID.length()!=0)
        Type type= _dApplic.getDMediator().getCurrentDoc().getDM().
                   getSetOfActivities().getType(actID,typID);
        for(int i=0; i< type.getSetOfSections().size(); i++){
          Section sect= _dApplic.getDMediator().getCurrentDoc().getDM().
                  getSetOfActivities().getSection(actID,typID,type.getSetOfSections().getResourceAt(i).getID());
          for (int j=0; j<sect.getSetOfUnities().size(); j++){
            unities.add(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().
            getResource(actID+"."+typID+"."+type.getSetOfSections().getResourceAt(i).getID()
            +"."+sect.getSetOfUnities().getResourceAt(j).getID()+"."));
          }// end for (int i=0; i<sect.getSetOfUnities().size(); i++)
        }// end for(int i=0; i< type.getSetOfSections().size(); i++)
      }// end else if(secID.length()!=0)
    }else{// else if(typID.length()!=0)
      Activity activity = (Activity)_dApplic.getDMediator().getCurrentDoc().getDM().
                          getSetOfActivities().getResource(actID).getAttach();
      for(int a=0; a<activity.getSetOfTypes().size(); a++ ){
        typID= activity.getSetOfTypes().getResourceAt(a).getID();
        Type type= _dApplic.getDMediator().getCurrentDoc().getDM().
                   getSetOfActivities().getType(actID,typID);
        for(int i=0; i< type.getSetOfSections().size(); i++){
          Section sect= _dApplic.getDMediator().getCurrentDoc().getDM().
                  getSetOfActivities().getSection(actID,typID,type.getSetOfSections().getResourceAt(i).getID());
          for (int j=0; j<sect.getSetOfUnities().size(); j++){
            unities.add(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().
            getResource(actID+"."+typID+"."+type.getSetOfSections().getResourceAt(i).getID()
            +"."+sect.getSetOfUnities().getResourceAt(j).getID()+"."));
          }// end for (int i=0; i<sect.getSetOfUnities().size(); i++)
        }// end for(int i=0; i< type.getSetOfSections().size(); i++)
      }
    }// end else if(typID.length()!=0)
    return unities;
  }


  /**
   *build duration
   * @return
   */
  private String buildDuration(){
    EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    int duration = event.getDuration()/_dApplic.getDMediator()
                 .getCurrentDoc().getDM().getTTStructure().getPeriodLenght();
    return String.valueOf(duration);
  }

  /**
   *build the hour list
   * @return
   */
  private Vector[] buildHourList(){
    Vector list[] = {new Vector(1), new Vector(1)};
    EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    Cycle cycle= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getCurrentCycle();
    long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
    long seqKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",1));
    long perKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",2));
    Period period= cycle.getPeriodByKey(dayKey,seqKey,perKey);
    list[0].add(period.getBeginHour()[0]+":"+period.getBeginHour()[1]);
    Day day = (Day)cycle.getSetOfDays().getResource(dayKey).getAttach();
    int[] avoidPriority={};
    int duration = event.getDuration()/_dApplic.getDMediator()
                 .getCurrentDoc().getDM().getTTStructure().getPeriodLenght();
    for (int i=0; i< day.getSetOfSequences().size(); i++){
      Sequence seq = (Sequence)day.getSetOfSequences().getResourceAt(i).getAttach();
      for (int j=0; j< seq.getSetOfPeriods().size(); j++){
        period = (Period)seq.getSetOfPeriods().getResourceAt(j).getAttach();
        if (cycle.isPeriodContiguous(dayKey,day.getSetOfSequences().getResourceAt(i).getKey()
                                     ,seq.getSetOfPeriods().getResourceAt(j).getKey(),duration, avoidPriority,true)){
          list[1].add(period.getBeginHour()[0]+":"+period.getBeginHour()[1]);
        }// end if (cycle.isPeriodContiguous(p
      }// end for (int j=0; j< seq.getSetOfPeriods().size(); j++)
    }// end for (int i=0; i< day.getSetOfSequences().size(); i++)
    return list;
  }

  /**
   *build the day list
   * @return
   */
  private Vector[] buildDayList(){
    Vector list[] = {new Vector(1), new Vector(1)};
    EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    Cycle cycle= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getCurrentCycle();
    long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
    Resource day = cycle.getSetOfDays().getResource(dayKey);
    list[0].add(day.getKey()+"."+day.getID());
    for(int i=0; i< cycle.getSetOfDays().size(); i++)
      list[1].add(cycle.getSetOfDays().getResourceAt(i).getKey()+"."+cycle.getSetOfDays().getResourceAt(i).getID());
    return list;
  }

  /**
   *build the instructor list
   * @return
   */
  private Vector buildCurrentInstructorList(int index){
    Vector v = new Vector();//, new Vector(1)};
    EventAttach event= (EventAttach)((Resource)_unities.get(index)).getAttach();
    SetOfInstructors soi= _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfInstructors();
    //long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
    long keys [] = event.getInstructorKey();
    for (int i = 0 ; i < keys.length ; i ++ ) {
      Resource instructor = soi.getResource(keys[i]);
      if(instructor != null)
        v.add(instructor.getID());   
    }
	return v;
  }
    
	private Vector buildInstructorList(int index){
		Vector v = new Vector();//, new Vector(1)};
		EventAttach event= (EventAttach)((Resource)_unities.get(index)).getAttach();
		SetOfInstructors soi= _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfInstructors();
		//long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
		long keys [] = event.getInstructorKey();
    for(int i=0; i< soi.size(); i++)
      v.add(soi.getResourceAt(i).getID());
    v.add(DConst.NO_ROOM_INTERNAL);
    return v;
  }

  /**
   *build the room list
   * @return
   */
  private Vector[] buildRoomList(){
    Vector list[] = {new Vector(1), new Vector(1)};
    EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    SetOfRooms sor= _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfRooms();
    //long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
    Resource room = sor.getResource(event.getRoomKey());
    if(room!=null)
      list[0].add(room.getID());
    else
      list[0].add(DConst.NO_ROOM_INTERNAL);
    for(int i=0; i< sor.size(); i++)
      list[1].add(sor.getResourceAt(i).getID());
    list[1].add(DConst.NO_ROOM_INTERNAL);
    return list;
  }

  /**
   * apply change in a event
   */
  private boolean applyChanges(){
    Cycle cycle= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getCurrentCycle();
    EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    //remove event
    _dApplic.getDMediator().getCurrentDoc().getDM().getConditionsTest().addOrRemEventInTTs((Resource)_unities.get(_currentActivityIndex),-1,false);

    JPanel tpane= ((JPanel)_tabbedPane.getComponentAt(_currentActivityIndex));
    String duration= ((JTextField)((JPanel)tpane.getComponent(0)).getComponent(1)).getText();
    if ((!DXToolsMethods.isIntValue(duration)) || (Integer.parseInt(duration)<0))
      return false;
    String day= ((JComboBox)((JPanel)tpane.getComponent(0)).getComponent(3)).getSelectedItem().toString();
    String hour= ((JComboBox)((JPanel)tpane.getComponent(0)).getComponent(5)).getSelectedItem().toString();
    String room= ((JComboBox)((JPanel)tpane.getComponent(1)).getComponent(1)).getSelectedItem().toString();
   // String instructor= ((JComboBox)((JPanel)tpane.getComponent(3)).getComponent(1)).getSelectedItem().toString();
    JList instructor= ((JList)((JScrollPane)((JPanel)tpane.getComponent(2)).getComponent(1)).getViewport().getComponent(0));
    ListModel lm = (ListModel) instructor.getModel();
    String  intructorKeys = getInstructorKeys(lm);
    boolean assignBut= ((JToggleButton)((JPanel)tpane.getComponent(3)).getComponent(0)).isSelected();
    boolean permanentBut= ((JToggleButton)((JPanel)tpane.getComponent(3)).getComponent(1)).isSelected();
    int[] daytime= {Integer.parseInt(DXToolsMethods.getToken(day,".",0)),Integer.parseInt(DXToolsMethods.getToken(hour,":",0)),
      Integer.parseInt(DXToolsMethods.getToken(hour,":",1))};
    String periodKey= cycle.getPeriod(daytime);
    event.setDuration( Integer.parseInt(duration)*_dApplic.getDMediator()
                 .getCurrentDoc().getDM().getTTStructure().getPeriodLenght());
    event.setKey(4,periodKey);
    event.setKey(1,intructorKeys);
    event.setKey(2,Long.toString(getResourceKey(_dApplic.getDMediator().getCurrentDoc().
                                  getDM().getSetOfRooms(),room)));
    event.setAssignState(assignBut);
    event.setPermanentState(permanentBut);
    Vector vect= new Vector();
    vect.add(_unities.get(_currentActivityIndex));
    _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().updateActivities(vect);
    //add event
    _dApplic.getDMediator().getCurrentDoc().getDM().getConditionsTest().addOrRemEventInTTs((Resource)_unities.get(_currentActivityIndex),1,false);
    return true;
  }


  private String  getInstructorKeys(ListModel lm){
    String a =  "";
    for (int i = 0 ; i < lm.getSize(); i++) {
      long key = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfInstructors().getResource((String) lm.getElementAt(i)).getKey();
      a+= key + ":";
    }
    return a;
  }
  /**
   * get a resource key
   * @param soresc
   * @param elt
   * @return the resource key or -1 if key does not found
   */
  private long getResourceKey(SetOfResources soresc, String elt){
    if (!elt.equalsIgnoreCase(DConst.NO_ROOM_INTERNAL)){
      return soresc.getResource(elt).getKey();
    }
    return -1;
  }

}// end class

/*
if(!_buttonsPanel.isFirstEnable()){
	new EditActivityDlg(_jDialog,_dApplic, (String)selectedItems[0], this, false);
	_buttonsPanel.setFirstDisable();
} else { 
	new InformationDlg(_jDialog, "Appliquer ou fermer pour continuer", "Operation interdite");
}
*/