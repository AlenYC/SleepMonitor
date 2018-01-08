package com.ycl.framework.albums;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;

import com.ycl.framework.base.AlbumEntityEBus;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.base.FrameActivityStack;
import com.ycl.framework.utils.io.FileUtils;
import com.ycl.framework.utils.util.PhotoUtils;
import com.ycl.framework.utils.util.YisLoger;

import java.io.File;

import de.greenrobot.event.EventBus;

public class CameraTempActivity extends FrameActivity {

    private String mPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            finish();
        } else {
            PhotoUtils.takePicture(this, PhotoUtils.CAMERA_FOLDER, PhotoUtils.INTENT_REQUEST_CODE_CAMERA);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);
        YisLoger.state(getClass().getName(), "---------  onActivityResult ");
        if (resultCode == RESULT_OK) {
            mPhotoPath = PhotoUtils.mPhotoPath;
            YisLoger.debugUrl("----------------    " + mPhotoPath);
            File f = FileUtils.getOwnCacheDirectory(getApplicationContext(), mPhotoPath);
            if (f.exists()) {
                if (AlbumActivity.MaxSelectNum != 1) {
                    MyAdapter.mSelectedImage.add(mPhotoPath);
                } else
                    MyAdapter.mSelectedImage.clear();
                AlbumEntityEBus cameraBus = new AlbumEntityEBus<>(mPhotoPath);
                cameraBus.setTypeEvent(2);  //表示 拍照
                EventBus.getDefault().post(cameraBus);
                //刷新相册
                MediaScannerConnection.scanFile(this, new String[]{mPhotoPath}, null, null);
                try {
                    FrameActivityStack.create().finishActivity(AlbumActivity.class);
                } catch (Exception e) {
                    showToast(e.toString());
                }
            } else {
                showToast("请重新选择照片！");
            }
        }
        finish();
    }


    @Override
    public void setRootView() {

    }
}
