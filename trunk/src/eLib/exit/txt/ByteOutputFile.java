/**
 * Created on July 13, 2007
 * 
 * 
 * Title: ByteOutputFile.java 
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


/**
 * Description: ClassName is a class used to
 *
 */


import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Description: ByteOutputFile is a class used to write a file from a
 *              byte array. Normal sequence of operations are
 *              call a constructor (which opens a file), write the file
 *              with writeFileFromBytes and close.
 *
 *              <p>
 *              All Exceptions are catch then a IOFileExeption is thrown,
 *              then when using this ByteOutputFile only one Exception
 *              must be catch.
 *
 */

public class ByteOutputFile {
  private FileOutputStream _fos;


  /**
  *
  *
  * Requires: a valid fileName otherwise an Exception is thrown.
  *
  * <p>
  * Modifies: nothing
  *
  * <p>
  * Effect: the file is open, then it can be written, if not open
  *         an Exception is thrown.
  * 
  * @param fileName a String which contains the file name
  *         of the file to open.
  * @throws FileNotFoundException 
  * 
  * @throws IOFileException.
  */  
  
  public ByteOutputFile(String fileName) throws FileNotFoundException {   
      _fos = new FileOutputStream(fileName);
  }

 /**
  *
  *
  * Requires: the file must be already open otherwise
  *           IOException is thrown.
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
  * @throws IOException.
  */
//-----------------------------------------
  public void writeFileFromBytes(byte [] b) throws IOException {
        _fos.write(b);
  } // end writeFile()

 /**
  *
  *
  * <p>
  * Requires: the file must be already open otherwise
  *           IOException is thrown.
  *
  * <p>
  * Modifies: nothing.
  *
  * <p>
  * Effect: the file is closed otherwise if 
  *      a problem arise
  *      IOException is thrown.
  * 
  * @throws IOException.
  * 
  */
  
//-----------------------------------------
  public void close() throws IOException {
      _fos.close();
  } // end close()
//-----------------------------------------
} //end class ByteOutputFile