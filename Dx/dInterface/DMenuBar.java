/**
 *
 * Title: DMenuBar $Revision: 1.122 $  $Date: 2005-02-02 16:27:52 $
 * Description: DMenuBar is a class used to
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
 * @version $Revision: 1.122 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface;

import java.awt.Font;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

import dConstants.DConst;
import dDeveloper.MyFileCmd;
import dDeveloper.ShowAllCmd;
import dDeveloper.StateZeroCmd;
import dInterface.dAffectation.ActivityCmd;
import dInterface.dAffectation.ActivityModifCmd;
import dInterface.dAffectation.DefineSetCmd;
import dInterface.dAffectation.EventsCmd;
import dInterface.dAffectation.InitialAssignCmd;
import dInterface.dAffectation.PartialTTStructureCmd;
import dInterface.dAffectation.RoomsAvailabilityCmd;
import dInterface.dAffectation.SectionCmd;
import dInterface.dAlgorithms.PersonalizeMixingAlgorithmCmd;
import dInterface.dAlgorithms.*;
import dInterface.dData.DefFilesToImportCmd;
import dInterface.dData.ExportCmd;
import dInterface.dData.ImportCmd;
import dInterface.dData.ImportSelectiveFileCmd;
import dInterface.dData.InstructorAvailabilityCmd;
import dInterface.dData.ReportCmd;
import dInterface.dTimeTable.CloseCmd;
import dInterface.dTimeTable.ManualImprovementCmd;
import dInterface.dTimeTable.NewTTCyCmd;
import dInterface.dTimeTable.NewTTExCmd;
import dInterface.dTimeTable.NewTTSCyCmd;
import dInterface.dTimeTable.NewTTSExCmd;
import dInterface.dTimeTable.OpenTTCmd;
import dInterface.dTimeTable.OpenTTSCmd;
import dInterface.dTimeTable.SaveAsCmd;
import dInterface.dTimeTable.SaveCmd;
import dInterface.dUtil.AboutCmd;
import dInterface.dUtil.ConflictCmd;
import dInterface.dUtil.ExitCmd;
import dInterface.dUtil.PLAFCmd;
import dInterface.dUtil.ViewHorizontalSplitCmd;
import dInterface.dUtil.ViewSimpleCmd;
import dInterface.dUtil.ViewVerticalSplitCmd;


public class DMenuBar extends JMenuBar{
	private DApplication _dApplic;
	
	private final String cMFONT = DConst.MFONTDialog;
	private final int cFONT = Font.PLAIN;
	private final int cNPT11 = DConst.NPT11;
	
	//the main menus
	private JMenu _file,
	_assign,
	_modification,
	_optimisation,
	_report,
	_preferences,
	_help,
	_inTest,
	_multi,
	_dev;
	
	// for each main menu there is a assocaited boolean
	private boolean _boolMenuFile, _boolMenuAssign, _boolMenuOptimization,
	_boolMenuModification, _boolMenuReport, _boolMenuPreferences,
	_boolMenuHelp, _boolMenuDev, _boolInTest;
	
	// the file menu: sub-menus
	private JMenu _newTTable, _newTTStruc, _importSelect;
	private boolean _boolNewTTable, _boolNewTTStruc;
	
	// the file menus
	private CmdMenu
	_newTTableCycle,   //calls postNewTTCycleCmd calls setNewTTCycle
	_newTTableExam,  //calls postNewTTExamCmd calls setNewTTExam
	_newTTStrucCycle,  //calls postNewTTSCycleCmd calls setNewSCycle
	_newTTStrucExam,  //calls postNewTTSExamCmd calls setNewSExam
	_openTTable,    //calls postOpenTTCmd calls setReadyToBuildTT
	_openTTStruc,   //calls postOpenTTSCmd calls setNewTTCy
	_close,         //calls postCloseCmd calls setZero
	_save,          //calls nothing
	_saveAs,        //calls nothing
	_defineFiles,   //calls nothing
	_import,        //calls postImportCmd calls setReadToBuildTT
	_mergeRooms,
	_mergeInstructors,
	_mergeActivities,
	_mergeStudents,
	_export,        //
	_exit;
	
	private boolean _boolNewTTableCy, _boolNewTTableEx, _boolNewTTStrucCy,
	_boolNewTTStrucEx, _boolOpenTTable, _boolOpenTTStruc,
	_boolClose, _boolSave, _boolSaveAs,_boolDefineFiles,
	_boolImport, /*_boolImportSelect,*/ _boolExport, _boolExit;
	
	// the edit menus
	
	// the assign menus
	private CmdMenu _activities, _sections, _instructorAvailability,
	_roomsAvailability, _events, /*_exclusion, _conflict, */ _defineSet, _partialTTStructure;
	private boolean _boolActivities, _boolSections, _boolInstructorAvailability,
	_boolRoomsAvailability, _boolEvents;  /* , _boolExcl, _boolConfl, _boolDefineSet;, */
	//_boolPartialTTStructure;
	
	// the modification menus
	private CmdMenu _activityModif;
	private boolean _boolMActivityModif;
	
	// the optimisation menus
	private CmdMenu _initialAssign,
	_doOptimization, 
	_doSectionPartition;
	private boolean _boolInitialAssign, 
	_boolDoOptimization,
	_boolDoSectionPartition;
	//_boolStudentsMiddleMixingBalance,
	//_boolStudentsMixingOptimize;
	//private JMenu _studentsRepartition;
	//private boolean _boolStudentsRepartition;
	
	//the report menus
	private CmdMenu _mReport;
	
	// the preferences menus
	private CmdMenu _lookAndFeel, _conflicts, _viewSimple,
	_viewDetailedHorizontal, _viewDetailedVertical;
	private JMenu _view;
	private boolean _boolLookAndFeel, _boolConflicts, _boolView,
	_boolViewSimple,
	_bool_ViewDetailedHorizontal, _boolViewDetailedVertical;
	
	
	// the help menus
	private CmdMenu _about;
	private boolean _boolAbout;
	
	// the inTest menus
	private JMenu _mInTest1, _mInTest2;
	//private CmdMenu _userTestMixingPersonal;
	private boolean _boolmInTest1, _boolmInTest2;
	
	//Developpement menus
	private CmdMenu _myFile, _showAll, _stateZero;
	private boolean _boolMyFile, _boolShowAll, _boolStateZero;
	
	
	/**
	 * Constructor
	 * @param dApplic
	 */
	public DMenuBar(DApplication dApplic) {
		super();
		_dApplic = dApplic;
		createMenuBar();
	}
	
	
	/**
	 *
	 */
	private void createMenuBar() {
		createFileMenu();
		createAssignMenu();
		createModificationMenu();
		createOptimisationMenu();
		createReportMenu();
		createPreferencesMenu();
		createHelpMenu();
		createInTestMenu();
		if (DConst.DEVELOPMENT)
			createDevelopmentMenu();
		alwaysSet();
		setZero(); //setAll();
	} // end createMenus
	
	private void createFileMenu() {
		//Build the menu FILE.
		_file = new JMenu(DConst.M_FILE);
		_file.setFont(new java.awt.Font( cMFONT, cFONT, cNPT11 ));
		this.add(_file);
		
		_newTTable = new JMenu(DConst.NEW_TT);
		_newTTable.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		// Horaire cycle
		_newTTableCycle = new CmdMenu(DConst.NTT_CY);
		_newTTableCycle.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_newTTableCycle.setCommand(new NewTTCyCmd());
		_newTTableCycle.addActionListener(_dApplic);
		_newTTable.add(_newTTableCycle);
		
		// Horaire examen
		_newTTableExam = new CmdMenu(DConst.NTT_EX);
		_newTTableExam.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_newTTableExam.setCommand(new NewTTExCmd());
		_newTTableExam.addActionListener(_dApplic);
		_newTTable.add(_newTTableExam);
		
		_file.add(_newTTable);
		
		_newTTStruc = new JMenu(DConst.NEW_TTS);
		_newTTStruc.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11 ));
		
		// Cycle Cycle
		_newTTStrucCycle = new CmdMenu(DConst.NTTS_CY);
		_newTTStrucCycle.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_newTTStrucCycle.setCommand(new NewTTSCyCmd());
		_newTTStrucCycle.addActionListener(_dApplic);
		_newTTStruc.add(_newTTStrucCycle);
		
		// Cycle Examen
		_newTTStrucExam = new CmdMenu(DConst.NTTS_EX);
		_newTTStrucExam.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_newTTStrucExam.setCommand(new NewTTSExCmd());
		_newTTStrucExam.addActionListener(_dApplic);
		_newTTStruc.add(_newTTStrucExam);
		
		_file.add(_newTTStruc);
		
		// Ouvrir horaire
		_openTTable = new CmdMenu(DConst.OPEN);
		_openTTable.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_openTTable.setCommand(new OpenTTCmd());
		_openTTable.addActionListener(_dApplic);
		_file.add(_openTTable);
		
		// Ouvrir grille
		_openTTStruc = new CmdMenu(DConst.OPEN_TTS);
		_openTTStruc.setFont(new java.awt.Font( cMFONT, cFONT, cNPT11));
		_openTTStruc.setCommand(new OpenTTSCmd());
		_openTTStruc.addActionListener(_dApplic);
		_file.add(_openTTStruc);
		
		_close = new CmdMenu(DConst.BUT_CLOSE);
		_close.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11) );
		_close.setCommand(new CloseCmd());
		_close.addActionListener(_dApplic);
		_file.add(_close);
		_file.addSeparator();
		
		_save = new CmdMenu(DConst.SAVE);
		_save.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_save.setCommand(new SaveCmd());
		_save.addActionListener(_dApplic);
		_file.add(_save);
		
		_saveAs = new CmdMenu(DConst.SAVE_AS);
		_saveAs.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_saveAs.setCommand(new SaveAsCmd());
		_saveAs.addActionListener(_dApplic);
		_file.add(_saveAs);
		
		_file.addSeparator();
		
		_defineFiles = new CmdMenu(DConst.DEF_F_M);
		_defineFiles.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_defineFiles.setCommand(new DefFilesToImportCmd());
		_defineFiles.addActionListener(_dApplic);
		_file.add(_defineFiles);
		
		_import = new CmdMenu(DConst.IMP_A_M);
		_import.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_import.setCommand(new ImportCmd());
		_import.addActionListener(_dApplic);
		_file.add(_import);
		
		_export = new CmdMenu(DConst.EXPO);
		_export.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_export.setCommand(new ExportCmd());
		_export.addActionListener(_dApplic);
		_file.add(_export);
		_file.addSeparator();
		
		_exit = new CmdMenu(DConst.EXIT);
		_exit.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_exit.setCommand(new ExitCmd());
		_exit.addActionListener(_dApplic);
		_file.add(_exit);
	}
	
	private void createAssignMenu() {
		
		//Build the menu ASSIGN.
		_assign = new JMenu(DConst.ASSIGN);
		_assign.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		this.add(_assign);
		
		_activities = new CmdMenu(DConst.ACTI_ASSIGN_M);
		_activities.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_activities.setCommand(new ActivityCmd());
		_activities.addActionListener(_dApplic);
		_assign.add(_activities);
		
		_sections = new CmdMenu(DConst.GROUP_ASSIGN_M);
		_sections.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_sections.setCommand(new SectionCmd());
		_sections.addActionListener(_dApplic);
		_assign.add(_sections);
		
		_instructorAvailability = new CmdMenu(DConst.INST_ASSIGN_M);
		_instructorAvailability.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_instructorAvailability.setCommand(new InstructorAvailabilityCmd());
		_instructorAvailability.addActionListener(_dApplic);
		_assign.add(_instructorAvailability);
		
		_roomsAvailability = new CmdMenu(DConst.LOCAUX_ASSIGN_M);
		_roomsAvailability.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_roomsAvailability.setCommand(new RoomsAvailabilityCmd());
		_roomsAvailability.addActionListener(_dApplic);
		_assign.add(_roomsAvailability);
		
		_events = new CmdMenu(DConst.EVENTS_ASSIGN_M);
		_events.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_events.setCommand(new EventsCmd());
		_events.addActionListener(_dApplic);
		_assign.add(_events);
		
		_assign.addSeparator();
		
		_defineSet = new CmdMenu(DConst.DEFINE_SET_M);
		_defineSet.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_defineSet.setCommand(new DefineSetCmd());
		_defineSet.addActionListener(_dApplic);
		_assign.add(_defineSet);
		
		_partialTTStructure = new CmdMenu(DConst.PARTIAL_TTSTRUCTURE_M);
		_partialTTStructure.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		_partialTTStructure.setCommand(new PartialTTStructureCmd());
		_partialTTStructure.addActionListener(_dApplic);
		_assign.add(_partialTTStructure);
		
		_assign.addSeparator();
		
		CmdMenu mConfl = new CmdMenu(DConst.MANUAL_ASSIGN_M);
		mConfl.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		mConfl.setCommand(new ManualImprovementCmd());
		mConfl.addActionListener(_dApplic);
		_assign.add(mConfl);
		
	}
	
	
	private void createModificationMenu() {
		//Build the menu Modification.
		_modification = new JMenu(DConst.MODIFICATION);
		_modification.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		this.add( _modification );
		
		_activityModif = new CmdMenu(DConst.ACTIVITY_MODIF_M);
		_activityModif.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_activityModif.setCommand(new ActivityModifCmd());
		_activityModif.addActionListener(_dApplic);
		//_mActivitiesModif.add(_mTypeModif);	
		_modification.add(_activityModif);
		
		// Items in menu feph.
		_mInTest2 = new JMenu(DConst.SPECIAL_IMPORT);
		_mInTest2.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		
		
		// selective import
		// instructor selective import
		_mergeInstructors = new CmdMenu(DConst.IMP_SELECT_INST);
		_mergeInstructors.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeInstructors.setCommand(new ImportSelectiveFileCmd(/*_dApplic.getJFrame(),*/DConst.IMP_SELECT_INST));
		_mergeInstructors.addActionListener(_dApplic);
		_mInTest2.add(_mergeInstructors);
		
		// room selective import
		_mergeRooms = new CmdMenu(DConst.IMP_SELECT_ROOM);
		_mergeRooms.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeRooms.setCommand(new ImportSelectiveFileCmd(/*_dApplic.getJFrame(),*/DConst.IMP_SELECT_ROOM));
		_mergeRooms.addActionListener(_dApplic);
		_mInTest2.add(_mergeRooms);
		
		// activity selective import
		_mergeActivities = new CmdMenu(DConst.IMP_SELECT_ACT);
		_mergeActivities.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeActivities.setCommand(new ImportSelectiveFileCmd(/*_dApplic.getJFrame(),*/DConst.IMP_SELECT_ACT));
		_mergeActivities.addActionListener(_dApplic);
		_mInTest2.add(_mergeActivities);
		
		// students selective import
		_mergeStudents = new CmdMenu(DConst.IMP_SELECT_STUD);
		_mergeStudents.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeStudents.setCommand(new ImportSelectiveFileCmd(/*_dApplic.getJFrame(),*/DConst.IMP_SELECT_STUD));
		_mergeStudents.addActionListener(_dApplic);
		_mInTest2.add(_mergeStudents);
		
		//_mInTest2.add(_mInTest2);
		
		
		_modification.add(_mInTest2);
		
	
	}//end createModificationMenu
	

	

	private void createOptimisationMenu() {
		//Build the menu Optimisation.
		_optimisation = new JMenu(DConst.OPTIMIZATION);
		_optimisation.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		this.add( _optimisation );
		// Items in menu Optimisation.
		_initialAssign = new CmdMenu(DConst.M_INITIAL_ASSIGN);
		_initialAssign.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_initialAssign.setCommand(new InitialAssignCmd());
		_initialAssign.addActionListener(_dApplic);
		_optimisation.add(_initialAssign);
		
		// _doOptimization Item in menu Optimisation.
		_doOptimization = new CmdMenu(DConst.FIRSTALGORITHM);
		_doOptimization.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_doOptimization.setCommand(new AlgorithmsCmd());
		_doOptimization.addActionListener(_dApplic);
		_optimisation.add(_doOptimization);
		
		// _doOptimization Item in menu Optimisation.
		_doSectionPartition = new CmdMenu(DConst.SECTION_PARTITION);
		_doSectionPartition.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_doSectionPartition.setCommand(new PersonalizeMixingAlgorithmCmd(true));
		_doSectionPartition.addActionListener(_dApplic);
		_optimisation.add(_doSectionPartition);
/*		// Items in menu StudentMixing.
		_studentsRepartition = new JMenu(DConst.STUDENTS_REPARTITION);
		_studentsRepartition.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		
		_mStudentsMixingBalance = new CmdMenu(DConst.STUDENTMIXINGBAL);
		_mStudentsMixingBalance.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mStudentsMixingBalance.setCommand(new BalanceMixingAlgorithmCmd(false));
		_mStudentsMixingBalance.addActionListener(_dApplic);
		_studentsRepartition.add(_mStudentsMixingBalance);
		
		_mStudentsMiddleMixingBalance= new CmdMenu(DConst.STUDENTMIXINGMIDBAL);
		_mStudentsMiddleMixingBalance.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mStudentsMiddleMixingBalance.setCommand(new MiddleBalMixingAlgoritmCmd(false));
		_mStudentsMiddleMixingBalance.addActionListener(_dApplic);
		_studentsRepartition.add(_mStudentsMiddleMixingBalance);
		
		_mStudentsMixingOptimize = new CmdMenu(DConst.STUDENTMIXINGOPTI);//, this);
		_mStudentsMixingOptimize.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mStudentsMixingOptimize.setCommand(new OptimizeMixingAlgorithmCmd(false));
		_mStudentsMixingOptimize.addActionListener(_dApplic);
		_studentsRepartition.add(_mStudentsMixingOptimize);
		
		_optimisation.add(_studentsRepartition);*/
		
	}//end createOptimisationMenu
	
	
	
	private void createReportMenu() {
		//Build the menu Report.
		_report = new JMenu(DConst.REPORT_M);
		_report.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		this.add( _report );
		// Items in menu Report.
		_mReport = new CmdMenu(DConst.REPORTS); // "Rapports ...");
		_mReport.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mReport.setCommand(new ReportCmd());
		_mReport.addActionListener(_dApplic);
		_report.add(_mReport);
	}//end createReportMenu
	
	private void createPreferencesMenu(){
		//Build the menu PREFERENCES.
		_preferences = new JMenu(DConst.PREF);
		_preferences.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		this.add(_preferences);
		
		// Items in menu PREFERENCES.
		_lookAndFeel = new CmdMenu(DConst.PLAF_M);//, this);
		_lookAndFeel.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_lookAndFeel.setCommand(new PLAFCmd());
		_lookAndFeel.addActionListener(_dApplic);
		_preferences.add(_lookAndFeel);
		
		
		// Items in menu PREFERENCES.
		_conflicts = new CmdMenu(DConst.CONFLICTS);//, this);
		_conflicts.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_conflicts.setCommand(new ConflictCmd());
		_conflicts.addActionListener(_dApplic);
		_preferences.add(_conflicts);
		
		
		// Items in menu PREFERENCES.
		_view = new JMenu(DConst.DISPLAY_TT);
		_view.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		
		_viewSimple = new CmdMenu(DConst.SIMPLE);
		_viewSimple.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_viewSimple.setCommand(new ViewSimpleCmd());
		_viewSimple.addActionListener(_dApplic);
		_view.add(_viewSimple);
		
		_viewDetailedHorizontal = new CmdMenu(DConst.SPLIT_HORIZONTAL);//, this);
		_viewDetailedHorizontal.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_viewDetailedHorizontal.setCommand(new ViewHorizontalSplitCmd());
		_viewDetailedHorizontal.addActionListener(_dApplic);
		_view.add(_viewDetailedHorizontal);
		
		_viewDetailedVertical = new CmdMenu(DConst.SPLIT_VERTICAL);//, this);
		_viewDetailedVertical.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_viewDetailedVertical.setCommand(new ViewVerticalSplitCmd());
		_viewDetailedVertical.addActionListener(_dApplic);
		_view.add(_viewDetailedVertical);
		
		_preferences.add(_view);
		
	} // end createPreferencesMenu
	
	
	private void createHelpMenu(){
		_help = new JMenu(DConst.HELP);
		_help.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		this.add( _help );
		// Items in menu HELP.
		_about = new CmdMenu(DConst.APP_NAME + ": " + DConst.ABOUT_M);//, this);
		
		_about.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_about.setCommand(new AboutCmd());
		_about.addActionListener(_dApplic);
		_help.add(_about);
	} // end createHelpMenu
	
	
	private void createInTestMenu(){} /*
		_inTest = new JMenu(DConst.IN_TEST);
		_inTest.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		this.add( _inTest );
		// Items in menu admin.
		_mInTest1 = new JMenu(DConst.SUBMENU1);
		_mInTest1.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		
		_userTestMixingPersonal = new CmdMenu(DConst.STUDENTS_REPARTITION+" "
				+DConst.STUDENTMIXINGPERSO);//, this);
		_userTestMixingPersonal.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_userTestMixingPersonal.setCommand(new PersonalizeMixingAlgorithmCmd(true));
		_userTestMixingPersonal.addActionListener(_dApplic);
		//_userSubMenu1.add(_userTestMixingPersonal);
		// add item in submenu 1
		_mInTest1.add(_userTestMixingPersonal);
		
		// Items in menu feph.
		_mInTest2 = new JMenu(DConst.SUBMENU2);
		_mInTest2.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		
		
		// selective import
		// instructor selective import
		_mergeInstructors = new CmdMenu(DConst.IMP_SELECT_INST);
		_mergeInstructors.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeInstructors.setCommand(new ImportSelectiveFileCmd(DConst.IMP_SELECT_INST));
		_mergeInstructors.addActionListener(_dApplic);
		_mInTest2.add(_mergeInstructors);
		
		// room selective import
		_mergeRooms = new CmdMenu(DConst.IMP_SELECT_ROOM);
		_mergeRooms.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeRooms.setCommand(new ImportSelectiveFileCmd(DConst.IMP_SELECT_ROOM));
		_mergeRooms.addActionListener(_dApplic);
		_mInTest2.add(_mergeRooms);
		
		// activity selective import
		_mergeActivities = new CmdMenu(DConst.IMP_SELECT_ACT);
		_mergeActivities.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeActivities.setCommand(new ImportSelectiveFileCmd(DConst.IMP_SELECT_ACT));
		_mergeActivities.addActionListener(_dApplic);
		_mInTest2.add(_mergeActivities);
		
		// students selective import
		_mergeStudents = new CmdMenu(DConst.IMP_SELECT_STUD);
		_mergeStudents.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeStudents.setCommand(new ImportSelectiveFileCmd(DConst.IMP_SELECT_STUD));
		_mergeStudents.addActionListener(_dApplic);
		_mInTest2.add(_mergeStudents);
		
		//_mInTest2.add(_mInTest2);
		
		_inTest.add(_mInTest1);
		_inTest.add(_mInTest2);
		
	} // end createInTestMenu*/
	
	private void createMultiSiteMenu(){}/*
		_multi = new JMenu("MultiSite");
		_multi.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		this.add( _multi );
		Vector v =_dApplic.getDModel().getSites();
		for (int i = 0; i < v.size(); i++) {
			ButtonGroup group = new ButtonGroup();
			JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem((String) v.get(i));
			rbMenuItem.setSelected(false);
				//rbMenuItem.setMnemonic(KeyEvent.VK_R);
			group.add(rbMenuItem);
			_multi.add(rbMenuItem);
		}
	} // end createMultiSiteMenu*/
	
	private void createDevelopmentMenu(){
		_dev = new JMenu("Development");
		_dev.setFont( new java.awt.Font( cMFONT, cFONT, cNPT11 ) );
		this.add( _dev );
		// Items in menu Development.
		_myFile = new CmdMenu("fichier1.dia");
		_myFile.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_myFile.setCommand(new MyFileCmd());
		_myFile.addActionListener(_dApplic);
		_dev.add(_myFile);
		
		
		
		_showAll = new CmdMenu("showAllMenus");
		_showAll.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_showAll.setCommand(new ShowAllCmd());
		_showAll.addActionListener(_dApplic);
		_dev.add(_showAll);
		
		_stateZero = new CmdMenu("stateZero");
		_stateZero.setFont(new java.awt.Font(cMFONT, cFONT, cNPT11));
		_stateZero.setCommand(new StateZeroCmd());
		_stateZero.addActionListener(_dApplic);
		_dev.add(_stateZero);
		
		// selective import
		_importSelect = new JMenu(DConst.IMP_SELECT);
		_importSelect.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		
		// instructor selective import
		_mergeInstructors = new CmdMenu(DConst.IMP_SELECT_INST);
		_mergeInstructors.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeInstructors.setCommand(new ImportSelectiveFileCmd(/*_dApplic.getJFrame(),*/DConst.IMP_SELECT_INST));
		_mergeInstructors.addActionListener(_dApplic);
		_importSelect.add(_mergeInstructors);
		
		// room selective import
		_mergeRooms = new CmdMenu(DConst.IMP_SELECT_ROOM);
		_mergeRooms.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeRooms.setCommand(new ImportSelectiveFileCmd(/*_dApplic.getJFrame(),*/ DConst.IMP_SELECT_ROOM));
		_mergeRooms.addActionListener(_dApplic);
		_importSelect.add(_mergeRooms);
		
		// activity selective import
		_mergeActivities = new CmdMenu(DConst.IMP_SELECT_ACT);
		_mergeActivities.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeActivities.setCommand(new ImportSelectiveFileCmd(/*_dApplic.getJFrame(),*/DConst.IMP_SELECT_ACT));
		_mergeActivities.addActionListener(_dApplic);
		_importSelect.add(_mergeActivities);
		
		// students selective import
		_mergeStudents = new CmdMenu(DConst.IMP_SELECT_STUD);
		_mergeStudents.setFont( new java.awt.Font(cMFONT, cFONT, cNPT11));
		_mergeStudents.setCommand(new ImportSelectiveFileCmd(/*_dApplic.getJFrame(),*/DConst.IMP_SELECT_STUD));
		_mergeStudents.addActionListener(_dApplic);
		_importSelect.add(_mergeStudents);
		
		_dev.add(_importSelect);
		
	} // end createDevelopmentMenu
	
	private void alwaysSet() {
		//the menu _file
		_boolDefineFiles = true;
		_boolExit = true;
		
		//the menu _preferences
		_boolMenuPreferences= true;
		_boolLookAndFeel =true;
		
		//the menu _help
		_boolMenuHelp= true;
		_boolAbout = true;
	} // end alwaysSet
	
	private void setZero() {
		//the menu _file
		_boolMenuFile = true;
		//the submenus
		_boolNewTTable = true;
		_boolNewTTableCy = _boolNewTTableEx = true;
		
		_boolNewTTStruc = true;
		_boolNewTTStrucCy = _boolNewTTStrucEx = true;
		_boolOpenTTable  = _boolOpenTTStruc = true;
		_boolClose = false;
		_boolSave = _boolSaveAs = false;
		// always_boolDefineFiles = true;
		_boolImport = _boolExport = false;
		// always _boolExit = true;
		
		
		//the menu assign
		_boolMenuAssign= false;
		_boolActivities = true;
		_boolSections= true;
		_boolInstructorAvailability= true;
		_boolRoomsAvailability= true;
		_boolEvents= true;
		
		//the menu modification
		_boolMenuModification = false;
		_boolMActivityModif = true;
		
		//the menu optimization
		_boolMenuOptimization = false;
		//_boolMOpti= true;
		
		//the menu report
		_boolMenuReport = false;
		//the menu preferences
		// always_boolPreferences= true;
		// always _boolLookAndFeel =true;
		
		_boolConflicts = false;
		_boolView = false;
		//_boolViewSimple= false;
		//_bool_ViewDetailedHorizontal = false;
		//_boolViewDetailedVertical= false;
		
		
		
		//the menu help
		// always _boolHelp= true;
		// always _boolAbout = true;
		
		//the testmenu
		_boolInTest = false;
		//_boolmInTest1= true;
		//_boolmInTest2 = true;
		
		//the menu dev
		if (DConst.DEVELOPMENT) {
			_boolMenuDev = true;
			_boolMyFile = _boolShowAll = _boolStateZero = true;
		}
		_boolInTest = false;
		setMenus();
	}
	
	private void setReadyToInitialAssign() {
		//the menu _file
		_boolMenuFile = true;
		//the submenus
		_boolNewTTable = false;
		_boolNewTTableCy = _boolNewTTableEx = true;
		
		_boolNewTTStruc = false;
		_boolNewTTStrucCy = _boolNewTTStrucEx = true;
		_boolOpenTTable  = _boolOpenTTStruc = false;
		_boolClose = true;
		_boolSave = _boolSaveAs = true;
		_boolDefineFiles = true;
		_boolImport = false;
		_boolExport = false;
		_boolExit = true;
		
		
		//the menu assign
		_boolMenuAssign= false;
		/*_boolActivities = true;
		 _boolSections= true;
		 _boolInstructorAvailability= true;
		 _boolRoomsAvailability= true;
		 _boolEvents= true;*/
		
		//the menu modification
		_boolMenuModification = false;
		/*_boolMActivityModif = true;*/
		
		//the menu otimization
		
		
		_boolMenuOptimization = true;
		_boolInitialAssign = true;
		_boolDoOptimization = false;
		_boolDoSectionPartition = false;
		//_boolStudentsRepartition = false;
		
		
		//the report menu
		_boolMenuReport = true;
		
		//the menu preferences
		// always_boolPreferences= true;
		// always _boolLookAndFeel =true;
		_boolInTest = false;
		//the menu help
		// always _boolHelp= true;
		// always _boolAbout = true;
		
		//the menu dev
		if (DConst.DEVELOPMENT) {
			_boolMenuDev = true;
			_boolMyFile = _boolShowAll = _boolStateZero = true;
		}
		setMenus();
	} //end setReadyToBuildTT()
	
	private void setAfterInitialAssign() {
		//the menu _file
		updateMenuBar();
		_boolMenuFile = true;
		//the submenus
		_boolNewTTable = false;
		_boolNewTTableCy = _boolNewTTableEx = true;
		
		_boolNewTTStruc = false;
		_boolNewTTStrucCy = _boolNewTTStrucEx = true;
		_boolOpenTTable  = _boolOpenTTStruc = false;
		_boolClose = true;
		_boolSave = _boolSaveAs = true;
		_boolDefineFiles = true;
		_boolImport = false;
		_boolExport = true;
		_boolExit = true;
		
		
		//the menu assign
		_boolMenuAssign= true;
		_boolActivities = true;
		_boolSections= true;
		_boolInstructorAvailability= true;
		_boolRoomsAvailability= true;
		_boolEvents= true;
		
		//the menu modification
		_boolMenuModification = true;
		_boolMActivityModif = true;
		
		//the menu otimization
		
		//the menu otimization
		_boolMenuOptimization = true;
		_boolInitialAssign = false;
		_boolDoOptimization= true;
		_boolDoSectionPartition= true;
		//_boolStudentsRepartition = true;
		//_boolStudentsMixingBalance = true;
		//_boolStudentsMiddleMixingBalance = true;
		//_boolStudentsMixingOptimize= true;
		
		
		//the report menu
		_boolMenuReport = true;
		
		
		// always_boolPreferences= true;
		// always _boolLookAndFeel =true;
		_boolConflicts = true;
		_boolView = true;
		_boolViewSimple= true;
		_bool_ViewDetailedHorizontal = true;
		_boolViewDetailedVertical= true;
		
		//the menu help
		// always _boolHelp= true;
		// always _boolAbout = true;
		
		//the testmenu
		_boolInTest = true;
		_boolmInTest1= true;
		_boolmInTest2 = true;
		
		//the menu dev
		if (DConst.DEVELOPMENT) {
			_boolMenuDev = true;
			_boolMyFile = _boolShowAll = _boolStateZero = true;
		}
		setMenus();
	} //end setAfterInitialAssign
	
	private void setNewTTCy() {
		//the menu _file
		_boolMenuFile = true;
		//the submenus
		_boolNewTTable = false;
		_boolNewTTableCy = _boolNewTTableEx = true;
		
		_boolNewTTStruc = false;
		_boolNewTTStrucCy = _boolNewTTStrucEx = true;
		_boolOpenTTable  = _boolOpenTTStruc = false;
		_boolClose = true;
		_boolSave = _boolSaveAs = true;
		_boolDefineFiles = true;
		_boolImport = true;
		_boolExport = true;
		_boolExit = true;
		
		
		//the menu assign
		_boolMenuAssign= false;
		_boolActivities = true;
		_boolSections= true;
		_boolInstructorAvailability= true;
		_boolRoomsAvailability= true;
		_boolEvents= true;
		
		//the menu modification
		_boolMenuModification = false;
		_boolMActivityModif = true;
		
		//the menu otimization
		_boolMenuOptimization = false;
		//_boolMOpti= true;
		
		//the report menu
		_boolMenuReport = false;
		
		//the menu preferences
		// always_boolPreferences= true;
		// always _boolLookAndFeel =true;
		
		//the menu help
		// always _boolHelp= true;
		// always _boolAbout = true;
		
		//the menu dev
		if (DConst.DEVELOPMENT) {
			_boolMenuDev = true;
			_boolMyFile = _boolShowAll = _boolStateZero = true;
		}
		setMenus();
	}
	
	/* private void setNewTTEx() {
	 //the menu _file
	  _boolMenuFile = true;
	  //the submenus
	   _boolNewTTable = false;
	   _boolNewTTableCy = _boolNewTTableEx = true;
	   
	   _boolNewTTStruc = false;
	   _boolNewTTStrucCy = _boolNewTTStrucEx = true;
	   _boolOpenTTable  = _boolOpenTTStruc = false;
	   _boolClose = true;
	   _boolSave = _boolSaveAs = true;
	   _boolDefineFiles = true;
	   _boolImport = true;
	   _boolExport = true;
	   _boolExit = true;
	   
	   
	   //the menu assign
	    _boolMenuAssign= false;
	    _boolActivities = true;
	    _boolSections= true;
	    _boolInstructorAvailability= true;
	    _boolRoomsAvailability= true;
	    _boolEvents= true;
	    
	    //the menu modification
	     _boolMenuModification = false;
	     _boolMActivityModif = true;
	     
	     //the menu otimization
	      _boolMenuOptimization = false;
	      //_boolMOpti= true;
	       
	       
	       //the report menu
	        _boolMenuReport = false;
	        
	        //the menu preferences
	         // always_boolPreferences= true;
	          // always _boolLookAndFeel =true;
	           
	           //the menu help
	            // always _boolHelp= true;
	             // always _boolAbout = true;
	              
	              //the menu dev
	               if (DConst.DEVELOPMENT) {
	               _boolMenuDev = true;
	               _boolMyFile = _boolShowAll = _boolStateZero = true;
	               }
	               setMenus();
	               }*/
	
	private void setNewTTSCy() {
		//the menu _file
		_boolMenuFile = true;
		//the submenus
		_boolNewTTable = false;
		_boolNewTTableCy = _boolNewTTableEx = true;
		
		_boolNewTTStruc = false;
		_boolNewTTStrucCy = _boolNewTTStrucEx = true;
		_boolOpenTTable  = _boolOpenTTStruc = false;
		_boolClose = true;
		_boolSave = _boolSaveAs = true;
		_boolDefineFiles = true;
		_boolImport = false;
		_boolExport = false;
		_boolExit = true;
		
		
		//the menu assign
		_boolMenuAssign= false;
		_boolActivities = true;
		_boolSections= true;
		_boolInstructorAvailability= true;
		_boolRoomsAvailability= true;
		_boolEvents= true;
		
		//the menu modification
		_boolMenuModification = false;
		_boolMActivityModif = true;
		
		//the menu otimization
		_boolMenuOptimization = false;
		//_boolMOpti= true;
		
		//the report menu
		_boolMenuReport = false;
		
		//the menu preferences
		// always_boolPreferences= true;
		// always _boolLookAndFeel =true;
		
		//the menu help
		// always _boolHelp= true;
		// always _boolAbout = true;
		
		//the menu dev
		if (DConst.DEVELOPMENT) {
			_boolMenuDev = true;
			_boolMyFile = _boolShowAll = _boolStateZero = true;
		}
		setMenus();
	} // end setNewTTSCy
	
	private void setAll() {
		//the menu _file
		_boolMenuFile = true;
		//the submenus
		_boolNewTTable = true;
		_boolNewTTableCy = _boolNewTTableEx = true;
		
		_boolNewTTStruc = true;
		_boolNewTTStrucCy = _boolNewTTStrucEx = true;
		_boolOpenTTable  = _boolOpenTTStruc = _boolClose = true;
		_boolSave = _boolSaveAs = true;
		_boolDefineFiles = _boolImport = _boolExport = true;
		_boolExit = true;
		
		
		//the menu assign
		_boolMenuAssign= true;
		_boolActivities = true;
		_boolSections= true;
		_boolInstructorAvailability= true;
		_boolRoomsAvailability= true;
		_boolEvents= true;
		
		//the menu modification
		_boolMenuModification = true;
		_boolMActivityModif = true;
		
		//the menu otimization
		_boolMenuOptimization = true;
		_boolInitialAssign = true;
		_boolDoOptimization = true;
		_boolDoSectionPartition = true;
		//_boolStudentsRepartition = true;
		//_boolStudentsMixingBalance = true;
		//_boolStudentsMiddleMixingBalance = true;
		//_boolStudentsMixingOptimize= true;
		
		
		//the report menu
		_boolMenuReport = true;
		
		//the menu preferences
		_boolMenuPreferences= true;
		_boolLookAndFeel =true;
		_boolConflicts = true;
		_boolView = true;
		_boolViewSimple= true;
		_bool_ViewDetailedHorizontal = true;
		_boolViewDetailedVertical= true;
		
		//the menu help
		_boolMenuHelp= true;
		_boolAbout = true;
		
		//the testmenu
		_boolInTest = true;
		_boolmInTest1= true;
		_boolmInTest2 = true;
		
		//the menu dev
		if (DConst.DEVELOPMENT) {
			_boolMenuDev = true;
			_boolMyFile = _boolShowAll = _boolStateZero = true;
		}
		setMenus();
	} //end setAll()
	
	private void setMenus() {
		//System.out.println("setMenus" + _boolAssign);
		if (_boolMenuFile)
			setFileMenu();
		else
			_file.setEnabled(_boolMenuFile);
		// System.out.println("setMenus" + _boolAssign);
		if (_boolMenuAssign)
			setAssignMenu();
		else
			_assign.setEnabled(_boolMenuAssign);
		if (_boolMenuModification)
			setModificationMenu();
		else
			_modification.setEnabled(_boolMenuModification);
		if (_boolMenuOptimization)
			setOptimisationMenu();
		else
			_optimisation.setEnabled(_boolMenuOptimization);
		if (_boolMenuReport)
			setReportMenu();
		else
			_report.setEnabled(_boolMenuReport);
		if (_boolMenuPreferences)
			setPreferencesMenu();
		else
			_preferences.setEnabled(_boolMenuPreferences);
		if (_boolMenuHelp)
			setHelpMenu();
		else
			_help.setEnabled(_boolMenuHelp);
		if (DConst.DEVELOPMENT) {
			if (_boolMenuDev)
				setDevMenu();
			else
				_dev.setEnabled(_boolMenuDev);
		}
		//if (_boolInTest)
		//	setInTestMenu();
		//else
		//	_inTest.setEnabled(_boolInTest);
		//System.out.println("setMenusEnd");
	}
	
	private void setFileMenu() {
		_file.setEnabled(_boolMenuFile);
		if (_boolNewTTable) {
			_newTTable.setEnabled(_boolNewTTable);
			_newTTableCycle.setEnabled(_boolNewTTableCy);
			_newTTableExam.setEnabled(_boolNewTTableEx);
		} else _newTTable.setEnabled(_boolNewTTable);
		if (_boolNewTTStruc) {
			_newTTStruc.setEnabled(_boolNewTTStruc);
			_newTTStrucCycle.setEnabled(_boolNewTTStrucCy);
			_newTTStrucExam.setEnabled(_boolNewTTStrucEx);
		} else _newTTStruc.setEnabled(_boolNewTTStruc);
		_openTTable.setEnabled(_boolOpenTTable);
		_openTTStruc.setEnabled(_boolOpenTTStruc);
		_close.setEnabled(_boolClose);
		_save.setEnabled(_boolSave);
		_saveAs.setEnabled(_boolSaveAs);
		_defineFiles.setEnabled(_boolDefineFiles);
		_import.setEnabled(_boolImport);
		_export.setEnabled(_boolExport);
		_exit.setEnabled(_boolExit);
	} //end  setFileMenu
	
	private void setAssignMenu() {
		_assign.setEnabled(_boolMenuAssign);
		_activities.setEnabled(_boolActivities);
		_sections.setEnabled(_boolSections);
		_instructorAvailability.setEnabled(_boolInstructorAvailability);
		_roomsAvailability.setEnabled(_boolRoomsAvailability);
		_events.setEnabled(_boolEvents);
	} //end setAssignMenu
	
	private void setModificationMenu() {
		_modification.setEnabled(_boolMenuModification);
		_activityModif.setEnabled(_boolMActivityModif);
	}
	private void setOptimisationMenu() {
		_optimisation.setEnabled(_boolMenuOptimization);
		//if (_boolDoOptimization) {
		_initialAssign.setEnabled(_boolInitialAssign);
		_doOptimization.setEnabled(_boolDoOptimization);
		_doSectionPartition.setEnabled(_boolDoSectionPartition);
		//_studentsRepartition.setEnabled(_boolStudentsRepartition);
		////_mStudentsMixingBalance.setEnabled(_boolStudentsMixingBalance);
		//_mStudentsMiddleMixingBalance.setEnabled(_boolStudentsMiddleMixingBalance); 
		//_mStudentsMixingOptimize.setEnabled(_boolStudentsMixingOptimize);
	}
	
	private void setReportMenu() {
		_report.setEnabled(_boolMenuReport);
	}
	
	private void setPreferencesMenu() {
		//_preferences.setEnabled(_boolMenuPreferences);
		_lookAndFeel.setEnabled(_boolLookAndFeel);
		_conflicts.setEnabled(_boolConflicts);
		_view.setEnabled(_boolView);
		_viewSimple.setEnabled(_boolViewSimple);
		_viewDetailedHorizontal.setEnabled(_bool_ViewDetailedHorizontal);
		_viewDetailedVertical.setEnabled(_boolViewDetailedVertical);
		
	}
	
	private void setHelpMenu() {
		_help.setEnabled(_boolMenuHelp);
		_about.setEnabled(_boolAbout);
	}
	
	private void setInTestMenu() {
		_inTest.setEnabled(_boolInTest);
		_mInTest1.setEnabled(_boolmInTest1);
		_mInTest2.setEnabled(_boolmInTest2);
		
	} //end setAssignMenu
	
	
	private void setDevMenu() {
		_dev.setEnabled(_boolMenuDev);
		_myFile.setEnabled(_boolMyFile);
		_showAll.setEnabled(_boolShowAll);
		_stateZero.setEnabled(_boolStateZero);
	}
	
	/* For Menu file
	 */
	/*
	 *  postNewTTCyCmd() used as
	 *  postNewTTExCmd()
	 */
	public void postNewTTCyCmd(){
		setNewTTCy();
	}
	/*
	 * just calling an existing state
	 */
	public void postNewTTSCyCmd(){
		setNewTTSCy();
	}
	
	/*
	 * just calling an existing state
	 */
	public void postNewTTSExCmd(){
		setNewTTSCy();
	}
	/*
	 * ready to build a tt
	 */
	public void postOpenTTCmd(){
		setReadyToInitialAssign();
	}
	/*
	 * just calling an existing state
	 */
	public void postOpenTTSCmd(){
		setNewTTSCy(); //setOpenTTS();
	}
	
	public void postCloseCmd(){
		setZero();
	}
	
	public void postImportCmd(){
		setReadyToInitialAssign();
	}
	
	public void postAbout() {
		// no changes
	}
	
	public void postShowAll() {
		setAll();
	}
	public void postMyFile() {
		setReadyToInitialAssign();
	}
	
	public void postStateZero() {
		setZero();
	}
	
	public void postConflict(){
	}
	
	public void postInitialAssign(){
		setAfterInitialAssign();
	}
	
	private void updateMenuBar(){
		if(_dApplic.getDModel().isMultiSite())
			createMultiSiteMenu();
	}
} /* end class DMenuBar */