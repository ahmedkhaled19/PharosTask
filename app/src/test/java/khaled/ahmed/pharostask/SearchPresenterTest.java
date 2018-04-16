package khaled.ahmed.pharostask;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import khaled.ahmed.pharostask.Objects.Cities;
import khaled.ahmed.pharostask.Presenters.SearchPresenter;
import khaled.ahmed.pharostask.Utils.SharedData;
import khaled.ahmed.pharostask.Views.SearchView;

/**
 * Created by ah.khaled1994@gmail.com on 4/16/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchPresenterTest {
    @Mock
    private SearchView view;
    @Mock
    private Context context;
    private SearchPresenter presenter;


    @Before
    public void setUp() throws Exception {
        presenter = new SearchPresenter(view, context);
    }

    @Test
    public void search() throws Exception {
        presenter.search("1");
    }

}