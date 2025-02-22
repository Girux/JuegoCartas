import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private int DISTANCIA = 40;
    private int MARGEN = 10;
    private int TOTAL_CARTAS = 10;
    private String MENSAJE_PREDETERMINADO = "No se encontraron grupos";
    private String ENCABEZADO_MENSAJE = "Se encontraron los siguientes grupos:\n";
    private int MINIMA_CANTIDAD_GRUPO = 2;
    private Carta[] cartas = new Carta[TOTAL_CARTAS]; //Arreglo que almacena las cartas del jugador
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll(); //Limpia el panel eliminando las cartas repartidas anteriormente
        int x = MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA; //Calcula la posición inicial y se apilan de derecha a izquierda
        for (Carta carta : cartas) { //Recorre todas las cartas del jugador
            carta.mostrar(pnl, x, MARGEN);
            x -= DISTANCIA;
        }

        pnl.repaint(); //Se actualiza el panel
    }

    public String getGrupos() {
        String mensaje = MENSAJE_PREDETERMINADO;

        int[] contadores = new int[NombreCarta.values().length]; //Crea un arreglo de contadores para cada tipo de carta definido en NombreCarta
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++; //Recorre todas las cartas y cuenta la cantidad de cada tipo de carta
        }

        boolean hayGrupos = false;
        for (int contador : contadores) {   
            if (contador >= MINIMA_CANTIDAD_GRUPO) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = ENCABEZADO_MENSAJE;
            int posicion = 0; //Posición actual del contador
            for (int contador : contadores) {
                if (contador >= MINIMA_CANTIDAD_GRUPO) {
                    mensaje += Grupo.values()[contador] + " de " + NombreCarta.values()[posicion] + "\n";
                    //Grupo.values Obtiene el nombre del grupo según el número de cartas iguales
                    //NombreCarta.values Obtiene el nombre del tipo de carta
                }
                posicion++; //Aumenta la posición para pasar al siguiente tipo de carta
            }
        }

        return mensaje;
    }

}
