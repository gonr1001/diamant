/**
 * Created on 2006-09-29
 * 
 * Title: DxStudentsReader.java 
 * 
 *
 * Copyright (c) 2006 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @author HAROUNA Abdoul-Kader
 * @since JDK1.3
 */

package dInternal.dData.dStudents;

import eLib.exit.exception.DxException;

public interface DxStudentsReader{
    public DxSetOfStudents readSetOfStudents() throws DxException;
}
