package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.adapter.CommentListAdapter;
import com.example.hin.entity.Comment;
import com.example.hin.entity.Post;
import com.example.hin.entity.Reply;
import com.example.hin.system.R;
import com.example.hin.ui.widget.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogRecord;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WWF on 2016/6/5.
 */
public class QuestionDetailActivity extends Activity implements View.OnClickListener {

    private ScrollView scrollView;
    private ListViewForScrollView lvComment;
    private TextView tv_title, tv_kind, tv_topic, tv_content, tv_reply,tv_comment_count;
    private CommentListAdapter adapter;
    private List<Comment> commentlist = new ArrayList<>();
    private LinearLayout commentLinear, ll_boomlinear;
    private EditText commentEdit;
    private Button commentButton;
    private FrameLayout fl_comment_count;

    private int position;                //记录回复评论的索引
    private boolean isReply;
    private String reply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        findView();
        initView();
        getQuestionDetail();
        uploadComment();

    }

    /*
      *获取控件ID
      */
    private void findView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_kind = (TextView) findViewById(R.id.tv_kind);
        tv_topic = (TextView) findViewById(R.id.tv_topic);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_reply = (TextView) findViewById(R.id.tv_reply);
        scrollView = (ScrollView) findViewById(R.id.scroll_detail);
        lvComment = (ListViewForScrollView) findViewById(R.id.lv_comment);
        commentLinear = (LinearLayout) findViewById(R.id.commentLinear);
        commentEdit = (EditText) findViewById(R.id.commentEdit);
        commentButton = (Button) findViewById(R.id.commentButton);
        ll_boomlinear = (LinearLayout) findViewById(R.id.ll_boomlinear);
        fl_comment_count=(FrameLayout)findViewById(R.id.fl_comment_count);
        tv_comment_count=(TextView)findViewById(R.id.tv_comment_count);
    }

    /*
   * 设置监听事件
   */
    private void initView() {
        scrollView.smoothScrollTo(0, 0);

        findViewById(R.id.iv_back).setOnClickListener(this);
        commentButton.setOnClickListener(this);
        findViewById(R.id.tv_comment).setOnClickListener(this);
        fl_comment_count.setOnClickListener(this);
    }

    /*
    从服务器获得问题详情
    */
    public void getQuestionDetail() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Post post = (Post) bundle.getSerializable("postObject");
        post.getObjectId();
        tv_title.setText(post.getTitle());
        tv_kind.setText(post.getKind());
        tv_topic.setText(post.getTopic());
        tv_content.setText(post.getContent());
        tv_reply.setText(post.getReply());
        Toast.makeText(QuestionDetailActivity.this, post.getObjectId(), Toast.LENGTH_SHORT).show();
        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
        post.getCreatedAt();
    }

    /*
    从服务器加载问题相关的评论
    */
    public void uploadComment() {
        Intent intent = QuestionDetailActivity.this.getIntent();
        Bundle bundle = intent.getExtras();
        Post post = (Post) bundle.getSerializable("postObject");
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        query.addWhereEqualTo("post", new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.findObjects(QuestionDetailActivity.this, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                commentlist = list;
                /*
                获得评论人数
                */
                tv_comment_count.setText(String.valueOf(commentlist.size()));

                adapter = new CommentListAdapter(QuestionDetailActivity.this, commentlist, handler);
                lvComment.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /*
    * 接受回复评论点击事件回传的handler
    */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                isReply = true;
                position = (Integer) msg.obj;
                commentLinear.setVisibility(View.VISIBLE);
                ll_boomlinear.setVisibility(View.GONE);
                onFocusChange(true);
            }

        }
    };

    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        commentEdit.getContext().getSystemService(INPUT_METHOD_SERVICE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (isFocus) {
                    //显示输入法
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
                }
            }
        }, 100);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_comment:
                showCommentDialog();
                break;
            case R.id.commentButton:
                if (isEditEmply()) {
                    uploadCommengReply();
                }
                commentLinear.setVisibility(View.GONE);
                ll_boomlinear.setVisibility(View.VISIBLE);
                onFocusChange(false);
                break;
            case R.id.fl_comment_count:
                scrollView.smoothScrollTo((int) lvComment.getX(), (int) lvComment.getY());
                break;
            default:
                break;
        }
    }

    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        reply = commentEdit.getText().toString().trim();
        if (reply.equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        commentEdit.setText("");
        return true;
    }

    /*
    显示评论弹窗
    */
    public void showCommentDialog() {
        onFocusChange(true);
        final EditText editText = new EditText(this);
        editText.setHint("忍不住说点什么");
        editText.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        editText.setHeight(150);
        new AlertDialog.Builder(this).setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadComment(editText);
                onFocusChange(false);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onFocusChange(false);
            }
        }).show();
    }

    /*
    * 将评论上传到服务器
    */
    public void uploadComment(EditText editText) {
        Intent intent = QuestionDetailActivity.this.getIntent();
        Bundle bundle = intent.getExtras();
        final Post post = (Post) bundle.getSerializable("postObject");
        post.getObjectId();
        final Comment comment = new Comment();
        if (!editText.getText().toString().equals("")) {
            comment.setCommentContent(editText.getText().toString());
            comment.setPost(post);
            // comment.setUser(user);
            comment.save(QuestionDetailActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(QuestionDetailActivity.this, "评论以发表！", Toast.LENGTH_SHORT).show();
                            commentlist.add(comment);
                            /*
                            更新评论人数
                            */
                            tv_comment_count.setText(String.valueOf(commentlist.size()));
                            post.setCommentCount(commentlist.size());
                            post.update(QuestionDetailActivity.this, post.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(QuestionDetailActivity.this, "评论人数！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(QuestionDetailActivity.this, "评论人数更新失败！", Toast.LENGTH_SHORT).show();
                                }
                            });

                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            } else {
                                adapter = new CommentListAdapter(QuestionDetailActivity.this, commentlist, handler);
                                lvComment.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(QuestionDetailActivity.this, "评论发表失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        } else {
            Toast.makeText(QuestionDetailActivity.this, "评论内容不能为空啊！", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    将评论的回复上传到服务器
    */
    public void uploadCommengReply() {

        final Reply bean = new Reply();
        bean.setCommentNickname("潇潇");
        bean.setReplyNickname("我是回复的人");
        bean.setReplyContent(reply);
        //获得回复对应的评论
        Comment comment = commentlist.get(position);
        if(comment==null||comment.getReplyList()==null)
        {
            List<Reply> replyList=new ArrayList<>();
            comment.setReplyList(replyList);
        }
        List<Reply> list=comment.getReplyList();
        list.add(bean);
        comment.setReplyList(list);
        comment.setReplyCount(list.size());
        String id=(String)comment.getObjectId();
        comment.update(QuestionDetailActivity.this, id, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(QuestionDetailActivity.this, "回复发表成功！", Toast.LENGTH_SHORT).show();
                if (adapter == null) {
                    adapter = new CommentListAdapter(QuestionDetailActivity.this, commentlist, handler);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(QuestionDetailActivity.this, "回复发表失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}