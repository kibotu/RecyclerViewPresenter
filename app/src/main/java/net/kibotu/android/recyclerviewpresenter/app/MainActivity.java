package net.kibotu.android.recyclerviewpresenter.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kotlin.Unit;
import net.kibotu.android.recyclerviewpresenter.v2.PresenterAdapter;
import net.kibotu.android.recyclerviewpresenter.v2.RecyclerViewModel;

import java.util.UUID;

import static android.text.TextUtils.isEmpty;
import static java.text.MessageFormat.format;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class MainActivity extends AppCompatActivity {

    @NonNull
    @BindView(R.id.list)
    RecyclerView list;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        runPagination();
        runAdapterV2();

        unbinder = ButterKnife.bind(this);

        PresenterAdapter<RecyclerViewModel<String>> adapter = new PresenterAdapter<>();
        list.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        list.setAdapter(adapter);

        adapter.setOnItemClick((item, rowView, position) -> {
            toast(format("{0}. {1}", position, item));
            return Unit.INSTANCE;
        });

        for (int i = 0; i < 1000; ++i) {
            RecyclerViewModel<String> viewModel = new RecyclerViewModel<>(FakeDataGenerator.INSTANCE.createRandomImageUrl(), UUID.randomUUID().toString(), null);
            adapter.add(viewModel, PhotoPresenterV2.class, 0);
            adapter.add(viewModel, LabelPresenterV2.class, 0);
        }

        // sorting
        // PresenterAdapter.sort(adapter);
        // sort if model doesn't implement Comparable
        // adapter.sortBy((o1, o2) -> o1.compareTo(o2));

//        adapter.update(0, "https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png");

        adapter.notifyDataSetChanged();
    }

    private void runPagination() {
        startActivity(new Intent(this, PaginationActivity.class));
    }

    private void runAdapterV2() {
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    public void toast(@Nullable final String message) {
        if (isEmpty(message))
            return;

        runOnUiThread(() -> {
            final Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        });
    }
}
