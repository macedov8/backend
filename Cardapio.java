import java.util.Scanner;

public class Cardapio {
    private static final String XBURGUER = "Xburguer";
    private static final String XBACON = "XBacon";
    private static final String XTUDO = "XTudo";

    public static void main(String[] args) {
    Scanner scnInput = new Scanner(System.in);
    String pedido = "";
    boolean continuar = true;

    System.out.println("Bem vindo à Perfect Burguer!");

    while (continuar) {
    System.out.println("Digite o número da opção desejada e tecle Enter:");
    System.out.println("1 - " + XBURGUER);
    System.out.println("2 - " + XBACON);
    System.out.println("3 - " + XTUDO);

    int escolha = scnInput.nextInt();
    scnInput.nextLine();

    switch (escolha) {
        case 1:
        pedido = XBURGUER;
        System.out.println("Você escolheu o " + XBURGUER + ". Adicione R$5 e troque por um Xbacon!");
        break;
        case 2:
        pedido = XBACON;
        System.out.println("Você escolheu o " + XBACON + ". Adicione R$5 e troque por um Xtudo!");
        break;
        case 3:
        pedido = XTUDO;
        System.out.println("Você escolheu o " + XTUDO + ". Esse é o top do momento! Agradecemos seu pedido!");
        break;
        default:
        System.out.println("Eita, esse lanche não temos! Escolha um número entre 1 e 3.");
        continue; 
            }

            
        boolean modificar = true;
            while (modificar) {
            System.out.println("Seu pedido atual é: " + pedido);
            System.out.println("Deseja alterar (A) ou confirmar (C) seu pedido? (Digite A ou C)");

            String opcao = scnInput.nextLine().trim().toUpperCase();

            if (opcao.equals("A")) {
                System.out.println("Digite o número da nova opção desejada e tecle Enter:");
                System.out.println("1 - " + XBURGUER);
                System.out.println("2 - " + XBACON);
                System.out.println("3 - " + XTUDO);

                escolha = scnInput.nextInt();
                scnInput.nextLine(); 

                switch (escolha) {
                case 1:
                    pedido = XBURGUER;
                            System.out.println("Você escolheu o " + XBURGUER + ". Adicione R$5 e troque por um Xbacon!");
                            break;
                        case 2:
                            pedido = XBACON;
                            System.out.println("Você escolheu o " + XBACON + ". Adicione R$5 e troque por um Xtudo!");
                            break;
                        case 3:
                            pedido = XTUDO;
                            System.out.println("Você escolheu o " + XTUDO + ". Esse é o top do momento! Agradecemos seu pedido!");
                            break;
                        default:
                            System.out.println("Eita, esse lanche não temos! Escolha um número entre 1 e 3.");
                            continue;
                    }
                } else if (opcao.equals("C")) {
                    System.out.println("Pedido confirmado: " + pedido + ". A Perfect Burguer agradece o seu pedido!");
                    modificar = false;
                    continuar = false;
                } else {
                    System.out.println("Opção inválida! Digite A para alterar ou C para confirmar.");
                }
            }
        }

        scnInput.close();
    }
}
