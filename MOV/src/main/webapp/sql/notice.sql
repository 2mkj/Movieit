drop table community cascade constraints purge;
select * from notice;
--커뮤니티 테이블
CREATE TABLE notice(
	BOARD_NUM 		NUMBER,			  --글 번호
	BOARD_NAME		VARCHAR2(100) references memberall(email) on delete cascade,	  --작성자
	BOARD_USER_NAME VARCHAR2(40),
	BOARD_SUBJECT	VARCHAR2(300),	  --제목
	BOARD_CONTENT	VARCHAR2(4000),	  --내용
	BOARD_READCOUNT	NUMBER,			--글의 조회수 
	BOARD_DATE DATE default sysdate, --글의 작성 날짜
	PRIMARY KEY(BOARD_NUM)
);

--커뮤니티 댓글 테이블
CREATE TABLE comm(
num 			number		 primary key,
id				varchar2(30) references memberall(email),
content			varchar2(200),
reg_date		date,
comment_board_num number  references community(board_num) on delete cascade,
							            --comm테이블이 참조하는 보드 글 번호
comment_re_lev	number(1) check(comment_re_lev in (0,1,2)), --원문이면0 답글이면 1
comment_re_seq  number,	 --원문이면 0, 1레벨이면 1레벨 시퀀스 + 1
comment_re_ref  number	 --원문은 자신 글 번호, 답글이면 원문 글번호
);
--게시판 글이 삭제되면 참조하는 댓글도 삭제됩니다.
drop sequence com_seq;
--시퀀스를 생성합니다.
create sequence com_seq;

--▼ 아래는 쿼리 테스트
-------------------------------------------------------------------------------------
drop table  comm CASCADE CONSTRAINTS;
drop table community CASCADE CONSTRAINTS;
insert into community(BOARD_NUM, BOARD_SUBJECT, BOARD_NAME)values(4,'두번째','admin2');
select * from comm
insert into comments values(1, 'admin1','아아', sysdate, 13, 0,0,1)

select *
from (select rownum rnum, j.*
	  from (
	  		select board_num, board_subject, nvl(cnt, 0) as cnt
			from community left outer join (select comment_num, count(*) cnt
				 						from comments 
				 						group by comment_num)
			on board_num = comment_num
	 		order by board_num desc ) j
	  where rownum <= 10 
	  )
where rnum>=1 and rnum<=10;

--닉네임 출력하려면..
select *
from (select rownum rnum, j.*
	  from (
	  		select b.* , m.user_name, nvl(cnt, 0) as cnt
			from community b left outer join (select comment_num, count(*) cnt
				 						from comments 
				 						group by comment_num) c
			                on b.board_num = c.comment_num
							left join memberall m
							on b.board_name = m.email
	 		order by board_num desc ) j
	  where rownum <= 10 
	  )
where rnum>=1 and rnum<=10;


select c.*, m.user_name from community c left join memberall m
on c.board_name = m.email

-- getmyListCount
select count(*) from community 
where board_name = ? 
and board_subject like ? 
or board_content like ? 

-- getmyList(Search)
select * 
from (select rownum rnum, j.*
	  from (select community.*,  nvl(cnt, 0) cnt 
	        from community left outer join (select comment_num, count(*) cnt 
				                            from comments 
											group by comment_num) 
			
			on board_num = comment_num 
			where board_name = 'admin2' 
			and (board_subject like '%ㅇ%' or board_content like '%ㅇ%')
			order by board_num desc ) j  
	  where rownum <= 10 
	  )
where rnum>=1 and rnum<=10;


update community set BOARD_SUBJECT='수정1', BOARD_CONTENT='수정2' where BOARD_NUM=12

update community  set BOARD_READCOUNT = BOARD_READCOUNT + 1  where BOARD_NUM = 1

select * 
 from (select rownum rnum, j.* 
	   from (select b.* , m.user_name, nvl(cnt, 0) as cnt 
			 from community b left outer join (select comment_board_num, count(*) cnt 
						                       from comm 
											   group by comment_board_num) c 
			 on b.board_num = c.comment_board_num 
			 left join memberall m 
			 on b.board_name = m.email 
			 where m.user_name like '%1%' 
			 order by board_num desc ) j 
	  where rownum <= 10 
	  )
where rnum>=1 and rnum<=10;


--댓글
select * 
 from (select rownum rnum, j.* 
	   from (select comm.* 
			 from comm
			 where id = 'admin1'
			 order by reg_date desc ) j 
	  where rownum <= 10 
	  )
where rnum>=1 and rnum<=10;


update comm set content='삭제된 댓글 입니다.' where num=22

--작성자 다른글 보기 listcount()
select count(*) 
from community left join memberall
on board_name = email
where user_name = '김유저' 

--작성자 다른글 보기 getlist()
select *  
from (select rownum rnum, j.*  
	  from (select community.*, memberall.user_name    
			from community left join memberall
			on board_name = email
			where user_name = '김유저'  
		    order by board_num desc ) j   
	  where rownum <= 20  
	  )  
where rnum>=11 and rnum<=20

---------------
select count(*) from community 
where board_name = 'admin1@gmail.com'  

select *   
from (select rownum rnum, j.* 
	  from (select * from community 
	  		where board_name = 'admin1@gmail.com'  
	  		order by board_num desc ) j 
	  where rownum <= 10 
	 ) 
where rnum>=1 and rnum<=10