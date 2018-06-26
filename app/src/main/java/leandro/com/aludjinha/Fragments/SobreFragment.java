package leandro.com.aludjinha.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import leandro.com.aludjinha.Helpers.ViewHelper;
import leandro.com.aludjinha.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SobreFragment extends Fragment {


    TextView txtLogo;
    public SobreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sobre, container, false);
        txtLogo = view.findViewById(R.id.txt_logo);
        ViewHelper.setFontPacifico(getActivity(), txtLogo);
        return view;
    }

}
