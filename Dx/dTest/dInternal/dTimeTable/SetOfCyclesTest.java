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
  import dInternal.dTimeTable.SetOfCycles;
  import dInternal.dTimeTable.Cycle;

  import xml.InPut.readFile;
  import xml.InPut.ReadXMLElement;
  import xml.OutPut.*;

  import org.w3c.dom.Element;
  import org.w3c.dom.Document;

   public class SetOfCyclesTest extends TestCase {
     String path;
     public SetOfCyclesTest(String name) {
       super(name);
       path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"TTxmlFiles"+File.separator;
     }

     public static Test suite() {
      // the type safe way is in SimpleTest
      // the dynamic way :
      return new TestSuite(SetOfCyclesTest.class);
     } // end suite


     /**
     * test that read the cycle xml tag
     * */
    public void test_readXMLtag(){
      readFile xmlFile;
      Element  item;
      SetOfCycles setOfcycle= new SetOfCycles();
      try{
        xmlFile = new readFile();
        //System.out.println(path+"StandardTTC.xml");//debug
        Document  doc = xmlFile.getDocumentFile(path+"StandardTTC.xml");
        ReadXMLElement list= new ReadXMLElement();
        item= list.getRootElement(doc);
        setOfcycle.readXMLtag(item);
        //_setOfCycles.readXMLtag(root);
      }catch(Exception e){
        System.out.println(e);
      }
      assertEquals("test_readXMLtag : assertEquals 1 (number of cycles):", setOfcycle.getSetOfCycles().size(), 3);
      assertEquals("test_readXMLtag : assertEquals 2 (period length):", setOfcycle.getPeriodLenght(), 60);
    }

    /**
    * test that write the cycle xml tag
    * */
   public void test_writeXMLtag(){
     readFile xmlFile;
     Element  item;
     SetOfCycles setOfcycle= new SetOfCycles();
     SetOfCycles newSetOfcycle= new SetOfCycles();
     //Cycle newCycle= new Cycle();
     //cycle.getSetOfDays().addResource(new Resource("Ma",new Day()),0);
     //cycle.addDays(3);
     setOfcycle.getSetOfCycles().addResource(new Resource("1",new Cycle()),0);
     setOfcycle.getSetOfCycles().addResource(new Resource("2",new Cycle()),0);
     try{
       xmlFile = new readFile();
       //System.out.println(path+"cycle.xml");//debug
       Document  doc;// = xmlFile.getDocumentFile(path+"cycle.xml");
       BuildXMLElement wr= new BuildXMLElement();
       doc=wr.getNewDocument();
       //write xml file
       Element ttCycle= setOfcycle.writeXMLtag(doc);
       doc= wr.buildDOM(doc,ttCycle);
       writeFile.write(doc,path+"SaveSetOfCycles.xml");

       // read xml file
       doc = xmlFile.getDocumentFile(path+"SaveSetOfCycles.xml");
       ReadXMLElement list= new ReadXMLElement();
       item= list.getRootElement(doc);
       newSetOfcycle.readXMLtag(item);
       //_setOfCycles.readXMLtag(root);
     }catch(Exception e){
       System.out.println(e);
     }
     assertEquals("test_writeXMLtag : assertEquals 1 (number of cycles):", setOfcycle.getSetOfCycles().size(), newSetOfcycle.getSetOfCycles().size());
     assertEquals("test_writeXMLtag : assertEquals 2 (period length):", setOfcycle.getPeriodLenght(), newSetOfcycle.getPeriodLenght());
   }



}