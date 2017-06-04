package com.drivemode.media.sample;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.drivemode.media.common.CursorUtils;
import com.drivemode.media.common.FilesFacade;
import com.drivemode.media.sample.databinding.FragmentFilesBinding;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/6/4 汪波 first commit
 */
public class FilesFragment extends Fragment {
  private FragmentFilesBinding mFragmentFilesBinding;
  private CursorAdapter adapter;
  private FilesFacade mFilesFacade;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_files,container,false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mFragmentFilesBinding = FragmentFilesBinding.bind(getView());

    mFilesFacade = FilesFacade.getInstance(getContext());

    adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_1, null,
        new String[] {
            MediaStore.Files.FileColumns.DATA
        }, new int[] { android.R.id.text1 }, 0);

    mFragmentFilesBinding.list.setEmptyView(mFragmentFilesBinding.empty);
    mFragmentFilesBinding.list.setAdapter(adapter);

    Observable.fromCallable(() -> mFilesFacade.mimeType().fetch(new String[] { "image/jpeg" }))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(adapter::swapCursor, LogHelper::logError, this::applyEmptyContent);
  }

  @Override
  public void onDestroyView() {
    CursorUtils.close(adapter.swapCursor(null));
    super.onDestroyView();
  }

  private void applyEmptyContent() {
    if (adapter.isEmpty())
      mFragmentFilesBinding.empty.setText(R.string.empty_view_no_content);
  }


}
