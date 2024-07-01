package Tareas;

import GestorMonitor.Monitor;

public class HiloBordeau extends Thread {

    private Monitor monitor;

    private final int[] transiciones = {0, 1};

    private int clientesIngresados;

    private int totalClientes;

    public HiloBordeau (Monitor monitor, int totalClientes) {
        this.monitor = monitor;
        this.setName("Hilo Bordeau");
        this.clientesIngresados = 0;
        this.totalClientes = totalClientes;
    }

    @Override
    public void run() {

        while (clientesIngresados < totalClientes) {
            int i = 0;

            while (i < transiciones.length) {
                if (monitor.fireTransition(transiciones[i])) {
                    if (i == 0) clientesIngresados++;
                    i++;
                }
            }
        }

        // Si se llega al total de clientes, termina la ejecucion del hilo

        System.out.println("NO SE ACEPTA EL INGRESO DE MAS CLIENTES");
    }
}