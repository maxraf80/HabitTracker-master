package udacity.com.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import udacity.com.habittracker.data.ActivityContract;
import udacity.com.habittracker.data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mNumberHours;
    private Spinner mKindActivity;

    private int mKind = ActivityContract.ActivityEntry.KIND_REST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        mNameEditText = (EditText) findViewById(R.id.edit_activity_name);
        mNumberHours = (EditText) findViewById(R.id.edit_hours);
        mKindActivity = (Spinner) findViewById(R.id.spinner_zone);

        setupSpinner();
    }

    private void setupSpinner() {

        ArrayAdapter kindOfActivitySpinner = ArrayAdapter.createFromResource(this,
                R.array.array_kindOfActivitySpinner, android.R.layout.simple_spinner_item);

        kindOfActivitySpinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mKindActivity.setAdapter(kindOfActivitySpinner);

        mKindActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selection = (String) parent.getItemAtPosition(position);

                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Work")) {
                        mKind = ActivityContract.ActivityEntry.KIND_WORK;
                    } else if (selection.equals("Fun")) {
                        mKind = ActivityContract.ActivityEntry.KIND_FUN;
                    } else {
                        mKind = ActivityContract.ActivityEntry.KIND_REST;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mKind = ActivityContract.ActivityEntry.KIND_WORK;
            }
        });
    }

    private void insertActivity() {

        String nameString = mNameEditText.getText().toString().trim();
        String numberOfHours = mNumberHours.getText().toString().trim();
        int hours = Integer.parseInt(numberOfHours);

        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ActivityContract.ActivityEntry.COLUMN_ACTIVITY_NAME, nameString);
        values.put(ActivityContract.ActivityEntry.COLUMN_ACTIVITY_KIND, mKind);
        values.put(ActivityContract.ActivityEntry.COLUMN_ACTIVITY_HOURS, hours);


        long newRowId = db.insert(ActivityContract.ActivityEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error saving activity", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Action saved whith row Id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_habit, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertActivity();
                finish();
                return true;
            case R.id.action_delete:
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



