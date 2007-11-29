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
import dInternal.dData.dInstructors.DxReadInstructorsdotDia;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxReadSitedotDia;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSiteReader;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DXToolsMethods;
import developer.DxFlags;
import eLib.exit.exception.DxException;
import eLib.exit.txt.ByteInputFile;
import eLib.exit.txt.FilterFile;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxLoadData is a class used to:
 * <p>
 * TODO:insert comments
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

	// TODO: since getSetOfInstructors throws exceptions, I had choice to add
	// throws to function
	// or try catch on the return. Since we want to propagate error to the
	// application, I thought throws was the solution
	public boolean loadDataStructures(String fileName, String currentDir)
			throws DxException {

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
			throws DxException {

		String dataloaded = new String(filterBadChars(fileName));
		StringTokenizer dataTokens;
		long linePosition = 0;
		DataExchange de;

		dataTokens = new StringTokenizer(dataloaded, DConst.SAVE_SEPARATOR);

		if (dataTokens.countTokens() == DConst.SAVE_SEPARATOR_COUNT) { // =================================
			// extract version
			_inDiaFileVersion = dataTokens.nextToken().trim();
			linePosition += ByteInputFile.count(_inDiaFileVersion);

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

			linePosition += ByteInputFile.count(inDiaFileInstructors);
			linePosition++; // for separator =========================
			// extract SetOfSites
			inDiaFileRooms = dataTokens.nextToken().trim();
			de = buildDataExchange(inDiaFileRooms.getBytes());
			// if (DxFlags.newRooms) {
			DxSiteReader dxrr = new DxReadSitedotDia(de, _tts
					.getNumberOfActiveDays(), _tts.getCurrentCycle()
					.getMaxNumberOfPeriodsADay(), linePosition);
			_dxSoSRooms = dxrr.readSetOfSites();
			// } else {
			// _roomsList = new SetOfSites();
			// if (_roomsList.analyseTokens(de, 3)) {
			// _roomsList.buildSetOfResources(de, 3);
			// }
			// }
			linePosition += ByteInputFile.count(inDiaFileRooms);
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
			linePosition += ByteInputFile.count(inDiaFileActivities);
			linePosition++; // for separator =========================
			// extract SetOfStudents
			inDiaFileStudents = dataTokens.nextToken().trim();
			linePosition += ByteInputFile.count(inDiaFileStudents);
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

	private boolean loadData2dot1(String fileName) throws DxException {

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

	
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws DxException
	 */
	private DataExchange buildDataExchange(String fileName) throws DxException {
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
	
	public byte[] filterBadChars(String str) throws DxException {
		FilterFile filter = new FilterFile();
		filter.validFile(str);
		return filter.getByteArray();
	}

} // end DxLoadData