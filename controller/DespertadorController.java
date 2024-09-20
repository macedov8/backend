package controller;

import model.*;
import view.*;
import java.util.*;

public class DespertadorController extends DespertadorView {
    public static String[] verOpcoes() {
        return DespertadorModel.mostrarOpcoes();
    }

    public static String acaoDespertador(int opcaoUsuario, Scanner scnDespertador) {
        String resposta = "";

        switch (opcaoUsuario) {
            case 1:
                System.out.println("Digite o número de segundos para adiar o alarme:");
                int segundosAdiar = lerEntradaInt(scnDespertador);
                resposta = adiarAlarme(segundosAdiar);
                break;

            case 2:
                System.out.println("Deseja configurar um novo alarme? (s/n):");
                String respostaNovoAlarme = scnDespertador.nextLine();
                if (respostaNovoAlarme.equalsIgnoreCase("s")) {
                    configurarNovoAlarme(scnDespertador);
                } else {
                    encerrarPrograma();
                }
                break;

            default:
                resposta = "Opção inválida.";
                break;
        }

        return resposta;
    }

    private static String adiarAlarme(int segundosAdiar) {
        if (contadorAdiamentos >= limiteAdiamentos) {
            return "Ops, você atingiu o limite de adiamentos. Você não pode adiar o alarme mais.";
        }

        if (segundosAdiar <= 0) {
            return "Número de segundos deve ser positivo.";
        }

        int minutosAdiar = segundosAdiar / 60;  
        int segundosRestantes = segundosAdiar % 60;  

        minutoDespertar += minutosAdiar;
        segundoDespertar += segundosRestantes;

        if (segundoDespertar >= 60) {
            minutoDespertar += segundoDespertar / 60;
            segundoDespertar %= 60;
        }
        if (minutoDespertar >= 60) {
            horaDespertar += minutoDespertar / 60;
            minutoDespertar %= 60;
        }

        contadorAdiamentos++;
        return String.format("Ok! Alarme adiado em: %02d minutos e %02d segundos. Você ainda tem %d adiamentos disponíveis.", minutosAdiar, segundosRestantes, limiteAdiamentos - contadorAdiamentos);
    }

    public static void getHMS() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        horaAtual = calendar.get(Calendar.HOUR_OF_DAY);
        minutoAtual = calendar.get(Calendar.MINUTE);
        segundoAtual = calendar.get(Calendar.SECOND);
    }

    private static int lerEntradaInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
            }
        }
    }
}
