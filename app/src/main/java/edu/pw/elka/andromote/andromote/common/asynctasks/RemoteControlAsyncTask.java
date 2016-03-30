package edu.pw.elka.andromote.andromote.common.asynctasks;

import android.app.Activity;
import android.os.AsyncTask;

import edu.pw.elka.andromote.andromote.tasks.task3.TaskThree;

public class RemoteControlAsyncTask extends AsyncTask<Void, Void, Void> {
    private TaskThree taskThree;
    public RemoteControlAsyncTask(Activity activity, TaskThree taskThree) {
        this.taskThree = taskThree;
    }

    @Override
    protected Void doInBackground(Void... params) {
        taskThree.start();
        return null;
    }
}
