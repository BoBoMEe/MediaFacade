package com.drivemode.media.common;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/6/4 汪波 first commit
 */
public class FilesFacade {
  private static FilesFacade instance;
  private final MimeType mMimeType;

  protected FilesFacade(Context context) {
    this(new MimeType(context));
  }

  protected FilesFacade(MimeType mimeType) {
    mMimeType = mimeType;
  }

  /**
   * Get an singleton object of {@link FilesFacade}.
   * @param context the context.
   * @return the singleton object of {@link FilesFacade}.
   */
  public static FilesFacade getInstance(Context context) {
    if (instance == null)
      instance = new FilesFacade(context.getApplicationContext());
    return instance;
  }

  public MimeType mimeType(){
    return mMimeType;
  }

  public static class MimeType {
    private final Context context;
    private final ContentResolver resolver;

    public MimeType(Context context) {
      this.context = context;
      this.resolver = context.getContentResolver();
    }

    /**
     * Fetch all mimetype files metadata from {@link MediaStore}.
     */
    public @Nullable FilesCursor fetch(String[] mimeType) {
      return fetch(mimeType, SortOrder.UNSPECIFIED);
    }

    /**
     * Fetch all mimetype files metadata from {@link MediaStore} in the specified {@link SortOrder}.
     */
    public @Nullable FilesCursor fetch(String[] mimeType, SortOrder order) {
      return new FilesCursor(resolver.query(MediaStore.Files.getContentUri("external"), null,
          MediaStore.Files.FileColumns.MIME_TYPE + "= ?", mimeType, order.toSql()));
    }
  }
}
