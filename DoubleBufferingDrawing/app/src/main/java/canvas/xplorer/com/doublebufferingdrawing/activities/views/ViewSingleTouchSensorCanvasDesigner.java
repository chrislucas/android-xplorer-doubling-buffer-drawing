package canvas.xplorer.com.doublebufferingdrawing.activities.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Locale;

import canvas.xplorer.com.doublebufferingdrawing.activities.ImplDoublingBufferDrawing;
import canvas.xplorer.com.doublebufferingdrawing.activities.events.DelegateGestureListenerTap;
import canvas.xplorer.com.doublebufferingdrawing.activities.events.IDetectTap;
import canvas.xplorer.com.doublebufferingdrawing.activities.utils.ColorUtils;

public class ViewSingleTouchSensorCanvasDesigner extends View implements IDetectTap {

    private Paint mPaint;
    private int mWidth, mHeight;

    private ImplDoublingBufferDrawing implDoublingBufferDrawing;

    private GestureDetector gestureDetector;

    private Path mPath;

    private float lastX, lastY;

    private final int BACKGROUND_COLOR_DEFAULT = getBackground() == null
            ? Color.WHITE : ((ColorDrawable) getBackground()).getColor();

    public ViewSingleTouchSensorCanvasDesigner(Context context) {
        super(context);
        init();
    }

    public ViewSingleTouchSensorCanvasDesigner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewSingleTouchSensorCanvasDesigner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initPencil();
        configureGestureDetector();
    }

    private void configureGestureDetector() {
        mPath = new Path();
        gestureDetector = new GestureDetector(getContext(), new DelegateGestureListenerTap(this));
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private void initPencil() {
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
        mPaint.setColor(ColorUtils.getRandomColorRGB255(100, 200));
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

    private void clearCanvas() {
        if (implDoublingBufferDrawing != null) {
            Log.i("CLEAR_SURFACE_VIEW", "CLEAR");
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(BACKGROUND_COLOR_DEFAULT);
            implDoublingBufferDrawing
                    .getCanvasCache()
                    .drawRect(implDoublingBufferDrawing.getRectCanvas(), mPaint);
            if (mPath != null)
                mPath.reset();
        }
        else {
            Log.e("EXCEPTION_DRAW_PATH", "NOT PREPARED TO DRAW");
        }
    }

    private void drawPathOnTouch() {
        if (implDoublingBufferDrawing != null) {
            //mPaint.setColor(ColorUtils.getRandomColorRGB255(100, 180));
            implDoublingBufferDrawing.getCanvasCache().drawPath(mPath, mPaint);
        }
        else {
            Log.e("EXCEPTION_DRAW_PATH", "NOT PREPARED TO DRAW");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();
        String tag = String.format(Locale.getDefault(), "UNDEFINED Action: %d", action);
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
                mPath.reset();
                lastX = x;
                lastY = y;
                setPencilToDrawPathOnTouch();
                break;
        }
        //Log.i(tag, String.format("(%f %f) (%f %f)", lastX, lastY, x, y));
        invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        if (implDoublingBufferDrawing != null) {
            this.implDoublingBufferDrawing.setWidth(w);
            this.implDoublingBufferDrawing.setHeight(h);
        }
        else {
            if (mWidth > 0 && mHeight > 0)
                implDoublingBufferDrawing = new ImplDoublingBufferDrawing(w, h);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.i("DISPATCH_DRAW", "DISPATCH_DRAW");
        super.dispatchDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.i("DRAW", "DRAW");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("ON_DRAW", "ON_DRAW");
        canvas.drawBitmap(implDoublingBufferDrawing.getBitmapCache()
                , implDoublingBufferDrawing.getIdentity(), mPaint);
    }

    @Override
    public void invalidate() {
        Log.i("INVALIDATE", "INVALIDATE");
        super.invalidate();
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) { }

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

    /**
     * {@link GestureDetector.SimpleOnGestureListener#onFling(MotionEvent, MotionEvent, float, float)}
     *
     * Notifica um evento de movimento do dedo sobre a tela de forma rápida (Exemplo quando vamos passar uma ViewPager)
     * Esse evento calcula a velocidade de execução do gesto sabendo a distance entre o Ponto inicial tocado S(x,y)
     * e o ponto final E(x, y) ao longo dos eixos x e y por segundo
     *
     * */
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
        /**
         * se o usuario fizer um movimento de 'arrastar' rapido na tela e levantar o dedo (Movimento
         * parecido com Swipe), vamos redefinir a caneta de desenho
         * */
        setPencilToDrawPathOnTouch();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) { }

    /**
     * Nos notifica quando o evento double tap ocorre
     * @param e The down motion event of the first tap of the double-tap.
     * */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return true;
    }

    /**
     * Nos notifica quando um evento dentro do double tap ocorre
     * UP, DOWN, MOVE
     * */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                clearCanvas();
                invalidate();
                setPencilToDrawPathOnTouch();
                break;
        }
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onContextClick(MotionEvent e) {
        return true;
    }
}
