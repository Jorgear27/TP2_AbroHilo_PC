package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloNaranja extends Thread {

    private int confirmadas;

    private Monitor monitor;

    private final int[] transiciones = {6, 9, 10};

    private final int[] demoras_desbalanceadas = {0, 54, 44};
    private final int[] demoras_balanceadas = {0, 100, 100 };

    public HiloNaranja (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.setName("Hilo Naranja");
        this.confirmadas = 0;
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
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            confirmadas++;
        }
    }

    public int getConfirmadas() {
        return confirmadas;
    }
}