package adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import br.com.leonardobenedeti.carrinhoorama.R;
import controllers.AppController;
import model.CartItem;

/**
 * Created by leonardobenedeti on 29/06/16.
 */
public class MyListAdapter extends BaseAdapter {

    private CartItem cartItem;
    private List<CartItem> cartItems;
    private Activity activity;

    public MyListAdapter(List<CartItem> cartItems, Activity activity) {
        this.cartItems = cartItems;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        cartItem = cartItems.get(position);
        return cartItem.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        cartItem = cartItems.get(position);

        LayoutInflater inflater = activity.getLayoutInflater();
        View linha = inflater.inflate(R.layout.item_list, null);


        NetworkImageView imgNetworkImageView = (NetworkImageView) linha.findViewById(R.id.image);

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        imgNetworkImageView.setImageUrl(cartItem.getPathImg(), imageLoader);


        TextView nome = (TextView) linha.findViewById(R.id.nome);
        nome.setText(cartItem.getProduto().toString());

        TextView valor = (TextView) linha.findViewById(R.id.valor);
        valor.setText("R$: "+cartItem.getValor());


        return linha;
    }
}