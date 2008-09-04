package dInterfaceTest;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;


public class MenuAction implements ActionListener {

	private MousePressListener mouse;
	
	public MenuAction(MousePressListener m){
		mouse = m;
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		mouse.pop++;
		
        if (command.equals(IHMConstant.NAMEDAYS)) {
        	final int nbJours = mouse.getGrille().getNumberOfDays();
        	JPanel[] pane = new JPanel[nbJours];
        	final JTextField[] inputDebut= new JTextField[nbJours];
        	JLabel label[] = new JLabel[nbJours];
        	
        	for(int i=0; i<nbJours; i++){
        		pane[i] = new JPanel();
        		inputDebut[i] = new JTextField(10);
        		label[i] = new JLabel("Enter le nom du jour "+(i+1)+" : ");
        		pane[i].setLayout(new BorderLayout());
        		
        		pane[i].add(label[i],BorderLayout.WEST);
        		pane[i].add(inputDebut[i],BorderLayout.EAST);
        		if(i!=0){
        			pane[i-1].add(pane[i], BorderLayout.SOUTH);
        		}
        	}
        	
        	final JDialog boite = new JDialog();
			
			Button ok = new Button(IHMConstant.BOK);
			Button cancel = new Button(IHMConstant.BCANCEL);
			JPanel paneSud = new JPanel();
			paneSud.add(ok);
			paneSud.add(cancel);

			boite.add(pane[0],BorderLayout.NORTH);
			boite.add(paneSud, BorderLayout.SOUTH);
			
			class Valide implements ActionListener {
				public void actionPerformed(ActionEvent event) {
					event.toString();// to avoid warning
					LinkedList <String> listeDeJours = new LinkedList<String>();
					for(int i=0; i<nbJours;i++){
						listeDeJours.add(inputDebut[i].getText());
						mouse.getGrille().setListeDeJours(listeDeJours);
						mouse.getGrille().repaint();
					}
					boite.dispose();
				}
			}
			class Cancel implements ActionListener {
				public void actionPerformed(ActionEvent event) {
					event.toString();// to avoid warning
					boite.dispose();
				}
			}
			ActionListener listener = new Valide();
			ActionListener del = new Cancel();
			ok.addActionListener(listener);
			cancel.addActionListener(del);
			
			boite.pack();
			boite.setModal(true);
			boite.setVisible(true);
			boite.setResizable(false);
        }
        else if (command.equals(IHMConstant.VIEW)) {
            mouse.getGrille().setView();
        }
        else if (command.equals(IHMConstant.MODIFGR)) {
            final JDialog boite = new JDialog();
            
            SpinnerModel model = new SpinnerNumberModel(mouse.getGrille().getNumberOfDays(), 0, 10, 1);
            final JSpinner spinner = new JSpinner(model);
            JPanel pane = new JPanel();
            pane.setLayout(new BorderLayout());
            
            JLabel texte = new JLabel("Nombre de jours du cycle : ");
            JPanel paneCenter = new JPanel();
            paneCenter.add(texte);
            paneCenter.add(spinner);
            
            Button ok = new Button(IHMConstant.BOK);
            Button cancel = new Button(IHMConstant.BCANCEL);        
            JPanel paneSud = new JPanel();
            paneSud.add(ok);
            paneSud.add(cancel);
            
            class Valide implements ActionListener {
    			public void actionPerformed(ActionEvent event) {
    				event.toString();// to avoid warning
    					System.out.println(spinner.getValue());
    					mouse.getGrille().setNumberOfDays(((Integer)spinner.getValue()).intValue());
    				boite.dispose();
    			}
    		}
            class Cancel implements ActionListener {
        		public void actionPerformed(ActionEvent event) {
        			event.toString();// to avoid warning
        			boite.dispose();
        		}
        	}
    		ActionListener listener = new Valide();
    		ActionListener del = new Cancel();
    		ok.addActionListener(listener);
    		cancel.addActionListener(del);
            
            
            pane.add(paneCenter,BorderLayout.CENTER);
	        pane.add(paneSud,BorderLayout.SOUTH);
            boite.add(pane);
            boite.pack();
            boite.setModal(true);
            boite.setVisible(true);
            boite.setResizable(false);

            
        }
        else if (command.equals(IHMConstant.DELETE)) {
            mouse.remove();
        }
        else if (command.equals(IHMConstant.MODIF)) {
	        mouse.modif();
        }
        else if (command.equals("Consultation")) {
        	mouse.setConsult();
        }

	}

}
