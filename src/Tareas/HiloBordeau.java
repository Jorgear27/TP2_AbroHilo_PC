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
    private int clientesIngresados;


    public HiloBordeau (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.red = red;
        this.setName("Hilo Bordeau");
        this.clientesIngresados = 0;
        //transiciones = new int[responsabilidades][];
        //transiciones[0] = T0;
        //transiciones[1] = T1;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < transiciones.length; i++) {
                if (monitor.fireTransition(transiciones[i])) {
                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    System.out.println(Thread.currentThread().getName()+": T" + transiciones[i] + " disparada");
                    //red.actualizarRdP(vector_disparo);

                    if (i == 0) {
                        clientesIngresados++;
                    }

                    try {
                        sleep(demoras[i]); // demora de la transicion
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (clientesIngresados == 10) {
                System.out.println("Clientes ingresados: " + clientesIngresados);
                System.out.println("Cerramos el ingreso de clientes." + Thread.currentThread().getName() + " termina.");
                flag = false;
                interrupt();
            }
        }

    }
}