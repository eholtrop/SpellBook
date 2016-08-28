package com.avianapps.spellbook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.avianapps.spellbook.models.Spell;
import com.google.gson.Gson;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpellActivity extends AppCompatActivity {
    public static String SPELL_ARG = "spell_json";

    @Bind(R.id.description)
    public TextView Description;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle(spell.name);
        Description.setText(Html.fromHtml(boldDiceRolls(spell.description)));
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
