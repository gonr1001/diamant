package dInterface.dData;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author YS
 * @version 1.0
 */


//import javax.swing.JFrame;

import dInterface.Command;
import dInterface.DApplication;


public class ImportSelectiveFileCmd implements Command {

  String _selectionName;
  /**
   *
   * @param jFrame
   */
  public ImportSelectiveFileCmd (/*JFrame jFrame,*/ String str) {
    _selectionName =str;
  } // end constructor


  /**
   *
   * @param dApplic
   */
  public void execute(DApplication dApplic) {
    new ImportSelectiveFileDlg(dApplic, _selectionName);
  }
} /* end class ImportSelectiveFileCmd */
