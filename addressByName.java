import java.util.ArrayList;

public class addressByName {
    ArrayList<String> kindOfMap;
    ArrayList<String> nameOfMap ;
    ArrayList<Drive> driveListOP ;
    ArrayList<Folder> folderListOP ;
    ArrayList<File> fileListOP ;
    String name;
    public addressByName(ArrayList<String>k,ArrayList<String>n,ArrayList<Drive>d,ArrayList<Folder>fo,ArrayList<File>fi,String naame){
        kindOfMap = k;
        nameOfMap = n;
        driveListOP = d;
        folderListOP =fo;
        fileListOP = fi;
        name = naame;
    }
    public addressByName(){

    };
    void printStatus(){
        int t = 1;
//        int d = driveListOP.size()-1;
//        int fo = folderListOP.size()-1;
//        int fi = fileListOP.size()-1;
//        System.out.print(this.driveListOP.get(0).name+":\\");
//        for (int i =0;i<this.nameOfMap.size();i++){
//            if (kindOfMap.get(i).equals("Drive")){
//                System.out.print(driveListOP.);
//            } else if (kindOfMap.get(i).equals("Folder")){
//                System.out.print();
//            }else if (kindOfMap.get(i).equals("file")){
//                System.out.print();
//            }
//            System.out.print(this.nameOfMap.get(i)+"\\");
//
//        }
//        for (Folder f:
//             folderListOP) {
//            System.out.print(f.name+"\\");
//        }
        for (String n:
             this.nameOfMap) {
            System.out.print(n+"\\");
        }
        System.out.println(" "+this.folderListOP.get(this.folderListOP.size()-1).opened);
    }


}
