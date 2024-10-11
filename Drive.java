import java.util.ArrayList;

public class Drive {
    ArrayList<Folder>folders = new ArrayList<>();
    ArrayList<File>filesOfDrive= new ArrayList<>();
    String name;
    int size;
    int freeSize;
    public Folder findFolderByName(String name){
        for (Folder f:
             folders) {
            if (f.name.equalsIgnoreCase(name)){
                return f;
            }
        }
        return null;
    }
    boolean isFolderOk(String name){
        for (Folder f: folders) {
            if (f.name.equalsIgnoreCase(name)){
                return false;
            }
        }
        return true;
    }
    void setFolders(Folder f){
        folders.add(f);
    }
    void setFilesOfDrive(File f){
        filesOfDrive.add(f);
    }
    boolean hasFile(String name){
        for (File f: filesOfDrive) {
            if (f.name.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    void removeFileByName(String name){
        int n = -1;
        for (int i = 0 ; i<filesOfDrive.size();i++){
            if (filesOfDrive.get(i).name.equalsIgnoreCase(name)){
                n=i;
            }
        }

        this.freeSize+=filesOfDrive.get(n).size;
        filesOfDrive.remove(n);
    }
    void removeFolderOfDrive(Folder f){
        int n = -1;
        for (int i = 0 ; i<folders.size();i++){
            if (folders.get(i).equals(f)){
                n=i;
            }
        }
        folders.remove(n);
    }
    void changeFileName(String oldName, String newName){
        for (File f: filesOfDrive) {
            if (f.name.equalsIgnoreCase(oldName)){
                f.name = newName;
            }
        }
    }
    void changeFolderName (String oldName , String newName ){
        for (Folder f:
             folders) {
            if (f.name.equalsIgnoreCase(oldName)){
                f.name = newName;
            }
        }
    }
    public File getFileByName(String name){
        for (File f: filesOfDrive) {
            if (f.name.equalsIgnoreCase(name)){
                return f;
            }
        }
        return null;
    }
    public Folder getFolderByName(String name){
        for (Folder f:
             folders) {
            if (f.name.equalsIgnoreCase(name)){
                return f;
            }
        }
        return null;
    }
}
