package LOG;
import RedPetri.RdP;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log extends Thread {

    private BufferedWriter writer;
    private String nombre;

    private int transicionUltima;

    private RdP rdp;

    public Log(String nombre, RdP rdp) {
        this.nombre = nombre;
        this.rdp = rdp;
        transicionUltima = 15;
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
                int transicionDisparada = rdp.getTransicionDisparada();

                if (transicionDisparada != transicionUltima) {
                    logTransition(transicionDisparada);
                    transicionUltima = transicionDisparada;
                }

            }

        }
    }
