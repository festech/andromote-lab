package edu.pw.elka.andromote.andromote.common.wrappers;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

public class TtsProcessor implements TextToSpeech.OnInitListener {
    private TextToSpeech textToSpeech;
    private boolean readyToTalk = false;

    public TtsProcessor(Context context) {
        textToSpeech = new TextToSpeech(context, this);
    }

    public void speak(String text) {
        TTSTask task = new TTSTask(text);
        task.execute();
    }

    public void cleanup() {
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

    private class TTSTask extends AsyncTask<Void, Void, Void> {
        private final String text;

        public TTSTask(String text) {
            this.text = text;
        }

        @Override
        protected Void doInBackground(Void... args) {
            try {
                while(!readyToTalk) {
                    Thread.sleep(50);
                }
                textToSpeech.setLanguage(Locale.getDefault());
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onInit(int status) {
        System.out.println("Checking status " + status);
        if(status == TextToSpeech.SUCCESS) {
            readyToTalk = true;

            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {}

                @Override
                public void onError(String utteranceId) {}

                @Override
                public void onDone(String utteranceId) {}
            });
        }
    }
}