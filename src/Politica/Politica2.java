package Politica;
import Tareas.HiloVioleta;
import Tareas.HiloAmarillo;
import Tareas.HiloRojo;
import Tareas.HiloNaranja;
import java.util.Random;

public class Politica2 {

    private boolean balanceada; // True si queremos politica balanceada, False si queremos politica priorizada

    private HiloVioleta hiloVioleta;
    private HiloAmarillo hiloAmarillo;
    private HiloNaranja hiloNaranja;
    private HiloRojo hiloRojo;
    int conflicto6y7;
    int resolv7;
    int resolv6;


    public Politica2(boolean balanceada, HiloVioleta hiloVioleta,HiloAmarillo hiloAmarillo,HiloNaranja hiloNaranja,HiloRojo hiloRojo) {
        this.balanceada = balanceada;
        this.hiloAmarillo = hiloAmarillo;
        this.hiloNaranja = hiloNaranja;
        this.hiloRojo = hiloRojo;
        this.hiloVioleta = hiloVioleta;
    }

    public int cual(int[] transicionesPosibles) { //Tengo que pasarle el vector de disparos posibles y me devuelve el disparo a realizar
    

        if (balanceada) { // Si es balanceada elegimos c/u con un 50% de probabilidad
    
            // Conflicto que decide entre los agentes de la plaza P6 (T2) y P7 (T3)
            if (transicionesPosibles[2] == 1 && transicionesPosibles[3] == 1) {
                if(hiloAmarillo.getVentasP6() < hiloVioleta.getVentasP7()){
                    return 2;
                    }
                else {
                    return 3;
                    }
            }
    
            // Conflicto que decide entre los agentes de la plaza P11 (T6) y P12 (T7)
            if(transicionesPosibles[6] == 1 && transicionesPosibles[7] == 1) {
                if(hiloNaranja.getConfirmadas() < hiloRojo.getCanceladas()){
                    return 6;
                    }
                else {
                    return 7;
                    }
            }
        }


        //PRIORIZADA
        else { // Si no es balanceada, elegimos c/u con un 75% de probabilidad
    
            // Conflicto que decide entre los agentes de la plaza P6 (T2) y P7 (T3)
            if (transicionesPosibles[2] == 1 && transicionesPosibles[3] == 1) {
                int aux1 = hiloAmarillo.getVentasP6() + hiloVioleta.getVentasP7();
                if(hiloAmarillo.getVentasP6() <= aux1 * 0.75){
                    return 2;
                    }
                else {
                    return 3;
                }
            }

            // Conflicto que decide entre los agentes de la plaza P11 (T6) y P12 (T7). Elije T6 el 80%
            if(transicionesPosibles[6] == 1 && transicionesPosibles[7] == 1) {
                conflicto6y7++;
                int aux2 = conflicto6y7;

                if(resolv6 <= (aux2 * 0.8)){
                    resolv6++;
                    System.out.println("Reservas en conflicto: " + conflicto6y7 + ". Confirmadas: "+ resolv6 + ". Canceladas: "+ resolv7 );
                    return 6;
                } else {
                    resolv7++;
                    System.out.println("Reservas en conflicto: " + conflicto6y7 + ". Confirmadas: "+ resolv6 + ". Canceladas: "+ resolv7 );
                    return 7;
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
}