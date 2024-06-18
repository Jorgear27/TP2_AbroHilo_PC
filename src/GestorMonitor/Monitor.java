package GestorMonitor;

import java.util.concurrent.Semaphore;
import Cola.Cola;
import LOG.NewLog;
import Politica.Politica2;
import RedPetri.RdP;


public class Monitor implements MonitorInterfaz {

    private Politica2 politica;

    private final Semaphore mutex = new Semaphore(1, true);

    private final Cola colaW = new Cola();

    private boolean k;

    private RdP rdp;

    private NewLog logger;

    public Monitor(RdP rdp) {
        k = false;
        this.rdp = rdp;
        logger = new NewLog("NewLog.txt");
    }

    public void setPolitica(Politica2 politica) {
        this.politica = politica;
    }

    @Override
    public boolean fireTransition(int transicion) {

        try { // intentamos agarrar el mutex para entrar al monitor
            //System.out.println("Voy a intentar agarrar el mutex " + Thread.currentThread().getName() + ": T" + transicion);
            mutex.acquire(); //Si no hay nadie en la cola de espera Ep, tomo el recurso
            //System.out.println("Obtuve el mutex " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        k = true;
        while (k) {
            int[] vector_disparo = new int[12];
            vector_disparo[transicion] = 1;

            k = rdp.disparoPosible(vector_disparo);  // le preguntamos a la red de petri si puede dispararse, vuelve con k actualizado

            if (k) {
                rdp.actualizarRdP(vector_disparo);
                logger.logTransition(transicion);

                int[] sensibilizadas = rdp.getTransicionesSensibilizadas(); // Pone un 1 en transiciones que pueden dispararse
                int[] Tesperando = colaW.quienesEstan();                    // Un array con todas las transiciones esperando
                int[] Tposibles = new int[sensibilizadas.length];           // multiplica ambas y pone un uno si puede y quiere dispararse

                // AND logico entre sensibilizadas y Tesperando
                for (int i = 0; i < sensibilizadas.length; i++) {
                    Tposibles[i] = sensibilizadas[i] * Tesperando[i];
                }

                if (hayTransicionesPosibles(Tposibles)) { //m<>0
                    int disparonuevo = politica.cual(Tposibles); //politica de disparo
                    //System.out.println("Despierto a la transicion: " + disparonuevo);
                    // notificar al hilo correspondiente y lo saca de la cola
                    colaW.despertar(disparonuevo);
                    return true; //el true fue que se pudo disparar la transicion y me voy del monitor y sigue el que desperte
                } else {  //m==0
                    k = false;
                    //System.out.println(Thread.currentThread().getName() + ": No encontre a nadie en la cola de recursos, asi que pongo a k en falso");
                }
            } else {
                //System.out.println(Thread.currentThread().getName() + " No pude disparar mi transicion: " + transicion + " asi que libero el mutex y me voy a la cola de recursos ");
                mutex.release();
                colaW.entrar(transicion);
                //System.out.println(Thread.currentThread().getName() + ": Me desperte");
            }
        }
        mutex.release();
        //System.out.println(Thread.currentThread().getName() +": Se rompio el while y libero el mutex y me voy");
        return true;
}

    private boolean hayTransicionesPosibles(int [] Tposibles) {
        for (int i = 0; i < Tposibles.length; i++) {
            if (Tposibles[i]==1){
                return true;
            }
        }
        return false;
    }
}