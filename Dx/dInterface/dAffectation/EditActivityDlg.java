/**
 *
 * Title: EditActivityDlg $Revision: 1.34 $  $Date: 2004-06-02 20:39:07 $
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
 * @version $Revision: 1.34 $
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
package dInterface.dAffectation;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import dResources.DConst;
import dInterface.DApplication;
import dInternal.DModel;
import dInternal.dConditionsTest.EventAttach;
import dInternal.dData.Activity;
import dInternal.dData.Resource;
import dInternal.dData.Section;
import dInternal.dData.SetOfInstructors;
import dInternal.dData.SetOfResources;
import dInternal.dData.SetOfRooms;
import dInternal.dData.Type;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dUtil.DXToolsMethods;

import com.iLib.gDialog.FatalProblemDlg;
import com.iLib.gDialog.InformationDlg;

import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;



public class EditActivityDlg 
		extends JDialog 
		implements ActionListener, ChangeListener{

	private DApplication _dApplic;
	private DModel _dm;
	private EventsDlgInterface _evDlgInt = null;
	private int _currentActivityIndex = 0;
	private boolean _canBeModified = false;
	private Vector _unities = new Vector();           // contains event resource
	private JList [] _instructorsLists;
	private JScrollPane _jScrollPane;
	private JTabbedPane _tabbedPane;
	private ButtonsPanel _applyPanel;
	private Vector _thePeriods;

	 
	  /**
	   * Constructor for EditActivityDlg in the case of one or more
	   *        events
	   * @param activityDialog The parent dialog of this dialog
	   * @param dApplic The application
	   * @param currentActivity The activity choiced in the activityDialog
	   * @param canBeModified
	   */
	  
	  
	  public EditActivityDlg(JDialog dialog, 
	  							DApplication dApplic, 
	  							String currentActivity, 
	  							boolean canBeModified) {
	    super(dialog, DConst.T_AFFEC_DLG);//"Affectation d'évenement(s)");
	    continueContructor(dialog, dApplic, currentActivity, canBeModified);
	 
	  } // end EditActivityDlg
	
	  /**
	   * Constructor for EditActivityDlg, in the case of one event
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
	  							boolean canBeModified) {
	    super(dialog, DConst.EVENTS_DLG_TITLE);
		_evDlgInt= evDlg;
		continueContructor(dialog, dApplic, currentActivity, canBeModified);
	
	  }
	  
	private void continueContructor(JDialog dialog, DApplication dApplic, 
	  								String currentActivity, boolean canBeModified){
		setLocationRelativeTo(dialog);
		_dApplic = dApplic;
		_dm = dApplic.getDMediator().getCurrentDoc().getDM();
		_canBeModified = canBeModified;
			//to verify  
		_thePeriods = buildThePeriods(dApplic.getDMediator().getCurrentDoc().getTTStructure().getCurrentCycle().getMaxNumberOfPeriodsInASequence());
		_unities = buildUnitiesVector(currentActivity);
		_instructorsLists = new JList[_unities.size()];
		    //to verify
		initialize();
	}
  
  

  /**
   * Initialize the dialog
   */
	private void initialize(){
		int FACTOR = 50;
		
		_tabbedPane = buildTabbedPane();
		//myJPanel.add(_tabbedPane, BorderLayout.CENTER);	    
	    this.getContentPane().add(_tabbedPane, BorderLayout.CENTER);
	    _tabbedPane.addChangeListener(this);
		_currentActivityIndex=0;
	    _tabbedPane.setSelectedIndex(_currentActivityIndex);
	    
		String [] a ={DConst.BUT_APPLY, DConst.BUT_CLOSE};
		_applyPanel = new TwoButtonsPanel(this, a);
		//myJPanel.add(_tabbedPane, BorderLayout.SOUTH);	 
		getContentPane().add(_applyPanel, BorderLayout.SOUTH);
		_applyPanel.setFirstDisable();
	    
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    
	    this.setBounds(screenSize.width/ 4, 
						screenSize.height/ 4, 
						screenSize.width/ 2, 
						screenSize.height/ 2 );	
						
	    this.setResizable(true);
	    this.setVisible(true);
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
	          new FatalProblemDlg(this,"Valeur erronée");
	          break;
	        } else
			_applyPanel.setFirstDisable();
	      } // end for
	      if(apply){
	        _dm.getTTStructure().sendEvent();
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
  
	private JTabbedPane buildTabbedPane() {
		JTabbedPane jtp = new JTabbedPane();
		_instructorsLists =  new JList[_unities.size()] ;
		for (int i=0; i< _unities.size(); i++){  	
			if(_unities.get(i)!= null){
				_currentActivityIndex = i;
				jtp.addTab(((Resource)_unities.get(i)).getID(),
				              buildUnityPanel(i, true, null));
			}  // end if
		}// end for
			return jtp;
	}// end buildTabbedPane


	/**
	 * Builds a panel to be placed in a tab of the tabbedPane
	 * @return the JPanel to be placed in a tab of the tabbedPane
	 */
	private JPanel buildUnityPanel(int index, boolean first, Vector newInstructors){
		JPanel myPanel = new JPanel(); 
		myPanel.setLayout(new GridLayout(4,1));	
		JPanel timePanel = buildTimePanel();
		JPanel instructorPanel = buildInstructorPanel(index);
		JPanel roomPanel = buildRoomPanel();
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

		instructorsPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.R_INSTRUCTOR_NAME));

		JButton jButtonChange = new JButton( DConst.BUT_CHANGE );
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
		
		return (JList)(jsp.getViewport()).getComponent(0);
	} // getInstructorsList
	
	private JPanel buildRoomPanel() {
		JPanel myPanel = new JPanel();
		JPanel roomPanel = new JPanel();
		Vector[] vect =  buildRoomList();
		JComboBox roomCB = new JComboBox(vect[1]);
		roomCB.setSelectedItem(vect[0].get(0).toString());
		roomCB.addActionListener(this);
	   
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.R_ROOM_NAME));
		roomPanel.add(roomCB);
		myPanel.add(roomPanel);
		
		return myPanel;

	} //end  buildRoomPanel
	
	private String getSelectedRoom(JPanel jPanel) {		
		JPanel externalPanel = (JPanel) jPanel.getComponent(2);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
	
		return ((JComboBox)myJPanel.getComponent(0)).getSelectedItem().toString();		
	} // end getSelectedRoom
	
	
	private JPanel buildFixingPanel(int index) {
		EventAttach event = (EventAttach)((Resource)_unities.get(index)).getAttach();
		JPanel myPanel = new JPanel();
		JPanel fixingPanel = new JPanel();
		fixingPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.R_ASSIGN));
		JToggleButton assigned = new JToggleButton(DConst.BUT_PLACE);
		assigned.setSelected(event.getAssignState());
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
		
		return ((JToggleButton)(myJPanel.getComponent(0))).isSelected();
		
	} // end isAssignedButtonSelected
 
	private boolean isFixedButtonSelected(JPanel jPanel) {
		JPanel externalPanel = (JPanel) jPanel.getComponent(3);
		JPanel myJPanel = (JPanel) externalPanel.getComponent(0);
		
		return ((JToggleButton)(myJPanel.getComponent(1))).isSelected();
	} //end isFixedButtonSelected
	
	private JPanel buildDurationPanel() {
		JPanel durationPanel = new JPanel();
		JComboBox periodsCB = new JComboBox(_thePeriods);

		durationPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.R_TIME_LENGTH));
		durationPanel.setName(DConst.R_TIME_LENGTH);
		durationPanel.add(periodsCB);
		
		if (_canBeModified) {
			periodsCB.setSelectedItem(_thePeriods.get(0).toString());
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
		return ((JComboBox)dP.getComponent(0)).getSelectedItem().toString();
	} // end getSelectedDuration
	
	private JPanel buildDayPanel() {
		JPanel dayPanel = new JPanel();
		Vector[] vect = buildDayList();
		JComboBox dayCB = new JComboBox(vect[1]);
						
		dayPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.R_DAY_NAME));
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
						
		hourPanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.R_ACTIVITY_BEGIN_HOUR));
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
   public void stateChanged(ChangeEvent ce) {
   	
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
   * Return vector of resources. each resource represent an event
   */
  private Vector buildUnitiesVector(String activityName){
    int nbTokens= DXToolsMethods.countTokens(activityName, ".");
    Vector unities= new Vector(1);
    //System.out.println("CounTokens: "+nbTokens);// debug
    String actID= DXToolsMethods.getToken(activityName,".",0);
    String typID= DXToolsMethods.getToken(activityName,".",1);
    
    if(typID.length()!=0){
      String secID= DXToolsMethods.getToken(activityName,".",2);
      if(secID.length()!=0){
        String unitID= DXToolsMethods.getToken(activityName,".",3);
        if(unitID.length()!=0){
          unities.add(_dm.getSetOfEvents().
                       getResource(activityName));

        }else{// else unitID.length()!=0
          Section sect= _dm.getSetOfActivities().getSection(actID,typID,secID);
          for (int i=0; i<sect.getSetOfUnities().size(); i++){
            unities.add(_dm.getSetOfEvents().
            getResource(actID+"."+typID+"."+secID+"."+sect.getSetOfUnities()
            .getResourceAt(i).getID()+"."));
          }// end for (int i=0; i<sect.getSetOfUnities().size(); i++)
        }// end else unitID.length()!=0
      }else{// else if(secID.length()!=0)
        Type type= _dm.getSetOfActivities().getType(actID,typID);
        for(int i=0; i< type.getSetOfSections().size(); i++){
          Section sect= _dm.getSetOfActivities().getSection(actID,typID,type.getSetOfSections().getResourceAt(i).getID());
          for (int j=0; j<sect.getSetOfUnities().size(); j++){
            unities.add(_dm.getSetOfEvents().
            getResource(actID+"."+typID+"."+type.getSetOfSections().getResourceAt(i).getID()
            +"."+sect.getSetOfUnities().getResourceAt(j).getID()+"."));
          }// end for (int i=0; i<sect.getSetOfUnities().size(); i++)
        }// end for(int i=0; i< type.getSetOfSections().size(); i++)
      }// end else if(secID.length()!=0)
    }else{// else if(typID.length()!=0)
      Activity activity = (Activity)_dm.getSetOfActivities().getResource(actID).getAttach();
      for(int a=0; a<activity.getSetOfTypes().size(); a++ ){
        typID= activity.getSetOfTypes().getResourceAt(a).getID();
        Type type= _dm.getSetOfActivities().getType(actID,typID);
        for(int i=0; i< type.getSetOfSections().size(); i++){
          Section sect= _dm.getSetOfActivities().getSection(actID,typID,type.getSetOfSections().getResourceAt(i).getID());
          for (int j=0; j<sect.getSetOfUnities().size(); j++){
            unities.add(_dm.getSetOfEvents().
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
    int duration = event.getDuration()/_dm.getTTStructure().getPeriodLenght();
    return String.valueOf(duration);
  }

  /**
   * build the hour list
   * @return Vector[] of two elements the first is a Vector containing
   * 
   *                  the second contains 
   */
  private Vector[] buildHourList(){
    Vector list[] = {new Vector(), new Vector()};
    EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    Cycle cycle= _dm.getTTStructure().getCurrentCycle();
    long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
    long seqKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",1));
    long perKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",2));
    Period period= cycle.getPeriodByKey(dayKey,seqKey,perKey);
    list[0].add(period.getBeginHour()[0]+":"+period.getBeginHour()[1]);
    Day day = (Day)cycle.getSetOfDays().getResource(dayKey).getAttach();
    int[] avoidPriority={};
    int duration = event.getDuration()/_dm.getTTStructure().getPeriodLenght();
    for (int i=0; i< day.getSetOfSequences().size(); i++){
      Sequence seq = (Sequence)day.getSetOfSequences().getResourceAt(i).getAttach();
      for (int j=0; j< seq.getSetOfPeriods().size(); j++){
        period = (Period)seq.getSetOfPeriods().getResourceAt(j).getAttach();
        if (cycle.isPeriodContiguous(dayKey,day.getSetOfSequences().getResourceAt(i).getKey(),
                                     seq.getSetOfPeriods().getResourceAt(j).getKey(),
                                     duration, avoidPriority, true)){
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
    Cycle cycle= _dm.getTTStructure().getCurrentCycle();
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
    SetOfInstructors soi= _dm.getSetOfInstructors();
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
		SetOfInstructors soi= _dm.getSetOfInstructors();
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
    SetOfRooms sor= _dm.getSetOfRooms();
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
    Cycle cycle= _dm.getTTStructure().getCurrentCycle();
    EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    //remove event
	_dm.getConditionsTest().addOrRemEventInTTs((Resource)_unities.get(_currentActivityIndex),-1,false);

    JPanel tpane= ((JPanel)_tabbedPane.getComponentAt(_currentActivityIndex));
	String duration = getSelectedDuration(tpane);
    if ((!DXToolsMethods.isIntValue(duration)) || (Integer.parseInt(duration)<0))
      return false;
    String day = getSelectedDay(tpane);
	String hour = getSelectedHour(tpane);
    JList instructor = getInstructorsList(tpane);
    ListModel lm = (ListModel) instructor.getModel();
    String  intructorKeys = getInstructorKeys(lm);
    
	String room = getSelectedRoom(tpane);

    boolean assignBut = isAssignedButtonSelected(tpane); 
//= ((JToggleButton)((JPanel)tpane.getComponent(3)).getComponent(0)).isSelected();
    boolean fixedBut = isFixedButtonSelected(tpane); //= ((JToggleButton)((JPanel)tpane.getComponent(3)).getComponent(1)).isSelected();
    int[] daytime= {Integer.parseInt(DXToolsMethods.getToken(day,".",0)),Integer.parseInt(DXToolsMethods.getToken(hour,":",0)),
      Integer.parseInt(DXToolsMethods.getToken(hour,":",1))};
    String periodKey= cycle.getPeriod(daytime);
    event.setDuration( Integer.parseInt(duration)*_dm.getTTStructure().getPeriodLenght());
    event.setKey(4,periodKey);
    event.setKey(1,intructorKeys);
    event.setKey(2,Long.toString(getResourceKey(_dm.getSetOfRooms(),room)));
    event.setAssignState(assignBut);
    event.setPermanentState(fixedBut);
    Vector vect= new Vector();
    vect.add(_unities.get(_currentActivityIndex));
	_dm.getSetOfEvents().updateActivities(vect);
    //add event
	_dm.getConditionsTest().addOrRemEventInTTs((Resource)_unities.get(_currentActivityIndex),1,false);
    return true;
  }


  private String  getInstructorKeys(ListModel lm){
    String a =  "";
    for (int i = 0 ; i < lm.getSize(); i++) {
      long key = _dm.getSetOfInstructors().getResource((String) lm.getElementAt(i)).getKey();
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
  private Vector  buildThePeriods(int size) {
	Vector v = new Vector();
	for (int i=0; i <= size ; i++) {
		v.addElement(Integer.toString(i));
	}
  	
	return v;
  	
  }
}// end class

