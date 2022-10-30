drop table memberall cascade constraints purge;
-- 220811 수정
create table memberall(
	email		varchar2(100) NOT NULL,
	pass 		varchar2(16) NOT NULL,
	user_name 	varchar2(40) NOT NULL,
	name		varchar2(40) NOT NULL,
	jumin1		char(6) NOT NULL,
	jumin2		char(7) NOT NULL,
	gender		varchar2(10) NOT NULL,
	phone		varchar2(30) NOT NULL,
	post		char(5) NOT NULL,
	address		varchar2(200) NOT NULL,
	memberfile  varchar2(50),
	primary key(email)
);

select * from memberall;


insert into memberall values ('admin','123','관리자','김땡땡','990909','3012367',
						'남','010-123-456','12345','서울',null);
insert into memberall values ('admin1@gmail.com','123','사용자1','김땡땡','990909','3012367',
						'남','010-123-456','12345','서울',null);
						

--테이블 수정(인데 번거로우니까 중요한 사용자 정보가 없다면 테이블을 새로 만들어주세요)
ALTER TABLE memberall DROP COLUMN domain;
ALTER TABLE memberall MODIFY email VARCHAR(100);
ALTER TABLE memberall MODIFY pass NOT NULL;
ALTER TABLE memberall MODIFY user_name NOT NULL;
ALTER TABLE memberall MODIFY name NOT NULL;
ALTER TABLE memberall MODIFY jumin1 NOT NULL;
ALTER TABLE memberall MODIFY jumin2 NOT NULL;
ALTER TABLE memberall MODIFY gender NOT NULL;
ALTER TABLE memberall MODIFY phone NOT NULL;
ALTER TABLE memberall MODIFY post NOT NULL;
ALTER TABLE memberall MODIFY address NOT NULL;


--220811 수정
ALTER TABLE memberall MODIFY user_name VARCHAR(40);
ALTER TABLE memberall MODIFY name VARCHAR(40);
