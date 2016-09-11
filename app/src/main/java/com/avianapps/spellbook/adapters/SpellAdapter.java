package com.avianapps.spellbook.adapters;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
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
import java.util.logging.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by evanh on 8/14/2016.
 */
public class SpellAdapter extends RecyclerView.Adapter {
    public List<Spell> Spells = new ArrayList<>();
    public List<Spell> filteredList = new ArrayList<>();

    public BehaviorSubject<String> Name = BehaviorSubject.create();
    public BehaviorSubject<String> Level = BehaviorSubject.create();
    public BehaviorSubject<String> Class = BehaviorSubject.create();
    private Observable<Filter> filter;

    public SpellAdapter(List<Spell> spells) {
        Spells = spells;
        Collections.sort(Spells, new Comparators.SpellLevelComparator());

        Name.startWith("");
        Level.startWith("");
        Class.startWith("");

        filter = Observable.combineLatest(Name, Level, Class, Filter::new)
                    .doOnNext(x -> filteredList.clear())
                    .doOnNext(x -> notifyDataSetChanged());

        filter.subscribe(x -> Log.d("filter", x.name));
        filter.subscribe(x -> Log.d("filterLevel", x.level));
        filter.subscribe(x -> Log.d("filterClass", x.clazz));
        filter.flatMap(this::filter)
                .subscribe(x -> {
                    filteredList.add(x);
                    notifyDataSetChanged();
                });
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

    private Observable<Spell> filter (final Filter filter) {

        Log.d("filterName", "filter: " + filter.name);
        Log.d("filterLevel", "filter: " + filter.level);
        Log.d("filterClass", "filter: " + filter.clazz);

        return Observable.from(Spells)
                .filter(x -> x.name.toLowerCase().contains(filter.name.toLowerCase()))
                .filter(x -> x.level.toLowerCase().contains(filter.level.toLowerCase()))
                .filter(x -> x.classes.toLowerCase().contains(filter.clazz.toLowerCase()));
    }

    public void setNameFilter(String name) {
        Name.onNext(name);
    }

    public void setLevelFilter(String level) {
        Level.onNext(level);
    }

    public void setClassFilter(String clazz) {
        Class.onNext(clazz);
    }

    private class Filter {
        public String name;
        public String level;
        public String clazz;

        public Filter(String name, String level, String clazz) {
            this.name = name;
            this.level = level;
            this.clazz = clazz;
        }
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
