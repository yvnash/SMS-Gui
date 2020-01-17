import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class UI extends Component {
    UiUtils uiUtils = new UiUtils();
    String obrazek;
    BazaButow bazaButow = new BazaButow();
    JMenuBar menubar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JMenu submenuZapisz = new JMenu("Zapisz");
    JMenu submenuWczytaj = new JMenu("Wczytaj");
    JMenuItem zapiszJsonPlik = new JMenuItem("Zapisz baze pliku JSON");
    JMenuItem zapiszCloud = new JMenuItem("Zapisz do chmury");
    JMenuItem wczytajJsonPlik = new JMenuItem("Wczytaj baze z pliku JSON");
    JMenuItem wczytajCloud = new JMenuItem("Wczytaj z chmury");


    JFrame mainFrame = new JFrame("SMS - Shoe Management System");
    JPanel mainPanel = new JPanel();
    ArrayList<Double> rozmiary = new ArrayList();
    JComboBox<Double> rozmiaryList;
    JTextField modelButaField = new JTextField(10);
    JTextField producentField= new JTextField(20);
    JLabel modelButaLabel = new JLabel("Model Buta* ");
    JLabel producentLabel = new JLabel("Producent*");
    JLabel rozmiarButaLabel = new JLabel("Rozmiar Buta");
    JButton zapiszBut = new JButton("Zapisz Buty ");
    JButton wyswietlBazeButow = new JButton("Wyswietl Baze Butow ");
    JButton dodajZdjecieButa = new JButton("Dodaj zdjecie Buta");
    JButton wyswietlWszystkieButy= new JButton("Wyswietl wszystkie zdjecia butow");



    public UI() throws IOException {
        mainFrame.setSize(400,600);
        menu.setMnemonic(KeyEvent.VK_M);
        submenuZapisz.setMnemonic(KeyEvent.VK_Z);
        submenuWczytaj.setMnemonic(KeyEvent.VK_W);
        zapiszJsonPlik.setMnemonic(KeyEvent.VK_P);
        wczytajJsonPlik.setMnemonic(KeyEvent.VK_P);
        zapiszCloud.setMnemonic(KeyEvent.VK_C);
        wczytajCloud.setMnemonic(KeyEvent.VK_C);
        menubar.add(menu);

        menu.add(submenuZapisz);
        menu.addSeparator();
        menu.add(submenuWczytaj);
        submenuZapisz.add(zapiszJsonPlik);
        submenuZapisz.add(zapiszCloud);
        submenuWczytaj.add(wczytajJsonPlik);
        submenuWczytaj.add(wczytajCloud);
        mainFrame.setJMenuBar(menubar);


        Image butyImg = ImageIO.read(new File("buty logo.jpg"));
        Image scaledButyImg = butyImg.getScaledInstance(mainFrame.getWidth(),mainFrame.getHeight()/2,Image.SCALE_SMOOTH);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        for (double i=36.0; i<=46.0; i+=0.5){
            rozmiary.add(i);
        }

        rozmiaryList = new JComboBox(rozmiary.toArray());

        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.getContentPane().add(mainPanel, BorderLayout.NORTH);
        mainPanel.setLayout(new GridBagLayout());

        uiUtils.addLabel(modelButaLabel, mainPanel);
        uiUtils.addLastField(modelButaField, mainPanel);
        uiUtils.addLabel(producentLabel, mainPanel);
        uiUtils.addLastField(producentField,mainPanel);
        uiUtils.addLabel(rozmiarButaLabel,mainPanel);
        uiUtils.addMiddleField(rozmiaryList,mainPanel);
        uiUtils.addLastField(dodajZdjecieButa,mainPanel);
        uiUtils.addLastField(wyswietlBazeButow,mainPanel);
        uiUtils.addLastField(new JPanel(), mainPanel);
        uiUtils.addLastField(wyswietlWszystkieButy,mainPanel);
        uiUtils.addLabel("",mainPanel);
        uiUtils.addLastField(zapiszBut,mainPanel);
        uiUtils.addLastField(new JPanel(), mainPanel);
        uiUtils.addLabel("* - pole wymagane ",mainPanel);

        uiUtils.addLastField(new JPanel(), mainPanel);
        ImageIcon butyIcon = new ImageIcon(scaledButyImg);
        JLabel butyLogo = new JLabel();
        butyLogo.setIcon(butyIcon);
        uiUtils.addLastField(butyLogo,mainPanel);


        wyswietlBazeButow.addActionListener(actionEvent -> {
            BazaButow.wyswietlButa();
        });
        zapiszJsonPlik.addActionListener(actionEvent -> {
            try {
                BazaButow.konwertujDoJson(BazaButow.getButy(),this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        zapiszCloud.addActionListener(actionEvent -> {
            try {
                bazaButow.zapiszWS3();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        wczytajCloud.addActionListener(actionEvent -> {
            try {
                bazaButow.wczytajZS3();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        wczytajJsonPlik.addActionListener(actionEvent -> {
            try {
                BazaButow.konwertujZJson(wczytajPlikJson());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        dodajZdjecieButa.addActionListener(actionEvent -> {
            try {
                obrazek = Utils.konwertujObraz(wybierzPlik());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        wyswietlWszystkieButy.addActionListener(actionEvent -> {
            try {
                wyswietlWszystkieButy();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        zapiszBut.addActionListener(actionEvent -> {
            if (producentField.getText().equals("")  && modelButaField.getText().equals("") ){
                JOptionPane.showMessageDialog(null," Nie podałeś wymaganych punktow ", " SMS - Shoe Management System ERROR ",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                if(obrazek != null){
                    But but = new But(Double.parseDouble(rozmiaryList.getSelectedItem().toString()),producentField.getText(),modelButaField.getText(),obrazek);
                    bazaButow.dodajBut(but);
                  }
                else{
                But but = new But(Double.parseDouble(rozmiaryList.getSelectedItem().toString()),producentField.getText(),modelButaField.getText());
                bazaButow.dodajBut(but);
            }}
            obrazek = null;
        });

        mainFrame.setVisible(true);
    }
    public File wybierzPlik() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            return f;
        } else {
            return null;
        }
    }
    public String wczytajPlikJson() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Jsons", "json");
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            StringBuilder fileContents = new StringBuilder((int)f.length());
            Scanner scanner =new Scanner(f);
            String lineSeparator = System.getProperty("line.separator");
            try{
                while (scanner.hasNextLine()) {
                    fileContents.append(scanner.nextLine()+ lineSeparator);
                }
                return fileContents.toString();
            }finally {
                scanner.close();
            }

        } else {
            return null;
        }

    }
    void wyswietlWszystkieButy() throws IOException {
        JFrame zdjFrame = new JFrame("SMS - Shoe management system");
        JLabel obrazButaLabel = new JLabel();
        zdjFrame.setSize(800,600);
        obrazButaLabel.setBounds(0,0, 800,500);
        JButton nastepny = new JButton("->");
        JButton poprzedni = new JButton("<-");
        ImageIcon[] obrazki = BazaButow.wszystkieObrazki();
        AtomicInteger finalI = new AtomicInteger();
        int finalObrazkiLenght = obrazki.length;
        poprzedni.setMnemonic(KeyEvent.VK_LEFT);
        nastepny.setMnemonic(KeyEvent.VK_RIGHT);
        poprzedni.setBounds(0,510, 400,75);
        nastepny.setBounds(400,510,400,75);

        if(obrazki.length > 1) {
            obrazButaLabel.setIcon(obrazki[0]);
            zdjFrame.add(poprzedni);
            zdjFrame.add(nastepny);
            nastepny.addActionListener(actionEvent -> {
                    finalI.incrementAndGet();
                    if(finalI.intValue() >= finalObrazkiLenght){
                        finalI.set(0);
                    };
                    obrazButaLabel.setIcon(obrazki[finalI.intValue()]);
            });
            poprzedni.addActionListener(actionEvent -> {
                finalI.getAndDecrement();
                if(finalI.intValue() < 0){
                    finalI.set(finalObrazkiLenght - 1);
                }
                obrazButaLabel.setIcon(obrazki[finalI.intValue()]);
            });
            zdjFrame.add(obrazButaLabel);
            zdjFrame.setVisible(true);
        }
        else if(obrazki.length == 1) {
            obrazButaLabel.setIcon(obrazki[0]);
            zdjFrame.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null,"W bazie nie ma zadnych obrazow butow", " SMS - Shoe Management System ERROR ",JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
