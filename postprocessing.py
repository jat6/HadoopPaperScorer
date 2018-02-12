import pandas as pd

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

# Find classification values
fourStarData = allData[allData["4*"] > allData["3*"] + allData["2*"] + allData["1*"] + allData["unclassified"] - 25]
threeStarData = allData[allData["3*"] > allData["2*"] + allData["1*"] + allData["4*"] + allData["unclassified"]]
twoStarData = allData[allData["2*"] > allData["3*"] + allData["4*"] + allData["1*"] + allData["unclassified"]]
oneStarData = allData[allData["1*"] > allData["3*"] + allData["2*"] + allData["4*"] + allData["unclassified"]]

fourStarPapers = pd.DataFrame()
fourStarPapers["Title"] = fourStarData["Title"]
fourStarPapers["Score"] = fourStarData["Title"]
threeStarPapers = pd.DataFrame()
threeStarPapers["Title"] = threeStarData["Title"]
threeStarPapers["Score"] = threeStarData["Title"]
twoStarPapers = pd.DataFrame()
twoStarPapers["Title"] = twoStarData["Title"]
twoStarPapers["Score"] = twoStarData["Title"]
oneStarPapers = pd.DataFrame()
oneStarPapers["Title"] = oneStarData["Title"]
oneStarPapers["Score"] = oneStarData["Title"]


# Scoring
title_count = 0
for paper in fourStarPapers["Title"]: 
    title = paper.split()
    score = 0
    score_change = 0
    for token in title:
        row_count = 0
        for row in allScores["Word"]:
            row_count = row_count + 1
            if(row == token):
                score = score + allScores.iloc[row_count, 1]      
    fourStarPapers.iloc[title_count, 1] = score
    
    title_count = title_count + 1


title_count = 0
for paper in twoStarPapers["Title"]: 
    title = paper.split()
    score = 0
    score_change = 0
    for token in title:
        row_count = 0
        for row in allScores["Word"]:
            row_count = row_count + 1
            if(row == token):
                score = score + allScores.iloc[row_count, 1]      
    twoStarPapers.iloc[title_count, 1] = score
    title_count = title_count + 1

title_count = 0
for paper in oneStarPapers["Title"]: 
    title = paper.split()
    score = 0
    score_change = 0
    for token in title:
        row_count = 0
        for row in allScores["Word"]:
            row_count = row_count + 1
            if(row == token):
                score = score + allScores.iloc[row_count, 1]      
    oneStarPapers.iloc[title_count, 1] = score
    title_count = title_count + 1

fourStarAverage = fourStarPapers["Score"].mean()
twoStarAverage = twoStarPapers["Score"].mean()
oneStarAverage = oneStarPapers["Score"].mean()


    
                
                