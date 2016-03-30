package edu.pw.elka.andromote.andromote.tasks.task3;

import android.app.Activity;
import android.content.Context;

import edu.pw.elka.andromote.andromote.common.wrappers.PacketBuilder;
import edu.pw.elka.andromote.hardwareapi.ElectronicsController;

/**
 * Zaimplementuj komunikacje sieciowa zgodnie z protokolem UDP
 *
 * Przesylane przez siec komendy tlumacz na pakiety Andromote w celu uzyskania zdalnego sterowania
 *
 * Jesli widzisz taka potrzebe - stworz dodatkowe klasy
 */
public class TaskThree {
    private Context context;
    private ElectronicsController electronicsController = ElectronicsController.INSTANCE;

    public TaskThree(Context context) {
        this.context = context;
    }

    public void execute() {
        //rozbuduj te metode
    }
}
