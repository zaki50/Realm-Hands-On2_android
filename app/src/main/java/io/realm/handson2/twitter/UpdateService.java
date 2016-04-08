package io.realm.handson2.twitter;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.handson2.twitter.entity.Tweet;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class UpdateService extends IntentService {
    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        loadTimeline();
    }

    private void loadTimeline() {
        final Twitter twitter = TwitterFactory.getSingleton();

        final ResponseList<Status> homeTimeline;
        try {
            homeTimeline = twitter.getHomeTimeline();
        } catch (TwitterException e) {
            Toast.makeText(this, "通信でエラーが発生しました: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("RealmTwitter", "通信でエラーが発生しました。", e);
            return;
        }

        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for (Status status : homeTimeline) {
                        final Tweet tweet = new Tweet(status);
                        realm.copyToRealm(tweet);
                    }
                }
            });
        } finally {
            realm.close();
        }
    }
}
