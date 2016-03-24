package com.example.angks.scheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MemorialInputActivity extends Activity implements View.OnClickListener {
    public MemorialJob myJob;
    public toDoLayout myLayout;
    public ImageView memorialSticker;
    public Button memorialSetStickerBtn;
    public Button memorialSetPersonBtn;
    public ListView memorialPersonList;
    public EditText memorialContent;
    public CheckBox memorialPush;
    public CheckBox memorialDday;
    public Button memorialOk;
    public Button memorialCancel;
    public Context context;
    public ArrayList<Person> list;
    public ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_input);
        context = this;
        init();
    }

    public void init() {
        myLayout = (toDoLayout) findViewById(R.id.memorialTodo);
        myLayout.tvColorPicker.setVisibility(View.INVISIBLE);

        memorialSticker = (ImageView) findViewById(R.id.MemorialSticker);

        memorialSetStickerBtn = (Button) findViewById(R.id.MemorialSetSticker);
        memorialSetPersonBtn = (Button) findViewById(R.id.MemorialSetPerson);
        memorialSetPersonBtn.setOnClickListener(listener);

        memorialPersonList = (ListView) findViewById(R.id.MemorialList);
        memorialContent = (EditText) findViewById(R.id.MemorialContent);
        memorialPush = (CheckBox) findViewById(R.id.MemorialPush);
        memorialDday=(CheckBox)findViewById(R.id.MemorialDdayCheck);

        memorialOk = (Button) findViewById(R.id.MemorialOk);
        memorialOk.setOnClickListener(this);
        memorialCancel = (Button) findViewById(R.id.MemorialCancel);
        memorialCancel.setOnClickListener(this);
        list=new ArrayList<Person>();
        adapter=new ListViewAdapter(list);
        memorialPersonList.setAdapter(adapter);

        final ScrollView cScroll=(ScrollView)findViewById(R.id.childScroll);
        memorialPersonList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                cScroll.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Person temp;
        if (resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            String phone=cursor.getString(0).replace("-","");
            temp=new Person(cursor.getString(1),cursor.getString(0));
            adapter.add(temp);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.MemorialSetPerson:
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(intent, 0);
                    break;
                case R.id.MemorialSetSticker:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.MemorialOk:
                myJob=new MemorialJob(new MyTime(myLayout.tvStartDate.getText().toString()), new MyTime(myLayout.tvEndDate.getText().toString()));
                myJob.title=myLayout.tvTitle.getText().toString();
                myJob.importantPoint=myLayout.importPoint.getChildCount();
                myJob.pushAlarm=memorialPush.isChecked();
                //illustration
                myJob.illustration=0;
                myJob.hour=myLayout.hour;
                myJob.minute=myLayout.minute;
                myJob.dDayCheck=memorialDday.isChecked();
                myJob.list=new Person[list.size()];
                for(int i=0;i<list.size();i++)
                    myJob.list[i]=new Person(list.get(i).phone,list.get(i).name);
                myJob.content=memorialContent.getText().toString();
                Intent goRegister = new Intent(MainActivity.myAction);
                goRegister.putExtra("ToDo", myJob);
                sendBroadcast(goRegister);
                finish();
                break;
            case R.id.MemorialCancel:
                finish();
                break;
        }
    }

    public class ListViewAdapter extends BaseAdapter{
        private Context mContext=null;
        ArrayList<Person> listPerson=new ArrayList<Person>();
        public ListViewAdapter(ArrayList<Person> arrayList){
            super();
            listPerson.addAll(arrayList);
        }
        @Override
        public int getCount() {
            return listPerson.size();
        }
        @Override
        public Object getItem(int position) {
            return listPerson.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos=position;
            final Context context=parent.getContext();
            CustomHolder holder;
            TextView name;
            TextView phone;
            if(convertView==null){
                LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.person_list,parent,false);
                name=(TextView)convertView.findViewById(R.id.personName);
                phone=(TextView)convertView.findViewById(R.id.personTelephone);
                holder=new CustomHolder();
                holder.name=name;
                holder.phone=phone;
                convertView.setTag(holder);
            }
            else{
                holder=(CustomHolder)convertView.getTag();
                name=holder.name;
                phone=holder.phone;
            }
            name.setText(listPerson.get(position).name);
            phone.setText(listPerson.get(position).phone);
            name.setTag(pos);
            name.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    remove((int)view.getTag());
                    return false;
                }
            });
            return convertView;
        }
        public class CustomHolder{
            TextView name;
            TextView phone;
        }
        public void add(Person temp){
            listPerson.add(temp);
            this.notifyDataSetChanged();
        }
        public void remove(int pos){
            listPerson.remove(pos);
            Log.d("test",listPerson.size()+"");
            this.notifyDataSetChanged();
        }
    }
}
