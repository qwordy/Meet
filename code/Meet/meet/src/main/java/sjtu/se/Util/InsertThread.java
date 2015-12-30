package sjtu.se.Util;

import sjtu.se.UserInformation.ContactCard;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public  class InsertThread extends Thread {
    private ContactCard contact;
    private Context ctx;
    public boolean status;

    public InsertThread(String str, Context ctx) {
        this.contact = ContactCard.parseContactCard(str);
        this.ctx = ctx;
        status = true;

        if(this.contact == null) status = false;
        else if(this.contact.name.equals("") || this.contact.phone.equals("")) status = false;
    }

    public void run() {
        ContentValues values = new ContentValues();
        Uri rawContactUri = ctx.getContentResolver().insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);

        values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        values.put(StructuredName.GIVEN_NAME, contact.name);
        ctx.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);

        values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        values.put(Phone.NUMBER, contact.phone);
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        ctx.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);

        if(!contact.email.equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
            values.put(Email.DATA, contact.email);
            values.put(Email.TYPE, Email.TYPE_OTHER);
            ctx.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }

        if(!contact.qq.equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Im.CONTENT_ITEM_TYPE);
            values.put(Im.DATA, "QQ： "+contact.qq);
            values.put(Im.TYPE,Im.CUSTOM_PROTOCOL);
            ctx.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }

        if(!contact.weibo.equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Im.CONTENT_ITEM_TYPE);
            values.put(Im.DATA, "微博： "+contact.weibo);
            values.put(Im.TYPE, Im.CUSTOM_PROTOCOL);
            ctx.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }

        if(!contact.wechat.equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Im.CONTENT_ITEM_TYPE);
            values.put(Im.DATA, "微信： "+contact.wechat);
            values.put(Im.TYPE,Im.CUSTOM_PROTOCOL);
            ctx.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
    }
}
