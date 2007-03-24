package dInternal.dData;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.util.StringTokenizer;
import java.util.Vector;


import dConstants.DConst;
import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.txt.FilterFile;


public class ExportData {

  private int _ROOMSIZE=10;
 private DModel _dm;

  /**
   *
   */
  public ExportData(DModel dm) {
    _dm = dm;
  }

  private String exportActivities(){
    /* 0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
    * 5= day number where activity is assign, 6= day name where activity is assign
    * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
    * 10= room name
    */
    StringBuffer report=new StringBuffer("");
    int [] table={1,2,6,7,8,10};
    //get data report of all sites
    StringBuffer strBuf= getStandardRepStr(table, 0);
    StringTokenizer strTokens = new StringTokenizer(strBuf.toString(),DConst.CR_LF);
    while(strTokens.hasMoreTokens()){
      String line= strTokens.nextToken();
      StringTokenizer rTokens = new StringTokenizer(line,";");
      //while(rTokens.hasMoreTokens()){
        report.append(rTokens.nextToken()+rTokens.nextToken());
        String section= rTokens.nextToken();
        report.append(section.toLowerCase()+rTokens.nextToken());
        StringTokenizer hourB = new StringTokenizer(rTokens.nextToken(),"h");
        report.append(hourB.nextToken()+hourB.nextToken());
        StringTokenizer hourEnd = new StringTokenizer(rTokens.nextToken(),"h");
        report.append(hourEnd.nextToken()+hourEnd.nextToken());
        String roomTemp=rTokens.nextToken();
        if(roomTemp.equalsIgnoreCase(DConst.NO_ROOM_INTERNAL))
          roomTemp= DConst.NO_ROOM_EXPORT;
        String room= roomTemp+"          ";
        report.append(room.substring(0,_ROOMSIZE)+DxTools.STIConvertGroup(section));
      //}// end while(tokens.hasMoreTokens())
        report.append(DConst.CR_LF);
    }// end while(strTokens.hasMoreTokens())
    return report.toString();
  }

  /**
   * return a Standard report string buffer
   * @param table
   *  @param type if type = 0: get ActivitiesReport. If type = 1: get StudentsReport
   * @return
   */
  private StringBuffer getStandardRepStr(int [] table, int type){
  	StringBuffer strBuf= new StringBuffer("");
 	if(_dm.isMultiSite()){
 		String currentS = _dm.getCurrentSite();
 		Vector sites =  _dm.getSites();
 		for(int i = 0; i < sites.size(); i++){
 			if(!DConst.ALL_SITES.equalsIgnoreCase(sites.get(i).toString())){
 				_dm.setCurrentSite(sites.get(i).toString());
 //				_dm.buildSetOfEvents();
 				_dm.getConditionsTest().initAllConditions();
 				DStandardReportData dataR = new DStandardReportData(_dm);
 				String str = getDataFromReport(dataR, table, type);
 				strBuf.append(str);
 			}
 		}
 		_dm.setCurrentSite(currentS);
 //		_dm.buildSetOfEvents();
		_dm.getConditionsTest().initAllConditions();
 	}else{
 		DStandardReportData dataR = new DStandardReportData(_dm);
 		String str = getDataFromReport(dataR, table, type);
 		strBuf= new StringBuffer(str);
 	}
 	return strBuf;
  }
  
  /**
   * get data from DStandardReportData
   * @param dataR
   * @param table
   * @param type if type = 0: get ActivitiesReport. If type = 1: get StudentsReport
   * @return
   */
  private String getDataFromReport(DStandardReportData dataR, int [] table , int type){
  	switch(type){
  	 case 0: return dataR.getActivitiesReport(0,table);
  	 case 1: return dataR.getStudentsReport(0,table);
  	}
  	return "";
  }
  
  private String exportStudents(){
     /*
  *_studentsReport is a string where each line contains more informations separeted
  * by a ";" separator
  * token number 0= student matricule, 1= student program, 2= student name, 3= student courses choice
  *--------------------------
  * the token number 3 contains more same type of informations separated by "," separator.
  * These subTokens contains more informations separated by a "-" separator
  * subtoken 0= unity name, 1= day number where this unity is place, 2= day name where this unity is place
  *3= begining hour of this unity
  */
    String report="";
    int [] table={1,2,3};
    String str= getStandardRepStr(table, 1).toString();
    StringTokenizer strTokens = new StringTokenizer(str,DConst.CR_LF);
    while(strTokens.hasMoreTokens()){
      String line= strTokens.nextToken();
      StringTokenizer rTokens = new StringTokenizer(line,";");
      report+=rTokens.nextToken()+rTokens.nextToken()+rTokens.nextToken()+"  ";
      if(rTokens.hasMoreTokens()){
        StringTokenizer courses = new StringTokenizer(rTokens.nextToken(),",");
        String groupCourseBef="";
        while(courses.hasMoreTokens()){
          String course= courses.nextToken();
          String viewElt= DXToolsMethods.getToken(course,"-",0);
          String groupCourse= " "+DXToolsMethods.getToken(viewElt,".",0)+DXToolsMethods.getToken(viewElt,".",1)+" ";
          if(!groupCourse.equalsIgnoreCase(groupCourseBef)){
            report+= groupCourse+DXToolsMethods.getToken(viewElt,".",2).toLowerCase();
          }
          groupCourseBef= groupCourse;
        }
      }// end while(strTokens.hasMoreTokens())
      report+=DConst.CR_LF;
    }// end if(rTokens.hasMoreTokens())

    return report;
  }

  /**
   *
   * @param fileName
   */
  public void saveExportReport(String dir){
    FilterFile filter = new FilterFile(exportActivities().getBytes(),"");
    filter.saveFile(dir + DConst.TT_FILE);
    filter = new FilterFile(exportStudents().getBytes(), "");
    filter.saveFile(dir + DConst.TT_STUD_FILE);
  }

}