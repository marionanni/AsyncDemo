package com.example.asyncdemo;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class AsyncDemoFragment extends ListFragment {
    private static final String[] items= { "lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
            "vel", "erat", "placerat", "ante", "porttitor", "sodales",
            "pellentesque", "augue", "purus" };
    private ArrayList<String> model=new ArrayList<String>();
    private ArrayAdapter<String> adapter=null;
    private AddStringTask task=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        task = new AddStringTask();
        task.execute();

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, model);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setScrollbarFadingEnabled(false);
        setListAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        if(task != null){
            task.cancel(false);
        }
        super.onDestroy();
    }

    private class AddStringTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for(String item : items){
                if(isCancelled())
                    break;
                publishProgress(item);
                SystemClock.sleep(400);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... item) {
            if(!isCancelled()){
                adapter.add(item[0]);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
            task = null;
        }
    }
}
