import java.util.ArrayList;

public class marker {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> kinds = new ArrayList<>();
    ArrayList<Drive> driveList = new ArrayList<>();
    ArrayList<Folder> folderList = new ArrayList<>();
    ArrayList<File> fileList = new ArrayList<>();
    ArrayList<Drive> getDriveList(){
        return driveList;
    }
    ArrayList<Folder> getfolderList(){
        return folderList;
    }
    ArrayList<File> getFileList(){
        return fileList;
    }
    void setF(String name ,String kind){
        names.add(name);
        kinds.add(kind);
    }
    void clean(){
        names.clear();
        kinds.clear();
        driveList.clear();
        fileList.clear();
        fileList.clear();
    }
    void removeLastOne(){
        names.remove(names.size()-1);
        kinds.remove(kinds.size()-1);
        folderList.remove(folderList.size()-1);
    }
    void setDriveList(Drive d){
        driveList.add(d);

    }
    void setFolderList(Folder f){
        folderList.add(f);

    }
    void setFileList(File f){
        fileList.add(f);
    }

    boolean hasmark(){
        if (driveList.size() == 0){
            return false;
        }
        else return true;
    }
    String getType(){

        return kinds.get(kinds.size()-1);
    }
    Drive getLastDrive(){
        return driveList.get(driveList.size()-1);
    }
    Folder getLastFolder(){
        return folderList.get(folderList.size()-1);
    }
    File getLastFile(){
        return fileList.get(fileList.size()-1);
    }
    void deleteLastFolder(){
        folderList.remove(folderList.size()-1);
        names.remove(names.size()-1);
        kinds.remove(kinds.size()-1);
    }
    ArrayList<String> getNameOfMap(){
        return names;
    }
    ArrayList<String> getKindsOfMap(){
        return kinds;
    }



}
