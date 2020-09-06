package com.kaandogruer.ibmovies.view.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.kaandogruer.ibmovies.R


class CustomUnitFontButton : AppCompatTextView {
    private var textFont: String? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setCustomFont(context, attrs)
    }


    private fun setCustomFont(ctx: Context, attrs: AttributeSet) {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomUnitTextView)
        textFont = a.getString(R.styleable.CustomUnitTextView_customTextFont)
        setText(this.text,TextView.BufferType.SPANNABLE)
        setCustomFont(ctx,textFont)
        a.recycle()
    }

    fun setCustomFont(ctx: Context, font: String?): Boolean {
        var typeface: Typeface? = null
        try {
            typeface = FontCache[font!!, ctx]
        } catch (e: Exception) {
            Log.e(TAG, "Unable to load typeface: " + e.message)
            return false
        }

        setTypeface(typeface)
        return true
    }


    companion object {
        private val TAG = "CustomUnitFontButton"
    }


}