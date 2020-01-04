import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BazaButow {


    private static int iloscButow = 0;
    private static List<But> buty = new ArrayList<>();

    public void dodajBut(But but) {
        buty.add(but);
        iloscButow++;
    }


    public BazaButow() {
    }

    public static void wyswietlButa() {
        JFrame mainFrame = new JFrame("SMS - Shoe Management System");
        JPanel mainPanel = new JPanel();
        JLabel ileButow =  new JLabel("Liczba butów w bazie wynosi " + iloscButow);

        mainPanel.setLayout(new GridBagLayout());
        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.getContentPane().add(mainPanel, BorderLayout.NORTH);
        UiUtils uiUtils = new UiUtils();
        uiUtils.addLastField(ileButow,mainPanel);
        uiUtils.addLabel("",mainPanel);
        uiUtils.addLastField(new JLabel(""), mainPanel);
        mainFrame.setSize(600,500);
        mainFrame.setVisible(true);
        JLabel butInfo;


        for(But but: buty){
            butInfo = new JLabel();
            butInfo.setText(but.detale());
            //uiUtils.addLabel(new JPanel(),mainPanel);
            uiUtils.addLabel(butInfo,mainPanel);


            if (but.obraz != null){
                dodajPrzyciskOtwarciaZdjecia(mainPanel,but);
            }
            else {
                uiUtils.addLastField(new JLabel(""), mainPanel);
            }
        }

    }
    private static void otworzZdjecieButa(But but) throws IOException {
        JFrame zdjFrame = new JFrame("SMS - Shoe management system");
        JLabel zdjLabel = new JLabel();
        zdjFrame.setSize(800,600);
        Image butyImg = but.obraz;
        Image scaledButImg = butyImg.getScaledInstance(zdjFrame.getWidth(),zdjFrame.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon butIcon = new ImageIcon(scaledButImg);
        zdjLabel.setIcon(butIcon);
        zdjFrame.add(zdjLabel);
        zdjFrame.setVisible(true);
    }
    private static void dodajPrzyciskOtwarciaZdjecia(Container panel, But but){
        JButton pokazZdjButa = new JButton();
        pokazZdjButa.setText("Pokaż Zdjęcie Buta");
        UiUtils uiUtils = new UiUtils();
        uiUtils.addLastField(pokazZdjButa,panel);

        pokazZdjButa.addActionListener(actionEvent -> {
            try {
                otworzZdjecieButa(but);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private String policz() {
        int liczbaButow = 0;

        for (But but : buty) {
            liczbaButow++;
        }
        return "Liczba butów w bazie wynosi" + liczbaButow;
    }
}