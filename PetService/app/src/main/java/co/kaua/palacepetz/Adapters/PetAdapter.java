package co.kaua.palacepetz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import co.kaua.palacepetz.R;
import co.kaua.palacepetz.Data.model.Pet;

import java.util.ArrayList;

public class PetAdapter extends BaseAdapter {
    private ArrayList<Pet> mOriginalValues; // Original Values
    private ArrayList<Pet> mDisplayedValues;    // Values to be displayed
    private Context context;
    private ArrayList<Pet> petList;

    public PetAdapter(Context context, ArrayList<Pet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @Override
    public int getCount() {
        return petList.size();
    }

    @Override
    public Object getItem(int position) {
        return petList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.cartlist,null);
//        TextView tv_ten = (TextView) view.findViewById(R.id.tv_name_cartlist);
//        TextView tv_gia = (TextView)view.findViewById(R.id.tv_gia_cartlist);
//        TextView tv_soLuong = (TextView)view.findViewById(R.id.tv_soluong_cartlist);

        Pet pet = petList.get(position);
//        tv_ten.setText(pet.getName());
//        tv_gia.setText(pet.getGia());
//        tv_soLuong.setText(pet.getSoLuong());

        return view;
    }

    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                mDisplayedValues = (ArrayList<Pet>) results.values;
                notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Pet> FilteredArrList = new ArrayList<Pet>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Pet>(mDisplayedValues);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Pet(mOriginalValues.get(i).getName(),mOriginalValues.get(i).getGia(),mOriginalValues.get(i).getSoLuong()));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
