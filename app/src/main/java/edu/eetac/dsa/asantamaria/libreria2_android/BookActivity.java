package edu.eetac.dsa.asantamaria.libreria2_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class BookActivity extends Activity {

    ArrayList<Review> reviewList;
    ReviewAdapter adapter;
    String url = "http://10.0.2.2:8010/libreria-api";
    ListView lvReview;
    Button bReview;
    Book aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        //Recuperar de la acividad viniente
        Book objeto = (Book)getIntent().getExtras().getSerializable("book");
        aux=objeto;

        TextView tvTitle_d=(TextView)findViewById(R.id.tvTitle_d);
        TextView tvAuthor_d=(TextView)findViewById(R.id.tvAuthor_d);
        TextView tvPublisher_d=(TextView)findViewById(R.id.tvPublisher_d);
        TextView tvEditionNumber_d=(TextView)findViewById(R.id.tvEditionNumber_d);
        TextView tvEditionDate_d=(TextView)findViewById(R.id.tvEditionDate_d);
        TextView tvLanguage_d=(TextView)findViewById(R.id.tvLanguage_d);
        TextView tvPrintingDate_d=(TextView)findViewById(R.id.tvPrintingDate_d);
        TextView tvBookid_d=(TextView)findViewById(R.id.tvBookid_d);

        tvTitle_d.setText(objeto.getTitle().toString());
        tvAuthor_d.setText("Autor: "+objeto.getAuthor().toString());
        tvPublisher_d.setText("Editorial: "+objeto.getPublisher().toString());
        tvEditionNumber_d.setText("Edición numero: " + objeto.getEdition());
        tvEditionDate_d.setText("Fecha de edición: " + objeto.getEditionDate().toString());
        tvLanguage_d.setText("Idioma: " + objeto.getLanguage().toString());
        tvPrintingDate_d.setText("Fecha Impresión: " + objeto.getPrintingDate().toString());
        tvBookid_d.setText("Identidicador: "+ objeto.getBookid());

        lvReview = (ListView)findViewById(R.id.reviewList);
        reviewList = new ArrayList<Review>();



        //System.out.println(url+"/reviews/reviewer/"+objeto.getBookid());

        new ReviewAsyncTask().execute(url+"/reviews/reviewer/"+objeto.getBookid());
        adapter = new ReviewAdapter(getApplicationContext(), R.layout.row_rev, reviewList);
        lvReview.setAdapter(adapter);



        bReview = (Button)findViewById(R.id.bReview);

        bReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /* Button code
        bReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    class ReviewAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(BookActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet peticionGet = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(peticionGet);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("review");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Review rev = new Review();

                        rev.setBookId(object.getInt("bookId"));
                        rev.setContent(object.getString("content"));
                        rev.setName(object.getString("name"));
                        rev.setUsername(object.getString("username"));
                        rev.setReviewid(object.getInt("reviewid"));
                        rev.setCreationTimestamp(object.getLong("creationTimestamp"));

                        reviewList.add(rev);
                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);






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
        if (id == R.id.addnewrev) {

            Intent intent=new Intent(BookActivity.this, NewReview.class );
            intent.putExtra("book",BookActivity.this.aux);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
