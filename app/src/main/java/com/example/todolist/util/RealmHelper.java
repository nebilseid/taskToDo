package com.example.todolist.util;

import com.example.todolist.model.DataToDo;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public ArrayList<DataToDo> retrieve()
    {
        ArrayList<DataToDo> todoTasks=new ArrayList<>();
        RealmResults<DataToDo> toDos=realm.where(DataToDo.class).findAll();

        for(DataToDo tsk :toDos)
        {
            todoTasks.add(tsk);
        }
        return todoTasks;
    }
}


