package GestorMonitor;

import java.util.concurrent.Semaphore;
import Cola.Cola;
import Politica.Politica;
import RedPetri.RdP;

import static java.lang.Thread.currentThread;


public class Monitor implements MonitorInterfaz {

    private Politica politica;

    private final Semaphore mutex = new Semaphore(1, true);

    private final Cola colaW = new Cola();

    private boolean k;

    private RdP rdp;



    private static boolean hayDurmiendo;

    public Monitor(RdP rdp) {
        hayDurmiendo = false;
        k = false;
        this.rdp = rdp;
    }

    public void setPolitica(Politica politica) {
        this.politica = politica;
    }


    public Politica getPolitica() {
        return politica;
    }

    @Override
    public boolean fireTransition(int transicion) {

        try { // El hilo intenta tomar el mutex para entrar al monitor
            System.out.println("Intento Agarrar Mutex T"+ transicion);
            mutex.acquire(); // Si el mutex no esta disponible, el hilo se agrega a una cola justa que llamaremos cola E
            System.out.println("Agarre Mutex T"+ transicion);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        k = true;

        while (k) {

            int[] vector_disparo = new int[12];
            vector_disparo[transicion] = 1;

            k = rdp.disparoPosible(vector_disparo);  // Si la red de Petri puede disparar esa transicion, k es true

            if (k) {
                // chequeo time
                boolean ventana = rdp.estaEnVentanaTemporal(transicion, politica);
                if (!ventana) { //el tiempo transcurrido es menor al alfa
                    System.out.println("Todavia no cumpli con el tiempo de espera T"+ transicion);

                    /**
                     if (hayDurmiendo){
                     System.out.println("Ya habia un hilo durmiendo, me voy a la cola");
                     k = false;
                     } else {*/

                    mutex.release();
                    //hayDurmiendo = true;
                    try {
                        int tiempoRestante = rdp.getTiempoRestante(transicion, getPolitica());

                        System.out.println("Me voy a dormir T"+ transicion+ " por "+ tiempoRestante+ " milisegundos");

                        currentThread().sleep(tiempoRestante);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //hayDurmiendo = false;
                    System.out.println("Me desperte y me voy T"+ transicion);
                    return false;
                    //}
                }

                System.out.println("Me disparo T"+ transicion);
                rdp.actualizarRdP(vector_disparo);  // Realizamos el disparo efectivo de la transicion

                int[] sensibilizadas = rdp.getTransicionesSensibilizadas(); // Vector con un 1 en transiciones que pueden dispararse
                int[] Tesperando = colaW.quienesEstan();                    // Vector con las transiciones esperando a la variable de condicion (cola W)
                int[] Tposibles = new int[sensibilizadas.length];           // Vector con las transiciones que estaban esperando en cola W y podrian dispararse con el nuevo marcado

                for (int i = 0; i < sensibilizadas.length; i++) {
                    Tposibles[i] = sensibilizadas[i] * Tesperando[i]; // AND logico entre sensibilizadas y Tesperando

                }

                if (hayTransicionesPosibles(Tposibles)) {
                    int disparonuevo = politica.cual(Tposibles); // Devuelve la transicion del hilo que despertaremos
                    System.out.println("Me identifico como T"+ transicion+ " y despierto a T"+ disparonuevo);
                    colaW.despertar(disparonuevo);
                    return true; // El hilo señalizador sale de monitor, habiendo disparado una transicion y despertando un hilo en espera

                } else {
                    System.out.println("No hay nadie para disparar me voy T"+ transicion);
                    k = false;  // El nuevo marcado generado por el hilo señalizador no sensibilizo ninguna transicion de la cola W. Aun no sale del monitor.
                }

            } else { // No es posible el disparo por el marcado de la red
                System.out.println("No me pude disparar, me voy a la cola de espera T"+ transicion);
                mutex.release();
                colaW.entrar(transicion);

            }
        }
        mutex.release();
        return true;
    }

    private boolean hayTransicionesPosibles(int [] Tposibles) {
        for(int i = 0; i < Tposibles.length; i++) {
            if (Tposibles[i]==1){
                return true;
            }
        }
        return false;
    }
}