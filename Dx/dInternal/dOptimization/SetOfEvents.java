package dInternal.dOptimization;

import java.awt.Component;
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInterface.dUtil.DXTools;
import dInternal.DModel;
import dInternal.dDataTxt.Activity;
import dInternal.dDataTxt.Assignment;
import dInternal.dDataTxt.Resource;
import dInternal.dDataTxt.Section;
import dInternal.dDataTxt.SetOfActivities;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dDataTxt.SetOfStudents;
import dInternal.dDataTxt.StudentAttach;
import dInternal.dDataTxt.Type;
import dInternal.dDataTxt.Unity;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;
import dInternal.dUtil.DXValue;


public class SetOfEvents extends SetOfResources{

  public Vector _soeListeners = new Vector(1);
  //protected boolean _isEventPlaced=false;
  private DModel _dm;
  //private String _UNAVAILABLE= "------";

  /***
   * Constructor
   */
  public SetOfEvents(DModel dm) {
    super(6);
    _dm= dm;
  }


  /**
   * Build setOfEvents from activities
   * @param cycle
   */
  public void build(SetOfActivities soa, SetOfResources soie){
    String unityKey;
    for (int i=0; i< soa.size(); i++){
      Resource activity= soa.getResourceAt(i);
      long instructorKey=-1, roomKey; //=-1;
      if(((Activity)activity.getAttach()).getActivityVisibility()){
        for(int j=0; j< ((Activity)activity.getAttach()).getSetOfTypes().size(); j++){
          Resource type = ((Activity)activity.getAttach()).getSetOfTypes().getResourceAt(j);
          for(int k=0; k< ((Type)type.getAttach()).getSetOfSections().size(); k++){
            Resource section = ((Type)type.getAttach()).getSetOfSections().getResourceAt(k);
            for(int l=0; l< ((Section)section.getAttach()).getSetOfUnities().size(); l++){
              Resource unity= ((Section)section.getAttach()).getSetOfUnities().getResourceAt(l);
              Assignment assignment = (Assignment)((Unity)unity.getAttach()).getAssignment(
                  _dm.getTTStructure().getCurrentCycleResource().getID()).getAttach();
              if(assignment!=null){
                unityKey = activity.getKey()+"."+ type.getKey()+"."+section.getKey()+"."+unity.getKey()+".";
                String unityID = activity.getID()+"."+ type.getID()+"."+section.getID()+"."+unity.getID()+".";
                if(DXToolsMethods.getToken(assignment.getPeriodKey(),".",0).equalsIgnoreCase("0")){
                  String perKeys= _dm.getTTStructure().getCurrentCycle().getPeriod(assignment.getDateAndTime());
                  Period per= _dm.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(perKeys);
                  if(per!=null)
                    assignment.setPeriodKey(perKeys);
                  else
                    assignment.setPeriodKey("1.1.1");
                }// end if(assignment.getPeriodKey()[0]==0)
                //System.out.println("event " +unityID+" InsName " +assignment.getInstructorName());
                String instructorNames [] = assignment.getInstructorNames();
                for (int m = 0 ; m < instructorNames.length; m++) {
                  int instructorIndex = _dm.getSetOfInstructors().getIndexOfResource(instructorNames[m]);
                  if(instructorIndex!=-1){
                    instructorKey = _dm.getSetOfInstructors().getResourceAt(instructorIndex).getKey();
                    assignment.addInstructorKeys(instructorKey);
                  }else{
                    DXValue error= new DXValue();
                    error.setStringValue("Erreur --> "+ unityID+": "+ instructorNames[m] +" Inexistant ");
                    soie.addResource(new Resource("2",error),0);
                  }
                }


                int roomIndex = _dm.getSetOfRooms().getIndexOfResource(assignment.getRoomName());
                if(roomIndex != -1){
                  roomKey = _dm.getSetOfRooms().getResourceAt(roomIndex).getKey();
                  //assignment.setRoomKey(roomKey);
                }else{
                  roomKey = -1;
                  DXValue error= new DXValue();
                  String str = assignment.getRoomName();
                  if (str.equals(DConst.NO_ROOM_INTERNAL))
                    str = DConst.NO_ROOM_EXTERNAL;
                  error.setStringValue("Erreur --> " + unityID + ": "+ str + " Inexistant ");
                  soie.addResource(new Resource("3", error), 0);
                }
                //int[] dayTime = assignment.getDateAndTime();

                EventAttach event = new EventAttach(unityKey, assignment.getSetInstructorKeys(), roomKey,
                    ((Unity)unity.getAttach()).getDuration(),assignment.getPeriodKey());
                event.setAssignState(((Unity)unity.getAttach()).isAssign());
                event.setPermanentState(((Unity)unity.getAttach()).isPermanent());
                //System.out.println("Unity Key: "+unityKey+ " - Period Key: "+((Cycle)cycle.getAttach()).getPeriod(dayTime));//debug
                this.addResource(new Resource(unityID, event),0);
                //System.out.println("event " +unityID+" InsName " +assignment.getInstructorName());
              }// end if(assignement!=null)
            }// end for(int l=0; l< ((Section)section.getAttach()).getSetOfUnities().size(); l++)
          }// end for(int k=0; k< ((Type)type.getAttach()).getSetOfSections().size(); k++)
        }//for(int j=0; j< activity.getSetOfTypes().size(); j++)
      }//end if(((Activity)activity.getAttach()).getActivityVisibility())
    }// end for (int i=0; i< soa.size(); i++)

  }

  /**
   * get the complet activity name of an event
   * @param eventKey
   * @param soa
   * @return
   */
  public String getEventID(String eventID, SetOfActivities soa){
    String id= eventID;
    StringTokenizer event1 = new StringTokenizer(eventID,DConst.TOKENSEPARATOR);
    if(event1.countTokens()>=4){
      Resource activity = soa.getResource(event1.nextToken());
      Resource type = ((Activity)activity.getAttach()).getSetOfTypes().getResource(event1.nextToken());
      Resource section = ((Type)type.getAttach()).getSetOfSections().getResource(event1.nextToken());
      Resource unity = ((Section)section.getAttach()).getSetOfUnities().getResource(event1.nextToken());
      id= activity.getID()+DConst.TOKENSEPARATOR+type.getID()+
          DConst.TOKENSEPARATOR+section.getID()+DConst.TOKENSEPARATOR+unity.getID();
    }
    return id;
  }

  /**
   *
   * @return
   */
  public int getNumberOfEventAssign(){
    int count=0 ;
    for (int i=0; i< this.size(); i++){
      if(((EventAttach)getResourceAt(i).getAttach()).isPlaceInAPeriod())
        count++;
    }// end for (int i=0; i< this.size(); i++)
    return count;
  }

  /**
   * update activities from events
   */
  public void updateActivities(SetOfActivities soa, Vector eventsToUpdate){
    EventAttach event;//= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    for (int i=0; i< eventsToUpdate.size(); i++){
      event=(EventAttach)((Resource)eventsToUpdate.get(i)).getAttach();
      long actKey= Long.parseLong(DXToolsMethods.getToken(event.getPrincipalRescKey(),".",0));
      long typeKey= Long.parseLong(DXToolsMethods.getToken(event.getPrincipalRescKey(),".",1));
      long sectKey= Long.parseLong(DXToolsMethods.getToken(event.getPrincipalRescKey(),".",2));
      long unitKey= Long.parseLong(DXToolsMethods.getToken(event.getPrincipalRescKey(),".",3));
      Unity unity= soa.getUnity(actKey,typeKey,sectKey,unitKey);
      Assignment assignment= (Assignment)unity.getSetOfAssignments().getResourceAt(
          _dm.getTTStructure().getCurrentCycleIndex()).getAttach();
      long keys [] = event.getInstructorKey();
      assignment.emptyInstructorNames();
      for (int j = 0 ; j < keys.length ; j++) {
        assignment.addInstructorName(getRescName(_dm.getSetOfInstructors(),keys[j]));
      }

      assignment.setRoom(getRescName(_dm.getSetOfRooms(),event.getRoomKey()));
     /* long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
      long seqKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",1));
      long perKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",2));*/
      //Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByKey(dayKey,seqKey,perKey);
      //assignment.setDateAndTime((int)dayKey,period.getBeginHour()[0],period.getBeginHour()[1]);
      assignment.setPeriodKey(event.getPeriodKey());
      unity.setAssign(event.getAssignState());
      unity.setPermanent(event.getPermanentState());
      unity.setDuration(event.getDuration());
    }// end for (int i=0; i< eventsToUpdate.size(); i++)

  }

  /**
   * get a resource key
   * @param soresc
   * @param elt
   * @return the resource key or -1 if key does not found
   */
  private String getRescName(SetOfResources soresc, long eltkey){
    if (eltkey!=-1){
      return soresc.getResource(eltkey).getID();
    }
    return DConst.NO_ROOM_INTERNAL;
  }


    /**
     * for two event in conflict
     * @param eventIDOne
     * @param eventIDTwo
     * @return
     */
  public String getInstructorConflictDescriptions(String eventIDOne, String eventIDTwo) {
    String res = "";
    long instKeyOne[] =((EventAttach)getResource(eventIDOne).getAttach()).getInstructorKey();
    long instKeyTwo[] =((EventAttach)getResource(eventIDTwo).getAttach()).getInstructorKey();
    for (int i=0; i< instKeyOne.length; i++){
      for (int j=0; j< instKeyTwo.length; j++){
        if(instKeyOne[i] == instKeyTwo[j]){
          String str= _dm.getSetOfInstructors().getResource(instKeyOne[i]).getID();
          res += DXToolsMethods.getToken(str,",",0)+" "+DXToolsMethods.getToken(str,",",1)+",";
        }// end if(instKeyOne[i] == instKeyTwo[j])
      }// end for (int j=0; j< instKeyOne.length; j++)
    }// end for (int i=0; i< instKeyOne.length; i++)
    return res;
  }

  /**
     * for instructor availibility conflict
     * @param eventIDOne
     * @param eventIDTwo
     * @return
     */
  public String getInstructorConflictDescriptions( DXValue confAt, String eventIDOne) {
    String res="";
      Vector insKeys= (Vector)(confAt).getObjectValue();
      for (int j=0; j< insKeys.size(); j++){
        String str= _dm.getSetOfInstructors().getResource(((Long)insKeys.get(j)).longValue()).getID();
          res += DXToolsMethods.getToken(str,",",0)+" "+DXToolsMethods.getToken(str,",",1)+",";
      }// end for (int j=0; j< insKeys.size(); j++)

    return res;
  }


    /**
     *
     * @param eventIDOne
     * @param eventIDTwo
     * @return
     */
    public String getStudentConflictDescriptions(String eventIDOne, String eventIDTwo) {
//System.out.println("Event 1: "+eventIDOne+"  Event2: "+eventIDTwo);
      Vector studentOne = ((Activity) _dm.getSetOfActivities().getResource(DXToolsMethods.getToken(eventIDOne,".",0)).getAttach()).getStudentRegistered();
      Vector studentTwo = ((Activity) _dm.getSetOfActivities().getResource(DXToolsMethods.getToken(eventIDTwo,".",0)).getAttach()).getStudentRegistered();
      Vector studentOneInSection = studentsInSection(studentOne,DXToolsMethods.getToken(eventIDOne,".",0)+
          DXToolsMethods.getToken(eventIDOne,".",1),
          DXToolsMethods.getToken(eventIDOne,".",2));
      Vector studentTwoInSection = studentsInSection(studentTwo,DXToolsMethods.getToken(eventIDTwo,".",0)+
          DXToolsMethods.getToken(eventIDTwo,".",1),
          DXToolsMethods.getToken(eventIDTwo,".",2));
      String res = "";
      for(int i=0; i<studentOneInSection.size(); i++) {
        if (studentTwoInSection.contains(studentOneInSection.get(i))){
          String id= _dm.getSetOfStudents().getResource(Long.parseLong(studentOneInSection.get(i).toString())).getID();
          String matricule= "00"+studentOneInSection.get(i).toString();
          res += matricule.substring(matricule.length()-SetOfStudents._ENDSTUDENTMATRICULE)+"-"+ id + ",";
        }
      }
      return res;
    }

    /**
     *
     * @param students
     * @param activityAndType
     * @param section
     * @return
     */
    public Vector studentsInSection(Vector students, String activityAndType, String section){
      Vector res = new Vector();
      for(int i = 0; i <students.size(); i++) {
        StudentAttach sa = (StudentAttach)_dm.getSetOfStudents().getResource(Long.parseLong((String)students.get(i))).getAttach();
        if( sa.isInGroup(activityAndType,DXTools.STIConvertGroupToInt(section)))
          res.add(students.get(i));
      }
      return res;
    }

    /**
     *
     * @param component
     */
    public void sendEvent(Component component) {
      SetOfEventsEvent event = new SetOfEventsEvent(this);
      for (int i=0; i< _soeListeners.size(); i++) {
        SetOfEventsListener soel = (SetOfEventsListener) _soeListeners.elementAt(i);
        soel.changeInSetOfEvents(event, component);
      }
    }

    /**
     *
     * @param dml
     */
    public synchronized void addSetOfEventsListener(SetOfEventsListener sorl) {
      if (_soeListeners.contains(sorl)){
        return;
      }
      _soeListeners.addElement(sorl);
      //System.out.println("addSetOfEvents Listener ...");//debug
    }


  }// end class