package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;
import xml.OutPut.BuildXMLElement;
import xml.OutPut.writeFile;
import xml.Const.Tag;
import org.w3c.dom.*;
//import javax.xml.parsers.FactoryConfigurationError;

public class TTStructure {
  private SetOfCycles _setOfCycles;
  //DXTimeTable tag
  public static final String ITEM2= "DXTimeTable";
  //subtag
  public static final String [] ITEM2_subTag={"TTcycle","TTdays","TTday",
    "TTsequences","TTsequence","TTperiods","TTperiod"};
  public static final String [] ITEM2_subConst={"cycleID","pLength","dayID",
    "sequenceID","priority","BeginTime","EndTime"};

  private String _str;
  private int _col;
  private int _row;

  public TTStructure() {
    _col=6;
    _row= 15;
    saveStandardTT("StandardTTC.xml");

  }

 public SetOfResources getSetOfCycles() {
    return new SetOfResources(4);
 }

 public void setSetOfResources(SetOfResources setOfCycles) {
 }

public Period getPeriod(){
  return new Period();
}

 public String toWrite() {
    return "";
 }

 public int rgetColumn(){
   return _col;
 }

 public int rgetRow(){
  return _row;
 }


 public int rgetBegingTime(){
   int [] b = {8, 00} ;
   return b[0];
 }
 public int rgetEndTime(){
   int [] b = {22, 0} ;
   return b[0];
 }

 public String rgetDayName(int i) {
   String [] a = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
   return a[i];
 }
 public String loadData(String  fils) {
   return new String("");
  }
  public String getError() {
    return new String("");
  }

  public void saveStandardTT(String fileName){
    BuildXMLElement wr;
    try{
      wr= new BuildXMLElement();
      Document doc= wr.getNewDocument();
      Element eltTT= wr.createElement(doc,ITEM2);
      Element eltCycle;
      Element eltDays;
      Element eltDay;
      Element eltSeqs;
      Element eltSeq;
      Element eltPers;
      //Element child0;
      //Element child1;
      for (int cyc=0; cyc<3; cyc++){
        eltCycle= wr.createElement(doc,ITEM2_subTag[0]);
        eltDays= wr.createElement(doc,ITEM2_subTag[1]);
        for (int day=0; day<5; day++){
          eltDay= wr.createElement(doc,ITEM2_subTag[2]);
          eltSeqs= wr.createElement(doc,ITEM2_subTag[3]);

          //add AM periods
          eltSeq= wr.createElement(doc,ITEM2_subTag[4]);
          eltPers= wr.createElement(doc,ITEM2_subTag[5]);
          for (int i=0; i<4; i++){
            String time= Integer.toString(8+i)+":30";
            Element child0=wr.createElement(doc,ITEM2_subConst[5],time);
            time= Integer.toString(9+i)+":30";
            Element child01=wr.createElement(doc,ITEM2_subConst[6],time);
            Element child1=wr.createElement(doc,ITEM2_subConst[4],"0");
            Element eltPer= wr.createElement(doc,ITEM2_subTag[6]);
            eltPer= wr.appendChildInElement(eltPer, child0);
            eltPer= wr.appendChildInElement(eltPer, child01);
            eltPer= wr.appendChildInElement(eltPer, child1);
            eltPers=wr.appendChildInElement(eltPers, eltPer);
          }
          Element childSeq=wr.createElement(doc,ITEM2_subConst[3],"AM");
          eltSeq= wr.appendChildInElement(eltSeq, childSeq);
          eltSeq= wr.appendChildInElement(eltSeq, eltPers);
          eltSeqs= wr.appendChildInElement(eltSeqs, eltSeq);

          //add PM periods
          eltSeq= wr.createElement(doc,ITEM2_subTag[4]);
          eltPers= wr.createElement(doc,ITEM2_subTag[5]);
          for (int i=4; i<9; i++){
            String time= Integer.toString(8+i)+":30";
            Element child0=wr.createElement(doc,ITEM2_subConst[5],time);
            time= Integer.toString(9+i)+":30";
            Element child01=wr.createElement(doc,ITEM2_subConst[6],time);
            Element child1=wr.createElement(doc,ITEM2_subConst[4],"0");
            Element eltPer= wr.createElement(doc,ITEM2_subTag[6]);
            eltPer= wr.appendChildInElement(eltPer, child0);
            eltPer= wr.appendChildInElement(eltPer, child01);
            eltPer= wr.appendChildInElement(eltPer, child1);
            eltPers=wr.appendChildInElement(eltPers, eltPer);
          }
          childSeq=wr.createElement(doc,ITEM2_subConst[3],"PM");
          eltSeq= wr.appendChildInElement(eltSeq, childSeq);
          eltSeq= wr.appendChildInElement(eltSeq, eltPers);
          eltSeqs= wr.appendChildInElement(eltSeqs, eltSeq);

          //add Evening periods
          eltSeq= wr.createElement(doc,ITEM2_subTag[4]);
          eltPers= wr.createElement(doc,ITEM2_subTag[5]);
          for (int i=0; i<3; i++){
            String time= Integer.toString(19+i)+":00";
            Element child0=wr.createElement(doc,ITEM2_subConst[5],time);
            time= Integer.toString(20+i)+":00";
            Element child01=wr.createElement(doc,ITEM2_subConst[6],time);
            Element child1=wr.createElement(doc,ITEM2_subConst[4],"1");
            Element eltPer= wr.createElement(doc,ITEM2_subTag[6]);
            eltPer= wr.appendChildInElement(eltPer, child0);
            eltPer= wr.appendChildInElement(eltPer, child01);
            eltPer= wr.appendChildInElement(eltPer, child1);
            eltPers=wr.appendChildInElement(eltPers, eltPer);
          }
          childSeq=wr.createElement(doc,ITEM2_subConst[3],"EM");
          eltSeq= wr.appendChildInElement(eltSeq, childSeq);
          eltSeq= wr.appendChildInElement(eltSeq, eltPers);
          eltSeqs= wr.appendChildInElement(eltSeqs, eltSeq);

          // add sequences in a day
          eltDay= wr.appendChildInElement(eltDay,eltSeqs);
          Element childDay=wr.createElement(doc,ITEM2_subConst[2],Integer.toString(day+1));
          eltDays= wr.appendChildInElement(eltDays, childDay);
          eltDays= wr.appendChildInElement(eltDays, eltDay);
        }// end for (day)
        Element childCycle=wr.createElement(doc,ITEM2_subConst[0],Integer.toString(cyc+1));
        eltCycle= wr.appendChildInElement(eltCycle, childCycle);
        childCycle=wr.createElement(doc,ITEM2_subConst[1],Integer.toString(60));
        eltCycle= wr.appendChildInElement(eltCycle, childCycle);
        eltCycle= wr.appendChildInElement(eltCycle, eltDays);
        eltTT= wr.appendChildInElement(eltTT, eltCycle);
      }//for (int cyc=0; cyc<3; cyc++)

      // create document and write in the file
      doc= wr.buildDOM(doc,eltTT);
      writeFile.write(doc,fileName);
    } catch(Exception e){
      System.out.println("TTStructure: "+e);//debug
    }
    /*catch(FactoryConfigurationError e){
      System.out.println("Factory: "+e);//debug
    }*/


  }

}
