package br.com.leonardobenedeti.carrinhoorama;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.readystatesoftware.viewbadger.BadgeView;

import controllers.AppController;
import controllers.DAOController;
import model.CartItem;

public class DetailsActivity extends AppCompatActivity {

    BadgeView badge;
    DAOController daoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CartItem select = (CartItem) getIntent().getSerializableExtra("selecionado");

        setTitle(select.getProduto());

        NetworkImageView imgNetworkImageView = (NetworkImageView) findViewById(R.id.image);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imgNetworkImageView.setImageUrl(select.getPathImg(), imageLoader);

        TextView descricao = (TextView) findViewById(R.id.descricao);
        descricao.setText(select.getDescricao());

        TextView valor = (TextView) findViewById(R.id.valor);
        valor.setText("R$: "+select.getValor());




        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        FloatingActionButton cart = (FloatingActionButton) findViewById(R.id.cart);

        badge = new BadgeView(this, cart);
        daoController = new DAOController(getBaseContext());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAOController daoController = new DAOController(getBaseContext());

                String resultado;

                resultado = daoController.insereDado(select);

                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

                int itens = daoController.getCount();
                badge.setText(itens+"");
                badge.show();
            }
        });

        if (cart != null) {
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(DetailsActivity.this, CartActivity.class);
                    startActivity(i);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int itens = daoController.getCount();
        badge.setText(itens+"");
        badge.show();
    }
}
