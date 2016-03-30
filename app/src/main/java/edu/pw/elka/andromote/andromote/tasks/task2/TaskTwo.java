package edu.pw.elka.andromote.andromote.tasks.task2;

import android.content.Context;

import edu.pw.elka.andromote.andromote.common.wrappers.SensorBase;

/**
 * W tej klasie zaimplementuj obsługę sensorów
 *
 * Po pobraniu wartosci z sensora, przypisz ją do pola 'value'
 * Przypisz tekst powitania do pola 'greetingText'
 */
public class TaskTwo extends SensorBase {
    private final Context context;

    public TaskTwo(Context context) {
        this.context = context;
        // do uzupelnienia
    }

    public void register() {
        // do uzupelnienia
    }

    public void unregister() {
        // do uzupelnienia
    }
}
