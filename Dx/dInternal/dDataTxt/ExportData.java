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
import java.util.StringTokenizer;

public class ExportData {

 StandardReportData _dataR;

  /**
   *
   */
  public ExportData(DModel dm) {
    _dataR = new StandardReportData(dm);
    //int [] table={0,1,2,6,7,8,10};
    // dataR.getActivitiesReport(9,table);
  }

  public String exportActivities(){
    /* 0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
    * 5= day number where activity is assign, 6= day name where activity is assign
    * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
    * 10= room name
    */
    int [] table={1,2,6,7,8,10};
    String str= _dataR.getActivitiesReport(0,table);
    StringTokenizer strTokens = new StringTokenizer(str,SetOfResources.CR_LF);
    while(strTokens.hasMoreTokens()){
      String line= strTokens.nextToken();

    }// end while(strTokens.hasMoreTokens())
    return "";
  }

  public String exportStudents(SetOfStudents sos){

    return "";
  }
}