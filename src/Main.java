import GestorMonitor.Monitor;
import RedPetri.RdP;
import Tareas.*;

public class Main {
    public static void main(String[] args) {

    RdP rdp = new RdP();

    Monitor monitor = new Monitor(rdp);
    /*
    for(int i = 0; i<rdp.getcantidadTransiciones() ; i++){


            System.out.println(rdp.getTransicionesSensibilizadas()[i]);

        // TransicionesSensibilizadas = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }*/

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

    }
}