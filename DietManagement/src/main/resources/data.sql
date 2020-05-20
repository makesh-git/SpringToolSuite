insert into batches values ('100','This batch is for the users whose BMI is above 25','AboveBMI25','AboveBMI25','15/03/2020');
insert into batches values ('101','This batch is for the users whose BMI is below 25','BelowBMI25','BelowBMI25','15/03/2020');

insert into groups values ('100','This group contains the users from Chennai locality','Chennai','Chennai','12/03/2020');
insert into groups values ('101','This group contains the users from Banglore locality','Banglore','Banglore','12/03/2020');
insert into groups values ('102','This group contains the users of Male Category','Mens','Mens','12/03/2020');
insert into groups values ('103','This group contains the users of Female Category','Ladies','Ladies','12/03/2020');  
insert into groups values ('104','This group contains the users having Vegetarian food diet','Vegetarian','Vegetarian','12/03/2020'); 


insert into LOGIN_CREDENTIALS values('admin@gmail.com','123456','Admin');
insert into LOGIN_CREDENTIALS values('makesh@gmail.com','123456','User');
insert into LOGIN_CREDENTIALS values('kumar@gmail.com','123456','User');
insert into LOGIN_CREDENTIALS values('karthik@gmail.com','123456','User');

insert into LOGIN_CREDENTIALS values('vijay@gmail.com','123456','Motivator');
insert into LOGIN_CREDENTIALS values('surya@gmail.com','123456','Motivator');
insert into LOGIN_CREDENTIALS values('ajith@gmail.com','123456','Motivator');

insert into users values ('makesh@gmail.com','23, West Tambarm,Nehru Street','23','24','Chennai','India','No Restrictios','makesh@gmail.com','Non-Vegetarian','Male','156','Not any Medical Restrictions','8967452310','Makesh','PASS','601023','Not Applicable','To lead a healthy life','ADDED BY ADMIN','YUWY2','15/04/2020','TAMILNADU','67','100','100');
insert into users values ('kumar@gmail.com','12,Sarjapur,Banglore','22','26','Banglore','India','Nothing','kumar@gmail.com','Vegetarian','Male','162','Not any','7856324570','Kumar','PASS','654908','Not Applicable','For Healthy life','ADDED BY ADMIN','GHAKQ','15/03/2020','Karnataka','67','101','101');
insert into users values ('karthik@gmail.com','10,Edachira,Ernakulam','25','26','Kochin','India','Nothing','karthik@gmail.com','Vegetarian','Male','166','Not any','8192834756','Karthik','PASS','564123','Not Applicable','To reduce my weight','ADDED BY ADMIN','GHAKQ','15/03/2020','Kerala','79','100','104');




insert into motivator values('vijay@gmail.com','20,Xyz Building,Kochin','30','AboveBMI25','Kochin','India','vijay@gmail.com','Male','159','9876123467','Vijay','pp','678432','Kochin','18KOH','15/04/2020','Kerala','68');
insert into motivator values('surya@gmail.com','31AUS Complex,Coimbatore','28','BelowBMI25','Coimbatore','India','surya@gmail.com','Male','162','8877654431','Surya','pp','678432','Coimbatore','45ROH','15/04/2020','TamilNadu','63');
insert into motivator values('ajith@gmail.com','25 YWE Building,Banglore','35','AboveBMI25','Banglore','India','ajith@gmail.com','Male','169','7867543219','Ajith','pp','874107','Banglore','07MSD','15/04/2020','Karnataka','71');


insert into REG_USERS values('sourav@gmail.com','Mysore,Karnataka','22','27.93','Mysore','India','Nothing','Nog-Vegetarian','Male','149','None','8764512980','Sourav','678234','Not Applicable','To gain my body weight and to lead helathy life','18KOH - Referred by : vijay@gmail.com ','11/03/2020 12:22:22','Karnataka','62');
insert into REG_USERS values('John@gmail.com','Tambaram,Chennai','23','37.93','Chennai','India','None','Vegetarian','Male','164','No','897651208','Sa','638702','Not Applicable','I want to reduce my weight','GY0349 - Referred By : Not Found','10/03/2020 10:16:39','Tamil Nadu','79');



insert into  MOTI_BATCH values('100','AboveBMI25','vijay@gmail.com');
insert into  MOTI_BATCH values('101','AboveBMI25','ajith@gmail.com');
insert into  MOTI_BATCH values('102','BelowBMI25','surya@gmail.com');









