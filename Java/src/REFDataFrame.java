import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class REFDataFrame {
	
	// Declaration of class variables
	private String[] header;
	private String[] titles;
	private int[][] sparceMatrix;
	private ArrayList<Token> tokens;
	private ArrayList<String> titlesList;
    private List<List<String>> csvData = new ArrayList<List<String>>();
	
    // Constructor that initialises class variables
	REFDataFrame(ArrayList<Token> tok, ArrayList<String> importedPapers) throws IOException
	{
		tokens = new ArrayList<Token>(tok);
		titlesList = new ArrayList<String>(importedPapers);
		header = new String[tokens.size()];
		titles = new String[titlesList.size()];
		
		// Gets a 1 dimensional string array of all the individual words in the corpus
		ConstructHeader(tokens);
		
		// Reads in data from the corpus stating the departments score
		ReadScoringData();
		
		// Exception handling for file reading
		try {
			ConstructSparceMatrix(header, titles);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create an object of wordScorer which scores the user-typed data frame created here
		WordScorer latestREF = new WordScorer(header, titles, sparceMatrix, csvData, tokens);
		
		// Code for memory issue
		sparceMatrix = null;
		header = null;
		titles = null;
		csvData = null;
		System.gc();
	}
	
	private void ConstructHeader(ArrayList<Token> tokens)
	{
		for(int i = 0; i < header.length; i++) 
		{
			header[i] = tokens.get(tokens.size() - i - 1).GetToken();	
		}
		
		for(int j = 0; j < titles.length; j++) 
		{
			titles[j] = titlesList.get(j);
		}
	}
	
	private void ReadScoringData() throws IOException 
	{		
	    String row = null;
	    BufferedReader stream = null;

	    try {
	    	stream = new BufferedReader(new FileReader("/media/sf_sharedfolder/" + REFAnalyzer.getFileName() +  "ScoreData.csv"));
	        while ((row = stream.readLine()) != null) {
	            String[] tokens = row.split(",");
	            List<String> datarow = new ArrayList<String>(tokens.length);
	            for (String data : tokens)
	            	datarow.add(data);
	            csvData.add(datarow);
	        }
	    } finally {
	        if (stream != null)
	            stream.close();
	    }
	}
	
	public void ConstructSparceMatrix(String[] headerTokens, String[] titles) throws FileNotFoundException 
	{
		// Set a multidimensional array with cols the size of the number of words (header) and rows the size of the number of paper titles
		sparceMatrix = new int[titles.length][header.length];
		
		// First set the header of the file to be the word tokens themselves
		for(int k = 0; k < header.length; k++) 
		{
			String f = header[k];
		}
		
		// For every title, split into tokens and sort alphabetically for speedup
		for (int i = 0; i < titles.length; i++) 
		{
			String g = titles[i];
			int tokenCount = 0;
			String titleFull = titles[i];
			String[] titleTokens = titleFull.split(" ");
			Arrays.sort(titleTokens);
			
			// For each word in corpus, check if tokens in this paper title match
			for (int j = 0; j < header.length; j++) 
			{		
				// If the token matches with a header set a value in that location of the matrix to 1
				if(titleTokens[tokenCount].equals(header[j]))
				{
					sparceMatrix[i][j] = 1;	
					
					if(tokenCount != titleTokens.length - 1)
						tokenCount++;
				}
				
				else 
					sparceMatrix[i][j] = 0;
			
			}
		}	
	}
	
	
}
