/**
 *
 * Title: DMenuBar $Revision: 1.64 $  $Date: 2003-09-09 13:44:42 $
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
 * @version $Revision: 1.64 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import dResources.DConst;
import dAux.DoNothingCmd;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;

import dInterface.dAffectation.*;
import dInterface.dData.*;
import dInterface.dTimeTable.NewTTExCmd;
import dInterface.dTimeTable.NewTTCyCmd;
import dInterface.dTimeTable.OpenTTCmd;
import dInterface.dTimeTable.NewTTSCyCmd;
import dInterface.dTimeTable.NewTTSExCmd;
import dInterface.dTimeTable.OpenTTSCmd;
import dInterface.dTimeTable.SaveAsCmd;
import dInterface.dTimeTable.SaveCmd;
import dInterface.dTimeTable.CloseCmd;

import dInterface.dUtil.AboutCmd;
import dInterface.dUtil.ExitCmd;
import dInterface.dUtil.PLAFCmd;

import dAux.MyFileCmd;
import dAux.ShowAllCmd;
import dAux.StateZeroCmd;


public class DMenuBar extends JMenuBar{
  private DApplication _dApplic;
  private final boolean _DEVELOPMENT = true;
  private final String _mfont = DConst.MFONTDialog;
  private final int _font = Font.PLAIN;
  private final int _nPT = DConst.NPT11;

  //the menus
  private JMenu _file, _assign, _optimisation, _preferences, _help,  _dev;
  private boolean _boolFile, _boolAssign, _boolOptimization, _boolPreferences, _boolHelp, _boolDev;
  // the file menus containing sub menus
  private JMenu _newTTable, _newTTStruc;
  private boolean _boolNewTTable, _boolNewTTStruc;
  // the file menus
  private CmdMenu _newTTableCy, _newNTTableEx, _newTTStrucCy,
  _newTTStrucEx, _openTTable, _openTTStruc,
  _close, _save, _saveAs, _defineFiles, _import, _export, _exit;
  private boolean _boolNewTTableCy, _boolNewTTableEx, _boolNewTTStrucCy,
  _boolNewTTStrucEx, _boolOpenTTable, _boolOpenTTStruc,
  _boolClose, _boolSave, _boolSaveAs,_boolDefineFiles,
  _boolImport, _boolExport, _boolExit;
  // the edit menus

  // the assign menus
  private CmdMenu _activities, _sections, _instructorAvailability,
  _roomsAvailability, _events,
  _mExcl, _mConfl;
  private boolean _boolActivities, _boolSections, _boolInstructorAvailability,
  _boolRoomsAvailability, _boolEvents, _boolExcl, _boolConfl;

  // the optimisation menus
  private CmdMenu _mOpti;
  private boolean _boolMOpti;
  // the preferences menus
  private CmdMenu _lookAndFeel;
  private boolean _boolLookAndFeel;
  // the help menus
  private CmdMenu _about;
  private boolean _boolAbout;

  private CmdMenu _myFile, _showAll, _stateZero;
  private boolean _boolMyFile, _boolShowAll, _boolStateZero;


  public DMenuBar(DApplication dApplic) {
    super();
    _dApplic = dApplic;
    createMenuBar();
  }

  private void createMenuBar() {
    createFileMenu();
    createAssignMenu();
    createOptimisationMenu();
    createPreferencesMenu();
    createHelpMenu();
    if (_DEVELOPMENT)
      createDevelopmentMenu();
    setAlways();
    setZero(); //setAll();
  } // end createMenus

  private void createFileMenu() {
    //Build the menu FILE.
    _file = new JMenu(DConst.FILE);
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

    _close = new CmdMenu(DConst.CLOSE);
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
    _export.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
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
    _sections.setCommand(new GroupCmd());
    _sections.addActionListener(_dApplic);
    _assign.add(_sections);

    _instructorAvailability = new CmdMenu(DConst.INST_ASSIGN_M);
    _instructorAvailability.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _instructorAvailability.setCommand(new InstructorAvailabilityCmd());
    _instructorAvailability.addActionListener(_dApplic);
    _assign.add(_instructorAvailability);

    _roomsAvailability = new CmdMenu(DConst.LOCAUX_ASSIGN_M);
    _roomsAvailability.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _roomsAvailability.setCommand(new roomsAvailabilityCmd(_dApplic));
    _roomsAvailability.addActionListener(_dApplic);
    _assign.add(_roomsAvailability);

    _events = new CmdMenu("Evenements");
    _events.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _events.setCommand(new EventsCmd());
    _events.addActionListener(_dApplic);
    _assign.add(_events);

    _assign.addSeparator();

    CmdMenu mConfl = new CmdMenu("Liste de Conflits");
    mConfl.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mConfl.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
    mConfl.addActionListener(_dApplic);
    _assign.add(mConfl);

  }

  private void createOptimisationMenu() {
    //Build the menu Optimisation.
    _optimisation = new JMenu("Optimisation");//DConst.PREF);
    _optimisation.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _optimisation );
    // Items in menu Optimisation.
    _mOpti = new CmdMenu(DConst.PLAF_M);//, this);
    _mOpti.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _mOpti.setCommand(new PLAFCmd(_dApplic));
    _mOpti.addActionListener(_dApplic);
    _optimisation.add(_mOpti);
  }//end createOptimisationMenu

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
  } // end createPreferencesMenu

  private void createHelpMenu(){
    _help = new JMenu(DConst.HELP);
    _help.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _help );
    // Items in menu HELP.
    _about = new CmdMenu(DConst.ABOUT_M + DConst.APP_NAME);//, this);

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
  } // end createDevelopmentMenu

  private void setAlways() {
    //the menu _file
    _boolDefineFiles = true;

    _boolExit = true;

    //the menu _preferences
    _boolPreferences= true;
    _boolLookAndFeel =true;

    //the menu _help
    _boolHelp= true;
    _boolAbout = true;
  } // end setAlways

  private void setZero() {
    //the menu _file
    _boolFile = true;
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
    _boolAssign= false;
    _boolActivities = true;
    _boolSections= true;
    _boolInstructorAvailability= true;
    _boolRoomsAvailability= true;
    _boolEvents= true;

    //the menu otimization
    _boolOptimization = false;
    _boolMOpti= true;

    //the menu preferences
    // always_boolPreferences= true;
    // always _boolLookAndFeel =true;

    //the menu help
    // always _boolHelp= true;
    // always _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
      _boolDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
    setMenus();
  }

  private void setReadyToBuildTT() {
    //the menu _file
    _boolFile = true;
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
    _boolAssign= true;
    _boolActivities = true;
    _boolSections= true;
    _boolInstructorAvailability= true;
    _boolRoomsAvailability= true;
    _boolEvents= true;

    //the menu otimization
    _boolOptimization = true;
    _boolMOpti= true;

    //the menu preferences
    // always_boolPreferences= true;
    // always _boolLookAndFeel =true;

    //the menu help
    // always _boolHelp= true;
    // always _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
      _boolDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
    setMenus();
  } //end setImport

  private void setNewTTCy() {
    //the menu _file
    _boolFile = true;
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
    _boolExport = false;
    _boolExit = true;


    //the menu assign
    _boolAssign= false;
    _boolActivities = true;
    _boolSections= true;
    _boolInstructorAvailability= true;
    _boolRoomsAvailability= true;
    _boolEvents= true;

    //the menu otimization
    _boolOptimization = false;
    _boolMOpti= true;

    //the menu preferences
    // always_boolPreferences= true;
    // always _boolLookAndFeel =true;

    //the menu help
    // always _boolHelp= true;
    // always _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
      _boolDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
    setMenus();
  }

  private void setNewTTEx() {
    //the menu _file
    _boolFile = true;
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
    _boolExport = false;
    _boolExit = true;


    //the menu assign
    _boolAssign= false;
    _boolActivities = true;
    _boolSections= true;
    _boolInstructorAvailability= true;
    _boolRoomsAvailability= true;
    _boolEvents= true;

    //the menu otimization
    _boolOptimization = false;
    _boolMOpti= true;

    //the menu preferences
    // always_boolPreferences= true;
    // always _boolLookAndFeel =true;

    //the menu help
    // always _boolHelp= true;
    // always _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
      _boolDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
    setMenus();
  }

  private void setNewTTSCy() {
    //the menu _file
    _boolFile = true;
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
    _boolAssign= false;
    _boolActivities = true;
    _boolSections= true;
    _boolInstructorAvailability= true;
    _boolRoomsAvailability= true;
    _boolEvents= true;

    //the menu otimization
    _boolOptimization = false;
    _boolMOpti= true;

    //the menu preferences
    // always_boolPreferences= true;
    // always _boolLookAndFeel =true;

    //the menu help
    // always _boolHelp= true;
    // always _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
      _boolDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
    setMenus();
  } // end setNewTTSCy

  private void setAll() {
    //the menu _file
    _boolFile = true;
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
    _boolAssign= true;
    _boolActivities = true;
    _boolSections= true;
    _boolInstructorAvailability= true;
    _boolRoomsAvailability= true;
    _boolEvents= true;

    //the menu otimization
    _boolOptimization = true;
    _boolMOpti= true;

    //the menu preferences
    _boolPreferences= true;
    _boolLookAndFeel =true;

    //the menu help
    _boolHelp= true;
    _boolAbout = true;

    //the menu dev
    if (_DEVELOPMENT) {
      _boolDev = true;
      _boolMyFile = _boolShowAll = _boolStateZero = true;
    }
    setMenus();
  } //end setAll()

  private void setMenus() {
    //System.out.println("setMenus" + _boolAssign);
    if (_boolFile)
      setFileMenu();
    else
      _file.setEnabled(_boolFile);
    // System.out.println("setMenus" + _boolAssign);
    if (_boolAssign)
      setAssignMenu();
    else
      _assign.setEnabled(_boolAssign);
    if (_boolOptimization)
      setOptimisationMenu();
    else
      _optimisation.setEnabled(_boolOptimization);
    if (_boolPreferences)
      setPreferencesMenu();
    else
      _preferences.setEnabled(_boolPreferences);
    if (_boolHelp)
      setHelpMenu();
    else
      _help.setEnabled(_boolHelp);
    if (_DEVELOPMENT) {
      if (_boolDev)
        setDevMenu();
      else
        _dev.setEnabled(_boolDev);
    }
    //System.out.println("setMenusEnd");
  }

  private void setFileMenu() {
    _file.setEnabled(_boolFile);
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
    _openTTable.setEnabled( _boolOpenTTable);
    _openTTStruc.setEnabled( _boolOpenTTStruc);
    _close.setEnabled(_boolClose);
    _save.setEnabled(_boolSave);
    _saveAs.setEnabled(_boolSaveAs);
    _defineFiles.setEnabled(_boolDefineFiles);
    _import.setEnabled(_boolImport);
    _export.setEnabled(_boolExport);
    _exit.setEnabled(_boolExit);
  } //end  setFileMenu

  private void setAssignMenu() {
    _assign.setEnabled(_boolAssign);
    _activities.setEnabled(_boolActivities);
    _sections.setEnabled(_boolSections);
    _instructorAvailability.setEnabled(_boolInstructorAvailability);
    _roomsAvailability.setEnabled(_boolRoomsAvailability);
    _events.setEnabled(_boolEvents);
  } //end setAssignMenu

  private void setOptimisationMenu() {
    _optimisation.setEnabled(_boolOptimization);
    _mOpti.setEnabled(_boolMOpti);
  }
  private void setPreferencesMenu() {
    _preferences.setEnabled(_boolPreferences);
    _lookAndFeel.setEnabled(_boolLookAndFeel);
  }

  private void setHelpMenu() {
    _help.setEnabled(_boolHelp);
    _about.setEnabled(_boolAbout);
  }

  private void setDevMenu() {
    _dev.setEnabled(_boolDev);
    _myFile.setEnabled(_boolMyFile);
    _showAll.setEnabled(_boolShowAll);
    _stateZero.setEnabled(_boolStateZero);
  }

  public void postNewTTCyCmd(){
    setNewTTCy();
  }

  public void postNewTTExCmd(){
    setNewTTCy(); //setNewTTEx();
  }

  public void postNewTTSCyCmd(){
    setNewTTSCy();
  }

  public void postNewTTSExCmd(){
    setNewTTSCy(); //setNewTTSEx();
  }

  public void postOpenTTCmd(){
    setReadyToBuildTT();
  }

  public void postOpenTTSCmd(){
    setNewTTSCy(); //setOpenTTS();
  }
  public void postImportCmd(){
    setReadyToBuildTT();
  }

  public void postAbout() {
    // no changes
  }

  public void postShowAll() {
    setAll();
  }
  public void postMyFile() {
    setReadyToBuildTT();
  }

  public void postStateZero() {
    setZero();
  }
} /* end class DMenuBar */