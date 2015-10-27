import java.util.ArrayList;


//This class is used to store the results of a SearchTask to the main program
public class Result {
    public String filename;
    public ArrayList<String> results;

    public Result(String filename, ArrayList<String> results){
        this.filename = filename;
        this.results = results;
    }

}
