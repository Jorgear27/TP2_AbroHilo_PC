package Politica;
import Tareas.HiloVioleta;
import Tareas.HiloAmarillo;
import Tareas.HiloRojo;
import Tareas.HiloNaranja;
import java.util.Random;

public class Politica {

    private boolean balanceada;

    private HiloVioleta hiloVioleta;
    private HiloAmarillo hiloAmarillo;
    private HiloNaranja hiloNaranja;
    private HiloRojo hiloRojo;

    public Politica(boolean balanceada, HiloVioleta hiloVioleta, HiloAmarillo hiloAmarillo, HiloNaranja hiloNaranja, HiloRojo hiloRojo) {
        this.balanceada = balanceada;
        this.hiloAmarillo = hiloAmarillo;
        this.hiloNaranja = hiloNaranja;
        this.hiloRojo = hiloRojo;
        this.hiloVioleta = hiloVioleta;
    }

    public int cual(int[] transicionesPosibles) {
    

        if (balanceada) { // Si es balanceada elegimos c/u con un 50% de probabilidad
    
            // Politica que resuelve el conflicto entre los agentes de la plaza P6 (T2) y P7 (T3)

            if (transicionesPosibles[2] == 1 && transicionesPosibles[3] == 1) {
                if(hiloAmarillo.getVentasP6() < hiloVioleta.getVentasP7()){
                    return 2;
                    }
                else{
                    return 3;
                    }
            }
    
            // Politica que resuelve el conflicto entre los agentes de la plaza P11 (T6) y P12 (T7)

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
        else {
    
            // Politica que resuelve el conflicto entre los agentes de la plaza P6 (T2) y P7 (T3)
            if (transicionesPosibles[2] == 1 && transicionesPosibles[3] == 1) {
                int aux1 = hiloAmarillo.getVentasP6() + hiloVioleta.getVentasP7();
                if(hiloAmarillo.getVentasP6() <= aux1 * 0.75){
                    return 2;
                    }
                else {
                    return 3;
                }
            }

            // Politica que resuelve el conflicto entre los agentes de la plaza P11 (T6) y P12 (T7)
            if(transicionesPosibles[6] == 1 && transicionesPosibles[7] == 1) {
                int aux2 = hiloNaranja.getConfirmadas()+hiloRojo.getCanceladas();
                System.out.println("Veces que se disparo T6: "+ hiloNaranja.getConfirmadas());
                System.out.println("Veces que se disparo T7: "+ hiloRojo.getCanceladas());

                if(hiloNaranja.getConfirmadas() <= (aux2 * 0.8)){
                    System.out.println("Transicion elegida: 6");
                    return 6;
                } else {
                    System.out.println("Transicion elegida: 7");
                    return 7;
                }
            }
        }
/**
        // Si no hay conflictos, elegimos una transicion aleatoria
        int cual;
        boolean flag = true;
        do {
            cual = new Random().nextInt(transicionesPosibles.length);
            if (transicionesPosibles[cual] == 1) {
                flag = false;
            }
        } while (flag);
    
        return cual;*/
        for (int i =0; i < transicionesPosibles.length; i++){
            if (transicionesPosibles[i] == 1 && i!= 6 && i!= 7){
                System.out.println("Transicion elegida: "+ i);
                return i;
            }
        }
        if (transicionesPosibles[6] == 1){
            System.out.println("Transicion elegida: 6");
            return 6;
        } else return 7;
    }
    
    public boolean isBalanceada(){
        return balanceada;
    }
}