/**
*
* Title: LoadData $Revision: 1.58 $  $Date: 2004-12-16 19:20:56 $
* Description: LoadData is a class used to read all files then 
*              the corresponding Resources are created.
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
* @version $Revision: 1.58 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package dInternal.dDataTxt;



import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import dInternal.DModel;
import dInternal.dUtil.DXToolsMethods;
//import dInternal.dUtil.DXValue;
import dInternal.Preferences;
//import dInternal.dOptimization.SetOfEvents;
import dInternal.dTimeTable.TTStructure;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.txt.FilterFile;
import dConstants.DConst;

public class LoadData {
	
	private String _instructorFileName;
	private String _roomsFileName;
	private String _activitiesFileName;
	private String _studentsFileName;
	private String _functionFileName;
	private String _caractFileName;
	private DModel _dm;	
	private RoomsAttributesInterpretor _roomsAttributesInterpretor; 
	private String _chars;

	
	/**
	 * LoadData initiate the private fields
	 *
	 */
	public LoadData() {
		_dm = null;		
	    initLoadData();
	    completeLoadData();
	}
    /**
     * LoadData initiate the private fields
     * @param dm the current DModel is taken in account
     */
	public LoadData(DModel dm) {
		_dm = dm;
		initLoadData();
	    completeLoadData();
	}
	
	/**
	 * LoadData initiate the private fields
	 * @param dm the current DModel is taken in account
	 * @param args contents of a .dim file
	 * 
	 */
	public LoadData(DModel dm, String args ) {
		_dm = dm;
		if (_dm != null)
			_chars =_dm.getDDocument().getDMediator(
				).getDApplication(
				).getPreferences()._acceptedChars;
		completeLoadData();
		_roomsAttributesInterpretor= extractRoomsAttributesInterpretor();
		verifyImportDataFile(args);
	}
  
	/**
	 * extractRoomsAttributesInterpretor 
	 * produces an instance of RoomsAttributesInterpretor
	 * 
	 * @return the instance of RoomsAttributesInterpretor
	 */
	public RoomsAttributesInterpretor extractRoomsAttributesInterpretor(){
		RoomsAttributesInterpretor attr = new RoomsAttributesInterpretor();
    byte[]  dataloaded = preLoad(_functionFileName);
    if (dataloaded != null)
      attr.loadSetOfFunctions(dataloaded);
    dataloaded = preLoad(_caractFileName);
    if (dataloaded != null)
      attr.loadSetOfCaracteristics(dataloaded);
    return attr;
  }
  
  	/**
  	 * extractRooms
  	 * @param currentList the current SetOfRooms
  	 * @param merge a boolean <p>(if merge = true --> merge the new SetOfRooms to the current SetOfRooms) </p>
  	 *                        (if merge = false --> replace the current SetOfRooms by the new SetOfRooms)
  	 * @return SetOfRooms
  	 */
  
	public SetOfRooms extractRooms(SetOfRooms currentList, boolean merge){
		byte[] dataloaded = preLoad(_roomsFileName);
		SetOfRooms roomsList = new SetOfRooms(dataloaded); //,5,14);// 5 jours et 14 periods!
		if (dataloaded != null) {
			if (merge)
				if(currentList!= null)
					roomsList.setSetOfResources(currentList.getSetOfResources());		
		     if (roomsList.analyseTokens(0)){
		     	roomsList.setAttributesInterpretor(_roomsAttributesInterpretor);
		     	roomsList.buildSetOfResources(0);
		     }
		} else {// (NullPointerException npe) {
			new FatalProblemDlg("I was in LoadData.extractRooms. preload failed!!!" );
			System.exit(52);
		}
		return roomsList;
	}// end extractRooms


	/**
	 * 
	 * @param currentList the current SetOfInstructors
  	 * @param merge a boolean <p>(if merge = true --> merge the new SetOfInstructors to the current SetOfInstructors) </p>
  	 *                        (if merge = false --> replace the current SetOfInstructors by the new SetOfInstructors)
	 * @return SetOfInstructors
	 */
	public SetOfInstructors extractInstructors(SetOfInstructors currentList, boolean merge){
		byte[]  dataloaded = preLoad(_instructorFileName);
		SetOfInstructors instructorsList= new SetOfInstructors(dataloaded,5,14);// 5 jours et 14 periods!
		if (dataloaded != null) {
			if (merge)
				if(currentList!=null)
					instructorsList.setSetOfResources(currentList.getSetOfResources());
			if (instructorsList.analyseTokens(0)){
				instructorsList.buildSetOfResources(0);
				//return instructorsList;
			}
		} else {// (NullPointerException npe) {
			new FatalProblemDlg("I was in LoadData.extractInstructors. preload failed!!!" );
			System.exit(52);
		}
		return instructorsList;
	}

	/**
	 * 
	 * @param currentList the current SetOfStudents
  	 * @param merge a boolean <p>(if merge = true --> merge the new SetOfStudents to the current SetOfStudents) </p>
  	 *                        (if merge = false --> replace the current SetOfStudents by the new SetOfStudents)
	 * @return SetOfStudents
	 */  
	public SetOfStudents extractStudents(SetOfStudents currentList, boolean merge){
    byte[]  dataloaded = preLoad(_studentsFileName);
     SetOfStudents studentsList = new SetOfStudents(dataloaded);
     if (dataloaded != null) {
       if (merge)
         if(currentList!=null)
           studentsList.setSetOfResources(currentList.getSetOfResources());

       if (studentsList.analyseTokens(0)){
         studentsList.buildSetOfResources(0);
         //return studentsList;
       }
     } else {// (NullPointerException npe) {
     	new FatalProblemDlg("I was in LoadData.extractStudents. preload failed!!!" );
     	System.exit(52);
     }
     return studentsList;
  }

	/**
	 * 
	 * @param currentList the current SetOfActivities
  	 * @param merge a boolean <p>(if merge = true --> merge the new SetOfActivities to the current SetOfActivities) </p>
  	 *                        (if merge = false --> replace the current SetOfActivities by the new SetOfActivities)
	 * @return SetOfActivities
	 */  		
	public SetOfActivities extractActivities(SetOfActivities currentList, boolean merge){
		byte[]  dataloaded = preLoad(_activitiesFileName);
		SetOfActivities activitiesList = new SetOfActivities(dataloaded,false);
		if (dataloaded != null) {
			if (merge)
				if(currentList!=null)
					activitiesList.setSetOfResources(currentList.getSetOfResources());
			if (activitiesList.analyseTokens(1)){
					activitiesList.buildSetOfResources(1);
			}
		} else {// (NullPointerException npe) {
			new FatalProblemDlg("I was in LoadData.extractActivities. preload failed!!!" );
			System.exit(52);
		}
		return activitiesList;
	}

  /**
   * 
   * @param currentSetOfResc the current SetOfResources
   * @param file the file to import
   * @return SetOfResources which is merge of the new SetOfResources to the current SetOfResources 
   */
	/*public SetOfResources selectiveImport(SetOfResources currentSetOfResc, String file){//, boolean merge){
		//TODO delete
		
		//String str= currentSetOfResc.getClass().getName();
		//int beginPosition=0;
		//byte[]  dataloaded = preLoad(file);
		//SetOfResources newSetOfResc=null;
		/*
		//if (str.equalsIgnoreCase("dInternal.dDataTxt.SetOfInstructors")){
		if (currentSetOfResc instanceof dInternal.dDataTxt.SetOfInstructors ){
			// implement selective import for instructors
			_instructorFileName= file;
			newSetOfResc= extractInstructors(null,false);
			_dm.resizeResourceAvailability(newSetOfResc);
			((SetOfInstructors)currentSetOfResc).setDataToLoad(dataloaded,5,14);
		} else if (str.equalsIgnoreCase("dInternal.dDataTxt.SetOfRooms")){
      // implement selective import for rooms
    	_roomsFileName= file;
        newSetOfResc= extractRooms(null,false);
        _dm.resizeResourceAvailability(newSetOfResc);
      ((SetOfRooms)currentSetOfResc).setDataToLoad(dataloaded); //,5,14);
    } else if (str.equalsIgnoreCase("dInternal.dDataTxt.SetOfActivities")){
      // implement selective import for activities
      beginPosition=1;
      _activitiesFileName= file;
      newSetOfResc= extractActivities(null,false);
      SetOfEvents soe = new SetOfEvents(_dm);
      /*TODO delete the whole class
      soe.build((SetOfActivities)newSetOfResc, new SetOfResources(99));
      soe.updateActivities((SetOfActivities)newSetOfResc,soe.getSetOfResources());
      ((SetOfActivities)currentSetOfResc).setDataToLoad(dataloaded,false);
    } else if (str.equalsIgnoreCase("dInternal.dDataTxt.SetOfStudents")){
        _studentsFileName= file;
        newSetOfResc= extractStudents(null,false);
        
      ((SetOfStudents)currentSetOfResc).setDataToLoad(dataloaded);
    } else {// (NullPointerException npe) {
    	new FatalProblemDlg("I was in LoadData.selectiveImport, No resource class available!!!" );
    }

    if (dataloaded != null) {
      if (currentSetOfResc.analyseTokens(beginPosition)){
      	makeDiff(currentSetOfResc, newSetOfResc);
      	currentSetOfResc.buildSetOfResources(beginPosition);
        currentSetOfResc.sortSetOfResourcesByID();
        //DXValue = (DXValue)_dm.getSetOfImportSelErrors().getResourceAt(0).getAttach();
        //System.out.println("Make diff: "+value.getStringValue());//debug
        //System.out.println(_dm.getSetOfImportSelErrors().toWrite());//debug
        //return currentsetOfResc;
      }
      
    } else {// (NullPointerException npe) {
      new FatalProblemDlg("I was in LoadData.selectiveImport. preload failed!!!" );     
    }
    if (str.equalsIgnoreCase("dInternal.dDataTxt.SetOfStudents")){
    	updateSetOfStudents(currentSetOfResc, newSetOfResc);
    	System.out.println("updateSetOfStudents: ");//debug
    } 
    
    //setOfResc.sortSetOfResourcesByID();
    return currentSetOfResc;
  }*/

  /**
   * loadTheTT  this loads a full timetable as it was saved
   * 
   * @param fileName the file name is a .dia file
   * @param currentDir this is the full path of the file
   * @return a Vector containing the elements in the file .dia
   *         <p>version</p>
   *         <p>ttStructure</p>
   *         <p>SetOfInstructor</p>
   *         <p>SetOfRooms</p>
   *         <p>SetOfActivities</p>
   *         <p>SetOfStudents</p>
   */
  public Vector loadTheTT(String fileName, String currentDir){
    Vector extract= new Vector();
    String dataloaded= new String(preLoad(fileName));

     StringTokenizer project= new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR);
     if(project.countTokens()==6){ // 6 !!!!!!!!!!!!!!
       // extract version
       extract.add(project.nextToken().trim());
       //extract ttStructure
       TTStructure tts= new TTStructure();
       String ttsFileName= DXToolsMethods.getAbsoluteFileName(currentDir, project.nextToken().trim());
       tts.loadTTStructure(ttsFileName);
       extract.add(tts);
       // extract SetOfInstructor
       if(tts.getError().length()==0){
         SetOfInstructors instructorsList= new SetOfInstructors(project.nextToken().trim().getBytes(),
             tts.getNumberOfActiveDays(),tts.getCurrentCycle().getMaxNumberOfPeriodsADay());
         if (instructorsList.analyseTokens(0)){
           instructorsList.buildSetOfResources(0);
         }
         extract.add(instructorsList);
       }// end if(tts.getError().length()==0)

      // SetOfRooms
      SetOfRooms roomsList = new SetOfRooms(project.nextToken().trim().getBytes()); //,5,14);
      if (roomsList.analyseTokens(0)){
        roomsList.setAttributesInterpretor(_roomsAttributesInterpretor);
        roomsList.buildSetOfResources(3);
      }
     extract.add(roomsList);

     // extract SetOfActivities
     SetOfActivities activitiesList = new SetOfActivities(project.nextToken().trim().getBytes(),true);
     if (activitiesList.analyseTokens(1)){
       activitiesList.buildSetOfResources(1);
     }
     extract.add(activitiesList);
     //extract SetOfStudents
     SetOfStudents studentsList = new SetOfStudents(project.nextToken().trim().getBytes());
     if (studentsList.analyseTokens(0)){
       studentsList.buildSetOfResources(0);
     }
      extract.add(studentsList);

     }else{
       new FatalProblemDlg("I was in"+getClass().toString()+" LoadData class and loadProject. extract failed!!!" );
       System.exit(1);
     }
     return extract;
  }

  
	private void initLoadData() {
		_roomsAttributesInterpretor = new RoomsAttributesInterpretor();
		Preferences preferences = new Preferences(System.getProperty("user.dir")
		          + File.separator +
		          "pref"
		          + File.separator +
		          "pref.txt");
		    _chars = preferences._acceptedChars;   
	}  
	private void completeLoadData() {
		String path =System.getProperty("user.dir")+ File.separator+"pref"+File.separator;
		_functionFileName= path + "DXfunctions.sig";
		_caractFileName= path + "DXcaracteristics.sig";
	}
  
	private byte[] preLoad(String str) {
		FilterFile filter = new FilterFile();
		filter.setCharKnown("");
		filter.appendToCharKnown(_chars);
		if (filter.validFile(str)) {
			return filter.getByteArray();
		} 
		return null;
	} // preLoad(String str)
	
	
  private void verifyImportDataFile(String str){
    FilterFile filter = new FilterFile(_chars);
    if (filter.validFile(str)) {
      StringTokenizer st = new StringTokenizer(new String (filter.getByteArray()), DConst.CR_LF );
      if (st.countTokens() == DConst.NUMBER_OF_FILES){
        _instructorFileName = st.nextToken();
        _roomsFileName = st.nextToken();
        _activitiesFileName = st.nextToken();
        _studentsFileName = st.nextToken();
      } else {
        new FatalProblemDlg(
            "Wrong number of lines in the file:" +
            str +
            "\n" +
            "I was in DLoadData constructor ");
        System.exit(1);
      }
    } else {
      new FatalProblemDlg(
          "Unable to filter a file" +
          str +
          "\n" +
          "I was in DLoadData constructor "); //ys
      System.exit(1);
    }
  }
  
 /**
  * 
  * @param currentRsc
  * @param newRsc
  * @return
  */
/*	  private SetOfResources makeDiff(SetOfResources currentRsc, SetOfResources newRsc){
	 	//find deleted element
	  	//TODO delete
	  	/*
	  	for (int i=0; i< currentRsc.size(); i++){
	  		//if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
	  		if(getResource(newRsc,currentRsc.getResourceAt(i))==null){
	  		DXValue error= new DXValue();
	  		error.setStringValue(DConst.DELETED_ELEMENT + currentRsc.getResourceAt(i).getID());
	  		_dm.getSetOfImportSelErrors().addResource(new Resource("1",error),0);
	  		}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	  	}// end for (int i=0; i< currentRsc.size(); i++)
	  	
	  	//	  find added element
	  	for (int i=0; i< newRsc.size(); i++){
	  		//if(currentRsc.getResource(newRsc.getResourceAt(i).getKey())==null){
	  		if(getResource(currentRsc,newRsc.getResourceAt(i))==null){
	  		DXValue error= new DXValue();
	  		error.setStringValue(DConst.ADDED_ELEMENT + newRsc.getResourceAt(i).getID());
	  		_dm.getSetOfImportSelErrors().addResource(new Resource("2",error),0);
	  		}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	  	}// end for (int i=0; i< newRsc.size(); i++)
	  	
	  	//	find changed element
	  	for (int i=0; i< currentRsc.size(); i++){
	  		//Resource r = 	newRsc.getResource(currentRsc.getResourceAt(i).getKey());
	  		Resource r = getResource(newRsc,currentRsc.getResourceAt(i));
	  		if(r!=null){
	  			if(!r.getAttach().isEquals(currentRsc.getResourceAt(i).getAttach())) {
	  		DXValue error= new DXValue();
	  		error.setStringValue(DConst.CHANGED_ELEMENT + currentRsc.getResourceAt(i).getID());
	        _dm.getSetOfImportSelErrors().addResource(new Resource("3",error),0);
	  		}
	  		}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	  	}// end for (int i=0; i< currentRsc.size(); i++)
	  	
	  	//	find unchanged element
	  	for (int i=0; i< currentRsc.size(); i++){
	  		//Resource r = newRsc.getResource(currentRsc.getResourceAt(i).getKey());
	  		Resource r = getResource(newRsc,currentRsc.getResourceAt(i));
	  		if(r!=null){
	  			if(r.getAttach().isEquals(currentRsc.getResourceAt(i).getAttach())) {
	  		DXValue error= new DXValue();
	  		//System.out.println(_dm.getSetOfImportSelErrors().toWrite());//debug
	  		error.setStringValue(DConst.UNCHANGED_ELEMENT + currentRsc.getResourceAt(i).getID());
	  		_dm.getSetOfImportSelErrors().addResource(new Resource("4",error),0);
	  		}
	  		}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	  	}// end for (int i=0; i< currentRsc.size(); i++)
	  	
	 	return null;
	 	
	 }*/
	  
	  /**
	   * 
	   * @param currentRsc
	   * @param newRsc
	   * @return
	   */
	/* 	  private SetOfResources updateSetOfStudents (SetOfResources currentRsc, SetOfResources newRsc){
	 	 	//find deleted element
	 	  	for (int i=0; i< currentRsc.size(); i++){
	 	  		//if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
	 	  		if(getResource(newRsc,currentRsc.getResourceAt(i))==null){
	 	  			currentRsc.removeResourceAt(i);
	 	  		}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	 	  	}// end for (int i=0; i< currentRsc.size(); i++)
	 	  	

	 	  	//	find changed element
	 	  	for (int i=0; i< currentRsc.size(); i++){
	 	  		//Resource r = newRsc.getResource(currentRsc.getResourceAt(i).getKey());
	 	  		Resource r = getResource(newRsc,currentRsc.getResourceAt(i));
	 	  		if(r!=null){
	 	  			if(!r.getAttach().isEquals(currentRsc.getResourceAt(i).getAttach())) {
	 	  				StudentAttach currentStudent = (StudentAttach) currentRsc.getResourceAt(i).getAttach();
	 	  				SetOfResources currentCourses =currentStudent.getCoursesList();
	 	  				StudentAttach newStudent = (StudentAttach) r.getAttach();
	 	  				SetOfResources newCourses =newStudent.getCoursesList();
	 	  				// course deleted
	 	  				for (int j=0; j< currentCourses.size(); j++){
	 	  			  		//if(currentRsc.getResource(newRsc.getResourceAt(i).getKey())==null){
	 	  			  		if(getResource(newCourses,currentCourses.getResourceAt(j))==null){
	 	  			  			currentCourses.removeResourceAt(j);
	 	  			  		}
	 	  				}	 	  				
	 	  			   //course added
	 	  				for (int j=0; j< newCourses.size(); j++){
	 	  			  		//if(currentRsc.getResource(newRsc.getResourceAt(i).getKey())==null){
	 	  			  		if(getResource(currentCourses,newCourses.getResourceAt(j))==null){
	 	  			  			currentCourses.addResource(newCourses.getResourceAt(j),1);
	 	  			  		}
	 	  				}	
	 	  				

	 	  		}
	 	  		}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	 	  	}// end for (int i=0; i< currentRsc.size(); i++)
	 	  	
	 	  	 	  	
	 	 	return null;
	 	 	
	 	 }*/

	 	 /**
	 	  * 
	 	  * @param source
	 	  * @param target
	 	  * @return
	 	  */
/*	 	  private Resource getResource(SetOfResources source,Resource target){
	 	  	String str= source.getClass().getName();
	 	  	if(str.equalsIgnoreCase("dInternal.dDataTxt.SetOfStudents")){
	 	  		return source.getResource(target.getKey());
	 	  	}
	 	  	
	 	 	return source.getResource(target.getID());
	 	 }*/

}