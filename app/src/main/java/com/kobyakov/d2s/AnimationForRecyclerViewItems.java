package com.kobyakov.d2s;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public interface AnimationForRecyclerViewItems {
    default void setFadeAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(
                0.0f, 1.0f,
                0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        int DURATION_MILLIS = 1000;

        anim.setDuration(DURATION_MILLIS);
        view.startAnimation(anim);
    }

    default void setFadeAnimationVerTwo(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }
}
