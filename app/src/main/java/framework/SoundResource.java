package framework;

/**
 * Created by Callum on 30/10/2015.
 */


import android.app.Activity;

import framework.implementation.AndroidAudio;

public class SoundResource {

    private Audio MyAudio;
    private Sound MySound;

    public SoundResource(Activity Act){
        MyAudio = new AndroidAudio(Act);
    }

    public void load(String resourcePath){
        MySound = MyAudio.createSound(resourcePath);
    }

    public void play(){
        MySound.play((float)0.9);
    }

    public void stop(){
        MySound.stop();
    }
}
