package leandro.com.aludjinha.Model.RequestModel;

import java.util.List;

import leandro.com.aludjinha.Model.Categorias;

public class RetornoCategorias {
    List<Categorias> data;

    public List<Categorias> getData() {
        return data;
    }

    public void setData(List<Categorias> data) {
        this.data = data;
    }
}
