package dTest.dInternal.dConditionsTest;

import junit.framework.*;
import dInternal.dConditionsTest.SetOfEvents;
import dInternal.dConditionsTest.EventAttach;
import dInternal.dData.SetOfActivities;
import dInternal.dData.SetOfInstructors;
import dInternal.dTimeTable.TTStructure;
import dInternal.dData.SetOfRooms;
import dInternal.dData.Resource;
import dInternal.dData.LoadData;
import java.util.Vector;
import java.io.File;
import java.util.StringTokenizer;

public class SetOfEventsTest extends TestCase {

 private SetOfActivities _soa;
 private SetOfInstructors _soi;
 private SetOfRooms _sor;
 private TTStructure _tts;
  public SetOfEventsTest(String name) {
    super(name);
    LoadData _lData= new LoadData();
    Vector timeTable = _lData.loadProject(System.getProperty("user.dir")+ File.separator+"dataTest"+File.separator+"loadData.dia");
    _soa = (SetOfActivities)timeTable.get(4);
    _soi = (SetOfInstructors)timeTable.get(2);
    _sor = (SetOfRooms)timeTable.get(3);
    _tts = (TTStructure)timeTable.get(1);
  }

     public static Test suite() {
      // the type safe way is in SimpleTest
      // the dynamic way :
      return new TestSuite(SetOfEventsTest.class);
     } // end suite



     /**
      * test the principal key of the first event of the setofevents
      */
     public void test_build(){
       SetOfEvents soe = new SetOfEvents();
       soe.build(_tts.getCurrentCycleResource(),_soa,_soi,_sor);
       String pincKey = ((EventAttach)soe.getResourceAt(0).getAttach()).getPrincipalRescKey();
       StringTokenizer keys = new StringTokenizer(pincKey,".");

       String firstEvent = _soa.getUnityCompleteName(Long.parseLong(keys.nextToken())
           ,Long.parseLong(keys.nextToken()),Long.parseLong(keys.nextToken()),
           Long.parseLong(keys.nextToken()));
       assertEquals("test_build : assertEquals: ", "AMC6401 A1", firstEvent);
     }

     /**
      * test the instructor key of the first event of the setofevents
      */
     public void test1_build(){
       SetOfEvents soe = new SetOfEvents();
       soe.build(_tts.getCurrentCycleResource(),_soa,_soi,_sor);
       long insKey = ((EventAttach)soe.getResourceAt(0).getAttach()).getInstructorKey();
       assertEquals("test_build : assertEquals: ", "THÉRIEN, NORMAND", _soi.getResource(insKey).getID());
     }

     /**
      * test the rooms key of the first event of the setofevents
      */
     public void test2_build(){
       SetOfEvents soe = new SetOfEvents();
       soe.build(_tts.getCurrentCycleResource(),_soa,_soi,_sor);
       long roomKey = ((EventAttach)soe.getResourceAt(0).getAttach()).getRoomKey();
       assertEquals("test_build : assertEquals: ", "D73020", _sor.getResource(roomKey).getID());
     }


   }
