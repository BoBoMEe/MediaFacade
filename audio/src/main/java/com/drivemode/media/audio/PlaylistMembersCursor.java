package com.drivemode.media.audio;

import android.database.Cursor;
import android.provider.MediaStore;

/**
 * @author KeithYokoma
 */
@SuppressWarnings("unused") // public API
public class PlaylistMembersCursor extends AudioCursor {
  public PlaylistMembersCursor(Cursor cursor) {
    super(cursor);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.Playlists.Members#_ID}
   */
  public long mebersId() {
    return getLong(MediaStore.Audio.Playlists.Members._ID);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.Playlists.Members#AUDIO_ID}
   */
  public long audioId() {
    return getLong(MediaStore.Audio.Playlists.Members.AUDIO_ID);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.Playlists.Members#PLAYLIST_ID}
   */
  public long playlistId() {
    return getLong(MediaStore.Audio.Playlists.Members.PLAYLIST_ID);
  }

  /**
   * @return Data for {@link android.provider.MediaStore.Audio.Playlists.Members#PLAY_ORDER}
   */
  public int playOrder() {
    return getInt(MediaStore.Audio.Playlists.Members.PLAY_ORDER);
  }
}
