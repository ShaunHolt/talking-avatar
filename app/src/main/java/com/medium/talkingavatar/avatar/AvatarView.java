package com.medium.talkingavatar.avatar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class AvatarView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int BORDER_WIDTH = 10;

    private Context mContext;

    private AvatarThread mAvatarThread;

    public void setMouthMorphs(int[] timeMorphs) {
        mAvatarThread.setMouthMorphs(timeMorphs);
    }

    public void endMouthMorphs() {
        mAvatarThread.endMouthMorphs();
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    private void init(Context context) {
        mContext = context;

        int sizeType = Utils.getSizeType();
        int size = Utils.calculateSize(480, sizeType);
        size += BORDER_WIDTH;
        
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        setZOrderOnTop(true);
        holder.setFormat(PixelFormat.TRANSLUCENT);
        holder.setFixedSize(size, size);

        mAvatarThread = new AvatarThread(holder, context, sizeType);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mAvatarThread.setRunning(true);

        if(mAvatarThread.isStarted()) return;

        mAvatarThread.start();
        mAvatarThread.setAvatar();
        mAvatarThread.setStarted();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mAvatarThread.setRunning(false);
        
        while (retry) {
            try {
                mAvatarThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
