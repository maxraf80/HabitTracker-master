package udacity.com.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import udacity.com.habittracker.data.ActivityContract;
import udacity.com.habittracker.data.HabitDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_habit);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, HabitActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatebaseInfo();
    }


    private Cursor readDataFromDB() {
        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                ActivityContract.ActivityEntry._ID,
                ActivityContract.ActivityEntry.COLUMN_ACTIVITY_NAME,
                ActivityContract.ActivityEntry.COLUMN_ACTIVITY_KIND,
                ActivityContract.ActivityEntry.COLUMN_ACTIVITY_HOURS};

        Cursor cursor = db.query(
                ActivityContract.ActivityEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }


    private void displayDatebaseInfo() {
        Cursor cursor = readDataFromDB();

        TextView displayView = (TextView) findViewById(R.id.activity);
        try {
            displayView.setText("The activity table containst " + cursor.getCount() + " activities. \n\n");
            displayView.append(ActivityContract.ActivityEntry._ID + " - " +
                    ActivityContract.ActivityEntry.COLUMN_ACTIVITY_NAME + " - " +
                    ActivityContract.ActivityEntry.COLUMN_ACTIVITY_KIND + " - " +
                    ActivityContract.ActivityEntry.COLUMN_ACTIVITY_HOURS + " - " + "\n");

            int idColumnIndex = cursor.getColumnIndex(ActivityContract.ActivityEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ActivityContract.ActivityEntry.COLUMN_ACTIVITY_NAME);
            int kindColumnIndex = cursor.getColumnIndex(ActivityContract.ActivityEntry.COLUMN_ACTIVITY_KIND);
            int numberColumnIndex = cursor.getColumnIndex(ActivityContract.ActivityEntry.COLUMN_ACTIVITY_HOURS);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentKind = cursor.getInt(kindColumnIndex);
                String currentHours = cursor.getString(numberColumnIndex);

                displayView.append(("\n" + currentID + " + " +
                        currentName + " - " + currentKind + " - " +
                        currentHours));
            }
        } finally {
            cursor.close();
        }


    }


}

