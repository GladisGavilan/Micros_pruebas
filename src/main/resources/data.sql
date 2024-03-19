CREATE TABLE appusers (
    ID INT AUTO_INCREMENT,
    USERNAME VARCHAR(255) PRIMARY KEY,
    PASSWORD VARCHAR(255),
    ROL VARCHAR(255)
);

INSERT INTO appusers (username, password, rol) VALUES ('ggladis', 'pandora20', 'ADMIN');
INSERT INTO appusers (username, password, rol) VALUES ('hbelgrano', 'macar201', 'ADMIN');
INSERT INTO appusers (username, password, rol) VALUES ('chalita', '21pono8', 'ADMIN');

CREATE TABLE heroes (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(255),
    FECHACREACION DATE
);
INSERT INTO heroes (nombre, fechaCreacion) VALUES ('Linterna Verde', '2000-10-25');
INSERT INTO heroes (nombre, fechaCreacion) VALUES ('Hombre Araña', '1945-01-12');
INSERT INTO heroes (nombre, fechaCreacion) VALUES ('Hulk', '1952-10-08');
INSERT INTO heroes (nombre, fechaCreacion) VALUES ('Batman', '1950-06-29');
INSERT INTO heroes (nombre, fechaCreacion) VALUES ('Ant', '1974-02-03');
