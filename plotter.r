#Importing the dataset
library(ggplot2)

dataset <- read.csv('output.csv')

#Datasets
ai <- dataset[dataset$Discipline == "Artificial Intelligence",]
os <- dataset[dataset$Discipline == "Languages & Operating Systems",]
ag <- dataset[dataset$Discipline == "Agents",]
cg <- dataset[dataset$Discipline == "Computational Geometry",]
cc <- dataset[dataset$Discipline == "Complexity",]
hc <- dataset[dataset$Discipline == "HCI",]
db <- dataset[dataset$Discipline == "Databases",]
gr <- dataset[dataset$Discipline == "Graphics",]
cv <- dataset[dataset$Discipline == "Vision",]
net <- dataset[dataset$Discipline == "Networking",]
se <- dataset[dataset$Discipline == "Software Engineering",]
cf <- dataset[dataset$Discipline == "Computational Finance",]

#Plot disciplines
plot(dataset$Mathematics, dataset$All, ylab ="Maths Score", xlab = "General Score")
#plot(ai$Computer.Science, ai$All, xlab="General Score", ylab="CS Score", col="Red", pch=15)
#points(db$Computer.Science, db$All, col="Black", pch=15)
#points(gr$Computer.Science, gr$All, col="Yellow", pch=15)
#points(cv$Computer.Science, cv$All, col="Magenta", pch=15)
#points(se$Computer.Science, se$All, col="Blue", pch=15)
#points(cf$Computer.Science, cf$All, col="Green", pch=15)
#points(ag$Computer.Science, ag$All, col="Cyan", pch=15)

#legend( x="bottomright", 
#        legend=c("AI/ML","Databases", "Graphics", "Vision", "Software Engineering", "Computational Finance", "Agents"),
#        col=c("red", "black", "yellow", "magenta", "blue", "green", "cyan"), 
#        pch=15 )

