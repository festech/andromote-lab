package edu.pw.elka.andromote.lab.common.asynctasks;

import android.os.AsyncTask;
import edu.pw.elka.andromote.lab.tasks.task3.TaskThree;

public class RemoteControlAsyncTask extends AsyncTask<Void, Void, Void> {
    private TaskThree taskThree;
    public RemoteControlAsyncTask(TaskThree taskThree) {
        this.taskThree = taskThree;
    }

    @Override
    protected Void doInBackground(Void... params) {
        taskThree.start();
        return null;
    }
}
