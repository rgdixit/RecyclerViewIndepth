package janika.co.roposo;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import janika.co.roposo.models.AuthorDetails;
import janika.co.roposo.models.PreFetchLayoutManager;

public class MainSearchActivity extends AppCompatActivity implements AuthorDetailsAdapter.onFollowButtonClickListener {

    private AuthorDetails[] details;
    private RecyclerView mRecyclerView;
    private AuthorDetailsAdapter authorDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        JsonParser jsonParser = new JsonParser();
        String json;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.data);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            details  = new Gson().fromJson(json, AuthorDetails[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.author_details_list);
        mRecyclerView.setLayoutManager(new PreFetchLayoutManager(this));
        authorDetailsAdapter = new AuthorDetailsAdapter(this, details, this);
        mRecyclerView.setAdapter(authorDetailsAdapter);
    }


    @Override
    public void onFollowButtonClicked(boolean following, HashSet<Integer> positions) {

        if(following) {
            String text = getString(R.string.following);
            Drawable drawable = getDrawable(R.drawable.followed_button_background);
            updateButtons(positions, text, drawable, following);
        }
        else {
            String text = getString(R.string.follow);
            Drawable drawable = getDrawable(R.drawable.follow_button_background);
            updateButtons(positions, text, drawable, following);
        }
    }

    private void updateButtons(HashSet<Integer> positions, String text, Drawable drawable, boolean following) {
        PreFetchLayoutManager pflm  = (PreFetchLayoutManager) mRecyclerView.getLayoutManager();
        int first = pflm.findFirstVisibleItemPosition();
        int last =  pflm.findLastVisibleItemPosition();
        for(int pos : positions) {
            details[pos].setIsFollowing(following);
             if(pos >=first && pos <= last) {
                View view = pflm.findViewByPosition(pos);
                Button button  = (Button) view.findViewById(R.id.user_follow_button);
                button.setText(text);
                button.setBackground(drawable);
             }
        }
    }
}
