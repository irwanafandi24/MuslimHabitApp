package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;

/**
 * Created by user on 05/06/2018.
 */

public class MyFragment extends Fragment {

    public MyFragment() {
        setupWindowAnimations();
    }

    protected void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        this.setEnterTransition(enterTransition);
//        this.setExitTransition(setupFade());
    }

    private Visibility buildEnterTransition() {
        Fade enterTransition = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            enterTransition = new Fade();
            enterTransition.setDuration(500);
        }
        // This view will not be affected by enter transition animation
        return enterTransition;
    }

    private Fade setupFade(){
        Fade fade = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            fade = new Fade();
            fade.setDuration(200);
            return fade;
        }
        return fade;
    }


}
