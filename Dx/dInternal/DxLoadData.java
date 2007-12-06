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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInternal.DModel;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dData.dActivities.DxActivitiesSitesReader;
import dInternal.dData.dActivities.DxReadActivitiesSites1dot5;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dInstructors.DxInstructorsReader;
import dInternal.dData.dInstructors.DxReadInstructors1dot5;
import dInternal.dData.dInstructors.DxReadInstructorsdotDia;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxReadSite1dot5;
import dInternal.dData.dRooms.DxReadSitedotDia;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSiteReader;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DXToolsMethods;
import developer.DxFlags;
import eLib.exit.exception.DxException;
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

	private SetOfActivitiesSites _activitiesList;

	private SetOfStuSites _studentsList;

	private String inDiaFileInstructors;

	private String inDiaFileActivities;

	private String inDiaFileRooms;

	private String inDiaFileStudents;

	Vector<Object> _diaData;


	public boolean loadDataStructures(String fileName, String currentDir)
			throws NullPointerException, FileNotFoundException, IOException,
			DxException {

		String dataloaded = new String(filterBadChars(fileName));
		StringTokenizer readFile;
		readFile = new StringTokenizer(dataloaded, DConst.CR_LF);

		String head = readFile.nextToken().trim();
		if (head.equalsIgnoreCase(DConst.FILE_HEADER_NAME1_5)
				|| head.equalsIgnoreCase(DConst.FILE_HEADER_NAME1_6)) {
			return loadData(fileName, currentDir);
		} else if (head.equalsIgnoreCase(DConst.FILE_HEADER_NAME2_1)) {
			return loadData2dot1(fileName);
		} else {
			throw new DxException("Invalid FILE_HEADER_NAME !");
		}
	}

	private boolean loadData(String fileName, String currentDir)
			throws NullPointerException, FileNotFoundException, IOException,
			DxException {

		String dataloaded = new String(filterBadChars(fileName));
		StringTokenizer dataTokens;
		long linePosition = 0;
		DataExchange de;

		dataTokens = new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR);

		if (dataTokens.countTokens() == DConst.SAVE_SEPARATOR_COUNT) { // =================================
			// extract version
			_inDiaFileVersion = dataTokens.nextToken().trim();
			linePosition += ByteInputFile.countLines(_inDiaFileVersion);

			linePosition++; // for separator =========================
			// extract ttStructure
			_tts = new TTStructure();
			String inDiaTTSFileName = DXToolsMethods.getAbsoluteFileName(
					currentDir, dataTokens.nextToken().trim());
			linePosition++;// for XML file name line
			_tts.loadTTSFromFile(inDiaTTSFileName);

			linePosition++; // for separator =========================
			inDiaFileInstructors = dataTokens.nextToken().trim();
			de = buildDataExchange(inDiaFileInstructors.getBytes());
			DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, _tts
					.getNumberOfActiveDays(), _tts.getCurrentCycle()
					.getMaxNumberOfPeriodsADay(), linePosition);
			_dxSoInst = dxir.readSetOfInstructors();

			linePosition += ByteInputFile.countLines(inDiaFileInstructors);
			linePosition++; // for separator =========================
			// extract SetOfSites
			inDiaFileRooms = dataTokens.nextToken().trim();
			de = buildDataExchange(inDiaFileRooms.getBytes());
			// if (DxFlags.newRooms) {
			DxSiteReader dxrr = new DxReadSitedotDia(de, _tts
					.getNumberOfActiveDays(), _tts.getCurrentCycle()
					.getMaxNumberOfPeriodsADay(), linePosition);
			_dxSoSRooms = dxrr.readSetOfSites();
			linePosition += ByteInputFile.countLines(inDiaFileRooms);
			linePosition++; // for separator =========================
			// extract SetOfActivities
			inDiaFileActivities = dataTokens.nextToken().trim();
			de = buildDataExchange(inDiaFileActivities.getBytes());
			if (DxFlags.newActivity) {
				DxActivitiesSitesReader dxasr = new DxReadActivitiesSites1dot5(
						de, _dxSoInst, _dxSoSRooms.getAllDxRooms(), _tts
								.getPeriodLenght(), true);
			} else {
				_activitiesList = new SetOfActivitiesSites(true, _tts
						.getPeriodLenght());
				if (_activitiesList.analyseTokens(de, 1)) {
					_activitiesList.buildSetOfResources(de, 1);
				}
			}
			linePosition += ByteInputFile.countLines(inDiaFileActivities);
			linePosition++; // for separator =========================
			// extract SetOfStudents
			inDiaFileStudents = dataTokens.nextToken().trim();
			linePosition += ByteInputFile.countLines(inDiaFileStudents);
			de = buildDataExchange(inDiaFileStudents.getBytes());
			_studentsList = new SetOfStuSites();
			if (_studentsList.analyseTokens(de, 0)) {
				_studentsList.buildSetOfResources(de, 0);
			}
		} else {
			throw new DxException(DConst.PARTS_IN_DIA_SEPARATED_BY
					+ DConst.CR_LF + DConst.SAVE_SEPARATOR);
		}
		// if we arrive here there is no exception
		// so true can be used as a constant
		return true;
	}

	private boolean loadData2dot1(String fileName) throws NullPointerException,
			FileNotFoundException, IOException, DxException {

		String dataloaded = new String(filterBadChars(fileName));
		StringTokenizer dataTokens;

		DataExchange de;

		dataTokens = new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR_VIS);

		if (dataTokens.countTokens() == DConst.SAVE_SEPARATOR_COUNT) { // 6
			// !!!!!!!!!!!!!!
			// extract version
			_inDiaFileVersion = dataTokens.nextToken().trim();

			_tts = new TTStructure();

			de = buildDataExchange(dataTokens.nextToken().trim().getBytes());
			_tts.loadTTSFromString(de.getContents());

			// extract SetOfInstructor
			if (_tts.getError().length() == 0) {
				de = buildDataExchange(dataTokens.nextToken().trim().getBytes());
				DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, _tts
						.getNumberOfActiveDays(), _tts.getCurrentCycle()
						.getMaxNumberOfPeriodsADay());
				_dxSoInst = dxir.readSetOfInstructors();
			}// end if

			// extract SetOfSites
			de = buildDataExchange(dataTokens.nextToken().trim().getBytes());
			DxSiteReader dxrr = new DxReadSitedotDia(de, _tts
					.getNumberOfActiveDays(), _tts.getCurrentCycle()
					.getMaxNumberOfPeriodsADay());

			_dxSoSRooms = dxrr.readSetOfSites();
			// }

			// extract SetOfActivities
			de = buildDataExchange(dataTokens.nextToken().trim().getBytes());
			if (DxFlags.newActivity) {
				_dxasr = new DxReadActivitiesSites1dot5(de, _dxSoInst,
						_dxSoSRooms.getAllDxRooms(), _tts.getPeriodLenght(),
						true);

			} else {
				boolean isOpen = true;
				_activitiesList = new SetOfActivitiesSites(isOpen, _tts
						.getPeriodLenght());
				if (_activitiesList.analyseTokens(de, 1)) {
					_activitiesList.buildSetOfResources(de, 1);
				}
			}

			// extract SetOfStudents
			de = buildDataExchange(dataTokens.nextToken().trim().getBytes());
			_studentsList = new SetOfStuSites();
			if (_studentsList.analyseTokens(de, 0)) {
				_studentsList.buildSetOfResources(de, 0);
			}

		} else {
			new DxException(DConst.PARTS_IN_DIA_SEPARATED_BY + DConst.CR_LF
					+ DConst.SAVE_SEPARATOR_VIS);
			// System.exit(-1);
		}
		// if we arrive here there is no exception
		// so true can be used as a constant
		return true;

	}

	// /**
	// *
	// * @param fileName
	// * @return
	// * @throws DxException
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
	public DataExchange buildDataExchange(byte[] dataloaded) {
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

	public SetOfActivitiesSites getSetOfActivitiesSites() {
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
			String file) throws DxException, NullPointerException,
			FileNotFoundException, IOException {// , boolean merge){
		// DataExchange de = buildDataExchange(file);
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
		} else {// (NullPointerException npe) {
			throw new DxException("Unknown resource type !!!");
		}

		if ((newSetOfResc != null) && (newSetOfResc.getError() == "")) {
			makeDiff(newSetOfResc, currentSetOfResc);
			currentSetOfResc.sortSetOfResourcesByID();
		}// Ici sans le else on passe m�me s�il y a une erreur !!!!
		else {
			if (newSetOfResc != null)
				throw new DxException(newSetOfResc.getError());
		}
		return currentSetOfResc;
	}

	public DxSetOfInstructors extractInstructors() throws DxException,
			NullPointerException, FileNotFoundException, IOException {
		DataExchange de = buildDataExchange(_instructorFileName);
		// hara2602 ! TODO params 4 et 14 Trop dangeureux
		DxInstructorsReader dxir = new DxReadInstructors1dot5(de, 5, 14);// 5
		// jours
		// et
		// 14
		return dxir.readSetOfInstructors(); // 

	}

	public DxSetOfSites extractDxRooms() throws DxException,
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
	 * @throws DxException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	public SetOfStuSites extractStudents(SetOfStuSites currentList,
			boolean merge) throws DxException, NullPointerException,
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
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	public SetOfActivitiesSites extractActivities(
			SetOfActivitiesSites currentList, boolean merge)
			throws DxException, NullPointerException, FileNotFoundException,
			IOException {
		DataExchange de = buildDataExchange(_activitiesFileName);
		SetOfActivitiesSites activitiesList = new SetOfActivitiesSites(false,
				_dm.getTTStructure().getPeriodLenght());
		if (de.getContents() != null) {
			// Vector setOfResources = currentList
			// .getSetOfResources();
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
	 * 
	 * @param fileName
	 * @return
	 * @throws DxException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	private DataExchange buildDataExchange(String fileName) throws DxException,
			NullPointerException, FileNotFoundException, IOException {
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

		// int siteSize = getSiteSize(currentSites);
		// for (int i = 0; i < siteSize; i++) {
		// String rscSite = getSite(currentSites, i);
		// DSetOfResources rescSite = getRscSite(currentSites, i);
		// int catSize = getCategorySize(rescSite);
		// // find category in site
		// for (int j = 0; j < catSize; j++) {
		// String rscCat = getCategory(rescSite, j);
		// DSetOfResources rescCat = getRscCategory(rescSite, j);
		// // find resource in a category
		// for (int k = 0; k < rescCat.size(); k++) {
		// // current ressource
		// DResource curResc = rescCat.getResourceAt(k);
		// if (getResource(newSites, curResc, rscSite, rscCat) == null) {
		// DValue error = new DValue();
		// error.setStringValue(DConst.DELETED_ELEMENT
		// + curResc.getID());
		// if (_dm != null)
		// _dm.getSetOfImportSelErrors().addResource(
		// new DResource("1", error), 0);
		// // System.out.println("DELETED_ELEMENT "+
		// // curResc.getID());
		// rescCat.removeResource(curResc.getID());
		// k--; // Puisque la liste est tri� Sinon c est k=0;
		// }// end
		// // if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
		// }// end for k++
		// }// end for j++
		//
		// }// end for i++

	}

	/**
	 * 
	 * @param newSites
	 * @param currentSites
	 */
	private void findAddedElements(DSetOfResources newSites,
			DSetOfResources currentSites) {
		// find a site

		// int newSize = getSiteSize(newSites);
		// for (int i = 0; i < newSize; i++) {
		// String rscSite = getSite(newSites, i);
		// DSetOfResources rescSite = getRscSite(newSites, i);
		// int catSize = getCategorySize(rescSite);
		// // find category in site
		// for (int j = 0; j < catSize; j++) {
		// String rscCat = getCategory(rescSite, j);
		// DSetOfResources rescCat = getRscCategory(rescSite, j);
		// // find resource in a category
		// for (int k = 0; k < rescCat.size(); k++) {
		// // current ressource
		// DResource newRes = rescCat.getResourceAt(k);
		// if (getResource(currentSites, newRes, rscSite, rscCat) == null) {
		// DValue error = new DValue();
		// error.setStringValue(DConst.ADDED_ELEMENT
		// + newRes.getID());
		// if (_dm != null)
		// _dm.getSetOfImportSelErrors().addResource(
		// new DResource("2", error), 0);
		// // System.out.println("ADDED_ELEMENT "+ newRes.getID());
		// DResource crescSite = currentSites.getResource(rscSite);
		// if (crescSite != null) {
		// DSetOfResources crescCat = ((DSetOfResources) crescSite
		// .getAttach());
		// crescCat.addResourceMod(newRes, 1);
		// }
		// }// end
		// // if(newRsc.getResource(currentRsc.getResourceAt(i).getID())!=null)
		// }// end for k++
		// }// end for j++
		// }// end for i++

	}

	/**
	 * 
	 * @param newSites
	 * @param currentSites
	 */
	private void findChangesInElements(DSetOfResources newSites,
			DSetOfResources currentSites) {
		// find a site

		// int siteSize = getSiteSize(currentSites);
		// for (int i = 0; i < siteSize; i++) {
		// String rscSite = getSite(currentSites, i);
		// DSetOfResources rescSite = getRscSite(currentSites, i);
		// int catSize = getCategorySize(rescSite);
		// // find category in site
		// for (int j = 0; j < catSize; j++) {
		// String rscCat = getCategory(rescSite, j);
		// DSetOfResources rescCat = getRscCategory(rescSite, j);
		// // find resource in a category
		// for (int k = 0; k < rescCat.size(); k++) {
		// DResource resc = rescCat.getResourceAt(k);
		// DResource newRes = getResource(newSites, resc, rscSite,
		// rscCat);
		// if (newRes != null) {
		// // Already exist does it change ?
		// boolean changed = false;
		// if (currentSites instanceof SetOfStuSites) {
		// // Find if element change
		// changed = compareStudents(newRes, resc);
		// } // TODO a revoir
		// // else if (currentSites instanceof SetOfInstructors) {
		// // changed = compareInstructors(resc, newRes);
		// // }
		// DValue error = new DValue();
		// if (changed == true) {
		// error.setStringValue(DConst.CHANGED_ELEMENT
		// + newRes.getID());
		// if (_dm != null)
		// _dm.getSetOfImportSelErrors().addResource(
		// new DResource("3", error), 0);
		// // System.out.println("CHANGED_ELEMENT "+
		// // newRes.getID());
		// } else {
		// error.setStringValue(DConst.UNCHANGED_ELEMENT
		// + newRes.getID());
		// if (_dm != null)
		// _dm.getSetOfImportSelErrors().addResource(
		// new DResource("4", error), 0);
		// // System.out.println("UNCHANGED_ELEMENT "+
		// // newRes.getID());
		// }
		// }// end if !=null
		// }// end for k++
		// }// end for j++
		// }// end for i++
	}

	public byte[] filterBadChars(String str) throws NullPointerException,
			FileNotFoundException, IOException, DxException {
		SemiExtendedAsciiFile filter = new SemiExtendedAsciiFile();
		filter.validFile(str);
		return filter.getByteArray();
	}

} // end DxLoadData
