package leandro.com.aludjinha.Helpers;

public class ConstantesHelper {

    public static final String BASE_URL = "https://alodjinha.herokuapp.com/";
    public static final String BANNER = "banner";
    public static final String CATEGORIA = "categoria";
    public static final String PRODUTO_BASE = "produto";
    public static final String PRODUTOS = PRODUTO_BASE + "?limit=%d&offset=%d";
    public static final String PRODUTOS_MAIS_VENDIDOS = PRODUTO_BASE + "/maisvendidos";
    public static final String PRODUTO = PRODUTO_BASE +"/%d";
    public static final String PRODUTO_CATEGORIA = PRODUTO_BASE + "?categoriaId=%d&limit=%d&offset=%d";


}
