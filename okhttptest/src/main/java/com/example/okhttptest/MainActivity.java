package com.example.okhttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView mLst;
    List<Entity> mDatas;
    String[] strs={"内存管理","个人中心","密码修改","内存管理","个人中心","密码修改",
            "内存管理","个人中心","密码修改","内存管理","个人中心","密码修改","内存管理","个人中心","密码修改","内存管理","个人中心","密码修改"};
    int[] iconsIds={R.drawable.ic_assessment_black_24dp,R.drawable.ic_assignment_black_24dp,R.drawable.ic_assignment_ind_black_24dp,R.drawable.ic_assessment_black_24dp,R.drawable.ic_assignment_black_24dp,R.drawable.ic_assignment_ind_black_24dp,R.drawable.ic_assessment_black_24dp,R.drawable.ic_assignment_black_24dp,R.drawable.ic_assignment_ind_black_24dp,R.drawable.ic_assessment_black_24dp,R.drawable.ic_assignment_black_24dp,R.drawable.ic_assignment_ind_black_24dp,R.drawable.ic_assessment_black_24dp,R.drawable.ic_assignment_black_24dp,R.drawable.ic_assignment_ind_black_24dp,R.drawable.ic_assessment_black_24dp,R.drawable.ic_assignment_black_24dp,R.drawable.ic_assignment_ind_black_24dp,
            R.drawable.ic_assessment_black_24dp,R.drawable.ic_assignment_black_24dp,R.drawable.ic_assignment_ind_black_24dp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLst= (ListView) findViewById(R.id.lst);
        initDatas();
        initAdapter();
        mLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,strs[position]+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        MyAdapter adapter=new MyAdapter(this,mDatas);
        mLst.setAdapter(adapter);
    }

    private void initDatas() {
        mDatas=new ArrayList<>();
        for (int i=0;i<strs.length;i++){
            Entity entuty=new Entity(strs[i],iconsIds[i]);
            mDatas.add(entuty);
        }
    }

}
