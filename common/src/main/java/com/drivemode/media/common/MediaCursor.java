package com.drivemode.media.common;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/5/16 汪波 first commit
 */
public class MediaCursor  extends BaseCursor {
  public MediaCursor(Cursor cursor) {
    super(cursor);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.MediaColumns#DATA}
   */
  public String data() {
    return getString(MediaStore.MediaColumns.DATA);
  }
  /**
   * @return Data for {@link android.provider.MediaStore.MediaColumns#SIZE}
   */
  public long size() {
    return getLong(MediaStore.MediaColumns.SIZE);
  }
  /**
   * @return Data for {@link android.provider.MediaStore.MediaColumns#DISPLAY_NAME}
   */
  public String displayName() {
    return getString(MediaStore.MediaColumns.DISPLAY_NAME);
  }
  /**
   * @return Data for {@link android.provider.MediaStore.MediaColumns#TITLE}
   */
  public String title() {
    return getString(MediaStore.MediaColumns.TITLE);
  }
  /**
   * @return Data for {@link android.provider.MediaStore.MediaColumns#DATE_ADDED}
   */
  public long createdAt() {
    return getLong(MediaStore.MediaColumns.DATE_ADDED);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.MediaColumns#DATE_MODIFIED}
   */
  public long updatedAt() {
    return getLong(MediaStore.MediaColumns.DATE_MODIFIED);
  }
   /**
   * @return Data for {@link android.provider.MediaStore.MediaColumns#MIME_TYPE}
   */
  public String mimeType() {
    return getString(MediaStore.MediaColumns.MIME_TYPE);
  }

  public boolean drmProtected() {
    return getInt("is_drm") != 0;
  }
  /**
   * @return Data for {@link android.provider.MediaStore.MediaColumns#WIDTH}
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public long width() {
    return getLong(MediaStore.MediaColumns.WIDTH);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.MediaColumns#HEIGHT}
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public long height() {
    return getLong(MediaStore.MediaColumns.HEIGHT);
  }

}
