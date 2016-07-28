package igor70334.mymovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

@Entity(
        // Specifies the name of the table in the database.
        // By default, the name is based on the entities class name.
        nameInDb = "AWESOME_MOVIES"

)
public class Movie implements Parcelable {

    @Id(autoincrement = true)
    Long mId = null;

    @SerializedName("Title")
    private String mTitle;
    @SerializedName("Year")
    private String mYear;
    @SerializedName("Rated")
    private String mRated;
    @SerializedName("Released")
    private String mReleased;
    @SerializedName("Runtime")
    private String mRuntime;
    @SerializedName("Genre")
    private String mGenre;
    @SerializedName("Director")
    private String mDirector;
    @SerializedName("Writer")
    private String mWriter;
    @SerializedName("Actors")
    private String mActors;
    @SerializedName("Plot")
    private String mPlot;
    @SerializedName("Language")
    private String mLanguage;
    @SerializedName("Country")
    private String mCountry;
    @SerializedName("Awards")
    private String mAwards;
    @SerializedName("Poster")
    private String mPoster;
    @SerializedName("Metascore")
    private String mMetascore;
    @SerializedName("imdbRating")
    private String mImdbRating;
    @SerializedName("imdbVotes")
    private String mImdbVotes;
    @SerializedName("imdbID")
    private String mImdbId;
    @SerializedName("Type")
    private String mType;
    @SerializedName("Response")
    private String mResponse;
    private boolean mIsFavorite;


    public long getId() {
        return mId;
    }
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getYear() {
        return mYear;
    }

    public String getRated() {
        return mRated;
    }

    public String getReleased() {
        return mReleased;
    }

    public String getRuntime() {
        return mRuntime;
    }

    public String getGenre() {
        return mGenre;
    }

    public String getDirector() {
        return mDirector;
    }

    public String getWriter() {
        return mWriter;
    }

    public String getActors() {
        return mActors;
    }

    public String getPlot() {
        return mPlot;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getAwards() {
        return mAwards;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getMetascore() {
        return mMetascore;
    }

    public String getImdbRating() {
        return mImdbRating;
    }

    public String getImdbVotes() {
        return mImdbVotes;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public String getType() {
        return mType;
    }

    public String getResponse() {
        return mResponse;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Movie) {
            Movie another = (Movie) obj;
            return this.getTitle().toLowerCase().equals(another.getTitle().toLowerCase()) &&
                    this.getReleased().equals(another.getReleased());
        } else {
            return false;
        }
    }

    @Keep
    protected Movie(Parcel in) {
        mId = in.readByte() == 0x00 ? null : in.readLong();
        mTitle = in.readString();
        mYear = in.readString();
        mRated = in.readString();
        mReleased = in.readString();
        mRuntime = in.readString();
        mGenre = in.readString();
        mDirector = in.readString();
        mWriter = in.readString();
        mActors = in.readString();
        mPlot = in.readString();
        mLanguage = in.readString();
        mCountry = in.readString();
        mAwards = in.readString();
        mPoster = in.readString();
        mMetascore = in.readString();
        mImdbRating = in.readString();
        mImdbVotes = in.readString();
        mImdbId = in.readString();
        mType = in.readString();
        mResponse = in.readString();
        mIsFavorite = in.readByte() != 0x00;
    }
    @Generated(hash = 1162457084)
    public Movie(Long mId, String mTitle, String mYear, String mRated, String mReleased,
            String mRuntime, String mGenre, String mDirector, String mWriter, String mActors,
            String mPlot, String mLanguage, String mCountry, String mAwards, String mPoster,
            String mMetascore, String mImdbRating, String mImdbVotes, String mImdbId, String mType,
            String mResponse, boolean mIsFavorite) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mYear = mYear;
        this.mRated = mRated;
        this.mReleased = mReleased;
        this.mRuntime = mRuntime;
        this.mGenre = mGenre;
        this.mDirector = mDirector;
        this.mWriter = mWriter;
        this.mActors = mActors;
        this.mPlot = mPlot;
        this.mLanguage = mLanguage;
        this.mCountry = mCountry;
        this.mAwards = mAwards;
        this.mPoster = mPoster;
        this.mMetascore = mMetascore;
        this.mImdbRating = mImdbRating;
        this.mImdbVotes = mImdbVotes;
        this.mImdbId = mImdbId;
        this.mType = mType;
        this.mResponse = mResponse;
        this.mIsFavorite = mIsFavorite;
    }
    @Generated(hash = 1263461133)
    public Movie() {
    }



    @Keep
    @Override
    public int describeContents() {
        return 0;
    }

    @Keep
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mId);
        }
        dest.writeString(mTitle);
        dest.writeString(mYear);
        dest.writeString(mRated);
        dest.writeString(mReleased);
        dest.writeString(mRuntime);
        dest.writeString(mGenre);
        dest.writeString(mDirector);
        dest.writeString(mWriter);
        dest.writeString(mActors);
        dest.writeString(mPlot);
        dest.writeString(mLanguage);
        dest.writeString(mCountry);
        dest.writeString(mAwards);
        dest.writeString(mPoster);
        dest.writeString(mMetascore);
        dest.writeString(mImdbRating);
        dest.writeString(mImdbVotes);
        dest.writeString(mImdbId);
        dest.writeString(mType);
        dest.writeString(mResponse);
        dest.writeByte((byte) (mIsFavorite ? 0x01 : 0x00));
    }
    public String getMResponse() {
        return this.mResponse;
    }
    public void setMResponse(String mResponse) {
        this.mResponse = mResponse;
    }
    public String getMType() {
        return this.mType;
    }
    public void setMType(String mType) {
        this.mType = mType;
    }
    public String getMImdbId() {
        return this.mImdbId;
    }
    public void setMImdbId(String mImdbId) {
        this.mImdbId = mImdbId;
    }
    public String getMImdbVotes() {
        return this.mImdbVotes;
    }
    public void setMImdbVotes(String mImdbVotes) {
        this.mImdbVotes = mImdbVotes;
    }
    public String getMImdbRating() {
        return this.mImdbRating;
    }
    public void setMImdbRating(String mImdbRating) {
        this.mImdbRating = mImdbRating;
    }
    public String getMMetascore() {
        return this.mMetascore;
    }
    public void setMMetascore(String mMetascore) {
        this.mMetascore = mMetascore;
    }
    public String getMPoster() {
        return this.mPoster;
    }
    public void setMPoster(String mPoster) {
        this.mPoster = mPoster;
    }
    public String getMAwards() {
        return this.mAwards;
    }
    public void setMAwards(String mAwards) {
        this.mAwards = mAwards;
    }
    public String getMCountry() {
        return this.mCountry;
    }
    public void setMCountry(String mCountry) {
        this.mCountry = mCountry;
    }
    public String getMLanguage() {
        return this.mLanguage;
    }
    public void setMLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }
    public String getMPlot() {
        return this.mPlot;
    }
    public void setMPlot(String mPlot) {
        this.mPlot = mPlot;
    }
    public String getMActors() {
        return this.mActors;
    }
    public void setMActors(String mActors) {
        this.mActors = mActors;
    }
    public String getMWriter() {
        return this.mWriter;
    }
    public void setMWriter(String mWriter) {
        this.mWriter = mWriter;
    }
    public String getMDirector() {
        return this.mDirector;
    }
    public void setMDirector(String mDirector) {
        this.mDirector = mDirector;
    }
    public String getMGenre() {
        return this.mGenre;
    }
    public void setMGenre(String mGenre) {
        this.mGenre = mGenre;
    }
    public String getMRuntime() {
        return this.mRuntime;
    }
    public void setMRuntime(String mRuntime) {
        this.mRuntime = mRuntime;
    }
    public String getMReleased() {
        return this.mReleased;
    }
    public void setMReleased(String mReleased) {
        this.mReleased = mReleased;
    }
    public String getMRated() {
        return this.mRated;
    }
    public void setMRated(String mRated) {
        this.mRated = mRated;
    }
    public String getMYear() {
        return this.mYear;
    }
    public void setMYear(String mYear) {
        this.mYear = mYear;
    }
    public String getMTitle() {
        return this.mTitle;
    }
    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public Long getMId() {
        return this.mId;
    }
    public void setMId(Long mId) {
        this.mId = mId;
    }
    public boolean getMIsFavorite() {
        return this.mIsFavorite;
    }
    public void setMIsFavorite(boolean mIsFavorite) {
        this.mIsFavorite = mIsFavorite;
    }


    public transient static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}