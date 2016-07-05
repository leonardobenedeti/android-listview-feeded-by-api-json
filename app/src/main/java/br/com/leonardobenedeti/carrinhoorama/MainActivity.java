package br.com.leonardobenedeti.carrinhoorama;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.MyListAdapter;
import controllers.AppController;
import controllers.DAOController;
import model.CartItem;

public class MainActivity extends AppCompatActivity {

//    TextView errorMessage;
//    ProgressBar progress;
//    LinearLayout content;
//    private TextView titleList;

    private ArrayList<CartItem> cartItems;


    MyListAdapter adapter;
//
//    private Map<String, String> params;
//    private RequestQueue rq;
//    private String resposta;
//    private Activity activity;
//    private ProgressDialog pd;
//    String url;
//
//    String metodo;
//    String parametro;

    private ListView listView;

    FloatingActionButton fab;
    BadgeView badge;
    DAOController daoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CartItem cartItem = new CartItem();

                cartItem = (CartItem) parent.getItemAtPosition(position);

                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                i.putExtra("selecionado", (Serializable) cartItem);
                startActivity(i);
            }
        });



        fab = (FloatingActionButton) findViewById(R.id.fab);

        badge = new BadgeView(this, fab);
        daoController = new DAOController(getBaseContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "http://leonardobenedeti.com.br/projetos/orama/api.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("onResponse - ", response.toString());
                        degenerateJson(response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onErrorResponse - ", "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        int itens = daoController.getCount();
        badge.setText(itens+"");
        badge.show();

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(i);
                }
            });
        }

    }

    public void degenerateJson(String data){

        try {
            JSONObject json = new JSONObject(data);

            if(!json.isNull("produtos")){
                JSONArray ja = json.getJSONArray("produtos");
                cartItems = new ArrayList<CartItem>();
                for(int i = 0, tam = ja.length(); i < tam; i++){
                    JSONObject jProds = ja.getJSONObject(i);

                    CartItem cart = new CartItem();

                    cart.setId(i);
                    cart.setProduto(jProds.getString("produto"));
                    cart.setDescricao(jProds.getString("descricao"));
                    cart.setPathImg(jProds.getString("pathImg"));
                    cart.setValor(Float.parseFloat(jProds.getString("valor")));

                    cartItems.add(cart);
                }

                adapter = new MyListAdapter(cartItems , MainActivity.this);
                listView.setAdapter(adapter);
            }
        }
        catch(Exception e){
            Toast.makeText(MainActivity.this, "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
