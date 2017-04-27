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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.bobo.communityservice.R;
import com.bobo.communityservice.model.CommunityUser;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class LoginFragment extends Fragment {
    private UserLoginTask mAuthTask = null;
    String TAG ="login";
    private Activity activity;
    ProgressDialog mProgressView;
    AutoCompleteTextView userName;
    EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        loginBind = DataBindingUtil.inflate(inflater,R.layout.fragment_login_layout,container,false);
        View view = inflater.inflate(R.layout.fragment_login_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressView = new ProgressDialog(getActivity());
        mProgressView.setMessage("Login...");
        userName = (AutoCompleteTextView) view.findViewById(R.id.user_name);
        password = (EditText) view.findViewById(R.id.password);
        view.findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("zzb","onloginclick!");
                if (mAuthTask != null) {
                    return;
                }

                // Reset errors.
                userName.setError(null);
                userName.setError(null);

                String uName = userName.getText().toString();
                String pd = password.getText().toString();

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(pd) || !isPasswordValid(pd)) {
                    password.setError(getString(R.string.error_invalid_password));
                    focusView = password;
                    cancel = true;
                }

                if (TextUtils.isEmpty(uName)) {
                    userName.setError(getString(R.string.error_field_required));
                    focusView = userName;
                    cancel = true;
                } else if (!isUserNameValid(uName)) {
                    userName.setError(getString(R.string.error_invalid_user_name));
                    focusView = userName;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    showProgress(true);
                    mAuthTask = new UserLoginTask(uName, pd);
                    mAuthTask.execute((Void) null);
                }
            }
        });

        view.findViewById(R.id.to_register_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("zzb","registerAccountClick!");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment registerFragment = new RegisterFragment();
                transaction.replace(R.id.fragment_layout, registerFragment);
                transaction.commit();
            }
        });

    }


    private boolean isUserNameValid(String userName) {
        return userName.length() > 8;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    private void showProgress(final boolean show) {
        if (show) {
            mProgressView.show();
        } else {
            mProgressView.dismiss();
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, DroiError> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected DroiError doInBackground(Void... params) {
            DroiError droiError = new DroiError();
            CommunityUser user = DroiUser.login(mEmail, mPassword, CommunityUser.class, droiError);
            return droiError;
        }

        @Override
        protected void onPostExecute(final DroiError droiError) {
            mAuthTask = null;
            showProgress(false);
            if (droiError.isOk()) {
                Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();
                activity.finish();
            } else {
                if (droiError.getCode() == DroiError.USER_NOT_EXISTS) {
                    userName.setError(getString(R.string.error_user_not_exists));
                    userName.requestFocus();
                } else if (droiError.getCode() == DroiError.USER_PASSWORD_INCORRECT) {
                    password.setError(getString(R.string.error_incorrect_password));
                    password.requestFocus();
                } else {
                    Log.i(TAG, "error:" + droiError.toString());
                    Toast.makeText(getActivity(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();
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
