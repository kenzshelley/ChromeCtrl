package jamn.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import jamn.fragments.SpeechDialogFragment;

public class SpeechActivity extends AppCompatActivity implements SpeechDialogFragment.OnSpeechRecognized {

    public void onSpeechRecognized(String result) {
        Intent i = new Intent();
        i.setData(Uri.parse(result));
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SpeechDialogFragment speechDialog = new SpeechDialogFragment();
        speechDialog.show(fragmentManager, null);
    }
}
