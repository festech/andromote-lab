package edu.pw.elka.andromote.andromote.common.asynctasks;

import android.app.Activity;
import android.os.AsyncTask;

import edu.pw.elka.andromote.andromote.common.AndroMoteMainActivity;
import edu.pw.elka.andromote.andromote.tasks.task3.TaskThree;

public class RemoteControlAsyncTask extends AsyncTask<Void, Void, Void> {
    TaskThree taskThree;

    public RemoteControlAsyncTask(Activity activity) {
        taskThree = new TaskThree(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        taskThree.execute();
        return null;
    }
}
