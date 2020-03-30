package software.techbase.novid.component.ui.reusable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.util.Objects;

import software.techbase.novid.R;

/**
 * Created by Wai Yan on 2019-07-17.
 */
public class XReusableViewDashboard extends LinearLayoutCompat {

    public XReusableViewDashboard(Context context) {
        super(context);
    }

    @SuppressLint("WrongConstant")
    public XReusableViewDashboard(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XReusableViewDashboard, 0, 0);

        String description = typedArray.getString(R.styleable.XReusableViewDashboard_labelDescription);
        String counter = typedArray.getString(R.styleable.XReusableViewDashboard_labelCounter);

        typedArray.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Objects.requireNonNull(inflater).inflate(R.layout.reusable_view_dashboard, this, true);

        AppCompatTextView lblDescription = findViewById(R.id.lblDescription);
        AppCompatTextView lblCounter = findViewById(R.id.lblCounter);

        lblDescription.setText(description);
        lblCounter.setText(counter);
    }

    public XReusableViewDashboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
