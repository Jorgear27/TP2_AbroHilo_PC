package LOG;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewLog {

    BufferedWriter writer;
    private String nombre;

    public NewLog(String nombre) {
        this.nombre = nombre;
        try {
            writer = new BufferedWriter(new FileWriter(nombre, false));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void logTransition(int transicion) {
        try {
            writer.write("T" + transicion);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}