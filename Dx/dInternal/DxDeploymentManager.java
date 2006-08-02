/**
 * Created on Jul 28, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: DxDeploymentManager.java 
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
 */

package dInternal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import dInternal.dDeployment.DxConfigResource;
import dmains.Diamant;

// TODO Passer de la methode checkAndDeploy() aux methodes check() et deploy()
public class DxDeploymentManager {

	public static String deploymentTarget = System.getProperty("user.home");

	// path of the file which lists the ressources to deploy 
	private static String fileListPath;

	// list of ressources to deploy
	private Vector<DxConfigResource> vsFileNames;

	
	/**
	 * Constructor
	 */
	public DxDeploymentManager() {
		fileListPath = Diamant.class.getClassLoader() + File.separator
				+ "dInternal" + File.separator + "dDeployment" + File.separator
				+ "dDeploymentList";

		try {
			vsFileNames = listFile(new File(fileListPath));
		} catch (IOException e1) {
			// TODO catch block
			e1.printStackTrace();
		}

	}

	/**
	 * Checks if the parent of the ressource was already deployed to avoid
	 * IOExceptions
	 * 
	 * @param ressource
	 *            the ressource we want to check the parent
	 * @return trus if the parent is deployed, false else
	 */
	private boolean checkParent(DxConfigResource ressource) {
		DxConfigResource toCheck = ressource.getParent();

		return check(toCheck);
	}

	/**
	 * Method that checks if the ressource was already deployed
	 * 
	 * @param ressource
	 *            the ressource to check
	 * @return true if the ressource is deployed, false else
	 */
	private boolean check(DxConfigResource ressource) {
		boolean toReturn = false;
		File fToDeploy = ressource.getRessourceAsFile();

		if (checkParent(ressource)) {
			if (fToDeploy.exists()) {
				// check that the file and the ressource to deploy are the same
				if ((fToDeploy.isDirectory() && ressource.isDirectory())
						|| (fToDeploy.isFile() && ressource.isFile())) {
					toReturn = true;
				}
			}
		}

		return toReturn;
	}

	/**
	 * Deploys a ressource from the jar of the application
	 * 
	 * @param ressource
	 *            the resssource to deploy
	 * @throws IOException
	 */
	private void deploy(DxConfigResource ressource) throws IOException {
		File fToDeploy = ressource.getRessourceAsFile();

		// create the file
		if (ressource.isDirectory()) {
			fToDeploy.mkdir();
		} else {
			fToDeploy.createNewFile();
		}

		// the classloader is used to extract ressource from the jar
		ClassLoader clLoader = Diamant.class.getClassLoader();

		InputStream isDeploy = clLoader.getResourceAsStream(ressource
				.getClassLoaderPath());
		OutputStream osDeploy = null;

		osDeploy = new FileOutputStream(fToDeploy);

		// Copy from the jar to the filesystem
		byte[] buf = new byte[1024];
		int len;
		try {
			while ((len = isDeploy.read(buf)) > 0) {
				osDeploy.write(buf, 0, len);
			}
		} catch (IOException e) {
			// TODO Modifier la facon de faire le log
			System.out.println("Impossible de lire");
		}
		try {
			isDeploy.close();
			osDeploy.close();
		} catch (IOException e) {
			// TODO Modifier la facon de faire le log
			System.out.println("Bordel de merde, j'en ai mare");
		}
	}

	
	
	/**
	 * Check if a list of ressources is deployed and deploys those who arn't from the jar of the application
	 */
	public void checkAndDeploy() {
		Iterator<DxConfigResource> itList = vsFileNames.iterator();

		// If Files do not exist then Creation of them
		while (itList.hasNext()) {
			DxConfigResource sCurrentFile = itList.next();
			// Check that File exist
			if (!check(sCurrentFile)) {
				// else creation of File
				try {
					deploy(sCurrentFile);
				} catch (IOException e) {
					// TODO catch bloc
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * List the paths of ressources written in a file
	 * 
	 * @param fileListPath
	 *            the file where ressources'paths are listed
	 *            
	 * @return Vector<DxConfigResource>
	 * 			   list of ressources
	 */
	public Vector<DxConfigResource> listFile(File fileListPath)
			throws IOException {
		// create the list of all file's name
		Vector<DxConfigResource> vsFileNames = new Vector<DxConfigResource>();

		// opening of the file which lists all names of the files
		BufferedReader fList = null;
		InputStream isList = new FileInputStream(fileListPath);

		fList = new BufferedReader(new InputStreamReader(isList));
		String cheminFich;
		String parentName;
		DxConfigResource parent = new DxConfigResource("", true);

		// reading list
		while ((cheminFich = fList.readLine()) != null) {
			StringTokenizer stCheminFich = new StringTokenizer(cheminFich, "/");
			parentName = stCheminFich.nextToken();

			// Change of object Parent when his name change
			if (parent.getPath().equals(parentName)) {
				parent = new DxConfigResource(parentName, true);
				vsFileNames.add(parent);
			}

			// Add Children to the list
			vsFileNames.add(new DxConfigResource(parent, stCheminFich
					.nextToken(), false));
		}

		// closing list's File
		fList.close();

		return vsFileNames;

	}

	
	/**
	 * Access to the list of ressources
	 * 
	 * @return Vector<DxConfigResource>
	 * 			   list of ressources
	 */
	public Vector<DxConfigResource> getListRessource()
	{
		return vsFileNames;
	}
	
	/*
	 * public void checkAndDeploy() { Vector<String> vsFileNames = new Vector<String>(); //
	 * Listage des noms de fichiers pour le systeme de fichier
	 * vsFileNames.add("pref" + File.separator + "pref.txt");
	 * vsFileNames.add("pref" + File.separator + "DXcaracteristics.sig");
	 * vsFileNames.add("pref" + File.separator + "DXfunctions.sig");
	 * vsFileNames.add("pref" + File.separator + "logoDiamant.gif");
	 * vsFileNames.add("pref" + File.separator + "room_function.xml");
	 * vsFileNames.add("pref" + File.separator + "StandardTTC.xml");
	 * vsFileNames.add("pref" + File.separator + "StandardTTE.xml");
	 * 
	 * vsFileNames.add("trace" + File.separator + "log4j.conf");
	 * vsFileNames.add("trace" + File.separator + "log4jreex.conf");
	 * vsFileNames.add("trace" + File.separator + "trace.log"); // Listage des
	 * noms de fichiers pour le jar Vector<String> vsClassLoader = new Vector<String>();
	 * vsClassLoader.add("pref" + "/" + "pref.txt"); vsClassLoader.add("pref" +
	 * "/" + "DXcaracteristics.sig"); vsClassLoader.add("pref" + "/" +
	 * "DXfunctions.sig"); vsClassLoader.add("pref" + "/" + "logoDiamant.gif");
	 * vsClassLoader.add("pref" + "/" + "room_function.xml");
	 * vsClassLoader.add("pref" + "/" + "StandardTTC.xml");
	 * vsClassLoader.add("pref" + "/" + "StandardTTE.xml");
	 * 
	 * vsClassLoader.add("trace" + "/" + "log4j.conf");
	 * vsClassLoader.add("trace" + "/" + "log4jreex.conf");
	 * vsClassLoader.add("trace" + "/" + "trace.log"); // Verification que le
	 * dossier pref exist sinon creation File fDir = new File(deploymentTarget +
	 * File.separator + "pref"); if (!(fDir.exists() && fDir.isDirectory())) {
	 * fDir.mkdir(); } // Verification que le dossier trace exist sinon creation
	 * fDir = new File(deploymentTarget + File.separator + "trace"); if
	 * (!(fDir.exists() && fDir.isDirectory())) { fDir.mkdir(); }
	 * 
	 * Iterator<String> itNames = vsFileNames.iterator(); Iterator<String>
	 * itClass = vsClassLoader.iterator(); while (itNames.hasNext()) { String
	 * sCurrentFile = itNames.next(); File fPref = new File(deploymentTarget +
	 * File.separator + sCurrentFile); if (!(fPref.exists())) {
	 * 
	 * try { fPref.createNewFile(); System.out.println("CreateFile fonctionne"); }
	 * catch (IOException e) { System.out.println("CreateFile n'a pas marche"); }
	 * 
	 * ClassLoader clLoader = Diamant.class.getClassLoader(); InputStream isPref =
	 * clLoader.getResourceAsStream(itClass .next());
	 * 
	 * if (isPref == null) { System.out.println("Classloader en probleme"); }
	 * OutputStream osPref = null; try { osPref = new FileOutputStream(System
	 * .getProperty("user.home") + File.separator + sCurrentFile);
	 * System.out.println("Fichier de sortie ouvert"); } catch
	 * (FileNotFoundException e) { System.out.println("Impossible d'ouvrir le
	 * fichier"); }
	 * 
	 * byte[] buf = new byte[1024]; int len; try { while ((len =
	 * isPref.read(buf)) > 0) { osPref.write(buf, 0, len); } } catch
	 * (IOException e) { System.out.println("Impossible de lire"); } try {
	 * isPref.close(); osPref.close(); } catch (IOException e) {
	 * System.out.println("Bordel de merde, j'en ai mare"); } } } }
	 */

}
