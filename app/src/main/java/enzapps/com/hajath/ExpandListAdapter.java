package enzapps.com.hajath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<MenuModel> groups;

    public ExpandListAdapter(Context context, ArrayList<MenuModel> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Items> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Items child = (Items) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_menu_item ,null);
        }



        TextView name = (TextView) convertView.findViewById(R.id.txt_pname);
        TextView price=(TextView)convertView.findViewById(R.id.txt_price);
        TextView id=(TextView)convertView.findViewById(R.id.txt_id);
        ImageView image=(ImageView)convertView.findViewById(R.id.img_photo);


        name.setText(child.getName().toString());
        price.setText(child.getPrice().toString());
        id.setText(child.getId().toString());
        Glide.with(context).load("http://hajatonline.com/resources/"+child.getImage().toString()).into(image);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Items> chList = groups.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        MenuModel group = (MenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.list_row_menu, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
        tv.setText(group.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
