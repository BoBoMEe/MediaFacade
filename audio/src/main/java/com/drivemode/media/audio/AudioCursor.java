package com.drivemode.media.audio;

import android.database.Cursor;
import android.provider.MediaStore;
import com.drivemode.media.common.MediaCursor;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/5/16 汪波 first commit
 */
public class AudioCursor extends MediaCursor {
  public AudioCursor(Cursor cursor) {
    super(cursor);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#TITLE_KEY}
   */
  public String titleKey() {
    return getString(MediaStore.Audio.AudioColumns.TITLE_KEY);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#DURATION}
   */
  public long duration() {
    return getLong(MediaStore.Audio.AudioColumns.DURATION);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#BOOKMARK}
   */
  public long bookmarkedPosition() {
    return getLong(MediaStore.Audio.AudioColumns.BOOKMARK);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#ARTIST_ID}
   */
  public long artistId() {
    return getLong(MediaStore.Audio.AudioColumns.ARTIST_ID);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#ARTIST}
   */
  public String artistName() {
    return getString(MediaStore.Audio.AudioColumns.ARTIST);
  }

  public String albumArtist() {
    return getString("album_artist");
  }

  public String compilation() {
    return getString("compilation");
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#ARTIST_KEY}
   */
  public String artistKey() {
    return getString(MediaStore.Audio.AudioColumns.ARTIST_KEY);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#COMPOSER}
   */
  public String composer() {
    return getString(MediaStore.Audio.AudioColumns.COMPOSER);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#ALBUM_ID}
   */
  public long albumId() {
    return getLong(MediaStore.Audio.AudioColumns.ALBUM_ID);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#ALBUM}
   */
  public String albumName() {
    return getString(MediaStore.Audio.AudioColumns.ALBUM);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#ALBUM_KEY}
   */
  public String albumKey() {
    return getString(MediaStore.Audio.AudioColumns.ALBUM_KEY);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#TRACK}
   */
  public int tracksCount() {
    return getInt(MediaStore.Audio.AudioColumns.TRACK);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#YEAR}
   */
  public int year() {
    return getInt(MediaStore.Audio.AudioColumns.YEAR);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#IS_MUSIC}
   */
  public boolean isMusic() {
    return getInt(MediaStore.Audio.AudioColumns.IS_MUSIC) != 0;
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#IS_PODCAST}
   */
  public boolean isPodcast() {
    return getInt(MediaStore.Audio.AudioColumns.IS_PODCAST) != 0;
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#IS_RINGTONE}
   */
  public boolean isRingtone() {
    return getInt(MediaStore.Audio.AudioColumns.IS_RINGTONE) != 0;
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#IS_ALARM}
   */
  public boolean isAlarm() {
    return getInt(MediaStore.Audio.AudioColumns.IS_ALARM) != 0;
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.AudioColumns#IS_NOTIFICATION}
   */
  public boolean isNotification() {
    return getInt(MediaStore.Audio.AudioColumns.IS_NOTIFICATION) != 0;
  }

  public String genre() {
    return getString("genre");
  }
}
