/*
 *
 *  * Copyright (C) 2015 eveR Vásquez.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package me.evervasquez.sociales.fragment.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidsocialnetworks.lib.SocialNetwork;
import com.androidsocialnetworks.lib.SocialNetworkManager;

import me.evervasquez.sociales.R;
import me.evervasquez.sociales.fragment.dialog.AlertDialogFragment;
import me.evervasquez.sociales.fragment.dialog.ProgressDialogFragment;

public abstract class BaseFragment extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, View.OnClickListener{
    private static final String TAG = BaseFragment.class.getSimpleName();

    public static final String SOCIAL_NETWORK_TAG = "BaseLoginDemoFragment.SOCIAL_NETWORK_TAG";
    private static final String PROGRESS_DIALOG_TAG = "BaseDemoFragment.PROGRESS_DIALOG_TAG";
    protected SocialNetworkManager mSocialNetworkManager;
    protected boolean mSocialNetworkManagerInitialized = false;

    protected Button mTwitterButton;
    protected Button mLinkedInButton;
    protected Button mFacebookButton;
    protected Button mGooglePlusButton;

    protected abstract void onTwitterAction();

    protected abstract void onLinkedInAction();

    protected abstract void onFacebookAction();

    protected abstract void onGooglePlusAction();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_button, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);

        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = SocialNetworkManager.Builder.from(getActivity())
                    .twitter("3IYEDC9Pq5SIjzENhgorlpera", "fawjHMhyzhrfcFKZVB6d5YfiWbWGmgX7vPfazi61xZY9pdD1aE")
                    .linkedIn("77ieoe71pon7wq", "pp5E8hkdY9voGC9y", "r_basicprofile+rw_nus+r_network+w_messages")
                    .facebook()
                    .googlePlus()
                    .build();
            getFragmentManager().beginTransaction().add(mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();

            mSocialNetworkManager.setOnInitializationCompleteListener(this);
        } else {
            // we need to setup buttons correctly, mSocialNetworkManager isn't null, so
            // we are sure that it was initialized
            mSocialNetworkManagerInitialized = true;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTwitterButton = (Button) view.findViewById(R.id.twitter_button);
        mLinkedInButton = (Button) view.findViewById(R.id.linkedin_button);
        mFacebookButton = (Button) view.findViewById(R.id.facebook_button);
        mGooglePlusButton = (Button) view.findViewById(R.id.google_plus_button);

        mTwitterButton.setOnClickListener(this);
        mLinkedInButton.setOnClickListener(this);
        mFacebookButton.setOnClickListener(this);
        mGooglePlusButton.setOnClickListener(this);

        if (mSocialNetworkManagerInitialized) {
            onSocialNetworkManagerInitialized();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        onRequestCancel();
    }

    protected void showProgress(String text) {
        ProgressDialogFragment progressDialogFragment = ProgressDialogFragment.newInstance(text);
        progressDialogFragment.setTargetFragment(this, 0);
        progressDialogFragment.show(getFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    protected void hideProgress() {
        Fragment fragment = getFragmentManager().findFragmentByTag(PROGRESS_DIALOG_TAG);

        if (fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    protected void handleError(String text) {
        AlertDialogFragment.newInstance("Error", text).show(getFragmentManager(), null);
    }

    protected void handleSuccess(String title, String message) {
        AlertDialogFragment.newInstance(title, message).show(getFragmentManager(), null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.twitter_button:
                onTwitterAction();
                break;
            case R.id.linkedin_button:
                onLinkedInAction();
                break;
            case R.id.facebook_button:
                onFacebookAction();
                break;
            case R.id.google_plus_button:
                onGooglePlusAction();
                break;
            default:
                throw new IllegalArgumentException("Can't find click handler for: " + v);
        }
    }

    @Override
    public void onSocialNetworkManagerInitialized() {

    }

    protected boolean checkIsLoginned(int socialNetworkID) {
        if (mSocialNetworkManager.getSocialNetwork(socialNetworkID).isConnected()) {
            return true;
        }

        AlertDialogFragment
                .newInstance("Request Login", "This action request login, please go to login demo first.")
                .show(getFragmentManager(), null);

        return false;
    }

    public void onRequestCancel() {
        Log.d(TAG, "BaseDemoFragment.onRequestCancel");

        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.cancelAll();
        }
    }
}
