/**
 *
 * Title: DModel 
 * Description: DModel is a class used to
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
package dInternal;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import dConstants.DConst;
import dDeveloper.DxFlags;
import dInterface.DApplication;
import dInterface.DxDocument;
import dInternal.dData.DLoadData;
import dInternal.dData.DSaveData;
import dInternal.dData.DxAvailability;
import dInternal.dData.ExportData;
import dInternal.dData.StandardCollection;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.DxActivitySite;
import dInternal.dData.dActivities.DxSetOfActivities;
import dInternal.dData.dActivities.DxSetOfActivitiesSites;
import dInternal.dData.dActivities.Section;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dActivities.Type;
import dInternal.dData.dActivities.Unity;
import dInternal.dData.dInstructors.DxInstructor;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSite;
import dInternal.dData.dRooms.RoomAttach;
import dInternal.dData.dRooms.SetOfCategories;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dRooms.SetOfRoomsFunctions;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dDlgModel.InstructorAvailabilityDlgModel;
import dInternal.dOptimization.DxConditionsToTest;
import dInternal.dOptimization.SetOfEvents;
import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.exception.DxException;

/**
 * Description: DModel is a class used to
 * 
 * <p>
 * 
 * 
 */

public class DModel extends Observable {

	private int _type;

	private String _currentSite;

	private boolean _importDone;

	private boolean _mergeDone;

	private boolean _modified;

	private boolean _isATimeTable;

	private boolean _isOnlyATimeTable;

	private boolean _isExamPrepared;

	protected int _constructionState;// tell where the time construction

	private String _version;

	private String _error;

	private DxSetOfInstructors _dxSetOfInstructors;

	private DxSetOfSites _dxSetOfSites;

	private DxSetOfActivitiesSites _dxsoasSetOfAct;

	private SetOfRoomsFunctions _setOfRoomsFunctions;

	private SetOfSites _setOfSites;

	private SetOfCategories _setOfCategories;

	private SetOfStuSites _setOfStuSites;

	private SetOfActivitiesSites _setOfActivitiesSites;

//	private DDocument _dDocument;

	private DxDocument _dxDocument;

	private TTStructure _ttStruct;

	private SetOfEvents _setOfEvents;

	private int _currentCycle;

	private DxConditionsToTest _conditionsToTest;

	private int[] _nbConflicts;

	private DSetOfResources _setOfImportErrors;

	private DSetOfResources _setOfImportSelErrors;

	/**
	 * intvalue is between 0-1000 and give the state of the progress bar
	 */
	private DValue _progressBarState;

	/**
	 * 
	 * @param dDocument
	 * @param fileName
	 *            is the full path file name containing the TTStructure
	 * @param type
	 *            is the type of timetable to be constructed see DConst.
	 *            possible types NO_TYPE = 0; CYCLE = 1; EXAM = 2; CYCLEANDEXAM =
	 *            3;
	 * @throws Exception
	 * 
	 */
	// XXXX Pascal: 'type' devrait etre un objet, pas un 'int' !
//	public DModel(DDocument dDocument, String fileName, int type)
//			throws DxException {
//		_error = "";
//		_modified = false;
//		_isExamPrepared = false;
//		_currentSite = DConst.ACTIVITY_STANDARD_SITE;
//		_importDone = false;
//		_mergeDone = false;
//		_constructionState = 0;
//		_currentCycle = 1;
//		_nbConflicts = new int[] { 10, 20, 30 };
//		_setOfEvents = new SetOfEvents(this);
//		_setOfImportErrors = new StandardCollection();
//		_setOfImportSelErrors = new StandardCollection();
//
//		_progressBarState = new DValue();
//		_progressBarState.setIntValue(0); // XXXX Pascal: magic number
//		_dDocument = dDocument;
//		_isOnlyATimeTable = false;
//
//		if (fileName.endsWith(DConst.DOT_DIA)) {
//			_error = loadTimeTable(fileName, getCurrentDir(fileName));
//			_isATimeTable = true;
//		} else if (fileName.endsWith(DConst.DOT_XML)) {
//			_ttStruct = new TTStructure();
//			_error = _ttStruct.loadTTSFromFile(fileName);
//			_isATimeTable = false;
//		} else {
//			_error = "Wrong type of file";
//		}
//		if (_error.length() == 0) {
//			if (_isATimeTable)
//				_conditionsToTest = new DxConditionsToTest(this);
//		} else {
//			throw new DxException(_error);
//		}
//		if ((type == DConst.CYCLE) || (type == DConst.EXAM)) {
//			_isATimeTable = true;
//			_isOnlyATimeTable = true;
//		}
//		_type = type;
//		_setOfRoomsFunctions = new SetOfRoomsFunctions();
//		_setOfRoomsFunctions.functionReader();
//		this.notifyObservers(this);
//
//	}

	public DModel(DxDocument dxDocument, String fileName) throws DxException {
		_error = "";
		_modified = false;
		_isExamPrepared = false;
		_currentSite = DConst.ACTIVITY_STANDARD_SITE;
		_importDone = false;
		_mergeDone = false;
		_constructionState = 0;
		_currentCycle = 1;
		_nbConflicts = new int[] { 10, 20, 30 };
		_setOfEvents = new SetOfEvents(this);
		_setOfImportErrors = new StandardCollection();
		_setOfImportSelErrors = new StandardCollection();

		_progressBarState = new DValue();
		_progressBarState.setIntValue(0); // XXXX Pascal: magic number
		_dxDocument = dxDocument;
		_isOnlyATimeTable = false;

		if (fileName.endsWith(DConst.DOT_DIA)) {
			_error = loadTimeTable(fileName, getCurrentDir(fileName));
			_isATimeTable = true;
		} else if (fileName.endsWith(DConst.DOT_XML)) {
			_ttStruct = new TTStructure();
			_error = _ttStruct.loadTTSFromFile(fileName);
			_isATimeTable = false;
		} else {
			_error = "Wrong type of file";
		}
		if (_error.length() == 0) {
			if (_isATimeTable)
				_conditionsToTest = new DxConditionsToTest(this);
		} else {
			throw new DxException(_error);
		}
		// if ((type == DConst.CYCLE) || (type == DConst.EXAM)) {
		// _isATimeTable = true;
		// _isOnlyATimeTable = true;
		// }
		// _type = type;
		_setOfRoomsFunctions = new SetOfRoomsFunctions();
		_setOfRoomsFunctions.functionReader();
		this.notifyObservers(this);

	}

	/**
	 * 
	 * @return
	 */
	public String getError() {
		return _error;
	}

	/**
	 * 
	 * @return
	 */
	public DValue getProgressBarState() {
		return _progressBarState;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getModified() {
		return _modified;
	}

	/**
	 * 
	 * @return
	 */
	public void setModified() {
		_modified = true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getImportDone() {
		return _importDone;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getMergeDone() {
		return _mergeDone;
	}

	/**
	 * 
	 * @return
	 */
	public void setImportDone(boolean v) {
		_importDone = v;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isATimeTable() {
		return _isATimeTable;
	}

//	/**
//	 * 
//	 * @return
//	 */
//	public DDocument getDDocument() {
//		return _dDocument;
//	}

	public void prepareExamsData() {
		// supprime natures2
		// supprime groups
		// supprime events

		for (int i = 0; i < this.getSetOfActivities().size(); i++) {
			Activity activity = (Activity) this.getSetOfActivities()
					.getResourceAt(i).getAttach();
			activity.getSetOfTypes().getSetOfResources().removeAllElements();
			activity.addType("1");
			Type type = (Type) activity.getSetOfTypes().getResource("1")
					.getAttach();
			type.addSection("01");
			Section section = (Section) type.getSetOfSections().getResource(
					"01").getAttach();
			section.addUnity("1", 1, true);
			((Unity) section.getSetOfUnities().getResource("1").getAttach())
					.setDuration(180);
			// unity.addAssignment("1");
		}
		// set new instructor availability
		// for (int i = 0; i < _DxSetOfInstructors.size(); i++) {
		// _dxSetOfInstructors.getResourceAt(i)
		// .getAttach()).setFullAvailability();
		// }

		// TODO a revoir
		_dxSetOfInstructors.alwaysAvailable();

		// TODO a revoir
		if (DxFlags.newRooms) {
			_dxSetOfSites.alwaysAvailable();
		} else {
			for (int i = 0; i < this.getSetOfRooms().size(); i++) {
				((RoomAttach) this.getSetOfRooms().getResourceAt(i).getAttach())
						.setFullAvailability();
			}
		}

		_isExamPrepared = true;
	}

	/**
	 * 
	 * @return
	 */
	public String getVersion() {
		return _version;
	}

	/**
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		_version = version;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */

	public String loadTimeTable(String fileName, String currentDir) {

		DLoadData loadD = new DLoadData(this);
		Vector theTT = null;
		try {
			theTT = loadD.loadTheTT(fileName, currentDir);
			if (theTT.size() != 0) {
				setVersion((String) theTT.get(0));
				_ttStruct = (TTStructure) theTT.get(1);
				if (_ttStruct.getError().length() != 0)
					return _ttStruct.getError();
				_dxSetOfInstructors = (DxSetOfInstructors) theTT.get(2);

				if (DxFlags.newRooms) {
					_dxSetOfSites = (DxSetOfSites) theTT.get(3);
					// _dxSetOfSites.resizeSiteAvailability(_ttStructure);
				} else {
					_setOfSites = (SetOfSites) theTT.get(3);
					resizeSiteAvailability(_setOfSites);
				}

				if (DxFlags.newActivity) {
					_dxsoasSetOfAct = (DxSetOfActivitiesSites) theTT.get(4);
				} else {
					_setOfActivitiesSites = (SetOfActivitiesSites) theTT.get(4);
				}

				_setOfStuSites = (SetOfStuSites) theTT.get(5);
				if (!DxFlags.newRooms) {
					if (_setOfSites.getError().length() != 0) {
						return _setOfSites.getError();
					}
				}

				if (_setOfActivitiesSites.getError().length() != 0) {
					return _setOfActivitiesSites.getError();
				}
				if (_setOfStuSites.getError().length() != 0) {
					return _setOfStuSites.getError();
				}
				buildSetOfEvents();
				_conditionsToTest = new DxConditionsToTest(this);
				this.getConditionsTest().initAllConditions();
			}
			_constructionState = 1;
			setImportDone(false);
			return "";

//		} catch (FileNotFoundException fnfe) { // alert the user that the
//												// specified file does not exist
//			new DxExceptionDlg(fnfe.getMessage(), fnfe);
		} catch (DxException e) {
			new DxExceptionDlg(e.getMessage(), e);
			// TODO hara2602
			// Use finally to reset cursor to DEFAULT.
		} finally {
			DApplication.getInstance().setCursorDefault();
		}
		return "";

	}

	/**
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String importData(String str) throws DxException {

		DLoadData loadData = new DLoadData(this, str);

		_dxSetOfInstructors = loadData.extractInstructors();
		resizeInstructorsResource(_dxSetOfInstructors);

		// import set of sites
		if (DxFlags.newRooms) {
			_dxSetOfSites = loadData.extractDxRooms();
			resizeSiteAvailability();//_dxSetOfSites);

		} else {
			_setOfSites = loadData.extractRooms(null, false);
			resizeSiteAvailability(_setOfSites);//
			if (_setOfSites.getError().length() != 0) {
				return _setOfSites.getError();
			}
		}

		// import set of activities
		if (DxFlags.newActivity) {
			if (!DxFlags.newRooms) {
				System.out
						.println("DModel.importData cannot be completed with new Activites and old Rooms");
			}
			_dxsoasSetOfAct = loadData.extractDxActivity(_dxSetOfInstructors,
					_dxSetOfSites.getAllRooms(), _ttStruct.getPeriodLenght());
		} else {
			_setOfActivitiesSites = loadData.extractActivities(null, false);
			if (_setOfActivitiesSites.getError().length() != 0) {
				return _setOfActivitiesSites.getError();
			}
		}

		// import set of students
		_setOfStuSites = loadData.extractStudents(null, false);
		if (_setOfStuSites.getError().length() != 0) {
			return _setOfStuSites.getError();
		}
		_constructionState = 1;
		buildSetOfEvents();

		setImportDone(true);
		_isOnlyATimeTable = false;
		return "";
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @throws DxException
	 */
	public void mergeData(String fileName, String selectionName)
			throws DxException {
		_setOfImportSelErrors = new StandardCollection();
		String error = "";
		DLoadData loadData = new DLoadData(this);
		if (selectionName.equalsIgnoreCase(DConst.IMP_SELECT_INST)) {// Importation
			// selective
			// --
			// Enseignants
			// TODO revoir ceci
			// if (!DConst.newInstructors) {
			// _setOfInstructors = (SetOfInstructors) loadData
			// .selectiveImport(_setOfInstructors, fileName);
			// } else {
			// // _dxSetOfInstructors = (DxSetOfInstructors)
			// // loadData.selectiveImport(
			// // _dxSetOfInstructors, fileName);
			// }
			// resizeResourceAvailability(_setOfInstructors);

			changeInDModel(_dxDocument.getJIF());
		} else if (selectionName.equalsIgnoreCase(DConst.IMP_SELECT_ROOM)) {// Importation
			// selective
			// --
			// Locaux
			// TODO revoir ceci
			// _setOfSites = (SetOfSites) loadData.selectiveImport(_setOfSites,
			// fileName);
			// resizeSiteAvailability(_setOfSites);
			// error = _setOfSites.getError();
			this.changeInDModel(_dxDocument.getJIF());// _setOfRooms.sendEvent(_dDocument.getJIF());
		} else if (selectionName.equalsIgnoreCase(DConst.IMP_SELECT_ACT)) {// Importation
			// // selective
			// // --
			// // Activit�
			// TODO revoir ceci
			// _setOfActivitiesSites = (SetOfActivitiesSites) loadData
			// .selectiveImport(_setOfActivitiesSites, fileName);
			// error = _setOfActivitiesSites.getError();
			//
			// _conditionsToTest.setMatrixBuilded(false, true);
			changeInDModel(_dxDocument.getJIF());
		} else if (selectionName.equalsIgnoreCase(DConst.IMP_SELECT_STUD)) {// Importation
			// selective
			// --
			// �tudiants
			_setOfStuSites = (SetOfStuSites) loadData.selectiveImport(
					_setOfStuSites, fileName);
			error = _setOfStuSites.getError();
			_conditionsToTest.setMatrixBuilded(false, true);
			changeInDModel(_dxDocument.getJIF());
		}
		_mergeDone = true;
		if (error != "")
			throw new DxException(error);
	}

	/**
	 * 
	 * @return
	 */
	public DxSetOfInstructors getDxSetOfInstructors() {
		return _dxSetOfInstructors;
	}

	/**
	 * 
	 * @return
	 */
	public SetOfActivities getSetOfActivities() {
		boolean open = false;
		SetOfActivities soaTmp = new SetOfActivities(open, _ttStruct
				.getPeriodLenght());
		// all sites old way
		if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES)) {
			for (int i = 0; i < _setOfActivitiesSites.size(); i++) {
				SetOfActivities soa = (SetOfActivities) _setOfActivitiesSites
						.getResourceAt(i).getAttach();
				for (int j = 0; j < soa.size(); j++) {
					soaTmp.addResource(soa.getResourceAt(j), 1);
				}// end for (int j = 0; j < soa.size(); j++)
			}// end for (int i = 0; i < _setOfActiv
			// return soaTmp;
		} else {// else if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES))
			// if new
			DResource resc = _setOfActivitiesSites.getResource(_currentSite);
			if (resc != null)
				soaTmp = (SetOfActivities) resc.getAttach();
		}// end else if (_currentSite.equalsIgnoreC

		return soaTmp;
	}

	public DxSetOfActivities getDxSetOfActivities() {
		DxSetOfActivities dxsoaTmp = null;
		if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES)) {
			dxsoaTmp = _dxsoasSetOfAct.getAllActivities();

		} else {// else if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES))
			DxActivitySite dxasCurrentSite = _dxsoasSetOfAct
					.getActivitySite(_currentSite);
			if (dxasCurrentSite != null) {
				dxsoaTmp = dxasCurrentSite.getSetOfActivities();
			}
		}
		return dxsoaTmp;
	}

	/**
	 * 
	 * @return
	 */
	public SetOfRooms getSetOfRooms() {
		SetOfRooms sorTmp = new SetOfRooms();
		// SetOfRooms sorTmp;
		if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES)) {
			sorTmp = new SetOfRooms();
			for (int i = 0; i < _setOfSites.size(); i++) {
				SetOfCategories soc = (SetOfCategories) _setOfSites
						.getResourceAt(i).getAttach();
				for (int j = 0; j < soc.size(); j++) {
					SetOfRooms sor = (SetOfRooms) soc.getResourceAt(j)
							.getAttach();
					for (int k = 0; k < soc.size(); k++) {
						sorTmp.addResource(sor.getResourceAt(k), 1);
					}
				}// end for (int j = 0; j < sor.size(); j++)
			}// end for (int i = 0; i < _setOfSites
			// return sorTmp;
		} else {// else if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES))
			DResource resc = _setOfSites.getResource(_currentSite);
			if (resc != null) {
				SetOfCategories site = (SetOfCategories) resc.getAttach();
				for (int i = 0; i < site.size(); i++) {
					SetOfRooms catSOR = (SetOfRooms) site.getResourceAt(i)
							.getAttach();
					for (int j = 0; j < catSOR.size(); j++)
						sorTmp.addResource(catSOR.getResourceAt(j), 1);
				}
			}
		}
		return sorTmp;
	}

	public DxSetOfRooms getDxSetOfRooms() {
		DxSetOfRooms dxsorTmp = null;
		if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES)) {
			dxsorTmp = _dxSetOfSites.getAllRooms();
			// Iterator itSites = _dxSetOfSites.iterator();
			// DxSetOfCategories dxsoc;
			// DxSetOfRooms dxsor;
			// while (itSites.hasNext()) {
			// dxsoc = ((DxSite) itSites.next()).getSetOfCat();
			// Iterator itCategory = dxsoc.iterator();
			// while (itCategory.hasNext()) {
			// dxsor = ((DxCategory) itCategory.next()).getSetOfRooms();
			// dxsorTmp.addSetOfRooms(dxsor);
			// }// end for (int j = 0; j < sor.size(); j++)
			// }// end for (int i = 0; i < _setOfSites
		} else {// else if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES))
			DxSite dxsCurrentSite = _dxSetOfSites.getSite(_currentSite);
			if (dxsCurrentSite != null) {
				dxsorTmp = dxsCurrentSite.getAllRooms();
				// DxSetOfCategories dxsoc = dxsCurrentSite.getSetOfCat();
				// Iterator itCategory = dxsoc.iterator();
				// DxSetOfRooms dxsor;
				// while (itCategory.hasNext()) {
				// dxsor = ((DxCategory) itCategory.next()).getSetOfRooms();
				// dxsorTmp.addSetOfRooms(dxsor);
				// }
			}
		}
		return dxsorTmp;
	}

	/**
	 * 
	 * @return
	 */
	public SetOfCategories getSetOfCategories() {
		return _setOfCategories;
	}

	/**
	 * @return Returns the _setOfActivitiesSites.
	 */
	public SetOfActivitiesSites getSetOfActivitiesSites() {
		return _setOfActivitiesSites;
	}

	public DxSetOfActivitiesSites getDxSetOfActivitiesSites() {
		return _dxsoasSetOfAct;
	}

	/**
	 * @return Returns the _setOfStuSites.
	 */
	public SetOfStuSites getSetOfStuSites() {
		return _setOfStuSites;
	}

	/**
	 * 
	 * @return
	 */
	public SetOfEvents getSetOfEvents() {
		return _setOfEvents;
	}

	/**
	 * 
	 * @return
	 */
	public SetOfRoomsFunctions getSetOfRoomsFunctions() {
		return _setOfRoomsFunctions;
	}

	/**
	 * 
	 * @return
	 */
	public DxConditionsToTest getConditionsTest() {
		return _conditionsToTest;
	}

	/**
	 * 
	 * @return
	 */
	public SetOfStudents getSetOfStudents() {
		SetOfStudents sosTmp = new SetOfStudents();
		if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES)) {
			for (int i = 0; i < _setOfStuSites.size(); i++) {
				SetOfStudents sos = (SetOfStudents) _setOfStuSites
						.getResourceAt(i).getAttach();
				for (int j = 0; j < sos.size(); j++) {
					sosTmp.addResource(sos.getResourceAt(j), 1);
				}// end for (int j = 0; j < soa.size(); j++)
			}// end for (int i = 0; i < _setOfActiv
		} else {// else if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES))
			DResource resc = _setOfStuSites.getResource(_currentSite);
			if (resc != null)
				sosTmp = (SetOfStudents) resc.getAttach();
		}// end else if (_currentSite.equalsIgnoreC
		return sosTmp;
	}

	/**
	 * 
	 * @return
	 */
	public DSetOfResources getSetOfImportErrors() {
		return _setOfImportErrors;
	}

	/**
	 * 
	 * @return
	 */
	public DSetOfResources getSetOfImportSelErrors() {
		return _setOfImportSelErrors;
	}

	/**
	 * 
	 * @return
	 */
	public TTStructure getTTStructure() {
		return _ttStruct;
	}

	/**
	 * 
	 * @return
	 */
	public int getTypeOfSchedule() {
		return _type;
	}

	/**
	 * 
	 * @return
	 */
	public int getCurrentCycle() {
		return _currentCycle;
	}

	/**
	 * @return Returns the _setOfSites.
	 */
	public SetOfSites getSetOfSites() {
		return _setOfSites;
	}

	/**
	 * @return Returns the _setOfSites.
	 */
	public DxSetOfSites getDxSetOfSites() {
		return _dxSetOfSites;
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public String saveTimeTable(String filename) {
		DSaveData saveD = new DSaveData("1.5");
		String error = "";
		if (_isATimeTable) {
			updateInstructorAvail();
			if (DxFlags.newRooms && DxFlags.newActivity) {
				error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
						_dxSetOfSites, _dxsoasSetOfAct, _setOfStuSites,
						filename);
			} else if (DxFlags.newRooms) {
				error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
						_dxSetOfSites, _setOfActivitiesSites, _setOfStuSites,
						filename);
			} else if (DxFlags.newActivity) {
				error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
						_setOfSites, _dxsoasSetOfAct, _setOfStuSites, filename);
			} else {
				error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
						_setOfSites, _setOfActivitiesSites, _setOfStuSites,
						filename);
			}
			if (error.length() != 0)
				return error;
		} else {
			saveD.saveTTStructure(_ttStruct, filename);
		}
		_mergeDone = false;

		_modified = false;
		return error;
	}

	/**
	 * 
	 */

	public void changeInDModelByModifyRemove(Object obj) {
		this.getConditionsTest().setMatrixBuilded(false, false);
		changeInDModel(obj);
	}

	public void changeInDModelBySectionModDlg(Object obj) {
		this.getConditionsTest().setMatrixBuilded(false, false);
		changeInDModel(obj);
	}

	public void changeInModelByUnityModifDlg(Object obj) {
		this.getConditionsTest().setMatrixBuilded(false, false);
		changeInDModel(obj);
	}

	public void changeInDModelByModifyAdd(Object obj, Vector students, String id) {
		getSetOfStudents().addActivityToStudents(students, id);
		getConditionsTest().setMatrixBuilded(false, false);
		changeInDModel(obj);
	}

	public void changeInDModelByToolBar(Object obj) {
		// getConditionsTest().setMatrixBuilded(false,true);
		this.setChanged();
		// change model
		this.setModified();
		// this.setStateBarComponent();
		_nbConflicts = getTTStructure().getCurrentCycle()
				.getTotalNumberOfConflicts();
		this.notifyObservers(obj);
		this.clearChanged();
	}// end changeInDModelByToolBar

	public void changeInDModelByEventsDlg(Object obj) {
		changeInDModel(obj);
	}// end changeInDModelByEventsDlg

	public void changeInDModelByActivity(Object obj) {
		getConditionsTest().setMatrixBuilded(false, true);
		changeInDModel(obj);
	}// end changeInDModelByBuildMatrixConflicts

	public void changeInDModelByEditActivityDlg(Object obj) {
		this.setChanged();
		this.setModified();
		// this.setStateBarComponent();
		_nbConflicts = getTTStructure().getCurrentCycle()
				.getTotalNumberOfConflicts();
		// notify
		this.notifyObservers(obj);
		this.clearChanged();
	}// end changeInDModelByEditActivityDlg

	public void changeInDModelByInstructorsDlg(Object obj) {
		changeInDModel(obj);
	}

	public void changeInDModelByImportDlg(Object obj) {
		this.setChanged();
		// change model
		this.setModified();
		// this.setStateBarComponent();
		_nbConflicts = getTTStructure().getCurrentCycle()
				.getTotalNumberOfConflicts();
		// notify
		this.notifyObservers(obj);
		this.clearChanged();
	}

	// public void changeInDModelByRoomsDlg(Object obj) {
	// changeInDModel(obj);
	// }

	public void changeInDModelByStudentsDlg(Object obj) {
		this.setChanged();
		this.setModified();
		this.getConditionsTest().initAllConditions();
		this.getSetOfActivities().sortSetOfResourcesByID();
		// notify
		_nbConflicts = getTTStructure().getCurrentCycle()
				.getTotalNumberOfConflicts();
		this.notifyObservers(obj);
		this.clearChanged();
	}// end changeInDModelByStudentsDlg

	public void changeInDModel(Object obj) {
		this.setChanged();
		// change model
		if (isMultiSite())
			updateInstructorAvail();
		this.setModified();

		if (this.getTypeOfSchedule() == DConst.EXAM && !_isExamPrepared) {
			this.prepareExamsData();
		}
		this.buildSetOfEvents();
		if (isMultiSite())
			this.getConditionsTest().setMatrixBuilded(true, false);
		this.getConditionsTest().initAllConditions();
		this.getSetOfActivities().sortSetOfResourcesByID();
		// _stateBarModel.update();

		// notify
		_nbConflicts = this.getTTStructure().getCurrentCycle()
				.getTotalNumberOfConflicts();
		this.notifyObservers(obj);
		this.clearChanged();
	}

	public void changeInDModelByAllSites(Object obj) {
		this.setChanged();
		// change model
		this.setModified();

		if (this.getTypeOfSchedule() == DConst.EXAM) {
			this.prepareExamsData();
		}
		this.buildSetOfEvents();
		this.getConditionsTest().setMatrixBuilded(true, false);
		this.getConditionsTest().initAllConditions();
		this.getSetOfActivities().sortSetOfResourcesByID();
		_nbConflicts = getTTStructure().getCurrentCycle()
				.getTotalNumberOfConflicts();
		this.notifyObservers(obj);
		// this.clearChanged();
	}

	/**
	 * Export data from soft to SIG
	 */
	public void exportData(String dir) {
		DApplication.getInstance().setCursorWait();
		ExportData dataExp = new ExportData(this);
		dataExp.saveExportReport(dir);
		DApplication.getInstance().setCursorDefault();
	}

	public String getCurrentSite() {
		return _currentSite;
	}

	public Vector getOtherSites() {
		Vector<String> v = new Vector<String>();
		for (int i = 0; i < _setOfActivitiesSites.size(); i++)
			if (!_currentSite.equalsIgnoreCase(_setOfActivitiesSites
					.getResourceAt(i).getID()))
				v.add(_setOfActivitiesSites.getResourceAt(i).getID());
		return v;
	}

	public void setCurrentSite(String str) {
		_currentSite = str;
	}

	public boolean isMultiSite() {
		return _setOfActivitiesSites.size() > 1;
	}

	public Vector getSites() {
		Vector<String> v = new Vector<String>();
		for (int i = 0; i < _setOfActivitiesSites.size(); i++)
			v.add(_setOfActivitiesSites.getResourceAt(i).getID());
		return v;
	}

	private String getCurrentDir(String fileName) {
		if (fileName.lastIndexOf(File.separator) > 0) {
			return fileName.substring(0, fileName.lastIndexOf(File.separator));
		}
		return "";
	}

	/**
	 * 
	 */
	public void updateEventsInTTS() {
		// _dm._conditionTest = new ConditionsTest(_dm);
		getConditionsTest().initAllConditions();
	}

	/**
	 * 
	 */
	public void updateInstructorAvail() {
		String currentS = _currentSite;
		if (isMultiSite()) {
			Vector sites = this.getSites();
			for (int i = 0; i < sites.size(); i++) {
				if (!DConst.ALL_SITES.equalsIgnoreCase(sites.get(i).toString())) {
					this.setCurrentSite(sites.get(i).toString());
					this.buildSetOfEvents();
					this.getConditionsTest().initAllConditions();
					this._setOfEvents.setAssignedInstAvail();
				}
			}
		}
		this.setCurrentSite(currentS);
	}

	/**
	 * build set of events using currentcycle, setofactivities, setofinstructors
	 * and setofrooms
	 */
	public void buildSetOfEvents() {
		_setOfEvents.getSetOfResources().removeAllElements();

		if (getSetOfActivities() != null) {
			_setOfEvents.build(getSetOfActivities(), getSetOfImportErrors());
			if ((getSetOfActivities() != null) && (getSetOfStudents() != null))
				getSetOfActivities().buildStudentRegisteredList(
						getSetOfStudents());
			_conditionsToTest = new DxConditionsToTest(this);
		}// end if (_setOfActivities!=null)

	}

	private void resizeResource(DSetOfResources soRes) {
		int[][] matrix;
		DObject attach;
		for (int i = 0; i < soRes.size(); i++) {
			attach = soRes.getResourceAt(i).getAttach();
			matrix = attach.getMatrixAvailability();
			matrix = DXToolsMethods
					.resizeAvailability(matrix, getTTStructure());
			attach.setAvailability(matrix);
		}
	}

	private void resizeInstructorsResource(DxSetOfInstructors soiRes) {
		int[][] matrix;
		DxInstructor dxiTemp;

		Iterator itInst = soiRes.iterator();
		while (itInst.hasNext()) {
			dxiTemp = (DxInstructor) itInst.next();
			matrix = dxiTemp.getAvailability().getMatrixAvailability();
			matrix = DXToolsMethods
					.resizeAvailability(matrix, getTTStructure());
			dxiTemp.setAvailability(new DxAvailability(matrix));

		}
	}

	/**
	 * resize resource availability
	 */
	public void resizeResourceAvailability(DSetOfResources soRes) {
		resizeResource(soRes);
	} // resizeResourceAvailability

	/**
	 * @param ofSites
	 */
	public void resizeSiteAvailability(SetOfSites setOfSites) {
		for (int i = 0; i < setOfSites.size(); i++) {
			SetOfCategories soc = (SetOfCategories) setOfSites.getResourceAt(i)
					.getAttach();
			for (int j = 0; j < soc.size(); j++) {
				SetOfRooms sor = (SetOfRooms) soc.getResourceAt(j).getAttach();
				resizeResource(sor);
			} // end for (int j=0; j < soc.size(); j++) {
		}// end for (int i=0; i< setOfSites.size(); i++){
	} // resizeSiteAvailability

	/**
	 * @param ofSites
	 */
	public void resizeSiteAvailability(){//DxSetOfSites dxSetOfSites) {
		DxSetOfRooms dxrAllRooms = getDxSetOfRooms();
		Iterator itRooms = dxrAllRooms.iterator();
		while (itRooms.hasNext()) {
			DxRoom dxrTemp = (DxRoom) itRooms.next();
			int[][] nMatrix = dxrTemp.getAvailability().getMatrixAvailability();
			nMatrix = DXToolsMethods.resizeAvailability(nMatrix,
					getTTStructure());
			dxrTemp.setAvailability(new DxAvailability(nMatrix));
		}
	} // resizeSiteAvailability

	/**
	 * @return
	 */
	public InstructorAvailabilityDlgModel getIADlgModel() {
		InstructorAvailabilityDlgModel iaDlgModel = new InstructorAvailabilityDlgModel();
		iaDlgModel.setHours(getTTStructure().getCurrentCycle()
				.getHourOfPeriodsADay());

		iaDlgModel.setDays(getTTStructure());
		iaDlgModel.setMaxNumOfPeriods(getTTStructure());
		iaDlgModel.setInstructorsNames(_dxSetOfInstructors);

		return iaDlgModel;
	}

	public int getStudentConflicts() {
		return _nbConflicts[2];
	}

	public int getInstructorConflicts() {
		return _nbConflicts[0];
	}

	public int getRoomConflicts() {
		return _nbConflicts[1];
	}

	public boolean isOnlyATimeTable() {
		return _isOnlyATimeTable;
	}

	public DxDocument getDxDocument() {
		return _dxDocument;
	}

	public void changeInModel(ActionListener listener) {
		this.setChanged();
		// change model
		if (isMultiSite())
			updateInstructorAvail();
		this.setModified();

		if (this.getTypeOfSchedule() == DConst.EXAM && !_isExamPrepared) {
			this.prepareExamsData();
		}
		this.buildSetOfEvents();
		this.getConditionsTest().initAllConditions();
		this.getSetOfActivities().sortSetOfResourcesByID();
		// _stateBarModel.update();

		// notify
		_nbConflicts = this.getTTStructure().getCurrentCycle()
				.getTotalNumberOfConflicts();
		this.notifyObservers(listener);
		this.clearChanged();
	}
} /* end class DModel */