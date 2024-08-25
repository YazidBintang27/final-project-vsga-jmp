package com.latihan.booklibrary.adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latihan.booklibrary.activity.UpdateDataActivity;
import com.latihan.booklibrary.database.DatabaseHelper;
import com.latihan.booklibrary.databinding.BookCardBinding;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    ArrayList<String> book_title, book_author, total_book;
    DatabaseHelper db;

    public ListAdapter(
        Context context,
        ArrayList<String> book_title,
        ArrayList<String> book_author,
        ArrayList<String> total_book
    ) {
        this.context = context;
        this.book_title = book_title;
        this.book_author = book_author;
        this.total_book = total_book;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookCardBinding binding = BookCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getBinding().etBookTitle.setText(String.valueOf(book_title.get(position)));
        holder.getBinding().etBookAuthor.setText(String.valueOf(book_author.get(position)));
        holder.getBinding().etTotalBook.setText(String.valueOf(total_book.get(position)));
        holder.getBinding().cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDataActivity.class);
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("total", String.valueOf(total_book.get(position)));
                context.startActivity(intent);
            }
        });
//        holder.getBinding().cardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                showDeleteDialog(position);
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return book_title.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final BookCardBinding binding;
        public ViewHolder(BookCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public BookCardBinding getBinding() {
            return this.binding;
        }
    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Hapus Buku")
                .setMessage("Apakah Anda yakin ingin menghapus buku ini?")
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Hapus buku dari database
                        db.deleteBook(book_title.get(position));
                        // Hapus buku dari list dan beri tahu adapter
                        book_title.remove(position);
                        book_author.remove(position);
                        total_book.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, book_title.size());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
