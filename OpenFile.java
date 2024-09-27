import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenFile {
    public static void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione um arquivo para abrir");
        chooser.setApproveButtonText("Abrir arquivo");

        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            System.out.println("Arquivo selecionado: " + filePath);
            
            readFile(filePath);
        } else {
            System.out.println("Operação cancelada. Nenhum arquivo foi selecionado.");
            System.exit(0);
        }
    }

    private static void readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Conteúdo do arquivo:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Erro: Não foi possível ler o arquivo. " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        openFile();
    }
}
