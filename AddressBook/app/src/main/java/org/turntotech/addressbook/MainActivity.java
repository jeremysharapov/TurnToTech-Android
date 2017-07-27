package org.turntotech.addressbook;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Log.i("TurnToTech", "Project name - AddressBook");
		//1. The class ContactsContract allows access to various types of data stored in the phone
		//Examples: Contacts, Phone settings, Contact groups etc.


		Cursor cursor = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		ArrayAdapter<String> list = new ArrayAdapter<String>(this,
				R.layout.activity_main);

		//2. Using the ContactsContract class above, we got a cursor to the data. Now we iterate through it to get all the data

		while (cursor.moveToNext()) {
			// In our example, we get the name and phone number
			String phoneNumber = "";
			String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
				ContentResolver cr = getContentResolver();
				Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
				while (pCur.moveToNext()) {
					phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					break;
				}
				pCur.close();
			} else {
				phoneNumber = "No Phone number found in address book";
			}
			list.add("Name: " + displayName + " | Phone Number: " + phoneNumber);
		}
		setListAdapter((list));
	}
}