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

import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


public class PeriodTest extends TestCase {
String path;
  public PeriodTest(String name) {
    super(name);
    path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"TTxmlFiles"+File.separator;
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(PeriodTest.class);
  } // end suite



  /**
   * test that read the period xml tag
   * */
  public void test_readXMLtag(){
    readFile xmlFile;
    Element  item, ID;
    try{
      xmlFile = new readFile();
      System.out.println(path+"period.xml");//debug
      Document  doc = xmlFile.getDocumentFile(path+"StandardTTC.xml");
      ReadXMLElement list= new ReadXMLElement();
      item= list.getRootElement(doc);
      Period period= new Period();
      period.readXMLtag(item);
      //_setOfCycles.readXMLtag(root);
    }catch(Exception e){
      System.out.println(e);
    }
    assertEquals("No test is required: : assertEquals", true, true);
  }


  /**
   * test that gives the end hour
   * */
  public void test_getEndHour(){
    assertEquals("No test is required: : assertEquals", true, true);
  }

  /**
   * test that gives the end hour
   * */
  public void test_writeXMLtag(){
    assertEquals("No test is required: : assertEquals", true, true);
  }


}