#Run retrive words before running this script to get "words.txt" file
#Put all of your retrived words in "all.txt" to avoid checking them over again
import numpy as np
import msvcrt
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
	if([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]] not in previousList):
		if(sp.isThere(words[1].lower().strip())):
			WordList.append([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]])
		else:
			ErrorWords.append([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]])

print (("%d new words found")%(len(WordList) + len(ErrorWords)))
sorted_set = set(map(tuple , WordList)) #Remove duplicates
x = list(sorted_set)

correctWords = []
needChange = []


with open("result_spelling_mistakes.txt",'a') as file3:
	for FinalList in ErrorWords:
		file3.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(FinalList[0].strip(), FinalList[1].strip(),FinalList[2].strip(),FinalList[3].strip(),FinalList[4].strip(),FinalList[5].strip()))

with open("result_new_words.txt",'a') as file4:
	for FinalList in WordList:
		file4.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(FinalList[0].strip(), FinalList[1].strip(),FinalList[2].strip(),FinalList[3].strip(),FinalList[4].strip(),FinalList[5].strip()))

#Press "l" for accept words
#Press "a" for review words later
#Press "q" to quit process
#Press any other key to skip word

for FinalList in x:
		print(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(FinalList[0].strip(), FinalList[1].strip(),FinalList[2].strip(),FinalList[3].strip(),FinalList[4].strip(),FinalList[5].strip()))
		input_char = msvcrt.getch()
		if(ord(input_char)==ord("l")):
			correctWords.append(FinalList)
		elif( ord(input_char)==ord("a") ):
			needChange.append(FinalList)
		elif(ord(input_char)==ord("q")):
			break



with open("result_correct_words.txt",'a') as file3:
	for FinalList in correctWords:
		file3.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(FinalList[0].strip(), FinalList[1].strip(),FinalList[2].strip(),FinalList[3].strip(),FinalList[4].strip(),FinalList[5].strip()))


with open("result_need_attention.txt",'a') as file3:
	for FinalList in needChange:
		file3.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(FinalList[0].strip(), FinalList[1].strip(),FinalList[2].strip(),FinalList[3].strip(),FinalList[4].strip(),FinalList[5].strip()))

