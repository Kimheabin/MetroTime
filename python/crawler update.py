from urllib.request import urlopen
from bs4 import BeautifulSoup
import time



site_url = "https://metrotime-for-test.tistory.com/"
latest_title = ""

while True:
	html = urlopen(site_url)
	bsObject = BeautifulSoup(html, "html.parser")
	titles = bsObject.find_all('span', {'class':'title'})
	
	# 업데이트 내용이 있다면 그 내용까지 출력한다.
	if latest_title != titles[0].text:
		print("<--- Update detected --->")
		for t in titles:
			if t.text != latest_title:
				print(t.text)
			else:
				break
		latest_title = titles[0].text # 현재 시점에서 최신 게시물이름을 저장한다.
		print("=====================================")
	else:
		print("<--- No update in", site_url, "--->")
	time.sleep(60)