import spellCheck as sp
#Dictionary words are downloaded from https://github.com/dwyl/english-words on 31 May 2016

#First get words retrived previously
with open("all.txt", 'r') as pfile:
	pcontent = pfile.readlines()
previousList =[]

for line in pcontent:
	words = line.split("\"")
	previousList.append([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]])

#Now check content from current file
filename = "words.txt"
with open(filename, 'r') as file:
	content = file.readlines()

WordList = []
ErrorWords = []

for line in content:
	words = line.split("\"")
	if(len(words)>11):
		if([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]] not in previousList):
			if(sp.isThere(words[1].lower().strip())):
				WordList.append([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]])
			else:
				ErrorWords.append([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]])

print (("%d new words found")%(len(WordList) + len(ErrorWords)))
sorted_set = set(map(tuple , WordList)) #Remove duplicates
x = list(sorted_set)


with open("result_spelling_mistakes.txt",'a') as file3:
	for FinalList in ErrorWords:
		file3.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(FinalList[0].strip(), FinalList[1].strip(),FinalList[2].strip(),FinalList[3].strip(),FinalList[4].strip(),FinalList[5].strip()))

with open("result_new_words.txt",'a') as file4:
	for FinalList in WordList:
		file4.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(FinalList[0].strip(), FinalList[1].strip(),FinalList[2].strip(),FinalList[3].strip(),FinalList[4].strip(),FinalList[5].strip()))
