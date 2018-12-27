package promethean2k18.com.Login_signup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

import promethean2k18.com.Activities.MainActivity;
import promethean2k18.com.R;

public class IntroActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        PaperOnboardingPage scr1 = new PaperOnboardingPage("How it works",
                "Let's get started with the app",
                Color.parseColor("#2196f3"), R.drawable.how, R.drawable.ic_navigate_next_black_24dp);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Register",
                "Signup using your email or phone",
                Color.parseColor("#ec407a"), R.drawable.register, R.drawable.ic_verified_user_black_24dp);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Find our events",
                "Have a look at all the events provided and register with us",
                Color.parseColor("#ff7043"), R.drawable.search, R.drawable.ic_search_black_24dp);
        PaperOnboardingPage scr4 = new PaperOnboardingPage("Payment",
                "You can also pay the registration fee through app" ,
                Color.parseColor("#66bb6a"), R.drawable.pay, R.drawable.ic_payment_black_24dp);
        PaperOnboardingPage scr6 = new PaperOnboardingPage("You are done",
                "That's it, wait for the event and be there!" ,
                Color.parseColor("#7e57c2"), R.drawable.tick_circle, R.drawable.ic_done_all_black_24dp);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        elements.add(scr4);
        elements.add(scr6);

        PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(elements);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment);
        fragmentTransaction.commit();

        onBoardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment bf = new BlankFragment();
                fragmentTransaction.replace(R.id.fragment_container, bf);
               // startActivity(new Intent(IntroActivity.this,AskPhone.class));
                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                fragmentTransaction.commit();
                finish();
            }
        });
    }
}
