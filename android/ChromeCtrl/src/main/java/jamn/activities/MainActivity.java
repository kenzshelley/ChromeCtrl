package jamn.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.hound.android.fd.HoundSearchResult;
import com.hound.android.libphs.PhraseSpotterReader;
import com.hound.android.fd.Houndify;
import com.hound.android.sdk.VoiceSearchInfo;
import com.hound.android.sdk.audio.SimpleAudioByteStreamSource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jamn.Constants;
import jamn.R;
import jamn.StatefulRequestInfoFactory;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private PhraseSpotterReader phraseSpotterReader;
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private Firebase mRootRef;
    private String mUid;
    protected static final int REQUEST_OK = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Firebase.setAndroidContext(this);
        // The activity_main layout contains the com.hound.android.fd.HoundifyButton which is displayed
        // as the black microphone. When press it will load the HoundifyVoiceSearchActivity.
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCtrl");
        toolbar.inflateMenu(R.menu.toolbar);
        final Activity hack = this;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        Log.d("MainActivity", "Clicked logout!");
                        mRootRef.unauth();

                        // Start main activity
                        Intent i = new Intent(hack, LoginActivity.class);
                        startActivity(i);
                        finish();
                        return true;
                }
                return false;
            }

        });

        mRootRef = new Firebase("https://remote-hound.firebaseio.com/");
        mUid = mRootRef.getAuth().getUid();

        // Text view for displaying written result
        mTextView = (TextView)findViewById(R.id.textView);

        ImageButton button = (ImageButton) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try {
                    startActivityForResult(i, REQUEST_OK);
                } catch (Exception e) {
                    Toast.makeText(getParent(), "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
                }
            }
        });


        // Normally you'd only have to do this once in your Application#onCreate
        Houndify.get(this).setClientId( Constants.CLIENT_ID );
        Houndify.get(this).setClientKey( Constants.CLIENT_KEY );
        Houndify.get(this).setRequestInfoFactory(StatefulRequestInfoFactory.get(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Main", "In the activity!");
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {

            Log.d("hi", "in here");
            Log.d("hi", data.toString());
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.d("hi", thingsYouSaid.toString());
            mTextView.setText(thingsYouSaid.get(0));
            mTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    //    startPhraseSpotting();
    }

    ///**
    // * Called to start the Phrase Spotter
    // */
    //private void startPhraseSpotting() {
    //    if ( phraseSpotterReader == null ) {
    //        phraseSpotterReader = new PhraseSpotterReader(new SimpleAudioByteStreamSource());
    //        phraseSpotterReader.setListener( phraseSpotterListener );
    //        phraseSpotterReader.start();
    //    }
    //}

    //@Override
    //protected void onPause() {
    //    super.onPause();
    //    // if we don't, we must still be listening for "ok hound" so teardown the phrase spotter
    //    if ( phraseSpotterReader != null ) {
    //        stopPhraseSpotting();
    //    }
    //}

    ///**
    // * Called to stop the Phrase Spotter
    // */
    //private void stopPhraseSpotting() {
    //    if ( phraseSpotterReader != null ) {
    //        phraseSpotterReader.stop();
    //        phraseSpotterReader = null;
    //    }
    //}

    ///**
    // * Implementation of the PhraseSpotterReader.Listener interface used to handle PhraseSpotter
    // * call back.
    // */
    //private final PhraseSpotterReader.Listener phraseSpotterListener = new PhraseSpotterReader.Listener() {
    //    @Override
    //    public void onPhraseSpotted() {

    //        // It's important to note that when the phrase spotter detects "Ok Hound" it closes
    //        // the input stream it was provided.
    //        mainThreadHandler.post(new Runnable() {
    //            @Override
    //            public void run() {
    //                stopPhraseSpotting();
    //                // Now start the HoundifyVoiceSearchActivity to begin the search.
    //                Houndify.get( MainActivity.this ).voiceSearch( MainActivity.this );
    //            }
    //        });
    //    }

    //    @Override
    //    public void onError(final Exception ex) {

    //        // for this sample we don't care about errors from the "Ok Hound" phrase spotter.

    //    }
    //};

    ///**
    // * The HoundifyVoiceSearchActivity returns its result back to the calling Activity
    // * using the Android's onActivityResult() mechanism.
    // */
    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);

    //    if (requestCode == Houndify.REQUEST_CODE) {
    //        final HoundSearchResult result = Houndify.get(this).fromActivityResult(resultCode, data);

    //        if (result.hasResult()) {
    //            Log.d("LOOK", result.getResponse().getResults().get(0).getWrittenResponse());
    //            String text = result.getResponse().getResults().get(0).getWrittenResponse();
    //            textView.setText(text);

    //            // Send text to firebase
    //            Map<String, String> textMap = new HashMap<>();
    //            textMap.put("text", text);
    //            mRootRef.child("users").child(mUid).child("tasks").push().setValue(textMap);
    //        }
    //        else if (result.getErrorType() != null) {
    //            onError(result.getException(), result.getErrorType());
    //        }
    //        else {
    //            textView.setText("Aborted search");
    //        }
    //    }
    //}


    ///**
    // * Called from onActivityResult() above
    // *
    // * @param ex
    // * @param errorType
    // */
    //private void onError(final Exception ex, final VoiceSearchInfo.ErrorType errorType) {
    //    textView.setText(errorType.name() + "\n\n" + exceptionToString(ex));
    //}

    //private static String exceptionToString(final Exception ex) {
    //    try {
    //        final StringWriter sw = new StringWriter(1024);
    //        final PrintWriter pw = new PrintWriter(sw);
    //        ex.printStackTrace(pw);
    //        pw.close();
    //        return sw.toString();
    //    }
    //    catch (final Exception e) {
    //        return "";
    //    }
    //}
}
