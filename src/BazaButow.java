import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BazaButow {
    AWSCredentials credentials = new BasicAWSCredentials("************************","************************************");

    AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.EU_CENTRAL_1)
            .build();

    public static ArrayList<But> getButy() {
        return buty;
    }

    public static void setButy(ArrayList<But> buty) {
        BazaButow.buty = buty;
    }

    private static ArrayList<But> buty = new ArrayList<>();
    public void dodajBut(But but) {
        buty.add(but);
    }
    public BazaButow() {
    }

    public static void wyswietlButa() {
        JFrame mainFrame = new JFrame("SMS - Shoe Management System");
        JPanel mainPanel = new JPanel();
        JLabel ileButow =  new JLabel("Liczba butów w bazie wynosi " + buty.size());

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
        Image butyImg = Utils.konwertujObraz(but.obraz);
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
    static void konwertujDoJson(ArrayList<But> buty, Component parent) throws IOException {
        String sb = new Gson().toJson(buty);
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File plik = fileChooser.getSelectedFile();
            if(!FilenameUtils.getExtension(plik.getName()).equalsIgnoreCase("json")){
               plik = new File(plik.toString()+".json");
            }
            FileUtils.writeStringToFile(plik, sb.trim(), "UTF-8");
        }

    }
    static void konwertujZJson(String json) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        setButy(gson.fromJson(reader, new TypeToken<ArrayList<But>>() {
        }.getType()));
    }
    static ImageIcon[] wszystkieObrazki() throws IOException {
        ImageIcon[] obrazki;
        int i = 0, j = 0;
        for(But but: buty) {
            if (but.obraz != null) {
                i++;
            }
        }
        obrazki = new ImageIcon[i];
        for(But but : buty){
            if(but.obraz != null){
            obrazki[j] = new ImageIcon(Utils.konwertujObraz(but.obraz));
            j++;}
            }
        return obrazki;
        }
    void zapiszWS3() throws IOException {
        String sb = new Gson().toJson(buty);
        File temp = File.createTempFile("tempJson", ".json");
        FileUtils.writeStringToFile(temp, sb.trim(), "UTF-8");
        s3client.putObject("*****************","BazaButow/baza.json",temp);
        temp.delete();
    }

    void wczytajZS3() throws IOException {
        //File temp = File.createTempFile("tempJson", ".json");
      //  StringBuilder fileContents = new StringBuilder((int)temp.length());
        S3Object s3Object = s3client.getObject("*******************","BazaButow/baza.json");
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream,writer,"UTF-8");
        konwertujZJson(writer.toString());
    }
    }

