package cbi.example.sdy10.myapplicationtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    int iN;
    private LayoutInflater mInflater;
    public ImageAdapter(Context c, int i) {
        mInflater = LayoutInflater.from(c);
        mContext = c;
        iN=i;
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
    // create a new ImageView for each item referenced by the
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {  // if it's not recycled,
            convertView = mInflater.inflate(R.layout.image, null);
            convertView.setLayoutParams(new GridView.LayoutParams(90, 90));
            holder = new ViewHolder();
            holder.icon = (ImageView)convertView.findViewById(R.id.ivFace);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setAdjustViewBounds(true);
        holder.icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.icon.setPadding(8, 8, 8, 8);
        if(iN==0){
            holder.icon.setImageResource(mThumbIds[position]);
        }
        if(iN==1){
            holder.icon.setImageResource(mWeathers[position]);
        }
        return convertView;
    }
    class ViewHolder {
        ImageView icon;
    }
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.bbackchim, R.drawable.boolman, R.drawable.danghwang, R.drawable.kk
            , R.drawable.love, R.drawable.sad, R.drawable.sm, R.drawable.uu, R.drawable.vv, R.drawable.why
            , R.drawable.zzang,R.drawable.wwww
    };
    private Integer[] mWeathers={
            R.drawable.boengae,R.drawable.hrim,R.drawable.onlywindow,R.drawable.rain,R.drawable.rain2,R.drawable.rainbow,R.drawable.rainbowrain
            ,R.drawable.snow,R.drawable.sun,R.drawable.three,R.drawable.window,R.drawable.window2
    };

}
