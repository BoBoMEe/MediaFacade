package com.drivemode.media.common;

import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.provider.BaseColumns;
import java.util.HashMap;

/**
 * @author KeithYokoma
 */
public abstract class BaseCursor extends CrossProcessCursorWrapper {
	private HashMap<String, Integer> mColumnIndexes = new HashMap<String, Integer>();

	public BaseCursor(Cursor cursor) {
		super(cursor);
	}

	protected int getCachedColumnIndexOrThrow(String colName) {
		Integer index = mColumnIndexes.get(colName);
		if (index == null) {
			index = getColumnIndexOrThrow(colName);
			mColumnIndexes.put(colName, index);
		}
		return index;
	}

	public int getInt(String column) {
		return getInt(getCachedColumnIndexOrThrow(column));
	}

	public long getLong(String column) {
		return getLong(getCachedColumnIndexOrThrow(column));
	}

	public double getDouble(String column) {
		return getDouble(getCachedColumnIndexOrThrow(column));
	}

	public String getString(String column) {
		return getString(getCachedColumnIndexOrThrow(column));
	}

	/**
	 * @return Data for {@link BaseColumns#_ID}
	 */
	public long id() {
		return getLong(BaseColumns._ID);
	}

}