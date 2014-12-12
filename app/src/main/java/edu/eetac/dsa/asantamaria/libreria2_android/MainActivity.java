package edu.eetac.dsa.asantamaria.libreria2_android;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

    ArrayList<Book> bookList;
    BookAdapter adapter;
    String url = "http://10.0.2.2:8010/libreria-api/";
    ListView listview;
    EditText etSearch;
    Button bAuthor;
    Button bTitle;

    //final static String BOOK_DETAIL = "edu.eetac.dsa.asantamaria.libreria2_android.BOOKDETAIL";
    // Ruta y Nombre de la activad a la cual voy a enviar la info.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView)findViewById(R.id.list);
        etSearch = (EditText)findViewById(R.id.etSearch);
        bAuthor = (Button)findViewById(R.id.bAuthor);
        bTitle = (Button)findViewById(R.id.bTitle);

        //Captura de text
        final EditText searchtext = (EditText) findViewById(R.id.etSearch);


        bookList = new ArrayList<Book>();


        //---------------------------------------------------------->AUTHOR PART
        bAuthor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String search=searchtext.getText().toString();
                System.out.println(url+"books?length=800&author="+search);

                new JSONAsyncTask().execute(url+"books?length=800&author="+search);
                adapter = new BookAdapter(getApplicationContext(), R.layout.row, bookList);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                        Toast.makeText(getApplicationContext(), bookList.get(position).getTitle(), Toast.LENGTH_LONG).show();
                        
                        //Pasar a otra actividad
                        Intent intent=new Intent(MainActivity.this, BookActivity.class );
                        intent.putExtra("",bookList.get(position));
                        startActivity(intent);

                    }
                });
            }
        });

        //---------------------------------------------------------->TITLE PART
        bTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String search=searchtext.getText().toString();
                System.out.println(url+"books?length=800&title="+search);

                //new JSONAsyncTask().execute(url+"books?length=800&title="+search);
                //Por algun motivo no me responde bien la api a la busqueda, no es de la app
                // sino de la api Rest pero se implementaria igual que en el caso del autor

                new JSONAsyncTask().execute(url+"books?length=800&title="+search);
                adapter = new BookAdapter(getApplicationContext(), R.layout.row, bookList);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                        Toast.makeText(getApplicationContext(), bookList.get(position).getTitle(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = new ArrayList<Book>();
        String url = "http://10.0.2.2:8010/libreria-api/books";

        new JSONAsyncTask().execute(url+"?length=80");

        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new BookAdapter(getApplicationContext(), R.layout.row, bookList);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Toast.makeText(getApplicationContext(), bookList.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }*/


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
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
                    JSONArray jarray = jsono.getJSONArray("books");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Book book = new Book();

                        book.setBookid(object.getInt("bookid"));
                        book.setTitle(object.getString("title"));
                        book.setAuthor(object.getString("author"));
                        book.setLanguage(object.getString("language"));
                        book.setEdition(object.getInt("edition"));
                        book.setEditionDate(object.getString("editionDate"));
                        book.setPrintingDate(object.getString("printingDate"));
                        book.setPublisher(object.getString("publisher"));
                        //actor.setImage(object.getString("image"));

                        bookList.add(book);
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
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }}

