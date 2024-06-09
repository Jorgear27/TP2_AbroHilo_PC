package GestorMonitor;

import java.util.concurrent.Semaphore;
import Cola.Cola;
import Politica.Politica;
import RedPetri.RdP;


public class Monitor implements MonitorInterfaz {

    private Politica politicas = new Politica();

    private final Semaphore mutex = new Semaphore(1);

    private final Cola colas = new Cola();

    private boolean k = true;

    private RdP rdp;

    public Monitor(RdP rdp) {
        k = false;
        this.rdp = rdp;
    }

    @Override
    public boolean fireTransition(int transicion) { // tiene que ser un entero solo
        k = mutex.tryAcquire();
        if (k) {  //si puede ser atendido
            while (k) {
                /** O hacemos asi o implementamos que en la red de petri reciba un entero y no un array*/
                int[] vector_disparo = new int[12];
                vector_disparo[transicion] = 1;

                k = rdp.disparoPosible(vector_disparo);  //le preguntamos a la red de petri si puede dispararse, vuelve con k actualizado
                if (k) {
                    int[] sensibilizadas = rdp.getTransicionesSensibilizadas(); //[0 0 0 1 0 1 0 1 1] pone un 1 en transiciones que pueden dispararse
                    int[] Tesperando = colas.quienesEstan();                    //Un array con todas las transiciones esperando
                    int[] Tposibles = new int[sensibilizadas.length];           //multiplica ambas y pone un uno si puede y quiere dispararse

                    // AND logico entre sensibilizadas y Tesperando
                    for (int i = 0; i < sensibilizadas.length; i++) {
                        Tposibles[i] = sensibilizadas[i] * Tesperando[i];
                    }

                    if (hayTransicionesPosibles(Tposibles)) {
                        int disparonuevo = politicas.cual(Tposibles); //politica de disparo
                        colas.despertar(disparonuevo); // sacar del la cola
                        return true; //el true fue que se pudo disparar la transicion por parametro
                    } else {
                        k = false;
                    }
                } else {
                    mutex.release();
                    colas.entrar(Thread.currentThread(), transicion);
                    return false; //el true fue que no pudo disparar la transicion por parametro
                }
            }

        } else {
            mutex.release();

        }
        return false;
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






