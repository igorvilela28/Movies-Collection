package igor70334.mymovies.data.model;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import igor70334.mymovies.data.model.Movie;
import igor70334.mymovies.data.model.MovieDao;

public class MovieTest extends AbstractDaoTestLongPk<MovieDao, Movie> {

    public MovieTest() {
        super(MovieDao.class);
    }

    @Override
    protected Movie createEntity(Long key) {
        Movie entity = new Movie();
        entity.setMId();
        return entity;
    }

}
