// IMyAidlInterface.aidl
package com.wxj.customview.aidl;

import com.wxj.customview.aidl.Book;
import java.util.List;
// Declare any non-default types here with import statements

interface IMyAidlInterface {

    List<Book> getBookList();

    void addBook(in com.wxj.customview.aidl.Book book);

}
