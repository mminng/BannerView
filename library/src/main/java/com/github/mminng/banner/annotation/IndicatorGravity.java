package com.github.mminng.banner.annotation;

import android.view.Gravity;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.github.mminng.banner.annotation.IndicatorGravity.CENTER;
import static com.github.mminng.banner.annotation.IndicatorGravity.END;
import static com.github.mminng.banner.annotation.IndicatorGravity.START;

/**
 * Created by zh on 1/26/21.
 */
@IntDef({CENTER, START, END})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface IndicatorGravity {

    int CENTER = Gravity.CENTER_HORIZONTAL;
    int START = Gravity.START;
    int END = Gravity.END;

}