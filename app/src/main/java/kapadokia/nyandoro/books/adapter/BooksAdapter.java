package kapadokia.nyandoro.books.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kapadokia.nyandoro.books.BookDetail;
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
    public class  BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle, tvAuthors, tvDate, tvPublisher;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthors = itemView.findViewById(R.id.tv_authors);
            tvPublisher = itemView.findViewById(R.id.tv_publisher);
            tvDate = itemView.findViewById(R.id.tv_published_date);

            //setting the listener
            itemView.setOnClickListener(this);
        }

        // create a method called bind, that will take book as its argument

        public void bind(Book book){
            tvTitle.setText(book.title);
            tvAuthors.setText(book.authors);
            tvDate.setText(book.publishedDate);
            tvPublisher.setText(book.publisher);
        }

        @Override
        public void onClick(View v) {

            // first retrieve the selected position
            int position = getAdapterPosition();
            Book selectedBook = books.get(position);

            //then create an intent to the BookDetail class
            Intent intent = new Intent(v.getContext(), BookDetail.class);
            intent.putExtra("Book", selectedBook);

            // finnally, call the start activity class.
            v.getContext().startActivity(intent);

        }
    }
}
