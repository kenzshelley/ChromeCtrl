package jamn.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jamn.R;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Firebase mRootRef;
    private String mUid;
    protected static final int REQUEST_OK = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Firebase.setAndroidContext(this);
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

        // Set listener for completed tasks
        mRootRef.child("users").child(mUid).child("completed_tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren().iterator().hasNext()) {
                    Object ob = dataSnapshot.getChildren().iterator().next().getValue();
                    Log.d("Main", ob.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String result = thingsYouSaid.get(0);
            mTextView.setText(result);
            mTextView.setTextColor(getResources().getColor(R.color.colorPrimary));

            // Send text to firebase
            Map<String, String> textMap = new HashMap<>();
            textMap.put("text", result);
            mRootRef.child("users").child(mUid).child("tasks").push().setValue(textMap);
        }
    }
}
