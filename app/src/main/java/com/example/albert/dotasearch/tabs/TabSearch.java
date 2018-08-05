package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.activity.FoundPlayerActivity;
import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class TabSearch extends AbstractTabFragment {
    private final static int LAYOUT = R.layout.fragment_search;
    private final static String TAG = "TabSearch";

    private Unbinder unbinder;
    public Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    String editText;

    @BindView(R.id.btn_search) Button btnSearch;
    @BindView(R.id.search_edit) EditText searchEditText;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        final View view = inflater.inflate(LAYOUT, container, false);

        context = view.getContext();

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(btnSearch != null && btnSearch.getVisibility() == View.INVISIBLE){
            btnSearch.setVisibility(View.VISIBLE);
        }
        if(progressBar != null && progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static TabSearch getInstance(Context context) {
        Bundle args = new Bundle();
        TabSearch fragment = new TabSearch();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.search));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @OnClick(R.id.btn_search)
    public void onClick(final View v) {
        editText = searchEditText.getText().toString();
        if(editText.trim().length() == 0){
            Toast.makeText(v.getContext(), "Вы не ввели не 1 символа ((", Toast.LENGTH_LONG).show();
        } else {
            btnSearch.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            /*Disposable d1 = Completable.fromAction(() -> db.foundPlayerDao().deleteAllFoundPlayer())
                    .andThen(
                            UtilDota.initRetrofitRx().getFoundPlayersRx(editText)
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::storeFoundPlayersToBD,
                            this::handleError,
                            this::goToFoundPlayerActivity
                    );*/

            UtilDota.initRetrofitRx().getFoundPlayersRx(editText)
                    .flatMap(foundPlayers -> {
                        App.get().getDB().foundPlayerDao().deleteAllFoundPlayer();
                        App.get().getDB().foundPlayerDao().insertAll(foundPlayers);
                        return Observable.fromIterable(foundPlayers);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response -> Log.d(TAG, response.getPersonaname()),
                            this::handleError,
                            this::goToFoundPlayerActivity
                    );


            /*UtilDota.initRetrofitRx().getFoundPlayersRx(editText)
                    .flatMap(foundPlayers -> Completable.fromAction(
                            () -> db.foundPlayerDao().deleteAllFoundPlayer())
                            .andThen(
                                    Completable.fromAction(() -> db.foundPlayerDao().insertAll(foundPlayers))
                            ).toObservable())
                    //.ignoreElements()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            System.out::println,
                            this::handleError,
                            this::goToFoundPlayerActivity
                    );*/
        }

    }

    public void handleError(Throwable t){
        btnSearch.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Log.e(TAG, "handleError: " + t.getMessage() + " " + t.getLocalizedMessage());
        //Toast.makeText(v.getContext(), t.getMessage() + " " + 44, Toast.LENGTH_LONG).show();
    }

    public void goToFoundPlayerActivity(){
        Log.d(TAG, "goToFoundPlayerActivity");
        Intent intent = new Intent(context, FoundPlayerActivity.class);
        startActivity(intent);
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        compositeDisposable.dispose();
    }
}
