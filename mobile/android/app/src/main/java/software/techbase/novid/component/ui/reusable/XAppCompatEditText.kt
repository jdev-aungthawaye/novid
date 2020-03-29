package software.techbase.novid.component.ui.reusable

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText
import software.techbase.novid.R

/**
 * Created by Wai Yan on 12/8/18.
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
class XAppCompatEditText : TextInputEditText {

    // Clear button image
    private lateinit var mClearButtonImage: Drawable

    constructor(context: Context) : super(context) {
        setupButton()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupButton()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setupButton()
    }

    private fun setupButton() {

        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_backspace, null)!!

        addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                if (s?.isEmpty()!!) {
                    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
                } else {
                    mClearButtonImage =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_backspace, null)!!
                    setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        mClearButtonImage,
                        null
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        // touch in X button to clear the current text
        setOnTouchListener(OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                    this.setText("")
                    return@OnTouchListener true
                }
            }
            return@OnTouchListener false
        })
    }
}
