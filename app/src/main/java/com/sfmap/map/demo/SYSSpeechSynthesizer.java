package com.sfmap.map.demo;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import java.util.Locale;


public class SYSSpeechSynthesizer implements SpeechSynthesizer,TextToSpeech.OnInitListener {
    // TTS对象
    private TextToSpeech mTextToSpeech;

    private SYSSpeechSynthesizer(Context context) {
        init(context);
    }

    private static SYSSpeechSynthesizer instance;

    public static SYSSpeechSynthesizer getInstance(Context context) {
        if (instance == null) {
            instance = new SYSSpeechSynthesizer(context);
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        mTextToSpeech = new TextToSpeech(context, this);
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        mTextToSpeech.setPitch(1.0f);
        // 设置语速
        mTextToSpeech.setSpeechRate(1f);
    }

    /**
     * 用来初始化TextToSpeech引擎
     *
     * @param status SUCCESS或ERROR这2个值
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            /*
                使用的是小米手机进行测试，打开设置，在系统和设备列表项中找到更多设置，
            点击进入更多设置，在点击进入语言和输入法，见语言项列表，点击文字转语音（TTS）输出，
            首选引擎项有三项为Pico TTs，科大讯飞语音引擎3.0，度秘语音引擎3.0。其中Pico TTS不支持
            中文语言状态。其他两项支持中文。选择科大讯飞语音引擎3.0。进行测试。

                如果自己的测试机里面没有可以读取中文的引擎，
            那么不要紧，我在该Module包中放了一个科大讯飞语音引擎3.0.apk，将该引擎进行安装后，进入到
            系统设置中，找到文字转语音（TTS）输出，将引擎修改为科大讯飞语音引擎3.0即可。重新启动测试
            Demo即可体验到文字转中文语言。
             */
            // setLanguage设置语言
            int result = mTextToSpeech.setLanguage(Locale.CHINA);
            // TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失
            // TextToSpeech.LANG_NOT_SUPPORTED：不支持
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean startSpeaking(String text) {
        if (TextUtils.isEmpty(text)) {
//            Toast.makeText(this, "请您输入要朗读的文字", Toast.LENGTH_SHORT).show();
            return false;
        }

        // TODO validate success, do something
        if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
            /*
                TextToSpeech的speak方法有两个重载。
                // 执行朗读的方法
                speak(CharSequence text,int queueMode,Bundle params,String utteranceId);
                // 将朗读的的声音记录成音频文件
                synthesizeToFile(CharSequence text,Bundle params,File file,String utteranceId);
                第二个参数queueMode用于指定发音队列模式，两种模式选择
                （1）TextToSpeech.QUEUE_FLUSH：该模式下在有新任务时候会清除当前语音任务，执行新的语音任务
                （2）TextToSpeech.QUEUE_ADD：该模式下会把新的语音任务放到语音任务之后，
                等前面的语音任务执行完了才会执行新的语音任务
             */
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
        }
        return false;
    }

    @Override
    public boolean stop() {
        if (mTextToSpeech != null){
            mTextToSpeech.stop();
        }

        return false;
    }

    @Override
    public void destroy() {
        if (mTextToSpeech  != null) {
            mTextToSpeech .shutdown();
            mTextToSpeech .stop();
            mTextToSpeech = null;
        }
    }
}
