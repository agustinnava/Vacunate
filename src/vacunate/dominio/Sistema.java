package vacunate.dominio;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Sistema {

	private static Scanner teclado = new Scanner(System.in);
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");

	private Usuario[] usuarios;
	private Vacuna[] vacunas;
	private Turno[] turnos;

	public LocalDate fechaActual = LocalDate.now();
	public Date fecha = new Date();

	public Sistema() {
		this.usuarios = new Usuario[100];
		this.vacunas = new Vacuna[100];
		this.turnos = new Turno[100];
		Usuario admin = new Usuario(1, "admin123", "SUPERADMIN", Rol.Admin, null, null, null, null);
		Usuario usuario = new Usuario(2, "123", "pepe", Rol.Paciente, null, null, null, null);
		Usuario vacunador = new Usuario(3, "123", "pipo", Rol.Vacunador, null, null, null, null);
		Vacuna vacuna1 = new Vacuna("SPUTNIK", 10);
		Vacuna vacuna2 = new Vacuna("ASTRAZENECA", 0);
		this.usuarios[0] = admin;
		this.usuarios[1] = usuario;
		this.usuarios[2] = vacunador;
		this.vacunas[0] = vacuna1;
		this.vacunas[1] = vacuna2;

	}

	public String fechaTurno(String fecha) {
		String fechaCadena = format.format(fecha);
		return fechaCadena;
	}

	public Boolean validarDni(int dni) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null)
				if (usuarios[i].getDni() == dni)
					return true;
		}
		return false;
	}

	public Boolean agregarUsuario(Usuario nuevoUsuario) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] == null) {
				usuarios[i] = nuevoUsuario;
				return true;
			}
		}
		return false;
	}

	public Usuario login(int dniLogin, String passwordLogin) {
		for (int i = 0; i < usuarios.length; i++) {
			if (this.usuarios[i] != null)
				if (this.usuarios[i].getDni() == dniLogin && this.usuarios[i].getPassword().equals(passwordLogin))
					return this.usuarios[i];
		}
		return null;
	}

	public Boolean loginOk(Usuario usuarioLogeado) {
		for (int i = 0; i < usuarios.length; i++) {
			if (this.usuarios[i] != null)
				if (this.usuarios[i] == usuarioLogeado)
					return true;
		}
		return false;
	}

	public boolean agregarTurno(Turno nuevoTurno) {
		for (int i = 0; i < turnos.length; i++) {
			if (turnos[i] == null) {
				turnos[i] = nuevoTurno;
				return true;
			}
		}
		return false;
	}

	public void mostrarPacientes() {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null && usuarios[i].getRol().equals(Rol.Paciente))
				System.out.println(i + "." + usuarios[i].getDni());
		}
	}

	public void mostrarUsuarios() {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null)
				System.out.println(i + "." + usuarios[i].getDni());
		}
	}

	public Usuario buscarUsuarioPorDni(int dni) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null)
				if (usuarios[i].getDni() == dni) {
					return usuarios[i];
				}
		}
		return null;
	}

//	public Usuario buscarPacientePorDni(int dni) {
//		for (int i = 0; i < usuarios.length; i++) {
//			if(usuarios[i] != null)
//				if(usuarios[i].getDni() == dni) {
//					return usuarios[i];
//				}					
//		}
//		return null;
//	}

	public boolean asignarTurno(Turno nuevoTurno, int dni) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null)
				if (usuarios[i].getDni() == dni)
					if (usuarios[i].getTurno() == null) {
						usuarios[i].setTurno(nuevoTurno);
						return true;
					} else if (usuarios[i].getTurno2() == null) {
						usuarios[i].setTurno2(nuevoTurno);
						return true;
					}
		}
		return false;
	}

	public boolean cancelarTurno(int dni) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null && usuarios[i].getDni() == dni) {
				if (usuarios[i].getTurno() != null)
					if (usuarios[i].getTurno().getEstado() == false) {
						usuarios[i].setTurno(null);
						return true;
					} else if (usuarios[i].getTurno2() != null) {
						if (usuarios[i].getTurno2().getEstado() == false)
							usuarios[i].setTurno2(null);
						return true;
					}
			}
		}
		return false;
	}

	public boolean agregarVacuna(Vacuna vacuna) {
		for (int i = 0; i < vacunas.length; i++) {
			if (vacunas[i] != null)
				vacunas[i] = vacuna;
			return true;
		}
		return false;
	}

	public void mostrarVacunas() {
		for (int i = 0; i < vacunas.length; i++) {
			if (vacunas[i] != null)
				System.out.println(i + "." + vacunas[i].getMarca());
		}
	}

	public Vacuna buscarVacuna(String marca) {
		for (int i = 0; i < vacunas.length; i++) {
			if (vacunas[i] != null)
				if (vacunas[i].getMarca().equals(marca)) {
					return vacunas[i];
				}
		}
		return null;
	}

	public boolean agregarCantidad(String marca, int cantidadVacunas) {
		for (int i = 0; i < vacunas.length; i++) {
			if (vacunas[i] != null)
				if (vacunas[i].getMarca().equals(marca))
					vacunas[i].setCantidad(cantidadVacunas);
			return true;
		}
		return false;
	}

	public boolean mostrarUltimoTurno(int dni) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null && usuarios[i].getDni() == dni)
				if (usuarios[i].getTurno() != null && usuarios[i].getTurno().getEstado() == false) {
					System.out.println(usuarios[i].getTurno().toString());
					return true;
				} else if (usuarios[i].getTurno2() != null && usuarios[i].getTurno2().getEstado() == false) {
					System.out.println(usuarios[i].getTurno2().toString());
					return true;
				}
		}
		return false;
	}

	public void mostrarTurnoUsuario(int dni) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null && usuarios[i].getDni() == dni)
				if (usuarios[i].getTurno() != null) {
					if (usuarios[i].getTurno().getEstado() == false) {
						System.out.println(usuarios[i].getTurno().toString());
					}
					if (usuarios[i].getTurno2() != null)
						if (usuarios[i].getTurno().getEstado() == false) {
							System.out.println(usuarios[i].getTurno2().toString());
						}
				} else {
					System.out.println("No hay turnos asignados");
				}
		}
	}

	public Boolean reprogramarTurno(int dni, Turno nuevoTurno) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null && usuarios[i].getDni() == dni)
				if (usuarios[i].getTurno() != null && usuarios[i].getTurno().getEstado() == false) {
					usuarios[i].setTurno(nuevoTurno);
					return true;
				} else if (usuarios[i].getTurno2() != null && usuarios[i].getTurno2().getEstado() == false)
					usuarios[i].setTurno2(nuevoTurno);
			return true;
		}
		return false;
	}

	public int ConsultarStockVacunas(String marca) {
		int cantidadVacunas = 0;
		for (int i = 0; i < vacunas.length; i++) {
			if (vacunas[i] != null)
				if (vacunas[i].getMarca().equals(marca))
					cantidadVacunas = vacunas[i].getCantidad();
		}
		return cantidadVacunas;
	}

	public Boolean aplicarVacuna(int dni, String dosis) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null && usuarios[i].getDni() == dni)
				if (usuarios[i].getTurno() != null) {
					if (usuarios[i].getTurno().getEstado() == false) {
						for (int j = 0; j < vacunas.length; j++) {
							if (vacunas[j] != null && vacunas[j].getMarca().equals(dosis)
									&& vacunas[j].getCantidad() > 0) {
								vacunas[j].setCantidad(vacunas[j].getCantidad() - 1);
								usuarios[i].setVacuna(dosis);
								usuarios[i].getTurno().setEstado(true);
								return true;
							}
						}
					}
				} else if (usuarios[i].getTurno2() != null) {
					if (usuarios[i].getTurno2().getEstado() == false)
						for (int j = 0; j < vacunas.length; j++) {
							if (vacunas[j] != null && vacunas[j].getMarca().equals(dosis)
									&& vacunas[j].getCantidad() > 0) {
								vacunas[j].setCantidad(vacunas[j].getCantidad() - 1);
								usuarios[i].setVacuna2(dosis);
								usuarios[i].getTurno2().setEstado(true);
								return true;
							}
						}
				}
		}
		return false;
	}

	public void mostrarUsuario(Usuario usuario) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null)
				if (usuarios[i] == usuario)
					System.out.println(usuarios[i].toString());

		}
	}

	public void modificarDatos(Usuario usuarioLogeado) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null) {
				if (usuarios[i] == usuarioLogeado) {
					System.out.println("*CAMBIE DNI*");
					int dni = teclado.nextInt();
					usuarios[i].setDni(dni);
					System.out.println("*CAMBIE NOMBRE*");
					String nombre = teclado.next();
					usuarios[i].setNombre(nombre);
					System.out.println("*CAMBIE CONTRASEÑA*");
					String contraseña = teclado.next();
					usuarios[i].setPassword(contraseña);
					System.out.println("Se han guardado los cambios");
				}
			}
		}

	}

	public String consultarVacunasAplicadas(int dni) {
		String vacuna = "";
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null && usuarios[i].getDni() == dni)
				if (usuarios[i].getTurno() != null && usuarios[i].getTurno().getEstado() == true)
					vacuna = "1.Vacuna aplicada: " + usuarios[i].getVacuna();
		}
		return null;
	}

	public String consultarVacunasAplicadas2(int dni) {
		String vacuna2 = "";
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null && usuarios[i].getDni() == dni)
				if (usuarios[i].getTurno2() != null && usuarios[i].getTurno2().getEstado() == true)
					vacuna2 = "2.Vacuna aplicada: " + usuarios[i].getVacuna2();
		}
		return null;
	}

	public void consultarCertificadoVacunacion(int dni) {
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i] != null && usuarios[i].getDni() == dni)
				if (usuarios[i].getVacuna() != null && usuarios[i].getVacuna2() != null) {
					System.out.println("Tu certificado esta listo");
					System.out.println("**********************");
					System.out.println("CERTIFICADO VACUNACION");
					System.out.println("**********************");
					System.out.println(usuarios[i].getTurno().toString());
					System.out.println("vacuna: " + usuarios[i].getVacuna());
					System.out.println(usuarios[i].getTurno2().toString());
					System.out.println("vacuna: " + usuarios[i].getVacuna2());
				} else {
					System.out.println("Aun no te has aplicado todas las vacunas");
				}
		}

	}

	public boolean validarPassword(String password) {

		boolean validacionMayuscula = validacionMayuscula(password);
		boolean validacionMinuscula = validacionMinuscula(password);
		boolean validacionLongitud = validacionLongitud(password);
		boolean validacionNumero = validacionDosNumeros(password);
		boolean validacionCaracteres = validacionCaracteres(password);

		return validacionMayuscula && validacionMinuscula && validacionLongitud && validacionNumero
				&& validacionCaracteres;
	}

	public String generarPasswordValida() {
		String password = " ";
		while (validarPassword(password) == false) {
			password = generarContraseñaAleatoria();
		}
		return password;
	}

	private boolean validacionCaracteres(String password) {
		boolean validacionCaracteres = true;
		int caracteresValidos = 0;
		for (int j = 0; j < password.length(); j++) {
			char elemento = password.charAt(j);
			if (Character.isAlphabetic(elemento) || Character.isDigit(elemento)) {
				caracteresValidos++;
			}
		}
		if (caracteresValidos == password.length()) {
			validacionCaracteres = true;
		} else {
			validacionCaracteres = false;
		}
		return validacionCaracteres;
	}

	private boolean validacionDosNumeros(String password) {
		boolean validacionNumero = false;
		int contadorDenumeros = 0;
		for (int i = 0; i < password.length(); i++) {
			char letra = password.charAt(i);
			if (Character.isDigit(letra) == true) {
				contadorDenumeros++;
			}
		}
		if (contadorDenumeros >= 2)
			validacionNumero = true;
		return validacionNumero;
	}

	private boolean validacionLongitud(String password) {
		boolean validacionLongitud = false;
		if (password.length() >= 6)
			validacionLongitud = true;
		return validacionLongitud;
	}

	private boolean validacionMinuscula(String password) {
		boolean validacionMinuscula = false;
		for (int i = 0; i < password.length(); i++) {
			char letra = password.charAt(i);
			if (Character.isLowerCase(letra) == true) {
				validacionMinuscula = true;
				break;
			}
		}
		return validacionMinuscula;
	}

	private boolean validacionMayuscula(String password) {
		boolean validacionMayuscula = false;
		for (int i = 0; i < password.length(); i++) {
			char letra = password.charAt(i);
			if (Character.isUpperCase(letra) == true) {
				validacionMayuscula = true;
				break;
			}
		}
		return validacionMayuscula;
	}

	public String generarContraseñaAleatoria() {
		String nuevaPassword = "";
		for (int i = 0; i < 6; i++) {
			int entero = (int) (Math.random() * (122 - 48 + 1) + 48);
			nuevaPassword += (char) entero;
		}
		return nuevaPassword;
	}
}