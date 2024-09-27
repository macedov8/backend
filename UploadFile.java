import javax.swing.*;
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.*;

public class UploadFile {
    public static void main(String[] args) {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecione o arquivo que deseja copiar.");
            int returnVal1 = chooser.showOpenDialog(null);
            String fileFullPath = "";
            String fileName = "";
            if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                fileFullPath = chooser.getSelectedFile().getAbsolutePath();
                fileName = chooser.getSelectedFile().getName();
                System.out.println("Arquivo selecionado: " + fileName);
                System.out.println("Caminho completo do arquivo: " + fileFullPath);
            } else {
                System.out.println("Operação cancelada. Nenhum arquivo foi selecionado.");
                return; 
            }

            chooser.setDialogTitle("Selecione a pasta de destino.");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal2 = chooser.showOpenDialog(null);
            String folderFullPath = "";
            if (returnVal2 == JFileChooser.APPROVE_OPTION) {
                folderFullPath = chooser.getSelectedFile().getAbsolutePath();
                System.out.println("Pasta selecionada: " + chooser.getSelectedFile().getName());
                System.out.println("Caminho completo da pasta: " + folderFullPath);
            } else {
                System.out.println("Operação cancelada. Nenhuma pasta foi selecionada.");
                return; 
            }

            Path pathOrigin = Paths.get(fileFullPath);
            Path pathDestination = Paths.get(folderFullPath + "\\" + fileName);
            Files.copy(pathOrigin, pathDestination, REPLACE_EXISTING);
            System.out.println("Sucesso! O arquivo \"" + fileName + "\" foi copiado para \"" + folderFullPath + "\".");
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível copiar o arquivo. " + e.getMessage());
        }
    }
}
