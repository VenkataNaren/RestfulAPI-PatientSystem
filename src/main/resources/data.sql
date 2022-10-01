create table IF NOT EXISTS patient (id integer not null auto_increment, date_of_birth date, first_name varchar(255), last_name varchar(255), primary key (id));
insert into patient (date_of_birth,first_name,last_name) values('2022-12-31','lala','lulu'),
('1977-07-19','Venkata','Narendra'),
('2008-07-07','Raj','Gopal'),
('2002-11-07','Kurapati','Yasasvini'),
('1978-12-02','bollu','sushma'),
('1985-12-02','bollu','suneel'),
('1998-12-02','bollu','sudhiksha'),
('1980-12-02','Rambha','actor'),
('1981-12-02','Urvasi','actor'),
('1982-12-02','Menaka','actor'),
('1983-12-02','Leka','actor'),
('1983-12-02','Modi','actor'),
('1983-12-02','Vajpayi','actor');
