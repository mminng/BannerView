package com.github.mminng.banner

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.mminng.banner.adapter.BannerAdapter
import com.github.mminng.banner.annotation.BannerOrientation
import com.github.mminng.banner.annotation.IndicatorGravity
import com.github.mminng.banner.decoration.BannerDecoration
import com.github.mminng.banner.decoration.IndicatorDecoration
import com.github.mminng.banner.indicator.IndicatorAdapter
import com.github.mminng.banner.indicator.IndicatorSelector
import com.github.mminng.banner.layoutmanager.BannerLayoutManager
import com.github.mminng.banner.utils.dp2px

/**
 * Created by zh on 1/21/21.
 */
class BannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LifecycleObserver {

    private val banner: RecyclerView = RecyclerView(context)
    private val bannerLayoutManager: BannerLayoutManager =
        BannerLayoutManager(context, LinearLayoutManager.HORIZONTAL)

    private val indicator: RecyclerView = RecyclerView(context)
    private val indicatorLayoutParams: LayoutParams = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT,
        Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
    )
    private val indicatorLayoutManager: LinearLayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    private val indicatorAdapter: IndicatorAdapter = IndicatorAdapter()

    private val pagerSnap: PagerSnapHelper = PagerSnapHelper()

    private var _currentPosition: Int = 1
    private var _duration: Int = 3 * 1000
    private var _dataSize: Int = 0
    private var _canPlay: Boolean = false
    private var _scrolling: Boolean = false
    private val indicatorDefaultPadding: Float = 5F
    private var _bannerPadding: Int = 0

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.BannerView, 0, 0).apply {
            try {
                _bannerPadding = getDimensionPixelSize(R.styleable.BannerView_bannerPadding, 0)
                val reverseLayout = getBoolean(R.styleable.BannerView_bannerReverseLayout, false)
                val gravity = getInt(R.styleable.BannerView_bannerIndicatorGravity, 0)
                val drawable = getResourceId(
                    R.styleable.BannerView_bannerIndicator,
                    R.drawable.default_indicator_unselected
                )
                val selectedDrawable = getResourceId(
                    R.styleable.BannerView_bannerIndicatorSelected,
                    R.drawable.default_indicator_selected
                )

                setBannerPadding(_bannerPadding)
                setReverseLayout(reverseLayout)
                setIndicatorGravity(getIndicatorGravity(gravity))
                setIndicatorDrawable(drawable)
                setIndicatorSelectedDrawable(selectedDrawable)
            } finally {
                recycle()
            }
        }

        banner.apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            clipToPadding = false
            layoutManager = bannerLayoutManager
            setSpeed()
            setHasFixedSize(true)
            pagerSnap.attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        RecyclerView.SCROLL_STATE_DRAGGING -> {
                            switch(recyclerView, pagerSnap)
                        }
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            switch(recyclerView, pagerSnap)
                        }
                        else -> {
                        }
                    }
                }
            })
            setOnTouchEvent(this)
        }

        indicator.apply {
            layoutParams = indicatorLayoutParams
            if (_bannerPadding <= 0) {
                setPadding(
                    dp2px(indicatorDefaultPadding, context).toInt(),
                    0,
                    dp2px(indicatorDefaultPadding, context).toInt(),
                    dp2px(indicatorDefaultPadding, context).toInt()
                )
            }
            layoutManager = indicatorLayoutManager
            adapter = indicatorAdapter
            addItemDecoration(IndicatorDecoration())
        }
        addView(banner, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(indicator)
    }

    fun <DATA> setAdapter(adapter: BannerAdapter<DATA>) {
        if (banner.adapter === adapter) return
        banner.adapter = adapter
        banner.scrollToPosition(_currentPosition)
        adapter.setOnDataChangedListener {
            banner.scrollToPosition(1)
            _currentPosition = 1
            _dataSize = it
            submitIndicator(it)
            play()
        }
        _dataSize = adapter.getRwaDataSize()
        submitIndicator(adapter.getRwaDataSize())
    }

    fun setBannerPadding(padding: Float) {
        if (padding > 0F) {
            val paddingPx = dp2px(padding, context)
            banner.setPadding(
                paddingPx.toInt(),
                0,
                0,
                0,
            )
            banner.addItemDecoration(BannerDecoration(padding, true))

            val indicatorPaddingPx = dp2px(indicatorDefaultPadding, context)
            indicator.setPadding(
                (paddingPx + indicatorPaddingPx * 1.5).toInt(),
                0,
                (paddingPx + indicatorPaddingPx * 1.5).toInt(),
                indicatorPaddingPx.toInt(),
            )
        }
    }

    fun setReverseLayout(reverseLayout: Boolean) {
        bannerLayoutManager.reverseLayout = reverseLayout
        indicatorLayoutManager.reverseLayout = reverseLayout
    }

    fun setIndicatorDrawable(@DrawableRes unselectedRes: Int) {
        indicatorAdapter.setIndicatorDrawable(unselectedRes)
    }

    fun setIndicatorSelectedDrawable(@DrawableRes selectedRes: Int) {
        indicatorAdapter.setIndicatorSelectedDrawable(selectedRes)
    }

    fun setIndicatorGravity(@IndicatorGravity gravity: Int) {
        indicatorLayoutParams.gravity = Gravity.BOTTOM or gravity
    }

    fun play(/*milliseconds*/duration: Int = _duration) {
        _duration = duration
        _canPlay = true
        play()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        play()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop() {
        removeCallbacks(runnable)
    }

    private fun setSpeed(speed: Float = 75F) {
        bannerLayoutManager.speed = speed
    }

    private fun setBannerPadding(paddingPx: Int) {
        if (paddingPx > 0) {
            banner.setPadding(
                paddingPx,
                0,
                0,
                0
            )
            banner.addItemDecoration(BannerDecoration(paddingPx.toFloat(), false))

            val indicatorPaddingPx = dp2px(indicatorDefaultPadding, context)
            indicator.setPadding(
                (paddingPx + indicatorPaddingPx * 1.5).toInt(),
                0,
                (paddingPx + indicatorPaddingPx * 1.5).toInt(),
                indicatorPaddingPx.toInt()
            )
        }
    }

    private fun setOrientation(@BannerOrientation orientation: Int) {
        bannerLayoutManager.orientation = orientation
        indicatorLayoutManager.orientation = orientation
    }

    private fun getIndicatorGravity(value: Int): Int {
        return when (value) {
            0 -> {
                IndicatorGravity.CENTER
            }
            1 -> {
                IndicatorGravity.START
            }
            2 -> {
                IndicatorGravity.END
            }
            else -> {
                IndicatorGravity.CENTER
            }
        }
    }

    private fun submitIndicator(dataSize: Int) {
        val indicatorData: MutableList<IndicatorSelector> = arrayListOf()
        for (index in 0 until dataSize) {
            indicatorData.add(IndicatorSelector(index == 0))
        }
        indicatorAdapter.submit(indicatorData)
    }

    private fun play() {
        if (!_canPlay) return
        if (banner.adapter == null) return
        if (_dataSize <= 1) return
        stop()
        postDelayed(runnable, _duration.toLong())
    }

    private val runnable: Runnable = Runnable {
        _currentPosition++
        banner.smoothScrollToPosition(_currentPosition)
        play()
    }

    private fun setOnTouchEvent(view: View) {
        view.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    stop()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!_scrolling) {
                        stop()
                        _scrolling = true
                    }
                }
                MotionEvent.ACTION_UP -> {
                    play()
                    _scrolling = false
                }
            }
            performClick()
            false
        }
    }

    private fun switch(recyclerView: RecyclerView, snap: PagerSnapHelper) {
        if (_dataSize <= 1) return
        val layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        val currentView: View = snap.findSnapView(layoutManager) ?: return
        val bannerCount: Int = layoutManager.itemCount
        _currentPosition = layoutManager.getPosition(currentView)
        when (_currentPosition) {
            0 -> {
                _currentPosition = bannerCount - 2
                recyclerView.scrollToPosition(_currentPosition)
            }
            bannerCount - 1 -> {
                _currentPosition = 1
                recyclerView.scrollToPosition(_currentPosition)
            }
            else -> {
            }
        }
        indicatorAdapter.switchToCurrent(_currentPosition - 1)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

}