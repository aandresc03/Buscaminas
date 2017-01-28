import acm.graphics.GImage;

/**
 * Clase correspondiente a las casillas del tablero
 */
public class casilla {
    private GImage imagen;
    private int x; //pos x de comienzo de la casilla
    private int y; // pos y de comienzo de la casilla
    private boolean mina; // contiene mina?
    private int numero;  //  numero de minas alrededor de la casilla
    private boolean descubierta; // casilla descubierta??

    public casilla(GImage image,int coordenadax, int coordenaday){
        this.imagen=image;
        this.mina=false;
        this.x=coordenadax;
        this.y=coordenaday;
        this.numero=0;
        this.descubierta=false;
    }

    public GImage getImagen() {
        return imagen;
    }

    public void setImagen(GImage imagen) {
        this.imagen = imagen;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMina() {
        return mina;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isDescubierta() {
        return descubierta;
    }

    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }

    /**
     * Define cual será el listener que capturará los clicks del ratón.
     * @param t objecto que recibirá los clicks
     */
    public final void añadirListener(final Tablero t) {
        if (t != null)  {
            imagen.addMouseListener(t);
        }
    }
}
