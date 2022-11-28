# movimientosBancarios
Neoris - Prueba técnica

## Información General
API REST que expone los siguientes endpoints para manipular los movimientos en las cuentas de los clientes: 
* /cuentas
* /clientes
* /movimientos
* /reportes

## Tecnologias
Proyecto generado con:
* Sprig boot
* Java 8
* Maven
* MySQL
* H2 - para ejecutar las pruebas unitarias de los endpoints en memoria.
	
## Configuración
Este proyecto esta configurado para correr en docker con el siguiente comando:

```
$ docker-compose up

```