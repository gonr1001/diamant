package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;
import xml.OutPut.BuildXMLElement;
import xml.OutPut.writeFile;
import xml.Const.Tag;
import org.w3c.dom.*;
//import javax.xml.parsers.FactoryConfigurationError;

public class TTStructure {
  private SetOfCycles _setOfCycles;

  private String _str;
  private int _col;
  private int _row;

  public TTStructure() {
    _col=5;
    _row= 14;
    saveStandardTT("StandardTTC.txt");
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

 public int getColumn(){
   return _col;
 }

 public int getRow(){
  return _row;
 }


 public int getBegingTime(){
   int [] b = {8, 00} ;
   return b[0];
 }
 public int getEndTime(){
   int [] b = {22, 0} ;
   return b[0];
 }

 public String getDayName(int i) {
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
      Element eltTT= wr.createElement(doc,Tag.ITEM2);
      Element eltCycle= wr.createElement(doc,Tag.ITEM2_subTag[0]);
      Element eltDays= wr.createElement(doc,Tag.ITEM2_subTag[1]);
      Element eltDay= wr.createElement(doc,Tag.ITEM2_subTag[2]);
      Element eltSeqs= wr.createElement(doc,Tag.ITEM2_subTag[3]);
      Element eltSeq= wr.createElement(doc,Tag.ITEM2_subTag[4]);
      Element eltPers= wr.createElement(doc,Tag.ITEM2_subTag[5]);
      //Element eltPer= wr.createElement(doc,Tag.ITEM2_subTag[6]);
      Element child0;//= wr.createElement(doc,"seq","AM");
      Element child1;

      for (int i=0; i<14; i++){
        String time= Integer.toString(8+i)+":30-"+Integer.toString(9+i)+":30";
        child0=wr.createElement(doc,Tag.ITEM2_subConst[5],time);
        child1=wr.createElement(doc,Tag.ITEM2_subConst[4],"0");
        Element eltPer= wr.createElement(doc,Tag.ITEM2_subTag[6]);
        eltPer= wr.appendChildInElement(eltPer, child0);
        eltPer= wr.appendChildInElement(eltPer, child1);
        eltPers=wr.appendChildInElement(eltPers, eltPer);
      }

      eltSeq= wr.appendChildInElement(eltSeq, eltPers);
      eltSeqs= wr.appendChildInElement(eltSeqs, eltSeq);
      //eltTT= wr.appendChildInElement(eltTT, child);
      doc= wr.buildDOM(doc,eltSeqs);
      writeFile.write(doc,fileName);
    } catch(Exception e){
      System.out.println("TTStructure: "+e);//debug
    }
    /*catch(FactoryConfigurationError e){
      System.out.println("Factory: "+e);//debug
    }*/


  }

}
