package leandro.com.aludjinha.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import leandro.com.aludjinha.Model.Categorias;
import leandro.com.aludjinha.R;
import leandro.com.aludjinha.Service.PicassoImageLoadingService;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriasViewHolder>{

    List<Categorias> mDataset;
    ICategoriasEvent mCategoriasEvent;

    public CategoriasAdapter(List<Categorias> mDataset, ICategoriasEvent mCategoriasEvent) {
        this.mDataset = mDataset;
        this.mCategoriasEvent = mCategoriasEvent;
    }


    @NonNull
    @Override
    public CategoriasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorias_row, parent, false);
        return new CategoriasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriasViewHolder holder, int position) {
        holder.txtCategoria.setText(mDataset.get(position).getDescricao());
        PicassoImageLoadingService.loadImage(mDataset.get(position).getUrlImagem(), holder.imgCategoira);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    class CategoriasViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCategoira;
        TextView txtCategoria;
        public CategoriasViewHolder(View itemView) {
            super(itemView);
            imgCategoira = itemView.findViewById(R.id.img_categoria);
            txtCategoria = itemView.findViewById(R.id.txt_categoria);

            itemView.setOnClickListener(view -> mCategoriasEvent.onCategoriaClick(mDataset.get(getLayoutPosition())));
        }
    }

    public interface ICategoriasEvent{
        void onCategoriaClick(Categorias categorias);
    }
}
