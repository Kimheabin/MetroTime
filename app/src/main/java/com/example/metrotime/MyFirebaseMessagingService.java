package com.example.metrotime;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //토큰을 서버로 전송
    }

    //클라우드 서버에서 메시지를 전송하면 자동으로 호출
    //이 메서드 안에서 메시지를 처리해 사용자에게 알림을 보낼 수 있다
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //FCM을 통해 전달받은 정보에 Notification 정보가 있는 경우 : 알림 생성
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage);
        } else {
            Log.d(TAG, "수신 에러: Notification이 비어있습니다.");
        }
    }

    //FCM에서 보낸 정보를 바탕으로 디바이스에 Notification을 생성한다
    //remoteMessage FCM에서 보낸 데이터 정보들을 저장한다
    private void sendNotification(RemoteMessage remoteMessage) {
        int id = 0;
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        //알림 채널 설정
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        //채널 생성
        String channelId = "Metrotime_ChannelID";
        android.net.Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Android 8.0 (Oreo) 이상에서만 채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(id, notificationBuilder.build());
    }
}
