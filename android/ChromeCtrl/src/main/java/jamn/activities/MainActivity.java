package jamn.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
import jamn.fragments.SpeechRecognitionFragment;
import jamn.models.Task;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Firebase mRootRef;
    private String mUid;
    private RelativeLayout mRelativeLayout;
    protected static final int REQUEST_OK = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Firebase.setAndroidContext(this);
        setContentView( R.layout.activity_main );

        // Setup toolbar
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

        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        mRootRef = new Firebase("https://remote-hound.firebaseio.com/");
        mUid = mRootRef.getAuth().getUid();

        // Set listener for completed tasks
        mRootRef.child("users").child(mUid).child("completed_tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Task> tasks = new ArrayList<Task>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tasks.add(snapshot.getValue(Task.class));
                }

                List<Task> taskList = new ArrayList<Task>();
                if (!tasks.isEmpty()) {
                    // Get the most recent task
                    Task task = tasks.get(tasks.size() - 1);
                    LayoutInflater layoutInflater = LayoutInflater.from(hack);
                    View view = layoutInflater.inflate(R.layout.task_item, mRelativeLayout, false);

                    TextView textView = (TextView) view.findViewById(R.id.taskName);
                    textView.setText(task.getName());

                    if (mRelativeLayout.findViewById(R.id.cardView) != null) {
                        mRelativeLayout.removeView(mRelativeLayout.findViewById(R.id.cardView));
                    }
                    mRelativeLayout.addView(view);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        // Text view for displaying written result
        mTextView = (TextView)findViewById(R.id.textView);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                SpeechRecognitionFragment speechDialog = new SpeechRecognitionFragment();
                speechDialog.show(fragmentManager, null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String result = thingsYouSaid.get(0);
            mTextView.setText("\"" + result + "\"");
            mTextView.setTextColor(getResources().getColor(R.color.colorPrimary));

            // Send text to firebase
            Map<String, String> textMap = new HashMap<>();
            textMap.put("text", result);
            mRootRef.child("users").child(mUid).child("tasks").push().setValue(textMap);
        }
    }
}
