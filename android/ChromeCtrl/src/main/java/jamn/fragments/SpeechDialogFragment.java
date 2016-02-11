package jamn.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;

import jamn.R;

public class SpeechDialogFragment extends DialogFragment {
    OnSpeechRecognized mOnSpeechRecognized;

    public interface OnSpeechRecognized {
        void onSpeechRecognized(String result);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mOnSpeechRecognized = (OnSpeechRecognized) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSpeechRecognized");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.setContentView(new SpeechFragment(this.getContext(), new SpeechCallback()));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        return dialog;
    }

    public class SpeechCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg == null) return true;
            Object o = msg.getData().get("result");
            if (o == null) return true;

            mOnSpeechRecognized.onSpeechRecognized(o.toString());
            getDialog().dismiss();
            return false;
        }
    }
}
