package com.drivemode.media.common;

import android.database.Cursor;
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
public class FilesCursor extends MediaCursor {
  public FilesCursor(Cursor cursor) {
    super(cursor);
  }

  public int storageId() {
    return getInt("storage_id");
  }

  public int format() {
    return getInt("format");
  }

  public int parent() {
    return getInt(MediaStore.Files.FileColumns.PARENT);
  }

  public int mediaType() {
    return getInt(MediaStore.Files.FileColumns.MEDIA_TYPE);
  }

  public boolean mediaTypeNone() {
    return mediaType() == MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;
  }

  public boolean mediaTypeImage() {
    return mediaType() == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
  }

  public boolean mediaTypeAudio() {
    return mediaType() == MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;
  }

  public boolean mediaTypeVideo() {
    return mediaType() == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
  }

  public boolean mediaTypePlayList() {
    return mediaType() == MediaStore.Files.FileColumns.MEDIA_TYPE_PLAYLIST;
  }
}
