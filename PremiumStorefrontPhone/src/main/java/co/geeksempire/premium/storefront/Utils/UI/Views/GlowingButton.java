/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/25/21 6:19 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import java.util.Arrays;

import co.geeksempire.premium.storefront.R;

public class GlowingButton extends AppCompatButton implements View.OnTouchListener {

    private int backgroundColor;

    private int glowColor;

    private int unpressedGlowSize;

    private int pressedGlowSize;

    private int cornerRadius;

    private float shadowDx, shadowDy = 0f;

    public GlowingButton(final Context context) {
        super(context);

        this.setStateListAnimator(null);

        setOnTouchListener(GlowingButton.this);

        initDefaultValues();
    }

    public GlowingButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.setStateListAnimator(null);

        setOnTouchListener(GlowingButton.this);

        initDefaultValues();
        parseAttrs(context, attrs);
    }

    public GlowingButton(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setStateListAnimator(null);

        setOnTouchListener(GlowingButton.this);

        initDefaultValues();
        parseAttrs(context, attrs);
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

                setBackground(getBackgroundWithGlow(GlowingButton.this, backgroundColor,
                        glowColor, cornerRadius, unpressedGlowSize, pressedGlowSize));

                break;
            case MotionEvent.ACTION_UP:

                setBackground(getBackgroundWithGlow(GlowingButton.this, backgroundColor,
                        glowColor, cornerRadius, unpressedGlowSize, unpressedGlowSize));

                break;
        }
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateButtonGlow();
    }

    @SuppressLint("ResourceAsColor")
    private void parseAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GlowButton);
        if (typedArray == null) {

            return;
        }

        for (int i = 0; i < typedArray.getIndexCount(); i++) {

            int attr = typedArray.getIndex(i);

            if (attr == R.styleable.GlowButton_backgroundColor) {

                backgroundColor = typedArray.getColor(attr, R.color.default_color);

            } else if (attr == R.styleable.GlowButton_glowColor) {

                glowColor = typedArray.getColor(attr, R.color.default_color_bright);

            } else if (attr == R.styleable.GlowButton_cornerRadius) {

                cornerRadius = typedArray.getDimensionPixelSize(attr, R.dimen.default_corner_radius);

            } else if (attr == R.styleable.GlowButton_unpressedGlowSize) {

                unpressedGlowSize = typedArray.getDimensionPixelSize(attr, R.dimen.default_unpressed_glow_size);

            } else if (attr == R.styleable.GlowButton_pressedGlowSize) {

                pressedGlowSize = typedArray.getDimensionPixelSize(attr, R.dimen.default_pressed_glow_size);

            } else if (attr == R.styleable.GlowButton_shadowDx) {

                shadowDx = typedArray.getDimension(attr, 0);

            } else if (attr == R.styleable.GlowButton_shadowDy) {

                shadowDy = typedArray.getDimension(attr, 0);

            }

        }

        typedArray.recycle();
    }

    private void updateButtonGlow() {

        setBackground(getBackgroundWithGlow(this, backgroundColor,
                glowColor, cornerRadius, unpressedGlowSize, unpressedGlowSize));

    }

    private void initDefaultValues() {

        Resources resources = getResources();
        if (resources == null) {
            return;
        }

        backgroundColor = getContext().getColor(R.color.default_color);
        glowColor = getContext().getColor(R.color.default_color_bright);

        cornerRadius = resources.getDimensionPixelSize(R.dimen.default_corner_radius);
        unpressedGlowSize = resources.getDimensionPixelSize(R.dimen.default_unpressed_glow_size);
        pressedGlowSize = resources.getDimensionPixelSize(R.dimen.default_pressed_glow_size);

    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        updateButtonGlow();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setGlowColor(int glowColor) {
        this.glowColor = glowColor;
        updateButtonGlow();
    }

    public int getGlowColor() {
        return glowColor;
    }

    public void setUnpressedGlowSize(int unpressedGlowSize) {
        this.unpressedGlowSize = unpressedGlowSize;
        updateButtonGlow();
    }

    public int getUnpressedGlowSize() {
        return unpressedGlowSize;
    }

    public void setPressedGlowSize(int pressedGlowSize) {
        this.pressedGlowSize = pressedGlowSize;
        updateButtonGlow();
    }

    public int getPressedGlowSize() {
        return pressedGlowSize;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        updateButtonGlow();
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public Drawable getBackgroundWithGlow(View view, int backgroundColor,
            int glowColor,
            int cornerRadius,
            int unPressedGlowSize,
            int pressedGlowSize) {

//        float[] radii = {0, 0, 0, 0, 0, 0, 0, 0};
//
//        radii[0] = topLeftCorner;
//        radii[1] = topLeftCorner;
//
//        radii[2] = topRightCorner;
//        radii[3] = topRightCorner;
//
//        radii[4] = bottomRightCorner;
//        radii[5] = bottomRightCorner;
//
//        radii[6] = bottomLeftCorner;
//        radii[7] = bottomLeftCorner;

        float[] outerRadius = new float[8];
        Arrays.fill(outerRadius, cornerRadius);

        Rect shapeDrawablePadding = new Rect();

        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setPadding(shapeDrawablePadding);

        shapeDrawable.getPaint().setColor(backgroundColor);
        shapeDrawable.getPaint().setShadowLayer(pressedGlowSize, shadowDx, shadowDy, glowColor);

        view.setLayerType(LAYER_TYPE_SOFTWARE, shapeDrawable.getPaint());

        shapeDrawable.setShape(new RoundRectShape(outerRadius, null, null));

        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{ shapeDrawable });
        layerDrawable.setLayerInset(0, unPressedGlowSize, unPressedGlowSize, unPressedGlowSize, unPressedGlowSize);

        return layerDrawable;
    }
}
