import numpy as np
import msvcrt
import spellCheck as sp
#Dictionary words are downloaded from https://github.com/dwyl/english-words on 31 May 2016

#Now check content from current file
filename = "words.txt"
with open(filename, 'r') as file:
	content = file.readlines()

WordList = []
ErrorWords = []
for line in content:
	words = line.split("\"")
	if(sp.isThere(words[1].lower().strip())):
		WordList.append([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]])
	else:
		ErrorWords.append([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]])

sorted_set = set(map(tuple , WordList)) #Remove duplicates
x = list(sorted_set)

correctWords = []
needChange = []

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

