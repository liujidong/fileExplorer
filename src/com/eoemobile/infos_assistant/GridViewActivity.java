package com.eoemobile.infos_assistant;
 
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
 

public class GridViewActivity  extends Activity  {
	private Bitmap[] mThumbIds = null; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);
        setTitle("GridViewActivity");
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String pathDir = extras.getString("path");
	        initImages(pathDir, this);
		}
        GridView gridview = (GridView) findViewById(R.id.grid_view);
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            //imageView.setImageResource(mThumbIds[position]);
            imageView.setImageBitmap(mThumbIds[position]);
            return imageView;
        }

    }
    private void initImages(String pathDir,Context context){
    	File dir = new File(pathDir);
    	String[] images = dir.list(new ImageNameFilter());
    	mThumbIds = new Bitmap[images.length];
    	Bitmap bit0 = null;
    	for (int i = 0;i<images.length;i++) {
             bit0 = BitmapFactory.decodeFile(pathDir+File.separator + images[i]);
             mThumbIds[i]=ThumbnailUtils.extractThumbnail(bit0, 60, 60);
		}
    }

 
}