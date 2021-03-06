package edu.pw.elka.andromote.lab.common.asynctasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import edu.pw.elka.andromote.lab.common.wrappers.SensorBase;
import edu.pw.elka.andromote.lab.common.wrappers.TtsProcessor;
import edu.pw.elka.andromote.lab.tasks.task2.TaskTwo;

public class SensorAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private static final int WAIT_FOR_SENSOR_INIT = 1000;
    private final Activity activity;
    private TtsProcessor ttsProcessor;
    private SensorBase sensorTask;

    public SensorAsyncTask(Activity activity, TtsProcessor ttsProcessor) {
        this.activity = activity;
        this.ttsProcessor = ttsProcessor;
        sensorTask = new TaskTwo(activity);
        sensorTask.register();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Thread.sleep(WAIT_FOR_SENSOR_INIT);
            ttsProcessor.speak(sensorTask.getValue() + sensorTask.getUnit());
            while(ttsProcessor.isSpeaking()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            ttsProcessor.stop();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean wasCompleted) {
        sensorTask.unregister();
        if(!wasCompleted) {
            System.out.println("Execution interrupted");
            Toast.makeText(activity, "Task two - stopped", Toast.LENGTH_SHORT).show();
        }
    }
}
