package com.datechnologies.androidtest.login.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.login.viewmodel.LoginViewModel;
import com.datechnologies.androidtest.network.rapptrlabs.result.ErrorResult;
import com.datechnologies.androidtest.network.rapptrlabs.result.Result;
import com.datechnologies.androidtest.network.rapptrlabs.result.SuccessResult;
import com.datechnologies.androidtest.util.Prefs;

@SuppressLint("ValidFragment")
public class LoginDialogFragment extends DialogFragment {
    private final LoginViewModel.LoginResult sLoginResult;
    private OnOkListener mListener;

    @SuppressLint("ValidFragment")
    public LoginDialogFragment(LoginViewModel.LoginResult sLoginResult){
        this.sLoginResult = sLoginResult;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater  = getActivity().getLayoutInflater();

        View root = inflater.inflate(R.layout.dialog_login, null);
        TextView mCodeMessageTv = root.findViewById(R.id.code_tv);
        TextView mMessageTv = root.findViewById(R.id.message_tv);
        TextView mTimeTv = root.findViewById(R.id.time_tv);
        builder.setView(root);


        long time = sLoginResult.time;
        Result typeResult = sLoginResult.result;

        if(typeResult instanceof SuccessResult){
            SuccessResult successResult = (SuccessResult)typeResult;
            mCodeMessageTv.setText(successResult.getError());
            mMessageTv.setText(successResult.getMessage());

            Prefs.setLogin(getActivity(), true, 1);
        } else {
            ErrorResult errorResult = (ErrorResult) typeResult;
            mCodeMessageTv.setText(errorResult.getError());
            mMessageTv.setText(errorResult.getMessage());
            Prefs.setLogin(getActivity(), true, 0);
        }
        mTimeTv.setText(Long.toString(time));

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            mListener.onOkClick();
            dialog.dismiss();
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        try{
            mListener = (OnOkListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnOkListener.");
        }
    }

    public interface OnOkListener{
        void onOkClick();
    }
}
