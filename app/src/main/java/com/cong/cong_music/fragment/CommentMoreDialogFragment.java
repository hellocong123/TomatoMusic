package com.cong.cong_music.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.cong.cong_music.R;

/**
 * @author Cong
 * @date 2018/10/10
 * @description
 */
public class CommentMoreDialogFragment extends DialogFragment {

    private DialogInterface.OnClickListener onClickListener;
    private int selectIndex;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.comment_more, onClickListener);
        return builder.create();
    }

    public void show(FragmentManager fragmentManager, DialogInterface.OnClickListener onClickListener) {
        this.selectIndex = selectIndex;
        this.onClickListener = onClickListener;
        show(fragmentManager, "CommentMoreDialogFragment");
    }
}
