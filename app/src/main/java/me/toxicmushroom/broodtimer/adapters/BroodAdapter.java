package me.toxicmushroom.broodtimer.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.toxicmushroom.broodtimer.Action;
import me.toxicmushroom.broodtimer.dialogs.InputDialog;
import me.toxicmushroom.broodtimer.dialogs.InputDialogSure;
import me.toxicmushroom.broodtimer.MainActivity;
import me.toxicmushroom.broodtimer.R;
import me.toxicmushroom.broodtimer.activities.EditBroodActivity;
import me.toxicmushroom.broodtimer.activities.LoadBroodActivity;
import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;
import me.toxicmushroom.broodtimer.reminder.PhaseService;

/**
 * Created by Merlijn on 4/02/2018.
 */

public class BroodAdapter extends RecyclerView.Adapter<BroodAdapter.BroodViewHolder> implements InputDialog.InputDialogListener {

    private Context context;
    private List<Broden> brodenList;
    private Drawable placeHolder;
    private MyDBHandler myDBHandler;

    public BroodAdapter(Context context, List<Broden> broden) {
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
    public BroodAdapter.BroodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.brood_items, parent, false);
        return new BroodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BroodAdapter.BroodViewHolder holder, int position) {
        Broden brood = brodenList.get(position);
        holder.itemView.setOnClickListener((v) -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.title);
            popupMenu.inflate(R.menu.brood_menu);
            popupMenu.setOnMenuItemClickListener((i) -> {
                int id = i.getItemId();
                switch (id) {
                    case R.id.action_edit:
                        EditBroodActivity.brood = brood;
                        if (myDBHandler.getData(MyDBHandler.TABLE_BRODEN, "loaded", brood.get_broodnaam()).equalsIgnoreCase("0"))
                            context.startActivity(new Intent(context.getApplicationContext(), EditBroodActivity.class));
                        else
                            Toast.makeText(context.getApplicationContext(), "Je kan geen geladen broden bewerken!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_load:
                        myDBHandler.setLoadedState(brood.get_broodnaam(), true);
                        Intent intent = new Intent(context.getApplicationContext(), PhaseService.class);
                        intent.putExtra("broodnaam", brood.get_broodnaam());
                        intent.putExtra("fase1", brood.getFase1());
                        intent.putExtra("fase2", brood.getFase2());
                        intent.putExtra("fase3", brood.getFase3());
                        intent.putExtra("fase4", brood.getFase4());
                        intent.putExtra("fase5", brood.getFase5());
                        intent.putExtra("fase6", brood.getFase6());
                        intent.putExtra("fase7", brood.getFase7());
                        intent.putExtra("fase8", brood.getFase8());
                        intent.putExtra("fase9", brood.getFase9());
                        intent.putExtra("fase10", brood.getFase10());
                        context.startService(intent);
                        PhaseService.paused.add(brood.get_broodnaam());
                        context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
                        break;
                    case R.id.action_delete:
                        LoadBroodActivity.action = Action.LIST_REMOVE;
                        LoadBroodActivity.broodToDelete = brood;
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
    }

    @Override
    public int getItemCount() {
        return brodenList.size();
    }

    @Override
    public void applyText(String input) {

    }

    public class BroodViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title;

        public BroodViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.thumbnail_image);
            title = itemView.findViewById(R.id.recycle_title);
        }
    }
}
