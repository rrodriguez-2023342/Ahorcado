Drop Database if exists DB_Ahorcado; 
Create Database DB_Ahorcado;
Use DB_Ahorcado;

create table Usuarios(
	codigoUsuario int auto_increment,
    correoUsuario varchar(100) not null,
    contraseña varchar(100) not null,
    primary key PK_codigoUsuario(codigoUsuario)
);

create table Palabras(
	codigoPalabra int auto_increment,
    palabra varchar(100) not null,
    pista varchar(200) not null,
    primary key PK_codigoPalabra(codigoPalabra)
);

-- CRUD USUARIOS
Delimiter //
	Create procedure sp_AgregarUsuario( 
    in correoUsuario varchar(100), 
    in contraseña varchar(100))
		Begin
			Insert into Usuarios(correoUsuario, contraseña)
				Values(correoUsuario, contraseña);
        End //
Delimiter ;
call sp_AgregarUsuario('robertoandre0307@gmail.com', 'hola');
call sp_AgregarUsuario('1', '1');

Delimiter //
	Create procedure sp_ListarUsuario()
		Begin
			select codigoUsuario, correoUsuario, contraseña from Usuarios;
        End //
Delimiter ;
call sp_ListarUsuario();

Delimiter //
	Create procedure sp_EliminarUsuarios(
    in _codigoUsuario int)
		Begin
			set foreign_key_checks = 0;
				Delete from Usuarios
					where codigoUsuario = _codigoUsuario;
				Select row_count() as filasEliminadas;
			set foreign_key_checks = 1;
        End//
Delimiter ;
-- call sp_EliminarUsuarios(1);

Delimiter //
	Create procedure sp_BuscarUsuario(
    in _codigoUsuario int)
		Begin
			Select codigoUsuario, correoUsuario, contraseña from Usuarios
				where codigoUsuario = _codigoUsuario;
        End //
Delimiter ;
call sp_BuscarUsuario(1);

Delimiter //
	Create procedure sp_BuscarUsuarioLog(
    in _correoUsuario varchar(100), 
    in _contraseña varchar(100))
		Begin
			Select codigoUsuario, correoUsuario, contraseña from Usuarios
				where correoUsuario = _correoUsuario and contraseña = _contraseña;
        End //
Delimiter ;
call sp_BuscarUsuarioLog('1', '1');

Delimiter //
	Create procedure sp_EditarUsuario(
    in _codigoUsuario int, 
    in _correoUsuario varchar(100),
    in _contraseña varchar(100))
		Begin
			Update Usuarios
				set correoUsuario = _correoUsuario,
                    contraseña = _contraseña
					where codigoUsuario = _codigoUsuario;
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
			select codigoPalabra, palabra, pista from Palabras;
        End //
Delimiter ;
call sp_ListarPalabra();

Delimiter //
	Create procedure sp_EliminarPalabra(
    in _codigoPalabra int)
		Begin
			set foreign_key_checks = 0;
				Delete from Palabras
					where codigoPalabra = _codigoPalabra;
				Select row_count() as filasEliminadas;
			set foreign_key_checks = 1;
        End//
Delimiter ;
-- call sp_EliminarPalabra(1);

Delimiter //
	Create procedure sp_BuscarPalabra(
    in _codigoPalabra int)
		Begin
			Select codigoPalabra, palabra, pista from Palabras
				where codigoPalabra = _codigoPalabra;
        End //
Delimiter ;
call sp_BuscarPalabra(2);

Delimiter //
	Create procedure sp_EditarPalabra(
    in _codigoPalabra int,
    in _palabra varchar(100), 
    in _pista varchar(100))
		Begin
			Update Palabras
				set palabra = _palabra,
					pista = _pista
					where codigoPalabra = _codigoPalabra;
        End //
Delimiter ;
-- call sp_EditarPalabra(2, 'HOLA', 'HOLA');