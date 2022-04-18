import adapter.Pen;
import adapter.PenAdapter;

public class School {

    public static void main(String []a){
        Pen penAdapter =  new PenAdapter();
        AssignmentWork aw = new AssignmentWork();
        aw.setP(penAdapter);
        penAdapter.write("Testing adapter implementation");
    }
}
