/**
 *
 * Title: DConst $Revision: 1.21 $  $Date: 2003-05-29 18:03:03 $
 * Description: DConst is a class used to
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
 * @version $Revision: 1.21 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */

package dResources;

import java.util.ResourceBundle;

public class DConst {

  static ResourceBundle res = ResourceBundle.getBundle("dResources.DStringFrRes");
  public final static String APP_NAME = res.getString("appName");
  public final static String V_DATE = res.getString("vDate");
  public final static String UN_TITLED = res.getString("untitled");

  public final static String BLOCS = "BLOCS";

  public final static String FILE  = res.getString("file");
  public final static String NEW_TT = res.getString("newTT");
  public final static String NEW_TT_TD  = res.getString("newTTTD");
  public final static String NEW_TT_M  = res.getString("newTTM");

  public final static String NTT_EX = res.getString("nTTEx");
  public final static String NTT_EX_TD  = res.getString("nTTExTD");
  public final static String NTT_EX_M  = res.getString("nTTExM");

  public final static String NTT_CY = res.getString("nTTCy");
  public final static String NTT_CY_TD  = res.getString("nTTCyTD");
  public final static String NTT_CY_M  = res.getString("nTTCyM");

  // public final static String N_TT  = res.getString("nTT");
  public final static String OPEN  = res.getString("open");
  public final static String CLOSE  = res.getString("close");
  //separator
  public final static String SAVE  = res.getString("save");
  public final static String SAVE_AS  = res.getString("saveAs");

  public final static String DTTS  = res.getString("dTTS");
  public final static String NTTS  = res.getString("nTTS");
  public final static String OTTS  = res.getString("oTTS");
 // public final static String IMP_M  = res.getString("impM");

  public final static String DEF_F_M  = res.getString("defFM");
  public final static String DEF_F_TD  = res.getString("defFTD");

  public final static String DEF_F_D1  = res.getString("defFD1"); // "Les cours"
  public final static String DEF_F_D2  = res.getString("defFD2"); // "Inscription d'étudiants"
  public final static String DEF_F_D3  = res.getString("defFD3"); // "Disponibilités d'enseignants"
  public final static String DEF_F_D4  = res.getString("defFD4"); // "Les locaux"
  public final static String DEF_F_D5  = res.getString("defFD5"); //"Au moins un champ ne contient aucune valeur.\n"
  public final static String DEF_F_D6  = res.getString("defFD6"); // "Veuillez entrer un fichier pour chaque type demandé."
  public final static String DEF_F_D7  = res.getString("defFD7"); // "Sauvegardés dans : "
  public final static String DEF_F_D8  = res.getString("defFD8"); // "Fichiers d'importation"

  public final static String IMP_A_M  = res.getString("impAM");
  public final static String IMP_A_TD  = res.getString("impATD");
  public final static String IMP_A_D  = res.getString("impAD");
  public final static String IMP_A_SUC  = res.getString("impASuc");

  public final static String EXPO  = res.getString("expo");

  public final static String EXIT  = res.getString("exit");


  public final static String EDIT = "Edition";
   final static String UNDO = "Annuler";
   final static String REDO = "Repeter";
   final static String CUT = "Couper";
  public final static String COPY = res.getString("copy");
   final static String PASTE = "Coller";
   final static String CLEAR = "Effacer";

  public final static String ASSIGN = res.getString( "assign");
  public final static String INST_ASSIGN_M = res.getString( "instAssignM");
  public final static String INST_ASSIGN_TD = res.getString("instAssignTD");
  public final static String INST_ASSIGN_D = res.getString("instAssignD");



   public final static String PREF = res.getString("pref");
   public final static String PLAF_M = res.getString("plafM");
   public final static String PLAF_TD = res.getString("plafTD");
   public final static String PLAF_D = res.getString("plafD");


   public final static String HELP = res.getString("help");


/*   final static String CONTENTS = "Contenu";
   final static String INDEX = "Index";
   final static String SEARCH = "Rechercher";*/

   public final static String ABOUT_M = res.getString("aboutM");
   public final static String ABOUT_TD = res.getString("aboutTD");
   public final static String ABOUT_D = res.getString("aboutD");

   public final static String BUT_OK = res.getString("butOK");
   public final static String BUT_CANCEL = res.getString("butCancel");
   public final static String BUT_APPLY = res.getString("butApply");
   public final static String BUT_BROWSE = res.getString("butBrowse");

   public final static String NO_NAME = res.getString("noName");
   public final static String DIM = res.getString("dim");
   public final static String DOT_DIM = res.getString("dotDim");
   public final static String SIG = res.getString("sig");
   public final static String DGH = res.getString("dgh");
   public final static String TXT = res.getString("txt");
   public final static String DIM_FILE = res.getString("dimFile");
   public final static String SIG_FILE = res.getString("sigFile");
   public final static String DGH_FILE = res.getString("dghFile");
   public final static String PROBLEM = res.getString("problem");



   //instructor constants
   public final static String INST_TEXT1= res.getString("instText1");
   public final static String INST_TEXT2= res.getString("instText2");
   public final static String INST_TEXT3= res.getString("instText3");
   public final static String INST_TEXT4= res.getString("instText4");
   public final static String INST_TEXT5= res.getString("instText5");
   public final static String INST_TEXT6= res.getString("instText6");

   // Student constants
   public final static String STUD_TEXT1= res.getString("studText1");
   public final static String STUD_TEXT2= res.getString("studText2");
   public final static String STUD_TEXT3= res.getString("studText3");
   public final static String STUD_TEXT4= res.getString("studText4");
   public final static String STUD_TEXT5= res.getString("studText5");
   public final static String STUD_TEXT6= res.getString("studText6");
   public final static String STUD_TEXT7= res.getString("studText7");

   // room constants
   public final static String ROOM_TEXT1= res.getString("roomText1");
   public final static String ROOM_TEXT2= res.getString("roomText2");
   public final static String ROOM_TEXT3= res.getString("roomText3");
   public final static String ROOM_TEXT4= res.getString("roomText4");
   public final static String ROOM_TEXT5= res.getString("roomText5");
   public final static String ROOM_TEXT6= res.getString("roomText6");

   // activity constants
   public final static String ACTI_TEXT1= res.getString("activText1");
   public final static String ACTI_TEXT2= res.getString("activText2");
   public final static String ACTI_TEXT3= res.getString("activText3");
   public final static String ACTI_TEXT4= res.getString("activText4");
   public final static String ACTI_TEXT5= res.getString("activText5");
   public final static String ACTI_TEXT6= res.getString("activText6");
   public final static String ACTI_TEXT7= res.getString("activText7");
   public final static String ACTI_TEXT8= res.getString("activText8");
   public final static String ACTI_TEXT9= res.getString("activText9");
   public final static String ACTI_TEXT10= res.getString("activText10");
   public final static String ACTI_TEXT11= res.getString("activText11");
   public final static String ACTI_TEXT12= res.getString("activText12");

   public final static String MFONTDialog = "Dialog";
   public final static int NPT11 = 11;

  public DConst() {
  }
}