package dInterfaceTest;



import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;


import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class MousePressListener implements MouseListener, MouseMotionListener {
	
	private Grille grille;
	
	private boolean press = false;
	
	private boolean consult = false;
	
	private int courant;
	
	//private double dureedefaut = 0.5;

	private PopupMenu popup;

	public int pop = 2;
	
	private int size = 0;
	
	private int indiceToRemove = 0;
	
	private LinkedList<Integer> liste;

	private boolean resizeLargeur = false;
	
	private boolean resizeHauteur = false;
	
	public MousePressListener(Grille g){
		grille=g;
		init();
	}
	
	public void setConsult() {
		if(consult==true)consult = false;
		else consult = true;
	}

	public Grille getGrille(){
		return grille;
	}
	
	private void init(){
		MenuItem mi;
		MenuAction acL = new MenuAction(this); 

	    popup = new PopupMenu("Edit");

	    mi = new MenuItem(IHMConstant.VIEW);
        mi.addActionListener(acL);
	    popup.add(mi);
	    
	    popup.addSeparator();
	    
	    mi = new MenuItem(IHMConstant.MODIFGR);
        mi.addActionListener(acL);
	    popup.add(mi);
	    
	    mi = new MenuItem(IHMConstant.NAMEDAYS);
        mi.addActionListener(acL);
	    popup.add(mi);
	    
	    popup.addSeparator();
	    
        mi = new MenuItem(IHMConstant.MODIF);
        mi.addActionListener(acL);
	    popup.add(mi);
	    
	    mi = new MenuItem(IHMConstant.DELETE);
	    mi.addActionListener(acL);
	    popup.add(mi);

	    popup.addSeparator();
	    
	    //JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("Consultation");
	    mi = new MenuItem("Consultation");
	    mi.addActionListener(acL);
        popup.add(mi);
	    
	    grille.add(popup);
	}
	
	private int getDay(MouseEvent e){
		int retour;
		
		if(grille.isHorizontal()==false){
			retour=Math.round((e.getX()-50)/grille.getLARG()) +1;
		}
		else retour=Math.round((e.getY()-grille.getHAUT())/grille.getLARG()) +1;
		
		return retour;
	}
	
	private int getHour(MouseEvent e){
		int retour;
		
		if(grille.isHorizontal()==false){
			retour=(int)(Math.floor(((e.getY()-grille.getHAUT())/(grille.getHAUT()*2)))+8);
		}
		else retour=(int)(Math.floor(((e.getX()-50)/(grille.getHAUT()*2)))+8);
		
		return retour;
	}
	
	private double getMinute(MouseEvent e){
		double minute;
		if(grille.isHorizontal()==false) minute = (((double)e.getY()-grille.getHAUT())/(grille.getHAUT()*2))-Math.floor((((double)e.getY()-grille.getHAUT())/(grille.getHAUT()*2)));
		else minute = (((double)e.getX()-50)/(grille.getHAUT()*2))-Math.floor((((double)e.getX()-50)/(grille.getHAUT()*2)));
		
		if(minute<0.25){
			minute = 0;
		}
		else if(minute>0.25 && minute <0.5){
			minute = 0.25;
		}
		else if(minute>0.5 && minute <0.75){
			minute = 0.5;
		}
		else{
			minute = 0.75;
		}
		
		return minute;
	}
	
	public void mousePressed(MouseEvent e){
		int jour, heure;
		double minute;
		
		pop++;
		if (e.isPopupTrigger()) { 
	        popup.show(e.getComponent(), e.getX(), e.getY());
	        pop = 0;
	    }
		int debut = grille.present(getHour(e)+getMinute(e), getDay(e));
		if(debut==-1)debut = grille.present(getHour(e)+getMinute(e)-0.25, getDay(e));
		
		/*if(e.getClickCount()== 2){
			press = false;
		}
		else */
		if(consult == false){
			if(pop>1 && e.getButton()== MouseEvent.BUTTON1){
				if(debut!=-1){
					press=true;
					courant = debut;
				}
				else{
					jour = getDay(e);
					heure = getHour(e);
					minute = getMinute(e);
					
					if(jour<=grille.getNumberOfDays() && heure<grille.getHOUR()){
						courant = grille.add(new Period(1, (heure+minute), jour));
						grille.repaint();
					}
					else press = false;
				}
			}
		}
		else if(consult == true){
			if(e.getClickCount()==2){
				//TODO afficher une boite avec les infos de la zone horaire
			}
		}
		else press = false;
	}
	
	public void mouseClicked(MouseEvent e) {
		e.toString(); // to avoid warning	
	}
	
	public void mouseReleased(MouseEvent e) {
		int jour = getDay(e);
		int heure = getHour(e);
		double minute = getMinute(e);
		double duree;
		
		if (e.isPopupTrigger()) {
			popup.getItem(5).setEnabled(false);
			popup.getItem(6).setEnabled(false);
			//popup.getItem(8).setEnabled(false);
			System.out.println("Heure + minute "+getHour(e)+" "+getMinute(e));
			liste = grille.selection(getDay(e),getHour(e)+getMinute(e));
	        if(liste.size()!=0){
	        	size=liste.size();
	        	indiceToRemove = (liste.get(0)).intValue();
	        	//popup.getItem(8).setEnabled(true);
	        	popup.getItem(5).setEnabled(true);
	        	popup.getItem(6).setEnabled(true);
	        }
	        popup.show(e.getComponent(), e.getX(), e.getY());
	        pop = 0;
	        press=false;
	    }
		
		if(press==true){
			Period c = grille.getListeDeCours().get(courant);
			duree = (heure+minute+0.25)-c.getDebut();
			//eventuellement a changer si on veut qu'un cours
			if(duree<0.5)duree=0.5;
			if(jour<=grille.getNumberOfDays()){
				grille.getListeDeCours().get(courant).setDuree(duree);
				grille.repaint();
			}
		}
		press=true;
	}
	
	public void mouseEntered(MouseEvent e) {
		e.toString();//to avoid warning
	}

	public void mouseExited(MouseEvent e) {	
		e.toString();//to avoid warning
	}

	public void mouseDragged(MouseEvent e) {
		int heure = getHour(e);
		int jour = getDay(e);
		double minute = getMinute(e);
		double duree;
		
		if(press==true && resizeHauteur==false && resizeLargeur==false){
			Period c = grille.getListeDeCours().get(courant);
			duree = (heure+minute+0.25)-c.getDebut();
			//eventuellement a changer si on veut qu'un cours
			if(duree<0.5)duree=0.5;
			if(jour<=grille.getNumberOfDays()){
				grille.getListeDeCours().get(courant).setDuree(duree);
				grille.repaint();	
			}	
		}
		else if(resizeLargeur==true && resizeHauteur==false){
			grille.setLARG((e.getX()-50)/grille.getNumberOfDays());
			/*Dimension minimumSize = new Dimension(grille.getLARG()*grille.getNumberOfDays()+80, grille.getHAUT()*2*(grille.getHOUR()-7));
			grille.setSize(minimumSize);*/
			grille.repaint();
		}
		else if(resizeHauteur==true && resizeLargeur==false){
			grille.setHAUT((e.getY()-grille.getHAUT())/(((grille.getHOUR()-8)*2)+1));
			grille.repaint();
		}
		else if(resizeHauteur==true && resizeLargeur==true){
			grille.setLARG((e.getX()-50)/grille.getNumberOfDays());
			grille.setHAUT((e.getY()-grille.getHAUT())/(((grille.getHOUR()-8)*2)+1));
			grille.repaint();
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if(e.getX()>=(50+grille.getNumberOfDays()*grille.getLARG()) && e.getX()<=(53+grille.getNumberOfDays()*grille.getLARG()) && e.getY()<=grille.getHAUT()+(grille.getHAUT()*((grille.getHOUR()-8)*2)+1)){
			grille.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR ));
			resizeLargeur = true;
			resizeHauteur = false;
		}
		else if(e.getY()>=grille.getHAUT()+(grille.getHAUT()*((grille.getHOUR()-8)*2)+1) && e.getY()<=grille.getHAUT()+(grille.getHAUT()*((grille.getHOUR()-8)*2)+1)+3 && e.getX()<=(50+grille.getNumberOfDays()*grille.getLARG())){
			grille.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR ));
			resizeLargeur = false;
			resizeHauteur = true;
		}
		else if(e.getX()>=(50+grille.getNumberOfDays()*grille.getLARG()) && e.getX()<=(53+grille.getNumberOfDays()*grille.getLARG()) && e.getY()>=grille.getHAUT()+(grille.getHAUT()*((grille.getHOUR()-8)*2)+1) && e.getY()<=grille.getHAUT()+(grille.getHAUT()*((grille.getHOUR()-8)*2)+1)+3){
			grille.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR ));
			resizeLargeur = true;
			resizeHauteur = true;
		}
		else{
			grille.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			resizeLargeur = false;
			resizeHauteur = false;
		}
		
	}

	public void remove() {
		if(size==1){
			grille.remove(indiceToRemove);
			grille.repaint();
		}
		else{
			Object[] possibilities = new Object[size];
			for(int i=0; i<size; i++){
				possibilities[i]=grille.getListeDeCours().get(liste.get(i).intValue()).getNom();
			}
			String reponse = (String)JOptionPane.showInputDialog(grille,"Quel creneau voulez vous supprimer:\n","Suppression",JOptionPane.PLAIN_MESSAGE,null,possibilities,possibilities[0]);
			if(reponse!=null){
				grille.remove(reponse);
				grille.repaint();
			}
		}
	}
	
	public void modif(){
		if(size==1){
			boiteModif(indiceToRemove);
			grille.repaint();
		}
		else{
			Object[] possibilities = new Object[size];
			for(int i=0; i<size; i++){
				possibilities[i]=grille.getListeDeCours().get(liste.get(i).intValue()).getNom();
			}
			String reponse = (String)JOptionPane.showInputDialog(grille,"Quel creneau voulez vous modifier:\n","Modification",JOptionPane.PLAIN_MESSAGE,null,possibilities,possibilities[0]);
			if(reponse!=null){
				boiteModif(grille.convert(reponse));
				grille.repaint();
			}
		}
	}
	
	public void boiteModif(final int indiceModifie){
		final JDialog boite = new JDialog();
        
        JPanel boiteDebut = new JPanel();
        JLabel debut = new JLabel("Modifier l'heure de debut de la periode");
        final JTextField inputDebut= new JTextField(10);
        boiteDebut.add(debut);
        boiteDebut.add(inputDebut);
        
        JPanel boiteDuree = new JPanel();
        JLabel duree = new JLabel("Modifier la duree de la periode");
        final JTextField inputDuree= new JTextField(10);
        boiteDuree.add(duree);
        boiteDuree.add(inputDuree);
        
        JPanel boitePri = new JPanel();
        JLabel Pri = new JLabel("Modifier la priorité de la periode");
        final JTextField inputPri= new JTextField(10);
        boitePri.add(Pri);
        boitePri.add(inputPri);
        
        Button ok = new Button("OK");
        
        class valide implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString();//to avoid warning
				String dureeVal, debutVal, priVal;
				dureeVal=inputDuree.getText();
				debutVal=inputDebut.getText();
				priVal=inputPri.getText();
				boite.dispose();
				//TODO modification
				if(!debutVal.equals("")){
					grille.getListeDeCours().get(indiceModifie).setDebut(Double.parseDouble(debutVal));
				}
				if(!dureeVal.equals("")){
					grille.getListeDeCours().get(indiceModifie).setDuree(Double.parseDouble(dureeVal));
				}
				if(!priVal.equals("")){
					grille.getListeDeCours().get(indiceModifie).setPriority(Integer.parseInt(priVal));
				}
			}
		}
		ActionListener listener = new valide();
		ok.addActionListener(listener);

		JPanel boiteNorth = new JPanel();
		boiteNorth.setLayout(new BorderLayout());
		boiteNorth.add(boiteDebut, BorderLayout.NORTH);
		boiteNorth.add(boiteDuree, BorderLayout.CENTER);
		
        boite.setLayout(new BorderLayout());
        boite.add(boiteNorth, BorderLayout.NORTH);
        boite.add(boitePri, BorderLayout.CENTER);
        boite.add(ok, BorderLayout.SOUTH);

        boite.pack();
		boite.setModal(true);
		boite.setVisible(true);
		boite.setResizable(false);
	}
	
}