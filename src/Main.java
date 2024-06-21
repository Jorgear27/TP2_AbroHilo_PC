import GestorMonitor.Monitor;
import RedPetri.RdP;
import Tareas.*;
import Politica.Politica;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {

    /** Creamos la Red de Petri */
    RdP rdp = new RdP();

    /** Creamos el monitor */
    Monitor monitor = new Monitor(rdp);

    /** Creamos todos los hilos de la red */
    HiloBordeau hilo1 = new HiloBordeau(monitor, rdp);
    HiloAmarillo hilo2 = new HiloAmarillo(monitor, rdp);
    HiloVioleta hilo3 = new HiloVioleta(monitor, rdp);
    HiloAzul hilo4 = new HiloAzul(monitor, rdp);
    HiloVerde hilo5 = new HiloVerde(monitor, rdp);
    HiloNaranja hilo6 = new HiloNaranja(monitor, rdp);
    HiloRojo hilo7 = new HiloRojo(monitor, rdp);
    HiloVerdeClaro hilo8 = new HiloVerdeClaro(monitor, rdp);

    /** Elegimos la politica deaseada */
    boolean balanceada = false;
    Politica politica = new Politica(balanceada, hilo3, hilo2, hilo6, hilo7);
    monitor.setPolitica(politica);

    /** Inciamos todos los hilos de la red */
    long tiempoInicio = System.currentTimeMillis();
    hilo1.start();
    hilo2.start();
    hilo3.start();
    hilo4.start();
    hilo5.start();
    hilo6.start();
    hilo7.start();
    hilo8.start();

    /** Esperamos que termine el hilo VerdeClaro (egreso de clientes) */
    try {
        hilo8.join();
        sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    /** Mostramos los resultados por pantalla */
    System.out.println("---------------------------------------------------");
    System.out.printf("Ventas por el agente en P6: \t%d (%.2f%%)\n",
                hilo2.getVentasP6(),
                (hilo2.getVentasP6() * 100.0) / (hilo2.getVentasP6() + hilo3.getVentasP7()));

    System.out.printf("Ventas por el agente en P7: \t%d (%.2f%%)\n",
                hilo3.getVentasP7(),
                (hilo3.getVentasP7() * 100.0) / (hilo2.getVentasP6() + hilo3.getVentasP7()));

    System.out.printf("Reservas totales confirmadas:\t%d (%.2f%%)\n",
                hilo6.getConfirmadas(),
                (hilo6.getConfirmadas() * 100.0) / (hilo6.getConfirmadas() + hilo7.getCanceladas()));

    System.out.printf("Reservas totales canceladas:\t%d (%.2f%%)\n",
                hilo7.getCanceladas(),
                (hilo7.getCanceladas() * 100.0) / (hilo6.getConfirmadas() + hilo7.getCanceladas()));System.out.print("---------------------------------------------------\nTIEMPO TOTAL DE EJECUCIÓN: ");
    System.out.println(System.currentTimeMillis() - tiempoInicio + " ms\n");

    System.exit(0);
    }
}