package com.drivemode.media.audio;

import android.database.Cursor;
import com.drivemode.media.common.MediaCursor;

/**
 * @author KeishinYokomaku
 */
@SuppressWarnings("unused") // public API
public class AlbumMembersCursor extends MediaCursor {
	public static final String TAG = AlbumMembersCursor.class.getSimpleName();

	public AlbumMembersCursor(Cursor cursor) {
		super(cursor);
	}

	public boolean drmProtected() {
		return getInt("is_drm") != 0;
	}
}
