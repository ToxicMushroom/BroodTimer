package me.toxicmushroom.broodtimer;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.toxicmushroom.broodtimer.data.Broden;

/**
 * Created by Merlijn on 4/02/2018.
 */

public class BroodAdapter extends RecyclerView.Adapter<BroodAdapter.BroodViewHolder> {

    Context context;
    List<Broden> brodenList;
    BitmapDrawable placeHolder;

    public BroodAdapter(Context context, List<Broden> broden) {
        brodenList = new ArrayList<>();
        if (brodenList != null) {
            brodenList.addAll(broden);
        }
        this.context = context;
        placeHolder = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_pause_black_24dp);
    }

    public void addItem(Broden brood) {
        brodenList.add(brood);
        notifyItemInserted(brodenList.size() - 1);
    }

    public void setItems(List<Broden> broodjes) {
        brodenList.clear();
        brodenList.addAll(broodjes);
        notifyItemRangeInserted(0, broodjes.size());
    }

    @Override
    public BroodAdapter.BroodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.brood_items, parent, false);
        BroodViewHolder broodViewHolder = new BroodViewHolder(view);
        return broodViewHolder;
    }

    @Override
    public void onBindViewHolder(BroodAdapter.BroodViewHolder holder, int position) {
        Broden brood = brodenList.get(position);
        holder.icon.setImageDrawable(placeHolder);
        holder.title.setText(brood.get_broodnaam());
        holder.currentPhase.setText(":|");
        holder.nextPhase.setText(":|");
    }

    @Override
    public int getItemCount() {
        return brodenList.size();
    }

    public class BroodViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title;
        TextView currentPhase;
        TextView nextPhase;

        public BroodViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.thumbnail_image);
            title = itemView.findViewById(R.id.recycle_title);
            currentPhase = itemView.findViewById(R.id.recycle_brood_phase_now);
            nextPhase = itemView.findViewById(R.id.recycle_brood_phase_incomming);

        }
    }
}
