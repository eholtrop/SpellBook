package com.avianapps.spellbook.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.avianapps.spellbook.R;
import com.avianapps.spellbook.SpellActivity;
import com.avianapps.spellbook.comparators.Comparators;
import com.avianapps.spellbook.models.Spell;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by evanh on 8/14/2016.
 */
public class SpellAdapter extends RecyclerView.Adapter {
    public List<Spell> Spells = new ArrayList<>();
    public List<Spell> filteredList = new ArrayList<>();
    public String filter = "";

    public SpellAdapter(List<Spell> spells) {
        Spells = spells;
        Collections.sort(Spells, new Comparators.SpellLevelComparator());
        setFilter("");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_spell, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Spell spell = filteredList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.Name.setText(spell.name);
        viewHolder.Description.setText(Html.fromHtml(spell.description));
        viewHolder.Level.setText(spell.level);
        viewHolder.Classes.setText(spell.classes);

        viewHolder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(holder.itemView.getContext(), SpellActivity.class);
            i.putExtra(SpellActivity.SPELL_ARG, new Gson().toJson(spell));
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void setFilter(String filter) {
        filteredList.clear();
        this.filter = filter;
        for (Spell spell : Spells) {
            if(spell.name.toLowerCase().contains(filter.toLowerCase())) {
                filteredList.add(spell);
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        public TextView Name;
        @Bind(R.id.description)
        public TextView Description;
        @Bind(R.id.level)
        public TextView Level;
        @Bind(R.id.classes)
        public TextView Classes;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
