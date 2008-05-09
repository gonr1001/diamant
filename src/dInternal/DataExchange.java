/*
 * Created on 11 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dInternal;

/**
*
* Title: DataExchange $Revision $  $Date: 2005-02-03 16:52:42 $
* Description: DataExchange is a class used to
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
* @version $ $
* @author  $Author: garr2701 $
* @since JDK1.3
*/

public abstract class DataExchange {
	String _header;
	String _contents;

	/**
	 * 
	 */
	public DataExchange(String header, String contents) {
		_header = header;
		_contents = contents;		
	}
	
	public String getHeader(){
		return _header;
	}
	
	public String getContents(){
		return _contents;
	}
	
	public void setContents(String contents){
		_contents = contents;
	}
}
