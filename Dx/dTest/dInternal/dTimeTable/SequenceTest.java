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

import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;

import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;
import xml.OutPut.*;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


public class SequenceTest extends TestCase {
String path;
  public SequenceTest(String name) {
    super(name);
    path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"TTxmlFiles"+File.separator;
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(SequenceTest.class);
  } // end suite



  /**
   * test that read the sequence xml tag
   * */
  public void test_readXMLtag(){
    readFile xmlFile;
    Element  setOfPers;
    Sequence sequence = new Sequence();
    try{
      xmlFile = new readFile();
      //System.out.println(path+"period.xml");//debug
      Document  doc = xmlFile.getDocumentFile(path+"sequence.xml");
      ReadXMLElement list= new ReadXMLElement();
      setOfPers= list.getRootElement(doc);
      sequence.readXMLtag(setOfPers);
      //_setOfCycles.readXMLtag(root);
    }catch(Exception e){
      System.out.println(e);
    }
    assertEquals("test_readXMLtag : assertEquals 1(Number of periods):", sequence.getSetOfPeriods().size(), 2);
    //assertEquals("test_readXMLtag : assertEquals 2(Minute):", sequence.getSetOfPeriods().getResourceAt(0).getID(), "1");
    //assertEquals("test_readXMLtag : assertEquals 2(Minute):", sequence.getSetOfPeriods().getResourceAt(1).getID(), "2");
    //assertEquals("test_readXMLtag : assertEquals 3(priotity):", period.getPriority(), 0);
  }


  /**
   * test to generate a clon of a sequence
   * */

  public void test_cloneSequence(){
    Sequence firstSequence = new Sequence();
    Sequence clonedSequence = new Sequence();
    SetOfResources setOfPeriods = new SetOfResources(4);

    for (int i = 0; i < 3; i++){
        setOfPeriods.addResource(new Resource(Integer.toString(i), new Period()),0);
    }
    firstSequence.setSetOfPeriods(setOfPeriods);
    clonedSequence = firstSequence.cloneSequence();

    assertEquals("test_cloneSequence : assertEquals 1 (Size of setOfResources):", firstSequence.getSetOfPeriods().size(), clonedSequence.getSetOfPeriods().size());
    assertEquals("test_cloneSequence : assertEquals 2 (ID of period 1):", firstSequence.getSetOfPeriods().getResourceAt(0).getID(), clonedSequence.getSetOfPeriods().getResourceAt(0).getID());
    assertEquals("test_cloneSequence : assertEquals 3 (ID of period 2):", firstSequence.getSetOfPeriods().getResourceAt(1).getID(), clonedSequence.getSetOfPeriods().getResourceAt(1).getID());

  }


  /**
   * test for writing a sequence XMLTag
   * */
  public void test_writeXMLtag(){
    readFile xmlFile;
    Element  eSetOfPers;
    Sequence firstSequence = new Sequence();
    Sequence savedSequence = new Sequence();
    SetOfResources setOfPeriods = new SetOfResources(4);
    try{
      xmlFile = new readFile();
      for (int i = 1; i < 5; i++){
        setOfPeriods.addResource(new Resource(Integer.toString(i), new Period()),1);
      }
      firstSequence.setSetOfPeriods(setOfPeriods);
      BuildXMLElement wr= new BuildXMLElement();
      Document  doc;
      doc = wr.getNewDocument();
      eSetOfPers = firstSequence.writeXMLtag(doc);
      doc = wr.buildDOM(doc,eSetOfPers);
      writeFile.write(doc,path+"SavedSequence.xml");

      // read xml file
      doc = xmlFile.getDocumentFile(path+"SavedSequence.xml");
      ReadXMLElement list= new ReadXMLElement();
      eSetOfPers = list.getRootElement(doc);
      savedSequence = new Sequence();
      savedSequence.readXMLtag(eSetOfPers);

    }catch(Exception e){
      System.out.println(e);
    }
    assertEquals("test_writeXMLtag : assertEquals 1 (Size of setOfPeriods):", firstSequence.getSetOfPeriods().size(), savedSequence.getSetOfPeriods().size());
    assertEquals("test_writeXMLtag : assertEquals 2 (ID of period 1):", firstSequence.getSetOfPeriods().getResourceAt(0).getID(), savedSequence.getSetOfPeriods().getResourceAt(0).getID());
    assertEquals("test_writeXMLtag : assertEquals 2 (ID of period 1):", firstSequence.getSetOfPeriods().getResourceAt(1).getID(), savedSequence.getSetOfPeriods().getResourceAt(1).getID());

  }


}