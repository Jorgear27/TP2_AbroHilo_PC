package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloAmarillo extends Thread {

    private int ventasP6;

    private Monitor monitor;

    private final int[] transiciones = {2};

    private final int[] demoras = {0};

    public int getVentasP6() {
        return ventasP6;
    }


    public HiloAmarillo (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.setName("Hilo Amarillo");
        this.ventasP6 = 0;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < transiciones.length; i++) {
                if (monitor.fireTransition(transiciones[i])) {

                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    //System.out.println(Thread.currentThread().getName()+": T" + transiciones[i] + " disparada");
                    //red.actualizarRdP(vector_disparo);

                    try {
                        sleep(demoras[i]); // demora de la transicion
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            ventasP6++;
        }
    }

    /** Implementacion Anterior
     * @Override
     * public void run() {
     *         while (true) { //Cuando se despierte tiene que volver a chequear???
     *             boolean flag = monitor.fireTransition(transiciones[0]);
     *             if (flag) {
     *                 //el hilo deberia tener que preguntar unicamente por su transicion que tiene conflicto?
     *                 //sus otras transiciones sin conflicto deberian dispararse por el mismo,sin entrar al monitor?
     *                 for (int i = 0; i < transiciones.length; i++) {
     *                     int[] vector_disparo = new int[12];
     *                     vector_disparo[transiciones[i]] = 1;
     *
     *                     System.out.println(Thread.currentThread().getName()+": T" + transiciones[i] + " disparada");
     *                     red.actualizarRdP(vector_disparo);
     *
     *                     try {
     *                         sleep(demoras[i]); // demora de la transicion
     *                     } catch (InterruptedException e) {
     *                         throw new RuntimeException(e);
     *                     }
     *                 }
     *             }
     *         }
     *     }
     */
}