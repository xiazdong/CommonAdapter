package info.xiazdong.common.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.xiazdong.common.CommonAdapter;
import info.xiazdong.common.R;

public class SimpleListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        List<DataItem> datas = getSampleDatas();
        ListView listView = (ListView)findViewById(R.id.list);
        Picasso.with(this).setIndicatorsEnabled(true);
        listView.setAdapter(new CommonAdapter<DataItem>(this,datas,R.layout.item_list) {
            @Override
            public void convert(ViewHolder holder, DataItem item,int position) {
                holder.setText(R.id.text,item.name);
                holder.setImage(R.id.image,item.url,null);
            }
        });
    }

    private List<DataItem> getSampleDatas() {
        List<DataItem> datas = new ArrayList<DataItem>();
        String[] urls = getResources().getStringArray(R.array.urls);
        for(int i = 0; i < urls.length; i++){
            DataItem item = new DataItem(urls[i],urls[i]);
            datas.add(item);
        }
        return datas;
    }
}

class DataItem{
    String url;
    String name;
    public DataItem(String url, String name){
        this.url = url;
        this.name = name;
    }
}
