package dTest.dInternal.dData;

/**
*
* Title: DLoadDataTest $Revision $  $Date: 2005-07-28 21:19:51 $
* Description: 	DLoadDataTest is a class used to test the class 
* 				DLoadData
*
*
* Copyright (c) 2001 by rgr.
* All rights reserved.
*
*
* This software is the confidential and proprietary information
* of rgr. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with rgr.
*
* @version $ $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

import java.io.File;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.Preferences;
import dInternal.dData.DLoadData;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dInstructors.SetOfInstructors;
import dInternal.dData.dRooms.SetOfCategories;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.TTStructure;



public class DLoadDataTest extends TestCase {
  String _path;
  Vector _timeTable;
  Preferences _preferences;

  public DLoadDataTest(String name) {
    super(name);
    _path ="." + File.separator+"dataTest"+File.separator+"loadData.dia";
    DLoadData loadData= new DLoadData();
    _timeTable = loadData.loadTheTT(_path, "." + File.separator+"dataTest"+File.separator);
  }

  public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(DLoadDataTest.class);
  } // end suite

  /**
   * test that check the version of timetable
   * */
  public void test_loadTimeTable(){
    assertEquals("test_loadTimeTable : assertEquals: ", "1.5", (String)_timeTable.get(0));
  }

  /**
   * test that check the xml file
   * */
  public void test1_loadTimeTable(){
    Cycle cycle= ((TTStructure)_timeTable.get(1)).getCurrentCycle();
    assertEquals("test1_loadTimeTable : assertEquals1: ", 12, cycle.getMaxNumberOfPeriodsADay());
    assertEquals("test1_loadTimeTable : assertEquals2: ", 3, cycle.getMaxNumberOfSeqs());
    assertEquals("test1_loadTimeTable : assertEquals3: ", 5, TTStructure.NUMBEROFACTIVESDAYS);
  }

  /**
   * test that check the setofactivities
   * */
  public void test2_loadTimeTable(){
    SetOfActivitiesSites setSite= ((SetOfActivitiesSites)_timeTable.get(4));
    assertEquals("test2_loadTimeTable : assertEquals: ", "SHE", setSite.getResourceAt(0).getID());
    SetOfActivities setAct= (SetOfActivities) setSite.getResourceAt(0).getAttach();
    assertEquals("test2_1_loadTimeTable : assertEquals: ", "AMC640", setAct.getResourceAt(1).getID());
  }

  /**
   * test that check the setofstudents
   * */
  public void test3_loadTimeTable(){
    SetOfStuSites setSite= ((SetOfStuSites)_timeTable.get(5));
    assertEquals("test3_loadTimeTable : assertEquals: ", "SHE", setSite.getResourceAt(0).getID());
    SetOfStudents setStud= (SetOfStudents)setSite.getResourceAt(0).getAttach();
    assertEquals("test3_1_loadTimeTable : assertEquals: ", "BERNARD D", setStud.getResourceAt(1).getID());
  }

  /**
  * test that check the setofinstructors
  * */
 public void test4_loadTimeTable(){
   SetOfInstructors setIns= ((SetOfInstructors)_timeTable.get(2));
   assertEquals("test4_loadTimeTable : assertEquals: ", "THÉRIAULT, MICHÈLE", setIns.getResourceAt(2).getID());
  }

  /**
 * test that check the setofrooms
 * */
public void test5_loadTimeTable(){
  SetOfSites setSite= ((SetOfSites)_timeTable.get(3));
  assertEquals("test5_loadTimeTable : assertEquals: ", "SHE", setSite.getResourceAt(0).getID());
  SetOfCategories setCat = ((SetOfCategories)setSite.getResourceAt(0).getAttach());
  assertEquals("test5_1_loadTimeTable : assertEquals: ", "CAT 1", setCat.getResourceAt(0).getID());
  SetOfRooms setRooms= ((SetOfRooms)setCat.getResourceAt(0).getAttach());
  assertEquals("test5_2_loadTimeTable : assertEquals: ", "D13016", setRooms.getResourceAt(4).getID());
  }

}