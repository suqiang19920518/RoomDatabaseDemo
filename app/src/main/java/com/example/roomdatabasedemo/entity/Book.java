package com.example.roomdatabasedemo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 两个数据表关联
 */
@Entity(foreignKeys = @ForeignKey(entity = UserInfo.class,
                                  parentColumns = "idCard",
                                  childColumns = "user_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
                                  indices = {@Index(value = "user_id")})
public class Book {

    @PrimaryKey
    private int bookId; //必须要有,且不为空

    private String title;

    @ColumnInfo(name = "user_id")
    private String userId;


    /**
     * Room默认使用该构造器
     *
     * @param bookId
     * @param title
     * @param userId
     */
    public Book(int bookId, String title, String userId) {
        this.bookId = bookId;
        this.title = title;
        this.userId = userId;
    }

    /**
     * Room只能识别一个构造器，如果希望定义多个构造器
     * 可以使用Ignore标签，让Room忽略这个构造器
     *
     * @param bookId
     * @param title
     */
    @Ignore
    public Book(int bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }

    @Ignore
    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
