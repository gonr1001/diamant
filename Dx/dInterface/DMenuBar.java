/**
 *
 * Title: DMenuBar $Revision: 1.8 $  $Date: 2003-03-13 15:21:01 $
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
 * @version $Revision: 1.8 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import dResources.DConst;
import dAux.DoNothingCmd;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class DMenuBar extends JMenuBar{
  DApplication _dApplic;

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
    CmdMenu mNew = new CmdMenu(DConst.NEW);
    menu.add(mNew);
    mNew.setFont( new java.awt.Font( mfont, font, nPT ) );
    mNew.setCommand(new NewCmd());
    mNew.addActionListener(_dApplic);

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

    //Build the menu ASSIGN.
    menu = new JMenu(DConst.ASSIGN);
    menu.setFont( new java.awt.Font( mfont, font, nPT ) );
    this.add( menu );

    // Items in menu ASSIGN
    CmdMenu mInstructorAvailability = new CmdMenu(DConst.INST_ASSIGN_M);//, this);
    menu.add(mInstructorAvailability);
    mInstructorAvailability.setFont(new java.awt.Font(mfont, font, nPT));
    mInstructorAvailability.setCommand(new InstructorAvailabilityCmd());
    mInstructorAvailability.addActionListener(_dApplic);

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
  } // end createMenus

} /* end class DMenuBar */