package leandro.com.aludjinha;

import android.app.Application;

import leandro.com.aludjinha.Service.PicassoImageLoadingService;
import ss.com.bannerslider.Slider;

public class ALodjinhaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Slider.init(new PicassoImageLoadingService());
    }
}
