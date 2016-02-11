package jamn.fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager;

import jamn.R;

public class SpeechRecognitionFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.setContentView(new SpeechDialogFragment(this.getContext(), new SpeechCallback()));

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        return dialog;
    }

    public class SpeechCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            getDialog().dismiss();
            return false;
        }
    }
}
