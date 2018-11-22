package com.cong.cong_music.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cong.cong_music.R;
import com.cong.cong_music.util.DensityUtil;
import com.cong.cong_music.util.ImageUtil;

/**
 * @author Cong
 * @date 2018/9/11
 * @description
 */
public class RecordThumbView extends View implements ValueAnimator.AnimatorUpdateListener {
    public static final float CD_BG_SCALE = 1.333F;                 //Cd白圈背景的比例
    private static final int CD_THUMB_LINE_HEIGHT = 1;              //指针下面那条线高度
    private static final float THUMB_ROTATION_PAUSE = -25F;         //指针在停止时候的，旋转角度
    private static final float THUMB_ROTATION_PLAY = 0F;            //指针在播放时候旋转的角度
    private static final long THUMB_DURATION = 300;                 //指针动画的播放时间
    private static final float THUMB_WIDTH_SCALE = 2.7F;            //指针宽度和1080的比值
    private float thumbRotation = THUMB_ROTATION_PAUSE;             //指针的旋转角度，默认，是不播放状态
    private Paint paint;                                            //绘制使用的画笔
    private Drawable cdThumbLine;                                   //指针上面的那条线
    private Drawable cdBg;                                          //白圈
    private ValueAnimator playThumbAnimator;                        //开始播放指针的移动动画
    private ValueAnimator pauseThumbAnimator;                       //停止播放指针的移动动画
    public static final float CD_BG_TOP_SCALE = 17.052F;            //CD白圈背景到顶部的比例
    private static final int THUMB_CIRCLE_WIDTH = 33;               //指针上面那个原点的宽度，dp
    private static final int THUMB_HEIGHT = 138;                    //指针的高度，原图px
    private Point thumbPoint;                                       //指针绘制的坐标
    private Point thumbRotationPoint;                               //指针旋转的坐标
    private Bitmap cdThumb;                                         //指针的bitmap
    private Matrix thumbMatrix = new Matrix();                      //指针旋转使用的矩阵
    private static final int THUMB_WIDTH = 92;                      //指针的宽度，px


    public RecordThumbView(Context context) {
        this(context,null);
    }

    public RecordThumbView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RecordThumbView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        //画笔
        paint = new Paint();
        paint.setAntiAlias(true);

        cdThumbLine = getResources().getDrawable(R.drawable.shape_cd_thumb_line);
        cdBg = getResources().getDrawable(R.drawable.shape_cd_bg);

        //创建指针的属性动画
        playThumbAnimator = ValueAnimator.ofFloat(THUMB_ROTATION_PAUSE, THUMB_ROTATION_PLAY);
        playThumbAnimator.setDuration(THUMB_DURATION);
        playThumbAnimator.addUpdateListener(this);

        pauseThumbAnimator = ValueAnimator.ofFloat(THUMB_ROTATION_PLAY, THUMB_ROTATION_PAUSE);
        pauseThumbAnimator.setDuration(300);
        pauseThumbAnimator.addUpdateListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int widthHalf = measuredWidth / 2;

        int cdBgWidth = (int) (measuredWidth / CD_BG_SCALE);
        int cdBgWidthHalf = cdBgWidth / 2;

        //设置线
        cdThumbLine.setBounds(0, 0, measuredWidth, DensityUtil.dip2px(getContext(), CD_THUMB_LINE_HEIGHT));

        //cd背景
        int cdBgLeft = widthHalf - cdBgWidthHalf;
        int cdBgTop = DensityUtil.dip2px(getContext(), measuredWidth / CD_BG_TOP_SCALE);
        cdBg.setBounds(cdBgLeft, cdBgTop, cdBgLeft + cdBgWidth, cdBgTop + cdBgWidth);

        int topCircleWidth = DensityUtil.dip2px(getContext(), THUMB_CIRCLE_WIDTH);

        thumbPoint = new Point(measuredWidth / 2 - topCircleWidth / 2, -topCircleWidth / 2);
        thumbRotationPoint = new Point(measuredWidth / 2, 0);

        if (cdThumb == null) {
            initBitmap();
        }
    }

    private void initBitmap() {
        //获取Bitmap，需要用到View宽度的，所以要在onMeasure中
        int measuredWidth = getMeasuredWidth();

        int imageHeight = (int) (measuredWidth / THUMB_WIDTH_SCALE);

        double scale = imageHeight * 1.0 / DensityUtil.dip2px(getContext(), THUMB_HEIGHT);

        //Thumb的宽度
        int imageWidth = (int) (scale * DensityUtil.dip2px(getContext(), THUMB_WIDTH));

        //获取到的Bitmap可以比需要的大，要进行调整
        cdThumb = ImageUtil.scaleBitmap(getResources(), R.drawable.cd_thumb, imageWidth, imageHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //可以通过SurfaceView来实现局部绘制
        //因为旋转指针时，背景和上面那条线不用在重新绘制了
        //但View不行，因为每次View都是一个全新的Canvas
        //绘制线
        cdThumbLine.draw(canvas);

        //绘制背景
        cdBg.draw(canvas);

        //绘制指针
        thumbMatrix.setRotate(thumbRotation, thumbRotationPoint.x, thumbRotationPoint.y);
        thumbMatrix.preTranslate(thumbPoint.x, thumbPoint.y);
        canvas.drawBitmap(cdThumb, thumbMatrix, paint);

        canvas.restore();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        thumbRotation = (float) animation.getAnimatedValue();
        invalidate();
    }

    public void stopThumbAnimation() {
        pauseThumbAnimator.start();
    }

    public void startThumbAnimation() {
        playThumbAnimator.start();
    }
}
