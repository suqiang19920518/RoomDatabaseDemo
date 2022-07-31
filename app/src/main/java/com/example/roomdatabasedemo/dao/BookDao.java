package com.example.roomdatabasedemo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdatabasedemo.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    //插入冲突解决方案，默认ABORT(中止)、REPLACE(替换)、IGNORE(忽略插入数据)、ROLLBACK(回滚)、FAIL(失败)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Book book);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Book> books);

    @Update
    void update(Book book);

    @Update
    void update(List<Book> books);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM Book")
    void deleteAll();

    @Query("SELECT * FROM Book")
    List<Book> findAll();

    @Query("SELECT * FROM Book WHERE title = :title")
    Book findByTitle(String title);

    @Query("SELECT * FROM Book WHERE user_id = :userId")
    List<Book> findByUserId(String userId);

//    @Query("SELECT * FROM Book " +
//            "INNER JOIN users ON users.idCard = book.user_id " +
//            "WHERE users.first_name LIKE :name OR users.last_name LIKE :name")
//    List<Book> findByUserName(String name);
}
