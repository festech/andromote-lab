package edu.pw.elka.andromote.lab.tasks.task3;

import android.app.Activity;

import edu.pw.elka.andromote.lab.common.StartableStopable;
import edu.pw.elka.andromote.hardwareapi.ElectronicsController;

/**
 * Zaimplementuj komunikacje sieciowa zgodnie z protokolem UDP
 * - zapoznaj sie z dokumentacja w Internecie
 * - utworz serwer na porcie 8080
 *
 * Przesylane przez siec komendy tlumacz na pakiety Andromote w celu uzyskania zdalnego sterowania
 * Wyzwalaj pakiety korzystajac z electronicsController
 *
 * Jesli widzisz taka potrzebe - stworz dodatkowe klasy
 */
public class TaskThree implements StartableStopable {
    private Activity activity;
    private ElectronicsController electronicsController = ElectronicsController.INSTANCE;

    public TaskThree(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void start() {
        // do uzupelnienia
    }

    @Override
    public void stop() {
        // do uzupelnienia
    }
}

