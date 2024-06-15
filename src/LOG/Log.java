package LOG;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private ArrayList lista;
    private Logger logger;
    private FileHandler fileHandler;

    public Log() {
        lista = new ArrayList();
        try {
            // Creamos un Logger
            logger = Logger.getLogger(Log.class.getName());

            // Creamos un FileHandler
            fileHandler = new FileHandler("registro.log", false);

            // Asignamos un SimpleFormatter al FileHandler
            fileHandler.setFormatter(new SimpleFormatter());

            // Añadimos el FileHandler al Logger
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loggear(int[] marcado1, int[] disparo, Date date,int[] marcado2) {

        String transicion = "T";
        for (int i = 0; i < disparo.length; i++) {
            if (disparo[i] == 1) {
                transicion = transicion.concat(String.valueOf(i));
            }
        }

        // para las regex guardamos las secuecuencias de transiciones en un arraylist
        lista.add(transicion);

        logger.info(transicion+=" ");

        //logger.info("Al disparar " + transicion + " en el momento "+ date + "se pasa de " + Arrays.toString(marcado1) + " a " + Arrays.toString(marcado2) + "\n");
    }

    public ArrayList<String> getLista() {
        return lista;
    }

}


