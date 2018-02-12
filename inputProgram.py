import pandas as pd
from collections import Counter
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer

# Data import
chemScores = pd.read_csv("ChemistryScore.csv", engine="python")
physScores = pd.read_csv("PhysicsScore.csv", engine="python")
compScores = pd.read_csv("ComputerScienceScore.csv", engine="python")
mathScores = pd.read_csv("MathematicsScore.csv", engine="python")
allScores = pd.read_csv("AllScore.csv", engine="python")

# Read title data
allData = pd.read_csv("Alldata.csv", engine="python")
chemData = allData[allData["Unit of assessment name"] == "Chemistry"]
physData = allData[allData["Unit of assessment name"] == "ComputerScience"]
compData = allData[allData["Unit of assessment name"] == "Mathematics"]
mathData = allData[allData["Unit of assessment name"] == "Physics"]

# Read user titles and treat titles as we preprocessed the training data
inputPapers = pd.read_csv("compdisciplines.csv", engine="python") 
stop_words = set(stopwords.words("english"))
numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}
symbols = {'!', "\'", '"', "-", "’", "{", "}", "‘", "\`", "\-", "\\", '\“' '\-', '\"', '\”', '£', '$', '%', '^', '&', '*', '(', ')', '_', '+', '=', '[', ']', '#', '~', '@', ':', ';', '<', ',', '.', '>', '/', '?'}
inputPapers["Titles"] = inputPapers["Titles"].str.replace('[0-9]+', ' ')

for chars in symbols:
    inputPapers["Titles"] = inputPapers["Titles"].str.replace(chars, ' ')
inputPapers["Titles"] = inputPapers["Titles"].str.lower()
inputPapers["Titles"] = inputPapers["Titles"].apply(lambda x: ' '.join([item for item in x.split() if len(item) > 3]))    
inputPapers["Titles"] = inputPapers["Titles"].apply(lambda x: ' '.join([item for item in x.split() if item not in (stop_words)]))
inputPapers["All"] = inputPapers["Titles"]
inputPapers["Chemistry"] = inputPapers["Titles"]
inputPapers["Computer Science"] = inputPapers["Titles"]
inputPapers["Physics"] = inputPapers["Titles"]
inputPapers["Mathematics"] = inputPapers["Titles"]


# Scoring
title_count = 0
for paper in inputPapers["Titles"]: 
    
    title = paper.split()
    score = 0
    score_change = 0
    for token in title:
        row_count = 0
        for row in allScores["Word"]:
            row_count = row_count + 1
            if(row == token):
                score = score + allScores.iloc[row_count, 1]      
    inputPapers.iloc[title_count, 1] = score
    
    score = 0
    for token in title:
        row_count = 0
        for row in chemScores["Word"]:
            row_count = row_count + 1
            if(row == token):
                score = score + chemScores.iloc[row_count, 1]      
    inputPapers.iloc[title_count, 2] = score
    
    score = 0
    for token in title:
        row_count = 0
        for row in physScores["Word"]:
            row_count = row_count + 1
            if(row == token):
                score = score + physScores.iloc[row_count, 1]      
    inputPapers.iloc[title_count, 4] = score
    
    score = 0
    for token in title:
        row_count = 0
        for row in compScores["Word"]:
            row_count = row_count + 1
            if(row == token):
                score = score + compScores.iloc[row_count, 1]      
    inputPapers.iloc[title_count, 3] = score
    
    score = 0
    for token in title:
        row_count = 0
        for row in mathScores["Word"]:
            row_count = row_count + 1
            if(row == token):
                score = score + mathScores.iloc[row_count, 1]      
    inputPapers.iloc[title_count, 5] = score    
    
    title_count = title_count + 1
                
                