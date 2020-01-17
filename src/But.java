import java.awt.*;
import java.io.File;
import java.util.Base64;

public class But {
    public String producent;
    public String modelButa;
    public double rozmiar;
    //public File obraz;
    public String obraz;
    public But(double rozmiar, String producent, String modelButa){
        this(rozmiar,producent,modelButa,null);
    }
    public But(double rozmiar, String producent, String modelButa, String obraz){
        this.rozmiar = rozmiar;
        this.modelButa = modelButa;
        this.producent = producent;
        this.obraz = obraz;
    }

    public String detale(){
        return  " But " + this.modelButa + " producenta " + this.producent + " O rozmiarze " + this.rozmiar;
    }

}