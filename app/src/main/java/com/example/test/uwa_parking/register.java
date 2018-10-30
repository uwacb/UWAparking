package com.example.test.uwa_parking;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class register extends Activity {

    Button button_signin = null;
    Button button_goback = null;
    TextView result_show = null;
    TextView text_name = null;
    TextView text_pass = null;
    TextView text_repass = null;
    TextView text_email = null;
    RadioGroup group_role = null;
    RadioGroup group_permission = null;
    RadioButton role_staff = null;
    RadioButton role_student = null;
    RadioButton permission_red = null;
    RadioButton permission_yellow = null;
    Socket socket;
    ExecutorService mThreadPool;
    String read = "";
    static Handler mMainHandler;
    String role;
    String permission;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mThreadPool = Executors.newCachedThreadPool();
        button_signin = (Button)findViewById(R.id.signin);
        button_goback = (Button)findViewById(R.id.goback);
        text_name = (TextView)findViewById(R.id.et_name);
        text_pass = (TextView)findViewById(R.id.et_pass);
        text_repass = (TextView)findViewById(R.id.et_repass);
        text_email = (TextView)findViewById(R.id.et_email);
        group_role = (RadioGroup)findViewById(R.id.rg_role);
        group_permission = (RadioGroup)findViewById(R.id.rg_permission);
        role_staff = (RadioButton)findViewById(R.id.rb_staff);
        role_student = (RadioButton)findViewById(R.id.rb_student);
        permission_red = (RadioButton)findViewById(R.id.rb_red);
        permission_yellow = (RadioButton)findViewById(R.id.rb_yellow);

        result_show = (TextView)findViewById(R.id.register_result);

        mMainHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (read.charAt(1) == 'p')
                        {
                            result_show.setText("register successful");
                        }
                        else if (read.charAt(1) == 'r')
                        {
                            text_name.setText("");
                            text_pass.setText("");
                            text_repass.setText("");
                            result_show.setText("user already exist");
                        }
                        else
                        {
                            text_name.setText("");
                            text_pass.setText("");
                            text_repass.setText("");
                            result_show.setText("unexceptted mistake happen");
                        }
                        break;
                }
            }
        };


        group_role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (role_staff.getId()==checkedId)
                {
                    role = "staff";
                }
                if (role_student.getId()==checkedId)
                {
                    role = "student";
                }
            }
        });

        group_permission.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (permission_red.getId()==checkedId)
                {
                    permission = "red";
                }
                if (permission_yellow.getId()==checkedId)
                {
                    permission = "yellow";
                }
            }
        });

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = text_name.getText().toString().trim();
                final String pass = text_pass.getText().toString().trim();
                String repass = text_repass.getText().toString().trim();
                final String email = text_email.getText().toString().trim();

                if((pass.length()==0) || (name.length()==0) || (repass.length()==0))
                {
                    Toast.makeText(register.this,"please input name and password",Toast.LENGTH_SHORT).show();
                }
                else if (!pass.equals(repass))
                {
                    Toast.makeText(register.this,"please input the same password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(register.this,"sign in successful",Toast.LENGTH_SHORT).show();
                    mThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                socket = new Socket("106.14.213.85",9999);
                                OutputStream outputStream = socket.getOutputStream();
                                DataOutputStream writer = new DataOutputStream(outputStream);
                                InputStream is = socket.getInputStream();
                                DataInputStream dis = new DataInputStream(is);
                                String temp = 'r' + name + '/' + pass + '/' + email + '/' + role + '/' + permission + '/';
                                writer.writeUTF(temp);
                                read = dis.readUTF();
                                if (read.charAt(0) == 'r')
                                {
                                    Message msg = Message.obtain();
                                    msg.what = 0;
                                    mMainHandler.sendMessage(msg);
                                }
                                dis.close();
                                is.close();
                                writer.close();
                                outputStream.close();
                                socket.close();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });


                }
            }
        });

        button_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
