package com.example.submissionintermediate.CustomView

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.submissionintermediate.R

class RegisterPasswordEditText : AppCompatEditText, View.OnTouchListener {
    private lateinit var errorIcon: Drawable
    private lateinit var visibleIcon: Drawable
    private lateinit var invisibleIcon: Drawable
    private var passwordVisible = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        errorIcon = resources.getDrawable(R.drawable.baseline_error_24, null)
        visibleIcon = resources.getDrawable(R.drawable.baseline_visibility_24, null)
        invisibleIcon = resources.getDrawable(R.drawable.baseline_visibility_off_24, null)
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ((s?.length ?: 0) < 8) {
                    setError("Password kurang dari 8 karakter", errorIcon)
                    setCompoundDrawablesWithIntrinsicBounds(null, null, errorIcon, null)
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    defineImage()
                }
            }
        })
    }

    override fun onTouch(p0: View?, event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_UP) return false
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonStart2: Float
            val clearButtonEnd: Float
            val clearButtonEnd2: Float
            var isClearButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (invisibleIcon.intrinsicWidth + paddingStart).toFloat()
                clearButtonEnd2 = (visibleIcon.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> isClearButtonClicked = true
                    event.x < clearButtonEnd2 -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - invisibleIcon.intrinsicWidth).toFloat()
                clearButtonStart2 = (width - paddingEnd - invisibleIcon.intrinsicWidth).toFloat()
                when {
                    event.x > clearButtonStart -> isClearButtonClicked = true
                    event.x > clearButtonStart2 -> isClearButtonClicked = true
                }
            }
            if (isClearButtonClicked) {
                passwordVisible = !passwordVisible
                val passwordInputType = if (passwordVisible) {
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                } else {
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                inputType = passwordInputType
                ContextCompat.getDrawable(
                    context,
                    if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                )?.let {
                    if (passwordVisible)
                        setCompoundDrawablesWithIntrinsicBounds(null, null, visibleIcon, null)
                    else setCompoundDrawablesWithIntrinsicBounds(null, null, invisibleIcon, null)
                }
            } else return false
        }
        return false
    }

    private fun defineImage() {
        if (passwordVisible) setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            visibleIcon,
            null
        )
        else setCompoundDrawablesWithIntrinsicBounds(null, null, invisibleIcon, null)
    }
}