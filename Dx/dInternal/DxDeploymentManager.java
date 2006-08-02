/**
 * Created on Jul 28, 2006
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import dInternal.dDeployment.DxConfigResource;
import dmains.Diamant;

// TODO Passer de la methode checkAndDeploy() aux methodes check() et deploy()
public class DxDeploymentManager {

	public static String deploymentTarget = System.getProperty("user.home");

	// path of the file which lists the ressources to deploy
	private static String _fileListPath = System.getProperty("user.dir")
			+ File.separator + "dInternal" + File.separator + "dDeployment"
			+ File.separator + "dDeploymentList";

	// list of ressources to deploy
	private Vector<DxConfigResource> _vsFileNames;

	private static Logger _logger = Logger.getLogger(Diamant.class.getName());

	/**
	 * Constructor
	 */
	public DxDeploymentManager() {
		try {
			_vsFileNames = new Vector<DxConfigResource>();
			listFile(new File(_fileListPath));
		} catch (IOException e1) {
			_logger
					.error("Unable to read the list of files required by Diamant's software");
		}

	}

	/**
	 * List the paths of ressources written in a file
	 * 
	 * @param fileListPath
	 *            the file where ressources'paths are listed
	 * 
	 * @return Vector<DxConfigResource> list of ressources
	 */
	public void listFile(File fileListPath) throws IOException {
		// opening of file which lists all files'paths
		BufferedReader fList = null;
		InputStream isList = new FileInputStream(fileListPath);

		fList = new BufferedReader(new InputStreamReader(isList));
		String cheminFich;
		String parentName;
		DxConfigResource parent = new DxConfigResource("", true);

		// reading list
		while ((cheminFich = fList.readLine()) != null) {
			StringTokenizer stCheminFich = new StringTokenizer(cheminFich, "/");

			// reading of parent path
			parentName = stCheminFich.nextToken();

			// Change of ressource Parent when his path change
			if (!(parent.getClassLoaderPath().equals(parentName))) {
				parent = new DxConfigResource(parentName, true);
				_vsFileNames.add(parent);
			}

			// Add Ressource to the list
			_vsFileNames.add(new DxConfigResource(parent, stCheminFich
					.nextToken(), false));
		}

		// closing list's File
		fList.close();

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
	 * Check if a list of ressources is deployed and deploys those who arn't
	 * from the jar of the application
	 */
	public void checkAndDeploy() {
		Iterator<DxConfigResource> itList = _vsFileNames.iterator();

		// If Files do not exist then Creation of them
		while (itList.hasNext()) {
			DxConfigResource sCurrentFile = itList.next();
			// Check that File exist

			if (!check(sCurrentFile)) {
				// else creation of File
				try {
					deploy(sCurrentFile);
				} catch (IOException e) {
					_logger.error("Unable to deploy File "+ sCurrentFile.getClassLoaderPath());
				}
			}
		}
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

		if (ressource.isDirectory() || checkParent(ressource)) {
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

			// the classloader is used to extract ressource from the jar
			ClassLoader clLoader = Diamant.class.getClassLoader();

			InputStream isDeploy = clLoader.getResourceAsStream(ressource
					.getClassLoaderPath());
			OutputStream osDeploy = null;

			osDeploy = new FileOutputStream(fToDeploy);

			// when we have to create a non empty file
			if (isDeploy != null) {
				// Copy from the jar to the filesystem
				byte[] buf = new byte[1024];
				int len;
				try {
					while ((len = isDeploy.read(buf)) > 0) {
						osDeploy.write(buf, 0, len);
					}
				} catch (IOException e) {
					_logger.error("Unabled to write on file "+fToDeploy.getPath());
				}
				try {
					isDeploy.close();
					osDeploy.close();
				} catch (IOException e) {
					_logger.error("Problem while closing File"+ fToDeploy.getPath());
				}
			}
		}
	}

	/**
	 * Access to the list of ressources
	 * 
	 * @return Vector<DxConfigResource> list of ressources
	 */
	public Vector<DxConfigResource> getListRessource() {
		return _vsFileNames;
	}

}
