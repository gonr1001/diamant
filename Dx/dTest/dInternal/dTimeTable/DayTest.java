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
import dInternal.dData.Activity;
import java.util.Vector;
import java.io.File;

import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;

import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.Period;

import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;
import xml.OutPut.*;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


public class DayTest extends TestCase {
String path;
  public DayTest(String name) {
    super(name);
    path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"TTxmlFiles"+File.separator;
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DayTest.class);
  } // end suite


  /**
   * test to read the day xml tag
   * */
  public void test_readXMLtag(){
    readFile xmlFile;
    Element  eDay;
    Day day= new Day();
    try{
      xmlFile = new readFile();
      Document  doc = xmlFile.getDocumentFile(path+"day.xml");
      ReadXMLElement list= new ReadXMLElement();
      eDay = list.getRootElement(doc);
      day.readXMLtag(eDay);
    }catch(Exception e){
      System.out.println(e);
    }
    assertEquals("test_readXMLtag : assertEquals 1(Size of the SetOfSequences): ", 3, day.getSetOfSequences().size());
    assertEquals("test_readXMLtag : assertEquals 2(ID of the first Sequence): ", "AM", day.getSetOfSequences().getResourceAt(0).getID());
    assertEquals("test_readXMLtag : assertEquals 2(ID of the 2nd Sequence): ", "PM", day.getSetOfSequences().getResourceAt(1).getID());
    assertEquals("test_readXMLtag : assertEquals 2(ID of the 3rd Sequence): ", "EM", day.getSetOfSequences().getResourceAt(2).getID());
  }

  /**
   * test to generate a clon of a day
   * */

  public void test_cloneDay(){
    Day firstDay = new Day();
    Day clonedDay = new Day();
    SetOfResources setOfSequences = new SetOfResources(4);

    for (int i = 1; i < 4; i++){
      setOfSequences.addResource(new Resource(Integer.toString(i), new Sequence()),0);
    }
    firstDay.setSetOfSequences(setOfSequences);
    clonedDay = firstDay.cloneDay();

    assertEquals("test_cloneDay : assertEquals 1 (Size of setOfSequences): ", firstDay.getSetOfSequences().size(), clonedDay.getSetOfSequences().size());
    assertEquals("test_cloneDay : assertEquals 2 (ID of sequence 1): ", firstDay.getSetOfSequences().getResourceAt(0).getID(), firstDay.getSetOfSequences().getResourceAt(0).getID());
    assertEquals("test_cloneDay : assertEquals 3 (ID of sequence 2): ", firstDay.getSetOfSequences().getResourceAt(1).getID(), firstDay.getSetOfSequences().getResourceAt(1).getID());
    assertEquals("test_cloneDay : assertEquals 4 (ID of sequence 3): ", firstDay.getSetOfSequences().getResourceAt(2).getID(), firstDay.getSetOfSequences().getResourceAt(2).getID());

  }//end of method




  public void test_writeXMLtag(){
    readFile xmlFile;
    Element  eSetOfSequences;
    Day firstDay = new Day();
    Day savedDay = new Day();
    SetOfResources setOfSequences = new SetOfResources(4);

    try{
      xmlFile = new readFile();
      Document  doc;
      Sequence seq;
      for (int i = 1; i < 4; i++){
        seq= new Sequence();
        seq.getSetOfPeriods().addResource(new Resource("AM",new Period()),0);
        setOfSequences.addResource(new Resource(Integer.toString(i), seq),1);

      }
      firstDay.setSetOfSequences(setOfSequences);
      BuildXMLElement wr = new BuildXMLElement();
      doc = wr.getNewDocument();
      eSetOfSequences = firstDay.writeXMLtag(doc);
      doc= wr.buildDOM(doc, eSetOfSequences);
      writeFile.write(doc, path+"SavedDay.xml");

      // read xml file
      doc = xmlFile.getDocumentFile(path+"SavedDay.xml");
      ReadXMLElement list= new ReadXMLElement();
      eSetOfSequences = list.getRootElement(doc);
      savedDay = new Day();
      savedDay.readXMLtag(eSetOfSequences);

    }catch(Exception e){
      System.out.println(e);
    }

    assertEquals("test_writeXMLtag : assertEquals 1 (setOfSequences size): ", firstDay.getSetOfSequences().size(), savedDay.getSetOfSequences().size());
    assertEquals("test_writeXMLtag : assertEquals 2 (ID of sequence 1): ", firstDay.getSetOfSequences().getResourceAt(0).getID(), savedDay.getSetOfSequences().getResourceAt(0).getID());
    assertEquals("test_writeXMLtag : assertEquals 3 (ID of sequence 2): ", firstDay.getSetOfSequences().getResourceAt(1).getID(), savedDay.getSetOfSequences().getResourceAt(1).getID());
    assertEquals("test_writeXMLtag : assertEquals 4 (ID of sequence 3): ", firstDay.getSetOfSequences().getResourceAt(2).getID(), savedDay.getSetOfSequences().getResourceAt(2).getID());

  }//end of method


}//end of class