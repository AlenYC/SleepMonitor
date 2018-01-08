package com.ycl.framework.albums;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.ycl.framework.R;
import com.ycl.framework.base.AlbumEntityEBus;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.string.DensityUtils;
import com.ycl.framework.utils.util.GlideProxy;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 图片gridview的adapter
 */
public class MyAdapter extends CommonAdapter<String> {

    /**
     * 用户选择的图片，存储为图片的完整路径（不重复LinkedList）
     */
    public static List<String> mSelectedImage = new LinkedList<>();

    /**
     * 文件夹路径
     */
    private String mDirPath;
    private int itemSize;

    public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
                     String dirPath) {

        super(context, mDatas, itemLayoutId);
        this.mDirPath = dirPath;
        itemSize = (DensityUtils.getScreenW(context) - DensityUtils.dp2px(12, context)) / 3;
//        if (AlbumActivity.MaxSelectNum != 1) {
//            for (String str : oldSelectedImage)
//                mSelectedImage.add(str);
//        } else
//            oldSelectedImage.clear();
    }

    public void setmDirPath(String dirPath) {
        mDirPath = dirPath;
    }

    public void setDatas(List<String> datas) {
        mDatas = datas;
    }

    @Override
    public void convert(final ViewHolder helper, final String item) {
        //设置no_pic
        RelativeLayout rlRoot = helper.getView(R.id.rl_dir_photo_root);
        rlRoot.getLayoutParams().width = itemSize;
        rlRoot.getLayoutParams().height = itemSize;
        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final ImageView mSelect = helper.getView(R.id.id_item_select);

        if (AlbumActivity.MaxSelectNum != 1) {
            //设置no_selected
            helper.setImageResource(R.id.id_item_select,
                    R.drawable.picture_unselected);
            mSelect.setVisibility(View.VISIBLE);
            if (AlbumActivity.ALL_IMG.equals(mDirPath) && helper.getPosition() == 0) {
                mSelect.setVisibility(View.GONE);
            }
            /**
             * 已经选择过的图片，显示出选择过的效果
             */
            if (mSelectedImage.contains(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item)) {
                helper.setImageResource(mSelect, R.drawable.pictures_selected);
                mImageView.setColorFilter(Color.parseColor("#57000000"));
            }
            ((AlbumActivity) mContext).refreshTitleBtn();
        } else {
            mSelect.setVisibility(View.GONE);
        }
        mImageView.setScaleType(ScaleType.FIT_XY);
        //设置图片
        if (AlbumActivity.ALL_IMG.equals(mDirPath)) {
            if (helper.getPosition() == 0) {
                rlRoot.setBackgroundResource(R.drawable.bg_album_item);
                //防止缓存
                int padding = DensityUtils.dp2px(10, mContext);
                rlRoot.setPadding(padding, padding, padding, padding);
                GlideProxy.loadImgForRes(mImageView,R.drawable.photocam);
            } else {
                rlRoot.setPadding(0, 0, 0, 0);
                rlRoot.setBackground(null);
                helper.setImageRes(mImageView, item);
            }
        } else {
            helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);
            rlRoot.setBackground(null);
        }
        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mImageView.setOnClickListener(new OnClickListener() {
                                          //选择，则将图片变暗，反之则反之
                                          @SuppressWarnings("unchecked")
                                          @Override
                                          public void onClick(View v) {
                                              if (AlbumActivity.CAMERA_TAG.equals(item)) {
                                                  ((FrameActivity) mContext).startAct(CameraTempActivity.class);
                                                  return;
                                              }

                                              if (AlbumActivity.MaxSelectNum == 1) {

                                                  if (((AlbumActivity) mContext).isFilterSize(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item)) {
                                                      EventBus.getDefault().post(new AlbumEntityEBus<String>(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item));
                                                      ((AlbumActivity) mContext).finish();
                                                  } else
                                                      ((AlbumActivity) mContext).showWarmToast("图片太小了!");
                                                  return;
                                              }

                                              // 已经选择过该图片
                                              if (mSelectedImage.contains(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item)) {
                                                  mSelectedImage.remove(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item);
                                                  helper.setImageResource(mSelect, R.drawable.picture_unselected);
                                                  mImageView.setColorFilter(null);
                                              } else
                                              // 未选择该图片
                                              {
                                                  if (AlbumActivity.MaxSelectNum - mSelectedImage.size() == 0) {
                                                      ((AlbumActivity) mContext).showWarmToast("最多选择" + mSelectedImage.size() + "张!");
                                                      return;
                                                  }
                                                  if (((AlbumActivity) mContext).isFilterSize(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item)) {
                                                      mSelectedImage.add(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item);
                                                      helper.setImageResource(mSelect, R.drawable.pictures_selected);
                                                      mImageView.setColorFilter(Color.parseColor("#57000000"));
                                                  } else {
                                                      ((AlbumActivity) mContext).showWarmToast("图片太小了!");
                                                      return;
                                                  }
                                              }
                                              ((AlbumActivity) mContext).refreshTitleBtn();
                                          }
                                      }
        );
    }

    //还原上次选择的图片数据
//    public void refreshSelectedPic() {
//        mSelectedImage.clear();
//        for (String str : oldSelectedImage)
//            mSelectedImage.add(str);
//    }
}