/**
 *
 * Title: DStringFrRes $Revision: 1.10 $  $Date: 2003-05-07 10:35:55 $
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
 * @version $Revision: 1.10 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */

package dResources;

import java.util.*;

public class DStringFrRes extends java.util.ListResourceBundle {
  static final Object[][] contents = new String[][]{
	{ "appName", "Diamant " },
	{ "vDate", "31 jan 2003" },
	{ "untitled", "Sans titre" },
	{ "bye", "bye" },

    /**
    *  the menus and dialogs
    */

    { "file", "Fichier" },
    { "nTT", "Nouveau horaire" },
    { "open", "Ouvrir" },
    { "close", "Fermer" },
    //Separator
    { "save", "Enregistrer" },
    { "saveAs", "Enregistrer sous" },
    //Separator
    { "impM", "Fichier" },
    { "defF", "Nouveau horaire" },
    { "impA", "Ouvrir" },
    { "expo", "Fermer" },
    //Separator
    { "exit", "Quitter" },





    { "copy", "Copier"},

    { "assign", "Affectation" },
    { "instAssignM", "Enseignants" },
    { "instAssignTD", "Disponibilité enseignants"},
    { "instAssignD", "To be defined" },


    { "pref", "Preferences"},
    { "plafM", "Options L&F"},
    { "plafTD", "Options L&F"},
    { "plafD", "Choisisez un L&F"},


    { "help", "Aide"},
    { "aboutM", "A propos de "},
    { "aboutTD", "A propos de "},
    { "aboutD", "rgr, \nCopyRight\n 2000 - 2003"},

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

    };
  public Object[][] getContents() {
    return contents;
  }
}