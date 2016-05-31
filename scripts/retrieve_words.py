#Written by Rohit Suratekar for Python 3.3+ on 31 May 2016
#You might need to change few functions (e.g. like print) to get this working for python 2.7
#This script will retrieve taboo words from online database of  http://taboogame.net/
# Script retrieve words based on HTML DIV tags. You need to check correct tags in case of change in website structure
#You can check tags by checking source code of webpage

from bs4 import BeautifulSoup
import requests
import re

#As this is open database , there will be lot of spam words 
#Make list of words which will be rejected by this script
AvoidList = ["sex","gay","sperm","lesbian","fuck","fag","fap", "penis" ,  "crotch" ,  "cock",  "dick" ,  "vagina", "wet", "pussy" , "clit " ,  "saliva", "orgasm", "boob"] 

url = 'http://taboogame.net/?'

#All retrieved words will be saved in this file. 
#You can use this file later on to review words
with open('words.txt', 'a') as file:
	for i in range(2000):
			try:
				results = requests.get(url)
				if results.status_code == 200:
					soup = BeautifulSoup(results.content, "html.parser")
					MainWord_Tag = soup.findAll('div', style="padding-top:15px;") #You might need to check if this tag is still there
					word1_Tag = soup.findAll('div', style="PADDING-TOP:30PX;") #You might need to check if this tag is still there
					SubWords = soup.findAll('div', style="PADDING-TOP:7PX;") #You might need to check if this tag is still there
					MainWord = MainWord_Tag[0].text
					firstWord = word1_Tag[0].text
					subWord_List = []
					for word in SubWords:
						subWord_List.extend([  word.text ])
					spamCheck_sentence = subWord_List[0] + subWord_List[1] + subWord_List[2] + subWord_List[3]
					re.sub(r'\s+', '', spamCheck_sentence) #Remove all white spaces
					if any(word in spamCheck_sentence.lower().strip() for word in AvoidList):
						print("Spam Detected")
					else:
						file.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(MainWord.title(), firstWord.title(), subWord_List[0].title(), subWord_List[1].title(), subWord_List[2].title(), subWord_List[3].title() ))
				else:
					print("Error")
			except ValueError:
				pass

