package dInternal.dData;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam
 * @version 1.0
 */


import java.util.Vector;
import java.util.StringTokenizer;
import java.io.File;

import com.iLib.gDialog.FatalProblemDlg;
import com.iLib.gIO.FilterFile;
import dInternal.dTimeTable.TTStructure;

public class SaveData {

  private final String CR_LF = "\r\n";
  private String _version="1.5";
  /**
   *
   * */
  public SaveData(String version) {
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
  public String saveTimeTable(TTStructure tts,SetOfInstructors inst, SetOfRooms rooms,
                          SetOfActivities act,SetOfStudents students, String fileName){
    String error = "";
    if (inst == null || rooms  == null ||  act == null || students == null){
      error = "SaveData : Some data have a null reference";
      return error;
    }
    FilterFile filter;
     if(fileName.endsWith(".dia"))
      fileName=fileName.substring(0,fileName.length()-4);
    tts.saveTTStructure(fileName+".xml");
    String diaData=_version+CR_LF;
    diaData+=LoadData._saveSeparator+CR_LF;
    diaData+=fileName+".xml"+CR_LF;
    diaData+=LoadData._saveSeparator+CR_LF;
    diaData+=inst.size()+CR_LF;
    diaData+=inst.toWrite()+CR_LF;
    diaData+=LoadData._saveSeparator+CR_LF;
    diaData+=rooms.toWrite()+CR_LF;
    diaData+=LoadData._saveSeparator+CR_LF;
    diaData+=act.toWrite()+CR_LF;
    diaData+=LoadData._saveSeparator+CR_LF;
    diaData+=students.size()+CR_LF;
    diaData+=students.toWrite()+CR_LF;
    diaData+=LoadData._saveSeparator;
    filter= new FilterFile(diaData.getBytes());
    filter.saveFile(fileName+".dia");
    return error;
  }

  /**
   *
   * */
  public void saveTTStructure(TTStructure tts, String fileName){
     if(!fileName.endsWith(".xml"))
      fileName=fileName+".xml";
    tts.saveTTStructure(fileName);
  }

}