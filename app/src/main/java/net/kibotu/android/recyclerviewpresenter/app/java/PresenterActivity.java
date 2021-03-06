package net.kibotu.android.recyclerviewpresenter.app.java;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import net.kibotu.android.recyclerviewpresenter.PresenterAdapter;
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel;
import net.kibotu.android.recyclerviewpresenter.app.R;
import net.kibotu.android.recyclerviewpresenter.app.misc.FakeDataGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import kotlin.Unit;

import static java.text.MessageFormat.format;
import static net.kibotu.logger.Logger.snackbar;

/**
 * Created by <a href="https://kibotu.net">Jan Rabe</a>.
 */

public class PresenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.list);
        SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);

        PresenterAdapter adapter = new PresenterAdapter();

        adapter.registerPresenter(new PhotoPresenter());
        adapter.registerPresenter(new LabelPresenter());
        adapter.registerPresenter(new NumberPresenter());

        list.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        list.setAdapter(adapter);

        adapter.onItemClick((item, rowView, position) -> {
            snackbar(format("{0}. {1}", position, item));
            return Unit.INSTANCE;
        });

        ArrayList<PresenterViewModel<?>> items = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            items.add(new PresenterViewModel<>(FakeDataGenerator.createRandomImageUrl(), R.layout.photo_presenter_item, UUID.randomUUID().toString(), null, null, null));
            items.add(new PresenterViewModel<>(FakeDataGenerator.createRandomImageUrl(), R.layout.label_presenter_item, UUID.randomUUID().toString(), null, null, null));
            items.add(new PresenterViewModel<>(i, R.layout.number_presenter_item, UUID.randomUUID().toString(), null, null, null));
        }

        Collections.shuffle(items);

        adapter.submitList(items);

        swipeRefresh.setOnRefreshListener(() -> {
            Collections.shuffle(items);
            adapter.submitList(items);
            swipeRefresh.setRefreshing(false);
        });
    }
}