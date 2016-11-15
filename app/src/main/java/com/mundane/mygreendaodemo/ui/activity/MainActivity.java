package com.mundane.mygreendaodemo.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.mundane.mygreendaodemo.R;
import com.mundane.mygreendaodemo.dao.UserDao;
import com.mundane.mygreendaodemo.db.DbUtil;
import com.mundane.mygreendaodemo.db.UserHelper;
import com.mundane.mygreendaodemo.entity.User;
import com.mundane.mygreendaodemo.ui.adapter.UserAdapter;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.Query;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, AdapterView.OnItemLongClickListener {

    private EditText    mEtName;
    private EditText    mEtAge;
    private EditText    mEtGender;
    private EditText    mEtSalary;
    private Button      mBtnDelete;
    private Button      mBtnInsert;
    private Button      mBtnQuery;
    private Button      mBtnQueryAll;
    private Button      mBtnUpdate;
    private ListView    mLv;
    private List<User>  mUserList;
    private UserAdapter mUserAdapter;
    private UserHelper  mUserHelper;
    private String      mUserName;
    private String      mUserAge;
    private String      mUserGender;
    private String      mUserSalary;
    private List<User>  mQueriedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();

        mUserHelper = DbUtil.getDriveHelper();
        mQueriedList = mUserHelper.queryAll();

        mUserList = new ArrayList<>();
        mUserList.addAll(mQueriedList);

        mUserAdapter = new UserAdapter(mUserList, this);
        mLv.setAdapter(mUserAdapter);

    }


    private void setUpViews() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtAge = (EditText) findViewById(R.id.et_age);
        mEtGender = (EditText) findViewById(R.id.et_gender);
        mEtSalary = (EditText) findViewById(R.id.et_salary);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
        mBtnInsert = (Button) findViewById(R.id.btn_insert);
        mBtnQuery = (Button) findViewById(R.id.btn_query);
        mBtnQueryAll = (Button) findViewById(R.id.btn_query_all);
        mBtnUpdate = (Button) findViewById(R.id.btn_update);
        mLv = (ListView) findViewById(R.id.lv);

        mBtnInsert.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnQueryAll.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);

        mEtSalary.setOnEditorActionListener(this);
        mLv.setOnItemLongClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insertData();
                refreshListView();
                break;
            case R.id.btn_query_all:
                refreshListView();
                break;
            case R.id.btn_query:
                getInputData();
                //  目前只支持单条件查询, 其余条件需要为空，想改的可以自己改
                if (!TextUtils.isEmpty(mUserName)) {
                    QueryData(UserDao.Properties.Name, mUserName);
                } else if (!TextUtils.isEmpty(mUserAge)) {
                    QueryData(UserDao.Properties.Age, mUserAge);
                } else if (!TextUtils.isEmpty(mUserGender)) {
                    QueryData(UserDao.Properties.Gender, mUserGender);
                } else if (!TextUtils.isEmpty(mUserSalary)) {
                    QueryData(UserDao.Properties.Salary, mUserSalary);
                }
                break;
        }
    }


    private void refreshListView() {
        //mUserList = mUserHelper.queryAll();不能直接这样
        mUserList.clear();
        mUserList.addAll(mUserHelper.queryAll());
        mUserAdapter.notifyDataSetChanged();
    }


    private void insertData() {
        getInputData();
        User user = new User(null, mUserName, mUserAge, mUserGender, mUserSalary);
        mUserHelper.save(user);
    }


    private void getInputData() {
        mUserName = mEtName.getText().toString().trim();
        mUserAge = mEtAge.getText().toString().trim();
        mUserGender = mEtGender.getText().toString().trim();
        mUserSalary = mEtSalary.getText().toString().trim();
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.et_salary:
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    return true;
                }
                break;
        }
        return false;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = mUserList.get(position);
                mUserHelper.delete(user);
                refreshListView();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return true;
    }


    public void QueryData(Property property, String text) {
        Query<User> query = mUserHelper
            .queryBuilder()
            .where(property.eq(text))
            .build();
        mQueriedList =  query.list();
        mUserList.clear();
        mUserList.addAll(mQueriedList);
        mUserAdapter.notifyDataSetChanged();
    }
}
