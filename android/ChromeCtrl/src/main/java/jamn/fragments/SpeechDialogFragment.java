package jamn.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import jamn.R;

public class SpeechDialogFragment extends LinearLayout {
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private SpeechRecognitionFragment.SpeechCallback mSpeechCallback;

    public SpeechDialogFragment(final Context context, SpeechRecognitionFragment.SpeechCallback speechCallback) {
       super(context);

        mSpeechCallback = speechCallback;
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(listener);

        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_speech_dialog, null);

        addView(v);
    }

    protected class SpeechRecognitionListener implements RecognitionListener {
        @Override
        public void onBeginningOfSpeech() {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {
            Log.d("speech", "End of speech");
        }

        @Override
        public void onError(int error) {}

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d("Partial results", partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).toString());
        }

        @Override
        public void onReadyForSpeech(Bundle params) {}

        @Override
        public void onResults(Bundle results) {
            List<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("results", matches.toString());

            String result = "";
            if (!matches.isEmpty()) {
               result = matches.get(0);
            }

            Bundle messageBundle = new Bundle();
            messageBundle.putSerializable("result", matches.get(0));
            Message message = new Message();
            message.setData(messageBundle);
            mSpeechCallback.handleMessage(message);
        }

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onEvent(int eventType, Bundle params) {}
    }
}
