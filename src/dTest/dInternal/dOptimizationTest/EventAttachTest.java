//package dTest.dInternal.dOptimizationTest;
//
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//import dInternal.DResource;
//import dInternal.DSetOfResources;
//import dInternal.dData.StandardCollection;
//import dInternal.dOptimization.EventDx;
//
//public class EventAttachTest extends TestCase {
//
//	EventDx _eventAttach;
//
//	public EventAttachTest(String name) {
//		super(name);
//		DSetOfResources sor = new StandardCollection();
//		sor.addResource(new DResource("", null), 1);
//		_eventAttach = new EventDx("1.1.2.1", sor, 52, 60, "1.1.1", 10);
//	}
//
//	public static Test suite() {
//		// the type safe way is in SimpleTest
//		// the dynamic way :
//		return new TestSuite(EventAttachTest.class);
//	} // end suite
//
//	/**
//	 *
//	 */
//	public void test_setKey_1() {
//		assertEquals("test_setKey_1 : assertEquals 1", "1.1.2.1", _eventAttach
//				.getPrincipalRescKey());
//		_eventAttach.setKey(0, "1.2.3.1");
//		assertEquals("test_setKey_1 : assertEquals 2", "1.2.3.1", _eventAttach
//				.getPrincipalRescKey());
//	}
//
//	/**
//	 *
//	 */
//	public void test_setKey_2() {
//		long a[] = _eventAttach.getInstructorKey();
//		assertEquals("test_setKey_2 : assertEquals 1", 1, a[0]);
//		_eventAttach.setKey(1, "20");
//		a = _eventAttach.getInstructorKey();
//		assertEquals("test_setKey_2 : assertEquals 2", 20, a[0]);
//	}
//
//	/**
//	 *
//	 */
//	public void test_setKey_3() {
//		assertEquals("test_setKey_3 : assertEquals 1", 52, _eventAttach
//				.getRoomKey());
//		_eventAttach.setKey(2, "40");
//		assertEquals("test_setKey_3 : assertEquals 2", 40, _eventAttach
//				.getRoomKey());
//	}
//
//	/**
//	 *
//	 */
//	public void test_setKey_4() {
//		assertEquals("test_setKey_4 : assertEquals 1", 60, _eventAttach
//				.getDuration());
//		_eventAttach.setKey(3, "120");
//		assertEquals("test_setKey_4 : assertEquals 2", 120, _eventAttach
//				.getDuration());
//	}
//
//	/**
//	 *
//	 */
//	public void test_setKey_5() {
//		assertEquals("test_setKey_5 : assertEquals 1", "1.1.1", _eventAttach
//				.getPeriodKey());
//		_eventAttach.setKey(4, "2.1.1");
//		assertEquals("test_setKey_5 : assertEquals 2", "2.1.1", _eventAttach
//				.getPeriodKey());
//	}
//
//}
