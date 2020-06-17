package kapadokia.nyandoro.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import kapadokia.nyandoro.books.model.Book;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView error_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pb_loading);
        error_tv = findViewById(R.id.error_tv);


        try {
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new BookQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }



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
            TextView textView = findViewById(R.id.tv_response);

            if (result==null){
                error_tv.setVisibility(View.VISIBLE);
            }else {
                error_tv.setVisibility(View.INVISIBLE);
            }

            ArrayList<Book> books = ApiUtil.getBooksFromJson(result);

            // create an empty string as a container for the result
            String resultString = "";

            for (Book book: books){
                resultString = resultString + book.title + "\n"+
                        book.publishedDate+"\n\n";
            }

            textView.setText(resultString);

        }
    }

}
