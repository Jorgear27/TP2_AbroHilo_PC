package Cola;

public class Cola {
    //private final Queue<Thread> queue;

    private int[] transicionesEsperando;

    private Thread[] hilosEsperando;

    public Cola() {
        this.transicionesEsperando = new int[12];
        this.hilosEsperando = new Thread[12];
        //this.queue = new LinkedList<>();
    }

    public void entrar(Thread thread, int transicion) {
        try {
            //queue.add(thread);
            transicionesEsperando[transicion] = 1;  // pone un 1 en la transicion que esta esperando
            hilosEsperando[transicion] = thread;    // al mismo lugar que la transicion pone el hilo que esta esperando
            thread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /*
    public boolean hayEsperando(Thread thread) {
        return queue.contains(thread);
    }

    public Thread siguienteHilo() {
        return queue.poll();
    }*/

    public int[] quienesEstan() {
        return transicionesEsperando;
    }

    public void despertar(int transicion) {
        transicionesEsperando[transicion] = 0;
        hilosEsperando[transicion].notify();
    }
}