package vacunate.dominio;

public class Vacuna {

	private String marca;
	private int cantidad;

	public Vacuna(String marca, int cantidad) {
		this.marca = marca;
		this.cantidad = cantidad;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
