package LOG;
import RedPetri.RdP;

import java.util.concurrent.BlockingQueue;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log extends Thread {

    private BufferedWriter writer;
    private String nombre;

    private BlockingQueue<Integer> queue;

    public Log(String nombre, RdP rdp) {
        this.queue = rdp.getQueue();
        this.nombre = nombre;
        try {
            writer = new BufferedWriter(new FileWriter(nombre, false));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logTransition(int transicion) {
        try {
            writer.write("T" + transicion);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int transicion = queue.take(); // Esto va a bloquear el hilo hasta que haya algo en la cola
                logTransition(transicion);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
