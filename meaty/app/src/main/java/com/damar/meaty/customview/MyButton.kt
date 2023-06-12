package com.damar.meaty.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.damar.meaty.R

class MyButton : AppCompatButton {

    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable
    private var enabledTextColor: Int = 0
    private var disabledTextColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isEnabled) {
            background = enabledBackground
            setTextColor(enabledTextColor)
        } else {
            background = disabledBackground
            setTextColor(disabledTextColor)
        }

        // Customize other attributes as needed
        textSize = 12f
        gravity = Gravity.CENTER
    }

    private fun init() {
        enabledTextColor = ContextCompat.getColor(context, android.R.color.white)
        disabledTextColor = ContextCompat.getColor(context, android.R.color.darker_gray)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_enabled) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disabled) as Drawable
    }
}