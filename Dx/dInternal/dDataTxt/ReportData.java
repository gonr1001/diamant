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
import dInternal.dTimeTable.*;
import dInternal.dUtil.DXValue;
import dInternal.dConditionsTest.EventAttach;
import dResources.DConst;
import java.util.StringTokenizer;
import java.util.Vector;

public class ReportData {

  private DModel _dm;
  private int _AHOUR=60;// a hour= 60 minutes
  private String _HOURSEPARATOR="h";
  private String _activitiesReport="";

  /**
   * Constructor
   * @param dm
   */
  public ReportData(DModel dm) {
    _dm=dm;
    _activitiesReport= buildActivitiesReport();
  }

  /**
   *
   * @return
   */
  private String buildActivitiesReport(){
    String actlist="";
    for (int i=0; i< _dm.getSetOfActivities().size(); i++){
      Activity activity = (Activity)_dm.getSetOfActivities().getResourceAt(i).getAttach();
      if (activity.getActivityVisibility()){
        for(int j=0; j< activity.getSetOfTypes().size(); j++){
          Type nature = (Type)(activity.getSetOfTypes().getResourceAt(j)).getAttach();
          for (int k=0; k< nature.getSetOfSections().size(); k++){
            Section section= (Section)nature.getSetOfSections().getResourceAt(k).getAttach();
            for(int l=0; l< section.getSetOfUnities().size(); l++){
              Unity bloc= (Unity)section.getSetOfUnities().getResourceAt(l).getAttach();
              if(bloc.isAssign()){
                actlist+= _dm.getSetOfActivities().getResourceAt(i).getID()+";";// write activity name
                actlist+= activity.getSetOfTypes().getResourceAt(j).getID()+";";// write nature and 2 space
                actlist+= nature.getSetOfSections().getResourceAt(k).getID()+";";//soa.CR_LF;//
                actlist+= section.getSetOfUnities().getResourceAt(l).getID()+";";
                Assignment currentCycAss = (Assignment)bloc.getSetOfAssignments(
                    ).getResourceAt(_dm.getTTStructure().getCurrentCycleIndex()).getAttach();
                String hour= "00"+Integer.toString(bloc.getDuration()/_AHOUR);
                String minute= "00"+Integer.toString(bloc.getDuration()%_AHOUR);
                actlist+= hour.substring(hour.length()-2,hour.length())+_HOURSEPARATOR+
                          minute.substring(minute.length()-2,minute.length())+";";
                StringTokenizer dtime= new StringTokenizer(_dm.getTTStructure(
                    ).getCurrentCycle().getPeriod(currentCycAss.getDateAndTime()),DConst.TOKENSEPARATOR);
                long dayKey= Long.parseLong(dtime.nextToken());
                long seqKey= Long.parseLong(dtime.nextToken());
                long perKey= Long.parseLong(dtime.nextToken());
                actlist+=dayKey+";";
                actlist+= _dm.getTTStructure().getCurrentCycle().getSetOfDays(
                    ).getResource(dayKey).getID()+";";//.getID()
                Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByKey(dayKey,seqKey,perKey);
                hour= "00"+period.getBeginHour()[0];
                minute= "00"+period.getBeginHour()[1];
                actlist+= hour.substring(hour.length()-2,hour.length())+_HOURSEPARATOR+
                          minute.substring(minute.length()-2,minute.length())+";";
                int mnEnd= period.getBeginHour()[1]+bloc.getDuration()%_AHOUR;
                int hrEnd= period.getBeginHour()[0]+bloc.getDuration()/_AHOUR+mnEnd/_AHOUR;
                hour= "00"+Integer.toString(hrEnd);
                minute= "00"+Integer.toString(mnEnd%_AHOUR);
                actlist+= hour.substring(hour.length()-2,hour.length())+_HOURSEPARATOR+
                          minute.substring(minute.length()-2,minute.length())+";";
                actlist+= currentCycAss.getInstructorName()+";";
                actlist+= currentCycAss.getRoomName()+";"+SetOfActivities.CR_LF;
              }// end if(bloc.isAssign())
            }// end for(int l=0; l< section.getSetOfUnities().size(); l++)
          }// end for (int k=0; k< nature.getSetOfSections().size(); k++)
        }// end for(int j=0; j< activity.getSetOfTypes().size(); j++)
      }// end for (int i=0; i< soa.size(); i++){
    }// end if (activity.getActivityVisibility())
    return actlist;
  }

  /**
   *
   * @return
   */
  public String getActivitiesReport(int principalAct, int[] otherAct){
    SetOfResources setOf= new SetOfResources(1);
    StringTokenizer theReport= new StringTokenizer(_activitiesReport,SetOfActivities.CR_LF);
    int nbTokens= theReport.countTokens();
    while(theReport.hasMoreTokens()){
      String currentLine= theReport.nextToken();
      String ID= extractToken(currentLine,principalAct);
      DXValue dxValue=new DXValue();
      Vector vec= new Vector(1);
      for(int i=0; i< otherAct.length; i++){
        vec.add(extractToken(currentLine,otherAct[i]));
      }
      dxValue.setObjectValue(vec);
      setOf.addResource(new Resource(ID, dxValue),1);
    }// end while(theReport.hasMoreTokens())
    String report="";
    for (int i=0;i< setOf.size(); i++){
      report+=setOf.getResourceAt(i).getID()+";";//+setOf.CR_LF;
      Vector vec= (Vector)((DXValue)setOf.getResourceAt(i).getAttach()).getObjectValue();
      for (int j=0; j< vec.size(); j++){
        report+=vec.get(j).toString()+";";
      }// end for (int j=0; j< vec.size(); j++)
      report+=setOf.CR_LF;
    }// end  for(int i=0; i< nbTokens; i++)
    return report;
  }

  /**
   *
   * @param str
   * @param tokenNumber
   * 0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
   * 5= day number where activity is assign, 6= day name where activity is assign
   * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
   * 10= room name
   * @return
   */
  private String extractToken(String line, int tokenNumber){
    StringTokenizer str = new StringTokenizer(line,";");
    int nb= str.countTokens();
    String currentToken="";
    for (int i=0; i< nb; i++){
      currentToken=str.nextToken();
      if (i==tokenNumber)
        return currentToken;
    }
    return "";
  }


  /**
   *
   * @return
   */
  public String buildStudentsReport(){
    return "";
  }
}