-- CREAR LAS TABLAS
CREATE TABLE proveedores(
	codigo VARCHAR(6) PRIMARY KEY NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    direccion VARCHAR(40) NOT NULL
);

CREATE TABLE piezas(
	codigo VARCHAR(6) PRIMARY KEY NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    precio FLOAT NOT NULL,
    descripcion TEXT
);

CREATE TABLE proyectos(
	codigo VARCHAR(6) PRIMARY KEY NOT NULL,
    nombre VARCHAR(40) NOT NULL,
    ciudad VARCHAR(40)
);

CREATE TABLE gestion(
	codigo INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	codProveedor VARCHAR(6) NOT NULL,
    codPieza VARCHAR(6) NOT NULL,
    codProyecto VARCHAR(6) NOT NULL,
    cantidad FLOAT,
    -- CONSTRAINT gestion_pk PRIMARY KEY(codProveedor, codPieza, codProyecto),
    CONSTRAINT gestion_fk_proveedor FOREIGN KEY (codProveedor) REFERENCES proveedores(codigo) ON DELETE CASCADE,
    CONSTRAINT gestion_fk_pieza FOREIGN KEY (codPieza) REFERENCES piezas(codigo) ON DELETE CASCADE,
    CONSTRAINT gestion_fk_proyecto FOREIGN KEY (codProyecto) REFERENCES proyectos(codigo)  ON DELETE CASCADE
);


-- INSERTAR DATOS BASE
-- --------- PROOVEEDORES
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00001', 'Ignacio', 'Álvarez Jimenez', 'C/Orio, 11. 01010');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00002', 'Irune', 'Méndez Roldán', 'C/Panamá, 3. 01012');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00003', 'María', 'Delgado Pritchet', 'C/Hollywood, 5. 01018');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00004', 'Daniel', 'Tamargo Saiz', 'C/Ori, 18. 01010');

-- --------- PIEZAS
INSERT INTO PIEZAS(codigo, nombre, precio, descripcion) VALUES ('G00001', 'Abarcón 17mm', 0.8, 'Abarcón con 4 tuercas para tubos de 17mm de diámetro');
INSERT INTO PIEZAS(codigo, nombre, precio, descripcion) VALUES ('G00002', 'Abarcón 34mm', 1.4, 'Abarcón con 4 tuercas para tubos de 34mm de diámetro');

-- --------- PROYECTOS
INSERT INTO PROYECTOS(codigo, nombre, ciudad) VALUES('M00001', 'Primer Proyecto', 'Vitoria');
INSERT INTO PROYECTOS(codigo, nombre, ciudad) VALUES('M00002', 'Reparación Ventana', 'Vitoria');

-- --------- GESTIONES
INSERT INTO GESTION(codProveedor, codPieza, codProyecto, cantidad) VALUES('A00001', 'G00001', 'M00001', 1);
INSERT INTO GESTION(codProveedor, codPieza, codProyecto, cantidad) VALUES('A00001', 'G00001', 'M00002', 3);

COMMIT;

-- DATOS EXTRA
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00005', 'Andrés', 'Martínez Loco', 'Avda Gloria, 2, 01011');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00006', 'Juan', 'Ruedas Cardenas', 'Plaza de España 4, 01015');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00007', 'Óscar', 'Fuentes Martínez', 'C/Perú 1, 01012');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00008', 'Elizabeth', 'Larreina Del Mar', 'C/Getaria 12, 01010');

INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00009', 'Gloria', 'Jiménez Ruiz', 'Avda Huetos 16, 01010');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00010', 'Julia', 'Diaz Moreno', 'Paseo de Moreda 2, 01015');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00011', 'Enrique', 'Muñoz Álvarez', 'Bulevar Mariturri 36, 01015');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00012', 'Vicente', 'Romero Alonso', 'Portal de Castilla 60, 01007');

INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00013', 'Silvia', 'Gutiérrez Navarro', 'C/Puerto Azáceta 2, 01013');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00014', 'Luis', 'Torres Domínguez', 'C/Bolivia 19, 010009');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00015', 'Beatriz', 'Pérez Gómez', 'C/Puerto Arlabán 7, 01013');
INSERT INTO PROVEEDORES(codigo, nombre, apellidos, direccion) VALUES('A00016', 'Alba', 'García Rodríguez', 'C/Paraguay 22, 01012');

INSERT INTO PIEZAS(codigo, nombre, precio, descripcion) VALUES ('G00003', 'Tubo sujección 17mm', 0.4, 'Tubo que sujeta parte de una estructura (necesita un abarcón de su medida)');
INSERT INTO PIEZAS(codigo, nombre, precio, descripcion) VALUES ('G00004', 'Tubo sujección 34mm', 0.7, 'Tubo que sujeta parte de una estructura (necesita un abarcón de su medida)');

INSERT INTO PIEZAS(codigo, nombre, precio, descripcion) VALUES ('G00005', 'Visagra Ventana ASF7', 2.4, 'Visagra encargada de la apertura y cierre de ventanas tipo ASF7');
INSERT INTO PIEZAS(codigo, nombre, precio, descripcion) VALUES ('G00006', 'Visagra Puerta CLO4se', 3, 'Visagra encargada de la apertura y cierre de puertas CLO4se');

INSERT INTO PROYECTOS(codigo, nombre, ciudad) VALUES('M00003', 'Construcción Ventana', 'Vitoria');
INSERT INTO PROYECTOS(codigo, nombre, ciudad) VALUES('M00004', 'Remodelación Piso', 'Barcelona');

INSERT INTO PROYECTOS(codigo, nombre, ciudad) VALUES('M00005', 'Reconstrucción Piso', 'Barcelona');
INSERT INTO PROYECTOS(codigo, nombre, ciudad) VALUES('M00006', 'Reconstrucción Piso', 'Madrid');
INSERT INTO PROYECTOS(codigo, nombre, ciudad) VALUES('M00007', 'Reparación Puerta', 'Vitoria');

INSERT INTO GESTION(codProveedor, codPieza, codProyecto, cantidad) VALUES('A00002', 'G00001', 'M00002', 3);
INSERT INTO GESTION(codProveedor, codPieza, codProyecto, cantidad) VALUES('A00002', 'G00002', 'M00001', 12);
INSERT INTO GESTION(codProveedor, codPieza, codProyecto, cantidad) VALUES('A00002', 'G00002', 'M00002', 8);

INSERT INTO GESTION(codProveedor, codPieza, codProyecto, cantidad) VALUES('A00001', 'G00001', 'M00003', 3);
INSERT INTO GESTION(codProveedor, codPieza, codProyecto, cantidad) VALUES('A00002', 'G00003', 'M00003', 3);
INSERT INTO GESTION(codProveedor, codPieza, codProyecto, cantidad) VALUES('A00003', 'G00001', 'M00001', 10);

COMMIT;

-- DELETES IF NEEDED
DELETE FROM noexiste; -- <- para parar el ejecutar todo el fichero
DELETE FROM GESTION;
DELETE FROM PROYECTOS;
DELETE FROM PIEZAS;
DELETE FROM PROVEEDORES;

-- DROP TABALE IF NEEDED
DROP TABLE GESTION;
DROP TABLE PROYECTOS;
DROP TABLE PIEZAS;
DROP TABLE PROVEEDORES;
