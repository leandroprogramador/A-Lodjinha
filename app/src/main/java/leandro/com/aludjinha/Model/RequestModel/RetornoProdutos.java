package leandro.com.aludjinha.Model.RequestModel;

import java.util.List;

import leandro.com.aludjinha.Model.Produto;

public class RetornoProdutos {

    List<Produto> data;

    public List<Produto> getData() {
        return data;
    }

    public void setData(List<Produto> data) {
        this.data = data;
    }
}
