/**
 *
 * Title: DApplication $Revision: 1.5 $  $Date: 2003-05-27 14:12:37 $
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
 * @version $Revision: 1.5 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import dInternal.Preferences;
import dResources.DConst;
import com.iLib.gDialog.FatalProblemDlg;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Container;
import java.awt.Toolkit;
import javax.swing.DefaultDesktopManager;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//import java.lang.IllegalAccessException;
import javax.swing.SwingUtilities;

public class DApplication implements ActionListener {
  /* ZERO is needed to fix Frame Location (origin)  */
  private final static int ZERO = 0;
  /* ADJUST_HEIGHT is needed to ajdust the screenSize
   * minus the barSize (the value is a guess) at the bottom */
  private final static int ADJUST_HEIGHT = 92;
  /* ADJUST_WIDTH is needed to ajdust the screenSize
   * minus border pixels (the value is a guess) at each side of the screen */
  private final static int ADJUST_WIDTH = 6;
  /* MIN_HEIGHT is needed to ajdust the minimum
   * height of the screenSize */
  private final static int MIN_HEIGHT = 400;
  /* MIN_WIDTH is needed to ajdust the minimum
   * width screenSize */
  private final static int MIN_WIDTH = 400;

  /* _screenSize contains the Dimension of the screen in pixels */
  private Dimension _screenSize;
  private JFrame _jFrame;
  private JDesktopPane _jDesktopPane;
  private Preferences _preferences;
  private DMediator _mediator;
  private String _currentDir;
  /**
    * DApplication initialize the data members
    */
  public DApplication() {
    _preferences = new Preferences(System.getProperty("user.dir")
                                   + File.separator +
                                   "pref"
                                   + File.separator +
                                   "pref.txt");
    System.out.println(System.getProperty("user.dir"));
    _screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    _mediator = new DMediator(this);
    _currentDir = System.getProperty("user.dir");
    _jFrame = createUI(DConst.APP_NAME + "   " + DConst.V_DATE);
    setLAF(_preferences._lookAndFeel);

    _jFrame.pack();
    _jFrame.setVisible(true);

  } // end constructor

  private JFrame createUI(String str) {
    // the jFrame will have a WindowListener after createFrame
    JFrame jFrame = createFrame(str + "   " + System.getProperty("user.dir"));
    JPanel panel = new JPanel(new BorderLayout(0,0));
    jFrame.setContentPane(panel);
    jFrame.setJMenuBar(new DMenuBar( this ));  //constructs the menu bar
    JToolBar _tbar = new DToolBar(this); //constucts the tool bar
    Container contentPane = jFrame.getContentPane();
    contentPane.add(_tbar,BorderLayout.NORTH);
    _jDesktopPane = new JDesktopPane();
    _jDesktopPane.setOpaque(false);
    _jDesktopPane.setDesktopManager(new DefaultDesktopManager());

    contentPane.add(_jDesktopPane,BorderLayout.CENTER);
    jFrame.setLocation(ZERO,ZERO);
    panel.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    panel.setMaximumSize(_screenSize);
    panel.setPreferredSize(new Dimension(_screenSize.width - ADJUST_WIDTH,
                                         _screenSize.height - ADJUST_HEIGHT));
    return jFrame;
    } //end createUI


    /**
     * createFrame will create the JFrame with a
     * WindowListener associated
     * Effect: the JFrame has a WindowListener
     *
     */
    private JFrame createFrame(String title) {
      JFrame jframe= new JFrame(title);
      jframe.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {System.exit(0);}
      } );
      return jframe;
    } // end createFrame

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



    public void setLAF(String str) {
      // Force SwingApp to come up in the System L&F
      try {
        UIManager.setLookAndFeel(str);
        System.out.println("pref" + str );
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

      SwingUtilities.updateComponentTreeUI(_jFrame);
    } //end setLF

} /* end class DApplication */
