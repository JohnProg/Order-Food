package com.tectime.johnpaul.orderfood.common;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.tectime.johnpaul.orderfood.R;

public class CustomDialogMessage extends AlertDialog {
    private EditText editText;

    public CustomDialogMessage(Context context) {
        super(context);
        initViews();
    }

    private void initViews() {
        View view = View.inflate(getContext(), R.layout.custom_dialog_message, null);
        editText = (EditText) view.findViewById(R.id.editText);
        setView(view);
    }

    public void setData(String title, String message, int icon) {
        if (!title.isEmpty()) {
            setTitle(title);
        }

        if (!message.isEmpty()) {
            setMessage(message);
        }
        setIcon(icon);
        editText.setVisibility(View.VISIBLE);
        editText.setText(message);
    }
}
