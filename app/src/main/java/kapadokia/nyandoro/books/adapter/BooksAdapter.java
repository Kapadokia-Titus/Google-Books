package kapadokia.nyandoro.books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kapadokia.nyandoro.books.R;
import kapadokia.nyandoro.books.model.Book;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder>{


    ArrayList<Book> books;
    //create a constructor that takes an arraylist of books as an argument
    public BooksAdapter (ArrayList<Book> books){
        this.books=books;
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        Book book = books.get(position);
        holder.bind(book);

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    // creating the view holder class
    public class  BookViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle, tvAuthors, tvDate, tvPublisher;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthors = itemView.findViewById(R.id.tv_authors);
            tvPublisher = itemView.findViewById(R.id.tv_publisher);
            tvDate = itemView.findViewById(R.id.tv_published_date);
        }

        // create a method called bind, that will take book as its argument

        public void bind(Book book){
            tvTitle.setText(book.title);
            String authors="";
            int i = 0;
            for (String author:book.authors){
                authors +=author;
                i++;

                // if the author is not the last one, we add a comma and a space
                if (i<book.authors.length){
                    authors+=", ";
                }
            }

            tvAuthors.setText(authors);
            tvDate.setText(book.publishedDate);
            tvPublisher.setText(book.publisher);
        }
    }
}
