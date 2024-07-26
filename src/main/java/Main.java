import dao.DaoCliente;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("Hello world!");
        DaoCliente dc = new DaoCliente();

        do{
            System.out.println("1- Listar Clientes");
            System.out.println("2- Inserir Clientes");
            System.out.println("3- Editar Clientes");
            System.out.println("4- Excluir Clientes");
            System.out.println("5- Receber histórico de compras");
            int opcao = read.nextInt();
            switch(opcao){
                case 1:
                    dc.listarClientes();
                    break;
                case 2:
                    dc.inserirCliente();
                    break;
                case 3:
                    dc.editarClientes();
                    break;
                case 4:
                    dc.excluirClientes();
                    break;
                case 5:
                    dc.enviarRelatorio();
                    break;
                default:
                    System.out.println("Encerramos");
            }
        }while (true);
    }
}