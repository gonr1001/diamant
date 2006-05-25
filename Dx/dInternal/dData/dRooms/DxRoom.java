/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxRoom.java 
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
package dInternal.dData.dRooms;

import java.util.Vector;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxRoom is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxRoom {
    private long _lKey;
    private String _sName;
    private int _nCapacity;
    
        //Next members are not required but are provided in legacy files
        //Kept in case it would be needed
    private int _nFunction;
    private Vector<Integer> _viCharacteristics;
    private String _sComment;
    

}
