/**
 *
 * Title: SaveData 
 * Description: DConst is a class used to
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
 *
 */

package dInternal.dData;

import java.io.File;

import dConstants.DConst;

import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dActivities.DxSetOfActivitiesSites;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dStudents.SetOfStuSites;

import dInternal.dTimeTable.TTStructure;
import eLib.exit.txt.FilterFile;

public class DSaveData {

	private String _version = DConst.APP_NAME;

	/**
	 *
	 */
	public DSaveData(String version) {
		_version = version;
	}

	/**
	 * 
	 */
	public String getVersion() {
		return _version;
	}


	/**
	 *
	 */
	public String saveTimeTable(TTStructure tts, DxSetOfInstructors inst,
			SetOfSites rooms, SetOfActivitiesSites act, SetOfStuSites students,
			String fileName) {
		String error = "";
		if (inst == null || rooms == null || act == null || students == null) {
			error = "SaveData : Some data have a null reference";
			return error;
		}
		FilterFile filter;
		String locaFileName = "";
		if (fileName.endsWith(DConst.DOT_DIA))
			locaFileName = fileName.substring(0, fileName.length() - 4);
		tts.saveTTStructure(locaFileName + DConst.DOT_XML);
		String diaData = _version + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += getRelativeFileName(locaFileName + DConst.DOT_XML).trim()
				+ DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += inst.size() + DConst.CR_LF;
		diaData += inst.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += rooms.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += act.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += students.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR;
		filter = new FilterFile(diaData.getBytes(), "");
		filter.saveFile(locaFileName + DConst.DOT_DIA);
		return error;
	} //end saveTimeTable
	
	/**
	 *
	 */
	public String saveTimeTable(TTStructure tts, DxSetOfInstructors inst,
			DxSetOfSites sites, SetOfActivitiesSites act, SetOfStuSites students,
			String fileName) {
		String error = "";
		if (inst == null || sites == null || act == null || students == null) {
			error = "SaveData : Some data have a null reference";
			return error;
		}
		String locaFileName = "";
		FilterFile filter;
		if (fileName.endsWith(DConst.DOT_DIA))
			locaFileName = fileName.substring(0, fileName.length() - 4);
		tts.saveTTStructure(locaFileName + DConst.DOT_XML);
		String diaData = _version + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += getRelativeFileName(locaFileName + DConst.DOT_XML).trim()
				+ DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += inst.size() + DConst.CR_LF;
		diaData += inst.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += sites.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += act.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += students.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR;
		filter = new FilterFile(diaData.getBytes(), "");
		filter.saveFile(locaFileName + DConst.DOT_DIA);
		return error;
	} //end saveTimeTable
	
	public String saveTimeTable(TTStructure tts, DxSetOfInstructors inst,
			DxSetOfSites sites, DxSetOfActivitiesSites act, SetOfStuSites students,
			String fileName) {
		String error = "";
		if (inst == null || sites == null || act == null || students == null) {
			error = "SaveData : Some data have a null reference";
			return error;
		}
		FilterFile filter;
		String locaFileName = "";
		if (fileName.endsWith(DConst.DOT_DIA))
			locaFileName = fileName.substring(0, fileName.length() - 4);
		tts.saveTTStructure(locaFileName + DConst.DOT_XML);
		String diaData = _version + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += getRelativeFileName(locaFileName + DConst.DOT_XML).trim()
				+ DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += inst.size() + DConst.CR_LF;
		diaData += inst.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += sites.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += act.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += students.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR;
		filter = new FilterFile(diaData.getBytes(), "");
		filter.saveFile(locaFileName + DConst.DOT_DIA);
		return error;
	} //end saveTimeTable
	
	
	
	public String saveTimeTable(TTStructure tts, DxSetOfInstructors inst,
			SetOfSites sites, DxSetOfActivitiesSites act, SetOfStuSites students,
			String fileName) {
		String error = "";
		if (inst == null || sites == null || act == null || students == null) {
			error = "SaveData : Some data have a null reference";
			return error;
		}
		FilterFile filter;
		String locaFileName = "";
		if (fileName.endsWith(DConst.DOT_DIA))
			locaFileName = fileName.substring(0, fileName.length() - 4);
		tts.saveTTStructure(locaFileName + DConst.DOT_XML);
		String diaData = _version + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += getRelativeFileName(locaFileName + DConst.DOT_XML).trim()
				+ DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += inst.size() + DConst.CR_LF;
		diaData += inst.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += sites.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += act.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR + DConst.CR_LF;
		diaData += DConst.FILE_VER_NAME1_6 + DConst.CR_LF;
		diaData += students.toWrite() + DConst.CR_LF;
		diaData += DConst.SAVE_SEPARATOR;
		filter = new FilterFile(diaData.getBytes(), "");
		filter.saveFile(locaFileName + DConst.DOT_DIA);
		return error;
	} //end saveTimeTable
	
	/**
	 * give the relative path of a file
	 * Exemple:
	 * input of an absolute path is c:\developpement\DiaExtreme\DX\data\fgen\ete04.dia
	 * the operation return: ete04.dia
	 * @param str
	 * @return
	 */
	private String getRelativeFileName(String str) {
		return str.substring(str.lastIndexOf(File.separator) + 1, str.length());
	} // end getRelativeFileName
	
	/**
	 *
	 * */
	public String saveTTStructure(TTStructure tts, String fileName) {
		String error = "";
		String localFileName = "";
		if (!fileName.endsWith(DConst.DOT_XML))
			localFileName = fileName + DConst.DOT_XML;
		error = tts.saveTTStructure(localFileName);
		return error;
	}
}