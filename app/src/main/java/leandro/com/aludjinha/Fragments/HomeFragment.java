package leandro.com.aludjinha.Fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;

import leandro.com.aludjinha.Adapters.BannerSliderAdapter;
import leandro.com.aludjinha.Adapters.CategoriasAdapter;
import leandro.com.aludjinha.Helpers.ConstantesHelper;
import leandro.com.aludjinha.Model.Banner;
import leandro.com.aludjinha.Model.Categorias;
import leandro.com.aludjinha.Model.RequestModel.RetornoBanner;
import leandro.com.aludjinha.Model.RequestModel.RetornoCategorias;
import leandro.com.aludjinha.R;
import leandro.com.aludjinha.Service.JsonRequest;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements JsonRequest.PostCommentResponseListener, BannerSliderAdapter.IBannerEvent, CategoriasAdapter.ICategoriasEvent {


    Activity mActivity;
    @SuppressLint("ValidFragment")
    public HomeFragment(Activity activity) {
        mActivity = activity;
    }

    Gson gson = new Gson();
    ProgressBar progressBar;
    BannerSliderAdapter bannerSliderAdapter;
    CategoriasAdapter categoriasAdapter;
    ViewPager viewPager;
    TextView txtIndicator, txtCategoriasNull;
    RecyclerView recyclerViewCategorias;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        getViews(view);

        recyclerViewCategorias = view.findViewById(R.id.recyclerCategorias);
        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(bannerSliderAdapter.getCount() > 0){
                    txtIndicator.setText(setTextIndicator(bannerSliderAdapter.getCount(), position));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        String urlBanner = ConstantesHelper.BASE_URL + ConstantesHelper.BANNER;
        makeRequest(urlBanner);
        return view;
    }

    private void getViews(View view) {
        progressBar = view.findViewById(R.id.progressBar);
        viewPager = view.findViewById(R.id.banner_slider);
        txtIndicator = view.findViewById(R.id.indicator);
        txtCategoriasNull = view.findViewById(R.id.txtCategoriasNull);
    }

    private void makeRequest(String url) {
         JsonRequest.jsonObjectRequest(mActivity, Request.Method.GET, url, null, null, HomeFragment.this);
    }


    @Override
    public void requestCompleted(String json, String request) {
        if(request.contains(ConstantesHelper.BANNER)){
            fillBanner(json);
            requestCategorias();
        }

        if(request.contains(ConstantesHelper.CATEGORIA)){
            fillCategories(json);
        }
        mActivity.runOnUiThread(() -> progressBar.setVisibility(View.INVISIBLE));
    }

    private void requestCategorias() {
        String urlCategorias = ConstantesHelper.BASE_URL + ConstantesHelper.CATEGORIA;
        makeRequest(urlCategorias);
    }

    private void fillCategories(String json) {
        try {
            RetornoCategorias retornoCategorias = gson.fromJson(json, RetornoCategorias.class);
            categoriasAdapter = new CategoriasAdapter(retornoCategorias.getData(), HomeFragment.this);
            if(categoriasAdapter.getItemCount() > 0){
                recyclerViewCategorias.setAdapter(categoriasAdapter);
            } else{
                throw new Exception();
            }
        } catch (Exception ex){
            recyclerViewCategorias.setVisibility(View.GONE);
            txtCategoriasNull.setVisibility(View.VISIBLE);
        }
    }

    private void fillBanner(String json) {
        try{
            RetornoBanner retornoBanner = gson.fromJson(json, RetornoBanner.class);
            bannerSliderAdapter = new BannerSliderAdapter(retornoBanner.getData(), mActivity, HomeFragment.this);
            viewPager.setAdapter(bannerSliderAdapter);
            if(bannerSliderAdapter.getCount() > 0){
                txtIndicator.setText(setTextIndicator(bannerSliderAdapter.getCount(), 0));
            }
        } catch (Exception ex){
        }
    }

    @Override
    public void requestError(String error, String request) {
        Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show();
        mActivity.runOnUiThread(() -> progressBar.setVisibility(View.INVISIBLE));
    }

    @Override
    public void onBannerClick(Banner banner) {
        String url = banner.getLinkUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mActivity.startActivity(intent);
    }

    private String setTextIndicator(int count, int position){
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i <count; i++){
            if(position==i){
                stringBuilder.append(getString(R.string.indicator_full));
            } else {
                stringBuilder.append(getString(R.string.indicator_empty));
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public void onCategoriaClick(Categorias categorias) {

    }
}
