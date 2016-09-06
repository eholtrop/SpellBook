package com.avianapps.spellbook;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by evanh on 9/5/2016.
 */
public class LabelTextView extends LinearLayout{

    @Bind(R.id.ltv_text)
    public TextView text;
    @Bind(R.id.ltv_title)
    public TextView title;

    public void setTitle(String value) {
        this.title.setText(value);
    }

    public void setText(String value) {
        this.text.setText(value);
    }
    public void setText(Spanned value) { this.text.setText(value); }

    public LabelTextView(Context context) {
        super(context);
        init(context, null);
    }

    public LabelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LabelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        View root = LayoutInflater.from(context).inflate(R.layout.text_view_label, this, true);
        ButterKnife.bind(this, root);
        if(attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LabelTextView, 0, 0);
            setText(a.getString(R.styleable.LabelTextView_ltv_text));
            setTitle(a.getString(R.styleable.LabelTextView_ltv_title));
        }
    }
}
