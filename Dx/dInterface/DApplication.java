package dInterface;
/**
 *
 * Title: DApplication $Revision: 1.45 $  $Date: 2004-04-27 18:13:10 $
 * Description: DApplication is a class used display the application GUI,
 *              The class creates the main window, and ...
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
 * @version $Revision: 1.45 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */


import dInternal.Preferences;
import dResources.DConst;
import com.iLib.gDialog.FatalProblemDlg;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import javax.swing.DefaultDesktopManager;
import java.io.File;
import javax.swing.JFrame;

import javax.swing.JDesktopPane;

import org.apache.log4j.Logger;

import javax.swing.JPanel;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.SwingUtilities;

import dInterface.dTimeTable.CloseCmd;

public class DApplication implements ActionListener {
  private static Logger _logger = Logger.getLogger(DApplication.class.getName());
  /* ZERO is needed to fix Frame Location (origin)  */
  private final static int ZERO = 0;
  /* ADJUST_HEIGHT is needed to ajdust the screenSize
   * minus the barSize (the value is a guess) at the bottom */
  //private final static int ADJUST_HEIGHT = 92;
  /* ADJUST_WIDTH is needed to ajdust the screenSize
   * minus border pixels (the value is a guess) at each side of the screen */
  private final static int ADJUST_WIDTH = 6;
  /* MIN_HEIGHT is needed to ajdust the minimum
   * height of the screenSize */
  private final static int MIN_HEIGHT = 512;
  /* MIN_WIDTH is needed to ajdust the minimum
   * width screenSize */
  private final static int MIN_WIDTH = 512;

  /* _screenSize contains the Dimension of the screen in pixels */
  private Dimension _screenSize;
  private JFrame _jFrame;
  private JDesktopPane _jDesktopPane;
  private Preferences _preferences;
  private DMediator _mediator;
  private String _currentDir;
  private DMenuBar _dMenuBar;
  private DToolBar _tbar;

  //-------------------------------------------
  /**
    * DApplication initialize the data members
    */
  public DApplication() {
    _logger.warn("hello_from DApplication");
    _preferences = new Preferences(System.getProperty("user.dir")
                                   + File.separator +
                                   "pref"
                                   + File.separator +
                                   "pref.txt");

    // System.out.println(System.getProperty("user.dir"));
    //System.out.println(System.getProperty("file.encoding"));
    _screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    _mediator = new DMediator(this);
    _currentDir = System.getProperty("user.dir");

    _jFrame = createFrame(DConst.APP_NAME + "   " + DConst.V_DATE);

    setLAF(_preferences._lookAndFeel);

    //updateLAF(_preferences._lookAndFeel);
    _logger.warn("bye_from DApplication");
  } // end constructor

  //-------------------------------------------
  private JFrame createFrame(String str) {
    JFrame jFrame= new JFrame(str + "   " + System.getProperty("user.dir"));
    jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    jFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing( WindowEvent e ) {
        closeApplic(e);
      }
      });
    JPanel panel = new JPanel(new BorderLayout(0,0));
    jFrame.setContentPane(panel);
    _dMenuBar = new DMenuBar(this);
    jFrame.setJMenuBar(_dMenuBar);  //constructs the menu bar

    _tbar = new DToolBar(this); //constucts the tool bar
    //jpToolBar.add(_tbar);

    jFrame.getContentPane().add(_tbar, BorderLayout.NORTH);
   // panel.add(_tbar,BorderLayout.NORTH);

    hideToolBar();

    _jDesktopPane = new JDesktopPane();
    _jDesktopPane.setOpaque(false);
    _jDesktopPane.setDesktopManager(new DefaultDesktopManager());
    panel.add(_jDesktopPane,BorderLayout.CENTER);
    jFrame.setLocation(ZERO,ZERO);
    panel.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    panel.setMaximumSize(_screenSize);

    panel.setPreferredSize(new Dimension(_screenSize.width - DConst.ADJUST_WIDTH,
                                         _screenSize.height - DConst.ADJUST_HEIGHT));
    //panel.setPreferredSize(new Dimension(_screenSize.width - ADJUST_WIDTH, 500));

    jFrame.pack();
    jFrame.setVisible(true);
    return jFrame;
    } //end createUI

    //-------------------------------------------
    public void actionPerformed(ActionEvent  e) {
      if (e.getSource() instanceof CommandHolder) {
        ((CommandHolder) e.getSource()).getCommand().execute(this);
        // repaint();
      }
      else {
        System.out.println("I do not know what to do, please help me (Action Performed)");
      }// end if ... else
    }// end actionPerformed

    public JDesktopPane getDesktop(){
      return _jDesktopPane;
    } // end getDesktop

    public JFrame getJFrame(){
      return _jFrame;
    } // end getJFrame

    public DMediator getDMediator(){
      return _mediator;
    } // end getJFrame

    public DMenuBar getMenuBar(){
      return _dMenuBar;
    } // end getDesktop

    public DToolBar getToolBar(){
      return _tbar;
    }

    //-------------------------------------------
    public Preferences getPreferences(){
      return _preferences;
    } // end getPreferences

    public String getCurrentDir(){
      return _currentDir;
    } // end getCurrentDir

    /*
    * the str can contain a file name, it will be left out
    */
    public void setCurrentDir(String str){
       _currentDir = str.substring(0,str.lastIndexOf(File.separator)+1);
    } // end setCurrentDir

    public void showToolBar(){
      _tbar.setVisible(true);
    }
    public void hideToolBar(){
      _tbar.setVisible(false);
    }

    //-------------------------------------------
    public void setLAF(String str) {
      // Force SwingApp to come up in the System L&F
      try {
        UIManager.setLookAndFeel(str);
        //System.out.println("pref" + str );
      }
      catch (UnsupportedLookAndFeelException ulafe)  {
        new FatalProblemDlg("UnsupportedLookAndFeel: " + str);
        System.err.println("Warning: UnsupportedLookAndFeel: " + str);
        ulafe.printStackTrace();
        System.exit(31);
      }
      catch (ClassNotFoundException cnfe) {
        new FatalProblemDlg("Error ClassNotFound LookAndFeel" + str);
        System.err.println("Error ClassNotFound LookAndFeel" + str);
        cnfe.printStackTrace();
        System.exit(41);
      }
      catch (IllegalAccessException iace) {
        new FatalProblemDlg("Error IllegalAccess LookAndFeel" + str);
        System.err.println("Error IllegalAccess LookAndFeel" + str);
        iace.printStackTrace();
        System.exit(51);
      }
      catch (InstantiationException ie) {
        new FatalProblemDlg("Error Instantiation LookAndFeel" + str);
        System.err.println("Error Instantiation LookAndFeel" + str);
        ie.printStackTrace();
        System.exit(61);
      }

      //SwingUtilities.updateComponentTreeUI(_jFrame);  //To erease (AlexJ)
    } //end setLF

    //-------------------------------------------
    /**
    * This methode updates the look and feel style
    * @param String A look and feel style
    **/
    public void updateLAF(String str){
      setLAF(str);
      SwingUtilities.updateComponentTreeUI(_jFrame);
    }

    public void constructToolBar(){
      _tbar = new DToolBar(this); //constucts the tool bar
      //jpToolBar.add(_tbar);
      _tbar.updateUI();

      _jFrame.getContentPane().add(_tbar, BorderLayout.NORTH);
      // panel.add(_tbar,BorderLayout.NORTH);
      _tbar.updateUI();
      updateLAF(_preferences._lookAndFeel);
      //hideToolBar();
    }
    //-------------------------------------------
    /**
     * Closes the DDocument(s) and the application.
     * Use this method for processing close via the
     * WindowClosing Event.
     *
     * @return void
     * @since JDK 1.2
     */
    private void closeApplic(WindowEvent e) {
      closeApplic();
    }

    //-------------------------------------------
    /**
     * Closes the document(s) and the application.
     * Use this method for processing close via the
     * quit menuItem.
     *
     * @return void
     * @since JDK 1.2
     */
    public void closeApplic() {
      // if no Document exit ok
      while (_mediator.getCurrentDoc() != null) { //is a while
        new CloseCmd().execute(this);
        if (_mediator.getCancel())
          break;
      }
      // if Document changed as for save or not
       if (_mediator.getCurrentDoc() == null) {
         _jFrame.setVisible(false);
         _jFrame.dispose();
         System.exit(0);
      }
  }
} /* end class DApplication */
