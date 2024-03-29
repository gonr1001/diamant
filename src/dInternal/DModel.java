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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import dConstants.DConst;
import dExceptions.DiaException;
import developer.DxFlags;
import dInterface.DApplication;
import dInterface.DxDocument;
import dInterface.DxTTableDoc;
import dInternal.dData.DLoadData;
import dInternal.dData.DSaveData;
import dInternal.dData.DxAvailability;
import dInternal.dData.DxResource;
import dInternal.dData.ExportData;
import dInternal.dData.StandardCollection;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.DxActivitySite;
import dInternal.dData.dActivities.DxSetOfActivities;
import dInternal.dData.dActivities.DxSetOfActivitiesSites;
import dInternal.dData.dActivities.Section;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.SetOfActivitiesInSites;
import dInternal.dData.dActivities.Type;
import dInternal.dData.dActivities.Unity;
import dInternal.dData.dInstructors.DxInstructor;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dData.dStudents.Student;
import dInternal.dOptimization.DxConditionsToTest;
import dInternal.dOptimization.SetOfEvents;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;

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

	private SetOfSites _setOfSites;

	private SetOfStuSites _setOfStuSites;

	private SetOfActivitiesInSites _setOfActivitiesSites;

	private DxDocument _dxDocument;

	private TTStructure _ttStruct;

	private SetOfEvents _setOfEvents;

	private int _currentCycle;

	private DxConditionsToTest _conditionsToTest;

	private int[] _nbConflicts;

	private DSetOfResources _setOfImportErrors;

	private DSetOfResources _setOfImportSelErrors;

	/**
	 * int value is between 0-1000 and give the state of the progress bar
	 */
	private int _progressBarState;

	/**
	 * 
	 * @param dDocument
	 * @param fileName
	 *            is the full path file name containing the TTStructure
	 * @param type
	 *            is the type of timetable to be constructed see DConst.
	 *            possible types NO_TYPE = 0; CYCLE = 1; EXAM = 2; CYCLEANDEXAM
	 *            = 3;
	 * @throws IOException
	 * @throws NullPointerException
	 * @throws Exception
	 *             used only in initDxTTableDoc
	 */
	public DModel(DxDocument dDocument, String fileName, int type)
			throws DiaException, NullPointerException, IOException {
		boolean onlyATimeTable = false;
		initDModel(dDocument, onlyATimeTable);

		if (fileName.endsWith(DConst.DOT_DIA)) {
			_error = loadTimeTable(fileName, getCurrentDir(fileName));
			_isATimeTable = true;
		} else if (fileName.endsWith(DConst.DOT_XML)) {
			_ttStruct = new TTStructure();
			InputStream is = new FileInputStream(fileName);
			try {
				_ttStruct.loadTTStructureFromInpuStream(is);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_isATimeTable = false;
		} else {
			_error = "Wrong type of file";
		}
		if (_error.length() == 0) {
			if (_isATimeTable)
				_conditionsToTest = new DxConditionsToTest(this);
		} else {
			throw new DiaException(_error);
		}
		if ((type == DConst.CYCLE) || (type == DConst.EXAM)) {
			_isATimeTable = true;
			_isOnlyATimeTable = true;
		}
		_type = type;

		this.notifyObservers(this);

	}

	/**
	 * @param dxTTableDoc
	 * @param dxLoadData
	 * @param _type2
	 * 
	 *            used only in initDxTTableDoc
	 */
	public DModel(DxTTableDoc dxTTableDoc, DxLoadData dxLoadData) {
		boolean onlyATimeTable = false;
		initDModel(dxTTableDoc, onlyATimeTable);	
		_isATimeTable = true;

		this.transferTimeTable(dxLoadData);

		_isATimeTable = true;
		if (_isATimeTable)
			_conditionsToTest = new DxConditionsToTest(this);
		this.notifyObservers(this);
	}

	// this constructor is used only on tests
	public DModel(DxDocument dxDocument, String fileName) throws DiaException,
			NullPointerException, IOException {
		boolean onlyATimeTable = false;
		initDModel(dxDocument, onlyATimeTable);

		_isATimeTable = true;

		if (fileName.endsWith(DConst.DOT_DIA)) {
			_error = loadTimeTable(fileName, getCurrentDir(fileName));
			// _isATimeTable = true;
		} else if (fileName.endsWith(DConst.DOT_XML)) {
			_ttStruct = new TTStructure();
			InputStream is = new FileInputStream(fileName);
			try {
				_ttStruct.loadTTStructureFromInpuStream(is);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_isATimeTable = false;
		} else {
			_error = "Wrong type of file";
		}
		if (_error.length() == 0) {
			if (_isATimeTable)
				_conditionsToTest = new DxConditionsToTest(this);
		} else {
			throw new DiaException(_error);
		}
		this.notifyObservers(this);
	}

	private void initDModel(DxDocument dDocument, boolean onlyATimeTable) {
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

		_progressBarState = 0;
		//_progressBarState.setIntValue(0);

		_dxDocument = dDocument;
		_isOnlyATimeTable = onlyATimeTable;
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
	public void setIsATimeTable() {
		_isATimeTable = true;
	}

	/**
	 * 
	 * @return
	 */
	public int getProgressBarState() {
		return _progressBarState;
	}
	/**
	 * 
	 * @return
	 */
	public void setProgressBarState(int i) {
		 _progressBarState = i;
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

	// /**
	// *
	// * @return
	// */
	// public DDocument getDDocument() {
	// return _dDocument;
	// }

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
		// if (DxFlags.newRooms) {
		_dxSetOfSites.alwaysAvailable();
		// } else {
		// for (int i = 0; i < this.getSetOfRooms().size(); i++) {
		// ((RoomAttach) this.getSetOfRooms().getResourceAt(i).getAttach())
		// .setFullAvailability();
		// }
		// }

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
	 * @throws IOException
	 * @throws NullPointerException
	 * @throws Exception
	 */

	public String loadTimeTable(String fileName, String currentDir)
			throws DiaException, NullPointerException, IOException {

		DLoadData loadData = new DLoadData(this);
		// try {
		boolean loadOk = loadData.loadDataStructures(fileName, currentDir);
		if (loadOk) {
			setVersion(loadData.getVersion());
			_ttStruct = loadData.getTTStructure();
			if (_ttStruct.getError().length() != 0)
				return _ttStruct.getError();

			_dxSetOfInstructors = loadData.getDxSetOfInstructors();

			_dxSetOfSites = loadData.getDxSetOfSitesRooms();

			if (DxFlags.newActivity) {
				_dxsoasSetOfAct = (DxSetOfActivitiesSites) loadData
						.getDxActivitiesSitesReader();
			} else {
				_setOfActivitiesSites = loadData.getSetOfActivitiesSites();

			}
			_setOfStuSites = loadData.getSetofStuSites();

			if (_setOfActivitiesSites.getError().length() != 0) {
				return _setOfActivitiesSites.getError();
			}
			if (_setOfStuSites.getError().length() != 0) {
				return _setOfStuSites.getError();
			}

			buildSetOfEvents();
			_conditionsToTest = new DxConditionsToTest(this);
			this._conditionsToTest.initAllConditions();
		}
		_constructionState = 1;
		setImportDone(false);
		DApplication.getInstance().setCursorDefault();
		return "";

		// } catch (FileNotFoundException fnfe) { // alert the user that the
		// // specified file does not exist
		// new DxExceptionDlg(fnfe.getMessage(), fnfe);
		// } catch (DiaException e) {
		// new DxExceptionDlg(e.getMessage(), e);
		// // TODO hara2602
		//
		// } finally {
		// DApplication.getInstance().setCursorDefault();
		// }
		// return "";

	}

	public void transferTimeTable(DxLoadData dxLoadData) {
		if (true) {
			setVersion(dxLoadData.getVersion());
			_ttStruct = dxLoadData.getTTStructure();
			// if (_ttStruct.getError().length() != 0)
			// return _ttStruct.getError();

			_dxSetOfInstructors = dxLoadData.getDxSetOfInstructors();

			_dxSetOfSites = dxLoadData.getDxSetOfSitesRooms();

			if (DxFlags.newActivity) {
				_dxsoasSetOfAct = (DxSetOfActivitiesSites) dxLoadData
						.getDxActivitiesSitesReader();
			} else {
				_setOfActivitiesSites = dxLoadData.getSetOfActivitiesSites();

			}
			_setOfStuSites = dxLoadData.getSetofStuSites();

			// if (_setOfActivitiesSites.getError().length() != 0) {
			// return _setOfActivitiesSites.getError();
			// }
			// if (_setOfStuSites.getError().length() != 0) {
			// return _setOfStuSites.getError();
			// }

			buildSetOfEvents();
			_conditionsToTest = new DxConditionsToTest(this);
			this._conditionsToTest.initAllConditions();
		}
		_constructionState = 1;
		setImportDone(false);
	}

	/**
	 * build set of events using currentcycle, setofactivities, setofinstructors
	 * and setofrooms
	 */
	private void buildSetOfEvents() {
		_setOfEvents.getSetOfResources().removeAllElements();
		// test to be sure that there is no null pointer exception
		if ((getSetOfActivities() != null) && (getSetOfStudents() != null)) {
			_setOfEvents.build(getSetOfActivities(), getSetOfImportErrors());
			getSetOfActivities().buildStudentRegisteredList(getSetOfStudents());
		}// end
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String importData(String str) throws DiaException {

		DLoadData loadData = new DLoadData(this, str);

		_dxSetOfInstructors = loadData.extractInstructors();
		resizeInstructorsResource(_dxSetOfInstructors);

		_dxSetOfSites = loadData.extractDxRooms();
		resizeSiteAvailability();// _dxSetOfSites);

		// import set of activities
		if (DxFlags.newActivity) {
			if (!DxFlags.newRooms) {
				System.out
						.println("DModel.importData cannot be completed with new Activites and old Rooms");
			}
			System.out.println("flag" + !DxFlags.newRooms);
			_dxsoasSetOfAct = loadData.extractDxActivity(_dxSetOfInstructors,
					_dxSetOfSites.getAllDxRooms(), _ttStruct.getPeriodLenght());
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

		_conditionsToTest = new DxConditionsToTest(this);
		this._conditionsToTest.initAllConditions();

		setImportDone(true);
		_isOnlyATimeTable = false;
		return "";
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @throws DiaException
	 */
	public void mergeData(String fileName, String selectionName)
			throws DiaException {
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
			// _setOfActivitiesSites = (SetOfActivitiesInSites) loadData
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
			throw new DiaException(error);
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
	// public SetOfRooms getSetOfRooms() {
	// SetOfRooms sorTmp = new SetOfRooms();
	// // if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES)) {
	// // sorTmp = new SetOfRooms();
	// // for (int i = 0; i < _setOfSites.size(); i++) {
	// // SetOfCategories soc = (SetOfCategories) _setOfSites
	// // .getResourceAt(i).getAttach();
	// // for (int j = 0; j < soc.size(); j++) {
	// // SetOfRooms sor = (SetOfRooms) soc.getResourceAt(j)
	// // .getAttach();
	// // for (int k = 0; k < soc.size(); k++) {
	// // sorTmp.addResource(sor.getResourceAt(k), 1);
	// // }
	// // }// end for (int j = 0; j < sor.size(); j++)
	// // }// end for (int i = 0; i < _setOfSites
	// // // return sorTmp;
	// // } else {// else if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES))
	// // DResource resc = _setOfSites.getResource(_currentSite);
	// // if (resc != null) {
	// // SetOfCategories site = (SetOfCategories) resc.getAttach();
	// // for (int i = 0; i < site.size(); i++) {
	// // SetOfRooms catSOR = (SetOfRooms) site.getResourceAt(i)
	// // .getAttach();
	// // for (int j = 0; j < catSOR.size(); j++)
	// // sorTmp.addResource(catSOR.getResourceAt(j), 1);
	// // }
	// // }
	// // }
	// return sorTmp;
	// }

	public DxSetOfRooms getDxSetOfRooms() {
		// // DxSetOfRooms dxsorTmp = new DxSetOfRooms();
		// // if (_currentSite.equalsIgnoreCase(DConst.ALL_SITES)) {
		// // dxsorTmp = _dxSetOfSites.getAllRooms();
		// // // Iterator itSites = _dxSetOfSites.iterator();
		// // // DxSetOfCategories dxsoc;
		// // // DxSetOfRooms dxsor;
		// // // while (itSites.hasNext()) {
		// // // dxsoc = ((DxSite) itSites.next()).getSetOfCat();
		// // // Iterator itCategory = dxsoc.iterator();
		// // // while (itCategory.hasNext()) {
		// // // dxsor = ((DxCategory) itCategory.next()).getSetOfRooms();
		// // // dxsorTmp.addSetOfRooms(dxsor);
		// // // }// end for (int j = 0; j < sor.size(); j++)
		// // // }// end for (int i = 0; i < _setOfSites
		// // } else {// else if
		// (_currentSite.equalsIgnoreCase(DConst.ALL_SITES))
		// // // DxSite dxsCurrentSite = _dxSetOfSites.getSite(_currentSite);
		// // // if (dxsCurrentSite != null) {
		// // // dxsorTmp = dxsCurrentSite.getAllRooms();
		// // // DxSetOfCategories dxsoc = dxsCurrentSite.getSetOfCat();
		// // // Iterator itCategory = dxsoc.iterator();
		// // // DxSetOfRooms dxsor;
		// // // while (itCategory.hasNext()) {
		// // // dxsor = ((DxCategory) itCategory.next()).getSetOfRooms();
		// // // dxsorTmp.addSetOfRooms(dxsor);
		// // // }
		// // dxsorTmp = _dxSetOfSites.getAllRooms();
		// // // }
		// // }
		return _dxSetOfSites.getAllDxRooms();
	}

	// /**
	// *
	// * @return
	// */
	// public SetOfCategories getSetOfCategories() {
	// return _setOfCategories;
	// }

	/**
	 * @return Returns the _setOfActivitiesSites.
	 */
	public SetOfActivitiesInSites getSetOfActivitiesSites() {
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

	// /**
	// *
	// * @return
	// */
	// public SetOfRoomsFunctions getSetOfRoomsFunctions() {
	// return _setOfRoomsFunctions;
	// }

	/**
	 * 
	 * @return
	 */
	public DxConditionsToTest getConditionsToTest() {
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
			if (DxFlags.newActivity) {
				error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
						_dxSetOfSites, _dxsoasSetOfAct, _setOfStuSites,
						filename);
			} else
				error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
						_dxSetOfSites, _setOfActivitiesSites, _setOfStuSites,
						filename);
		}
		if (DxFlags.newActivity) {
			error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
					_setOfSites, _dxsoasSetOfAct, _setOfStuSites, filename);
		} else {
			error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
					_setOfSites, _setOfActivitiesSites, _setOfStuSites,
					filename);
		}
		// // if (DxFlags.newRooms && DxFlags.newActivity) {
		// if (DxFlags.newActivity) {// if (DxFlags.newRooms &&
		// // DxFlags.newActivity) {
		// error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
		// _dxSetOfSites, _dxsoasSetOfAct, _setOfStuSites, filename);
		// // } else if (DxFlags.newRooms) {
		// } else {
		// error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
		// _dxSetOfSites, _setOfActivitiesSites, _setOfStuSites,
		// filename);
		// }
		// if (DxFlags.newActivity) {
		// error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
		// _setOfSites, _dxsoasSetOfAct, _setOfStuSites, filename);
		// } else {
		// error = saveD.saveTimeTable(_ttStruct, _dxSetOfInstructors,
		// _setOfSites, _setOfActivitiesSites, _setOfStuSites,
		// filename);
		// }
		if (error.length() != 0) {
			return error;
		} // else {
		saveD.saveTTStructure(_ttStruct, filename);
		// }
		_mergeDone = false;

		_modified = false;
		return error;
	}

	/**
	 * 
	 */

	public void changeInDModelByModifyRemove(Object obj) {
		this.getConditionsToTest().setMatrixBuilded(false, false);
		changeInDModel(obj);
	}

	public void changeInDModelBySectionModDlg(Object obj) {
		this.getConditionsToTest().setMatrixBuilded(false, false);
		changeInDModel(obj);
	}

	public void changeInModelByUnityModifDlg(Object obj) {
		this.getConditionsToTest().setMatrixBuilded(false, false);
		changeInDModel(obj);
	}

	public void changeInDModelByModifyAdd(Object obj, Vector<Student> students,
			String id) {
		getSetOfStudents().addActivityToStudents(students, id);
		getConditionsToTest().setMatrixBuilded(false, false);
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
		getConditionsToTest().setMatrixBuilded(false, true);
		changeInDModel(obj);
	}// end changeInDModelByBuildMatrixConflicts

	public void changeInDModelByDxEditEventDlg(Object obj) {
		this.setChanged();
		this.setModified();
		// this.setStateBarComponent();
		_nbConflicts = getTTStructure().getCurrentCycle()
				.getTotalNumberOfConflicts();
		// notify
		this.notifyObservers(obj);
		this.clearChanged();
	}// end changeInDModelByEditActivityDlg

	// public void changeInDModelByInstructorsDlg(Object obj) {
	// changeInDModel(obj);
	// }

	// public void changeInDModelByImportDlg(Object obj) {
	//
	// this.setChanged();
	// // change model
	// this.setModified();
	// // this.setStateBarComponent();
	// _nbConflicts = getTTStructure().getCurrentCycle()
	// .getTotalNumberOfConflicts();
	// // notify
	// this.notifyObservers(obj);
	// this.clearChanged();
	// }

	// public void changeInDModelByRoomsDlg(Object obj) {
	// changeInDModel(obj);
	// }

	public void changeInDModelByStudentsDlg(Object obj) {
		this.setChanged();
		this.setModified();
		this.getConditionsToTest().initAllConditions();
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
			this.getConditionsToTest().setMatrixBuilded(true, false);
		this.getConditionsToTest().initAllConditions();
		this.getSetOfActivities().sortSetOfResourcesByID();

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
		// this.buildSetOfEvents();
		this.getConditionsToTest().setMatrixBuilded(true, false);
		this.getConditionsToTest().initAllConditions();
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

	public String getCurrentSiteName() {
		return _currentSite;
	}

	public Vector<String> getOtherSites() {
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

	public Vector<String> getSites() {
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
		getConditionsToTest().initAllConditions();
	}

	/**
	 * 
	 */
	public void updateInstructorAvail() {
		String currentS = _currentSite;
		if (isMultiSite()) {
			Vector<String> sites = this.getSites();
			for (int i = 0; i < sites.size(); i++) {
				if (!DConst.ALL_SITES.equalsIgnoreCase(sites.get(i).toString())) {
					this.setCurrentSite(sites.get(i).toString());
					// this.buildSetOfEvents();
					this.getConditionsToTest().initAllConditions();
					this._setOfEvents.setAssignedInstAvail();
				}
			}
		}
		this.setCurrentSite(currentS);
	}

	// private void resizeResource(DSetOfResources soRes) {
	// int[][] matrix;
	// DObject attach;
	// for (int i = 0; i < soRes.size(); i++) {
	// attach = soRes.getResourceAt(i).getAttach();
	// matrix = attach.getMatrixAvailability();
	// matrix = DXToolsMethods
	// .resizeAvailability(matrix, getTTStructure());
	// attach.setAvailability(matrix);
	// }
	// }

	private void resizeInstructorsResource(DxSetOfInstructors soiRes) {
		int[][] matrix;
		DxInstructor dxiTemp;

		Iterator<DxResource> itInst = soiRes.iterator();
		while (itInst.hasNext()) {
			dxiTemp = (DxInstructor) itInst.next();
			matrix = dxiTemp.getAvailability().getMatrixAvailability();
			// matrix = DXToolsMethods
			// .resizeAvailability(matrix, getTTStructure());
			matrix = resizeAvailability(matrix, getTTStructure());
			dxiTemp.setAvailability(new DxAvailability(matrix));

		}
	}

	// /**
	// * resize resource availability
	// */
	// public void resizeResourceAvailability(DSetOfResources soRes) {
	// resizeResource(soRes);
	// } // resizeResourceAvailability

	/**
	 * @param ofSites
	 */
	public void resizeSiteAvailability(SetOfSites setOfSites) {
		// for (int i = 0; i < setOfSites.size(); i++) {
		// SetOfCategories soc = (SetOfCategories) setOfSites.getResourceAt(i)
		// .getAttach();
		// for (int j = 0; j < soc.size(); j++) {
		// SetOfRooms sor = (SetOfRooms) soc.getResourceAt(j).getAttach();
		// resizeResource(sor);
		// } // end for (int j=0; j < soc.size(); j++) {
		// }// end for (int i=0; i< setOfSites.size(); i++){
	} // resizeSiteAvailability

	/**
	 * @param ofSites
	 */
	public void resizeSiteAvailability() {// DxSetOfSites dxSetOfSites) {
		// DxSetOfRooms dxrAllRooms = getDxSetOfRooms();
		DxSetOfRooms dxrAllRooms = _dxSetOfSites.getAllDxRooms();
		Iterator<DxResource> itRooms = dxrAllRooms.iterator();
		while (itRooms.hasNext()) {
			DxRoom dxrTemp = (DxRoom) itRooms.next();
			int[][] nMatrix = dxrTemp.getAvailability().getMatrixAvailability();
			// nMatrix = DXToolsMethods.resizeAvailability(nMatrix,
			// getTTStructure());
			nMatrix = resizeAvailability(nMatrix, getTTStructure());
			dxrTemp.setAvailability(new DxAvailability(nMatrix));
		}
	} // resizeSiteAvailability

	// /**
	// * @return
	// */
	// public InstructorAvailabilityDlgModel getIADlgModel() {
	// InstructorAvailabilityDlgModel iaDlgModel = new
	// InstructorAvailabilityDlgModel();
	// iaDlgModel.setHours(getTTStructure().getCurrentCycle()
	// .getHourOfPeriodsADay());
	//
	// iaDlgModel.setDays(getTTStructure());
	// iaDlgModel.setMaxNumOfPeriods(getTTStructure());
	// iaDlgModel.setInstructorsNames(_dxSetOfInstructors);
	//
	// return iaDlgModel;
	// }

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
		// this.buildSetOfEvents();
		this.getConditionsToTest().initAllConditions();
		this.getSetOfActivities().sortSetOfResourcesByID();
		// _stateBarModel.update();

		// notify
		_nbConflicts = this.getTTStructure().getCurrentCycle()
				.getTotalNumberOfConflicts();
		this.notifyObservers(listener);
		this.clearChanged();
	}

	/**
	 * 
	 * @param initialAvail
	 * @param tt
	 * @return
	 */
	private int[][] resizeAvailability(int[][] initialAvail, TTStructure tt) {
		// check if is upper, lower or nothing operation
		// int UpperLower=0; // 1= make upper; 0= do nothing; -1= make lower
		if (initialAvail[0].length == tt.getCurrentCycle()
				.getMaxNumberOfPeriodsADay()) {
			// UpperLower=0;
			return initialAvail;
		} else if (initialAvail[0].length > tt.getCurrentCycle()
				.getMaxNumberOfPeriodsADay()) {
			// UpperLower=-1;
		} else if (initialAvail[0].length < tt.getCurrentCycle()
				.getMaxNumberOfPeriodsADay()) {
			// UpperLower=1;
		}

		Day day;
		Period per;
		int[][] finalAvail = new int[tt.getNumberOfActiveDays()][tt
				.getCurrentCycle().getMaxNumberOfPeriodsADay()];
		for (int h = 0; h < tt.getNumberOfActiveDays(); h++) {
			int itr = 0;
			day = tt.getCurrentCycle().getDayByIndex(h);
			for (int i = 0; i < day.getSetOfSequences().size(); i++) {
				for (int j = 0; j < day.getSequence(i).getSetOfPeriods().size(); j++) {
					per = day.getSequence(i).getPeriodByIndex(j);
					boolean avail = isAvailableInRange(initialAvail, h, per);// ,tt.getPeriodLenght());
					if (avail)
						finalAvail[h][itr] = 1;
					else
						finalAvail[h][itr] = 5;
					itr++;
				}// end for (int j=0; j<
				// day.getSequence(i).getSetOfPeriods().size(); j++)
			}// end for (int i=0; i< day.getSetOfSequences().size(); i++)
		}// end
		return finalAvail;
	}

	/**
	 * check the state availability in the range
	 * 
	 * @param initial
	 *            the availability matrix
	 * @param day
	 *            the day where to search availability
	 * @param per
	 *            the period
	 * @param int the period lenght
	 * @param up_low
	 *            the type of operation 1= make upper; 0= do nothing; -1= make
	 *            lower
	 * @return boolean
	 */
	private static boolean isAvailableInRange(int[][] initial, int day,
			Period per) {
		// , int periodLenght) {//, int up_low){
		int[] beginH = per.getBeginHour();
		int[] endH = per.getEndHour();
		int beginIndex;
		int endIndex;
		beginIndex = beginH[0] - DConst.STI_BEGIN_HOUR;
		if (beginH[1] < DConst.STI_BEGIN_MINUTE)
			beginIndex--;
		if (beginH[1] > DConst.STI_BEGIN_MINUTE)
			beginIndex++;
		if (beginIndex < 0)
			return false;
		// else{// else if (beginIndex<0)
		endIndex = endH[0] - DConst.STI_BEGIN_HOUR;
		if (endH[1] <= DConst.STI_BEGIN_MINUTE)
			endIndex--;
		else if (endH[1] > DConst.STI_BEGIN_MINUTE)
			endIndex++;
		if (endIndex < 0)
			return false;
		if (endIndex >= initial[day].length) {
			return false;
		}// else{// else if(endIndex> initial[day].length)
		for (int i = beginIndex; i <= endIndex; i++)
			if (initial[day][i] == 5)
				return false;
		// }// end else if(endIndex> initial[day].length)
		// }//end else if (beginIndex<0)
		return true;
	}

} /* end class DModel */