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
    super(dataloaded,1);
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
    int line=0;
    String activityName="";
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
            "Wrong activity name at line: "+line+  "in the activity file:" +
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
          position = 2;
          break;
        case 4:// teachers' names
          if (token.length() == 0){
            new FatalProblemDlg(
            "Wrong teachers' names at line: "+line+  "in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ");
            System.exit(1);
          }
          position = 5;
          break;
        case 5:// empty line
          position = 6;
          break;
        case 6:// empty line
          position = 7;
          break;
        case 7://number of blocs
          DXToolsMethods.isIntValue(token.trim(),"number of blocs at line: "+line,"ActivityList");
          position = 8;
          break;
        case 8://duration of blocs
          stLine = new StringTokenizer(token);
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
    int line=0;
    int numberOfBloc=0;
    String activityName="";
    String instructorName="";
    Activity activity= new Activity();
    Group group= new Group();
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
          activity = new Activity();
          //Resource nature;
          if (Integer.parseInt(token.trim())==1)
            activity.setActivityVisibility(true);
          activity.addNature(activityName.substring(_COURSENAMELENGTH,_COURSENAMELENGTH+1));
          //nature = activity.getNature(activityName.substring(_COURSENAMELENGTH,_COURSENAMELENGTH+1));
          position = 3;
          break;
        case 3://number of activities
          position = 2;
          break;
        case 4:// teachers' names
          instructorName =token;
          position = 5;
          break;
        case 5:// empty line
          position = 6;
          break;
        case 6:// empty line
          position = 7;
          break;
        case 7://number of blocs
          group= new Group();
          numberOfBloc = Integer.parseInt(token.trim());
          for (int i=1; i<= numberOfBloc; i++)
            group.addBloc(Integer.toString(i));
          position = 8;
          break;
        case 8://duration of blocs
          stLine = new StringTokenizer(token);
          int i=1;
          Resource bloc;
          if (stLine.countTokens()== numberOfBloc)
           while(stLine.hasMoreElements()){
             bloc= group.getBloc(Integer.toString(i));
             i++;
           }

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


  private int _COURSENAMELENGTH=6;
  private int _ACTIVITYLENGTH=10;
}