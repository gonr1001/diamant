package dInterface.dAlgorithms;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

//public class PersonalizeMixingAlgorithmDlg {


import javax.swing.JOptionPane;


/**
 *
 *
 */
public class PersonalizeMixingAlgorithmDlg  {

 private String _initialValue="1";

 /**
  *
  * @param initialValue
  */
 public PersonalizeMixingAlgorithmDlg(String initialValue) {
       _initialValue= initialValue;

  } // end constructor AboutDlg

  /**
    *
    * @return
    */
   public String showInputDialog(){
     String result="";
     boolean isNotValid=true;
     JOptionPane inputPanel= new JOptionPane();
     while(isNotValid){
       result= JOptionPane.showInputDialog("Variation Acceptable",_initialValue);
       if(result!=null){
         if(validation(result)!=0)
           JOptionPane.showMessageDialog(inputPanel,"Valeur éronnée ");
         else
           isNotValid= false;
       }else{
         isNotValid= false;
       }

     }
     return result;
   }

   /**
  *
  * @return
  */
 private int validation(String str) {
   if(!testText(str, 0, 99))
     return 1;
   return 0;
 }

 /**
  *
  * @param str
  * @param inf
  * @param sup
  * @return
  */
 private boolean testText(String str, int inf , int sup) {
   boolean res = false;
   int i;
   if (str == null)
     return res;
   if(str.length()==0)
     return res;
   try {
     i = Integer.parseInt(str);
   } catch (Exception e) {
     return res;
   }
   if ( i >= inf && i <= sup)
     res = true;

   return res;
  }

} /* end class PersonalizeMixingAlgorithmDlg */
