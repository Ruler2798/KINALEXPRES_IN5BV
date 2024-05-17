Drop database if exists DBKinalExpress;
set global time_zone = '-6:00';


create database DBKinalExpress;

use DBKinalExpress;

create table Clientes (
	clienteID int not null,
    nombreClientes varchar(50) not null,
    apellidoClientes varchar(50) not null,
    nitClientes varchar (10) not null,
	telefonoClientes varchar (15) not null,
    direccionClientes varchar(50),
    correoClientes varchar(45),
    primary key PK_clienteID (clienteID)
);

create table proveedores(
	codigoProveedor int,
    NITProveedor varchar(13),
    nombresProveedor varchar(60),
    apellidosProveedor varchar (60),
    direccionProveedor varchar (150),
    razonSocial varchar (60),
    contactoPrincipal varchar (100),
    paginaWeb varchar(50),
    telefonoProveedor varchar (13),
    emailProveedor varchar(50),
    primary key PK_codigoProveedor(codigoProveedor)
);

create table TipoProducto(
	codigoTipoProducto int ,
    descripcionProducto varchar (45),
    primary key PK_TipoProducto(codigoTipoProducto)
);

DESCRIBE TipoProducto;


create table Productos(
	codigoProducto varchar(15),
	descripcionProducto varchar(15),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	imagenProducto varchar(45),
	existencia int,
	codigoTipoProducto int,
	codigoProveedor int,
	primary key  PK_codigoProducto (codigoProducto),
	foreign key (codigoTipoProducto) references TipoProducto(codigoTipoProducto),
	foreign key (codigoProveedor) references Proveedores(codigoProveedor)
);

-- ---------------------------Procidimientos Almacenados-------------------
-- ---------------------------Clientes-------------------------------------
-- ---------------------------Agregar Clientas-----------------------------

Delimiter $$
	Create procedure sp_AgregarClientes(in clienteID int, in nombreClientes varchar(50), in apellidoClientes varchar(50), in nitClientes varchar(10),
    in telefonoClientes varchar (15), in direccionClientes varchar(50), in correoClientes varchar(45))
			Begin
				Insert into Clientes (clienteID, nombreClientes, apellidoClientes, nitClientes, telefonoClientes, direccionClientes, correoClientes ) values 
					(clienteID, nombreClientes, apellidoClientes, nitClientes, telefonoClientes, direccionClientes, correoClientes);
		End $$
Delimiter ;

call sp_AgregarClientes(001, 'Luis Pedro', 'Hernandez Garcia','114006350', '12345678', '13-54 zona 7 colonia Landivar', 'kinal@kinal.edu.gt');
call sp_AgregarClientes(002, 'Jose Carlos', 'Cortez Carrera','53308239',  '12345678', '13-54 zona 7 colonia Landivar', 'kinal@kinal.edu.gt');


-- -----------------------Listar clientes--------------------------------
Delimiter $$
    Create procedure sp_ListarClientes()
		Begin
        
			Select 
				C.clienteID, 
                C.nombreClientes, 
                C.apellidoClientes,
                C.nitClientes,
                C.telefonoClientes,
                C.direccionClientes,
				C.correoClientes
			From Clientes C;
            
		End$$
    
Delimiter ;	

call sp_ListarClientes();

-- ---------------------Buscar Clientes --------------------------------
Delimiter $$
	Create procedure sp_BuscarClientes(in cliId int)
		Begin
			Select C.clienteID, 
				C.nombreClientes, 
                C.apellidoClientes, 
                C.nitClientes,
                C.telefonoClientes,
                C.direccionClientes,
                C.correoClientes
            From Clientes C 
            where clienteID = cliId;
        End $$
Delimiter ;

call sp_BuscarClientes(1);

-- -----------------------Eliminar Clientes------------------------------
Delimiter $$
	Create procedure sp_EliminarClientes (in cliID int)
		Begin 
			Delete from Clientes
				where clienteID = cliID;
		End $$
Delimiter ;

call sp_EliminarClientes(303);
call sp_ListarClientes();

-- ----------------------------Editar Clientes------------------------------------
Delimiter $$
	Create procedure sp_EditarClientes(in cliID int, in nomClientes varchar(50), in apeClientes varchar(50), 
        in nitCl varchar (10), in teleClientes varchar(15), in direClientes varchar(50), in corrClientes varchar(45))
			Begin
				Update Clientes C
					set C.nombreClientes = nomClientes, 
                    C.apellidoClientes = apeClientes,
                    C.nitClientes = nitCl,
                    C.telefonoClientes = teleClientes, 
                    C.direccionClientes = direClientes,
                    C.correoClientes = corrClientes
                    where cliID = clienteID ;
			End $$
Delimiter ;

call sp_EditarClientes(2, 'Jose Carlos Efrain', 'Cortez Carrera', '53308239', '12345678','13-54 zona 7 colonia Landivar', 'kinal@edu.gt' );
call sp_ListarClientes();

-- -----------------------Proveedores  Procedimiento Almacenados ------------------------
-- CRUD PROVEEDORES
-- ---------------------------Agregar proveedores-----------------------------
delimiter $$

create procedure sp_agregarproveedor (
    in _codigoproveedor int,
    in _nitproveedor varchar(13),
    in _nombresproveedor varchar(60),
    in _apellidosproveedor varchar(60),
    in _direccionproveedor varchar(150),
    in _razonsocial varchar(60),
    in _contactoprincipal varchar(100),
    in _paginaweb varchar(50),
    in _telefonoproveedor varchar(13),
    in _emailproveedor varchar(50)
)
begin
    insert into Proveedores (codigoProveedor, NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb, telefonoProveedor, emailProveedor)
    values (_codigoproveedor, _nitproveedor, _nombresproveedor, _apellidosproveedor, _direccionproveedor, _razonsocial, _contactoprincipal, _paginaweb, _telefonoproveedor, _emailproveedor);
end $$

delimiter ;

call sp_agregarproveedor(1, '0614000000011', 'Gasolinera Express', 'S.A.', 'Av. Principal 123, Zona 1', 'Gasolinera Express S.A.', 'Juan Pérez', 'www.gasolineraexpress.com', '1234567890', 'info@gasolineraexpress.com');
call sp_agregarproveedor(2, '0614000000024', 'Distribuidora de Alimentos', 'Dialiment S.A.', 'Av. Comercial 456, Zona 2', 'Dialiment S.A.', 'María Gómez', 'www.dialiment.com', '2345678901', 'info@dialiment.com');
call sp_agregarproveedor(3, '0614000000037', 'Bebidas Refrescantes', 'Refrescos del Sur S.A.', 'Calle Refrescante 789, Zona 3', 'Refrescos del Sur S.A.', 'Pedro Martínez', 'www.refrescosdelsur.com', '3456789012', 'info@refrescosdelsur.com');
call sp_agregarproveedor(4, '0614000000040', 'Lubricantes y Aceites', 'Lubriaceites Ltda.', 'Carrera Lubricante 101, Zona 4', 'Lubriaceites Ltda.', 'Luis Rodríguez', 'www.lubriaceites.com', '4567890123', 'info@lubriaceites.com');
call sp_agregarproveedor(5, '0614000000053', 'Productos de Limpieza', 'Limpiafacil S.A.', 'Pasaje Limpio 202, Zona 5', 'Limpiafacil S.A.', 'Ana López', 'www.limpiafacil.com', '5678901234', 'info@limpiafacil.com');

delimiter $$
-- -----------------------Listar proveedores--------------------------------

create procedure sp_mostrarproveedor ()
begin
    select
        codigoproveedor,
        nitproveedor,
        nombresproveedor,
        apellidosproveedor,
        direccionproveedor,
        razonsocial,
        contactoprincipal,
        paginaweb,
        telefonoproveedor,
        emailproveedor
    from
        Proveedores;
end $$

delimiter ;

call sp_mostrarproveedor;
.-- ---------------------Buscar Proveedores --------------------------------

delimiter $$

create procedure sp_buscarproveedor (in _codigoproveedor int)
begin
    select
        codigoproveedor,
        nitproveedor,
        nombresproveedor,
        apellidosproveedor,
        direccionproveedor,
        razonsocial,
        contactoprincipal,
        paginaweb,
        telefonoproveedor,
        emailproveedor
    from
        Proveedores
    where
        codigoproveedor = _codigoproveedor;
end $$

delimiter ;

call sp_buscarproveedor(3);
-- -----------------------Eliminar Proveedor------------------------------
delimiter $$

create procedure sp_eliminarproveedor (in _codigoproveedor int)
begin
    delete from Proveedores
    where codigoproveedor = _codigoproveedor;
end $$

delimiter ;

call sp_eliminarproveedor(1);
-- ----------------------------Editar Proveedor------------------------------------
delimiter $$

create procedure sp_editarproveedor (
    in _codigoproveedor int,
    in _nitproveedor varchar(13),
    in _nombresproveedor varchar(60),
    in _apellidosproveedor varchar(60),
    in _direccionproveedor varchar(150),
    in _razonsocial varchar(60),
    in _contactoprincipal varchar(100),
    in _paginaweb varchar(50),
    in _telefonoproveedor varchar(13),
    in _emailproveedor varchar(50)
)
begin
    update Proveedores
    set
        NITProveedor = _nitproveedor,
        nombresProveedor = _nombresproveedor,
        apellidosProveedor = _apellidosproveedor,
        direccionProveedor = _direccionproveedor,
        razonSocial = _razonsocial,
        contactoPrincipal = _contactoprincipal,
        paginaWeb = _paginaweb,
        telefonoProveedor = _telefonoproveedor,
        emailProveedor = _emailproveedor
    where
        codigoProveedor = _codigoproveedor;
end $$

delimiter ;

 call sp_editarproveedor(1, '1234567890123', 'Juan', 'Perez', 'Calle 123', 'Razón Social', 'Contacto', 'www.proveedor.com', '1234567890', 'proveedor@correo.com');

-- -----------------------Tipo de Producto  Procedimiento Almacenados ------------------------
-- CRUD TIPO PRODUCTO
-- ---------------------------Agregar TipoDeProducto-----------------------------

delimiter $$

create procedure sp_agregarTipoProducto (
    in _codigoTipoProducto int,
    in _descripcionProducto varchar(45)
)
begin
    insert into TipoProducto (codigoTipoProducto, descripcionProducto)
    values (_codigoTipoProducto, _descripcionProducto);
end $$

delimiter ;

call sp_agregarTipoProducto(1, 'Combustibles');
call sp_agregarTipoProducto(2, 'Alimentos');
call sp_agregarTipoProducto(3, 'Bebidas');
call sp_agregarTipoProducto(4, 'Snacks');
call sp_agregarTipoProducto(5, 'Cuidado Personal');

-- -----------------------Listar TipodeProductos--------------------------------

delimiter $$

create procedure sp_mostrarTipoProducto ()
begin
    select
        codigoTipoProducto,
        descripcionProducto
    from
        TipoProducto;
end $$

delimiter ;

call sp_mostrarTipoProducto;

-- ---------------------Buscar TipoDeProducto --------------------------------

delimiter $$

create procedure sp_buscarTipoProducto (in _codigoTipoProducto int)
begin
    select
        codigoTipoProducto,
        descripcionProducto
    from
        TipoProducto
    where
        codigoTipoProducto = _codigoTipoProducto;
end $$

delimiter ;

call sp_buscarTipoProducto(2);
-- -----------------------Eliminar TipodeProducto------------------------------

delimiter $$

create procedure sp_eliminarTipoProducto (in _codigoTipoProducto int)
begin
    delete from TipoProducto
    where codigoTipoProducto = _codigoTipoProducto;
end $$

delimiter ;

call sp_eliminarTipoProducto(1);
-- ----------------------------Editar TipoDeProducto------------------------------------

delimiter $$

create procedure sp_editarTipoProducto (
    in _codigoTipoProducto int,
    in _descripcionProducto varchar(45)
)
begin
    update TipoProducto
    set descripcionProducto = _descripcionProducto
    where codigoTipoProducto = _codigoTipoProducto;
end $$

delimiter ;

 call sp_editarTipoProducto(1, 'Combustibles Premium');
 
 
 -- -------------------------- Productos  Procedimiento Almacenados -----------------------------
 -- CRUD PRODUCTOS
 -- ---------------------------Agregar Producto-----------------------------

DELIMITER $$

CREATE PROCEDURE sp_agregarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_descripcionProducto VARCHAR(15),
    IN p_precioUnitario DECIMAL(10,2),
    IN p_precioDocena DECIMAL(10,2),
    IN p_precioMayor DECIMAL(10,2),
    IN p_existencia INT,
    IN p_codigoTipoProducto INT,
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, existencia, codigoTipoProducto, codigoProveedor)
    VALUES(p_codigoProducto, p_descripcionProducto, p_precioUnitario, p_precioDocena, p_precioMayor, p_existencia, p_codigoTipoProducto, p_codigoProveedor);
END$$
DELIMITER ;

CALL sp_agregarProducto('P001', 'Arroz', 5.99, 68.99, 129.99, 100, 2, 2);
CALL sp_agregarProducto('P002', 'Frijoles', 3.49, 39.99, 74.99, 150, 2, 2);
CALL sp_agregarProducto('P003', 'Aceite', 8.99, 102.99, 194.99,  80, 3, 2);
CALL sp_agregarProducto('P004', 'Leche Entera', 2.99, 32.99, 62.99, 120, 3, 4);
CALL sp_agregarProducto('P005', 'Azúcar', 4.49, 51.99, 98.99, 90, 4, 5);

-- -----------------------Listar Productos--------------------------------

Delimiter $$
create procedure sp_mostrarProductos()
	begin
    select
		p.codigoProducto,
        p.descripcionProducto,
        p.precioUnitario,
        p.precioDocena,
        p.precioMayor,
        p.existencia,
        p.codigoTipoProducto,
        p.codigoProveedor
        from
        productos p;
	end$$
Delimiter ;

call sp_mostrarProductos();

-- ----------------------------Editar Producto------------------------------------

DELIMITER $$
CREATE PROCEDURE sp_actualizarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_nuevaDescripcionProducto VARCHAR(15),
    IN p_nuevoPrecioUnitario DECIMAL(10,2),
    IN p_nuevoPrecioDocena DECIMAL(10,2),
    IN p_nuevoPrecioMayor DECIMAL(10,2),
    IN p_nuevaExistencia INT,
    IN p_nuevoCodigoTipoProducto INT,
    IN p_nuevoCodigoProveedor INT
)
BEGIN
    UPDATE Productos
    SET descripcionProducto = p_nuevaDescripcionProducto,
        precioUnitario = p_nuevoPrecioUnitario,
        precioDocena = p_nuevoPrecioDocena,
        precioMayor = p_nuevoPrecioMayor,
        existencia = p_nuevaExistencia,
        codigoTipoProducto = p_nuevoCodigoTipoProducto,
        codigoProveedor = p_nuevoCodigoProveedor
    WHERE codigoProducto = p_codigoProducto;
END$$
DELIMITER ;

call sp_actualizarProducto('P001', 'Pollo', 8.99, 69.99, 130.99, 100, 2, 2);

-- -----------------------Eliminar Producto------------------------------

Delimiter $$
CREATE PROCEDURE sp_eliminarProducto(IN _codigoProducto VARCHAR(15))
BEGIN
    DELETE FROM Productos
    WHERE codigoProducto = _codigoProducto;
END $$

DELIMITER ;

call sp_eliminarProducto('P001');
-- -----------------------------------