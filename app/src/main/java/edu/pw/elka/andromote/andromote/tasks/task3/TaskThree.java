package edu.pw.elka.andromote.andromote.tasks.task3;

import android.app.Activity;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import edu.pw.elka.andromote.andromote.common.StartableStopable;
import edu.pw.elka.andromote.andromote.common.wrappers.PacketBuilder;
import edu.pw.elka.andromote.commons.PacketType;
import edu.pw.elka.andromote.hardwareapi.ElectronicsController;

/**
 * Zaimplementuj komunikacje sieciowa zgodnie z protokolem UDP
 *
 * Przesylane przez siec komendy tlumacz na pakiety Andromote w celu uzyskania zdalnego sterowania
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

