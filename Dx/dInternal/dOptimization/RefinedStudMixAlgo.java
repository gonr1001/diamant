package dInternal.dOptimization;

/**
 * Algorithme raffiné de formation de groupe
 * <p>Title: Diamant 1.5</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import java.util.Vector;

import dInterface.dUtil.DXTools;
import dInternal.DModel;
import dInternal.dDataTxt.Resource;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dDataTxt.StudentAttach;
import dInternal.dDataTxt.Type;
import dInternal.dUtil.DXValue;

public class RefinedStudMixAlgo{

  private DModel _dm;
  private Vector _eventsRescList;
  //private int _mixingType;// 0= balance student mixing,  1= balance student
  // mixing with acceptable variation, 2= optimize student mixing
  private int ACCEPTABLEVARIATION=10;

  /**
   *
   * @param DModel dm
   * @param int the mixingType 0= balance student mixing, 1= optimize student mixing
   */
  public RefinedStudMixAlgo(DModel dm,  int acceptableVariation) {
    _dm= dm;
    ACCEPTABLEVARIATION= acceptableVariation;
  }

  /**
   *
   * @param actID
   * @param typeID
   * @param allConvexGroups
   */
  public void build(String actID, String typeID,Vector allConvexGroups){
    SetOfResources sumOfConvex= buildSumOfConvexGroups(allConvexGroups);
    setStudInGroup(actID,typeID,allConvexGroups,sumOfConvex);
  }

  /**
   *
   * @param studentRegistered
   * @param allConvexGroups each convexgroup is sort by key (matricule)
   * @return
   */
  private SetOfResources buildSumOfConvexGroups( Vector allConvexGroups){
    // sort the the convexgroup by key (matricule)
    for(int j=0; j< allConvexGroups.size(); j++)
      ((SetOfResources)allConvexGroups.get(j)).sortSetOfResourcesByKey();

    SetOfResources convexSum= new SetOfResources(66);
    SetOfResources studentRegistered = (SetOfResources)allConvexGroups.get(0);
    for(int i=0; i< studentRegistered.size(); i++){
      int sum=0;
      for(int j=0; j< allConvexGroups.size(); j++){
        String conf=((SetOfResources)allConvexGroups.get(j)).getResourceAt(i).getID();
        sum+= Integer.parseInt(conf);
      }// end for(int j=0; j< studentRegistered.size(); j++)
       String confSum ="0000"+Integer.toString(sum);
       confSum= confSum.substring(confSum.length()-4,confSum.length());
      convexSum.setCurrentKey(studentRegistered.getResourceAt(i).getKey());
      convexSum.addResource(new Resource(confSum,new DXValue()),0);
    }// end for(int i=0; i< allConvexGroups.size(); i++)
    convexSum.sortSetOfResourcesByID();
    return convexSum;
  }

  /**
  *
  * @param activityID
  * @param typeID
  * @param allConvGroup
  */
 private void setStudInGroup(String activityID, String typeID, Vector allConvGroups, SetOfResources sumList){
   //SetOfResources studentRegistered = (SetOfResources)allConvGroups.get(0);
   Vector sizeOfGroups= new Vector();
   Type type= _dm.getSetOfActivities().getType(activityID,typeID);
   for(int i=0; i< type.getSetOfSections().size(); i++){
     DXValue size= new DXValue();
     int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(i).getID());//int group= i+1;
     size.setIntValue(_dm.getSetOfStudents().getStudentsByGroup(activityID,typeID,group).size());
     //size.setIntValue(0);
     sizeOfGroups.add(size);
    }// end for(int i=0; i< type.getSetOfSections().size(); i++)
    SetOfResources removeStudents= new SetOfResources(65);

    while(sumList.size()>0){
      boolean studentAffected= false;
      for (int i=0; i< sumList.size(); i++){
        //System.out.println("Student: "+ );
        long studentKey= sumList.getResourceAt(sumList.size()-1).getKey();
        System.out.println("Student: "+ studentKey);
        int groupIndex= getGroupIndex(studentKey, allConvGroups,sizeOfGroups);
        if(groupIndex!=-1){
          StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(studentKey).getAttach();
          int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(
              groupIndex).getID());//int group= i+1;
          DXValue value= (DXValue)sizeOfGroups.get(groupIndex);
          value.setIntValue(value.getIntValue()+1);
          student.setInGroup(activityID+typeID, group,false);
          sumList.removeResource(studentKey);
          studentAffected=true;
          break;
        } else{// end if(groupIndex!=-1)
          Resource resc = sumList.getResourceAt(sumList.size()-1);
          sumList.removeResource(resc.getKey());
          removeStudents.setCurrentKey(resc.getKey());
          removeStudents.addResource(resc,0);
          sumList.sortSetOfResourcesByID();
        }// end else if(groupIndex!=-1)


      }// end for (int i=0; i< studentRegistered.size(); i++)
      if((sumList.size()==0) || studentAffected){
        for(int i=0; i< removeStudents.size(); i++){
          Resource resc= removeStudents.getResourceAt(i);
          sumList.setCurrentKey(resc.getKey());
          sumList.addResource(resc,0);
        }// end for(int i=0; i< removeStudents.size(); i++)
        removeStudents.getSetOfResources().removeAllElements();
        sumList.sortSetOfResourcesByID();
        if(sumList.size()==0)
          System.out.println("--- End iteration---");//debug
      }// end if(sumList==0)

    }// end while(studentRegistered.size()>0)


 }

 /**
  *
  * @param studentKey
  * @param allConvexGrourps
  * @param eligibleGroups
  * @return
  */
 private int getGroupIndex (long studentKey, Vector allConvexGroups, Vector sizeOfGroups){
   SetOfResources setOfConflicts= new SetOfResources(99);
   Vector eligibleGroups= new Vector();
   for(int i=0; i< allConvexGroups.size(); i++){
     SetOfResources group = (SetOfResources)allConvexGroups.get(i);
     String conf="0000"+group.getResource(studentKey).getID();
     conf= conf.substring(conf.length()-4,conf.length());
     setOfConflicts.addResource(new Resource(conf,new DXValue()),0);
   }// end for(int i=0; i< allConvexGroups.size(); i++)
   setOfConflicts.sortSetOfResourcesByID();
   Vector bestGroupsIndex= getBestGroupsIndex(setOfConflicts,0);
   for (int i=0; i< setOfConflicts.size(); i++){
     int groupIndex= (int)setOfConflicts.getResourceAt(i).getKey()-1;
     if(isEligibleGroups(sizeOfGroups, groupIndex,ACCEPTABLEVARIATION)){
       eligibleGroups.add((DXValue)sizeOfGroups.get(i));
     }// end if(isEligibleGroups(sizeOfGroups, groupIndex,ACCEPTABLEVARIATION))
   }// end for (int i=0; i< setOfConflicts.size(); i++)

   for (int i=0; i< bestGroupsIndex.size(); i++){
     int groupIndex = ((DXValue)bestGroupsIndex.get(i)).getIntValue();
     if(groupIndex == getSmallerGroupIndex(eligibleGroups))
       return groupIndex;
   }// end for (int i=0; i< bestGroupsIndex.size(); i++)

   return -1;
 }

 /**
   *
   * @param sizeOfGroups
   * @param smallerGroup
   * @return
   */
 public final static boolean isEligibleGroups(Vector sizeOfGroups, int groupIndex, int acceptableVariation){
    int smalGroupIndex = getSmallerGroupIndex(sizeOfGroups);
    DXValue smallerGroup= (DXValue)sizeOfGroups.get(smalGroupIndex);
   if(groupIndex< sizeOfGroups.size()){
     DXValue currentGroup = ((DXValue)sizeOfGroups.get(groupIndex));
     if((currentGroup.getIntValue()- smallerGroup.getIntValue())<=acceptableVariation){
       return true;
     }// end if((currentGroup.getIntValue()- smallerGroup.getIntValue()
   }// end if(groupIndex< sizeOfGroups.size())

   return false;
  }

  /**
   *
   * @param setOfConflicts
   * @return
   */
  private Vector getBestGroupsIndex(SetOfResources setOfConflicts, int level){
    Vector bestGroupsIndex= new Vector();
    setOfConflicts.sortSetOfResourcesByID();
    String bestNumOfConf= setOfConflicts.getResourceAt(0).getID();
    int bestNumberOfConflicts=Integer.parseInt(bestNumOfConf);
    int groupIndex= (int)setOfConflicts.getResourceAt(0).getKey()-1;
    bestGroupsIndex.add(new DXValue(groupIndex, null));
    for (int i=1; i< setOfConflicts.size(); i++){
      String NumOfConf = setOfConflicts.getResourceAt(i).getID();
      int NumberOfConflicts = Integer.parseInt(NumOfConf);
      groupIndex= (int)setOfConflicts.getResourceAt(i).getKey()-1;
      if(NumberOfConflicts <= bestNumberOfConflicts){
        bestGroupsIndex.add(new DXValue(groupIndex, null));
      }// end if(NumberOfConflicts==bestNumberOfConflicts)
    }// end for (int i=0; i< setOfConflicts.size(); i++)
    return bestGroupsIndex;
  }

  /**
   *
   * @param sizeOfGroups
   * @return
   */
  public final static int getSmallerGroupIndex(Vector sizeOfGroups){
    int group=0;
    int min= ((DXValue)sizeOfGroups.get(0)).getIntValue();
    for (int i=1; i< sizeOfGroups.size(); i++){
      if(min> ((DXValue)sizeOfGroups.get(i)).getIntValue()){
        min= ((DXValue)sizeOfGroups.get(i)).getIntValue();
        group=i;
      }
    }
    return group;
  }
}