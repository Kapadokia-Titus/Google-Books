package kapadokia.nyandoro.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import kapadokia.nyandoro.books.adapter.BooksAdapter;
import kapadokia.nyandoro.books.model.Book;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ProgressBar progressBar;
    private TextView error_tv;
    private RecyclerView recyclerViewBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pb_loading);
        error_tv = findViewById(R.id.error_tv);

        recyclerViewBooks = findViewById(R.id.rv_books);
        // creating a layout manager
        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
           recyclerViewBooks.setLayoutManager(booksLayoutManager);


        try {
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new BookQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        // responding to user action
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_advanced_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);

           default:
               return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        try {
            URL bookUrl = ApiUtil.buildUrl(query);
            new BookQueryTask().execute(bookUrl);
        }catch (Exception e){
            Log.d("Error", "onQueryTextSubmit: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public class BookQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {

            URL searchUrl = urls[0];
            String result = null;

            try {
                result = ApiUtil.getJson(searchUrl);
            }catch (Exception e){
                Log.d("Error", e.toString());
            }

            return  result; 
      }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);


            if (result==null){
                recyclerViewBooks.setVisibility(View.INVISIBLE);
                error_tv.setVisibility(View.VISIBLE);
            }else {
                recyclerViewBooks.setVisibility(View.VISIBLE);
                error_tv.setVisibility(View.INVISIBLE);
            }

            ArrayList<Book> books = ApiUtil.getBooksFromJson(result);

            // create an empty string as a container for the result
            String resultString = "";


            BooksAdapter adapter =  new BooksAdapter(books);
            recyclerViewBooks.setAdapter(adapter);
        }
    }

}
