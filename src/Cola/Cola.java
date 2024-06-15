package Cola;

public class Cola {
    private int[] transicionesEsperando;

    private Thread[] hilosEsperando;

    private final Object lock = new Object();

    public Cola() {
        this.transicionesEsperando = new int[12];
        this.hilosEsperando = new Thread[12];
    }

    public void entrar(Thread thread, int transicion) {
        transicionesEsperando[transicion] = 1;  // pone un 1 en la transicion que esta esperando
        hilosEsperando[transicion] = thread;    // al mismo lugar que la transicion pone el hilo que esta esperando
    }

    public boolean estaVacia() {
        if (hilosEsperando.length == 0) {
            return true;
        }
        return false;
    }

    public int[] quienesEstan() {
        return transicionesEsperando;
    }

    public Thread despertar(int transicion) {
        transicionesEsperando[transicion] = 0;
        return hilosEsperando[transicion];
    }
}