package com.example.normann.opiatekvipotens;

import android.graphics.Matrix;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class TablePdf extends ActionBarActivity {
    private ImageView iv;
    private Matrix matrix = new Matrix();
    private float scale = 1f;
    private ScaleGestureDetector SGD;
    private float mLastTouchX, mLastTouchY;
    private int mActivePointerId;
    private float mPosX, mPosY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_pdf);

        iv=(ImageView)findViewById(R.id.imageView);
        SGD = new ScaleGestureDetector(this, new ScaleListener());
    }

    public boolean onTouchEvent(MotionEvent ev) {
//        int count = ev.getPointerCount();
//        if (count==2) {
//            SGD.onTouchEvent(ev);
//        }
        SGD.onTouchEvent(ev);
//        else if (count==1) {
//
//            final int action = MotionEventCompat.getActionMasked(ev);
//
//            switch (action) {
//                case MotionEvent.ACTION_DOWN: {
//                    final int pointerIndex = MotionEventCompat.getActionIndex(ev);
//                    final float x = MotionEventCompat.getX(ev, pointerIndex);
//                    final float y = MotionEventCompat.getY(ev, pointerIndex);
//
//                    // Remember where we started (for dragging)
//                    mLastTouchX = x;
//                    mLastTouchY = y;
//                    // Save the ID of this pointer (for dragging)
//                    mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
//                    break;
//                }
//
//                case MotionEvent.ACTION_MOVE: {
//                    // Find the index of the active pointer and fetch its position
//                    final int pointerIndex =
//                            MotionEventCompat.findPointerIndex(ev, mActivePointerId);
//
//                    final float x = MotionEventCompat.getX(ev, pointerIndex);
//                    final float y = MotionEventCompat.getY(ev, pointerIndex);
//
//                    // Calculate the distance moved
//                    final float dx = x - mLastTouchX;
//                    final float dy = y - mLastTouchY;
//
//                    mPosX += dx;
//                    mPosY += dy;
//
//    //                invalidate();
//
//                    // Remember this touch position for the next move event
//                    mLastTouchX = x;
//                    mLastTouchY = y;
//
//                    break;
//                }
//
//                case MotionEvent.ACTION_UP: {
//    //                mActivePointerId = INVALID_POINTER_ID;
//                    break;
//                }
//
//                case MotionEvent.ACTION_CANCEL: {
//                    final int pointerIndex = MotionEventCompat.getActionIndex(ev);
//                    final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
//
//                    if (pointerId == mActivePointerId) {
//                        // This was our active pointer going up. Choose a new
//                        // active pointer and adjust accordingly.
//                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
//                        mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
//                        mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
//                        mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
//                    }
//                    break;
//                }
//
//                case MotionEvent.ACTION_POINTER_UP: {
//
//                    final int pointerIndex = MotionEventCompat.getActionIndex(ev);
//                    final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
//
//                    if (pointerId == mActivePointerId) {
//                        // This was our active pointer going up. Choose a new
//                        // active pointer and adjust accordingly.
//                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
//                        mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
//                        mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
//                        mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
//                    }
//                    break;
//                }
//            }
//            matrix.setTranslate(mPosX, mPosY);
//        }

        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.

            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(1f, Math.min(scale, 5.0f));

            matrix.setScale(scale, scale);
            iv.setImageMatrix(matrix);
            return true;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
