package canvas.xplorer.com.doublebufferingdrawing.activities.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import canvas.xplorer.com.doublebufferingdrawing.activities.ImplDoublingBufferDrawing;
import canvas.xplorer.com.doublebufferingdrawing.activities.events.IDetectTap;
import canvas.xplorer.com.doublebufferingdrawing.activities.events.DelegateGestureListenerTap;
import canvas.xplorer.com.doublebufferingdrawing.activities.utils.ColorUtils;

public class SurfaceViewSingleTouchSensorCanvasDesigner extends SurfaceView implements SurfaceHolder.Callback2, IDetectTap {

    private Point mDimension;

    private Paint mPaint;

    private int mWidth, mHeight;

    private ImplDoublingBufferDrawing implDoublingBufferDrawing;
    private Matrix identity;
    private GestureDetector gestureDetector;

    private SurfaceHolder surfaceHolder;

    private Path mPath;

    private float lastX, lastY;

    private boolean isRunning;

    private final int BACKGROUND_COLOR_DEFAULT = getBackground() == null
            ? Color.WHITE : ((ColorDrawable) getBackground()).getColor();


    public SurfaceViewSingleTouchSensorCanvasDesigner(Context context) {
        super(context);
        init();
    }

    public SurfaceViewSingleTouchSensorCanvasDesigner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) { }

    /**
     *
     * */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            clearCanvas();
            draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        /*
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
        */
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    private void init() {
        prepareCanvasToDraw();
        configureGestureDetector();
    }

    public void start() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void stop() {}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        initializeDoublingBufferDrawer();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDimension = new Point(w, h);
    }

    private void preparePencilToDraw() {
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15.0f);
        /**
         * Paint.Join enum que indica como segmentos de linhas sao juntados
         * quando desenhamos um Path. Paint.Join.BEVEL indica que o tratamento
         * sera juntar as segmentos usando postas retas.
         * */
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        /**
         * Paint.Cap e um enum que especifca como as extremidades de um segmento
         * de linha serao desenhados
         *
         * */
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
    }

    /**
     * Configuracao padrao para desenhar
     * */
    private void setPencilToDrawPathOnTouch() {
        preparePencilToDraw();
        mPaint.setColor(ColorUtils.getRandomColorRGB255(100, 255));
    }


    private void drawPath() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            setPencilToDrawPathOnTouch();
            implDoublingBufferDrawing.getCanvasCache().drawPath(mPath, mPaint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void configureGestureDetector() {
        identity = new Matrix();
        mPath = new Path();
        gestureDetector = new GestureDetector(getContext()
                , new DelegateGestureListenerTap(this));
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private void initializeDoublingBufferDrawer() {
        implDoublingBufferDrawing = new ImplDoublingBufferDrawing(mPaint, mWidth, mHeight);
    }

    private void prepareCanvasToDraw() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(BACKGROUND_COLOR_DEFAULT);
    }

    private void clearCanvas() {
        prepareCanvasToDraw();
        implDoublingBufferDrawing.getCanvasCache()
                .drawRect(implDoublingBufferDrawing.getRectCanvas(), mPaint);
        invalidate();
        //initializeDoublingBufferDrawer();
        //preparePencilToDraw();
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                // define o ponto de inicio do segmento que sera desenhado
                mPath.moveTo(x, y);
                drawPath();
                break;
            case MotionEvent.ACTION_MOVE:
                // desenha yma curva de bezier entre o ponto P(lastX, lastY) e Q(x, y)
                mPath.quadTo(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
                drawPath();
                break;
            case MotionEvent.ACTION_UP:
                mPath.reset();
                preparePencilToDraw();
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(implDoublingBufferDrawing.getBitmapCache(), identity, mPaint);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        switch (e1.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                break;
        }
        switch (e2.getAction()) { }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(final MotionEvent e) {
        final int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        clearCanvas();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public boolean onContextClick(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
