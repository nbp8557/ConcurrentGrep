import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class SearchTask implements Callable<Result>{
    //Stores the filename
    private String file;
    //Stores the search regex
    private String searchRegex;

    public SearchTask(String file, String searchRegex) {
        this.file = file;
        this.searchRegex = searchRegex;
    }

    //This method will determine if a line within a file contains the REGEX and
    // returns a boolean to represent the success
    @Override
    public Result call() throws Exception {
        //Execute the search
        return this.SearchFile();
    }

    // This method returns true if the file contains the search regex
    private Result SearchFile(){
        //counter to keep track of the file line
        int lineCounter=0;
        //Used to store each line in the file to perform the regex search on
        String lineText;

        ArrayList<String> results = new ArrayList<String>();

        //Use a BufferedReader to read each line in the file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.file));
            while ((lineText = reader.readLine()) != null) {

                //Append the line of the file
                lineCounter++;

                //if the line of the file matches the regex
                if (Pattern.matches(this.searchRegex, lineText)){
                    results.add(lineCounter+ " " + lineText);
                }
                //TODO remove and add ThreadPool submission
                System.out.println(lineText);

            }
        } catch (IOException e) {
            //Something IO related messed up
            e.printStackTrace();
        }

        //construct the result object to return to the main program
        return new Result(this.file, results);
    }
}
