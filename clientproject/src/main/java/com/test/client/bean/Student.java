package com.test.client.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class Student implements Parcelable {

    public int studentId;
    public String name;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }


    protected Student(Parcel in) {
        studentId = in.readInt();
        name = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(studentId);
        dest.writeString(name);
    }
}
