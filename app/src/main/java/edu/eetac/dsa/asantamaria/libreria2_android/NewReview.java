package edu.eetac.dsa.asantamaria.libreria2_android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class NewReview extends Activity {

    EditText etNewReview;
    Button bPost;
    Book objeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        objeto = (Book)getIntent().getExtras().getSerializable("book");


        etNewReview=(EditText)findViewById(R.id.etNewReview);
        bPost=(Button)findViewById(R.id.bNewReview);

        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(v.getId()){
                    case R.id.bNewReview:
                        if(!validate())
                            Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                        // call AsynTask to perform network operation on separate thread
                        new HttpAsyncTask().execute("http://hmkcode.appspot.com/jsonservlet");
                        break;
                }


            }
        });


    }

    public static String POST(String url, Review rev){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("bookid", rev.getBookId());
            jsonObject.accumulate("content", rev.getContent());
            jsonObject.accumulate("name", rev.getName());
            jsonObject.accumulate("username", rev.getUsername());


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        System.out.println(result);
        return result;
    }

    private boolean validate(){
        if(etNewReview.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        Review review;
        @Override
        protected String doInBackground(String... urls) {

            review = new Review();
            review.setContent(etNewReview.getText().toString());
            review.setBookId(objeto.getBookid());
            //review.setName(etNRName.getText().toString());
            //review.setContent(etNewReview.getText().toString());


            return POST(urls[0],review);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
