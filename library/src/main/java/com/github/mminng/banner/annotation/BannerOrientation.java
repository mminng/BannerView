package com.github.mminng.banner.annotation;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.github.mminng.banner.annotation.BannerOrientation.HORIZONTAL;
import static com.github.mminng.banner.annotation.BannerOrientation.VERTICAL;

/**
 * Created by zh on 1/26/21.
 */
@IntDef({HORIZONTAL, VERTICAL})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface BannerOrientation {

    int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    int VERTICAL = LinearLayoutManager.VERTICAL;

}