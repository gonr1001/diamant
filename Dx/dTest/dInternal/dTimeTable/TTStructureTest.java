package dTest.dInternal.dTimeTable;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam
 * @version 1.0
 */

import junit.framework.*;
import java.io.File;
import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;

import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.Period;

import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;
import xml.OutPut.*;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

    public class TTStructureTest extends TestCase {
      String path;
      public TTStructureTest(String name) {
        super(name);
        path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"TTxmlFiles"+File.separator;
      }

      public static Test suite() {
       // the type safe way is in SimpleTest
       // the dynamic way :
       return new TestSuite(TTStructureTest.class);
      } // end suite

      /**
      * test that load the timetable from an xml file
      * */
     public void test_loadTTStructure(){
        TTStructure tts= new TTStructure();
        tts.loadTTStructure(path+"StandardTTC.xml");
        assertEquals("test_CreateStandardTT : assertEquals 1 (number of cycles):",3,tts.getSetOfCycles().getSetOfCycles().size());
     }

      /**
      * test that creates the standard xml timetable file
      * */
     public void test_CreateStandardTT(){
       TTStructure tts= new TTStructure();
       tts.CreateStandardTT(path+"newStandardTT.xml",5,5);
       tts.loadTTStructure(path+"newStandardTT.xml");
       assertEquals("test_CreateStandardTT : assertEquals 1 (number of cycles):",5,tts.getSetOfCycles().getSetOfCycles().size());
       assertEquals("test_CreateStandardTT : assertEquals 2 (PeriodLenght):",60,tts.getSetOfCycles().getPeriodLenght());
     }

     /**
      * test that give the first period of the time table
      * */
     public void test_getFirstPeriod(){
       TTStructure tts= new TTStructure();
       tts.loadTTStructure(path+"StandardTTC.xml");
       Period per= tts.getFirstPeriod(tts.getCurrentCycle());
       assertEquals("test_getFirstPeriod : assertEquals 1 (begin hour):",8,per.getBeginHour()[0]);
       assertEquals("test_getFirstPeriod : assertEquals 2 (begin minute):",15,per.getBeginHour()[1]);
       assertEquals("test_getFirstPeriod : assertEquals 3 (priority):",0,per.getPriority());
     }

     /**
      * test that give the last period of the time table
      * */
     public void test_getLastPeriod(){
       TTStructure tts= new TTStructure();
       tts.loadTTStructure(path+"StandardTTC.xml");
       Period per= tts.getLastPeriod(tts.getCurrentCycle());
       assertEquals("test_getLastPeriod : assertEquals 1 (begin hour):",21,per.getBeginHour()[0]);
       assertEquals("test_getLastPeriod : assertEquals 2 (begin minute):",0,per.getBeginHour()[1]);
       assertEquals("test_getLastPeriod : assertEquals 3 (priority):",1,per.getPriority());
     }

     /**
      * test that gives the maximal number of periods by day in a cycle
      * */

     public void test_getMaxNumberOfPeriodsADay(){
       TTStructure tts= new TTStructure();
       tts.loadTTStructure(path+"nonUniformTT.xml");
       int maxPer= tts.getMaxNumberOfPeriodsADay(tts.getCurrentCycle());
       assertEquals("test_getMaxNumberOfPeriodsADay : assertEquals 1 :",12,maxPer);
     }

     /**
      * test that gives the maximal number sequences in a cycle
      * */

     public void test_getMaxNumberOfSeqs(){
       TTStructure tts= new TTStructure();
       tts.loadTTStructure(path+"nonUniformTT.xml");
       int maxSeq= tts.getMaxNumberOfSeqs(tts.getCurrentCycle());
       assertEquals("test_getMaxNumberOfSequences : assertEquals 1 :",3,maxSeq);
     }

     /**
     * test that give a period of the time table
     * */
    public void test_getPeriod(){
      TTStructure tts= new TTStructure();
      tts.loadTTStructure(path+"StandardTTC.xml");
      Period per= tts.getPeriod(tts.getCurrentCycle(),4,2,1);
      assertEquals("test_getPeriod : assertEquals 1 (begin hour):",20,per.getBeginHour()[0]);
      assertEquals("test_getPeriod : assertEquals 2 (begin minute):",0,per.getBeginHour()[1]);
      assertEquals("test_getPeriod : assertEquals 3 (priority):",1,per.getPriority());
     }

     /**
     * test that save the ttstructure in a xml file
     * */
    public void test_saveTTStructure(){
      TTStructure tts= new TTStructure();
      tts.loadTTStructure(path+"StandardTTC.xml");
      tts.saveTTStructure(path+"SaveStandardTTC.xml");
      TTStructure newtts= new TTStructure();
      newtts.loadTTStructure(path+"SaveStandardTTC.xml");
      Period lastPer= newtts.getLastPeriod(tts.getCurrentCycle());
      assertEquals("test_saveTTStructure : assertEquals 1 (number of cycles):",3,newtts.getSetOfCycles().getSetOfCycles().size());
      assertEquals("test_saveTTStructure : assertEquals 2 (PeriodLenght):",60,newtts.getSetOfCycles().getPeriodLenght());
      assertEquals("test_saveTTStructure : assertEquals 3 (begin hour):",21,lastPer.getBeginHour()[0]);
      assertEquals("test_saveTTStructure : assertEquals 4 (begin minute):",0,lastPer.getBeginHour()[1]);
      assertEquals("test_saveTTStructure : assertEquals 5 (priority):",1,lastPer.getPriority());
    }



}