package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloVerdeClaro extends Thread {


    private Monitor monitor;

    private final int[] transiciones = {11};

    private final int[] demoras = {0};

    private int clientesAtendidos;

    private final int totalClientes = 186;

    public HiloVerdeClaro (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.clientesAtendidos = 0;
        this.setName("Hilo VerdeClaro");
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < transiciones.length; i++) {
                if (monitor.fireTransition(transiciones[i])) {
                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    //System.out.println(Thread.currentThread().getName()+": T" + transiciones[i] + " disparada");
                    //red.actualizarRdP(vector_disparo);

                    clientesAtendidos++;

                    try {
                        sleep(demoras[i]); // demora de la transicion
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (clientesAtendidos == totalClientes) {
                flag = false;
                interrupt();
                System.out.println("PROCESO DE ATENCION DE RESERVAS FINALIZADO");
            }
        }
    }

}