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
import dInternal.dConditionsTest.ConflictsAttach;

import dInterface.dUtil.DXTools;
import dResources.DConst;
import java.util.StringTokenizer;
import java.util.Vector;

public class StandardReportData {

  private DModel _dm;
  private int _AHOUR=60;// a hour= 60 minutes
  private int STATE1=300;
  private int STATE2=600;
  private int STATE3=100;
  private String _HOURSEPARATOR="h";
  /*
  *_activitiesReport is a string where each line contains more informations separeted
  * by a ";" separator
  * token number 0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
  * 5= day number where activity is assign, 6= day name where activity is assign
  * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
  * 10= room name
  */
  private String _activitiesReport="";
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
  private String _studentsReport="";

  /**
   *_conflictsReport is a string where each line contains more informations separeted
   * by a ";" separator
   * token number 0= day number, 1= day name, 2= sequence Id, 3= period id, 4= period begin hour
   * 5= first event in conflict, 6= event in conflict with the first, 7= number of conflicts, 8= type of conflicts
   * --------------------------
   * description of the token number 8: type of conflicts
   * type= 0: student conflict
   * type= 1: room conflict
   * type= 2: instructor conflict
   */
  private String _conflictsReport="";

  /**
   * Constructor
   * @param dm
   */
  public StandardReportData(DModel dm) {
    _dm=dm;
    _activitiesReport= buildActivitiesReport();
    _studentsReport = buildStudentsReport();
    _conflictsReport=buildConflictsReport();
    _dm.getProgressBarState().setIntValue(1000);
   // System.out.println("**** Final Change progess bar: "+ _dm.getProgressBarState().getIntValue());
  }

  /**
   *
   * @return
   */
  private String buildActivitiesReport(){
    String actlist="";
    int size=_dm.getSetOfActivities().size();
    for (int i=0; i<size ; i++){
      _dm.getProgressBarState().setIntValue(STATE1*i/size);
      //System.out.println("Change progess bar: "+ _dm.getProgressBarState().getIntValue());
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
   * return standard activity report
   * @param principalAct
   * @param otherAct
   * @return
   */
  public String getActivitiesReport( int principalElt, int[] otherElts){
    return getReport(_activitiesReport,principalElt,otherElts);
  }

  /**
   * return standard conflicts report
   * @param principalElt The index of the first report element
   * @param otherElts The index of the other report elements
   * @return
   */
  public String getConflictsReport( int principalElt, int[] otherElts){
    return getReport(_conflictsReport, principalElt, otherElts);
  }

  /**
   * return standard student report
   * @param principalElt
   * @param otherElt
   * @return
   */
  public String getStudentsReport( int principalElt, int[] otherElts){
    return getReport(_studentsReport,principalElt,otherElts);
  }

  /**
   * @return
   */
  private String getReport(String allLines, int principalAct, int[] otherAct){
    SetOfResources setOf= new SetOfResources(1);
    StringTokenizer theReport= new StringTokenizer(allLines,SetOfActivities.CR_LF);
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
      setOf.addResource(new Resource(ID, dxValue),0);
    }// end while(theReport.hasMoreTokens())
    setOf.sortSetOfResourcesByID();
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
   *_conflictsReport is a string where each line contains more informations separeted
   * by a ";" separator
   * token number 0= day number, 1= day name, 2= sequence Id, 3= period id, 4= period begin hour
   * 5= first event in conflict, 6= event in conflict with the first, 7= number of conflicts, 8= type of conflicts
   * --------------------------
   * description of the token number 8: type of conflicts
   * type= 0: student conflict
   * type= 1: room conflict
   * type= 2: instructor conflict
   * @return
   */
  private String buildConflictsReport(){
    String report="";
    int size= _dm.getTTStructure().getCurrentCycle().getSetOfDays().size();
    for(int i=0; i< size; i++){
      _dm.getProgressBarState().setIntValue(STATE1+STATE2+STATE3*i/size);
      Resource day= _dm.getTTStructure().getCurrentCycle().getSetOfDays().getResourceAt(i);
      for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++){
        Resource seq= ((Day)day.getAttach()).getSetOfSequences().getResourceAt(j);
        for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriods().size(); k++){
          Resource per= ((Sequence)seq.getAttach()).getSetOfPeriods().getResourceAt(k);
          ((Period)per.getAttach()).getEventsInPeriod().sortSetOfResourcesByID();
          for(int x=0; x< ((Period)per.getAttach()).getEventsInPeriod().size(); x++){
            Resource confEvents= ((Period)per.getAttach()).getEventsInPeriod().getResourceAt(x);
            ((ConflictsAttach)confEvents.getAttach()).getConflictsAttach().sortSetOfResourcesByID();
            for(int y=0; y< ((ConflictsAttach)confEvents.getAttach()).getConflictsAttach().size(); y++){
              Resource confAttach= ((ConflictsAttach)confEvents.getAttach()).getConflictsAttach().getResourceAt(y);
              DXValue confValue= (DXValue)confAttach.getAttach();
              report+=day.getKey()+";"+day.getID()+";"+seq.getID()+";"+per.getID()+";"+((Period)per.getAttach()).getBeginHour()[0]+
                      ":"+((Period)per.getAttach()).getBeginHour()[1]+";"+_dm.getSetOfEvents().getEventID(confEvents.getID(), _dm.getSetOfActivities())+
                      ";"+_dm.getSetOfEvents().getEventID(confAttach.getID(), _dm.getSetOfActivities())+
                      ";"+confValue.getIntValue()+";"+confValue.getStringValue()+";"+SetOfResources.CR_LF;
            }// end for(int y=0; y< ((ConflictsAttach)confEvents.ge
          }// end for(int x=0; x< ((Period)per.getAttach()).getEventsInPeriod()
        }//end for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriods()
      }//end for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size()
    }//end for(int i=0; i< _dm.getTTStructure().getCurrentCycle().getSetOfDays().size(
    //System.out.println(report);//debug
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
  private String buildStudentsReport(){
    String studlist="";
    int size= _dm.getSetOfStudents().size();
    for (int i=0; i< size; i++){
      _dm.getProgressBarState().setIntValue(STATE1+STATE2*i/size);
      StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResourceAt(i).getAttach();
      String line = _dm.getSetOfStudents().getResourceAt(i).toWrite(" ");
      StringTokenizer strTokens= new StringTokenizer(line.substring(SetOfStudents._ENDSTUDENTNUMBEROFCOURSE,line.length()));//+SetOfResources.CR_LF;
      String name_mat = line.substring(0,SetOfStudents._BEGINSTUDENTNUMBEROFCOURSE);
      String str= name_mat.substring(0,SetOfStudents._ENDSTUDENTMATRICULE)+";"+
                  name_mat.substring(SetOfStudents._ENDSTUDENTMATRICULE,SetOfStudents._BEGINSTUDENTNAME)
                +";"+name_mat.substring(SetOfStudents._BEGINSTUDENTNAME,SetOfStudents._ENDSTUDENTNAME)+";";
      String strcrs="";
      while(strTokens.hasMoreTokens()){
        String course= strTokens.nextToken();
        String sect="";
        if(course.length()==SetOfStudents._COURSEGROUPLENGTH){
          int group= Integer.parseInt(course.substring(SetOfStudents._COURSELENGTH, SetOfStudents._COURSEGROUPLENGTH));
          sect=course.substring(0,SetOfStudents._COURSELENGTH-1)+"."+
               course.substring(SetOfStudents._COURSELENGTH-1, SetOfStudents._COURSELENGTH)+
               "."+Character.toString(DXTools.STIConvertGroup(group))+".";
          Section section= _dm.getSetOfActivities().getSection(course.substring(0,SetOfStudents._COURSELENGTH-1)
              ,course.substring(SetOfStudents._COURSELENGTH-1, SetOfStudents._COURSELENGTH),
              Character.toString(DXTools.STIConvertGroup(group)));
          if(section!=null){
            for(int j=0; j<section.getSetOfUnities().size(); j++){
              Unity bloc= (Unity)section.getSetOfUnities().getResourceAt(j).getAttach();
              Assignment currentCycAss = (Assignment)bloc.getSetOfAssignments(
                  ).getResourceAt(_dm.getTTStructure().getCurrentCycleIndex()).getAttach();
              StringTokenizer dtime= new StringTokenizer(_dm.getTTStructure(
                  ).getCurrentCycle().getPeriod(currentCycAss.getDateAndTime()),DConst.TOKENSEPARATOR);
              long dayKey= Long.parseLong(dtime.nextToken());
              long seqKey= Long.parseLong(dtime.nextToken());
              long perKey= Long.parseLong(dtime.nextToken());
              Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByKey(dayKey,seqKey,perKey);
              String hour= "00"+period.getBeginHour()[0];
              String minute= "00"+period.getBeginHour()[1];
              String time= hour.substring(hour.length()-2,hour.length())+_HOURSEPARATOR+
                           minute.substring(minute.length()-2,minute.length());
              strcrs+=sect+Integer.toString(j+1)+"-"+ _dm.getTTStructure().getCurrentCycle().getSetOfDays(
                  ).getResource(dayKey).getKey()+"-"+ _dm.getTTStructure().getCurrentCycle().getSetOfDays(
                  ).getResource(dayKey).getID()+"-"+time+",";

            }// end for(int j=0; j<section.getSetOfUnities().size(); j++)
          }// end  if(section!=null)
        }// end if(course.length()==SetOfStudents._COURSEGROUPLENGTH)
      }// end while(strTokens.hasMoreTokens())
      studlist+= str+strcrs+";"+SetOfResources.CR_LF;
    }// end for (int i=0; i< _dm.getSetOfStudents().size(); i++)
    return studlist;
  }
}