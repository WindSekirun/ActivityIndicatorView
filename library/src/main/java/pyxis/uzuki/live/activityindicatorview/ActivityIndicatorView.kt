package pyxis.uzuki.live.activityindicatorview

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import pyxis.uzuki.live.activityindicatorview.ActivityIndicatorView.Constants.DEFAULT_AUTOPLAY
import pyxis.uzuki.live.activityindicatorview.ActivityIndicatorView.Constants.DEFAULT_CLOCKWISE
import pyxis.uzuki.live.activityindicatorview.ActivityIndicatorView.Constants.DEFAULT_COLOR
import pyxis.uzuki.live.activityindicatorview.ActivityIndicatorView.Constants.DEFAULT_DURATION
import pyxis.uzuki.live.activityindicatorview.ActivityIndicatorView.Constants.DEFAULT_ONESHOT
import java.util.*

class ActivityIndicatorView(context: Context, private val attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {
    private var frames = arrayListOf<Drawable>()

    /**
     * main color of Indicator
     */
    var indicatorColor = DEFAULT_COLOR

    /**
     * duration of animation
     */
    var duration = DEFAULT_DURATION

    /**
     * True if animation should be rotate clockwise
     */
    var clockwise = DEFAULT_CLOCKWISE

    /**
     * True if animation should be play once
     */
    var oneShot = DEFAULT_ONESHOT

    /**
     * True if animation should be play automatically
     */
    var autoPlay = DEFAULT_AUTOPLAY

    init {
        initView()
    }

    private fun initView() {
        this.background = getDrawableCompat(R.drawable.indicator)

        if(isInEditMode) {
            this.background = getDrawableCompat(R.drawable.indicator_3)
            return
        }

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ActivityIndicatorView, 0, 0)
            duration = typedArray.getInteger(R.styleable.ActivityIndicatorView_duration, DEFAULT_DURATION)
            indicatorColor = typedArray.getColor(R.styleable.ActivityIndicatorView_indicatorColor, DEFAULT_COLOR)
            clockwise = typedArray.getBoolean(R.styleable.ActivityIndicatorView_clockwise, DEFAULT_CLOCKWISE)
            oneShot = typedArray.getBoolean(R.styleable.ActivityIndicatorView_oneShot, DEFAULT_ONESHOT)
            autoPlay = typedArray.getBoolean(R.styleable.ActivityIndicatorView_autoPlay, DEFAULT_AUTOPLAY)
            typedArray.recycle()
        }

        val animationDrawable = context.createSpinner(indicatorColor, duration, clockwise)
        animationDrawable.isOneShot = oneShot
        this.background = animationDrawable

        if (autoPlay) {
            startAnimation()
        }
    }

    /**
     * apply new value of indicatorColor, duration, clockwise, oneShot, autoPlay
     */
    fun applyView() {
        var wasRunning = false
        val oneShot = isOneShot()

        if ((this.background as AnimationDrawable).isRunning) {
            wasRunning = true
            stopAnimation()
        }

        val animationDrawable = context.createSpinner(indicatorColor, duration, clockwise)
        animationDrawable.isOneShot = oneShot

        this.indicatorColor = indicatorColor
        this.duration = duration
        this.clockwise = clockwise
        this.background = animationDrawable

        if (autoPlay || wasRunning) {
            startAnimation()
        }
    }

    /**
     * Start animation
     */
    fun startAnimation() {
        (this.background as AnimationDrawable).start()
    }

    /**
     * Stop Animation
     */
    fun stopAnimation() {
        (this.background as AnimationDrawable).stop()
    }

    /**
     * @return True if animation should be play once, false otherwise
     */
    fun isOneShot() = (this.background as AnimationDrawable).isOneShot

    private fun Context.getIdentifierDrawable(name: String) = resources.getIdentifier(name, "drawable", packageName)
    private fun getDrawableCompat(@DrawableRes resId: Int) = ContextCompat.getDrawable(context, resId)

    private fun Context.createSpinner(@ColorInt indicatorColor: Int, duration: Int, clockwise: Boolean): AnimationDrawable {
        return if (indicatorColor == DEFAULT_COLOR && duration == DEFAULT_DURATION && clockwise == DEFAULT_CLOCKWISE) {
            this.createAnimation()
        } else {
            this.createAnimation(indicatorColor, duration, clockwise)
        }
    }

    private fun Context.createAnimation(): AnimationDrawable = getDrawableCompat(R.drawable.indicator) as AnimationDrawable
    private fun Context.createAnimation(@ColorInt indicatorColor: Int, duration: Int, clockwise: Boolean): AnimationDrawable {
        if (frames.isEmpty()) {
            (0 until 12)
                    .map { getDrawableCompat(getIdentifierDrawable("indicator_%s".format(it))) }
                    .forEach { frames.add(it) }
        }

        val animation = AnimationDrawable()
        val drawables = arrayListOf<Drawable>()

        for (drawable in frames) {
            val drwNewCopy = drawable.constantState.newDrawable().mutate()
            drwNewCopy.setColorFilter(indicatorColor, PorterDuff.Mode.MULTIPLY)
            drawables.add(drwNewCopy)
        }

        if (!clockwise) {
            Collections.reverse(drawables)
        }

        for (drawable in drawables) {
            animation.addFrame(drawable, duration)
        }
        return animation
    }


    object Constants {
        const val DEFAULT_DURATION = 60
        const val DEFAULT_COLOR = 0
        const val DEFAULT_CLOCKWISE = true
        const val DEFAULT_ONESHOT = false
        const val DEFAULT_AUTOPLAY = true
    }

}