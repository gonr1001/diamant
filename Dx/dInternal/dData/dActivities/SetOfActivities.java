/**
 *
 * Title: SetOfActivities $Revision: 1.7 $  $Date: 2005-03-08 16:00:44 $
 * Description: SetOfActivities is a class used to
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
 * @version $Revision: 1.7 $
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */

package dInternal.dData.dActivities;


import java.awt.Component;
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DataExchange;
import dInternal.dData.dStudents.SetOfStuCourses;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dUtil.DXToolsMethods;
//import d

public class SetOfActivities extends DSetOfResources{	
    /**
     * @associates SetOfActivitiesListener 
     */
	private Vector _SOAListeners = new Vector(1);
	/**activities in text format*/
	
	private String _error="";
	private int _line=1;
	private boolean _open;
	
	
	private int _NUMBEROFCYCLE = 1;
	final static public int _COURSENAMELENGTH=6;
	
	/**
	 * Constructor
	 * */
	public SetOfActivities(boolean open) {
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
		de.toString();
		beginPosition+=0;
		return false;
	}
	
	
	
	
	public void buildSetOfResources(DataExchange de, int beginPosition) {
		if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			//buildSetOfResources1_6(de.getContents().getBytes(), beginPosition);
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
    DResource unityResource, typeResource, activityResource=null;
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
            activityResource = new DResource(activityName.substring(0,_COURSENAMELENGTH),new Activity());
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
               bloc.addAssignment(new DResource(Integer.toString(i),cycleAss));
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
      SetOfStuCourses courses= ((SetOfStuCourses)sos.getResourceAt(i).getAttach());
      for (int j=0; j< courses.size(); j++){
        DResource activity= this.getResource(courses.getResourceAt(j).getID().
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
      DResource activ= new DResource(courseName.substring(0,_COURSENAMELENGTH), activity);
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
  public String toWrite(String site){
    String actlist="";// write
    for (int i=0; i<size(); i++){
      Activity activity = (Activity)getResourceAt(i).getAttach();
      for(int j=0; j< activity.getSetOfTypes().size(); j++){
        Type nature = (Type)(activity.getSetOfTypes().getResourceAt(j)).getAttach();
        for (int k=0; k< nature.getSetOfSections().size(); k++){
          actlist+= getResourceAt(i).getID();// write activity name
          actlist+= activity.getSetOfTypes().getResourceAt(j).getID()+"  ";// write nature and 2 space
          actlist+= nature.getSetOfSections().getResourceAt(k).getID();// write group and go to line
          actlist+= " "+site+" "+DConst.ACT_DEFAULT_CAPACITY+DConst.CR_LF;// write site and capacity of course
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
            DSetOfResources pfunctionRoom= bloc.getPreferFunctionRoom();
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
 *
 * @param actID
 * @param typeID
 * @return
 */
public DSetOfResources  getSetOfType(String actID){
	DResource a = getResource(actID);
	   if(a!=null){
	     return ((Activity)a.getAttach()).getSetOfTypes();
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

    
  
/* (non-Javadoc)
 * @see dInternal.DObject#getSelectedField()
 */
public long getSelectedField() {
	// TODO Auto-generated method stub
	return 0;
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
    //System.out.println("addSetOfActivities Listener ...");//debug
  }

/* (non-Javadoc)
 * @see dInternal.DSetOfResources#toWrite()
 */
public String toWrite() {
	// TODO Auto-generated method stub
	return null;
}
  
}