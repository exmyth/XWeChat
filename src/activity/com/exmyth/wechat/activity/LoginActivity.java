package com.exmyth.wechat.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.base.BaseActivity;
import com.exmyth.wechat.listener.SimpleTextWatcher;

import android.content.Context;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.InjectView;


public class LoginActivity extends BaseActivity implements View.OnClickListener{

	@InjectView(R.id.et_username)
    EditText mEtUserName;

    @InjectView(R.id.et_password)
    EditText mEtPassword;

    @InjectView(R.id.iv_clear_username)
    View mIvClearUserName;

    @InjectView(R.id.iv_clear_password)
    View mIvClearPassword;

    @InjectView(R.id.btn_login)
    Button mBtnLogin;
    
    private Context mContext;
    
	@Override
	protected int getLayoutId() {
		return R.layout.activity_login;
	}
	
	private final TextWatcher mUserNameWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            mIvClearUserName
                    .setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    };
    private final TextWatcher mPassswordWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            mIvClearPassword
                    .setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    };
	
	@Override
	public void initView() {
		mIvClearUserName.setOnClickListener(this);
        mIvClearPassword.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

        mEtUserName.addTextChangedListener(mUserNameWatcher);
        mEtPassword.addTextChangedListener(mPassswordWatcher);
	}

	@Override
	public void initData() {
		mContext = this;
	}
	
	@Override
	public void onClick(View v) {

        int id = v.getId();
        switch (id) {
        case R.id.iv_clear_username:
            mEtUserName.getText().clear();
            mEtUserName.requestFocus();
            break;
        case R.id.iv_clear_password:
            mEtPassword.getText().clear();
            mEtPassword.requestFocus();
            break;
        case R.id.btn_login:
            handleLogin();
            break;
        default:
            break;
        }
    }

	private void handleLogin() {
		Toast.makeText(mContext, "handleLogin", Toast.LENGTH_SHORT).show();
	}
}
