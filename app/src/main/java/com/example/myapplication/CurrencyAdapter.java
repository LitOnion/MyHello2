package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//定义适配器
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private List<Currency> currencyList;
    private OnItemClickListener itemClickListener;

    public CurrencyAdapter(List<Currency> currencyList, OnItemClickListener itemClickListener) {
        this.currencyList = currencyList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_currency_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Currency currency = currencyList.get(position);
        holder.tvCurrencyName.setText(currency.getName());
        holder.tvExchangeRate.setText(String.valueOf(currency.getRate()));

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(currency);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrencyName;
        TextView tvExchangeRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrencyName = itemView.findViewById(R.id.tvCurrencyName);
            tvExchangeRate = itemView.findViewById(R.id.tvExchangeRate);
        }
    }

    //定义点击事件接口
    public interface OnItemClickListener {
        void onItemClick(Currency currency);
    }
}