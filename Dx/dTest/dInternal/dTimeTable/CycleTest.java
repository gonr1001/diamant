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
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;

import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;
import xml.OutPut.*;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

 public class CycleTest extends TestCase {
   String path;
   public CycleTest(String name) {
     super(name);
     path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"TTxmlFiles"+File.separator;
   }

   public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(CycleTest.class);
   } // end suite



   /**
    * test the added days in a cycle
    * */
   public void test_addDays(){
     Cycle cycle= new Cycle();
     cycle.getSetOfDays().addResource(new Resource("Ma",new Day()),0);
     cycle.addDays(3);
     assertEquals("test_addDays : assertEquals 1 (day 1):", cycle.getSetOfDays().getResourceAt(0).getID(), "Ma");
     assertEquals("test_addDays : assertEquals 2 (day 2):", cycle.getSetOfDays().getResourceAt(1).getID(), "Me");
     assertEquals("test_addDays : assertEquals 3 (size setofdays):", cycle.getSetOfDays().size(), 4);
   }

   /**
    * test the removed days in a cycle
    * */
   public void test_removeDays(){
     Cycle cycle= new Cycle();
     cycle.getSetOfDays().addResource(new Resource("Ma",new Day()),0);
     cycle.addDays(4);
     cycle.removeDays(2);
     assertEquals("test_removeDays : assertEquals 1 (day 1):", cycle.getSetOfDays().getResourceAt(0).getID(), "Ma");
     assertEquals("test_removeDays : assertEquals 2 (day 3):", cycle.getSetOfDays().getResourceAt(2).getID(), "Je");
     assertEquals("test_removeDays : assertEquals 3 (size setofdays):", cycle.getSetOfDays().size(), 3);
   }

   /**
   * test that read the cycle xml tag
   * */
  public void test_readXMLtag(){
    readFile xmlFile;
    Element  item;
    Cycle cycle= new Cycle();
    try{
      xmlFile = new readFile();
      System.out.println(path+"cycle.xml");//debug
      Document  doc = xmlFile.getDocumentFile(path+"cycle.xml");
      ReadXMLElement list= new ReadXMLElement();
      item= list.getRootElement(doc);
      cycle.readXMLtag(item);
      //_setOfCycles.readXMLtag(root);
    }catch(Exception e){
      System.out.println(e);
    }
    assertEquals("test_readXMLtag : assertEquals 1 (number of days):", cycle.getNumberOfDays(), 7);
  }

  /**
  * test that write the cycle xml tag
  * */
 public void test_writeXMLtag(){
   readFile xmlFile;
   Element  item;
   Cycle cycle= new Cycle();
   Cycle newCycle= new Cycle();
   cycle.getSetOfDays().addResource(new Resource("Ma",new Day()),0);
   cycle.addDays(3);

   try{
     xmlFile = new readFile();
     //System.out.println(path+"cycle.xml");//debug
     Document  doc;// = xmlFile.getDocumentFile(path+"cycle.xml");
     BuildXMLElement wr= new BuildXMLElement();
     doc=wr.getNewDocument();
     //write xml file
     Element ttCycle= cycle.writeXMLtag(doc);
     doc= wr.buildDOM(doc,ttCycle);
     writeFile.write(doc,path+"SaveCycle.xml");

     // read xml file
     doc = xmlFile.getDocumentFile(path+"SaveCycle.xml");
     ReadXMLElement list= new ReadXMLElement();
     item= list.getRootElement(doc);
     newCycle.readXMLtag(item);
     item= list.getRootElement(doc);
     newCycle.readXMLtag(item);
     //_setOfCycles.readXMLtag(root);
   }catch(Exception e){
     System.out.println(e);
   }
   assertEquals("test_writeXMLtag : assertEquals 1 (number of days):", cycle.getNumberOfDays(), newCycle.getNumberOfDays());
 }



}