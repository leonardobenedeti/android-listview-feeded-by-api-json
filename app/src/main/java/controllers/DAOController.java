package controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dao.CartDAO;
import model.CartItem;

/**
 * Created by leonardobenedeti on 29/06/16.
 */
public class DAOController {

    private SQLiteDatabase db;
    private CartDAO banco;



    public DAOController(Context context){
        banco = new CartDAO(context);
    }

    public String insereDado(CartItem cartItem){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CartDAO.ID_PROD, cartItem.getId());
        valores.put(CartDAO.PRODUTO, cartItem.getProduto());
        valores.put(CartDAO.DESCRICAO, cartItem.getDescricao());
        valores.put(CartDAO.PATH_IMG, cartItem.getPathImg());
        valores.put(CartDAO.VALOR, cartItem.getValor());


        resultado = db.insert(CartDAO.DICTIONARY_TABLE_NAME, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor getAll(){
        Cursor cursor;
        db = banco.getReadableDatabase();

        cursor = db.rawQuery("select _id, produto, valor"+
                            " from "+ CartDAO.DICTIONARY_TABLE_NAME+
                            ";"
                            , null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    public String getById(int id){
        Cursor cursor2;
        db = banco.getReadableDatabase();

        cursor2 = db.rawQuery("select produto"+
                        " from "+ CartDAO.DICTIONARY_TABLE_NAME+
                        " where _id = "+id+
                        ";"
                , null);

        if(cursor2!=null){
            cursor2.moveToLast();
        }
        db.close();
        return cursor2.getString(2);
    }

}
