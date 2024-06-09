package Politica;

import java.util.Random;

public class Politica {

    public Politica() {
    }

    public int cual(int[] transicionesPosibles) { //Tengo que pasarle el vector de disparos posibles y me devuelve el disparo a realizar
        //Implementar politica de disparo
        int random;
        boolean flag = true;
        do {
            random = new Random().nextInt(transicionesPosibles.length);
            if (transicionesPosibles[random] == 1) {
                flag = false;
            }
        } while (flag);

        return random;
    }
}
