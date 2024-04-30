package vacunate.interfaz;

import java.util.Scanner;

import vacunate.dominio.Rol;
import vacunate.dominio.Sistema;
import vacunate.dominio.Turno;
import vacunate.dominio.Usuario;
import vacunate.dominio.Vacuna;

public class MainVacunate {

	private static final int REGISTRAR_USUARIO = 1;
	private static final int LOGIN = 2;
	private static final int SALIR = 3;
	private static final int REGISTRAR_VACUNADOR = 1;
	private static final int ADMINISTRAR_PACIENTES = 2;
	private static final int ADMINISTRAR_VACUNAS = 3;
	private static final int CERRAR_SESION = 4;
	private static final int ASIGNAR_TURNO = 1;
	private static final int CANCELAR_TURNO = 2;
	private static final int REPROGRAMAR_TURNO = 3;
	private static final int CONSULTAR_VACUNACION = 4;
	private static final int VOLVER = 5;
	private static final int AGREGAR_VACUNA_NUEVA = 1;
	private static final int AGREGAR_CANTIDADES_A_VACUNA = 2;
	private static final int VOLVER_ADMIN = 3;
	private static final int CONSULTAR_TURNOS = 1;
	private static final int APLICAR_VACUNA = 2;
	private static final int CONSULTAR_STOCK_VACUNA = 3;
	private static final int VER_PERFIL = 1;
	private static final int TURNOS = 2;
	private static final int VACUNAS_RECIBIDAS = 3;

	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		Sistema vacunate = new Sistema();

		int dni;
		String password;
		String nombre;
		String direccion;
		String localidad;
		String vacunatorio;
		String fecha;
		String hora;
		Rol rol;
		int opcionMenu;

		do {
			menuGeneral();
			opcionMenu = teclado.nextInt();
			switch (opcionMenu) {
			case REGISTRAR_USUARIO:
				dni = ingreseDni();
				boolean validarDni = vacunate.validarDni(dni);
				if (validarDni == false) {
					nombre = ingreseNombre();
					rol = Rol.Paciente;
					for (int i = 0; i <= 2; i++) {
						System.out.println("*DEBE SER DE 6 CARACTERES Y TENER: 1 MAYUSCULA, 1 MINUSCULA, 2 NUMEROS*");
						password = ingresePassword();
						boolean contraseñaOk = vacunate.validarPassword(password);
						if (contraseñaOk == true) {
							Usuario nuevoUsuario = new Usuario(dni, password, nombre, rol, null, null, null, null);
							Boolean registroOk = vacunate.agregarUsuario(nuevoUsuario);
							if (registroOk == true) {
								System.out
										.println("Se ha registrado como: " + nuevoUsuario.getRol() + " correctamente");
								break;
							} else
								System.out.println("No se ha podido registrar correctamente ");
							break;
						} else {
							System.out.println("La password no es segura, intente de nuevo");
						}

						if (i == 2) {
							System.out.println("*SE LE HA GENERADO UNA CONTRASEÑA*");
							password = vacunate.generarPasswordValida();
							System.out.println("Contraseña: " + password);
							Usuario nuevoUsuario = new Usuario(dni, password, nombre, rol, null, null, null, null);
							vacunate.agregarUsuario(nuevoUsuario);
							System.out.println("*REGISTRO EXITOSO*");

						}
					}
				} else {
					System.out.println("El usuario: " + dni + " ya existe");
				}
				break;
			case LOGIN:
				Usuario usuarioLogeado;
				do {
					dni = ingreseDni();
					password = ingresePassword();
					usuarioLogeado = vacunate.login(dni, password);
					validacionDatos(usuarioLogeado);
				} while (vacunate.loginOk(usuarioLogeado) == false);

				switch (usuarioLogeado.getRol()) {
				case Admin:
					int opcionAdmin;
					do {
						menuAdministrador();
						opcionAdmin = teclado.nextInt();
						switch (opcionAdmin) {
						case REGISTRAR_VACUNADOR:
							dni = ingreseDni();
							password = ingresePassword();
							nombre = ingreseNombre();
							rol = Rol.Vacunador;
							Usuario nuevoVacunador = new Usuario(dni, password, nombre, rol, null, null, null, null);
							Boolean registroOK = vacunate.agregarUsuario(nuevoVacunador);
							if (registroOK == true) {
								System.out.println("El " + nuevoVacunador.getRol() + " se ha registrado correctamente");
							} else {
								System.out.println("No se ha podido registrar correctamente ");
							}
							break;
						case ADMINISTRAR_PACIENTES:
							do {
								manuAdministrarPacientes();
								opcionAdmin = teclado.nextInt();
								switch (opcionAdmin) {
								case ASIGNAR_TURNO:
									System.out.println("*LISTA DE PACIENTES*");
									vacunate.mostrarPacientes();
									dni = usuarioQueRecibeTurno();
									fecha = ingreseFecha();
									hora = ingreseHora();
									vacunatorio = ingreseVacunatorio();
									direccion = ingreseDireccion();
									localidad = ingreseLocalidad();
									Turno nuevoTurno = new Turno(fecha, hora, vacunatorio, localidad, direccion, false);
									Boolean turnoOk = vacunate.asignarTurno(nuevoTurno, dni);
									if (turnoOk == true) {
										System.out.println("El turno a: " + dni + " fue asignado correctamente");
									} else {
										System.out.println(
												"No se ha podido asignar el turno y/o el usuario ya tiene turnos asignados");
									}
									break;
								case CANCELAR_TURNO:
									System.out.println("*LISTA DE PACIENTES*");
									vacunate.mostrarPacientes();
									dni = usuarioCancelarTurno();
									System.out.println("*SE CANCELARA EL ULTIMO TURNO ASIGNADO*");
									boolean turnosOk = vacunate.mostrarUltimoTurno(dni);
									if (turnosOk == true) {
										Boolean cancelarOk = vacunate.cancelarTurno(dni);
										if (cancelarOk == true) {
											System.out.println("El turno del usuario: " + dni
													+ " ha sido cancelado correctamente");
										} else {
											System.out.println("El usuario no tiene turnos pendientes para cancelar");
										}
									} else {
										System.out.println("El usuario: " + dni + " no tiene turnos asignados");
									}
									break;
								case REPROGRAMAR_TURNO:
									System.out.println("*LISTA DE PACIENTES*");
									vacunate.mostrarPacientes();
									System.out.println("*Se reprogramara el ultimo turno asignado*");
									System.out.println("*INTRODUZCA EL DNI DEL USUARIO A REPROGRAMAR*");
									dni = teclado.nextInt();
									boolean turnoValido = vacunate.mostrarUltimoTurno(dni);
									if (turnoValido == true) {
										System.out.println("INTRODUZCA EL NUEVO TURNO");
										fecha = ingreseFecha();
										hora = ingreseHora();
										vacunatorio = ingreseVacunatorio();
										direccion = ingreseDireccion();
										localidad = ingreseLocalidad();
										Turno reprogramado = new Turno(fecha, hora, vacunatorio, localidad, direccion,
												false);
										Boolean reprogramarOk = vacunate.reprogramarTurno(dni, reprogramado);
										if (reprogramarOk == true) {
											System.out.println("El " + reprogramado.toString() + " a: " + dni
													+ " fue reprogramado correctamente");
										} else {
											System.out.println("No se pudo reprogramar el turno");
										}
									} else {
										System.out.println("El usuario: " + dni + " no tiene turnos para reprogramar");
									}
									break;
								case CONSULTAR_VACUNACION:
									System.out.println("*LISTA DE PACIENTES");
									vacunate.mostrarPacientes();
									System.out.println("*INGRESE DNI DEL PACIENTE");
									dni = teclado.nextInt();
									if (vacunate.consultarVacunasAplicadas(dni) != null) {
										vacunate.consultarVacunasAplicadas(dni);
									}
									if (vacunate.consultarVacunasAplicadas2(dni) != null) {
										vacunate.consultarVacunasAplicadas2(dni);
									} else {
										System.out.println("El usuario no tiene vacunas aplicadas");
									}
									break;
								case VOLVER:
									System.out.println("Volviendo al menu...");
									break;
								default:
									System.out.println("Opcion incorrecta");
									break;
								}
							} while (opcionAdmin != VOLVER);
							break;
						case ADMINISTRAR_VACUNAS:
							do {
								menuAdministrarVacunas();
								opcionAdmin = teclado.nextInt();
								switch (opcionAdmin) {
								case AGREGAR_VACUNA_NUEVA:
									System.out.println("*INGRESE LA VACUNA*");
									String marca = teclado.next();
									Vacuna vacuna = new Vacuna(marca, 0);
									Boolean vacunaOk = vacunate.agregarVacuna(vacuna);
									if (vacunaOk) {
										System.out.println(
												"La vacuna: " + vacuna.getMarca() + " ha sido agregada exitosamente");
									} else {
										System.out.println("La vacuna no se ha podido agregar");
									}
									break;
								case AGREGAR_CANTIDADES_A_VACUNA:
									System.out.println("*ELIJA LA VACUNA POR NOMBRE*");
									vacunate.mostrarVacunas();
									marca = teclado.next();
									System.out.println("*AGREGUE LA CANTIDAD DE VACUNAS");
									int cantidadVacunas = teclado.nextInt();
									Boolean cantidadOk = vacunate.agregarCantidad(marca, cantidadVacunas);
									if (cantidadOk == true) {
										System.out.println(
												"La cantidad: " + cantidadVacunas + " fue agregada a: " + marca);
									} else {
										System.out.println(
												"La marca no existe y/o no se ha podido agregar la cantidad deseada");
									}
									break;
								case VOLVER_ADMIN:
									System.out.println("Volviendo al menu...");
									break;
								default:
									System.out.println("Opcion Incorrecta");
									break;
								}
							} while (opcionAdmin != VOLVER_ADMIN);
							break;
						case CERRAR_SESION:
							System.out.println("Cerrando Sesion, Adios!");
							break;

						default:
							System.out.println("Opcion Incorrecta");
							break;
						}
					} while (opcionAdmin != CERRAR_SESION);
					break;

				case Paciente:
					int opcionPaciente;
					do {
						// como conozco el paciente logueado no necesito buscarlo en un for
						// puedo modificar y acceder a sus atributos y/o metodos directamente
						menuPaciente();
						opcionPaciente = teclado.nextInt();
						switch (opcionPaciente) {
						case VER_PERFIL:
							do {
								menuPerfil();
								opcionPaciente = teclado.nextInt();
								switch (opcionPaciente) {
								case 1:
									vacunate.mostrarUsuario(usuarioLogeado);
									break;
								case 2:
									System.out.println("*MODIFICAR DATOS*");
									System.out.println("|1|.Cambiar Dni");
									System.out.println("|2|.Cambiar nombre");
									System.out.println("|3|.Cambiar password");
									System.out.println("|4|.Volver");
									int opcionPerfil = teclado.nextInt();
									switch (opcionPerfil) {
									case 1:
										System.out.println("Ingrese el nuevo DNI");
										dni = teclado.nextInt();
										usuarioLogeado.setDni(dni);
										System.out.println("Se han guardado los cambios");
										break;
									case 2:
										System.out.println("Ingrese el nuevo NOMBRE");
										nombre = teclado.next();
										usuarioLogeado.setNombre(nombre);
										System.out.println("Se han guardado los cambios");
										break;
									case 3:
										boolean passwordOk;
										do {
											System.out.println("Ingrese la nueva PASSWORD");
											System.out.println(
													"*DEBE SER DE 6 CARACTERES Y TENER: 1 MAYUSCULA, 1 MINUSCULA, 2 NUMEROS*");
											password = teclado.next();
											usuarioLogeado.setPassword(password);
											passwordOk = vacunate.validarPassword(password);
											if (passwordOk == true) {
												System.out.println("Los cambios se han guardado correctamente");
											} else {
												System.out.println("La contraseña no es valida");
											}
										} while (passwordOk != true);
										break;
									default:
										System.out.println("Opcion incorrecta");
										break;
									}
									break;
								case 3:
									System.out.println("Volviendo...");
									break;
								default:
									System.out.println("Opcion Incorrecta");
									break;
								}
							} while (opcionPaciente != 3);
							break;
						case TURNOS:
							do {
								menuTurnos();
								opcionPaciente = teclado.nextInt();
								switch (opcionPaciente) {
								case 1:
									vacunate.mostrarTurnoUsuario(usuarioLogeado.getDni());
									break;
								case 2:
									System.out.println("*SE CANCELARA EL ULTIMO TURNO ASIGNADO*");
									System.out.println("*DESEA CONTINUAR? SI(S) O NO(N)");
									char opcionsino = teclado.next().toUpperCase().charAt(0);
									if (opcionsino == 'S') {
										if (vacunate.cancelarTurno(usuarioLogeado.getDni()) == true) {
											System.out.println("El turno ha sido cancelado");
										} else {
											System.out.println("No existen turnos para cancelar");
										}
									} else {
										System.out.println("Volviendo atras");
									}
									break;
								case 3:
									break;
								default:
									System.out.println("Opcion Incorrecta");
									break;
								}
							} while (opcionPaciente != 3);
							break;
						case VACUNAS_RECIBIDAS:
							do {
								menuVacunas();
								opcionPaciente = teclado.nextInt();
								switch (opcionPaciente) {
								case 1:
									System.out.println("*VACUNAS APLICADAS*");
									if (vacunate.consultarVacunasAplicadas(usuarioLogeado.getDni()) != null) {
										vacunate.consultarVacunasAplicadas(usuarioLogeado.getDni());
									}
									if (vacunate.consultarVacunasAplicadas2(usuarioLogeado.getDni()) != null) {
										vacunate.consultarVacunasAplicadas2(usuarioLogeado.getDni());
									} else {
										System.out.println("No hay vacunas aplicadas");
									}
									break;
								case 2:
									vacunate.consultarCertificadoVacunacion(usuarioLogeado.getDni());
									break;
								case 3:
									System.out.println("Volviendo...");
									break;
								default:
									System.out.println("Opcion incorrecta");
									break;
								}
							} while (opcionPaciente != 3);
							break;
						case CERRAR_SESION:
							System.out.println("Cerrando sesion...");
							break;
						default:
							System.out.println("Opcion incorrecta");
							break;
						}
					} while (opcionPaciente != CERRAR_SESION);
					break;

				case Vacunador:
					int opcionVacunador;
					do {
						menuVacunador();
						opcionVacunador = teclado.nextInt();
						switch (opcionVacunador) {
						case CONSULTAR_TURNOS:
							System.out.println("*LISTA DE PACIENTES*");
							vacunate.mostrarPacientes();
							System.out.println("*INGRESE EL DNI DEL PACIENTE");
							dni = teclado.nextInt();
							vacunate.mostrarTurnoUsuario(dni);
							break;
						case APLICAR_VACUNA:
							System.out.println("*LISTA DE PACIENTES*");
							vacunate.mostrarPacientes();
							System.out.println("*INTRODUZCA EL DNI AL QUE SE LE APLICA LA VACUNA*");
							dni = teclado.nextInt();
							System.out.println("*SELECCIONE LA VACUNA*");
							vacunate.mostrarVacunas();
							String marca = teclado.next().toUpperCase();
							Boolean aplicarOkBoolean = vacunate.aplicarVacuna(dni, marca);
							if (aplicarOkBoolean == true) {
								System.out.println("La vacuna: " + marca + " se ha aplicado al paciente: " + dni
										+ " correctamente!");
							} else {
								System.out.println("No hay stock y/o no tiene turno para aplicar la vacuna");
							}
							break;
						case CONSULTAR_STOCK_VACUNA:
							System.out.println("*CONSULTAR STOCK DE:*");
							vacunate.mostrarVacunas();
							marca = teclado.next().toUpperCase();
							if (vacunate.ConsultarStockVacunas(marca) > 0) {
								System.out.println("La vacuna: " + marca + " dispone de: "
										+ vacunate.ConsultarStockVacunas(marca) + " unidades");
							} else {
								System.out.println("La vacuna: " + marca + " no cuenta con unidades disponibles");
							}
							break;
						case CERRAR_SESION:
							System.out.println("Cerrando sesion, Adios!");
							break;
						default:
							System.out.println("Opcion Incorrecta");
							break;
						}
					} while (opcionVacunador != CERRAR_SESION);
					break;

				default:
					System.out.println("Opcion incorrecta");
					break;
				}
				break;
			case SALIR:
				System.out.println("Usted a salido de la aplicacion");
				break;
			default:
				System.out.println("Opcion Incorrecta");
				break;
			}

		} while (opcionMenu != SALIR);

	}

	private static void menuPaciente() {
		System.out.println("*************");
		System.out.println("MENU PACIENTE");
		System.out.println("*************");
		System.out.println("|1|.Ver perfil");
		System.out.println("|2|.Turnos");
		System.out.println("|3|.Vacunas recibidas");
		System.out.println("|4|.Cerrar sesion");
	}

	private static void menuVacunas() {
		System.out.println("*******************");
		System.out.println("*VACUNAS RECIBIDAS*");
		System.out.println("*******************");
		System.out.println("|1|.Consultar vacunas aplicadas");
		System.out.println("|2|.Consultar certificado de vacunacion");
		System.out.println("|3|.Volver");
	}

	private static void menuTurnos() {
		System.out.println("******");
		System.out.println("TURNOS");
		System.out.println("******");
		System.out.println("|1|.Consultar turnos");
		System.out.println("|2|.Cancelar turnos");
		System.out.println("|3|.Volver");
	}

	private static void menuPerfil() {
		System.out.println("******");
		System.out.println("PERFIL");
		System.out.println("******");
		System.out.println("|1|.Ver mis datos");
		System.out.println("|2|.Modificar Datos");
		System.out.println("|3|.volver");
	}

	private static void menuVacunador() {
		System.out.println("**************");
		System.out.println("MENU VACUNADOR");
		System.out.println("**************");
		System.out.println("|1|.Consultar turnos");
		System.out.println("|2|.Aplicar vacuna a paciente");
		System.out.println("!3|.Consultar Stock de vacunas");
		System.out.println("|4|.Cerrar sesion");
	}

	private static String ingreseHora() {
		String hora;
		System.out.println("*INGRESE LA HORA*");
		return hora = teclado.next();
	}

	private static void menuAdministrarVacunas() {
		System.out.println("*******************");
		System.out.println("ADMINISTRAR VACUNAS");
		System.out.println("*******************");
		System.out.println("|1|.Agregar vacuna nueva");
		System.out.println("|2|.Agregar cantidades a una vacuna");
		System.out.println("|3|.Volver ");
	}

	private static int usuarioCancelarTurno() {
		int dni;
		System.out.println("Usuario(DNI) al que desea cancelar turno");
		dni = teclado.nextInt();
		return dni;
	}

	private static int usuarioQueRecibeTurno() {
		int dni;
		System.out.println("*USUARIO(DNI) AL QUE SE LE ASIGNA EL TURNO");
		dni = ingreseDni();
		return dni;
	}

	private static void listaUsuarios(Sistema vacunate) {
		System.out.println("*LISTA DE USUARIOS*");
		vacunate.mostrarUsuarios();
	}

	private static String ingreseDireccion() {
		String direccion;
		System.out.println("*INGRESE LA DIRECCION*");
		direccion = teclado.next();
		return direccion;
	}

	private static String ingreseLocalidad() {
		String localidad;
		System.out.println("*INGRESE LA LOCALIDAD*");
		localidad = teclado.next();
		return localidad;
	}

	private static String ingreseVacunatorio() {
		String vacunatorio;
		System.out.println("*INGRESE EL VACUNATORIO*");
		vacunatorio = teclado.next();
		return vacunatorio;
	}

	private static String ingreseFecha() {
		String fecha;
		System.out.println("*INGRESE LA FECHA DEL TURNO*");
		fecha = teclado.next();
		return fecha;
	}

	private static void manuAdministrarPacientes() {
		System.out.println("*********************");
		System.out.println("ADMINISTRAR PACIENTES");
		System.out.println("*********************");
		System.out.println("|1|.Asignar un turno de usuario");
		System.out.println("|2|.Cancelar turno de usuario");
		System.out.println("|3|.Reprogramar turno de usuario");
		System.out.println("|4|.Consultar vacunacion de un paciente");
		System.out.println("|5|.Volver a menu Admin");
	}

	private static void validacionDatos(Usuario usuarioLogeado) {
		if (usuarioLogeado != null)
			System.out.println("Se logeo correctamente " + usuarioLogeado.getRol());
		else
			System.out.println("Usuario y/o Password incorrecto!");

	}

	private static void menuGeneral() {
		System.out.println("************************");
		System.out.println("* BIENVENIDO A VACUNAR *");
		System.out.println("************************");
		System.out.println("|1|.Registrarse");
		System.out.println("|2|.Iniciar Sesion");
		System.out.println("|3|.Salir");
	}

	private static String ingreseNombre() {
		String nombre;
		System.out.println("*INGRESE NOMBRE*");
		nombre = teclado.next();
		return nombre;
	}

	private static String ingresePassword() {
		String password;
		System.out.println("*INGRESE PASSWORD*");
		password = teclado.next();
		return password;
	}

	private static int ingreseDni() {
		System.out.println("*INGRESE DNI*");
		int dni = teclado.nextInt();
		return dni;
	}

	private static void menuAdministrador() {
		System.out.println("******************");
		System.out.println("MENU ADMINISTRADOR");
		System.out.println("******************");
		System.out.println("|1|.Registrar Vacunador");
		System.out.println("|2|.Administrar Pacientes");
		System.out.println("|3|.Administrar Vacunas");
		System.out.println("|4|.Cerrar Sesion");
	}

}
