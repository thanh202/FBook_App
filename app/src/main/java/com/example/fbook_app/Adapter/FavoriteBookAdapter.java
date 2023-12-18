package com.example.fbook_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Response.ListFavouriteResponse;
import com.example.fbook_app.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoriteBookAdapter extends RecyclerView.Adapter<FavoriteBookAdapter.ViewHolder> {
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private OnUnFavouriteClickListener onUnFavouriteClickListener;
    private List<ListFavouriteResponse.Result> bookList = new ArrayList<>();
    public FavoriteBookAdapter(Context mContext){
        context = mContext;
    }
    public void setListBook(List<ListFavouriteResponse.Result> list){
        this.bookList = list;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
    public void setOnUnFavouriteClickListener(OnUnFavouriteClickListener unFavouriteClickListener) {
        this.onUnFavouriteClickListener = unFavouriteClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_favorite_book,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgViewItemImgBook;
        private TextView tvItemNameBook;
        private ImageView btnUnFavourite;
        private TextView tvItemDescription;
        private TextView tvItemPriceBook;

        private LinearLayout llSelectItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imgViewItemImgBook = (ImageView) itemView.findViewById(R.id.imgView_item_imgBook);
            tvItemNameBook = (TextView) itemView.findViewById(R.id.tv_item_nameBook);
            tvItemDescription = (TextView) itemView.findViewById(R.id.tv_item_description);
            tvItemPriceBook = (TextView) itemView.findViewById(R.id.tv_item_priceBook);
            llSelectItem = itemView.findViewById(R.id.ll_select_item_favorite_book);
            btnUnFavourite = itemView.findViewById(R.id.btn_un_favorite);
        }
        public void onBind(int position){
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);

                ListFavouriteResponse.Result book = bookList.get(position);
                tvItemNameBook.setText(book.getBookName());
                tvItemDescription.setText(book.getDiscription());
                String price = String.valueOf(book.getPriceBook());
                tvItemPriceBook.setText(format.format(book.getPriceBook()));
                String imgBook = RetrofitClient.BASE_URL+book.getImageBook();
            Glide.with(context).load(imgBook)
                    .into(imgViewItemImgBook);
            llSelectItem.setOnClickListener(v -> {
                onItemClickListener.onItemClick(book);
            });
            btnUnFavourite.setOnClickListener(v -> {
                onUnFavouriteClickListener.onUnFavouriteClick(book.getIDFavorite());
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ListFavouriteResponse.Result book);
    }
    public interface OnUnFavouriteClickListener {
        void onUnFavouriteClick(int idFavourite);
    }
}
