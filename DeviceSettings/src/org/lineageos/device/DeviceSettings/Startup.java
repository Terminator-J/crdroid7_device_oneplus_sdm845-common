/*
 * Copyright (C) 2018-2021 crDroid Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.device.DeviceSettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.preference.PreferenceManager;

public class Startup extends BroadcastReceiver {

    private boolean mHBM = false;

    @Override
    public void onReceive(final Context context, final Intent bootintent) {

	DeviceSettings.restoreSliderStates(context);

        VibratorStrengthPreference.restore(context);
        VibratorCallStrengthPreference.restore(context);
        VibratorNotifStrengthPreference.restore(context);

        boolean enabled = false;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_SRGB_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(SRGBModeSwitch.getFile(), enabled);
 	       }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_HBM_SWITCH, false);
        if (enabled) {
        mHBM = true;
        restore(HBMModeSwitch.getFile(), enabled);
               }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DC_SWITCH, false);
        if (enabled) {
        DCModeSwitch.setEnabled(enabled);
               }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DCI_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(DCIModeSwitch.getFile(), enabled);
               }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_WIDE_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(WideModeSwitch.getFile(), enabled);
               }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_FPS_INFO, false);
        if (enabled) {
            context.startService(new Intent(context, FPSInfoService.class));
        }

        Utils.enableService(context);
    }

    private void restore(String file, boolean enabled) {
        if (file == null) {
            return;
        }
        if (enabled) {
            Utils.writeValue(file, "1");
        }
    }

    private void restore(String file, String value) {
        if (file == null) {
            return;
        }
        Utils.writeValue(file, value);
    }
}
