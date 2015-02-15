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
package me.evervasquez.sociales.fragment.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidsocialnetworks.lib.SocialNetwork;
import com.androidsocialnetworks.lib.listener.OnLoginCompleteListener;

import me.evervasquez.sociales.R;
import me.evervasquez.sociales.fragment.base.BaseLoginDemoFragment;

public class InicioFragment extends BaseLoginDemoFragment implements OnLoginCompleteListener{

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_button,container,false);
    }

    @Override
    protected void onTwitterAction() {
        showProgress("Authentificating... Twitter");
        mSocialNetworkManager.getTwitterSocialNetwork().requestLogin();
    }

    @Override
    protected void onLinkedInAction() {
        showProgress("Authentificating... LinkedIn");
        mSocialNetworkManager.getLinkedInSocialNetwork().requestLogin();
    }

    @Override
    protected void onFacebookAction() {
        mSocialNetworkManager.getFacebookSocialNetwork().requestLogin();
    }

    @Override
    protected void onGooglePlusAction() {
        mSocialNetworkManager.getGooglePlusSocialNetwork().requestLogin();
    }

    @Override
    public void onLoginSuccess(int i) {
// let's reset buttons, we need to disable buttons
        onSocialNetworkManagerInitialized();

        hideProgress();
        handleSuccess("onLoginSuccess", "Now you can try other API Demos.");
    }

    @Override
    public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
        hideProgress();
        handleError(errorMessage);
    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        super.onSocialNetworkManagerInitialized();

        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
        }
    }
}
