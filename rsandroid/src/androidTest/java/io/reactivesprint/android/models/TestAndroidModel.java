package io.reactivesprint.android.models;

import android.os.Parcel;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class TestAndroidModel implements IAndroidModel {
    //region Fields

    private String title;

    //endregion

    //region Constructors

    public TestAndroidModel(String title) {
        this.title = title;
    }

    protected TestAndroidModel(Parcel in) {
        title = in.readString();
    }

    //endregion

    //region Properties

    public String getTitle() {
        return title;
    }

    //endregion

    //region Parcelable

    public static Creator<TestAndroidModel> CREATOR = new Creator<TestAndroidModel>() {
        @Override
        public TestAndroidModel createFromParcel(Parcel source) {
            return new TestAndroidModel(source);
        }

        @Override
        public TestAndroidModel[] newArray(int size) {
            return new TestAndroidModel[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    //endregion
}
