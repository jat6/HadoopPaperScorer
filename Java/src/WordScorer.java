import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WordScorer {

	// Class variable definitions
	private String[] header;
	private String[] titles;
	private int[][] sparceMatrix;
	private int[][] scoringCategoryAverage;
	private List<List<String>> scoringList = new ArrayList<List<String>>();
	private ArrayList<Token> tokenDictionary; 
	
	public Token[] wordScores; 
	
	public WordScorer(String[] headings, String[] titlesList, int[][] matrix, List<List<String>> scoring, ArrayList<Token> tokens) throws FileNotFoundException
	{
		// Class variable initialisation
		header = headings;
		titles = titlesList;
		sparceMatrix = matrix;
		scoringList = scoring;
		tokenDictionary = tokens;
		
		// Scores the words in matrix passed in from the REFDataFrame object
		ScoreMatrix();
	}
	
	public void ScoreMatrix() throws FileNotFoundException 
	{
		// Array of user defined type Token (token,score)
		wordScores = new Token[header.length];
		
		// Open a printwriter to write the scores to a csv file
		PrintWriter out = new PrintWriter("/media/sf_sharedfolder/" + REFAnalyzer.getFileName() + "Score.csv");
		
		// Write headers of csv
		out.print("Word");
		out.print(",");
		out.print("Score");
		
		// For each word in the entire document
		for(int i = 0; i < header.length; i++) 
		{
			// Store word in Token array, print to row in csv
			Token word = new Token(header[i], 0);
			out.println();
			out.print(word.token);
			
			// For each title in the set of titles
			for(int j = 0; j < titles.length; j++) 
			{
				// If the title includes the word at count i, then add to score based on weights of departments scoring
				if(sparceMatrix[j][i] == 1) 
				{
					// Compute score for word - get(0) - %4*, get(1) - %3*, get(2) - %2*, get(3) - %1*, get(4) - %unclassified
					double delta = (12.5 * Double.parseDouble(scoringList.get(j).get(0)));
					delta += (5 * Double.parseDouble(scoringList.get(j).get(1)));
					delta -= (12.5 * Double.parseDouble(scoringList.get(j).get(2)));
					delta -= (25 * Double.parseDouble(scoringList.get(j).get(3)));
					//delta -= (Double.parseDouble(scoringList.get(j).get(4)));
					
					// Sum the score from the weights of the 4*,3*,2*,1*,unclassified values above
					word.score += delta;
				}
			}
			
			//word.score = word.score / tokenDictionary.get(tokenDictionary.size() - 1 - i).GetCount();
			//word.score = (1/ (1 + Math.pow(Math.E, (-1*word.score))));
			out.print("," + word.score);
		}
		
		// Close file and clear memory
		out.close();
		header = null;
		titles = null;
		sparceMatrix = null;;
		scoringList = null;
		System.gc();
	}
}
