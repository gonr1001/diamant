package dInterface.dTimeTable;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.util.ResourceBundle;
import dInterface.DApplication;
import dResources.DFileFilter;
import com.iLib.gDialog.FatalProblemDlg;

import java.awt.Dimension;

import dResources.DConst;

import dInternal.dTimeTable.TTStructure;

import dResources.DConst;

  /**
   *
   * NewTTSDlg is a class used to display the New Time Table Structure dialog.
   *
   */

public class NewTTSDlg extends JDialog{

  /**
   * the constructor will displays the dialog
   *
   * @param jframe    the parent of the dialog
   * @param str       the title of the window dialog
   * @since           JDK1.3
   */


  DApplication _dApplic;
  /**
    * the constructor will displays the dialog
    *
    * @param jframe    the parent of the dialog
    * @param str       the title of the window dialog
    * @since           JDK1.3
    */

   public NewTTSDlg(DApplication dApplic) {
     //_dApplic= dApplic;
     loadTTData(dApplic);
   } // end constructor

   /**
    * */
   /**
    *
    * */
   private void loadTTData(DApplication dApplic){

     new TTDefinitionDlg(dApplic);
     /*JFileChooser fc = new JFileChooser(_dApplic.getCurrentDir());
     fc.setFileFilter( new DFileFilter (new String[] {DConst.DGH},
         DConst.DGH_FILE) );
     // Display the file chooser in a dialog
     Dimension d = fc.getPreferredSize();
     fc.setPreferredSize(new Dimension((int)d.getWidth()+ 100, (int)d.getHeight()));
     int returnVal = fc.showDialog(_dApplic.getJFrame(), DConst.NEW_TT_M);

     // If the file chooser exited sucessfully,
     // and a file was selected, continue
     if (returnVal == JFileChooser.APPROVE_OPTION) {
       // get the file name
       String fil = fc.getSelectedFile().getAbsolutePath();
       _dApplic.setCurrentDir(fil);
       //load grille,
       TTStructure ttStruct = new TTStructure();
       String error = ttStruct.loadData(fil);

       _dApplic.getDMediator().addDoc(_dApplic.getCurrentDir() + DConst.NO_NAME, ttStruct);

       if(error.length()==0){
         JOptionPane.showMessageDialog(this,DConst.IMP_A_SUC,
                                     DConst.IMP_A_TD, JOptionPane.INFORMATION_MESSAGE);
       }else{
         new FatalProblemDlg(_dApplic.getJFrame(),error);
              System.exit(1);
       }
       _dApplic.setCurrentDir(fc.getSelectedFile().getPath());
       dispose();*/

     //}
   }// end method







} /* end class NewTTSDlg */



