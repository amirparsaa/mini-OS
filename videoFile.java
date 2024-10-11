import java.util.ArrayList;

public class videoFile extends File{

    String Quality;
    String VideoLength;
    public videoFile(String name, String quality, String videoLength){
        super(name);
        Quality = quality;
        VideoLength = videoLength;
    }

}
