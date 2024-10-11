import java.util.ArrayList;

public class Folder {
    ArrayList<Folder> foldersInFolder = new ArrayList<>();
    ArrayList<File>files = new ArrayList<>();
    String name;
    Drive drive;
    int opened = 0;
    boolean isFolderOk(String name){
        for (Folder f: foldersInFolder) {
            if (f.name.equalsIgnoreCase(name)){
                return false;
            }
        }
        return true;
    }
    void setFoldersInFolder(Folder f){
        foldersInFolder.add(f);
    }
    void setFile(File f){
        files.add(f);
    }
    boolean isFileOk(String name){
        for (File f: files) {
            if (f.name.equalsIgnoreCase(name)){
                return false;
            }
        }
        return true;
    }
    void removeFileByName(String name, Drive d){
        int n = -1;
        for (int i =0;i<files.size();i++){
            if (files.get(i).name.equalsIgnoreCase(name)){
                n = i;
            }
        }
        d.freeSize+=files.get(n).size;
        files.remove(n);
    }
    public Folder findFolderByName(String name){
        for (Folder f:
             foldersInFolder) {
            if (f.name.equalsIgnoreCase(name)){
                return f;
            }
        }
        return null;
    }
    void changeFileName(String oldName , String newName){
        for (File f: files) {
            if (f.name.equalsIgnoreCase(oldName)){
                f.name = newName;
            }
        }
    }
    void changeFolderName(String oldName , String newName){
        for (Folder f: foldersInFolder) {
            if (f.name.equalsIgnoreCase(oldName)){
                f.name = newName;
            }
        }
    }
    File getFileByName(String name){
        for (File f:
             files) {
            if (f.name.equalsIgnoreCase(name)){
                return f;
            }
        }
        return null;
    }
    Folder getFolderByName(String name){
        for (Folder f:
             foldersInFolder) {
            if (f.name.equalsIgnoreCase(name)){
                return f;
            }
        }
        return null;
    }
}
