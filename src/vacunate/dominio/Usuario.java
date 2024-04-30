package vacunate.dominio;

public class Usuario {

	private int dni;
	private String password;
	private Rol rol;
	private String nombre;
	private Turno turno;
	private Turno turno2;
	private String vacuna;
	private String vacuna2;

	public Usuario(int dni, String password, String nombre, Rol rol, Turno turno, Turno turno2, String dosis,
			String dosis2) {
		this.dni = dni;
		this.password = password;
		this.rol = rol;
		this.nombre = nombre;
		this.turno = turno;
		this.turno = turno2;
		this.vacuna = dosis;
		this.vacuna2 = dosis;
	}

	public Turno getTurno2() {
		return turno2;
	}

	public void setTurno2(Turno turno2) {
		this.turno2 = turno2;
	}

	public String getVacuna2() {
		return vacuna2;
	}

	public void setVacuna2(String vacuna2) {
		this.vacuna2 = vacuna2;
	}

	public String getVacuna() {
		return vacuna;
	}

	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}

	public String toString() {
		return "Usuario [dni=" + dni + ", password=" + password + ", rol=" + rol + ", nombre=" + nombre + "]";
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
