package dInternal.dDataTxt;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam, alexander
 * @version 1.0
 */
import java.awt.Component;
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInternal.dUtil.DXToolsMethods;
//import d

public class SetOfActivities extends SetOfResources{

  private Vector _SOAListeners = new Vector(1);
  /**activities in text format*/
  private byte[] _dataloaded;
  private String _error="";
  private int _line=1;
  private boolean _open;

  //private String NULLINFORMATION = "xxxxxx";
  private int _NUMBEROFCYCLE = 1;
  final static public int _COURSENAMELENGTH=6;
  private int _ACTIVITYLENGTH=11;
  /**
   * Constructor
   * */
  public SetOfActivities(byte[] dataloaded, boolean open) {
    super(0);
    _dataloaded = dataloaded;
    _open= open;
  }

  /**
   *
   * @param dataloaded
   */
  public void setDataToLoad(byte[]  dataloaded, boolean open){
    _dataloaded = dataloaded;
    _open= open;
  }


  /**
   * analyse activities data by a finished states machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  public boolean analyseTokens(int beginPosition){
  	/*_error="";
    if(!analyseSIGTokens(beginPosition)){// analyse STI data
      return false;
    }else if(_open){// else if(!analyseSIGTokens(beginPosition))
        return analyseDeltaTokens(beginPosition);// analyse Delta data
    }// end else if(!analyseSIGTokens(beginPosition))

    return true;*/
  	String token=DXToolsMethods.getToken(new String (_dataloaded), DConst.CR_LF ,0);
	//StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
    //token = st.nextToken();//.toString().trim();
    //token = token.trim();
    if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
    	return analyseTokens1_6(beginPosition);
    } 
    return analyseTokens1_5(beginPosition);
  }
  
  /**
	 * analyseTokens1_5 will analyse the tokens of files
	 *        without any header like Diamant1.6
	 * @param beginPosition indicates from where start to read in the
	 *        array
	 * 
	 * @return true <p>if no errors in _dataloaded </p>
	 *         false otherwise
	 */
	private boolean analyseTokens1_5(int beginPosition) {
		_error="";
	    if(!analyseSIGTokens(beginPosition)){// analyse STI data
	      return false;
	    }else if(_open){// else if(!analyseSIGTokens(beginPosition))
	        return analyseDeltaTokens1_5(beginPosition);// analyse Delta data
	    }// end else if(!analyseSIGTokens(beginPosition))

	    return true;
	}

	/**
	 * analyseTokens1_5 will analyse the tokens of files
	 *        without any header like Diamant1.6
	 * @param beginPosition indicates from where start to read in the
	 *        array
	 * 
	 * @return true <p>if no errors in _dataloaded </p>
	 *         false otherwise
	 */
	private boolean analyseTokens1_6(int beginPosition) {
		_error="";
	    if(!analyseSIGTokens(beginPosition)){// analyse STI data
	      return false;
	    }else if(_open){// else if(!analyseSIGTokens(beginPosition))
	        return analyseDeltaTokens1_6(beginPosition);// analyse Delta data
	    }// end else if(!analyseSIGTokens(beginPosition))
	    
	    return true;
	}
	
  /**
   * analyse Delta activities data by a finished states machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  private boolean analyseDeltaTokens1_5(int beginPosition){
    String token;
    //String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
    StringTokenizer stLine = null; //auxiliar StringTokenizer for reading subStrings in a line
    //int state=0;
    int position=beginPosition;
    _line=0;
    //String activityName="";
    String instructorName="";
    int numberOfBlocs=0;
    while (st.hasMoreElements()){
      token = st.nextToken();
      _line++;
      if(_error.length()!=0)
        return false;
      switch (position){
        case 0:// empty line
          position = 1;
          break;
        case 1:// activity name
          position = 2;
          break;
        case 2://activity visibility
          position = 3;
          break;
        case 3://number of activities
          position = 4;
          break;
        case 4:// teachers' names
          instructorName= token;
          position = 7;
          break;
        case 5:// empty line
          position = 6;
          break;
        case 6:// empty line
          position = 7;
          break;
        case 7://number of blocs
          numberOfBlocs=Integer.parseInt(token.trim());
          if(DXToolsMethods.countTokens(instructorName,";")!=numberOfBlocs){
            _error= DConst.ACTI_TEXT13  +
            _line + ", I was in SetOfActivies class and in analyseDeltaTokens method ";
            return false;
          }
          position = 8;
          break;
        case 8://duration of blocs

          position = 9;
          break;
        case 9://days and periods of blocs
          stLine = new StringTokenizer(token);
          if(numberOfBlocs!=stLine.countTokens()){
            _error= DConst.ACTI_TEXT5+_line+" ActivityList";
            return false;
          }
          while(stLine.hasMoreElements()){
            StringTokenizer stLine1;
            stLine1 = new StringTokenizer(stLine.nextToken(),".");
            while(stLine1.hasMoreElements())
              _error= DXToolsMethods.isIntValue(stLine1.nextToken(),
                  DConst.ACTI_TEXT8+_line,"ActivityList");
            if(_error.length()!=0)
              return false;
          }
          position = 10;
          break;
        case 10://fixed rooms

          position = 11;
          break;
        case 11://Preferred rooms

          position = 12;
          break;
        case 12://type of rooms

          position = 13;
          break;
        case 13://idem

          position = 14;
          break;
        case 14://pre-affected cours
        	_error = analyseTokenPreaffectedRoom(token,numberOfBlocs,1,_line);
          position = beginPosition;
          break;

      }// end switch (position)

    }// end while (st.hasMoreElements())

    return true;
  }
  /**
   * analyse Delta activities data by a finished states machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  private boolean analyseDeltaTokens1_6(int beginPosition){
    String token;
    //String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
    StringTokenizer stLine = null; //auxiliar StringTokenizer for reading subStrings in a line
    //int state=0;
    int position=beginPosition;
    _line=0;
    //String activityName="";
    String instructorName="";
    int numberOfBlocs=0;
    while (st.hasMoreElements()){
      token = st.nextToken();
      _line++;
      if(_error.length()!=0)
        return false;
      switch (position){
        case 0:// empty line
          position = 1;
          break;
        case 1:// activity name
        	analyseDelTaTokenName1_6(token, _line);
          position = 2;
          break;
        case 2://activity visibility
          position = 3;
          break;
        case 3://number of activities
          position = 4;
          break;
        case 4:// teachers' names
          instructorName= token;
          position = 7;
          break;
        case 5:// empty line
          position = 6;
          break;
        case 6:// empty line
          position = 7;
          break;
        case 7://number of blocs
          numberOfBlocs=Integer.parseInt(token.trim());
          if(DXToolsMethods.countTokens(instructorName,";")!=numberOfBlocs){
            _error= DConst.ACTI_TEXT13  +
            _line + ", I was in SetOfActivies class and in analyseDeltaTokens method ";
            return false;
          }
          position = 8;
          break;
        case 8://duration of blocs

          position = 9;
          break;
        case 9://days and periods of blocs
          stLine = new StringTokenizer(token);
          if(numberOfBlocs!=stLine.countTokens()){
            _error= DConst.ACTI_TEXT5+_line+" ActivityList";
            return false;
          }
          while(stLine.hasMoreElements()){
            StringTokenizer stLine1;
            stLine1 = new StringTokenizer(stLine.nextToken(),".");
            while(stLine1.hasMoreElements())
              _error= DXToolsMethods.isIntValue(stLine1.nextToken(),
                  DConst.ACTI_TEXT8+_line,"ActivityList");
            if(_error.length()!=0)
              return false;
          }
          position = 10;
          break;
        case 10://fixed rooms

          position = 11;
          break;
        case 11://Preferred rooms

          position = 12;
          break;
        case 12://type of rooms

          position = 13;
          break;
        case 13://idem

          position = 14;
          break;
        case 14://pre-affected cours
        	_error = analyseTokenPreaffectedRoom(token,numberOfBlocs,1,_line);
          position = beginPosition;
          break;

      }// end switch (position)

    }// end while (st.hasMoreElements())

    return true;
  }

  /**
   * 
   * @param str
   * @param numberOfUnitys
   * @param position
   * @param line
   * @return
   */
  private String analyseTokenPreaffectedRoom(String str,int numberOfUnitys, int position, int line) {
//  check permanent token
    String permanentToken= DXToolsMethods.getToken(str,";",position);
    StringTokenizer stLine = new StringTokenizer(permanentToken);
    if(numberOfUnitys!=stLine.countTokens()){
    	return DConst.ACTI_TEXT12+line+" ActivityList";
    }
    //check if the permanent value is belong 0 and 1
    while(stLine.hasMoreElements()){
      String sousString = stLine.nextToken();
      String error= DXToolsMethods.checkIfBelongsValues(sousString,"0 1",
          DConst.ACTI_TEXT12+line,"ActivityList");
      if(error.length()!=0)
        return error;
    }
    return "";
	}
  
  
  /**
   * 
   * @param str
   * @param line
   * @return
   */
  private String analyseDelTaTokenName1_6 (String str, int line) {
  	// activity name number of token
  	if (DXToolsMethods.countTokens(str," ") != DConst.NUMBER_OF_TOKEN_COURSE_LINE){ 
		_error = DConst.ACTI_TEXT3+ line;
	}
	// first token
  	String st = DXToolsMethods.getToken(str," ",0);
	if (isErrorEmpty()){
		if (st.length()!=DConst.SIZE_OF_COURSE_TOKEN)
			_error= DConst.ACTI_TEXT1+ line;
	}
	// 2nd token
	st = DXToolsMethods.getToken(str," ",1);
	if (isErrorEmpty()){
		if (st.length()!=DConst.SIZE_OF_GROUP_TOKEN)
			_error= DConst.ACTI_TEXT14+ line;
	}
	if (isErrorEmpty()){
		if (DXToolsMethods.isIntValue(st))
			_error= DConst.ACTI_TEXT14+ line;
	}
	//3rd token
	st = DXToolsMethods.getToken(str," ",2);
	if (isErrorEmpty()){
		if (st.length()!=DConst.ACT_SITE_LENGTH)
			_error= DConst.ACTI_TEXT15+ line;
	}
	// 4th token
	st = DXToolsMethods.getToken(str," ",3);
	if (isErrorEmpty()){
		if (st.length()!=DConst.ACT_CAPACITY_LENGTH)
			_error= DConst.ACTI_TEXT16+ line;
	}
	if (isErrorEmpty()){
		if (DXToolsMethods.isIntValue(st))
			_error= DConst.ACTI_TEXT16+ line;
	}

  	return "";
  }
  
  /**
   * analyse SIG activities data by a finished states machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  private boolean analyseSIGTokens(int beginPosition){
    String token;
    String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    StringTokenizer stLine = null; //auxiliar StringTokenizer for reading subStrings in a line
    //int state=0;
    int position=beginPosition;
    if(!_open)
      _line=1;
    else
      _line=0;
    //String activityName="";
    int numberOfUnities=0;
    while (st.hasMoreElements()){
      token = st.nextToken();
      _line++;
      if(_error.length()!=0)
        return false;
      switch (position){
        case 0:// empty line
          position = 1;
          break;
        case 1:// activity name
          //activityName=token.trim();
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
            _error= DConst.ACTI_TEXT4 + _line +  "in the activity file:" +
            "\n" + "I was in ActiviesList class and in analyseTokens method ";
            return false;
          }
          position = 7;
          if(!_open)
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
          numberOfUnities = Integer.parseInt(token.trim());
          position = 8;
          break;
        case 8://duration of blocs
          stLine = new StringTokenizer(token);
          if (numberOfUnities!= stLine.countTokens()){
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
         // int typeOfData= DXToolsMethods.countTokens(token,".");
          if(!_open){
            if((numberOfUnities*2)!=stLine.countTokens()){
              _error= DConst.ACTI_TEXT5+_line+" ActivityList";
              return false;
            }
            while(stLine.hasMoreElements()){// rgr A problem in tests allwhile is a problem in real life
              //StringTokenizer stLine1;
              _error= DXToolsMethods.isIntValue(stLine.nextToken(),
                  DConst.ACTI_TEXT8+_line,"ActivityList");
              if(_error.length()!=0)
                return false;
            }// end while(stLine.hasMoreElements())
          }// end if(!_open)
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
            _error= DXToolsMethods.checkIfBelongsValues(sousString,"0 1",
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
          if (numberOfUnities != stLine.countTokens()) {
            _error=DConst.ACTI_TEXT10+_line+  "in the activity file:" +
           "\n" + "I was in ActiviesList class and in analyseTokens method ";
            return false;
          }
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
        case 14://pre-affected course

          _error = analyseTokenPreaffectedRoom(token,numberOfUnities,0,_line);
          position = beginPosition;
          if(st.hasMoreElements())
          _line++;
          break;

      }// end switch (position)

    }// end while (st.hasMoreElements())

    return true;
  }

  /**
   * build activitiesList from activities data by a finite state machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  public void buildSetOfResources(int beginPosition){
    String token;
    //String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    StringTokenizer stLine = null; //auxiliar StringTokenizer for reading subStrings in a line
    //int state=0;
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
          ((Type)typeResource.getAttach()).addSection(DXToolsMethods.getToken(activityName," ",1),section);
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
            int typeOfData= DXToolsMethods.countTokens(token,".");
            if(typeOfData==1){
              String day= stLine.nextToken().trim();
              String period= stLine.nextToken().trim();
              int dayKey=Integer.parseInt(day);
              int [] time= DXToolsMethods.convertSTIPeriods(Integer.parseInt(period));
              cycleAss.setDateAndTime(dayKey, time[0],time[1]);
            }else{// else if(typeOfData==1)
              String period= stLine.nextToken().trim();
              cycleAss.setPeriodKey(period);
            }// end else if(typeOfData==1)
            //cycleAss.addInstructorName(DXToolsMethods.getToken(instructorName,";",counter-1));
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
          String inst=instructorName;
          if(!_open){
          	for(int i=1; i< stLine.countTokens(); i++)
          		instructorName+=";" +inst;
          }
          StringTokenizer instLine = new StringTokenizer(instructorName,";");
           while(stLine.hasMoreElements()){
             unityResource= section.getUnity(Integer.toString(counter));
             Unity bloc= (Unity)unityResource.getAttach();
             String room= stLine.nextToken().trim();
            
             if(instLine.hasMoreElements())
               inst= instLine.nextToken().trim();
             for (int i=1; i<= _NUMBEROFCYCLE; i++){
               ((Assignment)bloc.getAssignment(Integer.toString(i)
                   ).getAttach()).setRoom(room);
               ((Assignment)bloc.getAssignment(Integer.toString(i)
                   ).getAttach()).addInstructorName(inst);
             }
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
          StringTokenizer visiToken = new StringTokenizer(new String (token),";" );
          int nbTokens= visiToken.countTokens();
          stLine = new StringTokenizer(visiToken.nextToken());
          StringTokenizer assignLine= null;
          if(nbTokens==2)
            assignLine= new StringTokenizer(visiToken.nextToken());
          counter=1;
          while(stLine.hasMoreElements()){
            int fix= Integer.parseInt(stLine.nextToken().trim());
            unityResource= section.getUnity(Integer.toString(counter));
            ((Unity)unityResource.getAttach()).setPermanent(fix==1);
            if( (nbTokens==2) && (assignLine.hasMoreElements()) ){
              int fix1= Integer.parseInt(assignLine.nextToken().trim());
              ((Unity)unityResource.getAttach()).setAssign(fix1==1);
            }else
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
    for(int i=0; i< size(); i++)
      ((Activity)getResourceAt(i).getAttach()).getStudentRegistered().removeAllElements();
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
          actlist+= nature.getSetOfSections().getResourceAt(k).getID()+DConst.CR_LF;// write group and go to line
          if(activity.getActivityVisibility())
            actlist+=1+DConst.CR_LF;
          else
            actlist+=0+DConst.CR_LF;// write visibility of activity and go to line
          actlist+=1+DConst.CR_LF;// write number of activities and go to line
          Section section= (Section)nature.getSetOfSections().getResourceAt(k).getAttach();
          Unity bloc;
          String instName="",lineDuration="", lineTime="", lineRoomFixed="",
          lineRoomName="", lineRoomType="", LineActFixed="", LineActAssign="";
          /* duration, time of each bloc*/
          for(int l=0; l< section.getSetOfUnities().size(); l++){
            bloc= (Unity)section.getSetOfUnities().getResourceAt(l).getAttach();
            lineDuration += bloc.getDuration()/60+" ";//
            Assignment firstCycAss = (Assignment)bloc.getSetOfAssignments(
                ).getResourceAt(0).getAttach();
            // a loop to be implemented
            String [] a = firstCycAss.getInstructorNames();
            for (int m = 0; m < a.length ; m++) {
               instName += a[m]+" :"; // to save
            }
            instName += " ;" ; // to save
            /*lineTime+=Integer.toString(firstCycAss.getDateAndTime()[0])+" "+
                     DXToolsMethods.convertSTIPeriods (firstCycAss.getDateAndTime()[1],30)+" ";*/
            lineTime+= firstCycAss.getPeriodKey()+" ";
            lineRoomName+= firstCycAss.getRoomName()+" ";//
            if(firstCycAss.getRoomState())
              lineRoomFixed+= "1 ";
            else
              lineRoomFixed+= "0 ";
            SetOfResources pfunctionRoom= bloc.getPreferFunctionRoom();
            if(pfunctionRoom.size()>0)
              lineRoomType+= pfunctionRoom.getResourceAt(0).getID()+" ";
            else
              lineRoomType+= "0 ";

            if (bloc.isPermanent()){
              LineActFixed+= "1 ";
            }else{
              LineActFixed+= "0 ";
            }

            if(bloc.isAssign()){
              LineActAssign+="1 ";
            }else{
              LineActAssign+="0 ";
            }

          }// end for(int l=0; l< group.getUnityList().size(); l++)
          actlist+=instName+DConst.CR_LF+section.getSetOfUnities().size()+DConst.CR_LF+
                   lineDuration+DConst.CR_LF+lineTime+DConst.CR_LF+lineRoomFixed+DConst.CR_LF+
                   lineRoomName+DConst.CR_LF+lineRoomType+DConst.CR_LF;//write the number of blocs
          actlist+=activity.getIdemLine()+DConst.CR_LF+LineActFixed+";"+LineActAssign+DConst.CR_LF;

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
    /*Activity a = (Activity)getResource(actKey).getAttach();
    Type t = (Type)a.getSetOfTypes().getResource(typeKey).getAttach();
    Section s = (Section)t.getSetOfSections().getResource(secKey).getAttach();
    Unity u = (Unity)s.getSetOfUnities().getResource(unitKey).getAttach();*/
    Resource a = getResource(actKey);
    if(a!=null){
      Resource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeKey);
      if(t!=null){
        Resource s = ((Type)t.getAttach()).getSetOfSections().getResource(secKey);
        if(s!=null){
          Resource u= ((Section)s.getAttach()).getSetOfUnities().getResource(unitKey);
          if(u!=null)
            return (Unity)u.getAttach();
        }
      }
    }
    return null;
  }

  /**
   * Return the unity specified by the parameters
   * @param actKey the activity ID
   * @param typeKey the type ID
   * @param secKey the section ID
   * @param unitKey the unity ID
   * @return The unity wanted
   */

  public Unity getUnity(String actID, String typeID, String secID, String unitID){
    /*Activity a = (Activity)getResource(actKey).getAttach();
    Type t = (Type)a.getSetOfTypes().getResource(typeKey).getAttach();
    Section s = (Section)t.getSetOfSections().getResource(secKey).getAttach();
    Unity u = (Unity)s.getSetOfUnities().getResource(unitKey).getAttach();*/
    Resource a = getResource(actID);
    if(a!=null){
      Resource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeID);
      if(t!=null){
        Resource s = ((Type)t.getAttach()).getSetOfSections().getResource(secID);
        if(s!=null){
          Resource u= ((Section)s.getAttach()).getSetOfUnities().getResource(unitID);
          if(u!=null)
            return (Unity)u.getAttach();
        }
      }
    }
    return null;
  }


  /**
   *
   * @param actID
   * @param typeID
   * @param secID
   * @return
   */
  public Section getSection(String actID, String typeID, String secID){
    Resource a = getResource(actID);
    if(a!=null){
      Resource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeID);
      if(t!=null){
        Resource s = ((Type)t.getAttach()).getSetOfSections().getResource(secID);
        if(s!=null)
          return (Section)s.getAttach();
      }
    }
    return null;
  }

  /**
  *
  * @param actID
  * @param typeID
  * @return
  */
 public Type getType(String actID, String typeID){
   Resource a = getResource(actID);
   if(a!=null){
     Resource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeID);
     if(t!=null){
       return (Type)t.getAttach();
     }
   }
   return null;
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
    return a.getID()+"."+t.getID()+"."+s.getID()+"."+u.getID()+".";
  }

  /**
   *
   * @param vect
   * @return
   */
  public Vector getUnitiesNames(Vector vect){
    Vector result= new Vector();
    for (int i=0; i< vect.size(); i++){
      long actKey = Long.parseLong(DXToolsMethods.getToken(vect.get(i).toString(),".",0));
      long typeKey = Long.parseLong(DXToolsMethods.getToken(vect.get(i).toString(),".",1));
      long secKey = Long.parseLong(DXToolsMethods.getToken(vect.get(i).toString(),".",2));
      long unitKey = Long.parseLong(DXToolsMethods.getToken(vect.get(i).toString(),".",3));
      result.add(getUnityCompleteName(actKey, typeKey, secKey, unitKey));
     }// end for (int i=0; i< vect.size(); i++)
    return result;
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



  /**
   *
   * @param actID
   * @param typeID
   * @param secID
   * @param unitID
   * @param fieldIndex
   * @param fieldValue
   */
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
    //System.out.println("addSetOfActivities Listener ...");//debug
  }
  
  /**
	 * check if there is an error detected
	 * @return
	 */
	private boolean isErrorEmpty() {
		return _error.length()==0;
	}


}