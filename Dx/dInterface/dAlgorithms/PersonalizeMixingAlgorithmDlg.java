/**
*
* Title: PersonalizeMixingAlgorithmDlg $Revision: 1.5 $  $Date: 2005-02-01 21:27:15 $
* Description: PersonalizeMixingAlgorithmDlg is a class used to
*
*
* Copyright (c) 2001 by rgr.
* All rights reserved.
*
*
* This software is the confidential and proprietary information
* of rgr. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with rgr.
*
* @version $Revision: 1.5 $
* @author  $Author: syay1801 $
* @since JDK1.3
*/
package dInterface.dAlgorithms;



import javax.swing.JOptionPane;


/**
 *
 *
 */
public class PersonalizeMixingAlgorithmDlg  {

 private String _initialValue;

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
