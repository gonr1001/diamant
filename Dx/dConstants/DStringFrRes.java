/**
 *
 * Title: DStringFrRes $Revision: 1.21 $  $Date: 2003-05-22 14:18:41 $
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
 * @version $Revision: 1.21 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */

package dResources;

import java.util.*;

public class DStringFrRes extends java.util.ListResourceBundle {
  static final Object[][] contents = new String[][]{
	{ "appName", "Diamant 1.5 " },
	{ "vDate", "13 mai 2003" },
	{ "untitled", "Sans titre" },
	{ "bye", "bye" },

    /**
    *  the menus and dialogs
    */
    { "newTT", "Nouvel horaire" },
    { "file", "Fichier" },
   // { "nTT", "Nouvel horaire" },
    { "open", "Ouvrir horaire" },
    { "close", "Fermer" },
    //Separator
    { "save", "Enregistrer" },
    { "saveAs", "Enregistrer sous" },
    //Separator


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

    { "assign", "Affectation" },
    { "instAssignM", "Enseignants" },
    { "instAssignTD", "Disponibilit� enseignants"},
    { "instAssignD", "To be defined" },


    { "pref", "Preferences"},
    { "plafM", "Options L&F"},
    { "plafTD", "Options L&F"},
    { "plafD", "Choisisez un L&F"},


    { "help", "Aide"},
    { "aboutM", "A propos de "},
    { "aboutTD", "A propos de "},
    { "aboutD",  " \nCopyRight\n 2000 - 2003"},

    // common buttons
    {"butOK", "Ok"},
    {"butCancel", "Annuler"},
    {"butApply", "Appliquer"},
    {"butBrowse", "Parcourir"},
    // commontitles
    {"noName", "Sans Titre"},
    {"dim", "dim"},
    {"dotDim", ".dim"},
    {"sig", "sig"},
    {"txt", "txt"},
    {"sigFile", "Fichier donn�es (*.sig)"},
    {"dimFile", "Fichier Diamant (*.dim)"},
    {"problem", "� corriger"},



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

    };
  public Object[][] getContents() {
    return contents;
  }
}