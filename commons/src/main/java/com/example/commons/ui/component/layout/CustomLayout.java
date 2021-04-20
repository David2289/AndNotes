package com.example.commons.ui.component.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.commons.R;


public class CustomLayout extends ConstraintLayout {

    public CustomLayout(Context context) {
        super(context);
        init(context, null);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLayout);

        int bgColor = typedArray.getColor(R.styleable.CustomLayout_bgColor, ContextCompat.getColor(context, R.color.white));
        int bgCornerRadius = typedArray.getDimensionPixelSize(R.styleable.CustomLayout_bgCornerRadius,
                (int)getResources().getDimension(R.dimen.customlayout_background_cornerradius));

        int showStroke = typedArray.getInt(R.styleable.CustomLayout_showStroke, 1);
        int strokeColor = typedArray.getColor(R.styleable.CustomLayout_strokeColor, ContextCompat.getColor(context, R.color.alto));
        int strokeWidth = typedArray.getDimensionPixelSize(R.styleable.CustomLayout_strokeWidth, (int)getResources().getDimension(R.dimen.customlayout_stroke_defaultwidth));

        int showShadow = typedArray.getInteger(R.styleable.CustomLayout_showShadow, 1);
        int shadowColor = typedArray.getColor(R.styleable.CustomLayout_shadowColor, ContextCompat.getColor(context, R.color.gallery));
        float shadowAlpha = typedArray.getFloat(R.styleable.CustomLayout_shadowAlpha, 1f);
        int shadowRadius = typedArray.getDimensionPixelSize(R.styleable.CustomLayout_shadowRadius, (int)getResources().getDimension(R.dimen.customlayout_shadow_defaultradius));
        int shadowXOffset = typedArray.getDimensionPixelSize(R.styleable.CustomLayout_shadowXOffset, (int)getResources().getDimension(R.dimen.customlayout_shadow_defaultoffset_x));
        int shadowYOffset = typedArray.getDimensionPixelSize(R.styleable.CustomLayout_shadowYOffset, (int)getResources().getDimension(R.dimen.customlayout_shadow_defaultoffset_y));

        setBackground(generateBackgroundWithShadow(
                this,
                bgColor,
                bgCornerRadius,
                showStroke,
                strokeColor,
                strokeWidth,
                showShadow,
                shadowColor,
                shadowAlpha,
                shadowRadius,
                shadowXOffset,
                shadowYOffset));

        getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            View child = getChildAt(0);
            if (child instanceof ImageView) {
                child.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight() + bgCornerRadius, bgCornerRadius);
                    }
                });
                child.setClipToOutline(true);
            }
        });

        typedArray.recycle();
    }

    public Drawable generateBackgroundWithShadow(View view,
                                                 int backgroundColor,
                                                 int backgroundCornerRadius,
                                                 int showStroke,
                                                 int strokeColor,
                                                 int strokeWidth,
                                                 int showShadow,
                                                 int shadowColor,
                                                 float shadowAlpha,
                                                 int shadowRadius,
                                                 int shadowXOffset,
                                                 int shadowYOffset) {


        //Result Drawable
        LayerDrawable resultDrawable = null;


        // Corner Radius
        float[] cornerRadiusGroup = {backgroundCornerRadius, backgroundCornerRadius, backgroundCornerRadius,
                backgroundCornerRadius, backgroundCornerRadius, backgroundCornerRadius, backgroundCornerRadius,
                backgroundCornerRadius};


        //Padding que compensará el radio que ocupará la sombra
        Rect shapePadding = new Rect();
        shapePadding.left = (shadowRadius - shadowXOffset) > 0 ? shadowRadius - shadowXOffset : 0;
        shapePadding.right = (shadowRadius + shadowXOffset) > 0 ? shadowRadius + shadowXOffset : 0;
        shapePadding.top = (shadowRadius - shadowYOffset) > 0 ? shadowRadius - shadowYOffset : 0;
        shapePadding.bottom = (shadowRadius + shadowYOffset) > 0 ? shadowRadius + shadowYOffset : 0;


        //Background Drawable
        ShapeDrawable backgroundShapeDrawable = new ShapeDrawable();
        backgroundShapeDrawable.setShape(new RoundRectShape(cornerRadiusGroup, null, null));
        backgroundShapeDrawable.getPaint().setColor(backgroundColor);


        if (showStroke == 0 && showShadow == 0) {
            resultDrawable = new LayerDrawable(new Drawable[]{backgroundShapeDrawable});
            return resultDrawable;
        }

        else if (showStroke == 1 && showShadow == 0) {
            //Stroke Drawable
            ShapeDrawable strokeShapeDrawable = getStrokeShape(strokeColor, strokeWidth, cornerRadiusGroup);
            resultDrawable = new LayerDrawable(new Drawable[]{strokeShapeDrawable, backgroundShapeDrawable});
            return resultDrawable;
        }

        else if (showStroke == 0 && showShadow == 1) {
            //Shadow Drawable
            ShapeDrawable shadowShapeDrawable = getShadowShape(shadowRadius, shadowColor, shadowXOffset, shadowYOffset, cornerRadiusGroup);
            shadowShapeDrawable.setPadding(shapePadding);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
                view.setLayerType(View.LAYER_TYPE_SOFTWARE, shadowShapeDrawable.getPaint());
            }
            resultDrawable = new LayerDrawable(new Drawable[]{shadowShapeDrawable, backgroundShapeDrawable});
            resultDrawable.setLayerInset(0, shapePadding.left, shapePadding.top, shapePadding.right, shapePadding.bottom);
            return resultDrawable;
        }
        
        else if (showStroke == 1 && showShadow == 1) {
            ShapeDrawable strokeShapeDrawable = getStrokeShape(strokeColor, strokeWidth, cornerRadiusGroup);
            ShapeDrawable shadowShapeDrawable = getShadowShape(shadowRadius, shadowColor, shadowXOffset, shadowYOffset, cornerRadiusGroup);
            shadowShapeDrawable.setPadding(shapePadding);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
                view.setLayerType(View.LAYER_TYPE_SOFTWARE, shadowShapeDrawable.getPaint());
            }
            resultDrawable = new LayerDrawable(new Drawable[]{shadowShapeDrawable, strokeShapeDrawable, backgroundShapeDrawable});
            resultDrawable.setLayerInset(0, shapePadding.left, shapePadding.top, shapePadding.right, shapePadding.bottom);
            return resultDrawable;
        }

        return resultDrawable;
    }

    private ShapeDrawable getStrokeShape(@ColorInt int strokeColor, @Dimension int strokeWidth, float[] cornerRadiusGroup) {
        //Stroke Drawable
        ShapeDrawable strokeShapeDrawable = new ShapeDrawable();
        strokeShapeDrawable.setShape(new RoundRectShape(cornerRadiusGroup, null, null));
        strokeShapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        strokeShapeDrawable.getPaint().setColor(strokeColor);
        strokeShapeDrawable.getPaint().setStrokeWidth(strokeWidth);
        return strokeShapeDrawable;
    }

    private ShapeDrawable getShadowShape(@Dimension int shadowRadius, @ColorInt int shadowColor,
                                         @Dimension int shadowXOffset, @Dimension int shadowYOffset,
                                         float[] cornerRadiusGroup) {
        //Shadow Drawable
        ShapeDrawable shadowShapeDrawable = new ShapeDrawable();
        shadowShapeDrawable.setShape(new RoundRectShape(cornerRadiusGroup, null, null));
//        shadowShapeDrawable.getPaint().setColor(backgroundColor);
        shadowShapeDrawable.getPaint().setShadowLayer(shadowRadius, shadowXOffset, shadowYOffset, shadowColor);
        return shadowShapeDrawable;
    }


    private int getColorWithAlpha(int color, float ratio) {
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(alpha, r, g, b);
    }

}
