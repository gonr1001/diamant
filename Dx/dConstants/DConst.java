/**
 *
 * Title: DConst $Revision: 1.47 $  $Date: 2003-09-11 19:25:39 $
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
 * @version $Revision: 1.47 $
 * @author  $Author: ysyam $
 * @since JDK1.3
 */

package dResources;

import java.util.ResourceBundle;
import java.awt.Color;

public class DConst {

  static ResourceBundle res = ResourceBundle.getBundle("dResources.DStringFrRes");


  //********** Shared and others constants ***************************
  public final static String APP_NAME = res.getString("appName");//Diamant 1.5
  public final static String CLOSE  = res.getString("close");//Fermer
  public final static String BUT_OK = res.getString("butOK");//Ok
  public final static String BUT_CANCEL = res.getString("butCancel");//Annuler
  public final static String BUT_APPLY = res.getString("butApply");//Appliquer
  public final static String BUT_BROWSE = res.getString("butBrowse");//Parcourir

  public final static String NO_NAME = res.getString("noName");//Sans titre
  public final static String DIA = res.getString("dia");//dia
  public final static String DIM = res.getString("dim");//dim
  public final static String XML = res.getString("xml");//xml
  public final static String DOT_DIA = res.getString("dotDia");//.dia
  public final static String DOT_DIM = res.getString("dotDim");//.dim
  public final static String DOT_XML = res.getString("dotXml");//.xml
  public final static String SIG = res.getString("sig");//sig
  public final static String DGH = res.getString("dgh");//dgh, unp
  public final static String TXT = res.getString("txt");//txt
  public final static String DIM_FILE = res.getString("dimFile");//*.dim
  public final static String DIA_FILE = res.getString("diaFile");//*.dia
  public final static String SIG_FILE = res.getString("sigFile");//*.sig
  public final static String DGH_FILE = res.getString("dghFile");//*.dgh, unp
  public final static String XML_FILE = res.getString("xmlFile");//*.xml
  public final static String PROBLEM = res.getString("problem");//À corriger, unp

  public final static String MFONTDialog = "Dialog";// unp????pas trouve dans DStringFrRes
  public final static int NPT11 = 11;//// unp????pas trouve dans DStringFrRes
  public final static boolean TT_STRUC = true;//unp????pas trouve dans DStringFrRes
  public final static int NO_TYPE = 0;
  public final static int CYCLE = 1;
  public final static int EXAM = 2;
  public final static int CYCLEANDEXAM = 3;
  public final static int STINBOFDAYS=5;
  public final static int STINBOFPERIODSADAY=14;
  public final static int STIBEGINHOUR=8;
  public final static int STIBEGINMINUTE=30;
  public final static int COURSENAMELENGTH=6;

  // Color constants
  public final static Color COLOR_BLACK = Color.black; // Default color
  public final static Color COLOR_AUX = Color.gray; // Other color
  public final static Color COLOR_ROOM = Color.blue; // Rooms conflicts color
  public final static Color COLOR_INST = Color.red;  // Instructors conflicts color
  public final static Color COLOR_STUD = Color.magenta; // Students conflicts color
  //DDocument
  public final static String V_DATE = res.getString("vDate");//13 mai 2003
  public final static String BLOCS = "BLOCS";//pas trouve dans DStringFrRes

  //**********Menus constants ***************************
  //File menu
  public final static String FILE  = res.getString("file");//Fichier
  public final static String NEW_TT = res.getString("newTT");//Nouvel horaire
  public final static String NTT_CY = res.getString("nTTCy");//Horaire cycle
  public final static String NTT_EX = res.getString("nTTEx");//Horaire Examen
  public final static String NEW_TTS = res.getString("newTTS");//Nouvelle grille
  public final static String NTTS_CY = res.getString("nTTSCy");//Grille cycle
  public final static String NTTS_EX = res.getString("nTTSEx");//Grille examen
  public final static String OPEN  = res.getString("open");//Ouvrir horaire
  public final static String OPEN_TTS  = res.getString("openTTS");//Ouvrir grille
  public final static String SAVE  = res.getString("save");//Enregistrer
  public final static String SAVE_AS  = res.getString("saveAs");//Enregistrer zouz
  public final static String DEF_F_M  = res.getString("defFM");//Definir fichiers à importer
  public final static String IMP_A_M  = res.getString("impAM");//Importer automatiquement
  public final static String EXPO  = res.getString("expo");//Exporter
  public final static String EXIT  = res.getString("exit");//Quitter
  //Edition Menu
  public final static String EDIT = "Edition";//unp
  final static String UNDO = "Annuler";//unp, the text is already in buttonCancel
  final static String REDO = "Repeter";//unp
  final static String CUT = "Couper";//unp
  public final static String COPY = res.getString("copy");
  final static String PASTE = "Coller";
  final static String CLEAR = "Effacer";
  //Assign menu
  public final static String ASSIGN = res.getString("assign");//Affectation
  public final static String INST_ASSIGN_M = res.getString("instAssignM");//Enseignants
  public final static String ACTI_ASSIGN_M = res.getString("ActiAssignM");//Activités
  public final static String GROUP_ASSIGN_M = res.getString("GroupAssignM");//Activités
  public final static String LOCAUX_ASSIGN_M= res.getString("LocauxAssignM");//Activités
  //Optimization menu
  public final static String INITAFFECT = res.getString("init");//Affectation
  //Preferences menu
  public final static String PREF = res.getString("pref");//Preferences
  public final static String PLAF_M = res.getString("plafM");//ptions L&F
  //Help menu
  public final static String HELP = res.getString("help");//Aide
  public final static String ABOUT_M = res.getString("aboutM");//A propos de

  public final static String NEW_TT_TD  = res.getString("newTTTD");//Indiquez grille horaire, unp
  public final static String NTT_CY_M  = res.getString("nTTCyM");//Cycle, unp
  public final static String NTT_EX_M  = res.getString("nTTExM");//Examen, unp

  //**********Dialogs constants ***************************
  //File dialogs
  public final static String NEW_TT_M  = res.getString("newTTM");//Grille horaire
  public final static String NTT_CY_TD  = res.getString("nTTCyTD");//Grille horaire cycle
  public final static String NTT_EX_TD  = res.getString("nTTExTD");//Grille horaire examen
  public final static String DEF_F_TD  = res.getString("defFTD");//Definir fichiers pour l'importation automatique
  public final static String IMP_A_TD  = res.getString("impATD");//Importation de fichiers
  public final static String IMP_A_SUC  = res.getString("impASuc");//Fichiers importés avec succes!!!
  //DefFilesToImportDlg
  public final static String DEF_F_D1  = res.getString("defFD1"); // "Les cours"
  public final static String DEF_F_D2  = res.getString("defFD2"); // "Inscription d'étudiants"
  public final static String DEF_F_D3  = res.getString("defFD3"); // "Disponibilités d'enseignants"
  public final static String DEF_F_D4  = res.getString("defFD4"); // "Les locaux"
  public final static String DEF_F_D5  = res.getString("defFD5"); //"Au moins un champ ne contient aucune valeur.\n"
  public final static String DEF_F_D6  = res.getString("defFD6"); // "Veuillez entrer un fichier pour chaque type demandé."
  public final static String DEF_F_D7  = res.getString("defFD7"); // "Sauvegardés dans : "
  public final static String DEF_F_D8  = res.getString("defFD8"); // "Fichiers d'importation"
  //Assign dialogs
  public final static String INST_ASSIGN_TD = res.getString("instAssignTD");//Disponibilité enseignants
  public final static String INST_ASSIGN_D = res.getString("instAssignD");//To be defined, unp
  public final static String TO_LEFT  = res.getString("toLeft");//««
  public final static String TO_RIGHT  = res.getString("toRight");//»»
  //ActivityDlg
  public final static String ACT_LIST  = res.getString("actList"); //Liste des activités
  public final static String SHOW  = res.getString("show"); //Aficher
  public final static String NOT_INCLUDED  = res.getString("notIncluded");//Non inclue(s)
  public final static String INCLUDED  = res.getString("included");//Inclue(s)
  //InstructorAvailabiliyDlg and roomsAvailabiliyDlg
  public final static String AVAILABILITIES = res.getString("dispo");//Disponibilités
  public final static String ROOMS_DLG_TITLE = res.getString("roomsDlgTitle");
  //GroupDlg
  public final static String ACT_STUD_NOT_ASSIGNED = res.getString("ActStudNotAss");//Étudiants non assignés
  public final static String ACT_STUD_ASSIGNED = res.getString("ActStudAss");//Étudiants assignés
  public final static String ACTIVITY = res.getString("Activity");//Activité
  public final static String GROUP_DLG_TITLE = res.getString("GroupDlgTitle");//Titre du dialog
  public final static String GROUP = res.getString("Group");//Groupe
  public final static String NUMBER_OF_ELEMENTS = res.getString("NumberOfElements");//Nombre d'éléments
  public final static String TYPE = res.getString("Type");//Type
  public final static String EVENTS_DLG_TITLE = res.getString("EventsDlgTitle");//Évenements
  public final static String EVENTS_FIXED = res.getString("EventsFixed");//Figés
  public final static String EVENTS_PLACED = res.getString("EventsPlaced");//Placés
  public final static String EVENTS_NOT_PLACED = res.getString("EventsNotPlaced");//Non placés
  //Preferences dialogs
  public final static String PLAF_TD = res.getString("plafTD");//Options L&F unp repete
  public final static String PLAF_D = res.getString("plafD");//Choisisez un L&F
  //Help dialogs
  public final static String ABOUT_TD = res.getString("aboutTD");//A propos de, repete
  public final static String ABOUT_D = res.getString("aboutD");// \nCopyRight\n 2000 - 2003

  public final static String OPEN_TT_TD  = res.getString("oTTTD");//Horaire, unp
  public final static String IMP_A_D  = res.getString("impAD");//To be defined, unp


  //ToolBar
  public final static String TB_DAYS  = res.getString("tBDay");//Jour
  public final static String TB_PER  = res.getString("tBPer");//Period
  //**********Internal constants ***************************
  //instructor constants
  public final static String INST_TEXT1= res.getString("instText1");//Wrong number of instructors in the instructor file:
  public final static String INST_TEXT2= res.getString("instText2");//Wrong name of instructor at line:
  public final static String INST_TEXT3= res.getString("instText3");//Wrong number of instructor availabilities periods per day at line:
  public final static String INST_TEXT4= res.getString("instText4");//Wrong description of instructor availability at line:
  public final static String INST_TEXT5= res.getString("instText5");// in the instructor file:
  public final static String INST_TEXT6= res.getString("instText6");//I was in analyseInstructor class and in analyseTokens method
  // Student constants
  public final static String STUD_TEXT1= res.getString("studText1");//Wrong student matricule at line:
  public final static String STUD_TEXT2= res.getString("studText2");//Wrong student name at line:
  public final static String STUD_TEXT3= res.getString("studText3");//Wrong student course choice at line:
  public final static String STUD_TEXT4= res.getString("studText4");//in the student file:
  public final static String STUD_TEXT5= res.getString("studText5");//I was in StudentList class and in analyseTokens method
  public final static String STUD_TEXT6= res.getString("studText6");//Wrong number of students in the students file:
  public final static String STUD_TEXT7= res.getString("studText7");//Wrong number of student courses choices at line:
  // room constants
  public final static String ROOM_TEXT1= res.getString("roomText1");//Wrong name of room at line:
  public final static String ROOM_TEXT2= res.getString("roomText2");//Wrong capacity at line:
  public final static String ROOM_TEXT3= res.getString("roomText3");//Wrong function at line:
  public final static String ROOM_TEXT4= res.getString("roomText4");//Wrong caracteristics at line:
  public final static String ROOM_TEXT5= res.getString("roomText5");// in the room file:
  public final static String ROOM_TEXT6= res.getString("roomText6");//I was in roomList class and in analyseTokens method
  public final static String ROOM_TEXT7= res.getString("roomText7");//Wrong line format at line:
  // activity constants
  public final static String ACTI_TEXT1= res.getString("activText1");//Wrong activity name at line:
  public final static String ACTI_TEXT2= res.getString("activText2");//Wrong format of activity visibility at line:
  public final static String ACTI_TEXT3= res.getString("activText3");//Wrong number of activities at line:
  public final static String ACTI_TEXT4= res.getString("activText4");//Wrong teacher name at line:
  public final static String ACTI_TEXT5= res.getString("activText5");//Wrong number of blocs at line:
  public final static String ACTI_TEXT6= res.getString("activText6");//Wrong empty line at line:
  public final static String ACTI_TEXT7= res.getString("activText7");//Wrong duration of blocs at line:
  public final static String ACTI_TEXT8= res.getString("activText8");//Wrong days and times of blocs at line:
  public final static String ACTI_TEXT9= res.getString("activText9");//Wrong format of fixed rooms at line:
  public final static String ACTI_TEXT10= res.getString("activText10");//Wrong name of preferred rooms at line:
  public final static String ACTI_TEXT11= res.getString("activText11");//Wrong type of rooms:
  public final static String ACTI_TEXT12= res.getString("activText12");//Wrong format of pre-affected rooms at line:

  public final static String FILE_TTS  = res.getString("fileTTS");//Fichier_Grille, unp
  public final static String O_TTSTD  = res.getString("oTTSTD");//Grille, unp
  public final static String CLOSE_TTS  = res.getString("closeTTS");//Fermer grille, unp
  public final static String SAVE_TTS  = res.getString("saveTTS");//Enregistrer grille, unp
  public final static String SAVE_AS_TTS  = res.getString("saveAsTTS");//Enregistrer grille sous, unp

  public final static String SB_TOTAL = res.getString("sbTotal"); // Total
  public final static String SB_T_ACT = res.getString("sbTAct"); // Act
  public final static String SB_T_INST = res.getString("sbTInst"); // Ens
  public final static String SB_T_ROOM = res.getString("sbTRoom"); // Loc
  public final static String SB_T_STUD = res.getString("sbTStu"); // Stu
  public final static String SB_T_EVENT = res.getString("sbTEvent"); // Blo
  public final static String SB_T_ASSIG = res.getString("sbTAssig"); // Pla

  public final static String SB_CONF = res.getString("sbConf"); // Conflicts
  public final static String SB_C_INST = res.getString("sbCInst"); // Ens
  public final static String SB_C_ROOM = res.getString("sbCRoom"); // Loc
  public final static String SB_C_STUD = res.getString("sbCStu"); // Stu

  public DConst() {
  }
}