package janika.co.roposo.models;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class PreFetchLayoutManager extends LinearLayoutManager {

    public PreFetchLayoutManager(Context context) {
        super(context);
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        return 500;
    }
}
