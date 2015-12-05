package sjtu.se.Util;

import sjtu.se.UserInformation.ContactCard;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.test.AndroidTestCase;

public class ContactInterface extends AndroidTestCase {

	public static void insert(String str, Context ctx){
		ContactCard contact = ContactCard.parseContactCard(str);
		ContactInterface.insert(contact, ctx);
	}

	public static void insert (ContactCard contact, Context ctx){
		if (contact == null)
			return;

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

        values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
        values.put(Email.DATA, contact.email);
        values.put(Email.TYPE, Email.TYPE_WORK);
        ctx.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
	}
}
