/**
 *
 * Title: DXToolsMethods 
 * Description: 
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
 * 
 */
package dInternal.dUtil;


import java.util.StringTokenizer;

public class DXToolsMethods {

	/**
	 * check if a String only contains a reference value and exit software
	 * otherwise
	 * 
	 * @param String
	 *            the string value to check
	 * @param String
	 *            the reference value to use
	 * @param String
	 *            the error message to print
	 * @param String
	 *            the classe name where error has been detect
	 */
	public final static String checkIfBelongsValues(String line,
			String refValues, String message, String classList) {
		StringTokenizer ref = new StringTokenizer(refValues);
		StringTokenizer lineValue = new StringTokenizer(line);
		String lvalue;
		String error = "";
		int count = 0, refSize = ref.countTokens();
		while (lineValue.hasMoreElements()) {
			lvalue = lineValue.nextToken();
			while (ref.hasMoreElements()) {
				if (!lvalue.equals(ref.nextToken()))
					count++;
				if (count == refSize) {
					error = message + " in the activity file:" + "\n"
							+ "I was in " + classList
							+ " class and in analyseTokens method ";
				}// end if(count==refSize)
			}// end while(ref.hasMoreElements())
		}// end while(lineValue.hasMoreElements())
		return error;
	}

	/**
	 * check if a String is not empty and exit software otherwise
	 * 
	 * @param String
	 *            the string value to check
	 * @param String
	 *            the error message to print
	 * @param String
	 *            the classe name where error has been detect
	 */
	public final static String checkIfLineIsEmpty(String line, String message,
			String classList) {
		String error = "";
		if (line.length() == 0) {
			error = message + " in the activity file:" + "\n" + "I was in "
					+ classList + " class and in analyseTokens method ";
		}
		return error;
	}

	/**
	 * check if a String is an int value and exit software otherwise
	 * 
	 * @param String
	 *            the string value to check
	 * @param String
	 *            the error message to print
	 * @param String
	 *            the classe name where error has been detect
	 */
	public final static String isIntValue(String value, String message,
			String classList) {
		String error = "";
		try {
			Integer.parseInt(value.trim());
		} catch (NumberFormatException exc) {
			error = message + " in the activity file:" + "\n" + "I was in "
					+ classList + " class and in analyseTokens method ";
		}
		return error;
	}

	/**
	 * check if a String is an int value and exit software otherwise
	 * 
	 * @param String
	 *            the string value to check
	 */
	public final static boolean isIntValue(String value) {
		// String error="";
		try {
			Integer.parseInt(value.trim());
		} catch (NumberFormatException exc) {
			return false;
		}
		return true;
	}

//	/**
//	 * compare two hour tables of int. tab[0] is the hour and tab[1] is the
//	 * minute
//	 * 
//	 * @param int[2]
//	 *            tab1 the first table of integer
//	 * @param int[2]
//	 *            tab2 the second table of integer
//	 * @return int "1" if tab1 is greater than tab2, "0" if tab1 and tab2 are
//	 *         equals, "-1" if tab1 is smaller than tab2
//	 */
//	public final static int compareTabsHour(int[] tab1, int[] tab2) {
//		if ((tab1.length == 2) && (tab2.length == 2)) {
//			if (tab1[0] >= tab2[0]) {
//				if (tab1[0] > tab2[0]) {
//					return 1;
//				}// else{// else if(tab1[0]>tab2[0])
//				if (tab1[1] > tab2[1]) {
//					return 1;
//				}// end if(tab1[1]>tab2[1])
//				if (tab1[1] == tab2[1]) {
//					return 0;
//				}// end if(tab1[1]==tab2[1])
//				if (tab1[1] < tab2[1]) {
//					return -1;
//				}// end if(tab1[1]<tab2[1])
//				// }// end else if(tab1[0]>tab2[0])
//			}// end if(tab1[0]>tab2[0])
//		}// end if((tab1.length==2) && (tab2.length==2))
//		return -1;
//	}

	/**
	 * Convert STI Time and periods
	 * 
	 * @param int
	 *            the reference period
	 * @return int[2] the first element of the table is hour and the second
	 *         element is minute
	 */
	public final static int[] convertSTIPeriods(int period) {
		int[] time = { 0, 30 };
		time[0] = 7 + period;
		return time;
	}



	/**
	 * return a token from a stringtokenizer
	 * 
	 * @param str
	 * @param delimiter
	 * @param position
	 * @return theToken or an empty String if position >= nbTokens
	 */
	public final static String getToken(String str, String delimiter,
			int position) {
		StringTokenizer strToken = new StringTokenizer(str, delimiter);
		int nbTokens = strToken.countTokens();
		for (int i = 0; i < nbTokens; i++) {
			if (i == position)
				return strToken.nextToken();
			strToken.nextToken();
		}
		return "";
	}

	/**
	 * remove a token in a stringtokenizer
	 * 
	 * @param str
	 * @param delimiter
	 * @param position
	 * @return
	 */
	public final static String removeToken(String str, String delimiter,
			int position) {
		StringTokenizer strToken = new StringTokenizer(str, delimiter);
		String res = "";
		int inc = 0;
		while (strToken.hasMoreElements()) {
			if (inc != position)
				res += strToken.nextToken() + delimiter;
			else
				strToken.nextToken();
			inc++;
		}
		return res;
	}

	/**
	 * count the number of tokens
	 * 
	 * @return
	 */
	public final static int countTokens(String str, String delimiter) {
		StringTokenizer strToken = new StringTokenizer(str, delimiter);
		return strToken.countTokens();

	}




	public static String getToken4Activitiy(String activityName,
			String delimiter, int position) {
		StringTokenizer strToken = new StringTokenizer(activityName, delimiter);
		int nbTokens = strToken.countTokens();
		for (int i = 0; i < nbTokens; i++) {
			if (i == position)
				return strToken.nextToken();
			strToken.nextToken();
		}
		return "";
	}

}