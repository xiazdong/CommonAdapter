package info.xiazdong.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by xiazdong on 2014-10-02.
 *
 * @version 0.1
 *      1. 基本实现通用的 Adapter
 *      2. 引入 Picasso 开源项目实现图片的异步加载
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    private final String TAG = CommonAdapter.class.getSimpleName();
    private Context mContext;
    private List<T> mData;
    private int mItemResId;

    public CommonAdapter(Context context, List<T> data,int itemResId){
        this.mContext = context;
        this.mData = data;
        this.mItemResId = itemResId;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = ViewHolder.newInstance(mContext,view,parent,mItemResId);
        convert(holder, getItem(position),position);
        return holder.getConvertView();
    }

    /**
     * 清空全部数据
     */
    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加一个数据
     * @param data
     */
    public void add(T data){
        mData.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加一组数据
     */
    public void addAll(List<T> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 需要重写的方法
     * @param holder
     * @param item 当前位置的的元素
     * @param position 当前的索引
     */
    public abstract void convert(ViewHolder holder, T item, int position);

    public static class ViewHolder {
        private final String TAG = ViewHolder.class.getSimpleName();
        private Context mContext;
        private SparseArray<View> mMaps;
        private View mConvertView;

        private ViewHolder(Context context, View convertView, ViewGroup parent, int itemResId){
            mMaps = new SparseArray<View>();
            mConvertView = LayoutInflater.from(context).inflate(itemResId,parent,false);
            mConvertView.setTag(this);
            convertView = mConvertView;
            this.mContext = context;
        }
        public static ViewHolder newInstance(Context context, View convertView, ViewGroup parent, int itemResId){
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder(context,convertView,parent,itemResId);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            return holder;
        }

        public <T extends View> T getView(int id){
            View view = mMaps.get(id);
            if(view == null){
                view = mConvertView.findViewById(id);
                mMaps.put(id,view);
            }
            return (T)view;
        }

        public ViewHolder setText(int id,String txt){
            TextView textView = getView(id);
            textView.setText(txt);
            return this;
        }

        public ViewHolder setImage(int id, String url, Callback callback){
            ImageView imageView = getView(id);
            Picasso.with(mContext).load(url).into(imageView,callback);
            return this;
        }

        public ViewHolder setImage(int id,int resId, Callback callback){
            ImageView imageView = getView(id);
            Picasso.with(mContext).load(resId).into(imageView,callback);
            return this;
        }

        public ViewHolder setImage(int id,File file, Callback callback){
            ImageView imageView = getView(id);
            Picasso.with(mContext).load(file).into(imageView,callback);
            return this;
        }
        public View getConvertView(){
            return mConvertView;
        }
    }
}
