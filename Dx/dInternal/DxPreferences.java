/**
 * Created on Jun 15, 2006
 * 
 * 
 * Title: DxPreferences.java 
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
 * 
 */
package dInternal;

import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInterface.DApplication;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.exception.IOFileException;
import eLib.exit.txt.ByteOutputFile;
import eLib.exit.txt.FilterFile;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxPreferences is a class used to:
 * <p>
 * Keep user preferences
 * <p> 
 * 
 */
public class DxPreferences {

		public String _lookAndFeel;

		public String _language;

		public String _standardTTC;

		public String _standardTTE;

		public String _defaultDir;

		private String _originalFullFileName;

		public String _acceptedChars;

		public String _selectedOptionsInFullReport;

		public String _selectedOptionsInConflictReport;

		public String _conflictLimits;

		private DApplication _dApplic;

		public DxPreferences() {
			// to avoid warning
		}

		public DxPreferences(String str) {// throws InputFileException{

			// _dApplic = dApplic;
			try {
				FilterFile filter = new FilterFile();
				if (filter.adjustingFile(str)) {
					StringTokenizer st = new StringTokenizer(new String(filter
							.getByteArray()), DConst.CR_LF);
					// System.out.println(st.toString());
					_lookAndFeel = st.nextToken();
					_language = st.nextToken();
					_standardTTC = st.nextToken();
					_standardTTE = st.nextToken();
					_defaultDir = st.nextToken();
					_originalFullFileName = st.nextToken();
					_acceptedChars = st.nextToken();
					_selectedOptionsInFullReport = st.nextToken();
					_selectedOptionsInConflictReport = st.nextToken();
					_conflictLimits = st.nextToken();
				}
			} catch (Exception e) {
				System.out.println("Preferences:" + e);
			}
		}

		public void setLAFName(String lnfName) {
			_lookAndFeel = lnfName;
		}

		public void save() {
			writeFile(_defaultDir, toString());
		}

		public String toString() {
			String str = _lookAndFeel;
			str += DConst.CR_LF;
			str += _language;
			str += DConst.CR_LF;
			str += _standardTTC;
			str += DConst.CR_LF;
			str += _standardTTE;
			str += DConst.CR_LF;
			str += _defaultDir;
			str += DConst.CR_LF;
			str += _originalFullFileName;
			str += DConst.CR_LF;
			str += _acceptedChars;
			str += DConst.CR_LF;
			str += _selectedOptionsInFullReport;
			str += DConst.CR_LF;
			str += _selectedOptionsInConflictReport;
			str += DConst.CR_LF;
			str += _conflictLimits;
			return str;
		}

		private void writeFile(String name, String data) {
			try {
				ByteOutputFile bof = new ByteOutputFile(name);
				bof.writeFileFromBytes(data.getBytes());
				bof.close();
			} catch (IOFileException iofe) {
				new FatalProblemDlg(_dApplic.getJFrame(), iofe
						+ "\n I was in Preferences.writeFile");
				System.out.println(iofe);
				iofe.printStackTrace();
				System.exit(31);
			} // end catch
		}// end writeFile

		public Vector getSelectedOptionsInFullReport() {
			StringTokenizer st = new StringTokenizer(_selectedOptionsInFullReport,
					";");
			String s = "selectedOptionsInFullReport";
			Vector<String> res = new Vector<String>();
			// st.nextToken();
			if (st.nextToken().equals(s)) {
				while (st.countTokens() > 0) {
					res.add(st.nextToken());
				}
			} else {
				System.out
						.println("Preferences.getSelectedOptionsFullReport  Help");
			}
			return res;
		} // end getSelectedOptionsInFullReport()

		public Vector getSelectedOptionsInConflictReport() {
			StringTokenizer st = new StringTokenizer(
					_selectedOptionsInConflictReport, ";");
			String s = "selectedOptionsInConflictReport";
			Vector<String> res = new Vector<String>();
			// st.nextToken();
			if (st.nextToken().equals(s)) {
				while (st.countTokens() > 0) {
					res.add(st.nextToken());
				}
			} else {
				System.out
						.println("Preferences.getSelectedOptionsInConflictReport  Help");
			}
			return res;
		} // end getSelectedOptionsInConflictReport()

		public int[] getConflictLimits() {
			StringTokenizer st = new StringTokenizer(_conflictLimits, ";");
			String s = "conflictLimits";
			Vector<String> res = new Vector<String>();
			// st.nextToken();
			if (st.nextToken().equals(s)) {
				while (st.countTokens() > 0) {
					res.add(st.nextToken());
				}
			} else {
				System.out.println("Preferences.getConflictLimits  Help");
			}
			int[] a = new int[res.size()];
			for (int i = 0; i < a.length; i++) {
				a[i] = Integer.parseInt(res.get(i));
			}

			return a;
		} // end getSelectedOptionsInFullReport()

		public void setSelectedOptionsInFullReport(Vector v) {
			_selectedOptionsInFullReport = "selectedOptionsInFullReport" + ";";
			for (int i = 0; i < v.size(); i++) {
				_selectedOptionsInFullReport += (String) v.elementAt(i) + ";";
			}
		} // end setSelectedOptionsInFullReport

		public void setSelectedOptionsInConflictReport(Vector v) {
			_selectedOptionsInConflictReport = "selectedOptionsInConflictReport"
					+ ";";
			for (int i = 0; i < v.size(); i++) {
				_selectedOptionsInConflictReport += (String) v.elementAt(i) + ";";
			}
		} // end setSelectedOptionsInConflictReport

		public void setConflicLimits(Vector v) {
			_conflictLimits = "conflictLimits" + ";";
			for (int i = 0; i < v.size(); i++) {
				_conflictLimits += (String) v.elementAt(i) + ";";
			}
		} // end setSelectedOptionsInFullReport

		public void setConflicLimitsString(String str) {
			_conflictLimits = str;

		}

	} /* end DxPreferences */