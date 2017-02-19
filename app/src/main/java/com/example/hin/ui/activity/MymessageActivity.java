package com.example.hin.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.common.LHImageSelectHandler;
import com.example.hin.common.select.ImageCrop;
import com.example.hin.common.select.interfaces.ImageSelectHook;
import com.example.hin.entity.User;
import com.example.hin.system.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Hin on 2016/6/1.
 */
@RuntimePermissions
public class MymessageActivity extends Activity implements
        View.OnClickListener,ImageSelectHook{

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
    private User user;
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

        user = BmobUser.getCurrentUser(this, User.class);
        tvStudentId.setText(user.getStudentId());
        etName.setText(user.getUsername());
        tvSex.setText(user.getSex());
        etMail.setText(user.getEmail());
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
                    ivEdit.setImageResource(R.drawable.edit);
                } else {
                    User newUser = new User();
                    newUser.setUsername(etName.getText().toString().trim());
                    newUser.setDepartment(etDepartment.getText().toString().trim());
                    newUser.setGrade(etGrade.getText().toString().trim());
                    newUser.setEmail(etMail.getText().toString().trim());
                    newUser.update(MymessageActivity.this, user.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(MymessageActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            ivEdit.setImageResource(R.mipmap.ico_finish);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
                changeInputType(!canInput);
                canInput = !canInput;
                break;
            default:
                break;
        }
    }

    public void changeInputType(boolean canInput) {
        etName.setEnabled(canInput);
        etDepartment.setEnabled(canInput);
        etGrade.setEnabled(canInput);
        etMail.setEnabled(canInput);
    }

    @Override
    public void onSelectImage(File image) {

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
