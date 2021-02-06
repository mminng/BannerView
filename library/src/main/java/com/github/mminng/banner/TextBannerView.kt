package com.github.mminng.banner

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.mminng.banner.adapter.BannerAdapter
import com.github.mminng.banner.layoutmanager.BannerLayoutManager

/**
 * Created by zh on 2021/1/26.
 */
class TextBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), LifecycleObserver {

    private val textBannerLayoutManager: BannerLayoutManager =
        BannerLayoutManager(context, LinearLayoutManager.VERTICAL)

    private val pagerSnap: PagerSnapHelper = PagerSnapHelper()

    private var _currentPosition: Int = 1
    private var _duration: Int = 3 * 1000
    private var _dataSize: Int = 0
    private var _canPlay: Boolean = false
    private var _scrolling: Boolean = false

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.TextBannerView, 0, 0).apply {
            try {
                val reverseLayout =
                    getBoolean(R.styleable.TextBannerView_bannerReverseLayout, false)

                setReverseLayout(reverseLayout)
            } finally {
                recycle()
            }
        }

        overScrollMode = View.OVER_SCROLL_NEVER
        layoutManager = textBannerLayoutManager
        setSpeed()
        setHasFixedSize(true)
        pagerSnap.attachToRecyclerView(this)
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    SCROLL_STATE_DRAGGING -> {
                        switch(recyclerView, pagerSnap)
                    }
                    SCROLL_STATE_IDLE -> {
                        switch(recyclerView, pagerSnap)
                    }
                    else -> {
                    }
                }
            }
        })
        setOnTouchEvent(this)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        val view: View? = getChildAt(0)
        view?.let {
            measureChild(it, widthSpec, heightSpec)
            val measuredWidth = MeasureSpec.getSize(widthSpec)
            val measuredHeight: Int = it.measuredHeight
            setMeasuredDimension(measuredWidth, measuredHeight)
        }
    }

    fun <DATA> setAdapter(adapter: BannerAdapter<DATA>) {
        if (this.adapter === adapter) return
        this.adapter = adapter
        scrollToPosition(_currentPosition)
        adapter.setOnDataChangedListener {
            scrollToPosition(1)
            _currentPosition = 1
            _dataSize = it
            play()
        }
        _dataSize = adapter.getRwaDataSize()
    }

    fun setReverseLayout(reverseLayout: Boolean) {
        textBannerLayoutManager.reverseLayout = reverseLayout
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

    private fun setSpeed(speed: Float = 700F) {
        textBannerLayoutManager.speed = speed
    }

    private fun play() {
        if (!_canPlay) return
        if (this.adapter == null) return
        if (_dataSize <= 1) return
        stop()
        postDelayed(runnable, _duration.toLong())
    }

    private val runnable: Runnable = Runnable {
        _currentPosition++
        smoothScrollToPosition(_currentPosition)
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
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

}