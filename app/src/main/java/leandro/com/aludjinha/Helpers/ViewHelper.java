package leandro.com.aludjinha.Helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class ViewHelper {

    public static void setFontPacifico(Context context, TextView textView){
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "pacifico.ttf");
        textView.setTypeface(tf);

    }
}
