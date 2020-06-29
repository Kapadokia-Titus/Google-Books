package kapadokia.nyandoro.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

import kapadokia.nyandoro.books.sharedPref.SpUtil;

public class SearchActivity extends AppCompatActivity {

    private EditText edTitle, edAuthor, edPublisher, edIsbn;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edTitle = findViewById(R.id.etTitle);
        edAuthor = findViewById(R.id.etAuthor);
        edPublisher = findViewById(R.id.etPublisher);
        edIsbn = findViewById(R.id.etIsbn);

        submit = findViewById(R.id.btn_search);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edTitle.getText().toString().trim();
                String author = edAuthor.getText().toString().trim();
                String publisher = edPublisher.getText().toString().trim();
                String isbn = edIsbn.getText().toString().trim();

                if (title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()){
                    String message =getString(R.string.no_search_user);
                    Toast.makeText(SearchActivity.this, message, Toast.LENGTH_SHORT).show();
                }else {
                    URL queryUrl = ApiUtil.buildUrl(title,author,publisher,isbn);

                    // SharedPreference
                    Context context = getApplicationContext();
                    int position = SpUtil.getPreferenceInt(context, SpUtil.POSITION);

                    if (position==0 || position ==5){
                        position =1;
                    }else {
                        position ++;
                    }

                    String key = SpUtil.QUERY + String.valueOf(position);
                    String value = title+","+author+","+publisher+","+isbn;

                    SpUtil.setPrefferenceString(context,key,value);

                    // update the preference position
                    SpUtil.setPrefferenceInt(context,SpUtil.POSITION, position);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("Query", queryUrl);
                    startActivity(intent);
                }
            }
        });
    }
}
