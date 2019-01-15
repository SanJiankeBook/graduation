
--create database novel character set utf8;
alter database novel character set utf8;
--show variables like ‘%character%’;

create table novel_type(					
	tid int auto_increment,		
	tname varchar(15),						
	standby_1 varchar(100),		
	standby_2 varchar(100),
	standby_3 varchar(100),
	primary key (tid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into novel_type(tname,standby_1,standby_2,standby_3) values('玄幻',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('修仙',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('都市',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('穿越1',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('轻小说',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('恐怖',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('穿越',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('科幻',1,1,1);


update novel_type
		set tname='科幻'
		where tid=8

update novel_type set tname='穿越1' where tid=4
delete from novel_type;
drop table novel_type;
select * from novel_type;
select tname from novel_type;



select nid,tname,aname,nname,npicture,ndescription,nstatus from novel_type
inner join novel
on novel_type.tid=novel.tid
inner join author
on author.aid=novel.aid

select *
		from novel_type novelType
		inner join novel n
		on novelType.tid=n.tid
		inner join author a
		on a.aid=n.aid
		where novelType.tname='修仙'


--=======================================================================================
create table novel(						
	nid int auto_increment,		
	tid int not null,						
	aid int not null,					
	nname varchar(30),						
	npicture varchar(100),						
	ndescription varchar(500),				
	nstatus varchar(15),					
	standby_1 varchar(100), --待审核 通过 未通过
	standby_2 varchar(100),
	standby_3 varchar(100),
	primary key (nid),
	foreign key (tid) references novel_type(tid),
	foreign key (aid) references author(aid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


insert into novel(tid,aid,nname,npicture,ndescription,nstatus,standby_1,standby_2,standby_3) 
values(2,1,'君王再临','rudaozhisheng.jpg','某一天...','连载',1,1,1);

insert into novel(tid,aid,nname,npicture,ndescription,nstatus,standby_1,standby_2,standby_3) 
values(1,1,'再临','c:\aii\aa','某一天...','未完结','待审核',1,1);
insert into novel(tid,aid,nname,npicture,ndescription,nstatus,standby_1,standby_2,standby_3) 
values(2,1,'神仙道','c:\aii\aa','某一天...','未完结','待审核',1,1);
insert into novel(tid,aid,nname,npicture,ndescription,nstatus,standby_1,standby_2,standby_3) 
values(4,3,'Chaos Child','c:\aii\aa','某一天...','未完结','待审核',1,1);
insert into novel(tid,aid,nname,npicture,ndescription,nstatus,standby_1,standby_2,standby_3) 
values(3,3,'再临123','c:\aii\aa','某一天...','未完结','待审核',1,1);

values(2,2,'再临','jiuxiantu.jpg','某一天...','未完结',1,1,1);

delete from novel where nid=7;

update novel
		set nname='再临123',npicture='c:5555555',nstatus='未完结',
		tid=4
		where novel.nid=5

select * from novel;
select nname from novel;

commit;





--=======================================================================================
create table novel_chapter(					
	cid int auto_increment,		
	nid int not null,						
	cname varchar(100),						
	caddress varchar(300),					
	standby_1 varchar(100),
	standby_2 varchar(100),--章节编号
	standby_3 varchar(100),
	primary key (cid),
	foreign key (nid) references novel(nid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
select max(standby_2) from novel_chapter where nid=2;

insert into novel_chapter(nid,cname,caddress,standby_1,standby_2,standby_3)
values(23,'第一章：45651','d:\ssss\sswq','待审核',1,1);
insert into novel_chapter(nid,cname,caddress,standby_1,standby_2,standby_3)
values(23,'第一章：45651','d:\ssss\sswq','待审核',1,1);
insert into novel_chapter(nid,cname,caddress,standby_1,standby_2,standby_3)
values(24,'第二章：45651','d:\ssss\sswq','待审核',1,1);
insert into novel_chapter(nid,cname,caddress,standby_1,standby_2,standby_3)
values(24,'第三章：45651','d:\ssss\sswq','待审核',1,1);
insert into novel_chapter(nid,cname,caddress,standby_1,standby_2,standby_3)
values(25,'第四章：45651','d:\\ssss\\sswq','待审核',1,1);
select * from novel_chapter;

--=======================================================================================
create table author(					
	aid int auto_increment,	
	uid int,							
	aname varchar(30),					
	pan_name varchar(30),				
	aage int,							
	agrade varchar(20),					
	acard varchar(30),					
	atel varchar(30),					
	standby_1 varchar(100),
	standby_2 varchar(100),
	standby_3 varchar(100),
	primary key (aid),
	foreign key (uid) references user(uid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into author(uid,aname,pan_name,aage,agrade,acard,atel,standby_1,standby_2,standby_3) 
values(1,'赵亮3333','天魁',12,'至尊','45203696661233','123589666',1,1,1);

insert into author(uid,aname,pan_name,aage,agrade,acard,atel,standby_1,standby_2,standby_3) 
values(2,'赵亮22222','天魁_1',10,'大成','45203696661233','123589666',1,1,1);

insert into author(uid,aname,pan_name,aage,agrade,acard,atel) 
values(3,'a','a',4,'a','1111111111','222222222');

update author set pan_name='进去欧',aage=50,acard='5556999',atel='999999' where aid=1

drop table author;
select * from author;



--=======================================================================================
create table user(						
	uid int auto_increment,	
	uname varchar(30),					
	u_number varchar(30),				
	upassword varchar(30),				
	usex varchar(10),					
	standby_1 varchar(100),--用户的手机号码
	standby_2 varchar(100),
	standby_3 varchar(100),
	primary key (uid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into user(uname,u_number,upassword,usex,standby_1,standby_2,standby_3)
values('巨无霸','469058237','123456','男',1,1,1);
insert into user(uname,u_number,upassword,usex,standby_1,standby_2,standby_3)
values('巨无霸2','469058','123','女',1,1,1);

 update user set upassword='aa' where u_number='469058237'
insert into user(uname,u_number,upassword,usex) values('a','469058237','a','男');
insert into user(uname,u_number,upassword,usex) values('b','469058237','b','男');
insert into user(uname,u_number,upassword,usex) values('c','469058237','c','女');
insert into user(uname,u_number,upassword,usex) values('d','469058237','d','男');
insert into user(uname,u_number,upassword,usex) values('f','469058237','f','女');

select * from user limit 0,5


drop table user;
select * from user;
delete from user where uid=4


--=======================================================================================
create table user_talk(						
	utid int primary key auto_increment,	
	nid int not null,						
	uid int not null,						
	utdate varchar(30),						
	utcontent varchar(500),					
	standby_1 varchar(100),
	standby_2 varchar(100),
	standby_3 varchar(100),
	foreign key (uid) references user(uid),
	foreign key (nid) references novel(nid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


insert into user_talk(nid,uid,utdate,utcontent,standby_1,standby_2,standby_3)
values(1,1,'2016-5-6','很好看',1,1,1);
insert into user_talk(nid,uid,utdate,utcontent,standby_1,standby_2,standby_3)
values(4,4,'2016-5-6','很好看',1,1,1);
commit;

select ut.utdate,ut.utcontent,u.uname from  user_talk ut,user u where ut.uid=u.uid and ut.uid=4 ORDER BY utdate DESC limit 0,5;



--=======================================================================================
drop tabe user_book;
create table user_book(					
	ubid int primary key auto_increment,	
	nid int not null,						
	uid int not null,						
	ubdate varchar(30),						
	standby_1 varchar(100),
	standby_2 int,
	standby_3 varchar(100),
	foreign key (uid) references user(uid),
	foreign key (nid) references novel(nid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

alter table user_book modify column standby_2 int; --修改字段

insert into user_book(nid,uid,ubdate,standby_1,standby_2,standby_3)
values(1,1,'2016-5-8',1,1,1);
insert into user_book(nid,uid,ubdate,standby_1,standby_2,standby_3)
values(3,1,'2016-5-8',1,1,1);


select * from user_book


	select novel.nid,novel_type.tname,author.pan_name,npicture,ndescription,nstatus ,from
	select *from
			user_book 
			inner join author
			on user_book.uid=author.uid  where user_book.nid=3 and user_book.uid=1
			left join novel
			on author.aid=novel.aid 
			inner join novel_type
			on novel_type.tid=novel.tid
			where user_book.nid=3 and user_book.uid=1
select * from user_book where nid=3 and uid=1;
commit
delete from user_book where uid=1 and nid in(1,3)

select ,e from

select author.pan_name,user_book.ubdate from
select * from
user_book inner join author
on user_book.uid=author.uid  where  user_book.uid=1

select  novel.nid,novel.nname,novel_type.tname,novel.npicture,novel.ndescription,
novel.nstatus from
user_book inner join novel
on novel.nid=user_book.nid 
inner join novel_type
on novel_type.tid=novel.tid
where user_book.nid=3 and user_book.uid=1

inner join novel on novel.aid=author.aid 
inner join novel_type on novel_type.tid=novel.tid
 where user_book.nid=3 and user_book.uid=1


--=======================================================================================
create table admin(							
	adid int primary key auto_increment,		
	adnumber varchar(30),					
	adpassword varchar(30),					
	standby_1 varchar(100),
	standby_2 varchar(100),
	standby_3 varchar(100)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
select * from admin;
insert into admin(adnumber,adpassword) values(1001,'a');
commit;






--测试
	select a.aname 
		from novel n
		inner join author a
		on n.aid=a.aid
		where n.nid=1	

	select cname from novel_chapter where nid=1 having max(cid);

	select count(cid) from 	novel_chapter;


	select nname 
	from novel n
	inner join novel_type nt
	on n.tid=nt.tid
	where nt.tname='修仙'
	
	
	select novelType.tname 
		from novel n
		inner join novel_type novelType
		on n.tid=novelType.tid
		where n.nname='君王再临'
		

	select nid,novel_type.tname,author.aname,author.pan_name,nname,npicture,ndescription,nstatus from novel_type
			inner join novel
			on novel_type.tid=novel.tid
			inner join author
			on author.aid=novel.aid
			where author.pan_name like '赵%' or nname like '赵%'
			limit 1,2

	select a.*
		from novel n
		inner join author a 
		on n.aid=a.aid
		 where nid=1	
		
	
		 
	select n.*
		from novel n
		inner join novel_type novelType
		on n.tid=novelType.tid
		where novelType.tname='修仙'	 
		 
		

	select * from user where u_number=469058237;
		
		
	select a.* 
		from user u
		inner join author a
		on u.uid=a.uid
		where u.uid=1	
		
	select * from novel where aid=1	
	
	
	select
		nid,novel_type.tname,nname,npicture,ndescription,nstatus
		from novel_type
		inner join novel
		on novel_type.tid=novel.tid
		where nid=1

	update novel
		set nname='我是谁',npicture='c:5555555',nstatus='完结',
		tid='轻小说'
		where novel.nid=1
		
	select nid,novel_type.tname,author.aname,author.pan_name,nname,npicture,ndescription,nstatus,novel.standby_1
			from novel_type
			inner join novel
			on novel_type.tid=novel.tid
			inner join author
			on author.aid=novel.aid
			where novel.tid=3
			limit 0,5
			
			
		select novel.*,aname,tname
		from novel_type
		inner join novel
		on novel_type.tid=novel.tid
		inner join author
		on author.aid=novel.aid
		where novel.tid=1
		limit 0,5	
		