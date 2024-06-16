package Cola;

import java.util.concurrent.Semaphore;

public class Cola {
    private int[] transicionesEsperando;

    //private Thread[] hilosEsperando;
    private Semaphore[] hilosEsperando;

    private final int cantidadTransiciones = 12;

    private final Object lock = new Object();

    public Cola() {
        this.transicionesEsperando = new int[cantidadTransiciones];
        this.hilosEsperando = new Semaphore[cantidadTransiciones];
    }

    public void entrar(int transicion) {
        transicionesEsperando[transicion] = 1;  // pone un 1 en la transicion que esta esperando
        hilosEsperando[transicion] = new Semaphore(0);
        try {
            hilosEsperando[transicion].acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //hilosEsperando[transicion] = thread;    // al mismo lugar que la transicion pone el hilo que esta esperando
    }

    public boolean estaVacia(){
        for (int i = 0; i < hilosEsperando.length; i++) {
            if (hilosEsperando[i].hasQueuedThreads()) {
                return false;
            }
        }
        return true;
    }

    public int[] quienesEstan() {
        return transicionesEsperando;
    }

    public void despertar(int transicion) {
        transicionesEsperando[transicion] = 0;
        hilosEsperando[transicion].release();
        //return hilosEsperando[transicion];
    }
}