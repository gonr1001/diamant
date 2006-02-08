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


import dConstants.DConst;

import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dInstructors.SetOfInstructors;
import dInternal.dData.dStudents.SetOfStuSites;


import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.txt.FilterFile;


public class DSaveData {

  private String _version = DConst.APP_NAME;
  /**
   *
   * */
  public DSaveData(String version) {
    _version= version;
  }

  /**
   * */
  public String getVersion(){
    return _version;
  }

  /**
   *
   * */
  public String saveTimeTable(TTStructure tts,SetOfInstructors inst, SetOfSites rooms,
                          SetOfActivitiesSites act,SetOfStuSites students, String fileName){
    String error = "";
    if (inst == null || rooms  == null ||  act == null || students == null){
      error = "SaveData : Some data have a null reference";
      return error;
    }
    FilterFile filter;
     if(fileName.endsWith( DConst.DOT_DIA ))
      fileName=fileName.substring(0,fileName.length()-4);
    tts.saveTTStructure(fileName+DConst.DOT_XML);
    String diaData=_version+DConst.CR_LF;
    diaData+= DConst.SAVE_SEPARATOR + DConst.CR_LF;
    diaData+= DXToolsMethods.getRelativeFileName(fileName+DConst.DOT_XML).trim()+DConst.CR_LF;
    diaData+=DConst.SAVE_SEPARATOR + DConst.CR_LF;
    diaData+=inst.size()+DConst.CR_LF;
    diaData+=inst.toWrite()+ DConst.CR_LF;
    diaData+=DConst.SAVE_SEPARATOR + DConst.CR_LF;
    diaData+=DConst.FILE_VER_NAME1_6+DConst.CR_LF;
    diaData+=rooms.toWrite()+ DConst.CR_LF;
    diaData+=DConst.SAVE_SEPARATOR + DConst.CR_LF;
    diaData+=DConst.FILE_VER_NAME1_6+DConst.CR_LF;
    diaData+=act.toWrite()+ DConst.CR_LF;
    diaData+=DConst.SAVE_SEPARATOR + DConst.CR_LF;
    diaData+=DConst.FILE_VER_NAME1_6+DConst.CR_LF;
    diaData+=students.toWrite()+DConst.CR_LF;
    diaData+=DConst.SAVE_SEPARATOR;
    filter= new FilterFile(diaData.getBytes(),"");
    filter.saveFile(fileName + DConst.DOT_DIA);
    return error;
  } //end saveTimeTable

  /**
   *
   * */
  public String saveTTStructure(TTStructure tts, String fileName){
  	String error="";
    if(!fileName.endsWith(DConst.DOT_XML))
      fileName=fileName+DConst.DOT_XML;
    error = tts.saveTTStructure(fileName);
    return error;
  }




}