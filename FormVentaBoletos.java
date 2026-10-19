import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.Queue;

public class FormVentaBoletos extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<String> comboRuta;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JTextArea areaCompras;
    private JButton btnComprar;
    private JLabel lblVendidosGuayaquil;
    private JLabel lblDisponiblesGuayaquil;
    private JLabel lblVendidosCuenca;
    private JLabel lblDisponiblesCuenca;
    private JLabel lblVendidosLoja;
    private JLabel lblDisponiblesLoja;
    private JLabel lblRecaudacion;

    private Bus busGuayaquil;
    private Bus busCuenca;
    private Bus busLoja;
    private Queue<Pasajero> compras;
    private double totalRecaudado;

    public FormVentaBoletos() {
        setTitle("Venta de boletos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(760, 560);
        setLocationRelativeTo(null);

        busGuayaquil = new Bus();
        busCuenca = new Bus();
        busLoja = new Bus();
        compras = new ArrayDeque<Pasajero>();
        totalRecaudado = 0;
        comboRuta.addItem("QUITO - GUAYAQUIL");
        comboRuta.addItem("QUITO - CUENCA");
        comboRuta.addItem("QUITO - LOJA");
        btnComprar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comprar();
            }
        });
        actualizarLabels();
    }

    private void comprar() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida de boletos.");
            return;
        }
        if (cedula.length() == 0 || nombre.length() == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese la cédula y el nombre del pasajero.");
            return;
        }
        if (cantidad < 1 || cantidad > 5) {
            JOptionPane.showMessageDialog(this, "Solo puede comprar entre 1 y 5 boletos por transacción.");
            return;
        }

        String ruta = (String) comboRuta.getSelectedItem();
        Bus bus = busGuayaquil;
        double precio = 10.50;
        if (ruta.equals("QUITO - CUENCA")) {
            bus = busCuenca;
            precio = 12.75;
        } else if (ruta.equals("QUITO - LOJA")) {
            bus = busLoja;
            precio = 15.00;
        }
        int boletosCedula = 0;
        for (Pasajero pasajero : compras) {
            if (pasajero.getCedula().equals(cedula)) {
                boletosCedula += pasajero.getCantidad();
            }
        }
        if (boletosCedula + cantidad > 5) {
            JOptionPane.showMessageDialog(this, "La cédula no puede superar los 5 boletos acumulados.");
            return;
        }

        Pasajero pasajero = new Pasajero(cedula, nombre, ruta, cantidad, precio);
        try {
            bus.encolar(pasajero);

            boolean encontrado = false;

            int tamanio = compras.size();

            for (int i = 0; i < tamanio; i++) {

                Pasajero p = compras.poll();

                if (p.getCedula().equals(cedula)
                        && p.getRuta().equals(ruta)) {

                    p.setCantidad(p.getCantidad() + cantidad);
                    encontrado = true;
                }

                compras.add(p);
            }

            if (encontrado == false) {
                compras.add(new Pasajero(cedula, nombre, ruta, cantidad, precio));
            }

            double subtotal = cantidad * precio;
            totalRecaudado += subtotal;

            areaCompras.setText("");

            int total = compras.size();

            for (int i = 0; i < total; i++) {

                Pasajero p = compras.poll();

                areaCompras.append("Ruta: " + p.getRuta()
                        + " | Boletos: " + p.getCantidad()
                        + " | Pasajero: " + p.getNombre()
                        + " | Total: $" + (p.getCantidad() * p.getPrecio())
                        + "\n");

                compras.add(p);
            }


            actualizarLabels();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void actualizarLabels() {
        lblVendidosGuayaquil.setText("QUITO - GUAYAQUIL vendidos: " + busGuayaquil.getBoletosVendidos());
        lblDisponiblesGuayaquil.setText("QUITO - GUAYAQUIL disponibles: " + busGuayaquil.getDisponibles());
        lblVendidosCuenca.setText("QUITO - CUENCA vendidos: " + busCuenca.getBoletosVendidos());
        lblDisponiblesCuenca.setText("QUITO - CUENCA disponibles: " + busCuenca.getDisponibles());
        lblVendidosLoja.setText("QUITO - LOJA vendidos: " + busLoja.getBoletosVendidos());
        lblDisponiblesLoja.setText("QUITO - LOJA disponibles: " + busLoja.getDisponibles());
        lblRecaudacion.setText("Monto total recaudado: $" + String.format("%.2f", totalRecaudado));
    }
    public static void main(String[] args) {

        FormVentaBoletos ventana = new FormVentaBoletos();
        ventana.setVisible(true);

    }
}
