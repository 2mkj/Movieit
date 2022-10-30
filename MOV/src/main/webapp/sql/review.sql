drop table review cascade constraints purge

--리뷰 테이블 추가
create table review(
review_number number constraint review_pk primary key ,
    review_id varchar2(40) ,
    review_title varchar2(50),
    review_user_name varchar2(40),
    review_content varchar2(300),
    review_date date default sysdate,
    review_star varchar2(4) default '0',
    review_readcount number default '0',
    review_move_name varchar2(50),
    review_poster    varchar2(100)        --포스터
);

--리뷰 번호 생성기
create sequence create_review_number;
drop sequence create_review_number;

insert into review(create_review_number.nextval(),review_id, review_title, review_content, review_star, review_readcount, review_move_name) values('admin', 'Test1', 'This is test 1', '0', 0, '헌트');

--데이터 추가
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user1', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user2', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user3', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user4', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user5', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user6', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user7', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user8', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user9', 'Test1', 'This is test 1', '0', 0, '헌트');
insert into review(review_number,review_id, review_title, review_content, review_star, review_readcount, review_move_name) values(create_review_number.nextval,'user0', 'Test1', 'This is test 1', '0', 0, '헌트');

select * from review;
select * from review where review_id ='admin'; 
select * from review where review_move_name ='��ȭ �̸��� Test1'; 
select *
from
	(select *
	from 
		(select rownum rnum, reviewID.*
		from (select * 
				from review 
			 	where review_move_name='��ȭ �̸��� Test1' order by review_id desc) reviewID)
	where rnum <= 5)
where rnum >=2;


------------------------------------------------------------------------------------------------------------
select * from review
delete from review where review_id = '널널' 

select * from review 
where review_id='admin1@gmail.com' and review_move_name='놉'




select *  
							     from (select rownum rnum, j.*  
							      	    from (select review.*   
							                 from review  
							   				where review_id = 'admin1@gmail.com'  
							     			and ( review_title like '%te%' 
							   				or review_content like '%te%' 
							     			or review_move_name like '%te%' )  
							   			order by review_date desc ) j   
							       	where rownum <= 8  
							   		)  
							   where rnum>=1 and rnum<=8
----------------------------------------
insert into review(review_id, review_title, review_content, review_star, review_readcount, review_move_name, review_poster) values('admin1@gmail.com', 'Test1', 'This is test 1', '0', 0, '헌트' ,'20211792.jpg');
insert into review(review_id, review_title, review_content, review_star, review_readcount, review_move_name, review_poster) values('admin1@gmail.com', 'Test2','비상선언을 봤다 재밋었다 특히 ㅇㅇ부분이 좋았따 어쩌고 저쩌고 ', '0', 0, '비상선언','20196410.jpg');
insert into review(review_id, review_title, review_content, review_star, review_readcount, review_move_name, review_poster) values('admin1@gmail.com', '미니언즈 어제 보고옴','미닝너즈 2을 봤다 재밋었다 특히 ㅇㅇ부분이 좋았따 어쩌고 저쩌고 근데 마지막에 어쩌고 저쩌고 이렇고 저렇고 엔딩은 좋앗다 ', '0', 0, '미니언즈2','20205362.jpg');
insert into review(review_id, review_title, review_content, review_star, review_readcount, review_move_name, review_poster) values('admin1@gmail.com', '한산 용의 출현 음 좀','한상 용의 출현을 봤다 재밋었다 특히 ㅇㅇ부분이 좋았따 어쩌고 저쩌고 근데 마지막에 어쩌고 저쩌고 이렇고 저렇고 엔딩은 좋앗다', '0', 0, '한산: 용의 출현','20209343.jpg');
insert into review(review_id, review_title, review_content, review_star, review_readcount, review_move_name, review_poster) values('admin1@gmail.com', '슈퍼펫','DC 리그 오브 슈퍼-펫을 봤다 재밋었다 특히 ㅇㅇ부분이 좋았따 어쩌고 저쩌고 근데 마지막에 어쩌고 저쩌고 이렇고 저렇고 엔딩은 좋앗다', '0', 0, 'DC 리그 오브 슈퍼-펫','20224965.jpg');
insert into review(review_id, review_title, review_content, review_star, review_readcount, review_move_name, review_poster) values('admin1@gmail.com', '재밋슴','가타카을 봤다 재밋었다 특히 ㅇㅇ부분이 좋았따 어쩌고 저쩌고 근데 마지막에 어쩌고 저쩌고 이렇고 저렇고 엔딩은 좋앗다', '0', 0, '가타카','https://ssl.pstatic.net/imgmovie/mdi/mit110/0190/A9074-00.jpg');
insert into review(review_id, review_title, review_content, review_star, review_readcount, review_move_name, review_poster) values('admin1@gmail.com', '헤어질 겨릿ㅁ 어','헤어질 결심을 봤다 재밋었다 특히 ㅇㅇ부분이 좋았따 어쩌고 저쩌고 근데 마지막에 어쩌고 저쩌고 이렇고 저렇고 엔딩은 좋앗다', '0', 0, '헤어질 결심','20209654.jpg');
