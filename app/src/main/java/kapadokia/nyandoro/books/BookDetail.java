package kapadokia.nyandoro.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import kapadokia.nyandoro.books.databinding.ActivityBookDetailBinding;
import kapadokia.nyandoro.books.model.Book;

public class BookDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Book book = getIntent().getParcelableExtra("Book");
        ActivityBookDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail);

        // passing the current book to this object;
        binding.setBook(book);
    }
}
