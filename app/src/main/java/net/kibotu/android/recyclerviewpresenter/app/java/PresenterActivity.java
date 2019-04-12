package net.kibotu.android.recyclerviewpresenter.app.java;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.exozet.android.core.misc.FakeDataGenerator;
import kotlin.Unit;
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter;
import net.kibotu.android.recyclerviewpresenter.app.R;

import static java.text.MessageFormat.format;
import static net.kibotu.logger.Logger.toast;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class PresenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.list);

        PresenterAdapter<String> adapter = new PresenterAdapter<>();
        list.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        list.setAdapter(adapter);

        adapter.onItemClick((item, rowView, position) -> {
            toast(format("{0}. {1}", position, item));
            return Unit.INSTANCE;
        });

        for (int i = 0; i < 100; ++i) {
            adapter.append(FakeDataGenerator.createRandomImageUrl(), PhotoPresenter.class, null);
            adapter.append(FakeDataGenerator.createRandomImageUrl(), LabelPresenter.class, null);
        }

        adapter.update(0, "https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png", PhotoPresenter.class, false, null);

        adapter.notifyDataSetChanged();
    }
}