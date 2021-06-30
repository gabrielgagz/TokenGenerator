package com.gabrielgagz.tokengenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class DataBaseHelper {

    private Cursor returnCursorValue;

    private static SQLiteDatabase DbConnection (Context context) {
        SQLiteHelper helper = new SQLiteHelper(context,
                "helper", null, 1);
        return helper.getWritableDatabase();
    }

    private Cursor queryResultCheckAndRetrieve(Context context, String tokenId) {
        SQLiteDatabase db = DbConnection(context);
        String[] tableColumns = new String[] { "TOKEN_CODE" };
        String whereClause = "TOKEN_NAME_ID LIKE ?";
        String[] whereArgs = new String[] { tokenId };
        Cursor queryResult = db.query("USER_TOKEN_CODE", tableColumns, whereClause, whereArgs, null, null, null);
        if(queryResult != null) {
            queryResult.moveToFirst();
        }
        returnCursorValue = queryResult;
        db.close();
        return returnCursorValue;
    }

    private Boolean checkTokenCode (Context context, String tokenId) {
        Boolean isUpdating;
        Cursor queryResult = queryResultCheckAndRetrieve(context, tokenId);
        if (queryResult.moveToFirst()) {
            isUpdating = true;
        } else {
            isUpdating = false;
        }
        return isUpdating;
    }

    public String retrieveTokenCodeFromDb (Context context, String tokenId) {
        Cursor queryResult = queryResultCheckAndRetrieve(context, tokenId);
        if(queryResult != null) {
            queryResult.moveToFirst();
        }
        return queryResult.getString(0);
    }

    public Cursor returnFullFillData(Context context, List spnItems) {
        SQLiteDatabase db = DbConnection(context);
        Cursor queryResult = db.query("USER_TOKEN_NAME", new String[] { "TOKEN_NAME" }, null, null, null, null, null);
        while (queryResult.moveToNext()) {
            spnItems.add(queryResult.getString(0));
        }
        returnCursorValue = queryResult;
        db.close();
        return returnCursorValue;
    }

    public void storeTokenAndCodeInDb(Context context, String tokenId, String tokenName) {
        SQLiteDatabase db = DbConnection(context);
        ContentValues register = new ContentValues();
        register.put("TOKEN_NAME_ID", tokenId);
        register.put("TOKEN_CODE", tokenName);
        Boolean isUpdating = checkTokenCode(context, tokenId);

        if (!isUpdating) {
            db.insert("USER_TOKEN_CODE", null, register);
        } else {
            db.update("USER_TOKEN_CODE", register, "TOKEN_NAME_ID='" + tokenId + "'", null);
        }
        db.close();
    }

    public void storeNewTokenInDb (Context context, String newName) {
        SQLiteDatabase db = DbConnection(context);
        ContentValues register = new ContentValues();
        register.put("TOKEN_NAME", newName);
        db.insert("USER_TOKEN_NAME", null, register);
        db.close();
    }

}
