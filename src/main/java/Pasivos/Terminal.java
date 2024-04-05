package Pasivos;

/**
 *
 * @author Selena
 */
public class Terminal {

    private int[] puestosEmbarque;
    private FreeShop freeShop;
    private char letraTerminal;
    private int numeroTerminal;
    
    public Terminal(char letra, int numeroTerminal, int[] puestos, FreeShop freeShop) {
        this.letraTerminal = letra;
        this.puestosEmbarque = puestos;
        this.numeroTerminal = numeroTerminal;
        this.freeShop = freeShop;
    }

    public int[] getPuestos() {
        return this.puestosEmbarque;
    }

    public char getLetra() {
        return this.letraTerminal;
    }

    public int getNumeroTerminal() {
        return this.numeroTerminal;
    }

    public FreeShop getFreeShop() {
        return this.freeShop;
    }

}
