/**
 *
 * Title: DMenuBar $Revision: 1.56 $  $Date: 2003-09-05 14:50:29 $
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
 * @version $Revision: 1.56 $
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
import dInterface.dUtil.*;

public class DMenuBar extends JMenuBar{
  private DApplication _dApplic;
  private int _currentState;
  private final boolean _DEVELOPMENT = true;


  private final String _mfont = DConst.MFONTDialog;
  private final int _font = Font.PLAIN;
  private final int _nPT = DConst.NPT11;
  //the menus
  private JMenu _file, _assign, _optimisation, _preferences, _help,  _dev;
  private boolean _boolFile, _boolAssign, _boolOptimisation, _boolPreferences, _boolHelp, _boolDev;
  // the file menus containing sub menus
  private JMenu _newTTable, _newTTStruc;
  private boolean _boolNewTTable, _boolNewTTStruc;
  // the file menus
  private CmdMenu _newTTableCy, _newNTTableEx, _newTTStrucCy, _newTTStrucEx, _openTTable, _openTTStruc,
  _close, _save, _saveAs, _defineFiles, _import, _export, _exit;
  private boolean _boolNewTTableCy, _boolNewTTableEx, _boolNewTTStrucCy, _boolNewTTStrucEx, _boolOpenTTable, _boolOpenTTStruc,
  _boolClose, _boolSave, _boolSaveAs,_boolDefineFiles, _boolImport, _boolExport, _boolExit;
  // the edit menus

  // the assign menus
  private CmdMenu _mActi, _section, _instructorAvailability, _roomsAvailability,
  _mExcl, _mConfl;
  // the optimisation menus
  private CmdMenu _mOpti;
  // the preferences menus
  private CmdMenu _mPLAF;
  // the help menus
  private CmdMenu _about;
  private boolean _boolAbout;

  private CmdMenu _hello;
  private boolean _boolHello;

  private int [] [] automaton = {{1,1},{0,0},{2,2}};
  public DMenuBar(DApplication dApplic) {
    super();
    _dApplic = dApplic;
    createMenuBar();

   /* for (int i = 0; i < automaton.length; i++)
      for (int j = 0; j < automaton[i].length; j++)
        System.out.println( i + " " +j + " " + automaton [i][j]) ;*/
  }

  private void createMenuBar() {
    createFileMenu();
    createAssignMenu();
    createOptimisationMenu();
    createPreferencesMenu();
    createHelpMenu();
    if (_DEVELOPMENT)
      createDevelopmentMenu();

    _currentState = 0;
    stateZero();
    setMenus();
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

    CmdMenu mClose = new CmdMenu(DConst.CLOSE);
    _file.add(mClose);
    mClose.setFont( new java.awt.Font(_mfont, _font, _nPT) );
    mClose.setCommand(new CloseCmd());
    mClose.addActionListener(_dApplic);

    _file.addSeparator();

    CmdMenu mSave = new CmdMenu(DConst.SAVE);
    _file.add(mSave);
    mSave.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mSave.setCommand(new SaveCmd());
    mSave.addActionListener(_dApplic);

    CmdMenu mSaveAs = new CmdMenu(DConst.SAVE_AS);
    _file.add(mSaveAs);
    mSaveAs.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mSaveAs.setCommand(new SaveAsCmd());
    mSaveAs.addActionListener(_dApplic);

    _file.addSeparator();

    CmdMenu mDefF = new CmdMenu(DConst.DEF_F_M);
    _file.add(mDefF);
    mDefF.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mDefF.setCommand(new DefFilesToImportCmd());
    mDefF.addActionListener(_dApplic);

    CmdMenu mImpA = new CmdMenu(DConst.IMP_A_M);
    _file.add(mImpA);
    mImpA.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mImpA.setCommand(new ImportCmd(_dApplic.getJFrame()));
    mImpA.addActionListener(_dApplic);

    CmdMenu mExpo = new CmdMenu(DConst.EXPO);
    _file.add(mExpo);
    mExpo.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mExpo.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
    mExpo.addActionListener(_dApplic);

    _file.addSeparator();

    CmdMenu mExit = new CmdMenu(DConst.EXIT);
    _file.add(mExit);
    mExit.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mExit.setCommand(new ExitCmd());
    mExit.addActionListener(_dApplic);
  }

  private void createAssignMenu() {

    //Build the menu ASSIGN.
    _assign = new JMenu(DConst.ASSIGN);
    _assign.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add(_assign);

    CmdMenu mActi = new CmdMenu(DConst.ACTI_ASSIGN_M);
    _assign.add(mActi);
    mActi.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mActi.setCommand(new ActivityCmd());
    mActi.addActionListener(_dApplic);

    _section = new CmdMenu(DConst.GROUP_ASSIGN_M);
    _assign.add(_section);
    _section.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _section.setCommand(new GroupCmd());
    _section.addActionListener(_dApplic);

    _instructorAvailability = new CmdMenu(DConst.INST_ASSIGN_M);
    _assign.add(_instructorAvailability);
    _instructorAvailability.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _instructorAvailability.setCommand(new InstructorAvailabilityCmd());
    _instructorAvailability.addActionListener(_dApplic);

    //CmdMenu mroomsAvailability = new CmdMenu(DConst.INST_ASSIGN_M);
    CmdMenu _roomsAvailability = new CmdMenu(DConst.LOCAUX_ASSIGN_M);
    _assign.add(_roomsAvailability);
    _roomsAvailability.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _roomsAvailability.setCommand(new roomsAvailabilityCmd(_dApplic));
    _roomsAvailability.addActionListener(_dApplic);

    CmdMenu mExcl = new CmdMenu("Evenements");
    _assign.add(mExcl);
    mExcl.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mExcl.setCommand(new EventsCmd());
    mExcl.addActionListener(_dApplic);

    _assign.addSeparator();

    CmdMenu mConfl = new CmdMenu("Liste de Conflits");
    _assign.add(mConfl);
    mConfl.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mConfl.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
    mConfl.addActionListener(_dApplic);

  }

  private void createOptimisationMenu() {
    //Build the menu Optimisation.
    _optimisation = new JMenu("Opti");//DConst.PREF);
    _optimisation.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _optimisation );
// Items in menu PREFERENCES.
    CmdMenu mXPLAF = new CmdMenu(DConst.PLAF_M);//, this);
    _optimisation.add(mXPLAF);
    mXPLAF.setFont(new java.awt.Font(_mfont, _font, _nPT));
    mXPLAF.setCommand(new PLAFCmd(_dApplic));
    mXPLAF.addActionListener(_dApplic);

  }

  private void createPreferencesMenu(){

    //Build the menu PREFERENCES.
    _preferences = new JMenu(DConst.PREF);
    _preferences.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _preferences );
    // Items in menu PREFERENCES.
    CmdMenu mPLAF = new CmdMenu(DConst.PLAF_M);//, this);
    _preferences.add(mPLAF);
    mPLAF.setFont(new java.awt.Font(_mfont, _font, _nPT));
    mPLAF.setCommand(new PLAFCmd(_dApplic));
    mPLAF.addActionListener(_dApplic);

  }
  private void createHelpMenu(){

    _help = new JMenu(DConst.HELP);
    _help.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _help );
    // Items in menu HELP.
    _about = new CmdMenu(DConst.ABOUT_M + DConst.APP_NAME);//, this);
    _help.add(_about);
    _about.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _about.setCommand(new AboutCmd(1));
    _about.addActionListener(_dApplic);

  }

  private void createDevelopmentMenu(){

    _dev = new JMenu("Development");
    _dev.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    this.add( _dev );
    // Items in menu Development.
    _hello = new CmdMenu("fichier1.dia");
    _dev.add(_hello);
    _hello.setFont(new java.awt.Font(_mfont, _font, _nPT));
    _hello.setCommand(new helloCmd(_dApplic));
    _hello.addActionListener(_dApplic);

  }

  private void stateZero() {
    _boolFile= true;
    _boolNewTTable = true;
    _boolNewTTableCy = _boolNewTTableEx = true;

    _boolNewTTStruc = true;
    _boolNewTTStrucCy = _boolNewTTStrucEx = true;
    _boolOpenTTable  = false;
    _boolOpenTTStruc = false;

    _boolAssign= true;
    _boolOptimisation=  true;
    _boolPreferences= true;
    _boolHelp= false;
    _boolAbout = true;
    _boolDev = false;


  }
  private void stateOne() {
    _boolFile= true;
    _boolAssign= true;
    _boolOptimisation=  true;
    _boolPreferences= true;
    _boolHelp= true;
    _boolAbout = false;


  }


  private void setMenus() {
    if (_boolFile)
      setFileMenu();
    else _file.setEnabled(_boolFile);
    if (_boolAssign)
      setAssignMenu();
    else _assign.setEnabled(_boolAssign);
    if (_boolOptimisation)
      setOptimisationMenu();
    else _optimisation.setEnabled(_boolOptimisation);
    if (_boolPreferences)
      setPreferencesMenu();
    else _optimisation.setEnabled(_boolPreferences);
    if (_boolHelp)
      setHelpMenu();
    else _optimisation.setEnabled(_boolHelp);
    if (_boolDev)
      setDevMenu();
    else _optimisation.setEnabled(_boolDev);
  }

  public void setFileMenu() {
    if (_boolNewTTable) {
      _newTTableCy.setEnabled(_boolNewTTableCy);
      _newNTTableEx.setEnabled(_boolNewTTableEx);
      } else _newTTable.setEnabled(_boolNewTTable);
    if (_boolNewTTStruc) {
      _newTTStrucCy.setEnabled(_boolNewTTStrucCy);
      _newTTStrucEx.setEnabled(_boolNewTTStrucEx);
      } else _newTTStruc.setEnabled(_boolNewTTStruc);
    _openTTable.setEnabled( _boolOpenTTable);
    _openTTStruc.setEnabled( _boolOpenTTStruc);
  //_close, _save, _saveAs, _defineFiles, _import, _export, _exit;
  //private boolean _boolNewTTableCy, _boolNewTTableEx, _boolNewTTStrucCy, _boolNewTTStrucEx, _boolOpenTTable, _boolOpenTTStruc,
  //_boolClose, _boolSave, _boolSaveAs,_boolDefineFiles, _boolImport, _boolExport, _boolExit;
  }

  public void setAssignMenu() {
/*  if (_boolFile)
    setFileMenu();
  else _file.setEnabled(_boolFile);
    // private JMenu _mNewTT, _mNewTTS;
// the file menus
    //_newTTCy, _mNTTEx, _mNTTSCy, _mNTTSEx, _mOpenTT, _mOpenTTS,
//_mClose, _mSave, _mSaveAs, _mDefF, _mImpA, _mExpo, _mExit;*/
  }

  public void setOptimisationMenu() {
 /* if (_boolFile)
    setFileMenu();
  else _file.setEnabled(_boolFile);
    // private JMenu _mNewTT, _mNewTTS;
// the file menus
    //_newTTCy, _mNTTEx, _mNTTSCy, _mNTTSEx, _mOpenTT, _mOpenTTS,
//_mClose, _mSave, _mSaveAs, _mDefF, _mImpA, _mExpo, _mExit;*/
  }
  public void setPreferencesMenu() {
 /* if (_boolFile)
    setFileMenu();
  else _file.setEnabled(_boolFile);
    // private JMenu _mNewTT, _mNewTTS;
// the file menus
    //_newTTCy, _mNTTEx, _mNTTSCy, _mNTTSEx, _mOpenTT, _mOpenTTS,
//_mClose, _mSave, _mSaveAs, _mDefF, _mImpA, _mExpo, _mExit;*/
  }

  public void setHelpMenu() {
    _about.setEnabled(_boolAbout);
  }

  public void setDevMenu() {
    _dev.setEnabled(_boolAbout);
  }

  public void setNewState(int transition) {
    int newState = automaton[_currentState][transition];
    switch (newState){
      case (0) : stateZero(); break;
      case (1) : stateOne(); break;
    }
    setMenus();
    _currentState= newState;
  }
} /* end class DMenuBar */