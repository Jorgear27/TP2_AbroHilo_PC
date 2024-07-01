package Tareas;

import GestorMonitor.Monitor;

public class HiloVerdeClaro extends Thread {

    private Monitor monitor;

    private final int transicion = 11;

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
            }
        }

        // Si ya se atendieron a todos los clientes, termina la ejecucion del hilo
        System.out.println("PROCESO DE ATENCION DE RESERVAS FINALIZADO");

    }
}
