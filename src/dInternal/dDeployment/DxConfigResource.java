/**
 *  Class representing a resource (file or folder) to deploy by Java Web Start
 * @author Vimenet Joël
 * @date 31/07/2006
 */
package dInternal.dDeployment;

import java.io.File;

public class DxConfigResource {
    
    private boolean _isDirectory;

    private DxConfigResource _parent;

    private String _ressourceName;

    /**
     * @param parent
     *            the parent of the resource
     * @param ressourceName
     *            the name of the resource
     * @param isDirectory
     *            true if the resource is a directory, false if a file
     */
    public DxConfigResource(DxConfigResource parent, String ressourceName,
            boolean isDirectory) {
        _parent = parent;
        _ressourceName = ressourceName;
        _isDirectory = isDirectory;
    }

    /**
     * @param ressourceName
     *            the name of the ressource
     * @param directory
     *            true if the ressource is a directory, false if a file
     */
    public DxConfigResource(String ressourceName, boolean directory) {
        _ressourceName = ressourceName;
        _isDirectory = directory;
    }

//    /**
//     * @param child
//     *            the ressource to add as a child
//     * @throws AddChildException 
//     */
//    public void addChild(DxConfigResource child) throws AddChildException {
//        if (!isDirectory()){
//            throw new AddChildException();
//        }
//        
//        _children.add(child);
//    }

    /**
     * @return the parent of the ressource
     */
    public DxConfigResource getParent() {
        return _parent;
    }

//    /**
//     * @return the children of the ressource
//     */
//    public Vector<DxConfigResource> getChildren() {
//        return _children;
//    }

    /**
     * Method that retrun the path of teh ressource as needed by the jar
     * 
     * @return the path with a "/" as separator
     */
    public String getClassLoaderPath() {
        String toReturn;
        if (_parent != null) {
            toReturn = _parent.getClassLoaderPath() + "/" + _ressourceName;
        } else {
            toReturn = _ressourceName;
        }
        return toReturn;
    }

    /**
     * Method that returns the path of the resource as needed by the FS of the
     * OS
     * 
     * @return the path of the resource formated for the OS
     */
    public String getPath() {
        String toReturn;
        if (_parent != null) {
            toReturn = _parent.getPath() + File.separator + _ressourceName;
        } else {
            toReturn = _ressourceName;
        }
        return toReturn;
    }

    /**
     * @return the file from the resource
     */
    public File getRessourceAsFile() {
        return new File(DxDeploymentManager.deploymentTarget + File.separator
                + getPath());
    }

    /**
     * @return true if the resource is a directory false else
     */
    public boolean isDirectory() {
        return _isDirectory;
    }
    
    /**
     * @return true if the resource is a file false else
     */
    public boolean isFile() {
        return !(_isDirectory);
    }
}
