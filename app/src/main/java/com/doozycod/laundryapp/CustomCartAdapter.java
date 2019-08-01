package com.doozycod.laundryapp;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomCartAdapter extends RecyclerView.Adapter<CustomCartAdapter.CartHolder> {
    Context context;
    List<DBModel> dbList = new ArrayList<>();
    DBHelper dbHelper;

    public CustomCartAdapter(Context context, List<DBModel> dbList) {
        this.context = context;
        this.dbList = dbList;
    }

    @NonNull
    @Override
    public CustomCartAdapter.CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_listview, parent, false);
        dbHelper = new DBHelper(context);


        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomCartAdapter.CartHolder holder, final int position) {

        holder.TOPclothes.setText(String.valueOf(dbList.get(position).getTop_clothes()));
        holder.JeansLower.setText(String.valueOf(dbList.get(position).getJeans_lower()));
        holder.Bedsheets.setText(String.valueOf(dbList.get(position).getBedsheets()));
        holder.Towels.setText(String.valueOf(dbList.get(position).getTowels()));
        holder.wash.setText(String.valueOf(dbList.get(position).getWash_only()));
        holder.iron.setText(String.valueOf(dbList.get(position).getIron_only()));
        holder.cart_price.setText("\u20B9 " + String.valueOf(dbList.get(position).getFinal_price()));
        holder.remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
            }
        });
    }

    void removeItem(final int position) {
        final Dialog removeDialog = new Dialog(context);
        removeDialog.setContentView(R.layout.delete_dialog);
        removeDialog.show();
        Button remove_item_btn = removeDialog.findViewById(R.id.yes_remove);
        Button cancel_remove_btn = removeDialog.findViewById(R.id.no_remove);
        remove_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteEntry(String.valueOf(dbList.get(position).getId()));
                dbList.remove(position);
                notifyDataSetChanged();
                removeDialog.dismiss();

            }
        });
        cancel_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    class CartHolder extends RecyclerView.ViewHolder {

        TextView cart_price, TOPclothes, JeansLower, Bedsheets, Towels, wash, iron;
        ImageView remove_from_cart;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            cart_price = itemView.findViewById(R.id.cart_price);
            TOPclothes = itemView.findViewById(R.id.tshirtsqty);
            JeansLower = itemView.findViewById(R.id.jeansqty);
            Bedsheets = itemView.findViewById(R.id.bedsheetsqty);
            Towels = itemView.findViewById(R.id.towelqty);
            wash = itemView.findViewById(R.id.washqty);
            iron = itemView.findViewById(R.id.ironqty);
            remove_from_cart = itemView.findViewById(R.id.delete_item);
        }
    }
}
