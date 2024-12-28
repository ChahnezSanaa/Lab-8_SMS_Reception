package com.chahnez.smsapplication2;

import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class ReceiveSms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Vérifier si l'action correspond à l'action reçue par SMS
        if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            Bundle bundle = intent.getExtras(); // Obtenir les suppléments d'intent
            if (bundle != null) {
                try {
                    // Retrieve the SMS message(s) from the PDU (Protocol Data Unit)
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    if (pdus != null) {
                        SmsMessage[] msgs = new SmsMessage[pdus.length];
                        String msgFrom = null;
                        StringBuilder msgBody = new StringBuilder();
                        for (int i = 0; i < pdus.length; i++) {
                            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            if (i == 0) {
                                msgFrom = msgs[i].getOriginatingAddress(); // Get sender
                            }
                            msgBody.append(msgs[i].getMessageBody()); // Concatenate message body
                        }
                        // Display the SMS details in a Toast
                        Toast.makeText(context, "From: " + msgFrom + "\nMessage: " + msgBody, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Use logging in production
                }
            }
        }
    }
}
