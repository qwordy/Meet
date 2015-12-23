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
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class ContactInterface {


	public static boolean insert(String str, Context ctx){
		ContactCard contact = ContactCard.parseContactCard(str);
		return ContactInterface.insert(contact, ctx);
	}

	public static boolean insert (ContactCard contact, Context ctx){
        if (contact == null)
			return false;

         if(contact.name.equals("") || contact.phone.equals(""))
             return false;

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
            values.put(Data.MIMETYPE, Website.CONTENT_ITEM_TYPE);
            values.put(Website.DATA, "微博： "+contact.weibo);
            values.put(Website.TYPE, Website.TYPE_BLOG);
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

         return true;
	}
}
