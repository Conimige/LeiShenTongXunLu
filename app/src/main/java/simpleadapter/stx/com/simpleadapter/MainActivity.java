package simpleadapter.stx.com.simpleadapter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import simpleadapter.stx.com.simpleadapter.util.DBHelper;
import simpleadapter.stx.com.simpleadapter.util.DBManager;
import simpleadapter.stx.com.simpleadapter.util.MyAdapter;
import simpleadapter.stx.com.simpleadapter.util.MyButton;
import simpleadapter.stx.com.simpleadapter.util.MyMD5;
import simpleadapter.stx.com.simpleadapter.util.Person;


public class MainActivity extends BaseActivity {

    private ListView lv;
    private List<Map<String,Object>> datas;
    private MyAdapter sa;
    private EditText et;
    private EditText pt;
    private long exitTime;
    private Button addBtn;
    private DBManager mgr;
    private ImageView imgSmallTitle;
    private int titlePic_id;
    public static String localUserName = "";
    private Bitmap bmpDefaultPic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mTintManager.setStatusBarDarkMode(true, this);


        mgr = new DBManager(this);

        lv=(ListView)findViewById(R.id.listView);
        addBtn=(Button)findViewById(R.id.newButton);
        imgSmallTitle=(ImageView)findViewById(R.id.ImageTitle2);

        addBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showMessageByToast("����������ϵ��");
                return false;
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,addActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //et=(EditText)findViewById(R.id.name);
        //pt=(EditText)findViewById(R.id.phone);


        Integer[] mButtonState = { R.drawable.add_button,
                R.drawable.btn_add, R.drawable.btn_add_on };
        Button mButton = (Button) findViewById(R.id.newButton);
        MyButton myButton = new MyButton(this);
        mButton.setBackgroundDrawable(myButton.setbg(mButtonState));


        registerForContextMenu(lv);


        localUserName = mgr.queryDB("localuser", "name", "");

        String path = "/data/data/simpleadapter.stx.com.simpleadapter/files/titlepic_" + localUserName + ".png";
        File file=new File(path);
        ImageView iv = (ImageView) findViewById(R.id.ImageTitle2);
        if (file.exists()){
            bmpDefaultPic = null;
            if (bmpDefaultPic == null)
                bmpDefaultPic = BitmapFactory.decodeFile(path, null);
            iv.setImageBitmap(bmpDefaultPic);
        }else{
            Log.i("localUserName",localUserName);
            if (!localUserName.equals(""))
                iv.setImageResource(R.drawable.title_logined);
        }


        //titlePic_id=R.drawable.title_cs;

        if (localUserName.equalsIgnoreCase("")) {
            titlePic_id = R.drawable.title_add;
        } else {
            //titlePic_id = R.drawable.title_cs;
            //((ImageView) findViewById(R.id.ImageTitle2)).setImageResource(R.drawable.title_cs_blur);
            showMessageByToast(">>�û�" + localUserName + "�ѵ�¼");
        }
        //String localUserName=mgr.queryDB("localuser","user","");

        datas=new ArrayList<Map<String,Object>>();
//
//        //һ����
//        Map<String,String> data1= new HashMap<String,String>();
//        data1.put("name","����");
//        //data1.put("age","21");
//        //data1.put("gender","male");
//        data1.put("phone", "18875050386");
//
//        //�ڶ�����
//        Map<String,String> data2= new HashMap<String,String>();
//        data2.put("name","Ф����");
//        //data2.put("age","22");
//        //data2.put("gender","male");
//        data2.put("phone","18502339836");
//
//        //��������
//        Map<String,String> data3= new HashMap<String,String>();
//        data3.put("name", "̷��ϼ");
//        //data3.put("age","30");
//        //data3.put("gender","male");
//        data3.put("phone","15334513069");
//
//        Map<String,String> data4= new HashMap<String,String>();
//        data4.put("name", "�λ�");
//        data4.put("phone","18723549123");
//
//        Map<String,String> data5= new HashMap<String,String>();
//        data5.put("name", "������");
//        data5.put("phone","18883339682");
//
//        Map<String,String> data6= new HashMap<String,String>();
//        data6.put("name", "��˧");
//        data6.put("phone","13310237646");
//
//
//        datas.add(data1);
//        datas.add(data2);
//        datas.add(data3);
//        datas.add(data4);
//        datas.add(data5);
//        datas.add(data6);

//        sa=new SimpleAdapter(this,datas,R.layout.layout_item,new String[]{"name","age","gender","phone"},
//                new int[]{R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView5});
//        sa=new SimpleAdapter(this,datas,R.layout.layout_item,new String[]{"name","phone"},
//                new int[]{R.id.textView2,R.id.textView3});
        refreshList();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView personName = (TextView) view.findViewById(R.id.textView2);
                TextView phoneNumber = (TextView) view.findViewById(R.id.textView3);
                TextView TxtPersonSex = (TextView) view.findViewById(R.id.sex);
//                ImageView callBtn = (ImageView) view.findViewById(R.id.btn_callItem);

//                callBtn.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        showMessageByToast("SDSD");
//                    }
//                });
//                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenum.getText().toString()));
//                startActivity(intent);
                //Toast.makeText(MainActivity.this,name.getText().toString(),Toast.LENGTH_SHORT).show();
                String resID = ((TextView) getViewByPosition(position, lv).findViewById(R.id.resID))
                        .getText().toString();
                Intent intent = new Intent();
                intent.putExtra("viewname", personName.getText().toString());
                intent.putExtra("viewphone", phoneNumber.getText().toString());
                intent.putExtra("viewsex",TxtPersonSex.getText().toString());
                intent.putExtra("resID", resID);
                intent.setClass(MainActivity.this, viewActivity.class);

                startActivity(intent);
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View c = lv.getChildAt(0);
                if (c != null) {
                    if (c.getHeight() >= 200) {
//                        if(c.getTop()<-370 ){
                        //imgSmallTitle.setVisibility(View.VISIBLE);
//                        }else{
//                            imgSmallTitle.setVisibility(View.GONE);
//                        }
                        Log.i(">>>h:", c.getTop() + "");
                        if (c.getTop() > -340) {
                            imgSmallTitle.setTop(c.getTop());
                        } else {
                            imgSmallTitle.setTop(-340);
                        }

                    }
                }
            }
        });

        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... params) {
                String url = "http://kongzue.sinaapp.com/LSTXL/getVersion";
                String result = "";
                try {
                    //��������
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    //���ò�������html���ύ
                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    BasicNameValuePair param = new BasicNameValuePair("ver", "preview0033");
                    paramList.add(param);

                    post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
                    //����HttpPost���󣬲�����HttpResponse����
                    HttpResponse httpResponse = httpClient.execute(post);
                    // �ж�������Ӧ״̬�룬״̬��Ϊ200��ʾ����˳ɹ���Ӧ�˿ͻ��˵�����
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //��ȡ���ؽ��
                        result = EntityUtils.toString(httpResponse.getEntity());
                        //showMessageByToast(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //showMessageByToast("����!");
                }
                return result;
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                //showMessageByToast("" + result);
            }
        }.execute();
    }

    public void callPhoneNumber(String phoneNum){
        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        startActivity(intent);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1, 0 ,menu.NONE,"�鿴");
        menu.add(1, 1 ,menu.NONE,"���");
        menu.add(1, 2 ,menu.NONE,"ɾ��");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acm=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String phoneNumber=((TextView)getViewByPosition(acm.position, lv).findViewById(R.id.textView3)).getText().toString();
        String personName=((TextView)getViewByPosition(acm.position, lv).findViewById(R.id.textView2)).getText().toString();
        String personSex=((TextView)getViewByPosition(acm.position, lv).findViewById(R.id.sex)).getText().toString();
        switch(item.getItemId()) {
            case 0 :
                //do �鿴

                //showMessageByToast(((TextView)getViewByPosition(acm.position, lv).findViewById(R.id.textView3)).getText().toString());
                String resID=((TextView) getViewByPosition(acm.position, lv).findViewById(R.id.resID))
                        .getText().toString();
                Intent intent=new Intent();
                intent.putExtra("viewname",personName);
                intent.putExtra("viewphone",phoneNumber);
                intent.putExtra("viewsex",personSex);
                intent.putExtra("resID",resID);
                intent.setClass(this,viewActivity.class);

                startActivity(intent);
                break;
            case 1:
                // do ���
                Intent intent2=new Intent();
                intent2.setClass(this,addActivity.class);
                startActivityForResult(intent2, 1);
                break;
            case 2:
                // do ɾ��
                Log.i("itemclick", "1");

                Person person = new Person(personName, phoneNumber, "",personSex);
                mgr.delete(person);
                datas.remove(acm.position);
                sa.notifyDataSetChanged();
                showMessageInTaskBar("����½ս�ӡ�ͨѶ¼","��ϵ��"+personName+"��ɾ��","�ɹ�ɾ����ϵ�ˣ�");

                TableSynchronize();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

//    public void addPerson(View v){
//        String name=et.getText().toString();
//        String phone=pt.getText().toString();
//        Map<String,String> data= new HashMap<String,String>();
//        data.put("name",name);
//        data.put("phone",phone);
//
//        datas.add(data);
//        sa.notifyDataSetChanged();
//
//        et.setText("");
//        pt.setText("");
//        Toast.makeText(MainActivity.this,"��ϵ��<"+name+">�Ѿ���ӳɹ���",Toast.LENGTH_SHORT).show();
//    }

    public void showMessageByToast(String Msg){
        Toast.makeText(MainActivity.this,Msg,Toast.LENGTH_SHORT).show();
    }

//    public void showMessage(View v){
//        String name="";//et.getText().toString();
////        String phone =pt.getText().toString();
////        Map<String,String> data= new HashMap<String,String>();
////        data.put("name",name);
////        data.put("phone", phone);
////
////        datas.add(data);
////        sa.notifyDataSetChanged();
////
////        et.setText("");
////        pt.setText("");
//        Intent intent=new Intent();
//        intent.setClass(this,addActivity.class);
//        startActivityForResult(intent, 1);
//
//    }

    public void showMessageInTaskBar(String Title,String message,String Tip){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        Intent resultIntent=new Intent(this, MainActivity.class);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT );

        builder.setContentTitle(Title);
        builder.setContentText(message);
        builder.setTicker(Tip);
        builder.setSmallIcon(R.drawable.btn_add);
        builder.setAutoCancel(true);    //���õ�����Զ�ɾ��
        //builder.setOngoing(true);       //���ò���ɾ��

        builder.setContentIntent(resultPendingIntent);
        NotificationManager me=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        me.notify(1, builder.build());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //Log.i("Main", "requestCode:"+requestCode+"resultCode:"+resultCode);

        if(resultCode==2){
            if(requestCode==1){
                String name=data.getStringExtra("addname");
                String phone =data.getStringExtra("addphone");
                String sex =data.getStringExtra("addsex");
                Map<String,Object> addData= new HashMap<String,Object>();
                addData.put("name", name);
                addData.put("phone", phone);
                addData.put("sex", sex);
                addData.put("icon", R.drawable.user_ico);
                //addData.put("titleBKG", R.drawable.img_alpha);

                //���ݿ�
                ArrayList<Person> persons = new ArrayList<Person>();
                persons.add(new Person(name, phone, "",sex));
                if(mgr.addPerson(persons,this)){
                    datas.add(addData);
                    sa.notifyDataSetChanged();
                    showMessageInTaskBar("����½ս�ӡ�ͨѶ¼", "��ϵ��" + name + "����ӣ�", "��ӳɹ���");
                    TableSynchronize();
                }

                //Toast.makeText(MainActivity.this, data.getStringExtra("addname")+";"+data.getStringExtra("addphone"),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void TableSynchronize(){
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... params) {
                String url = "http://kongzue.sinaapp.com/LSTXL/UpdateUserTable";
                String result = "";
                if (localUserName.equals("")){
                    return "";
                }
                try {
                    //��������
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    //���ò�������html���ύ
                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    BasicNameValuePair param = new BasicNameValuePair("username", localUserName);
                    BasicNameValuePair param1 = new BasicNameValuePair("table", mgr.getAllUserTable());
                    BasicNameValuePair param2 = new BasicNameValuePair("key", MyMD5.MD5(localUserName + new SimpleDateFormat("yyyyMMdd").format(new Date())));
                    paramList.add(param);
                    paramList.add(param1);
                    paramList.add(param2);

                    post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
                    //����HttpPost���󣬲�����HttpResponse����
                    HttpResponse httpResponse = httpClient.execute(post);
                    // �ж�������Ӧ״̬�룬״̬��Ϊ200��ʾ����˳ɹ���Ӧ�˿ͻ��˵�����
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //��ȡ���ؽ��
                        result = EntityUtils.toString(httpResponse.getEntity());
                        //showMessageByToast(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //showMessageByToast("����!");
                }
                return result;
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                if (!((String)result).equals("")){
                    showMessageByToast("ͬ���ɹ���");
                }else{
                    showMessageByToast("ͬ��ʧ�ܣ�");
                }
            }
        }.execute();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }









    //�������ݿ�
//    public void addDB(View view) {
//        ArrayList<Person> persons = new ArrayList<Person>();
//
//        Person person1 = new Person("����", "18875050386", "");
//        Person person2 = new Person("Ф����", "18502339836", "");
//        Person person3 = new Person("̷��ϼ", "15334513069", "");
//        Person person4 = new Person("�λ�", "18723549123", "");
//        Person person5 = new Person("������", "18883339682", "");
//        Person person6 = new Person("��˧", "13310237646", "");
//
//        persons.add(person1);
//        persons.add(person2);
//        persons.add(person3);
//        persons.add(person4);
//        persons.add(person5);
//        persons.add(person6);
//
//        mgr.add(persons);
//    }

    public void update(View view) {
        Person person = new Person();
        person.name = "Jane";
        person.phone = "";
        mgr.updatePhone(person);
    }

    public void delete(View view) {
        Person person = new Person();
        person.phone = "";
        mgr.delete(person);
    }

    public void refreshList() {
        List<Person> persons = mgr.query();
        Object[] obj=persons.toArray();       //for (int i=0;i<obj.length;i++) {
        HashMap<String, Object> map_title = new HashMap<String, Object>();
        map_title.put("name", "");
        map_title.put("phone", "");
        map_title.put("icon", 0);
        map_title.put("resID", 0);
        map_title.put("sex", "0");
        map_title.put("titleBKG", R.drawable.title_add);
        datas.add(map_title);


        for (Person person : persons) {
           //for (int i=0;i<obj.length;i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", person.name);
            map.put("phone", person.phone);
            map.put("sex", person.sex);
            int resID=0;
            switch (person.phone){
                case "18875050386":
                    resID = R.drawable.p_chao;
                    break;
                case "18502339836":
                    resID = R.drawable.p_dou;
                    break;
                case "15334513069":
                    resID = R.drawable.p_tan;
                    break;
                case "18723549123":
                    resID = R.drawable.p_hua;
                    break;
                case "18883339682":
                    resID = R.drawable.p_yun;
                    break;
                case "13310237646":
                    resID = R.drawable.p_shuai;
                    break;
                default:
                    resID = R.drawable.user_ico;
            }

            map.put("icon", resID);
            map.put("resID", resID);
            map.put("titleBKG", R.drawable.img_alpha);
            //Log.i(person.name, "" + R.drawable.img_alpha);
            datas.add(map);
        }
        sa=new MyAdapter(this,datas,R.layout.layout_item,new String[]{"name","phone","icon","resID","titleBKG","sex"},
                new int[]{R.id.textView2,R.id.textView3,R.id.image_people,R.id.resID,R.id.ImageTitle,R.id.sex});

        lv.setAdapter(sa);


    }


}
