package leandro.com.aludjinha.Adapters;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import leandro.com.aludjinha.Helpers.FormatHelper;
import leandro.com.aludjinha.Model.Produto;
import leandro.com.aludjinha.R;
import leandro.com.aludjinha.Service.PicassoImageLoadingService;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutosViewHolder>{
    List<Produto> mDataset;
    IProdutoClick mProdutoClick;

    public ProdutoAdapter(List<Produto> mDataset, IProdutoClick mProdutoClick) {
        this.mDataset = mDataset;
        this.mProdutoClick = mProdutoClick;
    }

    @NonNull
    @Override
    public ProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produtos_row, parent, false);
        return new ProdutosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutosViewHolder holder, int position) {
        Produto produto = mDataset.get(position);
        holder.txtDescricao.setText(produto.getNome());
        holder.txtPrecoDe.setText("De: " + FormatHelper.formatMoney(produto.getPrecoDe()));
        holder.txtPrecoDe.setPaintFlags(holder.txtPrecoDe.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtPrecoPor.setText("Por: " + FormatHelper.formatMoney(produto.getPrecoPor()));
        PicassoImageLoadingService.loadImage(produto.getUrlImagem(),holder.imgProduto);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();

    }

    public void add(Produto produto){
        mDataset.add(produto);
        notifyItemInserted(getItemCount());
    }

    class ProdutosViewHolder extends RecyclerView.ViewHolder{

        ImageView imgProduto;
        TextView txtDescricao, txtPrecoDe, txtPrecoPor;
        public ProdutosViewHolder(View itemView) {
            super(itemView);
            imgProduto = itemView.findViewById(R.id.image_produto);
            txtDescricao = itemView.findViewById(R.id.txt_descricao_produto);
            txtPrecoDe = itemView.findViewById(R.id.txt_preco_de);
            txtPrecoPor = itemView.findViewById(R.id.txt_preco_por);

            itemView.setOnClickListener(view -> mProdutoClick.onProdutoClick(mDataset.get(getLayoutPosition())));
        }
    }

    public interface IProdutoClick{
        void onProdutoClick(Produto produto);
    }
}
