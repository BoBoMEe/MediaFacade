package com.drivemode.media.image;

import android.database.Cursor;
import android.provider.MediaStore;
import com.drivemode.media.common.MediaCursor;

/**
 * @author KeishinYokomaku
 */
@SuppressWarnings("unused") // public API
public class ImageCursor extends MediaCursor {
	public static final String TAG = ImageCursor.class.getSimpleName();

	public ImageCursor(Cursor cursor) {
		super(cursor);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#DESCRIPTION}
	 */
	public String description() {
		return getString(MediaStore.Images.Media.DESCRIPTION);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#PICASA_ID}
	 */
	public String picasaId() {
		return getString(MediaStore.Images.Media.PICASA_ID);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#IS_PRIVATE}
	 */
	public boolean isPrivate() {
		return getInt(MediaStore.Images.Media.IS_PRIVATE) != 0;
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#LATITUDE}
	 */
	public double latitude() {
		return getDouble(MediaStore.Images.Media.LATITUDE);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#LONGITUDE}
	 */
	public double longitude() {
		return getDouble(MediaStore.Images.Media.LONGITUDE);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#ORIENTATION}
	 */
	public int orientation() {
		return getInt(MediaStore.Images.Media.ORIENTATION);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#MINI_THUMB_MAGIC}
	 */
	public int miniThumbId() {
		return getInt(MediaStore.Images.Media.MINI_THUMB_MAGIC);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#BUCKET_ID}
	 */
	public String bucketId() {
		return getString(MediaStore.Images.Media.BUCKET_ID);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#BUCKET_DISPLAY_NAME}
	 */
	public String bucketName() {
		return getString(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
	}

	/**
	 * @return Data for {@link android.provider.MediaStore.Images.Media#DATE_TAKEN}
	 */
	public long takenAt() {
		return getLong(MediaStore.Images.Media.DATE_TAKEN);
	}

}
