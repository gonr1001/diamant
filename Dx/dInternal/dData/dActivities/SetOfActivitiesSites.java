/*
 * Created on 26 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dInternal.dData.dActivities;


//import java.awt.Component;
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
//import dInternal.dData.dStudents.SetOfStuCourses;
//import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dUtil.DXToolsMethods;
//import d

public class SetOfActivitiesSites extends DSetOfResources{

  //private Vector _SOAListeners = new Vector(1);
  /**activities in text format*/
  //private byte[] _dataloaded;
  private String _error="";
  private int _line=1;
  private boolean _open;

  //private String NULLINFORMATION = "xxxxxx";
  //private int _NUMBEROFCYCLE = 1;
  final static public int _COURSENAMELENGTH=6;
  private int _ACTIVITYLENGTH=11;
  /**
   * Constructor
   * */
  public SetOfActivitiesSites(boolean open) {
    super();
    //_dataloaded = dataloaded;
    _open= open;
  }

  /**
   *
   * @param dataloaded
   */
  /*public void setDataToLoad(byte[]  dataloaded, boolean open){
    _dataloaded = dataloaded;
    _open= open;
  }*/


  /**
   * analyse activities data by a finished states machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  public boolean analyseTokens(DataExchange de, int beginPosition){
  	 if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
    	return analyseTokens1_6(de.getContents().getBytes(), beginPosition);
    } //else if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_7)) {
    return analyseTokens1_5(de.getContents().getBytes(), beginPosition);
	//return false;
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
	private boolean analyseTokens1_5(byte[]  dataloaded, int beginPosition) {
		_error="";
	    if(!analyseSIGTokens(dataloaded, beginPosition)){// analyse STI data
	      return false;
	    }else if(_open){// else if(!analyseSIGTokens(beginPosition))
	        return analyseDeltaTokens1_5(dataloaded, beginPosition);// analyse Delta data
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
	private boolean analyseTokens1_6(byte[]  dataloaded, int beginPosition) {
		_error="";
		StringBuffer newFile= new StringBuffer("");
		StringTokenizer st = new StringTokenizer(new String (dataloaded),DConst.CR_LF );
		st.nextToken();
		while(st.hasMoreTokens()){
			newFile.append(st.nextToken()+DConst.CR_LF);
		}
	    if(!analyseSIGTokens(newFile.toString().getBytes(), beginPosition)){// analyse STI data
	      return false;
	    }else if(_open){// else if(!analyseSIGTokens(beginPosition))
	        return analyseDeltaTokens1_6(dataloaded, beginPosition);// analyse Delta data
	    }// end else if(!analyseSIGTokens(beginPosition))
	    
	    return true;
	}
	
  /**
   * analyse Delta activities data by a finished states machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  private boolean analyseDeltaTokens1_5(byte[]  dataloaded, int beginPosition){
    String token;
    //String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (dataloaded), DConst.CR_LF );
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
  private boolean analyseDeltaTokens1_6(byte[]  dataloaded, int beginPosition){
    String token;
    //String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (dataloaded), DConst.CR_LF );
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
  private boolean analyseSIGTokens(byte[]  dataloaded, int beginPosition){
    String token;
    String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
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
          if (token.trim().length() < _ACTIVITYLENGTH){
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


	public void buildSetOfResources(DataExchange de, int beginPosition) {
	    if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
	    	buildSetOfResources1_6(de.getContents().getBytes(), beginPosition);
	    } else {
	    	buildSetOfResources1_5(de.getContents().getBytes(), beginPosition);
	    }
	      
	} //end analyseTokens
  
  /**
   * build activitiesList from activities data by a finite state machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  public void buildSetOfResources1_5(byte[]  dataloaded,int beginPosition){
  	String token;
    //String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
    //StringTokenizer stLine = null; //auxiliar StringTokenizer for reading subStrings in a line
    //int state=0;
    int position=beginPosition;
    String course="";
    while (st.hasMoreElements()){
      token = st.nextToken();
      switch (position){
        case 0:// empty line
          position = 1;
          break;
        case 1:// activity name
        	//activityName= token;
        	course= "";
        	token += DConst.ACTIVITY_NAME_TOKEN_SEPARATOR+
			DConst.ACTIVITY_STANDARD_SITE+
			DConst.ACTIVITY_NAME_TOKEN_SEPARATOR+
			DConst.ACTIVITY_STANDARD_CAPACITY;
        	position = 2;
        	break;
        case 2://activity visibility
        	
        	position = 3;
        	break;
        case 3://number of activities
          position = 4;
          break;
        case 4:// teachers' names
          
          position = 7;
          break;
        case 5:// empty line
          position = 6;
          break;
        case 6:// empty line
          position = 7;
          break;
        case 7://number of blocs
          position = 8;
          break;
        case 8://duration of blocs
          position = 9;
          break;
        case 9://days and periods of blocs
          
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
        case 14://activity is fixed or not
        	DataExchange dEx= new ByteArrayMsg(DConst.FILE_VER_NAME1_5, course+token);
        	String site = DConst.ACTIVITY_STANDARD_SITE;
        	DResource actResc = getResource(site);
			//SetOfStudents sos = (SetOfStudents)getResource(site).getAttach();
			if(actResc==null){
				actResc = new DResource(site,new SetOfActivities(_open));
				addResource(actResc,1);
			}
			
			SetOfActivities soa = (SetOfActivities)actResc.getAttach();
			soa.buildSetOfResources(dEx,1);
        	position = beginPosition;
          //addResource(activityResource,1);
          break;

      }// end switch (position)
      course+=token+DConst.CR_LF;

    }// end while (st.hasMoreElements())

  }
  
  /**
   * build activitiesList from activities data by a finite state machine
   * @param integer the beginPosition (start position of the finished states machine)
   * @return boolean "true" if the analysis proceeded successfully and false otherwise
   * */
  public void buildSetOfResources1_6(byte[]  dataloaded,int beginPosition){
  	String token;
    //String sousString; //auxiliar String for stocking a substring of a line
    StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
    //StringTokenizer stLine = null; //auxiliar StringTokenizer for reading subStrings in a line
    //int state=0;
    int position=beginPosition;
    String site="", course="";
    st.nextToken();// jump the first line
    while (st.hasMoreElements()){
      token = st.nextToken();
      switch (position){
        case 0:// empty line
          position = 1;
          break;
        case 1:// activity name
        	//activityName= token;
        	course= "";
        	site= DXToolsMethods.getToken(token, DConst.STUDENT_TOKEN_SEPARATOR,
        			DConst.ACTIVITY_SITE_TOKEN);
        	position = 2;
        	break;
        case 2://activity visibility
        	
        	position = 3;
        	break;
        case 3://number of activities
          position = 4;
          break;
        case 4:// teachers' names
          
          position = 7;
          break;
        case 5:// empty line
          position = 6;
          break;
        case 6:// empty line
          position = 7;
          break;
        case 7://number of blocs
          position = 8;
          break;
        case 8://duration of blocs
          position = 9;
          break;
        case 9://days and periods of blocs
          
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
        case 14://activity is fixed or not
        	DataExchange dEx= new ByteArrayMsg(DConst.FILE_VER_NAME1_5, course+token);
        	DResource actResc = getResource(site);
			//SetOfStudents sos = (SetOfStudents)getResource(site).getAttach();
			if(actResc==null){
				actResc = new DResource(site,new SetOfActivities(_open));
				//sos= new SetOfStudents();
				addResource(actResc,1);
			}
			
			SetOfActivities soa = (SetOfActivities)actResc.getAttach();
			soa.buildSetOfResources(dEx,1);
        	position = beginPosition;
          //addResource(activityResource,1);
          break;

      }// end switch (position)
      course+=token+DConst.CR_LF;

    }// end while (st.hasMoreElements())

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
    DResource a = getResource(actKey);
    if(a!=null){
      DResource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeKey);
      if(t!=null){
        DResource s = ((Type)t.getAttach()).getSetOfSections().getResource(secKey);
        if(s!=null){
          DResource u= ((Section)s.getAttach()).getSetOfUnities().getResource(unitKey);
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
    DResource a = getResource(actID);
    if(a!=null){
      DResource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeID);
      if(t!=null){
        DResource s = ((Type)t.getAttach()).getSetOfSections().getResource(secID);
        if(s!=null){
          DResource u= ((Section)s.getAttach()).getSetOfUnities().getResource(unitID);
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
    DResource a = getResource(actID);
    if(a!=null){
      DResource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeID);
      if(t!=null){
        DResource s = ((Type)t.getAttach()).getSetOfSections().getResource(secID);
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
   DResource a = getResource(actID);
   if(a!=null){
     DResource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeID);
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
    DResource a = getResource(actKey);
    DResource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeKey);
    DResource s = ((Type)t.getAttach()).getSetOfSections().getResource(secKey);
    DResource u = ((Section)s.getAttach()).getSetOfUnities().getResource(unitKey);
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
    DResource a = getResource(actKey);
    DResource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeKey);
    DResource s = ((Type)t.getAttach()).getSetOfSections().getResource(secKey);
    DResource u = ((Section)s.getAttach()).getSetOfUnities().getResource(unitKey);
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
    DResource a = getResource(actID);
    DResource t = ((Activity)a.getAttach()).getSetOfTypes().getResource(typeID);
    DResource s = ((Type)t.getAttach()).getSetOfSections().getResource(secID);
    DResource u = ((Section)s.getAttach()).getSetOfUnities().getResource(unitID);
    u.getAttach().setField(fieldIndex, fieldValue);
  }

    
  /**
	 * check if there is an error detected
	 * @return
	 */
	private boolean isErrorEmpty() {
		return _error.length()==0;
	}

/* (non-Javadoc)
 * @see dInternal.DObject#getSelectedField()
 */
public long getSelectedField() {
	// TODO Auto-generated method stub
	return 0;
}


}