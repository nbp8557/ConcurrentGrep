import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CGrep{
    private final ExecutorService threadPool;
    private final CompletionService<Result> threadResults;

    private String[] files;
    private String searchPattern;

    public CGrep(int numberOfThreads, String[] files, String searchPattern){
        this.threadPool = Executors.newFixedThreadPool(numberOfThreads);
        this.threadResults = new ExecutorCompletionService<Result>(threadPool);
        this.files = files;
        this.searchPattern = searchPattern;
    }

    //Search all of the files
    public void SearchAllFiles(){
        //An arrayList to store all of the results
        ArrayList<Future<Result>> results = new ArrayList<Future<Result>>();


        //Loop through each file and queue up the tasks to the thread pool
        for(String file: this.files){
            //Make sure that the file is not null
            if(file != null) {
                //Create a new SearchTask for each file which will search for the regex
                // and submit it to the ThreadPool for execution.
                //Also add the Future Object result from the Task to the results file
                results.add(this.threadResults.submit(new SearchTask(this.searchPattern, file)));

            }
        }

        //Now display the results to the user as the come in from the SearchTasks



    }

    public static void main(String[] args) {
        //file stores the name of the file to perform the regex search on
        String[] file = new String[args.length];
        //searchPattern Stores the search regex
        String searchPattern;

        //Check if there are invalid arguments
        if(args.length != 2){
            System.out.println("INVALID ARGUMENTS");
            System.out.println("Usage: java CGrep PATTERN [File ... ]");
            System.exit(1);
        }

        //Store the search regex
        searchPattern = args[0];

        //Loop through the files and store them in the file array
        for(int counter=1; counter < args.length ; counter++){
            //Add filename to the file array
            file[counter-1]=args[counter];
        }

        //Check Validity of REGEX pattern
        try{
            Pattern.compile(searchPattern);
        }catch (PatternSyntaxException exception){
            //The user entered an invalid regex pattern
            //Notify him and exit the program
            System.out.println("INVALID REGEX");
            System.exit(1);
        }

        //initialize the CGrep object with 3 threads
        CGrep grep = new CGrep(3,file,searchPattern);

        //Search all of the files
        grep.SearchAllFiles();

    }


}
