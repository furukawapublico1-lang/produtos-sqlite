package com.seuapp.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdutoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "produtos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "produtos";

    public ProdutoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql =
                "CREATE TABLE produtos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "preco REAL NOT NULL" +
                ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        db.execSQL("DROP TABLE IF EXISTS produtos");
        onCreate(db);
    }

    public boolean inserirProduto(
            String nome,
            double preco
    ) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues valores =
                new ContentValues();

        valores.put("nome", nome);
        valores.put("preco", preco);

        long resultado =
                db.insert(
                        TABLE_NAME,
                        null,
                        valores
                );

        return resultado != -1;
    }

    public List<String> listarProdutos() {

        List<String> lista =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM produtos ORDER BY id DESC",
                        null
                );

        if (cursor.moveToFirst()) {

            do {

                String nome =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("nome")
                        );

                double preco =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow("preco")
                        );

                lista.add(
                        nome +
                        " - R$ " +
                        String.format(
                                Locale.getDefault(),
                                "%.2f",
                                preco
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();

        return lista;
    }
}
