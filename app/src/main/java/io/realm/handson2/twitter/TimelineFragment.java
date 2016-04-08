package io.realm.handson2.twitter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.handson2.twitter.entity.Tweet;

public class TimelineFragment extends ListFragment {
    private Realm realm;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //noinspection ConstantConditions
        final ListView listView = getListView();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        realm = Realm.getDefaultInstance();

        final RealmResults<Tweet> tweets = buildTweetList(realm);
        final RealmBaseAdapter<Tweet> adapter = new RealmBaseAdapter<Tweet>(getContext(), tweets, true) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final Tweet tweet = getItem(position);

                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.listitem_tweet, parent, false);
                }

                // TODO 余裕があればViewHolderパターンを適用してください
                ((TextView) convertView.findViewById(R.id.screen_name)).setText(tweet.getScreenName());
                ((TextView) convertView.findViewById(R.id.text)).setText(tweet.getText());

                listView.setItemChecked(position, tweet.isFavorited());
                return convertView;
            }
        };

        setListAdapter(adapter);
    }

    @NonNull
    protected RealmResults<Tweet> buildTweetList(Realm realm) {
        return realm.allObjectsSorted(Tweet.class, "createdAt", Sort.DESCENDING);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((RealmBaseAdapter<?>) getListAdapter()).updateRealmResults(null);
        realm.close();
        realm = null;
    }
}
