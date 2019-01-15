select * from systemuser;

update systemuser set status=1

select * from authority;

select * from examinee;
select * from studentfees;

delete from studentfees where studentfeeid in (1,2,3)

alter table systemuser change uname status int 


update systemuser set password='e6ee9813edf81b9e95a92fb5c66871dda80a0b2f'
update examinee set password='e6ee9813edf81b9e95a92fb5c66871dda80a0b2f'

select distinct s.authorities,s.remark from SystemUser s

delete from systemuser where id=22;

delete from systemuser where id in( 5 )

select * from examinee;


select editionName, semester, subjectname
from edition, subject
where edition.id=subject.editionid 
order by semester

select * from subject;

select * from examineeclass;

update examineeclass set overDate='2014-06-06' where id=6