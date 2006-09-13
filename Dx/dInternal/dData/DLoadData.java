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
 * 
 */
package dInternal.dData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dDeveloper.DxFlags;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DValue;
import dInternal.DataExchange;
import dInternal.DxPreferences;
import dInternal.dData.dActivities.DxActivitiesSitesReader;
import dInternal.dData.dActivities.DxReadActivitiesSites1dot5;
import dInternal.dData.dActivities.DxReadActivitiesSites1dot6;
import dInternal.dData.dActivities.DxSetOfActivitiesSites;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dInstructors.DxInstructorsReader;
import dInternal.dData.dInstructors.DxReadInstructors1dot5;
import dInternal.dData.dInstructors.DxReadInstructorsdotDia;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxReadSite1dot5;
import dInternal.dData.dRooms.DxReadSite1dot6;
import dInternal.dData.dRooms.DxReadSitedotDia;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSiteReader;
import dInternal.dData.dRooms.SetOfCategories;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dStudents.SetOfStuCourses;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.Student;
import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.exception.DxException;
import eLib.exit.txt.FilterFile;

public class DLoadData {

	private String _instructorFileName;

	private String _roomsFileName;

	private String _activitiesFileName;

	private String _studentsFileName;

	private DModel _dm;

	private String _chars;

	/**
	 * LoadData initiate the private fields
	 * 
	 */
	public DLoadData() {
		_dm = null;
		doLoadData();
	}

	/**
	 * LoadData initiate the private fields
	 * 
	 * @param model
	 *            the current DModel is taken in account
	 */
	public DLoadData(DModel dm) {
		_dm = dm;
		doLoadData();
	}

	/**
	 * LoadData initiate the private fields
	 * 
	 * @param dm
	 *            the current DModel is taken in account
	 * @param args
	 *            contents of a .dim file
	 * 
	 */
	public DLoadData(DModel dm, String args) {
		_dm = dm;
		if (_dm != null) // XXXX Pascal: else ?
			if (DxFlags.newDoc) {
				_chars = _dm.getDxDocument().getDMediator().getDApplication()
						.getPreferences()._acceptedChars;
			} else {
				_chars = _dm.getDDocument().getDMediator().getDApplication()
						.getPreferences()._acceptedChars;
			}
		try {
			verifyImportDataFile(args);
		} catch (DxException e) {
			
			e.printStackTrace();
		}
	}

	// /**
	// * extractRoomsAttributesInterpretor produces an instance of
	// * RoomsAttributesInterpretor
	// *
	// * @return the instance of RoomsAttributesInterpretor
	// */
	// public RoomsAttributesInterpretor extractRoomsAttributesInterpretor() {
	// RoomsAttributesInterpretor attr = new RoomsAttributesInterpretor();
	// byte[] dataloaded = preLoad(_functionFileName);
	// if (dataloaded != null)
	// attr.loadSetOfFunctions(dataloaded);
	// dataloaded = preLoad(_caractFileName);
	// if (dataloaded != null)
	// attr.loadSetOfCaracteristics(dataloaded);
	// return attr;
	// }

	/**
	 * TODO: Create extractRooms for DxSetOfSites
	 * 
	 * @param currentList
	 *            the current SetOfRooms
	 * @param merge
	 *            a boolean
	 *            <p>
	 *            (if merge = true --> merge the new SetOfRooms to the current
	 *            SetOfRooms)
	 *            </p>
	 *            (if merge = false --> replace the current SetOfRooms by the
	 *            new SetOfRooms)
	 * @return SetOfRooms
	 * @throws DxException 
	 */

	public SetOfSites extractRooms(SetOfSites currentList, boolean merge) throws DxException {
		DataExchange de = buildDataExchange(_roomsFileName);
		SetOfSites roomsList = new SetOfSites(); // ,5,14);// 5 jours et 14
		// periods!
		if (de != null) {
			if (merge)
				if (currentList != null)
					roomsList
							.setSetOfResources(currentList.getSetOfResources());
			if (roomsList.analyseTokens(de, 0)) {
				// roomsList.setAttributesInterpretor(_roomsAttributesInterpretor);
				roomsList.buildSetOfResources(de, 0);
			}
		// (NullPointerException npe) {
			new DxExceptionDlg(
					"I was in LoadData.extractRooms. preload failed!!!");
			System.exit(52);
		}
		return roomsList;
	}// end extractRooms

	/**
	 * 
	 * @param currentList
	 *            the current SetOfInstructors
	 * @param merge
	 *            a boolean
	 *            <p>
	 *            (if merge = true --> merge the new SetOfInstructors to the
	 *            current SetOfInstructors)
	 *            </p>
	 *            (if merge = false --> replace the current SetOfInstructors by
	 *            the new SetOfInstructors)
	 * @return SetOfInstructors
	 */
	// public DxSetOfInstructors extractInstructors(
	// DxSetOfInstructors currentList, boolean merge, boolean dotDia) {
	// // byte[] dataloaded = preLoad(_instructorFileName);
	// DxSetOfInstructors instructorsList = null;
	// DataExchange de = buildDataExchange(_instructorFileName);
	// if (dotDia) {
	// // instructorsList= new
	// // DxSetOfInstructors(_dm.getTTStructure().getNumberOfActiveDays(),
	// // _dm.getTTStructure().getCurrentCycle().getMaxNumberOfPeriodsADay());//
	// // 5 jours et 14 periods!
	// } else {
	// // instructorsList= new DxSetOfInstructors(5,14);// 5 jours et 14
	// // periods !
	// }
	// // if (de != null) {
	// // if (merge)
	// // if(currentList!= null)
	// // instructorsList.setSetOfResources(currentList.getSetOfResources());
	// // if (instructorsList.analyseTokens(de, 0)){
	// // instructorsList.buildSetOfResources(de, 0);
	// // }
	// // } else {// (NullPointerException npe) {
	// // new FatalProblemDlg("I was in LoadData.extractInstructors. preload
	// // failed!!!" );
	// // System.exit(52);
	// // }
	// return instructorsList;
	// }
	/**
	 * 
	 * @param currentList
	 *            the current SetOfStudents
	 * @param merge
	 *            a boolean
	 *            <p>
	 *            (if merge = true --> merge the new SetOfStudents to the
	 *            current SetOfStudents)
	 *            </p>
	 *            (if merge = false --> replace the current SetOfStudents by the
	 *            new SetOfStudents)
	 * @return SetOfStudents
	 * @throws DxException 
	 */
	public SetOfStuSites extractStudents(SetOfStuSites currentList,
			boolean merge) throws DxException {
		DataExchange de = buildDataExchange(_studentsFileName);
		 SetOfStuSites studentsList = new SetOfStuSites();
		if (de.getContents() != null) {
			if (merge)
				if (currentList != null)
					studentsList.setSetOfResources(currentList
							.getSetOfResources());

			if (studentsList.analyseTokens(de, 0)) {
				studentsList.buildSetOfResources(de, 0);
				// return studentsList;
			}
		} else {
			new DxException(DConst.FILE_PRELOAD_FAILED);
		}
		return studentsList;
	}

	/**
	 * TODO Replace this method for DxSetOfActivitiesSites
	 * 
	 * @param currentList
	 *            the current SetOfActivities
	 * @param merge
	 *            a boolean
	 *            <p>
	 *            (if merge = true --> merge the new SetOfActivities to the
	 *            current SetOfActivities)
	 *            </p>
	 *            (if merge = false --> replace the current SetOfActivities by
	 *            the new SetOfActivities)
	 * @return SetOfActivities
	 */
public SetOfActivitiesSites extractActivities(
			SetOfActivitiesSites currentList, boolean merge) throws DxException {
		DataExchange de = buildDataExchange(_activitiesFileName);
		SetOfActivitiesSites activitiesList = new SetOfActivitiesSites(false,
				_dm.getTTStructure().getPeriodLenght());
		if (de.getContents() != null) {
			//            Vector  setOfResources = currentList
			//                            .getSetOfResources();
			if (merge)

				activitiesList.setSetOfResources(currentList
						.getSetOfResources());
			if (activitiesList.analyseTokens(de, 1)) {
				activitiesList.buildSetOfResources(de, 1);
			}
		} else {// (NullPointerException npe) {
			throw new DxException("NullPointerException: Preload failed!!!");
		}
		return activitiesList;
	}


	/**
	 * loadTheTT this loads a full timetable as it was saved
	 * 
	 * @param fileName
	 *            the file name is a .dia file
	 * @param currentDir
	 *            this is the full path of the file
	 * @return a Vector containing the elements in the file .dia
	 *         <p>
	 *         version
	 *         </p>
	 *         <p>
	 *         ttStructure
	 *         </p>
	 *         <p>
	 *         SetOfInstructor
	 *         </p>
	 *         <p>
	 *         SetOfRooms
	 *         </p>
	 *         <p>
	 *         SetOfActivities
	 *         </p>
	 *         <p>
	 *         SetOfStudents
	 *         </p>
	 * @throws Exception
	 */

	// TODO: since getSetOfInstructors throws exceptions, I had choice to add
	// thorws to function
	// or try catch on the return. Since we want to propagate error to the
	// application, I thought throws was the solution
	public Vector<Object> loadTheTT(String fileName, String currentDir)
			throws FileNotFoundException, DxException {
		// Vector <Object> diaData = new Vector <Object>();
		String dataloaded = new String(preLoad(fileName));
		// StringTokenizer project;
		StringTokenizer readFile;
		readFile = new StringTokenizer(dataloaded, DConst.CR_LF);

		String head = readFile.nextToken().trim();

		if (head.equalsIgnoreCase(DConst.FILE_HEADER_NAME1_5)
				|| head.equalsIgnoreCase(DConst.FILE_HEADER_NAME1_6)) {
			return load1dot5(fileName, currentDir);
		} else if (head.equalsIgnoreCase(DConst.FILE_HEADER_NAME2_1)) {
			return load2dot1(fileName, currentDir);
		} else {
			throw new DxException("Invalid FILE_HEADER_NAME !");
		}

		// DataExchange de;
		// if (!DConst.IN_DIA) {
		// project = new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR);
		// } else {
		// project = new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR_VIS);
		// }
		// if (project.countTokens() == 6) { // 6 !!!!!!!!!!!!!!
		// // extract version
		// diaData.add(project.nextToken().trim());
		// // extract ttStructure
		// TTStructure tts = new TTStructure();
		// if (!DConst.IN_DIA) {
		// String ttsFileName = DXToolsMethods.getAbsoluteFileName(
		// currentDir, project.nextToken().trim());
		// tts.loadTTSFromFile(ttsFileName);
		// diaData.add(tts);
		// } else {
		// de = buildDataExchange(project.nextToken().trim().getBytes());
		// tts.loadTTSFromString(de.getContents());
		// diaData.add(tts);
		// }
		// // extract SetOfInstructor
		// if (tts.getError().length() == 0) {
		// de = buildDataExchange(project.nextToken().trim().getBytes());
		// DxInstructorsReader dxir = new DxReadInstructors1dot5(de, tts
		// .getNumberOfActiveDays(), tts.getCurrentCycle()
		// .getMaxNumberOfPeriodsADay());
		// diaData.add(dxir.getSetOfInstructors());
		// }// end if(tts.getError().length()==0)
		//
		// // extract SetOfSites
		// if (!DxFlags.newRooms) {
		// SetOfSites roomsList = new SetOfSites();
		// de = buildDataExchange(project.nextToken().trim().getBytes());
		// if (roomsList.analyseTokens(de, 3)) {
		// // roomsList.setAttributesInterpretor(_roomsAttributesInterpretor);
		// roomsList.buildSetOfResources(de, 3);
		// }
		// diaData.add(roomsList);
		//
		// } else {
		// de = buildDataExchange(project.nextToken().trim().getBytes());
		// DxSiteReader dxrr = new DxReadSite1dot5(de);
		// diaData.add(dxrr.getSetOfSites());
		// }
		//
		// // extract SetOfActivities
		// de = buildDataExchange(project.nextToken().trim().getBytes());
		// SetOfActivitiesSites activitiesList = new SetOfActivitiesSites(true);
		// if (activitiesList.analyseTokens(de, 1)) {
		// activitiesList.buildSetOfResources(de, 1);
		// }
		// diaData.add(activitiesList);
		// // extract SetOfStudents
		// de = buildDataExchange(project.nextToken().trim().getBytes());
		// SetOfStuSites studentsList = new SetOfStuSites();
		// if (studentsList.analyseTokens(de, 0)) {
		// studentsList.buildSetOfResources(de, 0);
		// }
		// diaData.add(studentsList);
		//
		// } else {
		// new FatalProblemDlg("I was in" + getClass().toString()
		// + " LoadData class and loadProject. extract failed!!!");
		// //System.exit(-1);
		// }
		// return diaData;
	}

	private Vector<Object> load1dot5(String fileName, String currentDir)
			throws DxException {

		DxSetOfInstructors dxsoiInst = null;
		DxSetOfSites dxsosRooms = null;
		Vector<Object> diaData = new Vector<Object>();
		String dataloaded = new String(preLoad(fileName));
		StringTokenizer project;

		DataExchange de;

		project = new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR);

		if (project.countTokens() == 6) { // 6 !!!!!!!!!!!!!!
			// extract version
			diaData.add(project.nextToken().trim());

			// extract ttStructure
			TTStructure tts = new TTStructure();
			String ttsFileName = DXToolsMethods.getAbsoluteFileName(currentDir,
					project.nextToken().trim());
			tts.loadTTSFromFile(ttsFileName);
			diaData.add(tts);

			if (tts.getError().length() == 0) {
				// extract SetOfInstructor
				de = buildDataExchange(project.nextToken().trim().getBytes());
				DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, tts
						.getNumberOfActiveDays(), tts.getCurrentCycle()
						.getMaxNumberOfPeriodsADay());
				dxsoiInst = dxir.readSetOfInstructors();
				diaData.add(dxsoiInst);

				// extract SetOfSites
				de = buildDataExchange(project.nextToken().trim().getBytes());
				if (DxFlags.newRooms) {
					DxSiteReader dxrr = new DxReadSitedotDia(de, tts
							.getNumberOfActiveDays(), tts.getCurrentCycle()
							.getMaxNumberOfPeriodsADay());
					dxsosRooms = dxrr.readSetOfSites();
					diaData.add(dxsosRooms);
				} else {
					SetOfSites roomsList = new SetOfSites();
					if (roomsList.analyseTokens(de, 3)) {
						// roomsList.setAttributesInterpretor(_roomsAttributesInterpretor);
						roomsList.buildSetOfResources(de, 3);
					}
					diaData.add(roomsList);
				}

				// extract SetOfActivities
				de = buildDataExchange(project.nextToken().trim().getBytes());
				if (DxFlags.newActivity) {
					DxActivitiesSitesReader dxasr = new DxReadActivitiesSites1dot5(
							de, dxsoiInst, dxsosRooms.getAllRooms(), tts
									.getPeriodLenght(), true);

					diaData.add(dxasr.readSetOfActivitiesSites());
				} else {
					SetOfActivitiesSites activitiesList = new SetOfActivitiesSites(
							true, tts.getPeriodLenght());
					if (activitiesList.analyseTokens(de, 1)) {
						activitiesList.buildSetOfResources(de, 1);
					}
					diaData.add(activitiesList);
				}

				// extract SetOfStudents
				de = buildDataExchange(project.nextToken().trim().getBytes());
				SetOfStuSites studentsList = new SetOfStuSites();
				if (studentsList.analyseTokens(de, 0)) {
					studentsList.buildSetOfResources(de, 0);
				}
				diaData.add(studentsList);
			}// end if(tts.getError().length()==0)
			else {
				new FatalProblemDlg(
						"I was in"
								+ getClass().toString()
								+ " LoadData class and loadProject. extract failed, wrong time table structure!!!");
			}

		} else {
			new FatalProblemDlg("I was in" + getClass().toString()
					+ " LoadData class and loadProject. extract failed!!!");
			// System.exit(-1);
		}
		return diaData;

	}

	private Vector<Object> load2dot1(String fileName, String currentDir)
			throws DxException {
		DxSetOfInstructors dxsoiInst = null;
		DxSetOfSites dxsosRooms = null;
		Vector<Object> diaData = new Vector<Object>();
		String dataloaded = new String(preLoad(fileName));
		StringTokenizer project;

		// readFile = new StringTokenizer(dataloaded, DConst.CR_LF);

		DataExchange de;

		project = new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR_VIS);

		if (project.countTokens() == 6) { // 6 !!!!!!!!!!!!!!
			// extract version
			diaData.add(project.nextToken().trim());
			// extract ttStructure
			TTStructure tts = new TTStructure();

			de = buildDataExchange(project.nextToken().trim().getBytes());
			tts.loadTTSFromString(de.getContents());
			diaData.add(tts);

			// extract SetOfInstructor
			if (tts.getError().length() == 0) {
				de = buildDataExchange(project.nextToken().trim().getBytes());
				DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, tts
						.getNumberOfActiveDays(), tts.getCurrentCycle()
						.getMaxNumberOfPeriodsADay());
				dxsoiInst = dxir.readSetOfInstructors();
				diaData.add(dxir.readSetOfInstructors());
			}// end if(tts.getError().length()==0)

			// extract SetOfSites
			if (!DxFlags.newRooms) {
				SetOfSites roomsList = new SetOfSites();
				de = buildDataExchange(project.nextToken().trim().getBytes());
				if (roomsList.analyseTokens(de, 3)) {
					// roomsList.setAttributesInterpretor(_roomsAttributesInterpretor);
					roomsList.buildSetOfResources(de, 3);
				}
				diaData.add(roomsList);

			} else {
				de = buildDataExchange(project.nextToken().trim().getBytes());
				DxSiteReader dxrr = new DxReadSitedotDia(de, tts
						.getNumberOfActiveDays(), tts.getCurrentCycle()
						.getMaxNumberOfPeriodsADay());

				dxsosRooms = dxrr.readSetOfSites();
				diaData.add(dxsosRooms);
			}

			// extract SetOfActivities
			de = buildDataExchange(project.nextToken().trim().getBytes());
			if (DxFlags.newActivity) {
				DxActivitiesSitesReader dxasr = new DxReadActivitiesSites1dot5(
						de, dxsoiInst, dxsosRooms.getAllRooms(), tts
								.getPeriodLenght(), true);

				diaData.add(dxasr.readSetOfActivitiesSites());
			} else {
				SetOfActivitiesSites activitiesList = new SetOfActivitiesSites(
						true, tts.getPeriodLenght());
				if (activitiesList.analyseTokens(de, 1)) {
					activitiesList.buildSetOfResources(de, 1);
				}
				diaData.add(activitiesList);
			}

			// extract SetOfStudents
			de = buildDataExchange(project.nextToken().trim().getBytes());
			SetOfStuSites studentsList = new SetOfStuSites();
			if (studentsList.analyseTokens(de, 0)) {
				studentsList.buildSetOfResources(de, 0);
			}
			diaData.add(studentsList);

		} else {
			new FatalProblemDlg("I was in" + getClass().toString()
					+ " LoadData class and loadProject. extract failed!!!");
			// System.exit(-1);
		}
		return diaData;

	}

	private void doLoadData() {
		// _roomsAttributesInterpretor = new RoomsAttributesInterpretor();
		DxPreferences preferences = new DxPreferences(System
				.getProperty("user.home")
				+ File.separator + "pref" + File.separator + "pref.txt");
		_chars = preferences._acceptedChars;
	}

	// private void completeLoadData() {
	// String path = System.getProperty("user.dir") + File.separator + "pref"
	// + File.separator;
	// _functionFileName = path + "DXfunctions.sig";
	// _caractFileName = path + "DXcaracteristics.sig";
	// }

	public byte[] preLoad(String str) throws DxException { 
		FilterFile filter = new FilterFile();
		filter.setCharKnown("");
		filter.appendToCharKnown(_chars);
		filter.validFile(str);
		return filter.getByteArray();
	} 

	private void verifyImportDataFile(String str) throws DxException {
		FilterFile filter = new FilterFile(_chars);
		if (filter.validFile(str)) {
			StringTokenizer st = new StringTokenizer(new String(filter
					.getByteArray()), DConst.CR_LF);
			if (st.countTokens() == DConst.NUMBER_OF_FILES) {
				_instructorFileName = st.nextToken();
				_roomsFileName = st.nextToken();
				_activitiesFileName = st.nextToken();
				_studentsFileName = st.nextToken();
			} else {
				throw new DxException("Wrong number of lines in the file:");
			}
		} else {
			throw new DxException("Invalid file:"+str); 
		}
	}


	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws DxException 
	 */
	private DataExchange buildDataExchange(String fileName) throws DxException {
		byte[] dataloaded = preLoad(fileName);
		StringTokenizer st = new StringTokenizer(new String(dataloaded),
				DConst.CR_LF);
		String token = st.nextToken().toString().trim();
		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME_XML1_7)) {
			return new ByteArrayMsg(DConst.FILE_VER_NAME_XML1_7, fileName);
		}
		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			return new ByteArrayMsg(DConst.FILE_VER_NAME1_6, new String(
					dataloaded));
		}
		return new ByteArrayMsg(DConst.FILE_VER_NAME1_5, new String(dataloaded));
	}

	/**
	 * 
	 * @param dataloaded
	 * @return
	 */
	public DataExchange buildDataExchange(byte[] dataloaded) {
		// byte[] dataloaded = preLoad(fileName);
		StringTokenizer st = new StringTokenizer(new String(dataloaded),
				DConst.CR_LF);
		String token = st.nextToken().toString().trim();
		// if (token.equalsIgnoreCase(DConst.FILE_VER_NAME_XML1_7)) {
		// return new ByteArrayMsg(DConst.FILE_VER_NAME_XML1_7, fileName);
		// }
		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			return new ByteArrayMsg(DConst.FILE_VER_NAME1_6, new String(
					dataloaded));
		}
		return new ByteArrayMsg(DConst.FILE_VER_NAME1_5, new String(dataloaded));
	}

	/**
	 * 
	 * @param currentSetOfResc
	 *            the current DSetOfResources
	 * @param file
	 *            the file to import
	 * @return DSetOfResources which is merge of the new DSetOfResources to the
	 *         current DSetOfResources
	 */
	public DSetOfResources selectiveImport(DSetOfResources currentSetOfResc,
			String file) throws DxException {// , boolean merge){
		DataExchange de = buildDataExchange(file);
		DSetOfResources newSetOfResc = null;
		// int position=0;
		// TODO a revoir
		// if(false){;
		// if (currentSetOfResc instanceof
		// dInternal.dData.dInstructors.DxSetOfInstructors) {
		// _instructorFileName = file;
		// newSetOfResc = extractInstructors((DxSetOfInstructors) null, false,
		// false);
		// _dm.resizeResourceAvailability(newSetOfResc);
		// ((SetOfInstructors)currentSetOfResc).setDataToLoad(dataloaded,5,14);
		/* } else */
		if (currentSetOfResc instanceof dInternal.dData.dRooms.SetOfSites) {
			_dm.resizeSiteAvailability((SetOfSites) newSetOfResc);
		} else if (currentSetOfResc instanceof dInternal.dData.dStudents.SetOfStuSites) {
			_studentsFileName = file;
			newSetOfResc = extractStudents(null, false);
		} else if (currentSetOfResc instanceof dInternal.dData.dActivities.SetOfActivitiesSites) {
			_activitiesFileName = file;
			newSetOfResc = extractActivities(null, false);
			// position=1;
		} else {// (NullPointerException npe) {
			throw new DxException("Unknown resource type !!!");
		}

		if ((newSetOfResc != null) && (newSetOfResc.getError() == "")) {
			makeDiff(newSetOfResc, currentSetOfResc);
			// currentSetOfResc.buildSetOfResources(de, position);
			currentSetOfResc.sortSetOfResourcesByID();
		}// Ici sans le else on passe même s’il y a une erreur !!!!
		else
			throw new DxException(newSetOfResc.getError());

		return currentSetOfResc;
	}

	/**
	 * 
	 * @param currentRsc
	 * @param newRsc
	 * @return
	 */
	private void makeDiff(DSetOfResources newSites, DSetOfResources currentSites) {
		// System.err.println("...........MAKEDIFF..................\n");
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
	private void findDeletedElements(DSetOfResources newSites,
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
					// current ressource
					DResource curResc = rescCat.getResourceAt(k);
					if (getResource(newSites, curResc, rscSite, rscCat) == null) {
						DValue error = new DValue();
						error.setStringValue(DConst.DELETED_ELEMENT
								+ curResc.getID());
						if (_dm != null)
							_dm.getSetOfImportSelErrors().addResource(
									new DResource("1", error), 0);
						// System.out.println("DELETED_ELEMENT "+
						// curResc.getID());
						rescCat.removeResource(curResc.getID());
						k--; // Puisque la liste est trié Sinon c est k=0;
					}// end
					// if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
				}// end for k++
			}// end for j++

		}// end for i++

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
					// current ressource
					DResource newRes = rescCat.getResourceAt(k);
					if (getResource(currentSites, newRes, rscSite, rscCat) == null) {
						DValue error = new DValue();
						error.setStringValue(DConst.ADDED_ELEMENT
								+ newRes.getID());
						if (_dm != null)
							_dm.getSetOfImportSelErrors().addResource(
									new DResource("2", error), 0);
						// System.out.println("ADDED_ELEMENT "+ newRes.getID());
						DResource crescSite = currentSites.getResource(rscSite);
						if (crescSite != null) {
							DSetOfResources crescCat = ((DSetOfResources) crescSite
									.getAttach());
							crescCat.addResourceMod(newRes, 1);
						}
					}// end
					// if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
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
					DResource newRes = getResource(newSites, resc, rscSite,
							rscCat);
					if (newRes != null) {
						// Already exist does it change ?
						boolean changed = false;
						if (currentSites instanceof SetOfStuSites) {
							// Find if element change
							changed = compareStudents(newRes, resc);
						} // TODO a revoir
						// else if (currentSites instanceof SetOfInstructors) {
						// changed = compareInstructors(resc, newRes);
						// }
						DValue error = new DValue();
						if (changed == true) {
							error.setStringValue(DConst.CHANGED_ELEMENT
									+ newRes.getID());
							if (_dm != null)
								_dm.getSetOfImportSelErrors().addResource(
										new DResource("3", error), 0);
							// System.out.println("CHANGED_ELEMENT "+
							// newRes.getID());
						} else {
							error.setStringValue(DConst.UNCHANGED_ELEMENT
									+ newRes.getID());
							if (_dm != null)
								_dm.getSetOfImportSelErrors().addResource(
										new DResource("4", error), 0);
							// System.out.println("UNCHANGED_ELEMENT "+
							// newRes.getID());
						}
					}// end if !=null
				}// end for k++
			}// end for j++
		}// end for i++
	}

	/**
	 * Compare two students
	 * 
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
				// System.out.println("added "
				// + newCourses.getResourceAt(m).getID());// debug
				currentCourses.addResourceMod(newCourses.getResourceAt(m), 1);
			}
		} // end for

		// course deleted
		for (int k = 0; k < currentCourses.size(); k++) {
			// if(currentRsc.getResource(newRsc.getResourceAt(i).getKey())==null){
			if (newCourses.getIndexOfResource(currentCourses.getResourceAt(k)
					.getID()) == -1) {
				resChanged = true;
				// System.out.println("remove "
				// + currentCourses.getResourceAt(k).getID());// debug
				currentCourses.removeResourceAt(k);
			}
		}// end for
		return resChanged;
	}

	// /**
	// * Compare two Instructors
	// *
	// * @param newSites
	// * @param currentSites
	// * @return boolean resChanged
	// */
	// private boolean compareInstructors(DResource currentRes, DResource
	// newRes) {
	// boolean resChanged = false;
	// AvailabilityAttach newAtt = (AvailabilityAttach) newRes.getAttach();
	// AvailabilityAttach curAtt = (AvailabilityAttach) currentRes.getAttach();
	// if (!curAtt.isEquals(newAtt)) {
	// resChanged = true;
	// curAtt.setAvailability(newAtt.getMatrixAvailability());
	// currentRes.setAttach(curAtt);
	// System.out.println("Changed " + currentRes.getID());// debug
	// } else
	// System.out.println("UnChanged " + currentRes.getID());// debug
	//
	// return resChanged;
	// }// end for

	/**
	 * Compare two Courses
	 * 
	 * @param newSites
	 * @param currentSites
	 * @return boolean resChanged
	 */
	// private boolean compareCourses(DResource currentRes,DResource newRes) {
	// boolean resChanged = false;
	// Activity curAtt = (Activity) currentRes.getAttach();
	// System.err.println(newRes.getID()+"\n");
	// if (!curAtt.isEquals(newRes.getAttach())){
	// resChanged = true;
	// // curAtt.setAvailability(newAtt.getMatrixAvailability());
	// // currentRes.setAttach(curAtt);
	// System.out.println("Changed " + currentRes.getID());// debug
	// }else
	// System.out.println("UnChanged " + currentRes.getID());// debug
	//            
	// return resChanged;
	// }// end for
	// /**
	// *
	// * @param newSites
	// * @param currentSites
	// */
	// private void findDeletedElement(DSetOfResources newSites, DSetOfResources
	// currentSites){
	// // find a site
	// int siteSize = getSiteSize(currentSites);
	// for(int i=0; i< siteSize; i++){
	// String rscSite = getSite(currentSites, i);
	// DSetOfResources rescSite = getRscSite(currentSites, i);
	// int catSize = getCategorySize(rescSite);
	// // find category in site
	// for(int j = 0; j < catSize; j++ ){
	// String rscCat = getCategory(rescSite, j);
	// DSetOfResources rescCat = getRscCategory(rescSite, j);
	// //find resource in a category
	// for (int k=0; k< rescCat.size(); k++){
	// //if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
	// if(getResource(newSites,rescCat.getResourceAt(k), rscSite,
	// rscCat)==null){
	// DValue error= new DValue();
	// error.setStringValue(DConst.DELETED_ELEMENT +
	// rescCat.getResourceAt(k).getID());
	// _dm.getSetOfImportSelErrors().addResource(new DResource("1",error),0);
	// }// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	// }// end for (int k=0; k< currentSites.size(); k++)
	// }
	// }// end for (int i=0; i< currentRsc.size(); i++)
	// }
	//	
	// /**
	// *
	// * @param newSites
	// * @param currentSites
	// */
	// private void findAddedElement(DSetOfResources newSites, DSetOfResources
	// currentSites){
	// // find a site
	// int siteSize = getSiteSize(newSites);
	// for(int i=0; i< siteSize; i++){
	// String rscSite = getSite(newSites, i);
	// DSetOfResources rescSite = getRscSite(newSites, i);
	// int catSize = getCategorySize(rescSite);
	// // find category in site
	// for(int j = 0; j < catSize; j++ ){
	// String rscCat = getCategory(rescSite, j);
	// DSetOfResources rescCat = getRscCategory(rescSite, j);
	// //find resource in a category
	// for (int k=0; k< rescCat.size(); k++){
	// if(getResource(currentSites,rescCat.getResourceAt(k), rscSite,
	// rscCat)==null){
	// DValue error= new DValue();
	// error.setStringValue(DConst.ADDED_ELEMENT +
	// rescCat.getResourceAt(k).getID());
	// _dm.getSetOfImportSelErrors().addResource(new DResource("2",error),0);
	// }// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	// }// end for (int k=0; k< currentSites.size(); k++)
	// }
	// }// end for (int i=0; i< currentRsc.size(); i++)
	// }
	//	
	// /**
	// *
	// * @param newSites
	// * @param currentSites
	// */
	// private void findChangedElement(DSetOfResources newSites, DSetOfResources
	// currentSites){
	// // find a site
	// int siteSize = getSiteSize(currentSites);
	// for(int i=0; i< siteSize; i++){
	// String rscSite = getSite(currentSites, i);
	// DSetOfResources rescSite = getRscSite(currentSites, i);
	// int catSize = getCategorySize(rescSite);
	// // find category in site
	// for(int j = 0; j < catSize; j++ ){
	// String rscCat = getCategory(rescSite, j);
	// DSetOfResources rescCat = getRscCategory(rescSite, j);
	// //find resource in a category
	// for (int k=0; k< rescCat.size(); k++){
	// //if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
	// DResource r = getResource(newSites,rescCat.getResourceAt(k), rscSite,
	// rscCat);
	// if(r!=null)
	// if(!r.getAttach().isEquals(rescCat.getResourceAt(k).getAttach())){
	// DValue error= new DValue();
	// error.setStringValue(DConst.CHANGED_ELEMENT +
	// rescCat.getResourceAt(k).getID());
	// _dm.getSetOfImportSelErrors().addResource(new DResource("3",error),0);
	// }// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	// }// end for (int k=0; k< currentSites.size(); k++)
	// }
	// }// end for (int i=0; i< currentRsc.size(); i++)
	// }
	//	
	// /**
	// *
	// * @param newSites
	// * @param currentSites
	// */
	// private void findUnChangedElement(DSetOfResources newSites,
	// DSetOfResources currentSites){
	// // find a site
	// int siteSize = getSiteSize(newSites);
	// for(int i=0; i< siteSize; i++){
	// String rscSite = getSite(newSites, i);
	// DSetOfResources rescSite = getRscSite(newSites, i);
	// int catSize = getCategorySize(rescSite);
	// // find category in site
	// for(int j = 0; j < catSize; j++ ){
	// String rscCat = getCategory(rescSite, j);
	// DSetOfResources rescCat = getRscCategory(rescSite, j);
	// //find resource in a category
	// for (int k=0; k< rescCat.size(); k++){
	// //if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
	// DResource r = getResource(currentSites,rescCat.getResourceAt(k), rscSite,
	// rscCat);
	// if(r!=null)
	// if(!r.getAttach().isEquals(rescCat.getResourceAt(k).getAttach())){
	// DValue error= new DValue();
	// error.setStringValue(DConst.UNCHANGED_ELEMENT +
	// rescCat.getResourceAt(k).getID());
	// _dm.getSetOfImportSelErrors().addResource(new DResource("4",error),0);
	// }// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	// }// end for (int k=0; k< currentSites.size(); k++)
	// }
	// }// end for (int i=0; i< currentRsc.size(); i++)
	// }
	//	
	//	
	// /**
	// *
	// * @param currentRsc
	// * @param newRsc
	// * @return
	// */
	// private DSetOfResources makeDiff(DSetOfResources newSites,
	// DSetOfResources currentSites){
	// //find deleted element
	// findDeletedElement(newSites,currentSites);
	//		
	// // find added element
	// findAddedElement(newSites,currentSites);
	//		
	// // find changed element
	// findChangedElement(newSites,currentSites);
	//		
	// // find unchanged element
	// findUnChangedElement(newSites,currentSites);
	//		
	// return null;
	// }
	//	
	// /**
	// *
	// * @param currentRsc
	// * @param newRsc
	// * @return
	// */
	// private DSetOfResources updateSetOfStudents (DSetOfResources
	// currentSites, DSetOfResources newSites){
	// // find a site
	// int siteSize = getSiteSize(currentSites);
	// for(int i=0; i< siteSize; i++){
	// String rscSite = getSite(currentSites, i);
	// DSetOfResources rescSite = getRscSite(currentSites, i);
	// int stuSize = rescSite.size();
	// int inc=0;
	// //find deleted element
	// for (int j=0; j< stuSize; j++){
	// //if(newRsc.getResource(currentRsc.getResourceAt(i).getKey())==null){
	// DResource r = getResource(newSites,rescSite.getResourceAt(inc),
	// rscSite,"");
	// if(r==null){
	// rescSite.removeResourceAt(inc);
	// }else// end
	// if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	// inc++;
	// }// end for (int i=0; i< currentRsc.size(); i++)
	//			
	//			
	// // find changed element
	// for (int j=0; j< rescSite.size(); j++){
	// //Resource r = newRsc.getResource(currentRsc.getResourceAt(i).getKey());
	// DResource r = getResource(newSites,rescSite.getResourceAt(j),
	// rscSite,"");
	// if(r!=null){
	// if(!r.getAttach().isEquals(rescSite.getResourceAt(j).getAttach())) {
	// Student currentStudent = (Student) rescSite.getResourceAt(j);
	// SetOfStuCourses currentCourses =currentStudent.getCoursesList();
	// Student newStudent = (Student) r;
	// SetOfStuCourses newCourses =newStudent.getCoursesList();
	// // course deleted
	// for (int k=0; k< currentCourses.size(); k++){
	// //if(currentRsc.getResource(newRsc.getResourceAt(i).getKey())==null){
	// if(getResource(newCourses,currentCourses.getResourceAt(k),
	// rscSite,"")==null){
	// currentCourses.removeResourceAt(k);
	// }
	// }
	// //course added
	// for (int k=0; k< newCourses.size(); k++){
	// //if(currentRsc.getResource(newRsc.getResourceAt(i).getKey())==null){
	// if(getResource(currentCourses,newCourses.getResourceAt(k),
	// rscSite,"")==null){
	// currentCourses.addResource(newCourses.getResourceAt(k),1);
	// }
	// }
	// }
	// }// end if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
	// }// end for (int i=0; i< currentRsc.size(); i++)
	// }
	//		
	// return null;
	//		
	// }
	//	
	/**
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	/*
	 * private DResource getResource(DSetOfResources source, DResource target){
	 * if(source instanceof SetOfStuSites){ return
	 * source.getResource(target.getKey()); } return
	 * source.getResource(target.getID()); }
	 */

	/**
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	private DResource getResource(DSetOfResources source, DResource target,
			String site, String cat) {
		// String str= source.getClass().getName();
		// TODO make getResource for each site to search the resource
		// TODO a revoir
		// if (source instanceof dInternal.dData.dInstructors.SetOfInstructors)
		// {
		// return source.getResource(target.getID());
		// }
		DResource rescSite = source.getResource(site);
		if (source instanceof SetOfSites) {
			if (rescSite != null) {
				DResource rescCat = ((DSetOfResources) rescSite.getAttach())
						.getResource(cat);
				if (rescCat != null)
					return ((DSetOfResources) rescCat.getAttach())
							.getResource(target.getID());
			}
		}

		// TODO Make getResource for DxSetOfActivitiesSites
		if (source instanceof SetOfActivitiesSites) {
			if (rescSite != null)
				return ((DSetOfResources) rescSite.getAttach())
						.getResource(target.getID());
		}
		if (source instanceof SetOfStuSites) {
			if (rescSite != null)
				return ((DSetOfResources) rescSite.getAttach())
						.getResource(target.getID());
		}

		return null;
	}

	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private String getSite(DSetOfResources sourceSites, int index) {
		// TODO a revoir
		// if (sourceSites instanceof
		// dInternal.dData.dInstructors.SetOfInstructors) {
		// return DConst.ROOM_STANDARD_SITE;
		// }
		DResource rsc = sourceSites.getResourceAt(index);
		if (rsc != null)
			return rsc.getID();
		return null;

	}

	/**
	 * 
	 * @param sourceSites
	 * @return
	 */
	private int getSiteSize(DSetOfResources sourceSites) {
		// TODO a revoir
		// if (sourceSites instanceof
		// dInternal.dData.dInstructors.SetOfInstructors) {
		// return 1;
		// }
		return sourceSites.size();

	}

	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private DSetOfResources getRscSite(DSetOfResources sourceSites, int index) {
		// TODO a revoir
		// if (sourceSites instanceof
		// dInternal.dData.dInstructors.SetOfInstructors) {
		// return sourceSites;
		// }
		DResource rsc = sourceSites.getResourceAt(index);
		if (rsc != null)
			return (DSetOfResources) rsc.getAttach();
		return null;

	}

	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private String getCategory(DSetOfResources sourceCategories, int index) {
		if (sourceCategories instanceof SetOfCategories) {
			DResource rsc = sourceCategories.getResourceAt(index);
			if (rsc != null)
				return rsc.getID();
			return null;
		}

		return DConst.ROOM_STANDARD_CAT;
	}

	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private int getCategorySize(DSetOfResources sourceCategories) {
		if (sourceCategories instanceof SetOfCategories) {
			return sourceCategories.size();
		}

		return 1;
	}

	/**
	 * 
	 * @param sourceSites
	 * @param index
	 * @return
	 */
	private DSetOfResources getRscCategory(DSetOfResources sourceCategories,
			int index) {
		if (sourceCategories instanceof SetOfRooms) {
			DResource rsc = sourceCategories.getResourceAt(index);
			if (rsc != null)
				return (DSetOfResources) rsc.getAttach();
			return null;
		}

		return sourceCategories;
	}

	public DxSetOfInstructors extractInstructors() throws DxException {
		DataExchange de = buildDataExchange(_instructorFileName);
		// hara2602 ! TODO params 4 et 14 Trop dangeureux
		DxInstructorsReader dxir = new DxReadInstructors1dot5(de, 5, 14);// 5 jours  et 14
		return dxir.readSetOfInstructors(); // 
	
	}

	public DxSetOfSites extractDxRooms()throws DxException {
		DataExchange de = buildDataExchange(_roomsFileName);
		DxSiteReader dxsrReader;
		if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			dxsrReader = new DxReadSite1dot6(de);
		} else {
			dxsrReader = new DxReadSite1dot5(de);
		}

		return dxsrReader.readSetOfSites();
	}// end extractDxRooms

	public DxSetOfActivitiesSites extractDxActivity(
			DxSetOfInstructors dxsoiInst, DxSetOfRooms dxsorRooms, int nPerLen)
			throws DxException {
		DataExchange de = buildDataExchange(_activitiesFileName);
		DxActivitiesSitesReader dxasrReader;
		if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			dxasrReader = new DxReadActivitiesSites1dot6(de, dxsoiInst,
					dxsorRooms, nPerLen, false);
		} else {
			dxasrReader = new DxReadActivitiesSites1dot5(de, dxsoiInst,
					dxsorRooms, nPerLen, false);
		}

		return dxasrReader.readSetOfActivitiesSites();
	}
}