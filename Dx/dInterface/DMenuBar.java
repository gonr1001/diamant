/**
 *
 * Title: DMenuBar $Revision: 1.107 $  $Date: 2004-05-17 18:12:15 $
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
 * @version $Revision: 1.107 $
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */
package dInterface;

import dResources.DConst;
import dAux.DoNothingCmd;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import javax.swing.JMenu;

import dInterface.dAffectation.*;
import dInterface.dData.*;
import dInterface.dAlgorithms.*;
import dInterface.dTimeTable.NewTTExCmd;
import dInterface.dTimeTable.NewTTCyCmd;
import dInterface.dTimeTable.OpenTTCmd;
import dInterface.dTimeTable.NewTTSCyCmd;
import dInterface.dTimeTable.NewTTSExCmd;
import dInterface.dTimeTable.OpenTTSCmd;
import dInterface.dTimeTable.SaveAsCmd;
import dInterface.dTimeTable.SaveCmd;
import dInterface.dTimeTable.CloseCmd;
import dInterface.dTimeTable.ManualImprovementCmd;

import dInterface.dUtil.AboutCmd;
import dInterface.dUtil.ConflictCmd;
import dInterface.dUtil.ExitCmd;
import dInterface.dUtil.PLAFCmd;
import dInterface.dUtil.ViewHorizontalSplitCmd;
import dInterface.dUtil.ViewVerticalSplitCmd;
import dInterface.dUtil.ViewSimpleCmd;



import dAux.MyFileCmd;
import dAux.ShowAllCmd;
import dAux.StateZeroCmd;


public class DMenuBar extends JMenuBar{
  private DApplication _dApplic;
  private final boolean _DEVELOPMENT = DConst.DEVELOPMENT;

  private final String _mfont = DConst.MFONTDialog;
  private final int _font = Font.PLAIN;
  private final int _nPT = DConst.NPT11;

  //the main menus
  private JMenu _file,
  _assign,
  _modification,
  _optimisation,
  _preferences,
  _report,
  _help,
  _dev;
  // for each main menu there is a assocaited boolean
  private boolean _boolMenuFile, _boolMenuAssign, _boolMenuOptimization,
  _boolMenuModification, _boolMenuReport, _boolMenuPreferences,
  _boolMenuHelp, _boolMenuDev;

  // the file menu: sub-menus
  private JMenu _newTTable, _newTTStruc, _importSelect;
  private boolean _boolNewTTable, _boolNewTTStruc;

  // the file menus
  private CmdMenu
  _newTTableCy,   //calls postNewTTCyCmd calls setNewTTCy
  _newNTTableEx,  //calls postNewTTCyCmd calls setNewTTCy
  _newTTStrucCy,  //calls postNewTTSCyCmd calls setNewSCy
  _newTTStrucEx,  //calls postNewTTSExCmd calls setNewSCy
  _openTTable,    //calls postOpenTTCmd calls setReadyToBuildTT
  _openTTStruc,   //calls postOpenTTSCmd calls setNewTTCy
  _close,         //calls postCloseCmd calls setZero
  _save,          //calls nothing
  _saveAs,        //calls nothing
  _defineFiles,   //calls nothing
  _import,        //calls postImportCmd calls setReadToBuildTT
  _importSelectRoom,
  _importSelectInst,
  _importSelectAct,
  _importSelectStud,
  _export,        //
  _exit;
  private boolean _boolNewTTableCy, _boolNewTTableEx, _boolNewTTStrucCy,
  _boolNewTTStrucEx, _boolOpenTTable, _boolOpenTTStruc,
  _boolClose, _boolSave, _boolSaveAs,_boolDefineFiles,
  _boolImport, _boolImportSelect, _boolExport, _boolExit;
  // the edit menus

  // the assign menus
  private CmdMenu _activities, _sections, _instructorAvailability,
  _roomsAvailability, _events, _exclusion, _conflict, _defineSet, _partialTTStructure;
  private boolean _boolActivities, _boolSections, _boolInstructorAvailability,
  _boolRoomsAvailability, _boolEvents, _boolExcl, _boolConfl, _boolDefineSet,
  _boolPartialTTStructure;

  // the modification menus
  private CmdMenu _activityModif;
  private boolean _boolMActivityModif;

  // the optimisation menus
  private CmdMenu
  _initialAssign,
  _doOptimization, _mStudentsMixingBalance,
  _mStudentsMiddleMixingBalance, _mStudentsMixingOptimize;
  private boolean _boolInitialAssign, _boolDoOptimization,
  _boolStudentsMixingBalance,_boolStudentsMixingOptimize;
  private JMenu _studentsRepartition;
  private boolean _boolStudentsRepartition;
  //the report menus
  private CmdMenu _mReport;

  // the preferences menus
  private CmdMenu _lookAndFeel, _conflicts, _viewSimple,
  _viewDetailedHorizontal, _viewDetailedVertical;
  private boolean _boolLookAndFeel, _boolConflicts, _boolView, _boolViewSimple,
  _bool_ViewDetailedHorizontal, _boolViewDetailedVertical;
  private JMenu _view;
  // the help menus
  private CmdMenu _about;
  private boolean _boolAbout;

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
    if (_DEVELOPMENT)
      createDevelopmentMenu();
    setAlways();
    setZero(); //setAll();
  } // end createMenus

  private void createFileMenu() {
    //Build the menu FILE.
    _file = new JMenu(DConst.M_FILE);
    _file.setFont(new java.awt.Font( _mfont, _font, _nPT ));
    this.add(_file);

    _newTTable = new JMenu(DConst.NEW_TT);
    _newTTable.setFont( new java.awt.Font(_mfont, _font, _nPT));

    _newTTableCy = new CmdMenu(DConst.NTT_CY);
    _newTTableCy.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _newTTableCy.setCommand(new NewTTCyCmd());
    _newTTableCy.addActionListener(_dApplic);
    _newTTable.add(_newTTableCy);

    _newNTTableEx = new CmdMenu(DConst.NTT_EX);
    _newNTTableEx.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _newNTTableEx.setCommand(new NewTTExCmd());
    _newNTTableEx.addActionListener(_dApplic);
    _newTTable.add(_newNTTableEx);

    _file.add(_newTTable);

    _newTTStruc = new JMenu(DConst.NEW_TTS);
    _newTTStruc.setFont(new java.awt.Font(_mfont, _font, _nPT ));

    _newTTStrucCy = new CmdMenu(DConst.NTTS_CY);
    _newTTStrucCy.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _newTTStrucCy.setCommand(new NewTTSCyCmd());
    _newTTStrucCy.addActionListener(_dApplic);
    _newTTStruc.add(_newTTStrucCy);

    _newTTStrucEx = new CmdMenu(DConst.NTTS_EX);
    _newTTStrucEx.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _newTTStrucEx.setCommand(new NewTTSExCmd());
    _newTTStrucEx.addActionListener(_dApplic);
    _newTTStruc.add(_newTTStrucEx);

    _file.add(_newTTStruc);

    _openTTable = new CmdMenu(DConst.OPEN);
    _openTTable.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _openTTable.setCommand(new OpenTTCmd());
    _openTTable.addActionListener(_dApplic);
    _file.add(_openTTable);

    _openTTStruc = new CmdMenu(DConst.OPEN_TTS);
    _openTTStruc.setFont(new java.awt.Font( _mfont, _font, _nPT));
    _openTTStruc.setCommand(new OpenTTSCmd());
    _openTTStruc.addActionListener(_dApplic);
    _file.add(_openTTStruc);

    _close = new CmdMenu(DConst.BUT_CLOSE);
    _close.setFont( new java.awt.Font(_mfont, _font, _nPT) );
    _close.setCommand(new CloseCmd());
    _close.addActionListener(_dApplic);
    _file.add(_close);
    _file.addSeparator();

    _save = new CmdMenu(DConst.SAVE);
    _save.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _save.setCommand(new SaveCmd());
    _save.addActionListener(_dApplic);
    _file.add(_save);

    _saveAs = new CmdMenu(DConst.SAVE_AS);
    _saveAs.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _saveAs.setCommand(new SaveAsCmd());
    _saveAs.addActionListener(_dApplic);
    _file.add(_saveAs);

    _file.addSeparator();

    _defineFiles = new CmdMenu(DConst.DEF_F_M);
    _defineFiles.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _defineFiles.setCommand(new DefFilesToImportCmd());
    _defineFiles.addActionListener(_dApplic);
    _file.add(_defineFiles);

    _import = new CmdMenu(DConst.IMP_A_M);
    _import.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _import.setCommand(new ImportCmd(_dApplic.getJFrame()));
    _import.addActionListener(_dApplic);
    _file.add(_import);

    _export = new CmdMenu(DConst.EXPO);
    _export.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _export.setCommand(new ExportCmd(_dApplic.getJFrame()));
    _export.addActionListener(_dApplic);
    _file.add(_export);
    _file.addSeparator();

    _exit = new CmdMenu(DConst.EXIT);
    _exit.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _exit.setCommand(new ExitCmd());
    _exit.addActionListener(_dApplic);
    _file.add(_exit);
  }

  private void createAssignMenu() {

    //Build the menu ASSIGN.
    _assign = new JMenu(DConst.ASSIGN);
    _assign.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add(_assign);

    _activities = new CmdMenu(DConst.ACTI_ASSIGN_M);
    _activities.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _activities.setCommand(new ActivityCmd());
    _activities.addActionListener(_dApplic);
    _assign.add(_activities);

    _sections = new CmdMenu(DConst.GROUP_ASSIGN_M);
    _sections.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _sections.setCommand(new SectionCmd());
    _sections.addActionListener(_dApplic);
    _assign.add(_sections);

    _instructorAvailability = new CmdMenu(DConst.INST_ASSIGN_M);
    _instructorAvailability.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _instructorAvailability.setCommand(new InstructorAvailabilityCmd());
    _instructorAvailability.addActionListener(_dApplic);
    _assign.add(_instructorAvailability);

    _roomsAvailability = new CmdMenu(DConst.LOCAUX_ASSIGN_M);
    _roomsAvailability.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _roomsAvailability.setCommand(new RoomsAvailabilityCmd(_dApplic));
    _roomsAvailability.addActionListener(_dApplic);
    _assign.add(_roomsAvailability);

    _events = new CmdMenu(DConst.EVENTS_ASSIGN_M);
    _events.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _events.setCommand(new EventsCmd());
    _events.addActionListener(_dApplic);
    _assign.add(_events);

    _assign.addSeparator();

    _defineSet = new CmdMenu(DConst.DEFINE_SET_M);
    _defineSet.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _defineSet.setCommand(new DefineSetCmd());
    _defineSet.addActionListener(_dApplic);
    _assign.add(_defineSet);

    _partialTTStructure = new CmdMenu(DConst.PARTIAL_TTSTRUCTURE_M);
    _partialTTStructure.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _partialTTStructure.setCommand(new PartialTTStructureCmd());
    _partialTTStructure.addActionListener(_dApplic);
    _assign.add(_partialTTStructure);

    _assign.addSeparator();

    CmdMenu mConfl = new CmdMenu(DConst.MANUAL_ASSIGN_M);
    mConfl.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mConfl.setCommand(new ManualImprovementCmd());
    mConfl.addActionListener(_dApplic);
    _assign.add(mConfl);

  }


  private void createModificationMenu() {
    //Build the menu Modification.
    _modification = new JMenu(DConst.MODIFICATION);
    _modification.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _modification );

    _activityModif = new CmdMenu(DConst.ACTIVITY_MODIF_M);
    _activityModif.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _activityModif.setCommand(new ActivityModifCmd());
    _activityModif.addActionListener(_dApplic);
    //_mActivitiesModif.add(_mTypeModif);

    _modification.add(_activityModif);
  }//end createModificationMenu


  private void createOptimisationMenu() {
    //Build the menu Optimisation.
    _optimisation = new JMenu(DConst.OPTIMIZATION);
    _optimisation.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _optimisation );
    // Items in menu Optimisation.
    _initialAssign = new CmdMenu(DConst.M_INITIAL_ASSIGN);
    _initialAssign.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _initialAssign.setCommand(new InitialAssignCmd(_dApplic));
    _initialAssign.addActionListener(_dApplic);
    _optimisation.add(_initialAssign);

    // _doOptimization Item in menu Optimisation.
    _doOptimization = new CmdMenu(DConst.FIRSTALGORITHM);
    _doOptimization.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _doOptimization.setCommand(new AlgorithmsCmd());
    _doOptimization.addActionListener(_dApplic);
    _optimisation.add(_doOptimization);

    // Items in menu StudentMixing.
    _studentsRepartition = new JMenu(DConst.STUDENTS_REPARTITION);
    _studentsRepartition.setFont( new java.awt.Font(_mfont, _font, _nPT));

    _mStudentsMixingBalance = new CmdMenu(DConst.STUDENTMIXINGBAL);
    _mStudentsMixingBalance.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _mStudentsMixingBalance.setCommand(new BalanceMixingAlgorithmCmd());
    _mStudentsMixingBalance.addActionListener(_dApplic);
    _studentsRepartition.add(_mStudentsMixingBalance);

    _mStudentsMiddleMixingBalance= new CmdMenu(DConst.STUDENTMIXINGMIDBAL);
    _mStudentsMiddleMixingBalance.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _mStudentsMiddleMixingBalance.setCommand(new MiddleBalMixingAlgoritmCmd());
    _mStudentsMiddleMixingBalance.addActionListener(_dApplic);
    _studentsRepartition.add(_mStudentsMiddleMixingBalance);

    _mStudentsMixingOptimize = new CmdMenu(DConst.STUDENTMIXINGOPTI);//, this);
    _mStudentsMixingOptimize.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _mStudentsMixingOptimize.setCommand(new OptimizeMixingAlgorithmCmd());
    _mStudentsMixingOptimize.addActionListener(_dApplic);
    _studentsRepartition.add(_mStudentsMixingOptimize);

    _optimisation.add(_studentsRepartition);

  }//end createOptimisationMenu



  private void createReportMenu() {
    //Build the menu Report.
    _report = new JMenu(DConst.REPORT_M);
    _report.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _report );
    // Items in menu Report.
    _mReport = new CmdMenu(DConst.REPORTS); // "Rapports ...");
    _mReport.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _mReport.setCommand(new ReportCmd());
    _mReport.addActionListener(_dApplic);
    _report.add(_mReport);
  }//end createReportMenu

  private void createPreferencesMenu(){
    //Build the menu PREFERENCES.
    _preferences = new JMenu(DConst.PREF);
    _preferences.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add(_preferences);

    // Items in menu PREFERENCES.
    _lookAndFeel = new CmdMenu(DConst.PLAF_M);//, this);
    _lookAndFeel.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _lookAndFeel.setCommand(new PLAFCmd(_dApplic));
    _lookAndFeel.addActionListener(_dApplic);
    _preferences.add(_lookAndFeel);


    // Items in menu PREFERENCES.
    _conflicts = new CmdMenu(DConst.CONFLICTS);//, this);
    _conflicts.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _conflicts.setCommand(new ConflictCmd());
    _conflicts.addActionListener(_dApplic);
    _preferences.add(_conflicts);


    // Items in menu PREFERENCES.
    _view = new JMenu(DConst.DISPLAY_TT);
    _view.setFont( new java.awt.Font(_mfont, _font, _nPT));

    _viewSimple = new CmdMenu(DConst.SIMPLE);
    _viewSimple.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _viewSimple.setCommand(new ViewSimpleCmd());
    _viewSimple.addActionListener(_dApplic);
    _view.add(_viewSimple);

    _viewDetailedHorizontal = new CmdMenu(DConst.SPLIT_HORIZONTAL);//, this);
    _viewDetailedHorizontal.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _viewDetailedHorizontal.setCommand(new ViewHorizontalSplitCmd());
    _viewDetailedHorizontal.addActionListener(_dApplic);
    _view.add(_viewDetailedHorizontal);

    _viewDetailedVertical = new CmdMenu(DConst.SPLIT_VERTICAL);//, this);
    _viewDetailedVertical.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _viewDetailedVertical.setCommand(new ViewVerticalSplitCmd());
    _viewDetailedVertical.addActionListener(_dApplic);
    _view.add(_viewDetailedVertical);

    _preferences.add(_view);

  } // end createPreferencesMenu

  private void createManualPreferencesMenu(JFrame jframe){
    //Build the menu PREFERENCES.
    _preferences = new JMenu("Affichage");
    _preferences.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add(_preferences);

    // Items in menu PREFERENCES.
    _viewSimple = new CmdMenu("simple");//, this);
    _viewSimple.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _viewSimple.setCommand(new DoNothingCmd(jframe));
    _viewSimple.addActionListener(_dApplic);
    _preferences.add(_viewSimple);

    // Items in menu PREFERENCES.
    _viewDetailedHorizontal = new CmdMenu("Det + Split H");//, this);
    _viewDetailedHorizontal.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _viewDetailedHorizontal.setCommand(new DoNothingCmd(jframe));
    _viewDetailedHorizontal.addActionListener(_dApplic);
    _preferences.add(_viewDetailedHorizontal);

    _viewDetailedVertical = new CmdMenu("Det + Split V");//, this);
    _viewDetailedVertical.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _viewDetailedVertical.setCommand(new DoNothingCmd(jframe));
    _viewDetailedVertical.addActionListener(_dApplic);
    _preferences.add(_viewDetailedVertical);


  } // end createPreferencesMenu

  private void createHelpMenu(){
    _help = new JMenu(DConst.HELP);
    _help.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _help );
    // Items in menu HELP.
    _about = new CmdMenu(DConst.APP_NAME + ": " + DConst.ABOUT_M);//, this);

    _about.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _about.setCommand(new AboutCmd());
    _about.addActionListener(_dApplic);
    _help.add(_about);
  } // end createHelpMenu

  private void createDevelopmentMenu(){
    _dev = new JMenu("Development");
    _dev.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _dev );
    // Items in menu Development.
    _myFile = new CmdMenu("fichier1.dia");
    _myFile.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _myFile.setCommand(new MyFileCmd());
    _myFile.addActionListener(_dApplic);
    _dev.add(_myFile);



    _showAll = new CmdMenu("showAllMenus");
    _showAll.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _showAll.setCommand(new ShowAllCmd());
    _showAll.addActionListener(_dApplic);
    _dev.add(_showAll);

    _stateZero = new CmdMenu("stateZero");
    _stateZero.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _stateZero.setCommand(new StateZeroCmd());
    _stateZero.addActionListener(_dApplic);
    _dev.add(_stateZero);

    // selective import
    _importSelect = new JMenu(DConst.IMP_SELECT);
    _importSelect.setFont( new java.awt.Font(_mfont, _font, _nPT));

    // instructor selective import
    _importSelectInst = new CmdMenu(DConst.IMP_SELECT_INST);
    _importSelectInst.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _importSelectInst.setCommand(new ImportSelectiveFileCmd(_dApplic.getJFrame(),DConst.IMP_SELECT_INST));
    _importSelectInst.addActionListener(_dApplic);
    _importSelect.add(_importSelectInst);

    // room selective import
    _importSelectRoom = new CmdMenu(DConst.IMP_SELECT_ROOM);
    _importSelectRoom.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _importSelectRoom.setCommand(new ImportSelectiveFileCmd(_dApplic.getJFrame(),DConst.IMP_SELECT_ROOM));
    _importSelectRoom.addActionListener(_dApplic);
    _importSelect.add(_importSelectRoom);

    // activity selective import
    _importSelectAct = new CmdMenu(DConst.IMP_SELECT_ACT);
    _importSelectAct.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _importSelectAct.setCommand(new ImportSelectiveFileCmd(_dApplic.getJFrame(),DConst.IMP_SELECT_ACT));
    _importSelectAct.addActionListener(_dApplic);
    _importSelect.add(_importSelectAct);

    // students selective import
    _importSelectStud = new CmdMenu(DConst.IMP_SELECT_STUD);
    _importSelectStud.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _importSelectStud.setCommand(new ImportSelectiveFileCmd(_dApplic.getJFrame(),DConst.IMP_SELECT_STUD));
    _importSelectStud.addActionListener(_dApplic);
    _importSelect.add(_importSelectStud);

    _dev.add(_importSelect);

  } // end createDevelopmentMenu

  private void setAlways() {
    //the menu _file
    _boolDefineFiles = true;

    _boolExit = true;

    //the menu _preferences
    _boolMenuPreferences= true;
    _boolLookAndFeel =true;

    //the menu _help
    _boolMenuHelp= true;
    _boolAbout = true;
  } // end setAlways

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

    //the menu help
    // always _boolHelp= true;
    // always _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
      _boolMenuDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
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
    _boolDoOptimization= false;
    _boolStudentsRepartition = false;


    //the report menu
    _boolMenuReport = true;

    //the menu preferences
    // always_boolPreferences= true;
    // always _boolLookAndFeel =true;

    //the menu help
    // always _boolHelp= true;
    // always _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
      _boolMenuDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
    setMenus();
  } //end setReadyToBuildTT()

  private void setAfterInitialAssign() {
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

    _boolMenuOptimization = true;
    _boolInitialAssign = false;
    _boolDoOptimization= true;
    _boolStudentsRepartition = true;


    //the report menu
    _boolMenuReport = true;

    //the menu preferences
    // always_boolPreferences= true;
    // always _boolLookAndFeel =true;

    //the menu help
    // always _boolHelp= true;
    // always _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
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
    if (_DEVELOPMENT) {
      _boolMenuDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
    setMenus();
  }

  private void setNewTTEx() {
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
    if (_DEVELOPMENT) {
      _boolMenuDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
    setMenus();
  }

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
    if (_DEVELOPMENT) {
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
    //_boolMOpti= true;

    //the report menu
    _boolMenuReport = true;

    //the menu preferences
    _boolMenuPreferences= true;
    _boolLookAndFeel =true;

    //the menu help
    _boolMenuHelp= true;
    _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
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
    if (_DEVELOPMENT) {
      if (_boolMenuDev)
        setDevMenu();
      else
        _dev.setEnabled(_boolMenuDev);
    }
    //System.out.println("setMenusEnd");
  }

  private void setFileMenu() {
    _file.setEnabled(_boolMenuFile);
    if (_boolNewTTable) {
      _newTTable.setEnabled(_boolNewTTable);
      _newTTableCy.setEnabled(_boolNewTTableCy);
      _newNTTableEx.setEnabled(_boolNewTTableEx);
      } else _newTTable.setEnabled(_boolNewTTable);
    if (_boolNewTTStruc) {
      _newTTStruc.setEnabled(_boolNewTTStruc);
      _newTTStrucCy.setEnabled(_boolNewTTStrucCy);
      _newTTStrucEx.setEnabled(_boolNewTTStrucEx);
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
      _studentsRepartition.setEnabled(_boolStudentsRepartition);

  }
  private void setReportMenu() {
    _report.setEnabled(_boolMenuReport);
  }
  private void setPreferencesMenu() {
    _preferences.setEnabled(_boolMenuPreferences);
    _lookAndFeel.setEnabled(_boolLookAndFeel);
  }

  private void setHelpMenu() {
    _help.setEnabled(_boolMenuHelp);
    _about.setEnabled(_boolAbout);
  }

  private void setDevMenu() {
    _dev.setEnabled(_boolMenuDev);
    _myFile.setEnabled(_boolMyFile);
    _showAll.setEnabled(_boolShowAll);
    _stateZero.setEnabled(_boolStateZero);
  }

/* For Menu file
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
} /* end class DMenuBar */