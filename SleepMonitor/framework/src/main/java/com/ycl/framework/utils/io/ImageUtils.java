package com.ycl.framework.utils.io;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.ycl.framework.utils.util.PhotoUtils;
import com.ycl.framework.utils.util.YisLoger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    /**
     * 渐变显示图片
     *
     * @param imageView 宿主imageView
     * @param bitmap    视图对象
     */
    @SuppressWarnings("deprecation")
    public static void setImageBitmap(Context context, ImageView imageView,
                                      Bitmap bitmap) {
        // Use TransitionDrawable to fade in.
        final TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                new ColorDrawable(Color.TRANSPARENT),
                new BitmapDrawable(context.getResources(), bitmap)});
        // noinspection deprecation
        // imageView.setBackgroundDrawable(imageView.getDrawable());
        imageView.setImageDrawable(td);
        td.startTransition(200);
    }

    /**
     * 保存图片
     *
     * @param filePath 文件路径+文件名
     * @throws IOException
     * @ param conent
     * 文件内容
     */
    public static void saveAsJPEG(Bitmap bitmap, String filePath)
            throws IOException {
        FileOutputStream fos = null;

        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 保存图片
     *
     * @param filePath 文件路径+文件名
     * @throws IOException
     * @ param content
     * 文件内容
     */
    public static void saveAsPNG(Bitmap bitmap, String filePath)
            throws IOException {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 保存图片到SD卡
     *
     * @return String 路径
     */
    public static String savePhotoToSDCard(String picPath, Context mContext) {
        String IMAGE_PATH = Environment.getExternalStorageDirectory()
                .getPath() + File.separator
                + "Yuhua/temp";
        FileUtils.getInstance(mContext).createDirFile(IMAGE_PATH);
        String newFilePath = IMAGE_PATH + File.separator + System.currentTimeMillis() + ".png";

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Config.RGB_565;

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picPath, opts);// 此时返回bm为空
        int srcW = opts.outWidth;
        int srcH = opts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        int comressSzie;
        //图片过小不进行压缩
        opts.inSampleSize = 1;
        if (srcW < 800f && srcH < 800f)
            comressSzie = 100;
        else if (srcW < 1000f && srcH < 1000f)
            comressSzie = 88;
        else if (srcW < 1300 && srcH < 1300)
            comressSzie = 78;
        else if (srcW < 1500f && srcH < 1500f) {
            comressSzie = 68;
        } else if (srcW < 2000f && srcH < 200f) {
            opts.inSampleSize = 2;
            comressSzie = 85;
        } else {
            opts.inSampleSize = (int) ((srcW / 1500f + srcH / 1500f) / 2);
            comressSzie = 70;
        }
//        if (srcW < 730f && srcH < 730f)
//            comressSzie = 100;
//        else if (srcW < 850f && srcH < 850f)
//            comressSzie = 90;
//        else if (srcW < 950f && srcH < 920f)
//            comressSzie = 72;
//        else if (srcW < 1150f && srcH < 1150f) {
//            comressSzie = 30;
//        } else if (srcW < 1500f && srcH < 1500f) {
//            opts.inSampleSize = 2;
//            comressSzie = 42;
//        } else {
//            opts.inSampleSize = (int) ((srcW / 1000f + srcH / 1000f) / 2);
//            comressSzie = 45;
//        }
        opts.inJustDecodeBounds = false;

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(picPath, opts);
        if (bitmap == null) {
            return null;
        }
        int degreePic = PhotoUtils.readPictureDegree(picPath);
        if (degreePic > 0) {
            bitmap = PhotoUtils.rotateBitmap(degreePic, bitmap);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(newFilePath);
            bitmap.compress(CompressFormat.JPEG, comressSzie, fileOutputStream);
        } catch (Exception e) {
            return null;
        } catch (OutOfMemoryError e) {
            Toast.makeText(mContext.getApplicationContext(), "图片压缩异常，请清理内存后重试！", Toast.LENGTH_SHORT).show();
            return null;
        } finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.flush();
                fileOutputStream.close();
                if (!bitmap.isRecycled())
                    bitmap.recycle();
            } catch (IOException e) {
                return null;
            }
        }
        return newFilePath;
    }

    //质量压缩
    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    //使用Bitmap加Matrix来缩放()
    public static Bitmap resizeImage(Bitmap BitmapOrg, int w, int h) {
        Bitmap resizedBitmap = null;
        try {
            int width = BitmapOrg.getWidth();
            int height = BitmapOrg.getHeight();
            float scaleWidth = ((float) w) / width;
            float scaleHeight = ((float) h) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // if you want to rotate the Bitmap
            // matrix.postRotate(45);
            resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                    height, matrix, true);
        } catch (Exception e) {
            YisLoger.debug(e.toString());
        }
        return resizedBitmap;
    }

}
