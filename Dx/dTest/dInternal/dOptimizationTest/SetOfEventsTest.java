package dTest.dInternal.dOptimizationTest;

import java.io.File;
import java.util.StringTokenizer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.dOptimization.EventAttach;
import dInternal.dOptimization.SetOfEvents;

public class SetOfEventsTest extends TestCase {

 /*private SetOfActivities _soa;
 private SetOfInstructors _soi;
 private SetOfRooms _sor;
 private TTStructure _tts;*/
 private DModel _dm;

  public SetOfEventsTest(String name) {
    super(name);
    _dm = new DModel(new DDocument(),"." + File.separator+"dataTest"+File.separator+"loadData.dia", 1);
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
       SetOfEvents soe = new SetOfEvents(_dm);
       soe.build();
       String pincKey = ((EventAttach)soe.getResourceAt(0).getAttach()).getPrincipalRescKey();
       StringTokenizer keys = new StringTokenizer(pincKey,".");
       String firstEvent =  _dm.getSetOfActivities().getUnityCompleteName(Long.parseLong(keys.nextToken())
           ,Long.parseLong(keys.nextToken()),Long.parseLong(keys.nextToken()),
           Long.parseLong(keys.nextToken()));
       assertEquals("test_build : assertEquals: ", "AMC640.1.01.1.", firstEvent);
     }

     /**
      * test the instructor key of the first event of the setofevents
      */
     public void test1_build(){
       SetOfEvents soe = new SetOfEvents(_dm);
       soe.build();
       long insKey [] = ((EventAttach)soe.getResourceAt(0).getAttach()).getInstructorKey();
       //System.out.println("Event: "+soe.getResourceAt(0).getID()+" Instruc: "+((EventAttach)soe.getResourceAt(0).getAttach()).getInstructorKey());//debug
       assertEquals("test_build : assertEquals: ", "THÉRIEN, NORMAND", _dm.getSetOfInstructors().getResource(insKey[0]).getID());
     }

     /**
      * test the rooms key of the first event of the setofevents
      */
     public void test2_build(){
       SetOfEvents soe = new SetOfEvents(_dm);
       soe.build();
       long roomKey = ((EventAttach)soe.getResourceAt(0).getAttach()).getRoomKey();
       assertEquals("test_build : assertEquals: ", "D73020", _dm.getSetOfRooms().getResource(roomKey).getID());
     }


   }
