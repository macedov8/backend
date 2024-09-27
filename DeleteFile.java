import javax.swing.*;
import java.nio.file.*;

public class DeleteFile {
    public static void deleteFile() {
        try {
            JFileChooser chooser = new JFileChooser();

            chooser.setDialogTitle("Selecione o arquivo que deseja apagar");
            chooser.setApproveButtonText("Apagar arquivo");
            int returnVal1 = chooser.showOpenDialog(null);
            String fileFullPath = "";
            if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                fileFullPath = chooser.getSelectedFile().getAbsolutePath();
            } else {
                System.out.println("Operação cancelada. Saindo...");
                System.exit(0);
            }

            Path pathOrigin = Paths.get(fileFullPath);
            Files.delete(pathOrigin);
            System.out.println("Sucesso! O arquivo \"" + chooser.getSelectedFile().getName() + "\" foi apagado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível apagar o arquivo. " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        deleteFile();
    }
}
