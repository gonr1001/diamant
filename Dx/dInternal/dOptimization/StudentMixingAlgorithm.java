package dInternal.dOptimization;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import java.util.Vector;

import dConstants.DConst;
import dInterface.dUtil.DXTools;
import dInternal.DModel;
import dInternal.dDataTxt.Activity;
import dInternal.dDataTxt.Resource;
import dInternal.dDataTxt.Section;
import dInternal.dDataTxt.SetOfActivities;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dDataTxt.StudentAttach;
import dInternal.dDataTxt.Type;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;
import dInternal.dUtil.DXValue;

public class StudentMixingAlgorithm implements Algorithm {

  private DModel _dm;
  private Vector _eventsRescList;
  private int _mixingType;// 0= balance student mixing,  1= balance student
  // mixing with acceptable variation, 2= optimize student mixing,
  // 3= student mixing with personalize acceptable variation
  private int MEDIANACCEPTABLEVARIATION=10;
  private int PERSONALACCEPTABLEVARIATION=10;

  /**
   *
   * @param DModel dm
   * @param int the mixingType 0= balance student mixing, 1= optimize student mixing
   */
  public StudentMixingAlgorithm(DModel dm, int mixingType) {
    _dm= dm;
    _mixingType= mixingType;

  }

  /**
   *@param int the mixingType 0= balance student mixing,  1= balance student
  * mixing with acceptable variation, 2= optimize student mixing,
  * 3= student mixing with personalize acceptable variation
   * @param DModel dm
   */
  public StudentMixingAlgorithm(int AcceptVariation, DModel dm) {
    _dm= dm;
    _mixingType=3;
    PERSONALACCEPTABLEVARIATION= AcceptVariation;
  }

  /**
   *
   * @param context
   * @return
   */
  private int getAcceptableVariation(){
    int value=0;
    switch(_mixingType){
      case 0: value= 1;
        break;
      case 1: value= MEDIANACCEPTABLEVARIATION;//intermediaire
        break;
      case 2: value= 50;
        break;
      case 3: value= PERSONALACCEPTABLEVARIATION;
        break;
    }// end  switch(choice)
    return value;
  }

  /**
   *
   * @param DModel
   * @param vectorOfEvents
   */
  public void build(){

    Vector eventRescList=buildEventsVector();
    for(int i=0; i< 2; i++)
      compileStudents(eventRescList);
    //compileStudents(eventRescList);
    /*Vector secondVec= new Vector();
    for (int j=eventRescList.size()-1; j>=0; j--)
      secondVec.add(eventRescList.get(j));
    compileStudents(secondVec);*/
    //}

  }

  /**
   *
   * @param DModel
   * @param vectorOfEvents
   */
  private void compileStudents(Vector eventRescList){
    _eventsRescList= eventRescList;//buildEventsVector();
    for(int i=0; i< _eventsRescList.size(); i++){
      Vector allConvexGroups= new Vector(1);
      String actID= DXToolsMethods.getToken(_eventsRescList.get(i).toString(),".",0);
      String typeID= DXToolsMethods.getToken(_eventsRescList.get(i).toString(),".",1);
      Type type= _dm.getSetOfActivities().getType(actID,typeID);
      Vector studentRegistered= buildStudentsRegistered(actID,typeID);
      for (int j=0; j< type.getSetOfSections().size(); j++){
        Resource section= type.getSetOfSections().getResourceAt(j);
        //make it for each section of an activity type
        SetOfResources associatesEvents= buildOtherAssociatesEvents(actID,typeID,section.getID());
        SetOfResources convGroup= buildConvexGroup(associatesEvents,studentRegistered);
        allConvexGroups.add(convGroup);
        //
      }// end
      if(DConst.USER_TEST_ACTIV){
        RefinedStudMixAlgo newAlgo = new RefinedStudMixAlgo(_dm,getAcceptableVariation());
        newAlgo.build (actID,typeID,allConvexGroups);
      }else{
        setStudentsInGroup(actID,typeID,allConvexGroups); //old algo ysyam
      }
    }//end for(int i=0; i< _eventsRescList.size(); i++)
    _dm.getConditionsTest().setMatrixBuilded(false,false);
    _dm.getTTStructure().getCurrentCycle().getAttributsToDisplay(_dm.getTTStructure().getPeriodLenght());
    _dm.sendEvent(null);
    //System.out.println("Mixing type: "+_mixingType);// debug
  }

  /**
   * return events list to use by the algorithm. these events is all events place
   * in ttstructure
   * @return
   */
  private Vector buildEventsVector(){
    Vector vect= new Vector(1);
    for (int i=0; i< _dm.getSetOfEvents().size(); i++){
      Resource rescEvent= _dm.getSetOfEvents().getResourceAt(i);
      if(((EventAttach)rescEvent.getAttach()).isPlaceInAPeriod()){
        String actID= DXToolsMethods.getToken(rescEvent.getID(),".",0);
        String typeID= DXToolsMethods.getToken(rescEvent.getID(),".",1);
        Type type= _dm.getSetOfActivities().getType(actID,typeID);
        String str= actID+"."+typeID+".";
        if( (type.getSetOfSections().size()>1) && (!vect.contains(str)) )
          vect.add(str);
      }
    }// end for (int i=0; i< _dm.getSetOfEvents().size();
    return vect;
  }

  /**
   * build the list of all events wich are place in the same periods that the
   * current activity
   * @param String activityID
   * @param String typeID
   * @return
   */
  private SetOfResources buildOtherAssociatesEvents(String activityID, String typeID, String sectionID){
    SetOfResources assEvents= new SetOfResources(1);
    Type type= _dm.getSetOfActivities().getType(activityID,typeID);
    //for (int i=0; i< type.getSetOfSections().size(); i++){
    Resource section= type.getSection(sectionID);
    for(int j=0; j< ((Section)section.getAttach()).getSetOfUnities().size(); j++){
      Resource unity= ((Section)section.getAttach()).getSetOfUnities().getResourceAt(j);
      String eventID=activityID+"."+typeID+"."+section.getID()+"."+unity.getID()+".";
      EventAttach event= (EventAttach)_dm.getSetOfEvents().getResource(eventID).getAttach();
      if(event.isPlaceInAPeriod()){
        int duration = event.getDuration()/_dm.getTTStructure().getPeriodLenght();
        long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
        long seqKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",1));
        long perKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",2));
        for(int k=0; k< duration; k++){
          Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByKey(dayKey,seqKey,perKey+k);
          for (int l=0; l< period.getEventsInPeriod().size(); l++){
            String inPeriodEventID=period.getEventsInPeriod().getResourceAt(l).getID();
            if(!eventID.equalsIgnoreCase(inPeriodEventID)){
              Resource assEvent= assEvents.getResource(inPeriodEventID);
              if(assEvent!=null){
                ((DXValue)assEvent.getAttach()).setIntValue(((DXValue)assEvent.getAttach()).getIntValue()+1);
              }else{// end if(assEvent!=null)
                DXValue value= new DXValue();
                value.setIntValue(1);
                assEvents.addResource(new Resource(period.getEventsInPeriod().getResourceAt(l).getID(),value),1);
              }// end else if(assEvent!=null)
            }// end if(!eventID.equalsIgnoreCase(inPeriodEventID))
          }// end for (int l=0; l< period.getEventsInPeriod().size(); l++)
        }// end for(int k=0; k< event.getDuration(); k++)
      }// end if(event.isPlaceInAPeriod())
    }// end for(int j=0; j< section.getSetOfUnities().size(); j++)
    //}// end for (int i=0; i< type.getSetOfSections().size(); i++)
    return assEvents;
  }

  /**
   * get the liste of all students registered in the activity who are not fixe
   * in a group
   * @param activityID
   * @return
   */
  private Vector buildStudentsRegistered(String activityID, String typeID){
    Vector studentR= new Vector(1);
    Activity activity= (Activity)_dm.getSetOfActivities().getResource(activityID).getAttach();
    Vector studentList= activity.getStudentRegistered();
    for (int i=0; i< studentList.size(); i++){
      long studentKey= Long.parseLong(studentList.get(i).toString());
      StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(studentKey).getAttach();
      int group= student.getGroup(activityID+typeID);
      if(!student.isFixedInGroup(activityID+typeID,group)){
        studentR.add(Long.toString(studentKey));
        student.setInGroup(activityID+typeID,-1,false);
      }// end if(student.isFixedInGroup(activityID+typeID,group))

    }// end for (int i=0; i< student.size(); i++)
    return studentR;
  }

  /**
   * build all convex groups of students
   * @param String the current activity
   * @param associatesEvents
   * @param maxRepeats
   * @param studentsReg
   * @return
   */
  private SetOfResources buildConvexGroup(SetOfResources associatesEvents, Vector studentsReg){
    /**
     * contains a ressource where the key is the student key and the ID is the
     * number of potential conflicts of the student
     */
    SetOfResources convexGroup= new SetOfResources(1);//
    for (int i=0; i< studentsReg.size(); i++){
      long studentKey= Long.parseLong(studentsReg.get(i).toString());
      StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(studentKey).getAttach();
      int nbOfPotentialConf=0;
      for (int j=0; j< student.getCoursesList().size(); j++){
        String actID= student.getCoursesList().getResourceAt(j).getID();
        String typeID= actID.substring(SetOfActivities._COURSENAMELENGTH,SetOfActivities._COURSENAMELENGTH+1);
        actID=actID.substring(0,SetOfActivities._COURSENAMELENGTH);
        int group= student.getGroup(actID+typeID);
        Vector groupList= new Vector(1);
        if((group>0) ){
          groupList.add(DXTools.STIConvertGroup(group));
        }// end if((group>0) )

        for(int k=0; k< groupList.size(); k++){
          String sectionID= groupList.get(k).toString().trim();
          Section section= _dm.getSetOfActivities().getSection(actID,typeID,sectionID);
          if(section!=null){
            for(int l=0; l< section.getSetOfUnities().size(); l++){
              String eventID= actID+"."+typeID+"."+sectionID+"."+
                      section.getSetOfUnities().getResourceAt(l).getID()+".";
              Resource assEvent= associatesEvents.getResource(eventID);
              if(assEvent!=null){
                nbOfPotentialConf+= ((DXValue)assEvent.getAttach()).getIntValue();
              }// end if(assEvent!=null)
            }// end for(int l=0; l< section.getSetOfUnities().size(); l++)
          }//end if(section!=null)
        }// end for(int k=0; k< groupList.size(); k++)
      }// end for (int j=0; j< student.getCoursesList().size(); j++)
      convexGroup.setCurrentKey(studentKey);
      convexGroup.addResource(new Resource(Integer.toString(nbOfPotentialConf),new DXValue()),0);
    }//end for (int i=0; i< studentsReg.size(); i++)
    convexGroup.sortSetOfResourcesByKey();
    return convexGroup;
  }

  /**
   *
   * @param convGroup
   * @return
   */
  private String writeConvexGroups(Vector convGroups){
    String str=DConst.SEPARATOR;
    str+=DConst.CR_LF;
    for(int i=0; i< convGroups.size(); i++){
      SetOfResources group = (SetOfResources)convGroups.get(i);
      group.sortSetOfResourcesByKey();
      for (int j=0; j< group.size(); j++){
        String mat= "0000"+group.getResourceAt(j).getKey();
        str+= group.getResourceAt(j).getID()+ " --> "+ mat.substring(mat.length()-8,mat.length()) +DConst.CR_LF;
      }// end for (int j=0; j< group.size(); j++)
      str+=DConst.SEPARATOR;
      str+=DConst.CR_LF;
    }// end for(int i=0; i< convGroup.size(); i++)

    return str;
  }


  /**
   *
   * @param activityID
   * @param typeID
   * @param allConvGroup
   */
  private void setStudentsInGroup(String activityID, String typeID, Vector allConvGroup){
    DXValue value= new DXValue();
    value.setIntValue(0);
    DXValue size;
    Vector sizeOfGroups= new Vector();
    Type type= _dm.getSetOfActivities().getType(activityID,typeID);
    for(int i=0; i< allConvGroup.size(); i++){
      size= new DXValue();
      int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(i).getID());//int group= i+1;
      size.setIntValue(_dm.getSetOfStudents().getStudentsByGroup(activityID,typeID,group).size());
      //size.setIntValue(0);
      sizeOfGroups.add(size);
    }
    Resource newStudentGroup= getStudent(allConvGroup,value, sizeOfGroups);
    while(newStudentGroup!=null){
      StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(newStudentGroup.getKey()).getAttach();
      int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(
          ((DXValue)newStudentGroup.getAttach()).getIntValue()).getID());//int group= i+1;
      //student.setInGroup(activityID+typeID, ((DXValue)newStudentGroup.getAttach()).getIntValue(),false);
      student.setInGroup(activityID+typeID, group,false);
      newStudentGroup= getStudent(allConvGroup,value, sizeOfGroups);
    }
  }


  /**
   * get the student from allConvexGroup depending of the mixingType
   * @return
   */
  private Resource getStudent(Vector allConvGroup, DXValue currentConvGroup, Vector sizeOfGroups){
    Resource resc=null;
    if(quickSolution(allConvGroup))
      resc=giveBestGroup(resc, allConvGroup,  currentConvGroup,  sizeOfGroups,1);
    else
      resc=giveBestGroup(resc, allConvGroup,  currentConvGroup,  sizeOfGroups,getAcceptableVariation());
    return resc;
  }





  /**
   * return true if no student is in conflict
   * @return
   */
  private boolean quickSolution(Vector allConvGroup){
    for (int i=0; i< allConvGroup.size(); i++){
      SetOfResources group= (SetOfResources)allConvGroup.get(i);
      for (int j=0; j< group.size(); j++){
        if(Integer.parseInt(group.getResourceAt(j).getID())!=0)
          return false;
      }// end for (int j=0; j< group.size(); j++)
    }// end for (int i=0; i< allConvGroup.size(); i++)
    return true;
  }


  /**
   *
   * @param resc
   * @param allConvGroup
   * @param currentConvGroup
   * @param sizeOfGroups
   */
  private Resource giveBestGroup(Resource resc, Vector allConvGroup,
                                 DXValue currentConvGroup, Vector sizeOfGroups, int acceptableVariation){
    Vector includeGroupsList=new Vector();
    int smallGroup= RefinedStudMixAlgo.getSmallerGroupIndex(sizeOfGroups);
    for(int i=0; i< allConvGroup.size(); i++){
      if ((((DXValue)sizeOfGroups.get(i)).getIntValue()-
           ((DXValue)sizeOfGroups.get(smallGroup)).getIntValue())< acceptableVariation)
      includeGroupsList.add(new Integer(i));
    }// end for(int i=0; i< allConvGroup.size(); i++){

    int bGroup=((Integer)includeGroupsList.get(0)).intValue();
    if(((SetOfResources)allConvGroup.get( bGroup)).size()>0){
      //System.out.println("bGroup: "+bGroup+" ((SetOfResources)allConvGroup.get( bGroup)) size: "+((SetOfResources)allConvGroup.get( bGroup)).size() );//debug
      int bConf=Integer.parseInt(((SetOfResources)allConvGroup.get(
          bGroup)).getResourceAt(0).getID());


      for(int i=1; i< includeGroupsList.size(); i++){
        if(bConf>Integer.parseInt(((SetOfResources)allConvGroup.get(
            ((Integer)includeGroupsList.get(i)).intValue())).getResourceAt(0).getID())){
        bConf=Integer.parseInt(((SetOfResources)allConvGroup.get(
        ((Integer)includeGroupsList.get(i)).intValue())).getResourceAt(0).getID());
        bGroup=((Integer)includeGroupsList.get(i)).intValue();
      }// end if(bConf>=Integer.parseInt(((SetOfResources
      }// end for(int i=0; i< includeGroupsList.size(); i++){
      //currentConvGroup.setIntValue(bGroup);
      ((DXValue)sizeOfGroups.get(bGroup)).setIntValue(
          ((DXValue)sizeOfGroups.get(bGroup)).getIntValue()+1);

      resc= ((SetOfResources)allConvGroup.get(bGroup)).getResourceAt(0);
      ((DXValue)resc.getAttach()).setIntValue(bGroup);
      for (int i=0; i< allConvGroup.size(); i++)
        ((SetOfResources)allConvGroup.get(i)).removeResource(resc.getKey());
      currentConvGroup.setIntValue(bGroup);
    }// end if(((SetOfResources)allConvGroup.get( bGroup)).size()>0)
    return resc;
  }

}// end class
