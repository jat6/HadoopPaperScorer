import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

public class MRCount {
	
	private static ArrayList<String> titles = new ArrayList<String>();
	private static ArrayList<Token> tokens = new ArrayList<Token>();
    
	private static int titleCount = 0;
	private static int tokenCount = 0;

	MRCount(Path input, Path output) throws IOException, ClassNotFoundException, InterruptedException {
		
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Tokenizer");
		job.setJarByClass(REFAnalyzer.class);
		job.setMapperClass(TokenMap.class);
		job.setCombinerClass(CountReduce.class);
		job.setReducerClass(CountReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);
		job.waitForCompletion(true);
    
		REFDataFrame refDF = new REFDataFrame(tokens, titles);
		refDF = null;
    
		System.gc();
	}
	
	public static class TokenMap extends Mapper<Object, Text, Text, IntWritable>{
	
		// Hadoop types
	    private final static IntWritable initialValue = new IntWritable(1);
	    private Text token = new Text();
	    
	    // Maps each word into a token, setting starting key value pairs
	    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
	    	
	    	// Split each paper into tokens
	    	String row = value.toString();
	    	StringTokenizer tokens = new StringTokenizer(value.toString());
	      
	    	// Set initial key-valued pairs
	    	while (tokens.hasMoreTokens()) {
	    		token.set(tokens.nextToken());
	    		context.write(token, initialValue);
	    	}
	      
	    	// Add the titles to a list
	    	titles.add(titleCount, value.toString());
	  	  	titleCount++;
	    }
	}

	public static class CountReduce extends Reducer<Text,IntWritable,Text,IntWritable> {
		
		// IntWritable for hadoop output for overall token counts
		private IntWritable value = new IntWritable();
		
		// Reduce function gets overall sum of each occurence of the word in the input text
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			
			int count = 0;
      
			// While still token pair remain sum occurences
			while(values.iterator().hasNext()) {
				count += values.iterator().next().get();
			}
      
			// Output key value pairs 
			value.set(count);
			context.write(key, value);
			
			// Store result in user defined data structure Token
			Token newToken = new Token(key.toString(), count);
			tokens.add(tokenCount, newToken);
      
		}
	}

}

