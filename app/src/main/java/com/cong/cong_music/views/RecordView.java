package com.cong.cong_music.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cong.cong_music.R;
import com.cong.cong_music.util.DensityUtil;
import com.cong.cong_music.util.ImageUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Cong
 * @date 2018/9/11
 * @description
 */
public class RecordView extends View {

    private static final float CD_SCALE = 1.333F;               //黑胶唱片宽高比例
    //private static final float ALBUM_SCALE = 2.037F;
    private static final float ALBUM_SCALE = 2.1F;              //封面比例
    public static final float ROTATION_PER = 0.2304F;           //每16毫秒旋转的角度
    private Paint paint;                                        //画笔
    private Bitmap cd;                                          //黑胶唱片bitmap
    private Point cdPoint = new Point();                        //黑胶唱片绘制坐标
    private Point cdRotationPoint = new Point();                //旋转点，都是在中点，所以一个就够了
    private int albumWidth;                                     //封面的宽度
    private Point albumPoint = new Point();                     //封面绘制坐标
    private String albumUri;                                    //封面图
    private Bitmap album;                                       //封面bitmap
    private Matrix cdMatrix = new Matrix();                     //黑胶唱片矩阵
    private Matrix albumMatrix = new Matrix();                  //封面矩阵
    private float cdRotation = 0;                               //旋转的角度
    private TimerTask timerTask;                                //计时器任务
    private Timer timer;                                        //计算器，用来调度唱片，专辑转动



    public RecordView(Context context) {
        this(context,null);
    }

    public RecordView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RecordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int widthHalf = measuredWidth / 2;

        initResource();

        //黑胶
        int cdWidthHalf = cd.getWidth() / 2;
        int cdLeft = widthHalf - cdWidthHalf;

        //他的top，应该有后面白圈的中心点算
        int cdBgWidth = (int) (measuredWidth / RecordThumbView.CD_BG_SCALE);
        int cdBgWidthHalf = cdBgWidth / 2;
        int cdBgTop = DensityUtil.dip2px(getContext(), measuredWidth / RecordThumbView.CD_BG_TOP_SCALE);
        int cdBgCenterY=cdBgTop+cdBgWidthHalf;

        int cdTop = cdBgCenterY-cdWidthHalf;
        cdPoint.set(cdLeft, cdTop);
        cdRotationPoint.set(widthHalf, cdWidthHalf + cdTop);

        //封面
        albumWidth = (int) (measuredWidth / ALBUM_SCALE);
        int albumWidthHalf = albumWidth / 2;
        int albumLeft = widthHalf - albumWidthHalf;
        int albumTop = cdBgCenterY-albumWidthHalf;
        albumPoint.set(albumLeft, albumTop);

        showAlbum();
    }

    /**
     * 设置歌曲封面
     *
     * @param uri
     */
    public void setAlbumUri(String uri) {
        this.albumUri=uri;
        showAlbum();
    }

    private void showAlbum() {
        if (albumWidth!=0) {
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.circleCrop();
            //options.diskCacheStrategy(DiskCacheStrategy.NONE);
            options.override(albumWidth, albumWidth);
            Glide.with(this).asBitmap().load(ImageUtil.getImageURI(this.albumUri)).apply(options).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    album = ImageUtil.resizeImage(resource, albumWidth, albumWidth);
                    invalidate();
                }
            });
        }

    }

    private void initResource() {
        if (cd == null) {
            //cd背景
            int cdWidth = (int) (getMeasuredWidth() / CD_SCALE);
            cd = ImageUtil.scaleBitmap(getResources(), R.drawable.cd_bg, cdWidth, cdWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        //绘制cd
        cdMatrix.setRotate(cdRotation, cdRotationPoint.x, cdRotationPoint.y);
        cdMatrix.preTranslate(cdPoint.x, cdPoint.y);
        canvas.drawBitmap(cd, cdMatrix, paint);

        //绘制封面
        if (album != null) {
            albumMatrix.setRotate(cdRotation, cdRotationPoint.x, cdRotationPoint.y);
            albumMatrix.preTranslate(albumPoint.x, albumPoint.y);
            canvas.drawBitmap(album, albumMatrix, paint);
        }

        canvas.restore();
    }

    public void stopAlbumRotate() {
        cancelTask();
    }

    public void startAlbumRotate() {
        cancelTask();
        timerTask = new TimerTask() {

            @Override
            public void run() {
                if (cdRotation >= 360) {
                    cdRotation = 0;
                }
                cdRotation += ROTATION_PER;
                postInvalidate();
            }
        };
        timer = new Timer();

        timer.schedule(timerTask, 0, 16);
    }

    private void cancelTask() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

    }
}
