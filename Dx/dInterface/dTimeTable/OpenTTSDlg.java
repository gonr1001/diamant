package dInterface.dTimeTable;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, ajz
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
  public class OpenTTSDlg extends JDialog{

    /**
     * the constructor will displays the dialog
     *
     * @param jframe    the parent of the dialog
     * @param str       the title of the window dialog
     * @since           JDK1.3
     */

    public OpenTTSDlg(DApplication dApplic) {
   //dApplic= dApplic;
   loadTTData(dApplic);
 } // end constructor

 /**
  * */
 /**
  *
  * */
 private void loadTTData(DApplication dApplic){
   JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
   fc.setFileFilter( new DFileFilter (new String[] {DConst.DGH},
       DConst.DGH_FILE) );
   // Display the file chooser in a dialog
   Dimension d = fc.getPreferredSize();
   fc.setPreferredSize(new Dimension((int)d.getWidth()+ 100, (int)d.getHeight()));
   int returnVal = fc.showDialog(dApplic.getJFrame(), DConst.NEW_TT_M);

   // If the file chooser exited sucessfully,
   // and a file was selected, continue
   if (returnVal == JFileChooser.APPROVE_OPTION) {
     // get the file name
     String fil = fc.getSelectedFile().getAbsolutePath();
     dApplic.setCurrentDir(fil);
     //load grille,
     TTStructure ttStruct = new TTStructure();
     String error = ttStruct.rloadData(fil);

     new TTDefinitionDlg(dApplic);
 //    dApplic.getDMediator().addDoc(dApplic.getCurrentDir() + DConst.NO_NAME, ttStruct);

     if(error.length()==0){
       JOptionPane.showMessageDialog(this,DConst.IMP_A_SUC,
                                   DConst.IMP_A_TD, JOptionPane.INFORMATION_MESSAGE);
     }else{
       new FatalProblemDlg(dApplic.getJFrame(),error);
            System.exit(1);
     }
     dApplic.setCurrentDir(fc.getSelectedFile().getPath());
     dispose();

   }
 }// end method





  } /* end class OpenTTSDlg */



