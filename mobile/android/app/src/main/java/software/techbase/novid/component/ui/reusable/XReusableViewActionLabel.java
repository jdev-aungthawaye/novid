package software.techbase.novid.component.ui.reusable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.util.Objects;

import software.techbase.novid.R;

/**
 * Created by Wai Yan on 2019-07-17.
 */
public class XReusableViewActionLabel extends LinearLayoutCompat {

    public XReusableViewActionLabel(Context context) {
        super(context);
    }

    @SuppressLint("WrongConstant")
    public XReusableViewActionLabel(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XReusableViewActionLabel, 0, 0);

        String description = typedArray.getString(R.styleable.XReusableViewActionLabel_labelDescription);
        Drawable icon = typedArray.getDrawable(R.styleable.XReusableViewActionLabel_imvIcon);

        typedArray.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Objects.requireNonNull(inflater).inflate(R.layout.reusable_view_action_label, this, true);

        AppCompatTextView lblDescription = findViewById(R.id.lblDescription);
        AppCompatImageView imvIcon = findViewById(R.id.imvIcon);

        lblDescription.setText(description);
        imvIcon.setImageDrawable(icon);
    }

    public XReusableViewActionLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
