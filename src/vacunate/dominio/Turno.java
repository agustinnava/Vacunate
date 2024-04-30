package vacunate.dominio;

public class Turno {

	private String direccion;
	private String localidad;
	private String vacunatorio;
	private String fecha;
	private String hora;
	private Boolean estado = false;

	public Turno(String fecha, String hora, String vacunatorio, String localidad, String direccion, Boolean estado) {
		this.fecha = fecha;
		this.hora = hora;
		this.vacunatorio = vacunatorio;
		this.localidad = localidad;
		this.direccion = direccion;
		this.estado = estado;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String toString() {
		return "Turno [direccion=" + direccion + ", localidad=" + localidad + ", vacunatorio=" + vacunatorio
				+ ", fecha=" + fecha + ", hora=" + hora + "]";
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getVacunatorio() {
		return vacunatorio;
	}

	public void setVacunatorio(String vacunatorio) {
		this.vacunatorio = vacunatorio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
