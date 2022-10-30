--1. 찜한 영화 테이블
CREATE TABLE zzim(
	zzim_num    number,
	zzim_name	VARCHAR2(30) references memberall(email) on delete cascade NOT NULL,	  --사용자
	zzim_title	VARCHAR2(300),      --제목
	zzim_date	VARCHAR2(300),      --개봉일	
	zzim_poster	VARCHAR2(100),		--포스터
	PRIMARY KEY(zzim_num)
);
--2. 찜한 영화 테이블 시퀀스
create sequence zzim_seq;



--테스트------------------------------------------------
select * from zzim
delete zzim where zzim_num = 34
drop table zzim; drop sequence zzim_seq
-- listcount()
select count(*) from zzim
where zzim_name = 'admin1@gmail.com' 

-- getlist()
select *  
from (select rownum rnum, j.*  
	  from (select * from zzim 
			where zzim_name = 'admin1@gmail.com'  
		    order by zzim_num desc ) j   
	  where rownum <= 8  
	  )  
where rnum>=1 and rnum<= 8

--메인 5개 노출

SELECT  *
FROM(SELECT ROW_NUMBER() OVER(PARTITION BY zzim_title ORDER BY zzim_num DESC ) AS RNUM,
			zzim.*
	FROM zzim )
WHERE RNUM = 1
ORDER BY zzim_num DESC