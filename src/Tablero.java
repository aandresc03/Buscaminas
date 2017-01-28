import acm.graphics.GImage;
import acm.graphics.GLabel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;


/**
 * Clase correspondiente al tablero de juego
 */
public class Tablero implements MouseListener {
    private static final int ANCHO = 10;
    private static final int ALTO = 10;
    private static final int MINAS = 5;
    private Principal pantalla = null;
    private casilla [][] tablero =new casilla[ANCHO][ALTO];
    private GLabel etiqueta = null;
    private GImage emoji=null;

    /**
     * Rellena tablero con casillas vacías
     */
    public void rellenaTablero(){
        int x=0;
        for (int i = 0; i <tablero.length ; i++) {
            int y=0;
            for (int j = 0; j <tablero[i].length ; j++) {
                GImage imagen = new GImage("images/boton2.png");
                casilla nuevacasilla = new casilla(imagen,x,y);
                nuevacasilla.añadirListener(this);
                tablero[i][j]=nuevacasilla;
                y+=28; //Las imágenes utilizadas son de 28x28 pixels
            }
            x+=28;//Las imágenes utilizadas son de 28x28 pixels
        }
    }



    /**
     * Calcula posiciones de minas en tablero en posiciones aleatorias
     */
    public void calculaMinas(){
        Random rnd = new Random();
        int minas=0;
        do {
            int fila =rnd.nextInt(ANCHO); //Se tiene en cuenta el número de casillas
            int columna = rnd.nextInt(ALTO);
            if (!(tablero[fila][columna].isMina())) {
                    tablero[fila][columna].setMina(true);
                    minas++;
                    }
        }while(minas < MINAS);
    }



    /**
     * Calcula los valores numéricos que tendran las casillas que los contengan
     */
    public void calculaNumeros(){
        for (int i = 0; i <tablero.length ; i++) {
            for (int j = 0; j <tablero[i].length ; j++) {
                if (tablero[i][j].isMina()){
                    for (int k = i-1; k <i+2 ; k++) {
                        for (int l = j-1; l <j+2 ; l++) {
                            if ((k>=0)&&(l>=0)&&(k<tablero.length)&&(l<tablero[i].length)) {
                                if (!tablero[k][l].isMina()) {
                                    tablero[k][l].setNumero(tablero[k][l].getNumero() + 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    /**
     * Dibuja el tablero en pantalla y comprueba si se ha ganado
     */
    public void pintaTablero()  {
        int descubiertas=0;
        for (int i = 0; i <tablero.length ; i++) {
            for (int j = 0; j <tablero[i].length ; j++) {
                pantalla.add(tablero[i][j].getImagen(),tablero[i][j].getX(),tablero[i][j].getY());
                if ((tablero[i][j].isDescubierta())&&(!tablero[i][j].isMina())){
                    descubiertas++;
                }
            }
        }
        if (descubiertas==((tablero.length*tablero[0].length)-MINAS)) { //Todas descubiertas excepto las minas
            this.etiqueta=new GLabel("HECHO!!!!");
            this.emoji=new GImage("images/ganador.jpg");
            finalizaPartida();
        }
    }



    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    }






    /**
     * Muestra todas las minas
     */
    public void mostrarMinas(){
        for (int k = 0; k <tablero.length ; k++) {
            for (int l = 0; l <tablero[k].length ; l++) {
                if (tablero[k][l].isMina() &&(!tablero[k][l].isDescubierta())) {
                    tablero[k][l].setImagen(new GImage("images/mina.png"));
                    tablero[k][l].setDescubierta(true);
                }
            }
        }
    }


    /**
     * Busca el elemento del array que corresponde a la casilla sobre la que se ha clicado
     * @param posicion Punto sobre el que se ha clicado. Posición 0 es la x, posición 1 es la y
     */
    public void buscaClicEnArray (int [] posicion){
        int i=0,j=0;
        boolean trobat=false; //Buscar el elemento del array bidimensional donde se ha clicado
        while (!trobat && i<tablero.length) {
            j=0;
            while (!trobat && j<tablero[i].length){
                if ((tablero[i][j].getX() <= posicion[0]) && (tablero[i][j].getX() + 28 >= posicion[0]) //Se tiene en cuenta el numero de pixels de una casilla
                        && (tablero[i][j].getY() <= posicion[1]) && (tablero[i][j].getY() + 28 >= posicion[1])) {
                    trobat=true;
                    posicion[0]=i; //Guardar la posición de fila y columna
                    posicion[1]=j;
                }
                j++;
            }
            i++;
        }
    }



    public void mouseClicked(MouseEvent e) {
        int [] posicion = new int [2];
        posicion[0]=e.getX();
        posicion[1]=e.getY();
        buscaClicEnArray(posicion);

        if (tablero[posicion[0]][posicion[1]].isMina()) {  //Si es mina se muestran todas las minas
            System.out.println("MINA en " + tablero[posicion[0]][posicion[1]].getX() + " " + tablero[posicion[0]][posicion[1]].getY());
            tablero[posicion[0]][posicion[1]].setImagen(new GImage("images/mina2.png"));
            tablero[posicion[0]][posicion[1]].setDescubierta(true);
            mostrarMinas();
            this.etiqueta=new GLabel("MINA!!!!");//Se finaliza la partida
            this.emoji=new GImage("images/perdedor.jpg");
            finalizaPartida();
        }
        else { //si no es mina se muestra la imagen correspondiente a su número
            switch (tablero[posicion[0]][posicion[1]].getNumero()) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    tablero[posicion[0]][posicion[1]].setImagen(new GImage("images/"+tablero[posicion[0]][posicion[1]].getNumero()+".png"));
                    break;
                case 0:
                    eliminaVacios(posicion[0], posicion[1]);//Si no tiene número se eliminan todos los vacíos colindantes
                    break;
            }
        }
        tablero[posicion[0]][posicion[1]].setDescubierta(true);
        pintaTablero();
    }

    /**
     * Finaliza la partida y muestra mensaje
     */
    public void finalizaPartida(){
        pantalla.add(this.etiqueta,50,300);
        this.etiqueta.setFont("Arial-BOLD-18");
        pantalla.add(this.emoji,50,300);
        for (int i = 0; i <tablero.length ; i++) {
            for (int j = 0; j <tablero[i].length ; j++) {
                 tablero[i][j].getImagen().removeMouseListener(this);//desactiva ratón
            }
        }
    }

    /**
     * Descubre de forma recursiva las posiciones vacias a partir de una posición dada
     * @param posx valor x de la posición
     * @param posy valor y de la posición
     */
    public void eliminaVacios(int posx, int posy){
       if ((posx>=0)&&(posy>=0)&&(posx<tablero.length)&&(posy<tablero[0].length))
           if (!tablero[posx][posy].isDescubierta()) {
            if (tablero[posx][posy].getNumero() != 0) {
                tablero[posx][posy].setImagen(new GImage("images/" + tablero[posx][posy].getNumero() + ".png"));
                tablero[posx][posy].setDescubierta(true);
            }
            else {
                tablero[posx][posy].setImagen(new GImage("images/boton1.png"));
                tablero[posx][posy].setDescubierta(true);
                eliminaVacios(posx - 1, posy);//recursivo
                eliminaVacios(posx + 1,posy);//recursivo
                eliminaVacios(posx,posy-1);//recursivo
                eliminaVacios(posx,posy+1);//recursivo
            }
        }
}



    public Tablero(final Principal c){
        pantalla = c;
    }

}
