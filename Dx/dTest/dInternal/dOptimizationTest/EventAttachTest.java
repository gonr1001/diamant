package dTest.dInternal.dConditionsTest;


import junit.framework.*;
import dInternal.dConditionsTest.EventAttach;
import java.util.Vector;

 public class EventAttachTest extends TestCase {

   public EventAttachTest(String name) {
     super(name);

   }

   public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(EventAttachTest.class);
   } // end suite



   public void test_Eventattach(){
     assertEquals("No test is required: : assertEquals", true, true);
   }


 }
