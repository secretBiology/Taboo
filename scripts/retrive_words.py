from bs4 import BeautifulSoup
import requests

AvoidList = ["sex","gay","sperm","lesbian","fuck","fag","fap", "penis" ,  "crotch" ,  "cock",  "dick" ,  "vagina", "wet", "pussy" , "clit " ,  "saliva", "orgasm", "boob"] 

url = 'http://taboogame.net/?'
with open('words.txt', 'a') as file:
	for i in range(1000):
			try:
				results = requests.get(url)
				if results.status_code == 200:
					soup = BeautifulSoup(results.content, "html.parser")
					MainWord_Tag = soup.findAll('div', style="padding-top:15px;")
					word1_Tag = soup.findAll('div', style="PADDING-TOP:30PX;")
					SubWords = soup.findAll('div', style="PADDING-TOP:7PX;")
					MainWord = MainWord_Tag[0].text
					firstWord = word1_Tag[0].text
					subWord_List = []
					for word in SubWords:
						subWord_List.extend([  word.text ])
					spamCheck_sentence = subWord_List[0] + subWord_List[1] + subWord_List[2] + subWord_List[3]
					if any(word in spamCheck_sentence.lower() for word in AvoidList):
						print("Spam Detected")
					else:
						file.write(("{ \" %s \" ,  \" %s \" ,  \" %s \" ,  \" %s \",  \" %s \" ,  \" %s \"} ,\n")%(MainWord.title(), firstWord.title(), subWord_List[0].title(), subWord_List[1].title(), subWord_List[2].title(), subWord_List[3].title() ))
				else:
					print("Error")
			except ValueError:
				pass

