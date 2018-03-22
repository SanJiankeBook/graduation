--给examineeclass 添加  studentCount字段的默认值。 
alter table  examineeclass modify  studentcount int  default 0; 

select * from examineeclass;

commit;