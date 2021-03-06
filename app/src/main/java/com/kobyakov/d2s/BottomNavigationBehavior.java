package com.kobyakov.d2s;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

class BottomNavigationBehavior extends CoordinatorLayout.Behavior<View> {
    public BottomNavigationBehavior(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*@Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        child.setTranslationY(max(0f, min(child.getHeight(), child.getTranslationY() + dy)));
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }*/

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            updateSnackBar(child, dependency);
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    private void updateSnackBar(View child, View snackbarLayout) {
        if (snackbarLayout.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarLayout.getLayoutParams();
            //int bottomNavigationViewHeight = child.findViewById(child.getId()).getHeight();
            int bottomNavigationViewHeight = child.getHeight();

            //params.setAnchorId(child.getId());
            /*params.anchorGravity = Gravity.TOP;
            params.gravity = Gravity.TOP;*/
            params.setMargins(0, 0, 0, bottomNavigationViewHeight);
            snackbarLayout.setLayoutParams(params);
        }
    }
}