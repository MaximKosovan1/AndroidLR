package com.example.lr2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class GameStatsDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game_stats.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STATS = "stats";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_TOTAL = "total_questions";

    public GameStatsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_STATS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SCORE + " INTEGER, " +
                COLUMN_TOTAL + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        onCreate(db);
    }

    public void saveGameResult(int score, int totalQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_TOTAL, totalQuestions);
        db.insert(TABLE_STATS, null, values);
        db.close();
    }

    public List<String> getAllGameStats() {
        List<String> statsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STATS, null);

        if (cursor.moveToFirst()) {
            do {
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE));
                int total = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL));
                statsList.add("Результат: " + score + " / " + total);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return statsList;
    }
}
