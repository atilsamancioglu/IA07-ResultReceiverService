package com.atilsamancioglu.resultreceiverservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

    }


    public void download(View view) {

        ResultReceiver myResultReceiver = new MyResultReceiver(null);



        Intent intent = new Intent(this,DownloadClass.class);
        String userInput = editText.getText().toString();
        intent.putExtra("url", userInput);
        textView.setText("downloading...");
        intent.putExtra("receiver",myResultReceiver);

        startService(intent);
    }


    public class MyResultReceiver extends ResultReceiver {


        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyResultReceiver(Handler handler) {
            super(handler);
        }


        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {

            if (resultCode == 1 && resultData != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String result = resultData.getString("websiteResult");
                        textView.setText(result);
                    }
                });
            }

            super.onReceiveResult(resultCode, resultData);
        }
    }

}
