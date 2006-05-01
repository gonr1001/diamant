/**
 * Created on Apr 19, 2005
 * 
 * 
 * Title: GenTeams.java 
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

package dmains;

import java.awt.Dimension;

import javax.swing.JFileChooser;

import dConstants.DConst;
import eLib.exit.txt.ByteInputFile;
import java.io.FileOutputStream;

// System.out.println("char: "+(char)b[i] +
// " int: "+ b[i] +
//" byte: " + (byte)b[i] +
//" short: "+(short)b[i] +
//" HEX:" + Integer.toHexString(b[i]).toUpperCase());
public class ScanFile {
	String _error;

	String _fileName;

	public ScanFile() {
		_error = "";
	}

	public boolean fileNameExists(String[] args) {
		if (args.length == 0)
			return obtainFileNamefromUser();
		_fileName = args[0];
		return true; // D:\Developpements\DiamantExtreme\Dx\data\fgen\75\choixet.sig.CHOIXET
	}

	public void doIt(String iFileName, String oFileName) {
		byte[] b;
		StringBuffer out = new StringBuffer();
		StringBuffer str1 = new StringBuffer();
		StringBuffer str2 = new StringBuffer();
		StringBuffer str3 = new StringBuffer("");
		try {
			ByteInputFile inputFile = new ByteInputFile(iFileName);
			FileOutputStream outputFile = new FileOutputStream(oFileName);
			b = inputFile.readFileAsBytes();
			inputFile.close();
			int blankPad = (16 - b.length % 16) * 3;

			for (int m = 0; m < blankPad; m++) {
				str3.append(" ");
			}
			int i = 0;
			while (i < b.length) {

				if (i + 16 < b.length) {
					for (int j = 0; j < 16; j++) {
						str1.append(appendToHex(b[i + j]));
						str2.append(appendToChar(b[i + j]));
					}
					out.append(str1 + "   " + str2 + DConst.CR_LF);
					i += 16;

					str1 = new StringBuffer();
					str2 = new StringBuffer();

				} else {
					for (; i < b.length; i++) {
						str1.append(appendToHex(b[i]));
						str2.append(appendToChar(b[i]));
					}
					out.append(str1.append(str3) + "   " + str2 + DConst.CR_LF);

					str1 = new StringBuffer();
					str2 = new StringBuffer();
				}
			}//end while
			outputFile.write(out.toString().getBytes());

			outputFile.close();
		} catch (Exception e) {
			_error = e.toString();
		}
	}

	public String getError() {
		return _error;
	}

	public String getFileName() {
		return _fileName;
	}

	public StringBuffer appendToHex(byte b) {
		if (b > 16) {
			return new StringBuffer(Integer.toHexString(b).toUpperCase() + " ");
		}
		return new StringBuffer("0" + Integer.toHexString(b).toUpperCase()
				+ " ");
	} // appendToHex

	public StringBuffer appendToChar(byte b) {
		if (b < 126 && b > 31)
			return new StringBuffer("" + (char) b);

		if (b == 13 || b == 10)
			return new StringBuffer(".");

		return new StringBuffer("" + b + " ");

	} // appendToHex	

	private boolean obtainFileNamefromUser() {
		JFileChooser fc = new JFileChooser(".");

		// Display the file chooser in a dialog
		Dimension d = fc.getPreferredSize();
		fc.setPreferredSize(new Dimension((int) d.getWidth() + 100, (int) d
				.getHeight()));
		int returnVal = fc.showOpenDialog(null);

		// If the file chooser exited sucessfully,
		// and a file was selected, continue
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// get the file name
			_fileName = fc.getSelectedFile().getAbsolutePath();
			return true;
		}
		//else annulate or close gave 
		//Error in main : eLib.exit.exception.IOFileException: The file:  was not found
		return false;

	}// end getFileNamefromUser

	public static void main(String[] args) {
		System.out.println("hi!");

		ScanFile scan = new ScanFile();
		if (scan.fileNameExists(args)) {
			String inputFileName = scan.getFileName();
			String outFileName = inputFileName + "out" + ".txt";
			scan.doIt(inputFileName, outFileName);
			if (scan.getError() != "")
				System.out.println("Error in main : " + scan.getError());
		}
		System.out.println("bye");
		System.exit(1);
	} //end main

}