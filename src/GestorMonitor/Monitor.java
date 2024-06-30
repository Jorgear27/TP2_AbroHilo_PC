package GestorMonitor;

import java.util.concurrent.Semaphore;
import Cola.Cola;
import LOG.Log;
import Politica.Politica;
import RedPetri.RdP;


public class Monitor implements MonitorInterfaz {

    private Politica politica;

    private final Semaphore mutex = new Semaphore(1, true);

    private final Cola colaW = new Cola();

    private boolean k;

    private RdP rdp;

    private Log logger;

    public Monitor(RdP rdp) {
        k = false;
        this.rdp = rdp;
        logger = new Log("Log_Transiciones.txt");
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

            mutex.acquire(); // Si el mutex no esta disponible, el hilo se agrega a una cola justa que llamaremos cola E

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        k = true;

        while (k) {

            int[] vector_disparo = new int[12];
            vector_disparo[transicion] = 1;

            k = rdp.disparoPosible(vector_disparo);  // Si la red de Petri puede disparar esa transicion, k es true

            if (k) {

                rdp.actualizarRdP(vector_disparo);  // Realizamos el disparo efectivo de la transicion
                logger.logTransition(transicion);

                int[] sensibilizadas = rdp.getTransicionesSensibilizadas(); // Vector con un 1 en transiciones que pueden dispararse
                int[] Tesperando = colaW.quienesEstan();                    // Vector con las transiciones esperando a la variable de condicion (cola W)
                int[] Tposibles = new int[sensibilizadas.length];           // Vector con las transiciones que estaban esperando en cola W y podrian dispararse con el nuevo marcado

                for (int i = 0; i < sensibilizadas.length; i++) {
                    Tposibles[i] = sensibilizadas[i] * Tesperando[i]; // AND logico entre sensibilizadas y Tesperando

                }

                if (hayTransicionesPosibles(Tposibles)) {

                    int disparonuevo = politica.cual(Tposibles); // Devuelve la transicion del hilo que despertaremos
                    colaW.despertar(disparonuevo);
                    return true; // El hilo señalizador sale de monitor, habiendo disparado una transicion y despertando un hilo en espera

                } else {
                    k = false;  // El nuevo marcado generado por el hilo señalizador no sensibilizo ninguna transicion de la cola W. Aun no sale del monitor.
                }

            } else { // No es posible el disparo por el marcado de la red

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