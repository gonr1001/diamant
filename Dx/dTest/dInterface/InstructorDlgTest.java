package dTest.dInterface;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import junit.framework.*;
import dInternal.dData.InstructorAttach;
import dInternal.DModel;
import dInterface.dData.InstructorAvailabiliyDlg;
import dResources.DConst;
import dInterface.DApplication;
import javax.swing.*;
import java.util.Vector;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.RobotTestHelper;
import dInternal.dTimeTable.TTStructure;

public class InstructorDlgTest extends JFCTestCase {
  DApplication _dApplic;
  public InstructorDlgTest() {
  }

  public InstructorDlgTest(String name) {
    super(name);
//    _dApplic = new DApplication();
//    JFrame jFrame = _dApplic.getJFrame();

  }

  public static Test suite() {
  // the type safe way is in SimpleTest
  // the dynamic way :
    return new TestSuite(InstructorDlgTest.class);
  } // end suite

  protected void setUp() throws Exception {
    super.setUp();
    String [] arg = new String [0];
    helper = new RobotTestHelper();



  }
  protected void tearDown()throws Exception{
    super.tearDown();
     helper.cleanUp(this);
   // _dApplic.getJFrame().dispose();
  }

  public void testNumberOne() throws Exception{
    //JFrame jFrame = new jFrame("Hello");

    JDialog jDialog =
    new InstructorAvailabiliyDlg(null,
                             DConst.INST_ASSIGN_TD, _dApplic.getDMediator().getCurrentDoc().getDM()
                             /*new DModel(_dApplic, 0, new TTStructure())*/
                             );

   JPanel jPanel = (JPanel) helper.findComponent(JPanel.class , jDialog.getContentPane(), 2); //2 buttons?
   JButton jbutton = (JButton) helper.findComponent(JButton.class , jPanel, 0); // 2 cancel;
   helper.enterClickAndLeave(new MouseEventData(this, jbutton, 1 )); // 1 click
 helper.disposeWindow(jDialog, this);
   //flushAWT();

  }

  public void testNumberTwo() throws Exception{
  //JFrame jFrame = new jFrame("Hello");

  JDialog jDialog =
  new InstructorAvailabiliyDlg(null,
                           DConst.INST_ASSIGN_TD,_dApplic.getDMediator().getCurrentDoc().getDM()
                           /*new DModel(_dApplic, 0, new TTStructure())*/
                           );

 JPanel jPanel = (JPanel) helper.findComponent(JPanel.class , jDialog.getContentPane(), 2); //2 buttons?
 JButton jbutton = (JButton) helper.findComponent(JButton.class , jPanel, 2); // 2 cancel;
 helper.enterClickAndLeave(new MouseEventData(this, jbutton, 1 )); // 1 click
 helper.disposeWindow(jDialog, this);
   //flushAWT();

  }
}