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

import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dResources.DConst;
import java.io.File;


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
 public final static String checkIfBelongsValues(String line, String refValues, String message, String classList){
   StringTokenizer ref = new StringTokenizer(refValues);
   StringTokenizer lineValue = new StringTokenizer(line);
   String lvalue;
   String error="";
   int count=0, refSize= ref.countTokens();
   while(lineValue.hasMoreElements()){
     lvalue = lineValue.nextToken();
     while(ref.hasMoreElements()){
       if(!lvalue.equals(ref.nextToken()))
         count++;
       if(count==refSize){
         error= message +" in the activity file:" +
                             "\n" + "I was in "+classList+" class and in analyseTokens method ";
        }// end if(count==refSize)
     }// end while(ref.hasMoreElements())
   }// end while(lineValue.hasMoreElements())
   return error;
 }

 /**
   *check if a String is not empty and exit software otherwise
   * @param String the string value to check
   * @param String the error message to print
   * @param String the classe name where error has been detect
   * */
 public final static String checkIfLineIsEmpty(String line, String message, String classList){
   String error="";
   if(line.length() == 0){
     error = message +" in the activity file:" +
     "\n" + "I was in "+classList+" class and in analyseTokens method ";
   }
   return error;
  }

  /**
   *check if a String is an int value and exit software otherwise
   * @param String the string value to check
   * @param String the error message to print
   * @param String the classe name where error has been detect
   * */
  public final static String isIntValue(String value, String message, String classList){
    String error="";
    try{
       Integer.parseInt(value.trim());
    }catch (NumberFormatException exc){
      error= message +" in the activity file:" +
      "\n" + "I was in "+classList+" class and in analyseTokens method ";
    }
    return error;
  }

  /**
   *check if a String is an int value and exit software otherwise
   * @param String the string value to check
   * */
  public final static boolean isIntValue(String value){
    String error="";
    try{
       Integer.parseInt(value.trim());
    }catch (NumberFormatException exc){
      return false;
    }
    return true;
  }

  /**
   * compare two hour tables of int. tab[0] is the hour and tab[1] is the minute
   * @param int[2] tab1 the first table of integer
   * @param int[2] tab2 the second table of integer
   * @return int "1" if tab1 is greater than tab2, "0" if tab1 and tab2 are equals,
   * "-1" if tab1 is smaller than tab2
   * */
  public final static int compareTabsHour(int[] tab1, int[] tab2){
    if((tab1.length==2) && (tab2.length==2)){
      if(tab1[0]>=tab2[0]){
        if(tab1[0]>tab2[0]){
          return 1;
        }else{// else if(tab1[0]>tab2[0])
          if(tab1[1]>tab2[1]){
            return 1;
          }// end if(tab1[1]>tab2[1])
          if(tab1[1]==tab2[1]){
            return 0;
          }// end if(tab1[1]==tab2[1])
          if(tab1[1]<tab2[1]){
            return -1;
          }// end if(tab1[1]<tab2[1])
        }// end else if(tab1[0]>tab2[0])
      }// end if(tab1[0]>tab2[0])
    }// end  if((tab1.length==2) && (tab2.length==2))
    return -1;
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

   /**
    * check if a file exist
    * @param string the file name
    * @return boolean true if the file exist a nd false otherwise
    * */
   public final static boolean isFileExist(String filename){
     return (new File(filename)).exists();
   }

   /**
    *
    * @param initialAvail
    * @param tt
    * @return
    */
   public final static int[][] resizeAvailability(int[][] initialAvail, TTStructure tt){
     //check if is upper, lower or nothing operation
     int UpperLower=0; // 1= make upper; 0= do nothing; -1= make lower
     if(initialAvail[0].length== tt.getCurrentCycle().getMaxNumberOfPeriodsADay()) {
       UpperLower=0;
       return initialAvail;
     }
     else if(initialAvail[0].length > tt.getCurrentCycle().getMaxNumberOfPeriodsADay()) {
       UpperLower=-1;
     }
     else if(initialAvail[0].length< tt.getCurrentCycle().getMaxNumberOfPeriodsADay()) {
       UpperLower=1;
     }

     Day day;
     Period per;
     int[][] finalAvail= new int [tt.getNumberOfActiveDays()]
                       [tt.getCurrentCycle().getMaxNumberOfPeriodsADay()];
     for (int h=0; h< tt.getNumberOfActiveDays(); h++){
       int itr=0;
       day = tt.getCurrentCycle().getDayByIndex(h);
       for (int i=0; i< day.getSetOfSequences().size(); i++){
         for (int j=0; j< day.getSequence(i).getSetOfPeriods().size(); j++){
           per = day.getSequence(i).getPeriod(j);
           boolean avail = isAvailableInRange(initialAvail,h,per,tt.getPeriodLenght(),UpperLower);
           if (avail)
             finalAvail[h][itr]=1;
           else
             finalAvail[h][itr]=5;
           itr++;
         }//end for (int j=0; j< day.getSequence(i).getSetOfPeriods().size(); j++)
       }// end for (int i=0; i< day.getSetOfSequences().size(); i++)
     }// end
     return finalAvail;
   }

   /**
    * check the state availability in the range
    * @param initial the availability matrix
    * @param day the day where to search availability
    * @param per the period
    * @param int the period lenght
    * @param up_low the type of operation 1= make upper; 0= do nothing; -1= make lower
    * @return boolean
    */
   private static boolean isAvailableInRange(int[][] initial,int day, Period per, int periodLenght, int up_low){
     int[] beginH = per.getBeginHour();
     int[] endH = per.getEndHour(periodLenght);
     int beginIndex;
     int endIndex;
     beginIndex= beginH[0]- DConst.STIBEGINHOUR;
     if (beginH[1] < DConst.STIBEGINMINUTE)
       beginIndex--;
     if (beginH[1] > DConst.STIBEGINMINUTE)
       beginIndex++;
     if (beginIndex<0)
       return false;
     else{// else  if (beginIndex<0)
       endIndex = endH[0]- DConst.STIBEGINHOUR;
       if (endH[1] <= DConst.STIBEGINMINUTE)
         endIndex--;
       else if (endH[1] > DConst.STIBEGINMINUTE)
         endIndex++;
       if (endIndex<0)
         return false;
       if(endIndex>= initial[day].length){
         return false;
       }else{// else if(endIndex> initial[day].length)
         for (int i=beginIndex; i<= endIndex; i++)
           if(initial[day][i]==5)
             return false;
       }// end else if(endIndex> initial[day].length)
     }//end else  if (beginIndex<0)
     return true;
   }

   /**
    * return a token in from a stringtokenizer
    * @param str
    * @param delimiter
    * @param position
    * @return
    */
   public final static String getToken(String str, String delimiter, int position){
     StringTokenizer strToken= new StringTokenizer(str,delimiter);
     int nbTokens= strToken.countTokens();
     for (int i=0; i< nbTokens; i++){
       if(i==position)
         return strToken.nextToken();
       else
         strToken.nextToken();
     }
     return "";
   }


   /**
   * remove a token in a stringtokenizer
   * @param str
   * @param delimiter
   * @param position
   * @return
   */
  public final static String removeToken(String str, String delimiter, int position){
    StringTokenizer strToken= new StringTokenizer(str,delimiter);
    //int nbTokens= strToken.countTokens();
    String res="";
    int inc=0;
    while(strToken.hasMoreElements()){
      if(inc!=position)
        res+= strToken.nextToken()+delimiter;
      else
        strToken.nextToken();
      inc++;
    }
    return res;
   }

   /**
    * count the number of tokens
    * @return
    */
   public final static int countTokens(String str, String delimiter){
     StringTokenizer strToken= new StringTokenizer(str,delimiter);
     return strToken.countTokens();

   }

}