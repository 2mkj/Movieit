drop table comment_table cascade constraints purge

create table comment_table(
	comment_number number constraint comment_pk primary key,
	review_number_fk number,
	comment_id varchar2(40),
	comment_content varchar2(600),
	comment_password varchar2(30),
	comment_review_name varchar2(300),
	comment_date date default sysdate,
	constraint comment_fk foreign key(review_number_fk) references review(review_number) on delete cascade
)

create sequence create_comment_number;
drop sequence create_comment_number;

insert into comment_table(comment_number, comment_id, comment_content, comment_password,comment_review_name ) values(create_comment_number.nextval, 'admin', '�̰��� ����̴�.', '1234', '��ȭ �̸��� Test1');
insert into comment_table(comment_number, comment_id, comment_content, comment_password,comment_review_name ) values(create_comment_number.nextval, 'admin', '�̰��� ����̴�.', '1234', '��ȭ �̸��� Test1');
insert into comment_table(comment_number, comment_id, comment_content, comment_password,comment_review_name ) values(create_comment_number.nextval, 'admin', '�̰��� ����̴�.', '1234', '��ȭ �̸��� Test1');
insert into comment_table(comment_number, comment_id, comment_content, comment_password,comment_review_name ) values(create_comment_number.nextval, 'admin', '�̰��� ����̴�.', '1234', '��ȭ �̸��� Test1');
insert into comment_table(comment_number, comment_id, comment_content, comment_password,comment_review_name ) values(create_comment_number.nextval, 'admin', '�̰��� ����̴�.', '1234', '��ȭ �̸��� Test1');

select * from comment_table;