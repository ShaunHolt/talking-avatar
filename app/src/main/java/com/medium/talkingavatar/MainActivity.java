package com.medium.talkingavatar;

import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.medium.talkingavatar.avatar.AvatarView;
import com.medium.talkingavatar.avatar.Utils;

public class MainActivity extends AppCompatActivity {

    private AvatarView mAvatarView;
    private EditText mText;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAvatarView = findViewById(R.id.avatar_view);
        mFloatingActionButton = findViewById(R.id.say_it);

        RelativeLayout topPanel = findViewById(R.id.top_panel);
        topPanel.getLayoutParams().height = Utils.getWrapperHeight();

        mText = findViewById(R.id.text);
    }

    public void onSayIt(View view) {
        String text = mText.getText().toString();
        mFloatingActionButton.setEnabled(false);

        new TextToSpeechAsync(mAvatarView, mFloatingActionButton, text).execute();
    }
}
