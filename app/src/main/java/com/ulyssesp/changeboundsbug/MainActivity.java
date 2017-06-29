package com.ulyssesp.changeboundsbug;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    boolean mEnded;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEnded = false;

        final View t1 = findViewById(R.id.t1);
        final View t2 = findViewById(R.id.t2);
        final ViewGroup scc = (ViewGroup) findViewById(R.id.scene_change_cont);

        findViewById(R.id.go)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Transition tr1 = new ChangeBounds().addTarget(t1).setDuration(600);
                    Transition tr2 = new ChangeBounds().addTarget(t2).setDuration(600);
                    Transition tr3 = new ChangeBounds().addTarget(scc).setDuration(300);
                    Transition tall = new TransitionSet().addTransition(tr1).addTransition(tr2).addTransition(tr3);

                    TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.content), tall);

                    FrameLayout.LayoutParams lps1 = (FrameLayout.LayoutParams) t1.getLayoutParams();
                    lps1.leftMargin = lps1.leftMargin < 400 ? 400 : 200;
                    lps1.topMargin = 100;
                    t1.setLayoutParams(lps1);

                    FrameLayout.LayoutParams lps2 = (FrameLayout.LayoutParams) t2.getLayoutParams();
                    lps2.leftMargin = lps2.leftMargin < 400 ? 400 : 200;;
                    lps2.topMargin = 200;
                    t2.setLayoutParams(lps2);

                    Scene go = Scene.getSceneForLayout(scc, mEnded ? R.layout.scene_start : R.layout.scene_end, MainActivity.this);
                    TransitionManager.go(go);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scc.findViewById(R.id.text).requestLayout();
                            mEnded = !mEnded;
                        }
                    }, 200);
                }
            });
    }
}
