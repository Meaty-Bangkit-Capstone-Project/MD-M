package com.damar.meaty.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.damar.meaty.R

class MyPassword : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        hint = context.getString(R.string.password)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        transformationMethod = PasswordTransformationMethod.getInstance()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Kosong
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = getErrorMessage(s.length)
            }

            override fun afterTextChanged(s: Editable) {
                // Kosong
            }
        })
    }

    private fun getErrorMessage(passwordLength: Int): String? {
        return when {
            passwordLength < 8 -> context.getString(R.string.error_minimum_password)
            else -> null
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}
