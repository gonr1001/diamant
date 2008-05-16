/**
 *  Created on July 13, 2007
 * 
 * 
 * Title: ByteInputFile.java 
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
/**
 * 
 * Description: FilterFileTest is a class used to:
 * <p>
 * Make tests to FilterFile
 * <p>
 * 
 */


/**
 *
 * Title: ByteInputFile $Revision: 1.6 $  $Date: 2007-11-30 21:40:15 $
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
 * @version $Revision: 1.6 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;


/**
 * Description: ByteInputFile is a class used to read a file as a
 *              byte array. Normal sequence of operations are
 *              call a constructor (which opens a file), read the file
 *              with readFileAsBytes and close.
 *
 *              <p>
 *              All Exceptions are catch then a IOFileExeption is throw,
 *              then when using this ByteInputFile only one Exception
 *              must be catch.
 *
 */
public class ByteInputFile {
	
	private FileInputStream _fis;
	
	/**
	 *
	 *
	 * Requires: a valid fileName otherwise IOFileException is thrown.
	 *
	 * <p>
	 * Modifies: nothing
	 *
	 * <p>
	 * Effect: the file is open, then it can be read, if not open
	 *         an Exception is thrown.
	 * 
	 * @param fileName a String which contains the file name
	 *         of the file to open.
	 * 
	 * @throws NullPointerException, IOException.
	 * 
	 */
	public ByteInputFile(String fileName) throws NullPointerException,
			FileNotFoundException {
		_fis = new FileInputStream(fileName);
	}

	/**
	 *
	 *
	 * Requires: the file must be already open otherwise
	 *           IOFileException is thrown.
	 *
	 * <p>
	 * Modifies: the byte[] Array contains the bytes of the file.
	 *
	 * <p>
	 * Effect: the file is read, if a problem arise
	 *       an Exception is thrown.
	 * 
	 * @return  byte[] Array which contains all the bytes on the file.
	 * 
	 *  @throws NullPointerException, IOException.
	 *  
	 */
	public byte[] readFileAsBytes() throws NullPointerException, IOException {
		byte[] b = null;
		//    try{
		int n;
		/* all bytes are read in one time */
		while ((n = _fis.available()) > 0) {
			b = new byte[n];
			int result = _fis.read(b);
			if (result == -1)
				break;
		}// end while

		return b;
	} 

	/**
	 * Count lines, words and characters in given input stream
	 * @param string
	 * @return number of lines
	 * 
	 *  
	 *  @throws NullPointerException, IOException.
	 *
	 *
	 */

	public static long countLines(String string) throws NullPointerException, IOException {
		long numLines = 0;
		String line;
		BufferedReader in = new BufferedReader(new StringReader(string));
			do {
				line = in.readLine();
				if (line != null) {
					numLines++;
				}
			} while (line != null);
		return numLines;
	}

	/**
	 *
	 *
	 * <p>
	 * Requires: the file must be already open otherwise
	 *           IOFileException is thrown.
	 *
	 * <p>
	 * Modifies: nothing.
	 *
	 * <p>
	 * Effect: the file is closed otherwise if 
	 *      a problem arise
	 *      Exception is thrown.
	 * 
	 * @throws NullPointerException, IOException.
	 * 
	 */
	public void close() throws NullPointerException, IOException {
			_fis.close();
	} 
	

} /* end class ByteInputFile */