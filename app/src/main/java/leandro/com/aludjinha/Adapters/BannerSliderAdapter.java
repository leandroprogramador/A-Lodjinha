package leandro.com.aludjinha.Adapters;

import java.util.List;

import leandro.com.aludjinha.Model.Banner;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class BannerSliderAdapter  extends SliderAdapter{
    List<Banner> mDataset;

    public BannerSliderAdapter(List<Banner> mDataset) {
        this.mDataset = mDataset;
    }

    public Banner getBanner(int posiiton){
        return mDataset.get(posiiton);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(getBanner(position).getUrlImagem());
    }
}
