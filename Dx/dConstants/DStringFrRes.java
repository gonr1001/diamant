
/**
 *
 * Title: DStringFrRes $Revision: 1.130 $  $Date: 2005-01-25 15:51:19 $
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
 * @version $Revision: 1.130 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

package dConstants;



public class DStringFrRes extends java.util.ListResourceBundle {
  static final Object[][] contents = new String[][]{
	{ "appName", "Diamant 1.6 " },
	{ "fileVerName1_5", "Diamant1.5" },
	{ "fileVerName1_6", "Diamant1.6" },
	{ "fileVerNameXML1_7", "Diamant1.7" },
	{ "vDate", "30 sep 2004" },
	{ "bye", "bye" },
    /**
    *  the menus and dialogs
    */
    { "file", "Fichier" },

    { "newTT", "Nouvel horaire" },
    { "newTTTD", "Indiquez grille horaire"},
    { "newTTM", "Grille horaire"},

    { "nTTCy", "Horaire cycle ..." },
    { "nTTCyTD", "Grille horaire cycle"},
    { "nTTCyM", "Cycle"},

    { "nTTEx", "Horaire examen ..." },
    { "nTTExTD", "Grille horaire examen"},
    { "nTTExM", "Examen"},

    { "open", "Ouvrir horaire ..." },
    { "oTTTD", "Horaire" },

    { "openTTS", "Ouvrir grille ..." },
    { "oTTSTD", "Grille" },

    { "close", "Fermer" },
    //Separator
    { "save", "Enregistrer ..." },
    { "saveAs", "Enregistrer sous ..." },
    { "savePrompt", "Voulez vous enregistrer?" },


    { "defFM", "Definir fichiers à importer ..." },
    { "defFTD", "Definir fichiers pour l'importation automatique"},
    { "defFD1", "Les cours" },
    { "defFD2", "Les étudiants" },
    { "defFD3", "Les enseignants" },
    { "defFD4", "Les locaux" },
    { "defFD5", "Au moins une ligne est vide. \n"},
    { "defFD6", "Veuillez entrer un fichier pour chaque type demandé." },
    { "defFD7", "Sauvegardés dans : " },
    { "defFD8", "Fichiers d'importation" },


    { "impAM", "Importer automatiquement ..." },
    { "impATD", "Importation de fichiers"},
    { "impSelect", "Importation selective" },//
    { "impSelectRoom", "Locaux" },
    { "impSelectInst", "Enseignants" },
    { "impSelectAct", "Activités" },
    { "impSelectStud", "Étudiants" },//
    { "impAD", "To be defined" },
    { "impASuc","Fichiers importés avec succes!!!"},
    {"exists", "existe"},
    {"replace", "remplacer?"},
    { "expo", "Exporter" },
    //Separator
    { "exit", "Quitter" },


    { "copy", "Copier"},



    { "fileTTS", "Fichier_Grille" },
    { "newTTS", "Nouvelle grille"},
    /*{ "newTTTD", "Indiquez grille horaire"},
    { "newTTM", "Grille horaire"},*/


    { "nTTSCy", "Grille cycle" },


    { "nTTSEx", "Grille examen" },




    { "closeTTS", "Fermer grille" },
    { "saveTTS", "Enregistrer grille" },
    { "saveAsTTS", "Enregistrer grille sous" },

    //Assign
    { "assign", "Affectation" },
    { "instAssignM", "Enseignants ..." },
	{ "listsInstructorTD", "Listes d'enseignants"},
    { "instAssignTD", "Disponibilité enseignants"},

    {"ActiAssignM", "Activités ..."},
    {"GroupAssignM", "Groupes ..."},
    {"LocauxAssignM", "Locaux ..."},
    { "instAssignD", "To be defined"},
    {"EventsAssignM", "Événements ..."},
    {"ManualAssignM","Affectation manuelle ..."},
    {"DefineSetM","Définir ensemble"},
    {"PartialTTStructureM","Grille Partielle"},

    //Optimization
    { "OptimizationM", "Optimisation"},
    { "initialAssignM", "Affectation Initiale"},
    { "firstAlgo", "Construire l'horaire"},
    { "studentRepartition", "Formation de groupes"},
    { "studentMixingBal", "Balancé"},
    { "studentMixingMidBal", "Intermédiaire"},
    { "studentMixingOpti", "Optimisé"},
    { "studentMixingPerso", "Personalisée"},


    //Modification
    {"ModificationM","Modification"},
    {"EventsModifM","Modification évenements"},
    {"ActModifM","Activité ..."},

    //Report
    {"ReportM","Rapports"},
    {"Reports","Rapports ..."},



    //Preferences
    { "pref", "Preferences"},
    { "plafM", "Options L&F"},
    { "plafTD", "Options L&F"},
    { "plafD", "Choisisez un L&F"},
    { "conflicts", "Options Conflits ..."},
    { "displayTT", "Affichage grille"},
    { "simple", "Simple"},
    { "splitVertical", "Detaillé  + Split V"},
    { "splitHorizontal", "Detaillé + Split H"},

    //Help
    { "help", "Aide"},
    { "aboutM", "A propos de ..."},
    { "aboutTD", "A propos de "},
    { "aboutD",  " \n \u00A9 Copyright  \n" +
    			"All rigths reserved \n" +
    			"Tous droits réservés \n" +
    			"rgr \n 2000 - 2004"},

    //User
    { "userTest", "Beta Test"},
    { "user1", "Admin Formation Groupes"},
    { "user2", "Feph Importation Selective"},

    // common buttons
    {"butOK", "Ok"},
    {"butCancel", "Annuler"},
    {"butApply", "Appliquer"},
    {"butValidate","Valider"},
    {"butOptions", "Options"},
    {"butBrowse", "Parcourir"},
    {"butPlace", "Placer"},
    {"butFige", "Figer"},
    {"butAdd", "Ajouter"},
    {"butRemove", "Supprimer"},
    {"butChange", "Modifier"},
    {"butSave","Enregistrer"},
    {"butSaveAs","Enregistrer sous..."},

    // common titles
    {"noName", "Sans titre"},
    {"dia", "dia"},
    {"dim", "dim"},
    {"xml", "xml"},
    {"txt","txt"},
    {"dotDia", ".dia"},
    {"dotDim", ".dim"},
    {"dotXml", ".xml"},
    {"dotTxt",".txt"},
    {"sig", "sig"},
    {"txt", "txt"},
    {"dgh", "dgh"},
    {"sigFile", "Fichier données (*.sig)"},
    {"dimFile", "Fichier Diamant (*.dim)"},
    {"diaFile", "Fichier Diamant (*.dia)"},
    {"dghFile", "Fichier Grille Horaire (*.dgh)"},
    {"xmlFile", "Fichier Grille Horaire (*.xml)"},
    {"txtFile", "Fichier Rapport (*.txt)"},
    {"noRoomInternal", "------"},
    {"noRoomExternal", "xxxxxx"},
    {"noRoomExport", "      "},
    {"problem", "À corriger"},
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
    {"studText8","Wrong number of elements at line: "},
    {"studText9","Wrong site: "},

    // Rooms constants
    {"roomText1","Wrong name of room at line: "},
    {"roomText2","Wrong capacity at line: "},
    {"roomText3","Wrong function at line: "},
    {"roomText4","Wrong caracteristics at line: "},
    {"roomText5"," in the room file:"},
    {"roomText6","I was in roomList class and in analyseTokens method"},
    {"roomText7","Wrong line format at line: "},
    {"roomText8","Wrong name of site at line: "},
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
    {"activText12","Wrong format of pre-assigned activity at line: "},
    {"activText13", "Wrong number of instructors at line: "},
    {"activText14","Wrong group name at line: "},
    {"activText15","Wrong site name at line: "},
    {"activText16","Wrong capacity format at line: "},


    //Assign Dialogs
    {"toLeft","««"},
    {"toRight","»»"},
    {"toUp","+"},
    {"toDown","-"},
    //ActivityDlg
    {"actList","Listes des activités"},
    {"show","Afficher"},
    {"notIncluded","Non inclue(s)"},
    {"included","Inclue(s)"},
    {"tAffecDlg","Affectation d'événement(s)"},
    //InstructorAvailabiliyDlg and roomsAvailability
    {"dispo","Disponibilités"},
    {"roomsDlgTitle", "Disponibilite de locaux"},
    //GroupDlg
    {"ActStudNotAss","Étudiants non assignés"},
    {"ActStudAss","Étudiants assignés"},
    {"Activity","Activité"},
    {"SectionDlgTitle","Affectation de groupes"},
    {"Section","Groupe"},
    {"NumberOfElements","Nombre d'éléments"},
    {"Type","Nature"},
    {"SortByMatricul","Par matricule"},
    {"SortByName","Par nom"},
    {"SortByProgram", "Par programme"},

    //EventsDialog
    {"EventsDlgTitle","Événement"},
    {"EventsFixed","Figés"},
    {"EventsPlaced","Placés"},
    {"EventsNotPlaced","Non placés"},
    //
    {"ManImpDlgTitle","Affectation manuelle"},



    {"numberOfTypes","Nombre de natures"},
    {"numberOfSections","Nombre de groupes"},
    {"numberOfUnities","Nombre de blocs"},

    //Export dialogs

    {"ExportMessage", "Exportation"},
    {"Exported", "Exportation effectuée"},

    //Optimisation dialogs
    {"InitialAssignMessage", "Affectation initiale effectuée"},
    {"TTBuildMessage", "Construction d'horaires terminée"},
    {"StudentsMixingMessage", "Brassage d'étudiants terminé"},

    //Report dialogs
    {"ReportDlgTitle","Rapports"},
    {"ReportDlgTab1","Complet"},
    {"ReportDlgTab2","Conflits"},
    {"ReportDlgTab3","Importation"},
    {"ReportDlgTabMess","Veuillez faire click sur le button Options pour configurer le rapport"},
    {"ReportOptionsDlgTitle","Options de rapport"},
    {"ReportOpFieldsNotChoiced","Champs non choisis"},
    {"ReportOpFieldsChoiced","Champs choisis"},
    {"Report","Rapport"},
    {"ReportProducedAt","Produit en"},

    {"RActivityName","Acti"},
    {"RTypeName","Nat"},
    {"RSectionName","Grp"},
    {"RUnityName","Blc"},
    {"RDuration","Durée"},
    {"RDayNumber","JNum"},
    {"RDayName","Jour"},
    {"RActivityBeginHour","Début"},
    {"RActivityEndHour","Fin"},
    {"RInstructorName","Enseignant"},
    {"RInstructorNameAvail","Ens. Dispo"},
    {"RRoomName","Local"},
    {"RAssign", "Affectation"},
    {"RStudentName","Étudiant"},
    {"RStudentSizeName","Nomb É"},

    {"RSequenceID","PérJour"},
    {"RPeriodBegin", "Début"},
    {"REvent1_ID","Événement 1"},
    {"REvent2_ID","Événement 2"},
    {"RNumberOfConflicts","Numb C"},
    {"RTypeOfConflict","Type Conf"},
    {"RStudentConflict", "C-Étudiant"},
    {"RRoomConflict","C. C-Local"},
    {"RInstructorConflict","C-Instructor"},
    {"RElementsInConflict", "Conflits"},
    {"",""},
    {"",""},
    {"",""},
    {"",""},



    //ToolBar
    {"tBDay", "Jour"},
    {"tBPer","Période"},

    // Status Bar
    {"sbTotal", "Total "},
    {"sbTAct", "Act" },
    {"sbTInst", "Ens"},
    {"sbTRoom", "Loc"},
    {"sbTStu", "Etu"},
    {"sbTEvent", "Éve"},
    {"sbTAssig", "Pla"},

    {"sbConf", "Conflits"},
    {"sbCInst", "C-Ens"},
    {"sbCRoom", "C-Loc"},
    {"sbCStud", "C-Etu"},

    {"ttFile","S814.HORAIRE"},
    {"ttStudFIle", "S813.ASSGRO"},
    {"errorXMLFile", "XML file is corrupted"},

	{"nameAC", "nameAC"},
	{"categoryAC", "categoryAC"},

	{"nameLabel", "Nom"},
	{"categoryLabel", "Catégorie"},


	{"separator",     "------------------------------------------------------------------" },
	{"instructorSep", "------------------------  Enseignants  ---------------------------" },
	{"roomSep",       "------------------------  Locaux       ---------------------------" },
	{"studentSep",    "------------------------  Etudiants    ---------------------------" },

	
	{"deletedElement", "élément supprimé : " },
	{"addedElement", "élément ajouté : "},
	{"changedElement", "élément modifié : "},
	{"unchangedElement", "élément non modifié : "},
	{"hourSeparator", "h"},
	{"saveSeparator", "================================="},
	{"errorXML", "XML file is corrupted"}
  };


  public Object[][] getContents() {
    return contents;
  }
}