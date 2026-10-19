
import java.util.ArrayDeque;
import java.util.Queue;

public class Bus {

    private static final int CAPACIDAD_MAXIMA = 20;

    private Queue<Pasajero> pasajeros;
    private int boletosVendidos;

    public Bus() {
        pasajeros = new ArrayDeque<Pasajero>();
        boletosVendidos = 0;
    }

    public boolean isEmpty() {
        return pasajeros.isEmpty();
    }

    public void encolar(Pasajero pasajero) throws Exception {
        int cantidad = pasajero.getCantidad();


        if (cantidad < 1 || cantidad > 5) {
            throw new Exception(
                    "Solo puede comprar entre 1 y 5 boletos por transacción."
            );
        }

        if (boletosVendidos + cantidad > CAPACIDAD_MAXIMA) {
            throw new Exception(
                    "No hay suficientes asientos."
                            + "\nDisponibles: " + getDisponibles()
            );
        }

        int boletosCedula = 0;

        for (Pasajero p : pasajeros) {
            if (p.getCedula().equals(pasajero.getCedula())) {
                boletosCedula += p.getCantidad();
            }
        }

        if (boletosCedula + cantidad > 5) {
            throw new Exception(
                    "La cédula no puede superar los 5 boletos."
            );
        }

        pasajeros.add(pasajero);
        boletosVendidos += cantidad;
    }

    public Pasajero siguiente() throws Exception {
        if (!isEmpty()) {
            return pasajeros.peek();
        }

        throw new Exception("No hay pasajeros esperando.");
    }

    public Pasajero desencolar() throws Exception {
        if (!isEmpty()) {
            return pasajeros.poll();
        }

        throw new Exception("No hay pasajeros para embarcar.");
    }

    public int getBoletosVendidos() {
        return boletosVendidos;
    }

    public int getDisponibles() {
        return CAPACIDAD_MAXIMA - boletosVendidos;
    }
}


