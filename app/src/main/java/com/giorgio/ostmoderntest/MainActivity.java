package com.giorgio.ostmoderntest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.giorgio.ostmoderntest.adapters.SetsAdapter;
import com.giorgio.ostmoderntest.controllers.SetsController;

public class MainActivity extends AppCompatActivity implements SetsListener, AdapterView.OnItemClickListener{

    public static final String BASE_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000";

    private SetsController setsController;
    private ListView setsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setsListView = (ListView) findViewById(R.id.sets_list);
        initSetsController();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initSetsController(){
        this.setsController = SetsController.getInstance();
        this.setsController.setListener(this);
    }

    @Override
    public void onSetsLoaded() {
        if(this.setsController.getSets() != null) {
            SetsAdapter adapter = new SetsAdapter(this, R.layout.row_sets_list, this.setsController.getSets());
            this.setsListView.setAdapter(adapter);
            this.setsListView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onSetError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getResources().getString(R.string.set_error_title))
                        .setMessage(getResources().getString(R.string.set_error_msg))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(!isFinishing()){
                                    dialog.dismiss();
                                }
                               finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}
