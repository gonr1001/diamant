package dInternal;

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
import com.iLib.gDialog.FatalProblemDlg;
import dResources.DXToolsMethods;

public class ActivitiesList extends ResourceList{

  /**activities in text format*/
  private byte[] _dataloaded;
  /**
   * Constructor
   * */
  public ActivitiesList(byte[] dataloaded) {
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
    int line=1;
    String activityName="";
    int numberOfBlocs=0;
    while (st.hasMoreElements()){
      token = st.nextToken();
      line++;
      switch (position){
        case 0:// empty line
          position = 1;
          break;
        case 1:// activity name
          if (token.trim().length() != _ACTIVITYLENGTH){
            new FatalProblemDlg(
            "Wrong activity name at line: "+line+  " in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ");
            System.exit(1);
          }
          position = 2;
          break;
        case 2://activity visibility
          DXToolsMethods.checkIfBelongsValues(token,"0 1","format of activity visibility at line: "
                +line,"ActivityList");
          position = 3;
          break;
        case 3://number of activities
          DXToolsMethods.isIntValue(token.trim(),"number of activities at line: "+line,"ActivityList");
          position = 4;
          break;
        case 4:// teachers' names
          if (token.length() == 0){
            new FatalProblemDlg(
            "Wrong teachers' names at line: "+line+  "in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ");
            System.exit(1);
          }
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
          DXToolsMethods.isIntValue(token.trim(),"number of blocs at line: "+line,"ActivityList");
          numberOfBlocs = Integer.parseInt(token.trim());
          position = 8;
          break;
        case 8://duration of blocs
          stLine = new StringTokenizer(token);
          if (numberOfBlocs!= stLine.countTokens()){
            new FatalProblemDlg(
            "Wrong teachers' names at line: "+line+  "in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ");
            System.exit(1);
          }
          DXToolsMethods.checkIfLineIsEmpty(token, "line is empty at line: "+line,"ActivityList");
          while(stLine.hasMoreElements())
            DXToolsMethods.isIntValue(stLine.nextToken(),"duration of blocs at line: "+line,"ActivityList");
          position = 9;
          break;
        case 9://days and periods of blocs
          stLine = new StringTokenizer(token);
          DXToolsMethods.checkIfLineIsEmpty(token, "line is empty at line: "+line,"ActivityList");
          while(stLine.hasMoreElements())
            DXToolsMethods.isIntValue(stLine.nextToken(),"days and times of blocs at line: "+
                                      line,"ActivityList");
          position = 10;
          break;
        case 10://fixed rooms
          stLine = new StringTokenizer(token);
          DXToolsMethods.checkIfLineIsEmpty(token, "line is empty at line: "+line,"ActivityList");
          while(stLine.hasMoreElements()){
            sousString = stLine.nextToken();
            DXToolsMethods.checkIfBelongsValues(token,"0 1","format of fixed rooms at line: "
                +line,"ActivityList");
          }
          position = 11;
          break;
        case 11://Preferred rooms
          stLine = new StringTokenizer(token);
          DXToolsMethods.checkIfLineIsEmpty(token, "line is empty at line: "+line,"ActivityList");
          while(stLine.hasMoreElements())
          if (stLine.nextToken().length() == 0){
            new FatalProblemDlg(
            "Wrong name of preferred rooms at line: "+line+  "in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ");
          System.exit(1);
          }
          position = 12;
          break;
        case 12://type of rooms
          stLine = new StringTokenizer(token);
          DXToolsMethods.checkIfLineIsEmpty(token, "line is empty at line: "+line,"ActivityList");
          while(stLine.hasMoreElements())
            DXToolsMethods.isIntValue(stLine.nextToken(),"type of rooms: "+line,"ActivityList");
          position = 13;
          break;
        case 13://idem
          stLine = new StringTokenizer(token);
          DXToolsMethods.checkIfLineIsEmpty(token, "line is empty at line: "+line,"ActivityList");
          while(stLine.hasMoreElements())
            DXToolsMethods.isIntValue(stLine.nextToken(),"type of rooms: "+line,"ActivityList");
          position = 14;
          break;
        case 14://pre-affected rooms
          stLine = new StringTokenizer(token);
          DXToolsMethods.checkIfLineIsEmpty(token, "line is empty at line: "+line,"ActivityList");
          while(stLine.hasMoreElements()){
            sousString = stLine.nextToken();
            DXToolsMethods.checkIfBelongsValues(token,"0 1","format of pre-affected rooms at line: "+
                line,"ActivityList");
          }
          position = beginPosition;
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
  public void buildActivitiesList(int beginPosition){
    String token;
    String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    StringTokenizer stLine = null; //auxiliar StringTokenizer for reading subStrings in a line
    int state=0;
    int position=beginPosition;
    int line=1;
    int numberOfBloc=0;
    int counter=0;
    String activityName="";
    String instructorName="";
    Activity activity= new Activity();
    Group group= new Group();
    Resource blocResource, natureResource, activityResource=null;
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
          activity = (Activity)activityResource.getObject();
          //Resource nature;
          if (Integer.parseInt(token.trim())==1)
            activity.setActivityVisibility(true);
          activity.addNature(activityName.substring(_COURSENAMELENGTH,_COURSENAMELENGTH+1));
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
          natureResource= activity.getNature(activityName.substring(_COURSENAMELENGTH,_COURSENAMELENGTH+1));
          group= new Group();
          numberOfBloc = Integer.parseInt(token.trim());
          for (int i=1; i<= numberOfBloc; i++)
            group.addBloc(Integer.toString(i));
          ((Nature)natureResource.getObject()).addGroup(activityName.substring(_ACTIVITYLENGTH-1),group);
          position = 8;
          break;
        case 8://duration of blocs
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             blocResource= group.getBloc(Integer.toString(counter));
             Bloc bloc= (Bloc)blocResource.getObject();
             bloc.setDuration(Integer.parseInt(stLine.nextToken().trim())*60);
             blocResource.setObject(bloc);
             group.setBloc(blocResource);
             counter++;
           }// end while(stLine.hasMoreElements())
          position = 9;
          break;
        case 9://days and periods of blocs
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             blocResource= group.getBloc(Integer.toString(counter));
             Bloc bloc= (Bloc)blocResource.getObject();
             CycleAssignment cycleAss = new CycleAssignment();
             int day=Integer.parseInt(stLine.nextToken().trim());
             int [] time= DXToolsMethods.convertSTIPeriods(Integer.parseInt(stLine.nextToken().trim()));
             cycleAss.setDateAndTime(day, time[0],time[1]);
             cycleAss.setInstructor(instructorName);
             for (int i=1; i<= _NUMBEROFCYCLE; i++)
               bloc.addCycleAssignment(new Resource(Integer.toString(i),cycleAss));
              counter++;
           }// end while(stLine.hasMoreElements())
          position = 10;
          break;
        case 10://fixed rooms
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             blocResource= group.getBloc(Integer.toString(counter));
             int fixed= Integer.parseInt(stLine.nextToken().trim());
             Bloc bloc= (Bloc)blocResource.getObject();
             for (int i=1; i<= _NUMBEROFCYCLE; i++)
               ((CycleAssignment)bloc.getCycleAssignment(Integer.toString(i)
                   ).getObject()).setRoomState(fixed==1);
              counter++;
           }// end while(stLine.hasMoreElements())
          position = 11;
          break;
        case 11://Preferred rooms
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             blocResource= group.getBloc(Integer.toString(counter));
             Bloc bloc= (Bloc)blocResource.getObject();
             String room= stLine.nextToken().trim();
             for (int i=1; i<= _NUMBEROFCYCLE; i++)
               ((CycleAssignment)bloc.getCycleAssignment(Integer.toString(i)
                   ).getObject()).setRoom(room);
              counter++;
           }// end while(stLine.hasMoreElements())
          position = 12;
          break;
        case 12://type of rooms
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             blocResource= group.getBloc(Integer.toString(counter));
             Bloc bloc= (Bloc)blocResource.getObject();
             String roomType= stLine.nextToken().trim();
             bloc.addPreferFunctionRoom(roomType);
             counter++;
           }// end while(stLine.hasMoreElements())
           position = 13;
          break;
        case 13://idem
          activity._idemLine=token;
          position = 14;
          break;
        case 14://activity is fixed or not
          stLine = new StringTokenizer(token);
          counter=1;
           while(stLine.hasMoreElements()){
             int fix= Integer.parseInt(stLine.nextToken().trim());
             blocResource= group.getBloc(Integer.toString(counter));
             ((Bloc)blocResource.getObject()).setFixed(fix==1);
             counter++;
           }// end while(stLine.hasMoreElements())
          position = beginPosition;
          this.addResource(activityResource,1);
          break;

      }// end switch (position)

    }// end while (st.hasMoreElements())

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
   * add an activity object in the list
   * @param Resource the the activity resource
   * @return boolean result of the operation
   * */
  public boolean addActivity(Resource activity){
      return addResource(activity,1);
  }

  /**
   * get an activity object from de the list
   * @param String the courseName of the activity
   * @return Resource the activity Resource
   * */
  public Resource getActivity(String courseName){
    if(courseName.length()>=6){
       return getResource(courseName.substring(0,_COURSENAMELENGTH));
    }
    return null;
  }

  /**
   * get an activity object from de the list
   * @param long the key of the activity
   * @return Resource the activity Resource
   * */
  public Resource getActivity(long courseKey){
      return getResource(courseKey);
  }

  /**
   * set an activity Resource object in the list
   * @param Resource the activity resource
   * @return boolean result of the operation
   * */
  public boolean setActivity(Resource activity){
    return setResource(activity);
  }

  /**
   * remove an activity Resource from de list
   * @param long the key of the activity
   * @return boolean result of the operation
   * */
  public boolean removeActivity(long activityKey){

    return removeResource(activityKey);
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

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toString(){
    String actlist="";// write
    for (int i=0; i<size(); i++){
      Activity activity = (Activity)getResourceAt(i).getObject();
      for(int j=0; j< activity.getNaturesList().size(); j++){
        Nature nature = (Nature)(activity.getNaturesList().getResourceAt(j)).getObject();
        for (int k=0; k< nature.getGroupList().size(); k++){
          actlist+= getResourceAt(i).getID();// write activity name
          actlist+= activity.getNaturesList().getResourceAt(j).getID()+"  ";// write nature and 2 space
          actlist+= nature.getGroupList().getResourceAt(k).getID()+CR_LF;// write group and go to line
          if(activity.getActivityVisibility())
            actlist+=1+CR_LF;
          else
            actlist+=0+CR_LF;// write visibility of activity and go to line
          actlist+=1+CR_LF;// write number of activities and go to line
          Group group= (Group)nature.getGroupList().getResourceAt(k).getObject();
          Bloc bloc;
          String instName="",lineDuration="", lineTime="", lineRoomFixed="",
          lineRoomName="", lineRoomType="", LineActFixed="";
          /* duration, time of each bloc*/
          for(int l=0; l< group.getBlocList().size(); l++){
            bloc= (Bloc)group.getBlocList().getResourceAt(l).getObject();
            lineDuration += bloc.getDuration()/60+" ";//
            CycleAssignment firstCycAss = (CycleAssignment)bloc.getCycleAssignmentList(
                ).getResourceAt(0).getObject();
            instName = firstCycAss.getInstructor();//
            lineTime+=firstCycAss.getDateAndTime()[0]+" "+
                     DXToolsMethods.convertSTIPeriods (firstCycAss.getDateAndTime()[1],30)+" ";//
            lineRoomName+= firstCycAss.getRoom()+" ";//
            if(firstCycAss.getRoomState())
              lineRoomFixed+= "1 ";
            else
              lineRoomFixed+= "0 ";
            lineRoomType+= bloc.getPreferFunctionRoom().getResourceAt(0).getID();
            if (bloc.getFixed())
              LineActFixed+= "1 ";
            else
              LineActFixed+= "0 ";
          }// end for(int l=0; l< group.getBlocList().size(); l++)
          actlist+=instName+CR_LF+group.getBlocList().size()+CR_LF+
                   lineDuration+CR_LF+lineTime+CR_LF+lineRoomFixed+CR_LF+
                   lineRoomName+CR_LF+lineRoomType+CR_LF;//write the number of blocs
          actlist+=activity._idemLine+CR_LF+LineActFixed+CR_LF;

        }// for (int k=0; k< nature.size(); k++)
      }// end for(int j=0; j< activity.size(); j++)

    }// end for (int i=0; i<getResourceList().size(); i++)
    return actlist;
  }

  private int _NUMBEROFCYCLE = 1;
  private int _COURSENAMELENGTH=6;
  private int _ACTIVITYLENGTH=10;
}