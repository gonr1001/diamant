/**
 *
 * Title: DStringFrRes $Revision: 1.4 $  $Date: 2003-02-06 18:19:17 $
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
 * @version $Revision: 1.4 $
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

    { "copy", "Copier"},

    { "help", "Aide"},
    { "aboutD", "rgr, \nCopyRight\n 2000 - 2003"},
    { "aboutM", "A propos de "},
    { "aboutTD", "A propos de "}

    };
  public Object[][] getContents() {
    return contents;
  }
}