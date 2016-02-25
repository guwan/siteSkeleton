create table user(
id serial NOT NULL PRIMARY KEY,
username VARCHAR(255),
name VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255),
gender TIMESTAMP(23),
birthDate VARCHAR(6),
jobTitle VARCHAR(255),
location VARCHAR(255),
bio VARCHAR(255),
avatarUrl VARCHAR(255),
integral INTEGER(10),
levelName VARCHAR(255),
latitude DOUBLE(17),
longitude DOUBLE(17),
videoEmbeds VARCHAR(255),
createdAt TIMESTAMP(23),
updateAt TIMESTAMP(23),
enabled BOOLEAN(1),
accountNonExpired BOOLEAN(1),
accountNonLocked BOOLEAN(1),
credentialsNonExpired BOOLEAN(1)
);

create table authority(
id serial NOT NULL PRIMARY KEY,
username VARCHAR(255),
authority VARCHAR(255)
);