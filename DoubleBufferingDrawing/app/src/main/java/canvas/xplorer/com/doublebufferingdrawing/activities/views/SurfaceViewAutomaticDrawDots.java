package canvas.xplorer.com.doublebufferingdrawing.activities.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import canvas.xplorer.com.doublebufferingdrawing.activities.ImplDoublingBufferDrawing;
import canvas.xplorer.com.doublebufferingdrawing.activities.utils.ColorUtils;

public class SurfaceViewAutomaticDrawDots extends SurfaceView implements SurfaceHolder.Callback2 {

    private static final int OFFSET_DRAW_RECT_PX = 30; // px

    public  class DrawDots implements Runnable {
        private boolean isRunning;

        DrawDots() { this.isRunning = true; }

        void interrupt() { isRunning = false; }

        @Override
        public void run() {
            while (isRunning) {
                drawDot();
            }
        }
    }

    private Paint mPaint;

    private int mWidth, mHeight;

    private DrawDots drawDots;

    private ScheduledExecutorService scheduledExecutorService;

    private ImplDoublingBufferDrawing implDoublingBufferDrawing;
    private SurfaceHolder surfaceHolder;

    public SurfaceViewAutomaticDrawDots(Context context) {
        super(context);
        init();
    }

    public SurfaceViewAutomaticDrawDots(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void start(long interval, TimeUnit timeUnit) {
        Log.i("START_SERVICE", "START");
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        drawDots = new DrawDots();
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(drawDots, 0, interval, timeUnit);
    }

    public void stop() {
        Log.i("TRY_STOP_SERVICE", "TRY_STOP");
        drawDots.interrupt();
        scheduledExecutorService.shutdown();
        if (scheduledExecutorService.isShutdown()) {
            Log.v("STOP_SERVICE", "STOP");
        }
        if (scheduledExecutorService.isTerminated()) {
            Log.v("TERMINATED_SERVICE", "TERMINATED");
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    private void eraseSurfaceView() {
        int bg = getBackground() == null ? Color.WHITE
                : ((ColorDrawable) getBackground()).getColor();
        // configurando a canera para pintar a area de desenho inteira
        mPaint.setStyle(Paint.Style.FILL);
        // com a cor de fundo padrao da view
        mPaint.setColor(bg);
        // definindo buffer de pintura com as definicoes da caneta (COR, DIMENSAO)
        // e o formato do desenho (Um retangulo que vai preencher a tela inteira)
        implDoublingBufferDrawing.getCanvasCache()
                .drawRect(implDoublingBufferDrawing.getRectCanvas()
                        , mPaint);
        // invalida a view inteira para ser recriada
        invalidate();
        // redefinir a classe que cuida do doubling buffer
        initDefaultDoublingBuffer();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth     = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight    = MeasureSpec.getSize(heightMeasureSpec);
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
    }

    private void initDefaultDoublingBuffer() {
        if (mWidth > 0 && mHeight > 0)
            implDoublingBufferDrawing = new ImplDoublingBufferDrawing(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(implDoublingBufferDrawing.getBitmapCache(), implDoublingBufferDrawing.getIdentity(), mPaint);
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) { }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("SURFACE_CREATED", "SURFACE_CREATED");
        initDefaultDoublingBuffer();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { }

    // Desenha primeiro no canvas de cache para repassar para o canvas normal
    private void drawDot(Canvas canvas, float left, float top, float right, float bottom) {
        //mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        int color = ColorUtils.getRandomColorRGB255(127, 255);
        mPaint.setColor(color);
        //RectF dot = new RectF(left, top, right, bottom);
        Log.i("DRAW_DOT", String.format("%f, %f COR: %d", left, bottom, color));
        canvas.drawPoint(left, bottom, mPaint);
        //canvas.drawRect(dot, mPaint);
    }

    private void drawDot() {
        if (implDoublingBufferDrawing != null) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    Random random = new Random();
                    float left  = random.nextInt(mWidth - 1);
                    float top   = random.nextInt(mHeight - 1);
                    float right = left + OFFSET_DRAW_RECT_PX;
                    float bottom = top + OFFSET_DRAW_RECT_PX;
                    drawDot(implDoublingBufferDrawing.getCanvasCache(), left, top,right, bottom);
                    draw(canvas);
                }
            }

            catch (Exception e) { Log.e("EXCP_DRAW_DOT", e.getMessage()); }

            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        else {
            Log.e("EXCEPTION_DRAW_DOT", "NOT_PREPARE_TO_DRAW");
        }
    }
}
