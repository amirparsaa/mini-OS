public class imgFile extends File{
    String Resolution;
    String Extension;
    public imgFile(String name , String resolution, String extension){
        super(name);
        Resolution = resolution;
        Extension = extension;
    }
}
