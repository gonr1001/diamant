/**
 *
 * Title: DMenuBar $Revision: 1.27 $  $Date: 2003-06-09 10:23:40 $
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
 * @version $Revision: 1.27 $
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

import dInterface.dData.*;
import dInterface.dTimeTable.NewTTExCmd;
import dInterface.dTimeTable.NewTTCyCmd;
import dInterface.dTimeTable.OpenTTCmd;
import dInterface.dTimeTable.NewTTSCmd;
import dInterface.dTimeTable.OpenTTSCmd;
import dInterface.dTimeTable.SaveAsCmd;
import dInterface.dTimeTable.SaveCmd;
import dInterface.dTimeTable.CloseCmd;
import dInterface.dUtil.*;

public class DMenuBar extends JMenuBar{
  private DApplication _dApplic;
  private int state;
  public DMenuBar(DApplication dApplic) {
    super();
    _dApplic = dApplic;
    createMenuBar();
  }

  private void createMenuBar() {
    String mfont = DConst.MFONTDialog;
    int font = Font.PLAIN;
    int nPT = DConst.NPT11;

    //Build the menu FILE.
    JMenu menu = new JMenu(DConst.FILE);
    menu.setFont( new java.awt.Font( mfont, font, nPT ) );
    this.add( menu );

    // Items in menu FILE.
    /* This menu will be deleted in the final version */
    CmdMenu mHello = new CmdMenu("Hello");
    menu.add(mHello);
    mHello.setFont( new java.awt.Font( mfont, font, nPT ) );
    mHello.setCommand(new HelloCmd());
    mHello.addActionListener(_dApplic);

    menu.addSeparator();

    JMenu mNewTT = new JMenu(DConst.NEW_TT);
    mNewTT.setFont( new java.awt.Font( mfont, font, nPT ) );


    CmdMenu mNTTEx = new CmdMenu(DConst.NTT_EX);
    mNTTEx.setFont( new java.awt.Font( mfont, font, nPT ) );
    mNTTEx.setCommand(new NewTTExCmd());
    mNTTEx.addActionListener(_dApplic);
    mNewTT.add(mNTTEx);

    CmdMenu mNTTCy = new CmdMenu(DConst.NTT_CY);
    mNTTCy.setFont( new java.awt.Font( mfont, font, nPT ) );
    mNTTCy.setCommand(new NewTTCyCmd());
    mNTTCy.addActionListener(_dApplic);
    mNewTT.add(mNTTCy);

    menu.add(mNewTT);

    CmdMenu mOpenTT = new CmdMenu(DConst.OPEN);
    menu.add(mOpenTT);
    mOpenTT.setFont( new java.awt.Font( mfont, font, nPT ) );
    mOpenTT.setCommand(new OpenTTCmd());
    mOpenTT.addActionListener(_dApplic);

    CmdMenu mClose = new CmdMenu(DConst.CLOSE);
    menu.add(mClose);
    mClose.setFont( new java.awt.Font( mfont, font, nPT ) );
    mClose.setCommand(new CloseCmd());
    mClose.addActionListener(_dApplic);

    menu.addSeparator();




    CmdMenu mSave = new CmdMenu(DConst.SAVE);
    menu.add(mSave);
    mSave.setFont( new java.awt.Font( mfont, font, nPT ) );
    mSave.setCommand(new SaveCmd());
    mSave.addActionListener(_dApplic);

    CmdMenu mSaveAs = new CmdMenu(DConst.SAVE_AS);
    menu.add(mSaveAs);
    mSaveAs.setFont( new java.awt.Font( mfont, font, nPT ) );
    mSaveAs.setCommand(new SaveAsCmd());
    mSaveAs.addActionListener(_dApplic);

    menu.addSeparator();

    JMenu mDTTS = new JMenu(DConst.DTTS);
    mDTTS.setFont( new java.awt.Font( mfont, font, nPT ) );


    CmdMenu mNTTS = new CmdMenu(DConst.NTTS);
    mNTTS.setFont( new java.awt.Font( mfont, font, nPT ) );
    mNTTS.setCommand(new NewTTSCmd());
    mNTTS.addActionListener(_dApplic);
    mDTTS.add(mNTTS);

    CmdMenu mOTTS = new CmdMenu(DConst.OTTS);
    mOTTS.setFont( new java.awt.Font( mfont, font, nPT ) );
    mOTTS.setCommand(new OpenTTSCmd());
    mOTTS.addActionListener(_dApplic);
    mDTTS.add(mOTTS);

    menu.add(mDTTS);

    menu.addSeparator();

    /*CmdMenu mImpM = new CmdMenu(DConst.IMP_M);
    menu.add(mImpM);
    mImpM.setFont( new java.awt.Font( mfont, font, nPT ) );
    mImpM.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
    mImpM.addActionListener(_dApplic);*/


    CmdMenu mDefF = new CmdMenu(DConst.DEF_F_M);
    menu.add(mDefF);
    mDefF.setFont( new java.awt.Font( mfont, font, nPT ) );
    mDefF.setCommand(new DefFilesToImportCmd());
    mDefF.addActionListener(_dApplic);

    CmdMenu mImpA = new CmdMenu(DConst.IMP_A_M);
    menu.add(mImpA);
    mImpA.setFont( new java.awt.Font( mfont, font, nPT ) );
    mImpA.setCommand(new ImportCmd(_dApplic.getJFrame()));
    mImpA.addActionListener(_dApplic);


    CmdMenu mExpo = new CmdMenu(DConst.EXPO);
    menu.add(mExpo);
    mExpo.setFont( new java.awt.Font( mfont, font, nPT ) );
    mExpo.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
    mExpo.addActionListener(_dApplic);

    menu.addSeparator();

    CmdMenu mExit = new CmdMenu(DConst.EXIT);
    menu.add(mExit);
    mExit.setFont( new java.awt.Font( mfont, font, nPT ) );
    mExit.setCommand(new ExitCmd());
    mExit.addActionListener(_dApplic);

/*
    //Build the menu EDIT.
    menu = new JMenu(DConst.EDIT);
    //menu.setFont( new java.awt.Font( mfont, font, nPT ) );
    this.add( menu );

    // Items in menu EDIT.
    CmdMenu mCpy = new CmdMenu(DConst.COPY);
    menu.add(mCpy);
    //mNew.setFont( new java.awt.Font( mfont, font, nPT ) );
    mCpy.setCommand(new DoNothingCmd(_dApplic.getJFrame())); //this, med));
    mCpy.addActionListener(_dApplic);
    CmdMenu mTTDef = new CmdMenu("TTDialog");//, this);
    menu.add(mTTDef);
    mTTDef.setFont(new java.awt.Font(mfont, font, nPT));
    mTTDef.setCommand(new TTDefinitionCmd());//_dApplic.getJFrame(), _dApplic.getDMediator()));
    mTTDef.addActionListener(_dApplic);

*/
    //Build the menu ASSIGN.
    menu = new JMenu(DConst.ASSIGN);
    menu.setFont( new java.awt.Font( mfont, font, nPT ) );
    this.add( menu );

    CmdMenu mActi = new CmdMenu("Activit�s");
    menu.add(mActi);
    mActi.setFont( new java.awt.Font( mfont, font, nPT ) );
    mActi.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
    mActi.addActionListener(_dApplic);

    CmdMenu mSect = new CmdMenu("Groupes");
    menu.add(mSect);
    mSect.setFont( new java.awt.Font( mfont, font, nPT ) );
    mSect.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
    mSect.addActionListener(_dApplic);

    // Items in menu ASSIGN
    CmdMenu mInstructorAvailability = new CmdMenu(DConst.INST_ASSIGN_M);
    menu.add(mInstructorAvailability);
    mInstructorAvailability.setFont(new java.awt.Font(mfont, font, nPT));
    mInstructorAvailability.setCommand(new InstructorAvailabilityCmd());
    mInstructorAvailability.addActionListener(_dApplic);

    CmdMenu mExcl = new CmdMenu("Exclure");
    menu.add(mExcl);
    mExcl.setFont( new java.awt.Font( mfont, font, nPT ) );
    mExcl.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
    mExcl.addActionListener(_dApplic);

     menu.addSeparator();

     CmdMenu mConfl = new CmdMenu("Liste de Conflits");
     menu.add(mConfl);
     mConfl.setFont( new java.awt.Font( mfont, font, nPT ) );
     mConfl.setCommand(new DoNothingCmd(_dApplic.getJFrame()));
     mConfl.addActionListener(_dApplic);

    //Build the menu PREFERENCES.
    menu = new JMenu(DConst.PREF);
    menu.setFont( new java.awt.Font( mfont, font, nPT ) );
    this.add( menu );
    // Items in menu PREFERENCES.
    CmdMenu mPLAF = new CmdMenu(DConst.PLAF_M);//, this);
    menu.add(mPLAF);
    mPLAF.setFont(new java.awt.Font(mfont, font, nPT));
    mPLAF.setCommand(new PLAFCmd(_dApplic));
    mPLAF.addActionListener(_dApplic);

    //Build the menu HELP.
    menu = new JMenu(DConst.HELP);
    menu.setFont( new java.awt.Font( mfont, font, nPT ) );
    this.add( menu );
    // Items in menu HELP.
    CmdMenu mAbout = new CmdMenu(DConst.ABOUT_M + DConst.APP_NAME);//, this);
    menu.add(mAbout);
    mAbout.setFont(new java.awt.Font(mfont, font, nPT));
    mAbout.setCommand(new AboutCmd());
    mAbout.addActionListener(_dApplic);

    state = 0;
  } // end createMenus

} /* end class DMenuBar */