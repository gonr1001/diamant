/**
 * Created on 4-Dec-07
 * 
 * 
 * Title: SemiExtendedAsciiFile.java
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

package eLib.exit.txt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Properties;

import dExceptions.DiaException;


/**
 * Description: SemiExtendedAsciiFile is a class used to read a file all ASCII char in
 * range 0 to 127 are accepted. It is possible to add char from 128 to 255 by
 * putting them in a string then calling the constructor or call setCharKnown
 * after construction.
 * 
 * <p>
 * All Exceptions are catch then a IOFileExeption is throw, then when using
 * this ByteInputFile only one Exception must be catch.
 * 
 */

public class SemiExtendedAsciiFile {
	
	private static final String CHAR_PATH = "data/chars.txt";

	private ByteInputFile _bif;

	private ByteOutputFile _bof;

	private byte[] _bytesArray;

	private String _charKnown;

	/**
	 * 
	 * Requires: nothing.
	 * 
	 * <p>
	 * Modifies: nothing.
	 * 
	 * <p>
	 * Effect: the internal fields are initialized. Recognized chars are 0 to
	 * 127 ASCII.
	 * 
	 */
	public SemiExtendedAsciiFile()throws NullPointerException,
	FileNotFoundException,IOException  {
		_bytesArray = null;
		String charKnown = createCharKnown(CHAR_PATH);
		if (charKnown != null)
			_charKnown = charKnown;
		else
			_charKnown = "";
	} // end FilterFile




	/**
	 * 
	 * Requires: an array of bytes b and a String where the charKnown are
	 * defined
	 * 
	 * <p>
	 * Modifies: the set of char that can be recognized
	 * 
	 * <p>
	 * Effect: the internal fields are initialized. Recognized chars are 0 to
	 * 127 ASCII + those contained in the String
	 * 
	 * @param b
	 *            an array of bytes.
	 * @param charKnown
	 *            a String containing the char that can be recognized.
	 * 
	 */
	public SemiExtendedAsciiFile(byte[] b, String charKnown) {
		_bytesArray = b;
		if (charKnown != null)
			_charKnown = charKnown;
		else
			_charKnown = "";
	} // end FilterFile

	/**
	 * 
	 * Requires: nothing.
	 * 
	 * <p>
	 * Modifies: nothing.
	 * 
	 * <p>
	 * Effect: nothing.
	 * 
	 * @return a String containing the recognized char
	 * 
	 * 
	 */
	public String getCharKnown() {
		return _charKnown;
	}

	/**
	 * 
	 * Requires: a String containing the char(s) to be recognized
	 * 
	 * <p>
	 * Modifies: nothing.
	 * 
	 * <p>
	 * Effect: nothing.
	 * 
	 * @param a
	 *            String containing the recognized char
	 * 
	 * 
	 */
	public void setCharKnown(String str) {
		_charKnown = str;
	}

	/**
	 * 
	 * Requires: a String containing the char to be recognized
	 * 
	 * <p>
	 * Modifies: nothing.
	 * 
	 * <p>
	 * Effect: nothing.
	 * 
	 * @param a
	 *            String containing the recognized char
	 * 
	 * 
	 */
//	public void oldAppendToCharKnown(String str) {
//		byte[] b = new byte[1];
//		for (int i = 0; i < str.length(); i++) {
//			b[0] = (byte) str.charAt(i);
//			if (!isIn(_charKnown, b))
//				_charKnown += new String(b);
//		}
//	}
	
	/**
	 * 
	 * Requires: a String containing the char to be recognized
	 * 
	 * <p>
	 * Modifies: nothing.
	 * 
	 * <p>
	 * Effect: nothing.
	 * 
	 * @param a
	 *            String containing the recognized char
	 * 
	 * 
	 */
	public void appendToCharKnown(String str) {
		byte b = 0;
		for (int i = 0; i < str.length(); i++) {
			b = (byte) str.charAt(i);
			if (-1 ==_charKnown.indexOf((char)b))
				_charKnown += (char)b;
		}
	}

	public boolean validFile(String str) throws NullPointerException,
	FileNotFoundException,IOException, DiaException  {
		readFile(str);
		if (testValidityOfBytes()) {
			adjustingLines();
			adjustingEndFile();
			return true;
		}
		return false;
//		}
//		throw new DiaException(DConst.INVALID_FILE_FILTER);
	} // end validFile

	public boolean adjustingFile(String str) throws NullPointerException,
	FileNotFoundException,IOException {
		readFile(str);
		adjustingLines();
		adjustingEndFile();
		return true;
	} // end validFile

	public byte[] getByteArray() {
		return _bytesArray;
	}

	public void readFile(String str) throws NullPointerException,
	FileNotFoundException,IOException {
//		try {
			_bif = new ByteInputFile(fixSeparator(str));
			_bytesArray = _bif.readFileAsBytes();
			_bif.close();
//		} catch (Exception iofe) {
//			new DxExceptionDlg(iofe.getMessage(),iofe);
//		} // end catch
	}// readFile(String str)

	// ------------------------------------------------------
	// ordre de succession de fin de ligne sous windows: \r\n = cr + lf = 13 +
	// 10
	public void adjustingLines() {
		final String crlfStr = "\r\n";
		ArrayList<Byte> byteArrayList = new ArrayList<Byte>();

		for (int i = 0; i < _bytesArray.length; i++) {
			if ((_bytesArray[i] != (byte) crlfStr.charAt(0))
					&& (_bytesArray[i] != (byte) crlfStr.charAt(1))) {
				byteArrayList.add(new Byte(_bytesArray[i]));
			}
			// } else {// end if ((_b[i] != (byte)crlfStr.charAt(0)
			if (_bytesArray[i] == (byte) crlfStr.charAt(0)) {
				if (i == (_bytesArray.length - 1)) { // end of file?
					addCrLf(byteArrayList);
				} else {
					if (_bytesArray[i + 1] != (byte) crlfStr.charAt(1)) {
						addCrLf(byteArrayList);
					}
				}
			} else { // (_b[i]==(byte)crlfStr.charAt(1)){
				if (_bytesArray[i] == (byte) crlfStr.charAt(1)) { // adjusting LF
					addCrLf(byteArrayList);
				}
			}

			// }

		}// end for (int i=0; i< _b.length; i++)
		toArrayOfBytes(byteArrayList);

	}// end public void adjustingLines()

	private void toArrayOfBytes(ArrayList<Byte> byteArrayList) {
		_bytesArray = new byte[byteArrayList.size()];
		for (int i = 0; i < byteArrayList.size(); i++)
			_bytesArray[i] = byteArrayList.get(i).byteValue();
	}

	private void addCrLf(ArrayList<Byte> byteArrayList) {
		byteArrayList.add(new Byte((byte) 13));
		byteArrayList.add(new Byte((byte) 10));
	}

	// ------------------------------------------------------
	public void adjustingEndFile() {
		// Vector byteVector = new Vector();
		int i;
		for (i = (_bytesArray.length - 1); i >= 0; i -= 2) {
			if ((_bytesArray[i] != (byte) 10) && (_bytesArray[i] != (byte) 13)) {
				break;
			}
		}// end for (int i=_b.length-1; i>=0; i--)
		byte[] bTemp = new byte[i + 1];
		for (int j = 0; j < bTemp.length; j++)
			bTemp[j] = _bytesArray[j];
		_bytesArray = bTemp;
	}

	// ------------------------------------------------------
	public void saveFile(String str) {
		try {
			_bof = new ByteOutputFile(str);
			_bof.writeFileFromBytes(_bytesArray);
			_bof.close();
		} catch (Exception iofe) {
			new DiaException("Error while writing bytes !\n"+iofe.getMessage());
			System.exit(101);
		} // end catch

	}


	private String fixSeparator(String str) {
		return str.replace('/', File.separatorChar);
	}

	private boolean asciiChar(byte b) {
		String str = " ~";
		return !(b < (byte) str.charAt(0)) || (b > (byte) str.charAt(1));
	}

	private boolean byteIn(byte b, byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			if (b == bytes[i]) 
				return true;
		}
		return false;
	}

//	private boolean isIn(String str, byte[] b) { // XXXX Pascal: Pourquoi
//		// reinventer la roue quand
//		// ce type de methode est
//		// dans l'API standard?
//
//		for (int i = 0; i < str.length(); i++) {
//			if (b[0] == str.charAt(i))
//				return true;
//		}
//		return false;
//	}

	// -----------------------------------------------------------------------
	private boolean testValidityOfBytes() throws NullPointerException,
	 DiaException  {
		String nonImpStr = "\r\n\t";
		byte[] validCharTable = null;

		validCharTable = (nonImpStr + _charKnown).getBytes();

		for (int i = 0; i < _bytesArray.length; i++) {
			if (!asciiChar(_bytesArray[i]))
				if (!byteIn(_bytesArray[i], validCharTable)) {
					throw new DiaException(" Unknown caracter = " + Byte.toString(_bytesArray[i]));
              } // if (asciiChar(_b[i]) ;
		}
		return true;
	}// end testByte method
	
	private String createCharKnown(String path) throws NullPointerException,
	FileNotFoundException,IOException {
		if (path != null) {
//			try {
				//open file
				Properties prop = new Properties();
				prop.load(FilterFile.class.getResource(path).openStream());
				return prop.getProperty("acceptedChars", "");			

//			} catch (NullPointerException npe) {
//				System.err.println("Couldn't find file1: " + path);
//				return null;
//				//throw new IOFileException("The file name was empty");
//
//			} catch (FileNotFoundException fnfe) {
//				System.err.println("Couldn't find file3: " + path);
//				//throw new IOFileException("The file: " + path + " was not found");
//				return null;
//
//			} catch (IOException npe) {
//				System.err.println("Couldn't find file2: " + path);
//				return null;			
//			}
		}
		return "";

	}

}// end class Filter
