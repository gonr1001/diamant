/**
 * Created on 29-Nov-07
 * 
 * 
 * Title: DxLoadData.java
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
 * 
 * 
 */

package dInternal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dExceptions.DiaException;
import dInternal.DModel;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dData.dActivities.DxActivitiesSitesReader;
import dInternal.dData.dActivities.DxReadActivitiesSites1dot5;
import dInternal.dData.dActivities.SetOfActivitiesInSites;
import dInternal.dData.dInstructors.DxInstructorsReader;
import dInternal.dData.dInstructors.DxReadInstructors1dot5;
import dInternal.dData.dInstructors.DxReadInstructorsdotDia;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxReadSite1dot5;
import dInternal.dData.dRooms.DxReadSitedotDia;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSiteReader;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dStudents.SetOfStuCourses;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.Student;
import dInternal.dTimeTable.TTStructure;
import developer.DxFlags;
import eLib.exit.txt.ByteInputFile;
import eLib.exit.txt.SemiExtendedAsciiFile;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxLoadData is a class used to:
 * <p>
 * Load the data from a a file to classes
 * <p>
 * 
 */
public class DxLoadData {

	private final int OTHER_LINES = 4;

	private String _instructorFileName;

	private String _roomsFileName;

	private String _activitiesFileName;

	private String _studentsFileName;

	private DModel _dm;

	private String _inDiaFileVersion;

	private TTStructure _tts;

	private DxSetOfInstructors _dxSoInst;

	private DxSetOfSites _dxSoSRooms;

	private DxActivitiesSitesReader _dxasr;

	private SetOfActivitiesInSites _activitiesList;

	private SetOfStuSites _studentsList;

	private String inDiaFileInstructors;

	private String inDiaFileActivities;

	private String inDiaFileRooms;

	private String inDiaFileStudents;

	Vector<Object> _diaData;

	public boolean loadDataStructures(String fileName, String currentDir)
			throws NullPointerException, FileNotFoundException, IOException,
			DiaException {

		System.out.println("start DxLoadData.loadDataStructures");

		String dataloaded = new String(filterBadChars(fileName));
		StringTokenizer readFile;
		readFile = new StringTokenizer(dataloaded, DConst.CR_LF);

		String head = readFile.nextToken().trim();
		if (head.equalsIgnoreCase(DConst.FILE_HEADER_NAME1_5)
				|| head.equalsIgnoreCase(DConst.FILE_HEADER_NAME1_6)) {
			return loadData(fileName, currentDir);
		} else if (head.equalsIgnoreCase(DConst.FILE_HEADER_NAME2_1)) {
			System.out
					.println("before DxLoadData.loadDataStructures call loadData2dot1");
			return loadData2dot1(fileName);
		} else {
			throw new DiaException("Invalid FILE_HEADER_NAME !");
		}
	}

	private boolean loadData(String fileName, String currentDir)
			throws NullPointerException, FileNotFoundException, IOException,
			DiaException {

		String dataloaded = new String(filterBadChars(fileName));
		StringTokenizer dataTokens;
		int linePosition = 0;
		DataExchange de;

		dataTokens = new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR);

		if (dataTokens.countTokens() == DConst.SAVE_SEPARATOR_COUNT) { // =================================
			// extract version
			_inDiaFileVersion = dataTokens.nextToken().trim();
			linePosition += ByteInputFile.countLines(_inDiaFileVersion);

			linePosition++; // for separator =========================
			// extract ttStructure
			_tts = new TTStructure();
			String inDiaTTSFileName = getAbsoluteFileName(currentDir,
					dataTokens.nextToken().trim());
			linePosition++;// for XML file name line
			// _tts.loadTTSFromFile(inDiaTTSFileName);

			InputStream is = new FileInputStream(inDiaTTSFileName);
			try {
				_tts.loadTTStructureFromInpuStream(is);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			linePosition++; // for separator =========================
			inDiaFileInstructors = dataTokens.nextToken().trim();
			de = insertHeader(inDiaFileInstructors.getBytes());
			DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, _tts
					.getNumberOfActiveDays(), _tts.getCurrentCycle()
					.getMaxNumberOfPeriodsADay(), linePosition);
			_dxSoInst = dxir.readSetOfInstructors();

			linePosition += ByteInputFile.countLines(inDiaFileInstructors);
			linePosition++; // for separator =========================
			// extract SetOfSites
			inDiaFileRooms = dataTokens.nextToken().trim();
			de = insertHeader(inDiaFileRooms.getBytes());
			// if (DxFlags.newRooms) {
			DxSiteReader dxrr = new DxReadSitedotDia(de, _tts
					.getNumberOfActiveDays(), _tts.getCurrentCycle()
					.getMaxNumberOfPeriodsADay(), linePosition);
			_dxSoSRooms = dxrr.readSetOfSites();
			linePosition += ByteInputFile.countLines(inDiaFileRooms);
			linePosition++; // for separator =========================
			// extract SetOfActivities
			inDiaFileActivities = dataTokens.nextToken().trim();
			de = insertHeader(inDiaFileActivities.getBytes());
			if (DxFlags.newActivity) {
				DxActivitiesSitesReader dxasr = new DxReadActivitiesSites1dot5(
						de, _dxSoInst, _dxSoSRooms.getAllDxRooms(), _tts
								.getPeriodLenght(), true);
			} else {
				_activitiesList = new SetOfActivitiesInSites(true, _tts
						.getPeriodLenght());
				try {
					if (_activitiesList.analyseTokens(de, 1)) {
						_activitiesList.buildSetOfResources(de, 1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			linePosition += ByteInputFile.countLines(inDiaFileActivities);
			linePosition++; // for separator =========================
			// extract SetOfStudents
			inDiaFileStudents = dataTokens.nextToken().trim();
			linePosition += ByteInputFile.countLines(inDiaFileStudents);
			de = insertHeader(inDiaFileStudents.getBytes());
			_studentsList = new SetOfStuSites();
			if (_studentsList.analyseTokens(de, 0)) {
				_studentsList.buildSetOfResources(de, 0);
			}
		} else {
			throw new DiaException(DConst.PARTS_IN_DIA_SEPARATED_BY
					+ DConst.CR_LF + DConst.SAVE_SEPARATOR);
		}
		// if we arrive here there is no exception
		// so true can be used as a constant
		return true;
	}

	/**
	 * give the relative path of a file Exemple: input of a relative path is:
	 * ete04.dia the operation return:
	 * c:\developpement\DiaExtreme\DX\data\fgen\ete04.dia where
	 * c:\developpement\DiaExtreme\DX\data\fgen\ is the absolute path
	 * 
	 * @param str
	 * @return
	 */
	private String getAbsoluteFileName(String absolutePath, String str) {
		return absolutePath
				+ File.separator
				+ str.substring(str.lastIndexOf(File.separator) + 1, str
						.length());
	} // end getRelativeFileName

	private boolean loadData2dot1(String fileName) throws NullPointerException,
			FileNotFoundException, IOException, DiaException {

		System.out.println("start DxLoadData.loadData2dot1");
		if (DxFlags.newExceptionsReading) {
			loadData2dot1WithExceptions(fileName);
			System.out.println("end by DxLoadData.loadData2dot1Exceptions");
			return true;
		} else {
			System.out.println("end by DxLoadData.loadData2dot1Old");
			return loadData2dot1Old(fileName);
		}
	}

	private boolean loadData2dot1Old(String fileName)
			throws NullPointerException, FileNotFoundException, IOException,
			DiaException {

		System.out.println("start DxLoadData.loadData2dot1");
		String dataloaded = new String(filterBadChars(fileName));
		StringTokenizer dataTokens;

		DataExchange de;

		dataTokens = new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR_VIS);

		if (dataTokens.countTokens() == DConst.SAVE_SEPARATOR_COUNT) { // 6
			// !!!!!!!!!!!!!!
			// extract version
			_inDiaFileVersion = dataTokens.nextToken().trim();

			_tts = new TTStructure();

			de = insertHeader(dataTokens.nextToken().trim().getBytes());
			_tts.loadTTSFromString(de.getContents());

			// extract SetOfInstructor
			if (_tts.getError().length() == 0) {
				de = insertHeader(dataTokens.nextToken().trim().getBytes());
				DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, _tts
						.getNumberOfActiveDays(), _tts.getCurrentCycle()
						.getMaxNumberOfPeriodsADay());
				_dxSoInst = dxir.readSetOfInstructors();
			}// end if

			// extract SetOfSites
			de = insertHeader(dataTokens.nextToken().trim().getBytes());
			DxSiteReader dxrr = new DxReadSitedotDia(de, _tts
					.getNumberOfActiveDays(), _tts.getCurrentCycle()
					.getMaxNumberOfPeriodsADay());

			_dxSoSRooms = dxrr.readSetOfSites();
			// }

			// extract SetOfActivities
			de = insertHeader(dataTokens.nextToken().trim().getBytes());
			if (DxFlags.newActivity) {
				_dxasr = new DxReadActivitiesSites1dot5(de, _dxSoInst,
						_dxSoSRooms.getAllDxRooms(), _tts.getPeriodLenght(),
						true);

			} else {
				boolean isOpen = true;
				_activitiesList = new SetOfActivitiesInSites(isOpen, _tts
						.getPeriodLenght());
				try {
					if (_activitiesList.analyseTokens(de, 1)) {
						_activitiesList.buildSetOfResources(de, 1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// extract SetOfStudents
			de = insertHeader(dataTokens.nextToken().trim().getBytes());
			_studentsList = new SetOfStuSites();
			if (_studentsList.analyseTokens(de, 0)) {
				_studentsList.buildSetOfResources(de, 0);
			}

		} else {
			new DiaException(DConst.PARTS_IN_DIA_SEPARATED_BY + DConst.CR_LF
					+ DConst.SAVE_SEPARATOR_VIS);
			// System.exit(-1);
		}
		// if we arrive here there is no exception
		// so true can be used as a constant
		System.out.println("end DxLoadData.loadData2dot1");
		return true;

	}

	private void loadData2dot1WithExceptions(String fileName)
			throws NullPointerException, FileNotFoundException, IOException,
			DiaException {

		System.out.println("start DxLoadData.loadData2dot1WithExceptions");
		String dataloaded = new String(filterBadChars(fileName));

		StringTokenizer dataTokens = new StringTokenizer(dataloaded,
				DConst.SAVE_SEPARATOR_VIS);
		DataExchange de;

		if (dataTokens.countTokens() == DConst.SAVE_SEPARATOR_COUNT) { // 6
			// !!!!!!!!!!!!!!
			// extract version
			_inDiaFileVersion = dataTokens.nextToken().trim();

			_tts = new TTStructure();

			de = insertHeader(dataTokens.nextToken().trim().getBytes());
			int lines = de.countLines();
			lines += OTHER_LINES;
			_tts.loadTTSFromString(de.getContents());

			// extract SetOfInstructor
			// if (_tts.getError().length() == 0) {
			de = insertHeader(dataTokens.nextToken().trim().getBytes());
			DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, _tts
					.getNumberOfActiveDays(), _tts.getCurrentCycle()
					.getMaxNumberOfPeriodsADay(), lines);
			_dxSoInst = dxir.readSetOfInstructors();
			lines = dxir.getLines();
			// }// end if

			// extract SetOfSites
			de = insertHeader(dataTokens.nextToken().trim().getBytes());
			DxSiteReader dxrr = new DxReadSitedotDia(de, _tts
					.getNumberOfActiveDays(), _tts.getCurrentCycle()
					.getMaxNumberOfPeriodsADay(), lines);

			_dxSoSRooms = dxrr.readSetOfSites();
			lines = dxrr.getLines();

			// }

			// extract SetOfActivities
			de = insertHeader(dataTokens.nextToken().trim().getBytes());
			if (DxFlags.newActivity) {
				_dxasr = new DxReadActivitiesSites1dot5(de, _dxSoInst,
						_dxSoSRooms.getAllDxRooms(), _tts.getPeriodLenght(),
						true);

			} else {
				boolean isOpen = true;
				_activitiesList = new SetOfActivitiesInSites(isOpen, _tts
						.getPeriodLenght());
				_activitiesList.readSetOfActivities(de, 1);
			}

			// extract SetOfStudents
			de = insertHeader(dataTokens.nextToken().trim().getBytes());
			_studentsList = new SetOfStuSites();
			if (_studentsList.analyseTokens(de, 0)) {
				_studentsList.buildSetOfResources(de, 0);
			}

		} else {
			new DiaException(DConst.PARTS_IN_DIA_SEPARATED_BY + DConst.CR_LF
					+ DConst.SAVE_SEPARATOR_VIS);
			// System.exit(-1);
		}
		// if we arrive here there is no exception
		// so true can be used as a constant
		System.out.println("end lDxLoadData.loadData2dot1WithExceptions");

	}

	// /**
	// *
	// * @param fileName
	// * @return
	// * @throws DiaException
	// */
	// private DataExchange buildDataExchange(String fileName) throws Exception
	// {
	// byte[] dataloaded = filterBadChars(fileName);
	// StringTokenizer st = new StringTokenizer(new String(dataloaded),
	// DConst.CR_LF);
	// String token = st.nextToken().toString().trim();
	//
	// if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
	// return new ByteArrayMsg(DConst.FILE_VER_NAME1_6, new String(
	// dataloaded));
	// }
	// if (token.equalsIgnoreCase(DConst.FILE_VER_NAME_XML1_7)) {
	// return new ByteArrayMsg(DConst.FILE_VER_NAME_XML1_7, fileName);
	// }
	// return new ByteArrayMsg(DConst.FILE_VER_NAME1_5, new String(dataloaded));
	// }

	/**
	 * 
	 * @param dataloaded
	 * @return
	 */
	public DataExchange insertHeader(byte[] dataloaded) {
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

	public String getVersion() {
		return _inDiaFileVersion;
	}

	public TTStructure getTTStructure() {
		return _tts;
	}

	public DxActivitiesSitesReader getDxActivitiesSitesReader() {
		return _dxasr;
	}

	public SetOfActivitiesInSites getSetOfActivitiesSites() {
		return _activitiesList;
	}

	public SetOfStuSites getSetofStuSites() {
		return _studentsList;
	}

	public DxSetOfInstructors getDxSetOfInstructors() {
		return _dxSoInst;
	}

	public DxSetOfSites getDxSetOfSitesRooms() {
		return _dxSoSRooms;
	}

	/**
	 * 
	 * @param currentSetOfResc
	 *            the current DSetOfResources
	 * @param file
	 *            the file to import
	 * @return DSetOfResources which is merge of the new DSetOfResources to the
	 *         current DSetOfResources
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	public DSetOfResources selectiveImport(DSetOfResources currentSetOfResc,
			String file) throws DiaException, NullPointerException,
			FileNotFoundException, IOException {// , boolean merge){

		DSetOfResources newSetOfResc = null;
		// int position=0;
		// TODO a revoir
		// if(false){;
		// if (currentSetOfResc instanceof
		// dInternal.dData.dInstructors.DxSetOfInstructors) {
		// _instructorFileName = file;
		/* } else */
		if (currentSetOfResc instanceof dInternal.dData.dRooms.SetOfSites) {
			_dm.resizeSiteAvailability((SetOfSites) newSetOfResc);
		} else if (currentSetOfResc instanceof dInternal.dData.dStudents.SetOfStuSites) {
			_studentsFileName = file;
			newSetOfResc = extractStudents(null, false);
		} else if (currentSetOfResc instanceof dInternal.dData.dActivities.SetOfActivitiesInSites) {
			_activitiesFileName = file;
			newSetOfResc = extractActivities(null, false);
		} else {// (NullPointerException npe) {
			throw new DiaException("Unknown resource type !!!");
		}

		if ((newSetOfResc != null) && (newSetOfResc.getError() == "")) {
			makeDiff(newSetOfResc, currentSetOfResc);
			currentSetOfResc.sortSetOfResourcesByID();
		}// Ici sans le else on passe m�me s�il y a une erreur !!!!
		else {
			if (newSetOfResc != null)
				throw new DiaException(newSetOfResc.getError());
		}
		return currentSetOfResc;
	}

	public DxSetOfInstructors extractInstructors() throws DiaException,
			NullPointerException, FileNotFoundException, IOException {
		DataExchange de = buildDataExchange(_instructorFileName);
		// hara2602 ! TODO params 4 et 14 Trop dangeureux
		DxInstructorsReader dxir = new DxReadInstructors1dot5(de, 5, 14);// 5
		// jours
		// et
		// 14
		return dxir.readSetOfInstructors(); // 

	}

	public DxSetOfSites extractDxRooms() throws DiaException,
			NullPointerException, FileNotFoundException, IOException {
		DataExchange de = buildDataExchange(_roomsFileName);
		DxSiteReader dxsrReader;
		TTStructure tts = _dm.getTTStructure();
		if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			dxsrReader = new DxReadSitedotDia(de, tts.getNumberOfActiveDays(),
					tts.getCurrentCycle().getMaxNumberOfPeriodsADay());
		} else {
			dxsrReader = new DxReadSite1dot5(de);
		}

		return dxsrReader.readSetOfSites();
	}// end extractDxRooms

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
	 * @throws DiaException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	public SetOfStuSites extractStudents(SetOfStuSites currentList,
			boolean merge) throws DiaException, NullPointerException,
			FileNotFoundException, IOException {
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
			new DiaException(DConst.FILE_PRELOAD_FAILED);
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
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	public SetOfActivitiesInSites extractActivities(
			SetOfActivitiesInSites currentList, boolean merge)
			throws DiaException, NullPointerException, FileNotFoundException,
			IOException {
		DataExchange de = buildDataExchange(_activitiesFileName);
		SetOfActivitiesInSites activitiesList = new SetOfActivitiesInSites(
				false, _dm.getTTStructure().getPeriodLenght());
		if (de.getContents() != null) {
			// Vector setOfResources = currentList
			// .getSetOfResources();
			if (merge)

				activitiesList.setSetOfResources(currentList
						.getSetOfResources());
			try {
				if (activitiesList.analyseTokens(de, 1)) {
					activitiesList.buildSetOfResources(de, 1);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {// (NullPointerException npe) {
			throw new DiaException("NullPointerException: Preload failed!!!");
		}
		return activitiesList;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws DiaException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	private DataExchange buildDataExchange(String fileName)
			throws DiaException, NullPointerException, FileNotFoundException,
			IOException {
		byte[] dataloaded = filterBadChars(fileName);
		StringTokenizer st = new StringTokenizer(new String(dataloaded),
				DConst.CR_LF);
		String token = st.nextToken().toString().trim();

		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			return new ByteArrayMsg(DConst.FILE_VER_NAME1_6, new String(
					dataloaded));
		}
		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME_XML1_7)) {
			return new ByteArrayMsg(DConst.FILE_VER_NAME_XML1_7, fileName);
		}
		return new ByteArrayMsg(DConst.FILE_VER_NAME1_5, new String(dataloaded));
	}

	/**
	 * 
	 * @param currentRsc
	 * @param newRsc
	 * @return
	 */
	private void makeDiff(DSetOfResources newSites, DSetOfResources currentSites) {

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
			int catSize = 111111; // getCategorySize(rescSite);
			// find category in site
			for (int j = 0; j < catSize; j++) {
				String rscCat = DConst.ROOM_STANDARD_CAT;// getCategory(rescSite,
				// j);
				DSetOfResources rescCat = rescSite; // getRscCategory(rescSite,
				// j);
				// find resource in a category
				for (int k = 0; k < rescCat.size(); k++) {
					// current resource
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
						k--; // Puisque la liste est tri� Sinon c est k=0;
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
			int catSize = 111111; // getCategorySize(rescSite);
			// find category in site
			for (int j = 0; j < catSize; j++) {
				String rscCat = DConst.ROOM_STANDARD_CAT; // getCategory(rescSite,
				// j);
				DSetOfResources rescCat = rescSite; // getRscCategory(rescSite,
				// j);
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
			int catSize = 111111; // getCategorySize(rescSite);
			// find category in site
			for (int j = 0; j < catSize; j++) {
				String rscCat = DConst.ROOM_STANDARD_CAT;// getCategory(rescSite,
				// j);
				DSetOfResources rescCat = rescSite; // getRscCategory(rescSite,
				// j);
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
		if (source instanceof SetOfActivitiesInSites) {
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

	public byte[] filterBadChars(String str) throws NullPointerException,
			FileNotFoundException, IOException, DiaException {
		SemiExtendedAsciiFile filter = new SemiExtendedAsciiFile();
		filter.validFile(str);
		return filter.getByteArray();
	}

} // end DxLoadData
