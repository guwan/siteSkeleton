insert into user values (1,'user','niceuser','user@qq.com','$2a$10$CWqS8.KPJm/ysb829TvCz.V8Yku6tGJyL9BnDJG.ohMpwZT5VmkHe','male',null,'title','china','bio','avatarUrl',0,'levelName',0,0,'videoEmbeds',null,null,true,true,true,true);
insert into user values (2,'admin','niceadmin','admin@qq.com','$2a$10$CWqS8.KPJm/ysb829TvCz.V8Yku6tGJyL9BnDJG.ohMpwZT5VmkHe','female',null,'title','china','bio','avatarUrl',0,'levelName',0,0,'videoEmbeds',null,null,true,true,true,true);

insert into authority values (1,'user','ROLE_USER');
insert into authority values (2,'admin','ROLE_USER');
insert into authority values (3,'admin','ROLE_ADMIN');
commit;