package com.example.fbook_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.SearchResponse;
import com.example.fbook_app.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.ViewHolder> {
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private OnLikeClickListener onLikeClickListener;
    private OnBuyClickListener onBuyClickListener;

    private List<SearchResponse.Result> bookList = new ArrayList<>();

    public SearchBookAdapter(Context mContext) {
        context = mContext;
    }

    public void setListBook(List<SearchResponse.Result> list) {
        this.bookList = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
    public void setOnLikeClickListener(OnLikeClickListener likeClickListener) {
        this.onLikeClickListener = likeClickListener;
    }

    public void setOnBuyClickListener(OnBuyClickListener onBuyClickListener) {
        this.onBuyClickListener = onBuyClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_book, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgViewItemImgNewBook;
        private TextView tvItemNameNewBook;
        private TextView tvItemDescriptionNewBook;
        private TextView tvItemPriceNewBook;
        private RelativeLayout rlItemSelect;
        private ImageView btnItemLike;
        private ImageView btnItemBuy;


        public ViewHolder(View itemView) {
            super(itemView);
            imgViewItemImgNewBook = (ImageView) itemView.findViewById(R.id.imgView_item_imgNewBook);
            tvItemNameNewBook = (TextView) itemView.findViewById(R.id.tv_item_nameNewBook);
            tvItemDescriptionNewBook = (TextView) itemView.findViewById(R.id.tv_item_descriptionNewBook);
            tvItemPriceNewBook = (TextView) itemView.findViewById(R.id.tv_item_priceNewBook);
            rlItemSelect = itemView.findViewById(R.id.rl_item_select);
            btnItemLike = (ImageView) itemView.findViewById(R.id.btn_item_like);
            btnItemBuy = (ImageView) itemView.findViewById(R.id.btn_item_buy);
        }

        public void onBind(int position) {
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);

            SearchResponse.Result book = bookList.get(position);
            tvItemNameNewBook.setText(book.getBookName());
            tvItemDescriptionNewBook.setText(book.getDiscription());
            tvItemPriceNewBook.setText(format.format(book.getPriceBook()));
            String imgBook = RetrofitClient.BASE_URL+book.getImageBook();
            Glide.with(context).load(imgBook)
                    .into(imgViewItemImgNewBook);

            rlItemSelect.setOnClickListener(v -> {
                onItemClickListener.onItemClick(book);
            });

            btnItemLike.setOnClickListener(v -> {
                onLikeClickListener.onLikeClick(book.getIDBook());
            });

            btnItemBuy.setOnClickListener(v -> {
                onBuyClickListener.onBuyClick(book);
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SearchResponse.Result book);
    }
    public interface OnLikeClickListener{
        void onLikeClick(int idBook);
    }
    public interface OnBuyClickListener{
        void onBuyClick(SearchResponse.Result book);
    }

}
