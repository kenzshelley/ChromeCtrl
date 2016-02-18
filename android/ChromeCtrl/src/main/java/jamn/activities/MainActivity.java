package jamn.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jamn.R;
import jamn.models.Task;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private TextView mSpeechTextView;
    private Firebase mRootRef;
    private String mUid;
    private RelativeLayout mRelativeLayout;
    private ImageView mWaitingIcon;
    protected static final int REQUEST_OK = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Firebase.setAndroidContext(this);
        setContentView( R.layout.activity_main );

        // Setup toolbar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("ChromeCtrl");
        //toolbar.inflateMenu(R.menu.toolbar);
        //final Activity hack = this;
        //toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        //    @Override
        //    public boolean onMenuItemClick(MenuItem item) {
        //        switch (item.getItemId()) {
        //            case R.id.logout:
        //                Log.d("MainActivity", "Clicked logout!");
        //                mRootRef.unauth();

        //                // Start main activity
        //                Intent i = new Intent(hack, LoginActivity.class);
        //                startActivity(i);
        //                finish();
        //                return true;
        //        }
        //        return false;
        //    }
        //});
        final Activity hack = this;

        mRootRef = new Firebase("https://remote-hound.firebaseio.com/");
        mUid = mRootRef.getAuth().getUid();

        // Set listener for completed tasks
        mRootRef.child("users").child(mUid).child("completed_tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Main", "Data changed");
                List<Task> tasks = new ArrayList<Task>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task task = snapshot.getValue(Task.class);
                    task.setKey(snapshot.getKey());
                    tasks.add(task);
                }

                if (!tasks.isEmpty()) {
                    Task task = tasks.get(tasks.size() - 1);

                    TextView textView = (TextView) findViewById(R.id.textView);
                    Log.d("Main", "Setting new task");
                    textView.setText(task.getName());
                    mRootRef.child("users").child(mUid).child("completed_tasks").child(task.getKey()).removeValue();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        // Text view for displaying written result
        mTextView = (TextView)findViewById(R.id.textView);
        mTextView.setText("Tap Anywhere");
        mTextView.setTextColor(getResources().getColor(R.color.white));
        mSpeechTextView = (TextView) findViewById(R.id.speechTextView);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.main_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "about to start speech");
                Intent i = new Intent(hack, SpeechActivity.class);
                startActivityForResult(i, REQUEST_OK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("MainActivity", "in onActivityResult!");
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            String result = data.getDataString();
            mSpeechTextView.setText(result);
            mSpeechTextView.setTextColor(getResources().getColor(R.color.white));

            // Send text to firebase
            Map<String, String> textMap = new HashMap<>();
            textMap.put("text", result);
            mRootRef.child("users").child(mUid).child("tasks").push().setValue(textMap);
        }
    }
}
