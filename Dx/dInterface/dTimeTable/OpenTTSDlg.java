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

import javax.swing.JFileChooser;

import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dResources.DFileFilter;
import com.iLib.gDialog.FatalProblemDlg;


import java.awt.Dimension;

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
   buildDocument(dApplic);
 } // end constructor

 /**
     *
     * */
    private void buildDocument(DApplication dApplic) {
      JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
      String str1 = DConst.XML;
      String str2 = DConst.XML_FILE;
      String  str3 = DConst.NEW_TT_M ;

      fc.setFileFilter(new DFileFilter(new String[] {str1}, str2));
      // Display the file chooser in a dialog
      Dimension d = fc.getPreferredSize();
      fc.setPreferredSize(new Dimension((int)d.getWidth()+ 100, (int)d.getHeight()));
      //int returnVal = fc.showDialog(dApplic.getJFrame(), str3);
      int returnVal = DXTools.showDialog(dApplic.getJFrame(), fc, str3);
      // If the file chooser exited sucessfully,
      // and a file was selected, continue
       if (returnVal == JFileChooser.CANCEL_OPTION) {
         dApplic.getDMediator().closeCurrentDoc();
         dApplic.hideToolBar();
       }
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        // get the file name
        String fil = fc.getSelectedFile().getAbsolutePath();
        dApplic.setCurrentDir(fil);
        String error = dApplic.getDMediator().addDoc(fil, 0);
        if(error.length()!=0){
          new FatalProblemDlg(dApplic.getJFrame(),error);
          System.exit(1);
        }
        dispose();
        dApplic.getMenuBar().postOpenTTSCmd();
      }
   }// end loadTTData

  } /* end class OpenTTSDlg */



