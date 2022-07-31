package com.example.roomdatabasedemo.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.commonsware.cwac.saferoom.SafeHelperFactory;
import com.example.roomdatabasedemo.converters.DateConverters;
import com.example.roomdatabasedemo.converters.ListConverters;
import com.example.roomdatabasedemo.dao.BookDao;
import com.example.roomdatabasedemo.dao.UserDao;
import com.example.roomdatabasedemo.entity.Book;
import com.example.roomdatabasedemo.entity.UserInfo;

import java.io.File;
import java.util.concurrent.Executor;

/**
 * 写成抽象类可以不实现默认方法，编译时候会生成一个DataBase实现类
 * exportSchema = true 生成数据库创建表或升级等操作及字段描述的json文件
 */
@Database(entities = {UserInfo.class, Book.class}, version = 4, exportSchema = false)
@TypeConverters({ListConverters.class, DateConverters.class})
public abstract class UserDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "users.db";
    private volatile static UserDatabase mInstance;

    //创建DAO的抽象类
    public abstract UserDao getUserDao();

    //创建DAO的抽象类
    public abstract BookDao getBookDao();

    public static UserDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UserDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context, UserDatabase.class, DATABASE_NAME)
                            //版本升级时，数据库数据将会被清空，数据表被重新创建
//                            .fallbackToDestructiveMigration()
                            //允许在主线程中操作数据库（不推荐）【不加这行代码，直接在主线程中操作数据库，会抛异常】
//                            .allowMainThreadQueries()
                            //数据库创建和打开后的回调
                            .addCallback(callback)
                            //从文件系统预填充
//                            .createFromFile(new File("mypath"))
                            //从应用资源预填充
//                            .createFromAsset("database/myapp.db")
                            //设置查询的线程池
//                            .setQueryExecutor()
                            //设置数据工厂，默认FrameworkSQLiteOpenHelperFactory
//                            .openHelperFactory(new SafeHelperFactory())
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                            .build();

                }
            }
        }
        return mInstance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            initializeData();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static void initializeData() {
        Log.e("opendatabase", "初始化准备");
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS book " +
                    "(bookId INTEGER NOT NULL, " +
                    "title TEXT, " +
                    "user_id TEXT, " +
                    "PRIMARY KEY(bookId))");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users " +
                    "ADD COLUMN sex INTEGER NOT NULL DEFAULT 0"); //不添加这个NOT NULL DEFAULT 0，可能会报错
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users ADD COLUMN birthday INTEGER");
            database.execSQL("ALTER TABLE Book ADD COLUMN title TEXT");
        }
    };

}
