package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Users.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRY =
                " CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " ( " +
                        UsersMaster.Users._ID + " INTEGER PRIMARY KEY, " +
                        UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT, " +
                        UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT) ";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ UsersMaster.Users.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean addInfo(String userName, String password){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersMaster.Users.COLUMN_NAME_USERNAME, userName);
        contentValues.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        long result = sqLiteDatabase.insert(UsersMaster.Users.TABLE_NAME,null,contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor readAllInfo(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        return cursor;
    }

    public boolean validate(String userName,String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        Cursor cursor = sqLiteDatabase.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List usernames = new ArrayList<>();
        List passwords = new ArrayList<>();
        while (cursor.moveToNext()){
            String Username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String Password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));

            usernames.add(Username);
            passwords.add(Password);
        }

        cursor.close();

        for (int i = 0;i<usernames.size();i++){
            if(userName.equals(usernames.get(i)) && password.equals(passwords.get(i))){
                return true;
            }
        }
        return false;
    }

    public boolean updateInfo(String userName,String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(UsersMaster.Users.COLUMN_NAME_USERNAME,userName);
        contentValues.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME +" =?";
        String[] selectionArgs = { userName };

        int rows = sqLiteDatabase.update(
                UsersMaster.Users.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs
        );

        if (rows == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteInfo(String userName){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " = ? ";
        String[] selectionArgs = { userName };
        int rows = sqLiteDatabase.delete(UsersMaster.Users.TABLE_NAME,selection,selectionArgs);
        if (rows == 0){
            return false;
        }else {
            return true;
        }
    }
}
