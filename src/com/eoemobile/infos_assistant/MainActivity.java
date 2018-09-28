package com.eoemobile.infos_assistant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements OnItemClickListener {
	private static final String TAG = "eoeInfosAssistant";
	ListView itemlist = null;
	List<Map<String, Object>> list;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		itemlist = (ListView) findViewById(R.id.itemlist);
		refreshListItems();
	}

	private void refreshListItems() {
		list = buildListForSimpleAdapter();
		SimpleAdapter notes = new SimpleAdapter(this, list, R.layout.item_row,
				new String[] { "name", "desc", "img" }, new int[] { R.id.name,
						R.id.desc, R.id.img });
		itemlist.setAdapter(notes);
		itemlist.setOnItemClickListener(this);
		itemlist.setSelection(0);
	}

	private List<Map<String, Object>> buildListForSimpleAdapter() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(3);
		// Build a map for the attributes
		Map<String, Object> map = new HashMap<String, Object>();
		
		map = new HashMap<String, Object>();
		map.put("name", "内部存储");
		map.put("desc", "浏览查看文件系统.");
		map.put("img", R.drawable.file_explorer);
		list.add(map);
		String extSdcardPath = System.getenv("SECONDARY_STORAGE");
		if(null != extSdcardPath){
			String[] files = new File(extSdcardPath).list();
			if(null != files && files.length>0){
				//if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
					map = new HashMap<String, Object>();
					map.put("name", "外部sdcard存储");
					map.put("desc", "浏览查看文件系统.");
					map.put("img", R.drawable.file_explorer);
					list.add(map);
				//}				
			}
		}		
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Intent intent = new Intent();
		Log.i(TAG, "item clicked! [" + position + "]");
		intent.setClass(MainActivity.this, FSExplorer.class);
		intent.putExtra("position", position);
		startActivity(intent);
	}
}