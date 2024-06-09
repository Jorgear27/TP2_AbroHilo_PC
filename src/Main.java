//import src.RedPetri.RdP;


import RedPetri.RdP;

public class Main {
    public static void main(String[] args) {


    RdP rdp = new RdP();

    for(int i = 0; i<rdp.getcantidadTransiciones() ; i++){


            System.out.println(rdp.getTransicionesSensibilizadas()[i]);

        // TransicionesSensibilizadas = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    }
}