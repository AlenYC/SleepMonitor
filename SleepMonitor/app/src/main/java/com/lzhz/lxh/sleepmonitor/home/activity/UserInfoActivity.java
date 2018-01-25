package com.lzhz.lxh.sleepmonitor.home.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.SleepMonitorApplication;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.home.activity.bean.User;
import com.lzhz.lxh.sleepmonitor.tools.Constance;
import com.lzhz.lxh.sleepmonitor.tools.GlideCircleTransform;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetCallBack;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetParamas;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetUtil;
import com.lzhz.lxh.sleepmonitor.tools.PersissionUtils;
import com.lzhz.lxh.sleepmonitor.tools.SPUtil;
import com.lzhz.lxh.sleepmonitor.tools.ToastUtil;
import com.lzhz.lxh.sleepmonitor.tools.Tool;
import com.lzhz.lxh.sleepmonitor.tools.interfacetool.PermissionInter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.weyye.hipermission.PermissionItem;

/**
 * Created by dk on 2017/6/14.
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener ,PermissionInter {
    private EditText et_tel, et_nickname, et_name, et_height, et_weight;
    private TextView tv_gender, tv_birthday, snore_his;
    private ImageView iv_avator;
    private static boolean CAMERA;
    private static boolean WRITE_EXTERNAL_STORAGE;
    private static boolean READ_EXTERNAL_STORAGE;
    File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1, PHOTO_REQUEST_GALLERY = 2, PHOTO_REQUEST_CUT = 3;
    private String[] gender, his;
    private User user;
    RequestOptions options;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_userinfo);

        gender = new String[]{getString(R.string.male), getString(R.string.female)};
        his = new String[]{getString(R.string.had), getString(R.string.none)};
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.user)
                .error(R.mipmap.user)
                .priority(Priority.HIGH).transform(new GlideCircleTransform(this));
        initView();
        user = (User) getIntent().getSerializableExtra("user");
        if (user == null)
            initData();
        else {
            et_tel.setText(user.getPhone());
            et_height.setText(user.getHeight());
            et_weight.setText(user.getWeight());
            et_name.setText(user.getName());
            et_nickname.setText(user.getNickname());
            int i = user.getGender();
            if (i != 0) {
                tv_gender.setText(i == 1 ? getString(R.string.male) : getString(R.string.female));
            }
            tv_birthday.setText(user.getBirthday());
            snore_his.setText(user.getSnore_history());
            Glide.with(UserInfoActivity.this).load(user.getPortrait()).apply(options).into(iv_avator);
        }
    }
    private void setCamera(){
        PersissionUtils.setOnPermissionInter(this);
        PermissionItem Item = new PermissionItem(Manifest.permission.CAMERA, "相机权限", R.drawable.permission_ic_location);
        PermissionItem Item1 = new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "读取SD卡权限", R.drawable.permission_ic_location);
        PermissionItem Item2 = new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "写入SD卡权限", R.drawable.permission_ic_location);
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(Item);
        permissonItems.add(Item1);
        permissonItems.add(Item2);
        PersissionUtils.setPermissionList(this,permissonItems);
    }

    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText(R.string.update_userinfo);
        tvRight.setText(R.string.save);
        tvRight.setOnClickListener(this);
    }
    @Override
    public void initData() {
        user = new User();
        NetParamas netParamas = new NetParamas();
        netParamas.put("access_token", SleepMonitorApplication.access_token);
        NetUtil.get(Constance.HTTP_GET_INFO, netParamas, new NetCallBack() {
            @Override
            public void onResponse(JSONObject j) {
                try {
                    JSONObject result = j.getJSONObject("data");
                    et_tel.setText(result.optString("phone"));
                    et_height.setText(result.optString("height"));
                    et_weight.setText(result.optString("weight"));
                    et_name.setText(result.optString("name"));
                    et_nickname.setText(result.optString("nickname"));
                    int i = result.optInt("gender", 0);
                    if (i != 0) {
                        tv_gender.setText(i == 1 ? getString(R.string.male) : getString(R.string.female));
                    }

                    tv_birthday.setText(result.optString("birthday"));
                    snore_his.setText(result.optString("snore_history"));
                    Glide.with(UserInfoActivity.this).load(result.optString("portrait")).apply(options).into(iv_avator);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, getString(R.string.loading), this);
    }

    private void initView() {
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_name = (EditText) findViewById(R.id.et_name);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
        snore_his = (TextView) findViewById(R.id.snore_his);
        iv_avator = (ImageView) findViewById(R.id.iv_avator);
        iv_avator.setOnClickListener(this);
        tv_gender.setOnClickListener(this);
        snore_his.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);
    }

    private void updateUserInfo() {
        NetParamas netParamas = new NetParamas();
        netParamas.put("access_token", SleepMonitorApplication.access_token);
        netParamas.put("nickname", user.getNickname());
        netParamas.put("name", user.getName());
        netParamas.put("portrait", user.getPortrait());
        netParamas.put("height", user.getHeight());
        netParamas.put("weight", user.getWeight());
        netParamas.put("gender", user.getGender() + "");
        netParamas.put("birthday", user.getBirthday());
        netParamas.put("snore_history", user.getSnore_history());
        NetUtil.post(Constance.HTTP_UPDATE_INFO, netParamas, new NetCallBack() {
            @Override
            public void onResponse(JSONObject result) {
                Tool.showToast(UserInfoActivity.this, getString(R.string.update_success));
                Intent intent = new Intent();
                intent.putExtra("user",user);
                setResult(RESULT_OK,intent);
                try {
                    JSONObject data = result.getJSONObject("data");
                    SPUtil.getInstance(getApplicationContext()).put("userName",data.optString("name"));
                    SPUtil.getInstance(getApplicationContext()).put("userPortrait",data.optString("portrait"));
                    User use = new User();
                    use.setName(data.optString("name"));
                    use.setPortrait(data.optString("portrait"));
                    EventBus.getDefault().post(use);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                finish();
            }
        }, getString(R.string.uploading), this);
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_avator:
                showSimpleChioseDialog(new String[]{getString(R.string.take_pic), getString(R.string.choise_photo)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (which == 0) {
                            setCamera();
                            if (!CAMERA){
                                ToastUtil.showShort(UserInfoActivity.this,"请所有权限");
                            }
                            // 调用系统的拍照功能

                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    "image/*"
                            );
                            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                        }
                    }
                });
                break;
            case R.id.tv_gender:
                showSimpleChioseDialog(gender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        tv_gender.setText(gender[which]);
                    }
                });
                break;
            case R.id.snore_his:
                showSimpleChioseDialog(his, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        snore_his.setText(his[which]);
                    }
                });
                break;
            case R.id.tv_birthday:
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_birthday.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.tv_right_text:
                user.setPhone(et_tel.getText().toString().trim());
                user.setBirthday(tv_birthday.getText().toString().trim());
                if (TextUtils.isEmpty(tv_gender.getText().toString().trim()))
                    user.setGender(0);
                else
                    user.setGender(gender[0].equals(tv_gender.getText().toString().trim()) ? 1 : 2);
                user.setWeight(et_weight.getText().toString().trim());
                user.setHeight(et_height.getText().toString().trim());
                user.setName(et_name.getText().toString().trim());
                user.setNickname(et_nickname.getText().toString().trim());
                user.setSnore_history(snore_his.getText().toString().trim());
                updateUserInfo();
                break;
        }
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap avator = bundle.getParcelable("data");
            if (tempFile.exists()) {
                tempFile.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(tempFile);
                avator.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                Tool.showToast(this, getString(R.string.load_avator_fail));
            }
            List<String> image = new ArrayList<>();
            image.add(tempFile.getPath());
            NetParamas netParamas = new NetParamas();
            NetUtil.put(getString(R.string.uploading), Constance.HTTP_UPDATE_AVATOR, netParamas, image, new NetCallBack() {
                @Override
                public void onResponse(JSONObject result) {
                    try {
                        String url = result.getJSONObject("data").getString("urls");
                        user.setPortrait(url.substring(2, url.length() - 2).replaceAll("\\\\", ""));
                        Glide.with(UserInfoActivity.this).load(user.getPortrait()).apply(options).into(iv_avator);
                        tempFile.delete();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO://当选择拍照时调用
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    startPhotoZoom(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null)), 150);
                }
                break;
            case PHOTO_REQUEST_GALLERY://当选择从本地获取图片时
                //做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null)
                    startPhotoZoom(data.getData(), 150);
//                else Tool.showToast(this, getString(R.string.load_avator_fail));
                break;
            case PHOTO_REQUEST_CUT://返回的结果
                if (data != null)
                    setPicToView(data);
//                else Tool.showToast(this, getString(R.string.load_avator_fail));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClose() {

    }

    @Override
    public void onFinish() {
        CAMERA = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
//                            intent.putExtra(
//                                    MediaStore.EXTRA_OUTPUT,
//                                    Uri.fromFile(tempFile)
//                            );
        startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
    }

    @Override
    public void onDeny() {

    }

    @Override
    public void onGuarantee() {

    }
}
