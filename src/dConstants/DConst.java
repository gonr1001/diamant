  /**
 *
 * Title: DConst 
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
 * @since JDK1.3
 */

package dConstants;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class DConst {

	static ResourceBundle res = ResourceBundle.getBundle("dConstants.DStringFrRes");


  	public final static String V_DATE = "v 2.3.3 1 septembre 09";

  	/** The number format for Hours **/
    public final static NumberFormat HourFormat = NumberFormat.getIntegerInstance();

	public final static String JVM = "1.6.0_03"; 

	public final static String INCORRECT_JVM = res.getString("incorrect_JVM");
	
	public final static String FILE_VER_NAME1_5 = res.getString("fileVerName1_5"); // "Diamant1.5" 
	public final static String FILE_VER_NAME1_6 = res.getString("fileVerName1_6"); // "Diamant1.6" 
	public final static String FILE_VER_NAME1_6_1 = res.getString("fileVerName1_6_1"); // "Diamant1.6.1" 
	public final static String FILE_VER_NAME_XML1_7 = res.getString("fileVerNameXML1_7"); // "DiamantXML1.7" 
	
	public final static String FILE_HEADER_NAME1_5 = res.getString("fileHeaderName1_5"); // "1.5" 
	public final static String FILE_HEADER_NAME1_6 = res.getString("fileHeaderName1_6"); // "1.6" 
	public final static String FILE_HEADER_NAME2_1 = res.getString("fileHeaderName2_1"); // "2.1"
	
  //********** Shared and others constants ***************************
  public final static String APP_NAME = res.getString("appName");//Diamant 1.6
  public final static String BUT_CLOSE  = res.getString("close");//Fermer
  public final static String BUT_OK = res.getString("butOK");//Ok
  public final static String BUT_CANCEL = res.getString("butCancel");//Annuler
  public final static String BUT_APPLY = res.getString("butApply");//Appliquer
  public final static String BUT_VALIDATE = res.getString("butValidate");//Valider
  public final static String BUT_OPTIONS = res.getString("butOptions");//Options
  public final static String BUT_BROWSE = res.getString("butBrowse");//Parcourir
  public final static String BUT_PLACE = res.getString("butPlace");//Placer
  public final static String BUT_FIGE = res.getString("butFige");//Figer
  public final static String BUT_ADD = res.getString("butAdd");//Ajouter
  public final static String BUT_REMOVE = res.getString("butRemove");//Remove
  public final static String BUT_CHANGE = res.getString("butChange");//Change
  public final static String BUT_SAVE = res.getString("butSave");//Enregistrer
  public final static String BUT_SAVE_AS = res.getString("butSaveAs");//Enregistrer sous
  public final static String SORT_TITLE = res.getString("sortTitle");//Trier
  public final static String TOKENSEPARATOR = ".";//Sans titre
  public final static String NO_NAME = res.getString("noName");//Sans titre
  public final static String DIA = res.getString("dia");//dia
  public final static String DIM = res.getString("dim");//dim
  public final static String XML = res.getString("xml");//xml
  public final static String TXT = res.getString("txt");//txt
  public final static String DOT_DIA = res.getString("dotDia");//.dia
  public final static String DOT_DIM = res.getString("dotDim");//.dim
  public final static String DOT_XML = res.getString("dotXml");//.xml
  public final static String DOT_TXT = res.getString("dotTxt");//.txt
  public final static String SIG = res.getString("sig");//sig
  public final static String DGH = res.getString("dgh");//dgh, unp
  public final static String DIM_FILE = res.getString("dimFile");//*.dim
  public final static String DIA_FILE = res.getString("diaFile");//*.dia
  public final static String SIG_FILE = res.getString("sigFile");//*.sig
  public final static String DGH_FILE = res.getString("dghFile");//*.dgh, unp
  public final static String XML_FILE = res.getString("xmlFile");//*.xml
  public final static String TXT_FILE = res.getString("txtFile");//*.txt
  public final static String ALL = res.getString("all");//all
  public final static String NO_ROOM_INTERNAL = res.getString("noRoomInternal");//"-----!-"
  public final static String NO_ROOM_EXTERNAL = res.getString("noRoomExternal");//"xxxxxx"
  public final static String NO_ROOM_EXPORT = res.getString("noRoomExport");//"      "

  public final static String PROBLEM = res.getString("problem");//� corriger, unp

  public final static String MFONTDialog = "Dialog";// unp????pas trouve dans DStringFrRes
  public final static int NPT11 = 11;//// unp????pas trouve dans DStringFrRes
  public final static boolean TT_STRUC = true;//unp????pas trouve dans DStringFrRes
  public final static int NO_TYPE = 0;
  public final static int CYCLE = 1;
  public final static int EXAM = 2;
  public final static int CYCLEANDEXAM = 3;
  public final static int STI_NB_OF_DAYS=5;
  public final static int STI_NB_OF_PERIODS_A_DAY=14;
  public final static int STI_BEGIN_HOUR=8;
  public final static int STI_BEGIN_MINUTE=30;
  public final static int COURSE_NAME_LENGTH=6;

  public final static int STUDENT_ID_LENGTH=9;
  public final static int STUDENT_KEY_LENGTH=8;
  public final static int STUDENT_PROGRAM_LENGTH=6;


  public final static int ADJUST_HEIGHT = 92;
  public final static int ADJUST_WIDTH = 6;
  public final static int DIALOG_DIM = 400;   //Dimension for reportOptionsDlg
  public final static int CENTER_WIDTH = 20;  // Width for the centerPanel
  public final static int CENTER_HEIGHT = 75; // Height for the centerPanel
  public final static int LIST_LENGHT = 30;

  // Color constants
  public final static Color COLOR_BLACK = Color.black; // Default color
  public final static Color COLOR_AUX = Color.gray; // Other color
  public final static Color COLOR_ROOM = Color.blue; // Rooms conflicts color
  public final static Color COLOR_INST = Color.red;  // Instructors conflicts color
  public final static Color COLOR_STUD = Color.magenta; // Students conflicts color
  public final static Color COLOR_QUANTITY_DLGS = Color.blue; // Students conflicts color
  //DDocument

  public final static String BLOCS = "BLOCS";//pas trouve dans DStringFrRes

  //return constant
  public final static String CR_LF = "\r\n"; 
  public final static String CR = "\r"; 
  public final static String SPACE = " ";
  
 
  // TTStructure XML file tags
	public static final String TTXML_DXTIMETABLE = "DXTimeTable";

	public static final String TTXML_TTCYCLE = "TTcycle";

	public static final String TTXML_CYCLEID = "cycleID";

	public static final String TTXML_PLENGTH = "pLength";

	public static final String TTXML_TTDAYS = "TTdays";

	public static final String TTXML_TTPERIOD = "TTperiod";

	public static final String TTXML_PERIODID = "periodID";

	public static final String TTXML_BEGINTIME = "BeginTime";

	public static final String TTXML_ENDTIME = "EndTime";

	public static final String TTXML_PRIORITY = "Priority";

	public static final String TTXML_TTSEQUENCE = "TTsequence";

	public static final String TTXML_SEQUENCEID = "sequenceID";

	public static final String TTXML_TTPERIODS = "TTperiods";

	public static final String TTXML_TTDAY = "TTday";

	public static final String TTXML_DAYREF = "dayRef";

	public static final String TTXML_TTSEQUENCES = "TTsequences";

	public static final String TTXML_DAYID = "dayID";
    
  // Student file constants
  public final static char LINE_DESCRIPTOR_S = 'E';
  public final static char LINE_DESCRIPTOR_C = 'C';
  public final static char LINE_DESCRIPTOR_T = 'T';

  //**********Menu names constants ***************************
  //File menu
  public final static String M_FILE  = res.getString("file");//Fichier
  public final static String NEW_TT = res.getString("newTT");//Nouvel horaire
  public final static String NTT_CY = res.getString("nTTCy");//Horaire cycle
  public final static String NTT_EX = res.getString("nTTEx");//Horaire Examen
  public final static String NEW_TTS = res.getString("newTTS");//Nouvelle grille
  public final static String NTTS_CY = res.getString("nTTSCy");//Grille cycle
  public final static String NTTS_EX = res.getString("nTTSEx");//Grille examen
  public final static String OPEN  = res.getString("open");//Ouvrir horaire
  public final static String OPEN_TTS  = res.getString("openTTS");//Ouvrir grille
  public final static String SAVE  = res.getString("save");//Enregistrer
  public final static String SAVE_PROMPT = res.getString("savePrompt");//Voulez vous enregistrer?
  public final static String SAVE_AS  = res.getString("saveAs");//Enregistrer zouz
  public final static String DEF_F_M  = res.getString("defFM");//Definir fichiers � importer
  public final static String IMP_A_M  = res.getString("impAM");//Importer automatiquement
  public final static String IMP_SELECT  = res.getString("impSelect");//Importation selective
  public final static String IMP_SELECT_ROOM  = res.getString("impSelectRoom");//Importation selective de locaux
  public final static String IMP_SELECT_INST  = res.getString("impSelectInst");//Importation selective d'enseignants
  public final static String IMP_SELECT_ACT  = res.getString("impSelectAct");//Importation selective d'activit�
  public final static String IMP_SELECT_STUD  = res.getString("impSelectStud");//Importation selective d'�tudiants
  public final static String EXPO  = res.getString("expo");//Exporter
  public final static String EXIT  = res.getString("exit");//eXit
  //Edition Menu
  public final static String EDIT = "Edition";//unp
  final static String UNDO = "Annuler";//unp, the text is already in buttonCancel
  final static String REDO = "Repeter";//unp
  final static String CUT = "Couper";//unp
  public final static String COPY = res.getString("copy");
  final static String PASTE = "Coller";
  final static String CLEAR = "Effacer";
  //Assign menu
  public final static String M_ASSIGN = res.getString("assign");//Affectation
  public final static String INST_AVAILABILITY_M = res.getString("instAssignM");//Enseignants
  public final static String LISTS_INSTRUCTOR_TD = res.getString("listsInstructorTD"); // "Listes d'enseignants"},
  public final static String ASSIGN_ACTIVITIES_M = res.getString("ActiAssignM");//Activit�s
  public final static String ASSIGN_SECTION_M = res.getString("GroupAssignM");//Groupes
  public final static String ROOM_AVAILABILITY_M= res.getString("LocauxAssignM");//Locaux
  public final static String EVENTS_ASSIGN_M= res.getString("EventsAssignM");//�venements
  public final static String CONFLICTS_OF_AN_EVENT= res.getString("conflictsOfAnEvent");//Conflits d'un �v�nement
  
  public final static String MANUAL_ASSIGN_M= res.getString("ManualAssignM");//�venements
  public final static String DEFINE_SET_M = res.getString("DefineSetM");//D�finir ensemble
  public final static String PARTIAL_TTSTRUCTURE_M = res.getString("PartialTTStructureM");//Grille Partielle
  //Modification menu
  public final static String MODIFICATION = res.getString("ModificationM");//Modification
  public final static String EVENTS_MODIF_M = res.getString("EventsModifM");//Modification �venements
  public final static String ACTIVITY_MODIF_M = res.getString("ActModifM");//Modification �venements
  public final static String SPECIAL_IMPORT = res.getString("specialImport");//Modification �venements
  //Optimization menu
  public final static String OPTIMIZATION = res.getString("OptimizationM");//Optimisation
  public final static String M_INITIAL_ASSIGN = res.getString("initialAssignM");//Affectation Initiale
  public final static String FIRSTALGORITHM = res.getString("firstAlgo");//Optimizattion
  public final static String SECTION_PARTITION = res.getString("sectionPartition");//Formation de groupes
  public final static String STUDENTS_REPARTITION = res.getString("studentRepartition");//Brassage d'�tudiants
  public final static String STUDENTMIXINGBAL = res.getString("studentMixingBal");//Brassage d'�tudiants balanc�
  public final static String STUDENTMIXINGMIDBAL = res.getString("studentMixingMidBal");//Brassage d'�tudiants balanc�
  public final static String STUDENTMIXINGOPTI = res.getString("studentMixingOpti");//Brassage d'�tudiants optimal
  public final static String STUDENTMIXINGPERSO = res.getString("studentMixingPerso");//Brassage d'�tudiants personalis�
  //Report menu
  public final static String REPORT_M = res.getString("ReportM");//Rapports
  public final static String REPORTS = res.getString("Reports");//Rapports...

  //Preferences menu
  public final static String PREF = res.getString("pref");//Preferences

  public final static String PLAF_M = res.getString("plafM");//options L&F
  public final static String CONFLICTS = res.getString("conflicts");//options L&F

  public final static String DISPLAY_TT = res.getString("displayTT");//"affichage grille"
  public final static String SIMPLE = res.getString("simple");//"Simple"
  public final static String SPLIT_VERTICAL = res.getString("splitVertical");//"Detaill�  + Split V"
  public final static String SPLIT_HORIZONTAL = res.getString("splitHorizontal");//"Detaill� + Split H"

  //Help menu
  public final static String HELP = res.getString("help");//Aide
  public final static String ABOUT_M = res.getString("aboutM");//A propos de
  
  //Multi site menu
  public final static String MULTI_SITE = res.getString("multiSite");//multi Site
  
  public final static String NEW_TT_TD  = res.getString("newTTTD");//Indiquez grille horaire, unp
  public final static String NTT_CY_M  = res.getString("nTTCyM");//Cycle, unp
  public final static String NTT_EX_M  = res.getString("nTTExM");//Examen, unp

  //User menu
  public final static String IN_TEST = res.getString("userTest");//Test utilisateur version beta
  public final static String SUBMENU1 = res.getString("user1");//Sous menu utilisateur 1
  public final static String SUBMENU2 = res.getString("user2");//Sous menu utilisateur 2
  public final static String FEATURE_1_6_2 = res.getString("feature");//Sous menu feature 1.6.2
  public final static String ROOMASSIGN = res.getString("roomAssign");//Sous menu feature 1.6.2 --> Affectation de locaux
  public final static String EVENTASSIGN = res.getString("eventAssign");//Sous menu feature 1.6.2 --> Affectation d'�v�nements
  public final static String CONFLICTEFFECT = res.getString("conflictEffect");//Sous menu feature 1.6.2 --> Affectation Option de conflits
  

  //**********Dialogs constants ***************************
  public final static Font JLISTS_FONT = new java.awt.Font("Courier", Font.PLAIN, 12);

  //File dialogs
  public final static String NEW_TT_M  = res.getString("newTTM");//Grille horaire
  public final static String NTT_CY_TD  = res.getString("nTTCyTD");//Grille horaire cycle
  public final static String NTT_EX_TD  = res.getString("nTTExTD");//Grille horaire examen
  public final static String DEF_F_TD  = res.getString("defFTD");//Definir fichiers pour l'importation automatique
  public final static String IMP_A_TD  = res.getString("impATD");//Importation de fichiers
  public final static String IMP_A_SUC  = res.getString("impASuc");//Fichiers import�s avec succes!!!
  public final static String EXISTS  = res.getString("exists");//existe
  public final static String REPLACE  = res.getString("replace");//remplacer?

  //DefFilesToImportDlg
  public final static String DEF_F_D1  = res.getString("defFD1"); // "Les cours"
  public final static String DEF_F_D2  = res.getString("defFD2"); // "Inscription d'�tudiants"
  public final static String DEF_F_D3  = res.getString("defFD3"); // "Disponibilit�s d'enseignants"
  public final static String DEF_F_D4  = res.getString("defFD4"); // "Les locaux"
  public final static String DEF_F_D5  = res.getString("defFD5"); //"Au moins un champ ne contient aucune valeur.\n"
  public final static String DEF_F_D6  = res.getString("defFD6"); // "Veuillez entrer un fichier pour chaque type demand�."
  public final static String DEF_F_D7  = res.getString("defFD7"); // "Sauvegard�s dans : "
  public final static String DEF_F_D8  = res.getString("defFD8"); // "Fichiers d'importation"
  //Assign dialogs
  public final static String INST_ASSIGN_TD = res.getString("instAssignTD");//Disponibilit� enseignants
  public final static String INST_ASSIGN_D = res.getString("instAssignD");//To be defined, unp
  public final static String TO_LEFT  = res.getString("toLeft");//��
  public final static String TO_RIGHT  = res.getString("toRight");//��
  public final static String TO_UP  = res.getString("toUp");//+
  public final static String TO_DOWN  = res.getString("toDown");//-
  public final static String SHOW_ALL = res.getString("all"); //Tous
  //ActivityDlg
  public final static String ACT_LIST  = res.getString("actList"); //Liste des activit�s
  public final static String SHOW  = res.getString("show"); //Aficher
  public final static String NOT_INCLUDED  = res.getString("notIncluded");//Non inclue(s)
  public final static String INCLUDED  = res.getString("included");//Inclue(s)
  public final static String  T_AFFEC_DLG = res.getString("tAffecDlg");//"Affectation d'�venement(s)"
  //InstructorAvailabiliyDlg and roomsAvailabiliyDlg
  public final static String AVAILABILITIES = res.getString("dispo");//Disponibilit�s
  public final static String ROOMS_DLG_TITLE = res.getString("roomsDlgTitle");
  public final static String NOT_DISPO = res.getString("notDispo");
  
  //GroupDlg
  public final static String ACT_STUD_NOT_ASSIGNED = res.getString("ActStudNotAss");//�tudiants non assign�s
  public final static String ACT_STUD_ASSIGNED = res.getString("ActStudAss");//�tudiants assign�s
  public final static String ACTIVITY = res.getString("Activity");//Activit�
  public final static String SECTION_DLG_TITLE = res.getString("SectionDlgTitle");//Titre du dialog
  public final static String SECTION = res.getString("Section");//Groupe
  public final static String NUMBER_OF_ELEMENTS = res.getString("NumberOfElements");//Nombre d'�l�ments
  public final static String TYPE = res.getString("Type");//Nature
  public final static String SORT_BY_MATRICUL = res.getString("SortByMatricul");//par matricule
  public final static String SORT_BY_NAME = res.getString("SortByName");//par nom
  public final static String SORT_BY_PROGRAM = res.getString("SortByProgram");//par programme
  public final static String CHAR_FIXED_IN_GROUP = " *";
  public final static String CHAR_NOTFIXED_IN_GROUP = "  ";
  public final static String INFORMATION_DLG_NAME = res.getString("InfoDlgName");//Info Dialog name
  public final static String INFORMATION_DLG_MES = res.getString("InfoDlgMes");//Info Dialog message
  public final static int NUM_OF_LISTS_IN_ASSIGNED_PANEL=4;
  //EventsDlg
  public final static String EVENTS_DLG_TITLE = res.getString("EventsDlgTitle");//�venements
  public final static String EVENTS_FIXED = res.getString("EventsFixed");//Fig�s
  public final static String EVENTS_PLACED = res.getString("EventsPlaced");//Plac�s
  public final static String EVENTS_NOT_PLACED = res.getString("EventsNotPlaced");//Non plac�s
  public final static String ROOM_CAPACITY_DESC = res.getString("Places");//places
  public final static String FIXED_ROOM_STATE = res.getString("RoomFixed");//Fig�s
  public final static String PLACED_ROOM_STATE = res.getString("RoomPlaced");//Plac�s
  public final static String NOT_PLACED_ROOM_STATE = res.getString("RoomNotPlaced");//Non plac�s
  //DefineSetDlg


  //PartialTTStructureDlg

  //Modification

  public final static String NUMBER_OF_TYPES = res.getString("numberOfTypes");//"Nombre de natures"
  public final static String NUMBER_OF_SECTIONS = res.getString("numberOfSections");//"Nombre de groupes"
  public final static String NUMBER_OF_UNITIES = res.getString("numberOfUnities");//"Nombre de blocs"


  // Manual improvementDlg
   public final static String MANUALIMPROVEMENT_DLG_TITLE = res.getString("ManImpDlgTitle");//�venements
   public final static String CONFLICTS_OF_AN_EVENT_DLG_TITLE = res.getString("ConflictsOfAnEventDlgTitle");//�venements

  //Export Dialog
   public final static String EXPORT_MESSAGE = res.getString("ExportMessage");//Message d'exportation
   public final static String EXPORTED = res.getString("Exported");           //Message d'exportation

  //Optimisation dialogs
  public final static String INITIAL_ASSIGN_MESSAGE = res.getString("InitialAssignMessage");//Affectation initiale effectu�e
  public final static String ROOM_ASSIGN_MESSAGE = res.getString("RoomAssignMessage");//Affectation de locaux effectu�e
  public final static String TT_BUILD_MESSAGE = res.getString("TTBuildMessage");//Construction d'horaire termin�e
  public final static String STUDENTS_MIXING_MESSAGE = res.getString("StudentsMixingMessage");//brassage d'�tudiants termin�
  //Preferences dialogs
  public final static String PLAF_TD = res.getString("plafTD");//Options L&F unp repete
  public final static String PLAF_D = res.getString("plafD");//Choisisez un L&F

  //Report dialogs
  public final static String REPORT_DLG_TITLE = res.getString("ReportDlgTitle");//Rapports
  public final static String REPORT_DLG_TAB1 = res.getString("ReportDlgTab1");//Complet
  public final static String REPORT_DLG_TAB2 = res.getString("ReportDlgTab2");//Conflits
  public final static String REPORT_DLG_TAB3 = res.getString("ReportDlgTab3");//Importation
  public final static String REPORT_DLG_TAB_MESS = res.getString("ReportDlgTabMess");//Veuillez faire click sur le button options
  public final static String REPORT_OPTIONS_DLG_TITLE = res.getString("ReportOptionsDlgTitle");//Options de Rapport
  public final static String REPORT_OP_FIELDS_NOT_CHOICED = res.getString("ReportOpFieldsNotChoiced");//Champs non choisis
  public final static String REPORT_OP_FIELDS__CHOICED = res.getString("ReportOpFieldsChoiced");//Champs choisis
  public final static String REPORT = res.getString("Report");//Rapport
  public final static String REPORT_PRODUCED_AT = res.getString("ReportProducedAt");//Produit en
  //Activities report
  public final static String R_ACTIVITY_NAME = res.getString("RActivityName");//Nom d'activit�
  public final static String R_TYPE_NAME = res.getString("RTypeName");//Type
  public final static String R_SECTION_NAME = res.getString("RSectionName");//Section
  public final static String R_TIMETABLE_NAME = res.getString("RTimeTableName");//Horaire
  public final static String R_UNITY_NAME = res.getString("RUnityName");//Unit�
  public final static String R_TIME_LENGTH = res.getString("RDuration");//Dur�e
  public final static String R_DAY_NUMBER = res.getString("RDayNumber");//Jour num�ro
  public final static String R_DAY_NAME = res.getString("RDayName");//Jour nom
  public final static String R_ACTIVITY_BEGIN_HOUR = res.getString("RActivityBeginHour");//Heure de d�but
  public final static String R_ACTIVITY_END_HOUR = res.getString("RActivityEndHour");//Heure de fin
  public final static String R_INSTRUCTOR_NAME = res.getString("RInstructorName");//Nom instructor
  public final static String R_INSTRUCTOR_NAME_AVAIL = res.getString("RInstructorNameAvail");//Dispo de l'enseignant
  public final static String R_ROOM_NAME = res.getString("RRoomName");//Nom Salle
  public final static String R_ASSIGN = res.getString("RAssign");//Affectation
  public final static String R_STUDENT_NAME = res.getString("RStudentName");//Nom Salle
  public final static String R_STUDENT_SIZE_NAME = res.getString("RStudentSizeName");//Student size

   //the associated string length for each item above
  public final static String R_ACTIVITY_NAME_L = "7";
  public final static String R_TYPE_NAME_L = "5";
  public final static String R_SECTION_NAME_L = "5";
  public final static String R_UNITY_NAME_L = "5";
  public final static String R_DURATION_L = "6";
  public final static String R_DAY_NUMBER_L = "5";
  public final static String R_DAY_NAME_L = "6";
  public final static String R_ACTIVITY_BEGIN_HOUR_L = "6";
  public final static String R_ACTIVITY_END_HOUR_L = "6";
  public final static String R_INSTRUCTOR_NAME_L = "20";
  public final static String R_ROOM_NAME_L = "12";
  public final static String R_STUDENT_SIZE_L = "7";
  //Conflicts report
  public final static String R_SEQUENCE_ID = res.getString("RSequenceID");//S�quence
  public final static String R_PERIOD_BEGIN = res.getString("RPeriodBegin");//P�riode(heure de d�but)
  public final static String R_EVENT1_ID = res.getString("REvent1_ID");//Evenement 1
  public final static String R_EVENT2_ID = res.getString("REvent2_ID");//Evenement 2
  public final static String R_NUMBER_OF_CONFLICTS = res.getString("RNumberOfConflicts");//Num�ro de conflits
  public final static String R_TYPE_OF_CONFLICT = res.getString("RTypeOfConflict");//Type de conflit
  public final static String R_STUDENT_CONFLICT = res.getString("RStudentConflict");//Conflit d'�tudiant
  public final static String R_ROOM_CONFLICT = res.getString("RRoomConflict");//Conflit de salle
  public final static String R_INSTRUCTOR_CONFLICT = res.getString("RInstructorConflict");//Conflit d'insdtructor
  public final static String  R_ELEMENTS_IN_CONFLICT = res.getString("RElementsInConflict");//Elements en conflit
  //the associated string length for each item above
  public final static String R_SEQUENCE_ID_L = "8";
  public final static String R_UNITY_BEGIN_H_L = "6";
  public final static String R_PERIOD_BEGIN_H_L = "6";
  public final static String R_EVENT1_ID_L = "14";
  public final static String R_EVENT2_ID_L = "14";
  public final static String R_NUMBER_OF_CONFLICTS_L = "9";
  public final static String R_TYPE_OF_CONFLICT_L = "14";
  public final static String R_STUDENT_CONFLICT_L = "8";
  public final static String R_ROOM_CONFLICT_L = "15";
  public final static String R_INSTRUCTOR_CONFLICT_L = "15";
  public final static String R_ELEMENTS_IN_CONFLICT_L = "26";
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
  public final static String STUD_TEXT8= res.getString("studText8");//Wrong number of elements at line:
  public final static String STUD_TEXT9= res.getString("studText9");//Wrong site:
  // room constants
  public final static String ROOM_TEXT1= res.getString("roomText1");//Wrong name of room at line:
  public final static String ROOM_TEXT2= res.getString("roomText2");//Wrong capacity at line:
  public final static String ROOM_TEXT3= res.getString("roomText3");//Wrong function at line:
  public final static String ROOM_TEXT4= res.getString("roomText4");//Wrong caracteristics at line:
  public final static String ROOM_TEXT5= res.getString("roomText5");// in the room file:
  public final static String ROOM_TEXT6= res.getString("roomText6");//I was in roomList class and in analyseTokens method
  public final static String ROOM_TEXT7= res.getString("roomText7");//Wrong line format at line:
  public final static String ROOM_TEXT8= res.getString("roomText8");//Wrong name of site at line:
  // activity constants
  public final static String ERR_ACTIVITY_NAME = res.getString("errActivityName");//Erreur dans le nom de l'activit�
  public final static String ERR_ACTIVITY_LINE = res.getString("errActivityLine");//� la ligne :  
  public final static String ERR_ACTIVITY_FILE = res.getString("errActivityFile");//Du fichier ou section activit�s  
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
  public final static String ACTI_TEXT12= res.getString("activText12");//Wrong format of pre-assigned activity at line:
  public final static String ACTI_TEXT13= res.getString("activText13");//Wrong number of instructors at line:
  public final static String ACTI_TEXT14= res.getString("activText14");//Wrong group name at line:
  public final static String ACTI_TEXT15= res.getString("activText15");//Wrong site name at line:
  public final static String ACTI_TEXT16= res.getString("activText16");//Wrong capacity format at line: 

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

  public final static String SB_CONF = res.getString("sbConf");    // Conflicts
  public final static String SB_C_INST = res.getString("sbCInst"); // Ens
  public final static String SB_C_ROOM = res.getString("sbCRoom"); // Loc
  public final static String SB_C_STUD = res.getString("sbCStud"); // Stu


  public final static String TT_FILE = res.getString("ttFile"); // "S814.HORAIRE"
  public final static String TT_STUD_FILE = res.getString("ttStudFIle"); // "S813.ASSGRO"


  public final static String ERROR_XML_FILE = res.getString("errorXMLFile"); // "XML file is corrupted";

  public final static String SEPARATOR = res.getString("separator"); // "--";
  public final static String INSTRUCTOR_SEP = res.getString("instructorSep"); // "-Enseignants-";
  public final static String ROOM_SEP = res.getString("roomSep"); // "-Locaux-";
  public final static String STUDENT_SEP = res.getString("studentSep"); // "-Etudiants-";
  public final static String ERROR_TAG = res.getString("errorTag"); // "Error tag";
  public final static String NOT_ROOM = res.getString("notRoom"); // "Not room";
  public final static String NOT_INSTRUCTOR = res.getString("notInstructor"); // "Not instructor";
                      //"Not Student Activity";
  public final static String NO_INSTRUCTOR = res.getString("noInstructor"); // "------"
  public final static String NOT_STUD_ACT = res.getString("notStudentActivity");
                      //"Not Student Group";
  public final static String NOT_STUD_GROUP = res.getString("notStudentGroup"); 
  
  public final static String NAME_AC = res.getString("nameAC"); // "nameAC";
  public final static String CATEGORY_AC = res.getString("categoryAC"); // "categoryAC";
  public final static String FUNCTION_AC = res.getString("functionAC"); // "functionAC";
  public final static String STATE_AC = res.getString("stateAC"); // "stateAC";

  public final static String NAME_LABEL = res.getString("nameLabel"); // "Nom";
  public final static String CATEGORY_LABEL = res.getString("categoryLabel"); // "Category";
  public final static String FUNCTION_LABEL = res.getString("functionLabel"); // "function";
  public final static String CAPACITY_LABEL = res.getString("capacityLabel"); // "capacity";
  public final static String STATE_LABEL = res.getString("stateLabel"); // "capacity";

  public final static String DELETED_ELEMENT = res.getString("deletedElement"); // "�l�ment supprim�";
  public final static String ADDED_ELEMENT = res.getString("addedElement"); // "�l�ment ajout�";
  public final static String CHANGED_ELEMENT = res.getString("changedElement"); // "�l�ment modifi�";
  public final static String UNCHANGED_ELEMENT = res.getString("unchangedElement"); // "�l�ment no modifi�";

  public final static String HOUR_SEPARATOR = res.getString("hourSeparator"); //"h";

  public final static String SAVE_SEPARATOR = res.getString("saveSeparator"); //"=================================";
  public final static String SAVE_SEPARATOR_VIS = "!!!!!!!!!!"; //"!!!!!!!!!!";
  public static final String LIMIT = res.getString("limit"); // "limite :";;
  public final static String ERROR_XML =res.getString("errorXML"); // "XML file is corrupted";
  
  public static final int BEGIN_STUDENT_MATRICULE=0;
  public static final int END_STUDENT_MATRICULE=8;
  public static final int BEGIN_STUDENT_NAME=21;
  public static final int END_STUDENT_NAME=30;
  public static final int BEGIN_STUDENT_NUMBER_OF_COURSE=30;
  public static final int END_STUDENT_NUMBER_OF_COURSE=32; 
  public final static int NUMBER_OF_FILES = 4; 
  public final static int ROOM_NUM_TOKENS = 5;
  public static final int T_IN_STUDENT_LINE = 3;
  public static final int T_IN_STUDENT_NAME = 2;
  public static final int T_IN_STUDENT_COURSE_LINE = 3;
  public static final int STUD_COURSE_LENGTH = 7;
  public static final int STUD_COURSE_GROUP_LENGTH = 9;
  public static final int STUD_SITE_LENGTH = 3;
  
  
  public static final int NO_GROUP = -1;
  // activity
  public static final int NUMBER_OF_TOKEN_COURSE_LINE = 4;
  public static final int SIZE_OF_COURSE_TOKEN = 7;
  public static final int SIZE_OF_GROUP_TOKEN = 2;
  public static final int ACT_SITE_LENGTH = 3;
  public static final int ACT_CAPACITY_LENGTH = 5;
  public static final String ACT_DEFAULT_CAPACITY ="99999";
  public static final String ACT_UNITY_SEPARATOR=";";
  public static final String ACT_INST_SEPARATOR=":";
  
  
  // rooms and sites v 1.7
  public static final int ROOM_1_DOT_5_USELESS_HEADER= 3;
  public static final int ROOM_1_DOT_6_USELESS_HEADER= 4;
  public static final int ROOM_USELESS_DIA_HEADER= 1;
  public static final int ROOM_1DOT5_TOKEN_COUNT = 5;
  public static final int ROOM_1DOT6_TOKEN_COUNT = 7;
  public static final int ROOM_DIA_TOKEN_COUNT = 8;
  public static final int ROOM_NAME_TOKEN = 0;
  public static final int ROOM_CAPACITY_TOKEN = 1;
  public static final int ROOM_FUNCTION_TOKEN = 2;
  public static final int ROOM_CARACTERICTICS_TOKEN= 3;
  public static final int ROOM_SITE_TOKEN = 4;
  public static final int ROOM_CATEGORY_TOKEN= 5;
  public static final int ROOM_DESCRIPTION_TOKEN= 6;
  public static final int ROOM_AVAILABILITY_TOKEN= 7;
  public static final String ROOM_FIELD_SEPARATOR_TOKEN= ";";
  public static final String ROOM_CHAR_SEPARATOR_TOKEN= ",";
  public static final String ROOM_DEFAULT_SITE= "SHE";
  public static final String ROOM_STANDARD_CAT= "CAT 1";

  
  public static final String CONFLICT_LIMITS_SEPARATOR= ";";
  // students v 1.7
  public static final int STUDENT_COURSE_LENGTH = 7;
  public static final String STUDENT_STANDARD_SITE= "SHE";
  public static final int STUDENT_NAME_LINE = 0;
  public static final int STUDENT_COURSE_LINE = 1;
  public static final int STUDENT_LINE_DESC_TOKEN=0;
  public static final String STUDENT_TOKEN_SEPARATOR= " ";
  public static final String ACTIVITY_TOKEN_SEPARATOR= " ";
  public static final int STUDENT_MAT_TOKEN=1;
  public static final int STUDENT_COURSE_TOKEN=1;
  public static final int STUDENT_SITE_TOKEN=2;
  public static final int STUDENT_FIRST_NAME_TOKEN=2;
  public static final int STUDENT_LAST_NAME_TOKEN=3;
  
  // activities v 1.7
  public static final String ACTIVITY_STANDARD_SITE= "SHE";
  public static final String ACTIVITY_STANDARD_CAPACITY= "99999";
  public static final String ACTIVITY_NAME_TOKEN_SEPARATOR= " ";
  public static final int ACTIVITY_SITE_TOKEN=2;
  
  public static final String ALL_SITES = res.getString("allSites");//Tous
  public static final int X_OFFSET = 100;
  public static final int Y_OFFSET = 100;
  //public static final char STUDENT_NAME_DESC_TOKEN='E';
  //public static final char STUDENT_COURSES_DESC_TOKEN='C';
  /*public DConst() {
  }*/
  
  // availability constants
  public static final int AVAILABILITY_YES = 1;
  public static final int AVAILABILITY_NO = 5;
  public static final int AVAILABILITY_USED = 2;
  public static final int AVAILABILITY_ASSIGN_DEFAULT = 0;
  public static final String AVAILABILITY_ASSIGN_SEPARATOR = "+";
  public static final String AVAILABILITY_SEPARATOR = "/";
  public static final String AVAILABILITY_DAY_SEPARATOR_ROOM = ",";  //in dia files
  public static final String AVAILABILITY_DAY_SEPARATOR_INST = CR_LF;  //in dia files
  public static final String AVAILABILITY_PERIOD_SEPARATOR = SPACE;  //in dia files
  
  public static final String USEDSHE="SHE";
  public static final String USEDLON="LON";
  public static final String USEDCOW="COW";
  public static final String DEFAULT_MIX_ALGO = "8";

public static final String INVALID_NUMBER_OF_INSTRUCTORS = "The number of instructors is incorrect ";
 
public static final String INVALID_AVAILABILITY_AT = "Invalid Availability  at line:";

public static final String INVALID_FILE_FILTER = "Invalid filter file !";

public static final String FILE_PRELOAD_FAILED = "File prelaod failed !";

public static final String IMPORT_STUDENTS = "IMPORT_STUDENTS: ";

public static final int SAVE_SEPARATOR_COUNT = 6;

public static final String PARTS_IN_DIA_SEPARATED_BY = "Wrong number of parts in .dia separated by ";

public static final int MINIMUN_NAME = 1;

public static final String INVALID_INSTRUCTOR_NAME = "Name of instructor should begin by a Letter at line:";

public static final String WRONG_TIME_TABLE_STRUCTURE = "Wrong time table structure!";

public static final String ACT_CB_SITE = "actionJCBSite";




  
}