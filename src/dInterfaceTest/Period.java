package dInterfaceTest;


public class Period {

	private String nom;
	private double duree;
	private double debut;
	private int jour;
	private int conflit1;
	private int conflit2;
	private int conflit3;
	private int priority;
	//TODO private LinkedList<String> cours; a utiliser une fois l'integration dans diamant realisee
	

	public Period(double dureearg, double debutarg, int jourarg) {
		super();
		this.nom = null;
		this.duree = dureearg;
		this.debut = debutarg;
		this.jour = jourarg;
		this.conflit1 = 0;
		this.conflit2 = 0;
		this.conflit3 = 0;
		this.priority = 0;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priorityarg) {
		this.priority = priorityarg;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(String nomarg) {
		this.nom = nomarg;
	}
	
	public double getDuree() {
		return duree;
	}
	
	public void setDuree(double dureearg) {
		this.duree = dureearg;
	}
	
	public double getDebut() {
		return debut;
	}
	
	public void setDebut(double debutarg) {
		this.debut = debutarg;
	}
	
	public int getJour() {
		return jour;
	}
	
	public void setJour(int jourarg) {
		this.jour = jourarg;
	}

	public int getConflit1() {
		return conflit1;
	}

	public void setConflit1(int conflit1arg) {
		this.conflit1 = conflit1arg;
	}

	public int getConflit2() {
		return conflit2;
	}

	public void setConflit2(int conflit2arg) {
		this.conflit2 = conflit2arg;
	}

	public int getConflit3() {
		return conflit3;
	}

	public void setConflit3(int conflit3arg) {
		this.conflit3 = conflit3arg;
	}
}
