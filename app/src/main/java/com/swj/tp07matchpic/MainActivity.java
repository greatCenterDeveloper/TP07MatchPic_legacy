package com.swj.tp07matchpic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView ivStart, ivHowto;
    ImageView[] ivAnimalArr;
    ImageView ivBoard;
    SoundPool sp;
    int sdStart, sdSelect, sdAgain, sdGoodJob;
    ArrayList<Integer> animalArr;
    ArrayList<Integer> animalBoardArr;
    int animalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivStart = findViewById(R.id.iv_start);
        ivHowto = findViewById(R.id.iv_howto);
        ivBoard = findViewById(R.id.iv_board);

        ivAnimalArr = new ImageView[5];
        ivAnimalArr[0] = findViewById(R.id.iv_animal01);
        ivAnimalArr[1] = findViewById(R.id.iv_animal02);
        ivAnimalArr[2] = findViewById(R.id.iv_animal03);
        ivAnimalArr[3] = findViewById(R.id.iv_animal04);
        ivAnimalArr[4] = findViewById(R.id.iv_animal05);

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(10);
        sp = builder.build();
        sdStart = sp.load(this, R.raw.s_sijak, 0);
        sdSelect = sp.load(this, R.raw.s_select, 0);
        sdAgain = sp.load(this, R.raw.s_again, 0);
        sdGoodJob = sp.load(this, R.raw.s_goodjob, 0);

        animalArr = new ArrayList<>();
        for(int i=0; i<ivAnimalArr.length; i++)
            animalArr.add(R.drawable.a_ele + i);
        /*animalArr.add(R.drawable.a_ele);
        animalArr.add(R.drawable.a_frog);
        animalArr.add(R.drawable.a_lion);
        animalArr.add(R.drawable.a_monkey);
        animalArr.add(R.drawable.a_pig);*/

        animalBoardArr = new ArrayList<>();
        for(int i=0; i<ivAnimalArr.length; i++)
            animalBoardArr.add(R.drawable.b_ele + i);
        /*animalBoardArr.add(R.drawable.b_ele);
        animalBoardArr.add(R.drawable.b_frog);
        animalBoardArr.add(R.drawable.b_lion);
        animalBoardArr.add(R.drawable.b_monkey);
        animalBoardArr.add(R.drawable.b_pig);*/

        ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivHowto.setVisibility(View.VISIBLE);
                animalShuffle();
                sp.play(sdStart, 0.9f, 0.9f, 3, 0, 1.0f );
            }
        });

        ivHowto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(R.layout.dialog);
                builder.setPositiveButton("확인",(dialogInterface, i) -> {});
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        for(int i=0; i<ivAnimalArr.length; i++)
            ivAnimalArr[i].setOnClickListener(listener);
    }

    private void animalShuffle() {
        Collections.shuffle(animalArr);

        for(int i=0; i<ivAnimalArr.length; i++) {
            ivAnimalArr[i].setImageResource(animalArr.get(i));
            ivAnimalArr[i].setTag(animalArr.get(i));
        }

        ivBoard.setImageResource(animalBoardArr.get(animalCount));

        animalArr.clear();
        for(int i=0; i<ivAnimalArr.length; i++)
            animalArr.add(R.drawable.a_ele + i);
        ivBoard.setTag(animalArr.get(animalCount));

        animalCount++;
        if(animalCount > 4) animalCount = 0;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int myChoiceAnimalTag = (Integer) view.getTag();
            int animalBoardTag = (Integer) ivBoard.getTag();

            if(myChoiceAnimalTag == animalBoardTag) {
                sp.play(sdGoodJob, 0.8f, 0.8f, 2, 0, 1.0f);
                animalShuffle();
            } else sp.play(sdAgain, 0.8f, 0.8f, 2, 0, 1.0f);

        }
    };
}