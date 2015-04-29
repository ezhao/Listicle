package com.herokuapp.ezhao.listicle;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextDialog extends DialogFragment implements TextView.OnEditorActionListener {
    private EditText mEditText;
    private String title;

    public interface EditTextDialogListener {
        void onFinishEditText(String title, String inputText);
    }

    public EditTextDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditTextDialog newInstance(String title) {
        EditTextDialog frag = new EditTextDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_field, container);

        mEditText = (EditText) view.findViewById(R.id.etTextField);
        mEditText.setOnEditorActionListener(this);
        title = getArguments().getString("title", "Enter text");
        getDialog().setTitle(title);

        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditTextDialogListener listener = (EditTextDialogListener) getActivity();
            listener.onFinishEditText(title, mEditText.getText().toString());
            dismiss();
            return true;
        }
        return false;
    }
}
