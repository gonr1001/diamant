/**
 *
 * Title: DStringFrRes $Revision: 1.1.1.1 $  $Date: 2003-01-23 17:51:40 $
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
 * @version $Revision: 1.1.1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */

package dResources;

import java.util.*;

public class DStringFrRes extends java.util.ListResourceBundle {
  static final Object[][] contents = new String[][]{
	{ "appName", "Diamant " },
	{ "vDate", "16 jan 2002" },
	{ "hello", "hello" },
	{ "bye", "bye" },
    { "copy", "Copier"} };
  public Object[][] getContents() {
    return contents;
  }
}