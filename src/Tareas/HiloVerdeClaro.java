package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloVerdeClaro extends Thread {

    private Monitor monitor;

    private final int transicion = 11;

    private final int demora = 0;

    private int clientesAtendidos;

    private int totalClientes;

    public HiloVerdeClaro (Monitor monitor, RdP red, int totalClientes) {
        this.monitor = monitor;
        this.clientesAtendidos = 0;
        this.setName("Hilo VerdeClaro");
        this.totalClientes = totalClientes;
    }

    @Override
    public void run() {
        while (clientesAtendidos < totalClientes) {
            if (monitor.fireTransition(transicion)) {
                int[] vector_disparo = new int[12];
                vector_disparo[transicion] = 1;

                clientesAtendidos++;

                try {
                    sleep(demora); // demora de la transicion
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // Si ya se atendieron a todos los clientes, se interrumpe el hil
        System.out.println("PROCESO DE ATENCION DE RESERVAS FINALIZADO");
    }
}
