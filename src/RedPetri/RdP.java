package RedPetri;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class RdP {

    private final int cantidadPlazas = 15;

    private final int cantidadTransiciones = 12;

    private int[] Marcado;

    private int[] TransicionesSensibilizadas;

    private final int[][] MatrizIncidencia;               // columnas = numero de transiciones = 12
                                                          // filas = numero de plazas = 15
    private HashMap<int[],Date> historialDisparos;


    public RdP() {
        historialDisparos = new HashMap<>();

        Marcado = new int[]{5, 1, 0, 0, 5, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0};

        MatrizIncidencia = new int[][]{
               // 0  1  2  3  4  5  6  7  8  9 10 11
                {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},//P0
                {-1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//P1
                { 1,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//P2
                { 0, 1,-1,-1, 0, 0, 0, 0, 0, 0, 0, 0},//P3
                {-1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},//P4
                { 0, 0, 1, 0, 0,-1, 0, 0, 0, 0, 0, 0},//P5
                { 0, 0,-1, 0, 0, 1, 0, 0, 0, 0, 0, 0},//P6
                { 0, 0, 0,-1, 1, 0, 0, 0, 0, 0, 0, 0},//P7
                { 0, 0, 0, 1,-1, 0, 0, 0, 0, 0, 0, 0},//P8
                { 0, 0, 0, 0, 1, 1,-1,-1, 0, 0, 0, 0},//P9
                { 0, 0, 0, 0, 0, 0,-1,-1, 1, 0, 1, 0},//P10
                { 0, 0, 0, 0, 0, 0, 1, 0, 0,-1, 0, 0},//P11
                { 0, 0, 0, 0, 0, 0, 0, 1,-1, 0, 0, 0},//P12
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,-1, 0},//P13
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1,-1},//P14

        };

        setTransicionesSensibilizadas(sensibilizar()); // TransicionesSensibilizadas = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public int[] getMarcado() {
        return Marcado;
    }

    public int[] getTransicionesSensibilizadas() {
        return TransicionesSensibilizadas;
    }

    public int getcantidadTransiciones() {
        return cantidadTransiciones;
    }

    public int getcantidadPlazas() {
        return cantidadPlazas;
    }

    private int[] ecuacionFundamental(int[] disparo){

        // Multiplicamos la MatrizIncidencia con el vector disparo obteniendo un vector de tamaño 15
        int[] aux = new int[cantidadPlazas];

        for (int i = 0; i < cantidadPlazas; i++) {
            for (int j = 0; j < cantidadTransiciones; j++) {
                aux[i] += MatrizIncidencia[i][j] * disparo[j];
            }
        }

        // Sumamos el vector Marcado al vector obtenido

        for (int i = 0; i < Marcado.length; i++) {
            aux[i] += Marcado[i];
        }
        return aux;


    }

    private boolean invariantesPlaza() {
        boolean condition1 = Marcado[0] + Marcado[2] + Marcado[3] + Marcado[5] + Marcado[8] + Marcado[9] + Marcado[11] + Marcado[12] + Marcado[13] + Marcado[14] == 5;
        boolean condition2 = Marcado[1] + Marcado[2] == 1;
        boolean condition3 = Marcado[2] + Marcado[3] + Marcado[4] == 5;
        boolean condition4 = Marcado[5] + Marcado[6] == 1;
        boolean condition5 = Marcado[7] + Marcado[8] == 1;
        boolean condition6 = Marcado[10] + Marcado[11] + Marcado[12] + Marcado[13] == 1;
        return condition1 && condition2 && condition3 && condition4 && condition5 && condition6;
    }


    public boolean disparoPosible(int[] disparo) {

        int[] nuevoMarcado = ecuacionFundamental(disparo);

        // Vemos si encontramos uno negativo
        for (int i = 0; i < nuevoMarcado.length; i++) {
            if (nuevoMarcado[i] < 0) {
                return false;
            }
        }

        return true;
    }

    public int[] sensibilizar() {

        int[] aux = new int[cantidadTransiciones];

        // Check transicion T0
        if (Marcado[0] >= 1 && Marcado[4] >= 1)
            aux[0] = 1;

        // Check transicion T1
        if (Marcado[2] == 1)
            aux[1] = 1;

        // Check transicion T2
        if (Marcado[3] >= 1 && Marcado[6] == 1)
            aux[2] = 1;


        // Check transicion T3
        if (Marcado[3] >= 1 && Marcado[7] == 1)
            aux[3] = 1;


        // Check transicion T4
        if (Marcado[8] == 1)
            aux[4] = 1;


        // Check transicion T5
        if (Marcado[5] == 1)
            aux[5] = 1;


        // Check transicion T6
        if (Marcado[9] >= 1 && Marcado[10] == 1)
            aux[6] = 1;


        // Check transicion T7
        if (Marcado[9] >= 1 && Marcado[10] == 1)
            aux[7] = 1;

        // Check transicion T8
        if (Marcado[12] == 1)
            aux[8] = 1;


        // Check transicion T9
        if (Marcado[11] == 1)
            aux[9] = 1;


            // Check transition T10
        if (Marcado[13] == 1)
            aux[10] = 1;


        // Check transition T11
        if (Marcado[14] == 1)
            aux[11] = 1;

        return aux;
    }

    public void setMarcado (int[] marcado){
        Marcado = marcado;
    }

    public void setTransicionesSensibilizadas ( int[] transicionesSensibilizadas){
        TransicionesSensibilizadas = transicionesSensibilizadas;
    }


    public void actualizarRdP (int[] disparo) { // deberia recibir un int

        if (disparoPosible(disparo)) {

            Date date = new Date();
            historialDisparos.put(disparo, date);

            int[] m2 = ecuacionFundamental(disparo);

            setMarcado(m2);

            int transicion=0;
            for (int i = 0; i < disparo.length; i++) {
                if (disparo[i] == 1) {
                    transicion = i;
                }
            }
            if(invariantesPlaza()){
                System.out.println("Se respetaron los invariantes de Plaza al disparar T"+ transicion);
            }

            else{
                System.out.println("HUBO UN PROBLEMA. El marcado generado no respeta los invariantes de Plaza");
                System.out.println(Arrays.toString(getMarcado()));
            }

            setTransicionesSensibilizadas(sensibilizar());
        } else {
            System.out.println("No es posible realizar este disparo");
        }
    }

}
