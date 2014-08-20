drop database if exists ucac;
create database ucac;
use ucac;

create table t_admin(
   id int primary key auto_increment,
   username varchar(10) not null,
   password varchar(255) not null
);

create table t_settings(
   id int primary key auto_increment,
   video tinyint not null check(0<video and video<100),
   picture tinyint not null check(0<picture and picture<100),
   document tinyint not null check(0<document and document<100),
   apply_begin datetime not null,
   apply_end datetime not null,
   appraise_begin datetime not null,
   appraise_end datetime not null,
   tourist_state int not null default 0 check(tourist_state in(0,1))
);

create table t_applicant(
   id int primary key auto_increment,
   username varchar(12) not null,
   password varchar(24) not null,
   community_name varchar(60)  ,
   school varchar(60) not null,
   category tinyint   check(1<=category and category <=6),
   teacher_name	varchar(15) ,
   teacher_phone char(11) ,
   teacher_email varchar(60)  ,
   applicant_state int  default 0
);

create table t_work(
   id int primary key,
   picture_score decimal(5,2) not null check(0<picture_score and picture_score<100),
   video_score decimal(5,2) not null check(0<vedio_score and vedio_score<100),
   document_score decimal(5,2) not null check(0<document_score and document_score<100),
   score decimal(5,2) check(0<score and score<100),
   ranking int      default 0,
   work_state tinyint default -1 check(work_state in(-1,0,1)),
   constraint FK_work_applicant foreign key(id) references t_applicant(id) on delete cascade
);

create table t_expert(
   id int auto_increment primary key,
   username char(4) not null,
   password char(24) not null,
   category tinyint not null check(1<=category and category<=6),
   expert_name varchar(15) not null,
   expert_tel char(11) not null,
   expert_email varchar(60) not null,
   expert_state	int default 1 check(expert_state in(0,1))
);

create table t_expert_assess(
   id int auto_increment primary key,
   applicant_id int not null,
   expert_id int not null,
   picture_score decimal(5,2) not null check(0<picture_score and picture_score<100),
   video_score decimal(5,2) not null check(0<video_score and video_score<100),
   document_score decimal(5,2) not null check(0<document_score and document_score<100),
   score decimal(5,2) not null check(0<score and score<100),
   constraint FK_expert_assess_applicant foreign key(applicant_id) references t_applicant(id) on delete cascade,
   constraint FK_expert_assess_expert foreign key(expert_id) references t_expert(id) on delete cascade 
);

create index t_applicant_category on t_applicant(category);
create index t_expert_category on t_expert(category);
create index t_expert_assess_id on t_expert_assess(expert_id);
alter table t_expert_assess add unique(applicant_id,expert_id);

insert into t_admin values(null,'admin','admin');
 
insert into t_settings values(null,40,20,40,'2014-4-24','2014-4-27','2014-4-28','2014-4-29',0);

