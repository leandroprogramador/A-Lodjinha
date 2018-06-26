package leandro.com.aludjinha.Model.RequestModel;

public class ReservaProduto {
        int produtoId;

    public ReservaProduto(int produtoId) {
        this.produtoId = produtoId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }
}
