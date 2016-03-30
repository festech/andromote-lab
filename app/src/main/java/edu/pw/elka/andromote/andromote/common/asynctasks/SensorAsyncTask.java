package edu.pw.elka.andromote.andromote.common.asynctasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import edu.pw.elka.andromote.andromote.common.wrappers.TtsProcessor;
import edu.pw.elka.andromote.andromote.tasks.task2.TaskTwo;

public class SensorAsyncTask extends AsyncTask<Void, Void, Boolean> {
    public static final int WAIT_FOR_SENSOR_INIT = 1000;
    private final Activity activity;
    private TaskTwo sensorTask = new TaskTwo();
    private TtsProcessor ttsProcessor;

    public SensorAsyncTask(Activity activity, TtsProcessor ttsProcessor) {
        this.activity = activity;
        this.ttsProcessor = ttsProcessor;
        sensorTask.register();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Thread.sleep(WAIT_FOR_SENSOR_INIT);
            ttsProcessor.speak(sensorTask.getGreetingText() + " " + sensorTask.getValue());
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean wasCompleted) {
        sensorTask.unregister();
        if(!wasCompleted) {
            System.out.println("Execution interrupted");
            Toast.makeText(activity, "Task one - stopped", Toast.LENGTH_SHORT).show();
        }
    }
}
