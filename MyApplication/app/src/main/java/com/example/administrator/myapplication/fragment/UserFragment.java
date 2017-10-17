package com.example.administrator.myapplication.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entity.MyUser;
import com.example.administrator.myapplication.ui.CourierActivity;
import com.example.administrator.myapplication.ui.LoginActivity;
import com.example.administrator.myapplication.ui.PhoneActivity;
import com.example.administrator.myapplication.util.L;
import com.example.administrator.myapplication.util.ShareUtils;
import com.example.administrator.myapplication.util.UtilTools;
import com.example.administrator.myapplication.view.CustomDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

    private Button btn_update_ok;
    //圆形头像
    private CircleImageView profile_image;

    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    //物流查询
    private TextView tv_courier;
    //归属地查询
    private TextView tv_phone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,null);
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {
        btn_exit_user = view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);
        tv_courier = view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);

        et_username = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_desc = view.findViewById(R.id.et_desc);

        btn_update_ok = view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        profile_image = view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        //第一步：拿到String
        String imgString = ShareUtils.getString(getActivity(),"image_title","");
        if(!imgString.equals("")){
            //第二步：利用Base64将我们的String转换
            byte[] byteArray = Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //第三步：生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            profile_image.setImageBitmap(bitmap);
        }

        //初始化dialog
        dialog = new CustomDialog(getActivity(), 0, 0,
                R.layout.dialog_photo, R.style.pop_anim_style, Gravity.BOTTOM, 0);
        //屏幕外点击无效
        dialog.setCancelable(false);
        btn_camera = dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        //默认不可输入和不可点击的
        setEnabled(false);


        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_age.setText(userInfo.getAge()+"");
        et_sex.setText(userInfo.isSex()?"男":"女");
        et_desc.setText(userInfo.getDesc());
    }

    private void setEnabled(boolean is){
        et_username.setEnabled(false);
        et_sex.setEnabled(false);
        et_age.setEnabled(false);
        et_desc.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //退出登录
            case R.id.btn_exit_user:
                //清除缓存对象
                MyUser.logOut();
                //现在currentUser是null了
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            //编辑资料
            case R.id.edit_user:
                //setEnabled(true);
                et_username.setEnabled(true);
                et_sex.setEnabled(true);
                et_age.setEnabled(true);
                et_desc.setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //1.拿到输入框的值
                String username = et_username.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String sex = et_sex.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();

                //2.判断是否为空
                if(!TextUtils.isEmpty(username) &&
                        !TextUtils.isEmpty(age) &&
                        !TextUtils.isEmpty(sex)){
                    //3.更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    if(sex.equals("男")){
                        user.setSex(true);
                    }else{
                        user.setSex(false);
                    }
                    //简介
                    if(!TextUtils.isEmpty(desc)){
                        user.setDesc(desc);
                    }else{
                        user.setDesc("这个人很懒，什么都没有留下");
                    }

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                //修改成功
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                System.out.println("In the Image File");
                toPicture();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(),CourierActivity.class));
                break;
            case R.id.tv_phone:
                startActivity(new Intent(getActivity(),PhoneActivity.class));
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用,可用就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != getActivity().RESULT_CANCELED){
            switch (requestCode){
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if(data != null){
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if(tempFile != null){
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri){
        if(uri == null){
            L.e("uri == null");
            return;
        }


        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");


        //设置裁剪
        intent.putExtra("crop","true");
        //裁剪宽高
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪图片的质量
        intent.putExtra("outputX","320");
        intent.putExtra("outputY","320");
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent,RESULT_REQUEST_CODE);

    }

    //设置图片
    private void setImageToView(Intent data){
        Bundle bundle = data.getExtras();
        if(bundle != null){
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存
        BitmapDrawable drawable = (BitmapDrawable) profile_image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //第二步：利用base64将我们的字节数组输出流转化成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //第三步：将String保存在ShareUtils
        ShareUtils.putString(getActivity(),"image_title",imgString);
    }
}
