  package dInterface.dAlgorithms;

 /**
  * <p>Title: Proto</p>
  * <p>Description:  timetable construction</p>
  * <p>Copyright: Copyright (c) 2002</p>
  * <p>Company: UdeS</p>
  * @author unascribed
  * @version 1.0
  */

 import com.iLib.gDialog.InformationDlg;

 import dConstants.DConst;
 import dInterface.Command;
 import dInterface.DApplication;
 import dInternal.dOptimization.SelectAlgorithm;

 import javax.swing.JOptionPane;

 public class PersonalizeMixingAlgorithmCmd implements Command{

   int _selectedContext=3;// context for optimize mixing algorithm
   boolean _USER_TEST_ACTIV = false;

   /**
    *
    */
   public PersonalizeMixingAlgorithmCmd(boolean USER_TEST_ACTIV) {
     _USER_TEST_ACTIV = USER_TEST_ACTIV;
   }

   /**
    *
    * @param dApplic
    */
   public void execute(DApplication dApplic) {
     DConst.USER_TEST_ACTIV= _USER_TEST_ACTIV;
     //new PersonalizeMixingAlgorithmDlg();
     int personalizeAcceptableVariation=this.inPutValue();
     (new SelectAlgorithm(personalizeAcceptableVariation,dApplic.getDMediator().getCurrentDoc().getDM())).execute();
     new InformationDlg(dApplic.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
   }

   /**
    *
    * @return
    */
   private int inPutValue(){
     String result="";
     boolean isNotValid=true;
     JOptionPane inputPanel= new JOptionPane();
     while(isNotValid){
       result= inputPanel.showInputDialog("Variation Acceptable");
       if(validation(result)!=0)
         JOptionPane.showMessageDialog(inputPanel,"Valeur éronnée ");
       else
         isNotValid= false;
     }
     return Integer.parseInt(result);
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
}