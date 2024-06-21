package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloRojo extends Thread {

    private int canceladas;

    private Monitor monitor;

    private final int[] transiciones = {7, 8};

    private final int[] demoras_balanceadas = {0, 100};
    private final int[] demoras_desbalanceadas = {0, 277};

    public HiloRojo (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.setName("Hilo Rojo");
        this.canceladas = 0;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < transiciones.length; i++) {

                if (monitor.fireTransition(transiciones[i])) {

                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    try {
                        if(monitor.getPolitica().isBalanceada()){
                            sleep(demoras_balanceadas[i]);
                        }else{
                            sleep(demoras_desbalanceadas[i]);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            canceladas++;
        }
    }

    public int getCanceladas() {
        return canceladas;
    }
}