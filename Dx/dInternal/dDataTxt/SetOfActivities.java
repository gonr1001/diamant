package dInternal.dData;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam, alexander
 * @version 1.0
 */
import java.util.StringTokenizer;
import java.util.Vector;
import java.awt.Component;
import com.iLib.gDialog.FatalProblemDlg;
import dInternal.dUtil.DXToolsMethods;
import dResources.DConst;

public class SetOfActivities extends SetOfResources{

  private Vector _SOAListeners = new Vector(1);
  /**activities in text format*/
  private byte[] _dataloaded;
  private String _error="";
  private int _line=1;
  /**
   * Constructor
   * */
  public SetOfActivities(byte[] dataloaded) {
    super(0);
    _dataloaded = dataloaded;
  }

  /**
   * analyse activities datas by a finished states machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  public boolean analyseTokens(int beginPosition){
    String token;
    String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    StringTokenizer stLine = null; //auxiliar StringTokenizer for reading subStrings in a line
    int state=0;
    int position=beginPosition;
    _line=1;
    String activityName="";
    int numberOfUnitys=0;
    while (st.hasMoreElements()){
      token = st.nextToken();
      _line++;
      switch (position){
        case 0:// empty line
          position = 1;
          break;
        case 1:// activity name
          activityName=token.trim();
          if (token.trim().length() != _ACTIVITYLENGTH){
            _error= DConst.ACTI_TEXT1+_line+  " in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ";
            return false;
          }
          position = 2;
          break;
        case 2://activity visibility
          _error= DXToolsMethods.checkIfBelongsValues(token,"0 1",DConst.ACTI_TEXT2
                +_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          _error= DXToolsMethods.isIntValue(token.trim(),DConst.ACTI_TEXT2+_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          position = 3;
          break;
        case 3://number of activities
          _error= DXToolsMethods.isIntValue(token.trim(),DConst.ACTI_TEXT3+_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          position = 4;
          break;
        case 4:// teachers' names
          if (token.length() == 0){
            _error= DConst.ACTI_TEXT4+_line+  "in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ";
            return false;
          }
          position = 7;
          _line+=2;
          break;
        case 5:// empty line
          position = 6;
          break;
        case 6:// empty line
          position = 7;
          break;
        case 7://number of blocs
          _error= DXToolsMethods.isIntValue(token.trim(),
              DConst.ACTI_TEXT5+_line," ActivityList");
          if(_error.length()!=0)
            return false;
          numberOfUnitys = Integer.parseInt(token.trim());
          position = 8;
          break;
        case 8://duration of blocs
          stLine = new StringTokenizer(token);
          if (numberOfUnitys!= stLine.countTokens()){
            _error= DConst.ACTI_TEXT5+_line+  " in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ";
            return false;
          }
          _error= DXToolsMethods.checkIfLineIsEmpty(token,
              DConst.ACTI_TEXT6+_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          while(stLine.hasMoreElements()){
            _error= DXToolsMethods.isIntValue(stLine.nextToken(),
                DConst.ACTI_TEXT7+_line,"ActivityList");
            if(_error.length()!=0)
            return false;
          }
          position = 9;
          break;
        case 9://days and periods of blocs
          stLine = new StringTokenizer(token);
          _error= DXToolsMethods.checkIfLineIsEmpty(token,
              DConst.ACTI_TEXT6+_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          if(((numberOfUnitys*2)-(stLine.countTokens()))!=0){
            _error= DConst.ACTI_TEXT5+_line+"ActivityList";
            return false;
          }
          while(stLine.hasMoreElements()){
            _error= DXToolsMethods.isIntValue(stLine.nextToken(),
                DConst.ACTI_TEXT8+_line,"ActivityList");
            if(_error.length()!=0)
            return false;
          }
          position = 10;
          break;
        case 10://fixed rooms
          stLine = new StringTokenizer(token);
          _error= DXToolsMethods.checkIfLineIsEmpty(token,
              DConst.ACTI_TEXT6+_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          while(stLine.hasMoreElements()){
            sousString = stLine.nextToken();
            _error= DXToolsMethods.checkIfBelongsValues(token,"0 1",
                DConst.ACTI_TEXT9+_line,"ActivityList");
            if(_error.length()!=0)
            return false;
          }
          position = 11;
          break;
        case 11://Preferred rooms
          stLine = new StringTokenizer(token);
          _error= DXToolsMethods.checkIfLineIsEmpty(token,
              DConst.ACTI_TEXT6+_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          if (numberOfUnitys != stLine.countTokens()) {
            _error=DConst.ACTI_TEXT10+_line+  "in the activity file:" +
           "\n" + "I was in ActiviesList class and in analyseTokens method ";
            return false;
          }
          /*while(stLine.hasMoreElements())
          if (stLine.nextToken().length() == 0){
            _error=DConst.ACTI_TEXT10+line+  "in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ";
            return false;
          }*/
          position = 12;
          break;
        case 12://type of rooms
          stLine = new StringTokenizer(token);
          _error= DXToolsMethods.checkIfLineIsEmpty(token,
              DConst.ACTI_TEXT6+_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          while(stLine.hasMoreElements()){
            _error= DXToolsMethods.isIntValue(stLine.nextToken(),
                DConst.ACTI_TEXT11+_line,"ActivityList");
            if(_error.length()!=0)
            return false;
          }
          position = 13;
          break;
        case 13://idem
          stLine = new StringTokenizer(token);
          _error= DXToolsMethods.checkIfLineIsEmpty(token,
              DConst.ACTI_TEXT6+_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          while(stLine.hasMoreElements()){
            _error= DXToolsMethods.isIntValue(stLine.nextToken(),
                DConst.ACTI_TEXT11+_line,"ActivityList");
            if(_error.length()!=0)
              return false;
          }
          position = 14;
          break;
        case 14://pre-affected rooms
          stLine = new StringTokenizer(token);
          _error= DXToolsMethods.checkIfLineIsEmpty(token,
              DConst.ACTI_TEXT6+_line,"ActivityList");
          if(_error.length()!=0)
            return false;
          while(stLine.hasMoreElements()){
            sousString = stLine.nextToken();
            _error= DXToolsMethods.checkIfBelongsValues(token,"0 1",
                DConst.ACTI_TEXT12+_line,"ActivityList");
            if(_error.length()!=0)
            return false;
          }
          position = beginPosition;
          if(st.hasMoreElements())
            _line++;
          break;

      }// end switch (position)

    }// end while (st.hasMoreElements())

    return true;
  }

  /**
   * build activitiesList from activities datas by a finished states machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  public void buildSetOfActivities(int beginPosition){
    String token;
    String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    StringTokenizer stLine = null; //auxiliar StringTokenizer for reading subStrings in a line
    int state=0;
    int position=beginPosition;
    int line=1;
    int numberOfUnity=0;
    int counter=0;
    String activityName="";
    String instructorName="";
    Activity activity= new Activity();
    Section section= new Section();
    Resource unityResource, typeResource, activityResource=null;
    while (st.hasMoreElements()){
      token = st.nextToken();
      line++;
      switch (position){
        case 0:// empty line
          position = 1;
          break;
        case 1:// activity name
          activityName= token;
          position = 2;
          break;
        case 2://activity visibility
          activityResource = this.getResource(activityName.substring(0,_COURSENAMELENGTH));
          if(activityResource== null)
            activityResource = new Resource(activityName.substring(0,_COURSENAMELENGTH),new Activity());
          activity = (Activity)activityResource.getAttach();
          //Resource nature;
          if (Integer.parseInt(token.trim())==1)
            activity.setActivityVisibility(true);
          else
            activity.setActivityVisibility(false);
          activity.addType(activityName.substring(_COURSENAMELENGTH,_COURSENAMELENGTH+1));
          //nature = activity.getNature(activityName.substring(_COURSENAMELENGTH,_COURSENAMELENGTH+1));
          position = 3;
          break;
        case 3://number of activities
          position = 4;
          break;
        case 4:// teachers' names
          instructorName =token;
          position = 7;
          line+=2;
          break;
        case 5:// empty line
          position = 6;
          break;
        case 6:// empty line
          position = 7;
          break;
        case 7://number of blocs
          typeResource= activity.getType(activityName.substring(_COURSENAMELENGTH,_COURSENAMELENGTH+1));
          section= new Section();
          numberOfUnity = Integer.parseInt(token.trim());
          for (int i=1; i<= numberOfUnity; i++)
            section.addUnity(Integer.toString(i));
          ((Type)typeResource.getAttach()).addSection(activityName.substring(_ACTIVITYLENGTH-1),section);
          position = 8;
          break;
        case 8://duration of blocs
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             unityResource= section.getUnity(Integer.toString(counter));
             Unity unity= (Unity)unityResource.getAttach();
             unity.setDuration(Integer.parseInt(stLine.nextToken().trim())*60);
             unityResource.setAttach(unity);
             section.setUnity(unityResource);
             counter++;
           }// end while(stLine.hasMoreElements())
          position = 9;
          break;
        case 9://days and periods of blocs
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             unityResource= section.getUnity(Integer.toString(counter));
             Unity bloc= (Unity)unityResource.getAttach();
             Assignment cycleAss = new Assignment();
             int dayIndex=Integer.parseInt(stLine.nextToken().trim())-1;
             int [] time= DXToolsMethods.convertSTIPeriods(Integer.parseInt(stLine.nextToken().trim()));
             cycleAss.setDateAndTime(dayIndex, time[0],time[1]);
             cycleAss.setInstructor(instructorName);
             for (int i=1; i<= _NUMBEROFCYCLE; i++)
               bloc.addAssignment(new Resource(Integer.toString(i),cycleAss));
              counter++;
           }// end while(stLine.hasMoreElements())
          position = 10;
          break;
        case 10://fixed rooms
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             unityResource= section.getUnity(Integer.toString(counter));
             int fixed= Integer.parseInt(stLine.nextToken().trim());
             Unity bloc= (Unity)unityResource.getAttach();
             for (int i=1; i<= _NUMBEROFCYCLE; i++)
               ((Assignment)bloc.getAssignment(Integer.toString(i)
                   ).getAttach()).setRoomState(fixed==1);
              counter++;
           }// end while(stLine.hasMoreElements())
          position = 11;
          break;
        case 11://Preferred rooms
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             unityResource= section.getUnity(Integer.toString(counter));
             Unity bloc= (Unity)unityResource.getAttach();
             String room= stLine.nextToken().trim();
             for (int i=1; i<= _NUMBEROFCYCLE; i++)
               ((Assignment)bloc.getAssignment(Integer.toString(i)
                   ).getAttach()).setRoom(room);
              counter++;
           }// end while(stLine.hasMoreElements())
          position = 12;
          break;
        case 12://type of rooms
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             unityResource= section.getUnity(Integer.toString(counter));
             Unity bloc= (Unity)unityResource.getAttach();
             String roomType= stLine.nextToken().trim();
             bloc.addPreferFunctionRoom(roomType);
             counter++;
           }// end while(stLine.hasMoreElements())
           position = 13;
          break;
        case 13://idem
          activity.setIdemLine(token.trim());
          position = 14;

          break;
        case 14://activity is fixed or not
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             int fix= Integer.parseInt(stLine.nextToken().trim());
             unityResource= section.getUnity(Integer.toString(counter));
             ((Unity)unityResource.getAttach()).setPermanent(fix==1);
             ((Unity)unityResource.getAttach()).setAssign(fix==1);
             counter++;
           }// end while(stLine.hasMoreElements())
          position = beginPosition;
          this.addResource(activityResource,1);
          break;

      }// end switch (position)

    }// end while (st.hasMoreElements())

  }

  /**
   * build the students list registered in each activity
   * @param sos
   */
  public void buildStudentRegisteredList(SetOfStudents sos){
    for (int i=0; i< sos.size(); i++){
      SetOfResources courses= ((StudentAttach)sos.getResourceAt(i).getAttach()).getCoursesList();
      for (int j=0; j< courses.size(); j++){
        Resource activity= this.getResource(courses.getResourceAt(j).getID().
        substring(0,_COURSENAMELENGTH));
        //Activity activity= (Activity) this.getResource(courses.getResourceAt(i).getID()).getAttach();
        if (activity!=null)
          ((Activity)activity.getAttach()).addStudentRegistered(sos.getResourceAt(i).getKey());
      }// end for (int j=0; j< courses.size(); j++)
    }// end for (int i=0; i< sos.size(); i++)
  }

  /**
   * add an activity object in the list
   * @param String the courseName of the activity
   * @return boolean result of the operation
   * */
  public boolean addActivity(String courseName){
    if(courseName.length()>=6){
      Activity activity = new Activity();
      Resource activ= new Resource(courseName.substring(0,_COURSENAMELENGTH), activity);
      return addResource(activ,1);
    }
    return false;
  }



  /**
   * remove an activity Resource from de list
   * @param String the courseName of the activity
   * @return boolean result of the operation
   * */
  public boolean removeActivity(String courseName){
    if(courseName.length()>=6){
       return removeResource(courseName.substring(0,_COURSENAMELENGTH));
    }
   return false;
  }


  public String getError() {
   return _error;
  }

  /***
   * */
  public int getLine(){
    return _line;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toWrite(){
    String actlist="";// write
    for (int i=0; i<size(); i++){
      Activity activity = (Activity)getResourceAt(i).getAttach();
      for(int j=0; j< activity.getSetOfTypes().size(); j++){
        Type nature = (Type)(activity.getSetOfTypes().getResourceAt(j)).getAttach();
        for (int k=0; k< nature.getSetOfSections().size(); k++){
          actlist+= getResourceAt(i).getID();// write activity name
          actlist+= activity.getSetOfTypes().getResourceAt(j).getID()+"  ";// write nature and 2 space
          actlist+= nature.getSetOfSections().getResourceAt(k).getID()+CR_LF;// write group and go to line
          if(activity.getActivityVisibility())
            actlist+=1+CR_LF;
          else
            actlist+=0+CR_LF;// write visibility of activity and go to line
          actlist+=1+CR_LF;// write number of activities and go to line
          Section section= (Section)nature.getSetOfSections().getResourceAt(k).getAttach();
          Unity bloc;
          String instName="",lineDuration="", lineTime="", lineRoomFixed="",
          lineRoomName="", lineRoomType="", LineActFixed="";
          /* duration, time of each bloc*/
          for(int l=0; l< section.getSetOfUnities().size(); l++){
            bloc= (Unity)section.getSetOfUnities().getResourceAt(l).getAttach();
            lineDuration += bloc.getDuration()/60+" ";//
            Assignment firstCycAss = (Assignment)bloc.getSetOfAssignments(
                ).getResourceAt(0).getAttach();
            instName = firstCycAss.getInstructorName();
            lineTime+=Integer.toString(1+ firstCycAss.getDateAndTime()[0])+" "+
                     DXToolsMethods.convertSTIPeriods (firstCycAss.getDateAndTime()[1],30)+" ";//
            lineRoomName+= firstCycAss.getRoomName()+" ";//
            if(firstCycAss.getRoomState())
              lineRoomFixed+= "1 ";
            else
              lineRoomFixed+= "0 ";
            lineRoomType+= bloc.getPreferFunctionRoom().getResourceAt(0).getID()+" ";
            if (bloc.isAssign())
              LineActFixed+= "1 ";
            else
              LineActFixed+= "0 ";
          }// end for(int l=0; l< group.getUnityList().size(); l++)
          actlist+=instName+CR_LF+section.getSetOfUnities().size()+CR_LF+
                   lineDuration+CR_LF+lineTime+CR_LF+lineRoomFixed+CR_LF+
                   lineRoomName+CR_LF+lineRoomType+CR_LF;//write the number of blocs
          actlist+=activity.getIdemLine()+CR_LF+LineActFixed+CR_LF;

        }// for (int k=0; k< nature.size(); k++)
      }// end for(int j=0; j< activity.size(); j++)

    }// end for (int i=0; i<getSetOfResources().size(); i++)
    return actlist;
  }

  /**
   * Return the unity specified by the parameters
   * @param actKey the activity key
   * @param typeKey the type key
   * @param secKey the section key
   * @param unitKey the unity key
   * @return The unity wanted
   */

  public Unity getUnity(long actKey, long typeKey, long secKey, long unitKey){
    Activity a = (Activity)getResource(actKey).getAttach();
    Type t = (Type)a.getSetOfTypes().getResource(typeKey).getAttach();
    Section s = (Section)t.getSetOfSections().getResource(secKey).getAttach();
    Unity u = (Unity)s.getSetOfUnities().getResource(unitKey).getAttach();
    return u;
  }

  /**
   * Return the name of the unity specified by the parameters
   * @param actKey the activity key
   * @param typeKey the type key
   * @param secKey the section key
   * @param unitKey the unity key
   * @return The name of the unity wanted
   */
  public String getUnityCompleteName(long actKey, long typeKey, long secKey, long unitKey){
    Resource a = getResource(actKey);
    Resource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeKey);
    Resource s = ((Type)t.getAttach()).getSetOfSections().getResource(secKey);
    Resource u = ((Section)s.getAttach()).getSetOfUnities().getResource(unitKey);
    return a.getID()+t.getID()+" "+s.getID()+u.getID();
  }

  /**
   * Sets a field belonging a Unity
   * @param actKey the activity key
   * @param typeKey the type key
   * @param secKey the section key
   * @param unitKey the unity key
   * @param fieldIndex The index identifaying the field
   * @param fieldValue The value to be setted in the field
   */
  public void setUnityField(long actKey, long typeKey, long secKey, long unitKey, int fieldIndex, String fieldValue){
    Resource a = getResource(actKey);
    Resource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeKey);
    Resource s = ((Type)t.getAttach()).getSetOfSections().getResource(secKey);
    Resource u = ((Section)s.getAttach()).getSetOfUnities().getResource(unitKey);
    u.getAttach().setField(fieldIndex, fieldValue);
  }

  public void setUnityField(String actID, String typeID, String secID, String unitID, int fieldIndex, String fieldValue){
    Resource a = getResource(actID);
    Resource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeID);
    Resource s = ((Type)t.getAttach()).getSetOfSections().getResource(secID);
    Resource u = ((Section)s.getAttach()).getSetOfUnities().getResource(unitID);
    u.getAttach().setField(fieldIndex, fieldValue);
  }

  /**
   *
   * @param component
   */
 public void sendEvent(Component component) {
   SetOfActivitiesEvent event = new SetOfActivitiesEvent(this);
   for (int i=0; i< _SOAListeners.size(); i++) {
     SetOfActivitiesListener soal = (SetOfActivitiesListener) _SOAListeners.elementAt(i);
     soal.changeInSetOfActivities(event, component);
     //System.out.println("SetOfActivities listener started: "+i);//debug
   }
  }

  /**
   *
   * @param dml
   */
  public synchronized void addSetOfActivitiesListener(SetOfActivitiesListener soal) {
    //System.out.println("SetOfActivities listener addeed: ");//debug
    if (_SOAListeners.contains(soal)){
      return;
    }
    _SOAListeners.addElement(soal);
  }


  private int _NUMBEROFCYCLE = 1;
  final static public int _COURSENAMELENGTH=6;
  private int _ACTIVITYLENGTH=10;
}