/**
 *
 * Title: DMenuBar $Revision: 1.53 $  $Date: 2003-09-02 08:45:05 $
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
 * @version $Revision: 1.53 $
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
  private int _state;

  private final String _mfont = DConst.MFONTDialog;
  private final int _font = Font.PLAIN;
  private final int _nPT = DConst.NPT11;
  //the menus
  private JMenu _file, _assign, _optimisation, _preferences, _help;
  // the file menus containing sub menus
  private JMenu _mNewTT, _mNewTTS;
  // the file menus
  private CmdMenu _mNTTCy, _mNTTEx, _mNTTSCy, _mNTTSEx, _mOpenTT, _mOpenTTS,
  _mClose, _mSave, _mSaveAs, _mDefF, _mImpA, _mExpo, _mExit;
  // the edit menus

  // the assign menus
  private CmdMenu _mActi, _mSect, _mInstructorAvailability, _mroomsAvailability,
  _mExcl, _mConfl;
  // the optimisation menus
  private CmdMenu _mOpti;
  // the preferences menus
  private CmdMenu _mPLAF;
  // the help menus
  private CmdMenu _mAbout;



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

    _state = 0;
    stateZero();
  } // end createMenus

  public void createFileMenu() {
    //Build the menu FILE.
    _file = new JMenu(DConst.FILE);
    _file.setFont(new java.awt.Font( _mfont, _font, _nPT ));
    this.add(_file);

    // Items in menu FILE.
    CmdMenu mHello = new CmdMenu("fichier1.dia");
    _file.add(mHello);
    mHello.setFont(new java.awt.Font(_mfont, _font, _nPT));
    mHello.setCommand(new helloCmd(_dApplic));
    mHello.addActionListener(_dApplic);


    _mNewTT = new JMenu(DConst.NEW_TT);
    _mNewTT.setFont( new java.awt.Font(_mfont, _font, _nPT));

    _mNTTCy = new CmdMenu(DConst.NTT_CY);
    _mNTTCy.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _mNTTCy.setCommand(new NewTTCyCmd());
    _mNTTCy.addActionListener(_dApplic);
    _mNewTT.add(_mNTTCy);

    _mNTTEx = new CmdMenu(DConst.NTT_EX);
    _mNTTEx.setFont( new java.awt.Font(_mfont, _font, _nPT));
    _mNTTEx.setCommand(new NewTTExCmd());
    _mNTTEx.addActionListener(_dApplic);
    _mNewTT.add(_mNTTEx);

    _file.add(_mNewTT);

    _mNewTTS = new JMenu(DConst.NEW_TTS);
    _mNewTTS.setFont(new java.awt.Font(_mfont, _font, _nPT ));

    _mNTTSCy = new CmdMenu(DConst.NTTS_CY);
    _mNTTSCy.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _mNTTSCy.setCommand(new NewTTSCyCmd());
    _mNTTSCy.addActionListener(_dApplic);
    _mNewTTS.add(_mNTTSCy);

    _mNTTSEx = new CmdMenu(DConst.NTTS_EX);
    _mNTTSEx.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    _mNTTSEx.setCommand(new NewTTSExCmd());
    _mNTTSEx.addActionListener(_dApplic);
    _mNewTTS.add(_mNTTSEx);

    _file.add(_mNewTTS);

    CmdMenu mOpenTT = new CmdMenu(DConst.OPEN);
    _file.add(mOpenTT);
    mOpenTT.setFont(new java.awt.Font(_mfont, _font, _nPT));
    mOpenTT.setCommand(new OpenTTCmd());
    mOpenTT.addActionListener(_dApplic);

    CmdMenu mOpenTTS = new CmdMenu(DConst.OPEN_TTS);
    _file.add(mOpenTTS);
    mOpenTTS.setFont(new java.awt.Font( _mfont, _font, _nPT));
    mOpenTTS.setCommand(new OpenTTSCmd());
    mOpenTTS.addActionListener(_dApplic);

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

    CmdMenu mSect = new CmdMenu(DConst.GROUP_ASSIGN_M);
    _assign.add(mSect);
    mSect.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mSect.setCommand(new GroupCmd());
    mSect.addActionListener(_dApplic);

    CmdMenu mInstructorAvailability = new CmdMenu(DConst.INST_ASSIGN_M);
    _assign.add(mInstructorAvailability);
    mInstructorAvailability.setFont(new java.awt.Font(_mfont, _font, _nPT));
    mInstructorAvailability.setCommand(new InstructorAvailabilityCmd());
    mInstructorAvailability.addActionListener(_dApplic);

    //CmdMenu mroomsAvailability = new CmdMenu(DConst.INST_ASSIGN_M);
    CmdMenu mroomsAvailability = new CmdMenu(DConst.LOCAUX_ASSIGN_M);
    _assign.add(mroomsAvailability);
    mroomsAvailability.setFont(new java.awt.Font(_mfont, _font, _nPT));
    mroomsAvailability.setCommand(new roomsAvailabilityCmd(_dApplic));
    mroomsAvailability.addActionListener(_dApplic);

    CmdMenu mExcl = new CmdMenu("Exclure");
    _assign.add(mExcl);
    mExcl.setFont( new java.awt.Font( _mfont, _font, _nPT ) );
    mExcl.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
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
    CmdMenu mAbout = new CmdMenu(DConst.ABOUT_M + DConst.APP_NAME);//, this);
    _help.add(mAbout);
    mAbout.setFont(new java.awt.Font(_mfont, _font, _nPT));
    mAbout.setCommand(new AboutCmd());
    mAbout.addActionListener(_dApplic);

  }

  public void stateZero() {
    //_file.setEnabled(false);
    //_mNewTT.setEnabled(false);
    _mNTTCy.setEnabled(true);
    _mNTTEx.setEnabled(true);

  }

} /* end class DMenuBar */