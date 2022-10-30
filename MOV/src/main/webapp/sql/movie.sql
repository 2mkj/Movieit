-- ※ 관리자만 등록/수정 가능 ※ 

select * 
from (select rownum rnum, j.* 
	 from (select review.review_title,review.review_move_name , movie.poster 
							                  from review inner join movie 
							   				on review.review_move_name = movie.title  
							  				where review.review_id = 'admin1@gmail.com' 
							   				order by review_date desc ) j  
							        	where rownum <= 10
							   			) 
							    where rnum>=1 and rnum<=10
select * from movie
update movie set mnum=12 where title='비틀쥬스'
drop table movie cascade constraints purge;
--1. 영화 테이블
CREATE TABLE movie(
	mnum 		NUMBER,			    --글 번호
	title		VARCHAR2(300),      --영화 제목
	director	VARCHAR2(300),      --감독
	actor		VARCHAR2(300),      --출연 배우
	mdate	 	VARCHAR2(300),	  	--개봉일
	content 	VARCHAR2(4000),	 	--줄거리
	poster 		VARCHAR2(100),		--포스터
	PRIMARY KEY(mnum)
);
--2. 영화 테이블 시퀀스
create sequence movie_seq;
drop sequence movie_seq
--4. 기본 정보 돌리고 난후 아래 쿼리 실행
ALTER SEQUENCE movie_seq INCREMENT BY 13;


--3. 기본 정보
insert into movie values(1,'헌트','이정재','이정재,정우성,전혜진,허성태 외','2022','[조직 내 숨어든 스파이를 색출하라!
 ‘사냥꾼’이 될 것인가, ‘사냥감’이 될 것인가!]
 
망명을 신청한 북한 고위 관리를 통해 정보를 입수한 안기부 해외팀 ‘박평호’(이정재)와 국내팀 ‘김정도’(정우성)는 조직 내 숨어든 스파이 ‘동림’ 색출 작전을 시작한다.

스파이를 통해 일급 기밀사항들이 유출되어 위기를 맞게 되자 날 선 대립과 경쟁 속, 해외팀과 국내팀은 상대를 용의선상에 올려두고 조사에 박차를 가한다.
 
찾아내지 못하면 스파이로 지목이 될 위기의 상황, 서로를 향해 맹렬한 추적을 펼치던 ‘박평호’와 ‘김정도’는 감춰진 실체에 다가서게 되고, 마침내 ‘대한민국 1호 암살 작전’이라는 거대한 사건과 직면하게 되는데……
 
 하나의 목표, 두 개의 총구
 의심과 경계 속 두 남자의 신념을 건 작전이 시작된다','20211792.jpg');
insert into movie values(2,'한산: 용의 출현','김한민','박해일,변요한,김성규,안성기 외','2022','나라의 운명을 바꿀 압도적 승리의 전투가 시작된다!

1592년 4월, 조선은 임진왜란 발발 후 단 15일 만에 왜군에 한양을 빼앗기며 절체절명의 위기에 놓인다. 조선을 단숨에 점령한 왜군은 명나라로 향하는 야망을 꿈꾸며 대규모 병력을 부산포로 집결시킨다.

한편, 이순신 장군은 연이은 전쟁의 패배와 선조마저 의주로 파천하며 수세에 몰린 상황에서도 조선을 구하기 위해 전술을 고민하며 출전을 준비한다.

하지만 앞선 전투에서 손상을 입은 거북선의 출정이 어려워지고, 거북선의 도면마저 왜군의 첩보에 의해 도난당하게 되는데…

왜군은 연승에 힘입어 그 우세로 한산도 앞바다로 향하고, 이순신 장군은 조선의 운명을 가를 전투를 위해 필사의 전략을 준비한다.

1592년 여름, 음력 7월 8일 한산도 앞바다, 압도적인 승리가 필요한 조선의 운명을 건 지상 최고의 해전이 펼쳐진다.','20209343.jpg');
insert into movie values(3,'비상선언','한재림','송강호,이병헌,전도연,김남길,임시완 외','2022','[비상선언: 재난 상황에 직면한 항공기가 더 이상 정상적인 운항이 불가능하여, 무조건적인 착륙을 요청하는 비상사태를 뜻하는 항공 용어]

베테랑 형사 팀장 구인호(송강호)는 비행기 테러 예고 영상 제보를 받고 사건을 수사하던 중 용의자가 실제로 KI501 항공편에 타고 있음을 파악한다.

딸의 치료를 위해 비행 공포증임에도 불구하고 하와이로 떠나기로 한 박재혁(이병헌)은 주변을 맴돌며 위협적인 말을 하는 낯선 이가 신경 쓰인다.

인천에서 하와이로 이륙한 KI501 항공편에서 원인불명의 사망자가 나오고, 비행기 안은 물론 지상까지 혼란과 두려움의 현장으로 뒤바뀐다.

이 소식을 들은 국토부 장관 김숙희(전도연)는 대테러센터를 구성하고 비행기를 착륙시킬 방법을 찾기 위해 긴급회의를 소집하는데…','20196410.jpg');
insert into movie values(4,'탑건: 매버릭','조셉 코신스키','톰 크루즈,제니퍼 코넬리,마일즈 텔러 외','2022','한순간의 실수도 용납되지 않는 하늘 위,
가장 압도적인 비행이 시작된다!

최고의 파일럿이자 전설적인 인물 매버릭(톰 크루즈)은 자신이 졸업한 훈련학교 교관으로 발탁된다.
그의 명성을 모르던 팀원들은 매버릭의 지시를 무시하지만
실전을 방불케 하는 상공 훈련에서 눈으로 봐도 믿기 힘든 전설적인 조종 실력에 모두가 압도된다.

매버릭의 지휘 아래 견고한 팀워크를 쌓아가던 팀원들에게 국경을 뛰어넘는 위험한 임무가 주어지자
매버릭은 자신이 가르친 동료들과 함께 마지막이 될지 모를 하늘 위 비행에 나서는데…','20194376.jpg');
insert into movie values(5,'DC 리그 오브 슈퍼-펫','자레드 스턴,샘 J. 레빈','드웨인 존슨,케빈 하트,키아누 리브스 외','2022','하룻강아지 시절, 슈퍼맨과 함께 크립톤 행성에서 지구로 오게 된 슈퍼독 ‘크립토’
환상의 콤비 슈퍼맨과 힘을 합쳐 메트로폴리스를 지키며 평화로운 일상을 보내던 어느 날, 악당 ‘렉스 루터’와 기니피그 ‘룰루’의 계략으로 슈퍼맨과 저스티스 리그의 슈퍼 히어로들이 위험에 처한다.

절체절명의 순간에 초능력을 잃어버리고만 ‘크립토’는 보호소에 떨어진 크립토나이트의 힘으로 어쩌다 슈퍼 펫이 된 유기동물 철벽방어 댕댕이 ‘에이스’, 위대한 꿀꿀이 ‘피비’, 초고속 거북이 ‘머튼’, 썬더볼트 >다람쥐 ‘칩’에게 도움을 요청하는데…','20224965.jpg');
insert into movie values(6,'미니언즈2','카일 발다','스티브 카렐,타라지 P. 헨슨,루시 로리스 외','2022','오랜마니언!

세계 최고의 슈퍼 악당을 꿈꾸는 미니보스 ‘그루’와 그를 따라다니는 미니언들.
어느 날 그루는 최고의 악당 조직 ‘6’의 마법 스톤을 훔치는데 성공하지만
뉴페이스 미니언 ‘오토’의 실수로 스톤을 잃어버리고 빌런6에게 납치까지 당한다.
미니보스를 구하기 위해 잃어버린 스톤을 되찾아야 하는 ‘오토’, 그리고 쿵푸를 마스터해야 하는 ‘케빈’, ‘스튜어트’, ‘밥’!

올여름 극장가를 점령할 MCU(미니언즈 시네마틱 유니버스)가 돌아온다!','20205362.jpg');
insert into movie values(7,'바다 탐험대 옥토넛 : 탐험선 대작전','스테파니 심슨','하성용,정재헌,엄상현,윤승희 외','2022','#1. 대왕고래 구조작전 
이른 아침, 옥토넛 대원들 몰래 탐험에 나섰던 콰지. 그런데 꼬리를 다쳐 가라앉을 위기에 처한 대왕고래를 발견한다?! 옥토넛, 탐험선 A, B, C, D, E, X 타고 지금 바로 총출동! 커다란 그물로 대왕고래를 끌어올리는 특급 작전 시작이다! 

#2. 신비에 싸인 고래 
우연히 신비로운 부채이빨부리고래 모자를 발견한 셸링턴과 대쉬. 그런데 엄마 고래가 이빨이 빠져 괴로워하고 있다. 구조와 치료를 위해 탐험선을 타고 총출동하는 옥토넛이지만, 경계심 가득한 고래 모자는 옥토넛의 접근을 쉽게 허락하지 않는데... 

#3. 바다 탐험대와 유령선 
심해로 떨어지는 쓰레기를 추적하다 의문의 배를 발견하는 옥토넛. 어디선가 들리는 으스스한 소리에 콰지는 유령선이라며 호들갑이다. 소리의 정체는 과연 무엇일까? 옥토넛 앞에 놓인 배는 정말로 유령선일까? 

#4. 대서양 참다랑어 
산란을 위해 지중해로 이동하던 대서양 참다랑어 티나. 그런데 도중에 낚싯바늘이 입에 걸려 무리에서 낙오되고 만다. 티나가 늦지 않게 참다랑어 떼에 다시 합류할 수 있게 하려면 옥토넛의 가장 빠른 탐험선 R의 힘을 빌려야만 한다! 

#5. 바다 탐험대와 위험천만 먹이 쟁탈전 
산호초 상어들을 관찰하러 나갔던 셸링턴과 대쉬. 실수로 물고기 비스킷을 흘리자, 배고픈 암초상어들이 정신없이 달려드는데! 꼼짝없이 상어 무리에 갇힌 셸링턴과 대쉬를 구하기 위해 하늘에는 탐험선 H가, 바닷속에는 트윅이 만든 특별한 상어 탐험선이 출동한다!','20226034.jpg');
insert into movie values(8,'뽀로로 극장판 드래곤캐슬 대모험','강승훈,윤제완','이선,이미자,김현지,홍소영,정미숙 외','2022','깊은 산속 드래곤캐슬에
꼬마 드래곤 왕 아서의 막강한 힘이 봉인된 보석 ‘드래곤 하트’를 빼앗아
왕이 되려는 수상한 마법사 게드가 나타난다.

한편, 우연히 ‘드래곤 하트’의 힘을 흡수한 크롱이
자이언트 크롱으로 변하면서 뽀로로와 친구들 역시 위험에 처한다.

아서와 뽀로로와 친구들은
악당 마법사 게드로부터 힘을 되찾고 친구들을 구하기 위해
무적의 메카드래곤을 만들어 드래곤캐슬로 향하는데…!

뽀로로와 친구들의 판타지 액션 어드벤처!
과연 드래곤캐슬과 소중한 친구들을 지켜낼 수 있을까?','20225190.jpg');
insert into movie values(9,'헤어질 결심','박찬욱','박해일,탕웨이,이정현 외','2022','산 정상에서 추락한 한 남자의 변사 사건.
담당 형사 ''해준''(박해일)은
사망자의 아내 ''서래''(탕웨이)와 마주하게 된다.

"산에 가서 안 오면 걱정했어요, 마침내 죽을까 봐."

남편의 죽음 앞에서 특별한 동요를 보이지 않는 ''서래''.
경찰은 보통의 유가족과는 다른 ''서래''를 용의선상에 올린다.
''해준''은 사건 당일의 알리바이 탐문과 신문,
잠복수사를 통해 ''서래''를 알아가면서
그녀에 대한 관심이 점점 커져가는 것을 느낀다.

한편, 좀처럼 속을 짐작하기 어려운 ''서래''는
상대가 자신을 의심한다는 것을 알면서도
조금의 망설임도 없이 ''해준''을 대하는데….

진심을 숨기는 용의자
용의자에게 의심과 관심을 동시에 느끼는 형사
그들의 <헤어질 결심>','20209654.jpg');
insert into movie values(10,'외계+인 1부','최동훈','류준열,김우빈,김태리,소지섭,염정아,조우진 외','2022','"아주 오래전부터 외계인은 그들의 죄수를
인간의 몸에 가두어 왔다"

2022년 현재, ''가드''와 ''썬더''는 인간의 몸에 가두어진 외계인 죄수를 관리하며 지구에 살고 있다.

어느 날, 서울 상공에 우주선이 나타나고 형사 ''문도석''은 기이한 광경을 목격하게 되는데..

한편, 630년 전 고려에선 얼치기 도사 ''무륵''과 천둥 쏘는 처자 ''이안''이 엄청난 현상금이 걸린 신검을 차지하기 위해 서로를 속고 속이는 가운데

신검의 비밀을 찾는 두 신선 ''흑설''과 ''청운'', 가면 속의 ''자장''도 신검 쟁탈전에 나선다. 그리고 우주선이 깊은 계곡에서 빛을 내며 떠오르는데…

2022년 인간 속에 수감된 외계인 죄수를 쫓는 이들
1391년 고려 말 소문 속의 신검을 차지하려는 도사들

시간의 문이 열리고 모든 것이 바뀌기 시작했다!','20208446.jpg');