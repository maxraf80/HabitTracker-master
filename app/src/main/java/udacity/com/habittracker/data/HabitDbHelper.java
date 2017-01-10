package udacity.com.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HabitDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ACTIVITY_TABLE = "CREATE TABLE " + ActivityContract.ActivityEntry.TABLE_NAME + " ("
                + ActivityContract.ActivityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ActivityContract.ActivityEntry.COLUMN_ACTIVITY_NAME + " TEXT NOT NULL, "
                + ActivityContract.ActivityEntry.COLUMN_ACTIVITY_KIND + " INTEGER NOT NULL, "
                + ActivityContract.ActivityEntry.COLUMN_ACTIVITY_HOURS + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_ACTIVITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
