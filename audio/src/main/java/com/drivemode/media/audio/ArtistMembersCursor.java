package com.drivemode.media.audio;

import android.database.Cursor;

/**
 * @author KeishinYokomaku
 */
@SuppressWarnings("unused") // public API
public class ArtistMembersCursor extends AlbumCursor {
	public static final String TAG = ArtistMembersCursor.class.getSimpleName();

	public ArtistMembersCursor(Cursor cursor) {
		super(cursor);
	}

}
