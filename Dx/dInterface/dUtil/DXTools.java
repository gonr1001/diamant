package dInterface.dUtil;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ys
 * @version 1.0
 */
import javax.swing.JFileChooser;
import java.awt.Component;

import dInternal.dUtil.DXToolsMethods;

public class DXTools {

  public DXTools() {
  }

  /**
    * check if the selected file exist before closed the dialog
    * @param string the file name
    * @return boolean true if the file exist a nd false otherwise
    * */
   public final static int showOpenDialog(Component parent, JFileChooser fc){
     int returnVal=0;
      String filename="nothing.txt";
      while((!DXToolsMethods.isFileExist(filename))&&
            (returnVal==JFileChooser.APPROVE_OPTION)){
          returnVal = fc.showOpenDialog(parent);
        if(fc.getSelectedFile()!=null)
          filename= fc.getSelectedFile().getAbsolutePath();

      }
     return returnVal;
   }

   /**
   * check if the selected file exist before closed the dialog
   * @param string the file name
   * @return boolean true if the file exist a nd false otherwise
   * */
  public final static int showDialog(Component parent, JFileChooser fc, String message){
    int returnVal=0;
     String filename="nothing.txt";
     while((!DXToolsMethods.isFileExist(filename))&&
           (returnVal==JFileChooser.APPROVE_OPTION)){
         returnVal = fc.showDialog(parent, message);
       if(fc.getSelectedFile()!=null)
         filename= fc.getSelectedFile().getAbsolutePath();

     }
    return returnVal;
   }

}