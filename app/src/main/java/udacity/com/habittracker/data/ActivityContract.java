package udacity.com.habittracker.data;

import android.provider.BaseColumns;

public class ActivityContract {
    private ActivityContract() {
    }

    public static final class ActivityEntry implements BaseColumns {

        public final static String TABLE_NAME = "activities";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ACTIVITY_NAME = "name";
        public final static String COLUMN_ACTIVITY_KIND = "kind";
        public final static String COLUMN_ACTIVITY_HOURS = "hours";

        public final static int KIND_WORK = 1;
        public final static int KIND_REST = 2;
        public final static int KIND_FUN = 3;
    }
}
