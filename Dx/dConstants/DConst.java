/**
 *
 * Title: DConst $Revision: 1.35 $  $Date: 2003-07-28 10:59:06 $
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
 * @version $Revision: 1.35 $
 * @author  $Author: alexj $
 * @since JDK1.3
 */

package dResources;

import java.util.ResourceBundle;

public class DConst {

  //Shared constants

  //Menu constants

  //Dialog constants

  //
  static ResourceBundle res = ResourceBundle.getBundle("dResources.DStringFrRes");
  public final static String APP_NAME = res.getString("appName");//Diamant 1.5
  public final static String V_DATE = res.getString("vDate");
  //public final static String UN_TITLED = res.getString("untitled");

  public final static String BLOCS = "BLOCS";

  public final static String FILE  = res.getString("file");
  public final static String NEW_TT = res.getString("newTT");
  public final static String NEW_TT_TD  = res.getString("newTTTD");
  public final static String NEW_TT_M  = res.getString("newTTM");//Grille horaire

  public final static String NTT_CY = res.getString("nTTCy");
  public final static String NTT_CY_TD  = res.getString("nTTCyTD");//Grille horaire cycle
  public final static String NTT_CY_M  = res.getString("nTTCyM");//Cycle, utiliseé null part

  public final static String NTT_EX = res.getString("nTTEx");//Horaire Examen
  public final static String NTT_EX_TD  = res.getString("nTTExTD");//Grille horaire examen
  public final static String NTT_EX_M  = res.getString("nTTExM");//Examen, unp


  // public final static String N_TT  = res.getString("nTT");
  public final static String OPEN  = res.getString("open");
  public final static String OPEN_TT_TD  = res.getString("oTTTD");//Horaire, unp

  public final static String CLOSE  = res.getString("close");
  //separator
  public final static String SAVE  = res.getString("save");
  public final static String SAVE_AS  = res.getString("saveAs");

  public final static String DEF_F_M  = res.getString("defFM");//Definir fichiers à importer
  public final static String DEF_F_TD  = res.getString("defFTD");//Definir fichiers pour l'importation automatique

  public final static String DEF_F_D1  = res.getString("defFD1"); // "Les cours"
  public final static String DEF_F_D2  = res.getString("defFD2"); // "Inscription d'étudiants"
  public final static String DEF_F_D3  = res.getString("defFD3"); // "Disponibilités d'enseignants"
  public final static String DEF_F_D4  = res.getString("defFD4"); // "Les locaux"
  public final static String DEF_F_D5  = res.getString("defFD5"); //"Au moins un champ ne contient aucune valeur.\n"
  public final static String DEF_F_D6  = res.getString("defFD6"); // "Veuillez entrer un fichier pour chaque type demandé."
  public final static String DEF_F_D7  = res.getString("defFD7"); // "Sauvegardés dans : "
  public final static String DEF_F_D8  = res.getString("defFD8"); // "Fichiers d'importation"

  public final static String IMP_A_M  = res.getString("impAM");//Importer automatiquement
  public final static String IMP_A_TD  = res.getString("impATD");//Importation de fichiers
  public final static String IMP_A_D  = res.getString("impAD");//To be defined, unp
  public final static String IMP_A_SUC  = res.getString("impASuc");//Fichiers importés avec succes!!!

  public final static String EXPO  = res.getString("expo");

  public final static String EXIT  = res.getString("exit");


  public final static String EDIT = "Edition";
  final static String UNDO = "Annuler";//unp, the text is already in buttonCancel
  final static String REDO = "Repeter";//unp
  final static String CUT = "Couper";//unp
  public final static String COPY = res.getString("copy");
  final static String PASTE = "Coller";
  final static String CLEAR = "Effacer";

  public final static String FILE_TTS  = res.getString("fileTTS");//Fichier_Grille, unp
  public final static String NEW_TTS = res.getString("newTTS");//Nouvelle grille
 /* public final static String NEW_TT_TD  = res.getString("newTTTD");
  public final static String NEW_TT_M  = res.getString("newTTM");*/


  public final static String NTTS_CY = res.getString("nTTSCy");//Grille cycle
/*  public final static String NTTS_CY_TD  = res.getString("nTTCyTD");
  public final static String NTTS_CY_M  = res.getString("nTTCyM");*/

  public final static String NTTS_EX = res.getString("nTTSEx");//Grille examen
/*  public final static String NTTS_EX_TD  = res.getString("nTTExTD");
  public final static String NTTS_EX_M  = res.getString("nTTExM");*/


  public final static String OPEN_TTS  = res.getString("openTTS");
  public final static String O_TTSTD  = res.getString("oTTSTD");//Grille, unp

  public final static String CLOSE_TTS  = res.getString("closeTTS");//Fermer grille, unp
  public final static String SAVE_TTS  = res.getString("saveTTS");//Enregistrer grille, unp
  public final static String SAVE_AS_TTS  = res.getString("saveAsTTS");//Enregistrer grille sous, unp

  public final static String ASSIGN = res.getString( "assign");//Affectation
  public final static String INST_ASSIGN_M = res.getString( "instAssignM");//Enseignants
  public final static String INST_ASSIGN_TD = res.getString("instAssignTD");//Disponibilité enseignants
  public final static String INST_ASSIGN_D = res.getString("instAssignD");//To be defined, unp



   public final static String PREF = res.getString("pref");//Preferences
   public final static String PLAF_M = res.getString("plafM");//ptions L&F (for the menu)
   public final static String PLAF_TD = res.getString("plafTD");//Options L&F REPETE unp REPETE (for the dialog)
   public final static String PLAF_D = res.getString("plafD");//Choisisez un L&F

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
   public final static String DIA = res.getString("dia");
   public final static String DIM = res.getString("dim");
   public final static String XML = res.getString("xml");
   public final static String DOT_DIA = res.getString("dotDia");
   public final static String DOT_DIM = res.getString("dotDim");
   public final static String DOT_XML = res.getString("dotXml");
   public final static String SIG = res.getString("sig");
   public final static String DGH = res.getString("dgh");
   public final static String TXT = res.getString("txt");
   public final static String DIM_FILE = res.getString("dimFile");
   public final static String DIA_FILE = res.getString("diaFile");
   public final static String SIG_FILE = res.getString("sigFile");
   public final static String DGH_FILE = res.getString("dghFile");
   public final static String XML_FILE = res.getString("xmlFile");
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
   public final static String ROOM_TEXT7= res.getString("roomText7");

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

   public final static boolean TT_STRUC = true;

   public final static int NO_TYPE = 0;
   public final static int CYCLE = 1;
   public final static int EXAM = 2;
   public final static int CYCLEANDEXAM = 3;

   public final static int NBOFDAYS=5;
   public final static int NBOFPERIODSADAY=14;

  public DConst() {
  }
}