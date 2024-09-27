import javax.swing.*;
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.*;

public class CopyFile {
    public static void copyFile() {
        try {
            JFileChooser chooser = new JFileChooser();

            chooser.setDialogTitle("Selecione o arquivo que deseja copiar");
            chooser.setApproveButtonText("Copiar arquivo");
            int returnVal1 = chooser.showOpenDialog(null);
            String fileFullPath = "";
            String fileName = "";
            if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                fileFullPath = chooser.getSelectedFile().getAbsolutePath();
                fileName = chooser.getSelectedFile().getName();
            } else {
                System.out.println("Operação cancelada. Saindo...");
                System.exit(0);
            }

            chooser.setDialogTitle("Selecione a pasta de destino.");
            chooser.setApproveButtonText("Colar aqui");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal2 = chooser.showOpenDialog(null);
            String folderFullPath = "";
            if (returnVal2 == JFileChooser.APPROVE_OPTION) {
                folderFullPath = chooser.getSelectedFile().getAbsolutePath();
            } else {
                System.out.println("Operação cancelada. Saindo...");
                System.exit(0);
            }

            Path pathOrigin = Paths.get(fileFullPath);
            Path pathDestination = Paths.get(folderFullPath + "\\" + fileName);
            if (fileFullPath.length() > 0 && folderFullPath.length() > 0) {
                Files.copy(pathOrigin, pathDestination, REPLACE_EXISTING);
                System.out.println("Sucesso! O arquivo \"" + fileName + "\" foi copiado para \"" + folderFullPath + "\".");
            } else {
                System.out.println("Erro: Não foi possível copiar o arquivo. Verifique os caminhos e tente novamente.");
            }
        } catch (Exception e) {
            System.out.println("Erro: Ocorreu um problema ao tentar copiar o arquivo. " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        copyFile();
    }
}
