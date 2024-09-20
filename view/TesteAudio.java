package view;

import javax.sound.sampled.*;
import java.io.File;

public class TesteAudio {
    public static void main(String[] args) {
        try {
            File soundFile = new File("wav/alarme.wav"); // Caminho do arquivo
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            System.out.println("Som tocando!");
            Thread.sleep(5000); // Aguarde 5 segundos para ouvir o som
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
