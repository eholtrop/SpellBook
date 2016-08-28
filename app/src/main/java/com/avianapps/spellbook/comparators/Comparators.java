package com.avianapps.spellbook.comparators;

import com.avianapps.spellbook.models.Spell;

import java.util.Comparator;

/**
 * Created by evanh on 8/15/2016.
 */
public class Comparators {

    public static class SpellLevelComparator implements Comparator<Spell> {

        @Override
        public int compare(Spell spell, Spell t1) {
            if (spell.level.equals("Cantrip")) {
                return 1;
            }

            return 0;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
