/*schedule 이벤트 만들기*/

create event fillschedule
on schedule 
every 1 minute
on completion not preserve
enable
do
INSERT IGNORE INTO chae(content)
select content
from test
where content like '%채연%';

ALTER EVENT  fillschedule disable;  /*이벤트 준단*/

drop event fillschedule;       /*이벤트 지우기*/
  
SHOW VARIABLES LIKE 'event%' ;    /*이벤트 작동상황 확인*/

SHOW EVENTS ;    /*이벤트 확인*/

