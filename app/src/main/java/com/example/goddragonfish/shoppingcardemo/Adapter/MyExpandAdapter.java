package com.example.goddragonfish.shoppingcardemo.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goddragonfish.shoppingcardemo.Entity.Shop;
import com.example.goddragonfish.shoppingcardemo.R;

import java.util.List;

/**
 * Created by GodDragonFish on 2017/12/20.
 */

public class MyExpandAdapter extends BaseExpandableListAdapter {

    List<Shop> shopList;
    Context c;

    public MyExpandAdapter(Context c,List<Shop> s) {
        this.c=c;
        this.shopList=s;
    }

    @Override
    public int getGroupCount() {
        return shopList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return shopList.get(groupPosition).getItemList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return shopList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return shopList.get(groupPosition).getItemList();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view=null;
        GroupHolder groupHolder=null;
        if(convertView!=null){
            view=convertView;
            groupHolder=(GroupHolder)view.getTag();
        }else{
            view=View.inflate(c, R.layout.head,null);
            groupHolder=new GroupHolder();
            groupHolder.groupCb=view.findViewById(R.id.groupCb);
            groupHolder.shopName=view.findViewById(R.id.shop_name);
            view.setTag(groupHolder);
        }
        //判断数据集非空
        if(shopList!=null&&!shopList.isEmpty()){
            if(shopList.get(groupPosition).getItemList()!=null&&!shopList.get(groupPosition).getItemList().isEmpty()){
                int cc=0;
                int ss=shopList.get(groupPosition).getItemList().size();
                for (int i = 0; i < ss; i++) {
                    if(!shopList.get(groupPosition).getItemList().get(i).isItemCh()){
                        groupHolder.groupCb.setChecked(false);
                    }else {
                        cc++;
                    }
                }
                if(cc==ss) {
                    shopList.get(groupPosition).setShopCh(true);
                    groupHolder.groupCb.setChecked(true);
                }
                groupHolder.shopName.setText(shopList.get(groupPosition).getShopName());
                groupHolder.groupCb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!shopList.get(groupPosition).isShopCh()){
                            shopList.get(groupPosition).setShopCh(true);
                            for (int i = 0; i < shopList.get(groupPosition).getItemList().size(); i++) {
                                shopList.get(groupPosition).getItemList().get(i).setItemCh(true);
                            }
                        }else {
                            shopList.get(groupPosition).setShopCh(false);
                            for (int i = 0; i < shopList.get(groupPosition).getItemList().size(); i++) {
                                shopList.get(groupPosition).getItemList().get(i).setItemCh(false);
                            }
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        }



        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder1 childHolder=null;
        if(convertView!=null){
            childHolder=(ChildHolder1)convertView.getTag();
        }else{
            convertView= View.inflate(c, R.layout.item,null);
            childHolder=new ChildHolder1();
            childHolder.childCb=convertView.findViewById(R.id.cb_item);
            childHolder.mImage= convertView.findViewById(R.id.img_icon);
            childHolder.name=convertView.findViewById(R.id.name);
            childHolder.price=convertView.findViewById(R.id.price);
            childHolder.tv_count=convertView.findViewById(R.id.tv_count);
            childHolder.tvAdd=convertView.findViewById(R.id.tv_add);
            childHolder.tvReduce=convertView.findViewById(R.id.tv_reduce);
            convertView.setTag(childHolder);
        }

        //判断数据集非空
        if(shopList!=null&&!shopList.isEmpty()){
            if(shopList.get(groupPosition).getItemList()!=null&&!shopList.get(groupPosition).getItemList().isEmpty()) {
                childHolder.childCb.setChecked(shopList.get(groupPosition).getItemList().get(childPosition).isItemCh());
                childHolder.mImage.setImageResource(shopList.get(groupPosition).getItemList().get(childPosition).getImgUrl());
                childHolder.name.setText(shopList.get(groupPosition).getItemList().get(childPosition).getItemName());
                childHolder.price.setText(shopList.get(groupPosition).getItemList().get(childPosition).getPrice());
                childHolder.tv_count.setText(String.valueOf(shopList.get(groupPosition).getItemList().get(childPosition).getCount()));


                //为啥不能用setOnCheckedChangeListener（监听Cb的状态变化），因为当一家商店只有一种商品时候，
                // 删除的话，触发监听函数，读取数据集时候shopList.get(groupPosition).getItemList()发现越界，
                //因此说明监听触发比notifyDataSetChanged（）快，数据还没来得及更新，而改用以下就解决；
                childHolder.childCb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(shopList.get(groupPosition).getItemList().get(childPosition).isItemCh()){
                            shopList.get(groupPosition).getItemList().get(childPosition).setItemCh(false);
                        }else {
                            shopList.get(groupPosition).getItemList().get(childPosition).setItemCh(true);

                        }
                        notifyDataSetChanged();
                    }
                });

                childHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.e("expandAdapter","setOnClickListener=====>");
                        //加法
                        int count =shopList.get(groupPosition).getItemList().get(childPosition).getCount()+1;
                        shopList.get(groupPosition).getItemList().get(childPosition).setCount(count);
                        shopList.get(groupPosition).getItemList().get(childPosition).setItemCh(true);
                        notifyDataSetChanged();

                    }
                });

                childHolder.tvReduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //减法
                        int count =shopList.get(groupPosition).getItemList().get(childPosition).getCount();
                        if(count>1){
                            count--;
                            shopList.get(groupPosition).getItemList().get(childPosition).setCount(count);
                            shopList.get(groupPosition).getItemList().get(childPosition).setItemCh(true);
                        }
                        notifyDataSetChanged();
                    }
                });

                childHolder.tv_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog=new AlertDialog.Builder(c);
                        dialog.setTitle("Are you Sure");
                        dialog.setMessage("sure commit?");
                        dialog.setCancelable(true);
                        dialog.setPositiveButton("Yse", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //删除

                            }
                        });
                        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //取消
                            }
                        });
                        dialog.show();
                    }
                });
            }
        }





        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder{
        CheckBox groupCb;
        TextView shopName;
    }

    class ChildHolder1{
        CheckBox childCb;
        ImageView mImage;
        TextView name;
        TextView price;
        TextView tv_count;
        TextView tvAdd;
        TextView tvReduce;

    }
}
