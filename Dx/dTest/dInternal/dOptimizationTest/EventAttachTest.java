package dTest.dInternal.dConditionsTest;


import junit.framework.*;
import dInternal.dConditionsTest.EventAttach;


 public class EventAttachTest extends TestCase {

   EventAttach _eventAttach;
   public EventAttachTest(String name) {
     super(name);
     _eventAttach= new EventAttach("1.1.2.1",1,52,60,"1.1.1");
     //String princKey, long key1, long key2, int eventDuration, String eventPeriod
   }

   public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(EventAttachTest.class);
   } // end suite



   /**
    *
    */
   public void test_setKey_1(){
     assertEquals("test_setKey_1 : assertEquals 1", "1.1.2.1", _eventAttach.getPrincipalRescKey());
     _eventAttach.setKey(0,"1.2.3.1");
     assertEquals("test_setKey_1 : assertEquals 2", "1.2.3.1", _eventAttach.getPrincipalRescKey());
   }

   /**
    *
    */
   public void test_setKey_2(){
     assertEquals("test_setKey_2 : assertEquals 1", 1, _eventAttach.getInstructorKey());
     _eventAttach.setKey(1,"20");
     assertEquals("test_setKey_2 : assertEquals 2", 20, _eventAttach.getInstructorKey());
   }

   /**
    *
    */
   public void test_setKey_3(){
     assertEquals("test_setKey_3 : assertEquals 1", 52, _eventAttach.getRoomKey());
     _eventAttach.setKey(2,"40");
     assertEquals("test_setKey_3 : assertEquals 2", 40, _eventAttach.getRoomKey());
   }

   /**
   *
   */
  public void test_setKey_4(){
    assertEquals("test_setKey_4 : assertEquals 1", 60, _eventAttach.getDuration());
    _eventAttach.setKey(3,"120");
    assertEquals("test_setKey_4 : assertEquals 2", 120, _eventAttach.getDuration());
  }

  /**
  *
  */
 public void test_setKey_5(){
   assertEquals("test_setKey_5 : assertEquals 1", "1.1.1", _eventAttach.getPeriodKey());
   _eventAttach.setKey(4,"2.1.1");
   assertEquals("test_setKey_5 : assertEquals 2", "2.1.1", _eventAttach.getPeriodKey());
  }



 }
