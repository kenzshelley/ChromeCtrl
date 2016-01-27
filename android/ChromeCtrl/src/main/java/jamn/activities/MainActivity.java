package jamn.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.hound.android.fd.HoundSearchResult;
import com.hound.android.libphs.PhraseSpotterReader;
import com.hound.android.fd.Houndify;
import com.hound.android.sdk.VoiceSearchInfo;
import com.hound.android.sdk.audio.SimpleAudioByteStreamSource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import jamn.Constants;
import jamn.R;
import jamn.StatefulRequestInfoFactory;

public class MainActivity extends Activity  {
    private TextView textView;
    private PhraseSpotterReader phraseSpotterReader;
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private Firebase mRootRef;
    private String mUid;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Firebase.setAndroidContext(this);
        // The activity_main layout contains the com.hound.android.fd.HoundifyButton which is displayed
        // as the black microphone. When press it will load the HoundifyVoiceSearchActivity.
        setContentView( R.layout.activity_main );

        mRootRef = new Firebase("https://remote-hound.firebaseio.com/");
        mUid = "efec5b71-0bb2-4463-aabc-4f22a9319804";

        // Text view for displaying written result
        textView = (TextView)findViewById(R.id.textView);

        // Normally you'd only have to do this once in your Application#onCreate
        Houndify.get(this).setClientId( Constants.CLIENT_ID );
        Houndify.get(this).setClientKey( Constants.CLIENT_KEY );
        Houndify.get(this).setRequestInfoFactory(StatefulRequestInfoFactory.get(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPhraseSpotting();
    }

    /**
     * Called to start the Phrase Spotter
     */
    private void startPhraseSpotting() {
        if ( phraseSpotterReader == null ) {
            phraseSpotterReader = new PhraseSpotterReader(new SimpleAudioByteStreamSource());
            phraseSpotterReader.setListener( phraseSpotterListener );
            phraseSpotterReader.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // if we don't, we must still be listening for "ok hound" so teardown the phrase spotter
        if ( phraseSpotterReader != null ) {
            stopPhraseSpotting();
        }
    }

    /**
     * Called to stop the Phrase Spotter
     */
    private void stopPhraseSpotting() {
        if ( phraseSpotterReader != null ) {
            phraseSpotterReader.stop();
            phraseSpotterReader = null;
        }
    }

    /**
     * Implementation of the PhraseSpotterReader.Listener interface used to handle PhraseSpotter
     * call back.
     */
    private final PhraseSpotterReader.Listener phraseSpotterListener = new PhraseSpotterReader.Listener() {
        @Override
        public void onPhraseSpotted() {

            // It's important to note that when the phrase spotter detects "Ok Hound" it closes
            // the input stream it was provided.
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    stopPhraseSpotting();
                    // Now start the HoundifyVoiceSearchActivity to begin the search.
                    Houndify.get( MainActivity.this ).voiceSearch( MainActivity.this );
                }
            });
        }

        @Override
        public void onError(final Exception ex) {

            // for this sample we don't care about errors from the "Ok Hound" phrase spotter.

        }
    };

    /**
     * The HoundifyVoiceSearchActivity returns its result back to the calling Activity
     * using the Android's onActivityResult() mechanism.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Houndify.REQUEST_CODE) {
            final HoundSearchResult result = Houndify.get(this).fromActivityResult(resultCode, data);

            if (result.hasResult()) {
                Log.d("LOOK", result.getResponse().getResults().get(0).getWrittenResponse());
                String text = result.getResponse().getResults().get(0).getWrittenResponse();
                textView.setText(text);

                // Send text to firebase
                Map<String, String> textMap = new HashMap<>();
                textMap.put("text", text);
                mRootRef.child("users").child(mUid).child("tasks").push().setValue(textMap);
            }
            else if (result.getErrorType() != null) {
                onError(result.getException(), result.getErrorType());
            }
            else {
                textView.setText("Aborted search");
            }
        }
    }

    /**
     * Called from onActivityResult() above
     *
     * @param response
     */
    //private void onResponse(final HoundResponse response) {
    //    if (response.getResults().size() > 0) {
    //        // Required for conversational support
    //        StatefulRequestInfoFactory.get(this).setConversationState(response.getResults().get(0).getConversationState());

    //        textView.setText("Received response\n\n" + response.getResults().get(0).getWrittenResponse());

    //        /**
    //         * "Client Match" demo code.
    //         *
    //         * Houndify client apps can specify their own custom phrases which they want matched using
    //         * the "Client Match" feature. This section of code demonstrates how to handle
    //         * a "Client Match phrase".  To enable this demo first open the
    //         * StatefulRequestInfoFactory.java file in this project and and uncomment the
    //         * "Client Match" demo code there.
    //         *
    //         * Example for parsing "Client Match"
    //         */
    //        if ( response.getResults().size() > 0 ) {
    //            CommandResult commandResult = response.getResults().get( 0 );
    //            if ( commandResult.getCommandKind().equals("ClientMatchCommand")) {
    //                JsonNode matchedItemNode = commandResult.getJsonNode().findValue("MatchedItem");
    //                String intentValue = matchedItemNode.findValue( "Intent").textValue();

    //                if ( intentValue.equals("TURN_LIGHT_ON") ) {
//  //                      textToSpeechMgr.speak("Client match TURN LIGHT ON successful");
    //                }
    //                else if ( intentValue.equals("TURN_LIGHT_OFF") ) {
 // //                      textToSpeechMgr.speak("Client match TURN LIGHT OFF successful");
    //                }
    //            }
    //        }
    //    }
    //    else {
    //        textView.setText("Received empty response!");
    //    }
    //}

    /**
     * Called from onActivityResult() above
     *
     * @param ex
     * @param errorType
     */
    private void onError(final Exception ex, final VoiceSearchInfo.ErrorType errorType) {
        textView.setText(errorType.name() + "\n\n" + exceptionToString(ex));
    }

    private static String exceptionToString(final Exception ex) {
        try {
            final StringWriter sw = new StringWriter(1024);
            final PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.close();
            return sw.toString();
        }
        catch (final Exception e) {
            return "";
        }
    }
}
