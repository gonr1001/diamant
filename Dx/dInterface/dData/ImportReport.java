/**
 *
 * Title: ImportReport
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
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */

/**
 * Description: ImportReport is a class used to make the import's report
 *
 */
package dInterface.dData;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextArea;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dTimeTable.SaveAsTxtDlg;
import dInternal.DSetOfResources;
import dInternal.DValue;

public class ImportReport extends ViewReport implements ActionListener {

    public ImportReport(ReportsDlg parentDlg, DApplication dApplic,
            Dimension dim) {
        super(parentDlg, dApplic, dim);
        String[] strArray = { DConst.BUT_OPTIONS };
        disableButtons(_buttonsPanel, strArray);
        setImportReport(_jTextArea);
    }

    /**
     * 
     * @param jta
     */
    public void setImportReport(JTextArea jta) {
        JTextArea jtaInst = new JTextArea("");
        JTextArea jtaRooms = new JTextArea("");
        JTextArea jtaStud = new JTextArea("");
        jta.setFont(DConst.JLISTS_FONT);
        jta.setText("Rapport d'importation");
        jta.append(DConst.CR_LF + DConst.SEPARATOR + DConst.CR_LF);

        // enseignants
        jtaInst.append(DConst.CR_LF + DConst.INSTRUCTOR_SEP + DConst.CR_LF);
        // locaux
        jtaRooms.append(DConst.CR_LF + DConst.ROOM_SEP + DConst.CR_LF);
        // etudiants
        jtaStud.append(DConst.CR_LF + DConst.STUDENT_SEP + DConst.CR_LF);
        DSetOfResources setOfImportErrors = _dApplic.getCurrentDModel().getSetOfImportErrors();
        for (int i = 0; i < setOfImportErrors.size(); i++) {
            // Etudiants
            if (setOfImportErrors.getResourceAt(i).getID()
                    .equalsIgnoreCase("1"))
                jtaStud.append(((DValue) (setOfImportErrors.getResourceAt(i))
                        .getAttach()).getStringValue()
                        + DConst.CR_LF);
            // Enseignants
            else if (setOfImportErrors.getResourceAt(i).getID()
                    .equalsIgnoreCase("2"))
                jtaInst.append(((DValue) (setOfImportErrors.getResourceAt(i))
                        .getAttach()).getStringValue()
                        + DConst.CR_LF);
            // Locaux
            else if (setOfImportErrors.getResourceAt(i).getID()
                    .equalsIgnoreCase("3"))
                jtaRooms.append(((DValue) (setOfImportErrors.getResourceAt(i))
                        .getAttach()).getStringValue()
                        + DConst.CR_LF);

        }
        jta.append(jtaInst.getText());
        jta.append(jtaRooms.getText());
        jta.append(jtaStud.getText());
        jta.setCaretPosition(0);
    }

    /**
     * Action performed
     * 
     * @param e
     *            Action event
     * 
     * @lgd: 28-04-05. Buttom Options erased
     */
    public void actionPerformed(ActionEvent e) {
        // String command = e.getActionCommand();
        // if "Option" button
        //if (e.getActionCommand().equals(DConst.BUT_OPTIONS))
            // ; // it is disabled
            // if "Close" button
            if (e.getActionCommand().equals(DConst.BUT_CLOSE))
                // System.out.println("_buttonsNames[2]");
                dispose();
        // if "Save as" button
        if (e.getActionCommand().equals(DConst.BUT_SAVE_AS)) {
            // System.out.println("_buttonsNames[0]");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "EEEE-MMMM-dd-yyyy:kk:mm");

            JTextArea jta = (JTextArea) _scrollPane.getViewport().getComponent(
                    0);
            String data = "***** " + DConst.REPORT + " " + DConst.TO_LEFT
                    + DConst.REPORT_DLG_TAB3 + DConst.TO_RIGHT + "          ";
            data += DConst.REPORT_PRODUCED_AT + " " + sdf.format(date)
                    + " *****" + DConst.CR_LF + DConst.CR_LF;
            data += jta.getText();
            new SaveAsTxtDlg(_dApplic, data);
        }// end if (e.getActionCommand().equals(_buttonsNames[0]))

    }// end method

    /**
     * 
     * @param rigth
     *            Save the options
     */
    public void doSave(Vector rigth) {
        _dApplic.getPreferences().setSelectedOptionsInFullReport(rigth);
        _dApplic.getPreferences().save();
        _rightVec = rigth;
    }

}