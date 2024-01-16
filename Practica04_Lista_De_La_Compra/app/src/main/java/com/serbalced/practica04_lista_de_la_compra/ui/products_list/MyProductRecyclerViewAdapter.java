package com.serbalced.practica04_lista_de_la_compra.ui.products_list;

import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.serbalced.practica04_lista_de_la_compra.dto.Product;
import com.serbalced.practica04_lista_de_la_compra.databinding.FragmentProductBinding;
import com.serbalced.practica04_lista_de_la_compra.managers.DatabaseManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyProductRecyclerViewAdapter extends RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder> {
    private final List<Product> mValues;

    public MyProductRecyclerViewAdapter(List<Product> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).name);
        holder.mDescription.setText(mValues.get(position).description);
        holder.mPrice.setText(mValues.get(position).price + "€");
        holder.mAmount.setText(mValues.get(position).amount);
        final int finalPos = position;

        holder.mAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mValues.get(finalPos).amount = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getAsyncImage(holder.mImage, mValues.get(position).iD);
    }

    public void getAsyncImage(ImageView imageView, int iD) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap img = null;
                Cursor productImg = DatabaseManager.db.rawQuery("SELECT Image FROM products WHERE Id = " + iD + "", null);
                if (productImg.moveToFirst()) {
                    byte imgByte[] = productImg.getBlob(0);
                    img = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                }

                productImg.close();

                Bitmap finalImg = img;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(finalImg);
                        //ProductsManager.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mName;
        public final TextView mDescription;
        public final ImageView mImage;
        public final EditText mAmount;
        public final TextView mPrice;
        public Product mItem;

        public ViewHolder(FragmentProductBinding binding) {
            super(binding.getRoot());
            mName = binding.txtName;
            mDescription = binding.txtDescription;
            mImage = binding.imgProduct;
            mAmount = binding.txtAmount;
            mPrice = binding.txtPrice;
        }

        @Override
        public String toString() {
            return super.toString() + " ";
        }
    }
}