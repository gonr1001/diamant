package dInternal.dTimeTable;

import dInternal.dUtil.DXObject;
import xml.InPut.ReadXMLElement;
import org.w3c.dom.Element;

public class Period extends DXObject {

  /**
   * Constructor
   * */
  public Period() {

  }

  /**
   * set the begin hour of the period
   * @param int the hour
   * @param int the minute
   * */
  public void setBeginHour(int hour, int minute){
    _beginHour[0] = hour;
    _beginHour[1] = minute;
  }

  /**
   * set the priority of the periode
   * @param int the priority of the period
   * */
  public void setPriority(int prior){
    _priority= prior;
  }

  /**
   * get the begin hour of the period
   * @return int[2] the table of the begin hour. The in range 0 is the hour
   * and the element in the range 1 is the minutes
   * */
  public int[] getBeginHour(){
    return _beginHour;
  }

  /**
   * get the end hour of the period
   * @return int[2] the table of the begin hour. The in range 0 is the hour
   * and the element in the range 1 is the minutes
   * */
  public int[] getEndHour(int periodLenght){
    int[] endHour={0,0};
    endHour[1]= (_beginHour[1]+periodLenght)%60;//
    endHour[0]=_beginHour[0]+(_beginHour[1]+periodLenght)/60;
    return endHour;
  }

  /**
   * get number of students conflicts
   * @param int the number of students conflicts
   * */
  public int getNbStudConflict(){
    return nbStudConflict;
  }

  /**
   * get number of instructors conflicts
   * @param int the number of instructors conflicts
   * */
  public int getNbInstConflict(){
    return nbInstConflict;
  }

  /**
   * get number of rooms conflicts
   * @param int get number of rooms conflicts
   * */
  public int getNbRoomConflict(){
    return nbRoomConflict;
  }

  /**
   * get priority of the period
   * @param int the priority of the period
   * */
  public int getPriority(){
    return _priority;
  }

  /**
    *
    * */
   public void readXMLtag(Element setPeriod){
     ReadXMLElement list= new ReadXMLElement();
      Period period = new Period();
      String begin, end, prior;
      //int size= list.getSize(setofPers,_TAGITEM);
      //System.out.println(" Periods Size: "+size);//debug
        //Element per= list.getElement(setPeriod,_TAGITEM,i);
        begin= list.getElementValue(setPeriod,_TAGITEM);
        end= list.getElementValue(setPeriod,_TAGITEM1);
        prior= list.getElementValue(setPeriod,_TAGITEM2);
        System.out.println(" Period properties -- begin: "+begin+" end: "+end+" Priority: "+prior);//debug
        //Element periods= list.getElement(sequence,_TAGITEM2,0);
        //period.readXMLtag(per);

   }

  private int nbStudConflict = 0;
  private int nbInstConflict = 0;
  private int nbRoomConflict= 0;
  private int[] _beginHour= {8,0};//_beginHour[0]= hour; _beginHour[1]= minute
  private int _priority;// 0= normal; 1= low; 2= null
  private String _TAGITEM="BeginTime";
  private String _TAGITEM1="EndTime";
  private String _TAGITEM2="priority";
}