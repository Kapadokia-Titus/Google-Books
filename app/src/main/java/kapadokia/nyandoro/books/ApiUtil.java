package kapadokia.nyandoro.books;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import kapadokia.nyandoro.books.model.Book;

public class ApiUtil {


    //base url,  part of the address that is not going to change
    // that's why we've declared it as a constant
    public static final String BASE_API_URL="https:/www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY="q";
    public static final String TITLE= "intitle:";

    /**
     * a method to build a query url
     *
     * it will take @param title
     */


    // not recommended
    public static URL  buildUrl(String title){

        URL url =null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAMETER_KEY, title)
                    .build();
        try{

            url = new URL(uri.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return  url;
    }

    public static URL buildUrl(String title, String author, String publisher, String isbn){

    }
    //connect to the api
    public static String getJson(URL url) throws IOException {


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {

            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);

            //if we want to read everything we set the delimeter to \\A
            // the \\A is a pattern which is a regular expression.
            scanner.useDelimiter("\\A");

                // checking if the url has data
            // true if it has, and false if it does'nt
            boolean hasData = scanner.hasNext();

            //if there is data, lets return it, otherwise,  we return null;
            if (hasData == true){
                return scanner.next();
            }else {
                return null;
            }

            }catch (Exception e){
                e.printStackTrace();
            Log.d("Error", e.toString());
                return null;
            }
            //finally close the connection calling the disconnect() method
            finally {
                connection.disconnect();
            }


        }

    //getting books from json
    public static ArrayList<Book> getBooksFromJson(String json){

        //it is best to create constants instead of strings for the values we are extracting
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subTitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String VOLUMEINFO = "volumeInfo";
        final String ITEMS = "items";
        final String DESCRIPTION = "description";
        final String IMAGELINKS = "imageLinks";
        final String THUMBNAIL = "thumbnail";
        //declare an arraylist of books and set it to null in the beginning
        ArrayList<Book> books = new ArrayList<Book>();

        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray= jsonObject.getJSONArray(ITEMS);
            int numberOfBooks = jsonArray.length();

            for (int i=0; i<numberOfBooks; i++){

                // creating a single json object
                JSONObject bookJson = jsonArray.getJSONObject(i);
                JSONObject volumeInfoJson = bookJson.getJSONObject(VOLUMEINFO);

                // imageLinks json object
                JSONObject imageLinksJSON = volumeInfoJson.getJSONObject(IMAGELINKS);
                //remember the authors are in an array
                int authorNum= volumeInfoJson.getJSONArray(AUTHORS).length();

                //create an array of strings that will contain the authors
                String[] authors = new String[authorNum];
                //loop through the array and pass each author from the array
                for (int j=0; j<authorNum; j++){
                    authors[j]  = volumeInfoJson.getJSONArray(AUTHORS).get(j).toString();
                }

                Book book = new Book(bookJson.getString(ID),
                                    volumeInfoJson.getString(TITLE),
                        (volumeInfoJson.isNull(SUBTITLE)?"":volumeInfoJson.getString(SUBTITLE)),
                        authors,
                        volumeInfoJson.getString(PUBLISHER),
                        volumeInfoJson.getString(PUBLISHED_DATE),
                        volumeInfoJson.getString(DESCRIPTION),
                        imageLinksJSON.getString(THUMBNAIL));

                books.add(book);
            }


        }catch (Exception e){

        }
        return books;
    }

}