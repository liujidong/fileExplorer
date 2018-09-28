package com.eoemobile.infos_assistant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class FSExplorer extends Activity implements OnItemClickListener {
	private static final String TAG = "FSExplorer";
	private static final int IMG_SHOW = Menu.FIRST + 1;
	private static final int IMG_VIEW = IMG_SHOW + 1;
 
	ListView itemlist = null;
	String path = Environment.getDataDirectory().getPath();
	List<Map<String, Object>> list;
	private Menu menu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.files);
		setTitle("文件浏览器");
		Bundle extras = getIntent().getExtras();
		Log.i(TAG, "path="+path);
		if (extras != null) {
			int position = extras.getInt("position");
			if(position==1){
				path = Environment.getExternalStorageDirectory().getPath();//"/sdcard";
				Log.i(TAG, "path="+path);
			}
		}
		itemlist = (ListView) findViewById(R.id.itemlist);
		refreshListItems(path);
	}

	private void refreshListItems(String path) {
		setTitle("文件浏览器 > "+path);
		list = buildListForSimpleAdapter(path);
		SimpleAdapter notes = new SimpleAdapter(this, list, R.layout.file_row,
				new String[] { "name", "path" ,"img"}, new int[] { R.id.name,
						R.id.desc ,R.id.img});
		itemlist.setAdapter(notes);
		itemlist.setOnItemClickListener(this);
		itemlist.setSelection(0);
	}

	private List<Map<String, Object>> buildListForSimpleAdapter(String path) {
		File[] files = new File(path).listFiles();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(files.length);
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("name", "主目录");
		root.put("img", R.drawable.file_root);
		root.put("path", "go to index directory");
		list.add(root);
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("name", "..");
		pmap.put("img", R.drawable.file_paranet);
		pmap.put("path", "go to paranet Directory");
		list.add(pmap);
		for (File file : files){
			Map<String, Object> map = new HashMap<String, Object>();
			if(file.isDirectory()){
				map.put("img", R.drawable.directory);
			}else{
				map.put("img", R.drawable.file_doc);
			}
			map.put("name", file.getName());
			map.put("path", file.getPath());
			list.add(map);
		}
		return list;
	}
	
	private void goToParent() {
		File file = new File(path);
		File str_pa = file.getParentFile();
		if(str_pa == null){
			Toast.makeText(FSExplorer.this,
					getString(R.string.is_root_dir),
					Toast.LENGTH_SHORT).show();
			refreshListItems(path);	
		}else{
			path = str_pa.getAbsolutePath();
			refreshListItems(path);	
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		//Log.i(TAG, "item clicked! [" + position + "]");
		if (position == 0) {
			finish();
		}else if(position == 1){
			goToParent();
		} else {
			path = (String) list.get(position).get("path");
			File file = new File(path);
			if (file.isDirectory())
				refreshListItems(path);
			else
				Toast.makeText(FSExplorer.this,
						getString(R.string.is_file),
						Toast.LENGTH_SHORT).show();
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, IMG_SHOW, 0, R.string.menu_img_show);
		menu.add(0, IMG_VIEW, 0, R.string.menu_img_view);
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	File dir = new File(path);
    	String[] images = dir.list(new ImageNameFilter());
    	if(null != images && images.length>0){
			switch (item.getItemId()) {
			case IMG_SHOW:
				Intent i = new Intent(this, ImageShowActivity.class);
				i.putExtra("path", path);
				startActivity(i);
				return true;
			case IMG_VIEW:
				i = new Intent(this, GridViewActivity.class);
				i.putExtra("path", path);
				startActivity(i);
				return true;
			}
			return super.onMenuItemSelected(featureId, item);
    	}else{
			Toast.makeText(FSExplorer.this,
					"此目录没有图片",
					Toast.LENGTH_SHORT).show();   		
    		return false;
    	}
	}

}
