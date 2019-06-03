package net.kibotu.android.recyclerviewpresenter.app.java;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.exozet.android.core.misc.FakeDataGenerator;
import kotlin.Unit;
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter;
import net.kibotu.android.recyclerviewpresenter.PresenterModel;
import net.kibotu.android.recyclerviewpresenter.app.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.text.MessageFormat.format;
import static net.kibotu.logger.Logger.snackbar;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
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

        List<PresenterModel<String>> items = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            items.add(new PresenterModel<>(FakeDataGenerator.createRandomImageUrl(), R.layout.photo_presenter_item, UUID.randomUUID().toString(), null, null));
            items.add(new PresenterModel<>(FakeDataGenerator.createRandomImageUrl(), R.layout.label_presenter_item, UUID.randomUUID().toString(), null, null));
            items.add(new PresenterModel<>(FakeDataGenerator.createRandomImageUrl(), R.layout.number_presenter_item, UUID.randomUUID().toString(), null, null));
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