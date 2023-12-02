from urllib.request import urlopen
from bs4 import BeautifulSoup
import schedule
import time
import pymysql
import re

conn=pymysql.connect(host='localhost', user='root', password='1234',db='mysql', charset='utf8')
cur=conn.cursor()


cur.execute("USE metro") 

site_url= "https://doppelsoft.tistory.com/category/%EC%A7%80%ED%95%98%EC%B2%A0%EC%A2%85%EA%B2%B0%EC%9E%90%20%EA%B3%B5%EC%A7%80%EC%82%AC%ED%95%AD"
latest_title = ""

while True:
	html = urlopen(site_url)
	bsObject = BeautifulSoup(html, "html.parser")
	titles = bsObject.find_all('span', {'class':'title'})
	
	
	# 업데이트 내용이 있다면 그 내용까지 출력한다.
	if latest_title != titles[0].text:
		print("<--- Update detected --->")
		for t in titles:    #반복을 수행할 문장들
			if t.text != latest_title:     #조건문(만약에~)
				cur.execute(
        "INSERT IGNORE push (content) VALUES (%s)",
    (t.text) 
	)
				print(t.text)
				
			else:
				break
		latest_title = titles[0].text # 현재 시점에서 최신 게시물이름을 저장한다.
		cur.execute(
        "INSERT IGNORE push (content) VALUES (%s)",
    (latest_title)
	)
		
		print("=====================================")
	else:
		print("<--- No update in", site_url, "--->")
		cur.connection.commit()
	time.sleep(60)

	
 



    





    
