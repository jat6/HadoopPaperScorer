import pandas as pd
from collections import Counter
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer

stem = False
remove_stop_words = True

departments = ['All', 'Chemistry', 'Physics', 'Mathematics', 'ComputerScience']

for department in departments:
    # Code to read in csv file into a data frame, and tokenizing the sentences into individual words
    importedData = pd.read_csv("allPapers.csv", engine='python')
    
    if(department != 'All'):
        importedData = importedData[importedData["Unit of assessment name"] == department]
    else:
        importedData = importedData[importedData["Unit of assessment name"] != department]
        
    # Stop word removal and data cleaning
    stop_words = set(stopwords.words("english"))
    numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}
    symbols = {'!', "\'", '"', "-", "’", "{", "}", "‘", "\`", "\-", "\\", '\“' '\-', '\"', '\”', '£', '$', '%', '^', '&', '*', '(', ')', '_', '+', '=', '[', ']', '#', '~', '@', ':', ';', '<', ',', '.', '>', '/', '?'}
              
    # Clean data with regex and replace           
    importedData["Title"] = importedData["Title"].str.replace('[0-9]+', ' ')
    for chars in symbols:
        importedData["Title"] = importedData["Title"].str.replace(chars, ' ')
    importedData["Title"] = importedData["Title"].str.lower()
    importedData["Title"] = importedData["Title"].apply(lambda x: ' '.join([item for item in x.split() if len(item) > 3]))    
    
    # Remove stop words
    if(remove_stop_words):
       importedData["Title"] = importedData["Title"].apply(lambda x: ' '.join([item for item in x.split() if item not in (stop_words)]))
    
    # Stem the titles
    if(stem):
        ps = PorterStemmer()
        importedData["Title"] = importedData["Title"].apply(lambda x: ' '.join([ps.stem(y) for y in x.split()]))
    
    # Output clean data frame to csv
    importedData.to_csv((department+ "Data.csv"), index=False)
    importedData["Title"].to_csv((department + "Titles.csv"), index=False)
    
    # Weight calculation
    scoreData = pd.DataFrame()
    scoreData["4*"] = importedData["4*"]
    scoreData["3*"] = importedData["3*"]
    scoreData["2*"] = importedData["2*"]
    scoreData["1*"] = importedData["1*"]
    scoreData["unclassified"] = importedData["unclassified"]
    scoreData.to_csv((department + "ScoreData.csv"), index=False, header=False)