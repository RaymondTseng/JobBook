package com.example.jobbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 17-1-19.
 */

public class TypePersonBean extends PersonBean implements Parcelable{

//    private static final long serialVersionUID = 1L;

    private int type;

    public TypePersonBean(PersonBean personBean, int type) {
        super(personBean);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.type);
    }

    protected TypePersonBean(Parcel in) {
        super(in);
        this.type = in.readInt();
    }

    public static final Creator<TypePersonBean> CREATOR = new Creator<TypePersonBean>() {
        @Override
        public TypePersonBean createFromParcel(Parcel source) {
            return new TypePersonBean(source);
        }

        @Override
        public TypePersonBean[] newArray(int size) {
            return new TypePersonBean[size];
        }
    };
}
