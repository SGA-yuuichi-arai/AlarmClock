package com.so.okamnk.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    public enum PhoneState {
        STATE_IDLE,
        STATE_CALL
    }

    public interface OnPhoneStateChangedListener {

        void onPhoneStateChanged(PhoneState state);
    }

    TelephonyManager telephonyManager;
    PhoneReceiver phoneReceiver;

    static boolean isListen = false;

    @Override
    public void onReceive(Context context, Intent intent) {

        phoneReceiver = new PhoneReceiver(context);
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (!isListen) {
            telephonyManager.listen(phoneReceiver, PhoneStateListener.LISTEN_CALL_STATE);
            isListen = true;
        }
    }

    public void setOnPhoneStateChangedListener(OnPhoneStateChangedListener listener) {
        phoneReceiver.setOnPhoneStateChangedListener(listener);
    }

    private class PhoneReceiver extends PhoneStateListener {

        Context context;
        OnPhoneStateChangedListener listener;

        public PhoneReceiver(Context context) {
            this.context = context;
        }

        @Override
        public void onCallStateChanged(int state, String incommingNumber) {
            super.onCallStateChanged(state, incommingNumber);

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    if (listener != null) {
                        listener.onPhoneStateChanged(PhoneState.STATE_IDLE);
                    }
                    break;

                case TelephonyManager.CALL_STATE_RINGING:
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (listener != null) {
                        listener.onPhoneStateChanged(PhoneState.STATE_CALL);
                    }
                    break;
            }
        }

        public void setOnPhoneStateChangedListener(OnPhoneStateChangedListener listener) {
            this.listener = listener;
        }

    }
}
