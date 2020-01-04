import java.awt.*;
import java.util.Base64;

public class But {
    public String producent;
    public String modelButa;
    public double rozmiar;
    public Image obraz;

    public But(double rozmiar, String producent, String modelButa){
        this.rozmiar = rozmiar;
        this.modelButa = modelButa;
        this.producent = producent;
    }
    public But(double rozmiar, String producent, String modelButa, Image obraz){
        this.rozmiar = rozmiar;
        this.modelButa = modelButa;
        this.producent = producent;
        this.obraz = obraz;
    }

    public String detale(){
        return  " But " + this.modelButa + " producenta " + this.producent + " O rozmiarze " + this.rozmiar;
    }
    public void zapiszBut (BazaButow bazaButow){
        bazaButow.dodajBut(this);
    }
}