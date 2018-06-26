package leandro.com.aludjinha.Activities;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import leandro.com.aludjinha.Helpers.ConstantesHelper;
import leandro.com.aludjinha.Helpers.FormatHelper;
import leandro.com.aludjinha.Model.Produto;
import leandro.com.aludjinha.Model.RequestModel.ReservaProduto;
import leandro.com.aludjinha.Model.RequestModel.RetornoReserva;
import leandro.com.aludjinha.R;
import leandro.com.aludjinha.Service.JsonRequest;
import leandro.com.aludjinha.Service.PicassoImageLoadingService;

public class DetalhesProduto extends AppCompatActivity implements JsonRequest.PostCommentResponseListener {

    ImageView imgProduto;
    TextView txtTitulo, txtPrecoDe, txtPrecoPor, txtDescricao;
    ProgressBar progressBar;
    Gson gson = new Gson();
    Toolbar toolbar;
    FloatingActionButton floatReservar;
    int idProduto = 0;
    String url = "";
    JSONObject jsonObject = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);
        idProduto = getIntent().getIntExtra(getString(R.string.id), 0);
        initViews();
        url = ConstantesHelper.BASE_URL + String.format(ConstantesHelper.PRODUTO, idProduto);

        new Thread(() -> JsonRequest.jsonObjectRequest(DetalhesProduto.this, Request.Method.GET, url, null, null, DetalhesProduto.this)).start();


    }

    @SuppressLint("RestrictedApi")
    private void initViews() {
        imgProduto = findViewById(R.id.imgProduto);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtPrecoDe = findViewById(R.id.txt_preco_de);
        txtPrecoPor = findViewById(R.id.txt_preco_por);
        txtDescricao = findViewById(R.id.txtDescricao);
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        floatReservar = findViewById(R.id.floatingActionButton);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        txtPrecoDe.setPaintFlags(txtPrecoDe.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        floatReservar.setOnClickListener(view -> reservar());
    }

    private void reservar() {
        progressBar.setVisibility(View.VISIBLE);
        ReservaProduto reservaProduto = new ReservaProduto(idProduto);

        try {
             jsonObject = new JSONObject(gson.toJson(reservaProduto));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(() -> JsonRequest.jsonObjectRequest(DetalhesProduto.this, Request.Method.POST, url, jsonObject, null, DetalhesProduto.this)).start();



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void requestCompleted(String json, String request, int method) {
        try {
            if(method == Request.Method.GET) {
                Produto produto = gson.fromJson(json, Produto.class);
                txtTitulo.setText(produto.getNome());
                txtPrecoDe.setText("De: " + FormatHelper.formatMoney(produto.getPrecoDe()));
                txtPrecoPor.setText("De: " + FormatHelper.formatMoney(produto.getPrecoPor()));
                txtDescricao.setText(Html.fromHtml(produto.getDescricao()));
                PicassoImageLoadingService.loadImage(produto.getUrlImagem(), imgProduto);
            } else{
                RetornoReserva retornoReserva = gson.fromJson(json, RetornoReserva.class);
                if(retornoReserva.getResult().equals("success")){
                    openDialog();
                } else{
                    Toast.makeText(this, retornoReserva.getMensagem(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex){
            Toast.makeText(this, "Não foi possível recuperar todos os dados deste produto. :(", Toast.LENGTH_SHORT).show();
        }


        runOnUiThread(() -> progressBar.setVisibility(View.INVISIBLE));

    }

    private void openDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Produto reservado com sucesso!");
        alertBuilder.setPositiveButton("OK", null);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    @Override
    public void requestError(String error, String request) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        runOnUiThread(() -> progressBar.setVisibility(View.INVISIBLE));
    }
}
