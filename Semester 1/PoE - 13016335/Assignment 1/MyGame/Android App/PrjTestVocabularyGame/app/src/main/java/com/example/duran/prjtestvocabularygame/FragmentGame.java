/*
FragmentGame.java
Executes the game play section, produces the animation required and records user interactions
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 4/13/16
 */
package com.example.duran.prjtestvocabularygame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import java.util.Random;

public class FragmentGame extends Fragment implements View.OnClickListener{

    private View fragView;
    private Button btnOne;
    private Button btnTwo;
    private Button btnThree;
    private FragmentMakeWord fragmentMakeWord;
    private Chronometer timerOfButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_game, container, false);
        initializeView();
        timerOfButton.start();

        //Animates each button according to the parameters
        animateView(btnOne);
        animateView(btnTwo);
        animateView(btnThree);
        randomGenerateLetters();
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        return fragView;
    }
    //*************************************************************
    private void initializeView()
    {
        //Initialize all widgets
        btnOne = (Button) fragView.findViewById(R.id.btnFirstLetter);
        btnTwo = (Button) fragView.findViewById(R.id.btnSecondLetter);
        btnThree = (Button) fragView.findViewById(R.id.btnThirdButton);
        timerOfButton = (Chronometer) fragView.findViewById(R.id.chBtuttonTimer);
        fragmentMakeWord = (FragmentMakeWord) getFragmentManager().findFragmentById(R.id.fragMakeWord);
    }
    //*************************************************************
    private void setButtonInvisible(final ObjectAnimator buttonAnimation)
    {
        //Listens for the timer and waits for a time to pass
        timerOfButton.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer)
            {
                if (chronometer.getText().toString().equalsIgnoreCase("00:15"))
                {
                    //Once timer has been reached, stop animation and clear buttons, stop timer
                    buttonAnimation.cancel();
                    timerOfButton.stop();

                    btnOne.clearAnimation();
                    btnOne.setVisibility(View.INVISIBLE);

                    btnTwo.clearAnimation();
                    btnTwo.setVisibility(View.INVISIBLE);

                    btnThree.clearAnimation();
                    btnThree.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    //*************************************************************
    private void setAnimationListener(ObjectAnimator animation)
    {
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //Empty Method
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //Empty Method
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //Empty Method
            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {
                //After each repeat, text of the characters are randomly generated
                //and display on the buttons
                randomGenerateLetters();
            }
        });
    }
    //*************************************************************
    private void animateView(Button button)
    {
        //Animates all three buttons and sets the appropriate parameters
        ObjectAnimator buttonAnimation = ObjectAnimator.ofFloat(button,"translationY",0f, 375f);
        buttonAnimation.setDuration(2000);
        buttonAnimation.setRepeatCount(Animation.INFINITE);
        buttonAnimation.setRepeatMode(Animation.INFINITE);
        buttonAnimation.setInterpolator(new AccelerateInterpolator() );
        buttonAnimation.start();
        setAnimationListener(buttonAnimation);
        setButtonInvisible(buttonAnimation);
    }
    //*************************************************************
    @Override
    public void onClick(View sender)
    {
        //Retrieves any button click and displays the text of the button in another fragment make word
        final Button senderAsButton = (Button) sender;
        fragmentMakeWord.displayLetter(senderAsButton.getText().charAt(0));
    }
    //*************************************************************
    private void randomGenerateLetters()
    {
        //Randomly generates 3 different characters for the buttons
        //Using the ascii table
        Button [] arrButton = {btnOne,btnTwo,btnThree};
        char randomCharacter;
        Random ranNum = new Random();

        for (Button button : arrButton) {
            randomCharacter = (char) (ranNum.nextInt(122 - 97 + 1) + 97);
            button.setText(String.valueOf(randomCharacter));
        }
    }
}
