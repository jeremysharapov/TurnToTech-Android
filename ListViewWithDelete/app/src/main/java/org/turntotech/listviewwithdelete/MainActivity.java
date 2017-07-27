/*
 * One of the UI component we use often is ListView, for example 
 * when we need to show items in a vertical scrolling list. 
 * One interesting aspect is this component can be deeply customized 
 * and can be adapted to our needs. We will analyze
 *  the basic concepts behind the ListView class and how it is used.
 *  In this example, we will create a ListView with single selection mode,
 *  multiple selection mode with delete option. 
 */

package org.turntotech.listviewwithdelete;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView lView;

	private ArrayAdapter<String> adapter;
	ArrayList<String> data;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - ListViewWithDelete");
		lView = (ListView) findViewById(R.id.ListView01);
		data = new ArrayList<String>();

		// 1. This part is similar to the ArrayAdapter we saw earlier. We create an array of fruits to be shown in a list
		String[] fruits = new String[] { "Apple", "Avocado", "Banana",
				"Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
				"Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };
		
		//2. We add the above list to the ArrayList object. We do this extra step because we want to be able to delete objects.
		Collections.addAll(data, fruits);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data);

		//3. Set the adaptor for the list view
		lView.setAdapter(adapter);


	}

	
	//4. Create a list of menu options for menu button
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		//Create the options menu. From here we can select the mode
		menu.add("Select None");
		menu.add("Select Single");
		menu.add("Select Multiple");
		menu.add("Select All");
		menu.add("Delete");

		return true;
	}

	//5. Perform the action that user wants to perform e.g. Select multiple, select single, delete etc.
	@Override	
	public boolean onOptionsItemSelected(MenuItem item) {

		String title = (String) item.getTitle();

		//6. Choose the appropriate method for our option menu. The list view supports a variety of choice modes.

		if (title.equals("Select None")) {
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, data);
			lView.setAdapter(adapter);
			// Nothing can be selected
			lView.setChoiceMode(ListView.CHOICE_MODE_NONE);
		}

		else if (title.equals("Select Single")) {
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_single_choice, data);
			lView.setAdapter(adapter);

			// 7. Set choice mode on the list to the appropriate value 
			lView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}

		else if (title.equals("Select Multiple")) {
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_multiple_choice, data);
			lView.setAdapter(adapter);
			// we can select multiple item
			lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		}

		else if (title.equals("Select All")){
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_checked, data);
			lView.setAdapter(adapter);
			lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			for (int i = lView.getCount() - 1; i >= 0; i--){
				lView.setItemChecked(i, true);
			}


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete choices");
            builder.setMessage("Would you like to delete all choices?");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Delete();
                }
            });
            builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                }
            });
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.show();

		}

		else if (title.equals("Delete")) {
			
			//7. For delete, we remove the selected elements from the array list
            Delete();

		}

		return true;
	}

	void Delete(){
        if (lView.getChoiceMode() != ListView.CHOICE_MODE_NONE) {
            SparseBooleanArray checked = lView.getCheckedItemPositions();

            // get the position of every checked item and using remove() method delete those items.
            for (int i = lView.getCount() - 1; i >= 0; i--) {
                if (checked.get(i) == true)
                    data.remove(i);
            }
            adapter.notifyDataSetChanged();
            // Clear our choices
            lView.clearChoices();
        }
    }
}