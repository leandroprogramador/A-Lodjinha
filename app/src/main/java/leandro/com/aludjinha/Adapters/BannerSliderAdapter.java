package leandro.com.aludjinha.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import leandro.com.aludjinha.Model.Banner;
import leandro.com.aludjinha.R;
import leandro.com.aludjinha.Service.PicassoImageLoadingService;

public class BannerSliderAdapter extends PagerAdapter {
    List<Banner> mDataset;
    Context mContext;
    IBannerEvent mBannerEvent;


    public BannerSliderAdapter(List<Banner> mDataset, Context context, IBannerEvent bannerEvent) {
        this.mDataset = mDataset;
        this.mContext = context;
        this.mBannerEvent = bannerEvent;
    }

    public Banner getBanner(int posiiton) {
        return mDataset.get(posiiton);
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.banner_row, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageView);
        PicassoImageLoadingService.loadImage(getBanner(position).getUrlImagem(), imageView);
        imageView.setOnClickListener(view -> mBannerEvent.onBannerClick(getBanner(position)));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface IBannerEvent {
        void onBannerClick(Banner banner);
    }
}
