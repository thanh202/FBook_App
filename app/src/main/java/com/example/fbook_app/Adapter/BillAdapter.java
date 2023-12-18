package com.example.fbook_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Response.BillResponse;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{

    private final Context context;
    private List<BillResponse.Result> billList = new ArrayList<>();

    public BillAdapter(Context context) {
        this.context = context;
    }

    public void setBillList(List<BillResponse.Result> list) {
        this.billList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill,parent,false);
        return new BillAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_Name,tv_Price,tv_CreateAt,tv_Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Name=itemView.findViewById(R.id.nameBook_Bill);
            tv_Price=itemView.findViewById(R.id.price_bill);
            tv_CreateAt=itemView.findViewById(R.id.createat_bill);
            tv_Status=itemView.findViewById(R.id.status_bill);
        }
        public void onBind(int position){
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);

            BillResponse.Result bill = billList.get(position);
            tv_Name.setText(bill.getBookName());
            tv_CreateAt.setText(bill.getCreate_at());
            tv_Status.setText(bill.getStatus());
            tv_Price.setText(format.format(bill.getPriceTotal()));
        }
    }
}
