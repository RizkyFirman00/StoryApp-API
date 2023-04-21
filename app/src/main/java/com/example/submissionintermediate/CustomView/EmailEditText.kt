package com.example.submissionintermediate.CustomView

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatEditText
import com.example.submissionintermediate.R

class EmailEditText : AppCompatEditText {
    private lateinit var errorIcon: Drawable

    constructor(context: android.content.Context) : super(context) {
        init()
    }

    constructor(context: android.content.Context, attrs: android.util.AttributeSet) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: android.content.Context,
        attrs: android.util.AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        errorIcon = resources.getDrawable(R.drawable.baseline_error_24, null)
        this.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (text.toString().isEmpty()) {
                    setError("Email tidak boleh kosong", errorIcon)
                    setCompoundDrawablesWithIntrinsicBounds(null, null, errorIcon, null)
                } else if (!text.toString().contains("@") || !text.toString().contains(".")) {
                    setError("Email tidak valid", errorIcon)
                    setCompoundDrawablesWithIntrinsicBounds(null, null, errorIcon, null)
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }
            }
        }
    }
}