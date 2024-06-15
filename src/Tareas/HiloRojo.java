package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloRojo extends Thread {

    private Monitor monitor;

    private RdP red;

    private final int[] transiciones = {7, 8};

    private final int[] demoras = {0, 1000};

    public HiloRojo (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.red = red;
        this.setName("Hilo Rojo");
    }

    @Override
    public void run() {
        while (true) { //Cuando se despierte tiene que volver a chequear???
            boolean flag = monitor.fireTransition(transiciones[0]);
            if (flag) {
                //el hilo deberia tener que preguntar unicamente por su transicion que tiene conflicto?
                //sus otras transiciones sin conflicto deberian dispararse por el mismo,sin entrar al monitor?
                for (int i = 0; i < transiciones.length; i++) {
                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    System.out.println("T" + transiciones[i] + " disparada");
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