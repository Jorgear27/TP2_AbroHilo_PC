import GestorMonitor.Monitor;
import RedPetri.RdP;
import Tareas.*;
import Politica.Politica;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {

    RdP rdp = new RdP();

    Politica politica = new Politica(false);

    Monitor monitor = new Monitor(rdp, politica);

    // Creamos e iniciamos todos los hilos de la red
    HiloBordeau hilo1 = new HiloBordeau(monitor, rdp);
    hilo1.start();

    HiloAmarillo hilo2 = new HiloAmarillo(monitor, rdp);
    hilo2.start();

    HiloVioleta hilo3 = new HiloVioleta(monitor, rdp);
    hilo3.start();

    HiloAzul hilo4 = new HiloAzul(monitor, rdp);
    hilo4.start();

    HiloVerde hilo5 = new HiloVerde(monitor, rdp);
    hilo5.start();

    HiloNaranja hilo6 = new HiloNaranja(monitor, rdp);
    hilo6.start();

    HiloRojo hilo7 = new HiloRojo(monitor, rdp);
    hilo7.start();

    HiloVerdeClaro hilo8 = new HiloVerdeClaro(monitor, rdp);
    hilo8.start();

    /** Tenemos que esperar al hilo VerdeClaro*/
    boolean flag = true;
    while(flag){
        if(hilo8.isInterrupted()) {
            flag = false;
        }
    }

    try {
        sleep(5000);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }

    System.out.println("Ventas en P6: " + hilo4.getVentasP6() + " | Ventas en P7: " + hilo5.getVentasP7());
    System.out.println("Reservas confirmadas: " + hilo6.getConfirmadas() + " | Reservas canceladas: " + hilo7.getCanceladas());

    System.exit(0);
    }
}