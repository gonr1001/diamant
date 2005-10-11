/**
 *
 * Title: DLoadData 
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
 * @since JDK1.3
 */

package dInternal.dData;



import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;



import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DValue;
import dInternal.DataExchange;

import dInternal.Preferences;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dInstructors.InstructorAttach;
import dInternal.dData.dInstructors.SetOfInstructors;
import dInternal.dData.dRooms.RoomsAttributesInterpretor;
import dInternal.dData.dRooms.SetOfCategories;
import dInternal.dData.dRooms.SetOfRooms;

import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dStudents.SetOfStuCourses;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.Student;

import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.txt.FilterFile;
import dConstants.DConst;

public class DLoadData {
	
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
	public DLoadData() {
		_dm = null;		
		initLoadData();
		completeLoadData();
	}
	/**
	 * LoadData initiate the private fields
	 * @param model the current DModel is taken in account
	 */
	public DLoadData(DModel dm) {
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
	public DLoadData(DModel dm, String args ) {
		_dm = dm;
		if (_dm != null) // XXXX Pascal: else ?
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
	
	public SetOfSites extractRooms(SetOfSites currentList, boolean merge){
		
		DataExchange de = buildDataExchange(_roomsFileName);
		SetOfSites roomsList = new SetOfSites(); //,5,14);// 5 jours et 14 periods!
		if (de != null) {
			if (merge)
				if(currentList!= null)
					roomsList.setSetOfResources(currentList.getSetOfResources());		
			if (roomsList.analyseTokens(de, 0)){
				roomsList.setAttributesInterpretor(_roomsAttributesInterpretor);
				roomsList.buildSetOfResources(de, 0);
			}
		} else {// (NullPointerException npe) {
			new FatalProblemDlg("I was in LoadData.extractRooms. preload failed!!!" );
			System.exit(52);
		}
		return roomsList;
	}// end extractRooms
	
	/*   // SetOfRooms
	 SetOfSites roomsList = new SetOfSites(); //,5,14);
	 DataExchange de = buildDataExchange(project.nextToken().trim().getBytes());
	 if (roomsList.analyseTokens(de,0)){
	 roomsList.setAttributesInterpretor(_roomsAttributesInterpretor);
	 roomsList.buildSetOfResources(de, 3);
	 }
	 extract.add(roomsList);*/
	/**
	 * 
	 * @param currentList the current SetOfInstructors
	 * @param merge a boolean <p>(if merge = true --> merge the new SetOfInstructors to the current SetOfInstructors) </p>
	 *                        (if merge = false --> replace the current SetOfInstructors by the new SetOfInstructors)
	 * @return SetOfInstructors
	 */
	public SetOfInstructors extractInstructors(SetOfInstructors currentList, boolean merge){
		//byte[]  dataloaded = preLoad(_instructorFileName);
		DataExchange de = buildDataExchange(_instructorFileName);
		SetOfInstructors instructorsList= new SetOfInstructors(5,14);// 5 jours et 14 periods!
		if (de != null) {
			if (merge)
				if(currentList!= null)
					instructorsList.setSetOfResources(currentList.getSetOfResources());		
			if (instructorsList.analyseTokens(de, 0)){
				instructorsList.buildSetOfResources(de, 0);
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
	public SetOfStuSites extractStudents(SetOfStuSites currentList, boolean merge){
		DataExchange de = buildDataExchange(_studentsFileName);
		//byte[]  dataloaded = preLoad(_studentsFileName);
		SetOfStuSites studentsList = new SetOfStuSites();
		if (de.getContents() != null) {
			if (merge)
				if(currentList!=null)
					studentsList.setSetOfResources(currentList.getSetOfResources());
				
			if (studentsList.analyseTokens(de, 0)){
				studentsList.buildSetOfResources(de, 0);
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
	public SetOfActivitiesSites extractActivities(SetOfActivitiesSites currentList, boolean merge){
		DataExchange de = buildDataExchange(_activitiesFileName);
		SetOfActivitiesSites activitiesList = new SetOfActivitiesSites(false);
		if ( de.getContents() != null) {
			if (merge)
				if(currentList!=null)
					activitiesList.setSetOfResources(currentList.getSetOfResources());
			if (activitiesList.analyseTokens(de, 1)){
				activitiesList.buildSetOfResources(de, 1);
			}
		} else {// (NullPointerException npe) {
			new FatalProblemDlg("I was in LoadData.extractActivities. preload failed!!!" );
			System.exit(52);
		}
		return activitiesList;
	}
	
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
				SetOfInstructors instructorsList= new SetOfInstructors(tts.getNumberOfActiveDays(),
						tts.getCurrentCycle().getMaxNumberOfPeriodsADay());
				DataExchange de = buildDataExchange(project.nextToken().trim().getBytes());
				if (instructorsList.analyseTokens(de, 0)){
					instructorsList.buildSetOfResources(de, 0);
				}
				extract.add(instructorsList);
			}// end if(tts.getError().length()==0)
			
			// SetOfRooms
			SetOfSites roomsList = new SetOfSites(); //,5,14);
			DataExchange de = buildDataExchange(project.nextToken().trim().getBytes());
			if (roomsList.analyseTokens(de,3)){
				roomsList.setAttributesInterpretor(_roomsAttributesInterpretor);
				roomsList.buildSetOfResources(de, 3);
			}
			extract.add(roomsList);
			
			// extract SetOfActivities
			de = buildDataExchange(project.nextToken().trim().getBytes());
			SetOfActivitiesSites activitiesList = new SetOfActivitiesSites(true);
			if (activitiesList.analyseTokens(de, 1)){
				activitiesList.buildSetOfResources(de, 1);
			}
			extract.add(activitiesList);
			//extract SetOfStudents
			de = buildDataExchange(project.nextToken().trim().getBytes());
			SetOfStuSites studentsList = new SetOfStuSites();
			if (studentsList.analyseTokens(de, 0)){
				studentsList.buildSetOfResources(de, 0);
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
	
	private byte[] preLoad(String str) { // XXXX Pascal: Ne devrait pas avoir 2 return
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
	 */
	private DataExchange buildDataExchange(String fileName) {
		byte[] dataloaded = preLoad(fileName);
		StringTokenizer st = new StringTokenizer(new String (dataloaded), DConst.CR_LF);
		String token = st.nextToken().toString().trim();
		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME_XML1_7)) {
			return new ByteArrayMsg(DConst.FILE_VER_NAME_XML1_7, fileName);
		}
		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			return new ByteArrayMsg(DConst.FILE_VER_NAME1_6, new String (dataloaded));
		}
		return new ByteArrayMsg(DConst.FILE_VER_NAME1_5, new String (dataloaded));
	}
	
	/**
	 * 
	 * @param dataloaded
	 * @return
	 */
	public DataExchange buildDataExchange(byte[] dataloaded) {
		//byte[] dataloaded = preLoad(fileName);
		StringTokenizer st = new StringTokenizer(new String (dataloaded), DConst.CR_LF);
		String token = st.nextToken().toString().trim();
		//if (token.equalsIgnoreCase(DConst.FILE_VER_NAME_XML1_7)) {
		//	return new ByteArrayMsg(DConst.FILE_VER_NAME_XML1_7, fileName);
		//}
		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			return new ByteArrayMsg(DConst.FILE_VER_NAME1_6, new String (dataloaded));
		}
		return new ByteArrayMsg(DConst.FILE_VER_NAME1_5, new String (dataloaded));
	}
	
	
	/**
	 * 
	 * @param currentSetOfResc the current DSetOfResources
	 * @param file the file to import
	 * @return DSetOfResources which is merge of the new DSetOfResources to the current DSetOfResources 
	 */
	public DSetOfResources selectiveImport(DSetOfResources currentSetOfResc, String file){//, boolean merge){
		DataExchange de = buildDataExchange(file);
		DSetOfResources newSetOfResc=null;
		//int position=0;
		if (currentSetOfResc instanceof dInternal.dData.dInstructors.SetOfInstructors ){
			_instructorFileName= file;
			newSetOfResc= extractInstructors(null,false);
			_dm.resizeResourceAvailability(newSetOfResc);
			//((SetOfInstructors)currentSetOfResc).setDataToLoad(dataloaded,5,14);
		} else if (currentSetOfResc instanceof dInternal.dData.dRooms.SetOfSites){
			_roomsFileName= file;
			newSetOfResc= extractRooms(null,false);
			_dm.resizeSiteAvailability((SetOfSites)newSetOfResc);
		} else if (currentSetOfResc instanceof dInternal.dData.dStudents.SetOfStuSites){
			_studentsFileName= file;
			newSetOfResc= extractStudents(null,false);
		}else if (currentSetOfResc instanceof dInternal.dData.dActivities.SetOfActivitiesSites){
			_activitiesFileName= file;
			newSetOfResc= extractActivities(null,false);
			//position=1;
		}else {// (NullPointerException npe) {
			new FatalProblemDlg("I was in LoadData.selectiveImport, No resource class available!!!" );
		}
		
		if (de != null) {
			// newSetOfResc ne peut jamais être null !!!!
			if ((newSetOfResc != null) && (newSetOfResc.getError() == "")) {
				makeDiff(newSetOfResc, currentSetOfResc);
			//	currentSetOfResc.buildSetOfResources(de, position);
				currentSetOfResc.sortSetOfResourcesByID();
              }// Ici sans le else on passe même s’il y a une erreur !!!!
			else
				new FatalProblemDlg(newSetOfResc.getError());
		} else {// (NullPointerException npe) {
			new FatalProblemDlg(
					"I was in LoadData.selectiveImport. preload failed!!!");
		}

		return currentSetOfResc;
	}
    
    /**
        * 
        * @param currentRsc
        * @param newRsc
        * @return
        */
       private void makeDiff(DSetOfResources newSites, DSetOfResources currentSites) {
        System.err.println("...........MAKEDIFF.......................... \n");
            // find changed and unchanged element

            findChangesInElements(newSites, currentSites);

            // find deleted element

            findDeletedElements(newSites, currentSites);

            // find added element

            findAddedElements(newSites, currentSites);
    }

       /**
         * 
         * @param newSites
         * @param currentSites
         */
       private void findDeletedElements(DSetOfResources newSites,DSetOfResources currentSites) {
           // find a site
         
               int siteSize = getSiteSize(currentSites);
               for (int i = 0; i < siteSize; i++) {
                   String rscSite = getSite(currentSites, i);
                   DSetOfResources rescSite = getRscSite(currentSites, i);
                   int catSize = getCategorySize(rescSite);
                   // find category in site
                   for (int j = 0; j < catSize; j++) {
                       String rscCat = getCategory(rescSite, j);
                       DSetOfResources rescCat = getRscCategory(rescSite, j);
                       // find resource in a category
                       for (int k = 0; k < rescCat.size(); k++) {
                           // current ressource
                            DResource curResc = rescCat.getResourceAt(k);
                           if (getResource(newSites, curResc,rscSite, rscCat) == null) {
                               DValue error = new DValue();
                               error.setStringValue(DConst.DELETED_ELEMENT
                                       + curResc.getID());
                               if(_dm!=null)   _dm.getSetOfImportSelErrors().addResource(
                                       new DResource("1", error), 0);
                             // System.out.println("DELETED_ELEMENT "+ curResc.getID());
                               rescCat.removeResource(curResc.getID());
                               k--; // Puisque la liste est trié Sinon c est  k=0;
                           }// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
                       }// end for k++
                   }// end for  j++
               
               }// end for  i++
           
       } 
       
       /**
        * 
        * @param newSites
        * @param currentSites
        */
       private void findAddedElements(DSetOfResources newSites,
               DSetOfResources currentSites) {
           // find a site
      
               int newSize = getSiteSize(newSites);
               for (int i = 0; i < newSize; i++) {
                   String rscSite = getSite(newSites, i);
                   DSetOfResources rescSite = getRscSite(newSites, i);
                   int catSize = getCategorySize(rescSite);
                   // find category in site
                   for (int j = 0; j < catSize; j++) {
                       String rscCat = getCategory(rescSite, j);
                       DSetOfResources rescCat = getRscCategory(rescSite, j);
                       // find resource in a category
                       for (int k = 0; k < rescCat.size(); k++) {
//                       current ressource
                           DResource newRes = rescCat.getResourceAt(k);
                           if (getResource(currentSites, newRes ,
                                   rscSite, rscCat) == null) {
                               DValue error = new DValue();
                               error.setStringValue(DConst.ADDED_ELEMENT
                                       + newRes.getID());
                               if(_dm!=null)    _dm.getSetOfImportSelErrors().addResource(
                                       new DResource("2", error), 0);
                         //      System.out.println("ADDED_ELEMENT "+ newRes.getID());
                               DResource crescSite =currentSites.getResource(rscSite);
                              if ( crescSite!=null){
                                  DSetOfResources crescCat =((DSetOfResources)crescSite.getAttach());
                                  crescCat.addResourceMod(newRes,1);
                              }
                            }// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
                       }// end for k++
                   }// end for j++
               }// end for i++
          
       }
       /**
        * 
        * @param newSites
        * @param currentSites
        */
       private void findChangesInElements(DSetOfResources newSites,
               DSetOfResources currentSites) {
           // find a site
     
               int siteSize = getSiteSize(currentSites);
               for (int i = 0; i < siteSize; i++) {
                   String rscSite = getSite(currentSites, i);
                   DSetOfResources rescSite = getRscSite(currentSites, i);
                   int catSize = getCategorySize(rescSite);
                   // find category in site
                   for (int j = 0; j < catSize; j++) {
                       String rscCat = getCategory(rescSite, j);
                       DSetOfResources rescCat = getRscCategory(rescSite, j);
                       // find resource in a category
                       for (int k = 0; k < rescCat.size(); k++) {
                           DResource resc = rescCat.getResourceAt(k);
                           DResource newRes=getResource(newSites,resc ,rscSite, rscCat);
                           if (newRes!= null) {
                               // Already exist does it change ?
                               boolean changed=false;
                               if (currentSites instanceof SetOfStuSites) {
                                   // Find if element change
                                  changed=compareStudents(newRes,resc);
                               }else if (currentSites instanceof SetOfInstructors) {
                                   changed=compareInstructors(resc,newRes);
                               } 
                               DValue error = new DValue();
                               if (changed == true) {
                                   error.setStringValue(DConst.CHANGED_ELEMENT + newRes.getID());
                                   if(_dm!=null)     _dm.getSetOfImportSelErrors().addResource(
                                           new DResource("3", error), 0);
                               //    System.out.println("CHANGED_ELEMENT "+ newRes.getID());
                               } else {
                                   error.setStringValue(DConst.UNCHANGED_ELEMENT + newRes.getID());
                                   if(_dm!=null)    _dm.getSetOfImportSelErrors().addResource(
                                           new DResource("4", error), 0);
                                  // System.out.println("UNCHANGED_ELEMENT "+ newRes.getID());
                               }
                           }// end if !=null
                       }// end for k++
                   }// end for j++
               }// end for i++
        }
       
       /**
        *  Compare two students
        * @param newSites
        * @param currentSites
        * @return boolean resChanged
        */ 
       private boolean compareStudents(DResource newRes, DResource currentRes) {
        boolean resChanged = false;
        Student currentStudent = (Student) currentRes;
        SetOfStuCourses currentCourses = currentStudent.getCoursesList();
        Student newStudent = (Student) newRes;
        SetOfStuCourses newCourses = newStudent.getCoursesList();
        // course added
        for (int m = 0; m < newCourses.size(); m++) {
            if (currentCourses.getIndexOfResource(newCourses.getResourceAt(m)
                    .getID()) == -1) {
                resChanged = true;
                System.out.println("added "
                        + newCourses.getResourceAt(m).getID());// debug
                currentCourses.addResourceMod(newCourses.getResourceAt(m), 1);
            }
        } // end for

        // course deleted
        for (int k = 0; k < currentCourses.size(); k++) {
            // if(currentRsc.getResource(newRsc.getResourceAt(i).getKey())==null){
            if (newCourses.getIndexOfResource(currentCourses.getResourceAt(k)
                    .getID()) == -1) {
                resChanged = true;
                System.out.println("remove "
                        + currentCourses.getResourceAt(k).getID());// debug
                currentCourses.removeResourceAt(k);
            }
        }// end for
        return resChanged;
    }
       /**
        * Compare two Instructors
        * 
        * @param newSites
        * @param currentSites
        * @return boolean resChanged
        */ 
      private boolean compareInstructors(DResource currentRes,DResource newRes) {
             boolean resChanged = false;
             InstructorAttach newAtt = (InstructorAttach) newRes.getAttach();
             InstructorAttach curAtt = (InstructorAttach) currentRes.getAttach();
            if (!curAtt.isEquals(newAtt)){
                resChanged = true;
                curAtt.setAvailability(newAtt.getMatrixAvailability());
                currentRes.setAttach(curAtt);
                System.out.println("Changed "   + currentRes.getID());// debug
             }else
                 System.out.println("UnChanged "   + currentRes.getID());// debug
             
           return resChanged;
              }// end for 
      /**
       * Compare two Courses
       * 
       * @param newSites
       * @param currentSites
       * @return boolean resChanged
       */ 
//     private boolean compareCourses(DResource currentRes,DResource newRes) {
//            boolean resChanged = false;
//          Activity curAtt = (Activity) currentRes.getAttach();
//            System.err.println(newRes.getID()+"\n");
//           if (!curAtt.isEquals(newRes.getAttach())){
//               resChanged = true;
////               curAtt.setAvailability(newAtt.getMatrixAvailability());
////               currentRes.setAttach(curAtt);
//               System.out.println("Changed "   + currentRes.getID());// debug
//            }else
//                System.out.println("UnChanged "   + currentRes.getID());// debug
//            
//          return resChanged;
//     }// end for 

//	/**
//	 * 
//	 * @param newSites
//	 * @param currentSites
//	 */
//	private void findDeletedElement(DSetOfResources newSites, DSetOfResources currentSites){
//		//		find a site
//		int siteSize = getSiteSize(currentSites);
//		for(int i=0; i< siteSize; i++){
//			String rscSite = getSite(currentSites, i); 
//			DSetOfResources rescSite = getRscSite(currentSites, i);
//			int catSize = getCategorySize(rescSite);
//			// find category in site
//			for(int j = 0; j < catSize; j++ ){
//				String rscCat = getCategory(rescSite, j); 
//				DSetOfResources rescCat = getRscCategory(rescSite, j);
//				//find resource in a category
//				for (int k=0; k< rescCat.size(); k++){
//					//if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
//					if(getResource(newSites,rescCat.getResourceAt(k), rscSite, rscCat)==null){
//						DValue error= new DValue();
//						error.setStringValue(DConst.DELETED_ELEMENT + rescCat.getResourceAt(k).getID());
//						_dm.getSetOfImportSelErrors().addResource(new DResource("1",error),0);
//					}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
//				}// end for (int k=0; k< currentSites.size(); k++)
//			}
//		}// end for (int i=0; i< currentRsc.size(); i++)
//	}
//	
//	/**
//	 * 
//	 * @param newSites
//	 * @param currentSites
//	 */
//	private void findAddedElement(DSetOfResources newSites, DSetOfResources currentSites){
//		//		find a site
//		int siteSize = getSiteSize(newSites);
//		for(int i=0; i< siteSize; i++){
//			String rscSite = getSite(newSites, i); 
//			DSetOfResources rescSite = getRscSite(newSites, i);
//			int catSize = getCategorySize(rescSite);
//			// find category in site
//			for(int j = 0; j < catSize; j++ ){
//				String rscCat = getCategory(rescSite, j); 
//				DSetOfResources rescCat = getRscCategory(rescSite, j);
//				//find resource in a category
//				for (int k=0; k< rescCat.size(); k++){
//					if(getResource(currentSites,rescCat.getResourceAt(k), rscSite, rscCat)==null){
//						DValue error= new DValue();
//						error.setStringValue(DConst.ADDED_ELEMENT + rescCat.getResourceAt(k).getID());
//						_dm.getSetOfImportSelErrors().addResource(new DResource("2",error),0);
//					}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
//				}// end for (int k=0; k< currentSites.size(); k++)
//			}
//		}// end for (int i=0; i< currentRsc.size(); i++)
//	}
//	
//	/**
//	 * 
//	 * @param newSites
//	 * @param currentSites
//	 */
//	private void findChangedElement(DSetOfResources newSites, DSetOfResources currentSites){
//		//		find a site
//		int siteSize = getSiteSize(currentSites);
//		for(int i=0; i< siteSize; i++){
//			String rscSite = getSite(currentSites, i); 
//			DSetOfResources rescSite = getRscSite(currentSites, i);
//			int catSize = getCategorySize(rescSite);
//			// find category in site
//			for(int j = 0; j < catSize; j++ ){
//				String rscCat = getCategory(rescSite, j); 
//				DSetOfResources rescCat = getRscCategory(rescSite, j);
//				//find resource in a category
//				for (int k=0; k< rescCat.size(); k++){
//					//if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
//					DResource r = getResource(newSites,rescCat.getResourceAt(k), rscSite, rscCat);
//					if(r!=null)
//						if(!r.getAttach().isEquals(rescCat.getResourceAt(k).getAttach())){
//							DValue error= new DValue();
//							error.setStringValue(DConst.CHANGED_ELEMENT + rescCat.getResourceAt(k).getID());
//							_dm.getSetOfImportSelErrors().addResource(new DResource("3",error),0);
//						}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
//				}// end for (int k=0; k< currentSites.size(); k++)
//			}
//		}// end for (int i=0; i< currentRsc.size(); i++)
//	}
//	
//	/**
//	 * 
//	 * @param newSites
//	 * @param currentSites
//	 */
//	private void findUnChangedElement(DSetOfResources newSites, DSetOfResources currentSites){
//		//		find a site
//		int siteSize = getSiteSize(newSites);
//		for(int i=0; i< siteSize; i++){
//			String rscSite = getSite(newSites, i); 
//			DSetOfResources rescSite = getRscSite(newSites, i);
//			int catSize = getCategorySize(rescSite);
//			// find category in site
//			for(int j = 0; j < catSize; j++ ){
//				String rscCat = getCategory(rescSite, j); 
//				DSetOfResources rescCat = getRscCategory(rescSite, j);
//				//find resource in a category
//				for (int k=0; k< rescCat.size(); k++){
//					//if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
//					DResource r = getResource(currentSites,rescCat.getResourceAt(k), rscSite, rscCat);
//					if(r!=null)
//						if(!r.getAttach().isEquals(rescCat.getResourceAt(k).getAttach())){
//							DValue error= new DValue();
//							error.setStringValue(DConst.UNCHANGED_ELEMENT + rescCat.getResourceAt(k).getID());
//							_dm.getSetOfImportSelErrors().addResource(new DResource("4",error),0);
//						}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
//				}// end for (int k=0; k< currentSites.size(); k++)
//			}
//		}// end for (int i=0; i< currentRsc.size(); i++)
//	}
//	
//	
//	/**
//	 * 
//	 * @param currentRsc
//	 * @param newRsc
//	 * @return
//	 */
//	private DSetOfResources makeDiff(DSetOfResources newSites, DSetOfResources currentSites){
//		//find deleted element
//		findDeletedElement(newSites,currentSites);
//		
//		//	  find added element
//		findAddedElement(newSites,currentSites);
//		
//		//	find changed element
//		findChangedElement(newSites,currentSites);
//		
//		//	find unchanged element
//		findUnChangedElement(newSites,currentSites);
//		
//		return null;
//	}
//	
//	/**
//	 * 
//	 * @param currentRsc
//	 * @param newRsc
//	 * @return
//	 */
//	private DSetOfResources updateSetOfStudents (DSetOfResources currentSites, DSetOfResources newSites){
//		//		find a site
//		int siteSize = getSiteSize(currentSites);
//		for(int i=0; i< siteSize; i++){
//			String rscSite = getSite(currentSites, i); 
//			DSetOfResources rescSite = getRscSite(currentSites, i);
//			int stuSize = rescSite.size();
//			int inc=0;
//			//find deleted element
//			for (int j=0; j< stuSize; j++){
//				//if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
//				DResource r = getResource(newSites,rescSite.getResourceAt(inc), rscSite,"");
//				if(r==null){
//					rescSite.removeResourceAt(inc);
//				}else// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
//					inc++;
//			}// end for (int i=0; i< currentRsc.size(); i++)
//			
//			
//			//	find changed element
//			for (int j=0; j< rescSite.size(); j++){
//				//Resource r = newRsc.getResource(currentRsc.getResourceAt(i).getKey());
//				DResource r = getResource(newSites,rescSite.getResourceAt(j), rscSite,"");
//				if(r!=null){
//					if(!r.getAttach().isEquals(rescSite.getResourceAt(j).getAttach())) {
//						Student currentStudent = (Student) rescSite.getResourceAt(j);
//						SetOfStuCourses currentCourses =currentStudent.getCoursesList();
//						Student newStudent = (Student) r;
//						SetOfStuCourses newCourses =newStudent.getCoursesList();
//						// course deleted
//						for (int k=0; k< currentCourses.size(); k++){
//							//if(currentRsc.getResource(newRsc.getResourceAt(i).getKey())==null){
//							if(getResource(newCourses,currentCourses.getResourceAt(k), rscSite,"")==null){
//								currentCourses.removeResourceAt(k);
//							}
//						}	 	  				
//						//course added
//						for (int k=0; k< newCourses.size(); k++){
//							//if(currentRsc.getResource(newRsc.getResourceAt(i).getKey())==null){
//							if(getResource(currentCourses,newCourses.getResourceAt(k), rscSite,"")==null){
//								currentCourses.addResource(newCourses.getResourceAt(k),1);
//							}
//						}	
//					}
//				}// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
//			}// end for (int i=0; i< currentRsc.size(); i++)
//		}
//		
//		return null;
//		
//	}
//	
	/**
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	/*private DResource getResource(DSetOfResources source, DResource target){
	 if(source instanceof SetOfStuSites){
	 return source.getResource(target.getKey());
	 }
	 return source.getResource(target.getID());
	 }*/
	
	/**
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	private DResource getResource(DSetOfResources source, DResource target, String site, String cat){
		//String str= source.getClass().getName();
		//TODO make getResource for each site to search the resource
		if(source instanceof dInternal.dData.dInstructors.SetOfInstructors ){
			return source.getResource(target.getID());
		}
		DResource rescSite = source.getResource(site);
		if(source instanceof SetOfSites ){
			if(rescSite != null){
				DResource rescCat = ((DSetOfResources)rescSite.getAttach()).getResource(cat);
				if(rescCat!=null)
					return ((DSetOfResources)rescCat.getAttach()).getResource(target.getID());
			}
		}
		if(source instanceof SetOfActivitiesSites ){
			if (rescSite != null)
				return ((DSetOfResources)rescSite.getAttach()).getResource(target.getID());
		}
		if(source instanceof SetOfStuSites ){
			if (rescSite != null)
				return ((DSetOfResources)rescSite.getAttach()).getResource(target.getID());
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private String getSite(DSetOfResources sourceSites, int index){
		if(sourceSites instanceof dInternal.dData.dInstructors.SetOfInstructors ){
			return  DConst.ROOM_STANDARD_SITE;
		}
		DResource rsc= sourceSites.getResourceAt(index);
		if(rsc != null)
			return rsc.getID();
		return null;
		
	}	
	
	/**
	 * 
	 * @param sourceSites
	 * @return
	 */
	private int getSiteSize(DSetOfResources sourceSites){
		if(sourceSites instanceof dInternal.dData.dInstructors.SetOfInstructors ){
			return  1;
		}
		return sourceSites.size();
		
		
	}	
	
	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private DSetOfResources getRscSite(DSetOfResources sourceSites, int index){
		if(sourceSites instanceof dInternal.dData.dInstructors.SetOfInstructors ){
			return  sourceSites;
		}
		DResource rsc= sourceSites.getResourceAt(index);
		if(rsc != null)
			return (DSetOfResources) rsc.getAttach();
		return null;
		
	}	
	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private String getCategory(DSetOfResources sourceCategories, int index){
		if(sourceCategories instanceof SetOfCategories ){
			DResource rsc= sourceCategories.getResourceAt(index);
			if(rsc != null)
				return rsc.getID();
			return null;
		}
		
		return  DConst.ROOM_STANDARD_CAT;
	}	
	
	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private int getCategorySize(DSetOfResources sourceCategories){
		if(sourceCategories instanceof SetOfCategories ){
			return sourceCategories.size();
		}
		
		return  1;
	}	
	
	
	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private DSetOfResources getRscCategory(DSetOfResources sourceCategories, int index){
		if(sourceCategories instanceof SetOfRooms ){
			DResource rsc= sourceCategories.getResourceAt(index);
			if(rsc != null)
				return (DSetOfResources) rsc.getAttach();
			return null;
		}
		
		return  sourceCategories;
	}	
}