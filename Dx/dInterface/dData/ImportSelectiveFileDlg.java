package dInterface.dData;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author YS
 * @version 1.0
 */

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dResources.DFileFilter;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.dialog.InformationDlg;
import eLib.exit.exception.DxException;

public class ImportSelectiveFileDlg extends JDialog {

    /**
     * 
     * @param dApplic
     * @param str
     */
    public ImportSelectiveFileDlg(DApplication dApplic, String str) {
        loadData(dApplic, str);
    } // end constructor

    /**
     * 
     * @param dApplic
     * @param str
     */
    private void loadData(DApplication dApplic, String str) {
        JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
        fc.setFileFilter(new DFileFilter(new String[] { DConst.SIG },
                DConst.SIG_FILE));
        // Display the file chooser in a dialog
        Dimension d = fc.getPreferredSize();
        fc.setPreferredSize(new Dimension((int) d.getWidth(), (int) d
                .getHeight()));
        int returnVal = fc.showDialog(dApplic.getJFrame(), DConst.IMP_SELECT
                + " : " + str);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // get the file name
            String fil = fc.getSelectedFile().getAbsolutePath();
            dApplic.setCurrentDir(fil);

            if (dApplic.getCurrentDoc() != null) {
                try {
					dApplic.getCurrentDModel().mergeData(fil, str);
					new InformationDlg(dApplic.getJFrame(), DConst.IMP_A_SUC);
				} catch (DxException dxe) {
					 new DxExceptionDlg(dApplic.getJFrame(),dxe.getMessage());
					dxe.printStackTrace();
				}
            } else
                new DxExceptionDlg(
                        dApplic.getJFrame(),
                        "ImportSelectiveFileDlg : Il n'existe pas de document pour effectuer l'importation des données");

        }
    }// end method

} /* end class ImportSelectiveFileDlg */
