package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloVerdeClaro extends Thread {

    private Monitor monitor;

    private final int transicion = 11;

    private final int demora = 0;

    private int clientesAtendidos;

    private int totalClientes;

    public HiloVerdeClaro (Monitor monitor, int totalClientes) {
        this.monitor = monitor;
        this.clientesAtendidos = 0;
        this.setName("Hilo VerdeClaro");
        this.totalClientes = totalClientes;
    }

    @Override
    public void run() {

        while (clientesAtendidos < totalClientes) {

            if (monitor.fireTransition(transicion)) {

                clientesAtendidos++;

                try {
                    sleep(demora);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Si ya se atendieron a todos los clientes, termina la ejecucion del hilo
        System.out.println("PROCESO DE ATENCION DE RESERVAS FINALIZADO");

    }
}
