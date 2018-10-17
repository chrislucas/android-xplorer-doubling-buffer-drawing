package canvas.xplorer.com.doublebufferingdrawing.activities.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import canvas.xplorer.com.doublebufferingdrawing.R;
import canvas.xplorer.com.doublebufferingdrawing.activities.ImplDoublingBufferDrawing;
import canvas.xplorer.com.doublebufferingdrawing.activities.events.IDetectTap;
import canvas.xplorer.com.doublebufferingdrawing.activities.events.DelegateGestureListenerTap;
import canvas.xplorer.com.doublebufferingdrawing.activities.utils.ColorUtils;

public class SurfaceViewSingleTouchSensorCanvasDesigner
        extends SurfaceView implements SurfaceHolder.Callback2, IDetectTap {

    private Paint mPaint;

    private int mWidth, mHeight;

    private ImplDoublingBufferDrawing implDoublingBufferDrawing;
    private GestureDetector gestureDetector;

    private SurfaceHolder surfaceHolder;

    private Path mPath;

    private float lastX, lastY;

    private final int BACKGROUND_COLOR_DEFAULT = getBackground() == null
            ? ContextCompat.getColor(getContext(), R.color.light_green): ((ColorDrawable) getBackground()).getColor();

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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mWidth > 0 && mHeight > 0)
            implDoublingBufferDrawing = new ImplDoublingBufferDrawing(mWidth, mHeight);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    private void init() {
        preparePencilToDraw();
        configureGestureDetector();
    }

    public void start() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void stop() {
        if (surfaceHolder != null)
            surfaceHolder.removeCallback(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void preparePencilToDraw() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        setPencilToDrawPathOnTouch();
    }

    /**
     * Configuracao padrao para desenhar
     * */
    private void setPencilToDrawPathOnTouch() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ColorUtils.getRandomColorRGB255(0, 255));
        mPaint.setStrokeWidth(15.0f);
        /**
         * Paint.Join enum que indica como segmentos de linhas sao juntados
         * quando desenhamos um Path. Paint.Join.BEVEL indica que o tratamento
         * sera juntar as segmentos usando retas.
         * */
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        /**
         * Paint.Cap e um enum que especifca como as extremidades de um segmento
         * de linha serao desenhados
         *
         * */
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void drawPathOnTouch() {
        if (implDoublingBufferDrawing != null) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    implDoublingBufferDrawing.getCanvasCache().drawPath(mPath, mPaint);
                    draw(canvas);
                }
            }
            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        else {
            Log.e("EXCEPTION_DRAW_PATH", "NOT PREPARED TO DRAW");
        }
    }

    private void configureGestureDetector() {
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

    private void clearCanvas() {
        if (implDoublingBufferDrawing != null) {
            Log.i("CLEAR_SURFACE_VIEW", "CLEAR");
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    mPaint.setStyle(Paint.Style.FILL);
                    mPaint.setColor(BACKGROUND_COLOR_DEFAULT);
                    implDoublingBufferDrawing
                            .getCanvasCache()
                            .drawRect(implDoublingBufferDrawing.getRectCanvas(), mPaint);
                    draw(canvas);
                    setPencilToDrawPathOnTouch();
                }
            }
            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    invalidate();
                }
            }
        }
        else {
            Log.e("EXCEPTION_DRAW_PATH", "NOT PREPARED TO DRAW");
        }
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();
        String tag = "UNDEFINED";
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                // define o ponto de inicio do segmento que sera desenhado
                mPath.moveTo(x, y);
                drawPathOnTouch();
                tag = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                // desenha yma curva de bezier entre o ponto P(lastX, lastY) e Q(x, y)
                mPath.quadTo(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
                drawPathOnTouch();
                tag = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                tag = "ACTION_UP";
                setPencilToDrawPathOnTouch();
                mPath.reset();
                lastX = x;
                lastY = y;
                break;
        }
        //Log.i(tag, String.format("(%f %f) (%f %f)", lastX, lastY, x, y));
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(implDoublingBufferDrawing.getBitmapCache()
                , implDoublingBufferDrawing.getIdentity(), mPaint);
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
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    private static ArrayMap<Integer, String> EVENTS = new ArrayMap<>();

    static {
        EVENTS.put(MotionEvent.ACTION_CANCEL, "MotionEvent.ACTION_CANCEL");
        EVENTS.put(MotionEvent.ACTION_DOWN, "MotionEvent.ACTION_DOWN");
        EVENTS.put(MotionEvent.ACTION_UP, "MotionEvent.ACTION_UP");
        EVENTS.put(MotionEvent.ACTION_MOVE, "MotionEvent.ACTION_MOVE");
        EVENTS.put(MotionEvent.ACTION_HOVER_MOVE, "MotionEvent.ACTION_HOVER_MOVE");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int actionMaskedEvent1 = e1.getActionMasked();
        int actionMaskedEvent2 = e2.getActionMasked();
        /*
        Log.i("ON_FLING"
                , String.format("Event 1: %s Event 2: %s.\nVelocity X/Y: %f/%f"
                        , EVENTS.get(actionMaskedEvent1)
                        , EVENTS.get(actionMaskedEvent2)
                        , velocityX
                        , velocityY
                )
        );
        */
        setPencilToDrawPathOnTouch();
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
    public boolean onDoubleTap(final MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                clearCanvas();
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
