import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new UI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}