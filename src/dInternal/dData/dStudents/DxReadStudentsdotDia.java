/**
 * Created on 2006-09-29
 * 
 * Title: DxReadStudentsdotDia.java 
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

import dExceptions.DiaException;
import dInternal.DataExchange;


public class DxReadStudentsdotDia implements DxStudentsReader {
 	private DataExchange _deStudents;

  /**  Used to report line where error was found */ 
		private long currentLine=0;

		public DxReadStudentsdotDia(DataExchange de) {
			_deStudents = de;
		}
		public DxReadStudentsdotDia(DataExchange de,long line) {
			_deStudents = de;
			currentLine=line;
		}
		public DxSetOfStudents readSetOfStudents() throws DiaException {
			
			DxSetOfStudents dxsosStud = new DxSetOfStudents();
			return dxsosStud;
		}

	}
