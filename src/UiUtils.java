import javax.swing.*;
import java.awt.*;

public class UiUtils {

    private GridBagConstraints lastConstraints = null;
    private GridBagConstraints middleConstraints = null;
    private GridBagConstraints labelConstraints = null;

    public UiUtils() {
        // najpierw ustaw constrainty dla "ostatniego" pola w kazdym rzedzie
        // a potem kopiuj i modyfikuj te constrainty

        // weightx wynosi 1.0 dla pól, 0.0 dla labeli
        // gridwidth wynosi Pozostalosc dla pol, 1 dla dabeli
        lastConstraints = new GridBagConstraints();

        // rozciaga komponenty horyzontalnie
        lastConstraints.fill = GridBagConstraints.HORIZONTAL;

        // komponenty ktore sa za krotkie badz wąskie
        // powinny zostac przypiete do gornego lewego rogu
        lastConstraints.anchor = GridBagConstraints.NORTHWEST;

        // ostatni komponent powinien miec bardzo duza ilosc miejsca
        lastConstraints.weightx = 1.0;

        // daj ostatniemu komponentowi reszte wiersza
        lastConstraints.gridwidth = GridBagConstraints.REMAINDER;

        // padding
        lastConstraints.insets = new Insets(3, 5, 3, 5);

        // srodkowe komponenty
        middleConstraints =
                (GridBagConstraints) lastConstraints.clone();

        // duzo miejsca ale nie caly wiersz
        middleConstraints.gridwidth = GridBagConstraints.RELATIVE;

        // Labele ktore sa pierwsze z rzedu
        labelConstraints =
                (GridBagConstraints) lastConstraints.clone();

        // dajemy im minimum miejsca
        labelConstraints.weightx = 0.0;
        labelConstraints.gridwidth = 1;
    }

    /**
     * dodaje komponent "pole", ale dowolny komponent moze byc uzyty
     * bedzie on rozciagniety do konca okna
     */
    public void addLastField(Component c, Container parent) {
        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
        gbl.setConstraints(c, lastConstraints);
        parent.add(c);
    }
    /**
     * dodaje dowolny skladnik labela zaczynajac nowy wiersz
     * w razie potrzeby, zostanie ustawiona szerokosc komponentu do minimalnej szerokosci
     * najszerszego elementu w formularzu
     * */

    public void addLabel(Component c, Container parent) {
        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
        gbl.setConstraints(c, labelConstraints);
        parent.add(c);
    }


    // Dodaje label
    public JLabel addLabel(String s, Container parent) {
        JLabel c = new JLabel(s);
        addLabel(c, parent);
        return c;
    }

    /**
     * Dodaje srodkowy komponent, bedzie on rozciagniety zeby
     * dosiegnac ostatniego komponentu
     */
    public void addMiddleField(Component c, Container parent) {
        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
        gbl.setConstraints(c, middleConstraints);
        parent.add(c);
    }

}
