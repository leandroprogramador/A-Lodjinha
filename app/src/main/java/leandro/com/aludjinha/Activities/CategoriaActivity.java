package leandro.com.aludjinha.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;

import leandro.com.aludjinha.Adapters.ProdutoAdapter;
import leandro.com.aludjinha.Helpers.ConstantesHelper;
import leandro.com.aludjinha.Model.Produto;
import leandro.com.aludjinha.Model.RequestModel.RetornoProdutos;
import leandro.com.aludjinha.R;
import leandro.com.aludjinha.Service.JsonRequest;

public class CategoriaActivity extends AppCompatActivity implements JsonRequest.PostCommentResponseListener, ProdutoAdapter.IProdutoClick {

    RecyclerView recyclerView;
    Toolbar toolbar;
    LinearLayoutManager linearLayoutManager;
    ProdutoAdapter produtoAdapter;
    ProgressBar progressBar;
    int limit =20;
    int offset = 0;
    int page = 1;
    int categoriaId;
    boolean scroll = false;
    Gson gson = new Gson();
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        initViews();

        getProdutos();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @SuppressLint("RestrictedApi")
    private void initViews() {
        recyclerView = findViewById(R.id.recycler_produtos);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);

        configureToolbar();

        categoriaId = getIntent().getIntExtra(getString(R.string.id), 0);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scroll = true;

                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(offset == lastVisiblePosition + 1){
                    getProdutos();
                    page++;
                    offset =limit * page;

                }
            }
        });
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(getString(R.string.categoria_name)));
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getProdutos() {
        progressBar.setVisibility(View.VISIBLE);
        String url = ConstantesHelper.BASE_URL + String.format(ConstantesHelper.PRODUTO_CATEGORIA, categoriaId, limit, offset);
        new Thread(() -> JsonRequest.jsonObjectRequest(CategoriaActivity.this, Request.Method.GET, url, null, null, CategoriaActivity.this)).start();
    }

    @Override
    public void requestCompleted(String json, String request, @Nullable int method) {
       try{
           RetornoProdutos retornoProdutos = gson.fromJson(json, RetornoProdutos.class);
           if(retornoProdutos.getData().size() > 0){
               if(!scroll){
                   produtoAdapter = new ProdutoAdapter(retornoProdutos.getData(), CategoriaActivity.this);
                   recyclerView.setAdapter(produtoAdapter);
                   offset += limit;
               } else{
                   for (Produto produto : retornoProdutos.getData()){
                       produtoAdapter.add(produto);
                   }
               }
           }
       } catch (Exception ex){

       }

        runOnUiThread(() -> progressBar.setVisibility(View.INVISIBLE));
    }

    @Override
    public void requestError(String error, String request) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        runOnUiThread(() -> progressBar.setVisibility(View.INVISIBLE));
    }

    @Override
    public void onProdutoClick(Produto produto) {
        int id = produto.getId();
        Intent intent = new Intent(this, DetalhesProduto.class);
        intent.putExtra(getString(R.string.id), id);
        startActivity(intent);
    }
}
