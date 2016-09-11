package com.avianapps.spellbook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.avianapps.spellbook.models.Spell;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapter;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpellActivity extends AppCompatActivity {
    public static String SPELL_ARG = "spell_json";

    @Bind(R.id.description)
    public TextView Description;
    @Bind(R.id.higher_level)
    public TextView higherLevel;

    @Bind(R.id.level)
    public LabelTextView level;
    @Bind(R.id.classes)
    public LabelTextView classes;
    @Bind(R.id.components)
    public LabelTextView components;
    @Bind(R.id.school)
    public LabelTextView school;
    @Bind(R.id.ritual)
    public LabelTextView ritual;
    @Bind(R.id.range)
    public LabelTextView range;
    @Bind(R.id.duration)
    public LabelTextView duration;
    @Bind(R.id.page)
    public LabelTextView page;



    private Spell spell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);
        ButterKnife.bind(this);

        spell = new Gson().fromJson(getIntent().getExtras().getString(SPELL_ARG), Spell.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(spell.name);
        Description.setText(Html.fromHtml(boldDiceRolls(spell.description)));
        higherLevel.setText(Html.fromHtml(boldDiceRolls(spell.higherLevel)));
        level.setText(spell.level);
        classes.setText(spell.classes);
        components.setText(spell.components);
        school.setText(spell.school);
        ritual.setText(spell.ritual);
        range.setText(spell.range);
        duration.setText(spell.duration);
        page.setText(spell.page);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public String regex = "\\(*[0-9]+d[0-9]+\\)*";
    public String boldDiceRolls(String string) {
        if(string == null) return "";
        String[] strings = string.split(" ");
        StringBuilder builder = new StringBuilder();

        for(String word : strings) {
            if(Pattern.matches(regex, word)) {
                builder.append("<b>");
                builder.append(word);
                builder.append("</b>");
            } else {
                builder.append(word);
            }
            builder.append(" ");
        }
        return builder.toString();
    }
}
