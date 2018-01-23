package com.example.goddragonfish.shoppingcardemo;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goddragonfish.shoppingcardemo.Adapter.MyExpandAdapter;
import com.example.goddragonfish.shoppingcardemo.Entity.Item;
import com.example.goddragonfish.shoppingcardemo.Entity.Shop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ExpandableListView mExpand;
    private RelativeLayout before;
    private LinearLayout after;
    private static boolean vis=true;
    private Button btnDelete;
    private CheckBox cbAll;
    private TextView emptyText;
    private List<Shop> shopList;
    private List<Item> itemList1;
    private List<Item> itemList2;
    private List<Item> itemList3;
    private MyExpandAdapter myExpandAdapter;
    private Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDate();
        initViews();

    }

    private void initDate(){
        shopList=new ArrayList<>();
        itemList1=new ArrayList<>();
        itemList2=new ArrayList<>();
        itemList3=new ArrayList<>();
        Shop s1=new Shop(false);
        s1.setShopName("Nike旗舰店");
        for(int i=0;i<3;i++){
            Item item=new Item(false);
            item.setItemName("shoe");
            item.setImgUrl(R.mipmap.ic_launcher);
            item.setPrice("$ 200");
            item.setCount(1);
            itemList1.add(item);
        }
        s1.setItemList(itemList1);
        shopList.add(s1);

        Shop s3=new Shop(false);
        s3.setShopName("LiNing旗舰店");
        for(int i=0;i<2;i++){
            Item item=new Item(false);
            item.setItemName("shoe");
            item.setImgUrl(R.mipmap.ic_launcher);
            item.setPrice("$ 100");
            item.setCount(1);
            itemList3.add(item);
        }
        s3.setItemList(itemList3);
        shopList.add(s3);

        Shop s2=new Shop(false);
        s2.setShopName("Peak旗舰店");
        Item item1=new Item(false);
        item1.setItemName("shirt");
        item1.setImgUrl(R.mipmap.ic_launcher);
        item1.setPrice("$ 90");
        item1.setCount(1);
        itemList2.add(item1);
        s2.setItemList(itemList2);

        shopList.add(s2);




    }

    private void initViews(){
        context=MainActivity.this;
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("");//去除标题
        setSupportActionBar(toolbar);
        emptyText=findViewById(R.id.empty);
        mExpand=findViewById(R.id.expandableListView);
        btnDelete=findViewById(R.id.delete);
        cbAll=findViewById(R.id.cb_select_all);
        before=findViewById(R.id.before_etc);
        after=findViewById(R.id.after_etc);
        myExpandAdapter=new MyExpandAdapter(context,shopList);
        mExpand.setGroupIndicator(null);
        mExpand.setAdapter(myExpandAdapter);

        /*
        //为了防止滑动时，加减器获取焦点
        mExpand.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(SCROLL_STATE_TOUCH_SCROLL==scrollState){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity
                            .INPUT_METHOD_SERVICE);
                    View focusView = getCurrentFocus();
                    if (focusView != null) {
                        inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager
                                .HIDE_NOT_ALWAYS);
                        focusView.clearFocus();
                    }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });*/


        //默认展开
        for (int i = 0; i < shopList.size(); i++) {
            mExpand.expandGroup(i);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(shopList);
                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
                myExpandAdapter.notifyDataSetChanged();
            }
        });

        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeCbStaue(shopList,true);
                }else{
                    changeCbStaue(shopList,false);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.edit:
                if(vis){
                    item.setTitle("完成");
                    before.setVisibility(View.GONE);
                    after.setVisibility(View.VISIBLE);
                    vis=false;
                }else{
                    item.setTitle("编辑");
                    before.setVisibility(View.VISIBLE);
                    after.setVisibility(View.GONE);
                    vis=true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteItem(List<Shop> list){
        if(list!=null&&!list.isEmpty()){
            Iterator<Shop> it=list.iterator();

            while(it.hasNext()){
                Shop s=it.next();
                if(s.isShopCh()){
                    it.remove();
                }else {
                    if(s.getItemList()!=null&&!s.getItemList().isEmpty()){
                        Iterator<Item> it2=s.getItemList().iterator();
                        while (it2.hasNext()){
                            Item i=it2.next();
                            if(i.isItemCh()){
                                it2.remove();
                            }
                        }
                    }
                }
            }
        }

        myExpandAdapter.notifyDataSetChanged();


    }

    public void changeCbStaue(List<Shop> list,boolean twoOrFour){
        if(list!=null&&!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setShopCh(twoOrFour);
                if(list.get(i).getItemList()!=null&&!list.get(i).getItemList().isEmpty()){
                    for (int j = 0; j < list.get(i).getItemList().size(); j++) {
                        list.get(i).getItemList().get(j).setItemCh(twoOrFour);
                    }
                }

            }
            myExpandAdapter.notifyDataSetChanged();
        }
    }


}
