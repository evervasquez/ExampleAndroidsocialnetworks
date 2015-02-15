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

import android.graphics.Color;
import android.view.View;

public abstract class BaseLoginDemoFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public void onSocialNetworkManagerInitialized() {
        if (mSocialNetworkManager.getTwitterSocialNetwork().isConnected()) {
            mTwitterButton.setText("Twitter connected");
            mTwitterButton.setBackgroundColor(Color.LTGRAY);
            mTwitterButton.setOnClickListener(null);
        }

        if (mSocialNetworkManager.getLinkedInSocialNetwork().isConnected()) {
            mLinkedInButton.setText("LinkedIn connected");
            mLinkedInButton.setBackgroundColor(Color.LTGRAY);
            mLinkedInButton.setOnClickListener(null);
        }

        if (mSocialNetworkManager.getFacebookSocialNetwork().isConnected()) {
            mFacebookButton.setText("Facebook connected");
            mFacebookButton.setBackgroundColor(Color.LTGRAY);
            mFacebookButton.setOnClickListener(null);
        }

        if (mSocialNetworkManager.getGooglePlusSocialNetwork().isConnected()) {
            mGooglePlusButton.setText("Google Plus connected");
            mGooglePlusButton.setBackgroundColor(Color.LTGRAY);
            mGooglePlusButton.setOnClickListener(null);
        }
    }

}
