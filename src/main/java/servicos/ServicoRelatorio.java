package servicos;
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import modelo.Vendas;

import java.util.ArrayList;

public class ServicoRelatorio {
    public void gerarRelatorioPdf(ArrayList<Vendas> listaVendas) throws Exception{
        if(listaVendas.isEmpty()){
            return;
        }
        Document doc = new Document();
        Page page = doc.getPages().add();

        for (Vendas venda : listaVendas) {
            if (!venda.getCliente().isEmpty()) {
                TextFragment textFragment = new TextFragment(
                        "Cliente: " + venda.getCliente() + "\n" +
                                "Telefone: " + venda.getTelefone() + "\n" +
                                "Email: " + venda.getEmail() + "\n" +
                                "Produto: " + venda.getProduto() + "\n" +
                                "Categoria: " + venda.getCategoria() + "\n" +
                                "Valor do Produto: " + venda.getPrecoUnitario() + "\n" +
                                "Quantidade: " + venda.getQuantidade() + "\n" +
                                "Data da Compra: " + venda.getData() + "\n" +
                                "Valor total: " + venda.getValorFinal() + "\n"
                );
                page.getParagraphs().add(textFragment);
            }
        }
        doc.save("Relatório.pdf");

    }

}

