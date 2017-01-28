import acm.program.GraphicsProgram;

/**
 * Clase principal de inicio del juego
 */
public class Principal extends GraphicsProgram{
    public void run (){
        this.setSize(285,500);
        Tablero mitablero = new Tablero(this);
        mitablero.rellenaTablero();
        mitablero.calculaMinas();
        mitablero.calculaNumeros();
        mitablero.pintaTablero();
    }


    public static void main(String[] args) {
        new Principal().start(args);
    }
}
