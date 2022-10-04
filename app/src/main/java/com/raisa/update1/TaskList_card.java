package com.raisa.update1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class TaskList_card extends AppCompatActivity {
    TextView addTask;
    int l, m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_card);

        addTask = findViewById(R.id.addTask);


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateTaskeDialog();
            }
        });
    }

    private void showCreateTaskeDialog()
    {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_create_task);
        LottieAnimationView lottieAdd;
        lottieAdd = dialog.findViewById(R.id.btnAdd);

        //moner moto coding*******************************************
        dialog.show();

        lottieAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (delete_on){
                    lottieDelete.setMinAndMaxProgress(0.0f,1.0f);
                    lottieDelete.playAnimation();
                    delete_on = false;

                }
                else
                {
                    lottieDelete.setMinAndMaxProgress(0.0f,1.0f);
                    lottieDelete.playAnimation();
                    delete_on = true;
                }*/
                lottieAdd.setMinAndMaxProgress(0.0f,1.0f);
                lottieAdd.playAnimation();
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

}