Will add more instructions on compiling this later!

------------------------------------------------------------------------------------------------------------------------------------------
1) First performing preprocessing on the initial csv data in python, making use of:
    - Pandas, Regex, Data cleaning, Remove stopwords, stemming words
  
2) Import cleaned data into Java Hadoop code:
    - Use MapReduce to strip titles into tokens and key value pairs and reduce to give occurences of each word in the corpus
    - Construct sparce bag of words model representing each papers in the CSV as a row vector representing all the tokens in the corpus
    - Compute partial score for each word (token) by multiplying the number of occurences by the 4*/3*/2*/1* percentage scores for that university in the data
    - 4*, 3*, 2*, 1* are then given weights in an equation so words correlating with negative scores impact the overall score negatively
        - Word_Score = Sum over all occurences (12.5 * 4*%) + (5 * 3*%) - (12.5 * 2*%) - (25 * 1*%)
    - Use scoring on every word in the corpus and take an average to get score for each work
    - Do this for the domain we want (All fields/Computer Science/Physics/Chemistry/Maths)
  
3) Post-processing
    - Do some simple processing to average score and data visualisation in R.
  
  
Conclusions:
  - Shows important/unimportant words in the domain
  - Correlation shown of computational/machine learning techniques getting high scores across all engineer disciplines
 
      
