package eLib.exit.txt;
/**
 *
 * Title: ByteInputFile $Revision: 1.4 $  $Date: 2006-12-05 14:22:20 $
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

import eLib.exit.exception.IOFileException;


/**
  * Description: ByteInputFile is a class used to read a file as a
  *              byte array. Normal sequence of operations are
  *              call a constructor (which opens a file), read the file
  *              with readFileAsBytes and close.
  *
  *              <p>
  *              All Exceptions are catched then a IOFileExeption is throw,
  *              then when using this ByteInputFile only one Exception
  *              must be catched.
  *
  */
public class ByteInputFile {
  private FileInputStream _fis;
  private String _fileName;

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
   *         IOFileException is thrown.
   * 
   * @param fileName a String which contains the file name
   *         of the file to open.
   * 
   * @throws IOFileException.
   */
  public ByteInputFile(String fileName) throws IOFileException {
    _fileName = fileName;
    try {
      //open file
      _fis = new FileInputStream(_fileName);
    } catch (NullPointerException npe) {
      throw new IOFileException("The file name was empty");
    } catch (FileNotFoundException fnfe) {
      throw new IOFileException("The file: " + _fileName + " was not found");
    }
  }// end ByteInputFile(String fileName)

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
   *      IOFileException is thrown.
   * 
   * @return  byte[] Array which contains all the bytes of the file.
   * 
   * @throws IOFileException.
   */
  public byte[] readFileAsBytes() throws IOFileException {
    byte[] b = null;
    try{
      int n;
      /* all bytes are read in one time */
      while( (n=_fis.available())>0){
        b = new byte [n];
        int result = _fis.read(b);
        if(result == -1)
          break;
      }// end while
    } catch (NullPointerException npe) {
      throw new IOFileException("The file was not open");
    } catch (IOException e) {
        System.err.println(e);
        throw new IOFileException("The file: " + _fileName + " has a problem somewhere in file");
    }// end catch
    return b;
  } // end readFileAsBytes()
 
  /**
   * Count lines, words and characters in given input stream
   * @param string
   * @return number of lines
   */
  
	public static long count(String string) {
		long numLines = 0;
		String line;
		BufferedReader in = new BufferedReader(new StringReader(string));
		try {
			do {
				line = in.readLine();

				if (line != null) {
					numLines++;
				}
			} while (line != null);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
   *      IOFileException is thrown.
   * 
   * @throws IOFileException.
   * 
   */
  public void close() throws IOFileException {
    try {
      _fis.close();
    } catch (NullPointerException npe) {
      throw new IOFileException("The file was not open");
    } catch (IOException e){
      throw new IOFileException("Unable to close File: " + _fileName + "");
    }
  } // end close()

} /* end class ByteInputFile */