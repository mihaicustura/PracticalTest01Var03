package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Random;

public class PracticalTest01Var03Service extends Service {

    private Random random = new Random();
    private ProcessingThread processingThread;

    public PracticalTest01Var03Service() {
    }

    private class ProcessingThread extends Thread {

        private Context context;
        private String name, group;

        public ProcessingThread(Context context, String name, String group) {
            this.context = context;
            this.name = name;
            this.group = group;
        }

        @Override
        public void run() {
            while(true){
                int action = random.nextInt(3);
                sendMessage(action);
                sleep();
            }
        }

        private void sleep() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        private void sendMessage(int messageType) {
            Intent intent = new Intent();
            String result = name + " " + group;
            switch(messageType) {
                case 0:
                    intent.setAction("action string");
                    intent.putExtra("string", result);
                    break;
                case 1:
                    intent.setAction("action integer");
                    intent.putExtra("integer", result);
                    break;
                case 2:
                    intent.setAction("action boolean");
                    intent.putExtra("boolean", result);
                    break;
                default:
                    intent.setAction("action default");
                    intent.putExtra("default", result);
                    break;
            }
            context.sendBroadcast(intent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name = intent.getStringExtra("Name text");
        String group = intent.getStringExtra("Group text");
        processingThread = new ProcessingThread(this, name, group);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
