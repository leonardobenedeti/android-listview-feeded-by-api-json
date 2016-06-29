package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.CartItem;

/**
 * Created by leonardobenedeti on 29/06/16.
 */
public class CartDAO extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "oramacart";
    public static final String DICTIONARY_TABLE_NAME = "cart";

    public static final String ID_CART = "_id";
    public static final String ID_PROD = "id_prod";
    public static final String PRODUTO = "produto";
    public static final String DESCRICAO = "descricao";
    public static final String PATH_IMG = "path_img";
    public static final String VALOR = "valor";
    public static final String QTD = "qtd";

    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "id_prod INTEGER NOT NULL, " +
                    "produto TEXT ,"+
                    "descricao TEXT ,"+
                    "path_img TEXT ,"+
                    "valor TEXT "+
                    ");";


    public CartDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + DICTIONARY_TABLE_NAME);
        onCreate(db);
    }
}
