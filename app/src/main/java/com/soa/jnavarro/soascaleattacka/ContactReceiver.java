package com.soa.jnavarro.soascaleattacka;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Class to make operations when an Intent is sent.
 */
public class ContactReceiver extends BroadcastReceiver {
    @Override
    /** Overwritten method that runs when the intent is cached */
    public void onReceive(Context context, Intent intent) {
        Log.i("Editcontact", " critial action  intent");
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "Critical", duration);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.RED);
        toast.show();
        String name = intent.getStringExtra(ContactsContract.Intents.Insert.NAME);
        String phone = intent.getStringExtra(ContactsContract.Intents.Insert.PHONE);
        Log.i("Name", name);
        Log.i("Phone", phone);
        ContactUpdater contactUpdater = new ContactUpdater(context.getContentResolver());
        contactUpdater.updateContactPhoneByName(name,phone);
    }
}
