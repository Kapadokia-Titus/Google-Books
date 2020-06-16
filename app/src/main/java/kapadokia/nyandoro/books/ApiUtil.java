package kapadokia.nyandoro.books;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiUtil {


    //base url,  part of the address that is not going to change
    // that's why we've declared it as a constant
    public static final String BASE_API_URL="https:/www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY="q";

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

}