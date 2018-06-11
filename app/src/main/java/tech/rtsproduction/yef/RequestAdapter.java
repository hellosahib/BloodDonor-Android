package tech.rtsproduction.yef;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RequestAdapter extends ArrayAdapter<DonatorClass> {

    public RequestAdapter(@NonNull Context context, @NonNull List<DonatorClass> objects) {
        super(context, 0, objects);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View parentView = convertView;
        /*
         * Check if the View is Reused or not
         * If it is new it inflates the view
         */
        if (parentView == null) {
            parentView = LayoutInflater.from(getContext()).inflate(R.layout.custom_item, parent, false);
        }
        //Get Object at Current Position
        DonatorClass currentItem = getItem(position);
        /*
         *Initializing the TextView
         * Setting the Text to Specific object at Index
         */
        TextView donatorName = parentView.findViewById(R.id.nameText);
        TextView bloodGroupText = parentView.findViewById(R.id.bloodGroupText);
        TextView dateText = parentView.findViewById(R.id.dateText);

        donatorName.setText(currentItem.getDonatorName());
        bloodGroupText.setText(currentItem.getBloodType());
        dateText.setText(currentItem.getLocation()+" at "+currentItem.getRequestDate());
        return parentView;
    }
}
