package com.example.fbook_app.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.DanhGiaResponse;
import com.example.fbook_app.Model.Response.RatingTbResponse;
import com.example.fbook_app.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewBookAdapter extends RecyclerView.Adapter<NewBookAdapter.ViewHolder> {
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private OnLikeClickListener onLikeClickListener;
    private OnBuyClickListener onBuyClickListener;

    private List<BookResponse.Result> bookList = new ArrayList<>();
    private List<RatingTbResponse.Result> ratingList = new ArrayList<>();


    public NewBookAdapter(Context mContext) {
        context = mContext;
    }

    public void setRatingList(List<RatingTbResponse.Result> ratingList) {
        this.ratingList = ratingList;
        notifyDataSetChanged();
    }

    public void setListBook(List<BookResponse.Result> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_new_book, parent, false);
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
        private TextView tvItemPriceNewBook, tvRatingTb;
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
            tvRatingTb = itemView.findViewById(R.id.rating_tb_main);
        }

        public void onBind(int position) {
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);

            BookResponse.Result book = bookList.get(position);
            RatingTbResponse.Result rating = ratingList.get(position);
            SharedPreferences myToken = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
            String token = myToken.getString("token", null);
            if (token != null) {
                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                Call<RatingTbResponse> call = apiService.getRatingTb(token,book.getIDBook());
                call.enqueue(new Callback<RatingTbResponse>() {
                    @Override
                    public void onResponse(Call<RatingTbResponse> call, Response<RatingTbResponse> response) {
                        if (response.isSuccessful()){
                            tvRatingTb.setText(response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<RatingTbResponse> call, Throwable t) {

                    }
                });
            }
            tvItemNameNewBook.setText(book.getBookName());
            tvItemDescriptionNewBook.setText(book.getDiscription());
            tvItemPriceNewBook.setText(format.format(book.getPriceBook()));
            String imgBook = RetrofitClient.BASE_URL + book.getImageBook();
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
        void onItemClick(BookResponse.Result book);
    }

    public interface OnLikeClickListener {
        void onLikeClick(int idBook);
    }

    public interface OnBuyClickListener {
        void onBuyClick(BookResponse.Result book);
    }

}
