import java.util.ArrayList;
import java.util.Arrays;

public class OS {
    ArrayList<Drive> drives = new ArrayList<>();
    String name;
    String version;
    Drive systemDrive;
    int hardSize;
    int driveNum;
    void setDrives(Drive d){
        drives.add(d);
    }
    boolean isOkDrive(String d){
        if(d.length()==1){
            if ((int)d.charAt(0)>=(int)'A' && (int)d.charAt(0)<=(int)'Z'){
                for (Drive dd: drives) {
                    if (dd.name.equals(d)){
                        return false;
                    }
                }
                return true;
            }else return false;
        }else return false;
    }
    boolean isDrivesizeOk(int s){
        int sum = 0;
        for (Drive d: drives) {
            sum+=d.size;
        }
        sum+=s;
        if (sum > hardSize){
            return false;
        }else return true;
    }
    Drive getSystemDrive(){
        return drives.get(0);
    }
    boolean hasDrive(String d){
        for (Drive dd: drives) {
            if (dd.name.equals(d)){
                return true;
            }
        }
        return false;
    }
    Drive getDriveByName(String name){
        for (Drive d: drives) {
            if (d.name.equals(name)){
                return d;
            }
        }
        return null;
    }
}
