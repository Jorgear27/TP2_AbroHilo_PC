package Cola;

import java.util.concurrent.Semaphore;

public class Cola {
    private int[] transicionesEsperando;

    private Semaphore[] hilosEsperando;

    private final int cantidadTransiciones = 12;

    public Cola() {
        this.transicionesEsperando = new int[cantidadTransiciones];
        this.hilosEsperando = new Semaphore[cantidadTransiciones];
    }

    public void entrar(int transicion) {

        transicionesEsperando[transicion] = 1;  // Pone un 1 en la transicion que esta esperando
        hilosEsperando[transicion] = new Semaphore(0);

        try {
            hilosEsperando[transicion].acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
    }
}