package br.com.leonardobenedeti.carrinhoorama;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import controllers.DAOController;
import dao.CartDAO;
import model.CartItem;

public class CartActivity extends AppCompatActivity {

    private ListView lista;
    public static final String TITLE = "Remover produto";
    int itemID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lista = (ListView) findViewById(R.id.listView);
        registerForContextMenu(lista);

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

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        itemID = info.position;

        menu.add(0, v.getId(), 0, TITLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DAOController daoController = new DAOController(getBaseContext());
        Cursor cursor = daoController.getAll();

        String[] nomeCampos = new String[]{CartDAO.PRODUTO, CartDAO.VALOR};
        int[] idViews = new int[]{R.id.produto, R.id.valor};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.cart_itens, cursor, nomeCampos, idViews, 0);

        lista.setAdapter(adaptador);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        DAOController daoController = new DAOController(getBaseContext());

        if (item.getTitle() == TITLE) {
            daoController.remove(itemID);
            onResume();
        } else {
            return false;
        }
        return true;

    }

}
