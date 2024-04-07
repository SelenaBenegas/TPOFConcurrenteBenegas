package Otros;

/**
 *
 * @author Selena
 */
public class Random {

    private static final java.util.Random RANDOM = new java.util.Random();
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //Las aerolíneas van a tener un nombre "Aerolíena + <cantidad de LONG_AEROLINEA> letras en mayusculas"
    private static final int LONG_AEROLINEA = 3;

    public Random() {

    }

    public int generarInt(int tope) {
        return RANDOM.nextInt(tope);
    }

    public int generarIntRango(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public char getChar(int indice) {
        return CARACTERES.charAt(indice);
    }

    public int generarHora() { //Genera la hora de los vuelos
        int min = 7; // una hora después de que abra el aeropuerto
        int max = 22; // una hora antes de que cierre el aeropuerto
        return (RANDOM.nextInt(max - min + 1) + min)*100;
    }

    public String generarNombreAerolinea() {
        StringBuilder sb = new StringBuilder(LONG_AEROLINEA);
        for (int i = 0; i < LONG_AEROLINEA; i++) {
            int randomIndex = RANDOM.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(randomIndex));
        }
        return "AEROLÍNEA '" + sb.toString() + "'";
    }

    public boolean generarBoolean() {
        return RANDOM.nextBoolean();
    }

}
