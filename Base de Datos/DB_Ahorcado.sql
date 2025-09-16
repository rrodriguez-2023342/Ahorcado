Drop Database if exists DB_Ahorcado; 
Create Database DB_Ahorcado;
Use DB_Ahorcado;

create table Usuarios(
	codigo_usuario int auto_increment,
    correo_usuario varchar(100) not null,
    contraseña varchar(100) not null,
    primary key PK_codigo_Usuario(codigo_Usuario)
);

create table Palabras(
	codigo_Palabra int auto_increment,
    palabra varchar(100) not null,
    pista varchar(200) not null,
    primary key PK_codigo_Palabra(codigo_Palabra)
);

-- CRUD USUARIOS
Delimiter //
	Create procedure sp_AgregarUsuario( 
    in correo_usuario varchar(100), 
    in contraseña varchar(100))
		Begin
			Insert into Usuarios(correo_usuario, contraseña)
				Values(correo_usuario, contraseña);
        End //
Delimiter ;
call sp_AgregarUsuario('robertoandre0307@gmail.com', 'hola');
call sp_AgregarUsuario('1', '1');

Delimiter //
	Create procedure sp_ListarUsuario()
		Begin
			select codigo_usuario, correo_usuario, contraseña from Usuarios;
        End //
Delimiter ;
call sp_ListarUsuario();

Delimiter //
	Create procedure sp_EliminarUsuarios(
    in _codigo_usuario int)
		Begin
			set foreign_key_checks = 0;
				Delete from Usuarios
					where codigo_usuario = _codigo_usuario;
				Select row_count() as filasEliminadas;
			set foreign_key_checks = 1;
        End//
Delimiter ;
-- call sp_EliminarUsuarios(1);

Delimiter //
	Create procedure sp_BuscarUsuario(
    in _codigo_usuario int)
		Begin
			Select codigo_usuario, correo_usuario, contraseña from Usuarios
				where codigo_usuario = _codigo_usuario;
        End //
Delimiter ;
call sp_BuscarUsuario(1);

Delimiter //
	Create procedure sp_BuscarUsuarioLog(
    in _correo_usuario varchar(100), 
    in _contraseña varchar(100))
		Begin
			Select codigo_usuario, correo_usuario, contraseña from Usuarios
				where correo_usuario = _correo_usuario and contraseña = _contraseña;
        End //
Delimiter ;
call sp_BuscarUsuarioLog('1', '1');

Delimiter //
	Create procedure sp_EditarUsuario(
    in _codigo_usuario int, 
    in _correo_usuario varchar(100),
    in _contraseña varchar(100))
		Begin
			Update Usuarios
				set correo_usuario = _correo_usuario,
                    contraseña = _contraseña
					where codigo_usuario = _codigo_usuario;
        End //
Delimiter ;
-- call sp_EditarUsuario(1, 'JulioHola', 'julio@gmail.com');

-- CRUD PALABRAS
Delimiter //
	Create procedure sp_AgregarPalabra(
    in palabra varchar(100), 
    in pista varchar(200))
		Begin
			Insert into Palabras(palabra, pista)
				Values(palabra, pista);
        End //
Delimiter ;
call sp_AgregarPalabra('PROGRAMAR', 'Acción de crear instrucciones para una computadora.');
call sp_AgregarPalabra('ESTUDIAR', 'Ejercitar el entendimiento para alcanzar o comprender algo.');
call sp_AgregarPalabra('AHORCADO', 'Nombre del juego que estás jugando ahora.');
call sp_AgregarPalabra('COMPUTADORA', 'Máquina que procesa información.');
call sp_AgregarPalabra('PROFESORES', 'Persona que ejerce o enseña una ciencia o arte.');

Delimiter //
	Create procedure sp_ListarPalabra()
		Begin
			select codigo_palabra, palabra, pista from Palabras;
        End //
Delimiter ;
call sp_ListarPalabra();

Delimiter //
	Create procedure sp_EliminarPalabra(
    in _codigo_palabra int)
		Begin
			set foreign_key_checks = 0;
				Delete from Palabras
					where codigo_palabra = _codigo_palabra;
				Select row_count() as filasEliminadas;
			set foreign_key_checks = 1;
        End//
Delimiter ;
-- call sp_EliminarPalabra(1);

Delimiter //
	Create procedure sp_BuscarPalabra(
    in _codigo_palabra int)
		Begin
			Select codigo_palabra, palabra, pista from Palabras
				where codigo_Palabra = _codigo_palabra;
        End //
Delimiter ;
call sp_BuscarPalabra(2);

Delimiter //
	Create procedure sp_EditarPalabra(
    in _codigo_palabra int,
    in _palabra varchar(100), 
    in _pista varchar(100))
		Begin
			Update Palabras
				set palabra = _palabra,
					pista = _pista
					where codigo_palabra = _codigo_palabra;
        End //
Delimiter ;
-- call sp_EditarPalabra(2, 'HOLA', 'HOLA');