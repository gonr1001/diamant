package dResources;

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
  *
  * */
 public final static void checkIfBelongsValues(String line, String refValues, String message, String classList){
   StringTokenizer ref = new StringTokenizer(refValues);
   StringTokenizer lineValue = new StringTokenizer(line);
   String lvalue;
   while(lineValue.hasMoreElements()){
     lvalue = lineValue.nextToken();
     while(ref.hasMoreElements()){
       if(!lvalue.equals(ref.nextToken())){
         new FatalProblemDlg("Wrong "+message +" in the activity file:" +
                             "\n" + "I was in "+classList+" class and in analyseTokens method ");
         System.exit(1);
        }// end if(!lvalue.equals(ref.nextToken())
     }// end while(ref.hasMoreElements())
   }// end while(lineValue.hasMoreElements())
 }

 /**
  *
  * */
 public final static void checkIfLineIsEmpty(String line, String message, String classList){
   if(line.length() == 0){
     new FatalProblemDlg("Wrong "+message +" in the activity file:" +
     "\n" + "I was in "+classList+" class and in analyseTokens method ");
     System.exit(1);
   }
  }

  /**
   *
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

}