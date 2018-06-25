package leandro.com.aludjinha.Service;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import leandro.com.aludjinha.R;
import ss.com.bannerslider.ImageLoadingService;

public class PicassoImageLoadingService  {

    public static void loadImage(String url, ImageView imageView) {
        Picasso.get().load(url).placeholder(R.drawable.image_loading).into(imageView);
    }


}
