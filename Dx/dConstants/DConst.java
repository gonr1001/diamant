/**
 *
 * Title: DConst $Revision: 1.11 $  $Date: 2003-05-07 10:56:28 $
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
 * @version $Revision: 1.11 $
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
  public final static String NEW = "Nouveau";

  public final static String N_TT  = res.getString("nTT");
  public final static String OPEN  = res.getString("open");
  public final static String CLOSE  = res.getString("close");
  //separator
  public final static String SAVE  = res.getString("save");
  public final static String SAVE_AS  = res.getString("saveAs");

  public final static String IMP_M  = res.getString("impM");
  public final static String DEF_F  = res.getString("defF");
  public final static String IMP_A  = res.getString("impA");
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

   final static String CONS = "Consommateur";
   final static String PROD = "Producteur";
   final static String IRC = "Inducteur R-C";
   final static String ICP = "Inducteur C-P";

   final static String DOIT = "Execute";
   final static String VERIFY = "Verification";
   final static String JDOIT1 = "Calcul 1";
   final static String JDOIT2 = "Calcul 2";

   final static String REPORT = "Rapport";
   final static String REPORTS = "Rapports";

   public final static String PREF = res.getString("pref");
   public final static String PLAF_M = res.getString("plafM");
   public final static String PLAF_TD = res.getString("plafTD");
   public final static String PLAF_D = res.getString("plafD");


   public final static String HELP = res.getString("help");

   final static String CONTENTS = "Contenu";
   final static String INDEX = "Index";
   final static String SEARCH = "Rechercher";

   public final static String ABOUT_M = res.getString("aboutM");
   public final static String ABOUT_TD = res.getString("aboutTD");
   public final static String ABOUT_D = res.getString("aboutD");

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

   public final static String MFONTDialog = "Dialog";
   public final static int NPT11 = 11;

  public DConst() {
  }
}