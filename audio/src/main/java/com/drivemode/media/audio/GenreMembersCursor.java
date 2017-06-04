package com.drivemode.media.audio;

import android.database.Cursor;
import android.provider.MediaStore;

/**
 * @author KeishinYokomaku
 */
@SuppressWarnings("unused") // public API
public class GenreMembersCursor extends AudioCursor {
	public GenreMembersCursor(Cursor cursor) {
		super(cursor);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Audio.Genres.Members#AUDIO_ID}
	 */
	public long audioId() {
		return getLong(MediaStore.Audio.Genres.Members.AUDIO_ID);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Audio.Genres.Members#GENRE_ID}
	 */
	public long genreId() {
		return getLong(MediaStore.Audio.Genres.Members.GENRE_ID);
	}

}
