package me.toxicmushroom.broodtimer.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.toxicmushroom.broodtimer.Action;
import me.toxicmushroom.broodtimer.dialogs.DialogNotify;
import me.toxicmushroom.broodtimer.dialogs.InputDialogSure;
import me.toxicmushroom.broodtimer.MainActivity;
import me.toxicmushroom.broodtimer.R;
import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;
import me.toxicmushroom.broodtimer.reminder.PhaseService;

/**
 * Created by Merlijn on 4/02/2018.
 */

public class LoadedBroodAdapter extends RecyclerView.Adapter<LoadedBroodAdapter.BroodViewHolder> {

    private Context context;
    private List<Broden> brodenList;
    private Drawable placeHolder;
    private MyDBHandler myDBHandler;

    public LoadedBroodAdapter(Context context, List<Broden> broden) {
        brodenList = new ArrayList<>();
        setItems(broden);
        this.context = context;
        myDBHandler = new MyDBHandler(context, null, null, 0);
        placeHolder = context.getResources().getDrawable(R.drawable.ic_pause_black_24dp);
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
    public LoadedBroodAdapter.BroodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.loaded_brood_items, parent, false);
        return new BroodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(LoadedBroodAdapter.BroodViewHolder holder, int position) {
        Broden brood = brodenList.get(position);
        holder.itemView.setOnClickListener((v) -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.title);
            popupMenu.inflate(R.menu.loaded_brood_menu);
            popupMenu.setOnMenuItemClickListener((i) -> {
                int id = i.getItemId();
                switch (id) {
                    case R.id.action_pause:
                        PhaseService.paused.add(brood.get_broodnaam());
                        break;
                    case R.id.action_resume:
                        PhaseService.paused.remove(brood.get_broodnaam());
                        break;
                    case R.id.action_unload:
                        MainActivity.action = Action.MAIN_UNLOAD;
                        MainActivity.broodToUnload = brood;
                        InputDialogSure inputDialog = new InputDialogSure();
                        inputDialog.show(((FragmentActivity) context).getSupportFragmentManager(), "test123");
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
        holder.icon.setImageDrawable(placeHolder);
        holder.title.setText(brood.get_broodnaam());
        holder.currentPhase.setText(myDBHandler.getData("brodenVooruitgang", "currentPhaseTime", brood.get_broodnaam()) + " seconden voorbij.");
        holder.nextPhase.setText("Nog " + myDBHandler.getData("brodenVooruitgang", "untilPhaseTime", brood.get_broodnaam()) + " seconden tot " +
                MainActivity.faseToName(Integer.parseInt(myDBHandler.getData("brodenVooruitgang", "nextPhase", brood.get_broodnaam()))));

        if (PhaseService.paused.contains(brood.get_broodnaam())) holder.title.setTextColor(Color.YELLOW); else holder.title.setTextColor(Color.WHITE);
        if (PhaseService.finished.containsKey(brood.get_broodnaam()) && PhaseService.finished.get(brood.get_broodnaam())) {
            DialogNotify dialogNotify = new DialogNotify();
            Bundle bundle = new Bundle();
            bundle.putString("text", brood.get_broodnaam() + " is klaar");
            dialogNotify.setArguments(bundle);
            dialogNotify.show(((FragmentActivity) context).getSupportFragmentManager(), "test123");
            holder.title.setTextColor(Color.MAGENTA);
            holder.currentPhase.setText("Finished!");
            holder.nextPhase.setText("(paused.. can be resumed to restart or unloaded to remove from the list)");
        }
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
