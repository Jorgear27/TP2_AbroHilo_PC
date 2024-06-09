package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloBordeau extends Thread {

    private Monitor monitor;

    private RdP red;

    private final int[] transiciones = {0, 1};


    private final int[] demoras = {0, 1000};
    //private final int[] Tconflicto = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //private final int[] T0 = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //private final int[] T1 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    //private int[][] transiciones;


    public HiloBordeau (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.red = red;
        //transiciones = new int[responsabilidades][];
        //transiciones[0] = T0;
        //transiciones[1] = T1;

        while (true) { //Cuando se despierte tiene que volver a chequear???
            boolean flag =  monitor.fireTransition(transiciones[0]);
            if (flag) {
                //el hilo deberia tener que preguntar unicamente por su transicion que tiene conflicto?
                //sus otras transiciones sin conflicto deberian dispararse por el mismo,sin entrar al monitor?
                for(int i=0 ; i < transiciones.length ; i++) {
                    /** O hacemos asi o implementamos que en la red de petri reciba un entero y no un array*/
                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    red.actualizarRdP(vector_disparo);
                    try {
                        sleep(demoras[i]); // demora de la transicion
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
