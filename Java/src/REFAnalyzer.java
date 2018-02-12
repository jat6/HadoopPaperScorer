import org.apache.hadoop.fs.Path;

public class REFAnalyzer {
	
  public static String department;
  public static boolean papers = true;
	
  public static void main(String[] args) throws Exception 
  {
	  // List strings of all departments names to reference their specific files explicitly
	  String[] jobs = {"All", "Chemistry", "ComputerScience", "ElectricalEngineering", "GeneralEngineering", "Geography", "Mathematics", "Physics"};
	  int index = 2;
	  department = jobs[index];
	  
	  String inputText = "/home/ubuntu/workspace/REFAnalyzer/input/" + department;
	  String outputText = "/home/ubuntu/workspace/REFAnalyzer/output/" + department;
	  Path input = new Path(inputText);
	  Path output = new Path(outputText);
	  
	  // Create an object of MRCount who will perform map reduce on the specificed department from its MRCount constructor
	  MRCount mapreduce = new MRCount(input, output);  		  
	  mapreduce = null;
  }
  
  // Callable function that allows other classes to get the current department for file name appending
  public static String getFileName() 
  {
	  return department;  
  }
}

