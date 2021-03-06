package com.drivemode.media.audio;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.drivemode.media.common.CursorUtils;
import com.drivemode.media.common.SortOrder;

/**
 * Facade object to the modularized {@link Media}, {@link Playlist}, {@link Genre}, {@link Album}, and {@link Artist} database.
 * @author KeithYokoma
 */
@SuppressWarnings("unused") // public API
public class AudioFacade {
	private static AudioFacade instance;
	private final Media media;
	private final Playlist playlist;
	private final Album album;
	private final Genre genre;
	private final Artist artist;

	/**
	 * Initialize the facade with the application context.
	 * Do not call this method directly.
	 *
	 * @param context the application context
	 */
	protected AudioFacade(@NonNull Context context) {
		this(new Media(context), new Playlist(context), new Album(context), new Genre(context), new Artist(context));
	}

	/**
	 * Convenient constructor to inject each modular class, especially for testing purpose.
	 * @param media the media class
	 * @param playlist the playlist class
	 * @param album the album class
	 * @param artist the artist class
	 * @param genre the genre class
	 */
	protected AudioFacade(@NonNull Media media,
						  @NonNull Playlist playlist,
						  @NonNull Album album,
						  @NonNull Genre genre,
						  @NonNull Artist artist) {
		this.media = media;
		this.playlist = playlist;
		this.album = album;
		this.genre = genre;
		this.artist = artist;
	}

	/**
	 * Get an singleton object of {@link AudioFacade}.
	 * @param context the context.
	 * @return the singleton object of {@link AudioFacade}.
	 */
	public static AudioFacade getInstance(Context context) {
		if (instance == null)
			instance = new AudioFacade(context.getApplicationContext());
		return instance;
	}

	/**
	 * @return Modular class for {@link Playlist}.
	 */
	public @NonNull Playlist playlist() {
		return playlist;
	}

	/**
	 * @return Modular class for {@link Album}.
	 */
	public @NonNull Album album() {
		return album;
	}

	/**
	 * @return Modular class for {@link Genre}.
	 */
	public @NonNull Genre genre() {
		return genre;
	}

	/**
	 * @return Modular class for {@link Artist}.
	 */
	public @NonNull Artist artist() {
		return artist;
	}

	/**
	 * @return Modular class for {@link Media}.
	 */
	public @NonNull Media media() {
		return media;
	}

	/**
	 * {@link Media} provides access to the all audio media metadata.
	 */
	public static class Media {
		private final Context context;
		private final ContentResolver resolver;

		protected Media(Context context) {
			this.context = context;
			this.resolver = context.getContentResolver();
		}

		/**
		 * Fetch all audio metadata from {@link MediaStore}.
		 */
		public @Nullable AudioCursor fetch() {
			return fetch(SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all audio metadata from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable AudioCursor fetch(SortOrder order) {
			return new AudioCursor(resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, order.toSql()));
		}
	}

	/**
	 * {@link Playlist} provides access to the all playlist metadata.
	 */
	public static class Playlist {
		private final Context context;
		private final ContentResolver resolver;

		protected Playlist(Context context) {
			this.context = context;
			this.resolver = context.getContentResolver();
		}

		/**
		 * Fetch all playlist metadata from {@link MediaStore}.
		 */
		public @Nullable PlaylistCursor fetchLists() {
			return fetchLists(SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all playlist metadata from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable PlaylistCursor fetchLists(SortOrder order) {
			return new PlaylistCursor(resolver.query(
					MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, null, null, null, order.toSql()));
		}

		/**
		 * Fetch all audio metadata belong to the playlist from {@link MediaStore}.
		 */
		public @Nullable PlaylistMembersCursor fetchPlayableItems(long playlistId) {
			return fetchPlayableItems(playlistId, SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all audio metadata belong to the playlist from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable PlaylistMembersCursor fetchPlayableItems(long playlistId, SortOrder order) {
			return new PlaylistMembersCursor(resolver.query(
					MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId), null, null, null, order.toSql()));
		}

		/**
		 * Create a new playlist.
		 * @param name the playlist name. This should be unique so that you cannot put the name which already exists in the database.
		 * @return playlist {@link Uri}.
		 */
		public @Nullable Uri createNew(String name) {
			ContentValues value = new ContentValues();
			value.put(MediaStore.Audio.Playlists.NAME, name);
			return resolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, value);
		}

		/**
		 * Get the last order number in the playlist.
		 * @param playlistId the playlist id.
		 * @return the last order number in the playlist.
		 */
		public int getLastPlayOrder(long playlistId) {
			Cursor cursor = null;
			try {
				cursor = fetchPlayableItems(playlistId, new SortOrder(MediaStore.Audio.Playlists.Members.PLAY_ORDER, SortOrder.Order.ASCENDING));
				if (cursor == null || !cursor.moveToLast())
					return 0;
				return cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.PLAY_ORDER));
			} finally {
				CursorUtils.close(cursor);
			}
		}

		/**
		 * Update the playlist name.
		 * @param playlistId the playlist id.
		 * @param name new name for the playlist.
		 * @return 0 if not updated.
		 */
		public int updateName(long playlistId, String name) {
			ContentValues value = new ContentValues();
			value.put(MediaStore.Audio.Playlists.NAME, name);
			return resolver.update(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, value,
					MediaStore.Audio.Playlists._ID + " = ?", new String[] {String.valueOf(playlistId)});
		}

		/**
		 * Delete the playlist.
		 * @param playlistId the playlist.
		 * @return 0 if not deleted.
		 */
		public int remove(long playlistId) {
			return resolver.delete(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
					MediaStore.Audio.Playlists._ID + " = ?", new String[]{String.valueOf(playlistId)});
		}

		/**
		 * Put the audio to the playlist.
		 * @param playlistId the playlist.
		 * @param audioId the audio id to be included to the playlist.
		 * @param position where to put the audio.
		 * @return playlist item {@link Uri}.
		 */
		public @Nullable Uri insertItemTo(long playlistId, long audioId, int position) {
			ContentValues value = new ContentValues();
			value.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioId);
			value.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, position);
			return resolver.insert(MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId), value);
		}

		/**
		 * Put multiple audio to the playlist. the position will be determined in the order of the audio ids.
		 * @param playlistId the playlist.
		 * @param audioIds the audio id to be included to the playlist.
		 * @return playlist item {@link Uri}.
		 */
		public int insertItemsTo(long playlistId, @NonNull long[] audioIds) {
			int[] positions = new int[audioIds.length];
			for (int i = 0, limit = audioIds.length; i < limit; i++) {
				positions[i] = i;
			}
			return insertItemsTo(playlistId, audioIds, positions);
		}

		/**
		 * Put the audio to the playlist. {@param audioIds} and {@param positions} should have the same number of items.
		 * @param playlistId the playlist.
		 * @param audioIds the audio id to be included to the playlist.
		 * @param positions where to put the audio.
		 * @return playlist item {@link Uri}.
		 */
		public int insertItemsTo(long playlistId, @NonNull long[] audioIds, @NonNull int[] positions) {
			if (audioIds.length != positions.length)
				throw new IllegalArgumentException("audio id length does not match corresponding position array length");
			ContentValues[] values = new ContentValues[audioIds.length];
			for (int i = 0, limit = audioIds.length; i < limit; i++) {
				ContentValues value = new ContentValues();
				value.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioIds[i]);
				value.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, positions[i]);
				values[i] = value;
			}
			return resolver.bulkInsert(MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId), values);
		}

		/**
		 * Remote item from the playlist.
		 * @param playlistId the playlist.
		 * @param audioId the audio id to be excluded from the playlist.
		 * @return 0 if not removed.
		 */
		public int removeItemFrom(long playlistId, long audioId) {
			return resolver.delete(MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId),
					MediaStore.Audio.Playlists.Members.AUDIO_ID + " = ?", new String[]{String.valueOf(audioId)});
		}
	}

	/**
	 * {@link Album} provides access to the all album metadata. This class also provides the {@link Uri} for the album art.
	 */
	public static class Album {
		private static final Uri ALBUM_ART_URI_BASE = Uri.parse("content://media/external/audio/albumart");
		private final Context context;
		private final ContentResolver resolver;

		protected Album(Context context) {
			this.context = context;
			this.resolver = context.getContentResolver();
		}

		/**
		 * Fetch all album metadata from {@link MediaStore}.
		 */
		public @Nullable AlbumCursor fetchAlbums() {
			return fetchAlbums(SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all album metadata from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable AlbumCursor fetchAlbums(SortOrder order) {
			return new AlbumCursor(resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, order.toSql()));
		}

		/**
		 * Fetch all audio metadata belong to the album from {@link MediaStore}.
		 */
		public @Nullable AlbumMembersCursor fetchPlayableItems(long albumId) {
			return fetchPlayableItems(albumId, SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all audio metadata belong to the playlist from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable AlbumMembersCursor fetchPlayableItems(long albumId, SortOrder order) {
			return new AlbumMembersCursor(resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Audio.Media.ALBUM_ID + " = ?", new String[]{String.valueOf(albumId)}, order.toSql()));
		}

		/**
		 * Get the album art {@link Uri} for the album.
		 * @param albumId the album id.
		 * @return the album art {@link Uri}.
		 */
		public @NonNull Uri albumArtUri(long albumId) {
			return ContentUris.withAppendedId(ALBUM_ART_URI_BASE, albumId);
		}
	}

	/**
	 * {@link Genre} provides access to the all genre metadata.
	 */
	public static class Genre {
		private final Context context;
		private final ContentResolver resolver;

		protected Genre(Context context) {
			this.context = context;
			this.resolver = context.getContentResolver();
		}

		/**
		 * Fetch all genre metadata from {@link MediaStore}.
		 */
		public @Nullable GenreCursor fetchGenres() {
			return fetchGenres(SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all genre metadata from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable GenreCursor fetchGenres(SortOrder order) {
			return new GenreCursor(resolver.query(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI, null, null, null, order.toSql()));
		}

		/**
		 * Fetch all audio metadata belong to the genre from {@link MediaStore}.
		 */
		public @Nullable GenreMembersCursor fetchPlayableItems(long genreId) {
			return fetchPlayableItems(genreId, SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all audio metadata belong to the genre from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable GenreMembersCursor fetchPlayableItems(long genreId, SortOrder order) {
			return new GenreMembersCursor(resolver.query(MediaStore.Audio.Genres.Members.getContentUri("external", genreId), null, null, null, order.toSql()));
		}
	}

	/**
	 * {@link Artist} provides access to the all artist metadata.
	 */
	public static class Artist {
		private final Context context;
		private final ContentResolver resolver;

		protected Artist(Context context) {
			this.context = context;
			this.resolver = context.getContentResolver();
		}

		/**
		 * Fetch all artist metadata from {@link MediaStore}.
		 */
		public @Nullable ArtistCursor fetchArtists() {
			return fetchArtists(SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all artist metadata from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable ArtistCursor fetchArtists(SortOrder order) {
			return new ArtistCursor(resolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, order.toSql()));
		}

		/**
		 * Fetch all audio metadata belong to the artist from {@link MediaStore}.
		 */
		public @Nullable ArtistMembersCursor fetchAlbums(long artistId) {
			return fetchAlbums(artistId, SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all audio metadata belong to the artist from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable ArtistMembersCursor fetchAlbums(long artistId, SortOrder order) {
			return new ArtistMembersCursor(resolver.query(MediaStore.Audio.Artists.Albums.getContentUri("external", artistId), null, null, null, order.toSql()));
		}
	}
}
