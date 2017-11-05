package br.ufs.uolchallenge

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by bira on 11/4/17.
 */

class WideImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, (measuredWidth / GOLDEN_RATIO).toInt())
    }

    companion object {
        val GOLDEN_RATIO = 1.618f
    }
}