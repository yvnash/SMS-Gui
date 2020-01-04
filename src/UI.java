import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UI extends Component {
    Image obrazek;
    BazaButow bazaButow = new BazaButow();
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



    public UI() throws IOException {
        mainFrame.setSize(400,600);

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
        UiUtils uiUtils = new UiUtils();
        uiUtils.addLabel(modelButaLabel, mainPanel);
        uiUtils.addLastField(modelButaField, mainPanel);
        uiUtils.addLabel(producentLabel, mainPanel);
        uiUtils.addLastField(producentField,mainPanel);
        uiUtils.addLabel(rozmiarButaLabel,mainPanel);
        uiUtils.addMiddleField(rozmiaryList,mainPanel);
        uiUtils.addLastField(dodajZdjecieButa,mainPanel);
        uiUtils.addLastField(wyswietlBazeButow,mainPanel);
        uiUtils.addLastField(new JPanel(), mainPanel);
        uiUtils.addLabel("",mainPanel);
        uiUtils.addLastField(zapiszBut,mainPanel);
        uiUtils.addLabel("",mainPanel);
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
        dodajZdjecieButa.addActionListener(actionEvent -> {
            try {
                obrazek = wybierzPlik();
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
        });

        mainFrame.setVisible(true);
    }
    public Image wybierzPlik() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            Image i = ImageIO.read(f);
            return i;
        } else {
            return null;
        }
        }
}
