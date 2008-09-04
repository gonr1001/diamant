package dInterfaceTest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;


import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.text.AttributedString;
import java.util.LinkedList;


import javax.swing.JPanel;



public class Grille extends JPanel /*implements Observer*/{
	
	final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f, 
                                          BasicStroke.CAP_BUTT, 
                                          BasicStroke.JOIN_MITER, 
                                          10.0f, dash1, 0.0f);
    
    final static BasicStroke none = new BasicStroke();
	
    private int alpha = 200;

	private int HAUT = 30;
	
	private int LARG = 150;
	
	private int HOUR = 19;
	
	private int numberOfDays = 5;
	
	private MouseListener l;
	
	private LinkedList <Period> listeDePeriode = new LinkedList<Period>();
	
	private LinkedList <String> listeDeJours = new LinkedList<String>();
	
	private Color couleur1Box = new Color(113, 109, 174, alpha);
	
	private Color couleur2Box = new Color(205, 233, 255, alpha);
	
	private Color couleur1Header = new Color(84,82,183);
	
	private Color couleur2Header = new Color(161, 180,255);
	
	private Color couleurConflits = new Color(106, 165, 154);

	private Color couleur1Boxpri1 = new Color(255, 9, 9, alpha);//9, 0, 57, alpha);
	
	private Color couleur1Boxpri2 = new Color(22, 255, 138, alpha);
	

	private boolean horizontal = false;
	
	public boolean isHorizontal() {
		return horizontal;
	}

	public Grille(){
		constructor();
	}
	
	public Grille(int nbDays){
		constructor();
		numberOfDays = nbDays;
	}
	
	public Grille(int nbDays, int hauteur, int largeur, int lastHour){
		if(nbDays != 0)numberOfDays = nbDays;
		if(hauteur != 0) HAUT = hauteur;
		if(largeur != 0) LARG = largeur;
		if(lastHour != 0) HOUR = lastHour;
		constructor();
	}
	
	public void constructor(){
		build();
		Dimension minimumSize = new Dimension(LARG*numberOfDays+60,HAUT*2*(HOUR-7));
		this.setPreferredSize(minimumSize);
	    listeDeJours = new LinkedList<String>();
		fillRect();	
	}
	
	public void setListeDeJours(LinkedList<String> listeDeJoursarg) {
		this.listeDeJours = listeDeJoursarg;
	}
	
	public void setNumberOfDays(int numberOfDaysarg) {
		this.numberOfDays = numberOfDaysarg;
		Dimension minimumSize = new Dimension(LARG*numberOfDays+60,HAUT*2*(HOUR-7));
		this.setPreferredSize(minimumSize);
		repaint();
	}
	
	public void setColor1Box(int r, int g, int b){
		couleur1Box = new Color(r, g, b, alpha);
	}
	
	public void setColor2Box(int r, int g, int b){
		couleur2Box = new Color(r, g, b, alpha);
	}
	
	public void setColor1Header(int r, int g, int b){
		couleur1Header = new Color(r, g, b);
	}
	
	public void setColor2Header(int r, int g, int b){
		couleur2Header = new Color(r, g, b);
	}
	
	public void setCouleurConflits(int r, int g, int b){
		couleurConflits = new Color(r, g, b);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
	    if(horizontal==false){
	    	afficheVertical(g2d);
	    }
	    else afficheHorizontal(g2d);
	}
	
	@SuppressWarnings("null")
	public void afficheVertical(Graphics2D g2d){
		Dimension minimumSize = new Dimension(LARG*numberOfDays+60,HAUT*2*(HOUR-7));
		this.setSize(minimumSize);
		
		int j=8;
	    Line2D ligne = null;
	    
	    g2d.setPaint(new Color(238,243,249));//205, 255, 222));
	    g2d.fillRect(50, HAUT, (LARG*numberOfDays), HAUT*((HOUR-8)*2)+1);
	    g2d.setPaint(Color.black);
	    for(int i=1; i < ((HOUR-8)*2)+2	; i++){
	    	g2d.setStroke(none);
	    	ligne = new Line2D.Double(50,i*HAUT,50+(LARG*numberOfDays),i*HAUT);
	    	if(i%2==0){
	    		g2d.drawString((j-1)+"h30", 15, (i*HAUT)+5);
	    		g2d.setPaint(Color.red);
	    	}
	    	else{
	    		g2d.setPaint(Color.black);
	    		g2d.draw(ligne);
	    		ligne = new Line2D.Double(50,i*HAUT+1,50+(LARG*numberOfDays),i*HAUT+1);
	    		g2d.drawString((j)+"h", 25, (i*HAUT)+5);
	    		j++;
	    	}
	    	g2d.draw(ligne);
	    	g2d.setPaint(Color.black);
	    	if(i!=((HOUR-8)*2)+1)
	    	{
		    	g2d.setStroke(dashed);
		    	g2d.setPaint(Color.black);
		    	ligne = new Line2D.Double(50,i*HAUT+HAUT/2,50+(LARG*numberOfDays),i*HAUT+HAUT/2);
		    	g2d.draw(ligne);
		    	g2d.setStroke(none);
	    	}
	    } 
	    Line2D vert = null; 
	    vert = new Line2D.Double(ligne.getX1(),HAUT/2,50,ligne.getY2()+5);
	    g2d.draw(vert);
	    vert = new Line2D.Double(ligne.getX1()+1,HAUT/2,50+1,ligne.getY2()+5);
	    g2d.draw(vert);
	    
	    for(int i=1; i<(numberOfDays+1); i++)
	    {
	    	vert = new Line2D.Double(50+(i*LARG),HAUT/2,50+(i*LARG),ligne.getY2()+5);
		    g2d.draw(vert);
		    if(i == numberOfDays){
		    	vert = new Line2D.Double(50+(i*LARG)+1, HAUT/2, 50+(i*LARG)+1, ligne.getY2()+5);
			    g2d.draw(vert);
		    }
	    }
	    createFromList(g2d, false);
	    createFromString(g2d, false);
	}
	
	public void setHAUT(int haut) {
		HAUT = haut;
		Dimension minimumSize = new Dimension(LARG*numberOfDays+80,HAUT*2*(HOUR-7)+50);
		this.setPreferredSize(minimumSize);
	}

	public void setLARG(int larg) {
		LARG = larg;
		Dimension minimumSize = new Dimension(LARG*numberOfDays+80,HAUT*2*(HOUR-7)+50);
		this.setPreferredSize(minimumSize);
	}

	public void afficheHorizontal(Graphics2D g2d){
		Dimension minimumSize = new Dimension(HAUT*2*(HOUR-7), LARG*numberOfDays+60);
		this.setSize(minimumSize);
		
		int j=8;
	    Line2D ligne = new Line2D.Double(0,0,0,0);
	    g2d.setPaint(new Color(238,243,249));
	    g2d.fillRect(HAUT, 50, HAUT*((HOUR-8)*2)+1, (LARG*numberOfDays));
	    g2d.setPaint(Color.black);
	    
	    for(int i=1; i < ((HOUR-8)*2)+2	; i++){
	    	g2d.setStroke(none);
	    	ligne = new Line2D.Double(i*HAUT, 50, i*HAUT, 50+(LARG*numberOfDays));
	    	if(i%2==0){
	    		centerdText(g2d, (j-1)+"h30", i*HAUT, HAUT+5);
	    		g2d.setPaint(Color.red);
	    	}
	    	else{
	    		g2d.setPaint(Color.black);
	    		g2d.draw(ligne);
	    		ligne = new Line2D.Double(i*HAUT+1, 50, i*HAUT+1, 50+(LARG*numberOfDays));
	    		centerdText(g2d, (j)+"h", i*HAUT, HAUT+5);
	    		j++;
	    	}
	    	g2d.draw(ligne);
	    	g2d.setPaint(Color.black);
	    	
	    	if(i!=((HOUR-8)*2)+1)
	    	{
		    	g2d.setStroke(dashed);
		    	g2d.setPaint(Color.black);
		    	ligne = new Line2D.Double(i*HAUT+HAUT/2, 50, i*HAUT+HAUT/2, 50+(LARG*numberOfDays));
		    	g2d.draw(ligne);
		    	g2d.setStroke(none);
	    	}
	    } 
	    
	    Line2D horiz = null; 
	    horiz = new Line2D.Double(HAUT,50,ligne.getX2()+5,50);
	    g2d.draw(horiz);
	    horiz = new Line2D.Double(HAUT,50+1,ligne.getX2()+5,50+1);
	    g2d.draw(horiz);
	    
	    for(int i=1; i<(numberOfDays+1); i++)
	    {
	    	horiz = new Line2D.Double(HAUT,50+(i*LARG),ligne.getX2()+5,50+(i*LARG));
		    g2d.draw(horiz);
		    if(i == numberOfDays){
		    	horiz = new Line2D.Double(HAUT, 50+(i*LARG)+1, ligne.getX2()+5, 50+(i*LARG)+1);
			    g2d.draw(horiz);
		    }
	    }

	    createFromList(g2d, true);
	    createFromString(g2d, true);
	}
	
	private void centerdText(Graphics2D g2, String s, float centerX, float baselineY, boolean horiz){
		FontRenderContext frc = g2.getFontRenderContext();
		Rectangle2D bounds = g2.getFont().getStringBounds(s, frc);
		float width = (float)bounds.getWidth();
		
		AffineTransform saveXform = g2.getTransform();
		if(horiz==true){
			AffineTransform textAt = new AffineTransform();
			textAt.rotate(Math.toRadians(270));
			//g2.transform(textAt);
			g2.setTransform(AffineTransform.getRotateInstance(Math.toRadians(270)));
			g2.setColor(Color.BLUE);
		}
		g2.drawString(s, centerX - width/2, baselineY);
		g2.setTransform(saveXform);
	}
	
	private void centerdText(Graphics2D g2, String s, float centerX, float baselineY){
		FontRenderContext frc = g2.getFontRenderContext();
		Rectangle2D bounds = g2.getFont().getStringBounds(s, frc);
		float width = (float)bounds.getWidth();
		float height = (float)bounds.getHeight();
		//g2.drawString(s, centerX - width/2, baselineY);
		g2.drawString(s, centerX - width/2, baselineY + height/2);
	}
	
	private void createFromList(Graphics2D g2d, boolean horiz){
		for(int i=0; i<listeDePeriode.size(); i++){
			Period c = listeDePeriode.get(i);
			if(horiz==false){
				createHour(g2d, c);
			}
			else{
				createHourHoriz(g2d, c);
			}
		}
	}
	
	private void createFromString(Graphics2D g2d, boolean horiz){
		int limiteJour;
		if(numberOfDays<listeDeJours.size()){
			limiteJour = numberOfDays;
		}
		else limiteJour = listeDeJours.size();
		
		if(horiz==false){
			for(int i=0; i<limiteJour; i++){
				centerdText(g2d, listeDeJours.get(i), 50+LARG/2+LARG*i, HAUT-5, false);
			}
		}
		else{
			for(int i=0; i<limiteJour; i++){
				centerdText(g2d, "Lundi", 25, HAUT-5, true);
			    centerdText(g2d, "Mardi", (LARG*4-100)/2, HAUT-5, true);
			    centerdText(g2d, "Mercredi", (LARG*6-100)/2, HAUT-5, true);
			    centerdText(g2d, "Jeudi", (LARG*8-100)/2, HAUT-5, true);
			    centerdText(g2d, "Vendredi", (LARG*10-100)/2, HAUT-5, true);
			}
		}
	}

	private void fillRect(){
			this.add(new Period(4, 8, 1));
			this.add(new Period(2, 13, 1));
			this.add(new Period(1, 8.75, 2));
			this.add(new Period(1, 10.25, 2));
			this.add(new Period(3, 8, 3));
			this.add(new Period(1, 11, 3));
			listeDePeriode.get(1).setPriority(1);
			listeDePeriode.get(2).setPriority(2);
			listeDePeriode.get(5).setPriority(1);
			

		    listeDeJours.add("Lundi");
		    listeDeJours.add("Mardi");
		    listeDeJours.add("Mercredi");
		    listeDeJours.add("Jeudi");
		    listeDeJours.add("Vendredi");
	}
	
	public void createHour(Graphics2D g2d, Period c){
		/*setColor1Box(12, 135, 12);
		setColor2Box(232, 35, 12);
		setColor1Header(12, 12, 13);
		setColor2Header(156, 12, 156);
		setCouleurConflits(236, 0, 237);*/
		
		double heure = c.getDuree()*2;
		double debut = ((c.getDebut()-8)*2)+1;
		
		Color coul;
		if(c.getPriority()==1){
			coul = couleur1Boxpri1;
		}else if(c.getPriority()==2){
			coul = couleur1Boxpri2;
		}else coul = couleur1Box;
		
		GradientPaint col = new GradientPaint(new Point2D.Double(50+LARG*(c.getJour()-1), HAUT*debut), coul, new Point2D.Double(50+LARG*(c.getJour()-1)+LARG, HAUT*debut), couleur2Box);
		RoundRectangle2D rect = new RoundRectangle2D.Double(50+LARG*(c.getJour()-1),HAUT*debut,LARG,HAUT*heure, 20,20);
		g2d.setPaint(col);

		g2d.fill(rect);
		
		Area one = new Area(new RoundRectangle2D.Double(50+LARG*(c.getJour()-1),HAUT*debut,LARG,HAUT*heure, 20,20));
		Area two = new Area(new Rectangle2D.Double((50+LARG*(c.getJour()-1)),(HAUT*debut+((HAUT*3)/4)),LARG,HAUT*heure));
		one.subtract(two);

		GradientPaint grad = new GradientPaint(new Point2D.Double(50+LARG*(c.getJour()-1), HAUT*debut), couleur1Header, new Point2D.Double(50+LARG*(c.getJour()-1), (HAUT*debut+((HAUT*3)/4))-((HAUT*debut+(((HAUT*3)/4))-(HAUT*debut))/2)), couleur2Header, true);
		g2d.setPaint(grad);
		g2d.fill(one);
		
		g2d.setPaint(new Color(0, 78, 107));
		g2d.draw(rect);
		
		g2d.setPaint(new Color(0, 78, 107));
		g2d.drawLine((50+LARG*(c.getJour()-1)), (int)(HAUT*debut+((HAUT*3)/4)), (50+LARG*c.getJour()), (int)(HAUT*debut+((HAUT*3)/4)));
		g2d.setPaint(Color.black);
		
		AttributedString as = new AttributedString(c.getNom());
		as.addAttribute(TextAttribute.SIZE,(HAUT/2));
		g2d.drawString(as.getIterator(), (50+LARG*(c.getJour()-1))+5, (int)(HAUT*debut+((HAUT*3)/4))-5);
		
		Rectangle2D rec = new Rectangle2D.Double((50+LARG*(c.getJour()-1)+(LARG)/3)+1, HAUT*debut+((HAUT*3)/4)+1, LARG*2/3, HAUT*3/4);
		g2d.setPaint(couleurConflits);
		g2d.fill(rec);
		
		g2d.setPaint(Color.WHITE);
		//Ligne vertical devant la boite :
		g2d.drawLine(((50+LARG*(c.getJour()-1)+(LARG)/3)+1), (int)(HAUT*debut+((HAUT*3)/4)), ((50+LARG*(c.getJour()-1)+(LARG)/3)+1), (int)(HAUT*debut+((HAUT*3)/4)+HAUT*3/4));
		//Ligne horizontale sous la boite :
		g2d.drawLine(((50+LARG*(c.getJour()-1)+(LARG)/3)+1), (int)(HAUT*debut+((HAUT*3)/4)+HAUT*3/4), ((50+LARG*(c.getJour()-1)+(LARG)/3)+1+LARG*2/3), (int)(HAUT*debut+((HAUT*3)/4)+HAUT*3/4));
		
		g2d.setPaint(Color.black);
		g2d.drawLine(((50+LARG*(c.getJour()-1)+(LARG)/3)+1), (int)(HAUT*debut+((HAUT*3)/4)), ((50+LARG*(c.getJour()-1)+(LARG)/3)+1+LARG*2/3), (int)(HAUT*debut+((HAUT*3)/4)));
		
		g2d.setPaint(Color.PINK);
		centerdText(g2d, c.getConflit1()+"", ((50+LARG*(c.getJour()-1)+(LARG)/3)+((LARG/3)/2)), (float)((HAUT*debut+((HAUT*3)/4)+HAUT*3/8)));
		g2d.setPaint(Color.RED);
		centerdText(g2d, c.getConflit2()+"", ((50+LARG*(c.getJour()-1)+(LARG)/3)+(LARG/3)), (float)((HAUT*debut+((HAUT*3)/4)+HAUT*3/8)));
		g2d.setPaint(Color.BLUE);
		centerdText(g2d, c.getConflit3()+"", ((50+LARG*(c.getJour()-1)+(LARG)/3)+((LARG/3)*3/2)), (float)((HAUT*debut+((HAUT*3)/4)+HAUT*3/8)));
		g2d.setPaint(Color.black);
	}

	public void createHourHoriz(Graphics2D g2d, Period c){//double begin, double duree, double day, String title){
		double heure = c.getDuree()*2;
		double debut = ((c.getDebut()-8)*2)+1;
		
		Color coul;
		if(c.getPriority()==1){
			coul = couleur1Boxpri1;
		}else if(c.getPriority()==2){
			coul = couleur1Boxpri2;
		}else coul = couleur1Box;
		
		GradientPaint col = new GradientPaint(new Point2D.Double(HAUT*debut,50+LARG*(c.getJour()-1)), coul, new Point2D.Double(HAUT*debut, 50+LARG*(c.getJour()-1)+LARG), couleur2Box);
		RoundRectangle2D rect = new RoundRectangle2D.Double(HAUT*debut,50+LARG*(c.getJour()-1), HAUT*heure, LARG,20,20);
		g2d.setPaint(col);
		g2d.fill(rect);
		
		Area one = new Area(new RoundRectangle2D.Double(HAUT*debut,50+LARG*(c.getJour()-1), HAUT*heure, LARG,20,20));
		Area two = new Area(new Rectangle2D.Double((HAUT*debut+((HAUT*3)/4)),(50+LARG*(c.getJour()-1)),HAUT*heure,LARG));
		one.subtract(two);
		GradientPaint grad = new GradientPaint(new Point2D.Double(HAUT*debut,50+LARG*(c.getJour()-1)), couleur1Header, new Point2D.Double((HAUT*debut+((HAUT*3)/4))-((HAUT*debut+(((HAUT*3)/4))-(HAUT*debut))/2), 50+LARG*(c.getJour()-1)), couleur2Header, true);
		g2d.setPaint(grad);
		g2d.fill(one);
		
		g2d.setPaint(new Color(0, 78, 107));
		g2d.draw(rect);
		
		g2d.setPaint(new Color(0, 78, 107));
		g2d.drawLine((int)(HAUT*debut+((HAUT*3)/4)), (50+LARG*(c.getJour()-1)), (int)(HAUT*debut+((HAUT*3)/4)), (50+LARG*c.getJour()));
		g2d.setPaint(Color.black);
		
		AttributedString as = new AttributedString(c.getNom());
		as.addAttribute(TextAttribute.SIZE, HAUT/2);
		centerdText(g2d, c.getNom(), (float)(debut*HAUT+HAUT*3/8),(50+20+LARG*(c.getJour()-1)));
	}
	
	public int add(Period c){
		LinkedList<Period> nouveau = new LinkedList<Period>();
		int valeur;
		
		if(listeDePeriode.size()==0){
			c.setNom("P1");
			listeDePeriode.add(c);
			valeur = 0;
		}
		else{
			int i=0;
			while(i<listeDePeriode.size() && (listeDePeriode.get(i).getJour()<c.getJour() || (listeDePeriode.get(i).getJour()==c.getJour() && listeDePeriode.get(i).getDebut() <= c.getDebut()))){
				nouveau.add(listeDePeriode.get(i));
				i++;	
			}
			c.setNom("P"+(i+1));
			nouveau.add(c);
			for(int j = i; j<listeDePeriode.size(); j++)
			{
				Period cr = listeDePeriode.get(j);
				cr.setNom("P"+(j+2));
				nouveau.add(cr);
			}

			listeDePeriode = nouveau;
			valeur = i;
		}
		return valeur;
	}
	
	public void remove(int indice){
		LinkedList<Period> nouveau = new LinkedList<Period>();
		
		for(int i=0; i<listeDePeriode.size() ;i++){
			if(i != indice){
				nouveau.add(listeDePeriode.get(i));
				if(i<indice){
					nouveau.get(i).setNom("P"+(i+1));
				}
				else nouveau.get(i-1).setNom("P"+(i));
			}
		}
		listeDePeriode = nouveau;
	}
	
	public void remove(String indice){
		LinkedList<Period> nouveau = new LinkedList<Period>();
		int j=0;
		for(int i=0; i<listeDePeriode.size() ;i++){
			if(!listeDePeriode.get(i).getNom().equals(indice)){
				listeDePeriode.get(i).setNom("P"+(j+1));
				nouveau.add(listeDePeriode.get(i));
				j++;
			}
		}
		listeDePeriode = nouveau;
	}
	
	public int convert(String nom){
		int i=0;
		while(i<listeDePeriode.size() && !listeDePeriode.get(i).getNom().equals(nom)){
			i++;
		}
		return i;
	}
	
	public LinkedList<Integer> selection(int jour, double heure){
		LinkedList<Integer> select = new LinkedList<Integer>();
		int i=0;
		
		while(i<listeDePeriode.size() && listeDePeriode.get(i).getJour() <= jour){
			if(listeDePeriode.get(i).getJour() == jour){
				if(listeDePeriode.get(i).getDebut()<=heure && listeDePeriode.get(i).getDebut()+listeDePeriode.get(i).getDuree()>heure){
					select.add(i);
				}
			}
			i++;
		}
		return select;
	}
	
	public LinkedList<Period> getListeDeCours() {
		return listeDePeriode;
	}

	public int getHAUT() {
		return HAUT;
	}

	public int getLARG() {
		return LARG;
	}

	public int getHOUR() {
		return HOUR;
	}

	public void build(){
		l = new MousePressListener(this);
		if (this.getMouseListeners().length < 1)
		{
			this.addMouseListener(l);
			this.addMouseMotionListener((MouseMotionListener)l);
		}
	}
	
	public int getNumberOfDays() {
		return numberOfDays;
	}

	/*public void update(Observable o, Object arg) {
		System.out.println("bg.update");
		repaint();
	}*/

	public int present(double debut, double jour){
		int retour=0;
		boolean trouve = false;
		
		while(retour<listeDePeriode.size() && trouve==false){
			if(listeDePeriode.get(retour).getDebut()==debut && listeDePeriode.get(retour).getJour()==jour){
				trouve=true;
			}
			else{
				retour++;
			}
		}
		if(trouve==false){
			retour = -1;
		}
		
		return retour;

	}
	
	public void setView(){
		if(horizontal==true)horizontal=false;
		else horizontal=true;
		repaint();
	}
		
	protected void clear(Graphics g) {
		super.paintComponent(g);
	}
}
