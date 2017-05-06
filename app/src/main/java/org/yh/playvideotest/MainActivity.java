package org.yh.playvideotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 1);
        }
        else
        {
            initVideoPath(); // 初始化MediaPlayer
        }
    }

    private void initView()
    {
        videoView = (VideoView) findViewById(R.id.video_view);
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!videoView.isPlaying())
                {
                    videoView.start(); // 开始播放
                }
            }
        });
        findViewById(R.id.puse).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (videoView.isPlaying())
                {
                    videoView.pause(); // 暂停播放
                }
            }
        });
        findViewById(R.id.replay).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (videoView.isPlaying())
                {
                    videoView.resume(); // 重新播放
                }
            }
        });
    }

    private void initVideoPath()
    {
        File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
        //videoView.setVideoPath(file.getPath()); // 指定视频文件的路径
        //播放sd卡中的视频的代码
        //file:///sdcard/video/test.3gp
        //播放当前工程中的视频的代码
        //android.resource://包名/"+R.raw.tianyi
        Log.e(TAG, file.getPath());
        Log.e(TAG, "file://" + Environment.getExternalStorageDirectory().getPath() + File
                .separator + "movie.mp4");
        videoView.setVideoURI(Uri.parse("file://" + Environment.getExternalStorageDirectory()
                .getPath() + File
                .separator + "movie.mp4"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    initVideoPath();
                }
                else
                {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //释放资源
        if (null != videoView)
        {
            videoView.suspend();
        }
    }
}
