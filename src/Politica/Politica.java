package Politica;

import java.util.Random;

public class Politica {
    private boolean balanceada;
    private int ventasP6;
    private int ventasP7;
    private int pagados;
    private int cancelados;

    public Politica(boolean balanceada) { // True si queremos politica balanceada, False si queremos politica priorizada
        this.balanceada = balanceada;
        this.ventasP6 = 0;
        this.ventasP7 = 0;
        this.pagados = 0;
        this.cancelados = 0;
    }

    public int cual(int[] transicionesPosibles) { //Tengo que pasarle el vector de disparos posibles y me devuelve el disparo a realizar

        Random random = new Random();

        // Primero chequeamos que alguno de los 2 conflictos esten sensibilizados
        if((transicionesPosibles[2] == 1 && transicionesPosibles[3] == 1) || (transicionesPosibles[6] == 1 && transicionesPosibles[7] == 1)) {

            if (balanceada) { // Si es balanceada elegimos c/u con un 50% de probabilidad

                // Conflicto que decide entre los agentes de la plaza P6 (T2) y P7 (T3)
                if (transicionesPosibles[2] == 1 && transicionesPosibles[3] == 1) {
                    int binaryValue = random.nextInt(2);
                    if (binaryValue == 1) {
                        //ventasP6++;
                        return 2;
                    } else {
                        //ventasP7++;
                        return 3;
                    }
                }

                // Conflicto que decide entre los agentes de la plaza P11 (T6) y P12 (T7)
                if(transicionesPosibles[6] == 1 && transicionesPosibles[7] == 1) {
                    int binaryValue = random.nextInt(2);
                    if (binaryValue == 1) {
                        //pagados++;
                        return 6;
                    } else {
                        //cancelados++;
                        return 7;
                    }
                }
            } else { // Si no es balanceada, elegimos c/u con un 75% de probabilidad
                // Conflicto que decide entre los agentes de la plaza P6 (T2) y P7 (T3)
                if (transicionesPosibles[2] == 1 && transicionesPosibles[3] == 1) {
                    int randomValue = random.nextInt(100);
                    int binaryValue = (randomValue < 75) ? 1 : 0;

                    if (binaryValue == 1) {
                        //ventasP6++;
                        return 2;
                    } else {
                        //ventasP7++;
                        return 3;
                    }
                }
                // Conflicto que decide entre los agentes de la plaza P11 (T6) y P12 (T7)
                if (transicionesPosibles[6] == 1 && transicionesPosibles[7] == 1) {
                    int randomValue = random.nextInt(100);
                    int binaryValue = (randomValue < 80) ? 1 : 0;

                    if (binaryValue == 1) {
                        //pagados++;
                        return 6;
                    } else {
                        //cancelados++;
                        return 7;
                    }
                }
            }
        }

        //Si no hay conflictos, elegimos una transicion aleatoria
        int cual;
        boolean flag = true;
        do {
            cual = new Random().nextInt(transicionesPosibles.length);
            if (transicionesPosibles[cual] == 1) {
                flag = false;
            }
        } while (flag);

        return cual;
    }

    /*
    public int getCancelados() {
        return cancelados;
    }

    public int getPagados() {
        return pagados;
    }

    public int getVentasP6() {
        return ventasP6;
    }

    public int getVentasP7() {
        return ventasP7;
    }*/
}
