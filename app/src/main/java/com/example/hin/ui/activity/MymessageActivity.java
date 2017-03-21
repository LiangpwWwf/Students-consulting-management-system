package com.example.hin.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.common.LHImageSelectHandler;
import com.example.hin.common.UserPref;
import com.example.hin.common.select.ImageCrop;
import com.example.hin.common.select.interfaces.ImageSelectHook;
import com.example.hin.entity.User;
import com.example.hin.system.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Hin on 2016/6/1.
 */
@RuntimePermissions
public class MymessageActivity extends Activity implements
        View.OnClickListener, ImageSelectHook {

    @BindView(R.id.iv_head)
    SimpleDraweeView ivHead;
    @BindView(R.id.tv_student_id)
    TextView tvStudentId;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.et_department)
    EditText etDepartment;
    @BindView(R.id.et_grade)
    EditText etGrade;
    @BindView(R.id.et_email)
    EditText etMail;

    private ImageView iv_back;
    private ImageView ivEdit;
    private boolean canInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
        LHImageSelectHandler.get().setImageSelectHook(this);
        //用于退出程序
        CloseActivity.activityList.add(this);

        iniView();
        iniListener();
    }

    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        ivEdit = (ImageView) findViewById(R.id.iv_edit);

        ivHead.setImageURI(UserPref.get().get(UserPref.KEY_AVATAR));
        tvStudentId.setText(UserPref.get().get(UserPref.KEY_USERNAME));
        etName.setText(UserPref.get().get(UserPref.KEY_NICKNAME));
        tvSex.setText(UserPref.get().get(UserPref.KEY_SEX));
        etMail.setText(UserPref.get().get(UserPref.KEY_EMAIL));
    }

    //监听事件
    public void iniListener() {
        iv_back.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        ivHead.setOnClickListener(this);
    }

    @NeedsPermission({
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    protected void showSelectImgActivity() {
        startActivity(new Intent(MymessageActivity.this, SelectImageActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head:
                MymessageActivityPermissionsDispatcher.showSelectImgActivityWithCheck(this);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_edit:
                if (canInput) {
                    User newUser = new User();
                    newUser.setNickname(etName.getText().toString().trim());
                    newUser.setDepartment(etDepartment.getText().toString().trim());
                    newUser.setGrade(etGrade.getText().toString().trim());
                    newUser.setEmail(etMail.getText().toString().trim());
                    newUser.update(MymessageActivity.this, UserPref.get().get(UserPref.KEY_UID), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            UserPref.get().set(UserPref.KEY_NICKNAME, etName.getText().toString().trim());
                            UserPref.get().set(UserPref.KEY_DEPARTMENT, etDepartment.getText().toString().trim());
                            UserPref.get().set(UserPref.KEY_GRADE, etGrade.getText().toString().trim());
                            UserPref.get().set(UserPref.KEY_EMAIL, etMail.getText().toString().trim());
                            Toast.makeText(MymessageActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            ivEdit.setImageResource(R.drawable.edit);
                            setResult(RESULT_OK);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                } else {
                    ivEdit.setImageResource(R.mipmap.ico_finish);
                }
                changeInputType(!canInput);
                canInput = !canInput;
                break;
            default:
                break;
        }
    }

    public void changeInputType(boolean canInput) {
        ivHead.setClickable(canInput);
        etName.setEnabled(canInput);
        etDepartment.setEnabled(canInput);
        etGrade.setEnabled(canInput);
        etMail.setEnabled(canInput);
    }

    @Override
    public void onSelectImage(final File image) {
        final BmobFile file = new BmobFile(image);
        file.upload(MymessageActivity.this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                UserPref.get().set(UserPref.KEY_AVATAR, file.getFileUrl(MymessageActivity.this));
                ivHead.setController(Fresco.newDraweeControllerBuilder()
                        .setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(image))
                                .setResizeOptions(new ResizeOptions(160, 160)).build()).build());
                setResult(RESULT_OK);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MymessageActivity.this, "头像修改失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onSelectImages(List<File> images) {

    }

    @Override
    public boolean isMultiSelect() {
        return false;
    }

    @Override
    public ImageCrop customizeImageCrop(ImageCrop rawCrop) {
        return null;
    }

    @Override
    public int getImageSelectLimit() {
        return 1;
    }
}
