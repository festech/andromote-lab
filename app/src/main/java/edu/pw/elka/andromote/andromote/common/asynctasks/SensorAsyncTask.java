package edu.pw.elka.andromote.andromote.common.asynctasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import edu.pw.elka.andromote.andromote.common.wrappers.TtsProcessor;
import edu.pw.elka.andromote.andromote.tasks.task2.TaskTwo;

public class SensorAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private static final int WAIT_FOR_SENSOR_INIT = 1000;
    private final Activity activity;
    private TtsProcessor ttsProcessor;
    private TaskTwo sensorTask;

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
            ttsProcessor.speak(sensorTask.getGreetingText() + " " + sensorTask.getValue());
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
