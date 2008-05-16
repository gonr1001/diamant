/**
 * 
 * Title: RefinedStudMixAlgo $Revision: 1.14 $ $Date: 2006-06-21 14:57:02 $
 * Description: RefinedStudMixAlgo  - Algorithme raffiné de formation de groupe
 * 
 * 
 * Copyright (c) 2001 by rgr. All rights reserved.
 * 
 * 
 * This software is the confidential and proprietary information of rgr-fdl.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with rgr-fdl.
 * 
 * @version $Version$
 * @author $Author: gonzrubi $
 * @since JDK1.3
 */

package dInternal.dOptimization;

import java.util.Vector;

import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.DValue;
import dInternal.dData.StandardCollection;
import dInternal.dData.dStudents.Student;
import dInternal.DResource;
import dInternal.DSetOfResources;

import dInternal.dData.dActivities.Type;


public class RefinedStudMixAlgo{

  private DModel _dm;
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
    DSetOfResources sumOfConvex= buildSumOfConvexGroups(allConvexGroups);
    mixStudentsInGroup(actID,typeID,allConvexGroups,sumOfConvex);
  }


  /**
  *
  * @param activityID
  * @param typeID
  * @param allConvGroup
  */
 private void mixStudentsInGroup(String activityID, String typeID, Vector allConvGroups, DSetOfResources sumList){
   Vector <DValue>sizeOfGroups= new Vector<DValue>();
   Type type= _dm.getSetOfActivities().getType(activityID,typeID);
   //System.out.println("*****************Activity: "+ activityID+typeID);// debug
   for(int i=0; i< type.getSetOfSections().size(); i++){
     DValue size= new DValue();
     int group= DxTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(i).getID());//int group= i+1;
     size.setIntValue(_dm.getSetOfStudents().getStudentsByGroup(activityID,typeID,group).size());
     sizeOfGroups.add(size);
    }// end for(int i=0; i< type.getSetOfSections().size(); i++)
    Vector <String>removedStudents= new Vector<String>();
    Vector<String> placedStudents = new Vector<String>();
    Vector <String> students= buildVectorOfKeys(sumList);
    int level=0;
    while(students.size()>0){
      boolean studentAffected= false;
      for (int i=0; i< students.size(); i++){
        long studentKey= Long.parseLong(students.get(students.size()-1));
        int groupIndex= getGroupIndex(studentKey, allConvGroups,sizeOfGroups,level);
        if(groupIndex!=-1){
          sizeOfGroups= setStudentInAGroup(studentKey,groupIndex,sizeOfGroups,activityID,typeID);
          students.remove(String.valueOf(studentKey));// .removeResource(studentKey);
          placedStudents.add(String.valueOf(studentKey));
          studentAffected=true;
          //System.out.println("+++ Student: "+studentKey+" added +++");//debug
          break;
        } //else{// end if(groupIndex!=-1)
          students.remove(String.valueOf(studentKey));
          removedStudents.add(String.valueOf(studentKey));
        //}// end else if(groupIndex!=-1)
      }// end for (int i=0; i< studentRegistered.size(); i++)
      if((students.size()==0) || studentAffected){
        if(!studentAffected){
          level++;
          //System.out.println("--- End iteration Level: "+level+" ---");//debug
        }
        students.trimToSize();
        for(int i=removedStudents.size()-1; i>=0; i--){
          String StudKey= removedStudents.get(i);
          students.add(StudKey);
        }// end for(int i=0; i< removeStudents.size(); i++)
        removedStudents.removeAllElements();
        removedStudents.trimToSize();
      }// end if(sumList==0)
    }// end while(studentRegistered.size()>0)
 }

 /**
  *
  * @param studentKey
  * @param groupIndex
  * @param sizeOfGroups
  * @param activityID
  * @param typeID
  * @return
  */
 private Vector <DValue> setStudentInAGroup(long studentKey, int groupIndex, Vector <DValue>sizeOfGroups,
                                 String activityID, String typeID){
   Type type= _dm.getSetOfActivities().getType(activityID,typeID);
   //StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(studentKey).getAttach();   
   Student student= _dm.getSetOfStudents().getStudent(studentKey);
   int group= DxTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(
       groupIndex).getID());//int group= i+1;
   DValue value= sizeOfGroups.get(groupIndex);
   value.setIntValue(value.getIntValue()+1);
   student.setInGroup(activityID+typeID, group,false);
   return sizeOfGroups;
 }

 /**
  *
  * @param studentRegistered
  * @param allConvexGroups each convexgroup is sort by key (matricule)
  * @return
  */
 private DSetOfResources buildSumOfConvexGroups( Vector allConvexGroups){
   // sort the the convexgroup by key (matricule)
   for(int j=0; j< allConvexGroups.size(); j++)
     ((DSetOfResources)allConvexGroups.get(j)).sortSetOfResourcesByKey();

   DSetOfResources convexSum= new StandardCollection();
   DSetOfResources studentRegistered = (DSetOfResources)allConvexGroups.get(0);
   for(int i=0; i< studentRegistered.size(); i++){
     int sum=0;
     for(int j=0; j< allConvexGroups.size(); j++){
       String conf=((DSetOfResources)allConvexGroups.get(j)).getResourceAt(i).getID();
       sum+= Integer.parseInt(conf);
     }// end for(int j=0; j< studentRegistered.size(); j++)
      String confSum ="0000"+Integer.toString(sum);
      confSum= confSum.substring(confSum.length()-4,confSum.length());
     convexSum.setCurrentKey(studentRegistered.getResourceAt(i).getKey());
     convexSum.addResource(new DResource(confSum,new DValue()),0);
   }// end for(int i=0; i< allConvexGroups.size(); i++)
   convexSum.sortSetOfResourcesByID();
   return convexSum;
 }


 /**
  * build a vector containing keys of resources
  * @param sor
  * @return
  */
 private Vector <String> buildVectorOfKeys(DSetOfResources sor){
   Vector<String> vOfKeys = new Vector<String>();
   for (int i=0; i< sor.size(); i++){
     long key = sor.getResourceAt(i).getKey();
     vOfKeys.add(String.valueOf(key));
   }

   return vOfKeys;
 }

 /**
  *
  * @param studentKey
  * @param allConvexGrourps
  * @param eligibleGroups
  * @return
  */
 private int getGroupIndex (long studentKey, Vector allConvexGroups, Vector sizeOfGroups, int level){
   DSetOfResources setOfConflicts= new StandardCollection();
   for(int i=0; i< allConvexGroups.size(); i++){
     DSetOfResources group = (DSetOfResources)allConvexGroups.get(i);
     String conf="0000"+group.getResource(studentKey).getID();
     conf= conf.substring(conf.length()-4,conf.length());
     setOfConflicts.addResource(new DResource(conf,new DValue()),0);
   }// end for(int i=0; i< allConvexGroups.size(); i++)
   setOfConflicts.sortSetOfResourcesByID();
   Vector bestGroupsIndex= getBestGroupsIndex(setOfConflicts,level);
   DSetOfResources eligibleGroups= new StandardCollection();
   for (int i=0; i< setOfConflicts.size(); i++){
     int groupIndex= (int)setOfConflicts.getResourceAt(i).getKey()-1;
     if(isEligibleGroups(sizeOfGroups, groupIndex,ACCEPTABLEVARIATION)){
       int sizeIntValue= ((DValue)sizeOfGroups.get(i)).getIntValue();
       String size= "00000"+Integer.toString(sizeIntValue);
       size= size.substring(size.length()-5,size.length());
       DResource resc= new DResource(size,new DValue());
       eligibleGroups.setCurrentKey(setOfConflicts.getResourceAt(i).getKey());
       eligibleGroups.addResource(resc,0);
     }// end if(isEligibleGroups(sizeOfGroups, groupIndex,ACCEPTABLEVARIATION))
   }// end for (int i=0; i< setOfConflicts.size(); i++)
   eligibleGroups.sortSetOfResourcesByID();
   for(int i=0; i< eligibleGroups.size(); i++){
     long eligibleGroupIndex= eligibleGroups.getResourceAt(i).getKey()-1;
     for (int j=0; j< bestGroupsIndex.size(); j++){
       long bestGroupIndex = ((DValue)bestGroupsIndex.get(j)).getIntValue();
       if(eligibleGroupIndex==bestGroupIndex)
         return (int)bestGroupIndex;
     }// end for (int j=0; j< bestGroupsIndex.size(); j++)
   }// end for(int i=0; i< eligibleGroups.size(); i++)
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
    DValue smallerGroup= (DValue)sizeOfGroups.get(smalGroupIndex);
   if(groupIndex< sizeOfGroups.size()){
     DValue currentGroup = ((DValue)sizeOfGroups.get(groupIndex));
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
  private Vector getBestGroupsIndex(DSetOfResources setOfConflicts, int level){
    Vector <DValue> bestGroupsIndex= new Vector<DValue>();
    setOfConflicts.sortSetOfResourcesByID();
    int iteration=0;
    String bestNumOfConf= setOfConflicts.getResourceAt(0).getID();
    // search level
    for (int i=0; i< setOfConflicts.size(); i++){
      if(level== iteration)
        break;
      String numOfC= setOfConflicts.getResourceAt(i).getID();
      if(!numOfC.equalsIgnoreCase(bestNumOfConf)){
        bestNumOfConf= numOfC;
        iteration++;
      }// end if(numOfC.equalsIgnoreCase(bestNumOfConf))
    }// end for (int i=0; i< setOfConflicts.size(); i++)

    int bestNumberOfConflicts=Integer.parseInt(bestNumOfConf);
    int groupIndex= (int)setOfConflicts.getResourceAt(0).getKey()-1;
    bestGroupsIndex.add(new DValue(groupIndex, null));
    for (int i=1; i< setOfConflicts.size(); i++){
      String NumOfConf = setOfConflicts.getResourceAt(i).getID();
      int NumberOfConflicts = Integer.parseInt(NumOfConf);
      groupIndex= (int)setOfConflicts.getResourceAt(i).getKey()-1;
      if(NumberOfConflicts <= bestNumberOfConflicts){
        bestGroupsIndex.add(new DValue(groupIndex, null));
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
    int min= ((DValue)sizeOfGroups.get(0)).getIntValue();
    for (int i=1; i< sizeOfGroups.size(); i++){
      if(min> ((DValue)sizeOfGroups.get(i)).getIntValue()){
        min= ((DValue)sizeOfGroups.get(i)).getIntValue();
        group=i;
      }
    }
    return group;
  }
}