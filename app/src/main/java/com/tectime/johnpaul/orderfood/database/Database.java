package com.tectime.johnpaul.orderfood.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.tectime.johnpaul.orderfood.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnmachahuay on 4/1/18.
 */

public class Database extends SQLiteAssetHelper{
    private static final String DB_NAME = "eatItDB.db";
    private static final int DB_VERSION = 1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public List<Order> getAllOrders() {
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";

        sqLiteQueryBuilder.setTables(sqlTable);
        Cursor cursor = sqLiteQueryBuilder.query(database, sqlSelect, null, null, null, null, null);
        final List<Order> orders = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order(
                        cursor.getString(cursor.getColumnIndex("ProductId")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))
                );
                orders.add(order);
            } while (cursor.moveToNext());
        }

        return orders;
    }

    public void saveOrder(Order order) {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("Insert into OrderDetail(ProductId, ProductName, Quantity, Price, Discount) values('%s', '%s', '%s', '%s', '%s');",
                order.getProductId(), order.getProductName(), order.getQuantity(), order.getPrice(), order.getDiscount());

        database.execSQL(query);
    }

    public void cleanAllOrders() {
        SQLiteDatabase database = getReadableDatabase();
        String query = "delete from OrderDetail";

        database.execSQL(query);
    }
}
