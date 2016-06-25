package mr.liu.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import mr.liu.beans.Remind;

public class DBRemindHelper {
	private static final String TableName = "reminds";
	private static final String DBName = "remindDB";
	private static final String KEY_id = "_id";
	private static final String KEY_type = "type";
	private static final String KEY_date = "date";
	private static final String KEY_desc = "desc";
	private static final String KEY_done = "done";
	private static final String KEY_repeat = "repeat";
	private static final int DBVersion = 1;
	private static final String CreateTableSql = "create table " + TableName
			+ "( _id integer primary key autoincrement," + "type,date,desc,done,repeat)";
	private Context context;
	private RemindHelper DBHelper;
	private SQLiteDatabase db;

	public DBRemindHelper(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		DBHelper = new RemindHelper(context);
	}

	private class RemindHelper extends SQLiteOpenHelper {

		public RemindHelper(Context context) {
			super(context, DBName, null, DBVersion);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			System.out.println(CreateTableSql);
			db.execSQL(CreateTableSql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXIST " + TableName);
			onCreate(db);
		}

	}

	public DBRemindHelper openDB() {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	public void insertRemind(Remind re) {
		ContentValues content = new ContentValues();
		content.put(KEY_type, re.getType());
		content.put(KEY_date, re.getDate());
		content.put(KEY_desc, re.getDesc());
		content.put(KEY_done, re.getDone());
		content.put(KEY_repeat, re.getRepeat());
		db.insert(TableName, null, content);
	}

	public Remind queryRemind(Remind re) {
		Cursor cursor = db.query(TableName, //
				new String[] { KEY_id, KEY_type, KEY_date, KEY_desc,KEY_done,KEY_repeat}, //
				"type=? and date=? and desc=?", //
				new String[] { re.getType() + "", re.getDate(), re.getDesc() }, //
				null, null, null);//
		if (cursor.moveToFirst()) {
			int type = cursor.getInt(1);
			String date = cursor.getString(2);
			String desc = cursor.getString(3);
			int done = cursor.getInt(4);
			long repeat = cursor.getLong(5);
			return new Remind(type, date, desc, done,repeat);
		}
		return null;
	}

	public Remind getById(int id) {
		Cursor cursor = db.query(TableName, new String[] { KEY_id, KEY_type, KEY_date, KEY_desc,KEY_done,KEY_repeat}, "_id=?",
				new String[] { id + "" }, null, null, null);
		if (cursor.moveToFirst()) {
			int type = cursor.getInt(1);
			String date = cursor.getString(2);
			String desc = cursor.getString(3);
			int done = cursor.getInt(4);
			long repeat = cursor.getLong(5);
			return new Remind(type, date, desc, done,repeat);
		}
		return null;
	}

	public int getID(Remind re) {
//		Cursor cursor = db.query(TableName, //
//				new String[] { KEY_id, KEY_type, KEY_date, KEY_desc, KEY_done }, //
//				"type=? and date=? and desc=?", //
//				new String[] { re.getType() + "", re.getDate(), re.getDesc() }, //
//				null, null, null);//
		Cursor cursor = db.rawQuery("select * from reminds where date=? and desc=?", 
				new String[] {re.getDate(), re.getDesc() });
		if (cursor == null) {
			return -1;
		}
		if (cursor.moveToFirst()) {
			int id = cursor.getInt(0);
			return id;
		}
		return -1;
	}

	public void deleteRemind(Remind re) {
		int id = getID(re);
		if (id != -1) {
			db.delete(TableName, "_id=?", new String[] { id + "" });
		}
	}

	public List<Remind> getAllRemind() {
		List<Remind> list = new ArrayList<Remind>();
		Cursor cursor = db.query(TableName, new String[] { KEY_id, KEY_type, KEY_date, KEY_desc,KEY_done,KEY_repeat}, null, null, null,
				null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int type = cursor.getInt(1);
				String date = cursor.getString(2);
				String desc = cursor.getString(3);
				int done = cursor.getInt(4);
				long repeat = cursor.getLong(5);
				list.add(new Remind(type, date, desc, done,repeat));
			}
		}
		return list;
	}
	public void setDone(Remind re) {
		int id = getID(re);
		if (id != -1) {
			ContentValues content = new ContentValues();
			content.put(KEY_done, 1);
			db.update(TableName, content, "_id=?", new String[] { id + "" });
		}
	}
	public void updateRemind(Remind oldre, Remind newre) {
		int id = getID(oldre);
		if (id != -1) {
			ContentValues content = new ContentValues();
			content.put(KEY_type, newre.getType());
			content.put(KEY_date, newre.getDate());
			content.put(KEY_desc, newre.getDesc());
			content.put(KEY_done, newre.getDone());
			db.update(TableName, content, "_id=?", new String[] { id + "" });
		}
	}
	public int isInsert(Remind re) {
		int id = getID(re);
		System.out.println(id);
		return id>=0?id:-1;
	}
}
