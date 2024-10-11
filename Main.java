import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        ArrayList<Folder>allFolders = new ArrayList<>();
        ArrayList<String>addre = new ArrayList<>();


        ArrayList<ArrayList>addres = new ArrayList<>();
        ArrayList<addressByName>addressByNames = new ArrayList<>();
        String R_os = "\\s*install\\s+OS\\s+(?<name>\\S+)\\s+(?<version>\\S+)\\s*";
        String R_os2 = "\\s*(?<hardSize>\\d+)\\s+(?<drivesNum>\\d+)\\s*";
        String R_os3 = "\\s*(?<driveName>\\S+)\\s+(?<driveSize>\\d+)\\s*";
        String R_createFolder = "\\s*create\\s+folder\\s+(?<name>\\S+)\\s*";
        String R_GoTo = "\\s*go\\s+to\\s+drive\\s+(?<driveName>\\S+)\\s*";
        String R_createFile = "\\s*create\\s+file\\s+(?<name>\\S+)\\s+(?<format>\\S+)\\s+(?<size>\\d+)\\s*";
        Scanner scanner = new Scanner(System.in);
        String command;
        Matcher matcher;
        command = scanner.nextLine();
        OS open_os = null;
        Drive open_drive = null;
        Folder open_folder = null;
        File open_file = null;
        marker marker = new marker();
        int dN=0;
        while (!command.matches("\\s*end\\s*")){
            if (command.matches(R_os)){
                matcher = getMatcher(command, R_os);
                String name = matcher.group("name");
                String version = matcher.group("version");
                OS os = new OS();
                os.name = name;
                os.version = version;
                open_os = os;
            } else if (command.matches(R_os2)){
                matcher = getMatcher(command, R_os2);
                String hardS = matcher.group("hardSize");
                String drivesN = matcher.group("drivesNum");
                int hardSize = Integer.parseInt(hardS);
                int drivesNum = Integer.parseInt(drivesN);
                open_os.driveNum = drivesNum;
                dN = drivesNum;
                open_os.hardSize = hardSize;
            } else if (command.matches(R_os3)){
                if (dN>0) {
                    matcher = getMatcher(command, R_os3);
                    String driveName = matcher.group("driveName");
                    String driveS = matcher.group("driveSize");
                    int driveSize = Integer.parseInt(driveS);
                    if (open_os.isOkDrive(driveName)) {
                        if (open_os.isDrivesizeOk(driveSize)) {
                            Drive d = new Drive();
                            dN--;
                            d.name = driveName;
                            d.size = driveSize;
                            d.freeSize = driveSize;
                            open_os.setDrives(d);
                        } else System.out.println("insufficient hard size");
                    } else System.out.println("invalid name");
                }else System.out.println("invalid command");
            } else if (command.matches(R_createFolder)){
                matcher = getMatcher(command, R_createFolder);
                String name = matcher.group("name");
                name = name.trim();
                if (open_drive==null){
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }
                if (marker.getType().equals("Drive")){
                    if (open_drive.isFolderOk(name)){
                        System.out.println("folder created");
                        Folder f = new Folder();
                        f.name = name;
                        f.drive = open_drive;
                        open_drive.setFolders(f);
                        allFolders.add(f);
                        marker.setF(name,"Folder");
                        marker.setFolderList(f);

                  //      addressByName ad = new addressByName(marker.kinds,marker.names,marker.driveList,marker.folderList,marker.fileList,f.name);
                        addressByName ad = new addressByName();
                       ad.name = name;
                        ad.kindOfMap = marker.getKindsOfMap();
                        ad.nameOfMap = marker.getNameOfMap();
                        ad.driveListOP= marker.driveList;
                        ad.folderListOP = marker.getfolderList();
                        ad.fileListOP = marker.getFileList();
                        addressByNames.add(ad);
                        ArrayList<String>mark = marker.getNameOfMap();
                        addres.add(mark);
                       String adres="";
                        adres+=marker.names.get(0)+":";
                        for (int i =1 ; i<marker.names.size();i++){
                            adres+="\\"+marker.names.get(i);
                        }
                        addre.add(adres);
                        marker.removeLastOne();
                    } else System.out.println("folder exists with this name");
                } else if (marker.getType().equals("Folder")){
                    open_folder = marker.getLastFolder();
                    if (open_folder.isFolderOk(name)){
                        Folder f = new Folder();
                        f.name = name;
                        f.drive = open_drive;
                        open_folder.setFoldersInFolder(f);
                        allFolders.add(f);
                        marker.setF(name,"Folder");
                        marker.setFolderList(f);
                      //  addressByName ad = new addressByName();
                        addressByName ad = new addressByName();
                        ad.name = name;
                        ad.kindOfMap = marker.getKindsOfMap();
                        ad.nameOfMap = marker.getNameOfMap();
                        ad.driveListOP= marker.driveList;
                        ad.folderListOP = marker.getfolderList();
                        ad.fileListOP = marker.getFileList();
                        addressByNames.add(ad);
//                      addressByName ad = new addressByName(marker.kinds,marker.names,marker.driveList,marker.folderList,marker.fileList,name);
                       // addressByNames.add(ad);
                        ArrayList<String>mark = marker.getNameOfMap();
                        addres.add(mark);
                        String adres="";
                        adres+=marker.names.get(0)+":";
                        for (int i =1 ; i<marker.names.size();i++){
                            adres+="\\"+marker.names.get(i);
                        }
                        addre.add(adres);

                        marker.removeLastOne();
                        System.out.println("folder created");
                    } else System.out.println("folder exists with this name");
                }

            } else if (command.matches(R_GoTo)){
                matcher = getMatcher(command, R_GoTo);
                String name = matcher.group("driveName");
                name = name.trim();
                if (open_os.hasDrive(name)){
                    open_drive = open_os.getDriveByName(name);
                    open_file=null;
                    open_folder=null;
                    marker.clean();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }else System.out.println("invalid name");
            } else if (command.matches("\\s*back\\s*")){
                if (!marker.getType().equals("Drive")){
                    if (marker.getType().equals("Folder")){
                        marker.deleteLastFolder();
                    }
                }
            } else if (command.matches(R_createFile)){
                matcher = getMatcher(command, R_createFile);
                String name = matcher.group("name");
                name = name.trim();
                String format = matcher.group("format");
                format = format.trim();
                String sizee = matcher.group("size");
                int size = Integer.parseInt(sizee);
                if (open_drive==null){
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }
//                System.out.println(marker.getType()+"77");
                if (marker.getType().equals("Drive")){
//                    System.out.println("in");
                    open_drive = marker.getLastDrive();
                    if (!open_drive.hasFile(name)){
                        if (format.equals("img") || format.equals("txt") || format.equals("mp4")){
                            if (open_drive.freeSize>=size){
                                open_drive.freeSize -= size;

                                if (format.equals("mp4")){
                                    System.out.println("Quality:");
                                    String quality = scanner.nextLine();
                                    System.out.println("Video Length:");
                                    String len = scanner.nextLine();
                                    File f = new videoFile(name, quality, len);
                                    f.size = size;
                                    open_drive.setFilesOfDrive(f);
                                } else if (format.equals("txt")){
                                    System.out.println("Text:");
                                    String text = scanner.nextLine();
                                    File f = new textFile(name , text);
                                    f.size = size;
                                    open_drive.setFilesOfDrive(f);
                                } else if (format.equals("img")){
                                    System.out.println("Resolution:");
                                    String resolution = scanner.nextLine();
                                    System.out.println("Extension:");
                                    String exten = scanner.nextLine();
                                    File f = new imgFile(name,resolution,exten);
                                    f.size = size;
                                    open_drive.setFilesOfDrive(f);
                                }
                            //    addressByName ad = new addressByName(marker.kinds,marker.names,marker.driveList,marker.folderList,marker.fileList,name);
                            //    addressByNames.add(ad);

                                System.out.println("file created");
                            }else System.out.println("insufficient drive size");
                        } else System.out.println("invalid format");
                    } else System.out.println("file exists with this name");
                } else if (marker.getType().equals("Folder")) {
                    open_folder = marker.getLastFolder();
                    if (open_folder.isFileOk(name)) {
                        if (format.equals("img") || format.equals("txt") || format.equals("mp4")) {
                            if (open_drive.freeSize >= size) {
                                    open_drive.freeSize -= size;
                                    if (format.equals("mp4")) {
                                        System.out.println("Quality:");
                                        String quality = scanner.nextLine();
                                        System.out.println("Video Length:");
                                        String len = scanner.nextLine();
                                        File f = new videoFile(name, quality, len);
                                        f.size = size;
                                        open_folder.setFile(f);
                                    } else if (format.equals("txt")) {
                                        System.out.println("Text:");
                                        String text = scanner.nextLine();
                                        File f = new textFile(name, text);
                                        f.size = size;
                                        open_folder.setFile(f);
                                    } else if (format.equals("img")) {
                                        System.out.println("Resolution:");
                                        String resolution = scanner.nextLine();
                                        System.out.println("Extension:");
                                        String exten = scanner.nextLine();
                                        File f = new imgFile(name, resolution, exten);
                                        f.size = size;
                                        open_folder.setFile(f);
                                    }
                                 //   addressByName ad = new addressByName(marker.kinds,marker.names,marker.driveList,marker.folderList,marker.fileList,name);
                                  //  addressByNames.add(ad);
                                 //   System.out.println(marker.names);
                                    System.out.println("file created");
                            } else System.out.println("insufficient drive size");
                        } else System.out.println("invalid format");
                    } else System.out.println("file exists with this name");
                }

            } else if (command.matches("\\s*open\\s(?<folderName>\\S+)\\s*")){
                matcher = getMatcher(command, "\\s*open\\s(?<folderName>\\S+)\\s*");
                String fName = matcher.group("folderName");
                fName = fName.trim();
                if (marker.getType().equals("Drive")){
                    if (!open_drive.isFolderOk(fName)){
                       // marker.clean();
                        for (int i =0;i<allFolders.size();i++){
                            if (allFolders.get(i).name.equals(fName) && allFolders.get(i).drive.equals(open_drive)){
//                                marker.kinds = addressByNames.get(i).kindOfMap;
//                                marker.names = addressByNames.get(i).nameOfMap;
//                                marker.folderList = addressByNames.get(i).folderListOP;
//                                marker.fileList = addressByNames.get(i).fileListOP;
//                              // marker.driveList.add(allFolders.get(i).drive);
//                                marker.driveList = addressByNames.get(i).driveListOP;
//                                allFolders.get(i).opened++;
//                                marker.setF(fName,"Folder");
//                                marker.setFolderList(allFolders.get(i));
                             //   addressByNames.get(i).printStatus();
                                marker.setF(fName,"Folder");
                                marker.setFolderList(allFolders.get(i));
                                allFolders.get(i).opened++;
                            }
                        }
                    } else System.out.println("invalid name");
                } else if (marker.getType().equals("Folder")){
                    Folder f = marker.getLastFolder();
                    if (!f.isFolderOk(fName)){
                      //  marker.clean();
                        for (int i =0;i<allFolders.size();i++){
                            if (allFolders.get(i).name.equals(fName) && allFolders.get(i).drive.equals(open_drive)){
//                                marker.kinds = addressByNames.get(i).kindOfMap;
//                                marker.names = addressByNames.get(i).nameOfMap;
//                                marker.folderList = addressByNames.get(i).folderListOP;
//                                marker.fileList = addressByNames.get(i).fileListOP;
//                                //marker.driveList.add(allFolders.get(i).drive);
//                                marker.driveList = addressByNames.get(i).driveListOP;
                                allFolders.get(i).opened++;
                                marker.setF(fName,"Folder");
                                marker.setFolderList(allFolders.get(i));
                               // addressByNames.get(i).printStatus();
                            }
                        }
                    } else System.out.println("invalid name");
                }
            }else if (command.matches("\\s*print\\s+frequent\\s+folders\\s*")){
                for (addressByName ad:
                     addressByNames) {
                  //  ad.printStatus();
                  //  System.out.println(ad.nameOfMap);
                }
                for (int i =0;i<addre.size();i++){
                //    System.out.println(addre.get(i));
                }
                ArrayList<Integer>tedad = new ArrayList<>();

                for (int i = 0; i<allFolders.size(); i++){
                    tedad.add(allFolders.get(i).opened);
                }
                ArrayList<String>ads = new ArrayList<>();
                ads.clear();
                for (int i = 0; i<allFolders.size(); i++){
                    ads.add(addre.get(i));
                }
               // System.out.println(tedad);
                Collections.sort(ads);
                Collections.sort(tedad,Collections.reverseOrder());
               // System.out.println(tedad);
                ArrayList<String>useAds = new ArrayList<>();
                int m = 0;
                for (int i =0 ; i< Math.min(4,tedad.size()-1) ;i++){

                    int n = (int)tedad.get(i);
                   // System.out.println(n);
                    for (int j = 0; j<ads.size();j++){
                        for (int z =0 ; z<allFolders.size();z++){
                            boolean chek = true;
                            for (int k =0 ; k < useAds.size();k++){
                                if (useAds.get(k).equals(ads.get(j))){
                                    chek = false;
                                }
                            }
                            if (ads.get(j).equals(addre.get(z)) && allFolders.get(z).opened == n && chek && n>0 && m<5){
                                System.out.println(ads.get(j)+" "+n);
                                m++;
                                useAds.add(ads.get(j));
                            }
                        }
                    }
                }


            } else if (command.matches("\\s*delete\\s+file\\s+(?<fileName>\\S+)\\s*")) {
                matcher = getMatcher(command, "\\s*delete\\s+file\\s+(?<fileName>\\S+)\\s*");
                String fileName = matcher.group("fileName");
                fileName = fileName.trim();
                if (open_drive == null) {
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }
                if (marker.getType().equals("Drive")) {
                    open_drive = marker.getLastDrive();
                    if (open_drive.hasFile(fileName)) {
                        open_drive.removeFileByName(fileName);
                        System.out.println("file deleted");
                    }else System.out.println("invalid name");
                }else if (marker.getType().equals("Folder")) {
                    open_folder = marker.getLastFolder();
                    if (!open_folder.isFileOk(fileName)) {
                        open_folder.removeFileByName(fileName,open_drive);
                        System.out.println("file deleted");
                    }else System.out.println("invalid name");
                }
            } else if (command.matches("\\s*delete\\s+folder\\s+(?<folderName>\\S+)\\s*")){
                matcher = getMatcher(command, "\\s*delete\\s+folder\\s+(?<folderName>\\S+)\\s*");
                String folderName = matcher.group("folderName");
                folderName = folderName.trim();
                if (open_drive==null){
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }
                if (marker.getType().equals("Drive")) {
                    if (!open_drive.isFolderOk(folderName)) {


                        removeFolder(open_drive.findFolderByName(folderName),open_drive,allFolders,addre);

                        removeFolderFromAllFolders(open_drive.findFolderByName(folderName),allFolders,addre);
                        open_drive.removeFolderOfDrive(open_drive.findFolderByName(folderName));
                        System.out.println("folder deleted");
                    }else System.out.println("invalid name");
                }else if (marker.getType().equals("Folder")) {
                    open_folder = marker.getLastFolder();
                    if (!open_folder.isFolderOk(folderName)) {
                        removeFolder(open_folder.findFolderByName(folderName),open_drive,allFolders,addre);
                        removeFolderFromAllFolders(open_folder,allFolders,addre);
                        System.out.println("folder deleted");
                    }else System.out.println("invalid name");
                }

            } else if (command.matches("\\s*rename\\s+file\\s+(?<fileName>\\S+)\\s+(?<newName>\\S+)\\s*")){
                matcher = getMatcher(command,"\\s*rename\\s+file\\s+(?<fileName>\\S+)\\s+(?<newName>\\S+)\\s*");
                String fileName = matcher.group("fileName");
                String newName = matcher.group("newName");
                fileName = fileName.trim();
                newName = newName.trim();
                if (open_drive == null) {
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }
                if (marker.getType().equals("Drive")) {
                    open_drive = marker.getLastDrive();
                    if (open_drive.hasFile(fileName)) {
                        if (!open_drive.hasFile(newName)){
                            open_drive.changeFileName(fileName, newName);
                            System.out.println("file renamed");
                        }else System.out.println("file exists with this name");
                    }else System.out.println("invalid name");
                }else if (marker.getType().equals("Folder")) {
                    open_folder = marker.getLastFolder();
                    if (!open_folder.isFileOk(fileName)) {
                        if (open_folder.isFileOk(newName)){
                            open_folder.changeFileName(fileName, newName);
                            System.out.println("file renamed");
                        }else System.out.println("file exists with this name");
                    }else System.out.println("invalid name");
                }
            } else if (command.matches("\\s*rename\\s+folder\\s+(?<folderName>\\S+)\\s+(?<newName>\\S+)\\s*")){
                matcher = getMatcher(command, "\\s*rename\\s+folder\\s+(?<folderName>\\S+)\\s+(?<newName>\\S+)\\s*");
                String folderName = matcher.group("folderName");
                String newName = matcher.group("newName");
                if (open_drive==null){
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }
                if (marker.getType().equals("Drive")) {
                    if (!open_drive.isFolderOk(folderName)) {
                        if (open_drive.isFolderOk(newName)){
                            open_drive.changeFolderName(folderName,newName);
                            int n = -1;
                            for (int i = 0 ;i <allFolders.size(); i++){
                                if (allFolders.get(i).name.equalsIgnoreCase(newName))
                                    n=i;
                            }
                            int a1 = addre.get(n).lastIndexOf("\\")+1;
                            String newAd = addre.get(n).substring(0,a1)+newName;
                            addre.set(n,newAd);



                            System.out.println("folder renamed");
                        }else System.out.println("folder exists with this name");
                    }else System.out.println("invalid name");
                }else if (marker.getType().equals("Folder")) {
                    open_folder = marker.getLastFolder();
                    if (!open_folder.isFolderOk(folderName)) {
                        if (open_folder.isFolderOk(newName)){
                            open_folder.changeFolderName(folderName, newName);
                            int n = -1;
                            for (int i = 0 ;i <allFolders.size(); i++){
                                if (allFolders.get(i).name.equalsIgnoreCase(newName))
                                    n=i;
                            }
                            int a1 = addre.get(n).lastIndexOf("\\")+1;
                            String newAd = addre.get(n).substring(0,a1)+newName;
                            addre.set(n,newAd);

                            System.out.println("folder renamed");
                        }else System.out.println("folder exists with this name");
                    }else System.out.println("invalid name");
                }
            } else if (command.matches("\\s*print\\s+OS\\s+information\\s*")){
                System.out.println("OS is "+open_os.name+" "+open_os.version);
            } else if (command.matches("\\s*write\\s+text\\s+(?<fileName>\\S+)\\s*")){

                matcher = getMatcher(command, "\\s*write\\s+text\\s+(?<fileName>\\S+)\\s*");
                String fileName = matcher.group("fileName");

                if (open_drive == null) {
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }

                if (marker.getType().equals("Drive")) {

                    open_drive = marker.getLastDrive();
                    if (open_drive.hasFile(fileName)) {
                        if (open_drive.getFileByName(fileName)instanceof textFile){
                            String newText = scanner.nextLine();
                            ((textFile) open_drive.getFileByName(fileName)).text = newText;
                        }else System.out.println("this file is not a text file");

                    }else{ System.out.println("invalid name");}

                }else if (marker.getType().equals("Folder")) {

                    open_folder = marker.getLastFolder();
                    if (!open_folder.isFileOk(fileName)) {
                        if (open_folder.getFileByName(fileName)instanceof textFile){
                            String newText = scanner.nextLine();
                            ((textFile) open_folder.getFileByName(fileName)).text = newText;
                        }else System.out.println("this file is not a text file");

                    }else{
                        System.out.println("invalid name");}
                }
            } else if (command.matches("\\s*print\\s+drives\\s+status\\s*")){
                if (open_drive == null) {
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }
                for (Drive d:
                     open_os.drives) {
                    System.out.println(d.name+" "+d.size+"MB "+ (d.size - d.freeSize)+"MB");
                }
            }else if (command.matches("\\s*status\\s*")){
                if (open_drive == null) {
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }
                System.out.print(marker.names.get(0)+":");
                for (int i =1 ;i<marker.names.size();i++){
                    System.out.print("\\"+marker.names.get(i));
                }
                System.out.println();
                System.out.println("Folders:");
                if (marker.getType().equals("Drive")) {
                    ArrayList<String>folderNames = new ArrayList<>();
                    folderNames.clear();
                    for (Folder f :
                            open_drive.folders) {
                        folderNames.add(f.name);
                    }
                    Collections.sort(folderNames);
                    for (String f :
                            folderNames) {
                        System.out.print(f + " ");
                        getSize(open_drive.getFolderByName(f));
                    }
                }else if (marker.getType().equals("Folder")){
                    ArrayList<String>folderNames = new ArrayList<>();
                    folderNames.clear();;
                    for (Folder f :
                            open_folder.foldersInFolder) {
                        folderNames.add(f.name);
                    }
                    Collections.sort(folderNames);
                    for (String f :
                            folderNames) {
                        System.out.print(f + " ");
                        getSize(open_folder.getFolderByName(f));
                    }
                }
                System.out.println("Files:");
                if (marker.getType().equals("Drive")) {
                    ArrayList<String>fileNames = new ArrayList<>();
                    fileNames.clear();;
                    for (File f :
                            open_drive.filesOfDrive) {
                        fileNames.add(f.name);
                    }
                    Collections.sort(fileNames);
                    for (String f :
                            fileNames) {

                        if (open_drive.getFileByName(f) instanceof imgFile) {
                            System.out.print(f + " ");
                            System.out.println("img " + open_drive.getFileByName(f).size + "MB");

                        }
                    }
                    for (String f :
                            fileNames) {

                        if (open_drive.getFileByName(f) instanceof textFile) {
                            System.out.print(f + " ");
                            System.out.println("txt " + open_drive.getFileByName(f).size + "MB");

                        }
                    }
                    for (String f :
                            fileNames) {

                        if (open_drive.getFileByName(f) instanceof videoFile) {
                            System.out.print(f + " ");
                            System.out.println("mp4 " + open_drive.getFileByName(f).size + "MB");

                        }
                    }
                }else if (marker.getType().equals("Folder")){
                    ArrayList<String>fileNames = new ArrayList<>();
                    fileNames.clear();;
                    for (File f :
                            open_folder.files) {
                        fileNames.add(f.name);
                    }
                    Collections.sort(fileNames);
                    for (String f :
                            fileNames) {

                        if (open_folder.getFileByName(f) instanceof imgFile) {
                            System.out.print(f + " ");
                            System.out.println("img " + open_folder.getFileByName(f).size + "MB");

                        }
                    }
                    for (String f :
                            fileNames) {

                        if (open_folder.getFileByName(f) instanceof textFile) {
                            System.out.print(f + " ");
                            System.out.println("txt " + open_folder.getFileByName(f).size + "MB");

                        }
                    }
                    for (String f :
                            fileNames) {

                        if (open_folder.getFileByName(f) instanceof videoFile) {
                            System.out.print(f + " ");
                            System.out.println("mp4 " + open_folder.getFileByName(f).size + "MB");

                        }
                    }
                }


            }else if (command.matches("\\s*print\\s+file\\s+stats\\s+(?<fileName>\\S+)\\s*")){
                matcher = getMatcher(command,"\\s*print\\s+file\\s+stats\\s+(?<fileName>\\S+)\\s*");
                String fileName = matcher.group("fileName");
                if (open_drive == null) {
                    open_drive = open_os.getSystemDrive();
                    marker.setF(open_drive.name, "Drive");
                    marker.setDriveList(open_drive);
                }
                if (marker.getType().equals("Drive")) {
                    if (open_drive.hasFile(fileName)) {
                        File file = open_drive.getFileByName(fileName);
                        System.out.print(file.name);
                        if (file instanceof textFile){
                            System.out.println(" txt");
                            System.out.print(marker.names.get(0)+":");
                            for (int i =1 ;i<marker.names.size();i++){
                                System.out.print("\\"+marker.names.get(i));
                            }
                            System.out.println("\\"+file.name);
                            System.out.println("Size: "+file.size+"MB");
                            System.out.println("Text: "+((textFile) file).text);
                        }else if(file instanceof imgFile){
                            System.out.println(" img");
                            System.out.print(marker.names.get(0)+":");
                            for (int i =1 ;i<marker.names.size();i++){
                                System.out.print("\\"+marker.names.get(i));
                            }
                            System.out.println("\\"+file.name);
                            System.out.println("Size: "+file.size+"MB");
                            System.out.println("Resolution: "+((imgFile) file).Resolution);
                            System.out.println("Extension: "+((imgFile) file).Extension);
                        }else if (file instanceof  videoFile){
                            System.out.println(" mp4");
                            System.out.print(marker.names.get(0)+":");
                            for (int i =1 ;i<marker.names.size();i++){
                                System.out.print("\\"+marker.names.get(i));
                            }
                            System.out.println("\\"+file.name);
                            System.out.println("Size: "+file.size+"MB");
                            System.out.println("Quality: "+((videoFile) file).Quality);
                            System.out.println("Video Length: "+((videoFile) file).VideoLength);
                        }

                    }else System.out.println("invalid name");
                }else if (marker.getType().equals("Folder")){
                    if (!open_folder.isFileOk(fileName)){
                        File file = open_folder.getFileByName(fileName);
                        System.out.print(file.name);
                        if (file instanceof textFile){
                            System.out.println(" txt");
                            System.out.print(marker.names.get(0)+":");
                            for (int i =1 ;i<marker.names.size();i++){
                                System.out.print("\\"+marker.names.get(i));
                            }
                            System.out.println("\\"+file.name);
                            System.out.println("Size: "+file.size+"MB");
                            System.out.println("Text: "+((textFile) file).text);
                        }else if(file instanceof imgFile){
                            System.out.println(" img");
                            System.out.print(marker.names.get(0)+":");
                            for (int i =1 ;i<marker.names.size();i++){
                                System.out.print("\\"+marker.names.get(i));
                            }
                            System.out.println("\\"+file.name);
                            System.out.println("Size: "+file.size+"MB");
                            System.out.println("Resolution: "+((imgFile) file).Resolution);
                            System.out.println("Extension: "+((imgFile) file).Extension);
                        }else if (file instanceof  videoFile){
                            System.out.println(" mp4");
                            System.out.print(marker.names.get(0)+":");
                            for (int i =1 ;i<marker.names.size();i++){
                                System.out.print("\\"+marker.names.get(i));
                            }
                            System.out.println("\\"+file.name);
                            System.out.println("Size: "+file.size+"MB");
                            System.out.println("Quality: "+((videoFile) file).Quality);
                            System.out.println("Video Length: "+((videoFile) file).VideoLength);
                        }

                    }else System.out.println("invalid name");
                }
            }
            else System.out.println("invalid command");
            command = scanner.nextLine();
        }
    }
    public static void removeFolder (Folder folder, Drive drive,ArrayList<Folder> all, ArrayList<String> addre){
        folder.files.clear();
        for (Folder f: folder.foldersInFolder) {
            removeFolder(f,drive,all,addre);
            removeFolderFromAllFolders(f,all,addre);
        }
    }
    static void  removeFolderFromAllFolders(Folder f ,ArrayList<Folder> all, ArrayList<String> addre){
        int n = -1;
        for (int i = 0; i < all.size(); i++){
            if (all.get(i).equals(f)){
                n=i;
            }

        }
        all.remove(n);
        addre.remove(n);
    }
    static int getSizeOfFolder(Folder f){
        int size = 0;
        for (File file:
             f.files) {
            size+=file.size;
        }
        return size;
    }
    static void getSize(Folder f){
        int totalSize = 0;
        for (Folder folder:
             f.foldersInFolder) {
            totalSize+=getSizeOfFolder(folder);
        }
        totalSize+=getSizeOfFolder(f);
        System.out.println(totalSize+"MB");
    }
    public static Matcher getMatcher(String command, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
