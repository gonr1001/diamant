package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.event.*;
import java.awt.Dimension;
import java.util.Vector;
import java.util.StringTokenizer;
import javax.swing.event.*;
import javax.swing.*;

import dResources.DConst;
import dInterface.DApplication;
import dInternal.dConditionsTest.EventAttach;
import dInternal.dTimeTable.*;
import dInternal.dData.*;
import dInternal.dUtil.DXToolsMethods;

import com.iLib.gDialog.FatalProblemDlg;

public class EditActivityDlg extends JDialog implements ActionListener, ChangeListener{

  private DApplication _dApplic;
  private int _currentActivityIndex=0;
  private String _DURATION= "Durée:";
  private String _DAY= "Jour:";
  private String _HOUR="Heure de début:";
  private String _INSTRUCTOR= "Enseignant:";
  private String _ROOM= "Local:";
  private String _UNAVAILABLE= "------";
  private boolean _isModified=false;
  //private String _DIALOGMESSAGE= "Affectation d'évenement";
  Vector _unities = new Vector();// contains event resource

  JTabbedPane _tabbedPane;
  JPanel _bottomPanel;
  JButton _jButtonApply, _jButtonClose;

  /**
   * Constructor
   * @param activityDialog The parent dialog of this dialog
   * @param dApplic The application
   * @param currentActivity The ativiti choiced in the activityDialog
   */
  public EditActivityDlg(JDialog dialog, DApplication dApplic, String currentActivity, boolean isModified) {
    super(dialog, "Affectation d'évenement");
    setLocationRelativeTo(dialog);
    _dApplic = dApplic;
    _isModified= isModified;
    //_activityName = currentActivity;
    buildUnitiesVector(currentActivity);
    jbInit();

  }

  /**
   * Initialize the dialog
   */
  private void jbInit(){
    _bottomPanel = new JPanel();
    _jButtonApply = new JButton( DConst.BUT_APPLY );
    _jButtonApply.setPreferredSize(new Dimension(80, 22));
    _jButtonApply.addActionListener(this);
    //_jButtonApply.setEnabled(false);
    _jButtonClose = new JButton( DConst.BUT_CLOSE );
    _jButtonClose.setPreferredSize(new Dimension(75, 22));
    _jButtonClose.addActionListener(this);
    _bottomPanel.add(_jButtonApply);
    _bottomPanel.add(_jButtonClose);
    getContentPane().add(_bottomPanel, BorderLayout.SOUTH);
    _tabbedPane = new JTabbedPane();
    //_tabbedPane.
    for (int i=0; i< _unities.size(); i++){
      if(_unities.get(i)!=null){
        _currentActivityIndex=i;
        _tabbedPane.addTab(((Resource)_unities.get(i)).getID(), createUnityPanel(i));
      }
    }// end for
    _currentActivityIndex=0;
    getContentPane().add(_tabbedPane, BorderLayout.CENTER);
    _tabbedPane.addChangeListener(this);
    _tabbedPane.setSelectedIndex(_currentActivityIndex);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension unitySize = new Dimension(300, 270 );
    setBounds((screenSize.width - unitySize.width) / 2, (screenSize.height -
        unitySize.height) / 2, unitySize.width, unitySize.height);
    setResizable(false);
    setVisible(true);
  }

  /**
   * action performed
   * @param e
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //boolean _change = false, _restore = false;
    if (command.equals(DConst.BUT_CLOSE)) {  // fermer
      boolean apply=false;
      for(int i=0; i< this._unities.size(); i++){
        _currentActivityIndex=i;
        apply= applyChanges();
        if(!apply){
          new FatalProblemDlg(this,"Valeur eronnée");
          break;
        }
      }
      if(apply){
      _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().sendEvent();
      dispose();
      }

    } else if (command.equals( DConst.BUT_APPLY )) {  // apply
      if( applyChanges()){
        _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().sendEvent();
      }else
        new FatalProblemDlg(this,"Valeur eronnée");
    }

  }

  /**
   * item state changed
   * @param e
   */
   public void stateChanged( ChangeEvent ce) {
    _currentActivityIndex = ((JTabbedPane)ce.getSource()).getSelectedIndex();
    System.out.println("_currentActivityIndex: "+_currentActivityIndex);//debug
    //_tabbedPane.setComponentAt( _currentActivityIndex, ((JPanel)_tabbedPane.getComponentAt(_currentActivityIndex)));//createUnityPanel(_currentActivityIndex) );
    _tabbedPane.setSelectedIndex(_currentActivityIndex);
  }


  /**
   * Builds a panel to be placed in a tab of the tabbedPane
   * @return a JPanel to be placed in a tab of the tabbedPane
   */
  public JPanel createUnityPanel(int index){
    JPanel centerPanel = new JPanel(new GridLayout(0,1));
    EventAttach event= (EventAttach)((Resource)_unities.get(index)).getAttach();
    JPanel panel = new JPanel();
    JLabel duration, day, hour, room, instructor;
    JComboBox  cbDuration, cbDay, cbHour, cbRoom, cbInstructor;
    JToggleButton place, fix;
    duration = new JLabel(_DURATION);
    day = new JLabel(_DAY);
    hour = new JLabel(_HOUR);
    room = new JLabel(_ROOM);
    instructor = new JLabel(_INSTRUCTOR);
    //resDuration= new JLabel(buildDuration());
    JTextField resDuration= new JTextField(2);
    resDuration.setText(buildDuration());
    resDuration.setEnabled(_isModified);
    Vector[] vect = buildDayList();
    cbDay = new JComboBox(vect[1]);
    cbDay.setSelectedItem(vect[0].get(0).toString());
    vect = buildHourList();
    cbHour = new JComboBox(vect[1]);
    cbHour.setSelectedItem(vect[0].get(0).toString());
    vect= buildRoomList();
    cbRoom = new JComboBox(vect[1]);
    cbRoom.setSelectedItem(vect[0].get(0).toString());
    vect = buildInstructorList();
    cbInstructor = new JComboBox(vect[1]);
    cbInstructor.setPreferredSize(new Dimension(163,25));
    cbInstructor.setSelectedItem(vect[0].get(0).toString());
    place = new JToggleButton(DConst.BUT_PLACE);
    place.setSelected(event.getAssignState());
    fix = new JToggleButton(DConst.BUT_FIGE);
    fix.setSelected(event.getPermanentState());
    // duration
    panel.add(duration);
    panel.add(resDuration);
    centerPanel.add(panel);
    // day and hour
    panel = new JPanel();
    panel.add(day);
    panel.add(cbDay);
    panel.add(hour);
    panel.add(cbHour);
    centerPanel.add(panel);
    // room
    panel = new JPanel();
    panel.add(room);
    panel.add(cbRoom);
    centerPanel.add(panel);
    // instructor
    panel = new JPanel();
    panel.add(instructor);
    panel.add(cbInstructor);
    centerPanel.add(panel);
    // bottom
    JPanel buttomPanel = new JPanel();
    buttomPanel.add(place);
    buttomPanel.add(fix);
    centerPanel.add(panel);
    centerPanel.add(buttomPanel);
    return centerPanel;
  }

  /**
   *Return vector of resource. each resource represent an event
   */
  private void buildUnitiesVector(String activityName){
    int nbTokens= DXToolsMethods.countTokens(activityName, ".");
    //Vector unities= new Vector(1);
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
          _unities.add(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().
                       getResource(activityName));

        }else{// else unitID.length()!=0
          Section sect= _dApplic.getDMediator().getCurrentDoc().getDM().
                        getSetOfActivities().getSection(actID,typID,secID);
          for (int i=0; i<sect.getSetOfUnities().size(); i++){
            _unities.add(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().
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
            _unities.add(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().
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
            _unities.add(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().
            getResource(actID+"."+typID+"."+type.getSetOfSections().getResourceAt(i).getID()
            +"."+sect.getSetOfUnities().getResourceAt(j).getID()+"."));
          }// end for (int i=0; i<sect.getSetOfUnities().size(); i++)
        }// end for(int i=0; i< type.getSetOfSections().size(); i++)
      }
    }// end else if(typID.length()!=0)
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
                                     ,seq.getSetOfPeriods().getResourceAt(j).getKey(),duration, avoidPriority)){
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
  private Vector[] buildInstructorList(){
    Vector list[] = {new Vector(1), new Vector(1)};
    EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    SetOfInstructors soi= _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfInstructors();
    //long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
    Resource instructor = soi.getResource(event.getInstructorKey());
    if(instructor!=null)
      list[0].add(instructor.getID());
    else
      list[0].add(_UNAVAILABLE);
    for(int i=0; i< soi.size(); i++)
      list[1].add(soi.getResourceAt(i).getID());
    list[1].add(_UNAVAILABLE);
    return list;
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
      list[0].add(_UNAVAILABLE);
    for(int i=0; i< sor.size(); i++)
      list[1].add(sor.getResourceAt(i).getID());
    list[1].add(_UNAVAILABLE);
    return list;
  }

  /**
   * apply change in a event
   */
  private boolean applyChanges(){
    Cycle cycle= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getCurrentCycle();
    EventAttach event= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    //remove event
    _dApplic.getDMediator().getCurrentDoc().getDM().getConditionsTest().addOrRemEventInTTs((Resource)_unities.get(_currentActivityIndex),-1);

    JPanel tpane= ((JPanel)_tabbedPane.getComponentAt(_currentActivityIndex));
    String duration= ((JTextField)((JPanel)tpane.getComponent(0)).getComponent(1)).getText();
    if ((!DXToolsMethods.isIntValue(duration)) || (Integer.parseInt(duration)<1))
      return false;
    String day= ((JComboBox)((JPanel)tpane.getComponent(1)).getComponent(1)).getSelectedItem().toString();
    String hour= ((JComboBox)((JPanel)tpane.getComponent(1)).getComponent(3)).getSelectedItem().toString();
    String room= ((JComboBox)((JPanel)tpane.getComponent(2)).getComponent(1)).getSelectedItem().toString();
    String instructor= ((JComboBox)((JPanel)tpane.getComponent(3)).getComponent(1)).getSelectedItem().toString();
    boolean assignBut= ((JToggleButton)((JPanel)tpane.getComponent(4)).getComponent(0)).isSelected();
    boolean permanentBut= ((JToggleButton)((JPanel)tpane.getComponent(4)).getComponent(1)).isSelected();
    int[] daytime= {Integer.parseInt(DXToolsMethods.getToken(day,".",0)),Integer.parseInt(DXToolsMethods.getToken(hour,":",0)),
      Integer.parseInt(DXToolsMethods.getToken(hour,":",1))};
    String periodKey= cycle.getPeriod(daytime);
    event.setDuration( Integer.parseInt(duration)*_dApplic.getDMediator()
                 .getCurrentDoc().getDM().getTTStructure().getPeriodLenght());
    event.setKey(4,periodKey);
    event.setKey(1,Long.toString(getResourceKey(_dApplic.getDMediator().getCurrentDoc().
                                  getDM().getSetOfInstructors(),instructor)));
    event.setKey(2,Long.toString(getResourceKey(_dApplic.getDMediator().getCurrentDoc().
                                  getDM().getSetOfRooms(),room)));
    event.setAssignState(assignBut);
    event.setPermanentState(permanentBut);
    Vector vect= new Vector();
    vect.add(_unities.get(_currentActivityIndex));
    _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().updateActivities(vect);
    //add event
    _dApplic.getDMediator().getCurrentDoc().getDM().getConditionsTest().addOrRemEventInTTs((Resource)_unities.get(_currentActivityIndex),1);
    return true;
  }

  /**
   * get a resource key
   * @param soresc
   * @param elt
   * @return the resource key or -1 if key does not found
   */
  private long getResourceKey(SetOfResources soresc, String elt){
    if (!elt.equalsIgnoreCase(_UNAVAILABLE)){
      return soresc.getResource(elt).getKey();
    }
    return -1;
  }

}// end class