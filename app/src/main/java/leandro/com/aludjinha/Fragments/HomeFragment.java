package leandro.com.aludjinha.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;

import leandro.com.aludjinha.Adapters.BannerSliderAdapter;
import leandro.com.aludjinha.Helpers.ConstantesHelper;
import leandro.com.aludjinha.Model.RequestModel.RetornoBanner;
import leandro.com.aludjinha.R;
import leandro.com.aludjinha.Service.JsonRequest;
import ss.com.bannerslider.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements JsonRequest.PostCommentResponseListener {


    public HomeFragment() {
        // Required empty public constructor
    }

    Slider slider;
    BannerSliderAdapter bannerSliderAdapter;
    Gson gson = new Gson();
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        slider = view.findViewById(R.id.banner_slider);
        progressBar = view.findViewById(R.id.progressBar);
        String url = ConstantesHelper.BASE_URL + ConstantesHelper.BANNER;
        new Thread(() -> JsonRequest.jsonObjectRequest(getActivity(), Request.Method.GET, url, null, null, HomeFragment.this)).start();

        return view;
    }

    @Override
    public void requestCompleted(String json, String request) {
        if(request.contains(ConstantesHelper.BANNER)){
            try{
                RetornoBanner retornoBanner = gson.fromJson(json, RetornoBanner.class);
                bannerSliderAdapter = new BannerSliderAdapter(retornoBanner.getData());
                slider.setAdapter(bannerSliderAdapter);

            } catch (Exception ex){

            }
        }

        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.INVISIBLE));
    }

    @Override
    public void requestError(String error, String request) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.INVISIBLE));
    }
}
