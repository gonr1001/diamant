package dTest.dInternal.dTimeTable;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam
 * @version 1.0
 */

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xml.InPut.ReadXMLElement;
import xml.InPut.readFile;
import xml.OutPut.BuildXMLElement;
import xml.OutPut.writeFile;
import dInternal.dDataTxt.Resource;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.TTStructure;

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
        assertEquals("test_CreateStandardTT : assertEquals 1 (number of cycles):",3,tts.getSetOfCycles().size());
     }

      /**
      * test that creates the standard xml timetable file
      * */
     public void test_CreateStandardTT(){
       TTStructure tts= new TTStructure();
       tts.CreateStandardTT(path+"newStandardTT.xml",5,5);
       tts.loadTTStructure(path+"newStandardTT.xml");
       assertEquals("test_CreateStandardTT : assertEquals 1 (number of cycles):",5,tts.getSetOfCycles().size());
       assertEquals("test_CreateStandardTT : assertEquals 2 (PeriodLenght):",60,tts.getPeriodLenght());
     }

     /**
      * test that give the first period of the time table
      * */
     public void test_getFirstPeriod(){
       TTStructure tts= new TTStructure();
       tts.loadTTStructure(path+"StandardTTC.xml");
       Period per= tts.getCurrentCycle().getFirstPeriod();
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
       Period per= tts.getCurrentCycle().getLastPeriod();
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
       int maxPer= tts.getCurrentCycle().getMaxNumberOfPeriodsADay();
       assertEquals("test_getMaxNumberOfPeriodsADay : assertEquals 1 :",12,maxPer);
     }

     /**
      * test that gives the maximal number sequences in a cycle
      * */

     public void test_getMaxNumberOfSeqs(){
       TTStructure tts= new TTStructure();
       tts.loadTTStructure(path+"nonUniformTT.xml");
       int maxSeq= tts.getCurrentCycle().getMaxNumberOfSeqs();
       assertEquals("test_getMaxNumberOfSequences : assertEquals 1 :",3,maxSeq);
     }

     /**
     * test that give a period of the time table
     * */
    public void test_getPeriod(){
      TTStructure tts= new TTStructure();
      tts.loadTTStructure(path+"StandardTTC.xml");
      Period per= tts.getCurrentCycle().getPeriodByIndex(4,2,1);
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
      Period lastPer= newtts.getCurrentCycle().getLastPeriod();
      assertEquals("test_saveTTStructure : assertEquals 1 (number of cycles):",3,newtts.getSetOfCycles().size());
      assertEquals("test_saveTTStructure : assertEquals 2 (PeriodLenght):",60,newtts.getPeriodLenght());
      assertEquals("test_saveTTStructure : assertEquals 3 (begin hour):",21,lastPer.getBeginHour()[0]);
      assertEquals("test_saveTTStructure : assertEquals 4 (begin minute):",0,lastPer.getBeginHour()[1]);
      assertEquals("test_saveTTStructure : assertEquals 5 (priority):",1,lastPer.getPriority());
    }

    /**
     * test that read the cycle xml tag
     * */
    public void test_readXMLtag(){
      readFile xmlFile;
      Element  item;
      TTStructure tts= new TTStructure();
      //SetOfCycles setOfcycle= new SetOfCycles();
      try{
        xmlFile = new readFile();
        //System.out.println(path+"StandardTTC.xml");//debug
        Document  doc = xmlFile.getDocumentFile(path+"StandardTTC.xml");
        ReadXMLElement list= new ReadXMLElement();
        item= list.getRootElement(doc);
        tts.readXMLtag(item);
        //_setOfCycles.readXMLtag(root);
      }catch(Exception e){
        System.out.println(e);
      }
      assertEquals("test_readXMLtag : assertEquals 1 (number of cycles):", tts.getSetOfCycles().size(), 3);
      assertEquals("test_readXMLtag : assertEquals 2 (period length):", tts.getPeriodLenght(), 60);
    }

    /**
    * test that write the cycle xml tag
    * */
   public void test_writeXMLtag(){
     readFile xmlFile;
     Element  item;
     TTStructure tts = new TTStructure();
     TTStructure newtts = new TTStructure();
     //SetOfCycles setOfcycle= new SetOfCycles();
     //SetOfCycles newSetOfcycle= new SetOfCycles();
     //Cycle newCycle= new Cycle();
     //cycle.getSetOfDays().addResource(new Resource("Ma",new Day()),0);
     //cycle.addDays(3);

     Cycle cycle= new Cycle();
     cycle.getSetOfDays().addResource(new Resource("Ma",new Day()),0);
     cycle.getDayByIndex(0).getSetOfSequences().addResource(new Resource("AM",new Sequence()),0);
     cycle.getDayByIndex(0).getSequence(0).getSetOfPeriods().addResource(new Resource("1",new Period()),0);
     cycle.addDays(3);

     tts.getSetOfCycles().addResource(new Resource("1",cycle),0);
     tts.getSetOfCycles().addResource(new Resource("2",cycle),0);
     try{
       xmlFile = new readFile();
       //System.out.println(path+"cycle.xml");//debug
       Document  doc;// = xmlFile.getDocumentFile(path+"cycle.xml");
       BuildXMLElement wr= new BuildXMLElement();
       doc=wr.getNewDocument();
       //write xml file
       Element ttCycle= tts.writeXMLtag(doc);
       doc= wr.buildDOM(doc,ttCycle);
       writeFile.write(doc,path+"SaveSetOfCycles.xml");

       // read xml file
       doc = xmlFile.getDocumentFile(path+"SaveSetOfCycles.xml");
       ReadXMLElement list= new ReadXMLElement();
       item= list.getRootElement(doc);
       newtts.readXMLtag(item);
       //_setOfCycles.readXMLtag(root);
     }catch(Exception e){
       System.out.println(e);
     }
     assertEquals("test_writeXMLtag : assertEquals 1 (number of cycles):", tts.getSetOfCycles().size(), newtts.getSetOfCycles().size());
     assertEquals("test_writeXMLtag : assertEquals 2 (period length):", tts.getPeriodLenght(), newtts.getPeriodLenght());
   }


}