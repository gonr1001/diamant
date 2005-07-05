package dInternal.dDataTxt;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.util.StringTokenizer;


import dConstants.DConst;
import dInterface.dUtil.DXTools;
import dInternal.DModel;
//import dInternal.dData.StandardReportData;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.txt.FilterFile;


public class ExportData {

 private StandardReportData _dataR;
 private int _ROOMSIZE=10;

  /**
   *
   */
  public ExportData(DModel dm) {
    _dataR = new StandardReportData(dm);
    //int [] table={1,2,3,4,5,6,7,8,9,10};
    //System.out.println( _dataR.getActivitiesReport(0,table));
  }

  public String exportActivities(){
    /* 0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
    * 5= day number where activity is assign, 6= day name where activity is assign
    * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
    * 10= room name
    */
    StringBuffer report=new StringBuffer("");
    int [] table={1,2,6,7,8,10};
    StringBuffer strBuf= new StringBuffer(_dataR.getActivitiesReport(0,table));
    //System.out.println(str);
    //System.out.println("**********************************");
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
        report.append(room.substring(0,_ROOMSIZE)+DXTools.STIConvertGroup(section));
      //}// end while(tokens.hasMoreTokens())
        report.append(DConst.CR_LF);
    }// end while(strTokens.hasMoreTokens())
    return report.toString();
  }

  public String exportStudents(){
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
    String str= _dataR.getStudentsReport(0,table);
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