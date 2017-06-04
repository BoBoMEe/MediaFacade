package com.drivemode.media.image;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import com.drivemode.media.common.SortOrder;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Facade object to the modularized {@link Bucket}, and {@link Image} database.
 * @author KeithYokoma
 */
@SuppressWarnings("unused") // public API
public class ImageFacade {
	private static ImageFacade instance;
	private final Bucket bucket;
	private final Image image;

	/**
	 * Initialize the facade with the application context.
	 * Do not call this method directly.
	 *
	 * @param context the application context
	 */
	protected ImageFacade(Context context) {
		this(new Bucket(context), new Image(context));
	}

	/**
	 * Convenient constructor to inject each modular class, especially for testing purpose.
	 * @param bucket the bucket class
	 * @param image the image class
	 */
	protected ImageFacade(Bucket bucket, Image image) {
		this.bucket = bucket;
		this.image = image;
	}

	/**
	 * Get an singleton object of {@link ImageFacade}.
	 * @param context the context.
	 * @return the singleton object of {@link ImageFacade}.
	 */
	public static ImageFacade getInstance(Context context) {
		if (instance == null)
			instance = new ImageFacade(context.getApplicationContext());
		return instance;
	}

	/**
	 * @return Modular class for {@link Bucket}.
	 */
	public Bucket bucket() {
		return bucket;
	}

	/**
	 * @return Modular class for {@link Image}.
	 */
	public Image image() {
		return image;
	}

	/**
	 * {@link Bucket} provides access to the bucket, the group unit of image.
	 */
	public static class Bucket {
		private static final String[] BUCKET_PROJECTION = {
				MediaStore.Images.Media.BUCKET_ID,
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.Images.Media._ID
		};
		private static final String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
		private final Context context;
		private final ContentResolver resolver;

		protected Bucket(Context context) {
			this.context = context;
			this.resolver = context.getContentResolver();
		}

		/**
		 * Fetches all buckets.
		 */
		public @Nullable ImageCursor fetch() {
			return fetch(SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetches all buckets in the specified {@link SortOrder}.
		 */
		public @Nullable ImageCursor fetch(SortOrder order) {
			return new ImageCursor(resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, BUCKET_PROJECTION, BUCKET_GROUP_BY, null, order.toSql()));
		}
	}

	/**
	 * Small module class to get image metadata from {@link MediaStore}.
	 */
	public static class Image {
		private final Context context;
		private final ContentResolver resolver;

		protected Image(Context context) {
			this.context = context;
			this.resolver = context.getContentResolver();
		}

		/**
		 * Fetch all image metadata from {@link MediaStore}.
		 */
		public @Nullable ImageCursor fetch() {
			return fetch(SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch all image metadata from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable ImageCursor fetch(SortOrder order) {
			return new ImageCursor(resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, order.toSql()));
		}

		/**
		 * Fetch image metadata in the specified bucket from {@link MediaStore}.
		 */
		public @Nullable ImageCursor fetchByBucket(long bucketId) {
			return fetchByBucket(bucketId, SortOrder.UNSPECIFIED);
		}

		/**
		 * Fetch image metadata belongs to the specified bucket from {@link MediaStore} in the specified {@link SortOrder}.
		 */
		public @Nullable ImageCursor fetchByBucket(long bucketId, SortOrder order) {
			return new ImageCursor(resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
					MediaStore.Images.Media.BUCKET_ID + " = ?", new String[]{String.valueOf(bucketId)}, order.toSql()));
		}
	}

	/**
	 * Small module class to get image thumbnail in bitmap format.
	 */
	public static class Thumbnail {
		private final Context context;
		private final ContentResolver resolver;

		protected Thumbnail(Context context) {
			this.context = context;
			this.resolver = context.getContentResolver();
		}

		/**
		 * Create a {@link Bitmap} for the image in a size specified by kind.
		 * @param imageId the image id to generate the thumbnail
		 * @param kind one of {@link android.provider.MediaStore.Images.Thumbnails#MINI_KIND}, {@link android.provider.MediaStore.Images.Thumbnails#MICRO_KIND} or {@link android.provider.MediaStore.Images.Thumbnails#FULL_SCREEN_KIND}
		 * @return the thumbnail bitmap.
		 */
		public @Nullable Bitmap fetch(long imageId, @Kind int kind) {
			return fetch(imageId, kind, null);
		}

		/**
		 * Create a {@link Bitmap} for the image with options in a size specified by kind.
		 * @param imageId the image id to generate the thumbnail
		 * @param kind one of {@link android.provider.MediaStore.Images.Thumbnails#MINI_KIND}, {@link android.provider.MediaStore.Images.Thumbnails#MICRO_KIND} or {@link android.provider.MediaStore.Images.Thumbnails#FULL_SCREEN_KIND}
		 * @param ops bitmap options
		 * @return the thumbnail bitmap.
		 */
		public @Nullable Bitmap fetch(long imageId, @Kind int kind, @Nullable BitmapFactory.Options ops) {
			return MediaStore.Images
					.Thumbnails.getThumbnail(resolver, imageId, kind, ops);
		}

		@Retention(RetentionPolicy.SOURCE)
		@IntDef({MediaStore.Images.Thumbnails.MINI_KIND, MediaStore.Images.Thumbnails.MICRO_KIND, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND})
		public @interface Kind {}
	}
}
