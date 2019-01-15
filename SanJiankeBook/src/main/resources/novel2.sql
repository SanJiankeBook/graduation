create table novel_type(					
	tid int auto_increment,		
	tname varchar(15),						
	standby_1 varchar(100),		
	standby_2 varchar(100),
	standby_3 varchar(100),
	primary key (tid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
insert into novel_type(tname,standby_1,standby_2,standby_3) values('java',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('c++',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('c',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('c#',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('web',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('PHP',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values(' Python ',1,1,1);
insert into novel_type(tname,standby_1,standby_2,standby_3) values('其他',1,1,1);
select * from novel_type;

create table novel(						
	nid int auto_increment,		
	tid int not null,						
	aid int not null,					
	nname varchar(30),						
	npicture varchar(100),						
	ndescription varchar(500),				
	nstatus varchar(15),					
	standby_1 varchar(100),
	standby_2 varchar(100),
	standby_3 varchar(100),
	primary key (nid),
	foreign key (tid) references novel_type(tid),
	foreign key (aid) references author(aid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table novel_chapter(					
	cid int auto_increment,		
	nid int not null,						
	cname varchar(100),						
	caddress varchar(300),					
	standby_1 varchar(100),
	standby_2 varchar(100),
	standby_3 varchar(100),
	primary key (cid),
	foreign key (nid) references novel(nid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


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

create table admin(							
	adid int primary key auto_increment,		
	adnumber varchar(30),					
	adpassword varchar(30),					
	standby_1 varchar(100),
	standby_2 varchar(100),
	standby_3 varchar(100)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

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

create table user(						
	uid int auto_increment,	
	uname varchar(30),					
	u_number varchar(30),				
	upassword varchar(30),				
	usex varchar(10),					
	standby_1 varchar(100),
	standby_2 varchar(100),
	standby_3 varchar(100),
	primary key (uid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into admin(adnumber,adpassword) values(1001,'a');
commit;
select * from user;
select * from author
select * from admin
select * from novel for update
delete from novel
delete from novel_chapter
