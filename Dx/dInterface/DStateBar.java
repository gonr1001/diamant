package dInterface;


import java.util.Enumeration;
import java.util.Hashtable;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import dResources.DConst;
import dInternal.dData.Resource;
import dInternal.dData.State;
import dInternal.dData.SetOfStates;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Christian Jacques
 * @version 1.0
 */

public class DStateBar extends JPanel {
  private JLabel _theLabels[];

   public final static int ERROR_SIZE_TO_SMALL =-1;
   public final static int ERROR_SIZE_OVER     = 1;
   public final static int VALIDATE_OK         = 0;
   private Hashtable _constantTable = new Hashtable();
   private Hashtable _labelTable = new Hashtable();

   private int _maxItems =0;
   boolean _trace = false;


   public DStateBar(SetOfStates s) {
     boolean first = true;
     _theLabels = new JLabel [s.size()];
     for (int i = 0; i < s.size(); i++) {
       _theLabels[i] = new JLabel();
     }
     showDStateBar(s, first);
   }

   public void upDateDStateBar(SetOfStates s) {
     boolean first = false;
     showDStateBar(s, first);
   }

   private void showDStateBar(SetOfStates s, boolean first) {
     for(int i = 0; i < s.size(); i++) {
       Resource r =  (Resource) s.getResourceAt(i);
       if ( ((State)r.getAttach()).getColor() != null) {
         _theLabels[i].setForeground(((State)r.getAttach()).getColor());
       } else {
         _theLabels[i].setForeground(DConst.COLOR_BLACK);
     }
     if ( ((State)r.getAttach()).getValue() >= 0 ) {
       _theLabels[i].setText(r.getID() + " : " + " " + ((State)r.getAttach()).getValue() + " ");
     } else {
       _theLabels[i].setText(r.getID() + " : " +  "  ");
     }
   if (first) {
     this.add(_theLabels[i]);
   }
} // end for
} // end showDStateBar

   /**
    * Initialise les composante graphique de la status bar
    *
    * @param constantLabel : Contients les chaines de charactère qui ne changeront pas dans les Label
    * @param label : Contients les chaines de charactère qui changeront
    * @param labelColor : Couleurs des labels
    */
/*
   private void jbInit(String [] constantLabel, String [] label, Color [] labelColor, int maxItems)
   {
     String error = "";
      if (maxItems < constantLabel.length)
      {
         maxItems = constantLabel.length;
         error = "Le nombre items maximums \"maxItems\" était inférieur à la longueur du tableau donnée, \n"
                      + " il a été remplacer pour la longueur du tableau donnée";
      }
      else if(constantLabel.length != label.length)
      {
        error = "Le nombre de label constant doit être le même que le nombre de label, "
            +"utilisé \" \" pour un label ou un label constant vide ";
      }
      else if (labelColor.length <=0)
      {
        error = "Aucune couleur n'a été spécifiée.";
      }
      else
      {
        _maxItems = maxItems;
        setStatusBar(constantLabel, label, labelColor);
      }
      if (!error.equalsIgnoreCase(""))
      {
        if (_trace)
        {
          System.err.println(error);
        }
        showErrorDialog(error, "Error");
      }
   }

   /**
    *
    * Valide si la position objet (label) a changer est valide
    *
    * @param position : position de l'objet (label) à modifier dans la status bar
    * @return: Retourne ERROR_SIZE_TO_SMALL si la position est trop petite
    * Retourne ERROR_SIZE_OVER si la position demander est plus élever que les positions posibles
    * Retourne VALIDATE_OK si la position est valide
    */
/*
   private int validatePosition(int position)
   {
      int returnValue = ERROR_SIZE_TO_SMALL;
      if (position > _labelTable.size())
      {
         String error = "Cet élément n'existe pas : La position donnée est plus élevé que le nombre total d'élement";
         if (_trace)
         {
            System.err.println(error);
         }
         showErrorDialog(error, "Error");
         returnValue = ERROR_SIZE_OVER;
      }
      else if (position < 0)
      {
         String error = "Cet élément n'existe pas : La position donnée est inférieurs à 0";
         if (_trace)
         {
            System.err.println(error);
         }
         showErrorDialog(error, "Error");
         returnValue = ERROR_SIZE_TO_SMALL;
      }
      else
      {
         returnValue = VALIDATE_OK;
      }
      return returnValue;
   }

   /**
    * Chaque élément des tableaux seront pour le même objet JLabel de la JStatusBar
    * @param constantLabel : Contients les chaines de charactère qui ne changeront pas dans les Label
    * @param label : Contients les chaines de charactère qui changeront
    * @param labelColor : Couleurs des labels
    */
   /*
   public void setStatusBar(String [] constantLabel, String [] label, Color [] labelColor)
   {
      JLabel newLabels[] = new JLabel [constantLabel.length];
      Color mainColor =null;
      boolean uniformColors = false;
      if (labelColor.length != constantLabel.length)
      {
        mainColor = labelColor[0];
        uniformColors = true;
      }
      for (int i=0; i<constantLabel.length ;i++)
      {
         newLabels[i] = new JLabel();
         if (_trace)
         {
            System.out.println( "setStatusBar =>" +i + ":=" + constantLabel[i] + " " + label[i] + " color:" + labelColor[i].toString());
         }
         if (uniformColors)
         {
           newLabels[i].setForeground(mainColor);
         }else
         {
           newLabels[i].setForeground(labelColor[i]);
         }
         newLabels[i].setText(constantLabel[i] + " " + label[i]);
         _constantTable.put(Integer.toString(i), constantLabel[i]);
         _labelTable.put(Integer.toString(i), newLabels[i]);
         this.add(newLabels[i], null);
      }
      this.updateUI();
   }

   /**
    * Mets à jour tout les labels, ne change que la partie non constante
    * @param label : Nouvelles chaines de charactères
    */
   /*
   public boolean updateBar ( String [] label )
   {
      JLabel currentLabel = new JLabel();
      boolean returnValue= false;
      if (label.length <= _labelTable.size())
      {
         for (int i=0; i<label.length ;i++)
         {
            if (_trace)
            {
            System.out.println("Updating line "+ i + ":=" + _constantTable.get(Integer.toString(i)) + " " + label[i]);
         }
         currentLabel = (JLabel)_labelTable.get((Object)Integer.toString(i));
         currentLabel.setText((String)_constantTable.get(Integer.toString(i)) + " " + label[i]);
         }
         returnValue = true;
      }
      else
      {
         String error = "UpdateBar : Seulement " + _labelTable.size() + " items peuvent être entrés";
         if (_trace)
         {
            System.err.println(error);
         }
         showErrorDialog(error, "Error");
         returnValue = false;
      }
      return returnValue;
   }

   /**
    * Mets à jour un Label particulier, celui est déterminer par le label number
    * @param labelNumber : le numéro du Label à changer
    * @param message : le message qui remplacera l'ancien
    */

   /*
   public int updateLabel (int labelNumber, String message)
   {
      int returnValue = validatePosition(labelNumber);
      if (returnValue == 0)
      {
         JLabel currentLabel = (JLabel)_labelTable.get((Object)Integer.toString(labelNumber));
         currentLabel.setText((String)_constantTable.get(Integer.toString(labelNumber)) + " " + message);
      }
      return returnValue;
   }

   /**
    * Ajoute un nouveau Label à l'endroit défini par labelNumber
    * @param labelNumber : endroit ou le label sera ajouté
    * @param constantLabel : Contients les chaines de charactère qui ne changeront pas dans les Label
    * @param label : Contients les chaines de charactères qui changeront
    * @param labelColor : Couleurs des labels
    */
   /*
   public boolean newLabel (int labelNumber, String constantLabel, String label, Color labelColor)
   {
      boolean returnValues =false;
      if (labelNumber <= _constantTable.size()
          && labelNumber > -1
          && _constantTable.size() <= _maxItems)
      {
         if ( !label.equals("") || !constantLabel.equals(""))
         {
            JLabel newLabel = new JLabel();
            newLabel.setForeground(labelColor);
            newLabel.setText(constantLabel + " " + label);
            if (_trace)
            {
               System.out.println(labelNumber + ":" + constantLabel + " " + label);
            }
            _constantTable.put(Integer.toString(labelNumber), constantLabel);
            _labelTable.put(Integer.toString(labelNumber), newLabel);
            add(newLabel, null);
            updateUI();
            returnValues = true;
         }
         else
         {
            String error = "newLabel : les champs ne doivent pas être vides";
            showErrorDialog(error, "Error");
            if (_trace)
            {
               System.err.println(error);
            }
            returnValues = false;
         }
      }
      else
      {
         String error = "newLabel : Only " + _maxItems + " items peuvent être entrées";
         if (_trace)
         {
            System.err.println(error);
         }
         showErrorDialog(error, "Error");
         returnValues = false;
      }
      return returnValues;
   }

   /**
    * Ajoute un nouveau Label à l'endroit défini par labelNumber à la fin des Label déjà présent
    * @param constantLabel : Contients les chaines de charactère qui ne changeront pas dans les Label
    * @param label : Contients les chaines de charactères qui changeront
    * @param labelColor : Couleurs des labels
    */
   /*
   public void appendLabel (String constantLabel, String label, Color labelColor)
   {
      newLabel ( _constantTable.size(), constantLabel, label, labelColor);
   }

   /**
    * Change la couleur pour un label donné, celui est déterminer par le label number
    * @param labelNumber : Position du label a modifier
    * @param newColor : nouvelles couleurs du label
    */
   /*
   public int changeColor (int labelNumber, Color newColor)
   {
      int returnValue = validatePosition(labelNumber);
      if (returnValue == 0)
      {
         JLabel currentLabel = (JLabel)_labelTable.get((Object)Integer.toString(labelNumber));
         currentLabel.setForeground(newColor);
      }
      return returnValue;
   }

   /**
    * Uniformise toute les labels de la barre de status à une couleur
    * @param uniformColor : nouvelles couleurs du label
    */
   /*
   public void uniformizedColor (Color uniformColor)
   {
      Enumeration enum =  _labelTable.elements();
      JLabel currentLabel = new JLabel();
      while (enum.hasMoreElements())
      {
         currentLabel = (JLabel)enum.nextElement();
         currentLabel.setForeground(uniformColor);
      }
   }

   /**
    * Change le font pour un label donné, celui est déterminer par le label number
    * @param labelNumber : Position du label a modifier
    * @param newFont : nouvelle police de caractère du label
    */
   /*
   public int changeFont (int labelNumber, Font newFont)
   {
      int returnValue = validatePosition(labelNumber);
      if (returnValue == 0 )
      {
         JLabel currentLabel = (JLabel)_labelTable.get((Object)Integer.toString(labelNumber));
         currentLabel.setFont(newFont);
      }
      return returnValue;
   }

   /**
    * Uniformise toute les labels de la barre de status à un font
    * @param uniformFont : nouvelle police de caractère des labels
    */
   /*
   public void uniformizedFont (Font uniformFont)
   {
      Enumeration enum =  _labelTable.elements();
      JLabel currentLabel = new JLabel();
      while (enum.hasMoreElements())
      {
         currentLabel = (JLabel)enum.nextElement();
         currentLabel.setFont(uniformFont);
      }
   }

   /**
    * Vas cherher un label en particulier
    * @param labelNumber : le label à aller chercher
    * @return le label au numéro (défini par labelNumber) correspondant
    */
   /*
   public JLabel getLabel (int labelNumber)
   {
      int returnValue = validatePosition(labelNumber);
      if (returnValue == 0 )
      {
         return (JLabel)_labelTable.get((Object)Integer.toString(labelNumber));
      }
      return null;
   }
*/
   /**
    * Modifie un label en particulier
    * @param labelNumber : Le label à modifier
    * @param label : le label qui replacera le label à la position labelNumber
    */
   /*
   public int setLabel (int labelNumber, JLabel label)
   {
      int returnValue = validatePosition(labelNumber);
      if (returnValue == 0)
      {
         _labelTable.put((Object)Integer.toString(labelNumber), label);
      }
      return returnValue;
   }

*/

   public void showErrorDialog(String error, String title)
   {
      JOptionPane errorDlg = new JOptionPane();
      errorDlg.showMessageDialog(null, error, title, JOptionPane.ERROR_MESSAGE);
   }
}