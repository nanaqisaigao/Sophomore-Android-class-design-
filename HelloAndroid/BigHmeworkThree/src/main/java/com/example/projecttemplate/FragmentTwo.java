package com.example.projecttemplate;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTwo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText phone;
    private EditText msg;

    private Button btn_pick;
    private Button btn_send;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_pick:
                    getContact();
                    break;
                case R.id.btn_sendsms:
                    String s1 = phone.getText().toString();   //联系人电话号码
                    String s2 = msg.getText().toString();    //短信内容
                    sendSMS(s1, s2);
                    break;
            }
        }
    };
    private void getContact() {
        //选取联系人
        Log.d("flag", "选取联系人");
        permissionLauncher1.launch(Manifest.permission.READ_CONTACTS);
    }

    //注册权限请求 通过 ActivityResultLauncher 动态申请读取联系人权限
    ActivityResultLauncher<String> permissionLauncher1 = registerForActivityResult(
            //RequestPermission 单个权限请求
            new ActivityResultContracts.RequestPermission(),
            //registerForActivityResult() 是 startActivityForResult() + onActivityResult() 的替代，是一种全新的
            // ActivityResults API，它简化了数据回调的写法
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        contactLauncher.launch(null);
                    } else {
                        Toast.makeText(getActivity(), "未授予[读取联系人]权限", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    //查看并选择联系人   通过用户选择的联系人 uri 查询 Id 值，以 id 值为条件来查询对应联系人的电话
    ActivityResultLauncher<Void> contactLauncher = registerForActivityResult(
            //PickContact 通过Intent.ACTION_PICK选择联系人
            new ActivityResultContracts.PickContact(),
            new ActivityResultCallback<Uri>() {
                //registerForActivityResult() 是 startActivityForResult() + onActivityResult() 的替代，是一种全新的Activity
                //Results API，它简化了数据回调的写法
                @Override
                public void onActivityResult(Uri result) {
                    if (result == null) return;
                    Cursor cursor = getActivity().getContentResolver()
                            .query(result, null, null, null, null);
                    /*
        第一个参数：电话号码
        第二个参数：运营商，传入null就行，系统会自动调用
        第三个参数，短信的内容
        第四个，第五个参数：短信发送状态的广播，这里不用广播，传入null
         */
                    if (cursor != null && cursor.moveToFirst()) {
                        int id_index = cursor.getColumnIndex("_id");
                        String contactId = cursor.getString(id_index);
                        Cursor cursor2 = getActivity().getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, "contact_id = ?", new String[]{contactId}, null
                        );
                        if (cursor2 != null && cursor2.moveToFirst()) {
                            String phone0 = "";
                            do {
                                int index = cursor2.getColumnIndex("data1");
                                phone0 += cursor2.getString(index);
                                phone0 += ' ';
                            } while (cursor2.moveToNext());
                            phone.setText(phone0.trim());
                            cursor2.close();
                        }
                        cursor.close();
                    }
                }
            }
    );




    //注册权限请求
    ActivityResultLauncher<String> permissionLauncher2 = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (!result) {
                        Toast.makeText(getActivity(), "未授予[短信发送]权限", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


    //通过 ActivityResultLauncher 动态申请短信发送权限
    private void sendSMS(String s1, String s2) {
   /*     对联系人电话进行校验，一方面保证不是空值，另一方面，一个联系人可能有多
        个号码，保证选择唯一号码发送
        使用 TimePicker+AlarmManager 实现用户设置倒计时时间触发指令
        封装携带电话号码和信息内容数据，可以启动短信发送服务的 intent，通过
        PendingIntent.getService 得到延迟后台服务，并在时钟倒计时后启动后台服
                务发送短信*/

        //使用闹钟+SmsManager定时发送短信
        Log.d("flag", s1 + " " + s2);
        permissionLauncher2.launch(Manifest.permission.SEND_SMS);//请求权限
        if (s1.trim().isEmpty()) {
            Toast.makeText(getActivity(), "请选择发送联系人号码", Toast.LENGTH_SHORT).show();
        } else if (s1.trim().length() > 15) {
            Toast.makeText(getActivity(), "请选择唯一有效号码", Toast.LENGTH_SHORT).show();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            TimePickerDialog tpd = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    //显示intent 给延迟intent用
                    Intent intent = new Intent(getActivity(), SmsService.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", s1);
                    bundle.putString("msg", s2);
                    intent.putExtras(bundle);
                    //延迟intent
                    PendingIntent pendingIntent = PendingIntent.getService(
                            getActivity(), 100, intent, PendingIntent.FLAG_IMMUTABLE);
                    Calendar tmp = Calendar.getInstance();
                    tmp.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    tmp.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    tmp.set(Calendar.MINUTE, minute);
                    tmp.set(Calendar.SECOND, 0);
                    AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    manager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, tmp.getTimeInMillis(), pendingIntent);
                    //                        ① 闹钟类型：设备休眠也会被唤醒 ② 闹钟第一次执行时间③ 给闹钟添加PendingIntent，在闹钟定时结束时才执行
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            tpd.show();
        }

    }
    public FragmentTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTwo newInstance(String param1, String param2) {
        FragmentTwo fragment = new FragmentTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        btn_pick = view.findViewById(R.id.btn_pick);      //选取联系人
        btn_send = view.findViewById(R.id.btn_sendsms);   //发送按钮

        phone = view.findViewById(R.id.et_phone);       //联系人文本框
        msg = view.findViewById(R.id.et_msg);           //短信内容文本框


        btn_pick.setOnClickListener(listener);
        btn_send.setOnClickListener(listener);

        return view;
    }





}