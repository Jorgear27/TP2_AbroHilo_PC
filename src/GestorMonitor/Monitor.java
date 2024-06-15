package GestorMonitor;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import Cola.Cola;
import Politica.Politica;
import RedPetri.RdP;


public class Monitor implements MonitorInterfaz {

    private Politica politicas = new Politica();

    private final Semaphore mutex = new Semaphore(1,true);

    private final Cola colaE = new Cola();

    private final Cola colaW = new Cola();

    private boolean k;

    private RdP rdp;

    public Monitor(RdP rdp) {
        k = false;
        this.rdp = rdp;
    }

    @Override
    public boolean fireTransition(int transicion) {

        try {  //intentamos agarrar el mutex para entrar al monitor
            System.out.println("Voy a intentar agarrar el mutex " + Thread.currentThread().getName());
            mutex.acquire(); //Si no hay nadie en la cola de espera Ep, tomo el recurso
            System.out.println("Obtuve el mutex " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        k = true;
        while (k) {
            int[] vector_disparo = new int[12];
            vector_disparo[transicion] = 1;
            System.out.println("Vector generado a partir del numero de transicion" + Arrays.toString(vector_disparo));

            k = rdp.disparoPosible(vector_disparo);  //le preguntamos a la red de petri si puede dispararse, vuelve con k actualizado
            System.out.println("flag k: " + k);

            if (k) {
                System.out.println("Se puede disparar la transicion: " + transicion);
                int[] sensibilizadas = rdp.getTransicionesSensibilizadas(); //[0 0 0 1 0 1 0 1 1] pone un 1 en transiciones que pueden dispararse
                int[] Tesperando = colaW.quienesEstan();                    //Un array con todas las transiciones esperando
                int[] Tposibles = new int[sensibilizadas.length];           //multiplica ambas y pone un uno si puede y quiere dispararse

                // AND logico entre sensibilizadas y Tesperando
                for (int i = 0; i < sensibilizadas.length; i++) {
                    Tposibles[i] = sensibilizadas[i] * Tesperando[i];
                    System.out.print(Tposibles[i]);
                }
                System.out.println();

                if (hayTransicionesPosibles(Tposibles)) { //m<>0
                    int disparonuevo = politicas.cual(Tposibles); //politica de disparo
                    System.out.println("Despierto a la transicion: " + disparonuevo);
                     // notificar al hilo correspondiente y lo saca de la cola
                    Thread aux = colaW.despertar(disparonuevo);
                    synchronized (aux){
                        aux.notify();
                    }
                    return true; //el true fue que se pudo disparar la transicion y me voy del monitor y sigue el que desperte
                } else {  //m==0
                    k = false;
                    System.out.println(Thread.currentThread().getName()+" No encontre a nadie en la cola de recursos, asi que me voy del monitor y libero el mutex");
                    mutex.release();
                    return true;
                }
            } else {
                System.out.println(Thread.currentThread().getName()+" No se puede disparar la transicion: " + transicion + " asi que libero el mutex y me voy a la cola de recursos ");
                mutex.release();
                colaW.entrar(Thread.currentThread(), transicion);
                try {
                    synchronized(Thread.currentThread()){
                        Thread.currentThread().wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+ "Me desperte");
                //k =! rdp.disparoPosible(vector_disparo); //si tiene los recursos manda k=false
            }
        }
        mutex.release();
        System.out.println("Se rompio el while y libero el mutex");
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






