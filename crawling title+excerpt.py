# 터미널(크롤링) - pip install requests beautifulsoup4
# 터미널(db+필터링) - pip install pymysql db
from urllib.request import urlopen
from bs4 import BeautifulSoup
import time
import pymysql

conn = pymysql.connect(host='localhost', user='root', password='1234',db='mysql', charset='utf8')
cur = conn.cursor()
cur.execute("USE crawl_data")

# 도플소프트 티스토리
site_url = "https://doppelsoft.tistory.com/category/%EC%A7%80%ED%95%98%EC%B2%A0%EC%A2%85%EA%B2%B0%EC%9E%90%20%EA%B3%B5%EC%A7%80%EC%82%AC%ED%95%AD/%EC%A7%80%ED%95%98%EC%B2%A0%20%EC%95%8C%EB%A6%BC%20(%EC%A7%80%EC%97%B0%2C%EC%82%AC%EA%B3%A0)"
# 갱신 테스트용 티스토리
# site_url = "https://metrotime-for-test.tistory.com/"

latest_title = ""
latest_content = ""

while True:
	html = urlopen(site_url)
	bsObject = BeautifulSoup(html, "html.parser")
	titles = bsObject.find_all('span', {'class':'title'})
	contents = bsObject.find_all('span', {'class':'excerpt'})
	
	# 업데이트 내용이 있다면 그 내용까지 출력
	if latest_title != titles[0].text:
		print("<--- Update detected --->")
		for title, content in zip(titles, contents): #반복을 수행할 문장들
			if title.text != latest_title: # 최신 날짜 이후에 등장한 게시물만 처리
				cur.execute(
					"INSERT IGNORE metroinfo (title, content) VALUES (%s, %s)", # 테이블 - metroinfo
					# "INSERT IGNORE test (title, content) VALUES (%s, %s)", # 테이블 - test
					(title.text, content.text))
				cur.connection.commit()
				print(f"\nTitle: {title.text}\nContent: {content.text}")
			else:
				break
		latest_title = titles[0].text # 현재 시점에서 최신 게시글 제목을 저장
		latest_content = contents[0].text # 현재 시점에서 최신 게시글 내용을 저장
		cur.execute(
			"INSERT IGNORE metroinfo (title, content) VALUES (%s, %s)", # 테이블 - metroinfo
			# "INSERT IGNORE test (title, content) VALUES (%s, %s)", # 테이블 - test
			(latest_title, latest_content))
		cur.connection.commit()
		print("=====================================")
	else:
		print("<--- No update in Dopplesoft Tistory --->")
	time.sleep(60)
