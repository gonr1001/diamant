package dInternal.dData;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dInternal.DModel;
import dInternal.dUtil.DXToolsMethods;
import java.util.StringTokenizer;
import dInterface.dUtil.DXTools;
import com.iLib.gIO.FilterFile;

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
    String report="";
    int [] table={1,2,6,7,8,10};
    String str= _dataR.getActivitiesReport(0,table);
    //System.out.println(str);
    //System.out.println("**********************************");
    StringTokenizer strTokens = new StringTokenizer(str,SetOfResources.CR_LF);
    while(strTokens.hasMoreTokens()){
      String line= strTokens.nextToken();
      StringTokenizer rTokens = new StringTokenizer(line,";");
      //while(rTokens.hasMoreTokens()){
        report+=rTokens.nextToken()+rTokens.nextToken();
        String section= rTokens.nextToken();
        report+= section.toLowerCase()+rTokens.nextToken();
        StringTokenizer hourB = new StringTokenizer(rTokens.nextToken(),"h");
        report+=hourB.nextToken()+hourB.nextToken();
        StringTokenizer hourEnd = new StringTokenizer(rTokens.nextToken(),"h");
        report+=hourEnd.nextToken()+hourEnd.nextToken();
        String room= rTokens.nextToken()+"          ";
        report+= room.substring(0,_ROOMSIZE)+DXTools.STIConvertGroup(section);
      //}// end while(tokens.hasMoreTokens())
      report+=SetOfResources.CR_LF;
    }// end while(strTokens.hasMoreTokens())
    return report;
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
    StringTokenizer strTokens = new StringTokenizer(str,SetOfResources.CR_LF);
    while(strTokens.hasMoreTokens()){
      String line= strTokens.nextToken();
      StringTokenizer rTokens = new StringTokenizer(line,";");
      report+=rTokens.nextToken()+rTokens.nextToken()+rTokens.nextToken()+"    ";
      StringTokenizer courses = new StringTokenizer(rTokens.nextToken(),",");
      while(courses.hasMoreTokens()){
        String course= courses.nextToken();
        String viewElt= DXToolsMethods.getToken(course,"-",0);
        report+= DXToolsMethods.getToken(viewElt,".",0)+DXToolsMethods.getToken(viewElt,".",1)+
               "  "+DXToolsMethods.getToken(viewElt,".",2).toLowerCase()+" ";
      }
      report+=SetOfResources.CR_LF;
    }// end while(strTokens.hasMoreTokens())

    return report;
  }

  /**
   *
   * @param fileName
   */
  public void saveExportReport(String fileName){
    FilterFile filter = new FilterFile(exportActivities().getBytes(),"");
    filter.saveFile(DXToolsMethods.getToken(fileName,".",0)+"_Export_1.txt");
    filter = new FilterFile(exportStudents().getBytes(), "");
    filter.saveFile(DXToolsMethods.getToken(fileName,".",0)+"_Export_2.txt");
  }

}