CREATE DATABASE IF NOT EXISTS movimientos_bancarios;

USE movimientos_bancarios;

CREATE TABLE IF NOT EXISTS clientes (
    id INT NOT NULL AUTO_INCREMENT,
	identificacion VARCHAR(50),
    nombres VARCHAR(250),
    genero VARCHAR(20),
	edad TINYINT,
	direccion VARCHAR(100),
	telefono INT,
	contrasenia VARCHAR(32),
	estado BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tipos_cuentas (
    id INT NOT NULL AUTO_INCREMENT,
	tipo VARCHAR(50),
	descripcion VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cuentas (
    id INT NOT NULL AUTO_INCREMENT,
	numero_cuenta INT,
    tipo_cuenta_id INT,
    saldo INT,
	estado BOOLEAN,
	cliente_id INT,
    PRIMARY KEY (id),
	FOREIGN KEY (tipo_cuenta_id) REFERENCES tipos_cuentas(id),
	FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS tipos_movimientos (
    id INT NOT NULL AUTO_INCREMENT,
	tipo VARCHAR(50),
	descripcion VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS movimientos (
    id INT NOT NULL AUTO_INCREMENT,
	fecha DATE,
    tipo_movimiento_id INT,
    valor INT,
	saldo_inicial INT,
	saldo_disponible INT,
	estado BOOLEAN,
	cuenta_id INT,
    PRIMARY KEY (id),
	FOREIGN KEY (tipo_movimiento_id) REFERENCES tipos_movimientos(id),
	FOREIGN KEY (cuenta_id) REFERENCES cuentas(id)
);


INSERT INTO tipos_cuentas (id, tipo, descripcion) VALUES (1, 'Ahorros', 'Cuenta tipo ahorros');
INSERT INTO tipos_cuentas (id, tipo, descripcion) VALUES (2, 'Corriente', 'Cuenta tipo corriente');

INSERT INTO tipos_movimientos (id, tipo, descripcion) VALUES (1, 'Crédito', 'movimiento de deposito');
INSERT INTO tipos_movimientos (id, tipo, descripcion) VALUES (2, 'Débito', 'movimiento de retiro');

/* El campo contrasenia corresponde al MD5 del valor: 1234 */
INSERT INTO clientes (id, identificacion, nombres, genero, edad, direccion, telefono, contrasenia, estado) VALUES (1, '123456789', 'Jose Lema', 'Masculino', 39, 'Otavalo sn y principal', 98254785, '81dc9bdb52d04dc20036dbd8313ed055', 1);
/* El campo contrasenia corresponde al MD5 del valor: 5678 */
INSERT INTO clientes (id, identificacion, nombres, genero, edad, direccion, telefono, contrasenia, estado) VALUES (2, '987654321', 'Marianela Montalvo', 'Femenino', '45', 'Amazonas y NNUU', 97548965, '674f3c2c1a8a6f90461e8a66fb5550ba', 1);
/* El campo contrasenia corresponde al MD5 del valor: 1245 */
INSERT INTO clientes (id, identificacion, nombres, genero, edad, direccion, telefono, contrasenia, estado) VALUES (3, '567891234', 'Juan Osorio', 'Masculino', '29', '13 junio y Equinoccial', 98874587, '5eac43aceba42c8757b54003a58277b5', 1);

INSERT INTO cuentas (id, numero_cuenta, tipo_cuenta_id, saldo, estado, cliente_id) VALUES (1, 478758, 1, 1425, 1, 1);
INSERT INTO cuentas (id, numero_cuenta, tipo_cuenta_id, saldo, estado, cliente_id) VALUES (2, 225487, 2, 700, 1, 2);
INSERT INTO cuentas (id, numero_cuenta, tipo_cuenta_id, saldo, estado, cliente_id) VALUES (3, 495878, 1, 150, 1, 3);
INSERT INTO cuentas (id, numero_cuenta, tipo_cuenta_id, saldo, estado, cliente_id) VALUES (4, 496825, 1, 0, 1, 2);

INSERT INTO movimientos (id, fecha, tipo_movimiento_id, valor, saldo_inicial, saldo_disponible, estado, cuenta_id) VALUES (1, '2022-10-02', 2, -575, 2000, 1425, 1, 1);
INSERT INTO movimientos (id, fecha, tipo_movimiento_id, valor, saldo_inicial, saldo_disponible, estado, cuenta_id) VALUES (2, '2022-10-02', 1, 600, 100, 700, 1, 2);
INSERT INTO movimientos (id, fecha, tipo_movimiento_id, valor, saldo_inicial, saldo_disponible, estado, cuenta_id) VALUES (3, '2022-02-08', 1, 150, 0, 150, 1, 3);
INSERT INTO movimientos (id, fecha, tipo_movimiento_id, valor, saldo_inicial, saldo_disponible, estado, cuenta_id) VALUES (4, '2022-02-08', 2, -540, 540, 0, 1, 4);