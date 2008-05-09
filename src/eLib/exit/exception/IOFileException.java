package eLib.exit.exception;
/**
 *
 * Title: IOFileException $Revision: 1.3 $  $Date: 2006-07-27 15:07:13 $
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
 * @version $Revision: 1.3 $
 * @author  $Author: vimj9401 $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: IOFileException is a class used to create an
 *              IOFileException, this exception is used
 *              to catch Exceptions that can be throw when
 *              opening, reading, writing or closing a file.
 *
 */
public class IOFileException extends Exception{

  /**
     * 
     */
    //private static final long serialVersionUID = -5343466873661872412L;

/**
   * Requires: an exception.
   *
   * <p>
   * Modifies: nothing
   *
   * <p>
   * Effect: calls the Exception constructor
   */
  public IOFileException() {
    super();
  }

  /**
   *
   * Requires: an exceptition.
   *
   * <p>
   * Modifies: nothing
   *
   * <p>
   * Effect: calls the Exception constructor
   * 
   * @param str a String  which identifies the exception.
   */
  public IOFileException(String str) {
    super(str);
  }
} //end IOFileException