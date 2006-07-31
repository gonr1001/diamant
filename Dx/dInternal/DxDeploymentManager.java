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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Vector;

import dInternal.dDeployment.DxConfigResource;
import dmains.Diamant;
//TODO Passer de la methode checkAndDeploy() aux methodes check() et deploy()
public class DxDeploymentManager {

    public static String deploymentTarget = System.getProperty("user.home");

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
    public boolean check(DxConfigResource ressource) {
        boolean toReturn = false;
        File fToDeploy = ressource.getRessourceAsFile();

        if (checkParent(ressource)) {
            if (fToDeploy.exists()) {
                // check that the file and the ressource to deploy are the same
                if ((fToDeploy.isDirectory() && ressource.isDirectory())
                        || (!fToDeploy.isDirectory() && !ressource
                                .isDirectory())) {
                    toReturn = true;
                }
            }
        }

        return toReturn;
    }

    /**
     * Deploys a ressource from the jar of the application
     * @param ressource the resssource to deploy
     * @throws IOException
     */
    public void deploy(DxConfigResource ressource) throws IOException {
        File fToDeploy = ressource.getRessourceAsFile();

        //create the file
        if (ressource.isDirectory()) {
            fToDeploy.mkdir();
        } else {
            fToDeploy.createNewFile();
        }
        
        //the classloader is used to extract ressource from the jar
        ClassLoader clLoader = Diamant.class.getClassLoader();
        
        InputStream isDeploy = clLoader.getResourceAsStream(ressource
                .getClassLoaderPath());
        OutputStream osDeploy = null;

        try {
            osDeploy = new FileOutputStream(fToDeploy);
        } catch (FileNotFoundException e) {
            // TODO: handle exception
        }

        //Copy from the jar to the filesystem
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

    
    
    public void checkAndDeploy() {
        Vector<String> vsFileNames = new Vector<String>();
        vsFileNames.add("pref" + File.separator + "pref.txt");
        vsFileNames.add("pref" + File.separator + "DXcaracteristics.sig");
        vsFileNames.add("pref" + File.separator + "DXfunctions.sig");
        vsFileNames.add("pref" + File.separator + "logoDiamant.gif");
        vsFileNames.add("pref" + File.separator + "room_function.xml");
        vsFileNames.add("pref" + File.separator + "StandardTTC.xml");
        vsFileNames.add("pref" + File.separator + "StandardTTE.xml");

        vsFileNames.add("trace" + File.separator + "log4j.conf");
        vsFileNames.add("trace" + File.separator + "log4jreex.conf");
        vsFileNames.add("trace" + File.separator + "trace.log");

        Vector<String> vsClassLoader = new Vector<String>();
        vsClassLoader.add("pref" + "/" + "pref.txt");
        vsClassLoader.add("pref" + "/" + "DXcaracteristics.sig");
        vsClassLoader.add("pref" + "/" + "DXfunctions.sig");
        vsClassLoader.add("pref" + "/" + "logoDiamant.gif");
        vsClassLoader.add("pref" + "/" + "room_function.xml");
        vsClassLoader.add("pref" + "/" + "StandardTTC.xml");
        vsClassLoader.add("pref" + "/" + "StandardTTE.xml");

        vsClassLoader.add("trace" + "/" + "log4j.conf");
        vsClassLoader.add("trace" + "/" + "log4jreex.conf");
        vsClassLoader.add("trace" + "/" + "trace.log");

        File fDir = new File(System.getProperty("user.home") + File.separator
                + "pref");
        if (!(fDir.exists() && fDir.isDirectory())) {
            fDir.mkdir();
        }

        fDir = new File(System.getProperty("user.home") + File.separator
                + "trace");
        if (!(fDir.exists() && fDir.isDirectory())) {
            fDir.mkdir();
        }

        Iterator<String> itNames = vsFileNames.iterator();
        Iterator<String> itClass = vsClassLoader.iterator();
        while (itNames.hasNext()) {
            String sCurrentFile = itNames.next();
            File fPref = new File(System.getProperty("user.home")
                    + File.separator + sCurrentFile);
            if (!(fPref.exists())) {

                try {
                    fPref.createNewFile();
                    System.out.println("CreateFile fonctionne");
                } catch (IOException e) {
                    System.out.println("CreateFile n'a pas marche");
                }

                ClassLoader clLoader = Diamant.class.getClassLoader();
                InputStream isPref = clLoader.getResourceAsStream(itClass
                        .next());

                if (isPref == null) {
                    System.out.println("Classloader en probleme");
                }
                OutputStream osPref = null;
                try {
                    osPref = new FileOutputStream(System
                            .getProperty("user.home")
                            + File.separator + sCurrentFile);
                    System.out.println("Fichier de sortie ouvert");
                } catch (FileNotFoundException e) {
                    System.out.println("Impossible d'ouvrir le fichier");
                }

                byte[] buf = new byte[1024];
                int len;
                try {
                    while ((len = isPref.read(buf)) > 0) {
                        osPref.write(buf, 0, len);
                    }
                } catch (IOException e) {
                    System.out.println("Impossible de lire");
                }
                try {
                    isPref.close();
                    osPref.close();
                } catch (IOException e) {
                    System.out.println("Bordel de merde, j'en ai mare");
                }
            }
        }
    }
}
