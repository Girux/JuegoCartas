import java.util.*;

import javax.swing.JPanel;

public class Jugador {

    private int DISTANCIA = 40;
    private int MARGEN = 10;
    private int TOTAL_CARTAS = 10;
    private String MENSAJE_PREDETERMINADO = "No se encontraron grupos";
    private String ENCABEZADO_MENSAJE = "Se encontraron los siguientes grupos:\n";
    private String ENCABEZADO_ESCALERA = "Se encontraron las siguientes escaleras:\n";
    private String ENCABEZADO_PUNTAJE = "Su puntaje es de: ";
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

        List<List<Carta>> escaleraList = escaleraList(cartas);


            mensaje += ENCABEZADO_ESCALERA;
            for (int i = 0; i < escaleraList.size() - 1; i++) {  // No imprimimos la última lista
                List<Carta> escalera = escaleraList.get(i);
                mensaje += "Escalera: ";
                for (Carta carta : escalera) {
                    mensaje += carta.getNombre() + " ";

                    if (carta.equals(escalera.get(escalera.size() - 1))) {
                        mensaje += "de " + carta.getPinta() + "\n";
                    }
                }
            }
            if (escaleraList.getLast().isEmpty()) {
                mensaje += ENCABEZADO_PUNTAJE + "0";
            } else {
                int acumulado = 0;
                for (Carta carta : escaleraList.getLast()) {
                    acumulado += carta.getNombre().getValor();
                }
                mensaje += ENCABEZADO_PUNTAJE + " " + acumulado;
            }

        return mensaje;

    }


    public static List<List<Carta>> escaleraList(Carta[] jugadorCartas) {
        List<List<Carta>> escaleraList = new ArrayList<>();

        // Ordenar las cartas por índice
        Arrays.sort(jugadorCartas, Comparator.comparingInt(Carta::getIndice));

        List<Carta> escaleraActual = new ArrayList<>();
        Set<Carta> cartasEnEscaleras = new HashSet<>();

        for (int i = 0; i < jugadorCartas.length; i++) {
            if (escaleraActual.isEmpty()) {
                escaleraActual.add(jugadorCartas[i]);
            } else {
                Carta ultimaCarta = escaleraActual.get(escaleraActual.size() - 1);

                // Si la carta actual es consecutiva y de la misma pinta
                if (jugadorCartas[i].getIndice() == ultimaCarta.getIndice() + 1
                        && jugadorCartas[i].getPinta().equals(ultimaCarta.getPinta())) {
                    escaleraActual.add(jugadorCartas[i]);
                } else {
                    // Guardamos la escalera si tiene al menos 2 cartas
                    if (escaleraActual.size() >= 2) {
                        escaleraList.add(new ArrayList<>(escaleraActual));
                        cartasEnEscaleras.addAll(escaleraActual);
                    }
                    escaleraActual.clear();
                    escaleraActual.add(jugadorCartas[i]);
                }
            }
        }

        // Agregar la última escalera si es válida
        if (escaleraActual.size() >= 2) {
            escaleraList.add(new ArrayList<>(escaleraActual));
            cartasEnEscaleras.addAll(escaleraActual);
        }

        // Agregar las cartas que no forman escaleras en una lista aparte
        List<Carta> cartasSinEscalera = new ArrayList<>();
        for (Carta carta : jugadorCartas) {
            if (!cartasEnEscaleras.contains(carta)) {
                cartasSinEscalera.add(carta);
            }
        }

        escaleraList.add(cartasSinEscalera);


        return escaleraList;
    }

}
