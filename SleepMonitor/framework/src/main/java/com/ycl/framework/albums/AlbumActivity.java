package com.ycl.framework.albums;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ycl.framework.R;
import com.ycl.framework.albums.ListImageDirPopupWindow.OnImageDirSelected;
import com.ycl.framework.base.AlbumEntityEBus;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.string.DensityUtils;
import com.ycl.framework.view.TitleHeaderView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import de.greenrobot.event.EventBus;

public class AlbumActivity extends FrameActivity implements OnImageDirSelected, OnClickListener {

    public static final String CAMERA_TAG = "yis_canmra";
    public static final String ALL_IMG = "所有图片";
    //最大图片可选长度
    public static int MaxSelectNum;
    public static final String MAX_SELECTED_NUM = "MAX_Length";


    public static String INTENT_CLASS = ""; //意图class (便于广播 类型处理)

    public static boolean isClearCache = true;   //是否删除
    //过滤的最小尺寸（高度）
    private int filterMinHeight = 0;
    private int filterMinWidth = 0;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 各个相册的所有图片
     */
    private List<String> mImgs;

    private TitleHeaderView mTitle;

    private GridView mGirdView;
    MyAdapter mAdapter;

    private TextView mChooseDir;
    private TextView mImageCount;

    /**
     * 底部相册列表 RelativeLayout
     */
    private RelativeLayout mBottomLy;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<>();
    //所有图片张数
    int totalCount = 0;

    //所有图片
    private List<String> allImgs = new ArrayList<>();

    private ListImageDirPopupWindow mListImageDirPopupWindow;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_album);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initsViews();
        initsData(savedInstanceState);
    }


    public void initsViews() {
        mTitle = (TitleHeaderView) findViewById(R.id.frame_title_view);
        mGirdView = (GridView) findViewById(R.id.gd_album_photos);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        mImageCount = (TextView) findViewById(R.id.id_total_count);
        mBottomLy = (RelativeLayout) findViewById(R.id.rl_bottom_ly);

//        mTitle.setCustomClickListener(R.id.BackButton, this);
        mTitle.setTitleText("图片");

        mBottomLy.setOnClickListener(this);
    }

    public void initsData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            MaxSelectNum = getIntent().getIntExtra(MAX_SELECTED_NUM, Integer.MAX_VALUE);
            isClearCache = getIntent().getBooleanExtra("isClearCache", true);
            filterMinHeight = getIntent().getIntExtra("filterMinHeight", -1);
            filterMinWidth = getIntent().getIntExtra("filterMinWidth", -1);
        } else {
            if (!TextUtils.isEmpty(SavePreference.getStr(getApplicationContext(), "CameraPhoto"))) {
                finish();
                return;
            }
            MaxSelectNum = SavePreference.getInt(getApplicationContext(), MAX_SELECTED_NUM);
            filterMinHeight = SavePreference.getInt(getApplicationContext(), "filterMinHeight");
            filterMinWidth = SavePreference.getInt(getApplicationContext(), "filterMinWidth");
        }
        allImgs.add(CAMERA_TAG);
        getImages();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SavePreference.save(getApplicationContext(), MAX_SELECTED_NUM, MaxSelectNum);
        SavePreference.save(getApplicationContext(), "filterMinWidth", filterMinWidth);
        SavePreference.save(getApplicationContext(), "filterMinHeight", filterMinHeight);
    }

    @Override
    public void onClick(View v) {
        int ids = v.getId();
        if (ids == R.id.rl_bottom_ly) {
            /**
             * 为底部的布局设置点击事件，弹出popupWindow
             */
            mListImageDirPopupWindow
                    .setAnimationStyle(R.style.anim_popup_dir);
            mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
            // 设置背景颜色变暗
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = .3f;
            getWindow().setAttributes(lp);
        } else if (ids == R.id.title_view_right_id) {
            EventBus.getDefault().post(new AlbumEntityEBus<>(MyAdapter.mSelectedImage));
            finish();
        } else if (ids == R.id.title_view_left_id) {
            finish();
        }

    }

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            mImgs = new ArrayList<>();
            mImgs.add(CAMERA_TAG);
            mAdapter = new MyAdapter(this, mImgs,
                    R.layout.item_album_grid, ALL_IMG);
            mGirdView.setAdapter(mAdapter);
            mImageCount.setText(totalCount + "张");
            showWarmToast("擦，一张图片没扫描到");
            return;
        }
        //  if () {
        mImgs = allImgs;
//        } else {
//            mImgs = Arrays.asList(mImgDir.list());
//        }
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */

        List<String> data2 = new ArrayList<>();
        for (String items : mImgs) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGB_565;

            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(items, opts);// 此时返回bm为空
            int srcW = opts.outWidth;
            int srcH = opts.outHeight;
            if (srcH > 0 && srcW > 0) {
                data2.add(items);
            }
        }
        mImgs.clear();
        mImgs = data2;
        totalCount = mImgs.size();

        mAdapter = new MyAdapter(this, mImgs,
                R.layout.item_album_grid, mImgDir.getAbsolutePath());
        mAdapter.setmDirPath(ALL_IMG);
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "张");
    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                LayoutParams.MATCH_PARENT, (int) (DensityUtils.getScreenH(getApplicationContext()) * 0.65),
                mImageFloders, getLayoutInflater()
                .inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        ImageFloder imgFloder = new ImageFloder();
        imgFloder.setCustomName(ALL_IMG);
        imgFloder.setTag(false);
        mImageFloders.add(imgFloder);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String firstImage = null;

                Uri mImageUri = Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = AlbumActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        Media.MIME_TYPE + "=? or " + Media.MIME_TYPE + "=? or "
                                + Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png", "image/jpg"},
                        //       Media.DATE_MODIFIED + " DESC");
                        Media.DATE_ADDED + " DESC");
                //     Log.e("TAG", "---------------------------------            " + mCursor.getCount() + "");
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(Media.DATA));
                    allImgs.add(path);

                    //    Log.e("TAG", path); 打印图片 path
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    //绝对路径
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    try {
                        int picSize = parentFile.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String filename) {
                                return filename.endsWith(".jpg")
                                        || filename.endsWith(".png")
                                        || filename.endsWith(".jpeg");
                            }
                        }).length;
                        totalCount += picSize;
                        imageFloder.setCount(picSize);
                        mImageFloders.add(imageFloder);

                        if (picSize > mPicsSize) {
                            mPicsSize = picSize;
                            mImgDir = parentFile;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //全部图片 ImageFloder (没有图片的时候   size = 1)
                mImageFloders.get(0).setFirstImagePath(mImageFloders.get(mImageFloders.size() > 1 ? 1 : 0).getFirstImagePath());
                mImageFloders.get(0).setCount(totalCount);
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);
            }
        }).start();

    }

    //相册目录 dialog item点击
    @Override
    public void selected(ImageFloder floder) {
        if (floder.getTag()) {
            mImgDir = new File(floder.getDir());
            mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".png")
                            || filename.toLowerCase().endsWith(".jpeg");
                }
            }));
            if (mImgDir == null)
                mAdapter.setmDirPath(ALL_IMG);
            else
                mAdapter.setmDirPath(mImgDir.getAbsolutePath());
            mChooseDir.setText(floder.getName());
        } else {
            mImgs = allImgs;
            mAdapter.setmDirPath(ALL_IMG);
            mChooseDir.setText(ALL_IMG);
        }
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter.setDatas(mImgs);
        mAdapter.notifyDataSetChanged();
        mGirdView.smoothScrollToPositionFromTop(0, 0);
        mImageCount.setText(floder.getCount() + "张");
        mListImageDirPopupWindow.dismiss();
    }


    public void refreshTitleBtn() {
        mTitle.setRightBtn_Album(MyAdapter.mSelectedImage.size(), this, MaxSelectNum);
    }

    //过滤图片
    public boolean isFilterSize(String path) {
        if (filterMinHeight > 0 || filterMinWidth > 0) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            // 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存。
            BitmapFactory.decodeFile(path, opts);
            int srcWidth = opts.outWidth;// 获取图片的原始宽度
            int srcHeight = opts.outHeight;// 获取图片原始高度
            return opts.outWidth > filterMinWidth && opts.outHeight > filterMinHeight;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dismissProgressDialog();
            mHandler.removeCallbacksAndMessages(null);
            if (isClearCache)
                MyAdapter.mSelectedImage.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
