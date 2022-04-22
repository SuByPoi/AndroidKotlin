package com.example.piano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PianoView extends View {
    public static final int Number_OF_KEYS = 14;
    private Paint black,white,yellow,blackline;
    private ArrayList<Key> blacks,whites;
    private int keyWidth,keyHeight;
    private SoundManager soundManager;
    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        blackline = new Paint();
        blackline.setColor(Color.BLACK);
        blackline.setStrokeWidth(7);
        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);

        whites = new ArrayList<Key>();
        blacks = new ArrayList<Key>();
        soundManager = SoundManager.getInstance();
        soundManager.init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w / Number_OF_KEYS;
        keyHeight = h;
        int count = 15;
        for(int i = 0 ; i < Number_OF_KEYS;i++){
            int left = i * keyWidth;
            int right = left + keyWidth;
            RectF rect = new RectF(left,0,right,h);
            whites.add(new Key(i+1,rect));
            if(i!=0 && i!=3 && i!=7 && i!=10){
                rect = new RectF((float) (i-1)*keyWidth + 0.75f*keyWidth,
                                 0,
                                 (float) i*keyWidth + 0.25f*keyWidth,
                                 0.67f*keyHeight);
                blacks.add(new Key(count,rect));
                count++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Key k:whites){
            canvas.drawRect(k.rect,k.down?yellow:white);
            k.down = false;
        }
        for(int i=1;i<Number_OF_KEYS;i++){
            canvas.drawLine(i*keyWidth,0,i*keyWidth,keyHeight,blackline);
        }
        for(Key k:blacks){
            canvas.drawRect(k.rect,k.down?yellow:black);
            k.down = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownActive = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;
        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            Key k = keyForPosition(x, y);
            if (k != null) {
                k.down = isDownActive;
                invalidate();
            }
        }
        ArrayList<Key> temp = new ArrayList<>();
        temp.addAll(whites);
        temp.addAll(blacks);

        for (Key key : temp) {
            if (key.down) {
                switch (key.sound) {
                    case 1:
                        soundManager.playSound(R.raw.c3);
                        break;
                    case 2:
                        soundManager.playSound(R.raw.d3);
                        break;
                    case 3:
                        soundManager.playSound(R.raw.e3);
                        break;
                    case 4:
                        soundManager.playSound(R.raw.f3);
                        break;
                    case 5:
                        soundManager.playSound(R.raw.g3);
                        break;
                    case 6:
                        soundManager.playSound(R.raw.a3);
                        break;
                    case 7:
                        soundManager.playSound(R.raw.b3);
                        break;
                    case 8:
                        soundManager.playSound(R.raw.c4);
                        break;
                    case 9:
                        soundManager.playSound(R.raw.d4);
                        break;
                    case 10:
                        soundManager.playSound(R.raw.e4);
                        break;
                    case 11:
                        soundManager.playSound(R.raw.f4);
                        break;
                    case 12:
                        soundManager.playSound(R.raw.g4);
                        break;
                    case 13:
                        soundManager.playSound(R.raw.a4);
                        break;
                    case 14:
                        soundManager.playSound(R.raw.b4);
                        break;
                    case 15:
                        soundManager.playSound(R.raw.db3);
                        break;
                    case 16:
                        soundManager.playSound(R.raw.eb3);
                        break;
                    case 17:
                        soundManager.playSound(R.raw.gb3);
                        break;
                    case 18:
                        soundManager.playSound(R.raw.ab3);
                        break;
                    case 19:
                        soundManager.playSound(R.raw.bb3);
                        break;
                    case 20:
                        soundManager.playSound(R.raw.db4);
                        break;
                    case 21:
                        soundManager.playSound(R.raw.eb4);
                        break;
                    case 22:
                        soundManager.playSound(R.raw.gb4);
                        break;
                    case 23:
                        soundManager.playSound(R.raw.ab4);
                        break;
                    case 24:
                        soundManager.playSound(R.raw.bb4);
                        break;
                }

            }
        }
        return true;
    }
    private Key keyForPosition(float x, float y) {
        for (Key k : blacks) {
            if (k.rect.contains(x, y)) {
                return k;
            }
        }
        for (Key k : whites) {
            if (k.rect.contains(x, y)) {
                return k;
            }
        }
        return null;
    }
}
