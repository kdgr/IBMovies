package com.kaandogruer.ibmovies.view.components

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.kaandogruer.ibmovies.R

class CustomUnitFontTextView : AppCompatTextView {
    private var textSizeUnit: Float = 0.toFloat()
    private var textFont: String? = null
    private var unitText: String? = null
    private var unitType: Int = 0
    private var unitTextFont: String? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setCustomFont(context, attrs)
    }

    override fun setText(text: CharSequence?, type: TextView.BufferType) {
        if(unitText != null && unitText!!.isNotEmpty() && !text.toString().contains(unitText!!)){
            var totalText = ""
            textSizeUnit = this.textSize * 65 / 100

            if(unitType == 0) {
                totalText = text.toString() + " " + unitText
            }else {
                totalText = unitText + text.toString()
            }



            val span = SpannableString(totalText)
            if(unitType == 0) {
                span.setSpan(AbsoluteSizeSpan(textSizeUnit.toInt()),
                        totalText.length - unitText!!.length, totalText.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }else {
                span.setSpan(AbsoluteSizeSpan(textSizeUnit.toInt()),
                        0, unitText!!.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }



            super.setText(span, type)
        }else
            super.setText(text, type)
    }

    private fun setCustomFont(ctx: Context, attrs: AttributeSet) {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomUnitTextView)
        unitText = a.getString(R.styleable.CustomUnitTextView_customUnit)
        unitTextFont = a.getString(R.styleable.CustomUnitTextView_customUnitFont)
        textFont = a.getString(R.styleable.CustomUnitTextView_customTextFont)
        unitType = a.getInt(R.styleable.CustomUnitTextView_customUnitType,0)
        setText(this.text,TextView.BufferType.SPANNABLE)
        setCustomFont(ctx,textFont)
        a.recycle()
    }

    fun setCustomFont(ctx: Context, font: String?): Boolean {
        var typeface: Typeface? = null
        try {
            typeface = FontCache[font!!, ctx]
        } catch (e: Exception) {
            return false
        }

        setTypeface(typeface)
        return true
    }

    fun setCustomUnit(custonUnit: String){
        unitText = custonUnit;
        setText(this.text,TextView.BufferType.SPANNABLE)
    }
}