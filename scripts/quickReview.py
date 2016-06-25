import spellCheck as sp
import re
import numpy as np
#Dictionary words are downloaded from https://github.com/dwyl/english-words on 31 May 2016

from collections import Counter
from itertools import chain

#First get words retrived previously
with open("result_new_words.txt", 'r') as pfile:
	pcontent = pfile.readlines()
keyWords =[]
subWords = []
spellErrors = []
multiWords = []
#Check Spelling Mistakes and put it in two different lists for dictionary hashing
for line in pcontent:
	words = line.split("\"")
	if(len(words)>11):
		if(sp.isThere(words[3].lower().strip()) and sp.isThere(words[5].lower().strip()) and sp.isThere(words[7].lower().strip()) and sp.isThere(words[9].lower().strip()) and sp.isThere(words[11].lower().strip())):
			keyWords.append(words[1])
			subWords.append([words[3] , words[5] , words[7] , words[9], words[11]])
		else:
			count = len(re.findall(r'\w+', words[3])) + len(re.findall(r'\w+', words[5]))  + len(re.findall(r'\w+', words[7])) + len(re.findall(r'\w+', words[9])) + len(re.findall(r'\w+', words[11])) 
			if(count==5):
				spellErrors.append([words[1], words[3] , words[5] , words[7] , words[9], words[11]])  #Proper Spelling Mistakes
			else:
				multiWords.append([words[1], words[3] , words[5] , words[7] , words[9], words[11]]) #Need further review 

for word in multiWords:
	allValues = [ len(re.findall(r'\w+', word[1])) , len(re.findall(r'\w+', word[2])), len(re.findall(r'\w+', word[3])), len(re.findall(r'\w+', word[4])), len(re.findall(r'\w+', word[5]))]
	TruthArray = np.array(allValues) > 1
	TruthTable = TruthArray.tolist()
	mistakes = 0
	for i in range(len(TruthTable)):
		if(TruthTable[i]):
			temp_wordList = re.sub("[^\w]", " ",  word[i]).split()
			for halfWord in temp_wordList:
				if not(sp.isThere(words[i].lower().strip())):
					mistakes = mistakes+1
		if not (TruthTable[i]):
			if not(sp.isThere(words[i].lower().strip())):
					mistakes = mistakes+1
	if(mistakes!=0):
		spellErrors.append(word)  #Proper Spelling Mistakes
	else:
		keyWords.append(word[0])
		subWords.append([word[1] , word[2] , word[3] , word[4], word[5]])


#Reverse the order so that dictionary key hashing will take first value it encounters 
keyWords.reverse()
subWords.reverse()
dictionary = dict(zip(keyWords, subWords)) #this will remove all duplicate "MainWords". This apporach will take last items for particular key. 
uniqueKeys = list(set(keyWords))
print(("All words: %d , Unique words: %d")%(len(keyWords),len(dictionary)))

#Check againsts unique words
with open("all_unique_words.txt", 'r') as pfile:
	pcontent = pfile.readlines()
previousList =[]

for line in pcontent:
	words = line.split("\"")
	if(len(words)>11):
		previousList.append([ words[1] , words[3] , words[5] , words[7] , words[9], words[11]])


with open("result_unique_words.txt",'a') as file3:
	for key in uniqueKeys:
		item1 = key.strip()
		item2 = dictionary.get(key,None)[0].strip()
		item3 = dictionary.get(key,None)[1].strip()
		item4 = dictionary.get(key,None)[2].strip()
		item5 = dictionary.get(key,None)[3].strip()
		item6 = dictionary.get(key,None)[4].strip()
		if([ item1, item2, item3 , item4 , item5, item6] not in previousList):
			file3.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(item1, item2, item3, item4, item5, item6))

with open("result_spelling_errors.txt",'a') as file3:
	for item in spellErrors:
		file3.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(item[0], item[1],item[2],item[3],item[4],item[5]))