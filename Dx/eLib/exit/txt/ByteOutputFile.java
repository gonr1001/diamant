package eLib.exit.txt;
/**
 *
 * Title: ByteOutputFile $Revision: 1.2 $  $Date: 2004-09-10 13:31:14 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: ClassName is a class used to
 *
 */


import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import eLib.exit.exception.IOFileException;

/**
 * Description: ByteOutputFile is a class used to write a file from a
 *              byte array. Normal sequence of operations are
 *              call a constructor (which opens a file), write the file
 *              with writeFileFromBytes and close.
 *
 *              <p>
 *              All Exceptions are catched then a IOFileExeption is thrown,
 *              then when using this ByteOutputFile only one Exception
 *              must be catched.
 *
 */

public class ByteOutputFile {
  private FileOutputStream _fos;
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
  * Effect: the file is open, then it can be written, if not open
  *         IOFileException is thrown.
  * 
  * @param fileName a String which contains the file name
  *         of the file to open.
  * 
  * @throws IOFileException.
  */  
  
  public ByteOutputFile(String fileName) throws IOFileException {
    _fileName = fileName;
    try {
      //open file
      _fos = new FileOutputStream(_fileName);
    } catch (FileNotFoundException fnfe) {
        throw new IOFileException("The file: "  + _fileName + " was not found");
    }
  }// end OutputFile(String fname)

 /**
  *
  *
  * Requires: the file must be already open otherwise
  *           IOFileException is thrown.
  *
  * <p>
  * Modifies: the file contains the the byte[] Array.
  *
  * <p>
  * Effect: the file is read, if a problem arise
  *      IOFileException is thrown.
  * 
  * @return  byte[] Array which contains all the bytes of the file.
  * 
  * @throws IOFileException.
  */
//-----------------------------------------
  public void writeFileFromBytes(byte [] b) throws IOFileException {
    try{
        _fos.write(b);
    } catch (IOException e) {
          System.err.println(e);
          throw new IOFileException("The file: "  + _fileName + " has a problem somewhere in file");
      }// end catch
  } // end writeFile()

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
  
//-----------------------------------------
  public void close() throws IOFileException {
    try {
      _fos.close();
    } catch (IOException e){
        throw new IOFileException("Unable to close file: " + _fileName + ".");
    }
  } // end close()
//-----------------------------------------
} //end class ByteOutputFile