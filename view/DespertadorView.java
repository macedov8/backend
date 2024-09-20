package view;

import java.util.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import controller.*;

public class DespertadorView {
    protected static final String RESET = "\033[0m";
    private static final String RED = "\033[31m";
    protected static final String GREEN = "\033[32m";

    public static int horaDespertar;
    public static int minutoDespertar;
    public static int segundoDespertar;
    public static int limiteAdiamentos = 3; 
    public static int contadorAdiamentos = 0; 

    public static int horaAtual;
    public static int minutoAtual;
    public static int segundoAtual;
    public static String mensagemPersonalizada; 

    public static void main(String[] args) {
        boolean sair = false;
        Scanner scnDespertador = new Scanner(System.in);
        DespertadorController.getHMS();
        System.out.println(String.format(GREEN + "Agora são: %02d:%02d:%02d." + RESET, horaAtual, minutoAtual, segundoAtual));
        configurarNovoAlarme(scnDespertador);

        while (!sair) {
            DespertadorController.getHMS();
            System.out.println(String.format(GREEN + "Agora são: %02d:%02d:%02d." + RESET + " O próximo alarme irá despertar às " + RED + "%02d:%02d:%02d" + RESET, 
                horaAtual, minutoAtual, segundoAtual, horaDespertar, minutoDespertar, segundoDespertar));

            int tempoRestanteSegundos = calcularTempoRestante();
            int horasRestantes = tempoRestanteSegundos / 3600;
            int minutosRestantes = (tempoRestanteSegundos % 3600) / 60;
            int segundosRestantes = tempoRestanteSegundos % 60;

            System.out.printf("Faltam: %02d horas, %02d minutos e %02d segundos para o alarme.\n", horasRestantes, minutosRestantes, segundosRestantes);

            if (horaAtual >= horaDespertar && minutoAtual >= minutoDespertar && segundoAtual >= segundoDespertar) {
                tocarAlarme(scnDespertador);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.print("\033[H\033[2J");  
            System.out.flush();
        }

        scnDespertador.close();
    }

    public static void configurarNovoAlarme(Scanner scnConfigurar) {
        horaDespertar = lerEntradaInt(scnConfigurar, "Digite a hora (0-23) e tecle Enter:");
        minutoDespertar = lerEntradaInt(scnConfigurar, "Digite o minuto (0-59) e tecle Enter:");
        segundoDespertar = lerEntradaInt(scnConfigurar, "Digite o segundo (0-59) e tecle Enter:");
        System.out.println("Digite a mensagem personalizada e tecle Enter:");
        mensagemPersonalizada = scnConfigurar.nextLine();
        contadorAdiamentos = 0;
        System.out.printf(GREEN + "Novo alarme configurado para %02d:%02d:%02d com a mensagem: '%s'" + RESET + "\n", horaDespertar, minutoDespertar, segundoDespertar, mensagemPersonalizada);
    }

    private static int lerEntradaInt(Scanner scanner, String mensagem) {
        while (true) {
            System.out.println(mensagem);
            try {
                int entrada = Integer.parseInt(scanner.nextLine());
                if (entrada >= 0) return entrada;
                System.out.println("Entrada inválida. Por favor, insira um número positivo.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
            }
        }
    }

    private static void tocarAlarme(Scanner scnDespertador) {
        tocarSomAlarme();  // Toca o som do alarme
        String mensagemAlarme = obterMensagemAlarme();
        System.out.println(GREEN + "ALARME TOCANDO! " + mensagemAlarme + RESET);
        for (int i = 0; i < 10; i++) {
            System.out.print("\r" + (i % 2 == 0 ? RED + mensagemPersonalizada + RESET : "\033[33m" + mensagemPersonalizada + RESET));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(); 
        mostrarMenu(scnDespertador);
    }

    private static void tocarSomAlarme() {
        try {
            File soundFile = new File("wav/alarme.wav"); // Caminho atualizado
            System.out.println("Tentando carregar o arquivo: " + soundFile.getAbsolutePath()); // Mensagem de debug
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            // Para loop contínuo, você pode usar clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Erro ao tocar o som do alarme: " + e.getMessage());
        }
    }

    private static String obterMensagemAlarme() {
        if (horaAtual < 12) {
            return "Acorda, seu dorminhoco!";
        } else if (horaAtual < 18) {
            return "Olha quem resolveu sair da toca!";
        } else {
            return "Eita! Essas horas? Bora aproveitar a noite!";
        }
    }

    private static void mostrarMenu(Scanner scnDespertador) {
        System.out.println(GREEN + "Menu:" + RESET);
        String[] opcoes = DespertadorController.verOpcoes();
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println(String.format("[%d] >> %s", i + 1, opcoes[i]));
        }

        int respostaUsuario = lerEntradaInt(scnDespertador, "Escolha uma opção:");
        String resposta = DespertadorController.acaoDespertador(respostaUsuario, scnDespertador);
        System.out.println(resposta);
    }
    
    public static void encerrarPrograma() {
        String mensagemSaida = obterMensagemSaida();
        System.out.println(GREEN + mensagemSaida + RESET);
        System.exit(0);
    }

    private static String obterMensagemSaida() {
        if (horaAtual < 12) {
            return "Alarme encerrado. Tenha um bom dia!";
        } else if (horaAtual < 18) {
            return "Alarme encerrado. Aproveite sua tarde!";
        } else {
            return "Alarme encerrado. Tenha uma boa noite!";
        }
    }

    private static int calcularTempoRestante() {
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, horaDespertar);
        alarmTime.set(Calendar.MINUTE, minutoDespertar);
        alarmTime.set(Calendar.SECOND, segundoDespertar);

        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.HOUR_OF_DAY, horaAtual);
        currentTime.set(Calendar.MINUTE, minutoAtual);
        currentTime.set(Calendar.SECOND, segundoAtual);

        long tempoRestante = alarmTime.getTimeInMillis() - currentTime.getTimeInMillis();
        
        return tempoRestante > 0 ? (int) (tempoRestante / 1000) : 0;
    }
}

