package igor70334.mymovies;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.greendao.database.Database;

import igor70334.mymovies.data.model.DaoMaster;
import igor70334.mymovies.data.model.DaoSession;

/**
 * @author Igor Vilela Damasceno
 * @since 7/25/16.
 */
public class MyApplication extends Application {
    private DaoSession mDaoSession;

    public static MyApplication get(Context context) {
        return (MyApplication) context.
                getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "movies-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

        Fresco.initialize(this);
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
