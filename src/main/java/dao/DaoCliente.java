package dao;

import modelo.Cliente;
import modelo.Vendas;
import servicos.ServicoEmail;
import servicos.ServicoRelatorio;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class DaoCliente {
    Scanner read = new Scanner(System.in);
    Cliente cliente = new Cliente();
    public void excluirClientes(){
        System.out.println("Informe o ID do Cliente: ");
        int id = read.nextInt();
        String nome = "";
        try{
            Connection con = BaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT Nome_Cliente FROM Cliente WHERE ID_Cliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nome = rs.getString("Nome_Cliente");
            }

            if(nome != null){
                PreparedStatement pst = con.prepareStatement("DELETE FROM Cliente WHERE ID_Cliente = ?");
                pst.setInt(1, id);
                int exitCode = pst.executeUpdate();
                if(exitCode > 0){
                    System.out.println("Cliente " + nome + "Excluído com Sucesso!");
                }
            }else{
                System.out.println("Cliente não encontrado!");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void listarClientes() {
        try{
            Connection con = BaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Cliente");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("ID_Cliente"));
                System.out.println("Nome: " + rs.getString("Nome_Cliente"));
                System.out.println("Telefone: " + rs.getString("Telefone"));
                System.out.println("Email: " + rs.getString("Email"));
                System.out.println("Data: " + rs.getString("Data_Nascimento"));
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void inserirCliente() {
        System.out.println("Informe o nome do cliente: ");
        String nome = read.nextLine();
        if (nome.length() > 3 && !nome.isBlank()) {
            cliente.setNome(nome);
        } else {
            System.out.println("Nome precisa ter mais de 3 caracteres! E não pode ser vazio.");
            return;
        }

        System.out.println("Informe seu telefone: ");
        String telefone = read.nextLine();
        if (telefoneValido(telefone)) {
            cliente.setTelefone(telefone);
        } else {
            System.out.println("Telefone inválido, preencha novamente!");
            return;
        }

        System.out.println("Informe seu email: ");
        String email = read.nextLine();
        if (emailValido(email)) {
            cliente.setEmail(email);
        } else {
            System.out.println("Email inválido, preencha novamente!");
            return;
        }

        System.out.println("Informe sua data de nascimento (yyyy-MM-dd): ");
        String dataNascStr = read.nextLine();
        java.sql.Date dataNasc = dataNascimento(dataNascStr);
        if (dataNasc != null) {
            cliente.setDataNascimento(dataNasc);
        } else {
            System.out.println("Data de nascimento inválida, preencha novamente!");
            return;
        }

        try {
            Connection con = BaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO Cliente " +
                    "(Nome_Cliente, Telefone, Email, Data_Nascimento) VALUES (?, ?, ?, ?)");
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getEmail());
            ps.setDate(4, cliente.getDataNascimento());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarClientes() {
        System.out.println("Informe o ID do Cliente: ");
        int id = read.nextInt();
        read.nextLine();
        try {
            Connection con = BaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Cliente WHERE ID_Cliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("Nome_Cliente");
                String telefone = rs.getString("Telefone");
                String email = rs.getString("Email");
                java.sql.Date dataNasc = rs.getDate("Data_Nascimento");
                System.out.println("Nome do Cliente: " + nome);
                System.out.println("Telefone do Cliente: " + telefone);
                System.out.println("Email do Cliente: " + email);
                System.out.println("Data de Nascimento do Cliente: " + dataNasc);
                System.out.println();

                System.out.println("Qual campo deseja alterar? ");
                System.out.println("1- Nome do Cliente");
                System.out.println("2- Telefone do Cliente");
                System.out.println("3- Email do Cliente");
                System.out.println("4- Data de nascimento do Cliente");
                System.out.println("5- Cancelar Edição");
                int opcao = read.nextInt();
                read.nextLine();
                String campo = null;
                String valor = null;
                java.sql.Date dataNascSql = null;
                switch (opcao) {
                    case 1:
                        System.out.println("Informe o nome do Cliente");
                        valor = read.nextLine();
                        if (valor.length() > 3 && !valor.isBlank()) {
                            cliente.setNome(valor);
                        } else {
                            System.out.println("Nome precisa ter mais de 3 caracteres! E não pode ser vazio.");
                            return;
                        }
                        campo = "Nome_Cliente";
                        break;
                    case 2:
                        System.out.println("Informe o telefone do Cliente");
                        valor = read.nextLine();
                        if (telefoneValido(valor)) {
                            cliente.setTelefone(valor);
                        } else {
                            System.out.println("Telefone inválido, preencha novamente!");
                            return;
                        }
                        campo = "Telefone";
                        break;
                    case 3:
                        System.out.println("Informe o email do Cliente");
                        valor = read.nextLine();
                        if (emailValido(valor)) {
                            cliente.setEmail(valor);
                        } else {
                            System.out.println("Email inválido, preencha novamente!");
                            return;
                        }
                        campo = "Email";
                        break;
                    case 4:
                        System.out.println("Informe data de nascimento do Cliente (yyyy-MM-dd)");
                        String dataNascStr = read.nextLine();
                        dataNascSql = dataNascimento(dataNascStr);
                        if (dataNascSql != null) {
                            cliente.setDataNascimento(dataNascSql);
                        } else {
                            System.out.println("Data de nascimento inválida, preencha novamente!");
                            return;
                        }
                        campo = "Data_Nascimento";
                        break;
                    default:
                        System.out.println("Saindo do modo edição");
                        return;
                }
                String atualizarSql = "UPDATE Cliente SET " + campo + " = ? WHERE ID_Cliente = ?";
                PreparedStatement pst = con.prepareStatement(atualizarSql);
                if (campo.equals("Data_Nascimento")) {
                    pst.setDate(1, cliente.getDataNascimento());
                } else {
                    pst.setString(1, valor);
                }
                pst.setInt(2, id);
                int exitCode = pst.executeUpdate();
                if (exitCode > 0) {
                    System.out.println("Cliente atualizado com sucesso!");
                } else {
                    System.out.println("Erro ao atualizar o cliente.");
                }
            } else {
                System.out.println("Cliente não encontrado!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarRelatorio() {
        System.out.println("Informe seu email para enviar o relatório: ");
        String email = read.nextLine();
        if(emailValido(email)) {
            try (Connection con = BaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                         "SELECT Nome_Cliente, Telefone, Email, Nome_Produto, Tipo_Produto," +
                                 " Valor_Produto, Quantidade, Data_Venda, Valor_Final " +
                                 "FROM Venda " +
                                 "JOIN Cliente ON Venda.ID_Cliente = Cliente.ID_Cliente " +
                                 "JOIN Produto ON Venda.ID_Produto = Produto.ID_Produto " +
                                 "WHERE Cliente.Email = ?")) {

                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    ArrayList<Vendas> listaVendas = new ArrayList<>();

                    while (rs.next()) {
                        Vendas vendas = new Vendas();
                        vendas.setCliente(rs.getString("Nome_Cliente"));
                        vendas.setTelefone(rs.getString("Telefone"));
                        vendas.setEmail(rs.getString("Email"));
                        vendas.setProduto(rs.getString("Nome_Produto"));
                        vendas.setCategoria(rs.getString("Tipo_Produto"));
                        vendas.setPrecoUnitario(rs.getDouble("Valor_Produto"));
                        vendas.setQuantidade(rs.getInt("Quantidade"));
                        vendas.setData(rs.getDate("Data_Venda"));
                        vendas.setValorFinal(rs.getDouble("Valor_Final"));

                        listaVendas.add(vendas);
                    }

                    // verifica se a lista nao esta vazia
                    if (!listaVendas.isEmpty()) {
                        ServicoRelatorio sr = new ServicoRelatorio();
                        sr.gerarRelatorioPdf(listaVendas);
                    } else {
                        System.out.println("Nenhuma venda encontrada para o email fornecido.");
                    }

                    ServicoEmail se = new ServicoEmail();
                    se.servicoEmail(email);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    private boolean emailValido(String email) {
        return email.contains("@") && email.contains(".")
                && !email.isBlank();
    }

    private boolean telefoneValido(String telefone) {
        return !telefone.isBlank()
                && telefone.length() == 11;
    }
    private java.sql.Date dataNascimento(String dataNascimento) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = formatter.parse(dataNascimento);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }



}
