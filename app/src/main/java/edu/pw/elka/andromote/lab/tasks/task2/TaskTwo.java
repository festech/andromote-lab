package edu.pw.elka.andromote.lab.tasks.task2;

import android.content.Context;

import edu.pw.elka.andromote.lab.common.wrappers.SensorBase;

/**
 * W tej klasie zaimplementuj obsługę sensorów
 * zapoznaj sie z instruktarzami dostepnymi w Internecie
 *
 * Po pobraniu wartosci z sensora, przypisz ją do pola 'value'
 * Przypisz tekst powitania do pola 'unit'
 */
public class TaskTwo extends SensorBase {
    private final Context context;

    public TaskTwo(Context context) {
        this.context = context;
        this.value = 0;
        this.unit = "to obecna wartość";
    }

    @Override
    public void register() {
        // do uzupelnienia
    }

    @Override
    public void unregister() {
        // do uzupelnienia
    }

    //do uzupelnienia
}
