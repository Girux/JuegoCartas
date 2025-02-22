import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame { //Ventana Grafica

    JPanel pnlJugador1, pnlJugador2; //Paneles para mostrar la información del jugador
    JTabbedPane tpJugadores; //Permite tener pestañas para otganizar la interfaz

    public FrmJuego() { //Constructor
        setSize(700, 250);
        setTitle("Juguemos al apuntado!");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        getContentPane().add(btnRepartir); //Añade el botón a la ventana

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        getContentPane().add(btnVerificar);

        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 40, 650, 150);
        getContentPane().add(tpJugadores);

        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(16, 139, 37));
        pnlJugador1.setLayout(null);

        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1); //Se crean las pestañas con los diferentes jugadores
        tpJugadores.addTab("Raúl Vidal", pnlJugador2);

        btnRepartir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repartirCartas(); //Al hacer click llama al evento repartirCartas()
            }
        });

        btnVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarJugador(); //Al hacer click llama al evento verificarJugador()
            }
        });
    }

    Jugador jugador1 = new Jugador(); //Se crean dos objetos de la clase Jugador.
    Jugador jugador2 = new Jugador();

    private void repartirCartas() {
        jugador1.repartir(); //Llama el metodo repartir
        jugador1.mostrar(pnlJugador1); //Llama el metodo mostrar para actualizar el pnlJugador1
        jugador2.repartir();
        jugador2.mostrar(pnlJugador2); // Llama el metodo para actualizar el pnlJugador2
    }

    private void verificarJugador() {
        int pestañaSeleccionada = tpJugadores.getSelectedIndex();
        switch (pestañaSeleccionada) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.getGrupos());
                break;
            case 1:
                JOptionPane.showMessageDialog(null, jugador2.getGrupos());
                break;
        }
    }

}
