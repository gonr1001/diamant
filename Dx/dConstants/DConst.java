/**
 *
 * Title: DConst $Revision: 1.4 $  $Date: 2003-02-20 11:05:31 $
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
 * @version $Revision: 1.4 $
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


  public final static String FILE = "Fichier";
  public final static String NEW = "Nouveau";
   final static String OPEN = "Ouvrir";
   final static String CLOSE = "Fermer";
   final static String SAVE = "Enregistrer";
   final static String SAVEAS = "Enregistrer sous";
   final static String IMPORT = "Importer";
   final static String EXPORT = "Exporter";
   final static String EXIT = "Quitter";

  public final static String EDIT = "Edition";
   final static String UNDO = "Annuler";
   final static String REDO = "Repeter";
   final static String CUT = "Couper";
  public final static String COPY = res.getString("copy");
   final static String PASTE = "Coller";
   final static String CLEAR = "Effacer";

   final static String GRAPHE = "Graphe";
   final static String RESO = "Ressource";
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

   public final static String PLAF_M = res.getString( "plafM");
   public final static String PLAF_TD = res.getString("plafTD");
   public final static String PLAF_D = res.getString("plafD");


   public final static String HELP = res.getString("help");

   final static String CONTENTS = "Contenu";
   final static String INDEX = "Index";
   final static String SEARCH = "Rechercher";

   public final static String ABOUT_M = res.getString("aboutM");
   public final static String ABOUT_TD = res.getString("aboutTD");
   public final static String ABOUT_D = res.getString("aboutD");

   public final static String MFONTDialog = "Dialog";
   public final static int NPT11 = 11;

  public DConst() {
  }
}