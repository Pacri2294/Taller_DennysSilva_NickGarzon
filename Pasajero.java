public class Pasajero {
    private String cedula;
    private String nombre;
    private String ruta;
    private int cantidad;
    private double precio;

    public Pasajero(String cedula, String nombre, String ruta,
                    int cantidad, double precio) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.ruta = ruta;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Cédula: " + cedula
                + " | Nombre: " + nombre
                + " | Ruta: " + ruta
                + " | Boletos: " + cantidad
                + " | Precio por boleto: $" + precio;
    }
}
