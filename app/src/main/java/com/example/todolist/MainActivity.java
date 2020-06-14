package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.example.todolist.model.DataToDo;
import com.example.todolist.util.RealmHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    Realm realm;
    private static final String TAG = "MainActivity";
    TaskAdapter adapter;
    ArrayList<DataToDo> tasks;
    RecyclerView rv_toDos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_task_list));
        realm = Realm.getDefaultInstance();

        FloatingActionButton fbAdd = findViewById(R.id.fb_add_task);
        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog();

            }
        });

        rv_toDos = (RecyclerView) findViewById(R.id.rv_todo);
        rv_toDos.setLayoutManager(new LinearLayoutManager(this));

        RealmHelper helper = new RealmHelper(realm);
        tasks = helper.retrieve();

        adapter = new TaskAdapter();
        rv_toDos.setAdapter(adapter);
        adapter.setData(tasks);

        adapter.setOnItemClickListener(new TaskAdapter.onItemClickListener() {
            @Override
            public void itemClick(int position) {

            }
        });
    }



    public void addTaskDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("TO DO TASK");

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        builder.setView(customLayout);
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ADD TASK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String taskTitle, taskDescription, url;
                EditText title = customLayout.findViewById(R.id.tiet_task_title);
                EditText description = customLayout.findViewById(R.id.tiet_task_description);
                taskTitle = title.getText().toString();
                url = getString(R.string.random_image_url);
                taskDescription = description.getText().toString();
                addTask(taskTitle, taskDescription, url);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void addTask(final String title, final String description, final String url) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                DataToDo todo = bgRealm.createObject(DataToDo.class);
                todo.setTitle(title);
                todo.setDescription(description);
              //  todo.setUrl(url);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Success:task added successfully ");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.d(TAG, "Error: something went wrong!");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        RealmHelper helper = new RealmHelper(realm);
        tasks.addAll(helper.retrieve());
    }


    @Override
    protected void onDestroy() {
        if (realm != null) {
            realm.close();
        }
        super.onDestroy();
    }

}
