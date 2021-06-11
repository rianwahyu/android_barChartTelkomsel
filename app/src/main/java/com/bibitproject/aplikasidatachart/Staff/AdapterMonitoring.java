package com.bibitproject.aplikasidatachart.Staff;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.Staff.Model.Monitoring;
import com.bibitproject.aplikasidatachart.Utils.ItemClikListener;
import com.bibitproject.aplikasidatachart.Utils.ItemClikListener2;

import java.util.List;

public class AdapterMonitoring extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Monitoring> loc ;

    private ItemClikListener clickListener;
    private ItemClikListener2 clickListener2;

    public AdapterMonitoring(Context context, List<Monitoring> loc) {
        this.context = context;
        this.loc = loc;
    }

    public void setClickListener(ItemClikListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    int row_index=-1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.adapter_monitoring, viewGroup, false);

        ViewHolderRow viewHolder = new ViewHolderRow(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolderRow) {
            final ViewHolderRow viewHolders = (ViewHolderRow) viewHolder;

            viewHolders.textRegion.setText(loc.get(position).getReg());
            viewHolders.textCritical.setText(loc.get(position).getCri());
            viewHolders.textMajor.setText(loc.get(position).getMaj());
            viewHolders.textMinor.setText(loc.get(position).getMin());
        }
    }


    @Override
    public int getItemCount() {
        return loc == null ? 0 : loc.size();
    }

    public class ViewHolderRow extends RecyclerView.ViewHolder {
        TextView textRegion, textCritical, textMajor, textMinor ;

        public ViewHolderRow(@NonNull View itemView) {
            super(itemView);

            textRegion = itemView.findViewById(R.id.textRegion);
            textCritical = itemView.findViewById(R.id.textCritical);
            textMajor = itemView.findViewById(R.id.textMajor);
            textMinor = itemView.findViewById(R.id.textMinor);
        }
    }
}
