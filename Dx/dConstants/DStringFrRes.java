package dResources;
/**
 *
 * Title: DStringFrRes $Revision: 1.53 $  $Date: 2003-09-29 20:21:47 $
 * Description: DStringFrRes is a class used to handle all constants Strings
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
 * @version $Revision: 1.53 $
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */



import java.util.*;

public class DStringFrRes extends java.util.ListResourceBundle {
  static final Object[][] contents = new String[][]{
	{ "appName", "Diamant 1.5 " },
	{ "vDate", "16 sep 2003" },
	{ "bye", "bye" },

    /**
    *  the menus and dialogs
    */
    { "file", "Fichier" },

    { "newTT", "Nouvel horaire" },
    { "newTTTD", "Indiquez grille horaire"},
    { "newTTM", "Grille horaire"},

    { "nTTCy", "Horaire cycle" },
    { "nTTCyTD", "Grille horaire cycle"},
    { "nTTCyM", "Cycle"},

    { "nTTEx", "Horaire examen" },
    { "nTTExTD", "Grille horaire examen"},
    { "nTTExM", "Examen"},

    { "open", "Ouvrir horaire" },
    { "oTTTD", "Horaire" },

    { "close", "Fermer" },
    //Separator
    { "save", "Enregistrer" },
    { "saveAs", "Enregistrer sous" },


    { "defFM", "Definir fichiers � importer" },
    { "defFTD", "Definir fichiers pour l'importation automatique"},
    { "defFD1", "Les cours" },
    { "defFD2", "Les �tudiants" },
    { "defFD3", "Les enseignants" },
    { "defFD4", "Les locaux" },
    { "defFD5", "Au moins une ligne est vide. \n"},
    { "defFD6", "Veuillez entrer un fichier pour chaque type demand�." },
    { "defFD7", "Sauvegard�s dans : " },
    { "defFD8", "Fichiers d'importation" },


    { "impAM", "Importer automatiquement" },
    { "impATD", "Importation de fichiers"},
    { "impAD", "To be defined" },
    { "impASuc","Fichiers import�s avec succes!!!"},

    { "expo", "Exporter" },
    //Separator
    { "exit", "Quitter" },


    { "copy", "Copier"},



    { "fileTTS", "Fichier_Grille" },
    { "newTTS", "Nouvelle grille"},
    /*{ "newTTTD", "Indiquez grille horaire"},
    { "newTTM", "Grille horaire"},*/


    { "nTTSCy", "Grille cycle" },
/*    { "nTTCyTD", "Grille horaire Cycle"},
    { "nTTCyM", "Cycle"},*/

    { "nTTSEx", "Grille examen" },
/*    { "nTTExTD", "Grille horaire Examens"},
    { "nTTExM", "Examen"},*/

    { "openTTS", "Ouvrir grille" },
    { "oTTSTD", "Grille" },

    { "closeTTS", "Fermer grille" },
    { "saveTTS", "Enregistrer grille" },
     { "saveAsTTS", "Enregistrer grille sous" },

    //Assign
    { "assign", "Affectation" },
    { "instAssignM", "Enseignants" },
    { "instAssignTD", "Disponibilit� enseignants"},
    {"ActiAssignM", "Activit�s"},
    {"GroupAssignM", "Groupes"},
    {"LocauxAssignM", "Locaux"},
    { "instAssignD", "To be defined"},
    {"EventsAssignM", "�venements"},
    {"ManualAssignM","Affectation manuelle"},
    {"DefineSetM","D�finir ensemble"},
    {"PartialTTStructureM","Grille Partielle"},

    //Optimization
    { "OptimizationM", "Optimisation"},
    {"initialAffectM", "Affectation Initiale"},


    //Modification
    {"ModificationM","Modification"},
    {"EventsModifM","Modification �venements"},

    //Report
    {"ReportM","Rapports"},
    {"",""},
    {"",""},
    {"",""},
    {"",""},
    {"",""},
    {"",""},
    {"",""},

    //Preferences
    { "pref", "Preferences"},
    { "plafM", "Options L&F"},
    { "plafTD", "Options L&F"},
    { "plafD", "Choisisez un L&F"},

    //Help
    { "help", "Aide"},
    { "aboutM", "A propos de "},
    { "aboutTD", "A propos de "},
    { "aboutD",  " \nCopyRight\n 2000 - 2003"},

    // common buttons
    {"butOK", "Ok"},
    {"butCancel", "Annuler"},
    {"butApply", "Appliquer"},
    {"butBrowse", "Parcourir"},
    {"butPlace", "Placer"},
    {"butFige", "Figer"},
    // common titles
    {"noName", "Sans titre"},
    {"dia", "dia"},
    {"dim", "dim"},
    {"xml", "xml"},
    {"dotDia", ".dia"},
    {"dotDim", ".dim"},
    {"dotXml", ".xml"},
    {"sig", "sig"},
    {"txt", "txt"},
    {"dgh", "dgh"},
    {"sigFile", "Fichier donn�es (*.sig)"},
    {"dimFile", "Fichier Diamant (*.dim)"},
    {"diaFile", "Fichier Diamant (*.dia)"},
    {"dghFile", "Fichier Grille Horaire (*.dgh)"},
    {"xmlFile", "Fichier Grille Horaire (*.xml)"},
    {"problem", "� corriger"},
    {"sortTitle", "Trier"},



    //Instructor constants
    {"instText1","Wrong number of instructors in the instructor file:"},
    {"instText2","Wrong name of instructor at line: "},
    {"instText3","Wrong number of instructor availabilities periods per day at line: "},
    {"instText4","Wrong description of instructor availability at line: "},
    {"instText5"," in the instructor file:"},
    {"instText6","I was in analyseInstructor class and in analyseTokens method "},

    // Student constants
    {"studText1","Wrong student matricule at line: "},
    {"studText2","Wrong student name at line: "},
    {"studText3","Wrong student course choice at line: "},
    {"studText4","in the student file:"},
    {"studText5","I was in StudentList class and in analyseTokens method"},
    {"studText6","Wrong number of students in the students file:"},
    {"studText7","Wrong number of student courses choices at line: "},

    // Rooms constants
    {"roomText1","Wrong name of room at line: "},
    {"roomText2","Wrong capacity at line: "},
    {"roomText3","Wrong function at line: "},
    {"roomText4","Wrong caracteristics at line: "},
    {"roomText5"," in the room file:"},
    {"roomText6","I was in roomList class and in analyseTokens method"},
    {"roomText7","Wrong line format at line: "},

    // activities constants
    {"activText1","Wrong activity name at line: "},
    {"activText2","Wrong format of activity visibility at line: "},
    {"activText3","Wrong number of activities at line: "},
    {"activText4","Wrong teacher name at line: "},
    {"activText5","Wrong number of blocs at line: "},
    {"activText6","Wrong empty line at line: "},
    {"activText7","Wrong duration of blocs at line: "},
    {"activText8","Wrong days and times of blocs at line: "},
    {"activText9","Wrong format of fixed rooms at line: "},
    {"activText10","Wrong name of preferred rooms at line: "},
    {"activText11","Wrong type of rooms: "},
    {"activText12","Wrong format of pre-affected rooms at line: "},


    //Assign Dialogs
    {"toLeft","��"},
    {"toRight","��"},
    //ActivityDlg
    {"actList","Liste des activit�s"},
    {"show","Afficher"},
    {"notIncluded","Non inclue(s)"},
    {"included","Inclue(s)"},
    //InstructorAvailabiliyDlg and roomsAvailability
    {"dispo","Disponibilit�s"},
    {"roomsDlgTitle", "Disponibilite de locaux"},
    //GroupDlg
    {"ActStudNotAss","�tudiants non assign�s"},
    {"ActStudAss","�tudiants assign�s"},
    {"Activity","Activit�"},
    {"GroupDlgTitle","Affectation de sections"},
    {"Group","Groupe"},
    {"NumberOfElements","Nombre d'�l�ments"},
    {"Type","Type"},
    {"SortByMatricul","Par matricule"},
    {"SortByName","Par nom"},
    //EventsDialog
    {"EventsDlgTitle","�venements"},
    {"EventsFixed","Fig�s"},
    {"EventsPlaced","Plac�s"},
    {"EventsNotPlaced","Non plac�s"},

    //Optimisation dialogs
    {"InitialAffectMessage", "Affectation initiale effectu�e"},


    //ToolBar
    {"tBDay", "Jour"},
    {"tBPer","Period"},

    // Status Bar
    {"sbTotal", "Total "},
    {"sbTAct", "Act" },
    {"sbTInst", "Ens"},
    {"sbTRoom", "Loc"},
    {"sbTStu", "Etu"},
    {"sbTEvent", "�ve"},
    {"sbTAssig", "Pla"},

    {"sbConf", "Conflicts"},
    {"sbCInst", "C-Ens"},
    {"sbCRoom", "C-Loc"},
    {"sbCStu", "C-Etu"}

  };


  public Object[][] getContents() {
    return contents;
  }
}