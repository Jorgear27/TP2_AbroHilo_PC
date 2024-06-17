package GestorMonitor;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import Cola.Cola;
import LOG.NewLog;
import Politica.Politica;
import RedPetri.RdP;


public class Monitor implements MonitorInterfaz {

    private Politica politica;

    private final Semaphore mutex = new Semaphore(1,true);

    private final Cola colaE = new Cola();

    private final Cola colaW = new Cola();

    private boolean k;

    private RdP rdp;

    private NewLog logger;

    public Monitor(RdP rdp, Politica politica) {
        k = false;
        this.rdp = rdp;
        this.politica = politica;
        logger = new NewLog("NewLog.txt");
    }

    @Override
    public boolean fireTransition(int transicion) {

        try { //intentamos agarrar el mutex para entrar al monitor
            System.out.println("Voy a intentar agarrar el mutex " + Thread.currentThread().getName() + ": T"+transicion);
            mutex.acquire(); //Si no hay nadie en la cola de espera Ep, tomo el recurso
            System.out.println("Obtuve el mutex " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        k = true;
        while (k) {
            int[] vector_disparo = new int[12];
            vector_disparo[transicion] = 1;

            k = rdp.disparoPosible(vector_disparo);  //le preguntamos a la red de petri si puede dispararse, vuelve con k actualizado

            if (k) {
                /** Por lo que entendi de la clase, si podes hacer tu transicion, lo haces aca adentro y despues te vas del monitor */
                rdp.actualizarRdP(vector_disparo);
                logger.logTransition(transicion);
                System.out.println("Pude actualizar la red de petri con el vector de disparo: "+ Arrays.toString(vector_disparo) );

                int[] sensibilizadas = rdp.getTransicionesSensibilizadas(); //[0 0 0 1 0 1 0 1 1] pone un 1 en transiciones que pueden dispararse
                int[] Tesperando = colaW.quienesEstan();                    //Un array con todas las transiciones esperando
                int[] Tposibles = new int[sensibilizadas.length];           //multiplica ambas y pone un uno si puede y quiere dispararse

                // AND logico entre sensibilizadas y Tesperando
                for (int i = 0; i < sensibilizadas.length; i++) {
                    Tposibles[i] = sensibilizadas[i] * Tesperando[i];
                }
                System.out.println("Esperando: " + Arrays.toString(Tesperando));
                System.out.println("Sensibili: " + Arrays.toString(sensibilizadas));
                System.out.println("Posibless: " + Arrays.toString(Tposibles));

                if (hayTransicionesPosibles(Tposibles)) { //m<>0
                    int disparonuevo = politica.cual(Tposibles); //politica de disparo
                    System.out.println("Despierto a la transicion: " + disparonuevo);
                    // notificar al hilo correspondiente y lo saca de la cola
                    colaW.despertar(disparonuevo);
                    /*Thread aux = colaW.despertar(disparonuevo);
                    synchronized (aux){
                        aux.notify();
                    }*/
                    return true; //el true fue que se pudo disparar la transicion y me voy del monitor y sigue el que desperte
                } else {  //m==0
                    k = false;
                    System.out.println(Thread.currentThread().getName()+": No encontre a nadie en la cola de recursos, asi que pongo a k en falso");
                    //mutex.release();
                    //return true;
                }
            } else {
                System.out.println(Thread.currentThread().getName()+" No pude disparar mi transicion: " + transicion + " asi que libero el mutex y me voy a la cola de recursos ");
                mutex.release();
                colaW.entrar(transicion);
                /*try {
                    synchronized(Thread.currentThread()){
                        Thread.currentThread().wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*/
                System.out.println(Thread.currentThread().getName()+ ": Me desperte");
                //k =! rdp.disparoPosible(vector_disparo); //si tiene los recursos manda k=false
            }
        }
        mutex.release();
        System.out.println(Thread.currentThread().getName() + ": Se rompio el while y libero el mutex y me voy");
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






