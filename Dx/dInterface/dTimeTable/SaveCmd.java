/**
 *
 * Title: SaveCmd $Revision: 1.2 $  $Date: 2003-07-03 11:52:03 $
 * Description: SaveCmd is a class used to save a
 *  			document
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
 * @version $Revision: 1.2 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface.dTimeTable;

import javax.swing.JFrame;
import dInterface.Command;
import dInterface.DApplication;
import dResources.DConst;

public class SaveCmd implements Command {

  public SaveCmd() {
  } //end SaveCmd

  public void execute(DApplication dApplic) {
    /*System.out.println("DOC nam" + dApplic.getDMediator().getCurrentDoc().getDocumentName()+ "  "
                       + DConst.UN_TITLED);*/
    if (dApplic.getDMediator().getCurrentDoc().getDocumentName().endsWith(DConst.NO_NAME))
      new SaveAsDlg(dApplic);
    else
      if (dApplic.getDMediator().getCurrentDoc().isModified())
        dApplic.getDMediator().saveCurrentDoc(dApplic.getDMediator().getCurrentDoc().getDocumentName());
       //confirm dialog ?
      //else not necessary to save
       // dApplic.getDMediator().getCurrentDoc().getDM().rsaveTT();
  } // end execute

} /* end SaveCmd class */
