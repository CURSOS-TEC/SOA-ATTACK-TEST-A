package com.soa.jnavarro.soascaleattacka;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactUpdater {

    private ContentResolver mContentResolver;

    public ContactUpdater (ContentResolver pContantResolver){
        this.mContentResolver = pContantResolver;
    }

    private long getRawContactIdByName(String givenName)
    {


        // Query raw_contacts table by display name field ( given_name family_name ) to get raw contact id.

        // Create query column array.
        String queryColumnArr[] = {ContactsContract.RawContacts._ID};

        // Create where condition clause.
        String displayName = givenName;
        String whereClause = ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY + " = '" + displayName + "'";

        // Query raw contact id through RawContacts uri.
        Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;

        // Return the query cursor.
        Cursor cursor = this.mContentResolver.query(rawContactUri, queryColumnArr, whereClause, null, null);

        long rawContactId = -1;

        if(cursor!=null)
        {
            // Get contact count that has same display name, generally it should be one.
            int queryResultCount = cursor.getCount();
            // This check is used to avoid cursor index out of bounds exception. android.database.CursorIndexOutOfBoundsException
            if(queryResultCount > 0)
            {
                // Move to the first row in the result cursor.
                cursor.moveToFirst();
                // Get raw_contact_id.
                rawContactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.RawContacts._ID));
            }
        }

        return rawContactId;
    }


    /*
     * Update contact phone number by contact name.
     * Return update contact number, commonly there should has one contact be updated.
     */
    public int  updateContactPhoneByName(String givenName, String newPhone)
    {
        int ret = 0;

        ContentResolver contentResolver = this.mContentResolver;

        // Get raw contact id by display name.
        long rawContactId = getRawContactIdByName(givenName);

        // Update data table phone number use contact raw contact id.
        if(rawContactId > -1) {
            // Update mobile phone number.
            updatePhoneNumber(contentResolver, rawContactId, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE, newPhone);

            // Update work mobile phone number.
            updatePhoneNumber(contentResolver, rawContactId, ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE, newPhone);

            // Update home phone number.
            updatePhoneNumber(contentResolver, rawContactId, ContactsContract.CommonDataKinds.Phone.TYPE_HOME, newPhone);

            ret = 1;
        }else
        {
            ret = 0;
        }

        return ret;
    }

    /* Update phone number with raw contact id and phone type.*/
    private void updatePhoneNumber(ContentResolver contentResolver, long rawContactId, int phoneType, String newPhoneNumber)
    {
        // Create content values object.
        ContentValues contentValues = new ContentValues();

        // Put new phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, newPhoneNumber);

        // Create query condition, query with the raw contact id.
        StringBuffer whereClauseBuf = new StringBuffer();

        // Specify the update contact id.
        whereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID);
        whereClauseBuf.append("=");
        whereClauseBuf.append(rawContactId);

        // Specify the row data mimetype to phone mimetype( vnd.android.cursor.item/phone_v2 )
        whereClauseBuf.append(" and ");
        whereClauseBuf.append(ContactsContract.Data.MIMETYPE);
        whereClauseBuf.append(" = '");
        String mimetype = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE;
        whereClauseBuf.append(mimetype);
        whereClauseBuf.append("'");

        // Specify phone type.
        whereClauseBuf.append(" and ");
        whereClauseBuf.append(ContactsContract.CommonDataKinds.Phone.TYPE);
        whereClauseBuf.append(" = ");
        whereClauseBuf.append(phoneType);

        // Update phone info through Data uri.Otherwise it may throw java.lang.UnsupportedOperationException.
        Uri dataUri = ContactsContract.Data.CONTENT_URI;

        // Get update data count.
        int updateCount = contentResolver.update(dataUri, contentValues, whereClauseBuf.toString(), null);
        Log.i("Contact", Integer.toString(updateCount));
    }
}
