package com.bobo.communityservice.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo.communityservice.R;
import com.bobo.communityservice.model.CommunityUser;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiUser;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class RegisterFragment extends Fragment {
    private ProgressDialog mProgressView;
    private Activity activity;
    EditText mUserNameView;
    EditText mPasswordView;
    EditText mConfirmPasswordView;
    private UserRegisterTask mAuthTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        registerBind = DataBindingUtil.inflate(inflater,R.layout.fragment_register_layout,container,false);
        View view = inflater.inflate(R.layout.fragment_register_layout,container,false);
        mProgressView = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserNameView = (EditText)view.findViewById(R.id.user_name);
        mPasswordView =  (EditText)view.findViewById(R.id.password);
        mConfirmPasswordView =  (EditText)view.findViewById(R.id.confirm_password);

        view.findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        view.findViewById(R.id.to_login_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment loginFragment = new LoginFragment();
                transaction.replace(R.id.fragment_layout, loginFragment);
                transaction.commit();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "RegisterFragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "RegisterFragment");
    }

    private boolean isUserNameValid(String userName) {
        return userName.length() > 8;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    private boolean isConfirmPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private void showProgress(final boolean show) {
        if (show) {
            mProgressView.show();
        } else {
            mProgressView.dismiss();
        }
    }



    private void attemptRegister(){
        if (mAuthTask != null) {
            return;
        }

        mUserNameView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isConfirmPasswordValid(password, confirmPassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_password_not_same));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        } else if (!isUserNameValid(userName)) {
            mUserNameView.setError(getString(R.string.error_invalid_user_name));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            showProgress(true);
            mAuthTask = new UserRegisterTask(userName, password);
            mAuthTask.execute((Void) null);
        }
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, DroiError> {
        private final String mPassword;
        private final String mUserName;

        UserRegisterTask(String userName, String password) {
            mPassword = password;
            mUserName = userName;
        }

        @Override
        protected DroiError doInBackground(Void... params) {
//            CommunityUser
            CommunityUser user = DroiUser.getCurrentUser(CommunityUser.class);
            if(user != null&& user.isAnonymous()){
            }else{
                user = new CommunityUser();
            }
            user.setUserId(mUserName);
            user.setPassword(mPassword);
            DroiError droiError = user.signUp();

            return droiError;
        }

        @Override
        protected void onPostExecute(final DroiError droiError) {
            mAuthTask = null;
            showProgress(false);
            Log.i("test", "error" + droiError.toString());
            if (droiError.isOk()) {
                activity.finish();
            } else {
                if (droiError.getCode() == DroiError.USER_ALREADY_EXISTS) {
                    mUserNameView.setError(getString(R.string.error_user_already_exists));
                    mUserNameView.requestFocus();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error_network), Toast.LENGTH_SHORT);
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
