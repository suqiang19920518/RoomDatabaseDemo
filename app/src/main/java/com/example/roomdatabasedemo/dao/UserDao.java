package com.example.roomdatabasedemo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdatabasedemo.bean.NameTuple;
import com.example.roomdatabasedemo.entity.UserInfo;

import java.util.Date;
import java.util.List;

@Dao
public interface UserDao {

    /**
     * 注意，冒号后面必须紧跟参数名，中间不能有空格。大于小于号和冒号中间是有空格的。
     * select * from users where【表中列名】 =:【参数名】------>等于
     * where 【表中列名】 < :【参数名】------>小于
     * where 【表中列名】 between :【参数名1】 and :【参数2】------->这个区间
     * where 【表中列名】 like :【参数名】----->模糊查询
     * where 【表中列名】 in (:【参数名集合】)---->查询符合集合内指定字段值的记录
     */

    //插入冲突解决方案，默认ABORT(中止)、REPLACE(替换)、IGNORE(忽略插入数据)、ROLLBACK(回滚)、FAIL(失败)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserInfo userInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<UserInfo> userInfos);

    //只能传递对象，更新时根据UserInfo中的主键来比对的
    @Update
    void update(UserInfo userInfo);

    @Update
    void update(List<UserInfo> userInfos);

    @Query("UPDATE users SET age = :age WHERE idCard = :idCard")
    void updateAge(String idCard, int age);

    //只能传递对象，删除时根据UserInfo中的主键来比对的
    @Delete
    void delete(UserInfo userInfo);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT * FROM users")
    List<UserInfo> findAll();

    @Query("SELECT * FROM users WHERE post_code = :postCode")
    UserInfo findByPostCode(int postCode);

    @Query("SELECT * FROM users WHERE first_name LIKE :search OR last_name LIKE :search")
    UserInfo findByName(String search);

    @Query("SELECT * FROM users WHERE age BETWEEN :minAge AND :maxAge")
    List<UserInfo> findByAge(int minAge, int maxAge);

    @Query("SELECT * FROM users WHERE age > :minAge LIMIT 2")
    List<UserInfo> findByAge(int minAge);

    @Query("SELECT * FROM users WHERE idCard in (:idCards)")
    List<UserInfo> findByIdCard(List<String> idCards);

    @Query("SELECT first_name, last_name FROM users WHERE age < :maxAge")
    List<NameTuple> findFullName(int maxAge);

    @Query("SELECT * FROM users WHERE birthday BETWEEN :from AND :to")
    UserInfo findByBirth(Date from, Date to);
}
