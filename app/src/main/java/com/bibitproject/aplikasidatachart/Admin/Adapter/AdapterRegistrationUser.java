package com.bibitproject.aplikasidatachart.Admin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bibitproject.aplikasidatachart.Admin.Model.ModelRegistrationUser;
import com.bibitproject.aplikasidatachart.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class AdapterRegistrationUser extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ModelRegistrationUser> loc ;

    /*private ItemClikListener clickListener;
    private ItemClikListener2 clickListener2;*/

    public AdapterRegistrationUser(Context context, List<ModelRegistrationUser> loc) {
        this.context = context;
        this.loc = loc;
    }

    /*public void setClickListener(ItemClikListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void setClickListener2(ItemClikListener2 itemClickListener2) {
        this.clickListener2 = itemClickListener2;
    }*/

    int row_index=-1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.adapter_registration_user, viewGroup, false);

        ViewHolderRow viewHolder = new ViewHolderRow(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolderRow) {
            final ViewHolderRow viewHolders = (ViewHolderRow) viewHolder;

            viewHolders.textName.setText(loc.get(position).getName());
            viewHolders.textPassword.setText(loc.get(position).getPassword());
            viewHolders.textRole.setText(loc.get(position).getRole());

        }
    }


    @Override
    public int getItemCount() {
        return loc == null ? 0 : loc.size();
    }

    public class ViewHolderRow extends RecyclerView.ViewHolder {


        TextView textName, textPassword, textRole;


        public ViewHolderRow(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textPassword = itemView.findViewById(R.id.textPassword);
            textRole = itemView.findViewById(R.id.textRole);
        }
    }
}
