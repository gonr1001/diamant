/**
 *
 * Title: DMenuBar $Revision: 1.4 $  $Date: 2003-01-31 16:43:14 $
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
 * @version $Revision: 1.4 $
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
  DView _dView;
  JFrame _jFrame;
  DMediator _mediator;

  public DMenuBar(DView dView, DMediator mediator) {
    super();
    _dView = dView;
    _mediator = mediator;
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
    CmdMenu mNew = new CmdMenu(DConst.NEW, _jFrame);
    menu.add(mNew);
    mNew.setFont( new java.awt.Font( mfont, font, nPT ) );
    mNew.setCommand(new NewCmd( _mediator));
    mNew.addActionListener(_dView);

    //Build the menu EDIT.
    menu = new JMenu(DConst.EDIT);
    //menu.setFont( new java.awt.Font( mfont, font, nPT ) );
    this.add( menu );

    // Items in menu EDIT.
    CmdMenu mCpy = new CmdMenu(DConst.COPY);
    menu.add(mCpy);
    //mNew.setFont( new java.awt.Font( mfont, font, nPT ) );
    mCpy.setCommand(new DoNothingCmd(_jFrame)); //this, med));
    mCpy.addActionListener(_dView);

    //Build the menu PREFERENCES.
    menu = new JMenu(DConst.PREFERENCES);
    //menu.setFont( new java.awt.Font( mfont, font, nPT ) );
    this.add( menu );

    //Build the menu HELP.
    menu = new JMenu(DConst.HELP);
    menu.setFont( new java.awt.Font( mfont, font, nPT ) );
    this.add( menu );
    // Items in menu HELP.
    CmdMenu mAbout = new CmdMenu(DConst.ABOUT_M + DConst.APP_NAME);//, this);
    menu.add(mAbout);
    mAbout.setFont(new java.awt.Font(mfont, font, nPT));
    mAbout.setCommand(new AboutCmd(_jFrame));
    mAbout.addActionListener(_dView);
  } // end createMenus

} /* end class DMenuBar */