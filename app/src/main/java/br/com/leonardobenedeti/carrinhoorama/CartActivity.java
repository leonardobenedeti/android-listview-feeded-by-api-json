package br.com.leonardobenedeti.carrinhoorama;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import controllers.DAOController;
import dao.CartDAO;
import model.CartItem;

public class CartActivity extends AppCompatActivity {

    private ListView lista;
    public static final String TITLE = "Remover produto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        DAOController daoController = new DAOController(getBaseContext());
        Cursor cursor = daoController.getAll();

//        _id, (DISTINCT produto) as prod, count(DISTINCT protuto) as qtd, valor

        String[] nomeCampos = new String[] {CartDAO.PRODUTO, CartDAO.VALOR};
        int[] idViews = new int[] {R.id.produto, R.id.valor};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.cart_itens,cursor,nomeCampos,idViews, 0);



        lista = (ListView)findViewById(R.id.listView);
        registerForContextMenu(lista);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lista.showContextMenuForChild(view);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, v.getId(), 0, "Remover produto");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getTitle()) {
            case TITLE:
                String name = "";
                return true;
            case R.id.buy:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



}
