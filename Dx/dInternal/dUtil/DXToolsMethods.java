package dInternal.dUtil;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import java.util.StringTokenizer;
import com.iLib.gDialog.FatalProblemDlg;

public class DXToolsMethods {

  /**
   * Constructor
   * */
  public DXToolsMethods() {
  }
  /**
   *check if a String only contains a reference value and exit software otherwise
   * @param String the string value to check
   * @param String the reference value to use
   * @param String the error message to print
   * @param String the classe name where error has been detect
   * */
 public final static void checkIfBelongsValues(String line, String refValues, String message, String classList){
   StringTokenizer ref = new StringTokenizer(refValues);
   StringTokenizer lineValue = new StringTokenizer(line);
   String lvalue;
   int count=0, refSize= ref.countTokens();
   while(lineValue.hasMoreElements()){
     lvalue = lineValue.nextToken();
     while(ref.hasMoreElements()){
       if(!lvalue.equals(ref.nextToken()))
         count++;
       if(count==refSize){
         new FatalProblemDlg("Wrong "+message +" in the activity file:" +
                             "\n" + "I was in "+classList+" class and in analyseTokens method ");
         System.exit(1);
        }// end if(count==refSize)
     }// end while(ref.hasMoreElements())
   }// end while(lineValue.hasMoreElements())
 }

 /**
   *check if a String is not empty and exit software otherwise
   * @param String the string value to check
   * @param String the error message to print
   * @param String the classe name where error has been detect
   * */
 public final static void checkIfLineIsEmpty(String line, String message, String classList){
   if(line.length() == 0){
     new FatalProblemDlg("Wrong "+message +" in the activity file:" +
     "\n" + "I was in "+classList+" class and in analyseTokens method ");
     System.exit(1);
   }
  }

  /**
   *check if a String is an int value and exit software otherwise
   * @param String the string value to check
   * @param String the error message to print
   * @param String the classe name where error has been detect
   * */
  public final static void isIntValue(String value, String message, String classList){
    try{
      (new Integer (value.trim())).intValue();
    }catch (NumberFormatException exc){
      new FatalProblemDlg("Wrong "+message +" in the activity file:" +
      "\n" + "I was in "+classList+" class and in analyseTokens method ");
      System.exit(1);
    }
  }

  /**
   * Convert STI Time and periods
   * @param int the reference period
   * @return int[2] the first element of the table is hour and the second element
   * is minute
   * */
   public final static int [] convertSTIPeriods(int period){
     int [] time={0,30};
     time[0]=7+period;
     return time;
   }

   /**
   * Convert periods and STI Time
   * @param int[2] the first element of the table is hour and the second element
   *  is minute
   * @return  int the reference period
   * */
   public final static int convertSTIPeriods(int hour, int minute){
     return hour-7;
   }

}