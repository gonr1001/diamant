package dTest.dInternal.dDataTxt;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam
 * @version 1.0
 */

import java.io.File;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.Preferences;
import dInternal.dDataTxt.LoadData;
import dInternal.dDataTxt.SetOfActivities;
import dInternal.dDataTxt.SetOfInstructors;
import dInternal.dDataTxt.SetOfRooms;
import dInternal.dDataTxt.SetOfStudents;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.TTStructure;



public class LoadDataTest extends TestCase {
  String path;
  Vector _timeTable;
  Preferences _preferences;

  public LoadDataTest(String name) {
    super(name);
    path =System.getProperty("user.dir")+ File.separator+"dataTest"+File.separator+"loadData.dia";
    LoadData loadData= new LoadData();
    _timeTable = loadData.loadProject(path);
    /*_preferences = new Preferences(System.getProperty("user.dir")
                               + File.separator +
                               "pref"
                               + File.separator +
                                   "pref.txt");*/
  }

  public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(LoadDataTest.class);
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
    assertEquals("test1_loadTimeTable : assertEquals3: ", 5, ((TTStructure)_timeTable.get(1)).getNumberOfActiveDays());
  }

  /**
   * test that check the setofactivities
   * */
  public void test2_loadTimeTable(){
    SetOfActivities setAct= ((SetOfActivities)_timeTable.get(4));
    assertEquals("test2_loadTimeTable : assertEquals: ", "AMC640", setAct.getResourceAt(1).getID());
  }

  /**
   * test that check the setofstudents
   * */
  public void test3_loadTimeTable(){
    SetOfStudents setStud= ((SetOfStudents)_timeTable.get(5));
    assertEquals("test3_loadTimeTable : assertEquals: ", "BERNARD D", setStud.getResourceAt(1).getID());
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
  SetOfRooms setRooms= ((SetOfRooms)_timeTable.get(3));
  assertEquals("test5_loadTimeTable : assertEquals: ", "D13016", setRooms.getResourceAt(4).getID());
  }

}