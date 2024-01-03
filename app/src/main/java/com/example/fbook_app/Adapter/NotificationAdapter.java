package com.example.fbook_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbook_app.Model.Response.BillResponse;
import com.example.fbook_app.Model.Response.NotificationResponse;
import com.example.fbook_app.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private final Context context;
    private List<NotificationResponse.Result> notiList = new ArrayList<>();
    public NotificationAdapter(Context context) {
        this.context = context;
    }

    public void setNotiList(List<NotificationResponse.Result> notiList) {
        this.notiList = notiList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification,parent,false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_Title,tv_Body,tv_CreateAt,tv_NameBook;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Title=itemView.findViewById(R.id.title_nofi);
            tv_Body=itemView.findViewById(R.id.body_nofi);
            tv_CreateAt=itemView.findViewById(R.id.createat_nofi);
            tv_NameBook=itemView.findViewById(R.id.nameBook_thongbao);
        }
        public void onBind(int position){
            NotificationResponse.Result notifi = notiList.get(position);

            tv_Title.setText(notifi.getTitle());
            tv_CreateAt.setText(notifi.getCreate_at());
            tv_Body.setText(notifi.getContent());
            tv_NameBook.setText(notifi.getBookName());
        }
    }
}
